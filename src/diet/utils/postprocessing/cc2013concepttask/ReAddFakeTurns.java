/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.utils.postprocessing.cc2013concepttask;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chris
 */
public class ReAddFakeTurns {
    
    public static void inputMessageDat() throws FileNotFoundException, IOException, ClassNotFoundException{
        File filename = new File("C:/Users/Chris/Desktop/ExperimentalData/Error/robynTurns.txt");
        String directoryForOutput = "C:/Users/Chris/Desktop/ExperimentalData/Error/";
        FileInputStream fis = new FileInputStream(filename);
        int textFileQualifier = 0;
        int newDels = 0, currentLength = 0;
        String directoryPath = System.getProperty("user.dir")+File.separator+"experimentresources"+"/ConceptTaskTxt/0012/";
        DataInputStream in = new DataInputStream(fis);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        FileWriter outputFile = new FileWriter(directoryForOutput+"robynAllTurns.txt");
        String strLine, textSofar="";
        List<Long> l1 = new ArrayList<Long>();
        List<String> l2 = new ArrayList<String>();
        //String partID = ", dfobrien@hotmail.co.ukDave";
        //long segStart = 1364227926187L, totStart = 1364227926187L, segEnd;
        String partID = "robynmclellan@live.comRobyn123";
        long segStart = 1364227886281L, totStart = 1364227886281L, segEnd;
               
        while ((strLine = br.readLine())!=null){
            try{
                String[] lineSplit = strLine.split("[|]");
                int pipes = 0;
                for (int i=0; i < strLine.length(); i++){
                    if (strLine.charAt(i) == '|'){
                        pipes++;
                    }
                }
                if(pipes>=9 && !lineSplit[2].equals("Default") && !lineSplit[2].equals("server")){
                    currentLength = lineSplit[8].length();
                }else if(pipes>=9 && lineSplit[2].equals("Default")){
                    newDels = currentLength - lineSplit[9].length();
                    currentLength = 0;
                    strLine = lineSplit[0]+"|"+lineSplit[1]+"|"+lineSplit[2]+"|"+lineSplit[3]+"|"+lineSplit[4]+"|"+lineSplit[5]+"|"+lineSplit[6]+"|"+lineSplit[7]+"|"+lineSplit[8]+"|"+lineSplit[9]+"|"+lineSplit[10]+"|"+lineSplit[11]+"|"+newDels+"|"+newDels+"|"+lineSplit[14]+"|"+newDels+"|"+lineSplit[16]+"|";
                }else{
                    newDels = 0;
                }
                if(lineSplit[0].equals("server")&&!lineSplit[9].equals("Time up!")&&!lineSplit[9].equals("Next task")){
                     //get the values for the fake turns text file
                    long timeOfServer = Long.parseLong(lineSplit[3]);
                    long timeOfServerShort = Long.parseLong(lineSplit[4]);
                    String textFileID = directoryPath+lineSplit[9]+textFileQualifier+".txt";
                    BufferedReader s = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(textFileID))));                    String strLine2  ;
                    l2.clear();
                    l1.clear();
                    while ((strLine2 = s.readLine())!=null) {    
                        String[] lineSplit2 = strLine2.split("[|]");
                        long newTimeOfServer = Long.parseLong(lineSplit2[0])+timeOfServer;
                        long newTimeOfServerShort = Long.parseLong(lineSplit2[0])+timeOfServerShort;
                        l2.add("server|server|previousARTIFICIALTURN|"+newTimeOfServer+"|"+newTimeOfServerShort+"|"+newTimeOfServerShort+"|0|0|server|"+lineSplit2[1]+"|"+partID+"|OK|0|0|0|0|0|\n"); 
                        l1.add(newTimeOfServerShort);
                    }
                    s.close();
                }
                if(l1.isEmpty()||pipes<9){
                   outputFile.write(strLine+"\n");
                }else if(l1.get(0).longValue() <= Long.parseLong(lineSplit[4])){
                   outputFile.write(l2.get(0));
                   l1.remove(0);
                   l2.remove(0);
                }else{
                    outputFile.write(strLine+"\n");
                }
                
            } catch (Exception exc){
                in.close();
                outputFile.close();
                System.err.println("Error: " + exc.getMessage());
                break;
            }
        }
        in.close();
        fis.close();
        outputFile.close();
    }
    public static void main(String[] args){
        try {
            inputMessageDat();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReAddFakeTurns.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReAddFakeTurns.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReAddFakeTurns.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
