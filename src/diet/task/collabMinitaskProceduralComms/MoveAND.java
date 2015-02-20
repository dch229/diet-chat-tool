/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.collabMinitaskProceduralComms;

import diet.server.Participant;
import java.util.Date;

/**
 *
 * @author sre
 */
public class MoveAND extends Move {

    Participant p1;
    Participant p2;
    String name;

    public static long maxTimeBetweenPerformances = 3000;
    long timeOfFirstSelect;

    Participant firstHalf;
    Participant secondHalf;


    public MoveAND(Participant p1,Participant p2, String name){
        this.p1=p1;
        this.p2=p2;
        this.name=name;
    }

    public String getWordForParticipantAsHTMLOLD(Participant p){
         return "<B><I>"+name+"</B></I>";
    }
   public String getWordForParticipantAsHTML(Participant p){
         return "["+name+"";
    }
    public String getWordForParticipant(Participant p){
         return name;
    }

    @Override
    public String getName() {
        return name;
    }

     public String getType(){
        return "AND";
    }
    
    
    @Override
    public String evaluate(Participant p, String name, boolean isLastInSequence) {
        if(p!=p1&p!=p2) return Moves.errorTriedToSelectAnother;
        else if(!isLastInSequence & this.isSuccessful()) {
            this.setUnSuccessful();
            firstHalf=p;
             secondHalf=null;
             timeOfFirstSelect = new Date().getTime();
            return Moves.intermediateMessageReselectedFirstHalfOfAND;
        }
        else if(this.isSuccessful()){

            //there's a bug here - if they try to go back to thi -it'll need to save the old state and a pssible intermediate state while they are tryi
            //do it so that when it goes back it automatically treats the following as deselected
            //        and if the going back was correct, then it is, otherwise, it isn't'

            return Moves.wordHasAlreadyBeenSelectedByBoth;
        }
        else if(isLastInSequence && firstHalf == null & secondHalf == null){
            firstHalf=p;
            timeOfFirstSelect = new Date().getTime();
            return Moves.intermediateMessageSimultaneousANDCorrect;
        }
        else if(isLastInSequence && firstHalf == p ) {
            firstHalf=p;
            secondHalf=null;
            timeOfFirstSelect = new Date().getTime();
            return Moves.errorWordIsAlreadySelectedByRequestor;
        }
        else if(isLastInSequence && firstHalf !=p & secondHalf == null ) {
            long timeOfSecondPart = new Date().getTime();
            if(this.timeOfFirstSelect+this.maxTimeBetweenPerformances>timeOfSecondPart){  ///Made it
                secondHalf =p;
                this.setSuccessful();
                return Moves.correctSIMULTANEOUSWORD;
            }
            else{
                firstHalf=p;
                secondHalf=null;
                timeOfFirstSelect = new Date().getTime();
                return Moves.incorrectSecondMoveCorrectFirstMove;
            }

        }

            firstHalf=null;
            secondHalf=null;
            this.setUnSuccessful();
            return Moves.errorSIMULTANEOUS;      
     }

    @Override
    public void setUnSuccessful() {
        this.firstHalf=null;
        this.secondHalf=null;
        this.timeOfFirstSelect=0;
        super.setUnSuccessful();
    }

public void setName(String s){
        this.name=s;
    }


}
