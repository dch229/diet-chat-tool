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




public class CCGROOP3SEQ8_SAMEDIFF extends DefaultConversationController implements JSDM_4WAYConversationControllerINTERFACE{ //{extends CCGROOP3SEQ4{
   
    AlphabeticalTaskWithInsertedWords at1 = new AlphabeticalTaskWithInsertedWords(1,this);
    AlphabeticalTaskWithInsertedWords at2 = new AlphabeticalTaskWithInsertedWords(2,this);;
    AlphabeticalTaskWithInsertedWords at3 = new AlphabeticalTaskWithInsertedWords(3,this);;
    AlphabeticalTaskWithInsertedWords at4 = new AlphabeticalTaskWithInsertedWords(4,this);;
    
    
    SameDifferenceManagerONLY2PARTNERS_INTENDEDFOR8PARTICIPANTS sdm1;
    SameDifferenceManagerONLY2PARTNERS_INTENDEDFOR8PARTICIPANTS sdm2;
    
    public SameDifferencemanagerTime6STEPS sdmt1;
     public SameDifferencemanagerTime6STEPS sdmt2;
    
    public JSDM jsdm1 = new JSDM(this);
    public JSDM jsdm2 = new JSDM(this);
    
    long practiceTime =  (1000*60*26);//  (1000*60*26)
    long condnTime=      (1000*60*12);//  (1000*60*6)
    long postcondPreBWTime = (1000*60*20);
    long bwTime = (1000*60*1000);
     
    long minimumDisplayDurationOfSpoofMessage = 4000;
    long maximumSpoofDisplayDurationOfSpoofMessage = 6000;
    
    
    
   
    long spoofDuringPracticePhaseMaximumInterSpoofDistance = (1000 * 60 * 8);// (1000 * 60 * 8);
    long spoofDuringPracticePhaseMinimumInterSpoofDistance  =(1000 * 60 * 4);// (1000 * 60 * 4);
    long TimeOfNextSpoof = new Date().getTime() + spoofDuringPracticePhaseMinimumInterSpoofDistance + 
                r.nextInt((int)(spoofDuringPracticePhaseMaximumInterSpoofDistance-spoofDuringPracticePhaseMinimumInterSpoofDistance));
        
    
    String[] apparentPartnerChanges1;
    String[] apparentPartnerChanges2;
    
    int numberOfCorrectBeforeUsingAND;
    int numberOfCorrectBeforeUsingXOR;
    
    int numberOfCorrectBeforeUsingADDITIONALMOVE;
    long additionalMoveProbability       = 4;
    long additionalMoveMinimumListLengthForGeneration = 4; 
    
    int[] maxSize = { 1,2,3,4,5,6,7,8,9,10};     
    
    int interventionSetSize = 4;
    
    
    
    public void accelerateTime(long milliseconds){
        sdmt1.accelerateTime(milliseconds);
        sdmt2.accelerateTime(milliseconds);
    }
    
    
    @Override
    public void initialize(Conversation c, ExperimentSettings expSettings) {
        super.initialize(c, expSettings);
        jsdm1.setVisible(true);
        jsdm2.setVisible(true);
 
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
       
        c.displayNEWWebpage(p, "ID1", "Instructions", "", 500, 300, false,false);
        c.changeJProgressBar(p, "ID1", "Time till speaker change", Color.GREEN, 100);
        c.changeWebpageTextAndColour(p, "ID1", "Please wait for other participant to log in", "white", "black");
        if(c.getParticipants().getAllParticipants().size()>1){
           // at.startTask ((Participant)c.getParticipants().getAllParticipants().elementAt(0),(Participant)c.getParticipants().getAllParticipants().elementAt(1));
           
        }
        if(c.getParticipants().getAllParticipants().size()>7){
          
            String[][] tP = {{"same","same"},
                                  {"same","diff"},
                                  {"diff","same"},
                                  {"diff","diff"}
           };
            
            
           Participants ps = c.getParticipants();
           Vector allP = c.getParticipants().getAllParticipants();
           sdm1 = new SameDifferenceManagerONLY2PARTNERS_INTENDEDFOR8PARTICIPANTS(jsdm1, this,tP, ps.findParticipantWithEmail("111111"),  ps.findParticipantWithEmail("222222"),  ps.findParticipantWithEmail("333333"), ps.findParticipantWithEmail("444444") );
           sdm2 = new SameDifferenceManagerONLY2PARTNERS_INTENDEDFOR8PARTICIPANTS( jsdm2,this,tP, ps.findParticipantWithEmail("555555"),  ps.findParticipantWithEmail("666666"),  ps.findParticipantWithEmail("777777"), ps.findParticipantWithEmail("888888") );
           at1.startTask (sdm1.getPhysicalConvo1()[0], sdm1.getPhysicalConvo1()[1]);           
           at2.startTask (sdm1.getPhysicalConvo2()[0], sdm1.getPhysicalConvo2()[1]); 
           at3.startTask (sdm2.getPhysicalConvo1()[0], sdm2.getPhysicalConvo1()[1]); 
           at4.startTask (sdm2.getPhysicalConvo2()[0], sdm2.getPhysicalConvo2()[1]); 
           
           
           
           this.apparentPartnerChanges1=sdm1.getApparentPartnerChanges();
           this.apparentPartnerChanges2=sdm2.getApparentPartnerChanges();
           this.sdmt1 = new SameDifferencemanagerTime6STEPS(jsdm1,this,apparentPartnerChanges1,practiceTime,condnTime,false, sdm1, true, postcondPreBWTime, bwTime);
           this.sdmt2 = new SameDifferencemanagerTime6STEPS(jsdm2,this,apparentPartnerChanges2,practiceTime,condnTime,false, sdm2,false,postcondPreBWTime, bwTime);
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
        Conversation.printWSln("Main", "THIS REALLY SHOULDNT BE CALLED");
       // System.exit(-9);
    }
    
    
    public void changeJProgressBarforApparentSpeakerChange(float percentage, long absoluteTimeLeft, SameDifferenceManagerONLY2PARTNERS_INTENDEDFOR8PARTICIPANTS sdm){
        Color progressBarColor = Color.GREEN;
        if(percentage<50) progressBarColor = Color.ORANGE;
        if(percentage<25) progressBarColor = Color.RED;
         String timeMessage = "";
            if(absoluteTimeLeft>60000) timeMessage = ((long)absoluteTimeLeft/60000)+" minutes";
            if(absoluteTimeLeft<=60000) timeMessage = ((long)absoluteTimeLeft/1000)+" seconds";
        c.changeJProgressBar(sdm.a, "ID1", "Time till speaker change: "+timeMessage, progressBarColor, (int)percentage);
        c.changeJProgressBar(sdm.b, "ID1", "Time till speaker change: "+timeMessage, progressBarColor, (int)percentage);
        c.changeJProgressBar(sdm.c, "ID1", "Time till speaker change: "+timeMessage, progressBarColor, (int)percentage);
        c.changeJProgressBar(sdm.d, "ID1", "Time till speaker change: "+timeMessage, progressBarColor, (int)percentage);
        
        
        if(absoluteTimeLeft>60000) timeMessage = ((long)absoluteTimeLeft/60000)+" minutes";
        if(absoluteTimeLeft<=60000) timeMessage = ((long)absoluteTimeLeft/1000)+" seconds";
       
        if(sdm==sdm1){
              jsdm1.updateJProgressBarAppearance("Time till speaker change: "+timeMessage, (int)percentage);
              at1.jcrs.updateJProgressBarAppearance("Time till speaker change: "+timeMessage, (int)percentage);
              at2.jcrs.updateJProgressBarAppearance("Time till speaker change: "+timeMessage, (int)percentage);
        }
        else{
             jsdm2.updateJProgressBarAppearance("Time till speaker change: "+timeMessage, (int)percentage);
             at3.jcrs.updateJProgressBarAppearance("Time till speaker change: "+timeMessage, (int)percentage);
             at4.jcrs.updateJProgressBarAppearance("Time till speaker change: "+timeMessage, (int)percentage);
        }
        
    }
    
    
    
    
    /*
     * 
     *  
     * BETWEEN - WITHIN   
     * (P1 -- p4)
     * (p5 -- p8)
     * 
     * 
     * 
     * 
     * 
     */
    
    
    
    
   public int getIDXState(){
        return this.sdmt1.getIDX();
    }
    
    public void nextState(){
        

        int n = sdmt1.getIDX();
        
        
        sdm1.goNextStage(n-1);
        sdm2.goNextStage(n-1);
        sendSpoofRecalculatingMessagesFollowedByNewWordSet("Recalculating/resynching index...please wait") ;

        sdm1.getDescription();
        System.out.println("THE STAETE "+n+"....."+ sdm1.getDescription());
        //System.exit(-333);
        
        
        at1.nextState_assignNEWSPEAKERS___SWAPPING_OR_VS_AND_MOVES(this.interventionSetSize,sdm1.getPhysicalConvo1()[0], sdm1.getPhysicalConvo1()[1]);    
        at2.nextState_assignNEWSPEAKERS___SWAPPING_OR_VS_AND_MOVES(this.interventionSetSize,sdm1.getPhysicalConvo2()[0], sdm1.getPhysicalConvo2()[1]);   
        at3.nextState_assignNEWSPEAKERS___SWAPPING_OR_VS_AND_MOVES(this.interventionSetSize,sdm2.getPhysicalConvo1()[0], sdm2.getPhysicalConvo1()[1]);    
        at4.nextState_assignNEWSPEAKERS___SWAPPING_OR_VS_AND_MOVES(this.interventionSetSize,sdm2.getPhysicalConvo2()[0], sdm2.getPhysicalConvo2()[1]);  
       
        
        this.jsdm1.setStage(sdmt1.getIDX()-1, this.sdm1.getDescription());
        this.jsdm2.setStage(sdmt1.getIDX()-1, this.sdm2.getDescription());
        
    }
    
    public void calculateWhetherToDoRandomSpoofMessage(){
        if(!this.sdm1.getDescription().equalsIgnoreCase("PRACTICEPHASE"))return;
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
        at3.nextSet("***New set of words","SpoofERRORcausingRESTART");
        at4.nextSet("***New set of words","SpoofERRORcausingRESTART");
    }
    
   
    
   
   
    
   
   public synchronized void changePHYSICAL(String s){
       sdm1.goStagePHYSICAL(s);
       sdm2.goStagePHYSICAL(s);
   }
    public void changeAPPARENT(String s){
       sdm1.goStageAPPARENT(s);
       sdm2.goStageAPPARENT(s);
   }
   
    
    
    @Override
    public synchronized void processLoop(){
            // c.resetFromIsTypingtoNormalChatAllAllowedParticipants(super.getIsTypingTimeOut());
    }





    boolean queryaction = true;
    
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
             
              AlphabeticalTaskWithInsertedWords atwi = this.getATForP(sender);
              SameDifferenceManagerONLY2PARTNERS_INTENDEDFOR8PARTICIPANTS sdm2O2 = null;
              if (atwi==at1 || atwi ==at2){
                  sdm2O2 = sdm1;
              }
              else if (atwi==at3 || atwi ==at4){
                  sdm2O2 = sdm2;
              }
              
              if(sdm2O2==null){
                  System.out.println("EXCEPTION/ERROR CANT FIND IIIT IN E2");
                  Conversation.printWSln("Main", "NOT GOOD ERROR HERE");
              } 
               
              Participant pRecipient = sdm2O2.getPhysicalPartner(sender);
              if(pRecipient==null)pRecipient = sdm2O2.getPhysicalPartner(sender);
              if(pRecipient==null){
                  Conversation.printWSln("Main", "IT SHOULD BE ABLE TO FIND THE RECIPIENT!!!!!");
                  System.err.println("ERROR/EXCEPTION ....IT SHOULD BE ABLE TO FIND THE RECIPIENT");
              }
              
              
             // c.relayTurnToAllOtherParticipants(sender,mct, description);
              //c.sendLabelDisplayToAllowedParticipantsFromApparentOrigin(sender,"Status: OK",false);

              Participant fakeParticipant = sdm2O2.getApparentPartner(pRecipient);
              //c.sendArtificialTurnFromApparentOriginToRecipient(fakeParticipant, pRecipient, mct.getText());
              
              c.relayTurnToParticipantSpoofingOriginOfTurn(sender, fakeParticipant, pRecipient, mct, description);
              c.sendLabelDisplayAndEnableToParticipantInOwnStatusWindow(pRecipient, "Status:OK", false, true);
              
             
              
              
           }
           
           
           
          
           //System.exit(-3);
    }



    
    public String getDescriptionForP(Participant p){
        String description = "";
        if(at1.a==p||at1.b==p){
                    description = at1.getDyadDescription()+"_"+sdm1.getDescription();
                }
                else if(at2.a==p||at2.b==p){
                    description = at2.getDyadDescription()+"_"+sdm1.getDescription();
                }
        else if(at3.a==p||at3.b==p){
                    description = at3.getDyadDescription()+"_"+sdm2.getDescription();
                }
        else if(at4.a==p||at4.b==p){
                    description = at4.getDyadDescription()+"_"+sdm2.getDescription();
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
          this.sdm1.setTransitions(sdsds);
      }
      public void ifCrashedSetTransitions2(String sdsds){
          this.sdm2.setTransitions(sdsds);
      }
      
      public void swapMovesORvsAND(){ //THIS METHOD REACHES INTO THE MOVESFACTORY TO SWAP THE WORDS OR VS. AND
          at1.getMoves().getMovesFactory().swapMovesORvsAND__MustBeCalledAfterSwappingPhysicalAndApparentPArtners();
          at2.getMoves().getMovesFactory().swapMovesORvsAND__MustBeCalledAfterSwappingPhysicalAndApparentPArtners();
      }
      
      
      public void updateJProgressBarPhysical(String timeMessage, int value){
          
      }

    public void initializeGUIWITHSTATES(Vector v) {
        //this.jsdm.initializeWITHSTATES(v);
    }
      
      
      
}
