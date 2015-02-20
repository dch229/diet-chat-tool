package diet.server.ConversationController;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageKeypressed;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
import diet.message.MessageWYSIWYGTextSelectionFromClient;
import diet.server.Participant;
import java.awt.Color;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;




public class CC2013CONCEPTTASK extends DefaultConversationController{

//REDUCED SETS FOR PILOTS. Yes, okay, so I shouldn't be hard coding this stuff, but frankly, life's too short...
//String[] RATwords = new String[] {"show\nlife\nrow","river\nnote\naccount","note\nchain\nmaster","back\nstep\nscreen"};  
//String[] AUTwords = new String[] {"brick\n\ncommon use: building block","pencil\n\ncommon use: to write"};
//String[] CATwords = new String[] {"animals"};

String[] Task = new String[] {"AUT","CAT","RAT"};
String[] RATwords = new String[] {"show\nlife\nrow","river\nnote\naccount","fur\nrack\ntail","print\nberry\nbird","safety\ncushion\npoint","fish\nmine\nrush","high\ndistrict\nhouse","cadet\ncapsule\nship","shock\nshave\ntaste","rain\ntest\nstomach","board\nblade\nback","tooth\npotato\nheart","fight\ncontrol\nmachine","spoon\ncloth\ncard","note\nchain\nmaster","back\nstep\nscreen"};  
String[] AUTwords = new String[] {"brick\n\ncommon use: building block","car tyre\n\ncommon use: to protect a car wheel","barrel\n\ncommon use: for storage","pencil\n\ncommon use: to write"};
String[] CATwords = new String[] {"animals","countries"};
String[] Instructions = new String[] {"","",""};
int WordsSet,PracticeSet,TaskIteration = 0;
int[] TimeMS = new int[]{45000,0,0};
int[] Iterations = new int[]{1,0,0};
int serverColor = 7;
String Phase = "practice";
HashMap Screen = new HashMap();


Timer timer = new Timer(TimeMS[TaskIteration], new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent arg0) {
        Vector v = c.getParticipants().getAllParticipants();
            if(Phase.equals("practice")){
                if(WordsSet == 0){
                    if(Task[TaskIteration].equals("RAT")){
                        c.sendArtificialTurnToAllParticipants("Time up!", 0, serverColor, "");
                        c.chageWebpageOfAllParticipants("window1", "A solution to the first set of words is RACE\nRACE horse\nhuman RACE\ndrag RACE\nThe second set of practice words will appear when all of you have typed /next");
			WordsSet = 1;
                        for(int i=0;i<v.size();i++){
                            Participant p2 = (Participant)v.elementAt(i);
                            Screen.put(p2, "taskready");//3rd
                        }
                    }else if(Task[TaskIteration].equals("AUT")){
                        c.sendArtificialTurnToAllParticipants("Time up!", 0, serverColor, "");
                        c.chageWebpageOfAllParticipants("window1", "The common use for a newspaper is for reading.\n\nPossible other uses include\nfor swatting flies\nto line drawers\nto make a paper hat\nand so on\n\n\nIf you have any questions about this task, please ask the experimenter now.\n\nOtherwise the test will begin when all of you have typed /next");
                        Phase = "test";
                        WordsSet = 0;
                        for(int i=0;i<v.size();i++){
                            Participant p2 = (Participant)v.elementAt(i);
                            Screen.put(p2, "taskready");//3rd
                        }
                    }else if(Task[TaskIteration].equals("CAT")){
                        c.sendArtificialTurnToAllParticipants("Time up!", 0, serverColor, "");
                        c.chageWebpageOfAllParticipants("window1", "If you have any questions about this task, please ask the experimenter now.\n\nOtherwise the test will begin when all of you have typed /next");
                        Phase = "test";
                        WordsSet = 0;
                        for(int i=0;i<v.size();i++){
                            Participant p2 = (Participant)v.elementAt(i);
                            Screen.put(p2, "taskready");//3rd
                        }
                    }
                    timer.stop();
                }else if(WordsSet == 1){
                    if(Task[TaskIteration].equals("RAT")){
                        c.sendArtificialTurnToAllParticipants("Time up!", 0, serverColor, "");
                        c.chageWebpageOfAllParticipants("window1", "A solution to the second set of words is FIGURE\nFIGURE eight\nFIGURE skate\nstick FIGURE\n\nIf you have any questions about this task, please ask the experimenter now.\n\nThe first set of test words will appear when all of you have typed /next");
                    }
                    timer.stop();
                    Phase = "test";
                    WordsSet = 0;
                    for(int i=0;i<v.size();i++){
                        Participant p2 = (Participant)v.elementAt(i);
			Screen.put(p2, "taskready");//third
                    }
                }
            }else if(Phase.equals("test")){
                if(WordsSet >= Iterations[TaskIteration]){
                    if(TaskIteration == 2){
                        c.chageWebpageOfAllParticipants("window1", "YOU HAVE COMPLETED ALL TASKS.\n\nThankyou for your time\n\nPlease return to the briefing area, where the experimenter will answer any questions you may have.");
                        c.sendArtificialTurnToAllParticipants("That's all folks!", 0, serverColor, "");
                    }else{
                        c.sendArtificialTurnToAllParticipants("Next task", 0, serverColor, "");
                        c.chageWebpageOfAllParticipants("window1", "YOU HAVE COMPLETED THE TASK\n\nThe instructions for the next task will appear when all of you have typed /next");
                        TaskIteration++;
                        WordsSet = 0;
                        Phase = "practice";
                        for(int i=0;i<v.size();i++){
                            Participant p2 = (Participant)v.elementAt(i);
                            Screen.put(p2, "task");//might need a third thing
                        }
                    }
                    timer.stop();
                }else{
                    if(Task[TaskIteration].equals("RAT")){
                        c.chageWebpageOfAllParticipants("window1", "Your next set of words are:\n\n"+RATwords[WordsSet]);
                        c.sendArtificialTurnToAllParticipants(RATwords[WordsSet], 0, serverColor, "");
                    }else if(Task[TaskIteration].equals("AUT")){
                        c.chageWebpageOfAllParticipants("window1", "Your next item is:\n\n"+AUTwords[WordsSet]); 
                        c.sendArtificialTurnToAllParticipants(AUTwords[WordsSet].substring(0, AUTwords[WordsSet].indexOf("\n")), 0, serverColor, "");
                    }else if(Task[TaskIteration].equals("CAT")){
                        c.chageWebpageOfAllParticipants("window1", "Please list words in the category:\n\n"+CATwords[WordsSet]); 
                        c.sendArtificialTurnToAllParticipants(CATwords[WordsSet], 0, serverColor, "");
                    }
                    timer.start();
                    WordsSet++;
                }  
            }
        }
	});

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
        this.resetTimersCorrectly(sender, mct);
    }
    
    public void resetTimersCorrectly(Participant sender,MessageChatTextFromClient mct){
        if(mct.getText().equals("/next") & timer.isRunning()==false){//ONLY COMMAND I AM ALLOWING THEM, and only when timer stopped
            Vector v = c.getParticipants().getAllParticipants();
            if(Screen.containsValue("intro")){//If any person has not yet acknowledged intro screen
                for(int i=0;i<v.size();i++){
                    Participant p2 = (Participant)v.elementAt(i);
                    if(p2 == sender & Screen.get(p2).equals("intro")){
                        c.changeWebpageTextAndColour(sender, "window1", "Thankyou. Please wait for all participants to acknowledge the instructions", "WHITE", "BLACK");
                        Screen.put(p2, "taskready");
                    }
                }
                for(int j=0;j<3;j++){
                    if(Task[j].equals("RAT")){//TESTING
			Instructions[j]="LINKED WORDS TASK:\n\nYou will see three stimulus words in this window.\n\nYou should attempt to generate a fourth word, which, when combined with each of the three stimulus words, would result in word pairs that make up a common compound word or phrase.\n\nYou should use the chat window to discuss your ideas with each other.\nPlease don't forget to type anything you think of, not just possible solutions.\n\nEach set of three words will appear in this window for 45 seconds.\n\nThe first two sets of words will be practice sets followed by solutions.\n\nPlease type /next to acknowledge that you have read and understood these instructions - the practice words will appear when all participants have done so.";
			TimeMS[j] = 45000;//45000;
			Iterations[j] = RATwords.length;
			Phase = "practice";
                    }else if(Task[j].equals("AUT")){
			Instructions[j]="USES TASK:\n\nYou will be asked to produce as many different uses as you can think of, which are different from the normal use, for a number of common objects.\n\nYou will have five minutes on each object.\n\nIts common use will be stated but you are to try to produce possible uses which are different from the normal one and different in kind from each other.\n\nYou should use the chat tool to discuss your ideas.\n\nPlease type your reasoning, as well as any uses for the item.\n\nPlease type /next to acknowledge that you have read and understood these instructions - the practice words will appear when all participants have done so.";
			TimeMS[j] = 300000;//300000;
			Iterations[j] = AUTwords.length;
                    }else if(Task[j].equals("CAT")){
			Instructions[j]="CATEGORY TASK:\n\nYou will be asked to produce as many items as you can think of in a given category.\n\nYou will have five minutes on each category.\n\nYou should use the chat tool to discuss your ideas.\n\nPlease type any items in the category onto a new line.\n\nPlease type /next to acknowledge that you have read and understood these instructions - the practice category will appear when all participants have done so.";
			TimeMS[j] = 300000;//300000;
			Iterations[j] = CATwords.length;
                    }
                }
            }else if(Screen.containsValue("task")){
                for(int i=0;i<v.size();i++){
                    Participant p2 = (Participant)v.elementAt(i);
                    if(p2 == sender & Screen.get(p2).equals("task")){
                        Screen.put(p2, "taskready");
                        //c.changeClientInterface_clearMaintextEntryWindow(p2);
                    }
                }
            }else if(Screen.containsValue("taskready")){
                for(int i=0;i<v.size();i++){
                    Participant p2 = (Participant)v.elementAt(i);
                    if(p2 == sender & Screen.get(p2).equals("taskready")){
                        Screen.put(p2, "taskaccept");
                        timer.stop();
                        //c.changeClientInterface_clearMaintextEntryWindow(p2);
                    }
                }
            }

            timer.setInitialDelay(TimeMS[TaskIteration]);//set initial delay to correct value for task 
            //c.sendArtificialTurnToRecipient(sender, "Status is: "+Screen.toString(), 0);//debug message to check status
            
            
            if(!Screen.containsValue("intro")){//ALL HAVE ACCEPTED INTRO SCREEN - but will go into this after practise phase? ah, will it just flash up. Yes. Should restrict it then
		if(Phase.equals("practice") & Screen.containsValue("taskready") & !Screen.containsValue("task")){//its here
                    c.chageWebpageOfAllParticipants("window1", Instructions[TaskIteration]);//show relevant task instructions and wait for participants to agree
                    timer.stop();
		}
                if(Screen.containsValue("taskaccept") & !Screen.containsValue("task") & !Screen.containsValue("taskready")){//THEN EVERYONE HAS SAID OKAY TO TASK 
                    if(Phase.equals("practice") & WordsSet == 0){
                        for(int i=0;i<v.size();i++){//this bit clears all people's screens between tasks. Do we want it more regularly?
                            Participant p2 = (Participant)v.elementAt(i);
                            c.changeClientInterface_clearMainWindows(p2);
                        }
                        if(Task[TaskIteration].equals("RAT")){
                            c.chageWebpageOfAllParticipants("window1", "Your first practice set of words are:\n\nhorse\nhuman\ndrag");
                            c.sendArtificialTurnToAllParticipants("horse\nhuman\ndrag", 0, serverColor, "");
                        }else if(Task[TaskIteration].equals("AUT")){
                            c.chageWebpageOfAllParticipants("window1", "Your practice item is:\n\nnewspaper\n\nThe common use for a newspaper is for reading."); 
                            c.sendArtificialTurnToAllParticipants("newspaper", 0, serverColor, "");
                        }else if(Task[TaskIteration].equals("CAT")){
                            c.chageWebpageOfAllParticipants("window1", "Your practice category is:\n\nfruit and vegetables\n\nYou have 5 minutes to name as many fruit and vegetables as possible"); 
                            c.sendArtificialTurnToAllParticipants("fruit and veg", 0, serverColor, "");
                        }
                    }else if(Phase.equals("practice") & WordsSet == 1){
                        if(Task[TaskIteration].equals("RAT")){
                            c.chageWebpageOfAllParticipants("window1", "Your second practice set of words are:\n\neight\nskate\nstick");
                            c.sendArtificialTurnToAllParticipants("eight\nskate\nstick", 0, serverColor, "");
                        }
                    }
                    timer.start();
                    timer.setRepeats(false);
                }
            }
            if(Phase.equals("test") & Screen.containsValue("taskaccept") & !Screen.containsValue("task") & !Screen.containsValue("taskready")){
                if(WordsSet == 0){
                    if(Task[TaskIteration].equals("RAT")){
                        c.chageWebpageOfAllParticipants("window1", "Your first set of words is:\n\n"+RATwords[WordsSet]+"\n\nRemember, you have 45 seconds on each set of words and you should type any words as they come to you - not just the possible solutions");
                        c.sendArtificialTurnToAllParticipants(RATwords[WordsSet], 0, serverColor, "");
                    }else if(Task[TaskIteration].equals("AUT")){
                        c.chageWebpageOfAllParticipants("window1", "Your first item is:\n\n"+AUTwords[WordsSet]+"\n\nRemember, you have 5 minutes to come up with as many novel uses for the item as possible");
                        c.sendArtificialTurnToAllParticipants(AUTwords[WordsSet].substring(0, AUTwords[WordsSet].indexOf("\n")), 0, serverColor, "");
                    }else if(Task[TaskIteration].equals("CAT")){
                        c.chageWebpageOfAllParticipants("window1", "Your first category is:\n\n"+CATwords[WordsSet]+"\n\nRemember, you have 5 minutes to come up with as many words in the category as possible");
                        c.sendArtificialTurnToAllParticipants(CATwords[WordsSet], 0, serverColor, "");
                    }
                    timer.start();
                    WordsSet++;
                }
            }
        }
    }
    
    
    @Override
    public void participantJoinedConversation(Participant p) {
        c.displayNEWWebpage(p, "window1", "Instructions", "", 600, 800,false, false);
        c.changeWebpageTextAndColour(p, "window1", "We are investigating problem-solving using online text conversations<br><br>You will see instructions for a number of tasks in this window, and you should use the chat window to communicate about the tasks.<br><br>Please also discuss your reasoning in the chat window - we are interested in <b>HOW</b> people solve these tasks as well as what solutions they reach.<br><br>The chat window acts like common online messenger applications so that any other people in the conversation will also see what you have typed - please type something in it to test it now.<br><br>All tasks are automatically timed but between tasks you will be asked to type the command /next to indicate that you have read and undersood some instructions in this window<br><br>Please type /next to acknowledge these instructions", "BLACK", "WHITE");
        Screen.put(p, "intro");
        Phase = "Practice";
        
        Collections.shuffle(Arrays.asList(RATwords));
        Collections.shuffle(Arrays.asList(AUTwords));
        Collections.shuffle(Arrays.asList(CATwords));
        Collections.shuffle(Arrays.asList(Task));    
        
        c.changeClientInterface_AllColours(Color.white, Color.black, Color.red, Color.blue, Color.magenta, Color.orange, Color.gray, Color.green, Color.cyan, Color.cyan, 12);
        serverColor = c.getParticipants().getAllParticipants().size();

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
