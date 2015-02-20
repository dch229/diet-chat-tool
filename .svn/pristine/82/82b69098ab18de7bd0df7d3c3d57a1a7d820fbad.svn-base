package diet.server.ConversationController;
import diet.message.MessageChatTextFromClient;
import diet.server.Participant;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.Timer;




public class CC2013CONCEPTTASKaddPreviousPerson1 extends CC2013CONCEPTTASK{

//Participant p = new Participant(this.c, "previous", "previous");
int imported = 0;
int textFileQualifier = 1;
String directoryPath = System.getProperty("user.dir")+File.separator+"experimentresources"+"/ConceptTaskTxt/0013/";
List<Integer> l1 = new ArrayList<Integer>();
List<String> l2 = new ArrayList<String>();
List<Integer> l3 = new ArrayList<Integer>();
int iterate = 0;//TestTimes.length;
//(also need to know which word they are on.)

Timer fakeTurnTimer = new Timer(5000, new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent arg0) {
        c.sendArtificialTurnToAllParticipants(l2.get(iterate), 0, 2, "previous");
        iterate++;
        if(iterate >= l3.size()){
            fakeTurnTimer.stop();
            imported = 0;
            iterate=0;
        }else{
            fakeTurnTimer.setInitialDelay(l3.get(iterate));
            fakeTurnTimer.restart();
        }
    }
});
     
    @Override
    public void processLoop(){
        c.resetFromIsTypingtoNormalChatAllAllowedParticipants(super.getIsTypingTimeOut());      
        this.startTimerWithRightValues();
        
    }
    
    String textDirectory;
    
    public void startTimerWithRightValues(){
        if(imported == 0){
            if(Task[TaskIteration].equals("AUT") && Phase.equals("practice") && Screen.containsValue("taskready")){
                l1.clear();
                l2.clear();
                textDirectory = directoryPath+"newspaper"+textFileQualifier+".txt";
                importTimesAndText(textDirectory, 0, 1);
                imported = 1;
                resetl3();
            }else if(Task[TaskIteration].equals("AUT") && Phase.equals("test") && Screen.containsValue("taskready")){
                l1.clear();
                l2.clear();
                for (int j = 0; j<AUTwords.length; j++){
                    textDirectory = directoryPath+AUTwords[j].substring(0, AUTwords[j].indexOf("\n"))+textFileQualifier+".txt";
                    importTimesAndText(textDirectory, j, 300000);//need to add 300000 * j to each time
                }
                iterate = 0;
                imported = 1;
                resetl3();
            }else if(Task[TaskIteration].equals("CAT") && Phase.equals("practice") && Screen.containsValue("taskready")){
                l1.clear();
                l2.clear();
                textDirectory = directoryPath+"fruit and veg"+textFileQualifier+".txt";
                importTimesAndText(textDirectory, 0, 1);
                imported = 1;
                resetl3();
            }else if(Task[TaskIteration].equals("CAT") && Phase.equals("test") && Screen.containsValue("taskready")){
                l1.clear();
                l2.clear();
                for (int j = 0; j<CATwords.length; j++){
                    textDirectory = directoryPath+CATwords[j]+textFileQualifier+".txt";
                    importTimesAndText(textDirectory, j, 300000);//need to add 300000 * j to each time
                }
                iterate = 0;
                imported = 1;
                resetl3();
            }else if(Task[TaskIteration].equals("RAT") && Phase.equals("practice") && Screen.containsValue("taskready") && WordsSet == 0){
                l1.clear();
                l2.clear();
                textDirectory = directoryPath+"horse"+textFileQualifier+".txt";
                importTimesAndText(textDirectory, 0, 1);
                imported = 1;
                resetl3();
            }else if(Task[TaskIteration].equals("RAT") && Phase.equals("practice") && Screen.containsValue("taskready") && WordsSet == 1){
                l1.clear();
                l2.clear();
                textDirectory = directoryPath+"eight"+textFileQualifier+".txt";
                importTimesAndText(textDirectory, 0, 1);
                imported = 1;
                resetl3();
            }else if(Task[TaskIteration].equals("RAT") && Phase.equals("test") && Screen.containsValue("taskready")){
                l1.clear();
                l2.clear();
                for (int j = 0; j<RATwords.length; j++){
                    textDirectory = directoryPath+RATwords[j].substring(0, RATwords[j].indexOf("\n"))+textFileQualifier+".txt";
                    importTimesAndText(textDirectory, j, 45000);//need to add 300000 * j to each time
                }
                iterate = 0;
                imported = 1;
                resetl3();
            }else{
                fakeTurnTimer.stop();
            }
        }else if(imported == 1 && Screen.containsValue("taskaccept")){
            if(!l3.isEmpty()){
                fakeTurnTimer.setInitialDelay(l3.get(0));
                fakeTurnTimer.start();
            }
            imported = 2;
        }    
    }        
    
    public void resetl3(){
        l3.clear();
        l3.add(0, l1.get(0));
            for (int i = 1; i < l1.size(); i++) {
                 l3.add(i, l1.get(i)-l1.get(i-1));
            }
    }
    
    public void importTimesAndText(String fileImport, int NumberIteration, int TimeMultiple){
        try {
            BufferedReader s = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(fileImport)))); // Reading file using Scanner
            String strLine;// = new String;
            while ((strLine = s.readLine())!=null) {    
                String[] lineSplit = strLine.split("[|]");
                l2.add(lineSplit[1]); 
                l1.add(Integer.parseInt(lineSplit[0])+(NumberIteration * TimeMultiple));
            }
            
            
            s.close(); // close the file
            //System.out.println(l1);
            //System.out.println(l2);

        } catch (Exception e) {     
            e.printStackTrace();    
        }
    }
}
