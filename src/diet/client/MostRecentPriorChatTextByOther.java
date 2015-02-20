/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.client;

import diet.message.MessageChatTextToClient;
import java.util.Date;

/**
 *
 * @author GM
 */
public class MostRecentPriorChatTextByOther{
         //This is used in order to have a record of what the participant was actually responding to!
        
         
    
         //MessageChatTextToClient mcttc;
         String apparentUsername="";
         String text ="";
         long localTimeOfReceipt =0;
         boolean wasInterruptedDuringFormulation = false;
         
        
         public synchronized void clearChatTextFromOtherParticipcant(){
             this.localTimeOfReceipt=0;
             this.apparentUsername="";
             this.text="";
             this.wasInterruptedDuringFormulation=false;
         }
         
         public synchronized void storeChatTextFromOtherParticipant(String username, String text){
             this.apparentUsername=username;
             this.text=text;
             this.localTimeOfReceipt=new Date().getTime();
             //System.exit(-23423);
         }
         public synchronized String[] getMostRecentChatTextFromOtherParticipant(){
              
              //if(2<5)return new String[]{"AAAA","BBBB","CCCC"};
              
              String[] retValue = new String[3];
              if(localTimeOfReceipt==0){
                  retValue[0]="";
                  retValue[1]="";
                  retValue[2]="";
                  return retValue;
              }
              
              retValue[0]= ""+localTimeOfReceipt;
              retValue[1]= apparentUsername;
              retValue[2]= text;
              return retValue;
          }
    }
