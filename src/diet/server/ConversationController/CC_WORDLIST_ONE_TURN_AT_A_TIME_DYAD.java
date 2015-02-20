package diet.server.ConversationController;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageKeypressed;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.message.MessageWYSIWYGTextSelectionFromClient;
import diet.parameters.ExperimentSettings;
import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.Participant;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;
import javax.swing.JFileChooser;




public class CC_WORDLIST_ONE_TURN_AT_A_TIME_DYAD extends DefaultConversationController{
   
    
    
    Participant p1;
    Participant p2;

    String terminalCharacter = "_";
    String acceptString ="1";
    String rejectString ="0";
    
    boolean terminusHASBEENSIGNALLED = false;
    Participant currentSetInitiator = null;
    
    Object[] wordlist = {"apple","banana","carror","date"};
    
  
    String currentPrompt = "";
    
    int interventionNumber = 0;
    
    
    @Override
    public void participantJoinedConversation(Participant p) {
                                                        //self       //other     //instruction
        c.changeClientInterface_AllColours(Color.black, Color.white, Color.blue, Color.red, Color.magenta, Color.orange, Color.gray, Color.green, Color.cyan, Color.cyan, 16);
         
        
        super.participantJoinedConversation(p);
        if(p1==null){
            p1=p;
        }
        else if(p2==null){
            p2=p;
            
            this.enableParticipantAdisableParticipantB(p1, p2);
            
            currentSetInitiator = p1;
            
            this.nextTrial();
            
        }
        
        
    }
    
        
    
    public void enableParticipantAdisableParticipantB(Participant a,Participant b){
         c.changeClientInterface_disableTextEntry(b);
         c.changeClientInterface_enableTextEntry(a);
         
    }
    
    
    
    
    @Override
    public void processLoop(){
             c.resetFromIsTypingtoNormalChatAllAllowedParticipants(super.getIsTypingTimeOut());      
    }

    @Override
    public void initialize(Conversation c, ExperimentSettings expSettings) {
        super.initialize(c, expSettings);
                
        this.setIsTypingTimeOut(500);
        String [] sOptions = {"Clear","Show full conversation"};
        String s = CustomDialog.show2OptionDialog(sOptions, "When a participant types a word, do you want it to clear the previous word? ", "Setup");
        if(s.equalsIgnoreCase("Clear")){
            this.clearOnEachSend=true;
           
        }
        else{
            this.clearOnEachSend=false;
           
        }
        
        loadStrings();
        terminalCharacter = CustomDialog.getString("What do you want to use as the terminal String?", "_");
        this.acceptString = CustomDialog.getString("What do you want to use as the ACCEPT String?", "1");
        this.rejectString = CustomDialog.getString("What do you want to use as the REJECT String?", "0");
    }
    
    
    public void loadStrings(){
         String s = System.getProperty("user.dir");
         File f = new File(s);
         final JFileChooser fc = new JFileChooser(f);
         int returnVal = fc.showDialog(null,"Select the text file that contains the prompts");
         File fname = fc.getSelectedFile();
         Vector allWords = new Vector();
         try{
              BufferedReader br = new BufferedReader(new FileReader(fname));
              String line = br.readLine();
              while (line != null) {
                   allWords.addElement(line);
                   line = br.readLine();
              }
              
              br.close();
        
         }catch(Exception e){
             e.printStackTrace();
             CustomDialog.showDialog("Could not load the file for some reason");
             System.exit(-52);
         }
         this.wordlist=allWords.toArray();
    }
    
     boolean clearOnEachSend = true;
    
     public void nextTrial(){
          
          this.interventionNumber++;
          if(interventionNumber>=wordlist.length){
               c.sendArtificialTurnToAllParticipants("This is the end of the study! THANKS!", 0);
               CustomDialog.showDialog("They have reached the end of the list");
               c.sendArtificialTurnToAllParticipants("This is the end of the study! THANKS!", 0);
               c.changeClientInterface_disableTextEntry(p1);
               c.changeClientInterface_disableTextEntry(p2);
               return;
          }
          
          String interventionWord = (String)wordlist[interventionNumber];
          
          //c.changeClientInterface_clearMainWindow(p1);
          //c.changeClientInterface_clearMainWindow(p2);
          //c.changeClientInterface_clearTextEntryField(p1);
          //c.changeClientInterface_clearTextEntryField(p2);
          c.changeClientInterface_disableTextEntry(p1);
          c.changeClientInterface_disableTextEntry(p2);
          if(interventionNumber>1)c.sendArtificialTurnToAllParticipants("Thank you! Please wait for the next word", 0);
          String textTosend = CustomDialog.getString("This will be sent to the participants:", interventionWord);
          c.changeClientInterface_clearMainWindows(p1);
          c.changeClientInterface_clearMainWindows(p2);
          
          
          
          this.currentPrompt = textTosend;
          //String textTosend = "texttosend";
          Participant other =  (Participant)(this.getC().getParticipants().getAllOtherParticipants(currentSetInitiator)).elementAt(0);
          this.enableParticipantAdisableParticipantB(other, currentSetInitiator); 
          currentSetInitiator = other;
          this.terminusHASBEENSIGNALLED=false;
         
          if(this.clearOnEachSend){
               c.changeClientInterface_clearMainWindows(this.p1);
               c.changeClientInterface_clearMainWindows(this.p2);
               c.sendArtificialTurnToAllParticipants(textTosend, 0);   
               
          }else{
               c.sendArtificialTurnToAllParticipants(textTosend, 0);     
          }
          
         
         
          
     }

     
     
     
     
    
    
    @Override
    public void processChatText(Participant sender,MessageChatTextFromClient mct){    
           Participant other = (Participant)c.getParticipants().getAllOtherParticipants(sender).elementAt(0);
           if(this.clearOnEachSend){
               c.changeClientInterface_clearMainWindows(other);
               c.changeClientInterface_clearMainWindows(sender);
               
               //c.changeClientInterface_clearMaintextEntryWindow(this.p2);
               c.sendArtificialTurnToAllParticipants(this.currentPrompt, 0);   
               c.sendArtificialTurnFromApparentOriginToRecipient(sender, sender, mct.getText());
          }
        
        
        
         
           
           
           if(sender==p1){
               //c.sendArtificialTurnToRecipient(sender, "You should be disabled..you're p1", 0);
               //c.sendArtificialTurnToRecipient(other, "You should be enabled..you're p2", 0);
           }
           else if (sender ==p2){
               //c.sendArtificialTurnToRecipient(sender, "You should be disabled..you're p2", 0);
               //c.sendArtificialTurnToRecipient(other, "You should be enabled..you're p1", 0);
           }
           this.enableParticipantAdisableParticipantB(other,sender);
           
           
           
        
           pTurnsElapsed.setValue(((Integer)pTurnsElapsed.getValue())+1);
           super.expSettings.generateParameterEvent(pTurnsElapsed);
     
           c.relayTurnToAllOtherParticipants(sender,mct);
           c.sendLabelDisplayToAllowedParticipantsFromApparentOrigin(sender,"Status: OK",false);
           
           if(terminusHASBEENSIGNALLED){
                nextTrial();
                this.terminusHASBEENSIGNALLED=false;
           }
           else if(mct.getText().startsWith(terminalCharacter)||
                   mct.getText().startsWith(this.acceptString)||
                   mct.getText().startsWith(this.rejectString)){
               this.terminusHASBEENSIGNALLED=true;
               
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

    @Override
    public boolean requestParticipantJoinConversation(String participantID) {
        //return super.requestParticipantJoinConversation(participantID);
        if(c.getParticipants().getAllParticipants().size()<2)return true;
        return false;
    }
    
   
   


    
    
   

   

}
