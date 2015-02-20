/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.collabMinitaskProceduralComms;

import diet.server.Participant;

/**
 *
 * @author sre
 */
public class Move {

    private boolean successful;
    private String priorMoveType ="";

    public String getName(){
        return "";
    }

    public boolean hasAlreadyBeenCoordinatedOn(){
        return successful;
    }

    public void setSuccessful(){
        successful=true;
    }

    public boolean isSuccessful(){
       return successful;
    }

    public void setUnSuccessful(){
        successful=false;
    }

    public String getWordForParticipantAsHTML(Participant p){
        return null;
    }
    public String getWordForParticipant(Participant p){
        return null;
    }

    public String evaluate(Participant p, String name, boolean isLastInSequence){
       return "ERRORINSOFTWARE";
    }

    public void setName(String s){
        
    }
    
    public String getType(){
        return "MOVE";
    }

    public String getPriorMoveType(){
        return this.priorMoveType;
    }

    public void setPriorMoveType(String s){
        this.priorMoveType=s;
    }
    

    /*/ACTIONNAME
     *
     * Move    (NOT RIGHT NAME BUT NEXT ONE IS CORRECT)
     * Move    (NOT RIGHT NAME BUT NEXT ONE IS CORRECT)
     * Move     (NOT RIGHT NAME BUT NEXT ONE IS CORRECT)
     * Move     (NOT RIGHT NAME BUT NEXT ONE IS CORRECT)
     * Move     If the command matches the move name, then evaluate it.
     * Move
     * Move
     *
     *
     *
     *
     * Move    (NOT RIGHT NAME BUT NEXT ONE IS CORRECT)
     * Move    (NOT RIGHT NAME BUT NEXT ONE IS CORRECT)
     * Move    (NOT RIGHT NAME BUT NEXT ONE ISN'T CORRECT - THEN NEED TO STOP)
     * Move
     * Move
     * Move
     *
     *
     *
     *
     * Move    (NOT RIGHT NAME BUT NEXT ONE IS CORRECT)
     * Move    (NOT RIGHT NAME BUT NEXT ONE IS CORRECT)
     * Move    RIGHT NAME AND IT'S THE LAST ONE - IF GETS IT CORRECT THEN MOVES TO NEXT SET
     *
     *
     *
     *
     *
     *
     * Move    (NOT RIGHT NAME BUT NEXT ONE IS CORRECT)
     * Move    (NOT RIGHT NAME BUT NEXT ONE IS CORRECT)
     * Move    (NOT RIGHT NAME BUT NEXT ONE IS CORRECT)
     * Move    (NOT RIGHT NAME BUT NEXT ONE IS CORRECT)
     * Move     (NOT RIGHT NAME BUT NEXT ONE IS CORRECT)
     * Move    (NOT RIGHT NAME BUT NEXT ONE IS CORRECT BUT FINISHED ALL MOVES) THIS SHOULDN'T HAPPEN AS THEN IT SHOULD HAVE GONE TO NEXT ONE
     *
     *
     */

    

    public int avgNumberOfSuccesses=0;
    int numberOfSuccesses =0;
    public void incNumberofSuccesses(){
        numberOfSuccesses++;
    }
    public int getNumberOfSuccesses(){
        return numberOfSuccesses;
    }
    

}
