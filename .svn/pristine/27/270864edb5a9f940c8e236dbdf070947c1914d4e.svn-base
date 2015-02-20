package diet.server.ConversationController;
import client.MazeGameController2WAY;
import client.mazegameutils.MazeGameLoadMazesFromJarFile;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageKeypressed;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.message.MessageWYSIWYGTextSelectionFromClient;
import diet.parameters.DefaultSettingsFactory;
import diet.parameters.ExperimentSettings;
import diet.parameters.StringParameter;
import diet.server.Conversation;
import diet.server.Participant;
import diet.server.conversationhistory.turn.MAZEGAMETURN;
import diet.server.conversationhistory.turn.Turn;
import java.io.File;




public class CCMAZEGAME_2WAY_RANDOMIZEDMAZES  extends DefaultConversationController{
    
    
    MazeGameController2WAY mgc;
    
    public static boolean showcCONGUI(){
        return true;
    } 

    @Override
    public void initialize(Conversation c, ExperimentSettings expSettings) {
        super.initialize(c, expSettings); 
        
        
        System.err.println("-----------------------"+this.getClass().getName());
        expSettings.changeParameterValue("Conversation Controller", this.getClass().getName());
        
                
                
        
        
        StringParameter sp = (StringParameter)expSettings.getParameter("Recovery");
        if(sp!=null){
            Conversation.printWSln("Main", "attempting recovery");
            File f = new File(sp.getValue());
            mgc = new MazeGameController2WAY(c,new File(f,"cl1Mazes.v"),new File(f,"cl2Mazes.v"));
        }
        else{   
            MazeGameLoadMazesFromJarFile mglmfj = new MazeGameLoadMazesFromJarFile();
            mglmfj.getMazesFromJar();
            mgc = new MazeGameController2WAY(c,MazeGameLoadMazesFromJarFile.cl1MazesRANDOMIZED,MazeGameLoadMazesFromJarFile.cl2MazesRANDOMIZED);
            c.setTaskController(mgc);
        }
        
        //MazeGameController2WAY(Conversation c, Vector p1Mazes, Vector p2Mazes){
      
    }

    @Override
    public boolean requestParticipantJoinConversation(String participantID) {
       // return super.requestParticipantJoinConversation(participantID); //To change body of generated methods, choose Tools | Templates.
       if(c.getParticipants().getAllParticipants().size()>=2){
            //If there are already 2 participants, then only let a participant join (i.e. rejoin) the conversation if is already a participant
           Participant p = c.getParticipants().findParticipantWithEmail(participantID);
           if(p!=null){
               
               return true;
           }
           return false;
       }
       if(participantID.equalsIgnoreCase("111111"))return true;
       if(participantID.equalsIgnoreCase("222222"))return true;
       return false;
    
    }

    @Override
    public void participantRejoinedConversation(Participant p) {
        //super.participantRejoinedConversation(p); //To change body of generated methods, choose Tools | Templates.
        mgc.reconnectParticipant(p);
    
    }
   
    
    
    
    
    
    @Override
    public void processLoop(){
             c.resetFromIsTypingtoNormalChatAllAllowedParticipants(super.getIsTypingTimeOut());      
    }
    
   
    
    @Override
    public void processChatText(Participant sender, MessageChatTextFromClient mct){    
       
           pTurnsElapsed.setValue(((Integer)pTurnsElapsed.getValue())+1);
           super.expSettings.generateParameterEvent(pTurnsElapsed);
     
   
           MazeGameController2WAY mgc = (MazeGameController2WAY)c.getTaskController();
           mgc.appendToUI(sender.getUsername()+": "+mct.getText());
           
           int mazeNo = mgc.getMazeNo();
           int moveNo= mgc.getMoveNo();
           int indivMveNo = mgc.getParticipantsMoveNo(sender);
           c.relayMazeGameTurnToAllOtherParticipants(sender,mct,mazeNo,moveNo,indivMveNo);
           c.sendLabelDisplayToAllowedParticipantsFromApparentOrigin(sender, "Status: OK", false);
           
           
    }

    Participant p1;
    Participant p2;
    
    
    
    
    
    @Override
    public void participantJoinedConversation(Participant p) {
        super.participantJoinedConversation(p); //To change body of generated methods, choose Tools | Templates.
        if(p.getParticipantID().equalsIgnoreCase("111111"))p1=p;
        if(p.getParticipantID().equalsIgnoreCase("222222"))p2=p;
        
        
        
        if(c.getParticipants().getAllParticipants().size()<2){
            c.sendArtificialTurnToRecipient(p, "Please wait for the other participant to logon", 0);
        }
        else{
               mgc.startBothParticipants(p1, p2);
        }
    }
    
    
    
    
    
    
    
    @Override
    public void processKeyPress(Participant sender,MessageKeypressed mkp){
        //c.informIsTypingToAllowedParticipants(sender);//,mkp);
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
    public Turn getTurnTypeForIO_DEPRECATED() {
        return new MAZEGAMETURN();
    }
    
    public static ExperimentSettings getDefaultSettings(){
        ExperimentSettings es = DefaultSettingsFactory.getDefaultExperimentParameters();
        es.changeParameterValue("Experiment ID", "Maze game 2WAY");
        return es;
    }
   

   
    
    
   

   

}
