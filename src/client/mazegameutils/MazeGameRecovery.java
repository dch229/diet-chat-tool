/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client.mazegameutils;

import diet.message.MessageChatTextFromClient;
import diet.utils.VectorHashtable;
import diet.utils.VectorToolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author sre
 */
public class MazeGameRecovery {
    
     public String directory ="";
       
    
     long timeOfLastLoaded;
     
    
     final private synchronized void setTimeOfLastObjectLoaded(){
         timeOfLastLoaded = new Date().getTime();
     }
     final private synchronized long getTimeSinceLastObjectLoaded(){
         return new Date().getTime()-timeOfLastLoaded;
     };
    
      
    final public Vector loadFileBlocking(final ObjectInputStream oInp,  long timeoutForLastObject){
        
        
        final Vector fileLoaded = new Vector();
      
                 boolean loadRun = true;
                 int i =0;
                 while(loadRun){
                      try{
                         setTimeOfLastObjectLoaded();
                         Object o = oInp.readObject();
                         i++;
                         fileLoaded.add(o);
                         if(i%10000==0)System.err.println("LOADING DYAD$"+directory+i);
                      }catch (Exception e){
                          e.printStackTrace();
                          System.out.println("LOADING HAS TIMED OUT AT "+directory+"VIA AN ERROR AT"+i);
                                  
                          loadRun=false;
                          
                      }  
                 } 
        return fileLoaded;
    }  
    
    
    
    
    
        public Vector excludedForTypingAfterGoal = new Vector();
        VectorHashtable excludedChatTextByParticipant = new VectorHashtable();
        VectorHashtable excludedChatTextByMazeNo = new VectorHashtable();
    
        
        public void saveAsExcludedForTypingAfterBothOnGoal(MessageChatTextFromClient mcu, String pID, String pUsername, String directory, int mazeNo){
            String pIDUnique = pID+pUsername+directory;  
            excludedForTypingAfterGoal.addElement(mcu);
            try{
              this.excludedChatTextByParticipant.put(pIDUnique, mcu);
            }catch (Exception e){
                e.printStackTrace();
                System.err.println("ERROR2");
                System.exit(-6);
            } 
            try{
              //int newMazeNo =   
              this.excludedChatTextByMazeNo.put(mazeNo, mcu);
            }catch(Exception e){
                e.printStackTrace();
                System.err.println("ERROR3");
                System.exit(-5);
            }
        }
    
    
    public Vector getExcludedChatTextForParticipant(String pID, String pUsername, String directory, int mazeNo){
        String pIDUnique = pID+pUsername+directory;  
        Vector excludedChatTextOfPID = this.excludedChatTextByParticipant.get(pIDUnique)  ;
        Vector excludedChatTextOfMAZE = this.excludedChatTextByMazeNo.get(mazeNo);
        Vector common = VectorToolkit.getCommonObjectsInBothVectors(excludedChatTextOfPID,  excludedChatTextOfMAZE);
        return common;
          
    }
    
    
    
        
        
        
        
    
    
        static int suffix = 0;
    
        public static void main2(String[] args){
           String directory = "C:\\experimentdata\\01.TuesdayMorning\\GROUP4";
           MazeGameRecoveryGROUP4_14  mgrcGROUP4_01= new MazeGameRecoveryGROUP4_14(directory, "output6",14);
           String kFULL = mgrcGROUP4_01.getCSVChatText();
           String kCUMULATIVE = mgrcGROUP4_01.getText();
           System.out.println(kFULL);
        }
    
   
      public static void main(String[] args){
          
           String directory ="";
                   
          
       
            
           directory = "C:\\experimentdata\\07.TuesdayMorning\\DYAD2";
           MazeGameRecoveryDYAD_2_14 mgrcDYAD4_07 = new  MazeGameRecoveryDYAD_2_14(directory, "output6",16);
           String aFULL = mgrcDYAD4_07.getCSVChatText();
           String aCUMULATIVE = mgrcDYAD4_07.getText();
           
           
         
           
          // System.exit(-5);
           
           directory = "C:\\experimentdata\\08.TuesdayAfternoon1\\DYAD2";
           MazeGameRecoveryDYAD_2_14 mgrcDYAD4_08 = new  MazeGameRecoveryDYAD_2_14(directory, "output6",16);
           String bFULL = mgrcDYAD4_08.getCSVChatText();
           String bCUMULATIVE = mgrcDYAD4_08.getText();
           
          // directory = "C:\\experimentdata\\09.TuesdayAfternoon2\\DYAD2";
          // MazeGameRecoveryDYAD_2_14 mgrcDYAD4_09 = new  MazeGameRecoveryDYAD_2_14(directory, "output6",16);
           
           directory = "C:\\experimentdata\\10.ThursdayMorning\\DYAD2";
           MazeGameRecoveryDYAD_2_14 mgrcDYAD4_10 = new  MazeGameRecoveryDYAD_2_14(directory, "output6",16);
           String cFULL = mgrcDYAD4_10.getCSVChatText();
            String cCUMULATIVE = mgrcDYAD4_10.getText();
           
           directory = "C:\\experimentdata\\12.ThursdayAfternoon2\\DYAD2";
           MazeGameRecoveryDYAD_2_14 mgrcDYAD4_12 = new  MazeGameRecoveryDYAD_2_14(directory, "output6",16);
           String dFULL =mgrcDYAD4_12.getCSVChatText();
           String dCUMULATIVE =mgrcDYAD4_12.getText();
           
          //System.exit(-45);
          
          
          
          directory = "C:\\experimentdata\\01.TuesdayMorning\\DYADS4";
          MazeGameRecoveryDYAD_4_14 mgrcDYAD4_01 = new MazeGameRecoveryDYAD_4_14(directory, "output6",14,true);
          String eFULL =mgrcDYAD4_01.getCSVChatText();
           String eCUMULATIVE =mgrcDYAD4_01.getText();
           
         
          directory = "C:\\experimentdata\\02.TuesdayAfternoon\\DYADS";
          MazeGameRecoveryDYAD_4_14 mgrcDYAD4_02 = new MazeGameRecoveryDYAD_4_14(directory, "output6",16,false);               
          String fFULL =mgrcDYAD4_02.getCSVChatText();
          String fCUMULATIVE =mgrcDYAD4_02.getText();
          
          
          
          
          directory = "C:\\experimentdata\\03.WednesdayMorning\\DYADS";
          MazeGameRecoveryDYAD_4_14 mgrcDYAD4_03 = new MazeGameRecoveryDYAD_4_14(directory, "output6",16,false);
          String gFULL =mgrcDYAD4_03.getCSVChatText();
          String gCUMULATIVE =mgrcDYAD4_03.getText();
          
          
          directory = "C:\\experimentdata\\04.ThursdayMorning\\DYAD";
          MazeGameRecoveryDYAD_4_14 mgrcDYAD4_04 = new MazeGameRecoveryDYAD_4_14(directory, "output6",16,false);  
          String hFULL =mgrcDYAD4_04.getCSVChatText();
          String hCUMULATIVE =mgrcDYAD4_04.getText();
           
           
            
          directory = "C:\\experimentdata\\05.ThursdayAfternoon\\DYAD"; 
          MazeGameRecoveryDYAD_4_14 mgrcDYAD4_05 = new MazeGameRecoveryDYAD_4_14(directory, "output6",16,false);
          String iFULL=mgrcDYAD4_05.getCSVChatText();
          String iCUMULATIVE=mgrcDYAD4_05.getText();
                
          
          directory = "C:\\experimentdata\\11.ThursdayAfternoon1\\DYADS";
          MazeGameRecoveryDYAD_4_14 mgrcDYAD4_06 = new MazeGameRecoveryDYAD_4_14(directory, "output6",16,false);
          String jFULL =mgrcDYAD4_06.getCSVChatText();
          String jCUMULATIVE =mgrcDYAD4_06.getText();
          
           
           
          
          
          
           directory = "C:\\experimentdata\\01.TuesdayMorning\\GROUP4";
           MazeGameRecoveryGROUP4_14  mgrcGROUP4_01= new MazeGameRecoveryGROUP4_14(directory, "output6",14);
           String kFULL = mgrcGROUP4_01.getCSVChatText();
            String kCUMULATIVE = mgrcGROUP4_01.getText();
           
          
           
           
           directory = "C:\\experimentdata\\02.TuesdayAfternoon\\GROUP4";
           MazeGameRecoveryGROUP4_16  mgrcGROUP4_02= new MazeGameRecoveryGROUP4_16(directory, "output6",16);
            String lFULL = mgrcGROUP4_02.getCSVChatText();
             String lCUMULATIVE = mgrcGROUP4_02.getText();
           
           
           
           directory = "C:\\experimentdata\\03.WednesdayMorning\\GROUP4";
           MazeGameRecoveryGROUP4_16  mgrcGROUP4_03= new MazeGameRecoveryGROUP4_16(directory, "output6",16);
           String mFULL = mgrcGROUP4_03.getCSVChatText();
            String mCUMULATIVE = mgrcGROUP4_03.getText();
            
           
           directory = "C:\\experimentdata\\04.ThursdayMorning\\GROUP4";
           MazeGameRecoveryGROUP4_16  mgrcGROUP4_04= new MazeGameRecoveryGROUP4_16(directory, "output6",16);
           String nFULL = mgrcGROUP4_04.getCSVChatText();
            String nCUMULATIVE = mgrcGROUP4_04.getText();
             
           
           directory = "C:\\experimentdata\\05.ThursdayAfternoon\\GROUP4";
           MazeGameRecoveryGROUP4_16  mgrcGROUP4_05= new MazeGameRecoveryGROUP4_16(directory, "output6",16);
           String oFULL = mgrcGROUP4_05.getCSVChatText();
            String oCUMULATIVE = mgrcGROUP4_05.getText();
              
           directory = "C:\\experimentdata\\06.Friday\\GROUP4";
           MazeGameRecoveryGROUP4_16  mgrcGROUP4_06= new MazeGameRecoveryGROUP4_16(directory, "output6",16);
           String pFULL = mgrcGROUP4_06.getCSVChatText();
            String pCUMULATIVE = mgrcGROUP4_06.getText();
            
            
           
          
           directory = "C:\\experimentdata\\07.TuesdayMorning\\GROUP4";
           MazeGameRecoveryGROUP4_16  mgrcGROUP4_07= new MazeGameRecoveryGROUP4_16(directory, "output6",16);
           String qFULL = mgrcGROUP4_07.getCSVChatText();
            String qCUMULATIVE = mgrcGROUP4_07.getText();
                
           directory = "C:\\experimentdata\\08.TuesdayAfternoon1\\GROUP4";
           MazeGameRecoveryGROUP4_16  mgrcGROUP4_08= new MazeGameRecoveryGROUP4_16(directory, "output6",16);     
           String rFULL = mgrcGROUP4_08.getCSVChatText();  
           String rCUMULATIVE = mgrcGROUP4_08.getText();  
           
           
           directory = "C:\\experimentdata\\10.ThursdayMorning\\GROUP4";
           MazeGameRecoveryGROUP4_16  mgrcGROUP4_10= new MazeGameRecoveryGROUP4_16(directory, "output6",16);
           String sFULL = mgrcGROUP4_10.getCSVChatText();  
            String sCUMULATIVE = mgrcGROUP4_10.getText();  
           
           
           directory = "C:\\experimentdata\\11.ThursdayAfternoon1\\GROUP4";
           MazeGameRecoveryGROUP4_16  mgrcGROUP4_11= new MazeGameRecoveryGROUP4_16(directory, "output6",16);
           String tFULL = mgrcGROUP4_11.getCSVChatText();
           String tCUMULATIVE = mgrcGROUP4_11.getText();
        
          
         
           directory = "C:\\experimentdata\\01.TuesdayMorning\\GROUP8.crashedAFTER10Games";
           MazeGameRecoveryGROUP8 mgrcGROUP8_01 = new MazeGameRecoveryGROUP8(directory,"output6",14);
           String uFULL =mgrcGROUP8_01.getCSVChatText();
            String uCUMULATIVE =mgrcGROUP8_01.getText();
          
            
          
          
          directory = "C:\\experimentdata\\02.TuesdayAfternoon\\GROUP8";
          MazeGameRecoveryGROUP8 mgrcGROUP8_02 = new MazeGameRecoveryGROUP8(directory,"output6",16);
          String vFULL =mgrcGROUP8_02.getCSVChatText();
          String vCUMULATIVE =mgrcGROUP8_02.getText();
          
         
          
          
          directory = "C:\\experimentdata\\04.ThursdayMorning\\GROUP8.CRASHED" ;
          MazeGameRecoveryGROUP8 mgrcGROUP8_04 = new MazeGameRecoveryGROUP8(directory,"output6",16);
          String wFULL =mgrcGROUP8_04.getCSVChatText();
           String wCUMULATIVE =mgrcGROUP8_04.getText();
         
          directory = "C:\\experimentdata\\05.ThursdayAfternoon\\GROUP8"; 
          MazeGameRecoveryGROUP8 mgrcGROUP8_05 = new MazeGameRecoveryGROUP8(directory,"output6",16);
          String xFULL =mgrcGROUP8_05.getCSVChatText();
           String xCUMULATIVE =mgrcGROUP8_05.getText();
          
          
          
          directory = "C:\\experimentdata\\08.TuesdayAfternoon1\\GROUP8";
          MazeGameRecoveryGROUP8 mgrcGROUP8_08 = new MazeGameRecoveryGROUP8(directory,"output6",16);
          String yFULL =mgrcGROUP8_08.getCSVChatText();
           String yCUMULATIVE =mgrcGROUP8_08.getText();
          
          
         
          directory = "C:\\experimentdata\\09.TuesdayAfternoon2\\GROUP8";
          MazeGameRecoveryGROUP8 mgrcGROUP8_09 = new MazeGameRecoveryGROUP8(directory,"output6",16);
          String zFULL =mgrcGROUP8_09.getCSVChatText();
            String zCUMULATIVE =mgrcGROUP8_09.getText();
          
            
        //  System.exit(-5);  
            
            
          directory = "C:\\experimentdata\\11.ThursdayAfternoon1\\GROUP8.INTERRUPTED-SOMEHADTOLEAVE";
          MazeGameRecoveryGROUP8 mgrcGROUP8_11 = new MazeGameRecoveryGROUP8(directory,"output6",16);
          String aaFULL =mgrcGROUP8_11.getCSVChatText(); 
          String aaCUMULATIVE =mgrcGROUP8_11.getText(); 
          
          
          directory = "C:\\experimentdata\\12.ThursdayAfternoon2\\GROUP8";
          MazeGameRecoveryGROUP8 mgrcGROUP8_12 = new MazeGameRecoveryGROUP8(directory,"output6",16);
          String abFULL =mgrcGROUP8_12.getCSVChatText();
          String abCUMULATIVE =mgrcGROUP8_12.getText();
           
          //String  aFULL, bFULL,cFULL,dFULL,eFULL,fFULL,gFULL,hFULL,iFULL,jFULL,kFULL,lFULL, mFULL,nFULL,oFULL,pFULL,qFULL,rFULL,sFULL,tFULL, uFULL;
           
          
          String full = aFULL + bFULL + cFULL + dFULL + eFULL + fFULL + gFULL + hFULL + iFULL +jFULL + kFULL + lFULL + mFULL + nFULL + oFULL +pFULL +qFULL +rFULL +sFULL +tFULL 
                        +uFULL +vFULL +wFULL +xFULL + yFULL + zFULL + aaFULL + abFULL;
          
          
          String cumulative = aCUMULATIVE + bCUMULATIVE + cCUMULATIVE + dCUMULATIVE + eCUMULATIVE + fCUMULATIVE + gCUMULATIVE + hCUMULATIVE + iCUMULATIVE + jCUMULATIVE 
                              +kCUMULATIVE + lCUMULATIVE + mCUMULATIVE + nCUMULATIVE + pCUMULATIVE + qCUMULATIVE + rCUMULATIVE + sCUMULATIVE + tCUMULATIVE + uCUMULATIVE
                              +vCUMULATIVE + wCUMULATIVE + xCUMULATIVE +yCUMULATIVE + zCUMULATIVE  + aaCUMULATIVE + abCUMULATIVE;
          
          
          full = MazeGameRecoveryGROUP8.getHeaderFULL() + full;
          cumulative = MazeGameRecoveryGROUP8.getHeaderCUMULATIVE() + cumulative;
          
          
         directory = System.getProperty("user.dir");
         File f =new File(directory+"\\full.1002b"+suffix+".csv");
         try{
            FileWriter fw = new FileWriter(f);
            fw.append(full);
            fw.flush();
            fw.close();
         }catch (Exception e){
             e.printStackTrace();
         }
         File f2 =new File(directory+"\\cumulative.1002b"+suffix+".csv");
         try{
            FileWriter fw2 = new FileWriter(f2);
            fw2.append(cumulative);
            fw2.flush();
            fw2.close();
         }catch (Exception e){
             e.printStackTrace();
         }
        
   }
          
      
    
    
     
     
      
      
   
  
    
    
    
    
     //MazeGameRecoveryGROUP8 
     //   String directory = "C:\\experimentdata\\01.TuesdayMorning\\GROUP8.crashedAFTER10Games";
    //     String directory = "C:\\experimentdata\\02.TuesdayAfternoon\\GROUP8";
      // String directory = "C:\\experimentdata\\04.ThursdayMorning\\GROUP8.CRASHED" ;
      //       String directory = "C:\\experimentdata\\05.ThursdayAfternoon\\GROUP8";  
       //   String directory = "C:\\experimentdata\\05.ThursdayAfternoon\\GROUP8";
       //String directory = "C:\\experimentdata\\08.TuesdayAfternoon1\\GROUP8";
      //String directory = "C:\\experimentdata\\09.TuesdayAfternoon2\\GROUP8";
      // String directory = "C:\\experimentdata\\11.ThursdayAfternoon1\\GROUP8.INTERRUPTED-SOMEHADTOLEAVE";
      // String directory = "C:\\experimentdata\\12.ThursdayAfternoon2\\GROUP8";
       
       
       
       
      // MazeGameRecoveryDYAD_2_14
       //String directory = "C:\\experimentdata\\07.TuesdayMorning\\DYAD2";
      //String directory = "C:\\experimentdata\\08.TuesdayAfternoon1\\DYAD2";
      //String directory = "C:\\experimentdata\\09.TuesdayAfternoon2\\DYAD2";
      //String directory = "C:\\experimentdata\\10.ThursdayMorning\\DYAD2";
      //String directory = "C:\\experimentdata\\12.ThursdayAfternoon2\\DYAD2";
    
    
      //MazeGameRecoveryGROUP4_14 
      //String directory = "C:\\experimentdata\\01.TuesdayMorning\\GROUP4";

      
      String refMazesDirector = System.getProperty("user.dir")+File.separator+"experimentresources" 
                        + File.separator + "mazegame" + File.separator + "mazegamesetupswithbackup" + 
                          File.separator +"set_REFERENCESET" +"cl1mzes.v";
              String refMazesMatcher = System.getProperty("user.dir")+File.separator+"experimentresources" 
                        + File.separator + "mazegame" + File.separator + "mazegamesetupswithbackup" + 
                          File.separator +"set_REFERENCESET" +"cl2mzes.v";
      
      
      
}
