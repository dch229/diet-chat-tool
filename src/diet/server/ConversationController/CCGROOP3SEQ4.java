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
import diet.server.Participant;
import diet.server.Participants;
import diet.task.collabMinitaskProceduralComms.*;
import java.awt.Color;
import java.util.Date;
import java.util.Vector;




public class CCGROOP3SEQ4 extends DefaultConversationController implements JSDM_4WAYConversationControllerINTERFACE{
   
    AlphabeticalTaskWithInsertedWords at1 = new AlphabeticalTaskWithInsertedWords(1,this);
    AlphabeticalTaskWithInsertedWords at2 = new AlphabeticalTaskWithInsertedWords(2,this);;
    SameDifferenceManagerONLY2PARTNERS_INTENDEDFOR8PARTICIPANTS sdm;
    public SameDifferencemanagerTime6STEPS sdmt6;
    
    public JSDM jsdm = new JSDM(this);
    
    long practiceTime =  (1000*60*26);//  (1000*60*26)
    long condnTime=      (1000*60*6);//  (1000*60*6)
    
     
    long minimumDisplayDurationOfSpoofMessage = 4000;
    long maximumSpoofDisplayDurationOfSpoofMessage = 6000;
    
    
    
   
    long spoofDuringPracticePhaseMaximumInterSpoofDistance = (1000 * 60 * 8);// (1000 * 60 * 8);
    long spoofDuringPracticePhaseMinimumInterSpoofDistance  =(1000 * 60 * 4);// (1000 * 60 * 4);
    long TimeOfNextSpoof = new Date().getTime() + spoofDuringPracticePhaseMinimumInterSpoofDistance + 
                r.nextInt((int)(spoofDuringPracticePhaseMaximumInterSpoofDistance-spoofDuringPracticePhaseMinimumInterSpoofDistance));
        
    
    String[] apparentPartnerChanges;
    
    int numberOfCorrectBeforeUsingAND;
    int numberOfCorrectBeforeUsingXOR;
    
    int numberOfCorrectBeforeUsingADDITIONALMOVE;
    long additionalMoveProbability       = 4;
    long additionalMoveMinimumListLengthForGeneration = 4; 
    
    int[] maxSize = { 1,2,3,4,5,6,7,8,9,10};     
    
    int interventionSetSize = 4;
    
    
     public static boolean showcCONGUI() {
        return false;
    }
    
    public void accelerateTime(long milliseconds){
        sdmt6.accelerateTime(milliseconds);
    }
    
    
    @Override
    public void initialize(Conversation c, ExperimentSettings expSettings) {
        super.initialize(c, expSettings);
        jsdm.setVisible(true);
 
    }

    public static ExperimentSettings getDefaultSettings() {
        ExperimentSettings es = DefaultSettingsFactory.getDefaultExperimentParameters();
        es.changeParameterValue("Number of participants per conversation",4);
        return es;
        //return DefaultSettingsFactory.getDefaultExperimentParameters();
    }
    
    
    @Override
    public void participantJoinedConversation(Participant p) {
       
        super.participantJoinedConversation(p);
       
        c.displayNEWWebpage(p, "ID1", "Instructions", "", 500, 300, false,false);
        c.changeJProgressBar(p, "ID1", "Time till speaker change", Color.GREEN, 100);
        c.changeWebpageTextAndColour(p, "ID1", "Please wait for other participant to log in", "white", "black");
        if(c.getParticipants().getAllParticipants().size()>1){
           // at.startTask ((Participant)c.getParticipants().getAllParticipants().elementAt(0),(Participant)c.getParticipants().getAllParticipants().elementAt(1));
           
        }
        if(c.getParticipants().getAllParticipants().size()>3){
          
           Participants ps = c.getParticipants();
           Vector allP = c.getParticipants().getAllParticipants();
           sdm = new SameDifferenceManagerONLY2PARTNERS_INTENDEDFOR8PARTICIPANTS(jsdm, this, ps.findParticipantWithEmail("111111"),  ps.findParticipantWithEmail("222222"),  ps.findParticipantWithEmail("333333"), ps.findParticipantWithEmail("444444") );
           at1.startTask (sdm.getPhysicalConvo1()[0], sdm.getPhysicalConvo1()[1]);           
           at2.startTask (sdm.getPhysicalConvo2()[0], sdm.getPhysicalConvo2()[1]);       
           this.apparentPartnerChanges=sdm.getApparentPartnerChanges();
           this.sdmt6 = new SameDifferencemanagerTime6STEPS(jsdm,this,apparentPartnerChanges,practiceTime,condnTime,false, sdm, true, condnTime, condnTime);
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
        if(!fAndReplaced){
            c.printWln("Main", "Serious error trying to re login a participant who crashed..can't find task for "+p.getParticipantID()+"..."+p.getUsername());
            c.saveDataToConversationHistoryDEPRECATED("SERIOUS ERROR" + "Serious error trying to re login a participant who crashed..can't find task for "+p.getParticipantID()+"..."+p.getUsername());
        }
        
        
        
        
    }


   
    public void changeJProgressBarforApparentSpeakerChange(float percentage, long absoluteTimeLeft){
        Color progressBarColor = Color.GREEN;
        if(percentage<50) progressBarColor = Color.ORANGE;
        if(percentage<25) progressBarColor = Color.RED;
        Vector pss = (Vector)c.getParticipants().getAllParticipants();
        for(int i=0;i<pss.size();i++){
            Participant p = (Participant)pss.elementAt(i);
            String timeMessage = "";
            if(absoluteTimeLeft>60000) timeMessage = ((long)absoluteTimeLeft/60000)+" minutes";
            if(absoluteTimeLeft<=60000) timeMessage = ((long)absoluteTimeLeft/1000)+" seconds";
            
            c.changeJProgressBar(p, "ID1", "Time till speaker change: "+timeMessage, progressBarColor, (int)percentage);
        }
        String timeMessage = "";
        if(absoluteTimeLeft>60000) timeMessage = ((long)absoluteTimeLeft/60000)+" minutes";
        if(absoluteTimeLeft<=60000) timeMessage = ((long)absoluteTimeLeft/1000)+" seconds";
        at1.jcrs.updateJProgressBarAppearance("Time till speaker change: "+timeMessage, (int)percentage);
        at2.jcrs.updateJProgressBarAppearance("Time till speaker change: "+timeMessage, (int)percentage);
        jsdm.updateJProgressBarAppearance("Time till speaker change: "+timeMessage, (int)percentage);
    }
   
    
    
    public int getIDXState(){
        return this.sdmt6.getIDX();
    }
    
    public void nextState(){
        Vector pss = (Vector)c.getParticipants().getAllParticipants();

       
        
        
        sendSpoofRecalculatingMessagesFollowedByNewWordSet("Recalculating/resynching index...please wait") ;
        sdm.goNextStage(sdmt6.getIDX());
        at1.nextState_assignNEWSPEAKERS___SWAPPING_OR_VS_AND_MOVES(this.interventionSetSize,sdm.getPhysicalConvo1()[0], sdm.getPhysicalConvo1()[1]);    
        at2.nextState_assignNEWSPEAKERS___SWAPPING_OR_VS_AND_MOVES(this.interventionSetSize,sdm.getPhysicalConvo2()[0], sdm.getPhysicalConvo2()[1]);      
        this.jsdm.setStage(sdmt6.getIDX()-1, this.sdm.getDescription());    
        
    }
    
    public void calculateWhetherToDoRandomSpoofMessage(){
        if(!this.sdm.getDescription().equalsIgnoreCase("PRACTICEPHASE"))return;
        long currTime = new Date().getTime();
        if(!(currTime>TimeOfNextSpoof))return;
       
        this.sendSpoofRecalculatingMessagesFollowedByNewWordSet("Recalculating/resynching index...please wait");
        TimeOfNextSpoof = new Date().getTime() + spoofDuringPracticePhaseMinimumInterSpoofDistance + 
                r.nextInt((int)(spoofDuringPracticePhaseMaximumInterSpoofDistance-spoofDuringPracticePhaseMinimumInterSpoofDistance));  
    }
    
    
    
    private synchronized void sendSpoofRecalculatingMessagesFollowedByNewWordSet(String text){
         long durationOfRecalculatingDisplay =  r.nextInt(((int)(maximumSpoofDisplayDurationOfSpoofMessage-minimumDisplayDurationOfSpoofMessage)))+(int)minimumDisplayDurationOfSpoofMessage;
        
        Vector pss = (Vector)c.getParticipants().getAllParticipants();
        for(int i=0;i<pss.size();i++){
            Participant p = (Participant)pss.elementAt(i);
            c.sendAndEnableLabelDisplayToALLStatusWindowsOfParticipant(p, "please wait", true, false, this.getDescriptionForP(p));
            c.changeWebpageTextAndColour(p, "ID1", text, "white", "black");
        }
        try{
           System.err.println("SLEEPING FOR: "+durationOfRecalculatingDisplay);
           Conversation.printWSln("Main", "SLEEPING FOR: "+durationOfRecalculatingDisplay);
           Thread.sleep(durationOfRecalculatingDisplay);
           
        }catch(Exception e){
            e.printStackTrace();
        }
        for(int i=0;i<pss.size();i++){
            Participant p = (Participant)pss.elementAt(i);
            c.sendAndEnableLabelDisplayToALLStatusWindowsOfParticipant(p, "Status: OK", false, true,this.getDescriptionForP(p));     
        }
        
        at1.nextSet("***New set of words","SpoofERRORcausingRESTART");
        at2.nextSet("***New set of words","SpoofERRORcausingRESTART");
    }
    
   
    
   
   
    
   
   public synchronized void changePHYSICAL(String s){
       sdm.goStagePHYSICAL(s);
   }
    public void changeAPPARENT(String s){
       sdm.goStageAPPARENT(s);
   }
   
    
    
    @Override
    public synchronized void processLoop(){
            // c.resetFromIsTypingtoNormalChatAllAllowedParticipants(super.getIsTypingTimeOut());
    }





    boolean queryaction = true;
    
    @Override
    public void processChatText(Participant sender,MessageChatTextFromClient mct){       
           Debug.printDBG("RECEIVINGFROM "+sender.getUsername()+"\n");  
        
        
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
           if(atTask!=null){
               atTask.jcrs.appendChatText(sender.getUsername()+": "+mct.getText());
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
             
               
               
              Participant pRecipient = sdm.getPhysicalPartner(sender);
             // c.relayTurnToAllOtherParticipants(sender,mct, description);
              //c.sendLabelDisplayToAllowedParticipantsFromApparentOrigin(sender,"Status: OK",false);

              Participant fakeParticipant = sdm.getApparentPartner(pRecipient);
              //c.sendArtificialTurnFromApparentOriginToRecipient(fakeParticipant, pRecipient, mct.getText());
              
              c.relayTurnToParticipantSpoofingOriginOfTurn(sender, fakeParticipant, pRecipient, mct, description);
              c.sendLabelDisplayAndEnableToParticipantInOwnStatusWindow(pRecipient, "Status:OK", false, true);
              
             
              
              
           }
           
           
           
          
           //System.exit(-3);
    }



    
    public String getDescriptionForP(Participant p){
        String description = "";
        if(at1.a==p||at1.b==p){
                    description = at1.getDyadDescription()+"_"+sdm.getDescription();
                }
                else if(at2.a==p||at2.b==p){
                    description = at2.getDyadDescription()+"_"+sdm.getDescription();
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
        return null;
    }
    


   // public boolean performIntervention = false;
    public boolean isinCRSubdialogue = false;
    public boolean onlyForMostRecent = false;
    public Participant targetRecipient = null;

    public void setOnlyForMostRecent(boolean onlyForMostRecent) {
        this.onlyForMostRecent = onlyForMostRecent;
    }

    //public void setPerformIntervention(boolean performIntervention) {
      //  this.performIntervention = performIntervention;
    //}

    public void setTargetRecipient(Participant targetRecipient) {
        this.targetRecipient = targetRecipient;
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
        
        Vector pss = (Vector)c.getParticipants().getAllParticipants();
        for(int i=0;i<pss.size();i++){
            Participant p = (Participant)pss.elementAt(i);
            //
        }
        return false;
    }
      public static String[] acceptableID = {
          "111111", 
          "222222",
          "333333",
          "444444",
          "555555",
          "666666",
          "777777",
          "888888", 
          "AAAAAA",
          "BBBBBB", 
          "CCCCCC",
          "DDDDDD",
          "EEEEEE",
          "FFFFFF",
          "GGGGGG",
          "HHHHHH",
          "IIIIII",
          "JJJJJJ",
          "KKKKKK",
          "LLLLLL",
          "MMMMMM",
          "NNNNNN",
          "OOOOOO",
          "PPPPPP",
          "QQQQQQ",
          "RRRRRR",
          "SSSSSS",
          "TTTTTT",
          "UUUUUU",
          "VVVVVV",
          "WWWWWW",
          "XXXXXX",
          "YYYYYY",
          "ZZZZZZ",
          "1111A",
       "1111B",
        
       "2222A",
       "2222B",
        
       "3333A",
       "3333B",
        
       "4444A",
       "4444B",
       
       "5555A",
       "5555B",
        
       "6666A",
       "6666B",
     
       "7777A",
       "7777B",
        
       "8888A",
       "8888B",
         
       "9999A",
       "9999B",
       
       "0000A",
       "0000B",
       
       "XXXXA",
       "XXXXB",
       
       "YYYYA",
       "YYYYB"
      
      };

      
      
      public void ifCrashedSetTransitions(String sdsds){
          this.sdm.setTransitions(sdsds);
      }
      
      public void swapMovesORvsAND(){ //THIS METHOD REACHES INTO THE MOVESFACTORY TO SWAP THE WORDS OR VS. AND
          at1.getMoves().getMovesFactory().swapMovesORvsAND__MustBeCalledAfterSwappingPhysicalAndApparentPArtners();
          at2.getMoves().getMovesFactory().swapMovesORvsAND__MustBeCalledAfterSwappingPhysicalAndApparentPArtners();
      }
      
      
      public void updateJProgressBarPhysical(String timeMessage, int value){
          jsdm.updateJProgressBarPhysical(timeMessage, value);
      }

    public void initializeGUIWITHSTATES(Vector v) {
        this.jsdm.initializeWITHSTATES(v);
    }
      
      
      
}