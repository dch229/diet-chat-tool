/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.mazegameutils;

import diet.server.ConversationController.ui.CustomDialog;
import diet.utils.VectorToolkit;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Vector;

/**
 *
 * @author GM
 */
public class MazeGameLoadMazesFromJarFile {
    
    static Vector mazePairs = new Vector();
    static Vector mazePairsRandomized = new Vector();
    
    public static Vector cl1MazesRANDOMIZED = new Vector();
    public static Vector cl2MazesRANDOMIZED = new Vector();
    
    
    public Vector  getMazeFromJar(String name){
        //InputStream inp = ClassLoader.getSystemClassLoader().getResourceAsStream("featurestoadd.odt");  
        //InputStream inp = ClassLoader.getSystemClassLoader().getResourceAsStream(name); 
        InputStream inp = this.getClass().getResourceAsStream(name);    
        
        
    try {  
          ObjectInputStream ois = new ObjectInputStream (inp);
          Object o  = ois.readObject();
          System.err.println("READINGIN:" +o.getClass().toString());
          
          return (Vector)o;       
    } catch(Exception e){
       e.printStackTrace();
    }
       return null;
    }
    
    public static Vector cl1Mazes = new Vector();
    public static Vector cl2Mazes = new Vector();
    
    
    public void getSetOfSimplifiedMazesFromJar(){
        
       // C:\sourceforge\experimentresources\mazegame\simplifiedsetsof11\simplified11set1
        
       cl1Mazes = getMazeFromJar("/simplifiedsetsof11/simplified11set1/cl1mzes.v");
       cl2Mazes = getMazeFromJar("/simplifiedsetsof11/simplified11set1/cl2mzes.v");
       
       
      
        this.randomizeMazes();
    }
    
    
     public void getSetOfFurtherSimplifiedMazesFromJar(){
        
       // C:\sourceforge\experimentresources\mazegame\simplifiedsetsof11\simplified11set1
        
       cl1Mazes = getMazeFromJar("/simplifiedsetsof5/set1/cl1mzes.v");
       cl2Mazes = getMazeFromJar("/simplifiedsetsof5/set1/cl2mzes.v");
       
       cl1MazesRANDOMIZED = cl1Mazes;
       cl2MazesRANDOMIZED = cl2Mazes;
      
       
        //this.randomizeMazes();
    }
    
    
    
    
    public void getSetOf14MazesFromJar(){
        cl1Mazes = getMazeFromJar("/mazefourteen/set1/cl1mzes.v");
        cl2Mazes = getMazeFromJar("/mazefourteen/set1/cl2mzes.v");
        
        this.randomizeMazes();
        
        //cl1MazesRANDOMIZED = cl1Mazes;
        //cl2MazesRANDOMIZED = cl2Mazes;
    }
    
    public void getSetOf9MazesFromJar(){
       
        String[] choices = {"01","02","03","04","05","06","07","08","09","10",
                            "11","12","13","14","15","16","17","18","19","20",
                            "21","22","23","24","25","26","27","28","29","30",
                            "31","32","33","34","35","36","37","38","39","40",
                            "41","42","43"};
        
        String title = "Choose and START!";
        String prompt = "\n"
                + "Please select a set (of 9 mazes)\n\nPressing OK will start the timer!\n\n";
        String result = CustomDialog.showComboBoxDialog(title, prompt, choices, true);
        System.err.println("RESULT:"+result);
        //System.exit(-4);
        
        //cl1Mazes = getMazeFromJar("/mazenine/set"+result+"/cl1mzes.v");
        //cl2Mazes = getMazeFromJar("/mazenine/set"+result+"/cl2mzes.v");
        
       //if(cl1Mazes==null || cl2Mazes ==null){
            
            cl1Mazes = getMazeFromJar("/mazenine/set"+result+"/cl1Mazes.v");
            cl2Mazes = getMazeFromJar("/mazenine/set"+result+"/cl2Mazes.v");
            
             
        //}
       // cl1Mazes = getMazeFromJar("/mazegamesetup/expmazes12/cl1mzes.v");
        //cl2Mazes = getMazeFromJar("/mazegamesetup/expmazes12/cl2mzes.v");
        
        
        
        if(cl1Mazes==null || cl2Mazes == null){
            
            CustomDialog.showDialog("For some reason the chat tool could not find the mazes in the configuration file.\n"
                    + "The chat tool server will close after you press OK.\n"
                    + "If this problem persists, please email g.j.mills@rug.nl\n");
            System.exit(-4);
        }
        cl1MazesRANDOMIZED = cl1Mazes;
        cl2MazesRANDOMIZED = cl2Mazes;
        System.err.println("THE SIZE IS"+cl1MazesRANDOMIZED.size());
        //System.exit(-4);
    }
    
    public void getSetOf9MazesFromJarOLD(){
        getMazesFromJar();
    }
    
    
    
    
    
    
    
    
    public  void getMazesFromJar(){
        cl1Mazes = getMazeFromJar("/mazegamesetup/expmazes12/cl1mzes.v");
        cl2Mazes = getMazeFromJar("/mazegamesetup/expmazes12/cl2mzes.v");
        
        
        

         // System.exit(-4)
        if(cl1Mazes==null || cl2Mazes == null){
            
            CustomDialog.showDialog("For some reason the chat tool could not find the mazes in the configuration file.\n"
                    + "The chat tool server will close after you press OK.\n"
                    + "If this problem persists, please email g.j.mills@rug.nl\n");
            System.exit(-4);
        }
        
        randomizeMazes();
    }
    
    
    
    
    public  void randomizeMazes(){
        for(int i=0;i<cl1Mazes.size();i++){
            Object o1 = cl1Mazes.elementAt(i);
            Object o2 = cl2Mazes.elementAt(i);
            MazePair mp = new MazePair(o1,o2);
            mazePairs.add(mp);
            
        }
        mazePairsRandomized = VectorToolkit.randomSubset(mazePairs, mazePairs.size());
        for(int i=0;i<mazePairsRandomized.size();i++){
            MazePair mp = (MazePair)mazePairsRandomized.elementAt(i);
            cl1MazesRANDOMIZED.add(mp.cl1Maze);
            cl2MazesRANDOMIZED.add(mp.cl2Maze);
        }
        
    }
    
    
    class MazePair {
         
          public MazePair(Object m1, Object m2){
               cl1Maze = m1;
               cl2Maze = m2;
               
          }
         
          public Object cl1Maze;
          public Object cl2Maze;
     }
}
