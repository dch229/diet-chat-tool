/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.utils.postprocessing.cc2013concepttask;

import java.io.*;
import diet.message.*;
import diet.server.conversationhistory.turn.Turn;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chris
 */
public class RecoverMessages {
    
    public static void inputMessageDat() throws FileNotFoundException, IOException, ClassNotFoundException{
        File filename = new File("C:/Users/Chris/Desktop/0002_AUT_RAT_CAT_2people/turns.dat");
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(fis);



        while (in.readObject()!=null){
            Object o = in.readObject();
            if (o instanceof Turn){
            Turn mcct = (Turn)o;
            System.out.println("MCCT "+mcct.getText());
     //you now have the chat text..
            }
        } 
    }
    public static void main(String[] args){
        try {
            inputMessageDat();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RecoverMessages.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RecoverMessages.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RecoverMessages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }
