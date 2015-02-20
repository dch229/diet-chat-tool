/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.task.gridstimuli;

import diet.message.MessageGridStimuliSelectionFromClient;
import diet.message.MessageTask;
import diet.server.Conversation;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.Participant;
import diet.task.DefaultTaskController;
import diet.task.stimuliset.SerializableImage;
import diet.utils.VectorToolkit;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Date;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author sre
 */
public class GridStimuliTaskController_SelectingSquares_3WAY extends DefaultTaskController{
    
    int rows = 4;
    int columns =6;
    int numberOfSelectionsRequired =0; 
    //int numberOfTangramsAsDirector = 4;
    long delay = 20000;
    long delayshowingblank = 3000;
    
    Color colorMatcherUnselected = Color.WHITE;
    Color colorMatcherSelected = Color.ORANGE;
    Color colorDirector = Color.BLUE;
    Color colorCorrect = Color.GREEN;
    Color colorIncorrect = Color.RED;
    
    
    
    DefaultConversationController cC;
    //File fDIR;
    Vector images = new Vector();
   
    
    
    
    Participant pA;
    Participant pB;
    Participant pC;
    
    
    String[] pA_ImageNames = new String[rows*columns];
    Color[] pA_SelectionColour = new Color[rows*columns];
    Vector pA_ImageNamesAsDirector = new Vector();
    
    String[] pB_ImageNames = new String[rows*columns];
    Color[] pB_SelectionColour = new Color[rows*columns];
    Vector pB_ImageNamesAsDirector = new Vector();
    
    String[] pC_ImageNames = new String[rows*columns];
    Color[] pC_SelectionColour = new Color[rows*columns];
    Vector pC_ImageNamesAsDirector = new Vector();
    
    Vector trialSequences = new Vector();
    long serverID = new Date().getTime();
    int currentTrialNumber =0;
    
    File fDirectoryWithTangrams =  new File(System.getProperty("user.dir")+File.separatorChar+"experimentresources"+File.separatorChar+"gridstimuli"+File.separatorChar+"stimuliset4");
    
    public GridStimuliTaskController_SelectingSquares_3WAY(DefaultConversationController cC){
        this.cC = cC;
        
        String csvFilename = loadCSVFile();
         querySettings();
        loadImagesFromFile();
        loadTrialSequencesFromFile(csvFilename);
    }
    
    public void querySettings(){
        
        numberOfSelectionsRequired = CustomDialog.getInteger("How many correct selections required to go to next round?", 6);
       
    //int numberOfTangramsAsDirector = 4;
     delay = CustomDialog.getInteger("Delay in (msecs) after completing round\n(To give them time to work out what they got right/wrong)", 20000);
     //delay = CustomDialog.getInteger("Delay in (msecs) after completing round\n(To give them time to work out what they got right/wrong)", 20000);
     delayshowingblank = CustomDialog.getInteger("Length of time it (msecs) it shows blank screen while resetting", 30000);
    }
    
    public String loadCSVFile(){
        String s = System.getProperty("user.dir"); 
        
        File[] files = this.fDirectoryWithTangrams.listFiles();
        Vector csvFiles = new Vector();
        for(int i=0;i<files.length;i++){
            if (files[i].getName().endsWith("csv") || files[i].getName().endsWith("CSV")){
                csvFiles.addElement(files[i].getName());
                
            }
        }
        String filename = CustomDialog.showComboBoxDialog("Please choose the CSV File", "Please choose", csvFiles.toArray(), true);
        return filename;
        
        
    }
    
     public void loadTrialSequencesFromFile(String csvName){
          String s = System.getProperty("user.dir");
         // File f =  new File(s+File.separatorChar+"experimentresources"+File.separatorChar+"gridstimuli"+File.separatorChar+"stimuliset3"+File.separatorChar+"setup_test_all.csv");
          File f =  new File(s+File.separatorChar+"experimentresources"+File.separatorChar+"gridstimuli"+File.separatorChar+"stimuliset4"+File.separatorChar+csvName);
         //CustomDialog.
         // CustomDialog.
          
          
          Charset charset = Charset.forName("US-ASCII");
          try (BufferedReader reader = Files.newBufferedReader(f.toPath(), charset)) {
          String line = null;
          String trialSequences = "";
          while ((line = reader.readLine()) != null) {
              String trialPA = line;
              String trialPB= reader.readLine();
              String trialPC =reader.readLine();
              String separatorLine = reader.readLine();
              String[] trial = new String [3];
              trial[0]=trialPA.replace("\"", "").replace(", ", ",").replace("G", "").replace("N", "");
              trial[1]=trialPB.replace("\"", "").replace(", ", ",").replace("G", "").replace("N", "");
              trial[2]=trialPC.replace("\"", "").replace(", ", ",").replace("G", "").replace("N", "");
              
              
              
              this.trialSequences.add(trial);
              //System.out.println(line);
              //trialSequences = trialSequences +line;
          }
          } catch (IOException e) {
              System.err.format("IOException: %s%n", e);
             e.printStackTrace();
             System.exit(-234234);
          }
          
          
          
          
     }
    
     public void loadImagesFromFile(){
        try {
          
          Vector imageFiles = new Vector();
          File[] files  = this.fDirectoryWithTangrams.listFiles();
          for(int i=0;i<files.length;i++){
              File file = files[i];
              if(file.getName().endsWith("png")){
                  imageFiles.add(file);
              }
          }
          
          for(int i=0;i<imageFiles.size();i++){
              File f = (File)imageFiles.elementAt(i);
              BufferedImage bi = ImageIO.read(f);
              
              String name = f.getName().substring(0, f.getName().length()-".png".length());
              System.err.println(name);
              images.addElement(new SerializableImage(bi,name));
          }
       } catch (IOException e) {
            e.printStackTrace();
          }
      }
   
     public void participantRejoinedConversation(Participant p){
         // cC.getC().sendArtificialTurnToAllParticipants("Because of an error", 0);
          CustomDialog.showModelessDialog(p.getUsername()+"has just logged back in. RESETTING");    
          cC.getC().sendArtificialTurnToAllParticipants("Participant has logged back in...need to reset", 0);
          
          cC.getC().stimuliGrid_SendSet(pA, rows,columns,images, 131, serverID, cC.getDescriptionForP(pA));
          cC.getC().stimuliGrid_SendSet(pB, rows,columns,images, 131, serverID, cC.getDescriptionForP(pB));
          cC.getC().stimuliGrid_SendSet(pC, rows,columns,images, 131, serverID, cC.getDescriptionForP(pC));
          String[] trial = (String[])this.trialSequences.elementAt(this.currentTrialNumber);
          generateGridImageSetsForAandBandC(trial);
          cC.getC().sendArtificialTurnToAllParticipants("Participant has logged back in...please restart current set", 0);
          
          
     }
     
     
     
      public void participantJoinedConversation(Participant p){
      
       
       if(pA==null){
            pA=p;   
        }
        else if(pB==null){
            pB=p;
        }
        else if(pC==null){
            pC=p;
        }
   
        
        if(pA!=null & pB!=null & pC!=null){
              //CustomDialog.showDialog("START EXPERIMENT");
              cC.getC().stimuliGrid_SendSet(pA, rows,columns,images, 131, serverID, cC.getDescriptionForP(pA));
              cC.getC().stimuliGrid_SendSet(pB, rows,columns,images, 131, serverID, cC.getDescriptionForP(pB));
              cC.getC().stimuliGrid_SendSet(pC, rows,columns,images, 131, serverID, cC.getDescriptionForP(pC));
              String[] trial = (String[])this.trialSequences.elementAt(0);
              generateGridImageSetsForAandBandC(trial);
              cC.getC().sendArtificialTurnToAllParticipants("Welcome to the experiment. \n" +
                      pA.getUsername()+", "+pB.getUsername()+", "+pC.getUsername()+" are online.\nPlease start when ready!",0);
                      
                     
              
              //this.provideFeedbackAsDirector();
              
        }
        
      
       
      }

      
   
      
      
      
    public synchronized void generateGridImageSetsForAandBandC(String[] trial){
         String[] pAImageNames = trial[0].split(",");
         String[] pBImageNames = trial[1].split(",");
         String[] pCImageNames = trial[2].split(","); 
          
         
         pA_ImageNamesAsDirector.removeAllElements();
         pB_ImageNamesAsDirector.removeAllElements();
         pC_ImageNamesAsDirector.removeAllElements();
         
         for(int i=0;i<pAImageNames.length;i++){
             String s = pAImageNames[i];
             if(s.endsWith("D")){
                 pAImageNames[i]=pAImageNames[i].replace("D", "");
                 this.pA_ImageNamesAsDirector.add(pAImageNames[i]);
             }
         }    
         for(int i=0;i<pBImageNames.length;i++){
             String s = pBImageNames[i];
             if(s.endsWith("D")){
                 pBImageNames[i]=pBImageNames[i].replace("D", "");
                 this.pB_ImageNamesAsDirector.add(pBImageNames[i]);
             }
         }
         for(int i=0;i<pCImageNames.length;i++){
             String s = pCImageNames[i];
             if(s.endsWith("D")){
                 pCImageNames[i]=pCImageNames[i].replace("D", "");
                 this.pC_ImageNamesAsDirector.add(pCImageNames[i]);
                 
             }
         }
         
         
         
        
         
         System.err.println("AAAA"+pAImageNames[0]);
         System.err.println("AAAA"+pAImageNames[1]);
         //System.exit(-2342);
         setAandBandCImagesSettingAlltoUnselected(pAImageNames,pBImageNames, pCImageNames);
         //cC.getC().stimuliGrid_ChangeSelection(pA, serverID, this.pA_ImageNamesAsDirector, colorDirector, null);
         //cC.getC().stimuliGrid_ChangeSelection(pB, serverID, this.pB_ImageNamesAsDirector, colorDirector, null);
         changeColor(pA, this.pA_ImageNamesAsDirector, this.colorMatcherUnselected,colorDirector);
         changeColor(pB, this.pB_ImageNamesAsDirector, this.colorMatcherUnselected,colorDirector);
         changeColor(pC, this.pC_ImageNamesAsDirector, this.colorMatcherUnselected,colorDirector );
         
    }
      
      
   
    
    
    public synchronized void setAandBandCImagesSettingAlltoUnselected(String[] pANames, String[] pBNames, String[] pCNames){
         this.serverID= new Date().getTime();
         this.pA_ImageNames=pANames;
         this.pB_ImageNames=pBNames;    
         this.pC_ImageNames=pCNames;    
         for(int i=0;i<this.pA_SelectionColour.length;i++){
             this.pA_SelectionColour[i]=colorMatcherUnselected;
         }
         for(int i=0;i<this.pB_SelectionColour.length;i++){
             this.pB_SelectionColour[i]=colorMatcherUnselected;
         }
         for(int i=0;i<this.pC_SelectionColour.length;i++){
             this.pC_SelectionColour[i]=colorMatcherUnselected;
         }
         
         cC.getC().stimuliGrid_changeImages(pA, VectorToolkit.convertArrayToVector(pANames), serverID, null);
         cC.getC().stimuliGrid_changeImages(pB, VectorToolkit.convertArrayToVector(pBNames), serverID, null);
         cC.getC().stimuliGrid_changeImages(pC, VectorToolkit.convertArrayToVector(pCNames), serverID, null);
         cC.getC().stimuliGrid_ChangeSelection(pA, serverID, VectorToolkit.convertArrayToVector(pANames), colorMatcherUnselected,colorMatcherUnselected ,null);
         cC.getC().stimuliGrid_ChangeSelection(pB, serverID, VectorToolkit.convertArrayToVector(pBNames), colorMatcherUnselected,colorMatcherUnselected, null);
         cC.getC().stimuliGrid_ChangeSelection(pC, serverID, VectorToolkit.convertArrayToVector(pCNames), colorMatcherUnselected,colorMatcherUnselected, null);
    
    }  
      
      
    public void provideGraphicalFeedbackAsMatcher(){
        
        for(int i=0;i<this.pA_SelectionColour.length;i++){
             if(this.pA_SelectionColour[i]==colorMatcherSelected){
                 String imageName = this.pA_ImageNames[i];
                 if(VectorToolkit.vectorOfStringsContains(this.pB_ImageNamesAsDirector, imageName)){
                      this.changeColor(pA, imageName, this.colorMatcherUnselected,this.colorCorrect);
                 }
                 else if(VectorToolkit.vectorOfStringsContains(this.pC_ImageNamesAsDirector, imageName)){
                      this.changeColor(pA, imageName,this.colorMatcherUnselected, this.colorCorrect);
                 }
                 else{
                      this.changeColor(pA, imageName, this.colorMatcherUnselected,this.colorIncorrect);   
                 }
             }
        }
        for(int i=0;i<this.pB_SelectionColour.length;i++){
             if(this.pB_SelectionColour[i]==colorMatcherSelected){
                 String imageName = this.pB_ImageNames[i];
                 if(VectorToolkit.vectorOfStringsContains(this.pA_ImageNamesAsDirector, imageName)){
                      this.changeColor(pB, imageName, this.colorMatcherUnselected,this.colorCorrect);
                 }
                 else if(VectorToolkit.vectorOfStringsContains(this.pC_ImageNamesAsDirector, imageName)){
                      this.changeColor(pB, imageName,this.colorMatcherUnselected, this.colorCorrect);
                 }
                 else{
                      this.changeColor(pB, imageName, this.colorMatcherUnselected,this.colorIncorrect);   
                 }
             }
        }
        for(int i=0;i<this.pC_SelectionColour.length;i++){
             if(this.pC_SelectionColour[i]==colorMatcherSelected){
                 String imageName = this.pC_ImageNames[i];
                 if(VectorToolkit.vectorOfStringsContains(this.pA_ImageNamesAsDirector, imageName)){
                      this.changeColor(pC, imageName, this.colorMatcherUnselected,this.colorCorrect);
                 }
                 else if(VectorToolkit.vectorOfStringsContains(this.pB_ImageNamesAsDirector, imageName)){
                      this.changeColor(pC, imageName,this.colorMatcherUnselected, this.colorCorrect);
                 }
                 else{
                      this.changeColor(pC, imageName, this.colorMatcherUnselected,this.colorIncorrect);   
                 }
             }
        }
    }   
    
    int mistakesasdirector =0;
    int correctasdirector =0;
    
    public void provideGraphicalFeedbackAsDirector(){
         for(int i=0;i<this.pA_ImageNamesAsDirector.size();i++){
           
             String name = (String)this.pA_ImageNamesAsDirector.elementAt(i);
             int idxA = this.getIndexOfString(name, VectorToolkit.convertArrayToVector(this.pA_ImageNames));
             int idxB = this.getIndexOfString(name, VectorToolkit.convertArrayToVector(this.pB_ImageNames));
             int idxC = this.getIndexOfString(name, VectorToolkit.convertArrayToVector(this.pC_ImageNames));
             Color colB = this.pB_SelectionColour[idxB];
             Color colC = this.pC_SelectionColour[idxC];
            
             System.err.println("GOING THROUGH pA"+name+" " +colB.toString()+"---"+colC.toString());
             
             
             if(colB==this.colorMatcherSelected ){
                // this.changeColor(pA, name, Color.BLACK, Color.pink);
                 
             }
             else if(colC==this.colorMatcherSelected ){
                // this.changeColor(pA, name, Color.PINK, Color.black);
                 
             }
             
             if(colB==this.colorMatcherSelected && colC==this.colorMatcherSelected){
                 this.changeColor(pA, name, this.colorCorrect, this.colorDirector);
                 this.correctasdirector++;
             }
             else{
                 this.changeColor(pA, name, this.colorIncorrect, this.colorDirector);
                 this.mistakesasdirector++;
             }
                     
         }
         for(int i=0;i<this.pB_ImageNamesAsDirector.size();i++){
             String name = (String)this.pB_ImageNamesAsDirector.elementAt(i);
             int idxA = this.getIndexOfString(name, VectorToolkit.convertArrayToVector(this.pA_ImageNames));
             int idxB = this.getIndexOfString(name, VectorToolkit.convertArrayToVector(this.pB_ImageNames));
             int idxC = this.getIndexOfString(name, VectorToolkit.convertArrayToVector(this.pC_ImageNames));
             Color colA = this.pA_SelectionColour[idxA];
             Color colC = this.pC_SelectionColour[idxC];
             
            System.err.println("GOING THROUGH pB"+name+" " +colA.toString()+"---"+colC.toString());
             if(colA==this.colorMatcherSelected ){
                // this.changeColor(pB, name, Color.BLACK, Color.pink);
                 
             }
             else if(colC==this.colorMatcherSelected ){
                 //this.changeColor(pB, name, Color.PINK, Color.black);
                 
             }
             if(colA==this.colorMatcherSelected && colC==this.colorMatcherSelected){
                 this.changeColor(pB, name, this.colorCorrect, this.colorDirector);
                 this.correctasdirector++;
                  
             }
             else{
                 this.changeColor(pB, name, this.colorIncorrect, this.colorDirector);
                 this.mistakesasdirector++;
             }
                     
         }
         for(int i=0;i<this.pC_ImageNamesAsDirector.size();i++){
             String name = (String)this.pC_ImageNamesAsDirector.elementAt(i);
             int idxA = this.getIndexOfString(name, VectorToolkit.convertArrayToVector(this.pA_ImageNames));
             int idxB = this.getIndexOfString(name, VectorToolkit.convertArrayToVector(this.pB_ImageNames));
             //int idxC = this.getIndexOfString(name, VectorToolkit.convertArrayToVector(this.pC_ImageNames));
             Color colA = this.pA_SelectionColour[idxA];
             Color colB = this.pB_SelectionColour[idxB];
             System.err.println("GOING THROUGH pC"+name+" " +colA.toString()+"---"+colB.toString());
            
            
             //this.changeColor(pA, name, Color.pink, Color.white);
             //this.changeColor(pB, name, Color.pink, Color.white);
             
             if(colA==this.colorMatcherSelected ){
                 //this.changeColor(pC, name, Color.BLACK, Color.pink);
                 
             }
             else if(colB==this.colorMatcherSelected ){
                // this.changeColor(pC, name, Color.PINK, Color.black);
                 
             }
             
             
             if(colA==this.colorMatcherSelected && colB==this.colorMatcherSelected){
                 this.changeColor(pC, name, this.colorCorrect, this.colorDirector);
                 this.correctasdirector++;
                 
             }
             else{
                 this.changeColor(pC, name, this.colorIncorrect, this.colorDirector);
                 this.mistakesasdirector++;
             }
                     
         }
    }
    public void provideFeedbackAsText(){
          cC.getC().sendArtificialTurnToAllParticipants("As a group, you got "+this.correctasdirector+" correct and "+this.mistakesasdirector+ " incorrect",0);
    }
    
   
    
    public int getIndexOfString(String s, Vector v){
        for(int i=0;i<v.size();i++){
            String s2 = (String)v.elementAt(i);
            if(s2.equalsIgnoreCase(s)){
                return i;
            }
        }
        return -1;
    }
    
    
    public void gotoNextSet(){
          
          
          this.serverID=new Date().getTime();
          this.correctasdirector=0;
          this.mistakesasdirector=0;
          Thread t  = new Thread(){
              public void run(){
                    try{
                        Thread.sleep(delay);
                        String[] blank = new String[]{"blank","blank","blank","blank","blank","blank",
                            "blank","blank","blank","blank","blank","blank",
                            "blank","blank","blank","blank","blank","blank",
                            "blank","blank","blank","blank","blank","blank"};
                        setAandBandCImagesSettingAlltoUnselected(blank, blank, blank);
                        cC.getC().sendArtificialTurnToAllParticipants("\nLoading next round...", 0);
                        Thread.sleep(delayshowingblank);
                        cC.getC().changeClientInterface_clearMainWindows(pA);
                        cC.getC().changeClientInterface_clearMainWindows(pB);
                        cC.getC().changeClientInterface_clearMainWindows(pC);
                        cC.getC().sendArtificialTurnToAllParticipants("....Next round loaded. Please start:", 0);
                        
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    currentTrialNumber++;
                    String[] newtrial = (String[])trialSequences.elementAt(currentTrialNumber);
                    generateGridImageSetsForAandBandC(newtrial);
              }
          };
          t.start();
          
         
    }
    
    
    
  
    
    
    
    
    
    int clickcount=0;
    
    
    @Override
    public synchronized void processTaskMove(MessageTask mtm, Participant origin) {
       
        
       
        if(mtm instanceof diet.message.MessageGridStimuliSelectionFromClient){
            
            MessageGridStimuliSelectionFromClient mgssfc = (MessageGridStimuliSelectionFromClient)mtm;
            
            
            cC.getC().saveDataToConversationHistory(origin, "server", mgssfc.getTimeOnClientofSelection(), "SELECTION", mgssfc.getNameofImage()+","+mgssfc.getPosition());
            long serverIDOnClient = mgssfc.getServerID();
            if(serverIDOnClient!=this.serverID){
                System.err.println("IGNORING SELECTION FROM CLIENT "+origin.getUsername()+" times don't match");
                Conversation.printWSln("Main", "IGNORING SELECTION FROM CLIENT "+origin.getUsername()+" times don't match");
                return;
            }
            
            int position = mgssfc.getPosition();
            String nameOfSelection = mgssfc.getNameofImage();          
            
            if(nameOfSelection.equalsIgnoreCase("Select")){
                 int selections =  this.getNumberOfselectionsOfParticipant(origin);
                 System.err.println("SELECTION "+selections);
                 if(selections !=  numberOfSelectionsRequired){
                     
                     cC.getC().sendArtificialTurnToRecipient(origin, "You need to select "+numberOfSelectionsRequired+" but you selected "+selections+".", 0);
                     
                 }
                 else if(selections ==  this.numberOfSelectionsRequired){
                       if(getNumberOfselectionsOfParticipant(pA)==numberOfSelectionsRequired
                               && getNumberOfselectionsOfParticipant(pB)==numberOfSelectionsRequired
                               && getNumberOfselectionsOfParticipant(pC)==numberOfSelectionsRequired){
                           this.provideGraphicalFeedbackAsDirector();
                           provideGraphicalFeedbackAsMatcher();
                           provideFeedbackAsText();
                           gotoNextSet();
                           
                           
                       }
                       else{
                           cC.getC().sendArtificialTurnToRecipient(origin, "The other participants haven't selected all of their tangrams. Please try again when they have finished selecting.", 0);
                       }
                 }
                
                
               
                
                 return;
            }
            
            
            //The code below is only for selecting and deselecting.
            
            
            if(origin == pA){
                 String nameOnServer = this.pA_ImageNames[position];
                 if(!nameOnServer.equalsIgnoreCase(nameOfSelection)){
                     cC.getC().saveErrorLog("ERROR - GRIDSTIMULI - nameOnServer: "+nameOnServer+ " isn't same as "+nameOfSelection);
                     System.err.println("ERROR GRIDSTIMULI ");
                 }
                
                 if(this.pA_SelectionColour[position]==colorMatcherUnselected){
                     pA_SelectionColour[position]=colorMatcherSelected;
                     cC.getC().stimuliGrid_ChangeSelection(origin, serverID, nameOnServer, colorMatcherUnselected,colorMatcherSelected, "");
                 }
                 else if(this.pA_SelectionColour[position]==colorMatcherSelected){
                     pA_SelectionColour[position]=colorMatcherUnselected;
                     cC.getC().stimuliGrid_ChangeSelection(origin, serverID, nameOnServer, colorMatcherUnselected,colorMatcherUnselected, "");
                 }
            }
            else if(origin == pB){
                 String nameOnServer = this.pB_ImageNames[position];
                 if(!nameOnServer.equalsIgnoreCase(nameOfSelection)){
                     cC.getC().saveErrorLog("ERROR - GRIDSTIMULI - nameOnServer: "+nameOnServer+ " isn't same as "+nameOfSelection);
                     System.err.println("ERROR GRIDSTIMULI ");
                 }
                
                 if(this.pB_SelectionColour[position]==colorMatcherUnselected){
                      pB_SelectionColour[position]=colorMatcherSelected;
                     cC.getC().stimuliGrid_ChangeSelection(origin, serverID, nameOnServer, colorMatcherUnselected,colorMatcherSelected, "");
                 }
                 else if(this.pB_SelectionColour[position]==colorMatcherSelected){
                      pB_SelectionColour[position]=colorMatcherUnselected;
                     cC.getC().stimuliGrid_ChangeSelection(origin, serverID, nameOnServer, colorMatcherUnselected, colorMatcherUnselected, "");
                 }
            }
            else if(origin == pC){
                 String nameOnServer = this.pC_ImageNames[position];
                 if(!nameOnServer.equalsIgnoreCase(nameOfSelection)){
                     cC.getC().saveErrorLog("ERROR - GRIDSTIMULI - nameOnServer: "+nameOnServer+ " isn't same as "+nameOfSelection);
                     System.err.println("ERROR GRIDSTIMULI ");
                 }
                
                 if(this.pC_SelectionColour[position]==colorMatcherUnselected){
                      pC_SelectionColour[position]=colorMatcherSelected;
                     cC.getC().stimuliGrid_ChangeSelection(origin, serverID, nameOnServer, colorMatcherUnselected,colorMatcherSelected, "");
                 }
                 else if(this.pC_SelectionColour[position]==colorMatcherSelected){
                     pC_SelectionColour[position]=colorMatcherUnselected;
                     cC.getC().stimuliGrid_ChangeSelection(origin, serverID, nameOnServer, colorMatcherUnselected,colorMatcherUnselected, "");
                 }
            }
            
        }
        
        clickcount++;
        if(clickcount==5){
            //generateGridImageSetsForAandB();
            clickcount=0;
        }
       
    }
    
    private int getNumberOfselectionsOfParticipant(Participant p){
        
         int totalselections =0;
         if(p==pA) {
             for(int i=0;i<this.pA_SelectionColour.length;i++){
                 if(pA_SelectionColour[i]==colorMatcherSelected)totalselections++;
             }
             return totalselections;
         }
         
         else if(p==pB) {
             for(int i=0;i<this.pB_SelectionColour.length;i++){
                 if(pB_SelectionColour[i]==colorMatcherSelected)totalselections++;
             }
             return totalselections;
         } 
         else if(p==pC) {
             for(int i=0;i<this.pC_SelectionColour.length;i++){
                 if(pC_SelectionColour[i]==colorMatcherSelected)totalselections++;
             }
             return totalselections;
         } 
         return -1;
    }
    
    
      private void changeColor(Participant p, String s,Color cInnermost, Color cInner ){
          Vector v = new Vector();
          v.addElement(s);
          changeColor(p,v,cInnermost,  cInner);
      }    
    
      private void changeColor(Participant p, Vector names, Color cInnermost, Color cInner){
           if(p==pA){
               for(int i=0;i<names.size();i++){
                   String s = (String)names.elementAt(i);
                   for(int j=0;j<this.pA_ImageNames.length;j++){
                       if(s.equalsIgnoreCase(pA_ImageNames[j])){
                            this.pA_SelectionColour[j] =cInner;
                             cC.getC().stimuliGrid_ChangeSelection(pA, serverID, s, cInnermost,  cInner, null);
                             break;
                       }
                   }
               }
           }
           else if(p==pB){
               for(int i=0;i<names.size();i++){
                   String s = (String)names.elementAt(i);
                   for(int j=0;j<this.pB_ImageNames.length;j++){
                       if(s.equalsIgnoreCase(pB_ImageNames[j])){
                            this.pB_SelectionColour[j] =cInner;
                             cC.getC().stimuliGrid_ChangeSelection(pB, serverID, s,  cInnermost,  cInner, null);
                             break;
                       }
                   }
               }
           }
           else if(p==pC){
               for(int i=0;i<names.size();i++){
                   String s = (String)names.elementAt(i);
                   for(int j=0;j<this.pC_ImageNames.length;j++){
                       if(s.equalsIgnoreCase(pC_ImageNames[j])){
                            this.pC_SelectionColour[j] =cInner;
                             cC.getC().stimuliGrid_ChangeSelection(pC, serverID, s,  cInnermost,  cInner, null);
                             break;
                       }
                   }
               }
           }
      }
              
              
        // cC.getC().stimuliGrid_ChangeSelection(pA, serverID, this.pA_ImageNamesAsDirector, colorDirector, null);
        // cC.getC().stimuliGrid_ChangeSelection(pB, serverID, this.pB_ImageNamesAsDirector, colorDirector, null);
    
      
      
      
      
      
      
    
}
