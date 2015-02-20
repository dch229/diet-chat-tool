package diet.server.ConversationController;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.Vector;

import diet.client.WYSIWYGDocumentWithEnforcedTurntaking;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageKeypressed;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.message.MessageWYSIWYGTextSelectionFromClient;
import diet.message.MessageWYSIWYGTypingUnhinderedRequest;
import diet.parameters.*;
import diet.server.Conversation;
import diet.server.DocChangesOutgoingSequenceFIFO;
import diet.server.Participant;
import diet.server.StringOfDocChangeInserts;
import diet.server.CbyC.DocChange;
import diet.server.CbyC.DocInsert;
import diet.server.CbyC.DocRemove;
import diet.textmanipulationmodules.TypoGenerator.TypoGenerator;
import diet.utils.VectorToolkit;
import edu.gwu.wordnet.PointerType;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.trees.Tree;

public class CCSPLITUTTNONSBAR extends DefaultConversationController{
  
    public static boolean showcCONGUI(){
        return false;
    } 
    
      
    private DocChangesOutgoingSequenceFIFO chOut = new DocChangesOutgoingSequenceFIFO(null,10,new Date().getTime()); //THIS WILL BE RESET ON FIRST RECEIPT OF TURN
    
    //IntParameter pCountdownToIntervention=new IntParameter("Turns till Next intervention",10,10);
    //IntParameter pCountdownToInterventionMax=new IntParameter("Turns between interventions",15,15);
    
    IntParameter pCountdownToIntervention=new IntParameter("Turns till Next intervention",1,1);
    IntParameter pCountdownToInterventionMax=new IntParameter("Turns between interventions",1,1);
    
    
    IntParameter parserTimeOut = new IntParameter("Parser Timeout",500,1000);
    IntParameter maxLengthOfParseAttempt = new IntParameter("Parser Max Length (chars)",70,70);
    IntParameter minDelayBetweenTurns = new IntParameter("Min inter-turn delay",250,250);
    IntParameter maxDelayBetweenTurns = new IntParameter("Max inter-turn delay",1600,600);
    IntParameter avgTypingTimePerChar = new IntParameter("Avg typing time per char",60,60);
    StringParameterFixed splitIntervention = new StringParameterFixed();

   
    
    public void initialize(Conversation c,ExperimentSettings expSettings){
        super.initialize(c, expSettings);
        Vector v = new Vector();
        v.addElement("yes");
        v.addElement("no");
        StringParameterFixed spf = new StringParameterFixed("Display parse tree","no",v,"no");
        expSettings.addParameter(spf);
        
        Vector vI = new Vector();
        vI.addElement("AB");
        vI.addElement("BA");
        //vI.addElement("BB");
        splitIntervention = new StringParameterFixed("Split Intervention", 0, vI, 0);
        expSettings.addParameter(splitIntervention);
        
        expSettings.addParameter(minDelayBetweenTurns);
        expSettings.addParameter(maxDelayBetweenTurns);
        expSettings.addParameter(avgTypingTimePerChar);
        
        Vector vThingstoBlock = new Vector();
        String[] vListToBlock = {"I "," I "," I\n"," me\n","i "," i "," i\n","my"," my "," my\n","mine"," mine "," mine\n","me "," me "," me\n"};
        vThingstoBlock = new Vector(Arrays.asList(vListToBlock));
        
    }
    
    @Override
    public void processLoop(){
        if(pCountdownToIntervention.getValue()<0){
            
        }else{
            c.resetFromIsTypingtoNormalChatAllAllowedParticipants(super.getIsTypingTimeOut());
       
        }
    }
    
   
    Participant OriginFirstHalf = null;
    Participant OriginSecondHalf = null;
   
    @Override
    public void processChatText(Participant sender,MessageChatTextFromClient mct){    
        if(sender==p3||sender==p4){
            c.relayTurnToAllOtherParticipants(sender, mct);
            System.err.println("PERFORMING SIMPLE RELAY");
            return;
        }
        
        
        
        
        Vector v2 = new Vector();
       
        String ToSplit = null;
        Vector split = null;
        Vector split1 = new Vector();
        Vector allParticipants = c.getParticipants().getAllParticipants();
        Vector allOthers = c.getParticipants().getAllOtherParticipants(sender);
        Participant otherCollectiveParticipant = sender;
        if(sender ==p1) otherCollectiveParticipant= p2;
        if(sender ==p2) otherCollectiveParticipant= p1;
        
        
        int q1 = 0;
        int i = 0;
        Random WhichIntervention = new Random(); 
        int randomIndex = WhichIntervention.nextInt(2);
        splitIntervention.setValue(splitIntervention.getPermittedValues().elementAt(randomIndex));
        expSettings.generateParameterEvent(splitIntervention);
        System.err.println("COUNTDOWN IN: "+pCountdownToIntervention.getValue());
        
        if (pCountdownToIntervention.getValue()<=0&&mct.getText().length()>=15){
            ToSplit = mct.getText();
            q1 = mct.getText().length();
            q1 = q1/2;
            int index = ToSplit.indexOf(" ", q1); 
            split1.addElement(ToSplit.substring(0, index));
            split1.addElement(ToSplit.substring(index+1, ToSplit.length()));
            split = split1;
        }else if(pCountdownToIntervention.getValue()<=0){
            //c.informParticipantBthatParticipantAIsTyping(sender,recipient1);
            //c.informParticipantBthatParticipantAIsTyping(sender,recipient2);
            //try{Thread.sleep(minDelayBetweenTurns.getValue()+r.nextInt(maxDelayBetweenTurns.getValue()-minDelayBetweenTurns.getValue())+q1*this.avgTypingTimePerChar.getValue());}catch(Exception e){}
        }
        pCountdownToIntervention.setValue(pCountdownToIntervention.getValue()-1);
        expSettings.generateParameterEvent(pCountdownToIntervention);
        
        
        if(split!=null){    

             c.setNewTurnBeingConstructedNotRelayingOldTurnAddingOldTurnToHistory(sender,mct);
             
           
 
               
             
 
             String splitUtt = (String)split.elementAt(1);
             
             
             if(splitIntervention.getValue() == "AB"){
                OriginFirstHalf = sender;
             }else{//i.e. 1st half origin = fake                 
                OriginFirstHalf =  otherCollectiveParticipant;
             }
             if(splitIntervention.getValue() == "BA"){
                OriginSecondHalf = sender;
             }else{//i.e. 2nd half origin = fake
                OriginSecondHalf =  otherCollectiveParticipant;
             }

             //this is the bit that does all the fakery...
             c.informParticipantBthatParticipantAIsTyping(sender,otherCollectiveParticipant);
             c.informParticipantBthatParticipantAIsTyping(OriginFirstHalf,p3);
             c.informParticipantBthatParticipantAIsTyping(OriginFirstHalf,p4);
             
             try{Thread.sleep(minDelayBetweenTurns.getValue()+r.nextInt(maxDelayBetweenTurns.getValue()-minDelayBetweenTurns.getValue())+splitUtt.length()*this.avgTypingTimePerChar.getValue());}catch(Exception e){e.printStackTrace();}
             c.sendArtificialTurnFromApparentOriginToRecipient(sender,otherCollectiveParticipant,(String)split.elementAt(0));  
             c.sendArtificialTurnFromApparentOriginToRecipient(OriginFirstHalf,p3,(String)split.elementAt(0));
             c.sendArtificialTurnFromApparentOriginToRecipient(OriginFirstHalf,p4,(String)split.elementAt(0));
             
             
             c.informParticipantBthatParticipantAIsNotTypingAndStatusIsOK(sender,otherCollectiveParticipant);
             c.informParticipantBthatParticipantAIsNotTypingAndStatusIsOK(OriginFirstHalf, p3);
             c.informParticipantBthatParticipantAIsNotTypingAndStatusIsOK(OriginFirstHalf, p4);
           
             try{Thread.sleep(minDelayBetweenTurns.getValue()+r.nextInt(maxDelayBetweenTurns.getValue()-minDelayBetweenTurns.getValue()));}catch(Exception e){}
             c.informParticipantBthatParticipantAIsTyping(sender,otherCollectiveParticipant);
             c.informParticipantBthatParticipantAIsTyping(OriginSecondHalf,p3);
             c.informParticipantBthatParticipantAIsTyping(OriginSecondHalf,p4);
             
             try{
                 long timeToSleep = minDelayBetweenTurns.getValue()+r.nextInt(maxDelayBetweenTurns.getValue()-minDelayBetweenTurns.getValue())+splitUtt.length()*this.avgTypingTimePerChar.getValue()*2;
                 System.err.println("timeToSleep is"+timeToSleep);
                 System.err.println("avgtimeperchar is"+avgTypingTimePerChar.getValue());
                 Thread.sleep(timeToSleep);
             }catch(Exception e){e.printStackTrace();}
             c.sendArtificialTurnFromApparentOriginToRecipient(sender,otherCollectiveParticipant,(String)split.elementAt(1));
             c.sendArtificialTurnFromApparentOriginToRecipient(OriginSecondHalf,p3,(String)split.elementAt(1));
             c.sendArtificialTurnFromApparentOriginToRecipient(OriginSecondHalf,p4,(String)split.elementAt(1));
             
             c.informParticipantBthatParticipantAIsNotTypingAndStatusIsOK(sender,otherCollectiveParticipant);
             c.informParticipantBthatParticipantAIsNotTypingAndStatusIsOK(OriginSecondHalf,p3);
             c.informParticipantBthatParticipantAIsNotTypingAndStatusIsOK(OriginSecondHalf,p4);
            
                     
             pCountdownToIntervention.setValue(pCountdownToInterventionMax.getValue());
             expSettings.generateParameterEvent(pCountdownToIntervention);
             c.resetFromIsTypingtoNormalChatAllAllowedParticipants(super.getIsTypingTimeOut());
             }     
        
        else{
           c.sendLabelDisplayToAllowedParticipantsFromApparentOrigin(sender,"Status: OK",false);
           c.relayTurnToAllOtherParticipants(sender,mct);
           c.resetFromIsTypingtoNormalChatAllAllowedParticipants(super.getIsTypingTimeOut());
       }
       
    }
    
    @Override
    public void processKeyPress(Participant sender,MessageKeypressed mkp){
        //c.informIsTypingToAllowedParticipants(sender);//,mkp);
    }
    
    @Override
    public void processWYSIWYGTextInserted(Participant sender,MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp){
        //need to sort out istyping
        if(pCountdownToIntervention.getValue()<0){
             
        }else{ 
            c.informIsTypingToAllowedParticipants(sender);
        }
            
       
       
       
    }
    
    
    
    //Participant typist = null;
    //Participant priorTypist = null;
    //boolean mostRecentTurnEndedWithNewLine = true;
    //boolean isFirstTurn =true;
    
   
   
   
    
   
   
    // counter =0;

    Participant p1,p2,p3,p4;
    
    @Override
    public void participantJoinedConversation(Participant p) {
        super.participantJoinedConversation(p);
        if(p1==null)p1=p;
        else if(p2==null)p2=p;
        else if(p3==null)p3=p;
        else if(p4==null)p4=p;
    }
    
    
    
    
    
    
    
    
    
    public static ExperimentSettings getDefaultSettings() {
       
        Vector v = new Vector();
        StringParameter sp;
        StringParameterFixed spf;
        IntParameter ip;
        sp = new StringParameter("Experiment ID","CCONFIDENCE4WAY");
        v.addElement(sp);
        Vector spv = new Vector();
        
        ip = new IntParameter("Number of participants per conversation",4);
        v.addElement(ip);
         spf = new StringParameterFixed("Chat tool interface",spv,"Formulate revise then send. Single or multiple windows");
        v.addElement(spf);
        
        ip = new IntParameter("Number of windows per chat tool",1);
        v.addElement(ip);
       
              
        
        
        spf = new StringParameterFixed("Window numbering policy",spv,"ONEWINDOWENABLED");
        v.addElement(spf);
        
         StringParameterFixed bckg = new StringParameterFixed("Background colour","BLACK"); v.addElement(bckg);
         StringParameterFixed sfTextColour  = new StringParameterFixed("Text colour self",spv,"WHITE");           v.addElement(sfTextColour);       
         StringParameterFixed otherTextColour1  = new StringParameterFixed("Text colour other 1",spv,"YELLOW");      v.addElement(otherTextColour1);
         StringParameterFixed otherTextColour2  = new StringParameterFixed("Text colour other 2",spv,"BLUE");     v.addElement(otherTextColour2);
         StringParameterFixed otherTextColour3  = new StringParameterFixed("Text colour other 3",spv,"GREEN");    v.addElement(otherTextColour3);
         StringParameterFixed otherTextColour4  = new StringParameterFixed("Text colour other 4",spv,"CYAN");     v.addElement(otherTextColour4);
         StringParameterFixed otherTextColour5  = new StringParameterFixed("Text colour other 5",spv,"YELLOW");   v.addElement(otherTextColour5);
                        
         IntParameter ipr;
         ipr = new IntParameter("Width of main window",(Integer)400);         v.addElement(ipr);
         ipr = new IntParameter("Height of main window",(Integer)150);        v.addElement(ipr);
         ipr = new IntParameter("Width of text entry window",(Integer)120);   v.addElement(ipr);
         ipr = new IntParameter("Height of text entry window",(Integer)50);  v.addElement(ipr);
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
