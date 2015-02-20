package diet.server.ConversationController;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageKeypressed;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.message.MessageWYSIWYGTextSelectionFromClient;
import diet.parameters.ExperimentSettings;
import diet.parameters.IntParameter;
import diet.parameters.Parameter;
import diet.parameters.StringParameter;
import diet.parameters.StringParameterFixed;
import diet.server.Conversation;
import diet.server.Participant;
import java.util.Random;
import java.util.Vector;
import javax.swing.SwingUtilities;




public class CCREFLECTORDEBUG extends DefaultConversationController{
   
    
        
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
    
    
     Random r = new Random();
  
    
    
    @Override
    public void processChatText(Participant sender,MessageChatTextFromClient mct){    
       

           pTurnsElapsed.setValue(((Integer)pTurnsElapsed.getValue())+1);
           super.expSettings.generateParameterEvent(pTurnsElapsed);
     
           
           

           c.relayTurnToAllOtherParticipants(sender,mct);
           c.sendLabelDisplayToAllowedParticipantsFromApparentOrigin(sender,"Status: OK",false);
          
         
         
             
          Thread t = new Thread(){   
             public void run(){
                 int iterations = r.nextInt(50);
                 for(int i=0;i<iterations;i++){
                         c.sendArtificialTurnToAllParticipants("faketurn..", 0);
                         try{
                             Thread.sleep((long)r.nextInt(100));
                         }
                         catch (Exception e){
                             e.printStackTrace();
                         }
                 }
                 
             }
         
         };
         t.start();
           
           c.sendArtificialTurnToAllParticipants("faketurn..", 0);
           c.sendArtificialTurnToAllParticipants("faketurn..", 1);
       
                   
    }
    
    //@Override;
    public void processKeyPress(Participant sender,MessageKeypressed mkp){
        c.informIsTypingToAllowedParticipants(sender);//,mkp);
        c.saveClientKeypressToFile(sender, mkp);

    }

    
    @Override
    public void processWYSIWYGTextInserted(Participant sender,MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp){
          c.informIsTypingToAllowedParticipants(sender);
          Participant p = (Participant)c.getParticipants().getAllOtherParticipants(sender).elementAt(0);
          
          c.informParticipantBthatParticipantAIsTyping(p, sender);
       
          c.informParticipantBthatParticipantAIsTyping(sender, sender);
         // c.informParticipantBthatParticipantAIsDeleting(sender, sender, 1);
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
    
   
   
    
    
    
    
    
    
    
    
    
    public static ExperimentSettings getDefaultSettings() {
       
        Vector v = new Vector();
        StringParameter sp;
        StringParameterFixed spf;
        IntParameter ip;
        
        String suffix = "";
       
             

       
        
        
        sp = new StringParameter("Experiment ID","CCMAZE_DYADIC_MULTI_PARTICIPANTS_SEPARATEDYADS_DPBASELINE");
        v.addElement(sp);
        Vector spv = new Vector();
        
        ip = new IntParameter("Number of participants per conversation",4);
        v.addElement(ip);
         spf = new StringParameterFixed("Chat tool interface",spv,"Formulate revise then send. Single or multiple windows");
        v.addElement(spf);
        
        //ip = new IntParameter("Number of windows per chat tool",1);
        //v.addElement(ip);
       
         
        //
        ip = new IntParameter("Number of windows per chat tool",2);
        v.addElement(ip);
         sp = new StringParameter("Window numbering policy","EACHOWNWINDOWATTOPENABLED");
        v.addElement(sp);
        //
        
        spf = new StringParameterFixed("Window numbering policy",spv,"ONEWINDOWENABLED");
        v.addElement(spf);
        
         StringParameterFixed bckg = new StringParameterFixed("Background colour","BLACK"); v.addElement(bckg);
         StringParameterFixed sfTextColour  = new StringParameterFixed("Text colour self",spv,"WHITE");           v.addElement(sfTextColour);       
         StringParameterFixed otherTextColour1  = new StringParameterFixed("Text colour other 1",spv,"YELLOW");   v.addElement(otherTextColour1);
         StringParameterFixed otherTextColour2  = new StringParameterFixed("Text colour other 2",spv,"RED");     v.addElement(otherTextColour2);
         StringParameterFixed otherTextColour3  = new StringParameterFixed("Text colour other 3",spv,"YELLOW");    v.addElement(otherTextColour3);
         StringParameterFixed otherTextColour4  = new StringParameterFixed("Text colour other 4",spv,"YELLOW");     v.addElement(otherTextColour4);
         StringParameterFixed otherTextColour5  = new StringParameterFixed("Text colour other 5",spv,"YELLOW");      v.addElement(otherTextColour5);
         StringParameterFixed otherTextColour6  = new StringParameterFixed("Text colour other 6",spv,"YELLOW");  v.addElement(otherTextColour6);
         StringParameterFixed otherTextColour7  = new StringParameterFixed("Text colour other 7",spv,"YELLOW");   v.addElement(otherTextColour7);
         StringParameterFixed otherTextColour8  = new StringParameterFixed("Text colour other 9",spv,"YELLOW");   v.addElement(otherTextColour8);
         StringParameterFixed otherTextColour9  = new StringParameterFixed("Text colour other 9",spv,"YELLOW");   v.addElement(otherTextColour9);
         StringParameterFixed otherTextColour10  = new StringParameterFixed("Text colour other 10",spv,"YELLOW");   v.addElement(otherTextColour10);
         StringParameterFixed otherTextColour11  = new StringParameterFixed("Text colour other 11",spv,"YELLOW");   v.addElement(otherTextColour11);
         StringParameterFixed otherTextColour12  = new StringParameterFixed("Text colour other 12",spv,"YELLOW");   v.addElement(otherTextColour12);
         StringParameterFixed otherTextColour13  = new StringParameterFixed("Text colour other 13",spv,"YELLOW");   v.addElement(otherTextColour13);
         StringParameterFixed otherTextColour14  = new StringParameterFixed("Text colour other 14",spv,"YELLOW");   v.addElement(otherTextColour14);
         StringParameterFixed otherTextColour15  = new StringParameterFixed("Text colour other 15",spv,"YELLOW");   v.addElement(otherTextColour15);
         StringParameterFixed otherTextColour16  = new StringParameterFixed("Text colour other 16",spv,"YELLOW");   v.addElement(otherTextColour16);
         StringParameterFixed otherTextColour17  = new StringParameterFixed("Text colour other 17",spv,"YELLOW");   v.addElement(otherTextColour17);
         StringParameterFixed otherTextColour18  = new StringParameterFixed("Text colour other 18",spv,"YELLOW");   v.addElement(otherTextColour18);
         StringParameterFixed otherTextColour19  = new StringParameterFixed("Text colour other 19",spv,"YELLOW");   v.addElement(otherTextColour19);
         StringParameterFixed otherTextColour20  = new StringParameterFixed("Text colour other 20",spv,"YELLOW");   v.addElement(otherTextColour20);
         
              
          
          
         
         
         IntParameter ipr;
         
         
         ipr = new IntParameter("Width of main window",(Integer)400);         v.addElement(ipr);
         ipr = new IntParameter("Height of main window",(Integer)250);        v.addElement(ipr);
         ipr = new IntParameter("Width of text entry window",(Integer)120);   v.addElement(ipr);
         ipr = new IntParameter("Height of text entry window",(Integer)80);  v.addElement(ipr);
         ipr = new IntParameter("Maximum length of textentry",(Integer)1000); v.addElement(ipr);
          
         
        
         
         
         
         
         
         
         
        
        spf = new StringParameterFixed("Horizontal or vertical alignment of multiple windows",spv,"Vertical");
        v.addElement(spf);
        
        ip = new IntParameter("Typing status timeout (msecs)",1000);
        v.addElement(ip);
        ExperimentSettings expSett = new ExperimentSettings(v);
        
        
        for(int i=0;i<v.size();i++){
            System.err.println("VERIFYING PARAMETERS ");
            Object o = v.elementAt(i);
            if(o instanceof Parameter){
                System.err.println(i+"VERIFYING PARAMETERS "+((Parameter)o).getID());
            }
            else{
                System.err.println(i+" EXITING "+o.getClass().toString()+" "+o.toString());
                System.exit(10*-i);
            }
        }
        //if(2<5)System.exit(-5);
        
        return expSett;
    
    
    
    
    
    
    
    
    
    
    } 


    
    
   

   

}
