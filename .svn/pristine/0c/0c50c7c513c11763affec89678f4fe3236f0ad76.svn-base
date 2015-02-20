package diet.server.ConversationController;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageKeypressed;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.message.MessageWYSIWYGTextSelectionFromClient;
import diet.parameters.DefaultSettingsFactory;
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




public class CC_WORDLIST_ONE_TURN_AT_A_TIME_MONO extends DefaultConversationController{
   
    
    
    Participant p1;
    //Participant p2;

    String terminalCharacter = "_";
    String acceptString ="1";
    String rejectString ="0";
    
    boolean terminusHASBEENSIGNALLED = false;
   
    
    Object[] wordlist = {"apple","banana","carror","date"};
    
  
    String currentPrompt = "";
    
    int interventionNumber = 0;
    
    
    @Override
    public void participantJoinedConversation(Participant p) {
        super.participantJoinedConversation(p);
                                                         //self      //instructions
         c.changeClientInterface_AllColours(Color.black, Color.white, Color.red, Color.blue, Color.magenta, Color.orange, Color.gray, Color.green, Color.cyan, Color.cyan, 16);
      
        System.err.println("JOINED "+p.getUsername());
        if(p1!=null){
            System.exit(-5);
            return;
        }
        
        
            if(p1==null){
                p1=p;
            }else{
                c.sendArtificialTurnToRecipient(p, "THIS IS THE SINGLE PARTICIPANT VERSION..THIS CLIENT IS DISABLED", 0);
            }
            
             System.err.println("JOINED1"+p.getUsername());
            this.nextTrial();
            
       
         System.err.println("JOINED2"+p.getUsername());
        
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
          try{
          this.interventionNumber++;
         
          if(interventionNumber>=wordlist.length){
               c.sendArtificialTurnToAllParticipants("This is the end of the study! THANKS!", 0);
               CustomDialog.showDialog("They have reached the end of the list");
               c.sendArtificialTurnToAllParticipants("This is the end of the study! THANKS!", 0);
               c.changeClientInterface_disableTextEntry(p1);
              
               return;
          }
          
          String interventionWord = (String)wordlist[interventionNumber];
          
          //c.changeClientInterface_clearMainWindow(p1);
          //c.changeClientInterface_clearMainWindow(p2);
          //c.changeClientInterface_clearTextEntryField(p1);
          //c.changeClientInterface_clearTextEntryField(p2);
           
          c.changeClientInterface_enableTextEntry(p1);
          System.err.println("JOINED1B");
          if(interventionNumber>1)c.sendArtificialTurnToAllParticipants("Thank you! Please wait for the next word", 0);
          System.err.println("JOINED1C1");
          String textTosend = CustomDialog.getString("This will be sent to the participant:", interventionWord);
          c.changeClientInterface_clearMainWindows(p1);
          System.err.println("JOINED1D");
          
          
          
          this.currentPrompt = textTosend;
          //String textTosend = "texttosend";
         
          
          this.terminusHASBEENSIGNALLED=false;
          System.err.println("JOINED1F");
          if(this.clearOnEachSend){
               c.changeClientInterface_clearMainWindows(this.p1);
 
               c.sendArtificialTurnToAllParticipants(textTosend, 0);   
               
          }else{
               c.sendArtificialTurnToAllParticipants(textTosend, 0);     
          }
          
         
          }catch(Exception e){
              e.printStackTrace();
          }
          
     }

     
     
     
     
    
    
    @Override
    public void processChatText(Participant sender,MessageChatTextFromClient mct){    
          // Participant other = (Participant)c.getParticipants().getAllOtherParticipants(sender).elementAt(0);
           if(this.clearOnEachSend){
               
               c.changeClientInterface_clearMainWindows(sender);
               
               //c.changeClientInterface_clearMaintextEntryWindow(this.p2);
               c.sendArtificialTurnToAllParticipants(this.currentPrompt, 0);   
               c.sendArtificialTurnFromApparentOriginToRecipient(sender, sender, mct.getText());
          }
        
        
        
           pTurnsElapsed.setValue(((Integer)pTurnsElapsed.getValue())+1);
           super.expSettings.generateParameterEvent(pTurnsElapsed);
     
           c.relayTurnToAllOtherParticipants(sender,mct);
           c.sendLabelDisplayToAllowedParticipantsFromApparentOrigin(sender,"Status: OK",false);
           
          
           if(mct.getText().startsWith(terminalCharacter)||
                   mct.getText().startsWith(this.acceptString)||
                   mct.getText().startsWith(this.rejectString)){
              nextTrial();
               
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
    
   
   

    
    public  static ExperimentSettings getDefaultSettings() {
        ExperimentSettings es = DefaultSettingsFactory.getDefaultExperimentParameters();
        es.changeParameterValue("Number of participants per conversation",1);
        return es;
        //return DefaultSettingsFactory.getDefaultExperimentParameters();
    }
    
   
     public boolean requestParticipantJoinConversation(String participantID) {
        //return super.requestParticipantJoinConversation(participantID);
        if(c.getParticipants().getAllParticipants().size()<1)return true;
        return false;
    }
   

}
