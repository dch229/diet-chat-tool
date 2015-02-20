/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.utils.spreadsheet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

/**
 *
 * @author sre
 */
public class SpreadsheetTurns {
    
    String [] header;
    String [] [] spreadsheet;
    
    
    public SpreadsheetTurns(String s){
        this(new File (s));
    }
    
    
    public SpreadsheetTurns(File f){
        try {
        BufferedReader in = new BufferedReader(new FileReader(f));
        String str;
        str = in.readLine();
        header = str.split("[|]");
        System.out.println("HEADER0 "+header[0]);
        Vector allRows = new Vector();
        while ((str = in.readLine()) != null) {
            String[] rowString = str.split("[|]");
            for(int i=rowString.length;i<header.length;i++){
                rowString = addEmptyCells(rowString,1);
            }
            allRows.addElement(rowString);
            
        }
        
        
        spreadsheet = new String[allRows.size()][header.length+5];
        System.out.println("SPREADSHEET IS "+allRows.size());
        for(int i=0;i<allRows.size();i++){
            spreadsheet[i]=(String[])allRows.elementAt(i);
            if(i %10000 ==0 || i==allRows.size()-1)System.out.println(spreadsheet[i][0]);
        }
        
        
        in.close();
        
        
    } catch (IOException e) {
        
    }
    }
    
    
    public  String[] addEmptyCells(String[] oldArray, int number){
       String[] newArray = new String[oldArray.length+number];
       for(int i=0;i<newArray.length;i++){
           if(i<oldArray.length){
             newArray [i] = oldArray[i];
           }
           else{
              newArray[i]="";
           }
       }
       return newArray;
   }
    
    
    
     public int findColumnNumber(String headerName){
       for(int i=0;i<header.length;i++){
           String s = header[i];
           if(s.equals(headerName))return i;
       }
       System.err.println("CANNOT FIND "+headerName);
       System.exit(-55556);
       return -1;
   }
    
    public String getValue(String headername, int rowNumber){
        int headernumber = this.findColumnNumber(headername);
        return this.spreadsheet[rowNumber][headernumber];
    }
    
    public Vector findString(String needle, String headerHAYSTACK){
        Vector indices = new Vector();
        int headernumber = this.findColumnNumber(headerHAYSTACK);
        System.err.println("Searching for needle "+needle);
        for(int i =0 ; i < this.spreadsheet.length;i++){
            if(i % 100==0)System.err.println(i);
            String s = spreadsheet[i][headernumber];
            if(s.equals(needle)){
                System.err.println("FOUND: "+s);
                indices.addElement(i);
            }
        }
        return indices;
    }
    
    
}
