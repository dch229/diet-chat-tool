/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.client;

import java.util.Date;
import java.util.Random;

/**
 *
 * @author sre
 */
public class ChatFrameRandomTyping {
    
    ConnectionToServer cts;
    
    public ChatFrameRandomTyping(ConnectionToServer cts2){
        this.cts = cts2;
         
        //on about the fifth automated transition it wasnt sending text properly between participants...nothing displayed from participant 8
         
        
     Thread t = new Thread(){
         public void run(){
             int msgCount =0;
             while (true){
             Random r = new Random();
             String s = cts.getUsername()+"";
             msgCount++;
             try{
                Thread.sleep(r.nextInt(1000));
                
                //Put some typing information here
               
                
                
             }catch (Exception e){
                  e.printStackTrace();
             }  
         }        
         }
     };
        t.start();
    }
    
    
}
