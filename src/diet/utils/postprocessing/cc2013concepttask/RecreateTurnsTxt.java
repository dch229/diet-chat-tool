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
public class RecreateTurnsTxt {
    
    public static void inputMessageDat() throws FileNotFoundException, IOException, ClassNotFoundException{
        File filename = new File("C:/Users/PPAT/Desktop/Error/Grantypantz.txt");
        String directoryForOutput = "C:/Users/PPAT/Desktop/Error/";
        FileInputStream fis = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fis);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        FileWriter outputFile = new FileWriter(directoryForOutput+"GrantypantzTurns.txt");
        String strLine, textSofar="";
        String partID = "g.tregonning@hss12.qmul.ac.uk";
        long segStart = 1366888035468L, totStart = 1366888035468L, segEnd;
        //String partID = "robynmclellan@live.com";
        //long segStart = 1364227886281L, totStart = 1364227886281L, segEnd;
        
        outputFile.write("ParticipantID|Sender|Type|ClientTime|Onset|Enter|Typingti|Speed|AppOrig.|Text|Recipients|Blocked|KDels|DDels|DIns|DDels*N|DIns*N|TaggedText");
        
        while ((strLine = br.readLine())!=null){
            try{
                String[] lineSplit = strLine.split("[\t]");
                if(Long.parseLong(lineSplit[0])<segStart){
                    segStart = Long.parseLong(lineSplit[0])-totStart;
                }
                Long thisStart = Long.parseLong(lineSplit[0])-totStart;
                if(lineSplit.length == 3){
                    textSofar = textSofar.concat(lineSplit[2]);
                    outputFile.write(partID+"|"+lineSplit[1]+"|KEYPRESSS|-9999|"+thisStart+"|0|0|"+lineSplit[1]+"||"+textSofar+"||OK|0|0|0|0|0|\n");
                }else{
                    //System.out.println(lineSplit);
                    segEnd = Long.parseLong(lineSplit[0])-totStart;
                    long segDiff = segEnd - segStart;
                    outputFile.write(partID+"|"+lineSplit[1]+"|Default|"+lineSplit[0]+"|"+segStart+"|"+segEnd+"|"+segDiff+"|0||"+textSofar+"||OK|0|0|0|0|0|\n");
                    textSofar = "";
                    segStart = 9999999999999L;
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
            Logger.getLogger(RecreateTurnsTxt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RecreateTurnsTxt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RecreateTurnsTxt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
