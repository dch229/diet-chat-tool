/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client.mazegameutils;

import client.Maze;
import client.MazeGameMazeIdentifier;
import diet.message.Keypress;
import diet.message.Message;
import diet.message.MessageChatTextFromClient;
import diet.task.mazegame.message.MessageCursorUpdate;
import diet.utils.Conversion;
import diet.utils.VectorToolkit;
import diet.utils.spreadsheet.Spreadsheet248MazeGame;
import diet.utils.stringsimilarity.StringSimilarityMeasure;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author sre
 */
public class MazeGameRecoveryDYAD_4_14 extends MazeGameRecovery{
    
    
      
      //String directory = "E:\\experimentdata\\1stslot.tuesdaymorning\\dyad\\0006CCMAZE_DYADIC_MULTI_PARTICIPANTS_SEPARATEDYADS";
      //String directory = "C:\\Users\\sre\\Desktop\\databackup\\1stslot.tuesdaymorning\\dyad\\0006CCMAZE_DYADIC_MULTI_PARTICIPANTS_SEPARATEDYADS";
      
      
      //String directory = "E:\\experimentdata\\2ndslot.tuesdayafternoon\\dyad\\0008CCMAZE_DYADIC_MULTI_PARTICIPANTS_SEPARATEDYADS";
      //String directory = "E:\\experimentdata\\3rdslot.wednesday\\dyad\\0011CCMAZE_DYADIC_MULTI_PARTICIPANTS_SEPARATEDYADS";
      //String directory = "E:\\experimentdata\\4thslot.thursday.morning\\dyad\\0013CCMAZE_DYADIC_MULTI_PARTICIPANTS_SEPARATEDYADS";
      //String directory = "E:\\experimentdata\\5th.Thursday afternoon\\0014CCMAZE_DYADIC_MULTI_PARTICIPANTS_SEPARATEDYADS";
      //String directory = "E:\\experimentdata\\7th. Tuesday morning\\0001CCMAZE_DYADIC_MULTI_PARTICIPANTS_SEPARATEDYADS";
      //String directory = "E:\\experimentdata\\8th.Tuesday afternoon (1)\\0002CCMAZE_DYADIC_MULTI_PARTICIPANTS_SEPARATEDYADS";
      //String directory = "E:\\experimentdata\\11th.Thursday afternoon (1)\\0006CCMAZE_DYADIC_MULTI_PARTICIPANTS_SEPARATEDYADS";
      
      //C:\\experimentdata\\01.TuesdayMorning\\DYADS4";
     // String directory = "C:\\experimentdata\\02.TuesdayAfternoon\\DYADS";
     // String directory = "C:\\experimentdata\\03.WednesdayMorning\\DYADS";
     //String directory = "C:\\experimentdata\\04.ThursdayMorning\\DYAD";
     //String directory = "C:\\experimentdata\\05.ThursdayAfternoon\\DYAD";
      //String directory = "C:\\experimentdata\\11.ThursdayAfternoon1\\DYADS";
    
    
      Vector vClient1;
      Vector vClient2;
      
      Vector mazegamemessages;
      Vector mazegameturns;
       
     String filenameOUTPUT="";
     
     int or14or16 ;
     boolean findnamesManually = false;
     
     String p1ID;
     String p2ID;
     String p3ID;
     String p4ID;
     String p5ID;
     String p6ID;
     String p7ID;
     String p8ID;
     
     String p1Username;
     String p2Username;
     String p3Username;
     String p4Username;
     String p5Username;
     String p6Username;
     String p7Username;
     String p8Username;
     
     
     String p1PARTNERUSERNAME;
     String p2PARTNERUSERNAME;
     String p3PARTNERUSERNAME;
     String p4PARTNERUSERNAME;
     
     String p1PARTNERID;
     String p2PARTNERID;
     String p3PARTNERID;
     String p4PARTNERID;
     
          
     boolean p1IsDirector;
     boolean p2IsDirector;
     boolean p3IsDirector;
     boolean p4IsDirector;
     
     
     long[] p1MazeStartTime = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     long[] p2MazeStartTime = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     long[] p3MazeStartTime = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     long[] p4MazeStartTime = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     long[] p5MazeStartTime = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     long[] p6MazeStartTime = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     long[] p7MazeStartTime = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     long[] p8MazeStartTime = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     
     
     long[] p1MazeBothFinishTime = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     long[] p2MazeBothFinishTime = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     long[] p3MazeBothFinishTime = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     long[] p4MazeBothFinishTime = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     long[] p5MazeBothFinishTime = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     long[] p6MazeBothFinishTime = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     long[] p7MazeBothFinishTime = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     long[] p8MazeBothFinishTime = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     
     
     long[] p1MazeForcedStopTime = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     long[] p2MazeForcedStopTime = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     long[] p3MazeForcedStopTime = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     long[] p4MazeForcedStopTime = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     long[] p5MazeForcedStopTime = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     long[] p6MazeForcedStopTime = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     long[] p7MazeForcedStopTime = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     long[] p8MazeForcedStopTime = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     
     
     
     Vector[] p1ChatTextTurns = {new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector()};
     Vector[] p2ChatTextTurns = {new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector()};
     Vector[] p3ChatTextTurns = {new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector()};
     Vector[] p4ChatTextTurns = {new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector()};
     Vector[] p5ChatTextTurns = {new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector()};
     Vector[] p6ChatTextTurns = {new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector()};
     Vector[] p7ChatTextTurns = {new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector()};
     Vector[] p8ChatTextTurns = {new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector()};
     
     
     Vector[] p1CursorUpdates = {new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector()};
     Vector[] p2CursorUpdates = {new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector()};
     Vector[] p3CursorUpdates = {new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector()};
     Vector[] p4CursorUpdates = {new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector()};
     Vector[] p5CursorUpdates = {new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector()};
     Vector[] p6CursorUpdates = {new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector()};
     Vector[] p7CursorUpdates = {new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector()};
     Vector[] p8CursorUpdates = {new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector(),new Vector()};
      
    
     String[] p1Mazes =  {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};
     String[] p2Mazes =  {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};
     String[] p3Mazes =  {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};
     String[] p4Mazes =  {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};
     String[] p5Mazes =  {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};
     String[] p6Mazes =  {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};
     String[] p7Mazes =  {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};
     String[] p8Mazes =  {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};
     
     
     int[] p1WordCount = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     int[] p2WordCount = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     int[] p3WordCount = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     int[] p4WordCount = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     int[] p5WordCount = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     int[] p6WordCount = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     int[] p7WordCount = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     int[] p8WordCount = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     
     
     int[] p1UniqueWordCount = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     int[] p2UniqueWordCount = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     int[] p3UniqueWordCount = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     int[] p4UniqueWordCount = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     int[] p5UniqueWordCount = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     int[] p6UniqueWordCount = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     int[] p7UniqueWordCount = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     int[] p8UniqueWordCount = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     
     
     int[] p1Chars = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     int[] p2Chars = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     int[] p3Chars = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     int[] p4Chars = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     int[] p5Chars = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     int[] p6Chars = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     int[] p7Chars = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     int[] p8Chars = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
     
     
     
     String[] p1allWords = {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};
     String[] p2allWords = {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};
     String[] p3allWords = {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};
     String[] p4allWords = {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};
     String[] p5allWords = {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};
     String[] p6allWords = {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};
     String[] p7allWords = {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};
     String[] p8allWords = {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};
     
    
     
      public void removePipeFromChatText(){
         Conversion.replacePipeCharacterFromChatTextWithSlash(p1ChatTextTurns);
         Conversion.replacePipeCharacterFromChatTextWithSlash(p2ChatTextTurns);
         Conversion.replacePipeCharacterFromChatTextWithSlash(p3ChatTextTurns);
         Conversion.replacePipeCharacterFromChatTextWithSlash(p4ChatTextTurns);
         Conversion.replacePipeCharacterFromChatTextWithSlash(p5ChatTextTurns);
         Conversion.replacePipeCharacterFromChatTextWithSlash(p6ChatTextTurns);
         Conversion.replacePipeCharacterFromChatTextWithSlash(p7ChatTextTurns);
         Conversion.replacePipeCharacterFromChatTextWithSlash(p8ChatTextTurns);
         Conversion.replacePipeCharacterFromChatTextWithSlash(new Vector[]{super.excludedForTypingAfterGoal});
     }
     
     
     
   
    
    
    public  MazeGameRecoveryDYAD_4_14(String directory,String filenameOutput, int or14or16, boolean findnamesManually){
         super.directory=directory;
         this.or14or16 = or14or16;
         this.filenameOUTPUT=filenameOutput;
         this.findnamesManually=findnamesManually;
         doAllProcessing();
    }
    
    public void doAllProcessing() {
          
         
          //System.out.println(this.getMazeInforMusic("mazeiscompleted", " mazenumber:0 mazestarttime:1376377144354 mazeexittime:1376377701625 mazeiscompleted:COMPLETED mazetotalnumberofgamemoves:61 participanttotalnumberofgamemoves:17 participanttotalnumberofswitchtraversals:2 partnername: Lamington"));
          
          //System.exit(-4);
          
         // Spreadsheet248MazeGame ssmg= new Spreadsheet248MazeGame(directory+"\\turns.txt");
          
          //System.out.println(ssmg.getValue("Text", 0));
          //System.out.println(ssmg.getValue("Text", 1));
          //System.out.println(ssmg.getValue("Text", 2));
          //System.out.println(ssmg.getValue("Text", 3));
          
          
          //Vector v2 = ssmg.findString("Please start", "Text");
          
         // if(2<5)System.exit(-333);
          
          
          
          Vector v = new Vector();
          String fileToLoad = "unset";
          try{
               FileInputStream fi;
               fileToLoad =directory+File.separator+"cl1Mazes.v";
               fi = new FileInputStream(new File(fileToLoad));
               ObjectInputStream oInp = new ObjectInputStream(fi);
               Object o = oInp.readObject();//v = (Vector)oInp.readObject();
               vClient1 = (Vector)o;
               oInp.close();
               fi.close();
         }catch(Exception e){
             System.out.println("can't load in setup mazes1 "+e.getMessage()+"the name is: "+fileToLoad);
             e.printStackTrace();
 
         }
       
           v = new  Vector();
          fileToLoad = "unset";
          try{
               FileInputStream fi;
               fileToLoad =directory+File.separator+"cl2Mazes.v";
               fi = new FileInputStream(new File(fileToLoad));
               ObjectInputStream oInp = new ObjectInputStream(fi);
               Object o = oInp.readObject();//v = (Vector)oInp.readObject();
               vClient2 = (Vector)o;
               oInp.close();
               fi.close();
         }catch(Exception e){
             System.out.println("can't load in setup mazes2 "+e.getMessage()+"the name is: "+fileToLoad);
             e.printStackTrace();
 
         } 
         
          fileToLoad = "unset";
          try{
               FileInputStream fi;
               fileToLoad =directory+File.separator+"messages.dat";
               fi = new FileInputStream(new File(fileToLoad));
               ObjectInputStream oInp = new ObjectInputStream(fi);
               mazegamemessages = this.loadFileBlocking(oInp, 100) ;
               //System.out.println("SIZE IS"+vMazeGameMessages.size());
               oInp.close();
               fi.close();
         }catch(Exception e){
             System.out.println("can't load in mazegamemessages "+e.getMessage()+"the name is: "+fileToLoad);
             e.printStackTrace();
 
         }
         
         fileToLoad = "unset";
          try{
               //FileInputStream fi;
               //fileToLoad =directory+File.separator+"turns.dat";
               //fi = new FileInputStream(new File(fileToLoad));
               //ObjectInputStream oInp = new ObjectInputStream(fi);
               //mazegameturns = this.loadFileBlocking(oInp, 1500) ;
               //System.out.println("SIZE IS"+vMazeGameMessages.size());
               //oInp.close();
               //fi.close();
               //System.out.println("THERE ARE "+mazegameturns.size()+" maze game turns");
         }catch(Exception e){
             System.out.println("can't load in mazegameturns "+e.getMessage()+"the name is: "+fileToLoad);
             e.printStackTrace();
             System.exit(-2342);
 
         }       
         //processingFindNames();
        
         if(findnamesManually){
              processingFindNamesManually("EEEE","HHHH","FFFF","GGGG");
         }
         else{
              processingFindNames();
         }
         
         
         processingCalculateFINDPARTNER();
         processingCalculateDirectorOrMatcher();
         processingGetmazeIDs();
         processingCalculateMazeStartTimes();
         processingCalculateFinishTime();
         processingAddStartTimeForMaze15();
         processingExcludingPostBothOnGoal();
         removePipeFromChatText();
         processingText();
         this.processingFindInterventions();
         displayInfo();
         saveInfo();
   }
    
    
    
    
  
    
    
    
    
    
    
    
    
        
    
    
     
     
     
      
      
      public Maze getMazeForParticipant(String participantID, int mazeNo){
           String partnerID = this.getPartnerID(participantID, mazeNo);
          
          
           String actualDirectorID = this.getDirector(participantID, partnerID);
           if(actualDirectorID.equalsIgnoreCase(participantID)) return (Maze)this.vClient1.elementAt(mazeNo);
           else if(actualDirectorID.equalsIgnoreCase(partnerID)) return (Maze)this.vClient2.elementAt(mazeNo);
           else {System.exit(-34);}
           return null;
               
      }
      
      
     public String getPartnerID(String participantID, int mazeNo){
         if(participantID.equals(this.p1ID))return this.p1PARTNERID;  
         else if(participantID.equals(this.p2ID))return this.p2PARTNERID;
         else if(participantID.equals(this.p3ID))return this.p3PARTNERID;
         else if(participantID.equals(this.p4ID))return this.p4PARTNERID;
         System.exit(-23432432);
         return null;
     }      
     
     
     
     //GROUP4|15|EEEElv26|princess|A15|1376540691571|1376540946516|16|28|65|46|277| 1 2nd row down on the left  my x is fourth row down, 2nd from left not open :( yep which grey of yours is closest to my/your x? on it. now mine just sealed up yes please yep ok cool I'm on my x now haha yep that's the one I used before but then I got trapped in there yep yes!|GROUP4|15|FFFFam10|ashleigh|B15|1376540691571|1376540946516|13|43|149|57|668| 3 greys second row from the top and far right, another 4th row down and far left and then another second row from the bottom and second in from the left i think i'm on your grey? now? i have a grey, next to your x on the left on my x now do you need me to go back to your grey? okay, i can't get to my x now haha so your x is where again, fourth row down from top and second in from left? i have a grey next to that on left okay, i have another second row from top and far left or second row from the bottom and second in from left, can you get to either of those without getting trapped by gates? second row from top and far right* on my x now, can you get to yours?|E:\experimentdata\4thslot.thursday.morning\dyad\0013CCMAZE_DYADIC_MULTI_PARTICIPANTS_SEPARATEDYADS
//GROUP4|15|GGGGTW24|toni|A15|1376538143958|1376538360524|9|50|34|25|138| top right to middle left you? any grey in the top right? my only grey is 5c 2r i'm on x where is your grey my x is 2nd C gate any others?|GROUP4|15|HHHHILR26|RENE|B15|1376538143958|1376538360524|14|21|59|43|262| far left to middle do you have any greys bottom left? i mean bottom right 6th row 2nd from top thats tricky okay ill go there i cant get through where is your x again? okay farest left is my grey 5 C last row and 5 C 2nd row sorry 6th collum 2nd row no my x on*|E:\experimentdata\4thslot.thursday.morning\dyad\0013CCMAZE_DYADIC_MULTI_PARTICIPANTS_SEPARATEDYADS
    
     
     
     public String getDirector(String pIDA, String pIDB){
         String director1 = this.getDirectorAB(pIDA, pIDB);
         String director2 = this.getDirectorAB(pIDB, pIDA);
         if(director1==null && director2==null){
              System.out.println("DIRECTORA:"+pIDA);
             System.out.println("DIRECTORB:"+pIDB);
             System.exit(-202);
         }
         if(director1!=null && director2!=null &! director1.equals(director2)){
             System.out.println("DIRECTOR1:"+director1);
             System.out.println("DIRECTOR2:"+director2);
             System.exit(-212);
         }
         if(director1!=null) return director1;
         if(director2!=null) return director2;
         return null;
     }
     
     
     private String getDirectorAB(String pA, String pB){
          if(pA.equalsIgnoreCase(p1ID)&&this.p1IsDirector) return pA;
          if(pA.equalsIgnoreCase(p2ID)&&this.p2IsDirector) return pA;
          if(pA.equalsIgnoreCase(p3ID)&&this.p3IsDirector) return pA;
          if(pA.equalsIgnoreCase(p4ID)&&this.p4IsDirector) return pA;
          if(pB.equalsIgnoreCase(p1ID)&&this.p1IsDirector) return pB;
          if(pB.equalsIgnoreCase(p2ID)&&this.p2IsDirector) return pB;
          if(pB.equalsIgnoreCase(p3ID)&&this.p3IsDirector) return pB;
          if(pB.equalsIgnoreCase(p4ID)&&this.p4IsDirector) return pB;
          
         
       
          return null;
         }
           
      public void processingCalculateDirectorOrMatcher(){
          String refMazesDirector = directory+"\\cl1Mazes.v";
          String refMazesMatcher = directory+"\\cl2Mazes.v";
          MazeGameMazeIdentifier mgmi = new MazeGameMazeIdentifier(refMazesDirector,refMazesMatcher);
          this.p1IsDirector =  mgmi.isParticipantDirector(p1ID, this.mazegamemessages);
          mgmi = new MazeGameMazeIdentifier(refMazesDirector,refMazesMatcher);
          this.p2IsDirector =  mgmi.isParticipantDirector(p2ID, this.mazegamemessages);
          mgmi = new MazeGameMazeIdentifier(refMazesDirector,refMazesMatcher);
          this.p3IsDirector =  mgmi.isParticipantDirector(p3ID, this.mazegamemessages);
          mgmi = new MazeGameMazeIdentifier(refMazesDirector,refMazesMatcher);
          this.p4IsDirector =  mgmi.isParticipantDirector(p4ID, this.mazegamemessages);
          
          System.out.println(p1IsDirector);
          System.out.println(p2IsDirector);
          System.out.println(p3IsDirector);
          System.out.println(p4IsDirector);
         // System.exit(-6);
      }
     
      
      public void processingGetmazeIDs_OLD() {
           
          //String refMazesDirector = "C:\\sourceforge\\mazegamesetupswithbackup\\set_REFERENCESET\\cl1mzes.v";
          //String refMazesMatcher = "C:\\sourceforge\\mazegamesetupswithbackup\\set_REFERENCESET\\cl2mzes.v";
          MazeGameMazeIdentifier mgmi = new MazeGameMazeIdentifier(refMazesDirector,refMazesMatcher);
           
           
          
                   
           for(int i=0;i<14;i++){
                try{
                this.p1Mazes[i]=mgmi.getMazeID(this.getMazeForParticipant(p1ID, i));
                
                this.p2Mazes[i]=mgmi.getMazeID(this.getMazeForParticipant(p2ID, i));
                this.p3Mazes[i]=mgmi.getMazeID(this.getMazeForParticipant(p3ID, i));
                this.p4Mazes[i]=mgmi.getMazeID(this.getMazeForParticipant(p4ID, i));
                }catch (Exception e){
                    System.err.println("THE CRASH OCCURS AT:"+i);
                    e.printStackTrace();
                    if(p2ID==null)System.exit(-5569);
                    System.exit(-555);
                    
                }
                
                
           }
      }
      
      
      
     
     
       public void processingFindNamesManually(String firstIDPrefix, String secondIDPrefix, String thirdIDPrefix, String fourthIDPrefix){
        
         for(int i=0;i<this.mazegamemessages.size();i++){
             System.out.println("LOOKING2");
             Object o = mazegamemessages.elementAt(i);
             //System.out.println(o.getClass().getName());
             String nameOfClass = o.getClass().getName();
             if(nameOfClass.equalsIgnoreCase("diet.task.mazegame.message.MessageCursorUpdate")){            
                  MessageCursorUpdate mcu = (MessageCursorUpdate)o;
                  String senderID = mcu.getEmail();
                   System.out.println("........."+senderID);
                  String senderUsernName = mcu.getUsername();
                  
                  if(senderID.startsWith(firstIDPrefix)){
                        if(p1ID==null && p1Username==null){
                             this.p1ID=senderID;
                             this.p1Username=senderUsernName;
                             System.out.println("FOUND P1");
                             
                             
                        }
                        else if(this.p1ID!=null &! p1ID.equals(senderID)){
                             System.exit(-1111);
                        }
                        else if (this.p1Username!=null &! this.p1Username.equals(senderUsernName)){
                             System.exit(-1111111111);
                        }
                  }
                  if(senderID.startsWith(secondIDPrefix)){
                        if(p2ID==null && p2Username==null){
                             this.p2ID=senderID;
                             this.p2Username=senderUsernName;
                             System.out.println("FOUND P2");
                        }
                        else if(this.p2ID!=null &! p2ID.equals(senderID)){
                             System.exit(-1111);
                        }
                        else if (this.p2Username!=null &! this.p2Username.equals(senderUsernName)){
                             System.exit(-1111111111);
                        }
                  }
                  if(senderID.startsWith(thirdIDPrefix)){
                        if(p3ID==null && p3Username==null){
                             this.p3ID=senderID;
                             this.p3Username=senderUsernName;
                             System.out.println("FOUND P3");
                        }
                        else if(this.p3ID!=null &! p3ID.equals(senderID)){
                             System.exit(-1111);
                        }
                        else if (this.p3Username!=null &! this.p3Username.equals(senderUsernName)){
                             System.exit(-1111111111);
                        }
                  }
                  if(senderID.startsWith(fourthIDPrefix)){
                        if(p4ID==null && p4Username==null){
                             this.p4ID=senderID;
                             this.p4Username=senderUsernName;
                             System.out.println("FOUND P4");
                        }
                        else if(this.p4ID!=null &! p4ID.equals(senderID)){
                             System.exit(-1111);
                        }
                        else if (this.p4Username!=null &! this.p4Username.equals(senderUsernName)){
                             System.exit(-1111111111);
                        }
                  }
                  if(senderID.startsWith("5555")){
                        if(p5ID==null && p5Username==null){
                             this.p5ID=senderID;
                             this.p5Username=senderUsernName;
                             System.out.println("FOUND P5");
                        }
                        else if(this.p5ID!=null &! p5ID.equals(senderID)){
                             System.exit(-1111);
                        }
                        else if (this.p5Username!=null &! this.p5Username.equals(senderUsernName)){
                             System.exit(-1111111111);
                        }
                  }
                  if(senderID.startsWith("6666")){
                        if(p6ID==null && p6Username==null){
                             this.p6ID=senderID;
                             this.p6Username=senderUsernName;
                             System.out.println("FOUND P6");
                        }
                        else if(this.p6ID!=null &! p6ID.equals(senderID)){
                             System.exit(-1111);
                        }
                        else if (this.p6Username!=null &! this.p6Username.equals(senderUsernName)){
                             System.exit(-1111111111);
                        }
                  }
                  if(senderID.startsWith("7777")){
                        if(p7ID==null && p7Username==null){
                             this.p7ID=senderID;
                             this.p7Username=senderUsernName;
                             System.out.println("FOUND P7");
                        }
                        else if(this.p7ID!=null &! p7ID.equals(senderID)){
                             System.exit(-1111);
                        }
                        else if (this.p7Username!=null &! this.p7Username.equals(senderUsernName)){
                             System.exit(-1111111111);
                        }
                  }
                  if(senderID.startsWith("8888")){
                        if(p8ID==null && p8Username==null){
                             this.p8ID=senderID;
                             this.p8Username=senderUsernName;
                             System.out.println("FOUND P8");
                        }
                        else if(this.p8ID!=null &! p8ID.equals(senderID)){
                             System.exit(-1111);
                        }
                        else if (this.p8Username!=null &! this.p8Username.equals(senderUsernName)){
                             System.exit(-1111111111);
                        }
                  }
             }
         }
         if(p1ID==null)System.exit(-15);
         if(p2ID==null)System.exit(-16);
         if(p3ID==null)System.exit(-17);
         if(p4ID==null)System.exit(-18);
       }
      
      
      
       public void processingFindNames(){
        
         for(int i=0;i<this.mazegamemessages.size();i++){
             System.out.println("LOOKING2");
             Object o = mazegamemessages.elementAt(i);
             //System.out.println(o.getClass().getName());
             String nameOfClass = o.getClass().getName();
             if(nameOfClass.equalsIgnoreCase("diet.task.mazegame.message.MessageCursorUpdate")){            
                  MessageCursorUpdate mcu = (MessageCursorUpdate)o;
                  String senderID = mcu.getEmail();
                   System.out.println("........."+senderID);
                  String senderUsernName = mcu.getUsername();
                  
                  if(senderID.startsWith("EEEE")){
                        if(p1ID==null && p1Username==null){
                             this.p1ID=senderID;
                             this.p1Username=senderUsernName;
                             System.out.println("FOUND P1");
                             
                             
                        }
                        else if(this.p1ID!=null &! p1ID.equals(senderID)){
                             System.exit(-1111);
                        }
                        else if (this.p1Username!=null &! this.p1Username.equals(senderUsernName)){
                             System.exit(-1111111111);
                        }
                  }
                  if(senderID.startsWith("FFFF")){
                        if(p2ID==null && p2Username==null){
                             this.p2ID=senderID;
                             this.p2Username=senderUsernName;
                             System.out.println("FOUND P2");
                        }
                        else if(this.p2ID!=null &! p2ID.equals(senderID)){
                             System.exit(-1111);
                        }
                        else if (this.p2Username!=null &! this.p2Username.equals(senderUsernName)){
                             System.exit(-1111111111);
                        }
                  }
                  if(senderID.startsWith("GGGG")){
                        if(p3ID==null && p3Username==null){
                             this.p3ID=senderID;
                             this.p3Username=senderUsernName;
                             System.out.println("FOUND P3");
                        }
                        else if(this.p3ID!=null &! p3ID.equals(senderID)){
                             System.exit(-1111);
                        }
                        else if (this.p3Username!=null &! this.p3Username.equals(senderUsernName)){
                             System.exit(-1111111111);
                        }
                  }
                  if(senderID.startsWith("HHHH")){
                        if(p4ID==null && p4Username==null){
                             this.p4ID=senderID;
                             this.p4Username=senderUsernName;
                             System.out.println("FOUND P4");
                        }
                        else if(this.p4ID!=null &! p4ID.equals(senderID)){
                             System.exit(-1111);
                        }
                        else if (this.p4Username!=null &! this.p4Username.equals(senderUsernName)){
                             System.exit(-1111111111);
                        }
                  }
                  if(senderID.startsWith("5555")){
                        if(p5ID==null && p5Username==null){
                             this.p5ID=senderID;
                             this.p5Username=senderUsernName;
                             System.out.println("FOUND P5");
                        }
                        else if(this.p5ID!=null &! p5ID.equals(senderID)){
                             System.exit(-1111);
                        }
                        else if (this.p5Username!=null &! this.p5Username.equals(senderUsernName)){
                             System.exit(-1111111111);
                        }
                  }
                  if(senderID.startsWith("6666")){
                        if(p6ID==null && p6Username==null){
                             this.p6ID=senderID;
                             this.p6Username=senderUsernName;
                             System.out.println("FOUND P6");
                        }
                        else if(this.p6ID!=null &! p6ID.equals(senderID)){
                             System.exit(-1111);
                        }
                        else if (this.p6Username!=null &! this.p6Username.equals(senderUsernName)){
                             System.exit(-1111111111);
                        }
                  }
                  if(senderID.startsWith("7777")){
                        if(p7ID==null && p7Username==null){
                             this.p7ID=senderID;
                             this.p7Username=senderUsernName;
                             System.out.println("FOUND P7");
                        }
                        else if(this.p7ID!=null &! p7ID.equals(senderID)){
                             System.exit(-1111);
                        }
                        else if (this.p7Username!=null &! this.p7Username.equals(senderUsernName)){
                             System.exit(-1111111111);
                        }
                  }
                  if(senderID.startsWith("8888")){
                        if(p8ID==null && p8Username==null){
                             this.p8ID=senderID;
                             this.p8Username=senderUsernName;
                             System.out.println("FOUND P8");
                        }
                        else if(this.p8ID!=null &! p8ID.equals(senderID)){
                             System.exit(-1111);
                        }
                        else if (this.p8Username!=null &! this.p8Username.equals(senderUsernName)){
                             System.exit(-1111111111);
                        }
                  }
             }
         }
         if(p1ID==null)System.exit(-15);
         if(p2ID==null)System.exit(-16);
         if(p3ID==null)System.exit(-17);
         if(p4ID==null)System.exit(-18);
       }
       
       
   
       
    public String getMazeInfo(String needleValue, String haystack ){
         
       try{ 
         int i = haystack.indexOf(needleValue);
         if(i<0){
             System.err.println(needleValue);
             if(needleValue.equalsIgnoreCase("mazeexittime")){
                 needleValue="mazestoptime";
                 i = haystack.indexOf(needleValue);
                 if(i<0)System.exit(-32432425);
             }
             else{
                System.exit(-2342349);    
             }
             
         }
         int iSTART = i + needleValue.length()+1;
         int iEND = haystack.indexOf(" ", iSTART);
         return haystack.substring(iSTART, iEND);   
         
       }catch(Exception e){
           e.printStackTrace();
           System.exit(-21);
           return null;
       }  
    }
    
    
     public void processingCalculateFINDPARTNER(){
          Spreadsheet248MazeGame ssmg= new Spreadsheet248MazeGame(directory+"\\turns.txt"); 
          Vector v2 = ssmg.findString("MAZEGAMETURN", "Type");
          for(int i=0;i<v2.size();i++){
               Integer ii = (Integer)v2.elementAt(i);
               String participantID = ssmg.getValue("ParticipantID",ii );
               String type = ssmg.getValue("Type", ii);
               //System.err.println("Type: "+type);
               if(type.equalsIgnoreCase("MAZEGAMETURN")&&ssmg.getValue("Recipients",ii )!=null&!ssmg.getValue("Recipients",ii ).equals("")){
                    
                    String partnernameN = ssmg.getValue("Recipients",ii );
                    System.err.println("PARTNERNAME IS: "+partnernameN+"...."+i+"...."+ssmg.getValue("Text", ii));
                   
                    String partnername = ssmg.getValue("Recipients",ii ).substring(2);
                    
                    
                    if(participantID.equals(this.p1ID)){
                        this.p1PARTNERUSERNAME=partnername;
                    }
                    else if(participantID.equals(this.p2ID)){
                        this.p2PARTNERUSERNAME=partnername;
                    }
                    else if(participantID.equals(this.p3ID)){
                        this.p3PARTNERUSERNAME=partnername;
                    }
                    else if(participantID.equals(this.p4ID)){
                        this.p4PARTNERUSERNAME=partnername;
                    }
                    else{
                         System.exit(-5);
                    }
               }
               
               
               
          }
          if(p1PARTNERUSERNAME.equals(this.p2Username))p1PARTNERID=p2ID;
          else if(p1PARTNERUSERNAME.equals(this.p3Username))p1PARTNERID=p3ID;
          else if(p1PARTNERUSERNAME.equals(this.p4Username))p1PARTNERID=p4ID;
          else{System.exit(-600);}
          
          if(p2PARTNERUSERNAME.equals(this.p1Username))p2PARTNERID=p1ID;
          else if(p2PARTNERUSERNAME.equals(this.p3Username))p2PARTNERID=p3ID;
          else if(p2PARTNERUSERNAME.equals(this.p4Username))p2PARTNERID=p4ID;
          else{System.exit(-601);}
          
          if(p3PARTNERUSERNAME==null && p4PARTNERUSERNAME ==null)return;
          
          if(p3PARTNERUSERNAME.equals(this.p1Username))p3PARTNERID=p1ID;
          else if(p3PARTNERUSERNAME.equals(this.p2Username))p3PARTNERID=p2ID;
          else if(p3PARTNERUSERNAME.equals(this.p4Username))p3PARTNERID=p4ID;
          else{System.exit(-602);}
          
          if(p4PARTNERUSERNAME.equals(this.p1Username))p4PARTNERID=p1ID;
          else if(p4PARTNERUSERNAME.equals(this.p2Username))p4PARTNERID=p2ID;
          else if(p4PARTNERUSERNAME.equals(this.p3Username))p4PARTNERID=p3ID;
          else{System.exit(-602);}
          
          
     }
    
    
    
    public void processingCalculateMazeStartTimes(){
      
         
          Spreadsheet248MazeGame ssmg= new Spreadsheet248MazeGame(directory+"\\turns.txt"); 
          Vector v2 = ssmg.findString("MAZEINFO", "Type");
          for(int i=0;i<v2.size();i++){
              Integer ii = (Integer)v2.elementAt(i);
              String participantID = ssmg.getValue("ParticipantID",ii );
              String mazeinfo = ssmg.getValue("Text",ii );
              String mazeNo = this.getMazeInfo("mazenumber", mazeinfo);
              String mazeSTART = this.getMazeInfo("mazestarttime", mazeinfo);
              String mazeEXITTIME = this.getMazeInfo("mazeexittime", mazeinfo);
              String mazeISCOMPLETED = this.getMazeInfo("mazeiscompleted", mazeinfo);
              String mazetotalnumberofgamemoves = this.getMazeInfo("mazetotalnumberofgamemoves", mazeinfo);
              String participanttotalnumberofgamemoves = this.getMazeInfo("participanttotalnumberofgamemoves", mazeinfo);
              String participanttotalnumberofswitchtraversals= this.getMazeInfo("participanttotalnumberofswitchtraversals", mazeinfo);
              String partnername = this.getMazeInfo("partnername", mazeinfo);
              int mazenoASInt = Integer.parseInt(mazeNo);
              
              System.err.println("-------------------------------------------------------"+mazeEXITTIME);
              
              
              if(mazeNo.equals("15")&&participantID.startsWith("GGGG")){
                  System.err.println(mazeSTART);
                   System.err.println(mazeEXITTIME);
                   System.err.println(mazeEXITTIME);
                 //  System.exit(-345);
              }
              
            // (ADD THIS HERE IF IT CRASHES)
              if(participantID.equals(this.p1ID)){
                   if(this.p1MazeStartTime[mazenoASInt]>0){
                       System.err.println("MULTIPLESTARTTIMEmazenumber(P1ID):"+mazenoASInt);
                      // System.exit(-234324321);
                   }
                   else{
                      this.p1MazeStartTime[mazenoASInt]=Long.parseLong(mazeSTART);
                      this.p1MazeForcedStopTime[mazenoASInt]=Long.parseLong(mazeEXITTIME);
                   }   
              }
              else if(participantID.equals(this.p2ID)){
                   if(this.p2MazeStartTime[mazenoASInt]>0){
                        System.err.println("MULTIPLESTARTTIMEmazenumber(P2ID):"+mazenoASInt);
                       //System.exit(-234324322);
                   }
                   else{
                       this.p2MazeStartTime[mazenoASInt]=Long.parseLong(mazeSTART);
                       this.p2MazeForcedStopTime[mazenoASInt]=Long.parseLong(mazeEXITTIME);
                   }    
              }
              else if(participantID.equals(this.p3ID)){
                   if(this.p3MazeStartTime[mazenoASInt]>0){
                        System.err.println("MULTIPLESTARTTIMEmazenumber(P3ID):"+mazenoASInt);
                       //System.exit(-234324323);
                   }
                   else{
                       this.p3MazeStartTime[mazenoASInt]=Long.parseLong(mazeSTART);
                       this.p3MazeForcedStopTime[mazenoASInt]=Long.parseLong(mazeEXITTIME);
                   }   
              }
              else if(participantID.equals(this.p4ID)){
                   if(this.p4MazeStartTime[mazenoASInt]>0){
                       System.err.println("MULTIPLESTARTTIMEmazenumber(P4ID):"+mazenoASInt);
                       //System.exit(-234324324);
                   }
                   else{
                       this.p4MazeStartTime[mazenoASInt]=Long.parseLong(mazeSTART);
                       this.p4MazeForcedStopTime[mazenoASInt]=Long.parseLong(mazeEXITTIME);
                   }    
              } 
            
              
              
              
              
              
              
              
              
              
              
              
              
              
              
            
              else{
                  System.exit(-234324);
              }
          }
        
    }   
       
       
       
       
    
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       MessageCursorUpdate p1CurrentPosMCU;
       MessageCursorUpdate p2CurrentPosMCU;
       MessageCursorUpdate p3CurrentPosMCU;
       MessageCursorUpdate p4CurrentPosMCU;
       MessageCursorUpdate p5CurrentPosMCU;
       MessageCursorUpdate p6CurrentPosMCU;
       MessageCursorUpdate p7CurrentPosMCU;
       MessageCursorUpdate p8CurrentPosMCU;
       
       
       
       
       
               
       public void saveNewParticipantLocation(String participantID, MessageCursorUpdate mcu){
            if(participantID.equals(this.p1ID))p1CurrentPosMCU = mcu;
            else if(participantID.equals(this.p2ID))p2CurrentPosMCU = mcu;
            else if(participantID.equals(this.p3ID))p3CurrentPosMCU = mcu;
            else if(participantID.equals(this.p4ID))p4CurrentPosMCU = mcu;
            else if(participantID.equals(this.p5ID))p5CurrentPosMCU = mcu;
            else if(participantID.equals(this.p6ID))p6CurrentPosMCU = mcu;
            else if(participantID.equals(this.p7ID))p7CurrentPosMCU = mcu;
            else if(participantID.equals(this.p8ID))p8CurrentPosMCU = mcu;
            else{
                System.exit(-545454);
            }
       }
       
       public MessageCursorUpdate getLocation(String participantID){
           if(participantID.equals(this.p1ID)) return p1CurrentPosMCU;
            else if(participantID.equals(this.p2ID)) return p2CurrentPosMCU;
            else if(participantID.equals(this.p3ID)) return p3CurrentPosMCU;
            else if(participantID.equals(this.p4ID)) return p4CurrentPosMCU;
            else if(participantID.equals(this.p5ID)) return p5CurrentPosMCU;
            else if(participantID.equals(this.p6ID)) return p6CurrentPosMCU;
            else if(participantID.equals(this.p7ID)) return p7CurrentPosMCU;
            else if(participantID.equals(this.p8ID)) return p8CurrentPosMCU;
            else{
                System.exit(-545454);
            }
           return null;
       }
       
       
       //are there any moves in the same maze post being on maze? 
       //do they go onto maze if they are already on maze
       
       
       
   public void processingCalculateFinishTime(){
           
           for(int i=0;i<this.mazegamemessages.size();i++){
             Object o = mazegamemessages.elementAt(i);
             //System.out.println(o.getClass().getName());
             String nameOfClass = o.getClass().getName();
             //if(i%1000==0)System.out.println(""+nameOfClass);
             if(nameOfClass.equalsIgnoreCase("diet.task.mazegame.message.MessageCursorUpdate")){            
                  MessageCursorUpdate mcu = (MessageCursorUpdate)o;
                  String senderID = mcu.getEmail();
                  String senderUsernName = mcu.getUsername();
                  Maze m = this.getMazeForParticipant(senderID, mcu.getMazeNo());
                  
                  this.saveNewParticipantLocation(senderID, mcu);
                  
                  
                  if(mcu.newPos.width==m.finish.x && mcu.newPos.height==m.finish.y){
                      //System.out.println(senderID+" is on goal");
                      String partnerIID = this.getPartnerID(senderID, mcu.getMazeNo());
                      MessageCursorUpdate mcuPartner = this.getLocation(partnerIID);                   
                      Maze partnerMaze = this.getMazeForParticipant(partnerIID, mcuPartner.getMazeNo());
                      long bothfinishTime = mcu.getTimeOnServerOfReceipt().getTime();
                      if(m==partnerMaze){System.exit(-512342);}
                      if(mcuPartner.newPos.width==partnerMaze.finish.x && mcuPartner.newPos.height==partnerMaze.finish.y && mcu.getMazeNo()==mcuPartner.getMazeNo()){
                          // System.out.println("GOAL DETECTED. MAZE: "+mcu.getMazeNo()+"..."+senderID+"..."+partnerIID);
                          int mazeNo = mcu.getMazeNo();
                          if(senderID.equals(this.p1ID) )    {
                              this.p1MazeBothFinishTime[mazeNo]= bothfinishTime;
                          }
                          else if(senderID.equals(this.p2ID) ) {
                              this.p2MazeBothFinishTime[mazeNo]= bothfinishTime;
                               
                          }
                          else if(senderID.equals(this.p3ID)){
                              this.p3MazeBothFinishTime[mazeNo]= bothfinishTime;
                               
                          }
                          else if(senderID.equals(this.p4ID)) {
                              this.p4MazeBothFinishTime[mazeNo]= bothfinishTime;
                               
                          }
                          else if(senderID.equals(this.p5ID)) {
                              this.p5MazeBothFinishTime[mazeNo]= bothfinishTime;
                               
                          }
                          else if(senderID.equals(this.p6ID)) {
                              this.p6MazeBothFinishTime[mazeNo]= bothfinishTime;
                               
                          }
                          else if(senderID.equals(this.p7ID)){
                              this.p7MazeBothFinishTime[mazeNo]= bothfinishTime;
                               
                          }
                          else if(senderID.equals(this.p8ID)){
                              this.p8MazeBothFinishTime[mazeNo]= bothfinishTime;
                               
                          }
                          else{
                               System.exit(-545454);
                          } 
                          if(partnerIID.equals(this.p1ID)){
                              //if(mazeNo==14)System.exit(-4);
                              if(p1MazeBothFinishTime[mazeNo]>0)System.exit(-23441);
                              this.p1MazeBothFinishTime[mazeNo]= bothfinishTime;
                          }
                          else if(partnerIID.equals(this.p2ID)){
                              if(p2MazeBothFinishTime[mazeNo]>0)System.exit(-23442);
                              this.p2MazeBothFinishTime[mazeNo]= bothfinishTime;
                          }
                          else if(partnerIID.equals(this.p3ID)){
                              if(p3MazeBothFinishTime[mazeNo]>0){
                                  System.err.println("The new bothfinishtime is"+bothfinishTime);
                                  System.err.println("The old bothfinishtime is"+p3MazeBothFinishTime[mazeNo]);
                                  System.err.println("THE DIFFERENCE IS "+(bothfinishTime-p3MazeBothFinishTime[mazeNo]));
                                  System.err.println("FINISHED maze "+mazeNo+  " AT"+i+"---"+mazegamemessages.size());
                                  //System.exit(-23443);
                              }
                              else{
                                   this.p3MazeBothFinishTime[mazeNo]= bothfinishTime;
                              }     
                          }
                          else if(partnerIID.equals(this.p4ID)){
                              if(p4MazeBothFinishTime[mazeNo]>0)System.exit(-23444);
                              this.p4MazeBothFinishTime[mazeNo]= bothfinishTime;
                          }
                          else{
                               System.exit(-545454);
                          } 
                          
                          
                      }
                      
                      
                      
                  }
                  
                  //System.out.println(""+i);
             }
            } 
           
       }
   
   public void processingAddStartTimeForMaze15(){
         if(p1MazeStartTime[15]<0)p1MazeStartTime[15]=this.p1MazeForcedStopTime[14];
         if(p2MazeStartTime[15]<0)p2MazeStartTime[15]=this.p2MazeForcedStopTime[14];
         if(p3MazeStartTime[15]<0)p3MazeStartTime[15]=this.p3MazeForcedStopTime[14];
         if(p4MazeStartTime[15]<0)p4MazeStartTime[15]=this.p4MazeForcedStopTime[14];
         if(p5MazeStartTime[15]<0)p5MazeStartTime[15]=this.p5MazeForcedStopTime[14];
         if(p6MazeStartTime[15]<0)p6MazeStartTime[15]=this.p6MazeForcedStopTime[14];
         if(p7MazeStartTime[15]<0)p7MazeStartTime[15]=this.p7MazeForcedStopTime[14];
         if(p8MazeStartTime[15]<0)p8MazeStartTime[15]=this.p8MazeForcedStopTime[14];
         
   }
   
   
   
   
   
    public boolean isTimeStampInProperMazeGameDialogue(long time,String participantID){
         long[] startTimes=null;
         long[] bothFinishTimes=null;
        
            if(participantID.equals(this.p1ID)){
                startTimes = this.p1MazeStartTime;
                bothFinishTimes = this.p1MazeBothFinishTime;
            }
            else if(participantID.equals(this.p2ID)){
                startTimes = this.p2MazeStartTime;
                bothFinishTimes = this.p2MazeBothFinishTime;
            }
            else if(participantID.equals(this.p3ID)){
                startTimes = this.p3MazeStartTime;
                bothFinishTimes = this.p3MazeBothFinishTime;
            }
            else if(participantID.equals(this.p4ID)){
                 startTimes = this.p4MazeStartTime;
                 bothFinishTimes = this.p4MazeBothFinishTime;
            }
            else if(participantID.equals(this.p5ID)){
                 startTimes = this.p5MazeStartTime;
                 bothFinishTimes = this.p5MazeBothFinishTime;
            }
            else if(participantID.equals(this.p6ID)){
                 startTimes = this.p6MazeStartTime;
                 bothFinishTimes = this.p6MazeBothFinishTime;
            }
            else if(participantID.equals(this.p7ID)){
                  startTimes = this.p7MazeStartTime;
                 bothFinishTimes = this.p7MazeBothFinishTime;
            }
            else if(participantID.equals(this.p8ID)){
                 startTimes = this.p8MazeStartTime;
                 bothFinishTimes = this.p8MazeBothFinishTime;
            }
            else{
                System.exit(-545454);
            }
         
           
            
             int mazenumber =startTimes.length-1;
            //System.err.println("STARTTIMEMZENUMBER:"+mazenumber);
            for(int i=0;i<startTimes.length;i++){
                 if(startTimes[i]>time||startTimes[i]==-1){
                     mazenumber = i-1;
                     //System.err.println("BREAKINGAT: "+i);
                     if(mazenumber==-1){
                          //this is when they type before sending
                         System.err.println("THERE ARE SOME PARTICIPANTS WHO TYPED BEFORE THE START OF MAZE 0");
                         return true;
                     }
                     break;
                 }
                
            }
            //  System.err.println("STARTTIMETIME:"+time);
            //  System.err.println("STARTTIMEZERO:"+startTimes[0]);
            //  System.err.println("STARTTIMEDIFFERENCE:"+(startTimes[0]-time));
            
            // System.err.println("STARTTIMEMZENUMBER:"+mazenumber);
            long mazeStartTime = startTimes[mazenumber];
            long mazeFinishTime = bothFinishTimes[mazenumber];
            if(mazeStartTime>time){
                System.exit(-023424);
                return false;
            }
            else if(mazeFinishTime<0)return true;
            else if(time<=mazeFinishTime) return true;
            else return false;
       
    }
   
    
    public int getMazeNo(long time,String participantID){
         long[] startTimes=null;
         long[] bothFinishTimes=null;
        
            if(participantID.equals(this.p1ID)){
                startTimes = this.p1MazeStartTime;
                bothFinishTimes = this.p1MazeBothFinishTime;
            }
            else if(participantID.equals(this.p2ID)){
                startTimes = this.p2MazeStartTime;
                bothFinishTimes = this.p2MazeBothFinishTime;
            }
            else if(participantID.equals(this.p3ID)){
                startTimes = this.p3MazeStartTime;
                bothFinishTimes = this.p3MazeBothFinishTime;
            }
            else if(participantID.equals(this.p4ID)){
                 startTimes = this.p4MazeStartTime;
                 bothFinishTimes = this.p4MazeBothFinishTime;
            }
            else if(participantID.equals(this.p5ID)){
                 startTimes = this.p5MazeStartTime;
                 bothFinishTimes = this.p5MazeBothFinishTime;
            }
            else if(participantID.equals(this.p6ID)){
                 startTimes = this.p6MazeStartTime;
                 bothFinishTimes = this.p6MazeBothFinishTime;
            }
            else if(participantID.equals(this.p7ID)){
                  startTimes = this.p7MazeStartTime;
                 bothFinishTimes = this.p7MazeBothFinishTime;
            }
            else if(participantID.equals(this.p8ID)){
                 startTimes = this.p8MazeStartTime;
                 bothFinishTimes = this.p8MazeBothFinishTime;
            }
            else{
                System.exit(-545454);
            }
       
            
        
         int mazenumber =startTimes.length-1;
            for(int i=0;i<startTimes.length;i++){
                 if(startTimes[i]>time||startTimes[i]==-1){
                     mazenumber = i-1;
                     break;
                 }
                
            }
            if(mazenumber<0)return 0;
            return mazenumber;
           
        
        
       
    }
    
    
    
    
    public void processingExcludingPostBothOnGoal(){
        for(int i=0;i<this.mazegamemessages.size();i++){
             Object o = mazegamemessages.elementAt(i);
             //System.out.println(o.getClass().getName());
            // String nameOfClass = o.getClass().getName();
            // if(i%1000==0)System.out.println(""+nameOfClass);
             Message m = (Message)o;
             String senderID = m.getEmail();
             String senderUserName = m.getUsername();
             long timestampOfReceipt = m.getTimeOnServerOfReceipt().getTime();
             if(!this.isTimeStampInProperMazeGameDialogue(timestampOfReceipt, senderID)){
                  if(o instanceof MessageChatTextFromClient){
                      MessageChatTextFromClient mcu = (MessageChatTextFromClient)m;
                      System.out.println(i+":  "+mcu.getText());     
                      
                      long mazeStarttime = this.p5MazeStartTime[0];
                      long mazeFinishtime = this.p5MazeBothFinishTime[0];
                    //  System.out.println("time elapsed since start of maze: "+(timestampOfReceipt-mazeStarttime));
                     // System.out.println("time till end of maze:" +(mazeFinishtime-timestampOfReceipt));
                          
                     //  System.exit(-324);
                        super.saveAsExcludedForTypingAfterBothOnGoal(mcu, senderID, senderUserName, directory, this.getMazeNo(timestampOfReceipt, senderID));
                      
                  } 
             }
             else{
                  if(o instanceof MessageChatTextFromClient){
                      MessageChatTextFromClient mcu = (MessageChatTextFromClient)m;
                      int mazeNumber = this.getMazeNo(timestampOfReceipt, senderID);
                         if(senderID.equals(this.p1ID))       this.p1ChatTextTurns[mazeNumber].add(mcu);
                          else if(senderID.equals(this.p2ID)) this.p2ChatTextTurns[mazeNumber].add(mcu);
                          else if(senderID.equals(this.p3ID)) this.p3ChatTextTurns[mazeNumber].add(mcu);
                          else if(senderID.equals(this.p4ID)) this.p4ChatTextTurns[mazeNumber].add(mcu);
                          else if(senderID.equals(this.p5ID)) this.p5ChatTextTurns[mazeNumber].add(mcu);
                          else if(senderID.equals(this.p6ID)) this.p6ChatTextTurns[mazeNumber].add(mcu);
                          else if(senderID.equals(this.p7ID)) this.p7ChatTextTurns[mazeNumber].add(mcu);
                          else if(senderID.equals(this.p8ID)) this.p8ChatTextTurns[mazeNumber].add(mcu);
                          else{
                               System.exit(-545454);
                          } 
                       
                  } 
                  if(o instanceof MessageCursorUpdate){
                      MessageCursorUpdate mcu = (MessageCursorUpdate)m;
                      int mazeNumber = this.getMazeNo(timestampOfReceipt, senderID);
                         if(senderID.equals(this.p1ID))       this.p1CursorUpdates[mazeNumber].add(mcu);
                          else if(senderID.equals(this.p2ID)) this.p2CursorUpdates[mazeNumber].add(mcu);
                          else if(senderID.equals(this.p3ID)) this.p3CursorUpdates[mazeNumber].add(mcu);
                          else if(senderID.equals(this.p4ID)) this.p4CursorUpdates[mazeNumber].add(mcu);
                          else if(senderID.equals(this.p5ID)) this.p5CursorUpdates[mazeNumber].add(mcu);
                          else if(senderID.equals(this.p6ID)) this.p6CursorUpdates[mazeNumber].add(mcu);
                          else if(senderID.equals(this.p7ID)) this.p7CursorUpdates[mazeNumber].add(mcu);
                          else if(senderID.equals(this.p8ID)) this.p8CursorUpdates[mazeNumber].add(mcu);
                          else{
                               System.exit(-545454);
                          } 
                       
                  } 
             }
             
        }    
    }
    
    
   public String getText(Vector v){
       String text ="";
       for(int i=0;i<v.size();i++){
            MessageChatTextFromClient mctfc = (MessageChatTextFromClient)v.elementAt(i);
            text = text+" "+mctfc.getText();
       }
       return text;
   } 
    
   public int countTotalWords(Vector v){
       String text ="";
       for(int i=0;i<v.size();i++){
            MessageChatTextFromClient mctfc = (MessageChatTextFromClient)v.elementAt(i);
            text = text+" "+mctfc.getText();
       }
       Vector allWords = StringSimilarityMeasure.splitIntoWords(text);
       return allWords.size();
   }
   
   public int countTotalChars(Vector v){
       String text ="";
       for(int i=0;i<v.size();i++){
            MessageChatTextFromClient mctfc = (MessageChatTextFromClient)v.elementAt(i);
            text = text+" "+mctfc.getText();
       }
       
       return text.length();
   }
    
   public int countTotalUniqueWords(Vector v){
       String text ="";
       for(int i=0;i<v.size();i++){
            MessageChatTextFromClient mctfc = (MessageChatTextFromClient)v.elementAt(i);
            text = text+" "+mctfc.getText();
       }
       Vector allWords = StringSimilarityMeasure.splitIntoWords(text);
       Vector uniqueWords = StringSimilarityMeasure.removeDuplicates(allWords);
       return uniqueWords.size();
   } 
   
   
   
   
   
    public void processingText(){
        for(int i=0;i<this.or14or16;i++){
           this.p1allWords[i] = this.getText(this.p1ChatTextTurns[i]);
           this.p1WordCount[i] = this.countTotalWords(this.p1ChatTextTurns[i]);
           this.p1UniqueWordCount[i] = this.countTotalUniqueWords(this.p1ChatTextTurns[i]);
           this.p1Chars[i] = this.countTotalChars(this.p1ChatTextTurns[i]);
           
           this.p2allWords[i] = this.getText(this.p2ChatTextTurns[i]);
           this.p2WordCount[i] = this.countTotalWords(this.p2ChatTextTurns[i]);
           this.p2UniqueWordCount[i] = this.countTotalUniqueWords(this.p2ChatTextTurns[i]);
           this.p2Chars[i] = this.countTotalChars(this.p2ChatTextTurns[i]);
           
           this.p3allWords[i] = this.getText(this.p3ChatTextTurns[i]);
           this.p3WordCount[i] = this.countTotalWords(this.p3ChatTextTurns[i]);
           this.p3UniqueWordCount[i] = this.countTotalUniqueWords(this.p3ChatTextTurns[i]);
           this.p3Chars[i] = this.countTotalChars(this.p3ChatTextTurns[i]);
           
           this.p4allWords[i] = this.getText(this.p4ChatTextTurns[i]);
           this.p4WordCount[i] = this.countTotalWords(this.p4ChatTextTurns[i]);
           this.p4UniqueWordCount[i] = this.countTotalUniqueWords(this.p4ChatTextTurns[i]);
           this.p4Chars[i] = this.countTotalChars(this.p4ChatTextTurns[i]);
           
           this.p5allWords[i] = this.getText(this.p5ChatTextTurns[i]);
           this.p5WordCount[i] = this.countTotalWords(this.p5ChatTextTurns[i]);
           this.p5UniqueWordCount[i] = this.countTotalUniqueWords(this.p5ChatTextTurns[i]);
           this.p5Chars[i] = this.countTotalChars(this.p5ChatTextTurns[i]);
           
           this.p6allWords[i] = this.getText(this.p6ChatTextTurns[i]);
           this.p6WordCount[i] = this.countTotalWords(this.p6ChatTextTurns[i]);
           this.p6UniqueWordCount[i] = this.countTotalUniqueWords(this.p6ChatTextTurns[i]);
           this.p6Chars[i] = this.countTotalChars(this.p6ChatTextTurns[i]);
           
           this.p7allWords[i] = this.getText(this.p7ChatTextTurns[i]);
           this.p7WordCount[i] = this.countTotalWords(this.p7ChatTextTurns[i]);
           this.p7UniqueWordCount[i] = this.countTotalUniqueWords(this.p7ChatTextTurns[i]);
           this.p7Chars[i] = this.countTotalChars(this.p7ChatTextTurns[i]);
           
           this.p8allWords[i] = this.getText(this.p8ChatTextTurns[i]);
           this.p8WordCount[i] = this.countTotalWords(this.p8ChatTextTurns[i]);
           this.p8UniqueWordCount[i] = this.countTotalUniqueWords(this.p8ChatTextTurns[i]);
           this.p8Chars[i] = this.countTotalChars(this.p8ChatTextTurns[i]);
            
        }
    }
    
       
    public void displayInfo(){
         System.err.println("--------------------------------------");
         for(int i=0;i<16;i++){
              //System.err.println("P1ID"+this.p1ID);
              //System.out.print("MAZESTARTTIME "+i+":  "+this.p1MazeStartTime[i]); 
              //System.out.print("   MAZEBOTHONGOAL: "+this.p1MazeBothFinishTime[i]);
              
              //System.out.println("PARTNER:  "+this.getPartnerID(this.p1ID, i));
              
              
             // long mazeCompletionTime = this.p6MazeBothFinishTime[i]-this.p6MazeStartTime[i];
              //String text = Conversion.convertMillisecondsIntoText(mazeCompletionTime)+"                      ("+mazeCompletionTime;
              
              String text = "MAZESTARTTIME:"+this.p4MazeStartTime[i]+ "   MAZEBOTHONGOAL: "+this.p4MazeBothFinishTime[i]+ "NUMBER OF CHATTEXT TURNNS "+this.p4ChatTextTurns[i].size()+ " NUMBER OF CURSOR UPDATES "+this.p4CursorUpdates[i].size() + " UNIQUE MAZE ID: "+this.p4Mazes[i] + " NUMBER OF WORDS: "+this.p4WordCount[i]+ " NUMBER OF UNIQUE WORDS: "+this.p4UniqueWordCount[i]+ " NUMBER OF CHARS: "+this.p4Chars[i];
              System.out.println("MAZE: "+i+":  "+text);
              
              
              //System.out.println("MAZE "+i+" "+(this.p1MazeBothFinishTime[i]-this.p1MazeStartTime[i]));
              
              
         }
    }   
    
    
    
    
    public String getInfo(String participantID, int mazeNo){
       if(participantID.equalsIgnoreCase(this.p1ID))
        return "DYADS"                      +"|"+
               mazeNo                        +"|"+
               p1ID                          +"|"+
               p1Username                    +"|"+
               p1Mazes[mazeNo]               +"|"+
               p1MazeStartTime[mazeNo]       +"|"+
               p1MazeBothFinishTime[mazeNo]  +"|"+        
               p1ChatTextTurns  [mazeNo].size()     +"|"+
               p1CursorUpdates  [mazeNo].size()     +"|"+ 
               p1WordCount [mazeNo]          +"|"+
               p1UniqueWordCount[mazeNo]     +"|"+
               p1Chars[mazeNo]               +"|"+
               this.getText(this.p1ChatTextTurns[mazeNo])+"|";
       else if(participantID.equalsIgnoreCase(this.p2ID))
        return "DYADS"                      +"|"+
               mazeNo                        +"|"+
               p2ID                          +"|"+
               p2Username                    +"|"+
               p2Mazes[mazeNo]               +"|"+
               p2MazeStartTime[mazeNo]       +"|"+
               p2MazeBothFinishTime[mazeNo]  +"|"+
               p2ChatTextTurns  [mazeNo].size()     +"|"+
               p2CursorUpdates  [mazeNo].size()     +"|"+ 
               p2WordCount [mazeNo]          +"|"+
               p2UniqueWordCount[mazeNo]     +"|"+
               p2Chars[mazeNo]               +"|"+
               this.getText(this.p2ChatTextTurns[mazeNo])+"|";
       else if(participantID.equalsIgnoreCase(this.p3ID))
        return "DYADS"                      +"|"+
               mazeNo                        +"|"+
               p3ID                          +"|"+
               p3Username                    +"|"+
               p3Mazes[mazeNo]               +"|"+
               p3MazeStartTime[mazeNo]       +"|"+
               p3MazeBothFinishTime[mazeNo]  +"|"+
               p3ChatTextTurns  [mazeNo].size()     +"|"+
               p3CursorUpdates  [mazeNo].size()     +"|"+ 
               p3WordCount [mazeNo]          +"|"+
               p3UniqueWordCount[mazeNo]     +"|"+
               p3Chars[mazeNo]               +"|"+
               this.getText(this.p3ChatTextTurns[mazeNo])+"|";
       else if(participantID.equalsIgnoreCase(this.p4ID))
        return "DYADS"                      +"|"+
               mazeNo                        +"|"+
               p4ID                          +"|"+
               p4Username                    +"|"+
               p4Mazes[mazeNo]               +"|"+
               p4MazeStartTime[mazeNo]       +"|"+
               p4MazeBothFinishTime[mazeNo]  +"|"+
               p4ChatTextTurns  [mazeNo].size()     +"|"+
               p4CursorUpdates  [mazeNo].size()     +"|"+ 
               p4WordCount [mazeNo]          +"|"+
               p4UniqueWordCount[mazeNo]     +"|"+
               p4Chars[mazeNo]               +"|"+
               this.getText(this.p4ChatTextTurns[mazeNo])+"|";
       else if(participantID.equalsIgnoreCase(this.p5ID))
        return "DYADS"                      +"|"+
               mazeNo                        +"|"+
               p5ID                          +"|"+
               p5Username                    +"|"+
               p5Mazes[mazeNo]               +"|"+
               p5MazeStartTime[mazeNo]       +"|"+
               p5MazeBothFinishTime[mazeNo]  +"|"+
               p5ChatTextTurns  [mazeNo].size()     +"|"+
               p5CursorUpdates  [mazeNo].size()     +"|"+ 
               p5WordCount [mazeNo]          +"|"+
               p5UniqueWordCount[mazeNo]     +"|"+
               p5Chars[mazeNo]               +"|"+
               this.getText(this.p5ChatTextTurns[mazeNo])+"|";
       else if(participantID.equalsIgnoreCase(this.p6ID))
        return "DYADS4"                      +"|"+
               mazeNo                        +"|"+
               p6ID                          +"|"+
               p6Username                    +"|"+
               p6Mazes[mazeNo]               +"|"+
               p6MazeStartTime[mazeNo]       +"|"+
               p6MazeBothFinishTime[mazeNo]  +"|"+
               p6ChatTextTurns  [mazeNo].size()     +"|"+
               p6CursorUpdates  [mazeNo].size()     +"|"+ 
               p6WordCount [mazeNo]          +"|"+
               p6UniqueWordCount[mazeNo]     +"|"+
               p6Chars[mazeNo]               +"|"+
               this.getText(this.p6ChatTextTurns[mazeNo])+"|";
       else if(participantID.equalsIgnoreCase(this.p7ID))
        return "DYADS"                             +"|"+
               mazeNo                               +"|"+
               p7ID                                 +"|"+
               p7Username                           +"|"+
               p7Mazes[mazeNo]                      +"|"+
               p7MazeStartTime[mazeNo]              +"|"+
               p7MazeBothFinishTime[mazeNo]         +"|"+
               p7ChatTextTurns  [mazeNo].size()     +"|"+
               p7CursorUpdates  [mazeNo].size()     +"|"+ 
               p7WordCount [mazeNo]                 +"|"+
               p7UniqueWordCount[mazeNo]            +"|"+
               p7Chars[mazeNo]                       +"|"+
               this.getText(this.p7ChatTextTurns[mazeNo])+"|";
       else if(participantID.equalsIgnoreCase(this.p8ID))
        return "DYADS"                      +"|"+
               mazeNo                        +"|"+
               p8ID                          +"|"+
               p8Username                    +"|"+
               p8Mazes[mazeNo]               +"|"+
               p8MazeStartTime[mazeNo]       +"|"+
               p8MazeBothFinishTime[mazeNo]  +"|"+
               p8ChatTextTurns  [mazeNo].size()     +"|"+
               p8CursorUpdates  [mazeNo].size()     +"|"+ 
               p8WordCount [mazeNo]          +"|"+
               p8UniqueWordCount[mazeNo]     +"|"+
               p8Chars[mazeNo]               +"|"+
               this.getText(this.p8ChatTextTurns[mazeNo])+"|";
       
       System.exit(-234234);
       return null;
    }
    
   
    
    public String getInfo(String pA,String pB,int mazenumber){
         String firstHalf ="";
         String secondHalf="";
         return this.getInfo(pA, mazenumber)+ this.getInfo(pB, mazenumber)+directory+"\n";

    }
    
    
    
   public String getText(){
      // if(p2ID==null)System.exit(-23423);
       
       
       String value =  
       getInfo  (p1ID, p2ID, 0) + 
       getInfo  (p3ID, p4ID, 0) +
       
       getInfo  (p1ID, p2ID, 1) + 
       getInfo  (p3ID, p4ID, 1) +
               
       getInfo  (p1ID, p2ID, 2) + 
       getInfo  (p3ID, p4ID, 2) +
               
       getInfo  (p1ID, p2ID, 3) + 
       getInfo  (p3ID, p4ID, 3) +
               
       getInfo  (p1ID, p2ID, 4) + 
       getInfo  (p3ID, p4ID, 4) +
               
       getInfo  (p1ID, p2ID, 5) + 
       getInfo  (p3ID, p4ID, 5) +
               
       getInfo  (p1ID, p2ID, 6) + 
       getInfo  (p3ID, p4ID, 6) +
               
       getInfo  (p1ID, p2ID, 7) + 
       getInfo  (p3ID, p4ID, 7) +
               
       getInfo  (p1ID, p2ID, 8) + 
       getInfo  (p3ID, p4ID, 8) +
               
       getInfo  (p1ID, p2ID, 9) + 
       getInfo  (p3ID, p4ID, 9) +
               
       getInfo  (p1ID, p2ID, 10) + 
       getInfo  (p3ID, p4ID, 10) +
               
       getInfo  (p1ID, p2ID, 11) + 
       getInfo  (p3ID, p4ID, 11) +
               
       getInfo  (p1ID, p2ID, 12) + 
       getInfo  (p3ID, p4ID, 12) +
               
       getInfo  (p1ID, p2ID, 13) + 
       getInfo  (p3ID, p4ID, 13) +  
       
       getInfo  (p1ID, p2ID, 14) + 
       getInfo  (p3ID, p4ID, 14) +  
       
       getInfo  (p1ID, p2ID, 15) + 
       getInfo  (p3ID, p4ID, 15);  
               
               
               
               
               
       
       
         
     
         
     
               
       
       
      return value;
       
   }
    
   
    public void saveDEBUGInfo(){
       
         String value = this.getText();
         System.out.println(value);
       
        
         try{
            File f =new File(directory+"\\"+"debuginfoCUMULATIVE"+".csv");
            FileWriter fw = new FileWriter(f);
            fw.append(getText());
            fw.flush();
            fw.close();
         }catch (Exception e){
             e.printStackTrace();
         }
         String s = this.getCSVChatText();
         
         try{
            File f =new File(directory+"\\"+"debugfulltextCHAT"+".csv");
            FileWriter fw = new FileWriter(f);
            fw.append(this.getCSVChatText());
            fw.flush();
            fw.close();
         }catch (Exception e){
             e.printStackTrace();
         }
         
   }
   
      
   
  public void saveInfo(){
       
         String value = this.getText();
         System.out.println(value);
       
         File f =new File(directory+"\\"+this.filenameOUTPUT+".csv");
         try{
            FileWriter fw = new FileWriter(f);
            fw.append(getText());
            fw.flush();
            fw.close();
         }catch (Exception e){
             e.printStackTrace();
         }
   }
   
    

   
  ///Everything underneath this has to be copied over
  ///All the p3ID, pID, p5ID, p6ID, P7ID, p8ID.
  
  //p1ReceivesIntervention
  //To port to the others, need to undelete the p3ID, p4ID, p5ID, p6ID, p7ID, p8ID
  //Also in doAllProcessing(), add a reference to processFindInterventions()
  
  public Vector[] p1ClarificationRequests = {new Vector(),new Vector(), new Vector(), new Vector(), new Vector(), new Vector(), new Vector(),new Vector(), new Vector(), new Vector(), new Vector(), new Vector(),new Vector(), new Vector(), new Vector(),new Vector()};
  public Vector[] p2ClarificationRequests = {new Vector(),new Vector(), new Vector(), new Vector(), new Vector(), new Vector(), new Vector(),new Vector(), new Vector(), new Vector(), new Vector(), new Vector(),new Vector(), new Vector(), new Vector(),new Vector()};
  public Vector[] p3ClarificationRequests = {new Vector(),new Vector(), new Vector(), new Vector(), new Vector(), new Vector(), new Vector(),new Vector(), new Vector(), new Vector(), new Vector(), new Vector(),new Vector(), new Vector(), new Vector(),new Vector()};
  public Vector[] p4ClarificationRequests = {new Vector(),new Vector(), new Vector(), new Vector(), new Vector(), new Vector(), new Vector(),new Vector(), new Vector(), new Vector(), new Vector(), new Vector(),new Vector(), new Vector(), new Vector(),new Vector()};
  public Vector[] p5ClarificationRequests = {new Vector(),new Vector(), new Vector(), new Vector(), new Vector(), new Vector(), new Vector(),new Vector(), new Vector(), new Vector(), new Vector(), new Vector(),new Vector(), new Vector(), new Vector(),new Vector()};
  public Vector[] p6ClarificationRequests = {new Vector(),new Vector(), new Vector(), new Vector(), new Vector(), new Vector(), new Vector(),new Vector(), new Vector(), new Vector(), new Vector(), new Vector(),new Vector(), new Vector(), new Vector(),new Vector()};
  public Vector[] p7ClarificationRequests = {new Vector(),new Vector(), new Vector(), new Vector(), new Vector(), new Vector(), new Vector(),new Vector(), new Vector(), new Vector(), new Vector(), new Vector(),new Vector(), new Vector(), new Vector(),new Vector()};
  public Vector[] p8ClarificationRequests = {new Vector(),new Vector(), new Vector(), new Vector(), new Vector(), new Vector(), new Vector(),new Vector(), new Vector(), new Vector(), new Vector(), new Vector(),new Vector(), new Vector(), new Vector(),new Vector()};
 
  
  public MessageChatTextFromClient p1ResponseToWHAT, p1ResponseToWHY, p1ResponseToWHERE;
  public MessageChatTextFromClient p2ResponseToWHAT, p2ResponseToWHY, p2ResponseToWHERE;
  public MessageChatTextFromClient p3ResponseToWHAT, p3ResponseToWHY, p3ResponseToWHERE;
  public MessageChatTextFromClient p4ResponseToWHAT, p4ResponseToWHY, p4ResponseToWHERE;
  public MessageChatTextFromClient p5ResponseToWHAT, p5ResponseToWHY, p5ResponseToWHERE;
  public MessageChatTextFromClient p6ResponseToWHAT, p6ResponseToWHY, p6ResponseToWHERE;
  public MessageChatTextFromClient p7ResponseToWHAT, p7ResponseToWHY, p7ResponseToWHERE;
  public MessageChatTextFromClient p8ResponseToWHAT, p8ResponseToWHY, p8ResponseToWHERE;
  
  
  
  
   public String getParticipantID(String username){
      if(username.equalsIgnoreCase(p1Username))return this.p1ID;
      else if(username.equalsIgnoreCase(p2Username))return this.p2ID;
      else if(username.equalsIgnoreCase(p3Username))return this.p3ID;
      else if(username.equalsIgnoreCase(p4Username))return this.p4ID;
      else if(username.equalsIgnoreCase(p5Username))return this.p5ID;
      else if(username.equalsIgnoreCase(p6Username))return this.p6ID;
      else if(username.equalsIgnoreCase(p7Username))return this.p7ID;
      else if(username.equalsIgnoreCase(p8Username))return this.p8ID;
      //else if(participantID.equalsIgnoreCase(p5ID))return this.p5Username;
      //else if(participantID.equalsIgnoreCase(p6ID))return this.p6Username;
      else{
          System.err.println("Can't find "+username+" ---"+directory);
          System.exit(-32411);
      }
      return null;
  }
  
  
  
   
   
   
  public void processingFindInterventions(){
       String intervention1 =  "..what??";
       String intervention2 = "sorry where?";
       String intervention3 = "sry why?";
       //System.exit(-5);
       
       Spreadsheet248MazeGame ssmg= new Spreadsheet248MazeGame(directory+"\\turns.txt");          
       Vector v2WHAT_Unfiltered = ssmg.findString("..what??", "Text");
       Vector v2WHERE_Unfiltered = ssmg.findString("sorry where?","Text");
       Vector v2WHY_Unfiltered = ssmg.findString("sry why?", "Text");
       
       Vector v2WHAT = new Vector();
       Vector v2WHERE = new Vector();
       Vector v2WHY = new Vector();
       
       
       
       for(int i=0;i<v2WHAT_Unfiltered.size();i++){
          
           int index = (Integer)v2WHAT_Unfiltered.elementAt(i);
           String value = ssmg.getValue("Sender", index);
           if(value.equalsIgnoreCase("server")){
               v2WHAT.addElement(index);
           }
           else{
              System.err.println("There is a clarification with same format..produced by participant, not by server: "+value);
              System.exit(-523441);
            }
          
       }
       for(int i=0;i<v2WHERE_Unfiltered.size();i++){
           int index = (Integer)v2WHERE_Unfiltered.elementAt(i);
           String value = ssmg.getValue("Sender", index);
           if(value.equalsIgnoreCase("server")){
               v2WHERE.addElement(index);
           }
           else{
              System.err.println("There is a clarification with same format..produced by participant, not by server: "+value);
              System.exit(-523442);
            }
          
       }
       for(int i=0;i<v2WHY_Unfiltered.size();i++){
           int index = (Integer)v2WHY_Unfiltered.elementAt(i);
           String value = ssmg.getValue("Sender", index);
           if(value.equalsIgnoreCase("server")){
               v2WHY.addElement(index);
           }
           else{
              System.err.println("There is a clarification with same format..produced by participant, not by server: "+value);
              System.exit(-523443);
            }
          
       }
       //MessageChatTextFromClient mct;// = new MessageChatTextFromClient("server","server","text",new Date().getTime(),false,new Vector());
       
       
       for(int i=0;i<v2WHAT.size();i++){
           
           Integer index = (Integer)v2WHAT.elementAt(i);
           
           String senderID = ssmg.getValue("ParticipantID", index);
           String senderUsername = ssmg.getValue("Sender", index);
           String apparentUsername = ssmg.getValue("AppOrig.",index);
           System.err.println("DBG1");
           String apparentParticipantID = this.getParticipantID(apparentUsername);
                   
           String recipientsWithComma = ssmg.getValue("Recipients", index)        ;
           String recipientUsername = recipientsWithComma.replace(", ", "");
            System.err.println("DBG2");
           String recipientID = this.getParticipantID(recipientUsername);
           
           String timeABSOLUTEONSERVER_AS_STRING = ssmg.getValue("ClientTime", index);
           long timeABSOLUTEONSERVER = Long.parseLong(timeABSOLUTEONSERVER_AS_STRING);
           
           String timeRELATIVETO0START_AS_STRING = ssmg.getValue("ClientTime", index);
           long timeRELATIVETO0START = Long.parseLong(timeRELATIVETO0START_AS_STRING);        
           
           String textOfCR = ssmg.getValue("Text", index);
           int mazeNo = this.getMazeNo(timeABSOLUTEONSERVER, recipientID); 
           
        
           System.err.println("Maze number is: "+mazeNo+ "...crtext: "+textOfCR+"....senderID"+senderID);
          
           MessageChatTextFromClient mctRESPONSE = findNextTurnByParticipantSTARTINGATMAZE15(timeABSOLUTEONSERVER,recipientID);
            Object[] intervention = {senderID,senderUsername,apparentParticipantID,apparentUsername, 
                                   (""+timeABSOLUTEONSERVER), (""+timeRELATIVETO0START),textOfCR, 
                                    recipientID, recipientUsername, (""+mazeNo), mctRESPONSE};
           
           this.saveClarificationForParticipant(intervention, recipientID, mazeNo);
           if(mctRESPONSE!=null){
                    System.err.println("RESPONSE: "+mctRESPONSE.getText());
                    if(recipientID.equalsIgnoreCase(p1ID) ) this.p1ResponseToWHAT=mctRESPONSE;
                    else if(recipientID.equalsIgnoreCase(p2ID) ) this.p2ResponseToWHAT=mctRESPONSE;
                    else if(recipientID.equalsIgnoreCase(p3ID) ) this.p3ResponseToWHAT=mctRESPONSE;
                    else if(recipientID.equalsIgnoreCase(p4ID) ) this.p4ResponseToWHAT=mctRESPONSE;
                    //else if(recipientID.equalsIgnoreCase(p5ID) ) this.p5ResponseToWHAT=mctRESPONSE;
                    //else if(recipientID.equalsIgnoreCase(p6ID) ) this.p6ResponseToWHAT=mctRESPONSE;
                    //else if(recipientID.equalsIgnoreCase(p7ID) ) this.p7ResponseToWHAT=mctRESPONSE;
                    //else if(recipientID.equalsIgnoreCase(p8ID) ) this.p8ResponseToWHAT=mctRESPONSE;
                    else{
                        System.exit(-32141234);
                    }
           }
       }
       
       
       
       for(int i=0;i<v2WHERE.size();i++){
           
           Integer index = (Integer)v2WHERE.elementAt(i);
           
           String senderID = ssmg.getValue("ParticipantID", index);
           String senderUsername = ssmg.getValue("Sender", index);
           String apparentUsername = ssmg.getValue("AppOrig.",index);
            System.err.println("DBG3");
           String apparentParticipantID = this.getParticipantID(apparentUsername);
                   
           String recipientsWithComma = ssmg.getValue("Recipients", index)        ;
           String recipientUsername = recipientsWithComma.replace(", ", "");
            System.err.println("DBG4");
           String recipientID = this.getParticipantID(recipientUsername);
           
           String timeABSOLUTEONSERVER_AS_STRING = ssmg.getValue("ClientTime", index);
           long timeABSOLUTEONSERVER = Long.parseLong(timeABSOLUTEONSERVER_AS_STRING);
           
           String timeRELATIVETO0START_AS_STRING = ssmg.getValue("ClientTime", index);
           long timeRELATIVETO0START = Long.parseLong(timeRELATIVETO0START_AS_STRING);        
           
           String textOfCR = ssmg.getValue("Text", index);
           int mazeNo = this.getMazeNo(timeABSOLUTEONSERVER, recipientID); 
           
        
           System.err.println("Maze number is: "+mazeNo+ "...crtext: "+textOfCR+"....senderID"+senderID);
           MessageChatTextFromClient mctRESPONSE = findNextTurnByParticipantSTARTINGATMAZE15(timeABSOLUTEONSERVER,recipientID);
           Object[] intervention = {senderID,senderUsername,apparentParticipantID,apparentUsername, 
                                   (""+timeABSOLUTEONSERVER), (""+timeRELATIVETO0START),textOfCR, 
                                    recipientID, recipientUsername, (""+mazeNo),mctRESPONSE};
           
           this.saveClarificationForParticipant(intervention, recipientID, mazeNo);
           if(mctRESPONSE!=null){
                    System.err.println("RESPONSE: "+mctRESPONSE.getText());
                    if(recipientID.equalsIgnoreCase(p1ID) ) this.p1ResponseToWHERE=mctRESPONSE;
                    else if(recipientID.equalsIgnoreCase(p2ID) ) this.p2ResponseToWHERE=mctRESPONSE;
                    else if(recipientID.equalsIgnoreCase(p3ID) ) this.p3ResponseToWHERE=mctRESPONSE;
                    else if(recipientID.equalsIgnoreCase(p4ID) ) this.p4ResponseToWHERE=mctRESPONSE;
                    //else if(recipientID.equalsIgnoreCase(p5ID) ) this.p5ResponseToWHAT=mctRESPONSE;
                    //else if(recipientID.equalsIgnoreCase(p6ID) ) this.p6ResponseToWHAT=mctRESPONSE;
                    //else if(recipientID.equalsIgnoreCase(p7ID) ) this.p7ResponseToWHAT=mctRESPONSE;
                    //else if(recipientID.equalsIgnoreCase(p8ID) ) this.p8ResponseToWHAT=mctRESPONSE;
                    else{
                        System.exit(-32141234);
                    }
           }
       }
       for(int i=0;i<v2WHY.size();i++){
           
           Integer index = (Integer)v2WHY.elementAt(i);
           
           String senderID = ssmg.getValue("ParticipantID", index);
           String senderUsername = ssmg.getValue("Sender", index);
           String apparentUsername = ssmg.getValue("AppOrig.",index);
            System.err.println("DBG5");
           String apparentParticipantID = this.getParticipantID(apparentUsername);
                   
           String recipientsWithComma = ssmg.getValue("Recipients", index)        ;
           String recipientUsername = recipientsWithComma.replace(", ", "");
            System.err.println("DBG6");
           String recipientID = this.getParticipantID(recipientUsername);
           
           String timeABSOLUTEONSERVER_AS_STRING = ssmg.getValue("ClientTime", index);
           long timeABSOLUTEONSERVER = Long.parseLong(timeABSOLUTEONSERVER_AS_STRING);
           
           String timeRELATIVETO0START_AS_STRING = ssmg.getValue("ClientTime", index);
           long timeRELATIVETO0START = Long.parseLong(timeRELATIVETO0START_AS_STRING);        
           
           String textOfCR = ssmg.getValue("Text", index);
           int mazeNo = this.getMazeNo(timeABSOLUTEONSERVER, recipientID); 
           
        
           System.err.println("Maze number is: "+mazeNo+ "...crtext: "+textOfCR+"....senderID"+senderID);
           MessageChatTextFromClient mctRESPONSE = findNextTurnByParticipantSTARTINGATMAZE15(timeABSOLUTEONSERVER,recipientID);
           Object[] intervention = {senderID,senderUsername,apparentParticipantID,apparentUsername, 
                                   (""+timeABSOLUTEONSERVER), (""+timeRELATIVETO0START),textOfCR, 
                                    recipientID, recipientUsername, (""+mazeNo),mctRESPONSE};
           
           this.saveClarificationForParticipant(intervention, recipientID, mazeNo);
           if(mctRESPONSE!=null){
                System.err.println("RESPONSE: "+mctRESPONSE.getText());
                if(recipientID.equalsIgnoreCase(p1ID) ) this.p1ResponseToWHY=mctRESPONSE;
                else if(recipientID.equalsIgnoreCase(p2ID) ) this.p2ResponseToWHY=mctRESPONSE;
                else if(recipientID.equalsIgnoreCase(p3ID) ) this.p3ResponseToWHY=mctRESPONSE;
                else if(recipientID.equalsIgnoreCase(p4ID) ) this.p4ResponseToWHY=mctRESPONSE;
                //else if(recipientID.equalsIgnoreCase(p5ID) ) this.p5ResponseToWHAT=mctRESPONSE;
                //else if(recipientID.equalsIgnoreCase(p6ID) ) this.p6ResponseToWHAT=mctRESPONSE;
                //else if(recipientID.equalsIgnoreCase(p7ID) ) this.p7ResponseToWHAT=mctRESPONSE;
                //else if(recipientID.equalsIgnoreCase(p8ID) ) this.p8ResponseToWHAT=mctRESPONSE;
                else{
                    System.exit(-32141234);
                }
           }
       }
       
       
       
       
       
       
      // System.exit(-5);
  }
  
  public MessageChatTextFromClient findNextTurnByParticipantSTARTINGATMAZE15(long minimumTimeThreshold,String participantID){
       boolean foundNextTurn = false;
       Vector maze15 = this.getChatTextForParticipant(participantID, 14);
       int i=0;
       for( i=0;i<maze15.size();i++){
           MessageChatTextFromClient mct = (MessageChatTextFromClient)maze15.elementAt(i);
           if(mct.getTimeOnServerOfReceipt().getTime()>minimumTimeThreshold){
                
                return mct;
           }
       }
       Vector maze16 = this.getChatTextForParticipant(participantID, 15);
       int j=0;
       for( j=0;j<maze16.size();j++){
           MessageChatTextFromClient mct = (MessageChatTextFromClient)maze16.elementAt(j);
           if(mct.getTimeOnServerOfReceipt().getTime()>minimumTimeThreshold){
                return mct;
           }
       }
       System.err.println("DYAD4_14_CANNOTFINDNEXTTURNBYPARTICIPANT: " +participantID +"threshold:"+minimumTimeThreshold+   ".........."+directory +"i"+i+"+"+j);
       
       
       
       //System.err.println("maze10HASSIZE:"+this.getChatTextForParticipant(participantID, 3).size());
       System.err.println("maze0HASSIZE:"+this.getChatTextForParticipant(participantID, 0).size());
       System.err.println("maze1HASSIZE:"+this.getChatTextForParticipant(participantID, 1).size());
       System.err.println("maze2HASSIZE:"+this.getChatTextForParticipant(participantID, 2).size());
       System.err.println("maze3HASSIZE:"+this.getChatTextForParticipant(participantID, 3).size());
       System.err.println("maze4HASSIZE:"+this.getChatTextForParticipant(participantID, 4).size());
       System.err.println("maze5HASSIZE:"+this.getChatTextForParticipant(participantID, 5).size());
       System.err.println("maze6HASSIZE:"+this.getChatTextForParticipant(participantID, 6).size());
       System.err.println("maze7HASSIZE:"+this.getChatTextForParticipant(participantID, 7).size()); 
       System.err.println("maze8HASSIZE:"+this.getChatTextForParticipant(participantID, 8).size());
       System.err.println("maze9HASSIZE:"+this.getChatTextForParticipant(participantID, 9).size());
       System.err.println("maze10HASSIZE:"+this.getChatTextForParticipant(participantID, 10).size());
       System.err.println("maze11HASSIZE:"+this.getChatTextForParticipant(participantID, 11).size());
       System.err.println("maze12HASSIZE:"+this.getChatTextForParticipant(participantID, 12).size());
       System.err.println("maze13HASSIZE:"+this.getChatTextForParticipant(participantID, 13).size()); 
       System.err.println("maze14HASSIZE:"+this.getChatTextForParticipant(participantID, 14).size());
       System.err.println("maze10HASSIZE:"+this.getChatTextForParticipant(participantID, 10).size());
       System.err.println("maze10HASSIZE:"+this.getChatTextForParticipant(participantID, 10).size());
       System.err.println("maze11HASSIZE:"+this.getChatTextForParticipant(participantID, 11).size());
       System.err.println("maze12HASSIZE:"+this.getChatTextForParticipant(participantID, 12).size());
       System.err.println("maze13HASSIZE:"+this.getChatTextForParticipant(participantID, 13).size()); 
       System.err.println("maze14HASSIZE:"+this.getChatTextForParticipant(participantID, 14).size());
       System.err.println("maze15HASSIZE:"+maze15.size());
       System.err.println("maze16HASSIZE:"+maze16.size());
       
       System.err.println(this.p1Username);
       System.err.println("maze0HASSIZE:"+this.getChatTextForParticipant(p1ID, 0).size());
       System.err.println("maze1HASSIZE:"+this.getChatTextForParticipant(p1ID, 1).size());
       System.err.println("maze2HASSIZE:"+this.getChatTextForParticipant(p1ID, 2).size());
       System.err.println("maze3HASSIZE:"+this.getChatTextForParticipant(p1ID, 3).size());
       System.err.println("maze4HASSIZE:"+this.getChatTextForParticipant(p1ID, 4).size());
       System.err.println("maze5HASSIZE:"+this.getChatTextForParticipant(p1ID, 5).size());
       System.err.println("maze6HASSIZE:"+this.getChatTextForParticipant(p1ID, 6).size());
       System.err.println("maze7HASSIZE:"+this.getChatTextForParticipant(p1ID, 7).size()); 
       System.err.println("maze8HASSIZE:"+this.getChatTextForParticipant(p1ID, 8).size());
       System.err.println("maze9HASSIZE:"+this.getChatTextForParticipant(p1ID, 9).size());
       System.err.println("maze10HASSIZE:"+this.getChatTextForParticipant(p1ID, 10).size());
       System.err.println("maze11HASSIZE:"+this.getChatTextForParticipant(p1ID, 11).size());
       System.err.println("maze12HASSIZE:"+this.getChatTextForParticipant(p1ID, 12).size());
       System.err.println("maze13HASSIZE:"+this.getChatTextForParticipant(p1ID, 13).size()); 
       System.err.println("maze14HASSIZE:"+this.getChatTextForParticipant(p1ID, 14).size());
       System.err.println("maze10HASSIZE:"+this.getChatTextForParticipant(p1ID, 10).size());
       System.err.println("maze10HASSIZE:"+this.getChatTextForParticipant(p1ID, 10).size());
       System.err.println("maze11HASSIZE:"+this.getChatTextForParticipant(p1ID, 11).size());
       System.err.println("maze12HASSIZE:"+this.getChatTextForParticipant(p1ID, 12).size());
       System.err.println("maze13HASSIZE:"+this.getChatTextForParticipant(p1ID, 13).size()); 
       System.err.println("maze14HASSIZE:"+this.getChatTextForParticipant(p1ID, 14).size());
       System.err.println("maze14HASSIZE:"+this.getChatTextForParticipant(p1ID, 15).size());
       
       
       
        System.err.println(this.p2Username);
       System.err.println("maze0HASSIZE:"+this.getChatTextForParticipant(p2ID, 0).size());
       System.err.println("maze1HASSIZE:"+this.getChatTextForParticipant(p2ID, 1).size());
       System.err.println("maze2HASSIZE:"+this.getChatTextForParticipant(p2ID, 2).size());
       System.err.println("maze3HASSIZE:"+this.getChatTextForParticipant(p2ID, 3).size());
       System.err.println("maze4HASSIZE:"+this.getChatTextForParticipant(p2ID, 4).size());
       System.err.println("maze5HASSIZE:"+this.getChatTextForParticipant(p2ID, 5).size());
       System.err.println("maze6HASSIZE:"+this.getChatTextForParticipant(p2ID, 6).size());
       System.err.println("maze7HASSIZE:"+this.getChatTextForParticipant(p2ID, 7).size()); 
       System.err.println("maze8HASSIZE:"+this.getChatTextForParticipant(p2ID, 8).size());
       System.err.println("maze9HASSIZE:"+this.getChatTextForParticipant(p2ID, 9).size());
       System.err.println("maze10HASSIZE:"+this.getChatTextForParticipant(p2ID, 10).size());
       System.err.println("maze11HASSIZE:"+this.getChatTextForParticipant(p2ID, 11).size());
       System.err.println("maze12HASSIZE:"+this.getChatTextForParticipant(p2ID, 12).size());
       System.err.println("maze13HASSIZE:"+this.getChatTextForParticipant(p2ID, 13).size()); 
       System.err.println("maze14HASSIZE:"+this.getChatTextForParticipant(p2ID, 14).size());
       System.err.println("maze10HASSIZE:"+this.getChatTextForParticipant(p2ID, 10).size());
       System.err.println("maze10HASSIZE:"+this.getChatTextForParticipant(p2ID, 10).size());
       System.err.println("maze11HASSIZE:"+this.getChatTextForParticipant(p2ID, 11).size());
       System.err.println("maze12HASSIZE:"+this.getChatTextForParticipant(p2ID, 12).size());
       System.err.println("maze13HASSIZE:"+this.getChatTextForParticipant(p2ID, 13).size()); 
       System.err.println("maze14HASSIZE:"+this.getChatTextForParticipant(p2ID, 14).size());
       System.err.println("maze14HASSIZE:"+this.getChatTextForParticipant(p2ID, 15).size());
       //System.err.println("maze16HASSIZE:"+maze16.size());
       
       System.err.println(this.p3Username);
       System.err.println("maze0HASSIZE:"+this.getChatTextForParticipant(p3ID, 0).size());
       System.err.println("maze1HASSIZE:"+this.getChatTextForParticipant(p3ID, 1).size());
       System.err.println("maze2HASSIZE:"+this.getChatTextForParticipant(p3ID, 2).size());
       System.err.println("maze3HASSIZE:"+this.getChatTextForParticipant(p3ID, 3).size());
       System.err.println("maze4HASSIZE:"+this.getChatTextForParticipant(p3ID, 4).size());
       System.err.println("maze5HASSIZE:"+this.getChatTextForParticipant(p3ID, 5).size());
       System.err.println("maze6HASSIZE:"+this.getChatTextForParticipant(p3ID, 6).size());
       System.err.println("maze7HASSIZE:"+this.getChatTextForParticipant(p3ID, 7).size()); 
       System.err.println("maze8HASSIZE:"+this.getChatTextForParticipant(p3ID, 8).size());
       System.err.println("maze9HASSIZE:"+this.getChatTextForParticipant(p3ID, 9).size());
       System.err.println("maze10HASSIZE:"+this.getChatTextForParticipant(p3ID, 10).size());
       System.err.println("maze11HASSIZE:"+this.getChatTextForParticipant(p3ID, 11).size());
       System.err.println("maze12HASSIZE:"+this.getChatTextForParticipant(p3ID, 12).size());
       System.err.println("maze13HASSIZE:"+this.getChatTextForParticipant(p3ID, 13).size()); 
       System.err.println("maze14HASSIZE:"+this.getChatTextForParticipant(p3ID, 14).size());
       System.err.println("maze10HASSIZE:"+this.getChatTextForParticipant(p3ID, 10).size());
       System.err.println("maze10HASSIZE:"+this.getChatTextForParticipant(p3ID, 10).size());
       System.err.println("maze11HASSIZE:"+this.getChatTextForParticipant(p3ID, 11).size());
       System.err.println("maze12HASSIZE:"+this.getChatTextForParticipant(p3ID, 12).size());
       System.err.println("maze13HASSIZE:"+this.getChatTextForParticipant(p3ID, 13).size()); 
       System.err.println("maze14HASSIZE:"+this.getChatTextForParticipant(p3ID, 14).size());
       System.err.println("maze14HASSIZE:"+this.getChatTextForParticipant(p3ID, 15).size());
      
       System.err.println(this.p4Username);
       System.err.println("maze0HASSIZE:"+this.getChatTextForParticipant(p4ID, 0).size());
       System.err.println("maze1HASSIZE:"+this.getChatTextForParticipant(p4ID, 1).size());
       System.err.println("maze2HASSIZE:"+this.getChatTextForParticipant(p4ID, 2).size());
       System.err.println("maze3HASSIZE:"+this.getChatTextForParticipant(p4ID, 3).size());
       System.err.println("maze4HASSIZE:"+this.getChatTextForParticipant(p4ID, 4).size());
       System.err.println("maze5HASSIZE:"+this.getChatTextForParticipant(p4ID, 5).size());
       System.err.println("maze6HASSIZE:"+this.getChatTextForParticipant(p4ID, 6).size());
       System.err.println("maze7HASSIZE:"+this.getChatTextForParticipant(p4ID, 7).size()); 
       System.err.println("maze8HASSIZE:"+this.getChatTextForParticipant(p4ID, 8).size());
       System.err.println("maze9HASSIZE:"+this.getChatTextForParticipant(p4ID, 9).size());
       System.err.println("maze10HASSIZE:"+this.getChatTextForParticipant(p4ID, 10).size());
       System.err.println("maze11HASSIZE:"+this.getChatTextForParticipant(p4ID, 11).size());
       System.err.println("maze12HASSIZE:"+this.getChatTextForParticipant(p4ID, 12).size());
       System.err.println("maze13HASSIZE:"+this.getChatTextForParticipant(p4ID, 13).size()); 
       System.err.println("maze14HASSIZE:"+this.getChatTextForParticipant(p4ID, 14).size());
       System.err.println("maze10HASSIZE:"+this.getChatTextForParticipant(p4ID, 10).size());
       System.err.println("maze10HASSIZE:"+this.getChatTextForParticipant(p4ID, 10).size());
       System.err.println("maze11HASSIZE:"+this.getChatTextForParticipant(p4ID, 11).size());
       System.err.println("maze12HASSIZE:"+this.getChatTextForParticipant(p4ID, 12).size());
       System.err.println("maze13HASSIZE:"+this.getChatTextForParticipant(p4ID, 13).size()); 
       System.err.println("maze14HASSIZE:"+this.getChatTextForParticipant(p4ID, 14).size());
       System.err.println("maze14HASSIZE:"+this.getChatTextForParticipant(p4ID, 15).size());
       
       this.saveInfo();
       this.saveDEBUGInfo();
       
       System.exit(-5123412);
       return null;
  }
  
  
  public Vector getClarificationsForParticipant(String recipientID, int mazeNo){
      if(recipientID.equalsIgnoreCase(p1ID)) return p1ClarificationRequests[mazeNo];
      else if(recipientID.equalsIgnoreCase(p2ID)) return this.p2ClarificationRequests[mazeNo]; 
      else if(recipientID.equalsIgnoreCase(p3ID)) return this.p3ClarificationRequests[mazeNo]; 
      else if(recipientID.equalsIgnoreCase(p4ID)) return this.p4ClarificationRequests[mazeNo]; 
      //else if(recipientID.equalsIgnoreCase(p5ID)) return this.p5ClarificationRequests[mazeNo]; 
      //else if(recipientID.equalsIgnoreCase(p6ID)) return this.p6ClarificationRequests[mazeNo]; 
      //else if(recipientID.equalsIgnoreCase(p7ID)) return this.p7ClarificationRequests[mazeNo]; 
      //else if(recipientID.equalsIgnoreCase(p8ID)) return this.p8ClarificationRequests[mazeNo];
      else{
          System.exit(-2342);
      }
      return null;
  }
  
  public boolean areInterventionsSentToParticipantInMaze(String participantID, int mazeNo){
      Vector interventions = getClarificationsForParticipant(participantID, mazeNo);
      if(interventions.size()==0)return false;
      return true;    
  }
  
   public String getWhetherChatTextIsResponse(MessageChatTextFromClient mct){
       
           if(this.p1ResponseToWHAT==mct|| this.p2ResponseToWHAT==mct|| this.p3ResponseToWHAT==mct|| this.p4ResponseToWHAT==mct||
              this.p5ResponseToWHAT==mct|| this.p6ResponseToWHAT==mct|| this.p7ResponseToWHAT==mct|| this.p8ResponseToWHAT==mct){
                 return "WHAT";
           }
           else if(this.p1ResponseToWHERE==mct|| this.p2ResponseToWHERE==mct|| this.p3ResponseToWHERE==mct|| this.p4ResponseToWHERE==mct||
              this.p5ResponseToWHERE==mct|| this.p6ResponseToWHERE==mct|| this.p7ResponseToWHERE==mct|| this.p8ResponseToWHERE==mct){
                 return "WHERE";
           }
           else if(this.p1ResponseToWHY==mct|| this.p2ResponseToWHY==mct|| this.p3ResponseToWHY==mct|| this.p4ResponseToWHY==mct||
              this.p5ResponseToWHY==mct|| this.p6ResponseToWHY==mct|| this.p7ResponseToWHY==mct|| this.p8ResponseToWHY==mct){
                 return "WHY";
           }
           return "NORMAL";
   }
  
 
  public void saveClarificationForParticipant(Object[] clarification, String recipientID, int mazeNo){
       
       if(recipientID.equalsIgnoreCase(p1ID)) this.p1ClarificationRequests[mazeNo].addElement(clarification);
       else if(recipientID.equalsIgnoreCase(p2ID)) this.p2ClarificationRequests[mazeNo].addElement(clarification);
       else if(recipientID.equalsIgnoreCase(p3ID)) this.p3ClarificationRequests[mazeNo].addElement(clarification);
       else if(recipientID.equalsIgnoreCase(p4ID)) this.p4ClarificationRequests[mazeNo].addElement(clarification);
       //else if(recipientID.equalsIgnoreCase(p5ID)) this.p5ClarificationRequests[mazeNo].addElement(clarification);
       //else if(recipientID.equalsIgnoreCase(p6ID)) this.p6ClarificationRequests[mazeNo].addElement(clarification);
       //else if(recipientID.equalsIgnoreCase(p7ID)) this.p7ClarificationRequests[mazeNo].addElement(clarification);
       //else if(recipientID.equalsIgnoreCase(p8ID)) this.p8ClarificationRequests[mazeNo].addElement(clarification);
       else{
           System.exit(-231355);
       }
       
  }
  
  MazeGameMazeIdentifier mgmi;
  
  public void processingGetmazeIDs() {
           
           
          
          if(mgmi==null){
             // String refMazesDirector = "C:\\sourceforge\\mazegamesetupswithbackup\\set_REFERENCESET\\cl1mzes.v";
              //String refMazesMatcher = "C:\\sourceforge\\mazegamesetupswithbackup\\set_REFERENCESET\\cl2mzes.v";
              mgmi = new MazeGameMazeIdentifier(refMazesDirector,refMazesMatcher);
          }
           
          
                   
           for(int i=0;i<this.or14or16;i++){
                try{
                this.p1Mazes[i]=mgmi.getMazeID(this.getMazeForParticipant(p1ID, i));
                
                this.p2Mazes[i]=mgmi.getMazeID(this.getMazeForParticipant(p2ID, i));
                this.p3Mazes[i]=mgmi.getMazeID(this.getMazeForParticipant(p3ID, i));
                this.p4Mazes[i]=mgmi.getMazeID(this.getMazeForParticipant(p4ID, i));
                }catch (Exception e){
                    System.err.println("THE CRASH OCCURS AT:"+i);
                    e.printStackTrace();
                    if(p2ID==null)System.exit(-5569);
                    System.exit(-555);
                    
                }
                
                
           }
      }
      
  
  
  
  
  
  
  
  
  
   
   public String getUsername(String participantID){
       if(participantID.equalsIgnoreCase(p1ID))return this.p1Username;
       else if(participantID.equalsIgnoreCase(p2ID)) return this.p2Username;
       else if(participantID.equalsIgnoreCase(p3ID)) return this.p3Username;
       else if(participantID.equalsIgnoreCase(p4ID)) return this.p4Username;
       //else if(participantID.equalsIgnoreCase(p5ID)) return this.p5Username;
       //else if(participantID.equalsIgnoreCase(p6ID)) return this.p6Username;
       //else if(participantID.equalsIgnoreCase(p7ID)) return this.p7Username;
       //else if(participantID.equalsIgnoreCase(p8ID)) return this.p8Username;
       else{
           System.exit(-234234);
       }
       return null;
       
   }
  
   
  
    public Vector getChatTextForParticipant(String participantID, int mazeNo){
          if(participantID.equalsIgnoreCase(p1ID)) return this.p1ChatTextTurns[mazeNo];
          else if (participantID.equalsIgnoreCase(p2ID)) return this.p2ChatTextTurns[mazeNo];
          else if (participantID.equalsIgnoreCase(p3ID)) return this.p3ChatTextTurns[mazeNo];
          else if (participantID.equalsIgnoreCase(p4ID)) return this.p4ChatTextTurns[mazeNo];
          //else if (participantID.equalsIgnoreCase(p5ID)) return this.p5ChatTextTurns[mazeNo];
          //else if (participantID.equalsIgnoreCase(p6ID)) return this.p6ChatTextTurns[mazeNo];
          //else if (participantID.equalsIgnoreCase(p7ID)) return this.p7ChatTextTurns[mazeNo];
          //else if (participantID.equalsIgnoreCase(p8ID)) return this.p8ChatTextTurns[mazeNo];
          else{
              System.exit(-323111);
          }
          return null;
          
    }
  
    
 //senderID
 //senderUsername
 //typing onset
 //typing enter
 //recipientID
 //recipientusername
 //text typed
 //number of deletes
 //number of edits
 //maze number
 //maze ID
 //there have been clarification requests
    
      public String getExcludedChatTextForMaze(String conditionOfExp, String pA, String pB, int mazenumber){
      String csvLINEOFTEXT="";
      Vector pAExcludedChatTextTurns = this.getExcludedChatTextForParticipant(pA, this.getUsername(pA), directory, mazenumber);
      Vector pBExcludedChatTextTurns = this.getExcludedChatTextForParticipant(pB, this.getUsername(pB), directory, mazenumber);
      Vector allExcludedChatText = VectorToolkit.combineANDSORTChatText(pAExcludedChatTextTurns, pBExcludedChatTextTurns);
      for(int i=0;i<allExcludedChatText.size();i++){
       MessageChatTextFromClient mctfc = (MessageChatTextFromClient)allExcludedChatText.elementAt(i);
               String senderID =  mctfc.getEmail();
               String senderUsername = mctfc.getUsername();
               String recipientID="";
               String recipientUsername="";
               if(senderID.equalsIgnoreCase(pA)){
                    recipientID = pB;
                    recipientUsername = this.getUsername(recipientID);
               }
               else if(senderID.equalsIgnoreCase(pB)){
                    recipientID = pA;
                    recipientUsername = this.getUsername(recipientID);
               }
               else{
                   System.exit(-2342341);
               }
              
               long typingOnset = mctfc.getServerTypingOnset();
               long typingEnter = mctfc.getServerTypingENTER();
               String text = mctfc.getText();
               int numberOfDeletes = mctfc.getNoOfDeletes();
               Vector keypresses = mctfc.getKeyPresses();
               String keycodes ="";
               for (int j=0;j<keypresses.size();j++){
                     Keypress keyp = (Keypress)keypresses.elementAt(j);     
                     keycodes = keycodes+ "("+keyp.getKeycode()+")";
               } 
               int mazeNo = mazenumber;
               String mazeDescription = mgmi.getMazeID(this.getMazeForParticipant(senderID, mazeNo));
                              
               String responsetype = this.getWhetherChatTextIsResponse(mctfc);
               
               boolean areInterventionsReceivedByParticipant = this.areInterventionsSentToParticipantInMaze(senderID, mazeNo);
               String areInterventionsReceivedByParticipantInCurrentMaze ="NO";
               if(areInterventionsReceivedByParticipant) areInterventionsReceivedByParticipantInCurrentMaze = "Interventions";
                               
               boolean areInterventionsReceivedByParticipantPartner = this.areInterventionsSentToParticipantInMaze(recipientID, mazeNo);
               String areInterventionsReceivedByParticipantPartnerInCurrentMaze ="NO";
               if(areInterventionsReceivedByParticipantPartner) areInterventionsReceivedByParticipantPartnerInCurrentMaze = "InterventionsReceivedByPartner";
               
               
               csvLINEOFTEXT = csvLINEOFTEXT + conditionOfExp + "|"+
                                   senderID + "|" + 
                                   senderUsername  + "|" + 
                                   typingOnset + "|" + 
                                   typingEnter + "|" + 
                                   text + "|" + 
                                   recipientID + "|" +
                                   recipientUsername + "|"+
                                   numberOfDeletes + "|" +
                                   keycodes+  "|" +
                                   mazeNo + "|" +
                                   mazeDescription +"|"+
                                   i + "|" +
                                   allExcludedChatText.size()+"|" + 
                                   "excluded"+responsetype + "|"+
                                   areInterventionsReceivedByParticipantInCurrentMaze+"|"+
                                   areInterventionsReceivedByParticipantPartnerInCurrentMaze + "|"+
                                   directory+
                                   "\n";           
          }
      return csvLINEOFTEXT;
  }  
  
  public String getChatTextForMaze(String pA, String pB, int mazenumber){
      return getIncludedChatTextForMaze(pA, pB, mazenumber) + 
             getExcludedChatTextForMaze("Dyad(4)",pA, pB, mazenumber);
  }  
      
      
   public String getIncludedChatTextForMaze(String pA, String pB, int mazenumber){
         
          String csvLINE="";
          Vector pAChatTextTurns = this.getChatTextForParticipant(pA, mazenumber);
          Vector pBChatTextTurns = this.getChatTextForParticipant(pB, mazenumber);
          //Vector sortedChatText()
          Vector allChatText = VectorToolkit.combineANDSORTChatText(pAChatTextTurns, pBChatTextTurns);
          
          for(int i=0;i<allChatText.size();i++){
               MessageChatTextFromClient mctfc = (MessageChatTextFromClient)allChatText.elementAt(i);
               String senderID =  mctfc.getEmail();
               String senderUsername = mctfc.getUsername();
               String recipientID="";
               String recipientUsername="";
               if(senderID.equalsIgnoreCase(pA)){
                    recipientID = pB;
                    recipientUsername = this.getUsername(recipientID);
               }
               else if(senderID.equalsIgnoreCase(pB)){
                    recipientID = pA;
                    recipientUsername = this.getUsername(recipientID);
               }
               else{
                   System.exit(-2342341);
               }
              
               long typingOnset = mctfc.getServerTypingOnset();
               long typingEnter = mctfc.getServerTypingENTER();
               String text = mctfc.getText();
               int numberOfDeletes = mctfc.getNoOfDeletes();
               Vector keypresses = mctfc.getKeyPresses();
               String keycodes ="";
               for (int j=0;j<keypresses.size();j++){
                     Keypress keyp = (Keypress)keypresses.elementAt(j);     
                     keycodes = keycodes+ "("+keyp.getKeycode()+")";
               } 
               int mazeNo = mazenumber;
               String mazeDescription = mgmi.getMazeID(this.getMazeForParticipant(senderID, mazeNo));
                              
               String responsetype = this.getWhetherChatTextIsResponse(mctfc);
               
               boolean areInterventionsReceivedByParticipant = this.areInterventionsSentToParticipantInMaze(senderID, mazeNo);
               String areInterventionsReceivedByParticipantInCurrentMaze ="NO";
               if(areInterventionsReceivedByParticipant) areInterventionsReceivedByParticipantInCurrentMaze = "Interventions";
                               
               boolean areInterventionsReceivedByParticipantPartner = this.areInterventionsSentToParticipantInMaze(recipientID, mazeNo);
               String areInterventionsReceivedByParticipantPartnerInCurrentMaze ="NO";
               if(areInterventionsReceivedByParticipantPartner) areInterventionsReceivedByParticipantPartnerInCurrentMaze = "InterventionsReceivedByPartner";
               
               
               csvLINE = csvLINE + "Dyad(4)" + "|"+
                                   senderID + "|" + 
                                   senderUsername  + "|" + 
                                   typingOnset + "|" + 
                                   typingEnter + "|" + 
                                   text + "|" + 
                                   recipientID + "|" +
                                   recipientUsername + "|"+
                                   numberOfDeletes + "|" +
                                   keycodes+  "|" +
                                   mazeNo + "|" +
                                   mazeDescription +"|"+
                                   i + "|" +
                                   allChatText.size()+"|" + 
                                   responsetype + "|"+
                                   areInterventionsReceivedByParticipantInCurrentMaze+"|"+
                                   areInterventionsReceivedByParticipantPartnerInCurrentMaze + "|"+
                                   directory+
                                   "\n";
              
              
               
          }
          return csvLINE;
    }
  
  
  
     public String getCSVChatText(){
       
         
         String value =  
                 
       getChatTextForMaze  (p1ID, p2ID, 0) + 
      
       
       getChatTextForMaze  (p1ID, p2ID, 1) + 
      
               
       getChatTextForMaze  (p1ID, p2ID, 2) + 
       
               
       getChatTextForMaze  (p1ID, p2ID, 3) + 
       
               
       getChatTextForMaze  (p1ID, p2ID, 4) + 
      
               
       getChatTextForMaze  (p1ID, p2ID, 5) + 
      
               
       getChatTextForMaze  (p1ID, p2ID, 6) + 
      
               
       getChatTextForMaze  (p1ID, p2ID, 7) + 
      
               
       getChatTextForMaze  (p1ID, p2ID, 8) + 
      
               
       getChatTextForMaze  (p1ID, p2ID, 9) + 
       
               
       getChatTextForMaze  (p1ID, p2ID, 10) + 
      
               
       getChatTextForMaze  (p1ID, p2ID, 11) + 
      
               
       getChatTextForMaze  (p1ID, p2ID, 12) + 
      
               
       getChatTextForMaze  (p1ID, p2ID, 13) + 
        
       
       getChatTextForMaze  (p1ID, p2ID, 14) + 
       
       
       getChatTextForMaze  (p1ID, p2ID, 15) +
       
                 
       getChatTextForMaze  (p3ID, p4ID, 0) + 
      
       
       getChatTextForMaze  (p3ID, p4ID, 1) + 
      
               
       getChatTextForMaze  (p3ID, p4ID, 2) + 
       
               
       getChatTextForMaze  (p3ID, p4ID, 3) + 
       
               
       getChatTextForMaze  (p3ID, p4ID, 4) + 
      
               
       getChatTextForMaze  (p3ID, p4ID, 5) + 
      
               
       getChatTextForMaze  (p3ID, p4ID, 6) + 
      
               
       getChatTextForMaze  (p3ID, p4ID, 7) + 
      
               
       getChatTextForMaze  (p3ID, p4ID, 8) + 
      
               
       getChatTextForMaze  (p3ID, p4ID, 9) + 
       
               
       getChatTextForMaze  (p3ID, p4ID, 10) + 
      
               
       getChatTextForMaze  (p3ID, p4ID, 11) + 
      
               
       getChatTextForMaze  (p3ID, p4ID, 12) + 
      
               
       getChatTextForMaze  (p3ID, p4ID, 13) + 
        
       
       getChatTextForMaze  (p3ID, p4ID, 14) + 
       
       
       getChatTextForMaze  (p3ID, p4ID, 15);
         
         
         File f =new File(directory+"\\"+this.filenameOUTPUT+"TURNS.csv");
         try{
            FileWriter fw = new FileWriter(f);
            fw.append(value);
            fw.flush();
            fw.close();
            System.err.println("SAVING TO: "+f.getAbsolutePath());
            //System.exit(-234);
         }catch (Exception e){
             e.printStackTrace();
         }
         
         
         
         return value;
     }
   
   
   
    
}    
             
             
     
    

    
    
    
               
             
     
    

    
    
    
    