/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.ConversationController;

import diet.message.MessageChatTextFromClient;
import diet.server.Participant;
import diet.textmanipulationmodules.AcknowledmentDegrader.CheapOKDegrader;
import java.util.Vector;

/**
 *
 * @author gj
 */
public class CCGRIDSTIMULI_DP_DEMO extends CCGRIDSTIMULI{

    @Override
    public boolean requestParticipantJoinConversation(String participantID) {
        //return super.requestParticipantJoinConversation(participantID); //To change body of generated methods, choose Tools | Templates.
        if (c.getParticipants().getAllParticipants().size() < 3 )return true;
        Vector v = c.getParticipants().getAllParticipants();
        for (int i=0;i<v.size();i++){
            Participant p = (Participant )v.elementAt(i);
            if(p.getParticipantID().equals(participantID))return true;
        }
        return false;
        
    }

    @Override
    public void processChatText(Participant sender, MessageChatTextFromClient mct) {
        if(CheapOKDegrader.doesStringHaveOK(mct.getText())){
               c.relayTurnToAllOtherParticipants(sender,mct); 
                
        }
        else{
           int i= r.nextInt(10);
               //int i=1; 
               if(i==1){
                    String s ="";
                    int j = r.nextInt(7);
                    if(j==0) s = "uhh "+mct.getText();
                    if(j==1) s = "umm "+mct.getText();
                    if(j==2) s = "maybe.."+mct.getText() ;
                    if(j==3) s = "so.."+mct.getText();
                    if(j==4) s = "..but maybe "+mct.getText();
                    if(j==5) s = "but maybe "+mct.getText();
                    if(j==6) s = "..i dont know "+mct.getText();
                    c.sendArtificialTurnToAllOtherParticipants(sender, s);
                }
               
               
               else{
                    c.relayTurnToAllOtherParticipants(sender,mct);      
               }
              
                
                
           } 
    }
    
   
    
    
}
