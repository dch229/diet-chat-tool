package diet.server.ConversationController;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageKeypressed;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.message.MessageWYSIWYGTextSelectionFromClient;
import diet.parameters.ExperimentSettings;
import diet.server.Conversation;
import diet.server.Participant;
import java.util.Vector;




public class CCMESSENGERNORMAL_OK_IS_ADDED extends DefaultConversationController{
   
    
        
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
    
    
    
    public boolean doesStringHaveOK(String s){
        String haystack = " "+s+" ";
        if(haystack.indexOf(" ok ")>-1) return true;
        if(haystack.indexOf(" okay ")>-1) return true;
        if(haystack.indexOf(" oke ")>-1) return true;
        if(haystack.indexOf(" oky ")>-1) return true;
        if(haystack.indexOf(" k ")>-1) return true;
        if(haystack.indexOf(" kk ")>-1) return true;
        
        if(haystack.indexOf(" OK ")>-1) return true;
        if(haystack.indexOf(" OKAY ")>-1) return true;
        if(haystack.indexOf(" OKE ")>-1) return true;
        if(haystack.indexOf(" OKY ")>-1) return true;
        if(haystack.indexOf(" K ")>-1) return true;
        if(haystack.indexOf(" KK ")>-1) return true;
        
        System.err.println("NO_OK");
        Conversation.printWSln("Main", "false");
        return false;   
  
    }
     
  
    public String removeOKString(String s){
        String padded = " "+s+" ";
        padded = padded.replace(" ok ", " ");
        padded = padded.replace(" okay ", " ");
        padded = padded.replace(" oke ", " ");
        padded = padded.replace(" oky ", " ");
        padded = padded.replace(" k ", " ");
        padded = padded.replace(" kk ", " ");
        
        padded = padded.replace(" OK ", " ");
        padded = padded.replace(" OKAY ", " ");
        padded = padded.replace(" OKE ", " ");
        padded = padded.replace(" OKY ", " ");
        padded = padded.replace(" K ", " ");
        padded = padded.replace(" KK ", " ");
        
       
        
        System.err.println("MODIFYING PADDED "+padded);
        //System.exit(-4);
        return padded;
    }
    
    
    
    
    
    @Override
    public void processChatText(Participant sender,MessageChatTextFromClient mct){    
       

           pTurnsElapsed.setValue(((Integer)pTurnsElapsed.getValue())+1);
           super.expSettings.generateParameterEvent(pTurnsElapsed);
     
           if(this.doesStringHaveOK(mct.getText())){
               
                
           }else{
               int i= r.nextInt(8);
               //int i=1; 
               if(i==1){
                    String s ="";
                    int j = r.nextInt(3);
                    if(j==0) s = "ok "+mct.getText();
                    if(j==1) s = "okay "+mct.getText();
                    if(j==2) s = "OK.."+mct.getText();
                     if(j==3) s = "ok.."+mct.getText();
                    c.sendArtificialTurnToAllOtherParticipants(sender, s);
                }
               else{
                    c.relayTurnToAllOtherParticipants(sender,mct);      
               }
              
                
                
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
          //c.informIsTypingToAllowedParticipants(sender);
       
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
