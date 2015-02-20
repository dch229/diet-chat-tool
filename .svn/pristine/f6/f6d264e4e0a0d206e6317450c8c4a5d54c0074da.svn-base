/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.utils.postprocessing.cc2013concepttask;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chris
 */
public class SplitConceptTurns {
    
    public static void inputMessageDat() throws FileNotFoundException, IOException, ClassNotFoundException{
        //String directoryForOutput = "C:/Users/Chris/Desktop/ExperimentalData/Saved experimental data/";
        //String directoryForOutput = "C:/Users/PPAT/Desktop/Saved experimental data/";
        String directoryForOutput = "E:/ExperimentalData/Saved experimental data/";
        File filename = new File(directoryForOutput);
        
        File[] dirList=filename.listFiles();
        Date myDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String myDateString = sdf.format(myDate);
        BufferedWriter out=new BufferedWriter(new FileWriter(directoryForOutput+File.separator+"Overview_"+myDateString+".txt"));
        String strLine, subfileID = "", strLineNew = "";
        int zeroTime = 0;
        int textPosition = 9;
        int startTime = 4;
        int endTime = 5;
        List<String> whoTo = new ArrayList<String>();
        List<String> orderElements = new ArrayList<String>();
        List<String> orderElementsAll = new ArrayList<String>();
        List<FileWriter> fileNames = new ArrayList<FileWriter>();
        List<FileWriter> outi = new ArrayList<FileWriter>();
        boolean useLine = false;
        
        for(File expDir:dirList){
            if (expDir.isDirectory()){
                File turnsFile=new File(filename+File.separator+expDir.getName()+File.separator+"turns.txt");
                System.out.println("Processing exp with id:"+expDir.getName());
                directoryForOutput = expDir.getName();
                FileWriter currentFile = new FileWriter(filename+File.separator+directoryForOutput+File.separator+"opening.txt");
                //FileWriter allFile = new FileWriter(filename+File.separator+directoryForOutput+File.separator+"opening.txt");
                if (!turnsFile.exists()){
                    System.err.println(expDir.getName()+" folder skipped, no turns.txt file");
                    continue;
                }
                FileInputStream fis = new FileInputStream(turnsFile);
                DataInputStream in = new DataInputStream(fis);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
        
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
                                if(!lineSplit[1].equals("server")){
                                    if(whoTo.isEmpty()){
                                        //System.out.println("ADD FIRST PERSON"+lineSplit[0]);
                                        whoTo.add(whoTo.size(), lineSplit[1]);
                                    }else if(!whoTo.contains(lineSplit[1])){
                                        //System.out.println("ADD ANOTHER PERSON"+lineSplit[0]);
                                        whoTo.add(whoTo.size(), lineSplit[1]);
                                    }
                                }
                                useLine = true;
                            }else{
                                useLine = false;
                            }
                        }else{
                            strLineNew = strLineNew.concat(strLine+"#");
                            pipes = 0;
                            for (int i=0; i < strLineNew.length(); i++){
                                if (strLineNew.charAt(i) == '|'){
                                    pipes++;
                                    useLine =  false;
                                }
                                if(pipes > 9){
                                    lineSplit = strLineNew.split("[|]");
                                    useLine=true;
                                    strLineNew = "";
                                }   
                            }
                        }
                        
                        if (useLine && lineSplit[0].equals("server") && lineSplit[2].equals("ARTIFICIALTURN")){
                            currentFile.close();
                            zeroTime = Integer.parseInt(lineSplit[5]);
                            if(lineSplit[textPosition].contains("#")){
                                subfileID = lineSplit[textPosition].substring(0, lineSplit[textPosition].indexOf("#"));
                                lineSplit[textPosition] = lineSplit[textPosition].replaceAll("#"," ");
                            }else{
                                subfileID =  lineSplit[textPosition];
                            }
                            if(!orderElements.contains(subfileID) && !subfileID.equals("Time up!") && !subfileID.equals("Next task") && !subfileID.equals("That's all folks!")){
                                orderElements.add(subfileID);
                            }
                            if(!orderElementsAll.contains(subfileID) && !subfileID.equals("Time up!") && !subfileID.equals("Next task") && !subfileID.equals("That's all folks!")){
                                orderElementsAll.add(subfileID);
                                //System.out.println(orderElementsAll);
                                FileWriter tempFile = new FileWriter(filename+File.separator+"Overview"+File.separator+"Overview_"+subfileID+".txt", false);
                                outi.add(tempFile);   
                                tempFile.write("GroupID|subTask|timeInsubTask|ParticipantID|Sender|Type|ClientTime|Onset|Enter|Typingti|Speed|AppOrig.|Text|Recipients|Blocked|KDels|DDels|DIns|DDels*N|DIns*N|TaggedText");
                            }
                            
                            currentFile = new FileWriter(filename+File.separator+directoryForOutput+File.separator+subfileID+".txt");
             
                            for (int i = 0; i < whoTo.size(); i++) {
                                FileWriter tempFile = new FileWriter(filename+File.separator+directoryForOutput+File.separator+subfileID+i+".txt");
                                fileNames.add(i, tempFile);
                            }
                        }else if (useLine){
                            int TimeFromZero = Integer.parseInt(lineSplit[endTime]) - zeroTime;
                            if(orderElementsAll.contains(subfileID)&&TimeFromZero>=0){
                                outi.get(orderElementsAll.indexOf(subfileID)).write("\n"+expDir.getName()+"|"+subfileID+"|"+TimeFromZero+"|");
                            }
                            for (int j = 0; j < lineSplit.length; j++) {
                                currentFile.write(lineSplit[j]+"|");
                                if(orderElementsAll.contains(subfileID)&&TimeFromZero>=0){
                                    outi.get(orderElementsAll.indexOf(subfileID)).write(lineSplit[j]+"|");
                                }
                            }
                            currentFile.write(TimeFromZero+"\n");
                            
                            
                            if(!fileNames.isEmpty())  {
                                for (int i = 0; i < whoTo.size(); i++) {
                                    if(whoTo.get(i).contains(lineSplit[0])){
                                        for (int j = 0; j < lineSplit.length; j++) {
                                            fileNames.get(i).write(lineSplit[j]+"|");
                                        }
                                        fileNames.get(i).write(TimeFromZero+"\n");
                                    }
                                }
                            }
                        }
                    } catch (Exception exc){
                        currentFile.flush();
                        for (int i = 0; i < fileNames.size(); i++) {
                            fileNames.get(i).flush();
                        }
                        System.err.println("Error: " + exc.getMessage());
                    }
                }
                
                for(int j = 0; j < whoTo.size(); j++) {
                    out.write(expDir.getName()+"|"+whoTo.size()+"|"+whoTo.get(j));
                    for (int i = 0; i < orderElements.size(); i++) {
                        out.write("|"+orderElements.get(i));
                    }
                    out.newLine();
                }
                //out.newLine();
                in.close();
                fis.close();
                out.flush();
                currentFile.flush();
                for (int i = 0; i < fileNames.size(); i++) {
                    fileNames.get(i).flush();
                }
                for (int j = 0; j < outi.size(); j++) {
                    outi.get(j).flush();
                }
                whoTo.clear();
                orderElements.clear();
            }
        }
    }
    public static void main(String[] args){
        try {
            inputMessageDat();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SplitConceptTurns.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SplitConceptTurns.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SplitConceptTurns.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
