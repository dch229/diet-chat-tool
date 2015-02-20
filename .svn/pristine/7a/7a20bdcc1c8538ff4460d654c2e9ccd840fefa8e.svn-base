package diet.server.ConversationController;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageKeypressed;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.message.MessageWYSIWYGTextSelectionFromClient;
import diet.parameters.ExperimentSettings;
import diet.server.Conversation;
import diet.server.ConversationController.Interventions.ClariIntervention;
import diet.server.Participant;




public class CCMESSENGERNORMALWITHTRANSFORMEDTURNCR extends DefaultConversationController{
   
   public static boolean showcCONGUI(){  return false; }  
        
    @Override
    public void processLoop(){
             c.resetFromIsTypingtoNormalChatAllAllowedParticipants(super.getIsTypingTimeOut());      
    }

    @Override
    public void initialize(Conversation c, ExperimentSettings expSettings) {
        super.initialize(c, expSettings);
         ci = new ClariIntervention(c);
       
        
    }
    
  
   
     
    ClariIntervention ci; 
    
    
    @Override
    public void processChatText(Participant sender,MessageChatTextFromClient mct){    
       
          // c.changeClientInterface_backgroundColour(sender, Color.red);
          // System.exit(-4444411);
          // 
         // c.changeClientInterface_AllColours(Color.red, Color.yellow, Color.green, Color.white, Color.white, Color.white, Color.white, Color.white, Color.white, 20);
        
           pTurnsElapsed.setValue(((Integer)pTurnsElapsed.getValue())+1);
           super.expSettings.generateParameterEvent(pTurnsElapsed);
     
           Participant recipient = (Participant)c.getParticipants().getAllOtherParticipants(sender).elementAt(0);
           
           
           ClariIntervention.processChatText(sender, mct, recipient, 70000, 15000);
           
          // c.relayTurnToAllOtherParticipants(sender,mct);
          // c.sendLabelDisplayToAllowedParticipantsFromApparentOrigin(sender,"Status: OK",false);
          
       
                   
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
