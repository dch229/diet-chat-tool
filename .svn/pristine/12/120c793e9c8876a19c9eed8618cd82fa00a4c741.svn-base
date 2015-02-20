/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.ConversationController;

import diet.client.WebpageAndImageDisplay;
import diet.message.MessageChatTextFromClient;
import diet.message.MessageKeypressed;
import diet.message.MessagePopupResponseFromClient;
import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.parameters.ExperimentSettings;
import diet.server.Conversation;
import diet.server.ConversationController.ui.InterfaceForJCountDown;
import diet.server.ConversationController.ui.JCountDown;
import diet.server.Participant;
import java.awt.Color;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import diet.server.ConversationController.ui.CustomDialog;
import diet.utils.VectorToolkit;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JOptionPane;

/**
 *
 * @author sre
 */
public class CCPICTURESTIMUL_FACES_10  extends DefaultConversationController implements InterfaceForJCountDown{

    
    String serverANDFolder = "http://homepages.inf.ed.ac.uk/gmills/stimuliset9";
    
   
    
    public String redbackground = "<html><head><style type=\"text/css\">body {color: white; background: red;}div { font-size: 200%;}</style></head><body><div>INCORRECT </div></body></html>";
    
    WebpageAndImageDisplay wid = new WebpageAndImageDisplay(null);
  
    Score sc = new Score();
    
    //String sUSERDIR = System.getProperty("user.dir")+File.separatorChar+"webserver"+File.separatorChar+sbfolder;
    //File f = new File(sUSERDIR);
    
    //File[] flist = f.listFiles();

    String[] slist = this.loadSettingsFile();
    
    public static int mode =0;
    
    public static int mode0START =0;
    public static int mode1TESTPHASE_DIRECTOR = 1;
    public static int mode2TESTPHASE_POSTDIRECTOR_PREMATCHER = 2;
    public static int mode3TESTPHASE_MATCHER =3;
    public static int mode4POSTTESTPHASE =4;
    public static int mode5TRAININGPHASE = 5;
    public static int mode6TRAININGPHASECOMPLETE = 6;
    public static int mode7TESTPHASE_DIRECTOR = 7;
    public static int mode8TESTPHASE_POSTDIRECTOR_PREMATCHER = 8;
    public static int mode9TESTPHASE_MATCHER = 9;
    

     int firstteststartindex =1;
     int firsttestlength =  12;   //SHOULD BE 12
     
     int secondteststartindex = 50;
     int secondtestlength = 10;   //SHOULD BE 10
     
     
     
     int  startofnormaltrials = 100;
     
     public long lengthOfEachDirectorTrial = 25000;//27000;
     public long lengthOfEachMatcherTrial = 25000;//27000;
     public long pauseBetweenSetsDirectorTrial = 1000;
     public long pauseBetweenSetsMatcherTrial = 1000;

     
     int popupReceiptcount = 0;    
     //int numberOfTrainingTrials = 2;      //IT WAS 80
     int maxPointsbeforeFinalTest = 200;
    
    
    
    long timeToDisplayStimulus = -9;//20000;//5000;
    boolean freezeJProgressBARDuringPRESENTATION = true;
    
    long lengthOfTrainingPhase = 35*   60*1000;
    
    
    JCountDown jc;
   
     boolean usePopupsOnClients = true;

     
     
    public void performUIJProgressBarDisplayOnClient(int percentage, String text) {
        
    }
    
    
     
     
    public void performUITriggeredEvent(String eventName, int value){
        if(this.mode!=this.mode5TRAININGPHASE)return;
        this.initiateSecondTestPhaseDEPRECATED();
    }
    
    
    
    @Override
    public boolean requestParticipantJoinConversation(String participantID) {
        System.err.println("CHECKING PARTICIPANTID AGAIN");
        if(participantID.startsWith("-"))return false;
        return true;
    }

    Participant matcher;
    Participant director;

    String directorFile;
    String matcherFile;

    int matchercorrectFaceNumber ;
    int directorcorrectFaceNumber;

    String fileName;

    String imgsrcDHTML ="";
    String imgsrcMHTML = "";

    int currSetMatcher;
    int currSetDirector;

    int numberOfIncorrectGuessesForCurrentSet =0;
    public int maxNumberOfIncorrectGuesses = 1;

    public int numberOfConsecutiveCorrect =0;
    //public int numberOfCorrectguesses=0;

   
    
    public synchronized String gotoSet(int srch, boolean alternateDM){
         if(alternateDM){
            Participant temp = this.director;
            this.director = this.matcher;
            matcher = temp;
        }


        String num =""+srch;
        if(srch<10){
            num="00" + num;
        }
        else if(srch<100){
            num="0" + num;
        }
        
        
        boolean foundmatcher = false;
        boolean founddirector = false;

        int searchindex = 0;
        String matcherSelection ="";
        String directorSelection = "";
        while((!foundmatcher|!founddirector)&searchindex<slist.length){
                    String fs = slist[searchindex];
                    //System.out.println("--"+f.getName());
                    System.err.println("FILENAMEIS"+fs);
                    System.err.println("LOoKIING FOR----d+"+num+"_");
                    if(fs.startsWith("m"+num+"_")){
                       //System.exit(-22);
                        foundmatcher = true;
                        matcherFile = fs;
                        matcherSelection = fs.substring(fs.length()-6,fs.length()-4);
                    }
                    if(fs.startsWith("d"+num+"_")){
                        founddirector = true;
                        directorFile = fs;
                        directorSelection = fs.substring(fs.length()-6,fs.length()-4);
                    }
                    searchindex++;
        }

       
        try{
        this.directorcorrectFaceNumber=Integer.parseInt(directorSelection);
        this.matchercorrectFaceNumber=Integer.parseInt(matcherSelection);

        //System.err.println("matchernumber"+matcherNumber+"-------DirectorNumber:"+directorNumber);System.exit(-5);
        }catch(Exception e){
            System.err.println("ERROR PARSING"+directorSelection+"-"+matcherSelection);
            System.exit(-2223);
            e.printStackTrace();
        }
        imgsrcDHTML = "<html><img src='"+this.serverANDFolder+"/"+directorFile+ "'></img>";
        imgsrcMHTML = "<html><img src='"+this.serverANDFolder+"/"+matcherFile+ "'></img>";



        // urlD = this.redbackground;

        try{
            if(matcher ==null)System.exit(-1231235);
            
            c.changeWebpage(matcher, "ID1", imgsrcMHTML,"","");
            c.changeWebpage(director, "ID1",imgsrcDHTML,"","",timeToDisplayStimulus);
            wid.changeWebpage(matcher.getUsername(), "<html><img src='"+this.serverANDFolder+"/"+matcherFile + "'></img>", "", "");
            wid.changeWebpage(director.getUsername(), "<html><img src='"+this.serverANDFolder+"/"+directorFile+ "'></img>", "", "",timeToDisplayStimulus);
          
            
            
            
           

           //String[] options = {"Face 1","Face 2","Face 3","Face 4","Face 5","Face 6","face 7","Face 8","Face 9","Face 10","Face 11", "Face 12"};
           //if(this.usePopupsOnClients)c.showPopupOnClientQueryInfo("training_"+matcherFile,matcher, "Press which face you think it is", options, "", -1); 
            
            //c.changeWebpage(matcher, num, num);
            
           
            
            //c.changeWebpageTextAndColour(matcher, "ID1","correct", "red", "white");
            numberOfIncorrectGuessesForCurrentSet =0;
        }

        catch(Exception e){
            e.printStackTrace();
            System.err.println("ERRORINMETHOD");
        }
        currSetMatcher = srch;
        currSetDirector = srch;
        //c.saveDataToConversationHistory("DATA:"+srch+"-"+directorFile.getName()+matcherFile.getName());
        c.saveDataToFile("DATA", "server", "server", new Date().getTime(),new Date().getTime(), new Date().getTime(), director.getUsername()+":"+directorFile+"----"+matcher.getUsername()+"----"+matcherFile, null);
        
        return "";
    }


    //int director1Number;
    //int director2Number;

    //String testIMGSRCD1;
    //String testIMGSRCD2;
    
    
     //File director1File;
     //File  director2File;
    
    //Participant director1;
    //Participant director2;
    
    
    
    
    
    
    
    



    public synchronized void processSelection(Participant p,String t){
        if(p!=matcher){
            c.sendArtificialTurnToRecipient(p, "****You shouldn't be making this selection - your partner needs to make the selection*****", 0);
            return;
        }

        try{
             t = t.replaceAll(" " , "");
             int i = Integer.parseInt(t.substring(1, t.length()));
             if(i == this.matchercorrectFaceNumber){
                   // String remainingTrials = "You have "+this.numberOfTrainingTrials - (this.)
                     sc.updateSuccess();
                     
                     //c.changeWebpageTextAndColour(matcher, "ID1","Correct.\n You have made "+this.numberOfConsecutiveCorrect+ " consecutive correct guesses", "green", "white");
                     //c.changeWebpageTextAndColour(director, "ID1","Correct.\nYou have made "+this.numberOfConsecutiveCorrect+ " consecutive correct guesses", "green", "white");
                     //c.sendArtificialTurnToRecipient(director, "****Correct. You have made "+this.numberOfConsecutiveCorrect+ " consecutive guesses****", 0);
                     //c.sendArtificialTurnToRecipient(matcher,  "****Correct. You have made "+this.numberOfConsecutiveCorrect+ " consecutive guesses****", 0);
                     
                     c.changeWebpageTextAndColour(matcher, "ID1","Correct.\n Your score is "+sc.getScore(), "green", "white");
                     c.changeWebpageTextAndColour(director, "ID1","Correct.\n Your score is "+sc.getScore(), "green", "white");
                     c.sendArtificialTurnToRecipient(director, "Correct. Your score is "+sc.getScore(), 0);
                     c.sendArtificialTurnToRecipient(matcher,  "Correct. Your score is "+sc.getScore(), 0);
                     
                     
                     
                     Conversation.printWSln("Main", p.getUsername()+" made the CORRECT selection of "+i+"...moving to trial"+this.currSetMatcher+"--"+this.currSetMatcher+"---CONSECUTIVECORRECT:"+this.numberOfConsecutiveCorrect+"---INCORRECTGUESSESFORCURRENTSET"+this.numberOfIncorrectGuessesForCurrentSet+ "Score is: "+sc.getScore());
                     Conversation.printWSln("Main", matcher.getUsername()+"---"+director.getUsername());
                     Thread.sleep(5000);
                     c.changeWebpageTextAndColour(matcher, "ID1","", "black", "black");
                     c.changeWebpageTextAndColour(director, "ID1","", "black", "black");
                     if(this.numberOfIncorrectGuessesForCurrentSet==0)numberOfConsecutiveCorrect ++;
                     this.gotoSet(currSetDirector+1, true);
             }
             else{
                 sc.updateUnsuccessful();
                 numberOfConsecutiveCorrect=0;
                 if(this.numberOfIncorrectGuessesForCurrentSet>=this.maxNumberOfIncorrectGuesses){
                     c.changeWebpageTextAndColour(matcher, "ID1","Incorrect\nyou used all your guesses\n Your score is "+sc.getScore(), "red", "white");
                     c.changeWebpageTextAndColour(director, "ID1","Incorrect\nyou used all your guesses\n Your score is "+sc.getScore(), "red", "white");
                     c.sendArtificialTurnToRecipient(director, "****Incorrect. You used all your guesses**** Your score is "+sc.getScore(), 0);
                     c.sendArtificialTurnToRecipient(matcher, "****Incorrect. You used all your guesses**** Your score is "+sc.getScore(), 0);
                     Conversation.printWSln("Main", p.getUsername()+" made INCORRECT selection of "+i+"...EXHAUSTED ALL THE GUESSES...MOVING TO TRIAL:"+this.currSetDirector);

                     Thread.sleep(2000);
                     c.changeWebpageTextAndColour(matcher, "ID1","", "black", "black");
                     c.changeWebpageTextAndColour(director, "ID1","", "black", "black");
                     this.gotoSet(currSetDirector+1, true);
                 }
                 else{
                    Conversation.printWSln("Main", p.getUsername()+ "made INCORRECT selection of"+i+"..still has "+(this.maxNumberOfIncorrectGuesses-this.numberOfIncorrectGuessesForCurrentSet)+"..guesses left on this trial."+"Your score is "+sc.getScore());
                    
                    c.sendArtificialTurnToRecipient(director, "****INCORRECT SELECTION...You have "+ (maxNumberOfIncorrectGuesses-numberOfIncorrectGuessesForCurrentSet)     +" guesses left*****"+"Your score is "+sc.getScore(), 0);
                    c.sendArtificialTurnToRecipient(matcher, "****INCORRECT SELECTION...You have "+ (maxNumberOfIncorrectGuesses-numberOfIncorrectGuessesForCurrentSet)     +" guesses left*****"+"Your score is "+sc.getScore(), 0);
                    numberOfIncorrectGuessesForCurrentSet++;
                    //String[] options = {"Face 1","Face 2","Face 3","Face 4","Face 5","Face 6","face 7","Face 8","Face 9","Face 10","Face 11", "Face 12"};
                    //if(this.usePopupsOnClients)c.showPopupOnClientQueryInfo("training_"+matcherFile,matcher, "Press which face you think it is", options, "", -1);
                 }

                   
             }

         }catch (Exception e){
             c.sendArtificialTurnToRecipient(p, "****YOU MADE A TYPO - TYPE BACKSLASH FOLLOWED BY THE NUMBER *****", 0);
         }
    }

   

    @Override
    public void initialize(Conversation c, ExperimentSettings expSettings) {
        super.initialize(c, expSettings);
       // Arrays.sort(flist);
        //JOptionPane.showMessageDialog(null, "EXPERIMENT IS FINISHED");
        getSetupParametersFromExperimenter();
  
    }

    
    
    
    boolean doErrorcheckingInTestPhase = false;
    

    public String[]  loadSettingsFile(){
        
         try{   
            String sDIR = System.getProperty("user.dir");
            File fname = new File(sDIR+File.separator+"experimentresources"+File.separator+"facecomms"+File.separator+"serverandfolder.txt");
            BufferedReader br = new BufferedReader(new FileReader(fname));
            
            String line = br.readLine();
            this.serverANDFolder=line;
         }catch (Exception e){
              e.printStackTrace();
              Conversation.printWSlnLog("Main", "Error loading file ");
              Conversation.saveErr(e);
         }
         
         this.serverANDFolder = CustomDialog.getString("What is the URL of the server and folder where the images are stored?\n"
                                                     + "You can change this default value in the file called:\n"
                                                     + "./experimentresources/facecomms/serverandfolder.txt\n\n", serverANDFolder);
        
        
         
         try{   
            String sDIR = System.getProperty("user.dir");
            File fname = new File(sDIR+File.separator+"experimentresources"+File.separator+"facecomms"+File.separator+"serverfiles.txt");
            BufferedReader br = new BufferedReader(new FileReader(fname));
            
            String line = br.readLine();
            
         }catch (Exception e){
              e.printStackTrace();
              Conversation.printWSlnLog("Main", "Error loading file ");
              Conversation.saveErr(e);
         }
         
         
         
         
         CustomDialog.showDialog("The next menu will ask you to choose the file that contains the list\n"
                 + "of all the names of the images that are in the directory\n"
                 + "The default is 'serverfiles.txt'\n"
                 + ""); 
           String sDIR = System.getProperty("user.dir");
            
         File fname = new File(sDIR+File.separator+"experimentresources"+File.separator+"facecomms"+File.separator+"serverfiles.txt");
           
         File inputFile = CustomDialog.loadFile(sDIR,"Please choose the file that contains the names" ,fname);
        
         Vector allLines = new Vector();
         try{
             BufferedReader in = new BufferedReader(new FileReader(inputFile));
             String line = in.readLine();
             
             while(line!=null){
                 allLines.addElement(line);
                 line = in.readLine();
                 System.err.println("-------"+line+"\n");
             }
             
         }catch (Exception e){
             e.printStackTrace();
         }
         
         
         String[] asList = VectorToolkit.convertVectorToArrayOfStrings(allLines);
         Arrays.sort(asList);
         //System.err.println(asList[0]);System.exit(-32423);
         return asList;
    }
    
    
    
    
    public void getSetupParametersFromExperimenter(){
    /* String[] options = {"(1) Select faces with buttons","(2) Select with text, (e.g. /1). Perform errorchecking ","(3) No buttons or errorchecking (THIS IS THE PREVIOUS SETUP)"};   
        
     String pOption  = CustomDialog.show2OptionDialog(options, "There are 3 options\n"
             + "(1) In both test phases participants select the face with a buttons press\n"
             + "(2) In both test phases participants select with text. There is errorchecking that tells them to select a number\n"
             + "(3) In both test phases participants select with text. There is no errorchecking (THIS IS THE PREVIOUS SETUP WE USED)", "");
     if(pOption==options[0]){
         this.usePopupsOnClients=true;
     }
     else {
          this.usePopupsOnClients=false;
     }
      if(pOption==options[1]){
         this.doErrorcheckingInTestPhase=true;
     }*/
     this.usePopupsOnClients=false;
     this.doErrorcheckingInTestPhase=true;
        
     String textPromptPretrainingPOPUP ="";
     try{   
       String sDIR = System.getProperty("user.dir");
       File fname = new File(sDIR+File.separator+"experimentresources"+File.separator+"facecomms"+File.separator+"pretrainingmessagepopup.txt");
       BufferedReader br = new BufferedReader(new FileReader(fname));
       String line = br.readLine();
      
       while (line != null) {
                   textPromptPretrainingPOPUP=textPromptPretrainingPOPUP+line+"\n";
                   line = br.readLine();
              }
              
              br.close();  
     }catch (Exception e){
         e.printStackTrace();
         CustomDialog.showDialog("ERROR LOADING FILE: "+e.getMessage());
         System.exit(-125);
     } 
     CustomDialog.showDialog("The message that will be displayed to the participants as a popup before the training phase is below the line:\n"
                            + "______________________________________________________________________________________________________\n\n"+textPromptPretrainingPOPUP+"\n"
                            + "______________________________________________________________________________________________________\nIf you want to change this, please close the chat tool and then edit  ./experimentresources/facecomms/pretrainingmessagepopup.txt");
     
     
     
     
     this.message3AfterTestPhasePopup=textPromptPretrainingPOPUP;
     //this.message3AfterTestPhaseWindow=textPromptPretrainingWINDOW;
     
     
      
      
      firsttestlength =  CustomDialog.getInteger("How many trials in first test phase? (Please don't select more than 19)", firsttestlength);
      secondtestlength = CustomDialog.getInteger("How many trials in second test phase? (Please don't select more than 19)", secondtestlength);
      lengthOfEachDirectorTrial =  CustomDialog.getLong("What is the length (in milliseconds) of each Director test trial?", lengthOfEachDirectorTrial);
      lengthOfEachMatcherTrial =  CustomDialog.getLong("What is the length (in milliseconds) of each Matcher test trial?", lengthOfEachMatcherTrial);
    
      long lengthOfTrainingPhaseInMinutes = (this.lengthOfTrainingPhase / 60) / 1000;
      lengthOfTrainingPhaseInMinutes = CustomDialog.getLong("What is the length (in minutes) of the training phase?", lengthOfTrainingPhaseInMinutes);
      
      lengthOfTrainingPhase = lengthOfTrainingPhaseInMinutes* 60 * 1000;
      
      maxPointsbeforeFinalTest = CustomDialog.getInteger("What is the point threshold for automatically jumping to the second test phase?", maxPointsbeforeFinalTest);
      
      
      jc = new JCountDown(this,"This timer will start during the training phase",lengthOfTrainingPhase);

     //public long pauseBetweenSetsDirectorTrial = 1000;
     //public long pauseBetweenSetsMatcherTrial = 1000;
    }
    
    
    

    @Override
    public void processKeyPress(Participant sender, MessageKeypressed mkp) {
         if(this.mode==mode5TRAININGPHASE)super.processKeyPress(sender,mkp);
         //c.saveKeypressToFile(sender, mkp);
    }

    
    @Override
    public void processPopupResponse(final Participant origin, MessagePopupResponseFromClient mpr) {
           if(mpr.getPopupID().startsWith("testset")){
                //c.saveDataToFile(fileName, server, server, lengthOfTrainingPhase, timeToDisplayStimulus, lengthOfTrainingPhase, server, null);    
                long formulationTime = -99999;
                formulationTime = mpr.timeOfChoice-mpr.timeOnClientOfDisplay;
                long timeOfDisplayButOnServer = mpr.getTimeOnServerOfReceipt().getTime()-formulationTime;
                String text = mpr.getTitle()+"_"+mpr.getQuestion()+"_"+mpr.getSelection()+"_"+mpr.getSelectedValue();
                c.saveDataToFile(mpr.getPopupID(), origin.getParticipantID(), origin.getUsername(), mpr.getTimeOnClientOfCreation().getTime(), timeOfDisplayButOnServer, mpr.getTimeOnServerOfReceipt().getTime(), text, null);
                c.sendArtificialTurnToRecipient(origin, "\n\n\nThanks. Please wait for the next set of faces (In this stage of the experiment you don't get feedback about whether you guessed correctly", 0);
                return;
           }
           else if(mpr.getPopupID().startsWith("training")){
                //c.saveDataToFile(fileName, server, server, lengthOfTrainingPhase, timeToDisplayStimulus, lengthOfTrainingPhase, server, null);    
                long formulationTime = -99999;
                formulationTime = mpr.timeOfChoice-mpr.timeOnClientOfDisplay;
                long timeOfDisplayButOnServer = mpr.getTimeOnServerOfReceipt().getTime()-formulationTime;
                String text = mpr.getTitle()+"_"+mpr.getQuestion()+"_"+mpr.getSelection()+"_"+mpr.getSelectedValue();
                c.saveDataToFile(mpr.getPopupID(), origin.getParticipantID(), origin.getUsername(), mpr.getTimeOnClientOfCreation().getTime(), timeOfDisplayButOnServer, mpr.getTimeOnServerOfReceipt().getTime(), text, null);
                //c.sendArtificialTurnToRecipient(origin, "Thanks for selecting the face. Please wait for the next set of .", 0);
                this.processSelection(origin, "/"+(1+mpr.getSelection()));
                return;
           }
        
        
           this.popupReceiptcount++;
           if(this.popupReceiptcount==2 & this.mode==mode0START){
               popupReceiptcount=0;
               this.initiateFirstTestPhaseDirector(this.firstteststartindex, this.firsttestlength);
           }
           else if(this.popupReceiptcount==2 & this.mode==this.mode2TESTPHASE_POSTDIRECTOR_PREMATCHER){
               popupReceiptcount=0;
               this.initiateFirstTestPhaseMatcher(this.firstteststartindex, this.firsttestlength, startofnormaltrials);
           }
           else if(this.popupReceiptcount==2 & this.mode==this.mode4POSTTESTPHASE){
               popupReceiptcount=0;
               this.initiateTrainingPhase(startofnormaltrials);
               
           }
           else if(this.popupReceiptcount==2 & this.mode==this.mode6TRAININGPHASECOMPLETE){
               popupReceiptcount=0;
               this.initiateSecondTestPhaseDirector(this.secondteststartindex, this.secondtestlength);
           }
           else if(this.popupReceiptcount==2 & this.mode==this.mode8TESTPHASE_POSTDIRECTOR_PREMATCHER){
               popupReceiptcount=0;
               this.initiateSecondTestPhaseMatcher(this.secondteststartindex, this.secondtestlength, startofnormaltrials);
           }
          
           
           
    }
    
     
    

    
    public void showTestPopupsOnClient(String popupMessage){
        this.popupReceiptcount=0;
        c.showPopupOnClientQueryInfo("",director, popupMessage, new String[]{"START"}, "Test trial", 0);
        c.showPopupOnClientQueryInfo("",matcher, popupMessage, new String[]{"START"}, "Test trial", 0);   
    }

    
    
    
    public synchronized String gotoSet2D(int srch){
         /////
    /*     Vector v = new Vector();
         v.addElement(directorFile.getName());
         c.saveDataToFile("DESCRIPTION", director.getParticipantID(), director.getUsername(), ""+currSetDirector, new Date().getTime(), new Date().getTime(), "", v);
         v = new Vector();
         v.addElement(matcherFile.getName());
         c.saveDataToFile("DESCRIPTION", matcher.getParticipantID(), matcher.getUsername(), ""+currSetMatcher, new Date().getTime(), new Date().getTime(), "", v);
         ////
      */  
         currSetDirector = srch;
         currSetMatcher= 20+srch;
        
        
        String numD =""+currSetDirector;
        if(currSetDirector<10){
            numD="00" + numD;
        }
        else if(currSetDirector<100){
            numD="0" + numD;
        }
        
        String numM =""+currSetMatcher;
        if(currSetMatcher<10){
            numM="00" + numM;
        }
        else if(currSetMatcher<100){
            numM="0" + numM;
        }
        
        
        
        
        
     
       
         
        
        
        
        boolean foundmatcher = false;
        boolean founddirector = false;

        int searchindex = 0;
        String matcherSelection ="";
        String directorSelection = "";
        while((!foundmatcher|!founddirector)&searchindex<slist.length){
                    String fs = slist[searchindex];
                    if(fs.startsWith("d"+numM+"_")){
                       //System.exit(-22);
                        foundmatcher = true;
                        matcherFile = fs;
                        matcherSelection = fs.substring(fs.length()-6,fs.length()-4);
                    }
                    if(fs.startsWith("d"+numD+"_")){
                        founddirector = true;
                        directorFile = fs;
                        directorSelection = fs.substring(fs.length()-6,fs.length()-4);
                    }
                    searchindex++;
        }

       
        try{
        this.directorcorrectFaceNumber=Integer.parseInt(directorSelection);
        this.matchercorrectFaceNumber=Integer.parseInt(matcherSelection);

        //System.err.println("matchernumber"+matcherNumber+"-------DirectorNumber:"+directorNumber);System.exit(-5);
        }catch(Exception e){
            System.err.println("ERROR PARSING"+directorSelection+"-"+matcherSelection);
            System.exit(-223);
        }
        imgsrcDHTML = "<html><img src='"+this.serverANDFolder+"/"+directorFile+ "'></img>";
        imgsrcMHTML = "<html><img src='"+this.serverANDFolder+"/"+matcherFile+ "'></img>";




        // urlD = this.redbackground;

        try{
            if(matcher ==null)System.exit(-1231235);
          

 
            c.changeWebpage(matcher, "ID1", imgsrcMHTML,"","",timeToDisplayStimulus);
            c.changeWebpage(director, "ID1",imgsrcDHTML,"","",timeToDisplayStimulus);
            wid.changeWebpage(matcher.getUsername(), "<html><img src='"+this.serverANDFolder+"/"+matcherFile + "'></img>", "", "",timeToDisplayStimulus);
            wid.changeWebpage(director.getUsername(), "<html><img src='"+this.serverANDFolder+"/"+directorFile+ "'></img>", "", "",timeToDisplayStimulus);
            //c.changeWebpageTextAndColour(matcher, "ID1","correct", "red", "white");
            numberOfIncorrectGuessesForCurrentSet =0;
        }

        catch(Exception e){
            e.printStackTrace();
            System.err.println("ERRORINMETHOD");
        }
       
        //c.saveDataToConversationHistory("DATA(2D):"+srch+"-"+directorFile.getName()+"--"+matcherFile.getName());
        c.saveDataToFile("DATA(2D)", "server", "server",new Date().getTime(), new Date().getTime(), new Date().getTime(), director.getUsername()+":"+directorFile+"----"+matcher.getUsername()+"----"+matcherFile, null);
        
        return "";
    }


    public synchronized String gotoSet2M(int srch){
         currSetDirector = 20+srch;
         currSetMatcher=   srch;
        
        
        
        String numD =""+currSetDirector;
        if(currSetDirector<10){
            numD="00" + numD;
        }
        else if(currSetDirector<100){
            numD="0" + numD;
        }
        
        String numM =""+currSetMatcher;
        if(currSetMatcher<10){
            numM="00" + numM;
        }
        else if(currSetMatcher<100){
            numM="0" + numM;
        }
         
        
        
        
        
        
        boolean foundmatcher = false;
        boolean founddirector = false;

        int searchindex = 0;
        String matcherSelection ="";
        String directorSelection = "";
        while((!foundmatcher|!founddirector)&searchindex<slist.length){
                    String fs = slist[searchindex];
                    if(fs.startsWith("m"+numM+"_")){
                        //System.exit(-22);
                        foundmatcher = true;
                        matcherFile = fs;
                        matcherSelection = fs.substring(fs.length()-6,fs.length()-4);
                    }
                    if(fs.startsWith("m"+numD+"_")){
                        founddirector = true;
                        directorFile = fs;
                        directorSelection = fs.substring(fs.length()-6,fs.length()-4);
                    }
                    searchindex++;
        }

       //System.exit(-9);
        try{
       // this.matcherNumber=Integer.parseInt(matcherSelection);
        this.directorcorrectFaceNumber=Integer.parseInt(directorSelection);
        this.matchercorrectFaceNumber=Integer.parseInt(matcherSelection);

        //System.err.println("matchernumber"+matcherNumber+"-------DirectorNumber:"+directorNumber);System.exit(-5);
        }catch(Exception e){
            System.err.println("ERROR PARSING"+directorSelection+"-"+matcherSelection+"/---"+numD+"---"+numM);
            e.printStackTrace();
           // System.exit(-223);
            
        }
        imgsrcDHTML = "<html><img src='"+this.serverANDFolder+"/"+directorFile+ "'></img>";
        imgsrcMHTML = "<html><img src='"+this.serverANDFolder+"/"+matcherFile+ "'></img>";




        // urlD = this.redbackground;

        try{
            if(matcher ==null)System.exit(-1231235);
            
            //c.changeWebpageImage_OnServer_DEPRECATED(matcher, "ID1", imgsrcMHTML);
            //c.changeWebpageImage_OnServer_DEPRECATED(director, "ID1", imgsrcDHTML);

            
            c.changeWebpage(matcher, "ID1", imgsrcMHTML,"","");
            c.changeWebpage(director, "ID1",imgsrcDHTML,"","");
            wid.changeWebpage(matcher.getUsername(), "<html><img src='"+this.serverANDFolder+"/"+matcherFile + "'></img>", "", "");
            wid.changeWebpage(director.getUsername(), "<html><img src='"+this.serverANDFolder+"/"+directorFile+ "'></img>", "", "");
            
            
            
            
          
            
            
            
            //c.changeWebpageTextAndColour(matcher, "ID1","correct", "red", "white");
            numberOfIncorrectGuessesForCurrentSet =0;
        }

        catch(Exception e){
            e.printStackTrace();
            System.err.println("ERRORINMETHOD");
        }
        
       // c.saveDataToConversationHistory("DATA(2M):"+srch+"-"+directorFile.getName()+"--"+matcherFile.getName());
       // c.saveDataToConversationHistory("DATA(2M):"+srch+"-"+director.getUsername()+"--"+directorFile.getName()+"--"+matcher.getUsername()+"---"+matcherFile.getName());
        c.saveDataToFile("DATA(2D)", "server", "server",new Date().getTime(), new Date().getTime(), new Date().getTime(), director.getUsername()+":"+directorFile+"----"+matcher.getUsername()+"----"+matcherFile, null);
        
        return "";
    }

    
    //int numDnum ;
    //int numMnum ; 

    
    String subfolder = "";

    @Override
    public void participantJoinedConversation(Participant p) {    
       // super.participantJoinedConversation(p);
        //System.exit(-5);
           
        c.displayNEWWebpage(p, "ID1", "Faces", "", 1050, 688, false,false);
        c.changeJProgressBar(p, "ID1", "", Color.gray, 100);
        wid.displayNEWWebpage(p.getUsername(),  p.getUsername(), "", 1050, 688, false,false,false);
        wid.changeJProgressBar(p.getUsername(), p.getUsername(), Color.gray, 100);
       
        
         
        c.changeClientInterface_enableTextEntry(p);

        c.changeClientInterface_allowENTERSEND(p, true);
        
        
        
        if(c.getParticipants().getAllParticipants().size()<2){
             director = p;
             c.changeWebpage(p, "ID1", "", "Waiting for other participant to log in...please wait", "");
         }
        else if(c.getParticipants().getAllParticipants().size()==2){
            matcher=p;  
            //initiateTestPhaseDirector(1,3,20);
            showTestPopupsOnClient(message1BeforeTestSetDirector);
            
            //this.initiateTrainingPhase(21);
            //this.initiateSecondTestPhase();
        }
        

        
       


       
    }

    @Override
    public void participantRejoinedConversation(Participant p) {
        
    }


    
    @Override
    public void processLoop() {
        super.processLoop();
        if(mode==CCPICTURESTIMULI6WEB_BACKUP.mode5TRAININGPHASE)c.resetFromIsTypingtoNormalChatAllAllowedParticipants(super.getIsTypingTimeOut());
        
        //if(matcher!=null)c.sendArtificialTurnToRecipient(matcher, "MESSAGE", 0);
        //if(director!=null)c.sendArtificialTurnToRecipient(director, "MESSAGE", 0);
    }

    
    
    
    
    

    @Override
    public void processChatText(Participant sender, MessageChatTextFromClient mct) {
        
      if(this.doErrorcheckingInTestPhase){  
        if(mode ==CCPICTURESTIMULI6WEB_BACKUP.mode3TESTPHASE_MATCHER || mode ==CCPICTURESTIMULI6WEB_BACKUP.mode9TESTPHASE_MATCHER){
            if (mct.getText().contains("0")||mct.getText().contains("1")||mct.getText().contains("2")||mct.getText().contains("3")||mct.getText().contains("4")
                    ||mct.getText().contains("5")||mct.getText().contains("6")||mct.getText().contains("7")||mct.getText().contains("8")||mct.getText().contains("9")||
                    mct.getText().contains("10")){
                        c.changeClientInterface_clearMainWindows(sender);
                        c.sendArtificialTurnToRecipient(sender, "Thanks. Please wait for the next set of faces", 0);
                        c.sendLabelDisplayToParticipantInOwnStatusWindow(sender, " ", true);
                        c.changeClientInterface_disableTextEntry(sender);
                        
            }
            else{
                   c.sendArtificialTurnToRecipient(sender, "ERROR! You must enter a number between 1 and 12. Please try again!", 0);
            }
        }
      } 
        
        if(mode==CCPICTURESTIMULI6WEB_BACKUP.mode5TRAININGPHASE&&mct.getText().startsWith("/")){
           this.processSelection(sender, mct.getText());
           if(this.sc.score>=this.maxPointsbeforeFinalTest) this.initiateSecondTestPhaseDEPRECATED();
        }
        else if (mode==CCPICTURESTIMULI6WEB_BACKUP.mode5TRAININGPHASE){
             super.processChatText(sender, mct);
        }
        
        
        
    }


  
    
  
  
public synchronized void initiateFirstTestPhaseDirector(final int startindex, final int numberoftrials){
    
    Thread t = new Thread(){
        public void run(){
        mode=CCPICTURESTIMULI6WEB_BACKUP.mode1TESTPHASE_DIRECTOR;
        
        for(int i=startindex;i<startindex+numberoftrials;i++){
                  gotoSet2D(i);
                  singleDirectorTrial(i);
                  saveData();
                  try { Thread.sleep(pauseBetweenSetsDirectorTrial);} catch (Exception e){ e.printStackTrace();}   
        }
        mode=CCPICTURESTIMULI6WEB_BACKUP.mode2TESTPHASE_POSTDIRECTOR_PREMATCHER;
        showTestPopupsOnClient(message2AfterTestSetDirectorBeforeTestSetMatcher);
        c.changeWebpageTextAndColour(director, "ID1",message2AfterTestSetDirectorBeforeTestSetMatcherWINDOW,"black", "white");
        c.changeWebpageTextAndColour(matcher, "ID1",message2AfterTestSetDirectorBeforeTestSetMatcherWINDOW, "black", "white");
        //saveData();
        }};
         t.start();      
    }



public synchronized void initiateFirstTestPhaseMatcher(final int startindex, final int numberoftrials, final int returnindex){
        Thread t = new Thread(){
        public void run(){
        
        mode=CCPICTURESTIMULI6WEB_BACKUP.mode3TESTPHASE_MATCHER;

        for(int i=startindex;i<startindex+numberoftrials;i++){
                  singleMatcherTrial(i);
                  try { Thread.sleep(pauseBetweenSetsMatcherTrial);} catch (Exception e){ e.printStackTrace();}   
        }
        
        mode = CCPICTURESTIMULI6WEB_BACKUP.mode4POSTTESTPHASE;
        
       c.changeWebpageTextAndColour(director, "ID1",message3AfterTestPhaseWindow, "black", "white");
       c.changeWebpageTextAndColour(matcher, "ID1",message3AfterTestPhaseWindow, "black", "white");
       
       c.changeJProgressBar(director, "ID1", "", Color.gray, 0);
       c.changeJProgressBar(matcher, "ID1", "",Color.GRAY,0);
       
       showTestPopupsOnClient(message3AfterTestPhasePopup);
       
       c.changeClientInterface_clearMainWindows(director);
       c.changeClientInterface_clearMainWindows(matcher);
         
        
        
        }};
         t.start();      
    }


public synchronized void initiateSecondTestPhaseDEPRECATED(){
    this.showTestPopupsOnClient(this.message4BeforeSecondTestSetDirector);
    this.mode=this.mode6TRAININGPHASECOMPLETE;
     c.changeClientInterface_clearMainWindows(director);
     c.changeClientInterface_clearMainWindows(matcher);
}



public synchronized void initiateSecondTestPhaseDirector(final int startindex, final int numberoftrials){
    
    Thread t = new Thread(){
        public void run(){
        mode=CCPICTURESTIMULI6WEB_BACKUP.mode7TESTPHASE_DIRECTOR;
        
        for(int i=startindex;i<startindex+numberoftrials;i++){
                  gotoSet2D(i);
                  singleDirectorTrial(i);
                  saveData();
                  try { Thread.sleep(pauseBetweenSetsDirectorTrial);} catch (Exception e){ e.printStackTrace();}   
        }
        mode=CCPICTURESTIMULI6WEB_BACKUP.mode8TESTPHASE_POSTDIRECTOR_PREMATCHER;
        showTestPopupsOnClient(message5AfterSecondTestSetDirectorBeforeTestSetMatcher);
        c.changeWebpageTextAndColour(director, "ID1",message2AfterTestSetDirectorBeforeTestSetMatcherWINDOW,"black", "white");
        c.changeWebpageTextAndColour(matcher, "ID1",message2AfterTestSetDirectorBeforeTestSetMatcherWINDOW, "black", "white");
        //saveData();
        }};
         t.start();      
    }



public synchronized void initiateSecondTestPhaseMatcher(final int startindex, final int numberoftrials, final int returnindex){
        Thread t = new Thread(){
        public void run(){
        mode = CCPICTURESTIMULI6WEB_BACKUP.mode9TESTPHASE_MATCHER;
        for(int i=startindex;i<startindex+numberoftrials;i++){
                  singleMatcherTrial(i);
                  try { Thread.sleep(pauseBetweenSetsMatcherTrial);} catch (Exception e){ e.printStackTrace();}   
        }
        
       String postExperimentMessage = "Thank you! Experiment finished, please find the experimenter!";
        
        
       c.changeWebpageTextAndColour(director, "ID1",postExperimentMessage, "black", "white");
       c.changeWebpageTextAndColour(matcher, "ID1",postExperimentMessage, "black", "white");
       
       c.changeJProgressBar(director, "ID1", "", Color.gray, 0);
       c.changeJProgressBar(matcher, "ID1", "",Color.GRAY,0);
       
       showTestPopupsOnClient(postExperimentMessage);
       JOptionPane.showMessageDialog(null, "EXPERIMENT IS FINISHED");
        
      
        
        
    
        
        
        }};
         t.start();     
        
    }




  public synchronized void initiateTrainingPhase(final int trainingphasestart){
      this.mode=this.mode5TRAININGPHASE;
      Thread t = new Thread(){
        public void run(){
             c.sendAndEnableLabelDisplayToALLStatusWindowsOfParticipant(matcher, "Status: OK", false, true);
             
             gotoSet(trainingphasestart,true);
             c.changeClientInterface_allowENTERSEND(matcher, true);
             c.changeClientInterface_allowENTERSEND(director, true);
        
             c.changeClientInterface_enableTextEntry(matcher);
             c.changeClientInterface_enableTextEntry(director);
             jc.startCountdown();
  
        }};
        t.start();      
  }


public void saveWhoIsLookingAtWhat(){
    String directorIsLookingAt = "";
    String matcherIsLookingAt =  "";
    
    try{
    directorIsLookingAt = ""+this.director.getUsername()+": "+this.directorFile+"---"+this.directorcorrectFaceNumber;
    matcherIsLookingAt = ""+this.matcher.getUsername()+": "+this.matcherFile+"---"+this.matchercorrectFaceNumber;
    }
    catch(Exception e){
       
    }
    finally{
       c.saveDataToFile("WHOISLOOKINGAT", director.getUsername(), director.getUsername(),new Date().getTime(), new Date().getTime(), new Date().getTime(), "(D)"+ directorIsLookingAt, null);
       c.saveDataToFile("WHOISLOOKINGAT", matcher.getUsername(), matcher.getUsername(), new Date().getTime(),new Date().getTime(), new Date().getTime(), "(M)"+ matcherIsLookingAt, null);
    }
}


public synchronized void singleMatcherTrial(int startIndex){
    long startTime = new Date().getTime();
    gotoSet2M(startIndex); 
    //saveWhoIsLookingAtWhat();
    c.changeClientInterface_clearMainWindows(matcher);
    c.changeClientInterface_clearMainWindows(director);
    
    c.changeClientInterface_enableTextEntry(matcher);
    c.changeClientInterface_enableTextEntry(director);
    
    String stringForD = (String)rt.getDescription(matcher,currSetDirector);
    String stringForM = (String)rt.getDescription(director, currSetMatcher);
    
    String[] sMArray = stringForM.split("\\r\\n|\\r|\\n");
    String[] sDArray = stringForD.split("\\r\\n|\\r|\\n");
    
    String stringForDNoNewlines = stringForD.replaceAll("\\r\\n|\\r|\\n", "(NEWLINE)");
    String chOutPutForD =director.getUsername()+" is now looking at "+ this.directorFile+"..having to select "+this.directorcorrectFaceNumber+ " using this description:"+ stringForDNoNewlines.replaceAll("\\r\\n|\\r|\\n", "(NEWLINE)");
    c.saveDataToFile("WHOISLOOKINGAT(M)", "server", "server", new Date().getTime(),new Date().getTime(), new Date().getTime(), chOutPutForD, null);
    for(int i = 0; i < sDArray.length;i++){
        if(sDArray[i]!=null)c.sendArtificialTurnToRecipient(director,sDArray[i], 0);
    }
    
    
     String stringForMNoNewlines = stringForM.replaceAll("\\r\\n|\\r|\\n", "(NEWLINE)");
     String chOutPutForM = matcher.getUsername()+" is now looking at "+ this.matcherFile+"..having to select "+matchercorrectFaceNumber+ " using this description:"+ stringForMNoNewlines.replaceAll("\\r\\n|\\r|\\n", "(NEWLINE)");
     c.saveDataToFile("WHOISLOOKINGAT(M)", "server", "server", new Date().getTime(),new Date().getTime(), new Date().getTime(), chOutPutForM, null);
    
    for(int i=0;i<sMArray.length;i++){
        if(sMArray[i]!=null)c.sendArtificialTurnToRecipient(matcher, sMArray[i], 0);
    }
     
    
   
    
    
    System.err.println("sD: "+stringForD+" director: "+director.getUsername()+"..."+currSetDirector);
    System.err.println("sM: "+stringForM+" matcher: "+matcher.getUsername()+ "..."+currSetMatcher);
    //System.exit(-5);//.println("sM: "+sM);
    
    c.changeClientInterface_allowENTERSEND(director,true);
    c.changeClientInterface_allowENTERSEND(matcher,true);
        
     c.changeClientInterface_enableTextEntry(matcher);
     c.changeClientInterface_enableTextEntry(director);
    
    
    
    if(this.usePopupsOnClients){
       String[] options = {"Face 1","Face 2","Face 3","Face 4","Face 5","Face 6","face 7","Face 8","Face 9","Face 10","Face 11", "Face 12"};
       c.sendLabelDisplayToParticipantInOwnStatusWindow(matcher, "Please type the number of the face and then press ENTER", true);
       c.sendLabelDisplayToParticipantInOwnStatusWindow(director, "Please choose which face is being described", true);
       c.showPopupOnClientQueryInfo("testset_"+matcherFile+"_"+stringForMNoNewlines.replaceAll("\\r\\n|\\r|\\n", "(NEWLINE)") ,matcher, "Press which face you think it is", options, "", -1); 
       c.showPopupOnClientQueryInfo("testset_"+directorFile+"_"+stringForDNoNewlines.replaceAll("\\r\\n|\\r|\\n", "(NEWLINE)"),director, "Press which face you think it is", options, "", -1); 
    }
    else{
        c.sendLabelDisplayToParticipantInOwnStatusWindow(matcher,  "Please type the number of the face and then press ENTER", true);
        c.sendLabelDisplayToParticipantInOwnStatusWindow(director, "Please type the number of the face and then press ENTER", true);
    }
    
     
    //if(this.freezeJProgressBARDuringPRESENTATION)   startTime = startTime + this.timeToDisplayStimulus;
    
    while(new Date().getTime()-startTime < lengthOfEachMatcherTrial){
            long proportionOfTrialElapsed = 100*(new Date().getTime()-startTime)/this.lengthOfEachMatcherTrial;
            Color cColor = Color.GREEN;
            if(proportionOfTrialElapsed > 50) cColor=Color.ORANGE;
            if(proportionOfTrialElapsed > 80) cColor=Color.RED;
            long timeleftsecs = ((startTime + lengthOfEachMatcherTrial)-new Date().getTime())/1000;
            c.changeJProgressBar(director, "ID1", "You have "+(timeleftsecs)+" seconds remaining", cColor, 100-(int)proportionOfTrialElapsed);
            c.changeJProgressBar(matcher, "ID1", "You have "+(timeleftsecs)+" seconds remaining", cColor,  100-(int)proportionOfTrialElapsed);
             
            
            wid.changeJProgressBar(director.getUsername(), "You have "+(timeleftsecs)+" seconds remaining", cColor, 100-(int)proportionOfTrialElapsed);
            wid.changeJProgressBar(matcher.getUsername(), "You have "+(timeleftsecs)+" seconds remaining", cColor, 100-(int)proportionOfTrialElapsed);
            
            
            
             
             try { wait(500);} catch (Exception e){ e.printStackTrace();} 
        }
    
    
     try{
            c.changeClientInterface_disableTextEntry(matcher);
            c.changeClientInterface_disableTextEntry(director);
            c.sendLabelDisplayToParticipantInOwnStatusWindow(matcher, "Loading next set of faces...please wait", true);
            c.sendLabelDisplayToParticipantInOwnStatusWindow(director, "Loading next set of faces...please wait", true);
            
            
            c.changeClientInterface_clearTextEntryField(director);
            c.changeClientInterface_clearTextEntryField(matcher);
            c.changeWebpageTextAndColour(director, "ID1","Loading next set of faces...please wait", "black", "white");
            c.changeWebpageTextAndColour(matcher, "ID1","Loading next set of faces...please wait", "black", "white");
           
        }catch (Exception e){
           e.printStackTrace();
        }
    
    
    
    System.err.println("(A)SINGLEMATCHERTRIALLOOKINGFOR: "+stringForM+"------"+matcherFile+currSetMatcher);
    System.err.println("(B)SINGLEMATCHERTRIALLOOKINGFOR: "+stringForD+"------"+directorFile+currSetDirector);
    
    
    
}


public void saveData(){
    Vector v = c.getParticipants().getAllParticipants();
    for(int i=0;i<v.size();i++){
        Participant p = (Participant)v.elementAt(i);
        Hashtable participantsDescriptions = rt.getParticipantsDescriptions(p);
        Object[] o= participantsDescriptions.keySet().toArray();
        for(int j=0;j<o.length;j++){
            Integer isKEY = (Integer)o[j];
            String value = ((String)participantsDescriptions.get(isKEY)).replaceAll("\\r\\n|\\r|\\n", "[NEWLINE]");;
            if (value==null)value="(NULL)";
            System.err.println("VALUENINCOLLECTION"+i+":::::"+isKEY);
            c.saveDataToFile("DESCRIPTION", p.getUsername(), p.getUsername(), new Date().getTime(),new Date().getTime(), new Date().getTime(), isKEY+"---"+value, null);
        } 
    }
    
    
}

//long stimuluspresentationtime = 9000;

public synchronized void singleDirectorTrial(int startIndex){
       // saveWhoIsLookingAtWhat();
        
        long startTime = new Date().getTime();
        
         c.changeClientInterface_enableTextEntry(matcher);
         c.changeClientInterface_enableTextEntry(director);
        
        
        c.sendLabelDisplayToParticipantInOwnStatusWindow(matcher, "Please type your description of the face into the box below", true);
        c.sendLabelDisplayToParticipantInOwnStatusWindow(director, "Please type your description of the face into the box below", true);
        c.changeClientInterface_allowENTERSEND(director,false);
        c.changeClientInterface_allowENTERSEND(matcher,false);
        
        if(this.freezeJProgressBARDuringPRESENTATION)   startTime = startTime + this.timeToDisplayStimulus;
        
        while(new Date().getTime()-startTime < lengthOfEachDirectorTrial){
            long proportionOfTrialElapsed = 100*(new Date().getTime()-startTime)/this.lengthOfEachDirectorTrial;
            Color cColor = Color.GREEN;
            if(proportionOfTrialElapsed > 50) cColor=Color.ORANGE;
            if(proportionOfTrialElapsed > 80) cColor=Color.RED;
            long timeleftsecs = ((startTime + lengthOfEachDirectorTrial)-new Date().getTime())/1000;
            c.changeJProgressBar(director, "ID1", "You have "+(timeleftsecs)+" seconds remaining", cColor, 100-(int)proportionOfTrialElapsed);
            c.changeJProgressBar(matcher, "ID1", "You have "+(timeleftsecs)+" seconds remaining", cColor,  100-(int)proportionOfTrialElapsed);
            wid.changeJProgressBar(director.getUsername(), "You have "+(timeleftsecs)+" seconds remaining", cColor, 100-(int)proportionOfTrialElapsed);
            wid.changeJProgressBar(matcher.getUsername(), "You have "+(timeleftsecs)+" seconds remaining", cColor, 100-(int)proportionOfTrialElapsed);
            
            
            try { wait(500);} catch (Exception e){ e.printStackTrace();} 
        }
        try{
            c.changeClientInterface_disableTextEntry(matcher);
            c.changeClientInterface_disableTextEntry(director);
            c.sendLabelDisplayToParticipantInOwnStatusWindow(matcher, "Loading next set of faces...please wait", true);
            c.sendLabelDisplayToParticipantInOwnStatusWindow(director, "Loading next set of faces...please wait", true);
            
            
            c.changeClientInterface_clearTextEntryField(director);
            c.changeClientInterface_clearTextEntryField(matcher);
            c.changeWebpageTextAndColour(director, "ID1","Loading next set of faces...please wait", "black", "white");
            c.changeWebpageTextAndColour(matcher, "ID1","Loading next set of faces...please wait", "black", "white");
           
        }catch (Exception e){
           e.printStackTrace();
        }

             
             
}

    @Override
    public synchronized void processWYSIWYGTextInserted(Participant sender, MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp) {
        //super.processWYSIWYGTextInserted(sender, mWYSIWYGkp);
        System.err.println(mWYSIWYGkp.getTextTyped()+ "---"+mWYSIWYGkp.getAllTextInWindow());
        //if(this.mode==this.mode1TESTPHASE_DIRECTOR||this.mode==this.mode7TESTPHASE_DIRECTOR);
        
        if(sender==director && (this.mode==this.mode1TESTPHASE_DIRECTOR||this.mode==this.mode7TESTPHASE_DIRECTOR)){
            rt.saveDescription(director, currSetDirector, mWYSIWYGkp.getAllTextInWindow());
            String newLineRemoved = mWYSIWYGkp.getAllTextInWindow().replaceAll("\\r\\n|\\r|\\n", "(NEWLINE)");
            c.saveDataToFile("DIRECTORCRIPTIONMAKING", sender.getParticipantID(), sender.getUsername() ,mWYSIWYGkp.getTimeOnClientOfCreation().getTime(), new Date().getTime(), new Date().getTime(),newLineRemoved, null);       
      
        }
        else if(sender==matcher &&(this.mode==this.mode1TESTPHASE_DIRECTOR||this.mode==this.mode7TESTPHASE_DIRECTOR)){
            rt.saveDescription(matcher, currSetMatcher, mWYSIWYGkp.getAllTextInWindow());
            String newLineRemoved = mWYSIWYGkp.getAllTextInWindow().replaceAll("\\r\\n|\\r|\\n", "(NEWLINE)");
            c.saveDataToFile("DIRECTORCRIPTIONMAKING", sender.getParticipantID(), sender.getUsername() , mWYSIWYGkp.getTimeOnClientOfCreation().getTime(),new Date().getTime(), new Date().getTime(),newLineRemoved, null);       
        }
        else if (this.mode==this.mode3TESTPHASE_MATCHER || this.mode==this.mode9TESTPHASE_MATCHER){
             String newLineRemoved = mWYSIWYGkp.getAllTextInWindow().replaceAll("\\r\\n|\\r|\\n", "(NEWLINE)");
             c.saveDataToFile("MATCHERDESCRIPTIONRECEIVED", sender.getParticipantID(), sender.getUsername() ,mWYSIWYGkp.getTimeOnClientOfCreation().getTime(), new Date().getTime(), new Date().getTime(),newLineRemoved, null);            
        }
        else{
             String newLineRemoved = (mWYSIWYGkp.getAllTextInWindow().replaceAll("\n", "((NEWLINE))")).replaceAll("\\n", "(NEWLINE)").replaceAll("\\\n", "((NEWLINE))");
             c.saveClientDocumentchangeToFile (sender,mWYSIWYGkp.getTimeOnClientOfCreation().getTime() ,mWYSIWYGkp.getTimeOnServerOfReceipt().getTime(),newLineRemoved);
        }
        
        //c.saveDataToFile("", sbfolder, fileName, pauseBetweenSets, pauseBetweenSets, s, null);
        // c.saveKeypressDocumentchangeToFile (sender, mWYSIWYGkp.getTimeStamp().getTime(),newLineRemoved);
        
    }
      
     //Hashtable htDescriptions = new Hashtable();
     //Hashtable htOrigins = new Hashtable();
     
     Repository rt = new Repository();
     
     
     
     
     String message1BeforeTestSetDirector = "Please read all of this message.\n"
                + "After you and the other participant press START, " + "the experiment will show you different faces.\n"
                + "\n"
                + "Your task is to describe each face so that your partner will be able to identify it.\n"
                + "\n"
                + "You only get "+(lengthOfEachDirectorTrial/1000)+ " seconds to describe each face, so please be as quick and as accurate as possible.\n";
               
     
     String message2AfterTestSetDirectorBeforeTestSetMatcherWINDOW = "Next stage";
     String message2AfterTestSetDirectorBeforeTestSetMatcher = "OK. You have now finished describing faces\n"
             + "You will now be given your partner's descriptions of faces\n"
             + "and your task is to work out which face they are describing\n"
             + "For each set you will need to select the face you think they're describing\n"
             + "You select the face by typing its number and then pressing ENTER\n"
             + "Only one of the faces fits the description - which one is it?";
     
     
    String message3AfterTestPhaseWindow = "The test phase is finished. <br>"
              + "In the next phase you need to collaborate with your partner<br>";
       
     
    
    
     String message3AfterTestPhasePopup = "OK. You have now finished the test phase.\n"
             + "Now you need to collaborate with your partner\n"
             + "On each trial, both of you will have the same faces in front of you.\n"
             + "The order of the faces is randomly jumbled up.\n"
             + "One of you will have a set of faces with one face that is selected in a box\n"
             + "The other person will have a set of faces with numbers underneath each face\n"
             + "Your task together is to complete it as quickly and as accurately as possible\n"
             + "Once you have reached "+this.maxPointsbeforeFinalTest+ " points, you are free to go\n"
             + "Please be as quick and as accurate as possible!";

     
      
//- Emphasize that this is a "conversation" and unlike the previous test session, people can have back and forths before the matcher makes a guess.
//     There is no time limit, but the faster you guess the more points you can get.
//- The point system. Each correct guess is worth 5 points, but each incorrect guess is worth -2 points. You can only guess up to two times per trial.  If you reach 200 points, you can end the training early.
//- You must use forward slash ( / ) to enter guesses.  For example: /3 or /11
     
     
     
      String message4BeforeSecondTestSetDirector = 
                "You are now going to do "+secondtestlength +" more sets of faces\n"
              + "where you only have a limited time to describe each face\n"
              + "\n"
              + "Your partner will be describing a different set of faces\n"
              + "You won't be able to see what your partner is typing\n";
              
                
     
     String message5AfterSecondTestSetDirectorBeforeTestSetMatcher = "OK. You have now finished describing faces\n"
             + "You will now be given your partner's descriptions of faces\n"
             + "Your task is to work out which face they are describing\n"
             + "For each set you will need to select the face you think they're describing\n"
             + "You select the face by typing its number and then pressing ENTER\n"
             + "One of the faces fits the description - which one is it?";
     
     
    

     class Score{
          
         int score =0;
         
         int success_increment =5;
         int unsuccess_decrement =2;
         
         public Score(){
          }

         public void updateSuccess(){
             score = score+success_increment;
         }
         
         public void updateUnsuccessful(){
             score = score - unsuccess_decrement;
             if(score<0)score=0;
         }
         
         public int getScore(){
             return score;
         }
         
     }
     

     
     
     
     
     public class Repository{
         
        Hashtable participants = new Hashtable();
         
         
         public Repository(){
             
         }
         
         public Hashtable getParticipantsDescriptions(Participant p){
              Hashtable participantsDescriptions = (Hashtable)participants.get(p);
              if(participantsDescriptions==null){
                  participantsDescriptions = new Hashtable();
                  participants.put(p, participantsDescriptions);
                  
              }
              return participantsDescriptions;
         }
         
         
         public void saveDescription(Participant origin, int setNumber, String description){
              Hashtable participantsDescriptions = this.getParticipantsDescriptions(origin);
              participantsDescriptions.put(setNumber, description);
             
         }
         
         
         public String  getDescription(Participant origin,int setNumber){
              Hashtable participantsDescriptions = this.getParticipantsDescriptions(origin);
              String retValue = (String)participantsDescriptions.get(setNumber);
              //System.exit(-5);
              if(retValue==null)retValue="";
              return retValue;
         }
         
         
     }
     
     public static boolean showcCONGUI(){
        return true;
    } 
     
     
}


