package diet.server.ConversationController;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageKeypressed;
import diet.parameters.DefaultSettingsFactory;
import diet.parameters.ExperimentSettings;
import diet.server.Conversation;
import diet.server.Participant;
import diet.task.gridstimuli.GridStimuliTaskController;
import diet.task.gridstimuli.GridStimuliTaskController_SelectingSquares_3WAY;




public class CCGRIDSTIMULI extends DefaultConversationController{
   
   
    GridStimuliTaskController_SelectingSquares_3WAY gsc= new GridStimuliTaskController_SelectingSquares_3WAY(this);
    
    
    
    
    
    @Override
    public void processLoop(){
             c.resetFromIsTypingtoNormalChatAllAllowedParticipants(super.getIsTypingTimeOut());      
    }

    @Override
    public void initialize(Conversation c, ExperimentSettings expSettings) {
        super.initialize(c, expSettings);
        c.setTaskController(gsc);
       
    }

    
    
    
    
    
    
    
    @Override
    public void participantJoinedConversation(Participant p) {
        super.participantJoinedConversation(p);
        gsc.participantJoinedConversation(p);
        c.changeClientInterface_disableScrolling(p);
    }

    @Override
    public void participantRejoinedConversation(Participant p) {
        gsc.participantRejoinedConversation(p);
    }
    
    
     
  
    
    
    @Override
    public void processChatText(Participant sender,MessageChatTextFromClient mct){    
           pTurnsElapsed.setValue(((Integer)pTurnsElapsed.getValue())+1);
           super.expSettings.generateParameterEvent(pTurnsElapsed);
           c.relayTurnToAllOtherParticipants(sender,mct);
           c.sendLabelDisplayToAllowedParticipantsFromApparentOrigin(sender,"Status: OK",false);               
    }
    
    
    
    @Override
    public void processKeyPress(Participant sender,MessageKeypressed mkp){
        //c.informIsTypingToAllowedParticipants(sender);//,mkp);
        c.saveClientKeypressToFile(sender, mkp);

    }

    
    
    
   
   


     public static ExperimentSettings getDefaultSettings() {
        
        ExperimentSettings es = DefaultSettingsFactory.getDefaultExperimentParameters();
        es.changeParameterValue("Number of participants per conversation",3);
        return es;
        //return DefaultSettingsFactory.getDefaultExperimentParameters();
    }
    
   

   

}
