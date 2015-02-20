package diet.server.ConversationController;
import diet.message.*;
import diet.parameters.*;
import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.Participant;
import diet.server.Participants;
import diet.task.collabMinitaskProceduralComms.*;
import diet.task.stimuliset.ConfidenceTaskController;
import diet.task.stimuliset.ConfidenceTaskControllerSequence;
import diet.task.stimuliset.ConfidenceTaskControllerSequenceSet;
import diet.task.stimuliset.JConfidenceTaskControllerInitialFrame4WAY;
import java.awt.Color;
import java.io.File;
import java.util.Date;
import java.util.Vector;



//be able to load whicheveers samedif dif same sequence required
//be able to have following dialogue without it crashing (to pave way for 8 participant chat)



public class CCCONFIDENCE4WAY_ORIGINAL extends CCCONFIDENCE implements JSDM_4WAYConversationControllerINTERFACE{
   
    ConfidenceTaskControllerSequenceSet ctcss= new ConfidenceTaskControllerSequenceSet();
    //ConfidenceTaskControllerSequenceSet ctcss= new ConfidenceTaskControllerSequenceSet(new File(System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"confidence"+File.separator+"randomizedsequence.txt"));
    ConfidenceTaskController ctc1 = new ConfidenceTaskController(this,ctcss.ctcseq1,"ctc1","TRAINING1",99999999);
    ConfidenceTaskController ctc2 = new ConfidenceTaskController(this,ctcss.ctcseq1,"ctc2","TRAINING2",99999999);
    
    
    SameDifferenceManager sdm;
    public SameDifferencemanagerTime sdmt;
    
    public JSDMGUIINTERFACE jsdm = new JSDM(this);
    
    long practiceTime =  (1000*60*6);//  (1000*60*26)
    long condnTime=      (1000*60*6);//  (1000*60*6)
    
    
     
    long minimumDisplayDurationOfSpoofMessage = 4000;
    long maximumSpoofDisplayDurationOfSpoofMessage = 6000;
    
    
    
   
    long spoofDuringPracticePhaseMaximumInterSpoofDistance = (1000 * 60 * 8);// (1000 * 60 * 8);
    long spoofDuringPracticePhaseMinimumInterSpoofDistance  =(1000 * 60 * 3);// (1000 * 60 * 4);
    long timeOfNextSpoof = new Date().getTime() + spoofDuringPracticePhaseMinimumInterSpoofDistance + 
                r.nextInt((int)(spoofDuringPracticePhaseMaximumInterSpoofDistance-spoofDuringPracticePhaseMinimumInterSpoofDistance));
        
    
    boolean  addConditionTimetoLastCondition= false;
    
    String[] apparentPartnerChanges;
    
    //int numberOfCorrectBeforeUsingAND;
    //int numberOfCorrectBeforeUsingXOR;
    
    //int numberOfCorrectBeforeUsingADDITIONALMOVE;
    //long additionalMoveProbability       = 4;
    //long additionalMoveMinimumListLengthForGeneration = 4; 
    
    //int[] maxSize = { 1,2,3,4,5,6,7,8,9,10};     
    
    //int interventionSetSize = 4;

    String[][] tP = {{"notset","notset"}, 
                     {"notset","notset"},
                     {"notset","notset"},
                     {"notset","notset"}};
    
    
    String choice1b = "SamePhysical/DifferentApparent, DifferentPhysical/SameApparent, DifferentPhysical/DifferentApparent, SamePhysical/SameApparent";
    String choice2b = "SamePhysical/DifferentApparent, SamePhysical/SameApparent, DifferentPhysical/DifferentApparent, DifferentPhysical/SameApparent";
    String choice3b = "DifferentPhysical/DifferentApparent, DifferentPhysical/SameApparent, SamePhysical/DifferentApparent, SamePhysical/SameApparent";
    String choice4b = "DifferentPhysical/DifferentApparent, SamePhysical/SameApparent, SamePhysical/DifferentApparent, DifferentPhysical/SameApparent";
    
    String[][] tPChoice1b = {{"same","diff"}, 
                            {"diff","same"},
                            {"diff","diff"},
                            {"same","same"}};
    String[][] tPChoice2b = {{"same","diff"}, 
                            {"same","same"},
                            {"diff","diff"},
                            {"diff","same"}};
    String[][] tPChoice3b = {{"diff","diff"}, 
                            {"diff","same"},
                            {"same","diff"},
                            {"same","same"}};
    String[][] tPChoice4b = {{"diff","diff"}, 
                            {"same","same"},
                            {"same","diff"},
                            {"diff","same"}};   
    
    
    String choice1 = "SamePhysical/SameApparent, SamePhysical/DifferentApparent, DifferentPhysical/SameApparent, DifferentPhysical/DifferentApparent";
    String choice2 = "DifferentPhysical/SameApparent, SamePhysical/DifferentApparent, SamePhysical/SameApparent, DifferentPhysical/DifferentApparent";
    String choice3 = "SamePhysical/SameApparent, DifferentPhysical/DifferentApparent, DifferentPhysical/SameApparent, SamePhysical/DifferentApparent";
    String choice4 = "DifferentPhysical/SameApparent, DifferentPhysical/DifferentApparent, SamePhysical/SameApparent, SamePhysical/DifferentApparent";
    
    String[][] tPChoice1 = {{"same","same"},
                             {"same","diff"}, 
                             {"diff","same"},
                             {"diff","diff"},
                            };
    String[][] tPChoice2 = {{"diff","same"},
                             {"same","diff"}, 
                            {"same","same"},
                            {"diff","diff"},
                            };
    String[][] tPChoice3 = {{"same","same"},
                            {"diff","diff"}, 
                            {"diff","same"},
                            {"same","diff"},
                            };
    String[][] tPChoice4 = { {"diff","same"},
                            {"diff","diff"}, 
                            {"same","same"},
                            {"same","diff"},
                            };
    
    
   
    
    
    public static boolean showcCONGUI() {
        return false;
    }
    
    @Override
    public void initialize(Conversation c, ExperimentSettings expSettings) {
        super.initialize(c, expSettings);
        jsdm.setVisible(true);
        
        
        
        this.initializeConditions();
        
        
    }
    
    
    public void initializeConditions(){
        
        
        Vector v = new Vector();v.addElement(choice1);v.addElement(choice2);v.addElement(choice3);v.addElement(choice4);
        JConfidenceTaskControllerInitialFrame4WAY jctc = new JConfidenceTaskControllerInitialFrame4WAY (v);
        jctc.waitFOROK();
        String s = jctc.getSAMEDIFFTRANSTIONS();
               
        practiceTime = 1000*60* jctc.getPracticeMinutes();
        condnTime= 1000*60*jctc.getConditionMinutes();
        addConditionTimetoLastCondition = jctc.getAddExtraTimetoEnd();

       if(s.equalsIgnoreCase(choice1)){
           tP=this.tPChoice1;
       }
       else if(s.equalsIgnoreCase(choice2)){
           tP=this.tPChoice2;
       }
       else if(s.equalsIgnoreCase(choice3)){
           tP=this.tPChoice3;
       }
       else if(s.equalsIgnoreCase(choice4)){
           tP=this.tPChoice4;
       }
       else{
           System.err.println("ERRORCODE9");
           System.err.println(s);
           System.exit(-5);
       }
       
       
}

//If you're here, the return value was null/empty.

            
            
    public int getIDXState(){ ///This should not have to be called
        return -999999;
    }
    
    public void accelerateTime(long milliseconds){
        sdmt.accelerateTime(milliseconds);
    }
     public void changeAPPARENT(String s){
        //sdm.goStageAPPARENT(s);
    }
     public void changePHYSICAL(String s){
        //sdm.goStagePHYSICAL(s);
    }
    
    public void calculateWhetherToDoRandomSpoofMessage(){
        if(!this.sdm.getDescription().equalsIgnoreCase("PRACTICEPHASE"))return;
        long currTime = new Date().getTime();
        if(!(currTime>timeOfNextSpoof)){
           // System.out.println("Time till next spoof "+(timeOfNextSpoof-currTime));
            return;
        }
        
        
        this.sendSpoofRecalculatingMessagesFollowedByNewWordSet();
        timeOfNextSpoof = new Date().getTime() + spoofDuringPracticePhaseMinimumInterSpoofDistance + 
                r.nextInt((int)(spoofDuringPracticePhaseMaximumInterSpoofDistance-spoofDuringPracticePhaseMinimumInterSpoofDistance));  
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
            
            c.changeJProgressBar(p, "CHATFRAME", "Time till speaker change: "+timeMessage, progressBarColor, (int)percentage);
        }
        String timeMessage = "";
        if(absoluteTimeLeft>60000) timeMessage = ((long)absoluteTimeLeft/60000)+" minutes";
        if(absoluteTimeLeft<=60000) timeMessage = ((long)absoluteTimeLeft/1000)+" seconds";
        //at1.jcrs.updateJProgressBarAppearance("Time till speaker change: "+timeMessage, (int)percentage);
        //at2.jcrs.updateJProgressBarAppearance("Time till speaker change: "+timeMessage, (int)percentage);
        jsdm.updateJProgressBarAppearance("Time till speaker change: "+timeMessage, (int)percentage);
     }
    public void updateJProgressBarPhysical(String timeMessage, int value){
        jsdm.updateJProgressBarPhysical(timeMessage, value);
    }
    
    int currentState =0;
    
    public void nextState(){
        Vector pss = (Vector)c.getParticipants().getAllParticipants();

        sendSpoofRecalculatingMessagesFollowedByNewWordSet() ;
        sdm.goNextStage();
        
        currentState++;
        ConfidenceTaskControllerSequence ctcs=ctcss.ctcseq1;
        if(currentState==1)ctcs=ctcss.ctcseq2;
        else if(currentState==2)ctcs=ctcss.ctcseq3;
        else if(currentState==3)ctcs=ctcss.ctcseq4;
        else if(currentState==4)ctcs=ctcss.ctcseq5;
        else{
            Conversation.printWSln("Main", "ERROR WITH SEQUENCESETS..");   
            Conversation.printWSlnLog("Main", "ERROR WITH SEQUENCESETS..");  
            Conversation.saveErr("ERROR WITH SEQUENCESETS");
        }
        
        ctc1.nextState_assignNEWSPEAKERS(sdm.getPhysicalConvo1()[0], sdm.getPhysicalConvo1()[1],ctcs);    
        ctc2.nextState_assignNEWSPEAKERS(sdm.getPhysicalConvo2()[0], sdm.getPhysicalConvo2()[1],ctcs);      
        this.jsdm.setStage(sdm.getStageNumber(), this.sdm.getDescription());    
    }
    
    private synchronized void sendSpoofRecalculatingMessagesFollowedByNewWordSet(){
        long durationOfRecalculatingDisplay =  r.nextInt(((int)(maximumSpoofDisplayDurationOfSpoofMessage-minimumDisplayDurationOfSpoofMessage)))+(int)minimumDisplayDurationOfSpoofMessage;
        
        
        Vector pss = (Vector)c.getParticipants().getAllParticipants();
        for(int i=0;i<pss.size();i++){
            Participant p = (Participant)pss.elementAt(i);
            c.sendAndEnableLabelDisplayToALLStatusWindowsOfParticipant(p, "please wait", true, false, this.getDescriptionForP(p));
            this.ctc1.displayPleaseWait();
            this.ctc2.displayPleaseWait();
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
        ctc1.gotoSTATE1_SendNewStimulusSet();
        ctc2.gotoSTATE1_SendNewStimulusSet();
        
        
    }
    
    
    public String  getDescriptionForP(Participant p){
        String description = "SDM_is_unset";
        if(sdm!=null)description = sdm.getDescription(); 
        if(p!=null&&ctc1.participantIsInConversation(p))description = description+ ctc1.getID();
        if(p!=null&&ctc2.participantIsInConversation(p))description = description+ ctc2.getID();
        return description;
    }
    
    
    
    
        
    @Override
    public void processLoop(){
             c.resetFromIsTypingtoNormalChatAllAllowedParticipants(super.getIsTypingTimeOut());      
    }
    
   
    public static  ExperimentSettings getDefaultSettings() {
       
        Vector v = new Vector();
        StringParameter sp;
        StringParameterFixed spf;
        IntParameter ip;
        sp = new StringParameter("Experiment ID","CCONFIDENCE4WAY");
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
         StringParameterFixed otherTextColour1  = new StringParameterFixed("Text colour other 1",spv,"YELLOW");      v.addElement(otherTextColour1);
         StringParameterFixed otherTextColour2  = new StringParameterFixed("Text colour other 2",spv,"BLUE");     v.addElement(otherTextColour2);
         StringParameterFixed otherTextColour3  = new StringParameterFixed("Text colour other 3",spv,"GREEN");    v.addElement(otherTextColour3);
         StringParameterFixed otherTextColour4  = new StringParameterFixed("Text colour other 4",spv,"CYAN");     v.addElement(otherTextColour4);
         StringParameterFixed otherTextColour5  = new StringParameterFixed("Text colour other 5",spv,"YELLOW");   v.addElement(otherTextColour5);
                        
         IntParameter ipr;
         ipr = new IntParameter("Width of main window",(Integer)400);         v.addElement(ipr);
         ipr = new IntParameter("Height of main window",(Integer)150);        v.addElement(ipr);
         ipr = new IntParameter("Width of text entry window",(Integer)120);   v.addElement(ipr);
         ipr = new IntParameter("Height of text entry window",(Integer)50);  v.addElement(ipr);
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
  
    
    
     
    
    
        public ConfidenceTaskController getTaskController(Participant p){
            if(ctc1.participantIsInConversation(p))return ctc1;
            if(ctc2.participantIsInConversation(p))return ctc2;
            return null;
        }
    
    
    @Override
    public void processChatText(Participant sender,MessageChatTextFromClient mct){    
        if(c.getParticipants().getAllParticipants().size()<4){
               c.sendArtificialTurnToRecipient(sender, "PLEASE WAIT TILL EVERYONE HAS LOGGED IN! THANKS", 0);
               return;
        }   
        
        
        
        
        
        
        ConfidenceTaskController ctcOfP = null; 
        
           if(ctc1.participantIsInConversation(sender)) {
               ctcOfP = ctc1;
           }
           else if(ctc2.participantIsInConversation(sender))  {
               ctcOfP = ctc2;
           }
           else{
               Conversation.printWSln("Main", "ERROR IT CAN'T FIND THE CONFIDENCETASK");
                System.err.println("ERROR IT CAN'T FIND THE CONFIDENCETASK");
                Conversation.saveErr("PROBLEM - IT CAN'T FIND THE CONFIDENCE TASK");
           }
        
           String description =this.getDescriptionForP(sender);
           pTurnsElapsed.setValue(((Integer)pTurnsElapsed.getValue())+1);
           super.expSettings.generateParameterEvent(pTurnsElapsed);          
           if(mct.getText().startsWith("/")){
               c.setNewTurnBeingConstructedNotRelayingOldTurnAddingOldTurnToHistory(sender, mct,description);
               ctcOfP.processChatText(sender, mct);  
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
           
    }
    
    @Override
    public void processKeyPress(Participant sender,MessageKeypressed mkp){
        c.saveClientKeypressToFile(sender, mkp, this.getDescriptionForP(sender));
        
    }

    public void processClientEvent(Participant origin, MessageClientEvent mce){
         c.saveDataToConversationHistory(origin, "", mce.getTimeOnClientOfCreation().getTime(),this.getDescriptionForP(origin)+"ClientEvent", mce.getNameOfEvent());
         String s = mce.getNameOfEvent();
         
         
         
    }
   
   
    
            
            
            
    
    @Override
    public void processWYSIWYGTextInserted(Participant sender,MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp){
          //c.informIsTypingToAllowedParticipants(sender);
          
          Participant pRecipient = sdm.getPhysicalPartner(sender);
             // c.relayTurnToAllOtherParticipants(sender,mct, description);
              //c.sendLabelDisplayToAllowedParticipantsFromApparentOrigin(sender,"Status: OK",false);

           Participant fakeParticipant = sdm.getApparentPartner(pRecipient);
           //c.sendArtificialTurnFromApparentOriginToRecipient(fakeParticipant, pRecipient, mct.getText());
              

          Participant pPartner = this.sdm.getPhysicalPartner(sender);
          c.informParticipantBthatParticipantAIsTyping(fakeParticipant, pRecipient);
          
    }
    
    
    public void setSTime(long stime){
        this.ctc1.setSTime(stime);
        this.ctc2.setSTime(stime);
    }
    public void setFTime(long fixationtime ){
        this.ctc1.setFTime(fixationtime);
        this.ctc2.setFTime(fixationtime);
    }
     public void setBTime(long backgroundtime ){
        this.ctc1.setBTime(backgroundtime);
        this.ctc2.setBTime(backgroundtime);
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
    
    
    
    
    
    
    
    public void initializeGUIWITHSTATES(Vector v){
        jsdm.initializeWITHSTATES(v);
    }
     
    
    
    
    
    @Override
    public void participantJoinedConversation(Participant p) {
        super.participantJoinedConversation(p);
        ConfidenceTaskController ctcForP = this.getTaskController(p);
        //ctcForP.participantJoinedConversation(p);
        
       
        
        if(p.getParticipantID().equalsIgnoreCase("111111"))ctc1.participantJoinedConversation(p);
        if(p.getParticipantID().equalsIgnoreCase("222222"))ctc1.participantJoinedConversation(p);
        if(p.getParticipantID().equalsIgnoreCase("333333"))ctc2.participantJoinedConversation(p);
        if(p.getParticipantID().equalsIgnoreCase("444444"))ctc2.participantJoinedConversation(p);
                

        if(c.getParticipants().getAllParticipants().size()>3){
           CustomDialog.showDialog("ALL 4 PARTICIPANTS HAVE LOGGED ON\n"
                   + "PRESS OK TO START THE EXPERIMENT");
           Participants ps = c.getParticipants();
           Vector allP = c.getParticipants().getAllParticipants();
           //sdm = new SameDifferenceManager( this, ps.findParticipantWithEmail("111111"),  ps.findParticipantWithEmail("222222"),  ps.findParticipantWithEmail("333333"), ps.findParticipantWithEmail("444444") );      
           
           
           
            //String[][] tP2 = {{"same","diff"},{"diff","same"},{"same","same"},  {"diff","diff"},{"same","same"}};
            sdm = new SameDifferenceManager( this, tP, ps.findParticipantWithEmail("111111"),  ps.findParticipantWithEmail("222222"),  ps.findParticipantWithEmail("333333"), ps.findParticipantWithEmail("444444") );      
               
            this.apparentPartnerChanges=sdm.getApparentPartnerChanges();
            this.sdmt = new SameDifferencemanagerTime(this,apparentPartnerChanges,practiceTime,condnTime, this.addConditionTimetoLastCondition);
            ctc1.startExperiment();
            ctc2.startExperiment();
        }
        
        
        
        
        
        
        
        
        
    }

    @Override
    public void participantRejoinedConversation(Participant p) {
        super.participantRejoinedConversation(p);
         ConfidenceTaskController ctcForP = this.getTaskController(p);
        ctcForP.participantRejoinedConversation(p);
    }
    
   
   


    
    
   

   

}
