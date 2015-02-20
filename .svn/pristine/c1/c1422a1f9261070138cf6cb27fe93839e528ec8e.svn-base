/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.utils.postprocessing.collatingdata;

import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author sre
 */
public class CollatingData {
    
        public CollatingData(){
              //File f  = CustomDialog.loadFile(System.getProperty("user.dir"));
              String directory = System.getProperty("user.dir");
              //String directory = System.getProperty("user.dir")+File.separator+"data"+File.separator+"Saved experimental data";
              File[] fs = CustomDialog.loadDirectories("What are the directories containing the experiments you want to collate?", directory);
              String[] contents = new String[fs.length];
              contents[0] = processDirectory(fs[0],true);  
              for(int i=1;i<fs.length;i++){
                   contents[i] = processDirectory(fs[i],false); 
                   System.err.println("PROCESSEDDIRECTORY "+i);
              }
              System.err.println("COLLATINGA");
              String output ="";
              for(int i=0;i<contents.length;i++){
                   //System.out.print(contents[i]);
                   System.err.println("COLLATINGINTOONEBIGOUTPUT"+i);
                   output=output+contents[i];
              }
              String outputFilename = "collated"+new Date().getTime()+".txt";
              CustomDialog.saveFile(directory, outputFilename, output);
               Conversation.printWSln("Main","DONE!");
              
        }
             
        public static String processDirectory(File directoryFile,boolean includeheader){
             
             System.err.println("PROCESSING:"+directoryFile);
             Conversation.printWSln("Main","PROCESSING: "+directoryFile);
             File turns = new File(directoryFile,"turns.txt");
             if(!turns.exists()){
                  CustomDialog.showDialog("There is no turns.txt file in: "+directoryFile);
             }
             Vector v = new Vector();
             String line = null;
             try{
                BufferedReader br = new BufferedReader(new FileReader(turns));
                line = br.readLine();
                if(line==null) throw new Exception();
                line="Directory"+"|"+line +"\n";
                if(includeheader)v.addElement(line);
                long linecounter =0;
                while (line != null) {
                      line = br.readLine();
                      if(line!=null){
                           //if(!line.contains("KEYPRESSS")){
                                v.addElement( directoryFile.getName()+ "|" +line.replace("\n", "") +"\n");
                           //}
                      }
                         
                      //System.err.println("LINE_"+directoryFile+line);
                      
                      linecounter++;
                      if(linecounter % 1000 ==0){
                          System.err.println(linecounter);
                      }
                } 
                br.close();
                System.err.println("FINISHED PROCESSING "+directoryFile.getName());
        
         }catch(Exception e){
               e.printStackTrace();
               CustomDialog.showDialog("Could not load "+ directoryFile.getName()+  "for some reason: "+e.getMessage());
               System.exit(-52);
         }
             System.err.println("END1 "+directoryFile.getName());
             String retvalue="";
             
             for(int i=0;i<v.size();i++){
                 if(i % 2000 ==0){
                          System.err.println("ENDB "+directoryFile.getName()+" "+i);
                 
                 }
                 String s = (String)v.elementAt(i);
                 retvalue = retvalue+s;
             }
             
            
              System.err.println("RETURNING:"+directoryFile);
             return retvalue;
        }
      
        
        
    
       public static void main(String[] args){
          
           CollatingData cd = new CollatingData();
       }
}
