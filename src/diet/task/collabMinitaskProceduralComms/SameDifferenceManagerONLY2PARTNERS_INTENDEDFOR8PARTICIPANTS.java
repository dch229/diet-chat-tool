/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.task.collabMinitaskProceduralComms;

import diet.server.Conversation;
import diet.server.Participant;
import java.lang.String;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author sre
 */
public class SameDifferenceManagerONLY2PARTNERS_INTENDEDFOR8PARTICIPANTS {
    
    
    
    
    
    static public Participant p1;
    static public Participant p2;
    static public Participant p3;
    static public Participant p4;
    
    static public Participant p5;
    static public Participant p6;
    static public Participant p7;
    static public Participant p8;
    
    
    
    static public synchronized void addParticipantsToClass(Participant pA, Participant pB, Participant pC, Participant pD){
        if(p1==null){
            p1=pA; p2=pB;p3=pC;;p4=pD;
            
        }
        else{
            p5=pA; p6=pB;p7=pC;p8=pD;
        }
    }
    
   
    
    
    
    
    
    
    
    Random r = new Random();
    JSDM_4WAYConversationControllerINTERFACE cC;
    public Participant a;
    public Participant b;
    public Participant c;
    public Participant d;
    
   
    
    
    
    
   
    Vector transitions = new Vector();
    public String[] currTransition = {"INITIAL","INITIAL"};
    
    
    
    Vector  priorPhysicalPartnersOfA = new Vector();
    Vector  priorPhysicalPartnersOfB = new Vector();
    Vector  priorPhysicalPartnersOfC = new Vector();
    Vector  priorPhysicalPartnersOfD = new Vector();
    
    
    Participant physicalPartnerOfA;
    Participant physicalPartnerOfB;
    Participant physicalPartnerOfC;
    Participant physicalPartnerOfD;
    
    
    Vector  priorApparentPartnersOfA = new Vector();
    Vector  priorApparentPartnersOfB = new Vector();
    Vector  priorApparentPartnersOfC = new Vector();
    Vector  priorApparentPartnersOfD = new Vector();
    
    
    Participant apparentPartnerOfA;
    Participant apparentPartnerOfB;
    Participant apparentPartnerOfC;
    Participant apparentPartnerOfD;
    
    Participant[] convo1 = new Participant[2];
    Participant[] convo2 = new Participant[2];

    public Participant[] getPhysicalConvo1() {
        return convo1.clone();
    }

    public Participant[] getPhysicalConvo2() {
        return convo2;
    }

    

   
    
    public Participant getPhysicalPartner(Participant source){
        if(source==a)return this.physicalPartnerOfA;
        if(source==b)return this.physicalPartnerOfB;
        if(source==c)return this.physicalPartnerOfC;
        if(source==d)return this.physicalPartnerOfD;
        return null;
    }
    
    
    
    //String[] current = new String[2];
    
    //int stage =-1;
    JSDM jsd;
    
    public  SameDifferenceManagerONLY2PARTNERS_INTENDEDFOR8PARTICIPANTS(JSDM jsd,JSDM_4WAYConversationControllerINTERFACE cC,String[][] tP,Participant a, Participant b, Participant c, Participant d){
        addParticipantsToClass(a,b,c,d);
        this.cC=cC;
        this.jsd=jsd;
        this.a=a;this.b=b;this.c=c;this.d=d;
        transitions.addElement(tP[0]); transitions.addElement(tP[1]); transitions.addElement(tP[2]); transitions.addElement(tP[3]);
         String transitionsTOSAVE = "";   
        for(int i=0;i<transitions.size();i++){
            String[] t = (String[])transitions.elementAt(i);
            transitionsTOSAVE= transitionsTOSAVE +"  "+ t[0]+t[1];            
        }  
        cC.getC().saveDataToConversationHistoryDEPRECATED(transitionsTOSAVE);
           
           try{
               //cC.jsdm.setStage(initializeWITHST0, ((String[])transitions.elementAt(0))[0]+((String[])transitions.elementAt(0))[1]);
               //cC.initializeGUIWITHSTATES(transitions);
               jsd.initializeWITHSTATES(transitions);    
               
           }catch (Exception e){
               e.printStackTrace();
           }
           
           
           physicalPartnerOfA=b;  priorPhysicalPartnersOfA.addElement(b);
           physicalPartnerOfB=a;  priorPhysicalPartnersOfB.addElement(a);
           physicalPartnerOfC=d;  priorPhysicalPartnersOfC.addElement(d);
           physicalPartnerOfD=c;  priorPhysicalPartnersOfD.addElement(c);
           
           this.convo1[0]= a;
           this.convo1[1]= b;
           this.convo2[0]= c;
           this.convo2[1]= d;
           
           
           apparentPartnerOfA=b;  priorApparentPartnersOfA.addElement(b);
           apparentPartnerOfB=a;  priorApparentPartnersOfB.addElement(a);
           apparentPartnerOfC=d;  priorApparentPartnersOfC.addElement(d);
           apparentPartnerOfD=c;  priorApparentPartnersOfD.addElement(c);    
    }
    
    
    public SameDifferenceManagerONLY2PARTNERS_INTENDEDFOR8PARTICIPANTS(JSDM jsd,JSDM_4WAYConversationControllerINTERFACE cC,Participant a, Participant b, Participant c, Participant d){
           this.cC=cC;
           this.a=a;this.b=b;this.c=c;this.d=d;
           addParticipantsToClass(a,b,c,d);
           String[][] tP = {{"same","same"},
                                  {"same","diff"},
                                  {"diff","same"},
                                  {"diff","diff"}
           };
        Vector possibleTransitions = new Vector();
        possibleTransitions.addElement(tP[0]); possibleTransitions.addElement(tP[1]); possibleTransitions.addElement(tP[2]); possibleTransitions.addElement(tP[3]);
        Vector ppossibleTransitions = (Vector)possibleTransitions.clone();
        
        while(ppossibleTransitions.size()>0){
            int i = r.nextInt(ppossibleTransitions.size());     
            String[] transition = (String[] )ppossibleTransitions.elementAt(i);
            ppossibleTransitions.removeElement(transition);
            
            this.transitions.addElement(transition);
            System.err.println("ADDINGTOTRANSITIONS "+transition[0]+"....."+transition[1]);
        }
            
        String transitionsTOSAVE = "";   
        for(int i=0;i<transitions.size();i++){
            String[] t = (String[])transitions.elementAt(i);
            transitionsTOSAVE= transitionsTOSAVE +"  "+ t[0]+t[1];            
        }  
        
        
        
           cC.getC().saveDataToConversationHistoryDEPRECATED("");
           cC.getC().saveDataToConversationHistoryDEPRECATED("");
           cC.getC().saveDataToConversationHistoryDEPRECATED(transitionsTOSAVE);
           
           try{
               //cC.jsdm.setStage(initializeWITHST0, ((String[])transitions.elementAt(0))[0]+((String[])transitions.elementAt(0))[1]);
               //cC.initializeGUIWITHSTATES(transitions);
               jsd.initializeWITHSTATES(transitions);    
               
           }catch (Exception e){
               e.printStackTrace();
           }
           
           
           physicalPartnerOfA=b;  priorPhysicalPartnersOfA.addElement(b);
           physicalPartnerOfB=a;  priorPhysicalPartnersOfB.addElement(a);
           physicalPartnerOfC=d;  priorPhysicalPartnersOfC.addElement(d);
           physicalPartnerOfD=c;  priorPhysicalPartnersOfD.addElement(c);
           
           this.convo1[0]= a;
           this.convo1[1]= b;
           this.convo2[0]= c;
           this.convo2[1]= d;
           
           
           apparentPartnerOfA=b;  priorApparentPartnersOfA.addElement(b);
           apparentPartnerOfB=a;  priorApparentPartnersOfB.addElement(a);
           apparentPartnerOfC=d;  priorApparentPartnersOfC.addElement(d);
           apparentPartnerOfD=c;  priorApparentPartnersOfD.addElement(c);
           
           
           
    }
    
    
    
    
    
    public void setTransitions(String samediffsamediffsamediffETC){
        char[] sd = samediffsamediffsamediffETC.toCharArray(); //"sd-ds-ds-dd";
        
         String s0a,s0p,s1a,s1p,s2a,s2p,s3a,s3b;
         
         
        if(sd[0] =='s')  s0a="same"; //WHAT THE HELL IS THIS?
        if(sd[1] =='s')  s0p ="same";
        if(sd[3] =='s')  s1a="same";
        if(sd[4] =='s')  s1p="same";
        if(sd[6] =='s')  s2a="same";
        if(sd[7] =='s')  s2p="same";
        if(sd[9] =='s')  s3a="same";
        if(sd[10]=='s')  s3b="same";
            
        if(sd[0] =='d')  s0a="diff";
        if(sd[1] =='d')  s0p ="diff";
        if(sd[3] =='d')  s1a="diff";
        if(sd[4] =='d')  s1p="diff";
        if(sd[6] =='d')  s2a="diff";
        if(sd[7] =='d')  s2p="diff";
        if(sd[9] =='d')  s3a="diff";
        if(sd[10]=='d')  s3b="diff";
    }
    
    
    
   public String getPhysicalPartners(){
        return  a.getUsername()+ " -- " + physicalPartnerOfA.getUsername() + "\n"+
                b.getUsername()+ " -- " + physicalPartnerOfB.getUsername() + "\n"+
                c.getUsername()+ " -- " + physicalPartnerOfC.getUsername() + "\n"+
                d.getUsername()+ " -- " + physicalPartnerOfD.getUsername() +"\n";
        
    }
    
   //A    B
   //C    D
   
    
    public void goStagePHYSICAL(String sameORdiff){
        
          if(!sameORdiff.equalsIgnoreCase("diff"))return;
          if(physicalPartnerOfA==b){
              physicalPartnerOfA = c;
              physicalPartnerOfB = d;
              physicalPartnerOfC=a;
              physicalPartnerOfD =b;
          }
          else if(physicalPartnerOfA==c){
              physicalPartnerOfA=b;
              physicalPartnerOfB=a;
              physicalPartnerOfC=d;
              physicalPartnerOfD=c;
              
          }
          else{
              System.err.println("A VERY SERIOUS ERROR");
              Conversation.printWSln("Main", "A very serious error");
          }
          
          
          
          
          
          
          
          
         
          System.err.println(this.getPhysicalPartners());
          
          
          
          
    }
    
 
    
    public synchronized void displaySD(String ID){
         for(int i=0;i<transitions.size();i++){
            String[] t = (String[])transitions.elementAt(i);
            System.out.println(i+"TRANSL2-----("+ID+")-------------------------------------"+t[0]+"----------"+t[1]);
            
        }
    }
    
    
   
    
    
    public synchronized void goNextStage(int stage){
        try{
        
        
        if(stage>= transitions.size()){
            System.err.println("TRANSDEBUG_GONE INTO NEXT STAGE OF EXPERIMENT...MAKING DIFFERENT DIFFERENT.."+stage);
            this.goStageAPPARENT("diff");
            this.goStagePHYSICAL("diff");
           // System.exit(-5);
        }
        else{
           currTransition = (String[] )this.transitions.elementAt(stage);
           System.err.println("TRANSDEBUG1....looking for"+currTransition[0]+"............."+currTransition[1]);
           this.goStagePHYSICAL(currTransition[0]);
           this.goStageAPPARENT(currTransition[1]);
        }
        }catch(Exception e){
            e.printStackTrace();
        }
         
        
        //System.exit(-5);
         
    }
    
    String[] dbgc = new String[2];
    
    
    
    public String getDescription(){
        
        return   currTransition[0]+currTransition[1];
    }
    
    
    
    public String[] getApparentPartnerChanges(){
        String[] apparentPartnerChanges = new String[transitions.size()];
        for(int i=0;i<this.transitions.size();i++){
            String[] transition = (String[])transitions.elementAt(i);
            apparentPartnerChanges[i]=transition[1];
        }
        return apparentPartnerChanges;
    }
    
    
    
    public Participant getApparentPartner(Participant source){
        if(source==a)return this.apparentPartnerOfA;
        if(source==b)return this.apparentPartnerOfB;
        if(source==c)return this.apparentPartnerOfC;
        if(source==d)return this.apparentPartnerOfD;
        return null;
    
    }
    
    
    
    public String getApparentPartners(){
        return  a.getUsername()+ " -- " + apparentPartnerOfA.getUsername() + "\n"+
                b.getUsername()+ " -- " +apparentPartnerOfB.getUsername() + "\n"+
                c.getUsername()+ " -- " + apparentPartnerOfC.getUsername() + "\n"+
                d.getUsername()+ " -- " + apparentPartnerOfD.getUsername() +"\n";
        
    }
    
    
    
    
   
    public Participant getFreshApparentSpeaker(Participant p){
        int idXSTATE = cC.getIDXState();
        if( idXSTATE==1){
                   ///FIRST DIFFERENT APPARENT
             //if(2<5)System.exit(-2349);
             System.exit(-3);
             if(p==p1)return p2;    if(p==p2)return p1;
             if(p==p3)return p4;    if(p==p4)return p3;  
             if(p==p5)return p6;    if(p==p6)return p5;
             if(p==p7)return p8;    if(p==p8)return p7;
        }
         else if(idXSTATE==2){
                  //SECOND DIFFERENT APPARENT
             //if(2<5)System.exit(-23490);
             System.exit(-456);
             if(p==p1)return p5;    if(p==p5)return p1;
             if(p==p2)return p6;    if(p==p6)return p2;  
             if(p==p3)return p7;    if(p==p7)return p3;
             if(p==p4)return p8;    if(p==p8)return p4;
         }
         else if(idXSTATE==3){
                 //INTERMEDIATE1
             if(p==p1)return p6;    if(p==p6)return p1;
             if(p==p2)return p5;    if(p==p5)return p2;  
             if(p==p3)return p8;    if(p==p8)return p3;
             if(p==p4)return p7;    if(p==p7)return p4;
         }
         else if(idXSTATE==4){
                 //INTERMEDIATE2
             if(p==p1)return p7;    if(p==p7)return p1;
             if(p==p2)return p8;    if(p==p8)return p2;  
             if(p==p3)return p5;    if(p==p5)return p3;
             if(p==p4)return p6;    if(p==p6)return p4;
         }
          else if(idXSTATE==5){
                  //INTERMEDIATE3
             if(p==p1)return p7;    if(p==p7)return p1;
             if(p==p2)return p8;    if(p==p8)return p2;  
             if(p==p3)return p5;    if(p==p5)return p3;
             if(p==p4)return p6;    if(p==p6)return p4;
         }
          else if(idXSTATE==6){
                  //BW
             if(p==p1)return p4;    if(p==p4)return p1;
             if(p==p2)return p3;    if(p==p3)return p2;  
             if(p==p5)return p8;    if(p==p8)return p5;
             if(p==p6)return p7;    if(p==p7)return p6;
         }
          else if(idXSTATE>6){
                  //BW
             if(p==p1)return p4;    if(p==p4)return p1;
             if(p==p2)return p3;    if(p==p3)return p2;  
             if(p==p5)return p8;    if(p==p8)return p5;
             if(p==p6)return p7;    if(p==p7)return p6;
         } 
        
        
        
        
        if(2<5)System.exit(-2349);
        
        if(p1==null)System.exit(-5555);
        System.err.println("THESIZEWAS "+idXSTATE);
        System.exit(-324234213);
        
        
        return null;
    }
    
    
    
    
    public void goStageAPPARENT(String sameORdiff){
          if(!sameORdiff.equalsIgnoreCase("diff"))return;
          
          apparentPartnerOfA = getFreshApparentSpeaker(a);
          apparentPartnerOfB = getFreshApparentSpeaker(b);
          apparentPartnerOfC = getFreshApparentSpeaker(c);
          apparentPartnerOfD = getFreshApparentSpeaker(d);
         
          if(apparentPartnerOfA==null){
              System.exit(-234);
          }
          if(a==null)System.exit(-3245);
          
          System.err.println(this.getApparentPartners());      
          
    }
    
    
    
    
    
}
