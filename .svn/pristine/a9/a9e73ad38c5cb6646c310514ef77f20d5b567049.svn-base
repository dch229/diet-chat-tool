package diet.server.ConversationController;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageKeypressed;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.message.MessageWYSIWYGTextSelectionFromClient;
import diet.server.Participant;
import java.util.Vector;




public class CCCUSTOM6STIMULI extends DefaultConversationController{
   
    public static boolean showcCONGUI(){
        return false;
    } 
        
    @Override
    public void processLoop(){
             c.resetFromIsTypingtoNormalChatAllAllowedParticipants(super.getIsTypingTimeOut());      
    }
    
   
  
    
    
    @Override
    public void processChatText(Participant sender,MessageChatTextFromClient mct){    
       
           pTurnsElapsed.setValue(((Integer)pTurnsElapsed.getValue())+1);
           super.expSettings.generateParameterEvent(pTurnsElapsed);
           c.relayTurnToAllOtherParticipants(sender,mct);
           c.sendLabelDisplayToAllowedParticipantsFromApparentOrigin(sender,"Status: OK",false);
           if(mct.getText().startsWith("/")){
              
                c.changeWebpageTextAndColour(sender, "window1", "YOU JUST SENT: "+mct.getText(), "WHITE", "BLACK");
               
           }
           
           
    }

    
    
    
    
    @Override
    public void participantJoinedConversation(Participant p) {
        c.displayNEWWebpage(p, "window1", "Concept window", "", 600, 400,false, false);
        c.changeWebpageTextAndColour(p, "window1", "please wait for other person to log in", "WHITE", "BLACK");
        
        if(c.getParticipants().getAllParticipants().size()>1){     ///THIS ASSUMES THERE ARE TWO PARTICIPANTS IN THE EXPERIMENT
            Vector v = c.getParticipants().getAllParticipants();
            for(int i=0;i<v.size();i++){
                Participant p2 = (Participant)v.elementAt(i);
                c.changeWebpageTextAndColour(p2, "window1", "please start\n", "WHITE", "BLACK");
            }
            
            
        }
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
