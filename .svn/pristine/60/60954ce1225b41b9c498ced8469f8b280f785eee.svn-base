/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.ConversationController.Interventions;

import diet.message.MessageChatTextFromClient;
import diet.server.Conversation;
import diet.server.Participant;
import java.util.Date;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author sre
 */
public class ClariIntervention  {
        
        
        
         static Random r = new Random();
        
         static Hashtable htTimeOfMostRecentReceiptOfIntervention = new Hashtable();
         static Hashtable htInterventionsRemaining = new Hashtable();
         
         static Vector clarifications = new Vector();
         
         static Conversation c;
         
         public ClariIntervention(Conversation c){
              clarifications.addElement("..what??");
              clarifications.addElement("sorry where?");
              clarifications.addElement("sry why?");
              this.c=c;
              
         }
         
         
   
         static public Vector getInterventionsRemaining(Object p){
              Object o = htInterventionsRemaining.get(p);  
               if(o==null){
                  Vector vNEW = getCopyOfVector(clarifications);
                  htInterventionsRemaining.put(p,vNEW);
                  System.err.println("ADDING");
                  
               }
              return (Vector)htInterventionsRemaining.get(p);  
         }
         
        
         
         
         
         static Vector currentRecipients = new Vector();
         
         
         
         static private void spendTimeInIntervention(final long timeInIntervention, final Participant recipient){
             final long finishTime = new Date().getTime()+timeInIntervention;
             Thread t = new Thread(){
                  public void run(){
                         while(new Date().getTime() < finishTime ){
                         try{
                             Thread.sleep(1000);
                             System.out.println("COUNTING DOWN DURING INTERVENTION "+(new Date().getTime() < finishTime));
                         }catch (Exception e){
                             e.printStackTrace();
                         }
                   
                  }
                         
                  currentRecipients.remove(recipient);
                  
             }  
        };
         t.start();
            
             
         }
         
         
         public synchronized static void processChatText(Participant sender, MessageChatTextFromClient mct, Participant recipient, long minTimeSincePreviousIntervention, final long timeGivenToInterventionResponse){
              if(   currentRecipients.contains(recipient)){
                  c.setNewTurnBeingConstructedNotRelayingOldTurnAddingOldTurnToHistory(sender, mct, "(DELAYED TURN BY APPARENT SENDER OF CR)"); 
                  c.sendDelayedTextToParticipant(sender,recipient,mct.getText(),timeGivenToInterventionResponse);          
                  return;
              }
              if(   currentRecipients.contains(sender)){
                  c.setNewTurnBeingConstructedNotRelayingOldTurnAddingOldTurnToHistory(sender, mct, "(RESPONSE)"); 
                  c.relayTurnToParticipant(sender, recipient, mct);         
                  return;
              }
              
              
              String intervention = getIntervention(sender,mct.getText(),recipient,minTimeSincePreviousIntervention);
              if(intervention!=null){
                   c.sendArtificialTurnFromApparentOriginToRecipient(sender, recipient, intervention, "(INTERVENTION)");
                   c.sendDelayedTextToParticipant(sender, recipient, mct.getText(), timeGivenToInterventionResponse);
                   c.setNewTurnBeingConstructedNotRelayingOldTurnAddingOldTurnToHistory(sender, mct, intervention);
                   currentRecipients.add(recipient);
                   spendTimeInIntervention(timeGivenToInterventionResponse,recipient);
              }
              else{
                  c.relayTurnToParticipant(sender, recipient, mct);
           
              }
         }
        
          public synchronized static void processMazeChatText(Participant sender, MessageChatTextFromClient mct, Participant recipient, long minTimeSincePreviousIntervention, final long timeGivenToInterventionResponse,
                 int mazeNo, int moveNo, int indivMveNo ){
              if(   currentRecipients.contains(recipient)){
                  //c.setNewTurnBeingConstructedNotRelayingOldTurnAddingOldTurnToHistory(sender, mct, "DELAYED TURN"); 
                  c.setNewMazeGameTurnBeingConstructedNotRelayingOldTurnAddingOldTurnToHistory(sender, mct, mazeNo, moveNo, indivMveNo, "(DELAYED TURN  BY APPARENT SENDER OF CR)");
                  c.sendDelayedTextToParticipant(sender,recipient,mct.getText(),timeGivenToInterventionResponse);          
                  return;
              }
               if(   currentRecipients.contains(sender)){
                   c.setNewMazeGameTurnBeingConstructedNotRelayingOldTurnAddingOldTurnToHistory(sender, mct, mazeNo, moveNo, indivMveNo, "(RESPONSE)");
                    c.relayMazeGameTurnToParticipant(sender, recipient, mct, mazeNo, moveNo, indivMveNo);
                   return;
               }
              String intervention = getIntervention(sender,mct.getText(),recipient,minTimeSincePreviousIntervention);
              if(intervention!=null){
                   c.sendArtificialTurnFromApparentOriginToRecipient(sender, recipient, intervention, "(INTERVENTION)");
                   c.setNewMazeGameTurnBeingConstructedNotRelayingOldTurnAddingOldTurnToHistory(sender, mct, mazeNo, moveNo, indivMveNo,"(PREINTERVENTION-REPLACEDTURN)");         
                   currentRecipients.add(recipient);
                   spendTimeInIntervention(timeGivenToInterventionResponse,recipient);
              }
              else{
                  c.relayMazeGameTurnToParticipant(sender, recipient, mct, mazeNo, moveNo, indivMveNo);
           
              }
         }
         
         
         
         
         
         
         public static String getIntervention(Participant sender, String text, Participant recipient, long timeSincePrevious){
             
             long mostRecentTimeOfReceipt =0;      
             Object o =  htTimeOfMostRecentReceiptOfIntervention.get(recipient);
             if (o!= null) {
                 mostRecentTimeOfReceipt = (Long)o ;
                 System.err.println("MostRecentTimeOfReceiptAAA1"+mostRecentTimeOfReceipt);
             }
             else{
                 System.err.println("MostRecentTimeOfReceiptAAA2"+mostRecentTimeOfReceipt);
             }
              
             long timeSinceLastIntervention = new Date().getTime()-mostRecentTimeOfReceipt; 
             
             
             System.err.println("MostRecentTimeOfReceiptB"+mostRecentTimeOfReceipt);
             System.err.println("TIME SINCE INTERVENTION "+timeSinceLastIntervention) ;
             
              
             if((new Date().getTime()-mostRecentTimeOfReceipt)<timeSincePrevious){
                 System.err.println("TRYING TO GENERATE INTERVENTION TOO SOON");
                 return null;
             }
            
             
             Vector vRemainingInterventions =  getInterventionsRemaining(sender);
             if(vRemainingInterventions.size()<1){
                 System.err.println("HAVE GENERATED ALL THE INTERVENTIONS FOR "+sender.getUsername());
                 return null;
             }
             int i = r.nextInt(vRemainingInterventions.size());
             String s = (String) vRemainingInterventions.elementAt(i);
             vRemainingInterventions.remove(s);
             htInterventionsRemaining.put(sender,vRemainingInterventions);
             htTimeOfMostRecentReceiptOfIntervention.put(recipient,new Date().getTime());
             return s;
         }
             
             
        
        
        
         
         
          public static Vector getCopyOfVector(Vector v){
             Vector v2 = new Vector();
             for(int i=0;i<v.size();i++){
                 Object o = v.elementAt(i);
                 v2.addElement(v.elementAt(i));
                 //System.out.println("ADDING "+i);
             }
             return v2;
         }
   
          
          
          
          
          public static void main(String[] args){
               ClariIntervention ci = new ClariIntervention(null);
               Participant pSender = new Participant(null,"p1ID", "p1Username");
               Participant pRecipient = new Participant(null,"p2ID", "p2Username");
               System.err.println(ClariIntervention.getIntervention(pSender, null, pRecipient,500));
               try{ Thread.sleep(1000);} catch (Exception e){}
               System.err.println(ClariIntervention.getIntervention(pSender, null, pRecipient,500));
               try{ Thread.sleep(2000);} catch (Exception e){}               
               System.err.println(ClariIntervention.getIntervention(pSender, null, pRecipient,500));
               //System.err.println( ClariIntervention.processChatText(pSender, "", pRecipient));
               try{ Thread.sleep(1000);} catch (Exception e){}               
               System.err.println(ClariIntervention.getIntervention(pSender, null, pRecipient,500));
               
               
               System.err.println(""); System.err.println(""); System.err.println(""); System.err.println(""); System.err.println(""); System.err.println("");
               try{ Thread.sleep(1000);} catch (Exception e){}
               System.err.println(ClariIntervention.getIntervention(pRecipient, null, pSender,500));
               try{ Thread.sleep(2000);} catch (Exception e){}               
               System.err.println(ClariIntervention.getIntervention(pRecipient, null, pSender,500));
               //System.err.println( ClariIntervention.processChatText(pSender, "", pRecipient));
               try{ Thread.sleep(1000);} catch (Exception e){}               
               System.err.println(ClariIntervention.getIntervention(pRecipient, null, pSender,500));
                try{ Thread.sleep(1000);} catch (Exception e){}               
               System.err.println(ClariIntervention.getIntervention(pRecipient, null, pSender,500));
          }
}
