package diet.server.ConversationController;
import diet.message.*;
import diet.parameters.*;
import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.ConversationController.ui.InterfaceForJCountDown;
import diet.server.ConversationController.ui.JCountDown;
import diet.server.Participant;
import diet.task.stimuliset.ConfidenceTaskController;
import diet.task.stimuliset.ConfidenceTaskControllerSequence;
import diet.task.stimuliset.ConfidenceTaskControllerSequenceSet;
import diet.task.stimuliset.JConfidenceTaskControllerInitialFrame4WAY;
import diet.utils.FilenameToolkit;
import java.awt.Color;
import java.io.File;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;



//be able to load whicheveers samedif dif same sequence required
//be able to have following dialogue without it crashing (to pave way for 8 participant chat)



public class CCCONFIDENCE4WAY_NODECEPTION extends CCCONFIDENCE implements InterfaceForJCountDown{
   
    ConfidenceTaskControllerSequenceSet ctcss= new ConfidenceTaskControllerSequenceSet();
    //ConfidenceTaskControllerSequenceSet ctcss= new ConfidenceTaskControllerSequenceSet(new File(System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"confidence"+File.separator+"randomizedsequence.txt"));
    ConfidenceTaskController ctc1 = new ConfidenceTaskController(this,ctcss.ctcseq1,"ctc1","TRAINING1",99999999);
    ConfidenceTaskController ctc2 = new ConfidenceTaskController(this,ctcss.ctcseq1,"ctc2","TRAINING2",99999999);
    
    
    Participant p1;
    Participant p2;
    Participant p3;
    Participant p4;
    
    
    //
    
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
        
    String choice = "NOTSET";
    
    String choice1 = "SamePhysical/SameApparent";
    String choice2 = "DifferentPhysical/DifferentApparent";
    
    
   
    String sMessagePostTraining1 ="Not set";
    String sMessagePostTraining2 = "Not set";
    
    
   
     JCountDown jc;
    
    public static boolean showcCONGUI() {
        return true;
    }
    
    @Override
    public void initialize(Conversation c, ExperimentSettings expSettings) {
        super.initialize(c, expSettings);
     
        this.initializeConditions();
        
        
    }
    
    
     String msgSWAP1 = "Not set";
     String msgSWAP2 = "Not set";
     String msgSAME1 = "Not set";
     String msgSAME2 = "Not set";
    
    public void initializeConditions(){
    
        String directory = System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"confidence"+File.separator+"message";
        File fSWAP1 = new File(System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"confidence"+File.separator+"message"+File.separator+"messageswap1.txt");
        File fSWAP2 = new File(System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"confidence"+File.separator+"message"+File.separator+"messageswap2.txt");
        File fSAME1 = new File(System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"confidence"+File.separator+"message"+File.separator+"messagesame1.txt");
        File fSAME2 = new File(System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"confidence"+File.separator+"message"+File.separator+"messagesame2.txt");
        
        msgSWAP1 = FilenameToolkit.getStringFromFile(fSWAP1);
        msgSWAP2 = FilenameToolkit.getStringFromFile(fSWAP2);
        msgSAME1 = FilenameToolkit.getStringFromFile(fSAME1);
        msgSAME2 = FilenameToolkit.getStringFromFile(fSAME2);
        
        Vector v = new Vector();v.addElement(choice1);v.addElement(choice2);
        JConfidenceTaskControllerInitialFrame4WAY jctc = new JConfidenceTaskControllerInitialFrame4WAY (v);
        jctc.waitFOROK();
        String s = jctc.getSAMEDIFFTRANSTIONS();
        practiceTime = 1000*60* jctc.getPracticeMinutes();
        condnTime= 1000*60*jctc.getConditionMinutes();
        addConditionTimetoLastCondition = jctc.getAddExtraTimetoEnd();

       if(s.equalsIgnoreCase(choice1)){
           choice = choice1;
       }
       else if(s.equalsIgnoreCase(choice2)){
           choice = choice2;
       }
       
       else{
           System.err.println("ERRORCODE9");
           System.err.println(s);
           System.exit(-5);
       }
        //String directory = System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"confidence"+File.separator+"message1";        
        //   File f = CustomDialog.loadFile(s);
       
       
       
      jc = new JCountDown(this, "Time till swap",practiceTime);
}

//If you're here, the return value was null/empty.

            
            
    public int getIDXState(){ ///This should not have to be called
        return -999999;
    }
    
    public void accelerateTime(long milliseconds){
        //sdmt.accelerateTime(milliseconds);
    }
     public void changeAPPARENT(String s){
        //sdm.goStageAPPARENT(s);
    }
     public void changePHYSICAL(String s){
        //sdm.goStagePHYSICAL(s);
    }

    @Override
    public void performUITriggeredEvent(String eventname, int value) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        this.nextState();
    }

    @Override
    public void performUIJProgressBarDisplayOnClient(int percentage, String text) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   
    int currentState =0;
    
    public void nextState(){
        Vector pss = (Vector)c.getParticipants().getAllParticipants();

       
       
        
        
        ConfidenceTaskControllerSequence ctcs=ctcss.ctcseq2;
       
        if(this.choice==choice1){
            displayMessageOnClients(this.msgSAME1); 
            this.displaymessageOnServer("PRESS OK TO START THE SECOND STAGE!");
            ctc1.resetToStart();
            ctc2.resetToStart();
            ctc1.nextState_assignNEWSPEAKERS_STARTINGAGAIN(p1, p2,ctcs);  
            ctc2.nextState_assignNEWSPEAKERS_STARTINGAGAIN(p3, p4,ctcs);    
            
            
        }
        else if(this.choice==choice2){
            displayMessageOnClients(this.msgSWAP1); 
            this.displaymessageOnServer("PRESS OK TO START THE SECOND STAGE!");
            ctc1.resetToStart();
            ctc2.resetToStart();
            ctc1.nextState_assignNEWSPEAKERS_STARTINGAGAIN(p1, p3,ctcs);    
            ctc2.nextState_assignNEWSPEAKERS_STARTINGAGAIN(p2, p4,ctcs);     
           
        }
        
    }
    
    
    
    public void displayMessageOnClients(String stimulisetmessage){
        String[] ack = {"ok"};
        getC().subliminalstimuliset_displayText(p1, "","backgroundorange",Color.BLACK, 20,30,0,getDescriptionForP(p1));
        getC().subliminalstimuliset_displayText(p2, "","backgroundorange",Color.BLACK, 20,30,0,getDescriptionForP(p2));
        getC().subliminalstimuliset_displayText(p3, "","backgroundorange",Color.BLACK, 20,30,0,getDescriptionForP(p3));
        getC().subliminalstimuliset_displayText(p4, "","backgroundorange",Color.BLACK, 20,30,0,getDescriptionForP(p4));
        this.getC().showPopupOnClientQueryInfo("ID", p1, stimulisetmessage, ack, "Information!", 0);
        this.getC().showPopupOnClientQueryInfo("ID", p2, stimulisetmessage, ack, "Information!", 0);
        this.getC().showPopupOnClientQueryInfo("ID", p3, stimulisetmessage, ack, "Information!", 0);
        this.getC().showPopupOnClientQueryInfo("ID", p4, stimulisetmessage, ack, "Information!", 0);
        
    }
    
    public void displaymessageOnServer(String s){
          JOptionPane.showMessageDialog(null, s,  "Message",  JOptionPane.PLAIN_MESSAGE);
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
               
               
             if(ctc1.pA==sender){
                 c.relayTurnToParticipantWithEnforcedColour(sender, ctc1.pB, mct, 1, description);
                 c.sendLabelDisplayAndEnableToParticipantInOwnStatusWindow(ctc1.pB, "Status:OK", false, true);
             }
             else if(ctc1.pB==sender){
                 c.relayTurnToParticipantWithEnforcedColour(sender, ctc1.pA, mct, 1, description);
                 c.sendLabelDisplayAndEnableToParticipantInOwnStatusWindow(ctc1.pA, "Status:OK", false, true);
             } 
             else if(ctc2.pA==sender){
                 c.relayTurnToParticipantWithEnforcedColour(sender, ctc2.pB, mct, 1, description);
                 c.sendLabelDisplayAndEnableToParticipantInOwnStatusWindow(ctc2.pB, "Status:OK", false, true);
             } 
             else if(ctc2.pB==sender){
                 c.relayTurnToParticipantWithEnforcedColour(sender, ctc2.pA, mct, 1, description);
                 c.sendLabelDisplayAndEnableToParticipantInOwnStatusWindow(ctc2.pA, "Status:OK", false, true);
             } 
             else{
                 Conversation.printWSlnLog("Main", "there is a serious error...can't find other participant "+sender.getUsername());
             }
              
     
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
          
          if(ctc1.pA==sender){
                 c.informParticipantBthatParticipantAIsTyping(sender, ctc1.pB);
             }
             else if(ctc1.pB==sender){
                 c.informParticipantBthatParticipantAIsTyping(sender, ctc1.pA);
             } 
             else if(ctc2.pA==sender){
                  c.informParticipantBthatParticipantAIsTyping(sender, ctc2.pB);
             } 
             else if(ctc2.pB==sender){
                 c.informParticipantBthatParticipantAIsTyping(sender, ctc2.pA);
             } 
             else{
                 Conversation.printWSlnLog("Main", "there is a serious error...can't find other participant "+sender.getUsername());
             }  
        
        
        
          //c.informParticipantBthatParticipantAIsTyping(fakeParticipant, pRecipient);
          
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
    
    
    
    
    
    
    
    
     
    
    
    
    
    @Override
    public void participantJoinedConversation(Participant p) {
        super.participantJoinedConversation(p);
        ConfidenceTaskController ctcForP = this.getTaskController(p);
        //ctcForP.participantJoinedConversation(p);
        
       
        
        if(p.getParticipantID().equalsIgnoreCase("111111")){
            ctc1.participantJoinedConversation(p);
            this.p1=p;
        }
        if(p.getParticipantID().equalsIgnoreCase("222222")){
            ctc1.participantJoinedConversation(p);
            this.p2=p;
        }
        if(p.getParticipantID().equalsIgnoreCase("333333")){
            ctc2.participantJoinedConversation(p);
            this.p3=p;
        }
        if(p.getParticipantID().equalsIgnoreCase("444444")){
            ctc2.participantJoinedConversation(p);
            this.p4=p;
        }
                

        if(c.getParticipants().getAllParticipants().size()>3){
           CustomDialog.showDialog("ALL 4 PARTICIPANTS HAVE LOGGED ON\n"
                   + "PRESS OK TO START THE EXPERIMENT");
            jc.startCountdown();
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
