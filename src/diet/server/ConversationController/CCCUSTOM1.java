package diet.server.ConversationController;
import cornell.mchci.SurveyJFrame;
import cornell.mchci.TheSurveyFrame;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageKeypressed;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.message.MessageWYSIWYGTextSelectionFromClient;
import diet.server.Participant;
import java.util.Date;
import java.util.Vector;




public class CCCUSTOM1 extends DefaultConversationController{
   Date dt;
   //SurveyJFrame sFrame;
   TheSurveyFrame sFrame;
   int timerThresholdS;
   int firstTimerThresholdS;
   boolean firstTime = true;
   boolean timerRunning = false;
   
   
   public CCCUSTOM1(){
       firstTimerThresholdS = 10;//360;
       timerThresholdS = 8;//300;
    }
    public static boolean showcCONGUI(){
        return false;
    } 
    
    public void beginTimer(){
        timerRunning = true;
        dt = new Date();
    }
        
   
public void processLoop(){
    if(timerRunning){
        Date newDate = new Date();
        long seconds = (newDate.getTime() - dt.getTime())/1000;
        
        if(firstTime){
            if(seconds > firstTimerThresholdS){
            dt = new Date();
            Vector v = this.c.getParticipants().getAllParticipants();
            for(int i=0;i<v.size();i++){
                Participant p = (Participant)v.elementAt(i);
                this.c.getParticipants().displaySurvey(p, "survey" + p.getParticipantID(), "survey");
            }
            }
            firstTime = false;
        } else{
        if(seconds > timerThresholdS){
            dt = new Date();
            Vector v = this.c.getParticipants().getAllParticipants();
            for(int i=0;i<v.size();i++){
                Participant p = (Participant)v.elementAt(i);
                this.c.getParticipants().displaySurvey(p, "survey" + p.getParticipantID(), "survey");
            }
        }
        }
    }
}
    
   
    public void checkForJFrameClose() throws Exception{
        while(sFrame.isVisible()){
           dt = new Date();
           Thread.sleep(1000);
       }
       
    }
    
    
    @Override
    public void processChatText(Participant sender,MessageChatTextFromClient mct){    
       System.out.println("This part is being reached");
           pTurnsElapsed.setValue(((Integer)pTurnsElapsed.getValue())+1);
           super.expSettings.generateParameterEvent(pTurnsElapsed);
     
           c.relayTurnToAllOtherParticipants(sender,mct);
           c.sendLabelDisplayToAllowedParticipantsFromApparentOrigin(sender,"Status: OK",true);
          
           c.setNewTurnBeingConstructedNotRelayingOldTurnAddingOldTurnToHistory(sender, mct);
           String newTurn = mct.getText();
           c.sendArtificialTurnToAllOtherParticipants(sender,newTurn);
    }
    
    public void participantJoinedConversation(Participant p) {
        super.participantJoinedConversation(p);
        dt = new Date();
    }
    
    @Override
    public void processKeyPress(Participant sender,MessageKeypressed mkp){
        //c.informIsTypingToAllowedParticipants(sender);//,mkp);
    }

    
    @Override
    public void processWYSIWYGTextInserted(Participant sender,MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp){
          c.informIsTypingToAllowedParticipants(sender);
       
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
    
   
   


    
    
   

   

}
