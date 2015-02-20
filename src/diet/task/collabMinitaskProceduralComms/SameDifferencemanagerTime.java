/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.task.collabMinitaskProceduralComms;

import diet.server.ConversationController.CCGROOP3SEQ4;
import java.util.Date;

/**
 *
 * @author sre
 */
public class SameDifferencemanagerTime extends Thread {
    
    JSDM_4WAYConversationControllerINTERFACE cC;
    long practiceTime = 20000000;
    long condnTime= 100000000;
    //                                      10000  11000  12000  13000  14000
    String[] stage           = {"practice","null","null","null","null","null","null","null","null"};
    long[]   stageStartTime  = new long[9];
    //int[]    durationsOfContiguousApparentOrigins = new int[6];
    long[]   endOfContiguousApparentOrigins = new long[9];
    long[]   startOfContiguousApparentOrigins = new long[9];
    boolean  addConditionTimetoLastCondition=false;
    
    public SameDifferencemanagerTime(){
        
    }
    public int getIDX(){
        return this.stageIDX;
    }
    
    public SameDifferencemanagerTime(JSDM_4WAYConversationControllerINTERFACE cC, String[] stages, long practiceTime, long condnTime, boolean  addConditionTimetoLastCondition){
        this.cC=cC;
       
        stage[0] = "practice";
        stage[1] = stages[0];
        stage[2] = stages[1];
        stage[3] = stages[2];
        stage[4] = stages[3];
        stage[5] = "diff";
        stage[6] = "diff";
        stage[7] = "stop";
        this.practiceTime = practiceTime;
        this.condnTime=condnTime;
        this.addConditionTimetoLastCondition= addConditionTimetoLastCondition; 
       
        
        
        stageStartTime[0] =0;
        stageStartTime[1] = practiceTime;
        stageStartTime[2] = practiceTime+condnTime;
        stageStartTime[3] = practiceTime+condnTime+condnTime;
        stageStartTime[4] = practiceTime+condnTime+condnTime+condnTime;
        stageStartTime[5] = practiceTime+condnTime+condnTime+condnTime+condnTime;
    
        for(int i=0;i<stage.length;i++){
            String stagetype = stage[i];
            //durationsOfContiguousApparentOrigins[i]= getDurationOfContinuousApparentOriginIllusion(i); //DONT THINK THIS IS USED SO HAVE REMOVED IT
            endOfContiguousApparentOrigins[i]= getEndOfContinuousApparentOriginIllusion(i);
        }
        calculateStartsOfContinuousApparentOriginIllusion();
        display();
        this.start();
        
        
    }
    
    long startTime = new Date().getTime();   
    int stageIDX =0;
    
    
    
    public void drawJProgressBars(){
        //System.out.println("DRAWING A JPROGRESS BAR");
        long currTime = new Date().getTime();
        long timeElapsedSinceStart = currTime-startTime;
        long timeOfNextApparentSpeakerChange = this.endOfContiguousApparentOrigins[stageIDX];
        long timeOfLastApparentSpeakerChange = this.startOfContiguousApparentOrigins[stageIDX];
        long timeRemainingTillNextApparentSpeakerChange = timeOfNextApparentSpeakerChange - timeElapsedSinceStart;
        long durationOfCurrent = timeOfNextApparentSpeakerChange-timeOfLastApparentSpeakerChange;
        //System.out.println("time elapsed since start "+ timeElapsedSinceStart);
        //System.out.println("timeOfNextApparentSpeakerChange " + timeOfNextApparentSpeakerChange);
        //System.out.println("timeRemainingTillNextApparentSpeakerChange "+ timeRemainingTillNextApparentSpeakerChange);
        //System.out.println("durationOfCurrent "+durationOfCurrent);
        
        //System.out.println("PROGRESSBAR: "+timeRemainingTillNextApparentSpeakerChange+"...percentage "+  (((float) timeRemainingTillNextApparentSpeakerChange)/(float)durationOfCurrent )*100);
        float percentage = (((float) timeRemainingTillNextApparentSpeakerChange)/(float)durationOfCurrent )*100; 
        if(percentage<0)percentage=0;if(percentage>100)percentage=100;
        if(cC!=null)cC.changeJProgressBarforApparentSpeakerChange(percentage, timeRemainingTillNextApparentSpeakerChange);
        
        
        
        currTime = new Date().getTime();
        timeElapsedSinceStart = currTime-startTime;    
        long timeOfNextChange = stageStartTime[stageIDX+1];
        long timeRemainingTillNextIntervention = timeOfNextChange-timeElapsedSinceStart;
        String timeMessage="";
        if(timeRemainingTillNextIntervention>60000) timeMessage = ((long)timeRemainingTillNextIntervention/60000)+" minutes";
        if(timeRemainingTillNextIntervention<=60000) timeMessage = ((long)timeRemainingTillNextIntervention/1000)+" seconds";
        cC.updateJProgressBarPhysical("Time till next Intervention: "+timeMessage, 0);
    }
    
    
    public void nextIntervention(){
        try{
        //System.out.println("PERFORMING NEXT INTERVENTION");
        stageIDX++;
        if(cC!=null)cC.nextState();
        debugProgressbar();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void debugProgressbar(){
        try{
        long currTime = new Date().getTime();
        long timeElapsedSinceStart = currTime-startTime;
        long timeOfNextApparentSpeakerChange = this.endOfContiguousApparentOrigins[stageIDX];
        long timeOfLastApparentSpeakerChange = this.startOfContiguousApparentOrigins[stageIDX];
        long timeRemainingTillNextApparentSpeakerChange = timeOfNextApparentSpeakerChange - timeElapsedSinceStart;
        long durationOfCurrent = timeOfNextApparentSpeakerChange-timeOfLastApparentSpeakerChange;
        float percentage = (((float) timeRemainingTillNextApparentSpeakerChange)/(float)durationOfCurrent )*100; 
        if(percentage<0)percentage=0;if(percentage>100)percentage=100;
        if(cC!=null)cC.changeJProgressBarforApparentSpeakerChange(percentage, timeRemainingTillNextApparentSpeakerChange);
        System.out.println(stageIDX+" "+"JPROGRESSBAR:    percentage"+percentage+"      Time remaining"+timeRemainingTillNextApparentSpeakerChange);
        }catch (Exception e){
            System.err.println("");
        }
        
    }
    
    
    public void accelerateTime(long milliseconds){
        this.startTime = startTime - (milliseconds);
    }
    
    
    
    public void run(){
        //if(2<5)return;
        while(2<5){
            
             try{Thread.sleep(500);
                              
                 //System.err.println("m");
                 long currTime = new Date().getTime();
                 long timeElapsedSinceStart = currTime-startTime;
               
                 long timeOfNextChange = stageStartTime[stageIDX+1];
                 //System.out.println("Time remaining till next intervention "+(timeOfNextChange-timeElapsedSinceStart));
                 drawJProgressBars();
                 long timeRemainingTillNextIntervention = timeOfNextChange-timeElapsedSinceStart;
                 cC.calculateWhetherToDoRandomSpoofMessage(); 
                 if(timeRemainingTillNextIntervention<=0) nextIntervention();
                 
                 
             
             }
             catch (Exception e){
                 System.err.println();
                 e.printStackTrace();
                 //System.exit(-5);
             }
        }
    }
    
    
  
            
    
    
    
    
    
    
    
    public void display(){
        
        System.out.println("Displaying the SDMTIME");
         System.out.print("{ ");
         for(int i=0;i<stage.length;i++){
            System.out.print(stage[i]+",");
            
        }
        System.out.println(" }");
        
        for(int i=0;i<stageStartTime.length;i++){
            System.out.println(stageStartTime[i]);
            
        }
        System.out.println("DURATIONS");//DONT THINK THIS IS USED AT ALL...SO HAVE REMOVED IT!
       // for(int i=0;i<durationsOfContiguousApparentOrigins.length;i++){
         //   System.out.println(durationsOfContiguousApparentOrigins[i]);
            
        //}
        System.out.println("END OF");
        for(int i=0;i<endOfContiguousApparentOrigins.length;i++){
            System.out.println(endOfContiguousApparentOrigins[i]);
            
        }
         System.out.println("STARTS OF");
        for(int i=0;i<startOfContiguousApparentOrigins.length;i++){
            System.out.println(startOfContiguousApparentOrigins[i]);
            
        }
    }
    
    
    public int getDurationOfContinuousApparentOriginIllusion(int stageCurrent){ 
        for(int i = stageCurrent+1;i<5;i++){
            if(stage[i].equalsIgnoreCase("diff"))return (i-stageCurrent);       
            if(stage[i].equalsIgnoreCase("stop"))return 0;
        }
        return 5-stageCurrent;
    }
    
    public long getEndOfContinuousApparentOriginIllusion(int stageCurrent){ 
        for(int i = stageCurrent+1;i<5;i++){
            if(stage[i].equalsIgnoreCase("diff"))return this.stageStartTime[i];
            if(stage[i].equalsIgnoreCase("stop"))return this.stageStartTime[i];
            
        }
        if(this.addConditionTimetoLastCondition)return this.stageStartTime[4]+condnTime+condnTime;
        return this.stageStartTime[4]+condnTime;
    }
    public long calculateStartsOfContinuousApparentOriginIllusion(){ 
        startOfContiguousApparentOrigins[0] =0;     
       
        for(int i = 1 ;i < startOfContiguousApparentOrigins.length;i++){
                                  
            if(stage[i].equalsIgnoreCase("diff")|stage[i].equalsIgnoreCase("stop")){
                startOfContiguousApparentOrigins[i]=stageStartTime[i];
            }
            else{
                startOfContiguousApparentOrigins[i]=startOfContiguousApparentOrigins[i-1];
            }
            
        }
        return this.stageStartTime[4]+condnTime;
    }
    
    
    
   
    
}
