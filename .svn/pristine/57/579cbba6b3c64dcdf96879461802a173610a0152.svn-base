/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.utils.postprocessing.cc2013concepttask;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chris
 */
public class AUTforSCORE {
    
    public static void inputMessageDat() throws FileNotFoundException, IOException, ClassNotFoundException{
        //String directoryForOutput = "C:/Users/Chris/Desktop/ExperimentalData/Saved experimental data/";
        //String directoryForOutput = "C:/Users/PPAT/Desktop/Saved experimental data/";
        String directoryForOutput = "E:/ExperimentalData/Saved experimental data/";
        
        File filename = new File(directoryForOutput);
        
        BufferedReader AUTorder = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(filename+"/AUTorder.txt"))));
        String strLine, strLine2;
       
        while ((strLine = AUTorder.readLine())!=null){
            String[] lineSplit = strLine.split("[\t]");
            try{
                FileWriter currentFile = new FileWriter(directoryForOutput+File.separator+lineSplit[0]+"AUT.txt");
                for(int i=2; i<lineSplit.length; i++){
                    System.out.println(directoryForOutput+File.separator+lineSplit[0]+File.separator+lineSplit[i]+".txt");
                    File turnsFile=new File(directoryForOutput+File.separator+lineSplit[0]+File.separator+lineSplit[i]+".txt");
                    BufferedReader AUTfile = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(turnsFile))));
                    while ((strLine2 = AUTfile.readLine())!=null){
                        String[] lineSplit2 = strLine2.split("[|]");
                        currentFile.write(lineSplit[i]+"|"+lineSplit2[5]+"|"+lineSplit2[1]+"|"+lineSplit2[9]+"\n");
                    }
                }
                currentFile.close();        
            }catch (Exception exc){
                System.err.println("Error: " + exc.getMessage());
            }
        }
    }

    public static void main(String[] args){
        try {
            inputMessageDat();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AUTforSCORE.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AUTforSCORE.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AUTforSCORE.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
