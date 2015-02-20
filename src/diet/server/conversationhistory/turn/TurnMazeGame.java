/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.server.conversationhistory.turn;

import java.util.Vector;

import diet.server.conversationhistory.Conversant;
import diet.server.conversationhistory.ConversationHistory;
import edu.stanford.nlp.trees.Tree;

/**
 *
 * @author Greg
 */
public class TurnMazeGame extends Turn{

    public String descType;
    public String isTSTCRRESPACKNEXT;
    
    public  TurnMazeGame (ConversationHistory cH,long timeOfCreationOnClient,long onset, long enter,Conversant sender, Conversant apparentSender,String text,
          Vector recipients,boolean wasBlocked,Vector keyPresses,Vector documentUpdates,Vector parsedWords,Object parseTree,int turnNo,String descType,String isTSTCRRESPACKNEXT,Object[] priorTurnByOther){
          super(cH,timeOfCreationOnClient,"TurnMazeGame",onset, enter,sender,  apparentSender, text, recipients, wasBlocked,keyPresses,documentUpdates,parsedWords,parseTree,turnNo,new Vector(),priorTurnByOther);
          this.descType = descType;
          this.isTSTCRRESPACKNEXT = isTSTCRRESPACKNEXT;
    }
    
    public String getDescType(){
        return descType;
    }
    
    public String isTSTCRRESPACKNEXT(){
        return this.isTSTCRRESPACKNEXT;
    }
}
