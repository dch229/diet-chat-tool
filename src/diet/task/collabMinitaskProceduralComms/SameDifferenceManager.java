/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.task.collabMinitaskProceduralComms;

import diet.server.ConversationController.CCGROOP3SEQ4;
import diet.server.Participant;
import java.lang.String;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author sre
 */
public class SameDifferenceManager {
    
    Random r = new Random();
    JSDM_4WAYConversationControllerINTERFACE cC;
    public Participant a;
    public Participant b;
    public Participant c;
    public Participant d;
    
   
    
    
    
    
   
    Vector transitions = new Vector();
    
    
    
    
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
    
    int stage =-1;
    
    
    public  SameDifferenceManager(JSDM_4WAYConversationControllerINTERFACE cC,String[][] tP,Participant a, Participant b, Participant c, Participant d){
        this.cC=cC;
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
               cC.initializeGUIWITHSTATES(transitions);
               
               
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
    
    
    public SameDifferenceManager(JSDM_4WAYConversationControllerINTERFACE cC,Participant a, Participant b, Participant c, Participant d){
           this.cC=cC;
           this.a=a;this.b=b;this.c=c;this.d=d;
                    
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
               cC.initializeGUIWITHSTATES(transitions);
               
               
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
    
    
    public int getStageNumber(){
        return this.stage;
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
    
    
    private void goStagePHYSICAL(String sameORdiff){
        
          if(!sameORdiff.equalsIgnoreCase("diff"))return;
          Vector allParticipants = (Vector)cC.getC().getParticipants().getAllParticipants().clone();
          physicalPartnerOfA=null;physicalPartnerOfB=null;physicalPartnerOfC=null;physicalPartnerOfD=null;
          while(physicalPartnerOfA==null || physicalPartnerOfB==null ||physicalPartnerOfC==null||physicalPartnerOfD==null){
              System.err.println("TRANSITIONDEBUGPHYSICALLOOP");
              Participant pCandidate = (Participant)allParticipants.elementAt(r.nextInt(allParticipants.size()));
              //FOR A
              if(physicalPartnerOfA==null&pCandidate!=a & !priorPhysicalPartnersOfA.contains(pCandidate) & physicalPartnerOfB!=pCandidate&physicalPartnerOfC!=pCandidate&physicalPartnerOfD!=pCandidate){
                  physicalPartnerOfA=pCandidate;
                  priorPhysicalPartnersOfA.addElement(pCandidate);
                  if(pCandidate==c)  {physicalPartnerOfC=a; priorPhysicalPartnersOfC.addElement(a);convo1[0]=a;convo1[1]=c;}
                  if(pCandidate==d)  {physicalPartnerOfD=a; priorPhysicalPartnersOfD.addElement(a);convo1[0]=a;convo1[1]=d;}
              }
              //FORB
              if(physicalPartnerOfB==null&pCandidate!=b & !priorPhysicalPartnersOfB.contains(pCandidate) & physicalPartnerOfA!=pCandidate&physicalPartnerOfC!=pCandidate&physicalPartnerOfD!=pCandidate){
                  physicalPartnerOfB=pCandidate;
                  priorPhysicalPartnersOfB.addElement(pCandidate);
                  if(pCandidate==c)  {physicalPartnerOfC=b; priorPhysicalPartnersOfC.addElement(b);convo2[0]=b;convo2[1]=c;}
                  if(pCandidate==d)  {physicalPartnerOfD=b; priorPhysicalPartnersOfD.addElement(b);convo2[0]=b;convo2[1]=d;}
              }
              
              
          }
          
          
          
         
          System.err.println(this.getPhysicalPartners());
          
          
          
          
    }
    
 
    
    public synchronized void displaySD(String ID){
         for(int i=0;i<transitions.size();i++){
            String[] t = (String[])transitions.elementAt(i);
            System.out.println(i+"TRANSL2-----("+ID+")----"+stage+"---------------------------------"+t[0]+"----------"+t[1]);
            
        }
    }
    
    public int getStage(){
        return stage;
    }
    
    
    public synchronized void goNextStage(){
       
        
        stage = stage+1;
        String[] transition = (String[] )this.transitions.elementAt(stage);
        System.err.println("TRANSDEBUG1....looking for"+transition[0]+"............."+transition[1]);
        this.goStagePHYSICAL(transition[0]);
        this.goStageAPPARENT(transition[1]);
         
         
         
        
        //System.exit(-5);
         
    }
    
    String[] dbgc = new String[2];
    
    
    
    public String getDescription(){
        if(stage <0 ) return "PRACTICEPHASE";
        //return stage+"_"+current[0]+current[1];
        if(stage>=transitions.size())return "FINISHED:"+stage;
        return    stage+"_"+ ((String[])transitions.elementAt(stage))[0]+((String[])transitions.elementAt(stage))[1];
    }
    
    
    public String[] getApparentPartnerChanges(){
        String[] apparentPartnerChanges = new String[4];
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
    
    
    
    
    
    private void goStageAPPARENT(String sameORdiff){
          if(!sameORdiff.equalsIgnoreCase("diff"))return;
          Vector allParticipants = (Vector)cC.getC().getParticipants().getAllParticipants().clone();
          apparentPartnerOfA=null;apparentPartnerOfB=null;apparentPartnerOfC=null;apparentPartnerOfD=null;
          while(apparentPartnerOfA==null || apparentPartnerOfB==null ||apparentPartnerOfC==null||apparentPartnerOfD==null){
               System.err.println("TRANSDEBUG2");
              Participant pCandidate = (Participant)allParticipants.elementAt(r.nextInt(allParticipants.size()));
              //FOR A
              if(apparentPartnerOfA==null&pCandidate!=a & !priorApparentPartnersOfA.contains(pCandidate) & apparentPartnerOfB!=pCandidate&apparentPartnerOfC!=pCandidate&apparentPartnerOfD!=pCandidate){
                  apparentPartnerOfA=pCandidate;
                  priorApparentPartnersOfA.addElement(pCandidate);
                  if(pCandidate==c)  {apparentPartnerOfC=a; priorApparentPartnersOfC.addElement(a);}
                  if(pCandidate==d)  {apparentPartnerOfD=a; priorApparentPartnersOfD.addElement(a);}
              }
              //FORB
              if(apparentPartnerOfB==null&pCandidate!=b & !priorApparentPartnersOfB.contains(pCandidate) & apparentPartnerOfA!=pCandidate&apparentPartnerOfC!=pCandidate&apparentPartnerOfD!=pCandidate){
                  apparentPartnerOfB=pCandidate;
                  priorApparentPartnersOfB.addElement(pCandidate);
                  if(pCandidate==c)  {apparentPartnerOfC=b; priorApparentPartnersOfC.addElement(b);}
                  if(pCandidate==d)  {apparentPartnerOfD=b; priorApparentPartnersOfD.addElement(b);}
              }
              
              
          }
          
          
          
          
          System.err.println(this.getApparentPartners());
          
          
          
          
    }
    
    
    
    
    
}
