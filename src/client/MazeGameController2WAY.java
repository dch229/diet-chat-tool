/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.awt.Dimension;
import java.io.File;
import java.util.Random;
import java.util.Vector;

import diet.message.MessageTask;
import diet.parameters.StringParameter;
import diet.server.Conversation;
import diet.server.Participant;
import diet.task.DefaultTaskController;
import diet.task.mazegame.message.*;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Date;

/**
 *
 * @author user
 * This class should actually be part of diet.task.mazegame.server
 * However, due to legacy support it has to be in this package as it uses non-public fields
 * of other classes in this package.
 */
public class MazeGameController2WAY extends DefaultTaskController{

    
    int uniqueMazeGameControllerID = getUniqueID();
    
    Conversation c;
    SetupIO sIO;
    SetupIOMazeGameMoveWriting sIOWriting;
    MazeGameController2WAYUI mgcUI;
    public Participant pDirector;
    public Participant pMatcher;
    //Participant pMatcher2;
    
    Vector pDirectorMazes = new Vector();
    Vector pMatcher1Mazes = new Vector();
    //Vector pMatcher2Mazes = new Vector();
    
    MazeWrap pDirectorMaze, pMatcher1Maze;//, pMatcher2Maze;
    Dimension pDirectorPosition, pMatcher1Position;//, pMatcher2Position;
    
    
    
    
    
    int mazeNumber =0;
    int prevMazeNumber =-1;
    
    
    int moveNo=0;
    
    int participant1MoveNo=0;
    int participant2MoveNo=0;

    boolean participant1IsOnSwitch;
    boolean participant2IsOnSwitch;

    int participant1NoOfTimesOnSwitch=0;
    int participant2NoOfTimesOnSwitch=0;
    
    long startOfCurrentMaze = new Date().getTime();
    long timeOfJointGoal = -1;
    
    //public boolean participant1HasGoneOnSwitch = false;
    //public boolean participant2HasGoneOnSwitch = false;
    
    
    static int idcount=0;
    synchronized static int getUniqueID(){
        idcount=idcount+1;
        return idcount;
    }
    
    
    
    public void resetCounters(){
        moveNo=0;
        participant1MoveNo=0;
        participant2MoveNo=0;
        participant1NoOfTimesOnSwitch=0;
        participant2NoOfTimesOnSwitch=0;
        this.startOfCurrentMaze=new Date().getTime();
        this.timeOfJointGoal=-1;
         
    
    }
    
    
    public void saveDataForMaze(){
         //c.saveDataToConversationHistory(pDirector, pMatcher.getUsername(), new Date().getTime(), "MAZEINFO", "AbsMoveno:$"+participant1MoveNo+);
         
        String dataToSave ="";
        long timeOfExitFromMaze = new Date().getTime();
        try{
            String prevMazeNumberAsString ="FIRSTMAZE";
            if(this.prevMazeNumber>-1){
                prevMazeNumberAsString =""+prevMazeNumber;
            }
            
            
         dataToSave = dataToSave + 
                 
                 " mazenumber:"+ this.mazeNumber+
                 " previousmazenumber: "+prevMazeNumberAsString +
                 " mazestarttime:" + this.startOfCurrentMaze+
                 " mazestoptime:" + timeOfExitFromMaze;
                 
         if(this.isCompleted()){
             dataToSave = dataToSave+ " mazeiscompleted:" +"COMPLETED";
         } 
         else {
             dataToSave = dataToSave+ " mazeiscompleted:" +"INCOMPLETE";
         } 
         dataToSave = dataToSave+ " mazetotalnumberofgamemoves:"+moveNo;
         
         dataToSave = dataToSave+ " TimeOfBothOnGoal: "+ this.timeOfJointGoal;
         
         String dataToSaveDirector = dataToSave + " participanttotalnumberofgamemoves:"+participant1MoveNo +
                                     " participanttotalnumberofswitchtraversals:"+participant1NoOfTimesOnSwitch +
                                     " partnerid: "+pMatcher.getParticipantID()+
                                     " partnerusername: "+pMatcher.getUsername();
         
         String dataToSaveMatcher = dataToSave + " participanttotalnumberofgamemoves:"+participant2MoveNo +
                                     " participanttotalnumberofswitchtraversals:"+participant2NoOfTimesOnSwitch +
                                     " partnerid: "+pDirector.getParticipantID()+
                                     " partnerusername: "+pDirector.getUsername();
         
         
         
         
         c.saveDataToConversationHistory(pDirector, "", new Date().getTime(), "MAZEINFO:P1", dataToSaveDirector);
         c.saveDataToConversationHistory(pMatcher, "", new Date().getTime(), "MAZEINFO:P2", dataToSaveMatcher);
         
         }catch (Exception e){
             e.printStackTrace();
             c.saveDataToConversationHistory("DATASAVING", "ERRORSAVING" +mazeNumber);
         }
         
     }
    
    
    public MazeGameController2WAY(Conversation c, File p1Mazes, File p2Mazes){
         //ObjectInputStream oi = new ObjectInputStream
        try{
        ObjectInputStream p1Oi = new ObjectInputStream(new FileInputStream(p1Mazes));
        ObjectInputStream p2Oi = new ObjectInputStream(new FileInputStream(p2Mazes));
        
        Vector p1Mzs = (Vector)p1Oi.readObject();
        Vector p2Mzs = (Vector)p2Oi.readObject();
        
        this.c=c;
        this.pDirectorMazes=p1Mzs;
        this.pMatcher1Mazes=p2Mzs;
        //sIO = new SetupIO("C:\\svndiet\\chattool\\mazegame",c.getDirectoryNameContainingAllSavedExperimentalData().toString());
        sIO = new SetupIO(System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+File.separator+"mazegame"+File.separator+"mazegamesetup",c.getDirectoryNameContainingAllSavedExperimentalData().toString());
        sIOWriting = new SetupIOMazeGameMoveWriting(c.getDirectoryNameContainingAllSavedExperimentalData().toString());
        mgcUI = new MazeGameController2WAYUI(this);    
        
        
        
        }catch (Exception e){
            e.printStackTrace();
            Conversation.printWSln("Main", "Error loading mazes");
        }
        
        
        
    }
    
    
    public MazeGameController2WAY(Conversation c, Vector p1Mazes, Vector p2Mazes){
        
        this.c=c;
        this.pDirectorMazes=p1Mazes;
        this.pMatcher1Mazes=p2Mazes;
        //sIO = new SetupIO("C:\\svndiet\\chattool\\mazegame",c.getDirectoryNameContainingAllSavedExperimentalData().toString());
        sIO = new SetupIO(System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+File.separator+"mazegame"+File.separator+"mazegamesetup",c.getDirectoryNameContainingAllSavedExperimentalData().toString());
        sIOWriting = new SetupIOMazeGameMoveWriting(c.getDirectoryNameContainingAllSavedExperimentalData().toString());
        mgcUI = new MazeGameController2WAYUI(this);    
    }
    
    
    public MazeGameController2WAY(Conversation c){
        this.c=c;
        //sIO = new SetupIO("C:\\svndiet\\chattool\\mazegame",c.getDirectoryNameContainingAllSavedExperimentalData().toString());
         sIO = new SetupIO(System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+File.separator+"mazegame"+File.separator+"mazegamesetup",c.getDirectoryNameContainingAllSavedExperimentalData().toString());
        sIOWriting = new SetupIOMazeGameMoveWriting(c.getDirectoryNameContainingAllSavedExperimentalData().toString());
        mgcUI = new MazeGameController2WAYUI(this);
        this.loadMazes();
    }
    
     public MazeGameController2WAY(Conversation c, boolean toggleLoad14mazes_DEPRECATED){
        this.c=c;
        
        
        //sIO = new SetupIO("C:\\svndiet\\chattool\\mazegame",c.getDirectoryNameContainingAllSavedExperimentalData().toString());
        sIO = new SetupIO(System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+File.separator+"mazegame"+File.separator+"mazegamesetup",c.getDirectoryNameContainingAllSavedExperimentalData().toString());
        sIOWriting = new SetupIOMazeGameMoveWriting(c.getDirectoryNameContainingAllSavedExperimentalData().toString());
        mgcUI = new MazeGameController2WAYUI(this);
        this.loadMazes();
    }
    
    
    
    public MazeGameController2WAYUI getUI(){
        return this.mgcUI;
    }
    
    public void updateJProgressBar(final int value, final String text, final Color color){
        mgcUI.updateJProgressBar(value, text, color);
    }
    
    public Vector getP1Mazes_Cloned(){
        return cloneVectorOfMazes(this.pDirectorMazes);
    }
    public Vector getP2Mazes_Cloned(){
        
        return cloneVectorOfMazes(this.pMatcher1Mazes);
    }
    
    
     //public void  startMazeGame
           
        
     //}
    
    
    public void startNewMazeGame(Participant a, Participant b){
        Conversation.printWSln("Main", "Mazegame controller is sending mazes to both clients "+a.getUsername()+" and also to "+b.getUsername());
        pDirector = a;
        pMatcher=b;
        this.startOfCurrentMaze = new Date().getTime();
        this.sendMazesToClients();
        sIO.saveClientMazesOfTwoClientsByName(pDirectorMazes, pMatcher1Mazes);
    }
    
    
    
    public void startBothParticipants(Participant p1, Participant p2){
        pDirector = p1;
        pMatcher = p2;
        this.sendMazesToClients(); 
        sIO.saveClientMazesOfTwoClientsByName(pDirectorMazes, pMatcher1Mazes);
        
    }
    
    
    public void appendToUI(String chattext){
        this.mgcUI.append(chattext);
    }
    
    
    
  
    
    public void participantJoinedConversationDEPRECATED(Participant pJoined){
        //If the program gets to the end of this method 
        //The director and matchers should have been established
        Vector v = (Vector)c.getParticipants().getAllActiveParticipants().clone();
        v = (Vector)c.getParticipants().getAllParticipants().clone();
        if(v.size()<2){
            return;
        }
        else if(v.size()==2){
            c.getExpManager().getEMUI().println("Main", "Mazegame controller is sending mazes to both clients");
            
        }
        else{
            c.getExpManager().getEMUI().println("Main", "There are too many clients logged in. The max. number is 2");
            c.getExpManager().getEMUI().println("Main", "The maze game now has an indeterminate state.");
            c.getExpManager().getEMUI().println("Main", "IT IS ADVISABLE TO CLOSE THIS EXPERIMENT AND RE-RUN / RESTART");
            return;
        }
           
        pDirector = (Participant) v.elementAt(0);
        pMatcher = (Participant) v.elementAt(1);
                
        this.sendMazesToClients();
        sIO.saveClientMazesOfTwoClientsByName(pDirectorMazes, pMatcher1Mazes);
    }
    
   
    
    public void loadMazes(){
        try{
          System.err.println("MESSAGE0");
          //Check to see if the experiment is actually re-starting a previous one
          //If so, need to load the old mazes
          StringParameter spr = (StringParameter)c.getExperimentSettings().getParameter("Recovery");
          if(spr!=null){
              Vector v = sIO.getAllMazesFromExperimentDirectory2WAY(spr.getValue());
              this.pDirectorMazes=(Vector)v.elementAt(0);
              this.pMatcher1Mazes=(Vector)v.elementAt(1);
              
          }
          else{
             //System.exit(-12312324); 
             loadRandomMazesFromFile();
          }   
          initializeMazesToFirstMaze();
        }catch (Exception e){
            e.printStackTrace();
        }  
    }
    
   
    
    
   
    
    public void loadRandomMazesFromFile(){
        System.err.println("HEREA");
        Vector v = this.sIO.getRandomPairOfMazeVectors();
        System.err.println("HEREB");
        Vector client1Mazes = (Vector)v.elementAt(0);
        System.err.println("HEREC");
        Vector client2Mazes = (Vector)v.elementAt(1);
        System.err.println("HERED");
        Random r = new Random();
        System.err.println("HEREE");
        int i = r.nextInt(2);
        System.err.println("HEREF");
        this.pDirectorMazes=(Vector)v.elementAt(0);
        this.pMatcher1Mazes=(Vector)v.elementAt(1);
        

    }
    
    public static synchronized Vector cloneVectorOfMazes(Vector v){
            Vector v2 = new Vector();
            for(int i=0;i<v.size();i++){
                Maze m = (Maze)v.elementAt(i);
                Maze m2 = m.getClone();
                v2.addElement(m2); 
            }
            return v2;
    }
    
    public void initializeMazesToFirstMaze(){
        pDirectorMaze = new MazeWrap((Maze) pDirectorMazes.elementAt(0));
        pMatcher1Maze = new MazeWrap((Maze) pMatcher1Mazes.elementAt(0));
        mazeNumber=0;
        pDirectorPosition = new Dimension(0,0);
        pMatcher1Position = new Dimension(0,0);
    }
    
    public void sendMazesToClients(){
         initializeMazesToFirstMaze();
          mgcUI.initializeJTabbedPane(pDirector.getUsername(),pDirectorMazes,pMatcher.getUsername(),this.pMatcher1Mazes);//,pMatcher2.getUsername(),pMatcher2Mazes);
      
         Game gDirector = new Game(pDirectorMazes);
         Game gMatcher1 = new Game(pMatcher1Mazes);
         
         //expIOW.saveMazes(client1mazes, client2mazes, client3mazes);
         c.sendTaskMoveToParticipant(pDirector, new MessageNewMazeGame("server","server",gDirector));
         c.sendTaskMoveToParticipant(pMatcher, new MessageNewMazeGame("server","server",gMatcher1));
    }
   
    
    public void reconnectParticipant(Participant pReloggingIn){
        if(pReloggingIn==pDirector){
             Game gDirector = new Game(pDirectorMazes);
             c.sendTaskMoveToParticipant(pDirector, new MessageNewMazeGame("server","server",gDirector));
            
        }
        else if(pReloggingIn==pMatcher){
             Game gMatcher1 = new Game(pMatcher1Mazes);
             c.sendTaskMoveToParticipant(pMatcher, new MessageNewMazeGame("server","server",gMatcher1));
        }
         this.moveToMazeNo(this.mazeNumber);
        
    }
    
    
    
    
    
    synchronized public boolean isAonOthersSwitch(String a){
        if(this.pDirector.getUsername().equalsIgnoreCase(a)){
            //is participant 1 on participant 2s switch?
            return this.participant1IsOnSwitch;
        }
        else if(this.pMatcher.getUsername().equalsIgnoreCase(a)){
            return this.participant2IsOnSwitch;
        }
        else{
            Conversation.printWSln("Main", "Error in working out whether A is on Other's switch because "+a+" is not (1) "+pDirector.getUsername()+" or(2)"+pMatcher.getUsername());
            return false;
        }
    }

    synchronized public boolean isOtherSOnAswitch(String a){
        try{
            if(this.pDirector.getUsername().equalsIgnoreCase(a)){
                //is participant 1 on participant 2s switch?
                return this.participant2IsOnSwitch;
            }
            else if(this.pMatcher.getUsername().equalsIgnoreCase(a)){
                return this.participant1IsOnSwitch;
            }
            else{
                Conversation.printWSln("Main", "Error in working out whether Other is On A's switch because "+a+" is not (1) "+pDirector.getUsername()+" or(2)"+pMatcher.getUsername());
           
                return false;
            }
        }catch (Exception e){
             Conversation.printWSln("Main", "Error in working out whether Other is On A's switch");
             return false;
        }
    }


    synchronized public int getSwitchTraversalCount(String participantname){
      try{
        if(this.pDirector.getUsername().equalsIgnoreCase(participantname)){
            //is participant 1 on participant 2s switch?
            return this.participant1NoOfTimesOnSwitch;
        }
        else if(this.pMatcher.getUsername().equalsIgnoreCase(participantname)){
            return this.participant2NoOfTimesOnSwitch;
        }
        else{
            Conversation.printWSln("Main", "Error in working out traversal count (1) "+participantname);
            return -1;
        }
        }
      catch (Exception e){
            Conversation.printWSln("Main", "Error in working out traversal count (2) "+e.getMessage());
            return -1;
      }
    }

synchronized public int getOthersSwitchTraversalCount(String participantname){
      try{
        if(this.pDirector.getUsername().equalsIgnoreCase(participantname)){
            //is participant 1 on participant 2s switch?
            return this.participant2NoOfTimesOnSwitch;
        }
        else if(this.pMatcher.getUsername().equalsIgnoreCase(participantname)){
            return this.participant1NoOfTimesOnSwitch;
        }
        else{
            Conversation.printWSln("Main", "Error in working out switch traversal");
            return -1;
        }
        }
      catch (Exception e){
            Conversation.printWSln("Main", "Error in working out switch traversal");
            return -1;
      }
    }

    public int getOthersMoveNo(String participantname){
        try{
            if(this.pDirector.getUsername().equalsIgnoreCase(participantname)){
                //is participant 1 on participant 2s switch?
                return this.participant2MoveNo;
             }
             else if(this.pMatcher.getUsername().equalsIgnoreCase(participantname)){
                return this.participant1MoveNo;
        }
        else{
            Conversation.printWSln("Main", "Error in working out Others Move No");
            return -1;
            }
      }
      catch (Exception e){
            Conversation.printWSln("Main", "Error in working out Others Move No");
            return -1;

    }
    }

    
    public boolean processMazeMove(MessageTask mt,Participant origin, boolean automaticallyGoTONextMaze){
      try{   
        if(origin==pDirector){
          if (mt instanceof MessageCursorUpdate){
             System.out.println("Server detects client1 sending cursor update");
             
             
             
             MessageCursorUpdate mcu = (MessageCursorUpdate)mt;
             this.pDirectorMaze.getMaze().moveTo(mcu.newPos);
             this.mgcUI.movePositionDirector(mcu.newPos);
             
             if(pMatcher1Maze.getMaze().isASwitch(pDirectorMaze.getMaze().getMinBounds(),mcu.newPos)){
                 mcu.setASwitch(true);
             }
             else{
                 mcu.setASwitch(false);
             }
             if(pMatcher1Maze.getMaze().isASwitch(pDirectorMaze.getMaze().getMinBounds(),mcu.newPos)){
                System.out.println("Server detects client1 as being on client 2 and 3 switch");
                MessageChangeGateStatus mscg = new MessageChangeGateStatus("server","server",true,pMatcher.getUsername());
                //mscg.setRecipient(pMatcher1.getUsername()+"|"+pMatcher2.getUsername());
                //mcu.setASwitch(true);
                c.sendTaskMoveToParticipant(pMatcher,mscg);
                
                this.mgcUI.changeGateStatusMatcher(true);
                
                mcu.setASwitch(true);
                this.sIOWriting.saveMessage(mcu);
                this.sIOWriting.saveMessage(mscg);
                this.participant1IsOnSwitch=true;
                this.participant1NoOfTimesOnSwitch++;
                }
                else {
                    System.out.println("Server detects client1 as NOT being on switch");
                    MessageChangeGateStatus mscg = new MessageChangeGateStatus("server","server",false,pMatcher.getUsername());
                    //mscg.setRecipient(pMatcher1.getUsername()+"|"+pMatcher2.getUsername());
                    //mcu.setASwitch(false);
                    c.sendTaskMoveToParticipant(pMatcher,mscg);
                    
                    this.mgcUI.changeGateStatusMatcher(false);
                    
                    mcu.setASwitch(false);
                    this.sIOWriting.saveMessage(mcu);
                    this.sIOWriting.saveMessage(mscg);

                }
             
           }

          else {
            System.out.println("experiment in progress , was expecting username or cursor");
            System.out.println("don't know: " + mt.getClass().toString());
            }   

        //messages.addElement(m);
        ////expIOW.saveMessage(m);

       }
        else if(origin == pMatcher ) {
       if (mt instanceof MessageCursorUpdate){
           System.out.println("Server detects client2 sending cursor update");
           MessageCursorUpdate mcu = (MessageCursorUpdate)mt;
           pMatcher1Maze.getMaze().moveTo(mcu.newPos);
           this.mgcUI.movePositionMatcher(mcu.newPos);
           if(pDirectorMaze.getMaze().isASwitch(pMatcher1Maze.getMaze().getMinBounds(),mcu.newPos)){
                 mcu.setASwitch(true);
             }
             else{
                 mcu.setASwitch(false);
           }
           //MazeSquare current3=((Maze)client3Maze).getCurrent();
           if(pDirectorMaze.getMaze().isASwitch(pMatcher1Maze.getMaze().getMinBounds(),mcu.newPos)){
              //
               
              System.out.println("Server detects client2 and client3 as being on client1's switch");
              MessageChangeGateStatus mscg = new MessageChangeGateStatus("server","server",true,pDirector.getUsername());
              c.sendTaskMoveToParticipant(pDirector,mscg);
              this.mgcUI.changeGateStatusDirector(true);    
              //mcu.setASwitch(true);
              this.sIOWriting.saveMessage(mcu);
              this.sIOWriting.saveMessage(mscg);

              this.participant2IsOnSwitch=true;
              this.participant2NoOfTimesOnSwitch++;

          }
          else {
            System.out.println("Server detects client2 or client3 as NOT being on  cl1's switch");
            MessageChangeGateStatus mscg = new MessageChangeGateStatus("server","server",false,pDirector.getUsername());
            c.sendTaskMoveToParticipant(pDirector,mscg);
            this.mgcUI.changeGateStatusDirector(false);
            
            //mcu.setASwitch(false);
            this.sIOWriting.saveMessage(mcu);
            this.sIOWriting.saveMessage(mscg);
          }
       }

      else {
          System.out.println("experiment in progress2 , was expecting username or cursor update");
          System.out.println("don't know: " + mt.getClass().toString());
      }
      
      //expIOW.saveMessage(m);

   }
     
   moveNo++;
     if(origin==this.pDirector){
         participant1MoveNo++;
     }
     else if(origin == this.pMatcher){
         participant2MoveNo++;
     }     
        
     
   if(pDirectorMaze.getMaze().isCompleted() && pMatcher1Maze.getMaze().isCompleted()){
       if(timeOfJointGoal<0)this.timeOfJointGoal = new Date().getTime();
       c.saveDataToConversationHistory("MAZECOMPLETE", "("+this.mazeNumber+")"+ 
               " ("+pDirector.getParticipantID()+","+ pDirector.getUsername()+
               " ("+pMatcher.getParticipantID()+ ", "+pMatcher.getUsername()+timeOfJointGoal);
   }  
        
   if(automaticallyGoTONextMaze&&  pDirectorMaze.getMaze().isCompleted()&&pMatcher1Maze.getMaze().isCompleted()){
      System.out.println("Maze number"+ mazeNumber+ " completed");
      moveToNextMaze();
      
        

   }
     }catch (Exception e){
         System.out.println("STACKTRACE");
         System.err.println("ERROR IN MAZEGAME TASKMOVE HANDLER");
         e.printStackTrace();
     }      

     return (pDirectorMaze.getMaze().isCompleted()&&pMatcher1Maze.getMaze().isCompleted());
           
    }
    
    
    synchronized public boolean isCompleted(){
        return pDirectorMaze.getMaze().isCompleted()&&pMatcher1Maze.getMaze().isCompleted();
    }
    

    synchronized public void processTaskMove(MessageTask mt, Participant origin){
       this.processMazeMove(mt, origin, true);
  
    }
    
    
  public void displayMessage(String s){
      MessageWaitForOthersToCatchUp mwfotcu = new  MessageWaitForOthersToCatchUp("server","server",s,Color.red);
      c.sendTaskMoveToParticipant(pDirector,mwfotcu);
      c.sendTaskMoveToParticipant(pMatcher,mwfotcu);
  }  
    
   
  
  
  
 synchronized public boolean moveToMazeNo(int newMazeNo){
     return moveToMazeNo(newMazeNo, "Moving to maze "+newMazeNo);
 } 
  
 synchronized public boolean moveToMazeNo(int mazeNo,String text){
   this.saveDataForMaze();
   resetCounters();
   if (mazeNumber>=pDirectorMazes.size()-1 ||mazeNumber>=pMatcher1Mazes.size()-1) 
      {
       System.out.println("MoveToMazeNo returning false "+mazeNumber);
       Conversation.printWSln("Main", "Experimenter attempted to go to next or previous maze that doesn't exist...so doing nothing");
       return false;
   }
   resetCounters();
   pDirectorMaze = new MazeWrap((Maze) pDirectorMazes.elementAt(mazeNo));
   pMatcher1Maze = new MazeWrap((Maze) pMatcher1Mazes.elementAt(mazeNo));
   prevMazeNumber=mazeNumber;
   mazeNumber=mazeNo;
   
   pDirectorPosition = new Dimension(0,0);
   pMatcher1Position = new Dimension(0,0);
   System.out.println("move to maze number");
   //MessageNextMaze_SentAsIndex mnm= new MessageNextMaze_SentAsIndex("server","server",mazeNo,"Moving to maze "+mazeNo);
   MessageNextMaze_SentAsIndex mnm= new MessageNextMaze_SentAsIndex("server","server",mazeNo, text);
   this.mgcUI.moveToMazeNo(mazeNo);
   c.sendTaskMoveToParticipant(pDirector,mnm);
   c.sendTaskMoveToParticipant(pMatcher,mnm);
   
   this.sIOWriting.saveMessage(mnm);
   
   return true;
} 
  
 
 public boolean moveToNextMazeWithNewPartners(Participant aDirector, Participant bMatcher, int newMazeNumber){
       this.saveDataForMaze();
       resetCounters();
       if (mazeNumber>=pDirectorMazes.size()-1 ||mazeNumber>=pMatcher1Mazes.size()-1) {
          System.out.println("MoveToNextMaze returning false "+mazeNumber);
          return false;
       }
      
       this.pDirector=aDirector;
       this.pMatcher=bMatcher;
       prevMazeNumber=mazeNumber;
       mazeNumber=newMazeNumber;
       this.mgcUI.changeTabNamesAndMazes(pDirector.getUsername(), pDirectorMazes,  pMatcher.getUsername(),pMatcher1Mazes, mazeNumber);
        
       Game gDirector = new Game(pDirectorMazes);
       Game gMatcher1 = new Game(pMatcher1Mazes);
        
        //gDirector = gMatcher1;
        //gDirector=null;
        //gMatcher1=null;
        
        
        pDirectorMaze = new MazeWrap((Maze) pDirectorMazes.elementAt(pDirectorMazes.indexOf(pDirectorMaze.getMaze())+1));
        pMatcher1Maze = new MazeWrap((Maze) pMatcher1Mazes.elementAt(pMatcher1Mazes.indexOf(pMatcher1Maze.getMaze())+1));
       
        pDirectorPosition = new Dimension(0,0);
        pMatcher1Position = new Dimension(0,0);
        System.out.println("move to next maze EXPERIMENT IS TRUE");
        //MessageNextMaze_SentAsIndex mnmDirector= new MessageNextMaze_SentAsIndex("server","server",mazeNumber,gDirector, "Moving to maze "+mazeNumber);
        //MessageNextMaze_SentAsIndex mnmMatcher= new MessageNextMaze_SentAsIndex("server","server",mazeNumber,gMatcher1, "Moving to maze "+mazeNumber);
        
         MessageNextMaze_SentAsIndex mnmDirector= new MessageNextMaze_SentAsIndex("server","server",mazeNumber,gDirector, "Moving to next maze");
         MessageNextMaze_SentAsIndex mnmMatcher= new MessageNextMaze_SentAsIndex("server","server",mazeNumber,gMatcher1, "Moving to next maze");
        
        
        c.sendTaskMoveToParticipant(pDirector,mnmDirector);
        c.sendTaskMoveToParticipant(pMatcher,mnmMatcher);
        
        this.sIOWriting.saveMessage(mnmDirector);
        this.sIOWriting.saveMessage(mnmMatcher);
        
   return true;
       
       
       
       
       
 }
    
 public boolean moveToNextMaze(){
     return this.moveToNextMaze("");
 }
 
   
 
 
  public boolean moveToNextMaze(String displayMessage){
   this.saveDataForMaze();
   resetCounters();
    if (mazeNumber>=pDirectorMazes.size()-1 ||mazeNumber>=pMatcher1Mazes.size()-1) 
      {
       System.out.println("MoveToNextMaze returning false "+mazeNumber);
       return false;
   }
   
   pDirectorMaze = new MazeWrap((Maze) pDirectorMazes.elementAt(pDirectorMazes.indexOf(pDirectorMaze.getMaze())+1));
   pMatcher1Maze = new MazeWrap((Maze) pMatcher1Mazes.elementAt(pMatcher1Mazes.indexOf(pMatcher1Maze.getMaze())+1));
   prevMazeNumber=mazeNumber;
   mazeNumber++;
   pDirectorPosition = new Dimension(0,0);
   pMatcher1Position = new Dimension(0,0);
    System.out.println("move to next maze EXPERIMENT IS TRUE");
   MessageNextMaze_SentAsIndex mnm= new MessageNextMaze_SentAsIndex("server","server",mazeNumber, displayMessage);
   c.sendTaskMoveToParticipant(pDirector,mnm);
   c.sendTaskMoveToParticipant(pMatcher,mnm);
   this.mgcUI.moveToMazeNo(mazeNumber);
   this.sIOWriting.saveMessage(mnm);
    
   return true;
}

   public String getDirectorOrMatcher(Participant p){
       if(p==pDirector)return "D";
       if(p==pMatcher)return "M1";
       return "";
   }
    
    
    @Override
    public void closeDown(){
        try{
          this.sIOWriting.closeDown();
        }catch(Exception e){
            System.err.println("ERROR CLOSING DOWN MAZEGAME CONTROLLER");
        }  
    }
    
    public int getMazeNo(){
        return this.mazeNumber;
    }
    public int getMoveNo(){
        return this.moveNo;
    }
    public int getParticipantsMoveNo(Participant p){
        if(p==pDirector){
            return this.participant1MoveNo;
        }
        else if(p==pMatcher){
            return this.participant2MoveNo;
        }
        return -9999999;
    }
    
}
