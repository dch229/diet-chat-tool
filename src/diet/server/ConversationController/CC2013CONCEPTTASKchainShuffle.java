package diet.server.ConversationController;
import diet.message.MessageChatTextFromClient;
import diet.server.Participant;
import java.util.*;




public class CC2013CONCEPTTASKchainShuffle extends CC2013CONCEPTTASKchain{

    @Override
    public void processChatText(Participant sender,MessageChatTextFromClient mct){  
        Vector v = c.getParticipants().getAllParticipants();
        if(mct.getText().equals("/next")){
            Collections.shuffle(v);
        }
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
    
    
}
