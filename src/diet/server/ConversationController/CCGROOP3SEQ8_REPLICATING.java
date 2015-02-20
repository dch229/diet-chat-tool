package diet.server.ConversationController;
import diet.debug.Debug;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageKeypressed;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.message.MessageWYSIWYGTextSelectionFromClient;
import diet.parameters.DefaultSettingsFactory;
import diet.parameters.ExperimentSettings;
import diet.server.Conversation;
import diet.server.ConversationController.ui.InterfaceForJCountDown;
import diet.server.ConversationController.ui.JCountDown;
import diet.server.Participant;
import diet.task.collabMinitaskProceduralComms.*;
import java.awt.Color;
import java.util.Date;
import java.util.Vector;




public class CCGROOP3SEQ8_REPLICATING extends DefaultConversationController implements JSDM_4WAYConversationControllerINTERFACE, InterfaceForJCountDown{ //{extends CCGROOP3SEQ4{
   
   // JTimerOnServer_CCGROOP3SEQ8_REPLICATING jts;
    
     JCountDown jcd;
    
    AlphabeticalTaskWithInsertedWords at1 = new AlphabeticalTaskWithInsertedWords(1,this);
    AlphabeticalTaskWithInsertedWords at2 = new AlphabeticalTaskWithInsertedWords(2,this);;
    AlphabeticalTaskWithInsertedWords at3 = new AlphabeticalTaskWithInsertedWords(3,this);;
    AlphabeticalTaskWithInsertedWords at4 = new AlphabeticalTaskWithInsertedWords(4,this);;
    
    
    
    Participant p1,p2,p3,p4,p5,p6,p7,p8;
    
    
    int interventionSetSize = 4;
    
    
    public void accelerateTime(long t){
        
    }
    
    
    
    @Override
    public void initialize(Conversation c, ExperimentSettings expSettings) {
        super.initialize(c, expSettings);
        //jts = new JTimerOnServer_CCGROOP3SEQ8_REPLICATING(this);
        jcd = new JCountDown(this, "TIMER", 20000);
        
       // if(this==null)System.exit(-5);
    }

    public static ExperimentSettings getDefaultSettings() {
        ExperimentSettings es = DefaultSettingsFactory.getDefaultExperimentParameters();
        es.changeParameterValue("Number of participants per conversation",8);
        return es;
        //return DefaultSettingsFactory.getDefaultExperimentParameters();
    }
   
    
    
    
    @Override
    public void participantJoinedConversation(Participant p) {
       
        super.participantJoinedConversation(p);
        c.changeClientInterface_AllColours(Color.black, Color.white, Color.yellow, Color.yellow, Color.yellow, Color.yellow, Color.yellow, Color.yellow, Color.yellow,Color.yellow, 18);
              
        
        
        c.displayNEWWebpage(p, "ID1", "Instructions", "", 500, 300, false,false);
        c.changeJProgressBar(p, "ID1", "Time till speaker change", Color.GREEN, 100);
        c.changeWebpageTextAndColour(p, "ID1", "Please wait for other participant to log in", "white", "black");
        if(c.getParticipants().getAllParticipants().size()>1){
           // at.startTask ((Participant)c.getParticipants().getAllParticipants().elementAt(0),(Participant)c.getParticipants().getAllParticipants().elementAt(1));
           
        }
        if(c.getParticipants().getAllParticipants().size()>7){
          
           
           p1=c.getParticipants().findParticipantWithEmail("111111") ;
           p2=c.getParticipants().findParticipantWithEmail("222222") ;            
           p3=c.getParticipants().findParticipantWithEmail("333333") ;
           p4=c.getParticipants().findParticipantWithEmail("444444") ;
           p5=c.getParticipants().findParticipantWithEmail("555555") ;
           p6=c.getParticipants().findParticipantWithEmail("666666") ;
           p7=c.getParticipants().findParticipantWithEmail("777777") ;
           p8=c.getParticipants().findParticipantWithEmail("888888") ;
           
           
           
           at1.startTask (p1, p2);           
           at2.startTask (p3, p4); 
           at3.startTask (p5, p6); 
           at4.startTask (p7, p8); 
           
            }
        
        
          
        
        
        
    }

    @Override
    public void participantRejoinedConversation(Participant p) {
        super.participantRejoinedConversation(p);
        //c.displayNEWWebpage(p, "ID1", "Instructions", "", 300, 300, false,false);
        c.displayNEWWebpage(p, "ID1", "Instructions", "", 500, 300, false,false);
        c.changeJProgressBar(p, "ID1", "Time till speaker change", Color.GREEN, 100);
        boolean fAndReplaced = false;
        fAndReplaced = at1.findAndReplaceAndReset(p);
        if(!fAndReplaced)at2.findAndReplaceAndReset(p);
        if(!fAndReplaced)at3.findAndReplaceAndReset(p);
        if(!fAndReplaced)at4.findAndReplaceAndReset(p);
        if(!fAndReplaced){
            c.printWln("Main", "Serious error trying to re login a participant who crashed..can't find task for "+p.getParticipantID()+"..."+p.getUsername());
            c.saveDataToConversationHistoryDEPRECATED("SERIOUS ERROR" + "Serious error trying to re login a participant who crashed..can't find task for "+p.getParticipantID()+"..."+p.getUsername());
        }
        
        
        
        
    }


   
    public void changeJProgressBarforApparentSpeakerChange(float percentage, long absoluteTimeLeft){
        Conversation.printWSln("Main", "THIS REALLY SHOULDNT BE CALLED");
       // System.exit(-9);
    }
    
    
    public void changeJProgressBarforApparentSpeakerChange(float percentage, long absoluteTimeLeft, SameDifferenceManagerONLY2PARTNERS_INTENDEDFOR8PARTICIPANTS sdm){           
    }
    
     
   public int getIDXState(){
        return -1;
    }
    
    public void nextState(){
    }
    
    public void calculateWhetherToDoRandomSpoofMessage(){
     }
    
    
    
    private synchronized void sendSpoofRecalculatingMessagesFollowedByNewWordSet(String text){    
    }
    

    
   
   public synchronized void changePHYSICAL(String s){
      Conversation.printWSln("Main", "it really shouldnt be doing this");
   }
    public void changeAPPARENT(String s){
       
   }
   
    
    
    @Override
    public synchronized void processLoop(){
            // c.resetFromIsTypingtoNormalChatAllAllowedParticipants(super.getIsTypingTimeOut());
    }





    
    
    @Override
    public void processChatText(Participant sender,MessageChatTextFromClient mct){       
           Debug.printDBG("RECEIVINGFROM "+sender.getUsername()+"\n");  
        
          
           Vector v = c.getParticipants().getAllParticipants();
           for(int i=0;i<v.size();i++){
               Participant p = (Participant)v.elementAt(i);
               System.err.println(p.getParticipantID()+"..PIDUSERNAME....."+p.getUsername());
           }
           //System.exit(-25);
           
        
           if(c.getParticipants().getAllParticipants().size()<4){
               c.sendArtificialTurnToRecipient(sender, "PLEASE WAIT TILL EVERYONE HAS LOGGED IN! THANKS", 0);
               return;
           }
           pTurnsElapsed.setValue(((Integer)pTurnsElapsed.getValue())+1);
           super.expSettings.generateParameterEvent(pTurnsElapsed);   
           String description =this.getDescriptionForP(sender);
           AlphabeticalTaskWithInsertedWords atTask = null;
           if(this.at1.getParticipantA()==sender||this.at1.getParticipantB()==sender)atTask=at1;
           if(this.at2.getParticipantA()==sender||this.at2.getParticipantB()==sender)atTask=at2;
            if(this.at3.getParticipantA()==sender||this.at3.getParticipantB()==sender)atTask=at3;
           if(this.at4.getParticipantA()==sender||this.at4.getParticipantB()==sender)atTask=at4;
           if(atTask!=null){
               atTask.jcrs.appendChatText(sender.getUsername()+"("+this.getApparentName(sender)+")"        +": "+mct.getText());
           }
           
           if(mct.getText().startsWith("/")){
                System.err.println("SETTING MCT TO "+mct.getText().substring(1));
                description = this.getDescriptionForP(sender);
                try{
                c.setNewTurnBeingConstructedNotRelayingOldTurnAddingOldTurnToHistory(sender, mct, description);
                getATForP(sender).processSelection(sender, mct.getText());
                }catch (Exception e){
                    e.printStackTrace();
                }
                
                
                
           }
           else if(!mct.getText().startsWith("/")){
             
              AlphabeticalTaskWithInsertedWords atwi = this.getATForP(sender);
              Participant pRecipient = this.getPhysicalPartner(sender);
              
              //c.relayTurnToParticipantWithAlteredUserName(sender, "MRBOBO", mct, pRecipient);
              
              if(mct.getText().equalsIgnoreCase("swap")){
                  this.generateNewuniqueNames();
              }
              
              String apparentNameSender = this.getApparentName(sender);    
              c.relayTurnToParticipantWithAlteredUserNameAndEnforcedTextColour(sender, apparentNameSender, mct, pRecipient, 1);
               
               //must relay fake turns between participants
             
              
             
              
           }
           
           
           
          
           //System.exit(-3);
    }


    public void doNextState(){
        state++;
        if(state>1){
            generateNewuniqueNames();
            Conversation.printWSln("Main", "Generating unique names");
        }
        
        
        
    }
    
    
    
    
   
    boolean freezeTime = false; 
    int state =0;
    long stageLength =  20000;
    boolean doProgressBarsLoop = true;
    
    
    
   
    
    public void performUIJProgressBarDisplayOnClient(int percentage, String description){
         c.changeJProgressBarsOfAllParticipants("ID1", description, Color.red, percentage);
         
    }
    
    public void performUITriggeredEvent(String eventname, int value){
         jcd.appendMessage("CHANGING STATE TO: "+state+"\n");
         this.doNextState();
         jcd.appendMessage("CHANGING STATE TO: "+state+"\n");
         jcd.startCountdown();
    }
            
    
   
    
    
    
    public Participant getPhysicalPartner(Participant p){
        if(state==0||state==2||state == 4 ){
             if(p==p1)return p2;
             if(p==p2)return p1;
             if(p==p3)return p4;
             if(p==p4)return p3;
             if(p==p5)return p6;
             if(p==p6)return p5;
             if(p==p7)return p8;
             if(p==p8)return p7;           
        }
        else if (state ==1 || state ==3 || state ==5){
             if(p==p1)return p3;
             if(p==p2)return p4;
             if(p==p3)return p1;
             if(p==p4)return p2;
             if(p==p5)return p7;
             if(p==p6)return p8;
             if(p==p7)return p5;
             if(p==p8)return p6;  
        }
        else if (state ==6 || state >6){
             if(p==p1)return p4;
             if(p==p2)return p6;
             if(p==p3)return p7;
             if(p==p4)return p1;
             if(p==p5)return p8;
             if(p==p6)return p2;
             if(p==p7)return p3;
             if(p==p8)return p5;
        }
        return null;
    }
    
    
    public String getApparentName(Participant p){
         if(p==p1){
             if(p1ApparentName==null)return p1.getUsername();
             return p1ApparentName;
         }
         if(p==p2){
             if(p2ApparentName==null)return p2.getUsername();
             return p2ApparentName;
         }
         if(p==p3){
             if(p3ApparentName==null)return p3.getUsername();
             return p3ApparentName;
         }
         if(p==p4){
             if(p4ApparentName==null)return p4.getUsername();
             return p4ApparentName;
         }
         if(p==p5){
             if(p5ApparentName==null)return p5.getUsername();
             return p5ApparentName;
         }
         if(p==p6){
             if(p6ApparentName==null)return p6.getUsername();
             return p6ApparentName;
         }
         if(p==p7){
             if(p7ApparentName==null)return p7.getUsername();
             return p7ApparentName;
         }
         if(p==p8){
             if(p8ApparentName==null)return p8.getUsername();
             return p8ApparentName;
         }
         return "NOT SET";
    }    
    String p1ApparentName,   p2ApparentName,  p3ApparentName, p4ApparentName, p5ApparentName, p6ApparentName, p7ApparentName,p8ApparentName;
    
    
    
    Vector allNames = new Vector();
    
    
    public void generateNewuniqueNames(){
        p1ApparentName= "Participant"+  generateNewApparentName();
        p2ApparentName= "Participant"+  generateNewApparentName();
        p3ApparentName= "Participant"+  generateNewApparentName();
        p4ApparentName= "Participant"+  generateNewApparentName();
        p5ApparentName= "Participant"+  generateNewApparentName();
        p6ApparentName= "Participant"+  generateNewApparentName();
        p7ApparentName= "Participant"+  generateNewApparentName();
        p8ApparentName= "Participant"+  generateNewApparentName();
        
    }
    
    
    private String generateNewApparentName(){
        boolean generatedUniquename=false;
        while(!generatedUniquename){
            generatedUniquename = true;
            int nameCandidate = r.nextInt(99999);
            for(int i=0;i<allNames.size();i++){
                String nameAlreadyUsed = (String)allNames.elementAt(i);
                if(nameAlreadyUsed.equalsIgnoreCase(""+nameCandidate))generatedUniquename = false;
            }
            if(generatedUniquename){
                allNames.addElement(""+nameCandidate);
                return ""+nameCandidate;
            }
        }
        return "NOTSET";
    }
    
    
    
    
    public String getDescriptionForP(Participant p){
        String description = "";
        if(at1.a==p||at1.b==p){
                    description = at1.getDyadDescription()+"_";
                }
                else if(at2.a==p||at2.b==p){
                    description = at2.getDyadDescription()+"_";
                }
        else if(at3.a==p||at3.b==p){
                    description = at3.getDyadDescription()+"_";
                }
        else if(at4.a==p||at4.b==p){
                    description = at4.getDyadDescription()+"_";
                }
        
        return description+"_";
    }

    public AlphabeticalTaskWithInsertedWords getATForP(Participant p){
        if(at1.a==p||at1.b==p){
            return at1;
        }
         else if(at2.a==p||at2.b==p){
            return at2;
         }
         else if (at3.a==p||at3.b==p){
             return at3;
         }
         else if (at4.a==p||at4.b==p){
             return at4;
         }
        return null;
    }
    


   

    public void displayInterventionInfo(String interventioninfo){
        try{
            this.at1.jcrs.displayInterventionInfo("REMAINING: "+interventioninfo);
        }catch (Exception e){
            System.err.println("nullinterface");
            e.printStackTrace();
        }
    }


    @Override
    public void processKeyPress(Participant sender,MessageKeypressed mkp){
        //c.informIsTypingToAllowedParticipants(sender);//,mkp);
        String description = this.getDescriptionForP(sender);
        c.saveClientKeypressToFile(sender, mkp, description);
        
    }

  
    @Override
    public void processWYSIWYGTextInserted(Participant sender,MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp){
          //c.informIsTypingToAllowedParticipants(sender);

    }
    
    @Override
    public void processWYSIWYGTextRemoved(Participant sender,MessageWYSIWYGDocumentSyncFromClientRemove mWYSIWYGkp){
        //c.relayWYSIWYGTextRemovedToAllowedParticipants(sender,mWYSIWYGkp);
        //turnBeingConstructed.remove(mWYSIWYGkp.getOffset(),mWYSIWYGkp.getLength(),mWYSIWYGkp.getTimeStamp().getTime()); 
        //chOut.addMessage(sender,mWYSIWYGkp);
    }
    
    @Override
    public void processWYSIWYGSelectionChanged(Participant sender, MessageWYSIWYGTextSelectionFromClient mWYSIWYGSel){
        //c.relayWYSIWYGSelectionChangedToAllowedParticipants(sender,mWYSIWYGSel);
    }
    
   
   
    public void changeTime(long andTime){
        MoveAND.maxTimeBetweenPerformances=andTime;
    }

    
    
   
    public boolean requestParticipantJoinConversation(String participantID){
        if(participantID.equalsIgnoreCase("111111"))return true;
        if(participantID.equalsIgnoreCase("222222"))return true;
        if(participantID.equalsIgnoreCase("333333"))return true;
        if(participantID.equalsIgnoreCase("444444"))return true;
        if(participantID.equalsIgnoreCase("555555"))return true;
        if(participantID.equalsIgnoreCase("666666"))return true;
        if(participantID.equalsIgnoreCase("777777"))return true;
        if(participantID.equalsIgnoreCase("888888"))return true;
        
        Vector pss = (Vector)c.getParticipants().getAllParticipants();
        for(int i=0;i<pss.size();i++){
            Participant p = (Participant)pss.elementAt(i);
            //
        }
        return false;
    }
      public static String[] acceptableID = {"111111", "222222","333333","444444", "555555","666666","777777","888888"};

      
      
      public void ifCrashedSetTransitions1(String sdsds){
          
      }
      public void ifCrashedSetTransitions2(String sdsds){
          
      }
      
      public void swapMovesORvsAND(){ //THIS METHOD REACHES INTO THE MOVESFACTORY TO SWAP THE WORDS OR VS. AND
          
      }
      
      
      public void updateJProgressBarPhysical(String timeMessage, int value){
          
      }

    public void initializeGUIWITHSTATES(Vector v) {
        //this.jsdm.initializeWITHSTATES(v);
    }
      
      
      
}
