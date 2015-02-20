/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.utils.postprocessing.spreadsheet.CBYC;

import diet.server.experimentmanager.JOutput;
import diet.utils.stringsimilarity.SyntacticSimilarityMeasure;
import java.awt.Dimension;
import java.io.*;
import java.util.Vector;
import javax.swing.JFrame;

/**
 *
 * @author sre
 */
public class Spreadsheet {
    
    
    //Has to be sorted by dyad number and by time
    
    //UNIQID (DyadNo+ParticipantID + Sender)
    //DyadNo
    //Text
    //DescType
    //Sanity Check
    
    
    public static JOutput jo;
     
    //Vector v = new Vector();
    public Vector  dyads= new Vector();
    String sFileName ="spreadsheet";
    
    
    public int findColumnINDEX(String columnName, String[] headerrow){
        int column = -2;
        for(int i=0;i<headerrow.length;i++){
            String headerName = headerrow[i];
            if(headerName.equalsIgnoreCase(columnName)){
                return i;
            }
        }
       System.err.println("CANNOT FIND "+columnName);
       System.exit(-234);
       return -5; 
       
    }
    
    
    
    public void loadExperiment(){
        String str ="";
        try {
        File f = new File(System.getProperty("user.dir")+File.separator, "postprocessing"+File.separator+sFileName+".CSV");
        
        
        BufferedReader in = new BufferedReader(new FileReader(f));
        
        str = in.readLine();  
        Vector allRows = new Vector();
        
        Dyad currentDyad = null;
        
        String[] rowHeader = str.split("[|]");
        
        
        //int uniqID_index = findColumnINDEX("uniqid",rowHeader);
        int participantID_index = findColumnINDEX("participantID",rowHeader);
        int sender_index = findColumnINDEX("sender",rowHeader);
        int dyadNo_index = findColumnINDEX("dyadno",rowHeader);
        int dyadNoCorrected_index = findColumnINDEX("DyadNoCorrected",rowHeader);
        int ddels_index =  findColumnINDEX("ddels",rowHeader);
        int kdels_index = findColumnINDEX("kdels",rowHeader);
        int sanitycheck_index = findColumnINDEX("sanity",rowHeader);
        int text_index = findColumnINDEX("cbycfinaltext",rowHeader);
        int desctype_index = findColumnINDEX("desctype",rowHeader);
        int turntype_index = findColumnINDEX("turntype",rowHeader);
        int recipient_index = findColumnINDEX("recipients",rowHeader);
        int cbyctype_index = findColumnINDEX("cbyctype",rowHeader);
        
        while ((str = in.readLine()) != null) {
             String[] rowString = str.split("[|]"); 
             String  string1 =  rowString[participantID_index];  //ParticipantID
              String  string2 =  rowString[sender_index];  //Sender
              String  string3 =  rowString[dyadNo_index];  //dyadNo
              String  string3DyadNoCorrected  =  rowString[dyadNoCorrected_index];  //dyadNo
              String  string0 =  string1+string2+string3;//rowString[uniqID_index];  //UNIQID
              int  int4 =  Integer.parseInt(rowString[ddels_index]); //DDels
              int  int5 =  Integer.parseInt(rowString[kdels_index]); //DIns
              String  string6 =  rowString[text_index];  //Text
              String  string7 =  rowString[desctype_index];  //DescType
              Long    long8 =  Long.parseLong(rowString[sanitycheck_index]);
              String string9Turntype = rowString[turntype_index];
              String string10Recipient ="";
              String string11Cbyctype = rowString[cbyctype_index];
              
              try{
                   string10Recipient = string10Recipient.replace(", ", "");
              }catch(Exception e){
                  
              }
            
            
           
            if(currentDyad==null) {
                currentDyad = new Dyad(string3,string3DyadNoCorrected);
                dyads.addElement(currentDyad);
            }
            else if (!currentDyad.dyadN.equals(string3)){
                currentDyad = new Dyad(string3,string3DyadNoCorrected);
                dyads.addElement(currentDyad);
                jo.appendText("Add dyad: "+string3+"\n");
            }
             System.out.println("Loading in from file "+currentDyad.dyadN+"---"+string3+"----"+string6);
             
             try{
                       
              
             
                 
                 
                 
                 
                 currentDyad.addRowFromSpreadsheet(string0,  //UNIQID
                                              string1,  //ParticipantID
                                              string2,  //Sender
                                              string3,  //dyadNo
                                              string3DyadNoCorrected,
                                              int4, //DDels
                                              int5, //kdels
                                              string6,  //Text
                                              string7,  //DescType
                                              long8,
                                              string9Turntype,
                                              string10Recipient,
                                              string11Cbyctype
                                              );
                 
             }catch (Exception e){
                 System.err.println("------------------------------------ERROR BELOW");
                 System.err.println(rowString[8]);
                 e.printStackTrace();
                 System.err.println("THE STRING THAT CAUSED IT TO CRASH IS: "+str);
                 System.exit(-52);
             }
             
            
                                              
            
            allRows.addElement(rowString);
            //jo.appendText(str);
           
            
            
        }
        
        
        
        
        }catch (Exception e){
            //jo.appendText(e.printStackTrace());
            e.printStackTrace();
            System.err.println("THE STRING THAT CAUSED IT TO CRASH IS: "+str);
            //System.err.println("---"+);
            System.exit(-12345);
        }
        
        
    }
    
    
    public void testParsing(){
        
    }
    
    
    
    
    
    public  Spreadsheet(){
        
        //SyntacticSimilarityMeasure ssm = new SyntacticSimilarityMeasure();
        //ssm.dotest("THIS SHOULD BE A RULE SET HOPEFULLY IT IS");
        //ssm.getRulesInA_getRulesInB_getCommonRules("This is a house?", "This is a house");
        //ssm.getRulesInA_getRulesInB_getCommonRules("This is a house", "This is a house?");
        
        
        //System.exit(-5);
        //A) Relationship  (ie. proportions) between (1) Alignment of structures that are  not lexical repetitions
        //                                           (2) Alignment of structures that are lexical repetitions
        
        
        
        
        //if(2<4)return;
        jo = new JOutput();
        JFrame jf = new JFrame();
        jf.add(jo);
        jf.setPreferredSize(new Dimension(500,800));
        jf.setVisible(true);
        jf.pack();
        loadExperiment();
        for(int i=0;i<this.dyads.size();i++){
            Dyad d = (Dyad)dyads.elementAt(i);
            jo.appendText("Calculating Scores for dyad "+d.dyadN+ "\n");
            System.out.println("Calculating for dyad "+d.dyadN);
            //jo.appendText("Calculating S");
            
            d.calculateDistancesFromTSTAndFromRESPONSE();
            d.calculateAlignmentScores();
        }
        this.writeSpreadsheetToFile();
    }
    
    
    
    
    public static void main(String[] args){
         
        Spreadsheet s = new Spreadsheet();
        //int[] results = StringSimilarityMeasure.getNoOfUniqueWordsInA_NoOfUniqueWordsInB_NoOfMatchingWords("this should be something,really", "but this of course is really long");
        //System.out.println(results[0]);
        //System.out.println(results[1]);
        //System.out.println(results[2]);
        
    }
    
    
    
   
    
    public void writeSpreadsheetToFile(){
                           String header = 
                                   
                                   
                                   
                           "contig_UNIQID"+"|"+
                           "contig_DYADNO"+"|"+
                           "contig_DYADNOCORRECTED"+"|"+        
                           "contig_TURNTYPE"+"|"+
                           "contig_SANITY"+"|"+
                           "contig_TEXT"+"|"+
                           "contig_DESCTYPE"+"|"+                                   
                           "contig_contigTYPE" +"|"+
                           "contig_distanceFROM_TST" +"|"+
                           "contig_distanceFROM_RESP" +"|"+
                           "contig_distanceFROM_DUMMY" + "|"
                                  
                                   
                                   
                          +"contig_UniqueWords"+"|"+
                          "contig_UniqwrdsInThisAndPrior"  +"|"+
                          "contig_UniqwordsInThisAndNext"+"|"+
                          "contig_XXXXXXXX"+ "|"+
                          "contig_DescTMostAbs"+"|"+
                          "contig_DescTypeHowMuchMoreAbsOfHighestThanPrior"+"|"+
                          "contig_DescTypeHowMuchMoreAbsOfHighestThanNext"+"|"+
                          "contig_DescTLeastAbs"+"|"+
                          "contig_DescTypeHowMuchMoreAbsOfLowestThanPrior"+"|"+
                          "contig_DescTypeHowMuchMoreAbsOfLowestThanNext"+"|"+
                          "contig_DescTypeNoOfUnique"+"|"+
                          "contig_DescTypeUniqueSharedInPrior"+"|"+
                          "contig_DescTypeUniqueSharedWithNext"+"|"+
                          "contig_XXXXXXX"+ "|"+
                          "contig_SyntacticUnique"+"|"+
                          "contig_SyntacticSharedinPrior"+"|"+
                          "contig_SyntacticSharedInNext";
        
        
        
        
        
        
        
        
        
        
        
        
        try{  
         File fout = new File(System.getProperty("user.dir")+File.separator, "postprocessing"+File.separator+sFileName+"OUT.CSV");
        PrintWriter pw = new PrintWriter(fout);
        pw.println(header);
        for(int j=0;j<this.dyads.size();j++){
           Dyad d = (Dyad)dyads.elementAt(j);
           System.out.println("Saving for dyad "+d.dyadN);
           Vector rows = d.getRowsForSpreadsheet();
           for(int i=0;i<rows.size();i++){
             pw.println(((String)rows.elementAt(i)));           
           }
       }
       
        
       
        
        
       
       
       pw.flush();
       pw.close();
       }catch (IOException e){
           System.err.println("ERROR writing spreadsheet to file");
           e.printStackTrace();
       }
   }
    
    
    
}
