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
public class MAZEGAMETURN extends Turn{

    public int mazeNo;
    public int moveNo;
    public int indivMoveNo;
    
    public MAZEGAMETURN(){
    }
    
    public MAZEGAMETURN(String cvsPREFIX, ConversationHistory cH, long timeOfCreationOnClient,long onset, long enter, Conversant sender, Conversant apparentSender, String text, Vector recipients, boolean wasBlocked, Vector keyPresses, Vector documentUpdates, Vector parsedWords, Object parseTree, int turnNo,int mazeNo,int moveNo,int indivMoveNo, Object[] priorTurnByOther) {
        super(cH, timeOfCreationOnClient, cvsPREFIX+"MAZEGAMETURN",onset, enter, sender, apparentSender, text, recipients, wasBlocked, keyPresses, documentUpdates, parsedWords, parseTree, turnNo, new Vector(),priorTurnByOther);
        this.mazeNo=mazeNo;
        this.moveNo=moveNo;
        this.indivMoveNo=indivMoveNo;
    }

    @Override
    public String getIOAdditionalHeaders() {
        return "|"+"MazeNo"+"|"+"AbsMoveNo"+"|"+"IndivMoveNo";
    }

    @Override
    public String getIOAdditionalValues() {
        return "|"+mazeNo+"|"+moveNo+"|"+indivMoveNo;
    }
    
    
    
}
