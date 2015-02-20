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

/**
 *
 * @author sre
 */
public class GridStimuliTaskController_SelectingSquares_2Way extends DefaultTaskController{
    
    int rows = 4;
    int columns =6;
    int numberOfSelectionsRequired =3; 
    //int numberOfTangramsAsDirector = 4;
    
    Color colorMatcherUnselected = Color.BLACK;
    Color colorMatcherSelected = Color.ORANGE;
    Color colorDirector = Color.BLUE;
    Color colorMatcherCorrect = Color.GREEN;
    Color colorMatcherIncorrect = Color.RED;
    
    
    
    DefaultConversationController cC;
    File fDIR;
    Vector images = new Vector();
   
    
    
    
    Participant pA;
    Participant pB;
    
    
    String[] pA_ImageNames = new String[rows*columns];
    Color[] pA_SelectionColour = new Color[rows*columns];
    Vector pA_ImageNamesAsDirector = new Vector();
    
    String[] pB_ImageNames = new String[rows*columns];
    Color[] pB_SelectionColour = new Color[rows*columns];
    Vector pB_ImageNamesAsDirector = new Vector();
    
    Vector trialSequences = new Vector();
    long serverID = new Date().getTime();
    int currentTrialNumber =0;
    
    
    
    public GridStimuliTaskController_SelectingSquares_2Way(DefaultConversationController cC){
        this.cC = cC;
        loadImagesFromFile();
        loadTrialSequencesFromFile();
    }
    
     public void loadTrialSequencesFromFile(){
          String s = System.getProperty("user.dir");
          File f =  new File(s+File.separatorChar+"experimentresources"+File.separatorChar+"gridstimuli"+File.separatorChar+"stimuliset3"+File.separatorChar+"setup_test_all.csv");
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
          String s = System.getProperty("user.dir");
          fDIR = new File(s+File.separatorChar+"experimentresources"+File.separatorChar+"gridstimuli"+File.separatorChar+"stimuliset3");
          
          Vector imageFiles = new Vector();
          File[] files  = fDIR.listFiles();
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
    
      public void participantJoinedConversation(Participant p){
           if(pA==null){
            pA=p;   
        }
        else if(pB==null){
            pB=p;
        }
   
        
        if(pA!=null & pB!=null){
              CustomDialog.showDialog("START EXPERIMENT");
              cC.getC().stimuliGrid_SendSet(pA, rows,columns,images, 132, serverID, cC.getDescriptionForP(pA));
              cC.getC().stimuliGrid_SendSet(pB, rows,columns,images, 132, serverID, cC.getDescriptionForP(pB));
              String[] trial = (String[])this.trialSequences.elementAt(0);
              generateGridImageSetsForAandB(trial);
        }
        
      
       
      }

    public synchronized void generateGridImageSetsForAandB(String[] trial){
         String[] pAImageNames = trial[0].split(",");
         String[] pBImageNames = trial[1].split(",");
         String[] pCImageNames = trial[2].split(","); 
          
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
         
         
         
        
         
         System.err.println("AAAA"+pAImageNames[0]);
         System.err.println("AAAA"+pAImageNames[1]);
         //System.exit(-2342);
         setAandBImagesSettingAlltoBlack(pAImageNames,pBImageNames);
         //cC.getC().stimuliGrid_ChangeSelection(pA, serverID, this.pA_ImageNamesAsDirector, colorDirector, null);
         //cC.getC().stimuliGrid_ChangeSelection(pB, serverID, this.pB_ImageNamesAsDirector, colorDirector, null);
         changeColor(pA, this.pA_ImageNamesAsDirector, colorDirector);
         changeColor(pB, this.pB_ImageNamesAsDirector, colorDirector);
         
    }
      
      
    public void generateRandomGridImageSetsForAandB(){
        String[] imagenames = new String[images.size()];
              for(int i=0;i<images.size();i++){
                  SerializableImage si = (SerializableImage)images.elementAt(i);
                  imagenames[i]=(si.getName());      
              }
              Vector pAnew = VectorToolkit.randomSubset(VectorToolkit.convertArrayToVector(imagenames),4);
              Vector pBnew = VectorToolkit.randomSubset(VectorToolkit.convertArrayToVector(imagenames),4);
              
              String[] pAnewAsString = VectorToolkit.convertVectorToArrayOfStrings(pAnew);
              String[] pBnewAsString = VectorToolkit.convertVectorToArrayOfStrings(pBnew);
              
              setAandBImagesSettingAlltoBlack(pAnewAsString,pBnewAsString);
              
              
    }  
      
    
    
    public synchronized void setAandBImagesSettingAlltoBlack(String[] pANames, String[] pBNames){
         this.serverID= new Date().getTime();
         this.pA_ImageNames=pANames;
         this.pB_ImageNames=pBNames;    
         for(int i=0;i<this.pA_SelectionColour.length;i++){
             this.pA_SelectionColour[i]=colorMatcherUnselected;
         }
         for(int i=0;i<this.pB_SelectionColour.length;i++){
             this.pB_SelectionColour[i]=colorMatcherUnselected;
         }
         
         cC.getC().stimuliGrid_changeImages(pA, VectorToolkit.convertArrayToVector(pANames), serverID, null);
         cC.getC().stimuliGrid_changeImages(pB, VectorToolkit.convertArrayToVector(pBNames), serverID, null);
         cC.getC().stimuliGrid_ChangeSelection(pA, serverID, VectorToolkit.convertArrayToVector(pANames), colorMatcherUnselected,colorMatcherUnselected, null);
         cC.getC().stimuliGrid_ChangeSelection(pB, serverID, VectorToolkit.convertArrayToVector(pBNames), colorMatcherUnselected, colorMatcherUnselected, null);
    }  
      
      
    public void provideFeedback(){
        
        for(int i=0;i<this.pA_SelectionColour.length;i++){
             if(this.pA_SelectionColour[i]==colorMatcherSelected){
                 String imageName = this.pA_ImageNames[i];
                 if(VectorToolkit.vectorOfStringsContains(this.pB_ImageNamesAsDirector, imageName)){
                      this.changeColor(pA, imageName, this.colorMatcherCorrect);
                 }
                 else{
                      this.changeColor(pA, imageName, this.colorMatcherIncorrect);   
                 }
             }
        }
        for(int i=0;i<this.pB_SelectionColour.length;i++){
             if(this.pB_SelectionColour[i]==colorMatcherSelected){
                 String imageName = this.pB_ImageNames[i];
                 if(VectorToolkit.vectorOfStringsContains(this.pA_ImageNamesAsDirector, imageName)){
                      this.changeColor(pB, imageName, this.colorMatcherCorrect);
                 }
                 else{
                      this.changeColor(pB, imageName, this.colorMatcherIncorrect);   
                 }
             }
        }
    }   
        
    public void provideFeedbackOLD(){    
        Vector pANames = VectorToolkit.convertArrayToVector(pA_ImageNames);
        Vector pBNames = VectorToolkit.convertArrayToVector(pB_ImageNames);
        Vector commonSubset = VectorToolkit.getCommonObjectsInBothVectors(pANames, pBNames);
        for(int i=0;i<this.pA_SelectionColour.length;i++){
             if(this.pA_SelectionColour[i]==colorMatcherSelected){
                 String imageName = this.pA_ImageNames[i];
                 if(VectorToolkit.vectorOfStringsContains(commonSubset, imageName)){
                     pA_SelectionColour[i]=colorMatcherCorrect ;
                     cC.getC().stimuliGrid_ChangeSelection(pA, serverID, imageName, colorMatcherCorrect,colorMatcherCorrect, "");
                 }
                 else{
                     pA_SelectionColour[i]=colorMatcherIncorrect;
                     cC.getC().stimuliGrid_ChangeSelection(pA, serverID, imageName,colorMatcherIncorrect,colorMatcherIncorrect, "");
                 }
             }
         }
        for(int i=0;i<this.pB_SelectionColour.length;i++){
             if(this.pB_SelectionColour[i]==colorMatcherSelected){
                 String imageName = this.pB_ImageNames[i];
                 if(VectorToolkit.vectorOfStringsContains(commonSubset, imageName)){
                     pB_SelectionColour[i]=colorMatcherCorrect;
                     cC.getC().stimuliGrid_ChangeSelection(pB, serverID, imageName, colorMatcherCorrect,colorMatcherCorrect, "");
                 }
                 else{
                     pB_SelectionColour[i]=colorMatcherIncorrect;
                     cC.getC().stimuliGrid_ChangeSelection(pB, serverID, imageName, colorMatcherIncorrect,colorMatcherIncorrect, "");
                 }
             }
         }
        
        
        
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
                       if(getNumberOfselectionsOfParticipant(pA)==numberOfSelectionsRequired && getNumberOfselectionsOfParticipant(pB)==numberOfSelectionsRequired){
                           provideFeedback();
                       }
                       else{
                           cC.getC().sendArtificialTurnToRecipient(origin, "One of the other participants hasn't selected all of theirs yet. Please try again when they have finished selecting.", 0);
                       }
                 }
                
                
                
                //can have too many selected
                 //can have not enough selected
                 //
                
                
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
                     cC.getC().stimuliGrid_ChangeSelection(origin, serverID, nameOnServer, colorMatcherSelected,colorMatcherSelected, "");
                 }
                 else if(this.pA_SelectionColour[position]==colorMatcherSelected){
                     pA_SelectionColour[position]=colorMatcherUnselected;
                     cC.getC().stimuliGrid_ChangeSelection(origin, serverID, nameOnServer, colorMatcherUnselected,colorMatcherUnselected,"");
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
                     cC.getC().stimuliGrid_ChangeSelection(origin, serverID, nameOnServer, colorMatcherSelected,colorMatcherSelected, "");
                 }
                 else if(this.pB_SelectionColour[position]==colorMatcherSelected){
                      pB_SelectionColour[position]=colorMatcherUnselected;
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
    
    public int getNumberOfselectionsOfParticipant(Participant p){
        
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
         return -1;
    }
    
    
      public void changeColor(Participant p, String s,Color c ){
          Vector v = new Vector();
          v.addElement(s);
          changeColor(p,v,c);
      }    
    
      public void changeColor(Participant p, Vector names, Color c){
           if(p==pA){
               for(int i=0;i<names.size();i++){
                   String s = (String)names.elementAt(i);
                   for(int j=0;j<this.pA_ImageNames.length;j++){
                       if(s.equalsIgnoreCase(pA_ImageNames[j])){
                            this.pA_SelectionColour[j] =c;
                             cC.getC().stimuliGrid_ChangeSelection(pA, serverID, s, c,c, null);
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
                            this.pB_SelectionColour[j] =c;
                             cC.getC().stimuliGrid_ChangeSelection(pB, serverID, s, c,c, null);
                             break;
                       }
                   }
               }
           }
      }
              
              
        // cC.getC().stimuliGrid_ChangeSelection(pA, serverID, this.pA_ImageNamesAsDirector, colorDirector, null);
        // cC.getC().stimuliGrid_ChangeSelection(pB, serverID, this.pB_ImageNamesAsDirector, colorDirector, null);
    
      
}
