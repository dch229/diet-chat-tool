/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.textmanipulationmodules.CyclicRandomTextGenerators;

import diet.server.ConversationController.CCGROOP3SEQ4;
import diet.utils.VectorToolkit;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author Greg
 */
public class CyclicRandomParticipantIDGeneratorGROOP {

    Random r = new Random();

    static Vector v2 =new Vector(Arrays.asList(CCGROOP3SEQ4.acceptableID));

    public CyclicRandomParticipantIDGeneratorGROOP(){
        System.err.println("GENERATOR");
    }


    public synchronized String getNextOLD(){
        if(v2.size()==0)return "NOMOREIDSTOGENEREATE";
        Object o = v2.elementAt(0);
        System.err.println("---IDGENERATOBEGINNING THE 1st LIST HHERE ");
        VectorToolkit.list(v2);
        v2.removeElement(o);
        System.err.println("---IDGENERATORBEGINNING The 2nd list HERE RETURNING :"+(String)o);
         VectorToolkit.list(v2);
        
        return (String)o;
    }
    public String getNext(){

        if(v2.size()==0)return "NOMOREIDSTOGENEREATE";
        Object o = v2.elementAt(r.nextInt(v2.size()));
        v2.removeElement(o);
        return (String)o;
    }
    
}
