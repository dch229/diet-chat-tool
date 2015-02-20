package diet.server.ConversationController;
import client.MazeGameController2WAY;
import diet.message.*;
import diet.parameters.*;
import diet.server.ConnectionListener;
import diet.server.Conversation;
import diet.server.ConversationController.Interventions.ClariIntervention;
import diet.server.Participant;
import diet.server.conversationhistory.turn.MAZEGAMETURN;
import diet.server.conversationhistory.turn.Turn;
import diet.task.TaskControllerInterface;
import diet.task.mazegame.message.MessageCursorUpdate;
import diet.task.mazegame.server.JMazeGameControlFrameLimitedControl;
import diet.utils.Conversion;
import java.awt.Color;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;




public class CCMAZE_UWA2013_DYADIC_MULTI_4_PARTICIPANTS_SEPARATE_DYADS_MAZEBYMAZE_DEPRECATED  extends DefaultConversationController implements TaskControllerInterface{
    
    Vector participantsQueued = new Vector();
    
    Vector mazegameControllers = new Vector();
    Hashtable mazegameControllerLongTimeOfLatestChange = new Hashtable();  
    
    
    public long timePerMaze = 620000;
    //public long timer = 80000;
    boolean doTiming = true;
    boolean pauseTiming = true;
    
    //int numberOfTimers =0;
    
    // JMazeGameControlFrame mgcf ;
       JMazeGameControlFrameLimitedControl mgcflc ;
     
     boolean isDisplayingInterimMessage = false;
     ClariIntervention ci;
    
    public void startTiming(){
        //if(2<5)return; 
        //numberOfTimers++;
        
        //System.out.println("STARTING TIMING"+numberOfTimers); //This is for debug
        Thread t = new Thread(){
         public void run(){
               while(doTiming){
             try{
                 long timeOfStartOfSleep = new Date().getTime();
                  //System.out.println("TIMEOFSLEEP: "+ timeO   );
                  Thread.sleep(1000);
                  long timeOfEndOfSleep = new Date().getTime();
                  
                   
                  
                  for(int i=0;i<mazegameControllers.size();i++){
                      final MazeGameController2WAY mgc = (MazeGameController2WAY) mazegameControllers.elementAt(i);
                     
                      
                      
                      long timeOfLastChange =(Long) mazegameControllerLongTimeOfLatestChange.get(mgc);
                      if(pauseTiming){
                         timeOfLastChange = timeOfLastChange+ (timeOfEndOfSleep-timeOfStartOfSleep); 
                         mazegameControllerLongTimeOfLatestChange.put(mgc,timeOfLastChange);
                      }
                      
                      
                      
                      long timeSinceLastChange = new Date().getTime()-timeOfLastChange;
                      long timeRemaining = timePerMaze - timeSinceLastChange;
                      String timeLeft = Conversion.convertMillisecondsIntoText(timeRemaining);
                      Color background = Color.GREEN;
                      if(timeRemaining<120000) background = Color.ORANGE;
                      if(timeRemaining<60000) background = Color.RED;
                      
                      float proportionleft = ((float) timeRemaining)/timePerMaze;
                      
                      if(!isDisplayingInterimMessage){
                         //Need to do this so that it doesn't display progress bars while displaying the flashing screen
                         c.changeJProgressBar(mgc.pDirector, "CHATFRAME", timeLeft, background, (int)(100*proportionleft));
                         c.changeJProgressBar(mgc.pMatcher, "CHATFRAME", timeLeft, background, (int)(100*proportionleft));
                      }
                      mgc.updateJProgressBar((int)(100*proportionleft), timeLeft, background);
                      if(timeRemaining<=0){
                          c.changeJProgressBar(mgc.pDirector, "CHATFRAME", "Out of time! Please wait", Color.GRAY, 0); 
                          c.changeJProgressBar(mgc.pMatcher, "CHATFRAME", "Out of time! Please wait", Color.GRAY, 0);
                          
                          
                          Thread t = new Thread(){ public void run(){
                              isDisplayingInterimMessage=true;
                              c.doCountdownToNextPartner(mgc.pDirector, mgc.pMatcher,20, "Out of time. Next maze","");
                              mgc.moveToNextMaze("NEXT MAZE: Ran out of time");
                              isDisplayingInterimMessage=false;
                           } }; t.start();
                           
                           
                           mazegameControllerLongTimeOfLatestChange.put(mgc,new Date().getTime());
                      }
                      
                      
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
         for(int i=0;i<mazegameControllers.size();i++){
                      MazeGameController2WAY mgc = (MazeGameController2WAY) mazegameControllers.elementAt(i);
                      long timeOfLastChange =(Long) mazegameControllerLongTimeOfLatestChange.get(mgc);
                      mazegameControllerLongTimeOfLatestChange.put(mgc,timeOfLastChange+timetoAdd);
         }             
    }
    
    
    public void pauseTimer(){
        pauseTiming = !pauseTiming;
    }
    
    
    
    
    
   
    
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
        String portNumberOfServer = ""+ConnectionListener.staticGetPortNumber();
        mgcflc = new JMazeGameControlFrameLimitedControl(this, "Server: "+portNumberOfServer);
        mgcflc.pack();
        mgcflc.setVisible(true);
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
        
        
        if(participantID.startsWith("EEEE"))return true;
        if(participantID.startsWith("FFFF"))return true;
        if(participantID.startsWith("GGGG"))return true;
        if(participantID.startsWith("HHHH"))return true;
        if(participantID.startsWith("IIII"))return true;
        if(participantID.startsWith("JJJJ"))return true;
        if(participantID.startsWith("KKKK"))return true;
        if(participantID.startsWith("LLLL"))return true;
        if(participantID.startsWith("MMMM"))return true;
        if(participantID.startsWith("NNNN"))return true;
        if(participantID.startsWith("OOOO"))return true;
        if(participantID.startsWith("PPPP"))return true;
        if(participantID.startsWith("QQQQ"))return true;
        if(participantID.startsWith("RRRR"))return true;
        if(participantID.startsWith("SSSS"))return true;
        if(participantID.startsWith("TTTT"))return true;
        if(participantID.startsWith("UUUU"))return true;
        if(participantID.startsWith("VVVV"))return true;
        if(participantID.startsWith("WWWW"))return true;
        
        
        return false;
         //return super.requestParticipantJoinConversation(participantID);
    }

    @Override
    public synchronized void participantJoinedConversation(final Participant p) {
        super.participantJoinedConversation(p);
        participantsQueued.add(p);
        Conversation.printWSln("Main", "ID: "+p.getParticipantID()+" Username:"+p.getUsername()+" is ready to start. Total number: "+participantsQueued.size()+" participants");
        if(2<5)return;
        //if(c.getParticipants().getAllParticipants().size()>7) this.startExperiment();
 
        if(queuedA==null){
            queuedA = p;
        }
        else {
             if(mgcBASE==null){
                  mgcBASE =   new MazeGameController2WAY(c,true);
                  mgcBASE.startNewMazeGame(queuedA, p);
                  this.mazegameControllers.addElement(mgcBASE);
                  this.mazegameControllerLongTimeOfLatestChange.put(mgcBASE, new Date().getTime());
             }
             else{
                  MazeGameController2WAY mgcN = new MazeGameController2WAY(c,mgcBASE.getP1Mazes_Cloned(),mgcBASE.getP2Mazes_Cloned());
                  mgcN.startNewMazeGame(queuedA, p);
                  this.mazegameControllers.addElement(mgcN);
                  this.mazegameControllerLongTimeOfLatestChange.put(mgcN, new Date().getTime());
             }
             
             final Participant pA = queuedA;
             final Participant pB = p;
            
             
             queuedA=null;
            
            
        }
      
    }
 
     Participant queuedA = null;
     //Participant queuedB = null;
     MazeGameController2WAY mgcBASE;
     
     
    
             
     
     
     
     
     
     
     
     
    
    
    
    
     public void startExperimentOTHER(){
          this.startTiming();
          this.pauseTiming=false;
         
     }
    public void startExperiment(){
         try{
         MazeGameController2WAY mgc =null;
         while (this.participantsQueued.size()>1){
             Participant pA = (Participant)this.participantsQueued.elementAt(0);
             Participant pB = (Participant)this.participantsQueued.elementAt(1);
             Conversation.printWSln("Main", "Pairing "+pA.getUsername()+"  with "+pB.getUsername());
             if(mgc==null){
                 mgc =   new MazeGameController2WAY(c,true);
                 mgc.startNewMazeGame(pA, pB);
                 this.mazegameControllers.addElement(mgc);
                 this.mazegameControllerLongTimeOfLatestChange.put(mgc, new Date().getTime());
             } 
             else{
                  MazeGameController2WAY mgcN = new MazeGameController2WAY(c,mgc.getP1Mazes_Cloned(),mgc.getP2Mazes_Cloned());
                  mgcN.startNewMazeGame(pA, pB);
                  this.mazegameControllers.addElement(mgcN);
                  this.mazegameControllerLongTimeOfLatestChange.put(mgcN, new Date().getTime());
             }
             this.participantsQueued.remove(pA);
             this.participantsQueued.remove(pB);
             
         }
         if(this.participantsQueued.size()>0){
             Conversation.printWSln("Main", "THERE IS AN ODD NUMBER OF PARTICIPANTS! ");
         }
         }catch (Exception e){
             e.printStackTrace();
         }
         this.pauseTiming=false;
         this.startTiming();
         
    }
    
    
    
    
    

    @Override
    public void participantRejoinedConversation(Participant p) {
       try{ 
        super.participantRejoinedConversation(p);
        MazeGameController2WAY mgc = this.getMazeGameControllerForDyad(p);
        Participant pPartner = this.getCurrentInterlocutor(p);
        if(pPartner==null)return;
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
        for(int i=0;i<mazegameControllers.size();i++){
                      MazeGameController2WAY mgc = (MazeGameController2WAY) mazegameControllers.elementAt(i);
                      if(mgc.pDirector==p) return mgc;
                      if(mgc.pMatcher==p) return mgc;
                  }
       return null;     
   }
   
   
   public synchronized Participant  getCurrentInterlocutor(Participant p){
       
        try{
       MazeGameController2WAY mgc = this.getMazeGameControllerForDyad(p);
       if(mgc==null)return null;
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
        final MazeGameController2WAY mgc = getMazeGameControllerForDyad(origin);
        
         boolean mazeIsCompleted = mgc.isCompleted();
         if(mazeIsCompleted){
             Conversation.printWSln("Main", "Possible network lag and/or error. Experiment is receiving task moves from "+origin.getUsername()+"..."+"the current maze is: "+mgc.getMazeNo());  
             return;
         }
         if( mtm instanceof MessageCursorUpdate){
             MessageCursorUpdate mcu = (MessageCursorUpdate)mtm;
             if(mcu.getMazeNo() != mgc.getMazeNo()){
                  Conversation.printWSln("Main", "Possible network lag and/or error. Experiment is receiving maze cursor updates from "+origin.getUsername()+"...from maze number "+mcu.getMazeNo()+"..."+"but the current maze is: "+mgc.getMazeNo());
                  return;
             }
         }

         mazeIsCompleted = mgc.processMazeMove(mtm, origin,false);
         
          if(mazeIsCompleted){
             long finishTime = new Date().getTime();
             try{
                 c.saveDataToFile("DATA", mgc.pDirector.getParticipantID(), mgc.pDirector.getUsername(),  finishTime, finishTime, finishTime, "MAZECOMPLETE" +mgc.getMazeNo(), new Vector());
                 c.saveDataToFile("DATA", mgc.pMatcher.getParticipantID(), mgc.pMatcher.getUsername(),  finishTime, finishTime, finishTime, "MAZECOMPLETE" +mgc.getMazeNo(), new Vector());
             
             }catch (Exception e){
                 e.printStackTrace();
             }
         }
         
         
         
         if(mazeIsCompleted ){
             
             this.mazegameControllerLongTimeOfLatestChange.put(mgc, new Date().getTime());
              c.changeJProgressBar(mgc.pDirector, "CHATFRAME", "Completed. Please wait", Color.GRAY, 0); 
              c.changeJProgressBar(mgc.pMatcher, "CHATFRAME", "Completed. Please wait", Color.GRAY, 0);
             
             Thread t = new Thread(){ public void run(){
                           isDisplayingInterimMessage=true;
                           c.doCountdownToNextPartner(mgc.pDirector, mgc.pMatcher,20, "Completed the maze. New maze","");
                           mgc.moveToNextMaze();
                           isDisplayingInterimMessage=false;
             } }; t.start();
         }
               
    }
    
    
   
    
    
   public void nextMazeCALLEDFROMUI(){
        
             
       int newMazeNumber = mgcBASE.getMazeNo()+1;
       for(int i =0; i< this.mazegameControllers.size();i++){
            final MazeGameController2WAY mgc = (MazeGameController2WAY)mazegameControllers.elementAt(i);
            mgc.moveToNextMazeWithNewPartners(mgc.pDirector,mgc.pMatcher,newMazeNumber);
            this.mazegameControllerLongTimeOfLatestChange.put(mgc, new Date().getTime());
            
            
        }
       
   }
    
   
    
  
   
   
   
    @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct){    
          
        
        
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
           if(pRecipient==null)return;
           
            if(mgc.getMazeNo()>13){
                   
                   ClariIntervention.processMazeChatText(sender, mct, pRecipient, 70000, 18000, mazeNo, moveNo, indivMveNo);
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
        sp = new StringParameter("Experiment ID","CCMAZE_DYADIC_MULTI_PARTICIPANTS_SEPARATEDYADS");
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
         StringParameterFixed otherTextColour1  = new StringParameterFixed("Text colour other 1",spv,"YELLOW");   v.addElement(otherTextColour1);
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
