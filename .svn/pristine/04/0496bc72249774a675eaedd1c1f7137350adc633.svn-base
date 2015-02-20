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
public class RecoverFromError {
    
    public static void inputMessageDat() throws FileNotFoundException, IOException, ClassNotFoundException{
        File filename = new File("C:/Users/PPAT/Desktop/Error/Recovery.txt");
        String directoryForOutput = "C:/Users/PPAT/Desktop/Error/";
        FileInputStream fis = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fis);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        FileWriter currentFile = new FileWriter(directoryForOutput+"stripped.txt");
        FileWriter outputFile = new FileWriter(directoryForOutput+"recovered.txt");
        String strLine;
        String insertedBit,correctThread = "";
        String RecentClockTime = "";
        int IsADocinsert = 0;
        int Thread19 = 0, Thread20 = 0;
        String MostRecent = "", Previous = "";
        
        while ((strLine = br.readLine())!=null){
            //System.out.println(strLine);
            try{
               if(strLine.contains("DOCINSERT")){
                   insertedBit = strLine.substring(strLine.indexOf("(")+1, strLine.indexOf(")"));
                   currentFile.write(strLine+"\n");
                   outputFile.write(RecentClockTime+"|"+Previous+"|"+MostRecent+"|"+Thread19+"|"+Thread20+"|"+insertedBit+"\n");
                   IsADocinsert = 1;
               }else{
                   IsADocinsert = 0;
               }
               if(strLine.contains("RECEIVING FROM Thread-14")){
                   Thread19++; 
                   Previous = MostRecent;
                   MostRecent = "Grantypantz";
                   currentFile.write(strLine+"|"+Thread19+"|"+Thread20+"\n");
                   //System.out.println(strLine+"|"+Thread19+"|"+Thread20);
               }else if(strLine.contains("RECEIVING FROM Thread-13")){
                   Thread20++; 
                   Previous = MostRecent;
                   MostRecent = "dsefalk";
                    currentFile.write(strLine+"|"+Thread19+"|"+Thread20+"\n"); 
               }else if(strLine.contains("SLEEP")){
                   String[] lineSplit = strLine.split("[ ]");
                   RecentClockTime = lineSplit[3];
                   //currentFile.write(strLine+"\n");
               }else if(strLine.contains("DOCREMOVE")){
                   currentFile.write(strLine+"\n");
                   outputFile.write(RecentClockTime+"|"+Previous+"|"+MostRecent+"|"+Thread19+"|"+Thread20+"|"+strLine+"\n");
               }else if(strLine.contains("live.com")|strLine.contains("hotmail")|strLine.contains("diet.message")){
                   currentFile.write(strLine+"\n");
               }
            } catch (Exception exc){
                in.close();
                currentFile.close();
                outputFile.close();
                System.err.println("Error: " + exc.getMessage() + strLine);
                break;
            }
        }
        in.close();
        fis.close();
        currentFile.close();
        outputFile.close();
    }
    public static void main(String[] args){
        try {
            inputMessageDat();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RecoverFromError.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RecoverFromError.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RecoverFromError.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
