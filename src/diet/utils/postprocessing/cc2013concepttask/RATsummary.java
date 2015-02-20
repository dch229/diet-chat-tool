package diet.utils.postprocessing.cc2013concepttask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PPAT
 */
public class RATsummary {
    public static void createRATsummary() throws FileNotFoundException, IOException, ClassNotFoundException{
        File filename = new File("C:/Users/Chris/Desktop/ExperimentalData/Saved experimental data/");
        String directoryForOutput = "C:/Users/Chris/Desktop/ExperimentalData/Saved experimental data/";
        
        //File filename = new File("C:/Users/PPAT/Desktop/Saved experimental data/");
        //String directoryForOutput = "C:/Users/PPAT/Desktop/Saved experimental data/";
        String[] RATwords = new String[] {"horse","eight","show","river","fur","print","safety","fish","high","cadet","shock","rain","board","tooth","fight","spoon","note","back"};
        String[] RATwordcat = new String[] {"practice","practice","easy","easy","easy","easy","easy","easy","easy","easy","hard","hard","hard","hard","hard","hard","hard","hard"};
        String[][] RATsolutions = new String[][] {{"race","racing"},{"figure"},{"boat"},{"bank"},{"coat"},{"blue"},{"pin"},{"gold"},{"school","court"},{"space"},{"after"},{"acid"},{"switch"},{"sweet"},{"gun"},{"table"},{"key"},{"side","door"}};
        
        ArrayList<String> IndID = new ArrayList<String>();
        ArrayList<String> GroupID = new ArrayList<String>();
        //ArrayList<String> singleton = new ArrayList<String>();
        ArrayList<ArrayList<String>> GroupSolution = new ArrayList<ArrayList<String>>();
        
        int correctAnswer = 0, correctAtTime = 999999;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String myDateString = sdf.format(new Date());
        String strLine, subfileID = "", RATstrLine;
        BufferedWriter outInd=new BufferedWriter(new FileWriter(directoryForOutput+File.separator+"RAT_Ind_Overview_"+myDateString+".txt"));
        BufferedWriter outGroup=new BufferedWriter(new FileWriter(directoryForOutput+File.separator+"RAT_Gp_Overview_"+myDateString+".txt"));
        BufferedReader groupDetails = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(System.getProperty("user.dir")+File.separator+"experimentresources"+"/ConceptTaskTxt/GroupDetails.csv"))));

        boolean solution = false;
        
        while ((strLine = groupDetails.readLine())!=null){
             String[] lineSplit = strLine.split("[,]");
             if(lineSplit[0].matches("Foldername")){
                 outInd.write("Ind|");
                 for (int i=0; i < RATwords.length; i++){
                     outInd.write(RATwords[i]+"|"+RATwords[i]+"Time|");
                 }
                 outInd.write("easyCorrect|easyTime|easyAve|hardCorrect|hardTime|hardAve|totCorrect|totTime|totAve|\n");
                 //this is the first line: print out some output headings
             }else{
                 //this is any other line - so take foldername and do something for a file for each value in RATwords
                 if(!IndID.contains(lineSplit[5])){
                     IndID.add(lineSplit[5]);
                     outInd.write(lineSplit[5]+"|");
                 }
                 ArrayList<String> singleton = new ArrayList<String>();
                 if(!GroupID.contains(lineSplit[1])){
                     GroupID.add(lineSplit[1]);
                     singleton.add(lineSplit[1]);
                     GroupSolution.add(singleton);
                 }else{
                     singleton = GroupSolution.get(GroupID.indexOf(lineSplit[1]));
                     //System.out.println(singleton);
                 }
                 
                 int easy=0,hard=0,easyTime=0,hardTime=0,totCorrect,totTime;
                 double easyIndAve=0,hardIndAve=0,totIndAve;
                 for (int i=0; i < RATwords.length; i++){
                     File dataFile=new File(filename+File.separator+"Overview_"+RATwords[i]+".txt");
                     BufferedReader RATfile = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(dataFile))));
                     while ((RATstrLine = RATfile.readLine())!=null){
                        String[] RATlineSplit = RATstrLine.split("[|]");
                        for (int j=0; j < RATsolutions[i].length; j++){
                            
                            if(RATlineSplit[12].toLowerCase().contains(RATsolutions[i][j])){
                                //needs to not be case sensitive
                                solution = true;
                            }
                        }
                        if(solution){
                            if(lineSplit[5].contains(RATlineSplit[0])){//this should be the right dyad
                                if(singleton.contains(lineSplit[1])){//GROUP ID IS IN
                                    //how to tell it's first solution for this value.
                                    //need to know the size of the sublist
                                   if(singleton.size()-1==i){
                                        singleton.add(RATlineSplit[2]);
                                        GroupSolution.set(GroupID.indexOf(lineSplit[1]),singleton);
                                    }else{//there is already a value there
                                       //System.out.println("Other: "+i+": "+RATlineSplit[2]+singleton);
                                       if(Integer.parseInt(singleton.get(i+1))>Integer.parseInt(RATlineSplit[2])){
                                           singleton.remove(i+1); 
                                           singleton.add(i+1, RATlineSplit[2]);//hopefully this is the time
                                            //System.out.println("Other: "+singleton);
                                            GroupSolution.set(GroupID.indexOf(lineSplit[1]),singleton);
                                        }
                                    }       
                                }
                                
                                if(lineSplit[4].equals(RATlineSplit[4])){//and right individual
                                
                                    correctAnswer = 1;
                                    if(Integer.parseInt(RATlineSplit[2])<correctAtTime){
                                        correctAtTime = Integer.parseInt(RATlineSplit[2]);
                                    }
                                }
                            }
                            solution=false;
                        }
                     }
                     //write to outputfile here individuals
                     outInd.write(correctAnswer+"|"+correctAtTime+"|");
                     //add to totals here we are within i.
                     if(RATwordcat[i].equals("easy")){
                         easy = easy+correctAnswer;
                         if(correctAtTime != 999999){
                             easyTime = easyTime+correctAtTime;
                         }
                     }else if(RATwordcat[i].equals("hard")){
                         hard = hard+correctAnswer;
                         if(correctAtTime != 999999){
                             hardTime = hardTime+correctAtTime;
                         }
                     }
                     
                     correctAnswer = 0;
                     correctAtTime = 999999;
                     if(singleton.size()-1==i){
                        singleton.add("999999");
                        GroupSolution.set(GroupID.indexOf(lineSplit[1]),singleton);
                     }
                 }
                //write out totals here
                totCorrect=easy+hard;
                totTime=easyTime+hardTime;
                if(easy!=0){
                    easyIndAve= (double) easyTime/easy;
                }else{
                    easyIndAve=0;
                }
                if(hard!=0){
                    hardIndAve= (double) hardTime/hard;
                }else{
                    hardIndAve=0;
                }
                if(totCorrect!=0){
                    totIndAve= (double) totTime/totCorrect;
                }else{
                    totIndAve=0;
                }
                outInd.write(easy+"|"+easyTime+"|"+easyIndAve+"|"+hard+"|"+hardTime+"|"+hardIndAve+"|"+totCorrect+"|"+totTime+"|"+totIndAve+"\n");
                easy = 0;
                easyTime=0;
                hard=0;
                hardTime=0;
                outInd.flush();
             }
        }
        
        //write to group outputfile here
        int solved = 0, timeSolved = 0, easyCorrect = 0, hardCorrect = 0,easyTimeSolved=0,hardTimeSolved=0;
        double easyAve=0,hardAve=0, totalAve=0;
        //System.out.println("Output: "+GroupSolution);
        outGroup.write("GroupID");
        for(int k=0; k<RATwords.length;  k++){
            outGroup.write("|"+RATwords[k]+"Correct|"+RATwords[k]+"Time");
        }
        outGroup.write("|easyCorrect|easyTotalTime|EasyAverageTime|hardCorrect|hardTotalTime|hardAverageTime|totalCorrect|TotalTime|AverageTime\n");
        for(int i=0; i<GroupSolution.size();  i++){
            for(int j=0; j<GroupSolution.get(i).size();  j++){
                if(GroupSolution.get(i).get(j).equals("999999")){
                    solved = 0;
                }else{
                    solved = 1;
                    if(j!=0){
                        timeSolved = Integer.parseInt(GroupSolution.get(i).get(j));
                    }
                }
                if(j==0){
                    outGroup.write(GroupSolution.get(i).get(j) +"|");
                }else{
                    outGroup.write(solved+"|"+GroupSolution.get(i).get(j) +"|");
                    if(RATwordcat[j-1].equals("easy")){
                        easyCorrect = easyCorrect+solved;
                        easyTimeSolved = easyTimeSolved + timeSolved;
                    }else if(RATwordcat[j-1].equals("hard")){
                        hardCorrect = hardCorrect+solved;
                        hardTimeSolved = hardTimeSolved + timeSolved;
                    }  
                }
                timeSolved = 0;
            }
            if(easyCorrect != 0){
                easyAve = (double) easyTimeSolved/easyCorrect;
            }else{
                easyAve = 0;
            }
            if(hardCorrect != 0){
                hardAve = (double) hardTimeSolved/hardCorrect;
            }else{
                hardAve = 0;
            } 
            //also calculate totals
            solved = easyCorrect+hardCorrect;
            timeSolved = easyTimeSolved+hardTimeSolved;
            if(solved != 0){
                totalAve = (double) timeSolved/solved;
            }else{
                totalAve = 0;
            }
                    
            outGroup.write(easyCorrect+"|"+easyTimeSolved+"|"+easyAve+"|"+hardCorrect+"|"+hardTimeSolved+"|"+hardAve+"|"+solved+"|"+timeSolved+"|"+totalAve+"\n");
            easyCorrect = 0;
            easyTimeSolved = 0;
            hardCorrect = 0;
            hardTimeSolved = 0;
            solved = 0;
            timeSolved = 0;
        }
        outGroup.flush();
    }   
        
        
    
    
    public static void main(String[] args){
        try {
            createRATsummary();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SplitConceptTurns.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SplitConceptTurns.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SplitConceptTurns.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
