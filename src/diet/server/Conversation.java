/*
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *     Please, whatever you do, don't delete or rewrite any of the existing code in this class....
 * 
 *     But please DO ADD whatever you need!
 * 
 * 
 *  * 
 * 
 * 
 * 
 * 
 * 
 */
package diet.server;

import java.io.File;
import java.util.Date;
import java.util.Vector;

import lib.bsh.Interpreter;
import client.MazeGameController2WAY;
import client.MazeGameController3WAY;
import diet.client.StyledDocumentStyleSettings;
import diet.debug.Debug;
import diet.message.*;
import diet.parameters.ExperimentSettings;
import diet.parameters.ui.SavedExperimentsAndSettingsFile;
import diet.server.CbyC.DocChange;
import diet.server.CbyC.DocInsert;
import diet.server.CbyC.DocRemove;
import diet.server.ConversationController.CCBeanShell;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.conversationhistory.ConversationHistory;
import diet.server.experimentmanager.EMUI;
import diet.server.io.ConversationIO;
import diet.task.TaskControllerInterface;
import diet.task.tangram2D1M.TangramGameController;
import java.awt.Color;
import java.lang.reflect.Method;


/**
 * This is the main server class. Each experiment has an associated Conversation object. The Conversation object
 * acts as the intermediary between the clients. It constantly checks for incoming messages, and relays them to a
 * ConversationController object that determines whether the message should be transformed.
 *
 * The Conversation object contains methods that should be used to send messages to clients.
 *
 * @author user
 */


public class Conversation extends Thread{

    private ConversationHistory cH;
    private DefaultConversationController cC;
    private ConversationUIManager cHistoryUIM;
    private DeprecatedWindowController_MustReplace permissionsController;       private Participants ps;
    private boolean conversationIsActive = true;
    private boolean conversationThreadHasTerminated=false;
    private String conversationIdentifier;
    private DocChangesIncoming turnsconstructed = new DocChangesIncoming(this);
    private WordNetWrapper wnw;// = new WordNetWrapper();
    private ConversationIO convIO;
    private ExperimentSettings expSettings;
    private ExperimentManager expManager;
    private TaskControllerInterface tc; //= new MazeGameController(this);

    static Conversation statC;



    /**
     * Constructor for interventions programmed with BeanShell. Using this is not really recommended
     * @param expM
     * @param i
     */
    public Conversation(ExperimentManager expM, Interpreter i){
        statC=this;
        try{
          this.expManager=expM;
          expSettings = (ExperimentSettings)i.eval("getExperimentParameters()");

          i.set("cH", cH);
          i.set("c",this);
          i.set("ps", ps);
          i.set("expSett",expSettings);

         doBasicSetup();
         cC = new CCBeanShell(this,expSettings,i);
         i.set("cC", cC);
         cC.setControllerAndStartIntercept(cC);

         this.expManager.emui.println("Main", "Experiment: "+this.getConversationIdentifier()+" setup successfully. Waiting for Participants to login");
       }catch(Exception e){
          System.err.println("ERROR INITIALIZING CONVERSATION: "+e.getMessage().toString());
      }

  }

    
     public Conversation(ExperimentManager expM,String nameOfDefaultConversationController){
         this.expManager=expM;
         statC=this;
         try{  
              Class c = Class.forName( "diet.server.ConversationController."+nameOfDefaultConversationController);
              Method  method = c.getMethod ("getDefaultSettings");
              ExperimentSettings eSett = (ExperimentSettings)method.invoke (null);
              this.expSettings = eSett;
              this.doBasicSetup(nameOfDefaultConversationController);
              }catch (Exception e){
                      e.printStackTrace();
              }      
     }
         
 
           
          
            
             
         
     


  /**
   * Preferred constructor
   * @param expM ExperimentManager
   * @param expSettings settings of experiment
   */
  public Conversation(ExperimentManager expM,ExperimentSettings expSettings){
       statC=this;
       this.expSettings=expSettings;
        this.expManager=expM;
        doBasicSetup();
        if(cC==null){
            System.err.println("NO CONTROLLER");
            //System.exit(-200);
            if(this.expManager.emui!=null){
               this.expManager.emui.println("Main", "Experiment: "+this.getConversationIdentifier()+" could not start. Could not find ConversationController");
            }
        }
        if(this.expManager.emui!=null){
           this.expManager.emui.println("Main", "Experiment: "+this.getConversationIdentifier()+" setup done. Waiting for Participants to login");
        }
     }

  
    
     private void doBasicSetup(){
         doBasicSetup(null);
     }
  
  
    /**
     *
     * This method retrieves the individual settings for the Conversation by extracting
     * the {@link diet.parameters.Parameter} objects from {@link diet.parameters.ExperimentSettings}.
     * It instantiates the correct {@link ConversationController} using dynamic class loading if necessary, {@link TaskController} (if any), ensures the data will be saved
     * in a particular directory.
     *
     * <p>After initializing the components, it starts the ConversationController Thread.
     *
     * <p>Editing this method has to be done carefully. The sequence of the objects that are initialized is very
     * important as there are many complex interdependencies.
     *
     */
    private void doBasicSetup(String nameOfConversationController){
        //Editing this method has to be done carefully
        //The sequence of the objects that are initialized
        //Is very important..

        conversationIdentifier = (String)expSettings.getV("Experiment ID");


        //wnw = new WordNetWrapper(System.getProperty("user.dir")+File.separator+"utils");

        //String chattoolinterface = (String)expSettings.getV("Chat tool interface");
        
        String cCType = null;
        if(nameOfConversationController!=null){
            cCType = nameOfConversationController;
        }
        else{
            cCType = (String)expSettings.getV("Conversation Controller");
        }
        
       
        
        
        
        try{
            cCType = cCType.trim();
            Class c = null;
            if(cCType.contains("diet.server.ConversationController")){
               c=Class.forName(cCType);
            }
            else{
               c = Class.forName( "diet.server.ConversationController."+cCType);
            }
            DefaultConversationController dcc = (DefaultConversationController)c.newInstance();
            cC=dcc;
            ////MOVED THIS FROM AFTER THE CATCH BELOW....
             //MIGHT NEED TO REDO THIS
            
            convIO = new ConversationIO(this,System.getProperty("user.dir")+File.separator+"data"+File.separator+"Saved experimental data",conversationIdentifier,cC.getTurnTypeForIO_DEPRECATED());
            SavedExperimentsAndSettingsFile.writeAllParameterObjects(new File(convIO.getFileNameContainingConversationData(),"expparameters.xml"), expSettings.getParameters());
             
            
            
            dcc.initialize(this, expSettings);
            
            SavedExperimentsAndSettingsFile.writeAllParameterObjects(new File(convIO.getFileNameContainingConversationData(),"expparameters.xml"), expSettings.getParameters());
            
            
            
           }catch(Exception e){
                  System.err.println("COULD NOT FIND AND DYNAMICALLY LOAD CONVERSATION CONTROLLER...trying to load"+cCType);
                  e.printStackTrace();
                  if(this.expManager.emui!=null){
                    this.expManager.emui.print("Main","Could not dynamically load "+cCType);
                    e.printStackTrace();

                  }else{
                      System.err.println("Could not dynamically load "+cCType);
                      e.printStackTrace();
                  }
           }
         
            
        
        
        
        ////String parserFileLocation = (String)expSettings.getV("Parser file location");
        String parserFileLocation = System.getProperty("user.dir") +File.separator+"experimentresources"+ File.separator
			+ "utils" + File.separator+"englishPCFG.ser.gz";
        cH= new ConversationHistory(this,conversationIdentifier,  parserFileLocation, convIO);
        cHistoryUIM = new ConversationUIManager(cH,this);

        cH.setConversationUIManager(cHistoryUIM);
        ps = new Participants(this);

        int maxNumberOfParticipants = (Integer)expSettings.getV("Number of participants per conversation");
        String windowNumberingPolicy = (String)expSettings.getV("Window numbering policy");

         permissionsController = new DeprecatedWindowController_MustReplace(this,ps,0,windowNumberingPolicy);
       
        // permissionsController = new WindowAndRecipientsManager(this,ps,maxNumberOfParticipants,windowNumberingPolicy);
       
        permissionsController = new DeprecatedWindowController_MustReplace(this,ps,100,windowNumberingPolicy);
        //ps.start();
        if(cC!=null) {
            cC.setControllerAndStartIntercept(cC);
        }
        String taskC = (String)expSettings.getV("TaskController");
        if(taskC!=null){
          if(taskC.equalsIgnoreCase("Maze2"))this.tc=new MazeGameController2WAY(this);
          else if(taskC.equalsIgnoreCase("Maze3"))this.tc=new MazeGameController3WAY(this);
          else if(taskC.equalsIgnoreCase("Tangram2D1M"))
          {
              String experimentID=(String)expSettings.getV("Experiment ID");
              System.out.println("experiment id is:"+experimentID);

                  this.tc=new TangramGameController(this, experimentID);
          }

        }
        this.expManager.connectUIWithExperimentManager(this,cHistoryUIM);

    }

    
    
    public File getConversationDirectory(){
       return  convIO.getFileNameContainingConversationData();
    }
    
    
    /**
     * Returns the collection of tables that are associated with this Conversation
     * @return ConversationUIManager
     */
    public ConversationUIManager getCHistoryUIM() {
        return cHistoryUIM;
    }


    

    public synchronized boolean requestPermissionForNewParticipantToBeAddedToConversation(String participantID){
        return cC.requestParticipantJoinConversation(participantID);
    }




    /**
     * Adds participant to the Conversation and sends the participant a MessageClientSetupParameters message with
     * the necessary window and client settings.
     *
     * @param p Participant to be added to the Conversation
     */
    public synchronized boolean addNewParticipant(Participant p){
        
          Vector participants = ps.getAllParticipants();
        
          for(int i=0;i<participants.size();i++){
              Participant pAlreadyLoggedIn = (Participant)participants.elementAt(i);
              if(p.getParticipantID().equals(pAlreadyLoggedIn.getParticipantID())){
                    String messageToDisplay = "There is an error: A participant with a duplicate ParticipantID is logging in:\n"+
                            pAlreadyLoggedIn.getParticipantID()+", "+pAlreadyLoggedIn.getUsername()+" is already logged in\n"+
                            p.getParticipantID()+", "+p.getUsername()+" is about to log in\n"
                            + "THIS SHOULD NOT HAPPEN! PLEASE CHECK THE INSTRUCTIONS GIVEN TO THE PARTICIPANTS!!!\n"
                            + "IF YOU HAVE JUST STARTED AN EXPERIMENT, YOU MUST RESTART THE SERVER AND CLIENTS!!!! DO NOT CONTINUE!\n"
                            + "IF YOU ARE IN THE MIDDLE OF RUNNING AN EXPERIMENT, YOU SHOULD PROBABLY PRESS OK AND HOPE FOR THE BEST!";
                    
                            
                     this.saveDataToConversationHistory("ERROR", messageToDisplay);
                    CustomDialog.showDialog(messageToDisplay);
                   
              }
          }



         // if(p.getUsername().equalsIgnoreCase("HELLO"))return false;

        //Possible thread error if this thread sleeps inbetween adding participant and sending the default message
        //As the conversation might send a message before the participant has received the chat window setup.


        //Get the information from the Permissions
        //create the send client setup parameters

        //Ensure that the permissions are set up properly
        //There also has to be a script detailing how any new participant is
        //Dealt with, whether the participant that is newly added will be enabled
        //And who the participant can receive from
        //
        //Ensure that participant being added doesn't have the same name'
      try{
        if(Debug.debugGROOP4)System.err.println("ADDPARTICIPANTA: "+p.getUsername());
        ps.addNewParticipant(p);
        //Permission perm = ps.getPermissions(Participant p);
        int ownWindowNumber = permissionsController.getWindownumber_DefaultsToZero(p,p);
        MessageClientSetupParameters mcsp = diet.message.MessageClientInterfaceSetupParameterFactory.generateClientChatToolSetupMessage(ownWindowNumber,expSettings);
        mcsp.setNewEmailAndUsername(p.getParticipantID(),p.getUsername());
        p.sendMessage(mcsp);
        System.out.println("added new participant "+p.getParticipantID());
        if(this.expManager.emui!=null){
           this.expManager.emui.println("Main", "Participant with email: "+p.getParticipantID()+" and with username: "+p.getUsername()+" has logged in to "+this.getConversationIdentifier()+" there are "+ps.getAllParticipants().size());
        }
      }catch (Exception e){
         System.err.println("Problem adding new participant");
      }
      if(tc!=null){
          tc.participantJoinedConversation_StartingTask(p);
      }
      if(Debug.debugGROOP4)System.err.println("ADPRT: "+p.getUsername());
      cC.participantJoinedConversation(p);
       if(Debug.debugGROOP4)System.err.println("ADPRT9: "+p.getUsername());
      return true;
      }

    /**
     * This method still needs to be implemented and verified.
     * @param p
     */
    public void reactivateParticipant(Participant p){

       try{
        Conversation.printWSln("Main", "LOGGINGA");
        int ownWindowNumber = permissionsController.getWindownumber_DefaultsToZero(p,p);
        Conversation.printWSln("Main", "LOGGINGB");
        MessageClientSetupParameters mcsp = diet.message.MessageClientInterfaceSetupParameterFactory.generateClientChatToolSetupMessage(ownWindowNumber,expSettings);
        Conversation.printWSln("Main", "LOGGINGC");
        mcsp.setNewEmailAndUsername(p.getParticipantID(),p.getUsername());
        Conversation.printWSln("Main", "LOGGINGD");
        p.sendMessage(mcsp);
        Conversation.printWSln("Main", "LOGGINGE");
        cC.participantRejoinedConversation(p);
        CustomDialog.showModelessDialog("Participant ID: "+p.getParticipantID()+" USERNAME"+p.getUsername()+" was reconnected.");
        
        Conversation.printWSln("Main", "LOGGINGF");
        Conversation.printWSln("Main", "Participant "+p.getParticipantID()+" reactivated ");
      }catch (Exception e){
          System.err.println("Problem reactivating participant");
          e.printStackTrace();
      }
   }







    /**
     * Loop that polls {@link Participants} for any incoming messages. On receiving a message it calls the corresponding
     * methods in {@link diet.server.ConversationController}.
     *
     */
    @Override
    public void run(){
        System.out.println("Starting conversationcontroller");
        while (isConversationIsActive()){
          try{
             if(Debug.debugBLOCKAGE){System.out.println("MCT4");System.out.flush();}
             Message m = (Message) ps.getNextMessage();
             
             
             
              if(Debug.debugBLOCKAGE){System.out.println("MCT66601");System.out.flush();}
             if(Debug.debugBLOCKAGE)System.err.println("AWAKE1");

             if (m!=null){
               if(Debug.debugBLOCKAGE){System.out.println("MCT66602");System.out.flush();}
               cHistoryUIM.updateControlPanel(m);
               if(Debug.debugBLOCKAGE){System.out.println("MCT66603");System.out.flush();}
               convIO.saveMessage(m);
               if(Debug.debugBLOCKAGE){System.out.println("MCT66607777m");System.out.flush();}
               if(Debug.trackCBYCDyadError){
                   Conversation.printWSln("MESSAGEIN", "Saving Mesage:"+ m.getClass().toString());
               }
                if(Debug.debugBLOCKAGE){System.out.println("MCT6660888888mm");System.out.flush();}
               Participant origin = ps.findParticipantWithEmail(m.getEmail());
                if(Debug.debugBLOCKAGE){System.out.println("MCT66609999999m");System.out.flush();}
               //System.out.println("UPDATINGCONTROLPANEL");

                if (m instanceof MessageChatTextFromClient) {
                    MessageChatTextFromClient msctfc = (MessageChatTextFromClient)m;
                    //MessageChatTextToClient msccttc = new MessageChatTextToClient(msctfc.getEmail(),msctfc.getUsername(),0,msctfc.getText(),1);
                    //cHistoryUIM.updateChatToolTextEntryFieldsUI(msctfc);
                    //System.out.println("Received message onset"+msctfc.getTypingOnset()+":"+msctfc.getEndOfTyping());
                   // System.out.println("MCT1");
                    if(Debug.debugBLOCKAGE){System.out.println("MCT66609999999m22222");System.out.flush();}
                    cC.processChatText(origin,msctfc);
                    if(!msctfc.hasBeenRelayedByServer){
                         String prefix = "NOTRELAYED";
                         this.setNewTurnBeingConstructedNotRelayingOldTurnAddingOldTurnToHistory(origin, msctfc, "NOTRELAYED");
                    }
                    
                    if(Debug.debugBLOCKAGE){System.out.println("MCT2");System.out.flush();}
                    //sendMessageToAllOtherParticipants(p, msccttc);
                }
                else if (m instanceof MessageKeypressed){
                    //System.out.println("Received message keypress");
                    MessageKeypressed mkp = (MessageKeypressed)m;

                    //this.cHistoryUIM.updateChatToolTextEntryFieldsUI(mkp);

                    String txtEntered = mkp.getContentsOfTextEntryWindow();
                    if(txtEntered!=null){
                        if(txtEntered.length()>0){
                           char txtEnteredLastChar = txtEntered.charAt(txtEntered.length()-1);
                          // if(Character.isWhitespace(txtEnteredLastChar))this.cHistoryUIM.parseTreeAndDisplayOfParticipant(origin,txtEntered);
                        }
                    }
                    System.out.println(origin.getParticipantID());
                    System.out.println(mkp.getKeypressed());
                    cC.processKeyPress(origin,mkp);

                    //MessageStatusLabelDisplayAndBlocked sld = new MessageStatusLabelDisplayAndBlocked("server", "server",1,m.getUsername()+"... is Typing",false,true);
                    //sendMessageToAllOtherParticipants(p,sld);
                }
                else if (m instanceof MessageWYSIWYGDocumentSyncFromClientInsert){
                    
                    MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp = (MessageWYSIWYGDocumentSyncFromClientInsert)m;
                    getDocChangesIncoming().addInsert(origin,mWYSIWYGkp.getTextToAppendToWindow(),mWYSIWYGkp.getOffset(),mWYSIWYGkp.getTimeOnServerOfReceipt().getTime());
                    cC.processWYSIWYGTextInserted(origin,mWYSIWYGkp);

                    char txtToInsert = mWYSIWYGkp.getTextToAppendToWindow().charAt(mWYSIWYGkp.getTextToAppendToWindow().length()-1);
                    if(Character.isWhitespace(txtToInsert)){
                    String txtEntered = getDocChangesIncoming().getTurnBeingConstructed(origin).getParsedText();
                    if(txtEntered!=null){
                        if(txtEntered.length()>0){
                           char txtEnteredLastChar = txtEntered.charAt(txtEntered.length()-1);
                           if(Character.isWhitespace(txtEnteredLastChar))this.cHistoryUIM.parseTreeAndDisplayOfParticipantDEPRECATED(origin,txtEntered);
                        }
                      }
                    }
                    this.cHistoryUIM.updateChatToolTextEntryFieldsUI(origin);
                    
                }
                else if (m instanceof MessageWYSIWYGDocumentSyncFromClientRemove){
                    MessageWYSIWYGDocumentSyncFromClientRemove mWYSIWYGkp = (MessageWYSIWYGDocumentSyncFromClientRemove)m;
                    getDocChangesIncoming().addRemove(origin,mWYSIWYGkp.getOffset(),mWYSIWYGkp.getLength(),mWYSIWYGkp.getTimeOnServerOfReceipt().getTime());
                    cC.processWYSIWYGTextRemoved(origin,mWYSIWYGkp);

                    String txtEntered = getDocChangesIncoming().getTurnBeingConstructed(origin).getParsedText();
                    if(txtEntered!=null){
                        if(txtEntered.length()>0){
                           char txtEnteredLastChar = txtEntered.charAt(txtEntered.length()-1);
                           if(Character.isWhitespace(txtEnteredLastChar))this.cHistoryUIM.parseTreeAndDisplayOfParticipantDEPRECATED(origin,txtEntered);
                        }
                    }
                    this.cHistoryUIM.updateChatToolTextEntryFieldsUI(origin);
                }

                else if (m instanceof MessageWYSIWYGTextSelectionFromClient){
                    MessageWYSIWYGTextSelectionFromClient mwysts = (MessageWYSIWYGTextSelectionFromClient) m;
                    cC.processWYSIWYGSelectionChanged(origin,mwysts);
                    //MessageWYSIWYGTextSelectionToClient mwstsend = new MessageWYSIWYGTextSelectionToClient(1, mwysts.getEmail(),
                    //        mwysts.getUsername(), mwysts.getCorrectedStartIndex(), mwysts.getCorrectedFinishIndex());
                    //sendMessageToAllOtherParticipants(p, mwstsend);
                }
                else if (m instanceof MessageWYSIWYGTypingUnhinderedRequest){
                   MessageWYSIWYGTypingUnhinderedRequest mwTUR = (MessageWYSIWYGTypingUnhinderedRequest)m;
                   cC.processWYSIWYGTypingUnhinderedRequest(origin, mwTUR);
                }

               else if (m instanceof MessageCBYCTypingUnhinderedRequest){
                   MessageCBYCTypingUnhinderedRequest mCTUR = (MessageCBYCTypingUnhinderedRequest)m;
                   cC.processCBYCTypingUnhinderedRequest(origin, mCTUR);
                   this.cHistoryUIM.updateChatToolTextEntryFieldsUI(origin, mCTUR);
                   if(Debug.showErrorsForMacIntegration)Conversation.printWSln("INC", "TYPINGUNHINDEREDREQUESTFROM from "+m.getUsername());
                   if(Debug.trackCBYCDyadError){
                     Conversation.printWSln("INC", "CALLEDCCC");
                   }
               }
               else if(m instanceof MessageCBYCDocChangeFromClient){
                   MessageCBYCDocChangeFromClient mCDC = (MessageCBYCDocChangeFromClient)m;
                   cC.processCBYCDocChange(origin,  mCDC);
                   this.cHistoryUIM.updateChatToolTextEntryFieldsUI(origin, mCDC);
                   if(Debug.showErrorsForMacIntegration)Conversation.printWSln("INC", "DOCCHANGEINCOMINGFROM " +m.getUsername());
                   if(Debug.trackCBYCDyadError){
                     Conversation.printWSln("MESSAGEIN", "CALLEDCCC2"+m.getUsername());
                   }
               }
               else if (m instanceof MessageCBYCChangeTurntakingStatusConfirm){

                   MessageCBYCChangeTurntakingStatusConfirm mConf = (MessageCBYCChangeTurntakingStatusConfirm)m;
                   if(Debug.showErrorsForMacIntegration)Conversation.printWSln("INC", "RECEIVEDSTATECHANGECONFIRM from" +m.getUsername()+" "+mConf.getNewStatus());
                   cC.processCBYCChangeTurnTakingStatusConfirm(origin, mConf);
                   this.cHistoryUIM.updateChatToolTextEntryFieldsUI(origin, mConf);
               }



                else if (m instanceof MessageWithinTurnDocumentSyncFromClientInsert){
                   MessageWithinTurnDocumentSyncFromClientInsert mws= (MessageWithinTurnDocumentSyncFromClientInsert)m;
                   cC.processWithinTurnTextInserted(origin, mws);
                }
                else if (m instanceof MessageWithinTurnDocumentSyncFromClientRemove){
                   MessageWithinTurnDocumentSyncFromClientRemove mws= (MessageWithinTurnDocumentSyncFromClientRemove)m;
                   cC.processWithinTurnTextRemoved(origin, mws);
                }
                else if (m instanceof MessageWithinTurnDocumentSyncFromClientReplace){
                    MessageWithinTurnDocumentSyncFromClientReplace mws= (MessageWithinTurnDocumentSyncFromClientReplace)m;
                    cC.processWithinTurnTextReplaced(origin, mws);
                }
               else if(m instanceof MessageWithinTurnTextSelectionFromClient){
                   MessageWithinTurnTextSelectionFromClient mws= (MessageWithinTurnTextSelectionFromClient)m;
                   cC.processWithinTurnTextSelectionChanged(origin, mws);
               }

                else if (m instanceof MessageErrorFromClient){
                    MessageErrorFromClient mefc = (MessageErrorFromClient)m;
                    this.printWln("Main", "ERROR IN CLIENT: "+m.getEmail()+" "+m.getUsername()+"\n"+mefc.getErrorMessage());
                    System.err.println("ERROR IN CLIENT: "+m.getEmail()+" "+m.getUsername()+"\n"+mefc.getErrorMessage());
                    Throwable t = mefc.getThrowable();
                    this.saveErrorLog("ERROR IN CLIENT: "+m.getEmail()+" "+m.getUsername()+"\n"+mefc.getErrorMessage());
                    

                }
                else if(m instanceof MessageClientEvent){
                    MessageClientEvent mce = (MessageClientEvent)m;
                    cC.processClientEvent(origin,mce);
                }
                
                
                else if (m instanceof MessagePopupResponseFromClient){
                    try{
                       MessagePopupResponseFromClient mpr = (MessagePopupResponseFromClient)m;
                       String[] options = mpr.getOptions();
                       String question = mpr.getQuestion();
                       String title = mpr.getTitle();
                       String optionsFLATTENED = "";
                       for(int l=0;l<options.length;l++){
                           optionsFLATTENED = optionsFLATTENED+options[l];
                       }
                       
                       
                        String participantID = "Not yet set";
                        String username = "Not yet set";
                        participantID = mpr.getEmail();
                        username = mpr.getUsername();//origin.getUsername();
                        
                        String popupID=mpr.getPopupID();
                      
                       //System.out.println(id+username+"---------------------------------------------------");
                       //System.exit(-4);
                       
                       String s4 = ""+mpr.getTimeOnServerOfReceipt().getTime();
                       String s5 = ""+mpr.getTimeOnServerOfReceipt();
                       String s7 = optionsFLATTENED;
                       
                       String timeOnClientOfShowing = "(TimeOnClientOfShowing:"+mpr.timeOnClientOfDisplay+")";
                       String timeOnClientOfChoice = "(TimeOnClientOfSelecting:"+mpr.timeOfChoice+")";
                       
                       String text = mpr.getTitle()+"_"+mpr.getQuestion()+"_"+mpr.getSelection()+"_"+mpr.getSelectedValue();
                       this.saveDataToFile("POPUPRECEIVED:"+popupID, participantID ,username, username,mpr.getTimeOnClientOfCreation().getTime() ,mpr.getTimeOnServerOfReceipt().getTime(), mpr.getTimeOnServerOfReceipt().getTime(),(title+"/"+question+"/"+optionsFLATTENED).replaceAll("\n","(NEWLINE)")+ timeOnClientOfShowing+timeOnClientOfChoice+"_"+mpr.getSelection()+"__"+mpr.getSelectedValue(), null);                        
                      
                       cC.processPopupResponse(origin, (MessagePopupResponseFromClient)m);
                        
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    
                }
                
                
                
                
                else if(m instanceof MessageTask){
                    if(tc!=null){
                        tc.processTaskMove((MessageTask)m,origin);
                        
                    }else{
                        //System.exit(-2345);
                    }
                }
                else{
                   //System.exit(-23456);
                }
         }

          if(Debug.debugBLOCKAGE){System.out.println("MCT44");System.out.flush();}
          }catch (Exception e){
               System.err.println(this.getConversationIdentifier()+ ": ERROR SOMEWHERE IN THE CONVERSATION CONTROLLER");
               printWln("Main","There is an ERROR in the Conversation Controller");
               printWln("Main","Check the ERRORLOG.TXT file in the directory containing");
               printWln("Main","the saved experimental data");
               printWln("Main",e.getMessage());
                e.printStackTrace();
               convIO.saveErrorLog(e);
              
          }
       if(Debug.debugBLOCKAGE){System.out.println("MCT45");System.out.flush();}
      }
         if(Debug.debugBLOCKAGE){System.out.println("MCT46");System.out.flush();}
          System.err.println("THREAD TERMINATED");
          System.err.flush();
          conversationThreadHasTerminated=true;
    }




    
    
    /**
     * Sends a turn from a spoofed origin to a single Participant. It ensures that the turn is sent to the correct window
     * on the chat client and also saves the message to file.
     *
     * @param apparentOrigin "spoof" origin of turn
     * @param recipient recipient of turn
     * @param text text to appear
     */
    public void sendArtificialTurnFromApparentOriginToRecipient(Participant apparentOrigin,Participant recipient, String text){
        sendArtificialTurnFromApparentOriginToRecipient(apparentOrigin,recipient, text, "");
    }

    /**
     * Sends a turn from a spoofed origin to a single Participant. It ensures that the turn is sent to the correct window
     * on the chat client and also saves the message to file.
     *
     * @param apparentOrigin "spoof" origin of turn
     * @param recipient recipient of turn
     * @param text text to appear
     * @param prefixCVSSpreadsheetOutput This is a prefix that is added to the "TURNTYPE" column in the spreadsheet where all the data from the experiment is saved
     * 
     */
    public void sendArtificialTurnFromApparentOriginToRecipient(Participant apparentOrigin,Participant recipient, String text, String prefixCVSSpreadsheetOutput){
        int windowNo = permissionsController.getWindownumber_DefaultsToZero(apparentOrigin, recipient);
        int styleno = cC.getStyleManager().getUniqueIntForRecipient(recipient, apparentOrigin);
        MessageChatTextToClient mctc = new MessageChatTextToClient("server",apparentOrigin.getUsername(),0,text,windowNo,true,styleno);
        //mctc.timeStamp();
        Vector recipientNames = new Vector();
        recipientNames.addElement(recipient.getUsername());
        Vector recipients = new Vector();
        recipients.addElement(recipient);
        ps.sendMessageToParticipant(recipient,mctc);
        cH.saveMessage(prefixCVSSpreadsheetOutput+"ARTIFICIALTURN",mctc.getTimeOnClientOfCreation().getTime(),mctc.getTimeOnServerOfReceipt().getTime(),mctc.getTimeOnServerOfReceipt().getTime(),"server",apparentOrigin.getUsername(),text,recipientNames,false,null,null,0, null);
    }

    
    
    
    

     public void sendArtificialTurnFromApparentOriginToRecipient_NoUsername(Participant apparentOrigin,Participant recipient, String text, String prefixCVSSpreadsheetOutput){
        int windowNo = permissionsController.getWindownumber_DefaultsToZero(apparentOrigin, recipient);
        int styleno = cC.getStyleManager().getUniqueIntForRecipient(recipient, apparentOrigin);
        MessageChatTextToClient mctc = new MessageChatTextToClient("server","",0,text,windowNo,true,styleno);
        //mctc.timeStamp();
        Vector recipientNames = new Vector();
        recipientNames.addElement(recipient.getUsername());
        Vector recipients = new Vector();
        recipients.addElement(recipient);
        ps.sendMessageToParticipant(recipient,mctc);
        cH.saveMessage(prefixCVSSpreadsheetOutput+"ARTIFICIALTURN",mctc.getTimeOnClientOfCreation().getTime(),mctc.getTimeOnServerOfReceipt().getTime(),mctc.getTimeOnServerOfReceipt().getTime(),"server",apparentOrigin.getUsername(),text,recipientNames,false,null,null,0,null);
    }


     public void sendDelayedTextToAllOtherParticipants(final Participant sender,final String text,final long millisecdelay){
        //MessageChatTextToClient msccttc = new MessageChatTextToClient(mct.getEmail(),mct.getUsername(),0,mct.getText(),1);
        DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(sender,new Date().getTime());
        Vector recipientsOfChatText = permissionsController.getOtherParticipantsSettingWindowsTo0(sender);
        final Vector pRecipients = (Vector)recipientsOfChatText.elementAt(0);
        final Vector pUsernames = (Vector)recipientsOfChatText.elementAt(2);
        //Vector pChatWindows = (Vector)recipientsOfChatText.elementAt(3);
        //ps.sendChatTextToParticipants(pRecipients,mct.getEmail(),mct.getUsername(),0,mct.getText(),pChatWindows,true);
        //cH.saveMessage(mct.getTypingOnset(),mct.getEndOfTyping(),mct.getUsername(),mct.getUsername(),mct.getText(),pUsernames,mct.hasBeenBlocked(),mct.getKeypresses(),ds.getAllInsertsAndRemoves(),0);
        //sendDelayedArtificialTurnFromApparentOriginToRecipients(sender, pRecipients, text,millisecdelay);
        //cH.saveMessage(new Date().getTime(),new Date().getTime(),"server",sender.getUsername(),mct.getText(),pRecipients,false,null,null,0);
        //cH.saveMessage(turnNotRelayed.getTypingOnset(),turnNotRelayed.getEndOfTyping(),p.getUsername(),p.getUsername(),ds.getParsedText(),new Vector(),false,null,ds.getAllInsertsAndRemoves(),0);
        Thread t = new Thread(){
            public void run(){
                try{
                    sleep(millisecdelay);
                    sendArtificialTurnFromApparentOriginToRecipients(sender, pRecipients, text);
                    //cH.saveMessage(new Date().getTime(),new Date().getTime(),"server",sender.getUsername(),text,pUsernames,false,null,null,0);

                }catch(Exception e){
                    printWln("Main","ERROR: Could not send the following delayed text: "+text);
                    printWln("Main","The error is: "+e.getMessage());
                }
            }
        };
        t.start();
     }
     
     
     
     public void sendDelayedTextToParticipant(final Participant sender,final Participant recipient, final String text,final long millisecdelay){
        //MessageChatTextToClient msccttc = new MessageChatTextToClient(mct.getEmail(),mct.getUsername(),0,mct.getText(),1);
        DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(sender,new Date().getTime());
       
         Thread t = new Thread(){
            public void run(){
                try{
                    sleep(millisecdelay);
                    sendArtificialTurnFromApparentOriginToRecipient(sender, recipient, text, "delayedtext: "+millisecdelay);
                    //cH.saveMessage(new Date().getTime(),new Date().getTime(),"server",sender.getUsername(),text,pUsernames,false,null,null,0);

                }catch(Exception e){
                    printWln("Main","ERROR: Could not send the following delayed text: "+text);
                    printWln("Main","The error is: "+e.getMessage());
                }
            }
        };
        t.start();
        
        
     }
     





    /**
     * Sends a turn from a spoofed origin to a multiple Participants. It ensures that the turn is sent to the correct window
     * on the chat client and also saves the message to file.
     *
     * @param apparentOrigin "spoof" origin of turn
     * @param recipients Vector containing all the recipients of the turn.
     * @param text text to appear
     */
    public void sendArtificialTurnFromApparentOriginToRecipients(Participant apparentOrigin, Vector recipients, String text){
        Vector windowNumbers  = new Vector();
        Vector recipientNames = new Vector();
        for(int i=0;i<recipients.size();i++){
            Participant recipient = (Participant)recipients.elementAt(i);
            recipientNames.addElement(recipient.getUsername());
            int windowNo = permissionsController.getWindownumber_DefaultsToZero(apparentOrigin,recipient);
            windowNumbers.addElement(windowNo);
            this.sendArtificialTurnFromApparentOriginToRecipient(apparentOrigin, recipient, text);
        }
         cH.saveMessage("ARTIFICIALTURN",new Date().getTime(),new Date().getTime(),new Date().getTime(),"server",apparentOrigin.getUsername(),text,recipientNames,false,null,null,0,null);
    }

    /**
     * Sends a turn to a single Participant. This method requires the programmer to specify which window the
     *
     * It ensures that the turn is sent to the correct window
     * on the chat client and also saves the message to file.
     *
     * @param windowNo
     * @param recipient Recipient of the turn.
     * @param text text to appear
     */
     public void sendArtificialTurnToRecipient(Participant recipient,String text, int windowNo){
          sendArtificialTurnToRecipient(recipient, text, windowNo, "");
     }

     public void sendArtificialTurnToRecipientWithEnforcedTextColour(Participant recipient,String text, int windowNo, int styleNo){
          sendArtificialTurnToRecipientWithEnforcedTextColour(recipient, text, windowNo, "",styleNo);
     }
     public void sendArtificialTurnToRecipientWithEnforcedTextColour(Participant recipient,String text, int windowNo, int styleNo, String cvsPREFIX){
          sendArtificialTurnToRecipientWithEnforcedTextColour(recipient, text, windowNo, cvsPREFIX,styleNo);
     }
     
     //
     public void sendArtificialTurnToAllParticipants(String text, int windowNo){   ////This is what it calls
          Vector v = ps.getAllParticipants();
          for(int i=0;i<v.size();i++){
                Participant p2 = (Participant)v.elementAt(i);
                this.sendArtificialTurnToRecipient(p2, text, windowNo, "");
          }     
     }
     public void sendArtificialTurnToAllParticipants(String text, int windowNo, String apparentOrigin){
          Vector v = ps.getAllParticipants();
          for(int i=0;i<v.size();i++){
                Participant p2 = (Participant)v.elementAt(i);
                this.sendArtificialTurnToRecipient(p2, text, windowNo, "", apparentOrigin);
          }     
     }
    
     public void sendArtificialTurnToAllParticipants(String text, int windowNo, int textColor, String apparentOrigin){
          Vector v = ps.getAllParticipants();
          for(int i=0;i<v.size();i++){
                Participant p2 = (Participant)v.elementAt(i);
                this.sendArtificialTurnToRecipient(p2, text, windowNo, textColor, apparentOrigin);
          }     
     }

     /**
     * Sends a turn to a single Participant. 
     *
     * It ensures that the turn is sent to the correct window
     * on the chat client and also saves the message to file.
     *
     * @param windowNo
     * @param recipient Recipient of the turn.
     * @param text text to appear
     * @param prefixFORCSVSpreadsheetIDENTIFIER this is a string you would like to save in the spreadsheet of the experiment. It is saved as a prefix to the TurnType column.
     */    
    public void sendArtificialTurnToRecipient(Participant recipient,String text, int windowNo, String prefixFORCSVSpreadsheetIDENTIFIER ){
        int styleno = cC.getStyleManager().getUniqueIntForRecipientNoSender(recipient);
        System.err.println("STYLENO IS"+styleno);
       // styleno=2;
       // System.exit(-234234);
        MessageChatTextToClient mctc = new MessageChatTextToClient("server","server",0,text,windowNo,false,styleno);
         //mctc.timeStamp();
         Vector recipientNames = new Vector();
         recipientNames.addElement(recipient.getParticipantID()+recipient.getUsername());
         Vector recipients = new Vector();
         recipients.addElement(recipient);
         ps.sendMessageToParticipant(recipient,mctc);
         cH.saveMessage(prefixFORCSVSpreadsheetIDENTIFIER+"ARTIFICIALTURN",mctc.getTimeOnClientOfCreation().getTime(),mctc.getTimeOnServerOfReceipt().getTime(),mctc.getTimeOnServerOfReceipt().getTime(),"server","server",text,recipientNames,false,null,null,0,null);
    }
    
    
    public void sendArtificialTurnToRecipientWithEnforcedTextColour(Participant recipient,String text, int windowNo, String prefixFORCSVSpreadsheetIDENTIFIER, int styleNo ){
         MessageChatTextToClient mctc = new MessageChatTextToClient("server","server",0,text,windowNo,false,styleNo);
         //mctc.timeStamp();
         Vector recipientNames = new Vector();
         recipientNames.addElement(recipient.getParticipantID()+recipient.getUsername());
         Vector recipients = new Vector();
         recipients.addElement(recipient);
         ps.sendMessageToParticipant(recipient,mctc);
         cH.saveMessage(prefixFORCSVSpreadsheetIDENTIFIER+"ARTIFICIALTURN",mctc.getTimeOnClientOfCreation().getTime(),mctc.getTimeOnServerOfReceipt().getTime(),mctc.getTimeOnServerOfReceipt().getTime(),"server","server",text,recipientNames,false,null,null,0,null);
    }
    
 
    public void sendArtificialTurnFromApparentOriginToRecipientWithEnforcedTextColour(String apparentOriginUsername, Participant recipient,String text, int windowNo, String prefixFORCSVSpreadsheetIDENTIFIER, int styleNo ){
         MessageChatTextToClient mctc = new MessageChatTextToClient("server",apparentOriginUsername,0,text,windowNo,true,styleNo);
         //mctc.timeStamp();
         Vector recipientNames = new Vector();
         recipientNames.addElement(recipient.getParticipantID()+recipient.getUsername());
         Vector recipients = new Vector();
         recipients.addElement(recipient);
         ps.sendMessageToParticipant(recipient,mctc);
         cH.saveMessage(prefixFORCSVSpreadsheetIDENTIFIER+"ARTIFICIALTURN",mctc.getTimeOnClientOfCreation().getTime(),mctc.getTimeOnServerOfReceipt().getTime(),mctc.getTimeOnServerOfReceipt().getTime(),"server",apparentOriginUsername,text,recipientNames,false,null,null,0,null);
   
    }
    
     public void sendArtificialMazeGameTurnFromApparentOriginToRecipientWithEnforcedTextColour(String apparentOriginUsername, Participant recipient,String text, int windowNo, String prefixFORCSVSpreadsheetIDENTIFIER, int styleNo ){
         MessageChatTextToClient mctc = new MessageChatTextToClient("server",apparentOriginUsername,0,text,windowNo,true,styleNo);
         //mctc.timeStamp();
         Vector recipientNames = new Vector();
         recipientNames.addElement(recipient.getParticipantID()+recipient.getUsername());
         Vector recipients = new Vector();
         recipients.addElement(recipient);
         ps.sendMessageToParticipant(recipient,mctc);
         cH.saveMessage(prefixFORCSVSpreadsheetIDENTIFIER+"ARTIFICIALTURN",mctc.getTimeOnClientOfCreation().getTime(),mctc.getTimeOnServerOfReceipt().getTime(),mctc.getTimeOnServerOfReceipt().getTime(),"server",apparentOriginUsername,text,recipientNames,false,null,null,0,null);
   
    }
    
    
    
    
    
     /**
     * Sends a turn to a single Participant. 
     *
     * It ensures that the turn is sent to the correct window
     * on the chat client and also saves the message to file.
     *
     * @param windowNo
     * @param recipient Recipient of the turn.
     * @param text text to appear
     * @param styleNo A unique number which specifies the colour that the chat text will appear as in the participant's window. If you want to have a colur that is different from all the other colours used to identify participants, use a number at least 1 larger than the number of participants....so if your experiment has 3 participants and you would like to make text appear in a new colour, use 4 as the styleNo
     * @param prefixFORCSVSpreadsheetIDENTIFIER this is a string you would like to save in the spreadsheet of the experiment. It is saved as a prefix to the TurnType column.
     */    
    public void sendArtificialTurnToRecipient(Participant recipient,String text, int windowNo, int styleNo, String prefixFORCSVSpreadsheetIDENTIFIER ){
       
        MessageChatTextToClient mctc = new MessageChatTextToClient("server","server",0,text,windowNo,false,styleNo);
         //mctc.timeStamp();
         Vector recipientNames = new Vector();
         recipientNames.addElement(recipient.getParticipantID()+recipient.getUsername());
         Vector recipients = new Vector();
         recipients.addElement(recipient);
         ps.sendMessageToParticipant(recipient,mctc);
         cH.saveMessage(prefixFORCSVSpreadsheetIDENTIFIER+"ARTIFICIALTURN",mctc.getTimeOnClientOfCreation().getTime(),mctc.getTimeOnServerOfReceipt().getTime(),mctc.getTimeOnServerOfReceipt().getTime(),"server","server",text,recipientNames,false,null,null,0,null);
    }
    
    

    public void sendArtificialTurnToRecipient(Participant recipient, String text, int windowNo, String prefixFORCSVSpreadsheetIDENTIFIER,String mctcFrom ){
        int styleno = cC.getStyleManager().getUniqueIntForRecipientNoSender(recipient);
        MessageChatTextToClient mctc = new MessageChatTextToClient(mctcFrom,mctcFrom,0,text,windowNo,false,styleno);
         //mctc.timeStamp();
         Vector recipientNames = new Vector();
         recipientNames.addElement(recipient.getUsername());
         Vector recipients = new Vector();
         recipients.addElement(recipient);
         ps.sendMessageToParticipant(recipient,mctc);
         cH.saveMessage(prefixFORCSVSpreadsheetIDENTIFIER+"ARTIFICIALTURN",mctc.getTimeOnClientOfCreation().getTime(),mctc.getTimeOnServerOfReceipt().getTime(),mctc.getTimeOnServerOfReceipt().getTime(),mctcFrom,mctcFrom,text,recipientNames,false,null,null,0,null);
    }
    
    
    
    
    
    
     public void showPopupOnClientQueryInfo(String popupID,Participant recipient, String question, String[] options, String title, int selection){
         showPopupOnClientQueryInfo(popupID,recipient, question,  options, title, selection,"");
     }
    
    public void showPopupOnClientQueryInfo(String popupID,Participant recipient, String question, String[] options, String title, int selection, String prefixFORCSVSpreadsheet){
        MessagePopup mp = new MessagePopup (popupID,"server", "server", question, options, title, selection);
        ps.sendMessageToParticipant(recipient,mp);
        
        String questionCleaned = question.replaceAll("\n", "(NEWLINE)");
        String titleCleaned = title.replaceAll("\n", "(NEWLINE)");
        this.saveDataToFile(prefixFORCSVSpreadsheet+"POPUPSENT", recipient.getParticipantID(), recipient.getUsername(), recipient.getUsername(),new Date().getTime() ,new Date().getTime(), new Date().getTime(),titleCleaned+"-"+questionCleaned, null);
        //this.saveDataToFile(title, title, question, selection, selection, title, null);
    }
    
    
    
    
    public void saveDataToConversationHistory(Participant recipient, String turntype_WITHPREFIX, String data){
        this.saveDataToFile(turntype_WITHPREFIX, "Server", "server", recipient.getParticipantID()+"."+recipient.getUsername(),new Date().getTime() ,new Date().getTime(), new Date().getTime(),data, null);
 
        //this.saveDataToFile(title, title, question, selection, selection, title, null);
    }
    
    public void saveDataToConversationHistory(Participant sender,String recipientName , long timeCREATEDONCLIENT, String turntype_WITHPREFIX, String data){
        //String recipient
        this.saveDataToFile(turntype_WITHPREFIX, sender.getParticipantID(), sender.getUsername(), recipientName, timeCREATEDONCLIENT,new Date().getTime(), new Date().getTime(),data, null);
 
        //this.saveDataToFile(title, title, question, selection, selection, title, null);
    }
    
    
    public void saveDataToConversationHistory(String turntypePREFIX,String data){
        //MessageChatTextToClient mctc = new MessageChatTextToClient("server","server",0,data,0,false,-444);
        //mctc.timeStamp();
        long timeOfCreationOnServer = new Date().getTime();
        
        //mctc.timeStamp();
         //String sender = "server";
         Vector recipients = new Vector();
         //if(cH==null)System.exit(-4);
         cH.saveMessage(turntypePREFIX+"DATA",timeOfCreationOnServer,timeOfCreationOnServer,timeOfCreationOnServer,"server","server",data,recipients,false,null,null,0,null);
    }
    
    
    public void saveDataToConversationHistoryDEPRECATED(String data){
        //MessageChatTextToClient mctc = new MessageChatTextToClient("server","server",0,data,0,false,-444);
        //mctc.timeStamp();
          long timeOfCreationOnServer = new Date().getTime();
        //mctc.timeStamp();
         String sender = "server";
         Vector recipients = new Vector();
         //if(cH==null)System.exit(-4);
          cH.saveMessage("DATA",timeOfCreationOnServer,timeOfCreationOnServer,timeOfCreationOnServer,"server","server",data,recipients,false,null,null,0,null);
    }

    
    public void saveDataToFile(String datatype, String senderID, String senderUsername, String recipient,long timeOfCreationOnClient ,long onset, long enter, String text, Vector additionalData){
        try{
            
           cH.saveDataToFile(datatype,timeOfCreationOnClient ,onset, enter,senderID, senderUsername, recipient,text.replaceAll("\n", "(NEWLINE)"),new Vector(),false,null,null,0,additionalData,null);
             }catch (Exception e){
            Conversation.printWSln("Main", "Error saving data");
        }
    }
    
    public void saveDataToFile(String datatype, String senderID, String senderUsername, long timeOfCreationOnClient,long onset, long enter, String text, Vector additionalData){
        try{
            
           cH.saveDataToFile(datatype, timeOfCreationOnClient,onset, enter,senderID, senderUsername,senderUsername,text.replaceAll("\n", "(NEWLINE)"),new Vector(),false,null,null,0,additionalData,null);
             }catch (Exception e){
            Conversation.printWSln("Main", "Error saving data");
        }
    }

    
    public void saveClientKeypressToFile(Participant sender, MessageKeypressed mkp){
        this.saveClientKeypressToFile(sender, mkp, "");
    }

    
    public void saveClientKeypressToFile(Participant sender, MessageKeypressed mkp,String prefixFORCVSSPreadsheetIDENTIFIER ){
        this.saveDataToFile(prefixFORCVSSPreadsheetIDENTIFIER+"KEYPRESSS", sender.getParticipantID(),sender.getUsername(), mkp.getTimeOnClientOfCreation().getTime(),  mkp.getTimeOnServerOfReceipt().getTime(), mkp.getTimeOnServerOfReceipt().getTime(), mkp.getContentsOfTextEntryWindow(),null);
    }

    //PriorTurnByOther_TimestampOnClientOfReceipt	PriorTurnByOther_ApparentUsername	PriorTurnByOther_Text

    public void saveClientDocumentchangeToFile(Participant sender,long timeOfCreationOnClient ,long timestampOnServer, String contentsOfWindow, String priorTurnByOtherClientTimestamp, String priorTurnByOtherApparentUsername, String priorTurnByOtherText){
       if (contentsOfWindow==null)contentsOfWindow="";
       if (priorTurnByOtherClientTimestamp ==null) priorTurnByOtherClientTimestamp="";
       if (priorTurnByOtherApparentUsername==null) priorTurnByOtherApparentUsername ="";
       if (priorTurnByOtherText==null) priorTurnByOtherText="";
       
     // need to make it so that it can save DOCINSERTS, DOCREMOVES,and separate them...and saves them as indices..DOCINSERTS.put a "try" on everything!
      //...need to fix the Tylen experiment as well so that it blocks the receiving thread as well!
        
    }    
    public void saveClientDocumentchangeToFile(Participant sender,long timeOfCreationOnClient ,long timestamp, String contentsOfWindow){
        this.saveDataToFile("KEYPRESSDOCCHANGE", sender.getParticipantID(),sender.getUsername(),timeOfCreationOnClient ,timestamp, timestamp, contentsOfWindow,null);
    }

    /**
     *
     * Checks the last time of typing of all participants. If most recent keypress exceeds the threshold, this method
     * sends a message to all permitted clients that participant is no longer typing.
     *
     * @param timeout time threshold of no typing activity. If threshold is exceeded,
     */
    public void resetFromIsTypingtoNormalChatAllAllowedParticipants(long timeout){
       
     //  System.err.println("RESETTING TYPING"); 
       Vector v = ps.getAllParticipants();
       Vector participantsWhoAreReceivingIsTyping = new Vector();
       for(int i=0;i<v.size();i++){
            Participant p = (Participant)v.elementAt(i);
            if(p.isTyping(timeout)){
               Vector recipientsOfChatText = permissionsController.getOtherParticipantsSettingWindowsTo0(p);
               Vector pRecipients = (Vector)recipientsOfChatText.elementAt(0);
               for(int j=0;j<pRecipients.size();j++){
                   Participant pRecp = (Participant)pRecipients.elementAt(j);
                   if (!participantsWhoAreReceivingIsTyping.contains(pRecp)){
                       participantsWhoAreReceivingIsTyping.addElement(pRecp);
                   }
               }
            }
        }

        Vector participantsToSendStatusOKMessageTo = new Vector();
        Vector participantsToSendStatusOKMessageToChatWindows = new Vector();

        for(int i=0;i<v.size();i++){
            Participant p = (Participant)v.elementAt(i);
            if(!p.isTyping(timeout)){
               Vector recipientsOfChatText = permissionsController.getOtherParticipantsSettingWindowsTo0(p);
               Vector pRecipients = (Vector)recipientsOfChatText.elementAt(0);
               Vector pUsernames = (Vector)recipientsOfChatText.elementAt(2);
               Vector pChatWindows = (Vector)recipientsOfChatText.elementAt(3);

               for(int j=0;j<pRecipients.size();j++){
                   Participant candidate = (Participant)pRecipients.elementAt(j);
                   Integer candidateChatWindow = 0;//(Integer)pChatWindows.elementAt(j);
                   if(!participantsWhoAreReceivingIsTyping.contains(candidate)){
                       participantsToSendStatusOKMessageTo.addElement(candidate);
                       participantsToSendStatusOKMessageToChatWindows.addElement(candidateChatWindow);
                   }
               }

               ps.sendLabelDisplayToParticipants(participantsToSendStatusOKMessageTo," ",participantsToSendStatusOKMessageToChatWindows,false);
            }
        }

    }


    public void sendArtificialTurnToAllOtherParticipants(Participant sender, String text){
        Vector recipientsOfChatText = permissionsController.getOtherParticipantsSettingWindowsTo0(sender);
        Vector pRecipients = (Vector)recipientsOfChatText.elementAt(0);
        //Vector pUsernames = (Vector)recipientsOfChatText.elementAt(2);
        //Vector pChatWindows = (Vector)recipientsOfChatText.elementAt(3);
        this.sendArtificialTurnFromApparentOriginToRecipients(sender, pRecipients, text);
        //ps.sendChatTextToParticipants(pRecipients,sender.getParticipantID(),sender.getUsername(),0,text,pChatWindows,true);
        //cH.saveMessage(new Date().getTime(),new Date().getTime(),"server",sender.getUsername(),text,pUsernames,false,null,null,0);

    }



    public void relayTurnToParticipant(Participant sender, Participant recipient, MessageChatTextFromClient mct){
         mct.setChatTextHasBeenRelayedByServer();
        DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(sender,new Date().getTime());
        int windowNo =0;
        int styleno = cC.getStyleManager().getUniqueIntForRecipient(recipient, sender);
        MessageChatTextToClient mctc = new MessageChatTextToClient(sender.getParticipantID(),sender.getUsername(),0,mct.getText(),windowNo,true,styleno);
        //mctc.timeStamp();
        Vector recipientNames = new Vector();
        recipientNames.addElement(recipient.getUsername());
        Vector recipients = new Vector();
        recipients.addElement(recipient);
        ps.sendMessageToParticipant(recipient,mctc);
        cH.saveMessage("Default",mct.getTimeOnClientOfCreation().getTime(),mct.getServerTypingOnset(),mct.getServerTypingENTER(),sender.getUsername(),sender.getUsername(),mct.getText(),recipientNames,false,mct.getKeypresses(),ds.getAllInsertsAndRemoves(),0,mct.getMostRecentPriorChatText());
   }


     public void relayTurnToParticipant(Participant sender, Participant recipient, MessageChatTextFromClient mct, String prefixForSpreadSheet){
         mct.setChatTextHasBeenRelayedByServer();
        DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(sender,new Date().getTime());
        int windowNo =0;
        int styleno = cC.getStyleManager().getUniqueIntForRecipient(recipient, sender);
        MessageChatTextToClient mctc = new MessageChatTextToClient(sender.getParticipantID(),sender.getUsername(),0,mct.getText(),windowNo,true,styleno);
        //mctc.timeStamp();
        Vector recipientNames = new Vector();
        recipientNames.addElement(recipient.getUsername());
        Vector recipients = new Vector();
        recipients.addElement(recipient);
        ps.sendMessageToParticipant(recipient,mctc);
        cH.saveMessage(prefixForSpreadSheet+"Default",mct.getTimeOnClientOfCreation().getTime(),mct.getServerTypingOnset(),mct.getServerTypingENTER(),sender.getUsername(),sender.getUsername(),mct.getText(),recipientNames,false,mct.getKeypresses(),ds.getAllInsertsAndRemoves(),0,mct.getMostRecentPriorChatText());
   }
    
     
      public void relayTurnToParticipantWithEnforcedColour(Participant sender,Participant recipient, MessageChatTextFromClient mct, int styleNo, String prefixForSpreadSheet){
        //MessageChatTextToClient msccttc = new MessageChatTextToClient(mct.getEmail(),mct.getUsername(),0,mct.getText(),1);
        mct.setChatTextHasBeenRelayedByServer();
        DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(sender,new Date().getTime());
        int window =0;
        Vector pUsernames = new Vector();
        pUsernames.addElement(recipient.getUsername());       
        int windowNo =0;
        //int styleno = cC.getStyleManager().getUniqueIntForRecipient(recipient, sender);
        MessageChatTextToClient mctc = new MessageChatTextToClient(sender.getParticipantID(),sender.getUsername(),0,mct.getText(),windowNo,true,styleNo);
        ps.sendMessageToParticipant(recipient, mctc);
         cH.saveMessage(prefixForSpreadSheet+"Default",mct.getTimeOnClientOfCreation().getTime(),mct.getServerTypingOnset(),mct.getServerTypingENTER(),sender.getUsername(),sender.getUsername(),mct.getText(),pUsernames,false,mct.getKeypresses(),ds.getAllInsertsAndRemoves(),0,mct.getMostRecentPriorChatText());
 }
    
    
    /**
     *
     * Note that this is effectively duplicating the sendArtificialTurn methods. The reason for using this method is that if
     * the whole experiment is run using fake IDs, then this method produces nicer output in the spreadsheet.
     * 
     *  @ param  prefixForSpreadsheet
     * 
     */
     public void relayTurnToParticipantSpoofingOriginOfTurn(Participant actualSender,Participant spoofedSender, Participant recipient, MessageChatTextFromClient mct, String prefixForSpreadsheet){
         mct.setChatTextHasBeenRelayedByServer();
        DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(actualSender,new Date().getTime());
        int windowNo =0;
        int styleno = cC.getStyleManager().getUniqueIntForRecipient(recipient, spoofedSender);
        MessageChatTextToClient mctc = new MessageChatTextToClient(spoofedSender.getParticipantID(),spoofedSender.getUsername(),0,mct.getText(),windowNo,true,styleno);
        //mctc.timeStamp();
        Vector recipientNames = new Vector();
        recipientNames.addElement(recipient.getUsername());
        Vector recipients = new Vector();
        recipients.addElement(recipient);
        ps.sendMessageToParticipant(recipient,mctc);
        cH.saveMessage(prefixForSpreadsheet+"SpoofedRelayedTurn",mct.getTimeOnClientOfCreation().getTime(),mct.getServerTypingOnset(),mct.getServerTypingENTER(),actualSender.getUsername(),spoofedSender.getUsername(),mct.getText(),recipientNames,false,mct.getKeypresses(),ds.getAllInsertsAndRemoves(),0, mct.getMostRecentPriorChatText());
       
     }
    
    
    
    
    
    /**
     * Relays turn from clients to all permitted participants. This should be the default method invoked to relay turns that are
     * not modified / blocked. It ensures that the message is stored and displayed properly in the UI.
     * The prefixFORCSVSpreadsheetIDENTIFIER is so that you can add intervention-specific info to the spreadsheet output..
     * 
     *
     * @param sender
     * @param mct
     */
    public void relayTurnToAllOtherParticipants(Participant sender,MessageChatTextFromClient mct){
         mct.setChatTextHasBeenRelayedByServer(); 
        relayTurnToAllOtherParticipants(sender,mct,"");
    }
    
    
    

    /**
     * Relays turn from clients to all permitted participants. This should be the default method invoked to relay turns that are
     * not modified / blocked. It ensures that the message is stored and displayed properly in the UI.
     * The prefixFORCSVSpreadsheetIDENTIFIER is so that you can add intervention-specific info to the spreadsheet output..
     * 
     *
     * @param sender
     * @param mct
     * @param prefixFORCSVSpreadsheetIDENTIFIER
     */
    public void relayTurnToAllOtherParticipants(Participant sender,MessageChatTextFromClient mct, String prefixFORCSVSpreadsheetIDENTIFIER){
        mct.setChatTextHasBeenRelayedByServer();
        

        //MessageChatTextToClient msccttc = new MessageChatTextToClient(mct.getEmail(),mct.getUsername(),0,mct.getText(),1);
        DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(sender,new Date().getTime());
        Vector recipientsOfChatText = permissionsController.getOtherParticipantsSettingWindowsTo0(sender);
        Vector pRecipients = (Vector)recipientsOfChatText.elementAt(0);
        Vector pUsernames = (Vector)recipientsOfChatText.elementAt(2);
        Vector pChatWindows = (Vector)recipientsOfChatText.elementAt(3);
        for(int i=0;i<pRecipients.size();i++){
            Participant recipient = (Participant)pRecipients.elementAt(i);
            int windowNo = (Integer)pChatWindows.elementAt(i);
            int styleno = cC.getStyleManager().getUniqueIntForRecipient(recipient, sender);
            MessageChatTextToClient mctc = new MessageChatTextToClient(sender.getParticipantID(),sender.getUsername(),0,mct.getText(),windowNo,true,styleno);
            ps.sendMessageToParticipant(recipient, mctc);
        }
        cH.saveMessage(prefixFORCSVSpreadsheetIDENTIFIER+"Default",mct.getTimeOnClientOfCreation().getTime(),mct.getServerTypingOnset(),mct.getServerTypingENTER(),mct.getUsername(),mct.getUsername(),mct.getText(),pUsernames,mct.hasBeenBlocked(),mct.getKeypresses(),ds.getAllInsertsAndRemoves(),0,mct.getMostRecentPriorChatText());
       }

    /**
     * Relays turn from clients to all permitted participants. This should be the default method invoked to relay turns that are
     * not modified / blocked. It ensures that the message is stored and displayed properly in the UI. Be very careful using this method
     * as it "breaks" the metaphor used for representing turns and experimental manipulation of turns. The basic metaphor is that normal
     * turns that appear "as is", i.e. from the origin, displayed with the proper name etc. are treated as basic. Any turn that is changed
     * in ANY way is treated as not being the "same" turn...leading to the original turn being saved as not transmitted to anyone, and a second
     * turn that originates from the server is recorded. This method turns this metaphor on its head, and should only be used if
     * a participant's name is consistently changed throughout the experiment.
     *
     *
     * @param sender
     * @param mct
     */
    public void relayTurnToAllOtherParticipantsWithAlteredOriginName(Participant sender,String alteredName,MessageChatTextFromClient mct){
        //MessageChatTextToClient msccttc = new MessageChatTextToClient(mct.getEmail(),mct.getUsername(),0,mct.getText(),1);
         mct.setChatTextHasBeenRelayedByServer();
        DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(sender,new Date().getTime());
        Vector recipientsOfChatText = permissionsController.getOtherParticipantsSettingWindowsTo0(sender);
        Vector pRecipients = (Vector)recipientsOfChatText.elementAt(0);
        Vector pUsernames = (Vector)recipientsOfChatText.elementAt(2);
        Vector pChatWindows = (Vector)recipientsOfChatText.elementAt(3);

        for(int i=0;i<pRecipients.size();i++){
            Participant recipient = (Participant)pRecipients.elementAt(i);
            int windowNo = (Integer)pChatWindows.elementAt(i);
            int styleno = cC.getStyleManager().getUniqueIntForRecipient(recipient, sender);
            MessageChatTextToClient mctc = new MessageChatTextToClient(sender.getParticipantID(),alteredName,0,mct.getText(),windowNo,true,styleno);
            ps.sendMessageToParticipant(recipient, mctc);
        }
        cH.saveMessage("AlteredOrigin",mct.getTimeOnClientOfCreation().getTime(),mct.getServerTypingOnset(),mct.getServerTypingENTER(),sender.getUsername(),alteredName,mct.getText(),pUsernames,mct.hasBeenBlocked(),mct.getKeypresses(),ds.getAllInsertsAndRemoves(),0,mct.getMostRecentPriorChatText());

    }
    
    
    /**
     * Relays turn from a clients to another participant, but spoofing the name of the sender.
     *
     * @param sender
     * @param mct
     */
    public void relayTurnToParticipantWithAlteredUserName(Participant sender,String alteredSenderUsername,MessageChatTextFromClient mct, Participant recipient){
        mct.setChatTextHasBeenRelayedByServer();
        //MessageChatTextToClient msccttc = new MessageChatTextToClient(mct.getEmail(),mct.getUsername(),0,mct.getText(),1);
        DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(sender,new Date().getTime());
       // Vector recipientsOfChatText = new Vector();
        Vector pUsernames = new Vector();
        pUsernames.addElement(recipient.getUsername());
 
        int windowNo = 0;
        int styleno = cC.getStyleManager().getUniqueIntForRecipient(recipient, sender);
        MessageChatTextToClient mctc = new MessageChatTextToClient(sender.getParticipantID(),alteredSenderUsername,0,mct.getText(),windowNo,true,styleno);
        ps.sendMessageToParticipant(recipient, mctc);
        cH.saveMessage("AlteredOrigin",mct.getTimeOnClientOfCreation().getTime(),mct.getServerTypingOnset(),mct.getServerTypingENTER(),sender.getUsername(),alteredSenderUsername,mct.getText(),pUsernames,mct.hasBeenBlocked(),mct.getKeypresses(),ds.getAllInsertsAndRemoves(),0,mct.getMostRecentPriorChatText());

    }
    
    
     /**
     * Relays turn from a clients to another participant, but spoofing the name of the sender.
     *
     * @param sender
     * @param mct
     */
    public void relayTurnToParticipantWithAlteredUserNameAndEnforcedTextColour(Participant sender,String alteredSenderUsername,MessageChatTextFromClient mct, Participant recipient, int colourStyle){
        mct.setChatTextHasBeenRelayedByServer();
        //MessageChatTextToClient msccttc = new MessageChatTextToClient(mct.getEmail(),mct.getUsername(),0,mct.getText(),1);
        DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(sender,new Date().getTime());
       // Vector recipientsOfChatText = new Vector();
        Vector pUsernames = new Vector();
        pUsernames.addElement(recipient.getUsername());
 
        int windowNo = 0;
        //int styleno = cC.getStyleManager().getUniqueIntForRecipient(recipient, sender);
        MessageChatTextToClient mctc = new MessageChatTextToClient(sender.getParticipantID(),alteredSenderUsername,0,mct.getText(),windowNo,true,colourStyle);
        ps.sendMessageToParticipant(recipient, mctc);
        cH.saveMessage("AlteredOrigin",mct.getTimeOnClientOfCreation().getTime(),mct.getServerTypingOnset(),mct.getServerTypingENTER(),sender.getUsername(),alteredSenderUsername,mct.getText(),pUsernames,mct.hasBeenBlocked(),mct.getKeypresses(),ds.getAllInsertsAndRemoves(),0,mct.getMostRecentPriorChatText());

    }
    
     /**
     * Relays turn from a clients to another participant, but spoofing the name of the sender.
     *
     * @param sender
     * @param mct
     */
    public void relayTurnToParticipantWithAlteredUserNameAndEnforcedTextColour(Participant sender,String alteredSenderUsername,MessageChatTextFromClient mct, Participant recipient, int colourStyle, String prefixFORCSV){
        mct.setChatTextHasBeenRelayedByServer();
        //MessageChatTextToClient msccttc = new MessageChatTextToClient(mct.getEmail(),mct.getUsername(),0,mct.getText(),1);
        DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(sender,new Date().getTime());
       // Vector recipientsOfChatText = new Vector();
        Vector pUsernames = new Vector();
        pUsernames.addElement(recipient.getUsername());
 
        int windowNo = 0;
        //int styleno = cC.getStyleManager().getUniqueIntForRecipient(recipient, sender);
        MessageChatTextToClient mctc = new MessageChatTextToClient(sender.getParticipantID(),alteredSenderUsername,0,mct.getText(),windowNo,true,colourStyle);
        ps.sendMessageToParticipant(recipient, mctc);
        cH.saveMessage(prefixFORCSV+"AlteredOrigin",mct.getTimeOnClientOfCreation().getTime(),mct.getServerTypingOnset(),mct.getServerTypingENTER(),sender.getUsername(),alteredSenderUsername,mct.getText(),pUsernames,mct.hasBeenBlocked(),mct.getKeypresses(),ds.getAllInsertsAndRemoves(),0,mct.getMostRecentPriorChatText());

    }

    
    
   



    
    
    


    /**
     * Informs all other participants that are permitted to receive turns from the participant that participant is typing..
     * @param sender
     */
    public void informIsTypingToAllowedParticipants(Participant sender){//,Message m){
        Vector recipientsOfChatText = permissionsController.getOtherParticipantsSettingWindowsTo0(sender);
        Vector pRecipients = (Vector)recipientsOfChatText.elementAt(0);
        Vector pUsernames = (Vector)recipientsOfChatText.elementAt(2);
        Vector pChatWindows = (Vector)recipientsOfChatText.elementAt(3);
        ps.sendLabelDisplayToParticipants(pRecipients,sender.getUsername()+"...is typing",pChatWindows,false);
      }

    /**
     * Informs all other participants that are permitted to receive turns from the participant that participant is typing,
     * using a altered participant name (supplied)
     * @param sender
     * @param alteredName
     */
    public void informIsTypingToAllowedParticipantswithAlteredOriginName(Participant sender, String alteredName){//,Message m){
        Vector recipientsOfChatText = permissionsController.getOtherParticipantsSettingWindowsTo0(sender);
        Vector pRecipients = (Vector)recipientsOfChatText.elementAt(0);
        Vector pUsernames = (Vector)recipientsOfChatText.elementAt(2);
        Vector pChatWindows = (Vector)recipientsOfChatText.elementAt(3);
        ps.sendLabelDisplayToParticipants(pRecipients,alteredName+"...is typing",pChatWindows,false);
      }

    /**
     * Informs a participant that another participant IS typing
     *
     * @param sender Participant that appears to be typing
     * @param recipient Participant that receives the message that the other is typing
     */
    public void informParticipantBthatParticipantAIsTyping(Participant sender, Participant recipient){
        Vector recipients = new Vector();
        recipients.addElement(recipient);
      
        Vector chatWindows = new Vector();
        chatWindows.addElement(0);
        //chatWindows.addElement(0);
        ps.sendLabelDisplayToParticipants(recipients,sender.getUsername()+"...is typing", chatWindows,false);
    }
    
    
    
    /**
     * Informs a participant that another participant IS typing
     *
     * @param sender Participant that appears to be typing
     * @param recipient Participant that receives the message that the other is typing
     */
    public void informParticipantBthatParticipantAIsTyping(Participant sender, Participant recipient, int windowNo){
        Vector recipients = new Vector();
        recipients.addElement(recipient);
      
        Vector chatWindows = new Vector();
        chatWindows.addElement(windowNo);
        
        ps.sendLabelDisplayToParticipants(recipients,sender.getUsername()+"...is typing", chatWindows,false);
    }
    
    
    
    
    
    
     /**
     * Informs a participant that another participant IS Deleting
     *
     * @param sender Participant that appears to be typing
     * @param recipient Participant that receives the message that the other is typing
     */
    public void informParticipantBthatParticipantAIsDeleting(Participant sender, Participant recipient, int windowNumber){
        Vector recipients = new Vector();
        recipients.addElement(recipient);
        //int chatWindow = permissionsController.getWindownumberInWhichASendsToB(sender,recipient);
        Vector chatWindows = new Vector();
        chatWindows.addElement(windowNumber);
        ps.sendLabelDisplayToParticipants(recipients,sender.getUsername()+"...IS DELETING", chatWindows,true);
    }
    
    

    /**
     * Informs a participant that another participant is NOT typing
     *
     * @param sender Participant that appears to be not typing
     * @param recipient Participant that receives the message that the other is not typing
     */
    public void informParticipantBthatParticipantAIsNotTypingAndStatusIsOK(Participant sender, Participant recipient){
        Vector recipients = new Vector();
        recipients.addElement(recipient);
        int chatWindow = permissionsController.getWindownumber_DefaultsToZero(sender,recipient);
        Vector chatWindows = new Vector();
        chatWindows.addElement(chatWindow);
        ps.sendLabelDisplayToParticipants(recipients," ", chatWindows,false);
    }


    public void sendAndEnableLabelDisplayToALLStatusWindowsOfParticipant(Participant recipient,String text,boolean isRed,boolean isEnabled){
         sendAndEnableLabelDisplayToALLStatusWindowsOfParticipant(recipient,text,isRed,isEnabled,"");
    }
    
    
    
    public void sendAndEnableLabelDisplayToALLStatusWindowsOfParticipant(Participant recipient,String text,boolean isRed,boolean isEnabled,String prefixForSpreadsheet){
        Vector otherParticipants = (Vector)permissionsController.getOtherParticipantsSettingWindowsTo0(recipient).elementAt(0);
        Vector recipients = new Vector();
        recipients.addElement(recipient);
        Vector recipientsWindows = new Vector();
        for(int i=0;i<otherParticipants.size();i++){
            Participant otherParticipant = (Participant)otherParticipants.elementAt(i);
            int windowNumber = permissionsController.getWindownumber_DefaultsToZero(otherParticipant, recipient);
            boolean foundWindow = false;
            for(int j=0;j<recipientsWindows.size();j++){
                Integer recipientWindow = (Integer)recipientsWindows.elementAt(j);
                if(recipientWindow.equals(windowNumber)){
                    foundWindow = true;
                    break;
                }
            }
            if(!foundWindow)recipientsWindows.addElement(windowNumber);
        }
        ps.sendAndEnableLabelDisplayToParticipants(recipients, text, recipientsWindows, isRed, isEnabled);

        
        String labelColour = "BLACK"; if(isRed)labelColour="RED";
        String enablingScreen = "UNBLOCK"; if(!isEnabled) enablingScreen="BLOCK";
        this.saveDataToConversationHistory(prefixForSpreadsheet+"WINDOWBLOCK", text+"  ("+labelColour+","+enablingScreen+")");
        
        
      }

    /**
     * Instructs client of particular participant to display a status message (in black/red) in the status bar asssociated with a particular Participant.
     * @param sender
     * @param text
     * @param isRed
     */



    /**
     * Instructs clients to display a status message (in black/red) in the status bar asssociated with a particular Participant.
     * @param sender
     * @param text
     * @param isRed
     */
    public void sendLabelDisplayToAllowedParticipantsFromApparentOrigin(Participant sender,String text,boolean isRed){
        Vector recipientsOfChatText = permissionsController.getOtherParticipantsSettingWindowsTo0(sender);
        Vector pRecipients = (Vector)recipientsOfChatText.elementAt(0);
        Vector pUsernames = (Vector)recipientsOfChatText.elementAt(2);
        Vector pChatWindows = (Vector)recipientsOfChatText.elementAt(3);
        ps.sendLabelDisplayToParticipants(pRecipients,text,pChatWindows,isRed);
        
      }

    /**
     * Instructs client of particular participant to display a status message (in black/red) in the status bar asssociated with a particular Participant.
     * @param sender
     * @param text
     * @param isRed
     */
    public void sendAndEnableLabelDisplayToAllowedParticipantsFromApparentOrigin(Participant sender, String text, boolean isRed, boolean isEnabled){
         Vector recipientsOfChatText = permissionsController.getOtherParticipantsSettingWindowsTo0(sender);
         Vector pRecipients = (Vector)recipientsOfChatText.elementAt(0);
         //Vector pUsernames = (Vector)recipientsOfChatText.elementAt(2);
         Vector pChatWindows = (Vector)recipientsOfChatText.elementAt(3);
         ps.sendAndEnableLabelDisplayToParticipants(pRecipients,text,pChatWindows,isRed,isEnabled);
    }

    public void sendAndEnableLabelDisplayToAllOtherParticipantsFromApparentOriginInOwnStatusWindow(Participant sender, String text, boolean isRed, boolean isEnabled){
         Vector recipients = ps.getAllOtherParticipants(sender);
         Vector windows = new Vector();
         for(int i=0;i<recipients.size();i++){
             windows.addElement(0);
             System.err.println(((Participant)recipients.elementAt(i)).getUsername());
         }
         //System.exit(-5);
         //Vector pRecipients = (Vector)recipientsOfChatText.elementAt(0);
         ps.sendAndEnableLabelDisplayToParticipants(recipients,text,windows,isRed,isEnabled);
    }


    public void sendLabelDisplayToParticipantInOwnStatusWindow(Participant p, String text, boolean labelIsRed){
         Vector v = new Vector();
         Vector windows = new Vector();
         v.addElement(p);
         windows.addElement(0);
         ps.sendLabelDisplayToParticipants(v, text, windows, labelIsRed);
    }
    
    public void sendLabelDisplayToParticipantInStatusWindows1and2(Participant p, String text, boolean labelIsRed){
         Vector v = new Vector();
         Vector windows = new Vector();
         v.addElement(p);
         windows.addElement(0);
         windows.addElement(1);
         ps.sendLabelDisplayToParticipants(v, text, windows, labelIsRed);
    }

    /**
     * Sends a String to all clients that is displayed in the status window in which they see their own text.
     * @param text
     * @param labelIsRed
     */
    public void sendLabelDisplayToAllParticipantsInTheirOwnStatusWindow(String text,boolean labelIsRed){
         Vector v = ps.getAllParticipants();
         Vector windows = new Vector();
         for(int i=0;i<v.size();i++){
             Participant p = (Participant)v.elementAt(i);
             int windowNumber = permissionsController.getWindownumber_DefaultsToZero(p,p);
             windows.addElement(windowNumber);
         }
         ps.sendLabelDisplayToParticipants(v, text, windows, labelIsRed);
    }



    /**
     * Sends a String to a client that is displayed in the status window in which they see their own text.
     * @param text
     * @param labelIsRed
     * @param enable
     */
    public void sendLabelDisplayAndEnableToParticipantInOwnStatusWindow(Participant recipient,String text,boolean labelIsRed,boolean enable){
         int windowNumber = permissionsController.getWindownumber_DefaultsToZero(recipient,recipient);
         Vector windowNumbers = new Vector();
         windowNumbers.addElement(windowNumber);
         Vector v = new Vector();
         v.addElement(recipient);
         ps.sendAndEnableLabelDisplayToParticipants(v, text, windowNumbers, labelIsRed, enable);
    }

    
    public void changeClientInterface_allowENTERSEND(Participant recipient, boolean allowENTERSEND){
        if(allowENTERSEND){
           MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(0,MessageChangeClientInterfaceProperties.enableRETURNANDSEND_enableTEXTENTRY);
           ps.sendMessageToParticipant(recipient, mccip);
        }
        else{
           MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(0,MessageChangeClientInterfaceProperties.disableRETURNANDSEND_enableTEXTENTRY);
           ps.sendMessageToParticipant(recipient, mccip);
        }
    }

     public void changeClientInterface_clearTextEntryField(Participant recipient){
           MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(0,MessageChangeClientInterfaceProperties.clearTextEntryField);
           ps.sendMessageToParticipant(recipient, mccip); 
    }

    public void changeClientInterface_clearMainWindows(Participant recipient){
           MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(0,MessageChangeClientInterfaceProperties.clearMainTextWindows);
           ps.sendMessageToParticipant(recipient, mccip);
    }
    
    public void changeClientInterface_clearMainWindowsExceptWindow0(Participant recipient){
           MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(0,MessageChangeClientInterfaceProperties.clearAllWindowsExceptWindow0);
           ps.sendMessageToParticipant(recipient, mccip);
    }
    
    
    public void changeClientInterface_enableTextEntry(Participant recipient){
           MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(0,MessageChangeClientInterfaceProperties.enableTextEntry);
           ps.sendMessageToParticipant(recipient, mccip);
    }
    
    public void changeClientInterface_disableTextEntry(Participant recipient){
           MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(0,MessageChangeClientInterfaceProperties.disableTextEntry);
           ps.sendMessageToParticipant(recipient, mccip);
    }
    public void changeClientInterface_enableTextDisplay(Participant recipient){
           MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(0,MessageChangeClientInterfaceProperties.enableTextPane);
           ps.sendMessageToParticipant(recipient, mccip);
    }
     public void changeClientInterface_disableTextDisplay(Participant recipient){
           MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(0,MessageChangeClientInterfaceProperties.disableTextPane);
           ps.sendMessageToParticipant(recipient, mccip);
    }
     public void changeClientInterface_disableScrolling(Participant recipient){
           MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(0,MessageChangeClientInterfaceProperties.disableScrolling);
           ps.sendMessageToParticipant(recipient, mccip);
    }
    public void changeClientInterface_enableScrolling(Participant recipient){
           MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(0,MessageChangeClientInterfaceProperties.enableScrolling);
           ps.sendMessageToParticipant(recipient, mccip);
    }
    public void changeClientInterface_backgroundColour(Participant recipient, Color newColor){
           MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(0,MessageChangeClientInterfaceProperties.changeScreenBackgroundColour, newColor);
           ps.sendMessageToParticipant(recipient, mccip);
            //System.exit(-44444678);
    }
    public void changeClientInterface_AllColours(Color background, Color selfTextColor, Color other1, Color other2, Color other3, Color other4, Color other5, Color other6, Color other7,Color other8, int fontSize){
          StyledDocumentStyleSettings   sds = null ;
          Vector v = new Vector();
          v.addElement(other1); v.addElement(other2); v.addElement(other3); v.addElement(other4); v.addElement(other5); v.addElement(other6); v.addElement(other7);v.addElement(other8);
          sds = new  StyledDocumentStyleSettings(background,selfTextColor,v,true, fontSize);

          MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(0,MessageChangeClientInterfaceProperties.changeTextStyles, sds);
          Vector allP = ps.getAllParticipants();
          for(int i=0;i<allP.size();i++){
              Participant p = (Participant)allP.elementAt(i);
              ps.sendMessageToParticipant(p, mccip);
          }
           
           
         
            //System.exit(-44444678);
    }
    
    public void changeClientInterface_DisplayTextInMazeGameWindow(Participant recipient,String text, long lengthOfTime){
         MessageChangeClientInterfaceProperties mccip= new MessageChangeClientInterfaceProperties(0,MessageChangeClientInterfaceProperties.changeMazeWindow, text, lengthOfTime);
          ps.sendMessageToParticipant(recipient, mccip);
    }
    
    
    public void doCountdownToNextPartner(Participant a, Participant b,int steps, String message, String cvsPREFIX){
         for(int k=steps;k>0;k--){
            try{
                this.changeClientInterface_backgroundColour(a, Color.red);
                this.changeClientInterface_clearMainWindows(a);
                changeClientInterface_DisplayTextInMazeGameWindow(a,message+ " IN "+k+" secs",-1000); 
                this.changeClientInterface_backgroundColour(b, Color.red);
                this.changeClientInterface_clearMainWindows(b);
                changeClientInterface_DisplayTextInMazeGameWindow(b,message+ " IN "+k+" secs",-1000); 
                this.sendArtificialTurnToRecipientWithEnforcedTextColour(a, message+" in "+k+" secs", 0,2,cvsPREFIX);
                this.sendArtificialTurnToRecipientWithEnforcedTextColour(b, message+" in "+k+" secs", 0,2,cvsPREFIX);
                Thread.sleep(500);
                this.changeClientInterface_backgroundColour(a, Color.black);
                this.changeClientInterface_backgroundColour(b, Color.black);
                Thread.sleep(500);
                this.changeClientInterface_clearMainWindows(a);
                this.changeClientInterface_clearMainWindows(b);
                this.sendArtificialTurnToRecipientWithEnforcedTextColour(a, "Please start", 0,2,cvsPREFIX);
                this.sendArtificialTurnToRecipientWithEnforcedTextColour(b,"Please start", 0,2,cvsPREFIX);
                
            }catch (Exception e){
                e.printStackTrace();
            }
         }  
    }
    
    
    
    public void doCountdownToNextPartnerSendToAll(int steps, String message){
        
         Vector v = this.getParticipants().getAllParticipants();
        for(int k=steps;k>0;k--){
            try{
               
               
                for(int i=0;i<v.size();i++){
                    Participant p = (Participant)v.elementAt(i);
                    this.changeClientInterface_backgroundColour(p, Color.red);
                    this.changeClientInterface_clearMainWindows(p);
                    changeClientInterface_DisplayTextInMazeGameWindow(p,message+ " IN "+k+" secs",-1000);  
                }
                  this.sendArtificialTurnToAllParticipants(message+" in "+k+" secs",0);
                 
                  
                Thread.sleep(500);
                for(int i=0;i<v.size();i++){
                    Participant p = (Participant)v.elementAt(i);
                    
                    this.changeClientInterface_backgroundColour(p, Color.black);
                }
                
                
                Thread.sleep(500);
                
             }catch (Exception e){
                 e.printStackTrace();
             }   
        }  
        for(int i=0;i<v.size();i++){
                    Participant p = (Participant)v.elementAt(i);
                    this.changeClientInterface_clearMainWindows(p);
                   
        }
         this.sendArtificialTurnToAllParticipants("Please start",0);
        
        
    } 
    
    
    /**
     * Relays changes in the WYSIWYG window of client to other clients
     * @param sender
     * @param mWYSIWYG
     */
    public void relayWYSIWYGTextInsertedToAllowedParticipants(Participant sender,diet.message.MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYG){
           Vector recipientsOfChatText = permissionsController.getOtherParticipantsSettingWindowsTo0(sender);
           Vector pRecipients = (Vector)recipientsOfChatText.elementAt(0);
           Vector pUsernames = (Vector)recipientsOfChatText.elementAt(2);
           Vector pChatWindows = (Vector)recipientsOfChatText.elementAt(3);
           ps.sendWYSIWYGTextInsertedToParticipants(pRecipients,sender.getUsername(),pChatWindows,mWYSIWYG.getTextToAppendToWindow(),mWYSIWYG.getOffset(),mWYSIWYG.getLength());
    }

    /**
     * Relays changes in the WYSIWYG window of client to other clients
     * @param sender
     * @param mWYSIWYG
     */
    public void relayWYSIWYGTextRemovedToAllowedParticipants(Participant sender,diet.message.MessageWYSIWYGDocumentSyncFromClientRemove mWYSIWYG){
           Vector recipientsOfChatText = permissionsController.getOtherParticipantsSettingWindowsTo0(sender);
           Vector pRecipients = (Vector)recipientsOfChatText.elementAt(0);
           Vector pUsernames = (Vector)recipientsOfChatText.elementAt(2);
           Vector pChatWindows = (Vector)recipientsOfChatText.elementAt(3);
           ps.sendWYSIWYGTextRemovedToParticipants(pRecipients,sender.getUsername(),pChatWindows,mWYSIWYG.getOffset(),mWYSIWYG.getLength());
    }

    /**
     * Relays changes to the selection / cursor in the WYSIWYG window of client to other clients
     * @param sender
     * @param mWYSIWYGSel
     */
    public void relayWYSIWYGSelectionChangedToAllowedParticipants(Participant sender,MessageWYSIWYGTextSelectionFromClient mWYSIWYGSel){
          Vector recipientsOfChatText = permissionsController.getOtherParticipantsSettingWindowsTo0(sender);
          Vector pRecipients = (Vector)recipientsOfChatText.elementAt(0);
          Vector pUsernames = (Vector)recipientsOfChatText.elementAt(2);
          Vector pChatWindows = (Vector)recipientsOfChatText.elementAt(3);
          ps.sendWYSIWYGSelectionChangedToParticipants(pRecipients,sender.getUsername(),pChatWindows,mWYSIWYGSel.getCorrectedStartIndex(),mWYSIWYGSel.getCorrectedFinishIndex());
                    //MessageWYSIWYGTextSelectionToClient mwstsend = new MessageWYSIWYGTextSelectionToClient(1, mwysts.getEmail(),
                    //        mwysts.getUsername(), mwysts.getCorrectedStartIndex(), mwysts.getCorrectedFinishIndex());
    }

    /**
     * Sends message to WYSIWYG clients setting their rights on the conversational floor. The recipients are the participants
     * who are permitted to receive text from the sender Participant.
     *
     *
     * @param sender
     * @param newState
     * @param msgPrefix
     */
    public void sendWYSIWYGChangeInterceptionStatusToAllowedParticipants(Participant sender, int newState,String msgPrefix){
        Vector recipientsOfChatText = permissionsController.getOtherParticipantsSettingWindowsTo0(sender);
        Vector pRecipients = (Vector)recipientsOfChatText.elementAt(0);
        Vector pUsernames = (Vector)recipientsOfChatText.elementAt(2);
        ps.sendWYSIWYGChangeInterceptionStatusToParticipants(pRecipients,sender.getUsername(),newState, msgPrefix);
    }

    /**
     *
     * Sends message to WYSIWYG client setting its rights on the conversational floor (whether text entry is permitted)
     * @param recipient
     * @param newState
     * @param msgPrefix
     */
    public void sendWYSIWYGChangeInterceptionStatusToSingleParticipant(Participant recipient, int newState,String msgPrefix){
        MessageWYSIWYGChangeTurntakingStatus mWYSIWYGIS= new MessageWYSIWYGChangeTurntakingStatus("server","server",newState, msgPrefix);
        Vector recipients = new Vector();
        recipients.addElement(recipient);
        ps.sendMessageToParticipant(recipient,mWYSIWYGIS);
    }


    

     /**
      *
      * Saves the turn that is not being relayed to the log files. It also resets the queue of incoming messages associated with the participant.
      *
      * @param p
      * @param turnNotRelayed
      */
     public void setNewTurnBeingConstructedNotRelayingOldTurnAddingOldTurnToHistory(Participant p,MessageChatTextFromClient turnNotRelayed){
        DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(p,new Date().getTime());
        cH.saveMessage("NOTRELAYED.",turnNotRelayed.getTimeOnClientOfCreation().getTime(),turnNotRelayed.getServerTypingOnset(),turnNotRelayed.getServerTypingENTER(),p.getUsername(),p.getUsername(),turnNotRelayed.getText(),new Vector(),false,null,ds.getAllInsertsAndRemoves(),0,turnNotRelayed.getMostRecentPriorChatText());
    }

    
     
     
    /**
      *
      * Saves the turn that is not being relayed to the log files. It also resets the queue of incoming messages associated with the participant.
      * There is an additional 
      *
      * @param p
      * @param turnNotRelayed
      * @param prefixFORCSVSpreadsheetIDENTIFIER
      * 
      */
     public void setNewTurnBeingConstructedNotRelayingOldTurnAddingOldTurnToHistory(Participant p,MessageChatTextFromClient turnNotRelayed, String prefixFORCSVSpreadsheetIDENTIFIER){
        DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(p,new Date().getTime());
        cH.saveMessage(prefixFORCSVSpreadsheetIDENTIFIER+"notrelayed",turnNotRelayed.getTimeOnClientOfCreation().getTime(),turnNotRelayed.getServerTypingOnset(),turnNotRelayed.getServerTypingENTER(),p.getUsername(),p.getUsername(),turnNotRelayed.getText(),new Vector(),false,null,ds.getAllInsertsAndRemoves(),0,turnNotRelayed.getMostRecentPriorChatText());
    } 
     
     
     
     
     
     
     
     
     public void saveInfoAsFakeTurnFromServerWithAddresseesAsRecipients(String s,Participant pAddressee){
         Vector addressees = new Vector();
         addressees.addElement(pAddressee.getUsername());      
         try{
         cH.saveMessage("INFOASFAKETURNFROMSERVER",new Date().getTime(),new Date().getTime(), new Date().getTime(), "server", "server", s, addressees, false, null, new Vector(), 0,null);
         }catch(Exception e){
             e.printStackTrace();
         }
    }


    

    public void setNewTurnBeingConstructedNotRelayingOldTurnAddingOldTurnToHistory_WithAddresseeSavedAsRecipient(Participant p,MessageChatTextFromClient turnNotRelayed, Participant pAddressee){
        DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(p,new Date().getTime());
        Vector addressees = new Vector();
        if(pAddressee!=null)addressees.addElement(pAddressee.getUsername());
        cH.saveMessage("NEWTURNBEINGCONSTRUCTED",turnNotRelayed.getTimeOnClientOfCreation().getTime(),turnNotRelayed.getServerTypingOnset(),turnNotRelayed.getServerTypingENTER(),p.getUsername(),p.getUsername(),turnNotRelayed.getText(),addressees,false,null,ds.getAllInsertsAndRemoves(),0,turnNotRelayed.getMostRecentPriorChatText());
    }

     public void setNewMazeGameTurnBeingConstructedNotRelayingOldTurnAddingOldTurnToHistory(Participant p,MessageChatTextFromClient mct,int mazeNo,int moveNo,int indivMveNo){
         DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(p,new Date().getTime());
         cH.saveMazeGameMessage("",mct.getTimeOnClientOfCreation().getTime(),mct.getServerTypingOnset(),mct.getServerTypingENTER(),mct.getUsername(),mct.getUsername(),mct.getText(),new Vector(),mct.hasBeenBlocked(),mct.getKeypresses(),ds.getAllInsertsAndRemoves(),0,mazeNo,moveNo,indivMveNo,mct.getMostRecentPriorChatText());
     }
     
      public void setNewMazeGameTurnBeingConstructedNotRelayingOldTurnAddingOldTurnToHistory(Participant p,MessageChatTextFromClient mct,int mazeNo,int moveNo,int indivMveNo, String prefix){
         DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(p,new Date().getTime());
         cH.saveMazeGameMessage(prefix,mct.getTimeOnClientOfCreation().getTime(),mct.getServerTypingOnset(),mct.getServerTypingENTER(),mct.getUsername(),mct.getUsername(),mct.getText(),new Vector(),mct.hasBeenBlocked(),mct.getKeypresses(),ds.getAllInsertsAndRemoves(),0,mazeNo,moveNo,indivMveNo,mct.getMostRecentPriorChatText());
     }


     /**
      *
      *
      *
      * Demarcates a position in the stream of DocChanges from a Participant that determines the end of a turn. It resets the queue
      * of incoming DocChanges and saves the most recent turn to the logfiles. It resets and polls the outgoing queue of DocChangesOutgoingSequence
      * to check if any interventions were performed. If yes, a new turn is saved to the log files.
      *
      * <p>This method needs to be changed if it is to be used generally for WYSIWYG interfaces.
      *
      * @param p
      * @param timestamp
      * @param chOut
      */
     public void wYSIWYGSetNewDocChangesIncomingSavingBothChangesTypedAndModifiedOutgoingQueue(Participant p,long timestamp,DocChangesOutgoingSequenceFIFO chOut){
        DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(p,timestamp);
        Vector recipientsOfChatText = permissionsController.getOtherParticipantsSettingWindowsTo0(p);
        Vector pRecipients = (Vector)recipientsOfChatText.elementAt(0);
        Vector pUsernames = (Vector)recipientsOfChatText.elementAt(2);
        Vector pChatWindows = (Vector)recipientsOfChatText.elementAt(3);
        cH.saveMessage("WYSIWYGNewDocChangesIncoming(A)",-9999,ds.getStartTime(),ds.getEndOfTyping(),p.getUsername(),p.getUsername(),ds.getParsedText(),pUsernames,false,null,ds.getAllInsertsAndRemoves(),0,null);
        if(chOut.hasBeenChangedArtificially()){
            cH.saveMessage("WYSIWYGNewDocChangesIncoming(B)",-9999,chOut.getStartTime(),chOut.getFinishTime(),"server",p.getUsername(),chOut.getStringOfDocChangeInserts().getString(),
                    pUsernames,false,null,chOut.getAllInsertsAndRemoves(),0,null);
        }
    }


     public void sendDocChangeToAllowedParticipants(Participant sender,DocChange dc){
           Vector recipientsOfChatText = permissionsController.getOtherParticipantsSettingWindowsTo0(sender);
           Vector pRecipients = (Vector)recipientsOfChatText.elementAt(0);
           Vector pUsernames = (Vector)recipientsOfChatText.elementAt(2);
           Vector pChatWindows = (Vector)recipientsOfChatText.elementAt(3);
           if(dc instanceof DocInsert){
               DocInsert di = (DocInsert)dc;
               ps.sendWYSIWYGTextInsertedToParticipants(pRecipients,sender.getUsername(),pChatWindows,di.getStr(),di.getOffs(),di.getStr().length());
           }
           else if(dc instanceof DocRemove){
               DocRemove dr = (DocRemove)dc;
               ps.sendWYSIWYGTextRemovedToParticipants(pRecipients,sender.getUsername(),pChatWindows,dr.getOffs(),dr.getLen());
           }
    }


    public void relayMazeGameTurnToAllOtherParticipants(Participant sender,MessageChatTextFromClient mct,int mazeNo,int moveNo,int indivMveNo){
        //MessageChatTextToClient msccttc = new MessageChatTextToClient(mct.getEmail(),mct.getUsername(),0,mct.getText(),1);
        mct.setChatTextHasBeenRelayedByServer();
        DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(sender,new Date().getTime());
        Vector recipientsOfChatText = permissionsController.getOtherParticipantsSettingWindowsTo0(sender);
        Vector pRecipients = (Vector)recipientsOfChatText.elementAt(0);
        Vector pUsernames = (Vector)recipientsOfChatText.elementAt(2);
        Vector pChatWindows = (Vector)recipientsOfChatText.elementAt(3);


        for(int i=0;i<pRecipients.size();i++){
            Participant recipient = (Participant)pRecipients.elementAt(i);
            int windowNo = (Integer)pChatWindows.elementAt(i);
            int styleno = cC.getStyleManager().getUniqueIntForRecipient(recipient, sender);
            MessageChatTextToClient mctc = new MessageChatTextToClient(sender.getParticipantID(),sender.getUsername(),0,mct.getText(),windowNo,true,styleno);
            ps.sendMessageToParticipant(recipient, mctc);
        }

        cH.saveMazeGameMessage("",mct.getTimeOnClientOfCreation().getTime(),mct.getServerTypingOnset(),mct.getServerTypingENTER(),mct.getUsername(),mct.getUsername(),mct.getText(),pUsernames,mct.hasBeenBlocked(),mct.getKeypresses(),ds.getAllInsertsAndRemoves(),0,mazeNo,moveNo,indivMveNo,mct.getMostRecentPriorChatText());
    }

    
    public void relayMazeGameTurnToParticipant(Participant sender,Participant recipient, MessageChatTextFromClient mct,int mazeNo,int moveNo,int indivMveNo){
        //MessageChatTextToClient msccttc = new MessageChatTextToClient(mct.getEmail(),mct.getUsername(),0,mct.getText(),1);
        mct.setChatTextHasBeenRelayedByServer();
        DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(sender,new Date().getTime());
        
        Vector pUsernames = new Vector();
        pUsernames.addElement(recipient.getUsername());       
        int windowNo =0;
        int styleno = cC.getStyleManager().getUniqueIntForRecipient(recipient, sender);
        MessageChatTextToClient mctc = new MessageChatTextToClient(sender.getParticipantID(),sender.getUsername(),0,mct.getText(),windowNo,true,styleno);
        ps.sendMessageToParticipant(recipient, mctc);
        cH.saveMazeGameMessage("",mct.getTimeOnClientOfCreation().getTime(),mct.getServerTypingOnset(),mct.getServerTypingENTER(),mct.getUsername(),mct.getUsername(),mct.getText(),pUsernames,mct.hasBeenBlocked(),mct.getKeypresses(),ds.getAllInsertsAndRemoves(),0,mazeNo,moveNo,indivMveNo,mct.getMostRecentPriorChatText());
    }
    
    public void relayMazeGameTurnToParticipantWithEnforcedColour(Participant sender,Participant recipient, MessageChatTextFromClient mct,int mazeNo,int moveNo,int indivMveNo, int styleNo){
        //MessageChatTextToClient msccttc = new MessageChatTextToClient(mct.getEmail(),mct.getUsername(),0,mct.getText(),1);
        mct.setChatTextHasBeenRelayedByServer();
        DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(sender,new Date().getTime());
        
        Vector pUsernames = new Vector();
        pUsernames.addElement(recipient.getUsername());       
        int windowNo =0;
        //int styleno = cC.getStyleManager().getUniqueIntForRecipient(recipient, sender);
        MessageChatTextToClient mctc = new MessageChatTextToClient(sender.getParticipantID(),sender.getUsername(),0,mct.getText(),windowNo,true,styleNo);
        ps.sendMessageToParticipant(recipient, mctc);
        cH.saveMazeGameMessage("",mct.getTimeOnClientOfCreation().getTime(),mct.getServerTypingOnset(),mct.getServerTypingENTER(),mct.getUsername(),mct.getUsername(),mct.getText(),pUsernames,mct.hasBeenBlocked(),mct.getKeypresses(),ds.getAllInsertsAndRemoves(),0,mazeNo,moveNo,indivMveNo,mct.getMostRecentPriorChatText());
    }
    
    
     public void relayMazeGameTurnToParticipantWithEnforcedColourInMultipleWindow(Participant sender,Participant recipient, int windowNo, MessageChatTextFromClient mct,int mazeNo,int moveNo,int indivMveNo, int styleNo, String cvsPREFIX){
        //MessageChatTextToClient msccttc = new MessageChatTextToClient(mct.getEmail(),mct.getUsername(),0,mct.getText(),1);
        mct.setChatTextHasBeenRelayedByServer();
        DocChangesIncomingSequenceFIFO ds = getDocChangesIncoming().setNewTurnAndReturnPreviousTurn(sender,new Date().getTime());
        
        Vector pUsernames = new Vector();
        pUsernames.addElement(recipient.getUsername());       
        
        //int styleno = cC.getStyleManager().getUniqueIntForRecipient(recipient, sender);
        MessageChatTextToClient mctc = new MessageChatTextToClient(sender.getParticipantID(),sender.getUsername(),0,mct.getText(),windowNo,true,styleNo);
        ps.sendMessageToParticipant(recipient, mctc);
        cH.saveMazeGameMessage(cvsPREFIX,mct.getTimeOnClientOfCreation().getTime(),mct.getServerTypingOnset(),mct.getServerTypingENTER(),mct.getUsername(),mct.getUsername(),mct.getText(),pUsernames,mct.hasBeenBlocked(),mct.getKeypresses(),ds.getAllInsertsAndRemoves(),0,mazeNo,moveNo,indivMveNo,mct.getMostRecentPriorChatText());
    }
    
    

    public void stimuliGrid_ChangeSelection(Participant recipient, long serverID, String imageName, Color innermostC, Color innerC, String prefixFORCSVSpreadsheet){
        MessageGridStimuliSelectionToClient mgsstc= new MessageGridStimuliSelectionToClient("server", "server", serverID, imageName,  innermostC,  innerC);
        ps.sendMessageToParticipant(recipient, mgsstc); 
        this.saveDataToConversationHistory(recipient, prefixFORCSVSpreadsheet+"GRIDSTIMULISET:CHANGING "+imageName+" to RGB: "+innermostC.getRed()+","+innermostC.getGreen()+", ("+innerC.getBlue()+" "+innerC.getRed()+","+innerC.getGreen()+","+innerC.getBlue()+")", ""); 
    }
     public void stimuliGrid_ChangeSelection(Participant recipient, long serverID, Vector imageNames,Color innermostC, Color innerC, String prefixFORCSVSpreadsheet){
        MessageGridStimuliSelectionToClient mgsstc= new MessageGridStimuliSelectionToClient("server", "server", serverID, imageNames,  innermostC, innerC);
        ps.sendMessageToParticipant(recipient, mgsstc); 
        this.saveDataToConversationHistory(recipient, prefixFORCSVSpreadsheet+"GRIDSTIMULISET:CHANGING WHOLE SET"+" to RGB: "+innermostC.getRed()+","+innermostC.getGreen()+", ("+innerC.getBlue()+" "+innerC.getRed()+","+innerC.getGreen()+","+innerC.getBlue()+")", "");  
    }
    
    public void stimuliGrid_SendSet(Participant recipient, int rows, int columns,Vector serializedImages, int widthheight, long serverID, String prefixFORCSVSpreadsheet){
        MessageGridStimuliSendSetToClient  mssstc = new  MessageGridStimuliSendSetToClient(rows, columns,serializedImages, widthheight, serverID);
        ps.sendMessageToParticipant(recipient, mssstc);     
        this.saveDataToConversationHistory(recipient, prefixFORCSVSpreadsheet+"GRIDSTIMULISET:SENDING", "");
    }
    
    public void stimuliGrid_changeImages(Participant recipient, Vector names, long serverID, String prefixFORCSVSpreadsheet){
        MessageGridStimuliChangeImages mgsci = new MessageGridStimuliChangeImages(names,serverID);
        ps.sendMessageToParticipant(recipient, mgsci);
        String imagenames = "";
        for(int i=0;i<names.size();i++){
            imagenames = imagenames + ": "+(String)names.elementAt(i);
        }
        this.saveDataToConversationHistory(recipient,prefixFORCSVSpreadsheet+"GRIDSTIMULISET:CHANGING",imagenames);
    }
    
    
    
     public void subliminalstimuliset_SendSet(Participant recipient, Vector serializedImages, int width,int height, String prefixFORCSVSpreadsheet){
         MessageSubliminalStimuliSendSetToClient  mssstc = new MessageSubliminalStimuliSendSetToClient(serializedImages, width, height);
         ps.sendMessageToParticipant(recipient, mssstc);     
         this.saveDataToConversationHistory(recipient, prefixFORCSVSpreadsheet+"STIMULISET:SENDING", "");
     }
    
     
     public void subliminalstimuliset_displaySet(Participant recipient, long fixation1time, long stimulus1time
                               ,long blankscreen1time
                               ,long fixation2time, long stimulus2time
                               ,long blankscreen2time,
                               String stimulus1ID, String stimulus2ID, String prefixFORCSVSpreadsheet ){
         
         MessageSubliminalStimuliChangeImage  mssci  = new MessageSubliminalStimuliChangeImage(fixation1time,stimulus1time
                               ,blankscreen1time
                               ,fixation2time,  stimulus2time
                               ,blankscreen2time,
                               stimulus1ID,  stimulus2ID);
         ps.sendMessageToParticipant(recipient, mssci);  
          this.saveDataToConversationHistory(recipient, prefixFORCSVSpreadsheet+"STIMULISET:Display new set",
                  " fixationtime:"+fixation1time+
                  " stimulus1time:"+stimulus1time+
                  "blankscreen1time:"+blankscreen1time+
                  " fixation2time:"+fixation2time+
                  " stimulus2time:"+stimulus2time+
                  "blankscreen2time:"+blankscreen2time+
                  " stimulus1ID:"+stimulus1ID+
                  " stimulus2ID:"+stimulus2ID);
     }
        
     
     public void subliminalstimuliset_displayText(Participant recipient, String text, String panelName, Color textColour, int positionX, int positionY, long lengthOfTime, String prefixFORCSVSpreadsheet ){
         
          MessageSubliminalStimuliDisplayText mssdt= new MessageSubliminalStimuliDisplayText(text,textColour,panelName,positionX,positionY, lengthOfTime);
          ps.sendMessageToParticipant(recipient, mssdt); 
          
          this.saveDataToConversationHistory(recipient, prefixFORCSVSpreadsheet+"STIMULISET: Send text",text+" ("+positionX+","+positionY+")");
     }
     
         
    
    


     /**
      * Sends task move (e.g. move in the maze game) to a participant.
      * @param p
      * @param mt
      */
     public void sendTaskMoveToParticipant(Participant p, MessageTask mt){
        ps.sendMessageToParticipant(p, mt);

    }

     /**
      * Returns the TaskController (e.g. MazeGameController) associated with the Conversation.
      * @return TaskController
      */
     public TaskControllerInterface getTaskController(){
        if(tc!=null)return tc;
        return null;
    }


    public void setTaskController(TaskControllerInterface tc){
       this.tc=tc;
    }


     /**
      * Searches for a participant by their email
      * @param participantlogin
      * @return Participant
      */
     public Participant findParticipantWithEmail(String participantlogin){
        return ps.findParticipantWithEmail(participantlogin);
    }

     /**
      * Returns the location on the local file system where the data from the experiment is saved
      * @return File representing the enclosing folder/directory containing the data from the experiment
      */
     public File getDirectoryNameContainingAllSavedExperimentalData(){
        return convIO.getFileNameContainingConversationData();
    }

    public ConversationHistory getHistory(){
        return cH;
    }
    public DefaultConversationController getController(){
        return cC;
    }
    public String getConversationIdentifier(){
        return this.conversationIdentifier;
    }

    public DeprecatedWindowController_MustReplace getPermissionsController(){
        return this.permissionsController;
    }



    public Participants getParticipants(){
        return this.ps;
    }
    public DocChangesIncoming getDocChangesIncoming(){
        return this.turnsconstructed;
    }
    public DocChangesIncomingSequenceFIFO getTurnBeingConstructed(Participant p){
        return this.turnsconstructed.getTurnBeingConstructed(p);
    }
    public WordNetWrapper getWordNetWrapper(){
        return wnw;
    }

    public ConversationIO getConvIO(){
        return convIO;
    }

    public ExperimentSettings getExperimentSettings(){
        return this.expSettings;
    }
    static public boolean isRunningLocally(){
        if(Conversation.statC==null)return true;
        return false;
    }

    
    
    
    
    
    
    
    public void killClientOnRemoteMachine(Participant p){
        ps.killClient(p);
    }
    
    
    
    
    
    /**
     * Attempts to close down the Conversation and associated resources.
     * @param sendCloseToClients
     */
    public void closeDown(boolean sendCloseToClients){
        try{
            this.conversationIsActive=false;
            this.cC.stop();
            if(this.tc!=null){
                tc.closeDown();
            }
            this.cHistoryUIM.closedown();
            this.cH.closeDown();
            this.stop();
            this.ps.closeDown(sendCloseToClients);
            //this.ps.stop();
            this.ps = null;

            
            
            //this.conversationIsActive=false;
            this.convIO.shutThreadDownAndCloseFiles();
        } catch (Exception e){

        }
    }


    public void displayNEWWebpage(Participant p, String id, String header,String url, int width, int height,boolean vScrollBar,boolean displayCOURIERFONT){
        ps.displayNEWWebpage(p, id, header, url, width, height, vScrollBar,displayCOURIERFONT);
        
    }


    public void changeWebpageTextAndColour(Participant p, String id,String text, String colourBackground, String colourText){
        String textToBeSent = "<html><head><style type=\"text/css\">body {color:"+colourText+"; background: "+colourBackground+";}div { font-size: 120%;}</style></head><body><div>"+ text+"</div></body></html>";
        ps.changeWebpage(p, id, textToBeSent);
    }
//public String redbackground = "<html><head><style type=\"text/css\">body {color: white; background: red;}div { font-size: 200%;}</style></head><body><div>INCORRECT </div></body></html>";

    public void changeWebpageImage_OnServer_DEPRECATED(Participant p, String id,  String imageaddressOnServerWebserver){
        //imageaddressOnServerWebserver needs to include a backslash as first character.
        String url = "<html><img src='http://%%SERVERIPADDRESS%%"+          imageaddressOnServerWebserver+"'></img>";
       
       
        ps.changeWebpage(p, id, url);
    }

    public void changeWebpage(Participant p, String id,String url){
        ps.changeWebpage(p, id, url);
    }

     public void changeWebpage(Participant p, String id,String url, String prepend, String append, long lengthOfTime){
        ps.changeWebpage(p, id, url, prepend, append, lengthOfTime);
    }

    public void changeWebpage(Participant p, String id,String url, String prepend, String append){
        ps.changeWebpage(p, id, url, prepend, append);
    }

    public void changeJProgressBar(Participant p,String id, String text, Color foreCol, int value){
        ps.changeJProgressBar(p, id, text, foreCol, value);
    }
    public void changeJProgressBarsOfAllParticipants(String id, String text, Color foreCol,int value){
        if(Debug.debugBLOCKAGE){System.out.println("SINH1");;System.out.flush();}
        Vector v = ps.getAllParticipants();
         if(Debug.debugBLOCKAGE){ System.out.println("SINH2");;System.out.flush();}
        for(int i=0;i<v.size();i++){
            
            Participant p = (Participant)v.elementAt(i);
            if(Debug.debugBLOCKAGE){ System.out.println("SINH4 "+p.getUsername());;System.out.flush();}
             ps.changeJProgressBar(p, id, text, foreCol, value);
             if(Debug.debugBLOCKAGE){System.out.println("SINH5 "+p.getUsername());System.out.flush();}
        }
        if(Debug.debugBLOCKAGE){System.out.println("SINH6 ");System.out.flush();}
    }

    public void chageWebpageOfAllParticipants(String id, String text){
        Vector v = ps.getAllParticipants();
        for(int i=0;i<v.size();i++){
            Participant p = (Participant)v.elementAt(i);
            ps.changeWebpage(p, id, "", text, "");
        }
    }

     public void closeWebpageWindow(Participant p, String id){
        ps.closeWebpageWindow(p, id);
    }



    public ExperimentManager getExpManager() {
        return expManager;
    }

    public void printWln(String windowName, String text){
        EMUI em = this.getExpManager().getEMUI();
        if(em!=null){
            em.println(windowName, text);
        }

    }

    public static void printWSln(String windowName, String text){
        if(statC!=null){
           statC.printWln(windowName, text);
        }
    }
    public static void printWSlnLog(String windowName, String text)
    {
    	if(statC!=null)
    		statC.printWlnLog(windowName, text);

    }
    public void printWlnLog(String windowName, String text)
    {
    	EMUI em = this.getExpManager().getEMUI();
        if(em!=null){
            em.println(windowName, text);
        }
        this.convIO.saveWindowTextToLog(windowName, text);


    }

    public static void saveErr(Throwable t){
        if(statC!=null){
           statC.saveErrorLog(t);
        }
    }

    public static void saveErr(String s){
        if(statC!=null){
           statC.saveErrorLog(s);
        }
    }

    public void saveErrorLog(Throwable t){
        System.err.println(this.getConversationIdentifier()+ ": ERROR SOMEWHERE IN THE CONVERSATION CONTROLLER");
        printWln("Main","There is an ERROR in the Conversation Controller");
        printWln("Main","Check the ERRORLOG.TXT file in the directory containing");
        printWln("Main","the saved experimental data");
        printWln("Main",t.getMessage());
        getConvIO().saveErrorLog(t);
    }
    public void saveErrorLog(String s){
        System.err.println(this.getConversationIdentifier()+ ": ERROR SOMEWHERE IN THE CONVERSATION CONTROLLER");
        printWln("Main","There is an ERROR in the Conversation Controller");
        printWln("Main","Check the ERRORLOG.TXT file in the directory containing");
        printWln("Main","the saved experimental data");
        printWln("Main",s);
        getConvIO().saveErrorLog(s);
    }


    public boolean isConversationIsActive() {
        return conversationIsActive;
    }

    public void setConversationIsActive(boolean conversationIsActive) {
        this.conversationIsActive = conversationIsActive;
    }


    public void printAllP(){
        System.err.println("----------LISTING PARTICIPANTS------------------------");
        for(int i=0;i<this.ps.getAllParticipants().size();i++){
            Participant p = (Participant) ps.getAllParticipants().elementAt(i);
            System.err.println("PARTICIPANT:"+p.getParticipantID()+" "+p.getUsername());
        }
    }
}
