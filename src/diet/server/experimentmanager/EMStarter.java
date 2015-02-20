/*
 * EMTester.java
 *
 * Created on 05 January 2008, 19:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package diet.server.experimentmanager;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Vector;


import javax.swing.UIManager;


import diet.client.ConnectionToServer;
import diet.debug.Debug;
import diet.parameters.ExperimentSettings;
import diet.parameters.IntParameter;
import diet.parameters.LongParameter;
import diet.parameters.Parameter;
import diet.parameters.StringListParameter;
import diet.parameters.StringParameter;
import diet.parameters.StringParameterFixed;
import diet.parameters.ui.JListEditorWithSingleSelectionOption;
import diet.parameters.ui.SavedExperimentsAndSettingsFile;
import diet.server.Conversation;
import diet.server.ConversationController.ClassLoader.ClassLoad;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.ExperimentManager;
import diet.server.demomode.DemoModeChecker;
import diet.server.experimentmanager.ui.JEMStarter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author user
 */
public class EMStarter {
    
    /**
     * Creates a new instance of EMTester
     */
    public EMStarter() {
       
        
    }
    
    
 /*    static public void beanshellTester(){
     try{  
       Interpreter i = new Interpreter();  // Construct an interpreter
       i.set("foo", 5);                    // Set variables
       i.set("date", new Date() ); 

        Date date = (Date)i.get("date");    // retrieve a variable

       // Eval a statement and get the result
       i.eval("bar = foo*10");      
       i.eval("classBrowser()");
       System.out.println( i.get("bar") );
       ClassBrowser cb= new ClassBrowser(i.getClassManager());
       JConsole jc = new JConsole();
       JFrame jf = new JFrame();
       jf.getContentPane().add(cb);
       jf.getContentPane().add(jc);
       jf.pack();
       jf.setVisible(true);
       
      }catch(Exception e){
          System.out.println(e.getMessage().toString());
          //System.exit(-2);
      }
      
      
      
  
      
        
    } */
    
    static public void GUITester(){
        
        StringListParameter slp = new StringListParameter("NAME","FIRSTVALUE");
        slp.addNewString("TESTINGVALUE1", false);
        slp.addNewString("TESTINGVALUE2", false);
        
        //JListEditor jles = new  JListEditor(slp);
        JListEditorWithSingleSelectionOption jlesd= new JListEditorWithSingleSelectionOption(slp);
      
        
    }
    
    public static String[] args;

    public static void startNOGUI(String f){

    }

    //public static int portNumberFOREMSTARTERGUI = 20000;
    
    public static void startClient(String ipAddress, String portNumber){
        try{
              
               Integer i = Integer.parseInt(portNumber);
               ConnectionToServer cts = new ConnectionToServer(ipAddress,i);
               cts.start();
           }
           catch(Exception e){
               System.err.println("Could not start client");
               e.printStackTrace();
           }       
           return;
    }
    
    
    public static void startServer(int portNumber){
         DemoModeChecker dmc = new DemoModeChecker();
         try {
            
             //System.setProperty("apple.laf.useScreenMenuBar", "true");
             //System.setProperty("com.apple.mrj.application.apple.menu.about.name", "DiET Chattool");
             UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
       
            
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "DiET Chattool");
             UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //  UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());

             try {
                 UIManager.setLookAndFeel(
                 UIManager.getCrossPlatformLookAndFeelClassName());
             } catch (Exception e) { }


         } catch (Exception e) { 
             System.exit(-1);
         } 
       EMUI emn= new EMUI(portNumber);
    }          
    
    
    
    public static void mainb (String [] args2){
        JEMStarter jem = new JEMStarter();
        jem.setVisible(true);
         
            
        
    }
    
    
    public static void checkVersion(){
        String version = System.getProperty("java.version");
        if(version.startsWith("1.1")||version.startsWith("1.2")||version.startsWith("1.3")||version.startsWith("1.4")
                ||version.startsWith("1.5")||version.startsWith("1.6")){
            CustomDialog.showDialog("This program requires at least java version 1.7.\n"
                    + "Your computer says your java version is: "+version +"\n"
                    + "Please close this progam and install the latest version of java.\n"
                    + "You can proceed with running the chat tool, but unless you know\n"
                    + "what you're doing, you will almost certainly get runtime errors\n");
        }
        
    }
    static File chattooljar = null;
    
    public static void testJARLOADER(){
         String s = System.getProperty("user.dir");
         File fDIR = new File(s);
         System.out.println(fDIR.getName());
         File fCHATTOOL = new File(fDIR,"chattool.jar");
         if(fCHATTOOL.exists()){
             chattooljar = fCHATTOOL;
             return;
         }
         else{
             System.err.println("Can't find "+fCHATTOOL.getAbsoluteFile());
         }
         fCHATTOOL = new File(fDIR,"chattoolprogram.jar");
         if(fCHATTOOL.exists()){
             chattooljar = fCHATTOOL;
             return;
         }
         else{
             System.err.println("Can't find "+fCHATTOOL.getAbsoluteFile());
         }
         fCHATTOOL = new File(fDIR+File.separator+"dist"+File.separator+"chattool.jar");
         if(fCHATTOOL.exists()){
             chattooljar = fCHATTOOL;
             return;
         }
         else{
             System.err.println("Can't find "+fCHATTOOL.getAbsoluteFile());
             CustomDialog.showDialog("For some reason, the chattool can't identify which folder it is being run from.\n"
                     + "This might mean that you cannot load some of the default mazegame setups.\n"
                     + "If this problem persists, please email g.j.mills@rug.nl with a description of the problem.");
             
         }
         //try to find "chattool.jar" 
         //if it can't...try to find chattoolprogram.jar
         //if it can't try and look in subdirectory //dist
         //if it can't then please show error....please email saying what the directory is and it will be fixed.
         
         
         //System.exit(-5);
         
    }
    
    
    
    
    
    
     public static void main (String [] args2){
        checkVersion();
       // testJARLOADER();
       // getMazesFromJar();
        String s = System.getProperty("user.dir");

        try{
        if(s.indexOf("dist")>-1){
            File f = new File(s);
            File f2 = f.getParentFile();
            System.setProperty("user.dir", f2.getCanonicalPath());
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        ClassLoad.fixUSERDIR(); ////This is to fix a bug in Java - sometimes when starting a JARFILE, Java doesn't identify the correct user directory
        
        System.out.println(s);
        
        //if(2<5)System.exit(-5);
        
        
        
         if (args2 == null || args2.length==0 || args2[0] ==null ||args2[0] =="" || args2[0] ==null ) {
            System.err.println("Checkpoint1a");
             try{
              System.err.println("Checkpoint1b");
             String sDirectory = System.getProperty("user.dir");
             System.err.println("Checkpoint1");
             File serverADDRESS = new File(sDirectory, "ip_address_of_server.txt") ;
              System.err.println("Checkpoint2");
             if(serverADDRESS.exists()){
                    System.err.println("Checkpoint2b");
                 BufferedReader in = new BufferedReader(new FileReader(serverADDRESS));
                 String descline = in.readLine();
                 String serveraddrline = in.readLine().replace("SERVER IP ADDRESS:", "");
                 String serverportline = in.readLine().replace("SERVER PORT:", "");
                 String autoStartClient = in.readLine().replace("START CLIENT AUTOMATICALLY:", "");
                 
                 
                  System.err.println("Checkpoint2c");
                 if(autoStartClient.contains("YES")||autoStartClient.contains("yes")){
                    System.err.println("Checkpoint3a");
                    CustomDialog.showModeLessDialog("The client is connecting automatically to: "+serveraddrline+",  port number: "+serverportline+"\n", 4000);    
                    Integer ii= Integer.parseInt(serverportline);
                    ConnectionToServer cts = new ConnectionToServer(serveraddrline,ii);
                    cts.start(); 
                    
                 }
                 else{
                       System.err.println("Checkpoint3b");
                     Integer serverportlineINT = Integer.parseInt(serverportline);             
                     JEMStarter jem =new JEMStarter(serveraddrline,serverportlineINT);              
                     jem.setVisible(true);    
                 }
                 
             }
             else{
                   System.err.println("Checkpoint4a");
                  JEMStarter jem = new JEMStarter();
                  jem.setVisible(true);
             }
             
           } catch (Exception e){
               e.printStackTrace();
               CustomDialog.showDialog("ERROR: "+e.getMessage());
               JEMStarter jem = new JEMStarter();
               jem.setVisible(true);
           }
          
           //System.exit(-324324);
         }
         else{
             processArgs(args2);
         }
    }   
    
    public static void processArgs (String [] args2){
      args = args2;
      if(args.length>1&&(args[0].equalsIgnoreCase("nogui")||args[0].equalsIgnoreCase("nogui_autologin")) ||args[0].equalsIgnoreCase("nogui_ccname_autologin") ||args[0].equalsIgnoreCase("nogui_ccname")){
            DemoModeChecker dmc = new DemoModeChecker();
            if(args[0].equalsIgnoreCase("nogui_autologin")||args[0].equalsIgnoreCase("nogui_ccname_autologin")){
                 Debug.doAUTOLOGIN = true;
             }
             String s = args[1];
             for(int i=2;i<args.length;i++){
                  s=s+" ";
                  s = s+args[i];
            }        
            //File generalSettingsFile = new File(System.getProperty("user.dir")+File.separator+"data"+File.separator+"General settings.xml");
            //Vector v =SavedExperimentsAndSettingsFile.readParameterObjects(generalSettingsFile);
            
           
            String separatorChar = File.separator;
            ExperimentSettings expSett = null;
            
             IntParameter portNo = new IntParameter("Port Number",20000);
             ExperimentSettings generalSett = new ExperimentSettings();
             generalSett.addParameter(portNo);
            
            ExperimentManager em = new ExperimentManager(null,generalSett);   
           
             
                    
            int numberOfClients = 0;
            if(args[0].equalsIgnoreCase("nogui_ccname")||args[0].equalsIgnoreCase("nogui_ccname_autologin")){
                 Conversation c =em.createAndActivateNewExperiment(args[1]);
                 Object o = c.getExperimentSettings().getClass();
                 numberOfClients = (Integer)c.getExperimentSettings().getV("Number of participants per conversation");
            }
            else{
                 String realPathname = System.getProperty("user.dir") + separatorChar + "data" + separatorChar + "Interventions" + separatorChar 				+ "Templates" + separatorChar + s;
		 System.err.println(":" + realPathname);
		 Vector vExp = SavedExperimentsAndSettingsFile.readParameterObjects(new File(realPathname));
	         expSett = new ExperimentSettings(vExp);
	         expSett.changeParameterValue("Experiment Data Folder", System.getProperty("user.dir")+ File.separator	+ "data"+ File.separator+ "Saved experimental data");
                 em.createAndActivateNewExperiment(expSett);
                 numberOfClients = (Integer)expSett.getV("Number of participants per conversation");
            }
             
            
          
            for (int i = 0; i < numberOfClients; i++) {
	         ConnectionToServer cts = new ConnectionToServer("localhost",portNo.getValue());
		 cts.start();
            }

        
    }


    if(args.length>=2&&args[0].equalsIgnoreCase("client")){
           try{
               //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");;
               Integer i = Integer.parseInt(args[2]);
               ConnectionToServer cts = new ConnectionToServer(args[1],i);
               cts.start();
           }
           catch(Exception e){
               System.err.println("Could not start client");
               e.printStackTrace();
           }       
           return;
    }
    
    DemoModeChecker dmc = new DemoModeChecker();
    
    if(args[0].startsWith("SERVER")){

        try {
            
             //System.setProperty("apple.laf.useScreenMenuBar", "true");
             //System.setProperty("com.apple.mrj.application.apple.menu.about.name", "DiET Chattool");
             UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
       
            
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "DiET Chattool");
             UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //  UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());

             try {
                 UIManager.setLookAndFeel(
                 UIManager.getCrossPlatformLookAndFeelClassName());
             } catch (Exception e) { }


         } catch (Exception e) { 
             System.exit(-1);
         } 
       EMUI emn= new EMUI(20000);
    }          
 }
    
    
    public static void write(){
        try{    
        XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("output.xml")));
        System.out.println("HEREA");
        StringParameter p = new StringParameter("IDNAMESTRING","DEFAULT","VALUE");
        IntParameter p2 = new IntParameter("IDNAMEINT",5,6);
        LongParameter p3 = new LongParameter("ID2NAMEINT",55,70);
        //StringParameterFixed p4 = new StringParameterFixed("this","that","then");
        Vector v2 = new Vector();
        v2.addElement("List item1");
        v2.addElement("List item2");
        v2.addElement("List item3");
        StringParameterFixed p5 = new StringParameterFixed("thisalso",1,v2,2);
        System.out.println("HEREB");
        e.writeObject(p);
        e.writeObject(p2);
        e.writeObject(p3);
        //e.writeObject(p4);
        e.writeObject(p5);
        System.out.println("HEREC");
        e.flush();
        e.close();
        read();
       e.close();
      }catch (Exception e){
          System.out.println("ERROR");
      }
    }     
      public static void read(){    
      try{
       System.out.println("HERE1");   
       XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream("Testbutton.xml")));
       System.out.println("HERE2C");
       Object result = d.readObject();
       System.out.println("HERE3");
        Parameter p = (StringParameter)result;
       System.out.println("PARAMETERIS "+p.getValue());
       //Object result2 = d.readObject();
       while(2<5){
           System.out.println("OBJECT "+result.getClass().toString());
           
           result = d.readObject();
          
               
       }
      
      }  catch(Exception e){
          System.out.println("ERROR "+e.toString());
          
      }
        
        
    }
    
    
}
