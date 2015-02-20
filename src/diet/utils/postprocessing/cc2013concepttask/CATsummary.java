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
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PPAT
 */
public class CATsummary {
    public static void createCATsummary() throws FileNotFoundException, IOException, ClassNotFoundException{
        String directoryForOutput = "C:/Users/Chris/Desktop/ExperimentalData/Saved experimental data/";
        //String directoryForOutput = "C:/Users/PPAT/Desktop/Saved experimental data/";
        String[] CATwords = new String[] {"fruit and veg","countries","animals"};
        String[] lineSplit= new String[] {};
        ArrayList<String> IndID = new ArrayList<String>();
        ArrayList<String> GroupID = new ArrayList<String>();
        //ArrayList<String> singleton = new ArrayList<String>();
        ArrayList<ArrayList<String>> GroupSolution = new ArrayList<ArrayList<String>>();
        int correctAnswer = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String myDateString = sdf.format(new Date());
        String strLine, subfileID = "", CATstrLine;
        BufferedWriter outMispellings=new BufferedWriter(new FileWriter(directoryForOutput+File.separator+"CAT_Mispellings_"+myDateString+".txt"));
        BufferedReader groupDetails = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(System.getProperty("user.dir")+File.separator+"experimentresources"+"/ConceptTaskTxt/GroupDetails.csv"))));
        BufferedWriter outNonCAT=new BufferedWriter(new FileWriter(directoryForOutput+File.separator+"CAT_non_"+myDateString+".txt"));
        BufferedWriter CATfileout;
        BufferedReader animals = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(System.getProperty("user.dir")+File.separator+"experimentresources"+"/ConceptTaskTxt/animalsTypo.txt"))));
        BufferedReader countries = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(System.getProperty("user.dir")+File.separator+"experimentresources"+"/ConceptTaskTxt/countriesTypo.txt"))));
        ArrayList<String[]> animalsArray = new ArrayList<String[]>();
        ArrayList<String[]> countriesArray = new ArrayList<String[]>();
        ArrayList<String[]> CATarray;
        ArrayList<String> checkArray = new ArrayList<String>();
        
        ArrayList<String> hasBeenSaid = new ArrayList<String>();
        ArrayList<ArrayList<String>> individualArrayList = new  ArrayList<ArrayList<String>>();
        
        while ((strLine = animals.readLine())!=null){
            String[] animalsSplit = strLine.split("[\t]");
            animalsArray.add(animalsSplit);
        }
        while ((strLine = countries.readLine())!=null){
            String[] countriesSplit = strLine.split("[\t]");
            countriesArray.add(countriesSplit);
        }
        
        while ((strLine = groupDetails.readLine())!=null){
            lineSplit = strLine.split("[,]");
        }
        for (int i=1; i < CATwords.length; i++){
            checkArray.clear();
            individualArrayList.clear();
            IndID.clear();
            if(i==1){
                for(int k=0; k<countriesArray.size();  k++){
                    checkArray.add(countriesArray.get(k)[0]);
                }
                CATarray = countriesArray;
            }else{
                for(int k=0; k<animalsArray.size();  k++){
                     checkArray.add(animalsArray.get(k)[0]);
                }
                CATarray = animalsArray;
            }
            File CATmapfile= new File(System.getProperty("user.dir")+File.separator+"experimentresources"+"/ConceptTaskTxt/"+CATwords[i]+"Map.txt");
            BufferedReader CATmap = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(CATmapfile))));
            ArrayList<ArrayList<String>> CATmapArray = new ArrayList<ArrayList<String>>();
            
            while ((strLine = CATmap.readLine())!=null){
                ArrayList<String> catSplit = new ArrayList<String> (Arrays.asList(strLine.split("[\t]")));
                CATmapArray.add(catSplit);
            }
            CATmap.close();
                        
            File dataFile=new File(directoryForOutput+File.separator+"Overview_"+CATwords[i]+".txt");
            BufferedReader CATfile = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(dataFile))));
            CATfileout=new BufferedWriter(new FileWriter(directoryForOutput+File.separator+"CAT_out_"+CATwords[i]+myDateString+".txt",true));
                     
            String currentSelf, previousGroup="none", previousSelf="none", previousOther="none", previousSeq="none";
            int CATsaid;
            int previousPerson = -1;
            while ((CATstrLine = CATfile.readLine())!=null){
                String[] CATlineSplit = CATstrLine.split("[|]");
                if(!previousGroup.equals(CATlineSplit[0])){
                    hasBeenSaid.clear();
                    previousGroup = CATlineSplit[0];
                }
                if(checkArray.contains(CATlineSplit[12].toLowerCase())){//the text typed
                    String[] CATitemSplit = CATarray.get(checkArray.indexOf(CATlineSplit[12].toLowerCase()))[1].split("[/]");
                    String CATitemSplit2 = CATarray.get(checkArray.indexOf(CATlineSplit[12].toLowerCase()))[2];
                    for(int m=0; m<CATitemSplit.length; m++){
                        //check if its actually an animal/country. If yes add it if it's not already there
                        if(!CATitemSplit[m].equals("~")){
                            String temp = CATitemSplit[m]+CATitemSplit2;
                            currentSelf = CATitemSplit[m];
                            if(!hasBeenSaid.contains(temp)){
                                CATsaid=1;
                                hasBeenSaid.add(hasBeenSaid.size(),temp);
                            }else{
                                CATsaid=0;
                            }
                            if(!IndID.contains(CATlineSplit[0]+CATlineSplit[4])){//doublecheck that individual  ID is in (server won't be)
                                IndID.add(CATlineSplit[0]+CATlineSplit[4]);
                                ArrayList<String> tempIndID = new ArrayList<String>();
                                tempIndID.add(CATlineSplit[0]+CATlineSplit[4]);
                                individualArrayList.add(tempIndID);
                            }
                            int otherInConvo = -1;
                            for(int j=0; j<IndID.size(); j++){
                                if(IndID.get(j).contains(CATlineSplit[0])&&!IndID.get(j).contains(CATlineSplit[0]+CATlineSplit[4])){
                                    otherInConvo = j;
                                }    
                            }
                            
                            if(!CATlineSplit[0].contains("GroupID")){//add own ones (all, not just novel ones) to array
                                int index = IndID.indexOf(CATlineSplit[0]+CATlineSplit[4]);
                                individualArrayList.get(index).add(CATitemSplit[m]);
                                if(individualArrayList.get(index).size()<=2){
                                    previousSelf = "~";
                                }else{
                                    previousSelf = individualArrayList.get(index).get(individualArrayList.get(index).size()-2);
                                }
                                if(otherInConvo == -1){
                                    previousOther = "~";
                                }else{
                                    previousOther = individualArrayList.get(otherInConvo).get(individualArrayList.get(otherInConvo).size()-1);
                                }
                                if(previousPerson == index && index != -1){//is self
                                    previousSeq = previousSelf;
                                }else if(previousPerson == otherInConvo && otherInConvo != -1){//is other person
                                    previousSeq = previousOther;
                                }else{
                                    previousSeq = "~";
                                }
                                previousPerson = index;
                                //set previous as self or other
                            }    
                        }else{//if it does = ~ i.e. is a non-animal turn
                            outNonCAT.write(CATlineSplit[0]+"\t"+CATwords[i]+"\t"+CATlineSplit[12].toLowerCase()+"\n");
                            CATsaid = 0;
                            currentSelf = "~";
                            previousSelf = "~";
                            previousOther = "~";
                            previousSeq = "~";
                        }
                        String selfToSelf = "~", selfToOther = "~", selfToSeq = "~";
                        if(CATlineSplit[0].contains("GroupID")){
                            CATfileout.write(CATstrLine+"|novel_item|this_item|Prev_item_self|Prev_item_Oth|Self_to_self|Self_to_other|Self_toSeq\n");
                        }else{
                            if(!currentSelf.equals("~") && !previousSelf.equals("~")){
                                if(CATmapArray.get(0).indexOf(currentSelf)==-1 || CATmapArray.get(0).indexOf(previousSelf)==-1){
                                    System.out.println("ITEM NOT FOUND: "+currentSelf+">>>"+previousSelf);    
                                }else{
                                    int colNum = CATmapArray.get(0).indexOf(currentSelf);
                                    int rowNum = CATmapArray.get(0).indexOf(previousSelf);
                                    selfToSelf = CATmapArray.get(colNum).get(rowNum);
                                }
                            }
                            if(!currentSelf.equals("~") && !previousOther.equals("~")){
                                if(CATmapArray.get(0).indexOf(currentSelf)==-1 || CATmapArray.get(0).indexOf(previousOther)==-1){
                                    System.out.println("ITEM NOT FOUND: "+currentSelf+">>>"+previousOther);    
                                }else{
                                    int colNum = CATmapArray.get(0).indexOf(currentSelf);
                                    int rowNum = CATmapArray.get(0).indexOf(previousOther);
                                    selfToOther = CATmapArray.get(colNum).get(rowNum);
                                }
                            }
                            if(!currentSelf.equals("~") && !previousSeq.equals("~")){
                                if(CATmapArray.get(0).indexOf(currentSelf)==-1 || CATmapArray.get(0).indexOf(previousSeq)==-1){
                                    System.out.println("ITEM NOT FOUND: "+currentSelf+">>>"+previousSeq);    
                                }else{
                                    int colNum = CATmapArray.get(0).indexOf(currentSelf);
                                    int rowNum = CATmapArray.get(0).indexOf(previousSeq);
                                    selfToSeq = CATmapArray.get(colNum).get(rowNum);
                                }
                            }

                            CATfileout.write(CATstrLine+"|"+CATsaid+"|"+currentSelf+"|"+previousSelf+"|"+previousOther+"|"+selfToSelf+"|"+selfToOther+"|"+selfToSeq+"\n");
                        } 
                    }
                }else{//it's not in my mispellings map and should be
                    outMispellings.write(CATwords[i]+"\t"+CATlineSplit[12].toLowerCase()+"\n");
                    CATfileout.write(CATstrLine+"|~|~|~\n"); 
                }
            }
            outMispellings.flush();
            CATfileout.close();
            correctAnswer = 0;
            CATmapArray.clear();
        }
        outNonCAT.flush();
    }   
        
        
    
    
    public static void main(String[] args){
        try {
            createCATsummary();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SplitConceptTurns.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SplitConceptTurns.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SplitConceptTurns.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
