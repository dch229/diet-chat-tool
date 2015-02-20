package diet.server.ConversationController;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.server.Participant;
import java.util.*;


public class CC2013CONCEPTTASKchain extends CC2013CONCEPTTASK{

int partNum = 0;

    @Override
    public void processChatText(Participant sender,MessageChatTextFromClient mct){  
        Vector v = c.getParticipants().getAllParticipants();
        if(v.indexOf(sender)==v.size()-1){
            partNum = 0;
        }else{
            partNum = v.indexOf(sender)+1;
        }
        Participant recip = (Participant)v.elementAt(partNum);
        pTurnsElapsed.setValue(((Integer)pTurnsElapsed.getValue())+1);
        super.expSettings.generateParameterEvent(pTurnsElapsed);
        
        //c.relayTurnToParticipant(sender, recip, mct); //Do we want to have a fake name? 
        c.sendArtificialTurnToRecipient(recip, mct.getText(), 0, 1, "");
        c.sendLabelDisplayToAllowedParticipantsFromApparentOrigin(sender,"Status: OK",false);
        this.resetTimersCorrectly(sender, mct);
    }
    
    @Override
    public void processWYSIWYGTextInserted(Participant sender,MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp){
        c.informIsTypingToAllowedParticipantswithAlteredOriginName(sender, "Someone");
        //c.informIsTypingToAllowedParticipants(sender);
    }
}
