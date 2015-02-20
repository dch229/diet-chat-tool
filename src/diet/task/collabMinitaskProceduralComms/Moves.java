/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.collabMinitaskProceduralComms;

import diet.debug.Debug;
import diet.server.Participant;
import java.util.Date;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author sre
 */






public class Moves {

    public Vector moves = new Vector();
    MovesFactory mf;

    Vector wordsForA = new Vector();
    Vector wordsForB = new Vector();

    //int maxSize =3;
    Random r = new Random();

    //Participant a;
    //Participant b;

    AlphabeticalTaskWithInsertedWords at;

    Move mostRecentDiscussed=null;
    
    
    
    static Hashtable htParticipantPriorSuccessfulMoves = new Hashtable();
    


    public Moves(AlphabeticalTaskWithInsertedWords at) {
        this.at=at;
        mf = new MovesFactory(this);
    }

    
    public AlphabeticalTaskWithInsertedWords getAT(){
        return at;
    }
    
    
    public Move getMostRecentDiscussed(){
        return this.mostRecentDiscussed;
    }

   // public Participant getOther()

    public int getLastSel(){
        for(int i=0;i<moves.size();i++){
            Move mve = (Move)moves.elementAt(i);
            if(!mve.isSuccessful())return i;
        }
        return moves.size();
    }


    

    public Object[] doesTextMentionMostRecent_andFindAllMovesForWords(String[] wrds){
          Object[] response = new Object[2];
          Vector allWordsFound = new Vector();
          for(int j=0;j<moves.size();j++){
               Move mve = (Move)moves.elementAt(j);
               for(int i=0;i<wrds.length;i++){
                   String wrdToLookFor = wrds[i];


                 if(mve==mostRecentDiscussed){
                       response[0]=mostRecentDiscussed;
                 }
                 if(mve.getName().equalsIgnoreCase(wrdToLookFor)){
                     allWordsFound.addElement(mve);
                 }
            }
        }
        response[1]=allWordsFound;
        return response;
    }


    public Vector findMovesForWords(String[] wrds){
        Vector allWordsFound = new Vector();
        for(int i=0;i<wrds.length;i++){
            String wrdToLookFor = wrds[i];
            for(int j=0;j<moves.size();j++){
                Move mve = (Move)moves.elementAt(j);
                if(mve.getName().equalsIgnoreCase(wrdToLookFor))allWordsFound.addElement(mve);
            }
        }
        return allWordsFound;
    }

    public String getWordsForAToSelectHTML(){
        String s="";
        for(int i=0;i<wordsForA.size();i++){
            s  = s + " "+ (String)wordsForA.elementAt(i);
        }
        return s.trim();
    }



    public String getWordsForBToSelectHTML(){
         String s="";
        for(int i=0;i<wordsForB.size();i++){
            s  = s + " "+(String)wordsForB.elementAt(i);
        }
        return s.trim();
    }

    public static String asString(Vector vString){
        String s="";
        for(int i=0;i<vString.size();i++){
            s=s+ " "+(String)vString.elementAt(i);
        }
        return s;
    }

    public Vector getWordsForAToSelect(){
        return wordsForA;
    }

    public Vector getWordsForBToSelect(){
        return wordsForB;
    }


    
    
    
    
    
    public Object[] addRNDWRD(Participant p,boolean onlyALLOWED, boolean xorALLOWED, boolean andALLOWED){
        Object[] allMOVES_newMOVE = null;
        try{
           allMOVES_newMOVE=  mf.addRandword(moves, p, onlyALLOWED, xorALLOWED, andALLOWED);
           //if(2<5)return;
           
          moves = (Vector)allMOVES_newMOVE[0];
           
           Vector wdsForA = new Vector();
           Vector wdsForB = new Vector();
           wordsForA = new Vector();
           wordsForB = new Vector();
        for(int i=0;i<moves.size();i++){
           
            Move mve = (Move)moves.elementAt(i);
            System.err.println("THECONTENTOFTHEMOVESVECTORIRISTHIS"+i+" "+mve.getName());
            String wordForA = mve.getWordForParticipantAsHTML(at.getParticipantA());
            if(wordForA!=null){
                wdsForA.addElement(wordForA);
                Debug.printDBG("ADDINGFORB: "+wordForA);
            }
            String wordForB =  mve.getWordForParticipantAsHTML(at.getParticipantB());
            if(wordForB!=null){
                wdsForB.addElement(wordForB);
                Debug.printDBG("ADDINGFORB: "+wordForB);
            }
        }
        while(wdsForA.size()>0){
            Object o = wdsForA.elementAt(r.nextInt(wdsForA.size()));
            wordsForA.addElement(o);
            wdsForA.remove(o);
            System.err.println("REMOVING "+(o.toString()));
            
        }
        while(wdsForB.size()>0){
            Object o = wdsForB.elementAt(r.nextInt(wdsForB.size()));
            wordsForB.addElement(o);
            wdsForB.remove(o);
            System.err.println("REMOVING2 "+(o.toString()));
        }
        if(Debug.debugGROOP3){
             String dbMVES="UNINITIALIZED";
             for(int i=0;i<moves.size();i++){
                 Move mve = (Move)moves.elementAt(i);
                 dbMVES = "--"+dbMVES+"!!!!!"+mve.getName();
             }
             //at.cC.getC().sendArtificialTurnToRecipient(a, dbMVES, 0);
             //at.cC.getC().sendArtificialTurnToRecipient(b, dbMVES, 0);
        }
           
           
           
           
           
           
        } catch (Exception e){
            e.printStackTrace();
        }  
        return allMOVES_newMOVE;
        
    }
    
    
    
    
    
    public void createNewMoveSet(int maxSize, boolean onlyALLOWED, boolean xorALLOWED, boolean andALLOWED){
       
        moves = mf.createRandomMoves(maxSize,onlyALLOWED,xorALLOWED,andALLOWED);
        this.mostRecentDiscussed=null;

        wordsForA = new Vector();
        wordsForB = new Vector();
        Vector wdsForA = new Vector();
        Vector wdsForB = new Vector();

        
        for(int i=0;i<moves.size();i++){
           
            Move mve = (Move)moves.elementAt(i);
            String wordForA = mve.getWordForParticipantAsHTML(at.getParticipantA());
            if(wordForA!=null){
                wdsForA.addElement(wordForA);
                Debug.printDBG("ADDINGFORB: "+wordForA);
            }
            String wordForB =  mve.getWordForParticipantAsHTML(at.getParticipantB());
            if(wordForB!=null){
                wdsForB.addElement(wordForB);
                Debug.printDBG("ADDINGFORB: "+wordForB);
            }
        }
        while(wdsForA.size()>0){
            Object o = wdsForA.elementAt(r.nextInt(wdsForA.size()));
            wordsForA.addElement(o);
            wdsForA.remove(o);
            System.err.println("REMOVING "+(o.toString()));
            
        }
        while(wdsForB.size()>0){
            Object o = wdsForB.elementAt(r.nextInt(wdsForB.size()));
            wordsForB.addElement(o);
            wdsForB.remove(o);
            System.err.println("REMOVING2 "+(o.toString()));
        }
        if(Debug.debugGROOP3){
             String dbMVES="UNINITIALIZED";
             for(int i=0;i<moves.size();i++){
                 Move mve = (Move)moves.elementAt(i);
                 dbMVES = "--"+dbMVES+"!!!!!"+mve.getName();
             }
             //at.cC.getC().sendArtificialTurnToRecipient(a, dbMVES, 0);
             //at.cC.getC().sendArtificialTurnToRecipient(b, dbMVES, 0);
        }
        //getAllMovesToString();
    }


    

    public void storeWordForParticipant(int wordSetSwapNumber,Move mve){///THIS NEEDS TO BE CLEARED AT EACH STAGE
        if(!mve.isSuccessful())return;
        Vector vA = (Vector) htParticipantPriorSuccessfulMoves.get(at.getParticipantA());
          
          if(vA==null){
              vA = new Vector();
          }
          if(!vA.contains(mve)){
              boolean found=false;
              for(int i=0;i<vA.size();i++){
                  Move mveAlreadyMadeMoves = (Move)vA.elementAt(i);
                  if(mveAlreadyMadeMoves.getName().equalsIgnoreCase(mve.getName())){
                      mveAlreadyMadeMoves.incNumberofSuccesses();
                      found=true;
                  }
              }
              if(!found)vA.addElement(mve);
          }
           htParticipantPriorSuccessfulMoves.put(at.getParticipantA(), vA);  
          
          Vector vB = (Vector)  htParticipantPriorSuccessfulMoves.get(at.getParticipantB());
          if(vB==null){
              vB = new Vector();
          }
          if(!vB.contains(mve)){
              vB.addElement(mve);
          }
          if(!vB.contains(mve)){
              boolean found=false;
              for(int i=0;i<vB.size();i++){
                  Move mveAlreadyMadeMoves = (Move)vB.elementAt(i);
                  if(mveAlreadyMadeMoves.getName().equalsIgnoreCase(mve.getName())){
                       mveAlreadyMadeMoves.incNumberofSuccesses();
                       found=true;
                  }
              }
             if(!found) vB.addElement(mve);
          }
           htParticipantPriorSuccessfulMoves.put(at.getParticipantB(), vB);          
    }
    
    public String getAandBSuccessfulMoves(){
             
         Vector vA = (Vector)  htParticipantPriorSuccessfulMoves.get(at.getParticipantA());
         Vector vB = (Vector)  htParticipantPriorSuccessfulMoves.get(at.getParticipantB());
         if(vA==null){
              vA = new Vector();
          }
          if(vB==null){
              vB = new Vector();
          }
          String sA = "";
          String sB = "";
          for(int i=0;i<vA.size();i++){
              sA = sA+"("+ ((Move)vA.elementAt(i)).getPriorMoveType()+","+((Move)vA.elementAt(i)).getType()+","+((Move)vA.elementAt(i)).getName()+")"+"  ";
          }
           for(int i=0;i<vB.size();i++){
              sB = sB+"("+ ((Move)vB.elementAt(i)).getPriorMoveType()+","+((Move)vB.elementAt(i)).getType()+","+((Move)vB.elementAt(i)).getName()+")"+"  ";
          }
          return "THE SUCCESSFUL MOVES OF A ARE: "+sA + "\n"+"THE SUCCESSFUL MOVES OF B ARE: "+sB+ "\n";         
    }
    
    public Vector getPriorSuccessfulMovesOfParticipantA(){
        
         Vector vA = (Vector)  htParticipantPriorSuccessfulMoves.get(at.getParticipantA());
         if(vA==null)vA= new Vector();
         return vA;
    }
    public Vector getPriorSuccessfulMovesOfParticipantB(){
        
         Vector vB = (Vector) htParticipantPriorSuccessfulMoves.get(at.getParticipantB());
         if(vB==null)vB= new Vector();
         return vB;
    }
    
    public void removeWordFromParticipantRecordOfPriorSuccess(Participant p, String word){
          Vector vA = (Vector) htParticipantPriorSuccessfulMoves.get(at.getParticipantA());  
          if(vA==null){
              vA = new Vector();
          }
          boolean found=false;
          for(int i=0;i<vA.size();i++){
              Move mveAlreadyMadeMoves = (Move)vA.elementAt(i);
              if(mveAlreadyMadeMoves.getName().equalsIgnoreCase(word)){
                     vA.removeElement(mveAlreadyMadeMoves);
                  }
              }
           htParticipantPriorSuccessfulMoves.put(p, vA);
    }   
    
 
    
    
    

    public void showStoredWordsForBothParticipants(){
        
    }
    
    
    
    
    //0049 911 366 9015
    
    public synchronized String evaluate(Participant p, String name){
         
        
        
        
        
         Move movefound=null;
         for(int i=0;i<moves.size();i++){
               Move mve = (Move)moves.elementAt(i);
               String s = mve.getName();
               if(s.equalsIgnoreCase(name)){
                   if(i>0){
                       Move priorMove = (Move)moves.elementAt(i-1);
                       if(!priorMove.isSuccessful()){
                             return Moves.errorTriedToSelectWordThatsTooFarAhead;
                       }
                   }
                   movefound = mve;
                   this.mostRecentDiscussed=mve;
               }
         }
         if (movefound == null){
            return Moves.errorTypo;
         }

         
         
         //Check to see if it is attempting to select item that's already been selected. MoveONLY is the only type they're allowed to go back to
         boolean moveIsLastSelection = true;
         int indexOfMoveFound = moves.indexOf(movefound);
         for(int i=indexOfMoveFound+1;i<moves.size();i++){
             Move mveToTest = (Move)moves.elementAt(i);
             if(mveToTest.isSuccessful()) moveIsLastSelection = false;
         }
         


         String evalItemResult = movefound.evaluate(p, name,moveIsLastSelection);

           
         at.cC.getC().saveDataToFile(at.getDyadDescription()+"TASK", p.getParticipantID(), p.getUsername(),  new Date().getTime(),new Date().getTime(), new Date().getTime(), "("+movefound.getPriorMoveType()+","+movefound.getType()+","+movefound.getName()+")"+name+"(SELECTION)"+evalItemResult, null);
          
         
         
         if(movefound==moves.lastElement()&& (evalItemResult.equalsIgnoreCase(Moves.correctWORD)|evalItemResult.equalsIgnoreCase(Moves.correctSIMULTANEOUSWORD))){
             this.storeWordForParticipant(at.getWordSetSwapNumber(),movefound);
              System.err.println(getAandBSuccessfulMoves());
             return Moves.correctSET;
         }

         for(int j= indexOfMoveFound+1;j<moves.size();j++){
                         Move mveToReset = (Move)moves.elementAt(j);
                         mveToReset.setUnSuccessful();
         }

         this.storeWordForParticipant(at.getWordSetSwapNumber(),movefound);
         
        
          System.err.println(getAandBSuccessfulMoves());
         return evalItemResult;
         
    }


     public String getAllMovesToString(){
         String aHeader =(Moves.addLeftPadding(this.at.getParticipantA().getUsername()));
         String bHeader =(Moves.addRightPadding(this.at.getParticipantB().getUsername()));

         String textMoves = "";
         for(int i=0;i<moves.size();i++){
             Move mve = (Move)moves.elementAt(i);
             String line ="";
             String type = "";
             String aWord ="";
             String bWord ="";
             if(mve instanceof MoveONLY){
                 type = "ONLY:";
                 if(mve.getWordForParticipantAsHTML(at.getParticipantA())!=null){
                     aWord = addLeftPadding(mve.getWordForParticipant(at.getParticipantA()));
                     bWord = addRightPadding("");
                 }
                 if(mve.getWordForParticipantAsHTML(at.getParticipantB())!=null){
                     aWord = addLeftPadding(mve.getWordForParticipant(at.getParticipantA()));
                     bWord = addRightPadding(mve.getWordForParticipant(at.getParticipantB()));
                 }
             }
             else if (mve instanceof MoveXOR){
                 type="XOR: ";
                 if(mve.getWordForParticipantAsHTML(at.getParticipantA())!=null) aWord = addRightPadding(mve.getWordForParticipant(at.getParticipantA()));
                 if(mve.getWordForParticipantAsHTML(at.getParticipantB())!=null) bWord = addLeftPadding(mve.getWordForParticipant(at.getParticipantB()));
             }
             else if (mve instanceof MoveAND){
                 type="AND: ";
                 if(mve.getWordForParticipantAsHTML(at.getParticipantA())!=null) aWord = addLeftPadding(mve.getWordForParticipant(at.getParticipantA()));
                 if(mve.getWordForParticipantAsHTML(at.getParticipantB())!=null) bWord = addRightPadding(mve.getWordForParticipant(at.getParticipantB()));
             }

             String isCorr = " ";
             if(mve.isSuccessful())isCorr="*";
             line=isCorr+"]"+type+aWord+bWord;
             textMoves = textMoves+line+"\n";
         }
         textMoves = "       "+aHeader + bHeader +"\n"+textMoves;
         System.err.println(textMoves);
         return textMoves;
        // System.exit(-5);
         
     }




    public Move gt(){
        for(int i=0;i<this.moves.size(); i++){
            Move mve = (Move)moves.elementAt(i);
            if(mve.isSuccessful()){

            }
        }
       return null;
    }





     static public String addLeftPadding(String s){
         if(s==null)s="";
         String output = "";
         int spacesToAdd = 30-s.length();
         for(int i=0;i<spacesToAdd;i++){
             output = "="+output;
         }
         return output+s;
     }

     static public String addRightPadding(String s){
         if(s==null)s="";
         String output = "";
         int spacesToAdd = 30-s.length();
         for(int i=0;i<spacesToAdd;i++){
             output = output+"=";
         }
         return s+output;
     }


     public static String errorTriedToSelectWordThatsTooFarAhead = "ERROR, other person tried to select a word that is too far ahead";
     public static String errorTriedToSelectWordThatsTooFarBACK = "ERROR, the OTHER person tried to select a word that has already been selected and is of the wrong type to go back to";
     public static String errorTriedToSelectOther = "ERROR, OTHER person tried to select one of YOUR words";
     public static String errorTriedToSelectAnother = "ERROR, OTHER person tried to select ANOTHER's words - THIS SHOULDN'T HAPPEN";

     public static String errorWordIsAlreadySelectedByRequestor = "ERROR, the OTHER person tried to select a word that THEY had already selected";
     public static String errorWordHasAlreadyBeenSelectedByOtherPerson = "ERROR,the OTHER person tried to select a word that YOU had already selected";

     public static String intermediateMessageSimultaneousANDCorrect = "Other selected first half correctly";
     public static String intermediateMessageReselectedFirstHalfOfAND = "Other has RESELECTED first half correctly";
     public static String wordHasAlreadyBeenSelectedByBoth = "This word has already been selected by BOTH of you";

     public static String errorTypo = "ERROR. Typo...no such word exists";
     public static String correctSET = "CORRECT. All words were correctly selected";
     public static String correctWORD = "CORRECT word was selected";
     public static String correctWORDReselected = "CORRECT word was reselected";
     //public static String goneBackToPriorState = "Gone back to prior state";
     public static String cantGoBackToPriorState_TriedToSelectOthersWord = "Can't go back to prior state. Participant tried to select your word";



     public static String correctSIMULTANEOUSWORD = "CORRECT.You both selected the word simultaneously";
     public static String errorSIMULTANEOUS= "You didn't manage to select the word simultaneously";
     public static String incorrectSecondMoveCorrectFirstMove = "The OTHER person didn't select the word simultaneously. The OTHER's word is now saved as the first part";


     public static String errorInterface = "PROGRAMMING ERROR";




     
     
     public MovesFactory getMovesFactory(){
         return mf;
     }
      
}


