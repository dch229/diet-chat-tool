/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.keylogging;

import diet.debug.Debug;
import java.util.Date;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JOptionPane;

/**
 *
 * @author sre
 */
public class Keylogging {
    
      KeyLoggingOutput klo;
    
      public Keylogging(){
          
         //JOptionPane.showMessageDialog(null, "Stage 1 Completed. PRESS OK TO START STAGE 2",  "Message 1 of 2",  JOptionPane.PLAIN_MESSAGE);
          String username = JOptionPane.showInputDialog(null, "Please choose a username:");
          //String username ="output";
          
          
          klo= new KeyLoggingOutput(username);
          JKeyloggingChatFrame jf = new JKeyloggingChatFrame(username,this);
          jf.pack();
          jf.setVisible(true);
         
          
          Debug.printDBG("Trying to debug sound capabilities.\n");
          try{
              try{Thread.sleep(1000);}catch (Exception e){ Debug.printDBG(e.getLocalizedMessage());     }
              
             // Debug.printDBG("Trying to debug sound capabilities.\n");
              try{Thread.sleep(1000);}catch (Exception e){ Debug.printDBG(e.getLocalizedMessage());     }
              Debug.printDBG("COUNTING DOWN: 5\n");
              try{Thread.sleep(1000);}catch (Exception e){ Debug.printDBG(e.getLocalizedMessage());     }
              Debug.printDBG("COUNTING DOWN: 4\n");
              try{Thread.sleep(1000);}catch (Exception e){ Debug.printDBG(e.getLocalizedMessage());     }
              Debug.printDBG("COUNTING DOWN: 3\n");
               try{Thread.sleep(1000);}catch (Exception e){ Debug.printDBG(e.getLocalizedMessage());     }
              Debug.printDBG("COUNTING DOWN: 2\n");
               try{Thread.sleep(1000);}catch (Exception e){ Debug.printDBG(e.getLocalizedMessage());     }
              Debug.printDBG("COUNTING DOWN: 1\n");
               try{Thread.sleep(1000);}catch (Exception e){ Debug.printDBG(e.getLocalizedMessage());     }
              Debug.printDBG("IT SHOULD GENERATE A TONE NOW OF 500 Hz for 5 seconds\n");
               // AudioSynchronizer.tone(null,100,1000,500);
              
                long msecs = 5000;
                 float SAMPLE_RATE = 8000f;
                 int hz = 500;
                  double vol = 1;
                
                 byte[] buf = new byte[1];
    AudioFormat af = 
        new AudioFormat(
            SAMPLE_RATE, // sampleRate
            8,           // sampleSizeInBits
            1,           // channels
            true,        // signed
            false);      // bigEndian
    SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
    sdl.open(af);
    
    sdl.start();
    long startTime = new Date().getTime();
    for (int i=0; i < msecs*8; i++) {
      double angle = i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
      buf[0] = (byte)(Math.sin(angle) * 127.0 * vol);
      sdl.write(buf,0,1);
    }
    sdl.drain();
    sdl.stop();
    sdl.close();
    long finishTime = new Date().getTime();
    klo.saveSynchronizationData(startTime, hz, finishTime);
                
    
    
     Debug.printDBG("IT SHOULD NOW GENERATE ANOTHER TONE OF 300 Hz for 5 seconds\n");
               // AudioSynchronizer.tone(null,100,1000,500);
              
                 msecs = 5000;
                 SAMPLE_RATE = 8000f;
                 hz = 300;
                   vol = 1;
                
                  buf = new byte[1];
     af = 
        new AudioFormat(
            SAMPLE_RATE, // sampleRate
            8,           // sampleSizeInBits
            1,           // channels
            true,        // signed
            false);      // bigEndian
     sdl = AudioSystem.getSourceDataLine(af);
    sdl.open(af);
    
    sdl.start();
    startTime = new Date().getTime();
    for (int i=0; i < msecs*8; i++) {
      double angle = i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
      buf[0] = (byte)(Math.sin(angle) * 127.0 * vol);
      sdl.write(buf,0,1);
    }
    sdl.drain();
    sdl.stop();
    sdl.close();
    
    finishTime = new Date().getTime();
    klo.saveSynchronizationData(startTime, hz, finishTime);
    
    
    
    
    
    
    
              
              
          }catch (Exception e){
                Debug.printDBG("THERE IS AN ERROR\n");
                Debug.printDBG(e.getLocalizedMessage()+"\n");
                Debug.printDBG(e.getMessage()+"\n");
                Debug.printDBG(e.toString()+"\n");
          }
          
          
      }
    
    
     public static void main(String[] args) throws Exception{
          Keylogging kl = new Keylogging();
            long start = new Date().getTime();
      /*    
            AudioSynchronizer.tone(1000,4000);   
            long finish = new Date().getTime();
            
            System.out.println(finish-start);
    Thread.sleep(1000);
    AudioSynchronizer.tone(100,1000);
    Thread.sleep(1000);
    AudioSynchronizer.tone(5000,100);
    Thread.sleep(1000);
    AudioSynchronizer.tone(400,500);
    Thread.sleep(1000);
    AudioSynchronizer.tone(400,500, 0.2);
          */
     }
     
     
     
     
     
     
}

     
