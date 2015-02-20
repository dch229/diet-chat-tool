package diet.server.ConversationController;
import diet.message.*;
import diet.parameters.*;
import diet.server.Participant;
import diet.task.collabMinitaskProceduralComms.JSDM_4WAYConversationControllerINTERFACE;
import diet.task.stimuliset.ConfidenceTaskController;
import diet.task.stimuliset.ConfidenceTaskControllerSequenceSet;
import java.util.Vector;




public class CCCONFIDENCE2WAY_2STAGES_64_TRIALS extends CCCONFIDENCE {
   
    ConfidenceTaskControllerSequenceSet ctcss= new ConfidenceTaskControllerSequenceSet();
    ConfidenceTaskController ctc = new ConfidenceTaskController(this,ctcss.ctcseq1,"ctc","",64);
     
    public static boolean showcCONGUI() {
        return true;
    }
    
    @Override
    public void processLoop(){
             c.resetFromIsTypingtoNormalChatAllAllowedParticipants(super.getIsTypingTimeOut());      
    }

    
     public ConfidenceTaskController getTaskController(Participant p){
           return ctc;
     }
    
    
    
    
    
    @Override
    public void processClientEvent(Participant origin, MessageClientEvent mce) {
        super.processClientEvent(origin, mce);
        
        //Two kinds of event
        //Individual stimulus
        //prompt
        
        String s = mce.getNameOfEvent();
        if(s.startsWith("INDIVIDUALSTIMULUS")){
            ConfidenceTaskController pctc = this.getTaskController(origin);
            pctc.updateWithClientEvent_IndividualStimulusInfo(origin, s);
        }
        else if (s.contains("jointquestionresponder")){
             ConfidenceTaskController pctc = this.getTaskController(origin);
             pctc.updateWithClientEvent_JointStimulusInfo(origin, s);
        }
        else if (s.contains("jointquestionnonresponder")){
            ConfidenceTaskController pctc = this.getTaskController(origin);
            pctc.updateWithClientEvent_JointStimulusInfo(origin, s);
        }
        
        
        
        
        
        
        
        
    }
    
    
    //INDIVIDUALSTIMULUS 1381268073531_1381268076031_1381268077131_1381268079631_1381268080731
    
    
    //Test scenario
    //Both choose the same in the individual scenario (and it goes to the next
       
   
   
    public static ExperimentSettings getDefaultSettings() {
  
      
        
        
        Vector v = new Vector();
        StringParameter sp;
        StringParameterFixed spf;
        IntParameter ip;
        sp = new StringParameter("Experiment ID","CCONFIDENCE");
        v.addElement(sp);
        Vector spv = new Vector();
        
        ip = new IntParameter("Number of participants per conversation",2);
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
  
    
    
    
    
    
    @Override
    public void processChatText(Participant sender,MessageChatTextFromClient mct){    
       
           pTurnsElapsed.setValue(((Integer)pTurnsElapsed.getValue())+1);
           super.expSettings.generateParameterEvent(pTurnsElapsed);
     
           
          
           if(mct.getText().startsWith("/")){
               ctc.processChatText(sender, mct);
               c.setNewTurnBeingConstructedNotRelayingOldTurnAddingOldTurnToHistory(sender, mct);
               
           }
           else{
                c.relayTurnToAllOtherParticipants(sender,mct);
           }
           c.sendLabelDisplayToAllowedParticipantsFromApparentOrigin(sender,"Status: OK",false);
          
       
                   
    }
    
    @Override
    public void processKeyPress(Participant sender,MessageKeypressed mkp){
        //c.informIsTypingToAllowedParticipants(sender);//,mkp);
        c.saveClientKeypressToFile(sender, mkp);

    }

    
   
    
    @Override
    public void processWYSIWYGTextInserted(Participant sender,MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp){
          c.informIsTypingToAllowedParticipants(sender);
       
    }
    
    
    public void setSTime(long stime){
        this.ctc.setSTime(stime);
    }
    public void setFTime(long fixationtime ){
        this.ctc.setFTime(fixationtime);
    }
     public void setBTime(long backgroundtime ){
        this.ctc.setBTime(backgroundtime);
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
    public void participantJoinedConversation(Participant p) {
        super.participantJoinedConversation(p);
        ctc.participantJoinedConversation(p);
        if(c.getParticipants().getAllParticipants().size()>1)ctc.startExperiment();
        //System.exit(-4); 
    }

    @Override
    public void participantRejoinedConversation(Participant p) {
        super.participantRejoinedConversation(p);
        ctc.participantRejoinedConversation(p);
    }
    
   
   

    public void reset(){
        this.ctc.resetToStart();
    }
    
    
   

   

}
