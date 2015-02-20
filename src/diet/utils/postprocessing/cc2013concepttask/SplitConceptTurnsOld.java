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
public class SplitConceptTurnsOld {
    
    public static void inputMessageDatOld() throws FileNotFoundException, IOException, ClassNotFoundException{
        File filename = new File("C:/Users/PPAT/Desktop/Saved experimental data/0040Normal/turns.txt");
        String directoryForOutput = "./ConceptTaskTxt/0040a/";
        FileInputStream fis = new FileInputStream(filename);
        DataInputStream in = new DataInputStream(fis);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        FileWriter currentFile = new FileWriter(directoryForOutput+"opening.txt");
        String subfileID = new String("");
        String strLine;
        int zeroTime = 0;
        int textPosition = 9;
        int startTime = 4;
        int endTime = 5;
        String strLineNew = new String("");
        List<String> whoTo = new ArrayList<String>();
        List<FileWriter> fileNames = new ArrayList<FileWriter>();
        boolean foundName = false;
        boolean useLine = false;
        
        while ((strLine = br.readLine())!=null){
            String[] lineSplit = strLine.split("[|]");
            try{
                int pipes = 0;
                for (int i=0; i < strLine.length(); i++){
                    if (strLine.charAt(i) == '|'){
                        pipes++;
                    }
                }
                if(pipes > 9){
                    if(strLine.contains("Default")||strLine.contains("ARTIFICIALTURN")){
                        System.out.println(lineSplit[0]+","+lineSplit[textPosition]);
                        if(!lineSplit[0].equals("server")){
                            if(whoTo.isEmpty()){
                                System.out.println("ADD FIRST PERSON"+lineSplit[0]);
                                whoTo.add(whoTo.size(), lineSplit[0]);
                            }else if(!whoTo.contains(lineSplit[0])){
                                System.out.println("ADD ANOTHER PERSON"+lineSplit[0]);
                                whoTo.add(whoTo.size(), lineSplit[0]);
                            }
                        }
                        useLine = true;
                        System.out.println(strLine);
                    }else{
                        useLine = false;
                    }
                }else{
                    strLineNew = strLineNew.concat(strLine+"#");
                    //System.out.println("SHORTER: "+strLineNew);
                    pipes = 0;
                    for (int i=0; i < strLineNew.length(); i++){
                        if (strLineNew.charAt(i) == '|'){
                        pipes++;
                        useLine =  false;
                    }
                    if(pipes > 9){
                        lineSplit = strLineNew.split("[|]");
                        System.out.println(lineSplit[0]+","+lineSplit[textPosition]);
                        useLine=true;
                        System.out.println(strLineNew);
                        strLineNew = "";
                    }   
                }
                }
                if (useLine && lineSplit[0].equals("server")){
                    currentFile.close();
                    zeroTime = Integer.parseInt(lineSplit[5]);
                      if(lineSplit[textPosition].contains("#")){
                                subfileID = lineSplit[textPosition].substring(0, lineSplit[textPosition].indexOf("#"));
                                lineSplit[8] = lineSplit[8].replaceAll("#"," ");
                            }else{
                                subfileID =  lineSplit[textPosition];
                            }
                            currentFile = new FileWriter(directoryForOutput+subfileID+".txt");
                            //currentFile.write("return,text\n");
                                for (int i = 0; i < whoTo.size(); i++) {
                                    FileWriter tempFile = new FileWriter(directoryForOutput+subfileID+i+".txt");
                                    //fileNames.add(i, "D:/TEST/"+subfileID+i+".txt");
                                    fileNames.add(i, tempFile);
                                    //fileNames.get(i).write("return,text\n");

                                }
                      }else if (useLine){
                      int TimeFromZero = Integer.parseInt(lineSplit[endTime]) - zeroTime;
                      currentFile.write(TimeFromZero+"|"+lineSplit[textPosition]+"\n");
                      
                        if(!fileNames.isEmpty())  {
                        for (int i = 0; i < whoTo.size(); i++) {
                             if(whoTo.get(i).contains(lineSplit[0])){
                                //System.out.println(subfileID+":"+fileNames.get(i));
                                fileNames.get(i).write(TimeFromZero+"|"+lineSplit[textPosition]+"\n"); 
                             }
                          }
                      }
                      }

            } catch (Exception exc){
                in.close();
                currentFile.close();
                for (int i = 0; i < fileNames.size(); i++) {
                    fileNames.get(i).close();
                }
                System.err.println("Error: " + exc.getMessage());
                //break;
            }
           
        }
        foundName = false;
        in.close();
        fis.close();
        currentFile.close();
        for (int i = 0; i < fileNames.size(); i++) {
            fileNames.get(i).close();
       }
    }
    public static void main(String[] args){
        try {
            inputMessageDatOld();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SplitConceptTurns.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SplitConceptTurns.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SplitConceptTurns.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    }
