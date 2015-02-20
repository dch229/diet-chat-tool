package diet.server.ConversationController;
import client.MazeGameController2WAY;
import diet.message.*;
import diet.parameters.*;
import diet.server.Conversation;
//import diet.server.ConversationController.CCMAZE_DYADIC_MULTI_4_PARTICIPANTS_SEPARATE_DYADS_MAZEBYMAZE.MediatedInteractionGenerateCR;
import diet.server.ConversationController.Interventions.ClariIntervention;
import diet.server.Participant;
import diet.server.conversationhistory.turn.MAZEGAMETURN;
import diet.server.conversationhistory.turn.Turn;
import diet.task.TaskControllerInterface;
import diet.task.mazegame.message.MessageCursorUpdate;
import diet.task.mazegame.server.JMazeGameControlFrame;
import diet.utils.Conversion;
import diet.utils.swing.Display;
import java.awt.Color;
import java.util.Date;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;




public class CCMAZE_UWA2013_DYADIC_4PARTICIPANTS_SEPARATE_DYADS_MAZEBYMAZE  extends DefaultConversationController implements TaskControllerInterface{
    
    
    public MazeGameController2WAY mgc1;
    public MazeGameController2WAY mgc2;
    
    
    
    Participant p1;
    Participant p2;
    Participant p3;
    Participant p4;
   
    
    public int partnerNumber =1;
    int everynumber =1;
    
    
    
    public long timePerMaze = 600000;
    public long timer =       600000;
    boolean doTiming = true;
    boolean pauseTiming = true;
    
    ClariIntervention ci;
    
    
    public void startTiming(){
        //if(2<5)return; 
        Thread t = new Thread(){
         public void run(){
               while(doTiming){
             try{
                  long timeOfSleep = new Date().getTime();
                  Thread.sleep(1000);
                  
                  
                  long endOfSleep = new Date().getTime();
                  if(!pauseTiming)timer =timer -(endOfSleep-timeOfSleep);
                  float proportionleft = ((float)timer) / ((float)timePerMaze);
                  System.out.println("THE TIME IS "+timer+"timepermaze is:"+timePerMaze +"......proportionleft: "+ (100*proportionleft));
                  
                  String timeLeft = Conversion.convertMillisecondsIntoText(timer);
                  mgcf.updateJProgressBar((int)(100*proportionleft), timeLeft);
                  Color background = Color.GREEN;
                  if(timer<120000) background = Color.ORANGE;
                  if(timer<60000) background = Color.RED;
                  
                  //c.changeJProgressBarsOfAllParticipants("CHATFRAME", timeLeft, background, (int)(100*proportionleft));
                  if(!mgc1.isCompleted()){
                      c.changeJProgressBar(mgc1.pDirector, "CHATFRAME", timeLeft, background, (int)(100*proportionleft));
                      c.changeJProgressBar(mgc1.pMatcher, "CHATFRAME", timeLeft, background, (int)(100*proportionleft));
                  }
                  if(!mgc2.isCompleted()){
                      c.changeJProgressBar(mgc2.pDirector, "CHATFRAME", timeLeft, background, (int)(100*proportionleft));
                      c.changeJProgressBar(mgc2.pMatcher, "CHATFRAME", timeLeft, background, (int)(100*proportionleft));
                  }
                  
                  
                  
                  
                  if(timer<=0) {
                      System.out.println("TIMER IS AT ZERO");
                      
                      //c.changeClientInterface_DisplayTextInMazeGameWindow(mgc1.pDirector, "Ran out of time", -1);
                      // c.changeClientInterface_DisplayTextInMazeGameWindow(mgc1.pMatcher, "Ran out of time", -1);
                      //  c.changeClientInterface_DisplayTextInMazeGameWindow(mgc2.pDirector, "Ran out of time", -1);
                      // c.changeClientInterface_DisplayTextInMazeGameWindow(mgc2.pMatcher, "Ran out of time", -1);
                      Display.displayTextPopup("TIMEOUT!");
                      performSwap(partnerNumber+1,mgc1.getMazeNo()+1);
                  }
             }catch (Exception e){
                  e.printStackTrace();
             }
         }
         }  
      };
        t.start();
        
        
        
         
    }
    
    public void addTimeToClock(long timetoAdd){
        timer = timer+timetoAdd;
    }
    public void pauseTimer(){
        pauseTiming = !pauseTiming;
    }
    public void startExperiment(){
         startTiming();
        pauseTiming=false;
    }
    
    
    
    
    JMazeGameControlFrame mgcf ;
    
    public static boolean showcCONGUI(){
        return false;
    } 
    
    @Override
    public void processLoop(){
             c.resetFromIsTypingtoNormalChatAllAllowedParticipants(super.getIsTypingTimeOut());      
    }

    @Override
    public void initialize(Conversation c, ExperimentSettings expSettings) {
        super.initialize(c, expSettings);
        c.setTaskController(this);
        //System.exit(-2343);
        mgcf = new JMazeGameControlFrame(this,"QUADS(4)");
        mgcf.pack();
        mgcf.setVisible(true);
        ci = new ClariIntervention(c);
    }

    @Override
    public boolean requestParticipantJoinConversation(String participantID) {
        if(c.getParticipants().getAllParticipants().size()>3){
            boolean foundOriginalParticipant = false;
            Vector vParticipants = c.getParticipants().getAllParticipants();
            for(int i=0;i<vParticipants.size();i++){
                Participant p = (Participant)vParticipants.elementAt(i);
                if(p.getParticipantID().equalsIgnoreCase(participantID)){
                    return true;
                }
            }
            return false;
        }
        
        
        
        if(participantID.startsWith("AAAA"))return true;
        if(participantID.startsWith("BBBB"))return true;
        if(participantID.startsWith("CCCC"))return true;
        if(participantID.startsWith("DDDD"))return true;
        
        
        
//        if(participantID.equalsIgnoreCase("555555"))return true;
        
        return false;
         //return super.requestParticipantJoinConversation(participantID);
    }

    @Override
    public synchronized void participantJoinedConversation(Participant p) {
        super.participantJoinedConversation(p);
        if(p.getParticipantID().startsWith("AAAA")) p1 = p;
        if(p.getParticipantID().startsWith("BBBB")) p2 = p;
        if(p.getParticipantID().startsWith("CCCC")) p3 = p;
        if(p.getParticipantID().startsWith("DDDD")) p4 = p;
        
       
        
        if(c.getParticipants().getAllParticipants().size()>3){
          
           mgc1 = new MazeGameController2WAY(c,true); mgc1.startNewMazeGame(p1, p2);
           mgc2 = new MazeGameController2WAY(c,mgc1.getP1Mazes_Cloned(),mgc1.getP2Mazes_Cloned()); mgc2.startNewMazeGame(p3, p4);
          
        }
        
        
        
        
    }

    @Override
    public void participantRejoinedConversation(Participant p) {
       try{ 
        super.participantRejoinedConversation(p);
        MazeGameController2WAY mgc = this.getMazeGameControllerForDyad(p);
        Participant pPartner = this.getCurrentInterlocutor(p);
        mgc.reconnectParticipant(p);
        c.sendArtificialTurnToRecipient(pPartner, "Network error. Your partner has logged back in. Resetting to the start of the maze",0);
        c.sendArtificialTurnToRecipient(p, "Please continue", 0);
        mgc.appendToUI("PARTICIPANT: "+p.getUsername()+" IS LOGGING BACK IN. CHECK NETWORK CABLES!");
       }catch (Exception e){
           e.printStackTrace();
           Conversation.printWSln("Main", "Error trying to let "+p.getUsername()+" properly rejoin conversation");
       }
    }
    
   
    
    
    
    
    
   public synchronized MazeGameController2WAY getMazeGameControllerForDyad(Participant p){
       if(mgc1.pDirector==p) return mgc1;
       if(mgc1.pMatcher==p) return mgc1;
       if(mgc2.pDirector==p) return mgc2;
       if(mgc2.pMatcher==p) return mgc2;
       
       
       
       
       return null;     
   }
   public synchronized Participant  getCurrentInterlocutor(Participant p){
       
        try{
       MazeGameController2WAY mgc = this.getMazeGameControllerForDyad(p);
       if(mgc.pDirector==p)return mgc.pMatcher;
       if(mgc.pMatcher==p)return mgc.pDirector;
       
       
      
        
       }catch(Exception e){
            Conversation.printWSln("Main", "Serious error...returning no partner for: "+p.getUsername());
           e.printStackTrace();}  
       
       return null;
       
   }

    public void closeDown() { }
    public void initialize() {    }
    public void participantJoinedConversation_StartingTask(Participant p){ }    

    
   
    
    
    
    
    
    public synchronized void processTaskMove(MessageTask mtm, Participant origin) {
        MazeGameController2WAY mgc = getMazeGameControllerForDyad(origin);
        
         boolean mazeIsCompleted = mgc.isCompleted();
         if(!mazeIsCompleted){
             mazeIsCompleted = mgc.processMazeMove(mtm, origin,false);
            System.err.println("OUTPUTT1");
         }
                 
         else{             
             Conversation.printWSln("Main", "Possible network lag and/or error...Experiment is receiving maze moves from "+origin.getUsername()+" even though the maze is completed! Maze number "+mgc.getMazeNo());
             System.err.println("OUTPUTT1B");
         }
         if( mtm instanceof MessageCursorUpdate){
             MessageCursorUpdate mcu = (MessageCursorUpdate)mtm;
             if(mcu.getMazeNo() != mgc.getMazeNo()){
                  Conversation.printWSln("Main", "Possible network lag and/or error. Experiment is receiving maze cursor updates from "+origin.getUsername()+"...from maze number "+mcu.getMazeNo()+"..."+"but the current maze is: "+mgc.getMazeNo());
             }
         }
         if(mazeIsCompleted){
             long finishTime = new Date().getTime();
             try{
                 c.saveDataToFile("DATA", mgc.pDirector.getParticipantID(), mgc.pDirector.getUsername(),  finishTime, finishTime, finishTime, "MAZECOMPLETE" +mgc.getMazeNo(), new Vector());
                 c.saveDataToFile("DATA", mgc.pMatcher.getParticipantID(), mgc.pMatcher.getUsername(),  finishTime, finishTime, finishTime, "MAZECOMPLETE" +mgc.getMazeNo(), new Vector());
             
             }catch (Exception e){
                 
             }
         }
         
         if(mazeIsCompleted && (mgc.getMazeNo()+1 ) % everynumber ==0){
                
                mgc.displayMessage("Please wait for the others to finish! ");
                 
                c.changeJProgressBar(mgc.pDirector, "CHATFRAME", "Completed! Please wait", Color.GRAY, 0); 
                c.changeJProgressBar(mgc.pMatcher, "CHATFRAME", "Completed! Please wait", Color.GRAY, 0);
                c.changeClientInterface_DisplayTextInMazeGameWindow(mgc.pDirector, "Completed. Please wait", -1);
                c.changeClientInterface_DisplayTextInMazeGameWindow(mgc.pMatcher, "Completed. Please wait", -1);
                
                
                
                if(mgc1.isCompleted()&&mgc1.getMazeNo()+1 % everynumber ==0) System.err.println("SWAPPING2A---"+mgc1.getMazeNo());
                if(mgc2.isCompleted()&&mgc2.getMazeNo()+1 % everynumber ==0) System.err.println("SWAPPING2B---"+mgc2.getMazeNo());
               
                
                 //System.exit(-5);
                if(mgc1.isCompleted()&&(mgc1.getMazeNo()+1) % everynumber ==0 &&
                   mgc2.isCompleted()&&(mgc2.getMazeNo()+1) % everynumber ==0 
                       
                        ){
                        System.err.println("SWAPPING2");
                         Display.displayTextPopup("ALLFINISHED!");
                        performSwap(partnerNumber+1, mgc1.getMazeNo()+1);
                        
                }
                System.err.println("OUTPUTT3");
        }
        else if (mazeIsCompleted){
                mgc.moveToNextMaze();
                //System.exit(-5);
        }
    }
    
    
    
    
    
    public synchronized void performSwap(int newPartnerNumber, int mazeNumber){
         c.doCountdownToNextPartnerSendToAll(20,"Changing to next partner");
         //
         if(newPartnerNumber==1 || newPartnerNumber==4 || newPartnerNumber==7 || newPartnerNumber==10  || newPartnerNumber==13 || newPartnerNumber==16){
             mgc1.moveToNextMazeWithNewPartners(p1, p2, mazeNumber);
             mgc2.moveToNextMazeWithNewPartners(p3, p4, mazeNumber); 
        }      
         else if(newPartnerNumber==2 || newPartnerNumber==5 || newPartnerNumber==8 || newPartnerNumber==11 || newPartnerNumber==14 ){
             mgc1.moveToNextMazeWithNewPartners(p1, p3, mazeNumber);
             mgc2.moveToNextMazeWithNewPartners(p2, p4, mazeNumber); 
        } 
        else if(newPartnerNumber==3 || newPartnerNumber==6 || newPartnerNumber==9 || newPartnerNumber==12 || newPartnerNumber==15){
            mgc1.moveToNextMazeWithNewPartners(p1, p4,mazeNumber);
            mgc2.moveToNextMazeWithNewPartners(p2, p3,mazeNumber); 
        }
         timer=this.timePerMaze;
         this.partnerNumber=newPartnerNumber;
         mgcf.updatePartnerNumber(newPartnerNumber);
    }
    
    
    public synchronized void performSwapNoFlash(int newPartnerNumber){
         int mazeNumber = newPartnerNumber-1;
         //
         if(newPartnerNumber==1 || newPartnerNumber==4 || newPartnerNumber==7 || newPartnerNumber==10  || newPartnerNumber==13 || newPartnerNumber==16){
             mgc1.moveToNextMazeWithNewPartners(p1, p2, mazeNumber);
             mgc2.moveToNextMazeWithNewPartners(p3, p4, mazeNumber); 
        }      
         else if(newPartnerNumber==2 || newPartnerNumber==5 || newPartnerNumber==8 || newPartnerNumber==11 || newPartnerNumber==14 ){
             mgc1.moveToNextMazeWithNewPartners(p1, p3, mazeNumber);
             mgc2.moveToNextMazeWithNewPartners(p2, p4, mazeNumber); 
        } 
        else if(newPartnerNumber==3 || newPartnerNumber==6 || newPartnerNumber==9 || newPartnerNumber==12 || newPartnerNumber==15){
            mgc1.moveToNextMazeWithNewPartners(p1, p4,mazeNumber);
            mgc2.moveToNextMazeWithNewPartners(p2, p3,mazeNumber); 
        }
         timer=this.timePerMaze;
         this.partnerNumber=newPartnerNumber;
         mgcf.updatePartnerNumber(newPartnerNumber);
    }
    
    
  
   
   
   
    @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct){    
           if(c.getParticipants().getAllParticipants().size()<4)return;
        
           if(diet.debug.Debug.debugMAZEGAME){
               if(mct.getText().contains("l")){
                   //performSwap(2,1);
                   //c.changeClientInterface_backgroundColour(sender, Color.red);
                   //c.doCountdownToNextPartnerSendToAll();
                   //c.doCountdownToNextPartnerSendToAll(20);
                   
               }
           }
           
           
           
           pTurnsElapsed.setValue(((Integer)pTurnsElapsed.getValue())+1);
           super.expSettings.generateParameterEvent(pTurnsElapsed);
     
   
           MazeGameController2WAY mgc = getMazeGameControllerForDyad(sender);
           if(mgc==null){
               Conversation.saveErr("ERROR: could not find mgc for "+sender.getUsername()+"..."+mct.getText());
               Conversation.printWSln("Main", "ERROR: could not find mgc for "+sender.getUsername()+"..."+mct.getText());
               return;
           }
           
           
                  
           
               int mazeNo = mgc.getMazeNo();
               int moveNo= mgc.getMoveNo();
               
               int indivMveNo = mgc.getParticipantsMoveNo(sender);
               Participant pRecipient = this.getCurrentInterlocutor(sender);
               if(mgc.getMazeNo()>13){
                   
                   ClariIntervention.processMazeChatText(sender, mct, pRecipient, 90000, 18000, mazeNo, moveNo, indivMveNo);
                   c.sendLabelDisplayToParticipantInOwnStatusWindow(pRecipient, "Status:OK", false);
                   mgc.appendToUI(sender.getUsername()+": "+mct.getText());
                   return;
               }
               
               
              
               c.relayMazeGameTurnToParticipant(sender, pRecipient, mct, mazeNo, moveNo, indivMveNo);
               c.sendLabelDisplayToParticipantInOwnStatusWindow(pRecipient, "Status:OK", false);
               mgc.appendToUI(sender.getUsername()+": "+mct.getText());
           
           
           
           
           
           
    }
    
    
    
    
    
    
    
    @Override
    public void processKeyPress(Participant sender,MessageKeypressed mkp){
        //c.informIsTypingToAllowedParticipants(sender);//,mkp);
          c.saveClientKeypressToFile(sender, mkp);
    }

    
    @Override
    public synchronized void processWYSIWYGTextInserted(Participant sender,MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp){
          //c.informIsTypingToAllowedParticipants(sender);
         if(c.getParticipants().getAllParticipants().size()<4)return; 
         if(mgc1==null||mgc2==null)return;
         Participant pRecipient = this.getCurrentInterlocutor(sender);
         if(pRecipient==null)return;
         //if(pRecipient==null)System.exit(-5);
         //if(sender==null)System.exit(-3);
         c.informParticipantBthatParticipantAIsTyping(sender, pRecipient);
       
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

    @Override
    public Turn getTurnTypeForIO_DEPRECATED() {
        return new MAZEGAMETURN();
    }
    
   
   

   
    
    
    
    
    
    
    public static ExperimentSettings getDefaultSettings() {
       
        Vector v = new Vector();
        StringParameter sp;
        StringParameterFixed spf;
        IntParameter ip;
        sp = new StringParameter("Experiment ID","CCMAZE_DYADIC_4PARTICIPANTS_SEPARATEDYADS");
        v.addElement(sp);
        Vector spv = new Vector();
        
        ip = new IntParameter("Number of participants per conversation",4);
        v.addElement(ip);
         spf = new StringParameterFixed("Chat tool interface",spv,"Formulate revise then send. Single or multiple windows");
        v.addElement(spf);
        
        ip = new IntParameter("Number of windows per chat tool",1);
        v.addElement(ip);
       
              
        
        
        spf = new StringParameterFixed("Window numbering policy",spv,"ONEWINDOWENABLED");
        v.addElement(spf);
        
         StringParameterFixed bckg = new StringParameterFixed("Background colour","BLACK"); v.addElement(bckg);
         StringParameterFixed sfTextColour  = new StringParameterFixed("Text colour self",spv,"WHITE");           v.addElement(sfTextColour);       
         StringParameterFixed otherTextColour1  = new StringParameterFixed("Text colour other 1",spv,"pink");   v.addElement(otherTextColour1);
         StringParameterFixed otherTextColour2  = new StringParameterFixed("Text colour other 2",spv,"BLUE");     v.addElement(otherTextColour2);
         StringParameterFixed otherTextColour3  = new StringParameterFixed("Text colour other 3",spv,"GREEN");    v.addElement(otherTextColour3);
         StringParameterFixed otherTextColour4  = new StringParameterFixed("Text colour other 4",spv,"CYAN");     v.addElement(otherTextColour4);
         StringParameterFixed otherTextColour5  = new StringParameterFixed("Text colour other 5",spv,"RED");      v.addElement(otherTextColour5);
         StringParameterFixed otherTextColour6  = new StringParameterFixed("Text colour other 6",spv,"MAGENTA");  v.addElement(otherTextColour6);
         StringParameterFixed otherTextColour7  = new StringParameterFixed("Text colour other 7",spv,"ORANGE");   v.addElement(otherTextColour7);
         
         
         
         IntParameter ipr;
         ipr = new IntParameter("Width of main window",(Integer)400);         v.addElement(ipr);
         ipr = new IntParameter("Height of main window",(Integer)250);        v.addElement(ipr);
         ipr = new IntParameter("Width of text entry window",(Integer)120);   v.addElement(ipr);
         ipr = new IntParameter("Height of text entry window",(Integer)80);  v.addElement(ipr);
         ipr = new IntParameter("Maximum length of textentry",(Integer)1000); v.addElement(ipr);
         
         
         
        
         
         
         
         
         
         
         
        
        spf = new StringParameterFixed("Horizontal or vertical alignment of multiple windows",spv,"Vertical");
        v.addElement(spf);
        
        ip = new IntParameter("Typing status timeout (msecs)",1000);
        v.addElement(ip);
        ExperimentSettings expSett = new ExperimentSettings(v);
        
        
        for(int i=0;i<v.size();i++){
            System.err.println("VERIFYING PARAMETERS ");
            Object o = v.elementAt(i);
            if(o instanceof Parameter){
                System.err.println(i+"VERIFYING PARAMETERS "+((Parameter)o).getID());
            }
            else{
                System.err.println(i+" EXITING "+o.getClass().toString()+" "+o.toString());
                System.exit(10*-i);
            }
        }
        //if(2<5)System.exit(-5);
        
        return expSett;
    
    
    
    
    
     

   
    
    
    
    
    } 
    
   

   

}
