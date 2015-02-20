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
public class UpdateOld {
    
    public static void inputMessageDat() throws FileNotFoundException, IOException, ClassNotFoundException{
        File filename = new File("C:/Users/PPAT/Desktop/Saved experimental data/0002_2people/turns.txt");
        String directoryForOutput = "C:/Users/PPAT/Desktop/Saved experimental data/0002_2people/";
        FileInputStream fis = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fis);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        FileWriter outputFile = new FileWriter(directoryForOutput+"recoveredTurns.txt");
        String strLine;

        outputFile.write("ParticipantID|Sender|Type|ClientTime|Onset|Enter|Typingti|Speed|AppOrig.|Text|Recipients|Blocked|KDels|DDels|DIns|DDels*N|DIns*N|TaggedText");
        
        while ((strLine = br.readLine())!=null){
            try{
                int pipes = 0;
                for (int i=0; i < strLine.length(); i++){
                    if (strLine.charAt(i) == '|'){
                        pipes++;
                    }
                }
                String[] lineSplit = strLine.split("[|]");
                if(pipes>=3&&lineSplit[2].equals("KEYPRESS")){
                    outputFile.write(lineSplit[0]+"|"+lineSplit[1]+"|"+lineSplit[2]+"S|-9999|"+lineSplit[3]+"|"+lineSplit[4]+"|"+lineSplit[5]+"|"+lineSplit[6]+"|"+lineSplit[7]+"|"+lineSplit[8]+"|"+lineSplit[9]+"|"+lineSplit[10]+"|"+lineSplit[11]+"|"+lineSplit[12]+"|"+lineSplit[13]+"|"+lineSplit[14]+"|"+lineSplit[15]+"|\n");
                }else if(pipes>=9){
                    outputFile.write(lineSplit[0]+"|"+lineSplit[1]+"|"+lineSplit[2]+"|"+lineSplit[3]+"|"+lineSplit[3]+"|"+lineSplit[4]+"|"+lineSplit[5]+"|"+lineSplit[6]+"|"+lineSplit[7]+"|"+lineSplit[8]+"|"+lineSplit[9]+"|"+lineSplit[10]+"|"+lineSplit[11]+"|"+lineSplit[12]+"|"+lineSplit[13]+"|"+lineSplit[14]+"|"+lineSplit[15]+"|\n");
                }else if(pipes>=2&&lineSplit[0].equals("server")){
                    outputFile.write(lineSplit[0]+"|"+lineSplit[1]+"|"+lineSplit[2]+"|"+lineSplit[3]+"|"+lineSplit[3]+"|"+lineSplit[4]+"|"+lineSplit[5]+"|"+lineSplit[6]+"|"+lineSplit[7]+"|"+lineSplit[8]+"\n");
                }else{
                    outputFile.write(strLine+"\n");
                }
            } catch (Exception exc){
                //in.close();
                //outputFile.close();
                System.err.println("Error: " + exc.getMessage());
                //break;
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
            Logger.getLogger(UpdateOld.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UpdateOld.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UpdateOld.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
