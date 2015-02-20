package diet.server.ConversationController;
import client.MazeGameController2WAY;
import client.mazegameutils.MazeGameLoadMazesFromJarFile;
import diet.message.*;
import diet.parameters.*;
import diet.server.Conversation;
import diet.server.Participant;
import diet.task.TaskControllerInterface;
import diet.task.mazegame.message.MessageCursorUpdate;
import diet.textmanipulationmodules.AcknowledmentDegrader.CheapOKDegrader;
import diet.textmanipulationmodules.CyclicRandomTextGenerators.CyclicRandomIntGenerator;
import diet.utils.Conversion;
import java.awt.Color;
import java.util.Date;
import java.util.Vector;




public class CCMAZE_DYADIC_MULTI_2_PARTICIPANTS_SEPARATE_DYADS_MAZEBYMAZE_TIMEOUT_DP_DEMO  extends CCMAZE_DYADIC_MULTI_2_PARTICIPANTS_SEPARATE_DYADS_MAZEBYMAZE_TIMEOUT implements TaskControllerInterface{
    
    
    
         public long timePerMaze =  (5 * 60 * 1000) + (numberOfCountdownSteps*1000);      
    
          //CyclicRandomIntGenerator crig = new CyclicRandomIntGenerator(0,4);
          CyclicRandomIntGenerator crig = new CyclicRandomIntGenerator(3,1);
          
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
        
                              crig.generateNext(mgc);
                              c.changeClientInterface_clearMainWindows(mgc.pDirector);
                              c.changeClientInterface_clearMainWindows(mgc.pMatcher);
                              addFillerSpaceInChatWindow(mgc);
                              
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
           ///
           
            if(CheapOKDegrader.doesStringHaveOK(mct.getText())&&mct.getText().length()>4){
                Conversation.printWSln("Main", "String has ok in it...!");
                String text = CheapOKDegrader.removeOKString(mct.getText());
                //c.sendArtificialTurnToAllOtherParticipants(sender, text);
                c.sendArtificialTurnToRecipientWithEnforcedTextColour(pRecipient, sender.getUsername()+":"+text, 0, 1, this.getCVSPREFIX(mgc)+"OK substitute");
                
           }else{
                  
                 c.relayMazeGameTurnToParticipantWithEnforcedColour(sender, pRecipient, mct, mazeNo, moveNo, indivMveNo,1);
                //c.relayMazeGameTurnToParticipant(sender, pRecipient, mct, mazeNo, moveNo, indivMveNo);
                c.sendLabelDisplayToParticipantInOwnStatusWindow(pRecipient, "Status:OK", false);
           }
           

           
           
           
           
           ///
           
           
           mgc.appendToUI(sender.getUsername()+": "+mct.getText());
           
           
           
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
                           crig.generateNext(mgc);
                           isDisplayingInterimMessage=false;
                           
                           c.changeClientInterface_clearMainWindows(mgc.pDirector);
                           c.changeClientInterface_clearMainWindows(mgc.pMatcher);
                           addFillerSpaceInChatWindow(mgc);
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
           int interfaceNumber = crig.getCurrentValue(mgc);
           cvsPREFIX = "MGC"+mazeControllerNumber+"_"+interfaceNumber;
           }catch(Exception e){}
           return cvsPREFIX;
    }
   
    @Override
   
    
    
    public void processKeyPress(Participant sender,MessageKeypressed mkp){
       super.processKeyPress(sender, mkp);
          
    }
    
     public void startExperiment(){
         super.startExperiment();
         for(int i=0;i<mazegameControllers.size();i++){
                      MazeGameController2WAY mgc = (MazeGameController2WAY) mazegameControllers.elementAt(i);
                      c.sendArtificialTurnToRecipientWithEnforcedTextColour(mgc.pDirector, "From now on please do not talk to the person sitting next to you. If you have a question, raise your hand!", 0,2, ""+this.getCVSPREFIX(mgc));
                      c.sendArtificialTurnToRecipientWithEnforcedTextColour(mgc.pMatcher, "From now on please do not talk to the person sitting next to you. If you have a question, raise your hand!", 0,2, ""+this.getCVSPREFIX(mgc));
                      
         } 
     }
    
   
    
    
   
    //int interfacemode =2;
    
    
    

    
    
    
    
        @Override
    public boolean requestParticipantJoinConversation(String participantID) {
        if(2<5)return super.requestParticipantJoinConversation(participantID);
        
        if(experimenthasstarted){
            boolean foundOriginalParticipant = false;
            Vector vParticipants = c.getParticipants().getAllParticipants();
            for(int i=0;i<vParticipants.size();i++){
                Participant p = (Participant)vParticipants.elementAt(i);
                if(p.getParticipantID().equalsIgnoreCase(participantID)){
                    return true;
                }
            }
            Conversation.printWSlnLog("Main", "A participant tried to log in with ID: "+participantID+" during the experiment.");
            return false;
        }
        if(participantID.startsWith("1111A"))return true;
        if(participantID.startsWith("1111B"))return true;
        
        if(participantID.startsWith("2222A"))return true;
        if(participantID.startsWith("2222B"))return true;
        
        if(participantID.startsWith("3333A"))return true;
        if(participantID.startsWith("3333B"))return true;
        
        if(participantID.startsWith("4444A"))return true;
        if(participantID.startsWith("4444B"))return true;
        
        if(participantID.startsWith("5555A"))return true;
        if(participantID.startsWith("5555B"))return true;
        
        if(participantID.startsWith("6666A"))return true;
        if(participantID.startsWith("6666B"))return true;
     
       if(participantID.startsWith("7777A"))return true;
       if(participantID.startsWith("7777B"))return true;
        
       if(participantID.startsWith("8888A"))return true;
       if(participantID.startsWith("8888B"))return true;
         
       if(participantID.startsWith("9999A"))return true;
       if(participantID.startsWith("9999B"))return true;
       
       if(participantID.startsWith("0000A"))return true;
       if(participantID.startsWith("0000B"))return true;
       
       
        //if(participantID.equals("YYYYYY"))return true;
        //if(participantID.equals("ZZZZZZ"))return true;
        
        
        return false;
         //return super.requestParticipantJoinConversation(participantID);
    }

    @Override
    public void processWYSIWYGTextRemoved(Participant sender, MessageWYSIWYGDocumentSyncFromClientRemove mWYSIWYGkp) {
        super.processWYSIWYGTextRemoved(sender, mWYSIWYGkp); //To change body of generated methods, choose Tools | Templates.
         this.updateInfoAsItIsTyped(sender, mWYSIWYGkp.getAllTextInWindow());
        //System.exit(-4);
    }

    @Override
    public synchronized void processWYSIWYGTextInserted(Participant sender, MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp) {
        super.processWYSIWYGTextInserted(sender, mWYSIWYGkp); //To change body of generated methods, choose Tools | Templates.
        
       this.updateInfoAsItIsTyped(sender, mWYSIWYGkp.getAllTextInWindow());
        // System.exit(-5);
    }
    
    public synchronized void updateInfoAsItIsTyped(Participant sender, String textInBoxOLD){
           String textInBox ="";
           try{
              //textInBox = "PRESET"+textInBoxOLD.replaceAll("\n", "").replace("\n", "").replaceAll("\r\n", "").replaceAll("\r", "")+"ENDSET";
               textInBox = textInBoxOLD.replaceAll("\n", "").replace("\n", "").replaceAll("\r\n", "").replaceAll("\r", "");
           }catch(Exception e){e.printStackTrace();}
           
           MazeGameController2WAY mgc = getMazeGameControllerForDyad(sender);
           if(mgc==null)return;
           
           int mazeControllerNumber = getMazeControllerNumber(mgc);
           int interfaceNumber = crig.getCurrentValue(mgc);
           Participant pRecipient = this.getCurrentInterlocutor(sender);
           int mazeNo = mgc.getMazeNo();
           int moveNo= mgc.getMoveNo();
           int indivMveNo = mgc.getParticipantsMoveNo(sender);
           String info = this.getCVSPREFIX(mgc);
          
           
           
           System.out.println("contents are"+textInBox);
           
          if(interfaceNumber==2||interfaceNumber==3) {
                c.changeClientInterface_clearMainWindowsExceptWindow0(pRecipient);
                //c.relayMazeGameTurnToParticipantWithEnforcedColourInMultipleWindow(sender, pRecipient,1, mct, mazeNo, moveNo, indivMveNo,1,info); 
                //c.sendArtificialTurnToRecipient(pRecipient, info, indivMveNo);
                //c.sendArtificialTurnToRecipient(pRecipient, info, indivMveNo, cvsID);
               //c.sendArtificialTurnToRecipient(pRecipient, textInBox, 1, info+"UPDATEINFOASTYPED");
                c.sendArtificialTurnToRecipientWithEnforcedTextColour(pRecipient, textInBox, 1, 1, info+"UPDATEINFOASTYPED");
                
          }
    }
    
     
    
    
    
    
    
    
      
    
    
    

}
