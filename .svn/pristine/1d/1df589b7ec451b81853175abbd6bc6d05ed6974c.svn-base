/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.Vector;

public class SetupIO {
    
   public String mainmazegamelibrary;
   public String experimentmazegamedirectory; 
   String parentDirectory;
   File vectorOfMazeMoves;
   File vectorOfMazeMovesTxt;

   public SetupIO(String parentdirect) {
      parentDirectory = parentdirect;
             
      mainmazegamelibrary = System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"mazegame"+File.separator+"mazegamesetup";
  }
  
   public static void main(String[] args){
       
       SetupIO sIO = new SetupIO(System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"mazegame"+File.separator+"mazegamesetup");
 
       sIO.doingSetup=true;
       JSetupFrame jsf = new JSetupFrame(sIO);
       File f = new File(System.getProperty("user.dir")+File.separator+"experimentresources"+File.separator+"mazegame"+File.separator+"mazegamesetup");
       
       
       //System.out.println(f.getParentFile().list());
       
   }
   
  
   
   
  public SetupIO(String mainmazegamelibrary,String experimentmazegamedirectory){
      this.mainmazegamelibrary=mainmazegamelibrary;
      this.experimentmazegamedirectory=experimentmazegamedirectory;
  }
  
  public Vector getRandomPairOfMazeVectors(){
      System.err.println("HERE0");
      Vector pairOfMazeVectors = new Vector();
      System.err.println("HERE1");
      Vector v = getAllMazeConfigsFromLibrary();
      System.err.println("HERE2 "+v.size());
      Random r = new Random();
      System.err.println("HERE3");
      int i = r.nextInt(v.size());
      System.err.println("HERE4");
      String s = (String)v.elementAt(i);
      System.err.println("HERE5");
      Vector cl1Mazes = this.getClient1MazesFromSetupName(s);
      System.err.println("HERE6");
      Vector cl2Mazes = this.getClient2MazesFromSetupName(s);
      System.err.println("HERE7");
      pairOfMazeVectors.addElement(cl1Mazes);
      System.err.println("HERE8");
      pairOfMazeVectors.addElement(cl2Mazes);
      System.err.println("HERE9");
      return pairOfMazeVectors;
  }
  
  
  public Vector getAllMazeConfigsFromLibrary(){
       Vector v = new Vector();
      try {
        System.err.println("step1")  ;
        File setupdir = new File(mainmazegamelibrary);
        System.err.println("step2")  ;
        System.out.println("Looking for mazes in "+setupdir.getAbsolutePath());
         File[] setupDirListing = setupdir.listFiles();
         for(int i=0;i<setupDirListing.length;i++){
               if( setupDirListing[i].isDirectory() && !setupDirListing[i].isHidden()){
                   v.addElement(setupDirListing[i].getCanonicalPath());
                   System.err.println("LISTING CONTENTS "+setupDirListing[i].getCanonicalPath());
               }
               
         }
      }catch(Exception e){
          System.out.println("Could not read directory to get list of configs "+e.getMessage());
      }    
      return v;
  }

  boolean doingSetup = false;
  

public Vector getClient1MazesFromSetupName(String directoryname){
  Vector v = new Vector();
  String fileToLoad = "unset";
try{
   FileInputStream fi;
   fileToLoad =directoryname+File.separator+"cl1mzes.v";
   if(doingSetup){
       fi = new FileInputStream(new File(this.mainmazegamelibrary+File.separator+    directoryname+File.separator+"cl1mzes.v"));
   }
   else{
       fi = new FileInputStream(new File(directoryname+File.separator+"cl1mzes.v"));
   }
   
   
   
   System.out.println("client1a");
   ObjectInputStream oInp = new ObjectInputStream(fi);
   System.out.println("client1b "+directoryname+File.separator+"cl1mzes.v");
   Object o = oInp.readObject();//v = (Vector)oInp.readObject();
   v = (Vector)o;
   System.out.println("client1c");
   System.out.println("IS OF CLASS TYPE: "+o.getClass().toString());
   System.out.println("client1d");
   oInp.close();
   fi.close();

}catch(Exception e){
 System.out.println("can't load in setup mazes1 "+e.getMessage()+"the name is: "+fileToLoad);
 e.printStackTrace();
 
}
return v;


}
 public Vector getClient2MazesFromSetupName(String directoryname){
  Vector v = new Vector();
try{ String fileToLoad =directoryname+File.separator+"cl2mzes.v";
   //FileInputStream fi = new FileInputStream(new File(this.mainmazegamelibrary+File.separator+    directoryname+File.separator+"cl2mzes.v"));
   
   FileInputStream fi;// = new FileInputStream(new File(directoryname+File.separator+"cl2mzes.v"));
   if(doingSetup){
       fi = new FileInputStream(new File(this.mainmazegamelibrary+File.separator+    directoryname+File.separator+"cl2mzes.v"));
   }
   else{
       fi = new FileInputStream(new File(directoryname+File.separator+"cl2mzes.v"));
   } 
   
   
  ObjectInputStream oInp = new ObjectInputStream(fi);
   v = (Vector)oInp.readObject();
   oInp.close();
   fi.close();

}catch(Exception e){
System.out.println("can't load in setup mazes2 "+e.getMessage());
}
return v;

 }
public void saveClientMazesOfTwoClientsByName(Vector cl1Mazes, Vector cl2Mazes){
 try{   
   File directory = new File(this.experimentmazegamedirectory);
   if(!directory.exists())directory.mkdirs();
   File cl1MazesFile = new File(directory+File.separator+"cl1Mazes.v"); 
   File cl2MazesFile = new File(directory+File.separator+"cl2Mazes.v");
   FileOutputStream fv1Cl1Out = new FileOutputStream(cl1MazesFile);
   FileOutputStream fv2Cl2Out = new FileOutputStream(cl2MazesFile);
   ObjectOutputStream cl1out = new ObjectOutputStream(fv1Cl1Out);
   ObjectOutputStream cl2out = new ObjectOutputStream(fv2Cl2Out);
   cl1out.writeObject(cl1Mazes);
   cl2out.writeObject(cl2Mazes); 
   
   File mazes1TextFile = new File(directory+File.separator+"cl1Mazes.txt");
   File mazes2TextFile = new File(directory+File.separator+"cl2Mazes.txt");
   FileWriter mazes1 = new FileWriter (mazes1TextFile);
   FileWriter mazes2 = new FileWriter (mazes2TextFile);
   
   
    for(int i=0;i<cl1Mazes.size();i++){
       Maze m1 = (Maze)cl1Mazes.elementAt(i);
       Maze m2 = (Maze)cl2Mazes.elementAt(i);
       mazes1.write("MAZE No: " +  i+"\n"+m1.toText());
       mazes2.write("MAZE No: " +  i+"\n"+m2.toText());     
   }
   cl1out.flush();
   cl2out.flush();
   mazes1.flush();
   mazes2.flush();
   cl1out.close();
   cl2out.close();   
   mazes1.close();
   mazes2.close();
   
 } catch (Exception e){
    System.out.println("Error saving mazes of both clients in setup " +e.getMessage());
    e.printStackTrace();
 }
   
} 
 
public Vector getAllMazesFromExperimentDirectory2WAY(String s){
    Vector vAllMazes = new Vector();
    try{
       FileInputStream fiDirector = new FileInputStream(s+File.separator+"cl1Mazes.v");
       FileInputStream fiMatcher1 = new FileInputStream(s+File.separator+"cl2Mazes.v");
       
       
       ObjectInputStream oInpDirector = new ObjectInputStream(fiDirector);
       ObjectInputStream oInpMatcher1 = new ObjectInputStream(fiMatcher1);
       
       
       Vector vDirectorMazes = (Vector)oInpDirector.readObject();
       Vector vMatcher1Mazes = (Vector)oInpMatcher1.readObject();
       
       
       oInpDirector.close();
       oInpMatcher1.close();
       
       
       vAllMazes.addElement(vDirectorMazes);
       vAllMazes.addElement(vMatcher1Mazes);
       
        
       
    }catch(Exception e){
       System.err.println("COULD NOT LOAD EXPERIMENT MAZES: ");
       System.out.println("STACKTRACE:");
       e.printStackTrace();
    }
    
    return vAllMazes;
}
 
 
 
 
 
 public void saveClientMazesFromSetupNo(Vector v1,Vector v2, String description){
   try{
     File directory = new File(parentDirectory+"/setup/"+description+"/");
     directory.mkdirs();
     File client1MazesFile = new File(parentDirectory+ "/setup/"+description+"/cl1mzes"+".v");
     File client2MazesFile = new File(parentDirectory+ "/setup/"+description+"/cl2mzes"+".v");
     FileOutputStream fv1 = new FileOutputStream(client1MazesFile);
     FileOutputStream fv2 = new FileOutputStream(client2MazesFile);
     ObjectOutputStream v1out = new ObjectOutputStream(fv1);
     ObjectOutputStream v2out = new ObjectOutputStream(fv2);
  v1out.writeObject(v1);
  v2out.writeObject(v2);

  File mazes1TextFile = new File(parentDirectory+ "/setup/"+description+"/client1m"+".txt");
  File mazes2TextFile = new File(parentDirectory+ "/setup/"+description+"/client2m"+".txt");
  FileWriter mazes1 = new FileWriter (mazes1TextFile);
  FileWriter mazes2 = new FileWriter (mazes2TextFile);

  for(int i=0;i<v1.size();i++){
      Maze m1 = (Maze)v1.elementAt(i);
      Maze m2 = (Maze)v2.elementAt(i);
      mazes1.write("MAZE No: " +  i+"\n"+m1.toText());
      mazes2.write("MAZE No: " +  i+"\n"+m2.toText());
  }
  v1out.flush();
  v2out.flush();
  mazes1.flush();
  mazes2.flush();
  v1out.close();
  v2out.close();
  mazes1.close();
  mazes2.close();


 } catch (Exception e){
    System.out.println("Error saving mazes of both clients in setup " +e.getMessage());
 }



}

 
 
 
 public Vector getListOfMazeConfigs(){
  Vector v = new Vector();
  try {
    //File setupdir = new File(parentDirectory+"/setup/");
    File setupdir = new File(this.mainmazegamelibrary);
    File[] setupDirListing = setupdir.listFiles();
    for(int i=0;i<setupDirListing.length;i++){
       if( setupDirListing[i].isDirectory()) v.addElement(setupDirListing[i].getName());
    }
  }catch (Exception e){ System.out.println("Could not read directory to get list of configs "+e.getMessage());

  }
  return v;
}

 
 
 
public void saveClientMazesFromSetupNo(Vector vDirector,Vector vMatcher1, Vector vMatcher2,String description){
  try{
   File directory = new File(this.experimentmazegamedirectory);
   if(!directory.exists())directory.mkdirs();
   File directorMazesFile = new File(directory+File.separator+"directorMazes.v"); 
   File matcher1MazesFile = new File(directory+File.separator+"matcher1Mazes.v"); 
   File matcher2MazesFile = new File(directory+File.separator+"matcher2Mazes.v"); 
   FileOutputStream fv1DirectorOut = new FileOutputStream(directorMazesFile);
   FileOutputStream fv2Matcher1Out = new FileOutputStream(matcher1MazesFile);
   FileOutputStream fv3Matcher2Out = new FileOutputStream(matcher2MazesFile);
   ObjectOutputStream fdout = new ObjectOutputStream(fv1DirectorOut);
   ObjectOutputStream fm1out = new ObjectOutputStream(fv2Matcher1Out);
   ObjectOutputStream fm2out = new ObjectOutputStream(fv3Matcher2Out);
   fdout.writeObject(vDirector);
   fm1out.writeObject(vMatcher1); 
   fm2out.writeObject(vMatcher2);
   
   File mazes1TextFile = new File(directory+File.separator+"directorMazes.txt");
   File mazes2TextFile = new File(directory+File.separator+"matcher1Mazes.txt");
   File mazes3TextFile = new File(directory+File.separator+"matcher2Mazes.txt");
   FileWriter mazes1 = new FileWriter (mazes1TextFile);
   FileWriter mazes2 = new FileWriter (mazes2TextFile);
   FileWriter mazes3 = new FileWriter (mazes2TextFile);
       
   for(int i=0;i<vDirector.size();i++){
       Maze m1 = (Maze)vDirector.elementAt(i);
       Maze m2 = (Maze)vMatcher1.elementAt(i);
       Maze m3 = (Maze)vMatcher2.elementAt(i);
       mazes1.write("MAZE No: " +  i+"\n"+m1.toText());
       mazes2.write("MAZE No: " +  i+"\n"+m2.toText());
       mazes3.write("MAZE No: " +  i+"\n"+m3.toText());
   }
       
   fdout.flush();
   fm1out.flush();
   fm2out.flush();

   mazes1.flush();
   mazes2.flush();
   mazes3.flush();
   
   fdout.close();
   fm1out.close();
   fm2out.close();
   
   mazes1.close();
   mazes2.close();
   mazes3.close();

 } catch (Exception e){
    System.out.println("Error saving mazes of both clients in setup " +e.getMessage());
 }



}


public Vector getAllMazesFromExperimentDirectory3WAY(String s){
    Vector vAllMazes = new Vector();
    try{
       FileInputStream fiDirector = new FileInputStream(s+File.separator+"directorMazes.v");
       FileInputStream fiMatcher1 = new FileInputStream(s+File.separator+"matcher1Mazes.v");
       FileInputStream fiMatcher2 = new FileInputStream(s+File.separator+"matcher2Mazes.v");
       
       ObjectInputStream oInpDirector = new ObjectInputStream(fiDirector);
       ObjectInputStream oInpMatcher1 = new ObjectInputStream(fiMatcher1);
       ObjectInputStream oInpMatcher2 = new ObjectInputStream(fiMatcher2);
       
       Vector vDirectorMazes = (Vector)oInpDirector.readObject();
       Vector vMatcher1Mazes = (Vector)oInpMatcher1.readObject();
       Vector vMatcher2Mazes = (Vector)oInpMatcher2.readObject();
       
       oInpDirector.close();
       oInpMatcher1.close();
       oInpMatcher2.close();
       
       vAllMazes.addElement(vDirectorMazes);
       vAllMazes.addElement(vMatcher1Mazes);
       vAllMazes.addElement(vMatcher2Mazes);
        
       
    }catch(Exception e){
       System.err.println("COULD NOT LOAD EXPERIMENT MAZES: ");
       System.out.println("STACKTRACE:");
       e.printStackTrace();
    }
    
    return vAllMazes;
}


 



}





