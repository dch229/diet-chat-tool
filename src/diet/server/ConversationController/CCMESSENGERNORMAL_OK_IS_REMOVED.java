package diet.server.ConversationController;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageKeypressed;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.message.MessageWYSIWYGTextSelectionFromClient;
import diet.parameters.ExperimentSettings;
import diet.server.Conversation;
import diet.server.Participant;
import diet.textmanipulationmodules.AcknowledmentDegrader.CheapOKDegrader;




public class CCMESSENGERNORMAL_OK_IS_REMOVED extends DefaultConversationController{
   
    
        
    @Override
    public void processLoop(){
             c.resetFromIsTypingtoNormalChatAllAllowedParticipants(super.getIsTypingTimeOut());      
    }

    @Override
    public void initialize(Conversation c, ExperimentSettings expSettings) {
        super.initialize(c, expSettings);
        
    }

    @Override
    public void participantJoinedConversation(Participant p) {
        super.participantJoinedConversation(p);
        //String[] options = {"face 1","face 2","face 3","face 4","face 5","face 6","face 7","face 8","face 9","face 10", "face 11", "face 12"};
        //c.showPopupOnClientQueryInfo("",p, "this is the name of the question", options, "", -1); 
       
    }
    
    
    
    
     
  
    
    
    
    
    
    @Override
    public void processChatText(Participant sender,MessageChatTextFromClient mct){    
       

           pTurnsElapsed.setValue(((Integer)pTurnsElapsed.getValue())+1);
           super.expSettings.generateParameterEvent(pTurnsElapsed);
     
           if(CheapOKDegrader.doesStringHaveOK(mct.getText())&&mct.getText().length()>4){
                Conversation.printWSln("Main", "String has ok in it...");
                String text = CheapOKDegrader.removeOKString(mct.getText());
                c.sendArtificialTurnToAllOtherParticipants(sender, text);
                
           }else{
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
