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
   int timerThreshholdS;
   
   
   public CCCUSTOM1(){
       timerThreshholdS = 20;
    }
    public static boolean showcCONGUI(){
        return false;
    } 
        
    /*@Override
    public void processLoop(){
            
             c.resetFromIsTypingtoNormalChatAllAllowedParticipants(super.getIsTypingTimeOut());      
              
    }*/

   
public void processLoop(){
        
//    long theTimestamp = System.currentTimeMillis();
//    super.processLoop();
//    Vector v = c.getParticipants().getAllParticipants(); // Get list of all the participants
//    if(v.size()<2)return; // Need to be at least two participants for this to work
//    
//    if(System.currentTimeMillis() - theTimestamp > 6000){ //Make sure 6 seconds elapse between auto messages
//        for(int i=0;i < v.size();i++){ // Iterate through lists of participants
//        Participant p = (Participant)v.elementAt(i);
//        if(!p.isTyping(6000)){ //Found a participant who hasn't typed for 6 seconds
//        int randindex = new Random().nextInt(v.size()-1);
//        Participant apparentOrigin =
//        (Participant)c.getParticipants().getAllOtherParticipants(p).elementAt(randindex);
//        //This selects a random other participant (essential if the group is larger than 2) who will be the artificial origin of the turn
//        c.sendArtificialTurnFromApparentOriginToRecipient(apparentOrigin, p, "Wake up!");
//        hasSent = true;
//        //This sends the artificial turn to the participant who hasn't responded
//}
//}
    
   Date newDate = new Date();
   long seconds = (newDate.getTime() - dt.getTime())/1000;
   if(seconds > timerThreshholdS){
       dt = new Date();
       Vector v = this.c.getParticipants().getAllParticipants();
       for(int i=0;i<v.size();i++){
       Participant p = (Participant)v.elementAt(i);
       sFrame = new TheSurveyFrame(c, p);
//sFrame = new SurveyJFrame(c, p);
       sFrame.setVisible(true);
    }
       
       
//       try {
//           checkForJFrameClose();
//       } catch (Exception ex) {
//           Logger.getLogger(CCCUSTOM1.class.getName()).log(Level.SEVERE, null, ex);
//       }
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
           c.sendLabelDisplayToAllowedParticipantsFromApparentOrigin(sender,"Status: OK",false);
          
//           c.setNewTurnBeingConstructedNotRelayingOldTurnAddingOldTurnToHistory(sender, mct);
//           String newTurn = mct.getText()+ "...which is great";
//           c.sendArtificialTurnToAllOtherParticipants(sender,newTurn);
        
           
        
        
        //c.changeClientInterface_AllColours(Color.white, Color.DARK_GRAY, Color.black, Color.white, Color.white, Color.white, Color.white, Color.white, Color.white, Color.white, MIN_PRIORITY);
    }
    
    public void participantJoinedConversation(Participant p) {
        super.participantJoinedConversation(p);
        
        dt = new Date();
        
        //Location of this file: code/build/classes/diet/server/ConversationController/CCCUSTOM1.java
        c.displayNEWWebpage(p, "ID1", "Lifeboat Task " + dt.toString(), "", 500, 300, false,false);
        c.changeWebpage(p, "ID1", "", "My string: " + dt.toString(), "");
        //c.changeWebpageTextAndColour(p, "ID1", "Please wait for other participant to log in", "white", "black");
        
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
