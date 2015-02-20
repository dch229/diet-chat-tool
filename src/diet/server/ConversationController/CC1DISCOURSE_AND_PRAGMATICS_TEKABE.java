package diet.server.ConversationController;
import client.MazeGameController2WAY;
import client.mazegameutils.MazeGameLoadMazesFromJarFile;
import diet.message.*;
import diet.parameters.*;
import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.Participant;
import diet.task.TaskControllerInterface;
import diet.task.mazegame.message.MessageCursorUpdate;
import diet.textmanipulationmodules.CyclicRandomTextGenerators.CyclicRandomIntGenerator;
import diet.utils.Conversion;
import java.awt.Color;
import java.util.Date;
import java.util.Vector;




public class CC1DISCOURSE_AND_PRAGMATICS_TEKABE  extends CCMAZE_DYADIC_MULTI_2_PARTICIPANTS_SEPARATE_DYADS_MAZEBYMAZE_TIMEOUT implements TaskControllerInterface{

    
       
    
        public String experimentNORMALORBLOCKTYPING= CustomDialog.show2OptionDialog(new String[]{"NORMAL","NO_TYPING"}, "Please choose which experiment to run", "Experiment Chooser");
        
        
        
    
        
    
    
         public long timePerMaze =  (5 * 60 * 1000) + (numberOfCountdownSteps*1000);      
    
          //CyclicRandomIntGenerator crig = new CyclicRandomIntGenerator(0,4);
          //CyclicRandomIntGenerator crig = new CyclicRandomIntGenerator(3,1);
          
        public void startTiming(){
        //if(2<5)return; 
        //numberOfTimers++;
        
        //System.out.println("STARTING TIMING"+numberOfTimers); //This is for debug
        
        Thread t = new Thread(){
         public void run(){
             experimenthasstarted = true;
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
                          c.changeJProgressBar(mgc.pDirector, "CHATFRAME", "Out of time! Please wait...changing the interface", Color.GRAY, 0); 
                          c.changeJProgressBar(mgc.pMatcher, "CHATFRAME", "Out of time! Please wait...changing the interface", Color.GRAY, 0);
                          
                          
                          Thread t = new Thread(){ public void run(){
                              isDisplayingInterimMessage=true;
                              c.doCountdownToNextPartner(mgc.pDirector, mgc.pMatcher,numberOfCountdownSteps, "Out of time. Next maze",getCVSPREFIX(mgc));
                              mgc.moveToNextMaze("NEXT MAZE: Ran out of time");
                             
                              
                             // crig.generateNext(mgc);
                              c.changeClientInterface_clearMainWindows(mgc.pDirector);
                              c.changeClientInterface_clearMainWindows(mgc.pMatcher);
                              
                             
                              
                              addFillerSpaceInChatWindow(mgc);
                              showImportantMessages(mgc);
                              
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
    
        public void showImportantMessages(MazeGameController2WAY mgc){
            try{ 
            
            
            
            }catch (Exception e){
                e.printStackTrace();
                
            }
        }
        
        
        
        
        public void addFillerSpaceInChatWindow(MazeGameController2WAY mgc2w){
            try{
                  String info = this.getCVSPREFIX(mgc2w);
                  c.sendArtificialTurnToRecipientWithEnforcedTextColour(mgc2w.pDirector, "\n\n\n\n\n\n\n\n\n",0, info +"PREFILLER", 0);
                  c.sendArtificialTurnToRecipientWithEnforcedTextColour(mgc2w.pMatcher, "\n\n\n\n\n\n\n\n\n",0, info +"PREFILLER", 0);
                  
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    
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
                 c.saveDataToFile(this.getCVSPREFIX(mgc)+"DATA", mgc.pDirector.getParticipantID(), mgc.pDirector.getUsername(),  finishTime, finishTime, finishTime, "MAZECOMPLETE" +mgc.getMazeNo(), new Vector());
                 c.saveDataToFile(this.getCVSPREFIX(mgc)+"DATA", mgc.pMatcher.getParticipantID(), mgc.pMatcher.getUsername(),  finishTime, finishTime, finishTime, "MAZECOMPLETE" +mgc.getMazeNo(), new Vector());
             
             }catch (Exception e){
                 e.printStackTrace();
             }
         }
         
         
         
         if(mazeIsCompleted ){
             
             this.mazegameControllerLongTimeOfLatestChange.put(mgc, new Date().getTime());
              c.changeJProgressBar(mgc.pDirector, "CHATFRAME", "Please wait...changing the interface settings", Color.GRAY, 0); 
              c.changeJProgressBar(mgc.pMatcher, "CHATFRAME", "Please wait...changing the interface settings", Color.GRAY, 0);
             
              
              
             Thread t = new Thread(){ public void run(){
                           isDisplayingInterimMessage=true;
                           c.doCountdownToNextPartner(mgc.pDirector, mgc.pMatcher,numberOfCountdownSteps, "Completed the maze. New maze",getCVSPREFIX(mgc));
                           mgc.moveToNextMaze();
                           //crig.generateNext(mgc);
                           isDisplayingInterimMessage=false;
                           
                           c.changeClientInterface_clearMainWindows(mgc.pDirector);
                           c.changeClientInterface_clearMainWindows(mgc.pMatcher);
                           addFillerSpaceInChatWindow(mgc);
                           showImportantMessages(mgc);
                          //c.sendArtificialTurnToRecipient(null, null, interfacemode);
                          
                           
             } }; t.start();
         }
               
    }
    
    
    public synchronized int getMazeControllerNumber(MazeGameController2WAY mgc2){
       try{
        return this.mazegameControllers.indexOf(mgc2);
       }catch (Exception e){
           System.err.println("OUT");
           return -2;
       } 
    }
    
    
    public String getCVSPREFIX(MazeGameController2WAY mgc){
           String cvsPREFIX="";
           try{
           int mazeControllerNumber = getMazeControllerNumber(mgc);
           String interfaceType = this.experimentNORMALORBLOCKTYPING;
           cvsPREFIX = "MGC"+mazeControllerNumber+"_"+interfaceType;
           }catch(Exception e){}
           return cvsPREFIX;
    }
   
    
    
   
    
    
    
   
    
    
    
    
    
    
    
    @Override
    public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct){    
          
           if(!this.experimenthasstarted){
                
           
               c.sendArtificialTurnToRecipientWithEnforcedTextColour(sender, "Please wait for the experiment to start before typing! Thanks!", 0,2);
               return;
           }
        
           pTurnsElapsed.setValue(((Integer)pTurnsElapsed.getValue())+1);
           super.expSettings.generateParameterEvent(pTurnsElapsed);
     
   
           MazeGameController2WAY mgc = getMazeGameControllerForDyad(sender);
           if(mgc==null){
               Conversation.saveErr("ERROR: could not find mgc for "+sender.getUsername()+"..."+mct.getText()+"This is not critical if the experiment hasn't started yet");
               Conversation.printWSln("Main", "ERROR: could not find mgc for "+sender.getUsername()+"..."+mct.getText());
               //c.relayTurnToParticipant(sender, sender, mct);
               return;
           }
           
           int mazeNo = mgc.getMazeNo();
           int moveNo= mgc.getMoveNo();
           int indivMveNo = mgc.getParticipantsMoveNo(sender);
           Participant pRecipient = this.getCurrentInterlocutor(sender);
           if(pRecipient==null)return;
           
            
           //c.relayMazeGameTurnToParticipantWithEnforcedColour(sender, pRecipient, mct, mazeNo, moveNo, indivMveNo, moveNo);
           
          
           //interfacemode = mazeNo %3; 
           
           
           int mazeControllerNumber = getMazeControllerNumber(mgc);
         
           String info = this.getCVSPREFIX(mgc);
           
           
           
           c.relayMazeGameTurnToParticipantWithEnforcedColourInMultipleWindow(sender, pRecipient,0, mct, mazeNo, moveNo, indivMveNo,1,info);    
           c.sendLabelDisplayToParticipantInOwnStatusWindow(pRecipient, " ", false);
           mgc.appendToUI(sender.getUsername()+": "+mct.getText());
           
           //c.changeClientInterface_clearMainWindow(pRecipient);
           //c.changeClientInterface_clearMainWindows(sender);
           
    }
    
    
     @Override
    public void processKeyPress(Participant sender,MessageKeypressed mkp){
        //c.informIsTypingToAllowedParticipants(sender);//,mkp);
          String cvsID="_unset_";
          try{
          cvsID = this.getCVSPREFIX(this.getMazeGameControllerForDyad(sender));
          c.saveClientKeypressToFile(sender, mkp,cvsID);
          }catch(Exception e){};
          
           
          
          
          
          
           
          
    }
    
     public void startExperiment(){
         super.startExperiment();
         for(int i=0;i<mazegameControllers.size();i++){
                      MazeGameController2WAY mgc = (MazeGameController2WAY) mazegameControllers.elementAt(i);
                      c.sendArtificialTurnToRecipientWithEnforcedTextColour(mgc.pDirector, "From now on please do not talk to the person sitting next to you. If you have a question, please raise your hand!", 0,2, ""+this.getCVSPREFIX(mgc));
                      c.sendArtificialTurnToRecipientWithEnforcedTextColour(mgc.pMatcher, "From now on please do not talk to the person sitting next to you. If you have a question, please raise your hand!", 0,2, ""+this.getCVSPREFIX(mgc));
                      
         } 
     }
    
     public void startExperimentOLD(){
         try{
         MazeGameController2WAY mgc =null;
         System.err.println("STARTING EXPERIMENT1");
         while (this.participantsQueued.size()>1){
             Participant pA = (Participant)this.participantsQueued.elementAt(0);
             Participant pB = (Participant)this.participantsQueued.elementAt(1);
             Conversation.printWSln("Main", "Pairing "+pA.getUsername()+"  with "+pB.getUsername());
             if(mgc==null){
                  MazeGameLoadMazesFromJarFile mglmfj = new MazeGameLoadMazesFromJarFile();
                  mglmfj.getSetOf14MazesFromJar();    
                  mgc = new MazeGameController2WAY(c,MazeGameLoadMazesFromJarFile.cl1MazesRANDOMIZED,MazeGameLoadMazesFromJarFile.cl2MazesRANDOMIZED);
                 //mgc =   new MazeGameController2WAY(c,true);
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
             System.err.println("THERE IS AN ODD NUMBER OF PARTICIPANTS");
         }
         }catch (Exception e){
             e.printStackTrace();
         }
         this.pauseTiming=false;
         ////
         
         for(int i=0;i<mazegameControllers.size();i++){
                      MazeGameController2WAY mgc = (MazeGameController2WAY) mazegameControllers.elementAt(i);
                      c.sendArtificialTurnToRecipientWithEnforcedTextColour(mgc.pDirector, "Please start!\nFrom now on please do not talk to the person sitting next to you. If you have a question, raise your hand!", 0,2, ""+this.getCVSPREFIX(mgc));
                      c.sendArtificialTurnToRecipientWithEnforcedTextColour(mgc.pMatcher, "Please start!\nFrom now on please do not talk to the person sitting next to you. If you have a question, raise your hand!", 0,2, ""+this.getCVSPREFIX(mgc));
                      
         }         
         
         
                      
                      
         
         
         ////
         this.startTiming();
         System.err.println("STARTING EXPERIMENT");
    }
    
    
   
    //int interfacemode =2;
    
    
    

    
    
    
    
        @Override
    public boolean requestParticipantJoinConversation(String participantID) {
        //if(2<5)return super.requestParticipantJoinConversation(participantID);
        
        
        return true;
         //return super.requestParticipantJoinConversation(participantID);
    }

    @Override
    public void processWYSIWYGTextRemoved(Participant sender, MessageWYSIWYGDocumentSyncFromClientRemove mWYSIWYGkp) {
       // super.processWYSIWYGTextRemoved(sender, mWYSIWYGkp); //To change body of generated methods, choose Tools | Templates.
        this.updateInfoAsItIsTyped(sender, mWYSIWYGkp.getAllTextInWindow(),true);
        
        //System.exit(-4);
    }

    @Override
    public synchronized void processWYSIWYGTextInserted(Participant sender, MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp) {
       //super.processWYSIWYGTextInserted(sender, mWYSIWYGkp); //To change body of generated methods, choose Tools | Templates.
        
        
        
       this.updateInfoAsItIsTyped(sender, mWYSIWYGkp.getAllTextInWindow(),false);
        // System.exit(-5);
    }
    
    public synchronized void updateInfoAsItIsTyped(Participant sender, String textInBoxOLD, boolean updateISREMOVE){
           String textInBox ="";
           try{
              //textInBox = "PRESET"+textInBoxOLD.replaceAll("\n", "").replace("\n", "").replaceAll("\r\n", "").replaceAll("\r", "")+"ENDSET";
               textInBox = textInBoxOLD.replaceAll("\n", "").replace("\n", "").replaceAll("\r\n", "").replaceAll("\r", "");
           }catch(Exception e){e.printStackTrace();}
           
           
           
           
          
           
           MazeGameController2WAY mgc = getMazeGameControllerForDyad(sender);
           if(mgc==null)return;
           
           int mazeControllerNumber = getMazeControllerNumber(mgc);
           
           Participant pRecipient = this.getCurrentInterlocutor(sender);
           int mazeNo = mgc.getMazeNo();
           int moveNo= mgc.getMoveNo();
           int indivMveNo = mgc.getParticipantsMoveNo(sender);
           String info = this.getCVSPREFIX(mgc);
          
           
           
           System.out.println("contents are"+textInBox);
           
           if(this.experimentNORMALORBLOCKTYPING.equalsIgnoreCase("NORMAL")){
             c.informParticipantBthatParticipantAIsTyping(sender, pRecipient);
           }
           
           
           
          
          
    }
    
     
    
    
    
    
    
    
      public static ExperimentSettings getDefaultSettings() {
       
        Vector v = new Vector();
        StringParameter sp;
        StringParameterFixed spf;
        IntParameter ip;
        sp = new StringParameter("Experiment ID","CCMAZE_DYADIC_MULTI_PARTICIPANTS_SEPARATEDYADS");
        v.addElement(sp);
        Vector spv = new Vector();
        
        ip = new IntParameter("Number of participants per conversation",2);
        v.addElement(ip);
         spf = new StringParameterFixed("Chat tool interface",spv,"Formulate revise then send. Single or multiple windows");
        v.addElement(spf);
        
        //ip = new IntParameter("Number of windows per chat tool",1);
        //v.addElement(ip);
       
         
        //
        ip = new IntParameter("Number of windows per chat tool",2);
        v.addElement(ip);
         sp = new StringParameter("Window numbering policy","EACHOWNWINDOWATTOPENABLED");
        v.addElement(sp);
        //
        
        spf = new StringParameterFixed("Window numbering policy",spv,"ONEWINDOWENABLED");
        v.addElement(spf);
        
         StringParameterFixed bckg = new StringParameterFixed("Background colour","BLACK"); v.addElement(bckg);
         StringParameterFixed sfTextColour  = new StringParameterFixed("Text colour self",spv,"WHITE");           v.addElement(sfTextColour);       
         StringParameterFixed otherTextColour1  = new StringParameterFixed("Text colour other 1",spv,"YELLOW");   v.addElement(otherTextColour1);
         StringParameterFixed otherTextColour2  = new StringParameterFixed("Text colour other 2",spv,"RED");     v.addElement(otherTextColour2);
         StringParameterFixed otherTextColour3  = new StringParameterFixed("Text colour other 3",spv,"YELLOW");    v.addElement(otherTextColour3);
         StringParameterFixed otherTextColour4  = new StringParameterFixed("Text colour other 4",spv,"YELLOW");     v.addElement(otherTextColour4);
         StringParameterFixed otherTextColour5  = new StringParameterFixed("Text colour other 5",spv,"YELLOW");      v.addElement(otherTextColour5);
         StringParameterFixed otherTextColour6  = new StringParameterFixed("Text colour other 6",spv,"YELLOW");  v.addElement(otherTextColour6);
         StringParameterFixed otherTextColour7  = new StringParameterFixed("Text colour other 7",spv,"YELLOW");   v.addElement(otherTextColour7);
         StringParameterFixed otherTextColour8  = new StringParameterFixed("Text colour other 9",spv,"YELLOW");   v.addElement(otherTextColour8);
         StringParameterFixed otherTextColour9  = new StringParameterFixed("Text colour other 9",spv,"YELLOW");   v.addElement(otherTextColour9);
         StringParameterFixed otherTextColour10  = new StringParameterFixed("Text colour other 10",spv,"YELLOW");   v.addElement(otherTextColour10);
         StringParameterFixed otherTextColour11  = new StringParameterFixed("Text colour other 11",spv,"YELLOW");   v.addElement(otherTextColour11);
         StringParameterFixed otherTextColour12  = new StringParameterFixed("Text colour other 12",spv,"YELLOW");   v.addElement(otherTextColour12);
         StringParameterFixed otherTextColour13  = new StringParameterFixed("Text colour other 13",spv,"YELLOW");   v.addElement(otherTextColour13);
         StringParameterFixed otherTextColour14  = new StringParameterFixed("Text colour other 14",spv,"YELLOW");   v.addElement(otherTextColour14);
         StringParameterFixed otherTextColour15  = new StringParameterFixed("Text colour other 15",spv,"YELLOW");   v.addElement(otherTextColour15);
         StringParameterFixed otherTextColour16  = new StringParameterFixed("Text colour other 16",spv,"YELLOW");   v.addElement(otherTextColour16);
         StringParameterFixed otherTextColour17  = new StringParameterFixed("Text colour other 17",spv,"YELLOW");   v.addElement(otherTextColour17);
         StringParameterFixed otherTextColour18  = new StringParameterFixed("Text colour other 18",spv,"YELLOW");   v.addElement(otherTextColour18);
         StringParameterFixed otherTextColour19  = new StringParameterFixed("Text colour other 19",spv,"YELLOW");   v.addElement(otherTextColour19);
         StringParameterFixed otherTextColour20  = new StringParameterFixed("Text colour other 20",spv,"YELLOW");   v.addElement(otherTextColour20);
         
              
          
          
         
         
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
