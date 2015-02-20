/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.task.gridstimuli;

import diet.message.MessageTask;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.Participant;
import diet.task.DefaultTaskController;
import diet.task.stimuliset.SerializableImage;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.Vector;
import javax.imageio.ImageIO;

/**
 *
 * @author sre
 */
public class GridStimuliTaskController extends DefaultTaskController{
    
    DefaultConversationController cC;
    File fDIR;
    Vector images = new Vector();
    int rows = 3;
    int columns =3;
    
    
    Participant pA;
    Participant pB;
    
    long serverID = new Date().getTime();
    
    
    public GridStimuliTaskController(DefaultConversationController cC){
        this.cC = cC;
        initializeImages();
    }
    
    
    
     public void initializeImages(){
        try {
          String s = System.getProperty("user.dir");
          fDIR = new File(s+File.separatorChar+"experimentresources"+File.separatorChar+"gridstimuli"+File.separatorChar+"stimuliset");
          
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
        cC.getC().stimuliGrid_SendSet(p, rows,columns,images, 200, serverID, cC.getDescriptionForP(p));
       // cC.getC().subliminalstimuliset_displayText(p, "Please wait for the other participant to log in","backgroundwhite",Color.BLACK, 20,30,0,cC.getDescriptionForP(p));
        
        Vector imagenames = new Vector();
        for(int i=0;i<images.size();i++){
            
            SerializableImage si = (SerializableImage)images.elementAt(i);
            imagenames.addElement(si.getName());
            
        }
        
        
        cC.getC().stimuliGrid_changeImages(p, imagenames, serverID, "");
      }

      Random r = new Random();
      
    @Override
    public void processTaskMove(MessageTask mtm, Participant origin) {
     
    }
    int count = 0;
      
    private void debugMethod(MessageTask mtm, Participant origin){
           super.processTaskMove(mtm, origin);
        Vector vCopy = (Vector)images.clone();
        Vector vCopyNEW = new Vector();
        while(vCopy.size()>0){
            Object o = vCopy.elementAt(r.nextInt(vCopy.size()));
            vCopyNEW.addElement(o);
            vCopy.remove(o);
        }
        
        
        Vector vCopyNEWNAMES = new Vector();
        
        
        for(int i=vCopyNEW.size()-1;i>=0;i--){
            SerializableImage si = (SerializableImage)vCopyNEW.elementAt(i);
            vCopyNEWNAMES.addElement(si.getName());
           
        }
         String nameToHighlight = (String)vCopyNEWNAMES.elementAt(count);
         cC.getC().stimuliGrid_changeImages(origin, vCopyNEWNAMES, serverID, "");
         cC.getC().stimuliGrid_ChangeSelection(origin, serverID, nameToHighlight, Color.red, Color.RED,"");
         count++;
    }  
      
    
      
}
