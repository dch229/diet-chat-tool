/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.utils.postprocessing.cc2013concepttask;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chris
 */
public class AUTplusSCORE {
    
    public static void inputMessageDat() throws FileNotFoundException, IOException, ClassNotFoundException{
        //String directoryForOutput = "C:/Users/Chris/Desktop/ExperimentalData/Saved experimental data/";
        //String directoryForOutput = "C:/Users/PPAT/Desktop/Saved experimental data/";
        String directoryForOutput = "E:/ExperimentalData/Saved experimental data/";
        
        File filename = new File(directoryForOutput);
        
        //String directoryForSCoRE = "D:/QMUL/AUTkappa/";
        String directoryForSCoRE = "E:/ExperimentalData/AUT/";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String myDateString = sdf.format(new Date());
        
        //File filename = new File("C:/Users/PPAT/Desktop/Saved experimental data/");
        //String directoryForOutput = "C:/Users/PPAT/Desktop/Saved experimental data/";
        BufferedReader AUTorder = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(filename+"/AUTorder.txt"))));
        String strLine, strLine2, strLine3, strLine4;
        FileWriter currentFile = new FileWriter(directoryForOutput+File.separator+"AUT"+myDateString+".txt");
        currentFile.write("convo|condition|item|itemNum|linenum|ParticipantID|Sender|Type|ClientTime|Onset|Enter|Typingti|Speed|AppOrig.|Text|Recipients|Blocked|KDels|DDels|DIns|DDels*N|DIns*N|TimeInItem|lineNum2|Use|Follows|FollowsItem|FollowsWho|ItemSame|FollowsSelf|Similarity|Complexity\n");
        
        while ((strLine = AUTorder.readLine())!=null){
            String[] lineSplit = strLine.split("[\t]");
            int lineNum = 1;
            File SCoREFile=new File(directoryForSCoRE+File.separator+"chris-AUT-"+lineSplit[0]+"AUT.exp");
            File SCoREFile2=new File(directoryForSCoRE+File.separator+lineSplit[0]+"AUT.txt");
            
            if(!SCoREFile.exists()){
                SCoREFile=new File(directoryForSCoRE+File.separator+"raquel-AUT-"+lineSplit[0]+"AUT.exp");                    
            }
            if(!SCoREFile2.exists()){
                System.out.println(lineSplit[0]+".txt");
            }
            
            BufferedReader SCoREfile = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(SCoREFile))));
            ArrayList<ArrayList<String>> AUTscoreArray = new ArrayList<ArrayList<String>>();
            
            while ((strLine3 = SCoREfile.readLine())!=null){
                ArrayList<String> AUTSplit = new ArrayList<String> (Arrays.asList(strLine3.split("[\t]")));
                AUTscoreArray.add(AUTSplit);
                if(Integer.parseInt(AUTSplit.get(0))!=lineNum){
                   System.out.println(lineSplit[0]+"; "+lineNum+"; "+AUTSplit); 
                }
                lineNum++;
            }
            SCoREfile.close();
            lineNum = 1;
            
            BufferedReader SCoREfile2 = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(SCoREFile2))));
            ArrayList<ArrayList<String>> AUTscoreArray2 = new ArrayList<ArrayList<String>>();
            
            while ((strLine4 = SCoREfile2.readLine())!=null){
                ArrayList<String> AUTSplit2 = new ArrayList<String> (Arrays.asList(strLine4.split("[|]")));
                AUTscoreArray2.add(AUTSplit2);
            }
            SCoREfile2.close();

            try{
               
                for(int i=2; i<lineSplit.length; i++){
                    
                    File turnsFile=new File(directoryForOutput+File.separator+lineSplit[0]+File.separator+lineSplit[i]+".txt");
                    BufferedReader AUTfile = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(turnsFile))));
                    
                    while ((strLine2 = AUTfile.readLine())!=null){
                        //String[] lineSplit2 = strLine2.split("[|]");
                        int i2 = i-2; 
                        currentFile.write(lineSplit[0]+"|"+lineSplit[1]+"|"+lineSplit[i]+"|"+i2+"|"+lineNum+"|"+strLine2);
                        for(int j=0; j<AUTscoreArray.get(lineNum-1).size(); j++){
                            currentFile.write("|"+AUTscoreArray.get(lineNum-1).get(j));
                            if(j==2&&!AUTscoreArray.get(lineNum-1).get(j).isEmpty()){
                                int lineFollows = Integer.parseInt(AUTscoreArray.get(lineNum-1).get(j));
                                //System.out.println("AUT out: "+AUTscoreArray2.get(lineFollows-1).get(0));
                                currentFile.write("|"+AUTscoreArray2.get(lineFollows-1).get(0)+"|"+AUTscoreArray2.get(lineFollows-1).get(2));
                                if(AUTscoreArray2.get(lineFollows-1).get(0).equals(AUTscoreArray2.get(lineNum-1).get(0))){
                                    currentFile.write("|same");
                                }else{
                                    currentFile.write("|diff");
                                }
                                if(AUTscoreArray2.get(lineFollows-1).get(2).equals(AUTscoreArray2.get(lineNum-1).get(2))){
                                    currentFile.write("|self");
                                }else{
                                    currentFile.write("|other");
                                }

                                
                                //need to put self/other same/other
                            }else if(j==2){
                                currentFile.write("||||");
                            }
                        }
                        
                        currentFile.write("\n");
                        lineNum++;
                        
                    }
                }
                currentFile.flush();        
                lineNum=1;
            }catch (Exception exc){
                System.err.println("Error: " + exc.getMessage());
            }
        }
    }

    public static void main(String[] args){
        try {
            inputMessageDat();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AUTplusSCORE.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AUTplusSCORE.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AUTplusSCORE.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
