/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.ConversationController.ClassLoader;

import diet.server.Conversation;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.ConversationController.ui.CustomDialog;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 *
 * @author sre
 */
public class ClassLoad {
    
    
    //Class<?>[] c = DefaultConversationController.class.getClasses();
    
    
    
    
    
    public static void fixUSERDIR(){
           URL u = ClassLoad.class.getResource("ClassLoad.class");
           String fileJARFILE ="";
           String fileJARPATH ="";
           
           try{
              String us = u.toString();
              if(!us.startsWith("jar:file")){
                  System.err.println("Isn't running from jarfile");
                  return;
              }    
             
              String us_MinusPrefix=us.replace("jar:file:", "");
              int us_MinusPrefix_indexOfExclamation = us_MinusPrefix.indexOf("!");
              int indexOfExclamation = us_MinusPrefix.indexOf("!");
              String uss_MM = us_MinusPrefix.substring(0, indexOfExclamation);
              //See if it's on windows machine
                  if(uss_MM.charAt(2)==':'){
                      uss_MM=uss_MM.substring(1);
                      // CustomDialog.showDialog("JARFILEJAR="+uss_MM.toString());
                  }
              fileJARFILE = uss_MM;
              fileJARPATH = uss_MM.replace("chattool.jar", "");
              
              String sSystemUSERDIR = System.getProperty("user.dir");
              File jarFile = new File(sSystemUSERDIR,"chattool.jar");
              if(jarFile.exists()){
                  //CustomDialog.showDialog("JARFILEXISTS");
              }
              else{
                  //CustomDialog.showDialog("JARFILEDOESNTEXIST==="+fileJARPATH);
                  System.setProperty("user.dir", fileJARPATH);
              }
              
  
              
           }catch(Exception e){
               e.printStackTrace();
           }
           
           
          
          
           
           
           
    }
      
     
    
    
    public static Vector getConversationControllers(){
    
        
        Class<?>[] c = DefaultConversationController.class.getClasses();  
        System.out.println("HERE"+ c.toString());
        for (int i=0;i<c.length;i++){
            String name = c[i].getName();
            String name2 = c[i].getCanonicalName();
            System.out.println("HERE2B"+name+"...."+name2);
        }
            
     
        
       for (String s : System.getProperty("java.class.path").split(System.getProperty("path.separator")) ){
         if (s.endsWith(".jar")) {  
            //System.out.println("dfdfd"+s); 
        }
       }  
       
       //File f = new File(System.getProperty("java.class.path"));
        //File f2 =   f.getParentFile();
        //File[] f3 = f2.listFiles();
        //File f3 =   f2.getParentFile();
        //File f4 = new File(f3.getAbsoluteFile()+File.separator+"dist"+File.separator);
        //File f5 = new File(f3.getAbsoluteFile()+File.separator+"dist"+File.separator+"chattool.jar");       
        //System.out.println("...."+f.getPath());
       
         
        //fixUSERDIR();
       
        
        File f6 = new File(System.getProperty("user.dir"));
        System.out.println("USEDIR:" +f6.getAbsolutePath());
        Conversation.printWSln("Main", "Home directory is: "+f6.getAbsolutePath());
        
        
        
        
        
        ///debugging for linux
        
        //File f8DEBUG = new File(f6.getAbsoluteFile()+File.separator+"chattool.jar");
        //List    listDEBUG =  getClasseNamesInPackage(f8DEBUG.getAbsolutePath(), "diet.server.ConversationController");
        //System.err.println("THE SIZE OF LIST IS "+listDEBUG.size());
        //System.err.println("ABOUTTOEXIT"+f8DEBUG.getAbsolutePath());
        //System.exit(-4);
        
        
        ///
        //System.out.println("...thename is.."+f6.getName()+"........."+f7.getAbsolutePath());
        //if(2<5)System.exit(-5);
        
        List list = null;
        try{
          //File f7 = new File(f6.getAbsoluteFile()+File.separator+"dist"+File.separator+"chattool.jar");
          File f7 = new File(f6.getAbsoluteFile()+File.separator+"chattool.jar");
         
          
          list =  getClasseNamesInPackage(f7.getAbsolutePath(), "diet.server.ConversationController");
        }catch (Exception e){
          System.err.println("ERROR1");
          e.printStackTrace();
          
          File f8 = new File(f6.getAbsoluteFile()+File.separator+"chattool.jar");
          list =  getClasseNamesInPackage(f8.getAbsolutePath(), "diet.server.ConversationController");
        }
        if(list==null|| list.size()<1){
            File f8 = new File(f6.getAbsoluteFile()+File.separator+"chattool.jar");
            list =  getClasseNamesInPackage(f8.getAbsolutePath(), "diet.server.ConversationController");
            
        }
        
        
        Vector listOfAvailableConversationControllers = new Vector();
        
        for(int i=0;i<list.size();i++){
            String name = (String)list.get(i);
            try{
               Class t = Class.forName(name.substring(0, name.length()-6));
               Method[] ms = t.getMethods();
               Method mSHOWONGUI = null;
               for(int j=0;j<ms.length;j++){
                   //System.err.println(ms[j].getName());
                   if(ms[j].getName().equalsIgnoreCase("showcCONGUI")){
                       mSHOWONGUI=ms[j];
                       //System.err.println("FOUND");
                       boolean show = (Boolean)mSHOWONGUI.invoke(null);
                       if(show){
                            String className = t.getSimpleName();
                            if(!className.equalsIgnoreCase("DefaultConversationController")){
                                listOfAvailableConversationControllers.addElement(className);
                                System.out.println("......"+className);
                            }
                            
                           
                            System.out.println("......"+t.getSimpleName());
                            
                            //String name = t.
                       }
                       break;
                   }
               }
               
               
               
              
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        
        
     
       
        return listOfAvailableConversationControllers;
    }
    
    
    
    public ClassLoad(){
         
    }
    
    
    
    
    
    
    

 private static boolean debug = true;

 public static List getClasseNamesInPackage
     (String jarName, String packageName){
   ArrayList classes = new ArrayList ();

   packageName = packageName.replaceAll("\\." , "/");
   if (debug) System.out.println
        ("Jar " + jarName + " looking for " + packageName);
   try{
     JarInputStream jarFile = new JarInputStream
        (new FileInputStream (jarName));
     JarEntry jarEntry;

     while(true) {
       jarEntry=jarFile.getNextJarEntry ();
       if(jarEntry == null){
         break;
       }
       if((jarEntry.getName ().startsWith (packageName)) &&
            (jarEntry.getName ().endsWith (".class")) ) {
         if (debug) System.out.println 
           ("Found " + jarEntry.getName().replaceAll("/", "\\."));
         classes.add (jarEntry.getName().replaceAll("/", "\\."));
       }
     }
   }
   catch( Exception e){
     e.printStackTrace ();
   }
   return classes;
}

/**
*
*/
  public static void main (String[] args){
    //Vector v = getConversationControllers();
    //System.exit(-4);  
    
  //List list =  getClasseNamesInPackage
     //   ("C:/j2sdk1.4.1_02/lib/mail.jar", "com.sun.mail.handlers");
  // System.out.println(list);
   /*
   output :
   
      //Vector v = 
      
      
      
    Jar C:/j2sdk1.4.1_02/lib/mail.jar looking for com/sun/mail/handlers
    Found com.sun.mail.handlers.text_html.class
    Found com.sun.mail.handlers.text_plain.class
    Found com.sun.mail.handlers.text_xml.class
    Found com.sun.mail.handlers.image_gif.class
    Found com.sun.mail.handlers.image_jpeg.class
    Found com.sun.mail.handlers.multipart_mixed.class
    Found com.sun.mail.handlers.message_rfc822.class
    [com.sun.mail.handlers.text_html.class, 
    com.sun.mail.handlers.text_xml.class,  com
    .sun.mail.handlers.image_jpeg.class, 
    , com.sun.mail.handlers.message_rfc822.class]
    
   */
  }
}
    
    
    
    
    
    
    
    
    
    