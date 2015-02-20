/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.collabMinitaskProceduralComms;

import diet.server.Participant;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author sre
 */
public class MovesFactory {


     Random r = new Random();
     static Vector wordsAND = new Vector();
     static Vector wordsXOR = new Vector();
     static Vector wordsONLY = new Vector();

     //Participant a;
     //Participant b;

     
     
     

     Moves mvs;
    
    Vector randomWords = new Vector();

     

    
     
     
     
     
     
     public void initialize(){
         

 randomWords.addElement("beyond");
 randomWords.addElement("box");
 randomWords.addElement("carp");
 randomWords.addElement("carpet");
 randomWords.addElement("chuckle");
 randomWords.addElement("come");
 randomWords.addElement("dog");
 randomWords.addElement("fast");
 randomWords.addElement("fluff");
 randomWords.addElement("gone");
 randomWords.addElement("good");
 randomWords.addElement("grid");
 randomWords.addElement("group");
 randomWords.addElement("hair");
 randomWords.addElement("here");
 randomWords.addElement("hurry");
 randomWords.addElement("imp");
 randomWords.addElement("individual");
 //randomWords.addElement("it");
 randomWords.addElement("light");
 randomWords.addElement("lolly");
 randomWords.addElement("lovely");

 randomWords.addElement("measure");
 randomWords.addElement("morose");

 randomWords.addElement("needed");
 randomWords.addElement("nope");
 randomWords.addElement("not");


 randomWords.addElement("other");
 randomWords.addElement("others");
 randomWords.addElement("nothing");

  
  //randomWords.addElement("outer");
  randomWords.addElement("pet");
  randomWords.addElement("pit");
  
  randomWords.addElement("pig");
  randomWords.addElement("sausage");
  randomWords.addElement("scream");
  randomWords.addElement("seat");

  randomWords.addElement("something");
  randomWords.addElement("spangle");
  randomWords.addElement("speed");
  randomWords.addElement("star");
  randomWords.addElement("strangle");
  randomWords.addElement("tonic");

  randomWords.addElement("threat");
  randomWords.addElement("train");
  randomWords.addElement("trample");

  randomWords.addElement("vile");
  randomWords.addElement("wangle");


  randomWords.addElement("yes");
  randomWords.addElement("you");






         randomWords.addElement("apple");
         randomWords.addElement("banana");
         randomWords.addElement("cherry");
         randomWords.addElement("dfruit");
         randomWords.addElement("everything");
         randomWords.addElement("raspberry");
         randomWords.addElement("tomato");


         randomWords.addElement("cake");
         randomWords.addElement("pie");
         randomWords.addElement("pastry");
         randomWords.addElement("dough");
         randomWords.addElement("pizza");
         randomWords.addElement("bread");
         randomWords.addElement("flour");



         randomWords.addElement("car");
         randomWords.addElement("bike");
         randomWords.addElement("motor");
         randomWords.addElement("caravan");
        randomWords.addElement("boat");
        randomWords.addElement("lorry");
        randomWords.addElement("truck");
        randomWords.addElement("yacht");
        randomWords.addElement("plane");
        randomWords.addElement("horse");

         
        
        int desiredSizeOfONLY =15;
        int desiredSizeOfAND  =15;
        int desiredSizeOfXOR =15;
        
        
        
        while (wordsONLY.size() <desiredSizeOfONLY){
            int randomIndex = r.nextInt(randomWords.size());
            String randomWord = (String)randomWords.elementAt(randomIndex);
            boolean found =false;
            for(int i=0;i<wordsONLY.size();i++){
                String wordONLY = (String)wordsONLY.elementAt(i);
                if(wordONLY.equalsIgnoreCase(randomWord)){
                    found=true;
                    break;
                }
            }
            if(!found)wordsONLY.addElement(randomWord);
        }
        
        
        
        while (wordsAND.size() <desiredSizeOfAND){
            int randomIndex = r.nextInt(randomWords.size());
            String randomWord = (String)randomWords.elementAt(randomIndex);
            boolean found =false;
            for(int i=0;i<wordsAND.size();i++){
                String wordAND = (String)wordsAND.elementAt(i);
                if(wordAND.equalsIgnoreCase(randomWord)){
                    found=true;
                    break;
                }
            }
            if(!found)wordsAND.addElement(randomWord);
        }
        
        while (wordsXOR.size() <desiredSizeOfXOR){
            int randomIndex = r.nextInt(randomWords.size());
            String randomWord = (String)randomWords.elementAt(randomIndex);
            boolean found =false;
            for(int i=0;i<wordsXOR.size();i++){
                String wordXOR = (String)wordsXOR.elementAt(i);
                if(wordXOR.equalsIgnoreCase(randomWord)){
                    found=true;
                    break;
                }
            }
            if(!found)wordsXOR.addElement(randomWord);
        }
        
        
        
        
     //    for(int i=0;i<randomWords.size();i++){
       //      String s = (String)randomWords.elementAt(i);
         //    int choice = i % 3;
       //      if(choice==0 && !wordsONLY.contains(s)){
      //           wordsONLY.addElement(s);
      //       }
      //       else if(choice ==1&& !wordsONLY.contains(s)){
      //           wordsXOR.addElement(s);
      //       }
      //       else if (choice ==2&& !wordsONLY.contains(s)){
     //            wordsAND.addElement(s);
     //        }
             
     //    }



     }
     
     public MovesFactory(Moves mvs){
         this.mvs=mvs;    
         initialize();         
     }


     

public Object[] addRandword(Vector existingMoves, Participant recip, boolean onlyALLOWED, boolean xorALLOWED, boolean andALLOWED){
    Vector rs = new Vector();
    Move newMove = null;
    while (newMove==null){
        System.out.println("P1A"+existingMoves.size());
        int rindex = 0;//r.nextInt(3);
        Vector vHaystack = null;
        if(rindex==0 & onlyALLOWED)  vHaystack = (Vector)this.wordsONLY.clone();
        if(rindex==1 & xorALLOWED)  vHaystack = (Vector)this.wordsXOR.clone();
        if(rindex==2 & andALLOWED)  vHaystack = (Vector)this.wordsAND.clone();
        
        
           
            
            if(vHaystack !=null){
            while(vHaystack.size()>0 & newMove ==null){
                
                System.out.println("P2A-----"+vHaystack.size());
                int indexOfHaystack = r.nextInt(vHaystack.size());
                //int indexOfHaystack =0;
                
                
                System.out.println("P2B-----");
                String newMoveCandidate = (String)vHaystack.elementAt(indexOfHaystack);
                System.out.println("P2C------"+existingMoves.size());
                boolean wordisalreadyinlist=false;
                for(int i=0;i<existingMoves.size();i++){
                    System.out.println("P4A");
                    Move mv = (Move)existingMoves.elementAt(i);
                    System.out.println("Looking at existing moves.."+mv.getName());
                    if (mv.getName().equalsIgnoreCase(newMoveCandidate)){
                        wordisalreadyinlist=true;
                        
                    }
                }
                if(wordisalreadyinlist) {
                    vHaystack.remove(newMoveCandidate);
                    System.out.println("CANNOT ADD THE WORD "+newMoveCandidate+"  it is already in list");
                }
                if(!wordisalreadyinlist) {
                    if(rindex==0)  newMove = new MoveONLY(recip,newMoveCandidate);
                    if(rindex==1)   newMove = new MoveXOR(newMoveCandidate);
                    if(rindex==2)   newMove = new MoveAND( mvs.getAT().getParticipantA(), mvs.getAT().getParticipantB(),newMoveCandidate);
                    
                    
                    System.out.println("FOUND THE WORD"+newMoveCandidate+"  it is NOT in list");
                    
                    
                     mvs.getAT().getParticipantA();
                }
               
                
                
            
            
        }
            
            }
            
       
       
       
        
    }
     rs = (Vector)existingMoves.clone();
     rs.add(newMove);
     Vector newSorted = sortMoves(rs);
     int indexOfNewMove = newSorted.indexOf(newMove);
     for(int i=indexOfNewMove;i<newSorted.size();i++){
         Move mve =  (Move)newSorted.elementAt(i);
         System.err.println("SETTING TO UNSUCCESSUL "+mve.getName());
         mve.setUnSuccessful();
     }
    Object[] result = {newSorted, newMove};
     
    return  result;
}

     
     

public Vector createRandomMoves(int number, boolean onlyALLOWED, boolean xorALLOWED, boolean andALLOWED){
        //number = 20;
        // number = 100;
         Vector wordsONLY_copy = (Vector)wordsONLY.clone();
         Vector wordsAND_copy = (Vector)wordsAND.clone();
         Vector wordsXOR_copy= (Vector)wordsXOR.clone();

         Vector createdMoves = new Vector();

         if(this.enqueuedSubstitutions.size()>0){
             
             Move enqueuedSubstitutdeMove = (Move)this.enqueuedSubstitutions.firstElement();
             createdMoves.addElement(enqueuedSubstitutdeMove);
             this.performedSubstitutions.addElement(enqueuedSubstitutdeMove);
             this.enqueuedSubstitutions.removeElement(enqueuedSubstitutdeMove);
             
             
             
             
             for(int i=0;i<wordsONLY_copy.size();i++){
                 String mname = (String)wordsONLY_copy.elementAt(i);
                 if(mname.equalsIgnoreCase(enqueuedSubstitutdeMove.getName())){
                     wordsONLY_copy.remove(i);
                     break;
                 }
             }
             for(int i=0;i<wordsAND_copy.size();i++){
                 String mname = (String)wordsAND_copy.elementAt(i);
                 if(mname.equalsIgnoreCase(enqueuedSubstitutdeMove.getName())){
                     wordsAND_copy.remove(i);
                     break;
                 }
             }
             for(int i=0;i<wordsXOR_copy.size();i++){
                String mname = (String)wordsXOR_copy.elementAt(i);
                 if(mname.equalsIgnoreCase(enqueuedSubstitutdeMove.getName())){
                     wordsXOR_copy.remove(i);
                     break;
                 }
             }
             
             
         }
         
         
         
         while (createdMoves.size()<number){
             System.err.println("..."+wordsONLY_copy.size()+"-----"+wordsXOR_copy.size()+"----"+wordsAND_copy.size());
             int mveType= r.nextInt(3);
             if(mveType==0&&onlyALLOWED&&wordsONLY_copy.size()>0){
                    int rChoiceOfWord = r.nextInt(wordsONLY_copy.size());
                    String wrd = (String)wordsONLY_copy.elementAt(rChoiceOfWord);
                    int recipient = r.nextInt(2);
                    if(recipient==0){
                         Move mve = new MoveONLY( mvs.getAT().getParticipantA(),wrd);
                         createdMoves.addElement(mve);
                    }
                    else{
                         Move mve = new MoveONLY( mvs.getAT().getParticipantB(),wrd);
                        createdMoves.addElement(mve);
                    }
                    wordsONLY_copy.remove(wrd);
             }
             else if (mveType==1&&xorALLOWED&&wordsXOR_copy.size()>0){
                   int rChoiceOfWord = r.nextInt(wordsXOR_copy.size());
                   String wrd = (String)wordsXOR_copy.elementAt(rChoiceOfWord);
                   Move mve = new MoveXOR(wrd);
                   createdMoves.addElement(mve);
                   wordsXOR_copy.removeElement(wrd);
             }
             else if (mveType==2&&andALLOWED&&wordsAND_copy.size()>0){
                 int rChoiceOfWord = r.nextInt(wordsAND_copy.size());
                 String wrd = (String)wordsAND_copy.elementAt(rChoiceOfWord);
                 Move mve = new MoveAND( mvs.getAT().getParticipantA(), mvs.getAT().getParticipantB(),wrd);
                 createdMoves.addElement(mve);
                 wordsAND_copy.removeElement(wrd);
             }
         }
         return sortMoves(createdMoves);
     }


     public Vector sortMoves(Vector vMoves){
         Vector vSorted = new Vector();
         Vector vMovesCopy = (Vector)vMoves.clone();
         while(vMovesCopy.size()>0){
             Object o = findSmallestValue(vMovesCopy);
             vMovesCopy.remove(o);
             vSorted.addElement(o);
         }


         return vSorted;
     }

     public Move findSmallestValue(Vector v){
         if (v.size()==0)return null;
         Move smallestValue = (Move)v.elementAt(0);
         for(int i=0;i<v.size();i++){
             Move comparedMove = (Move)v.elementAt(i);
             try{
             if(comparedMove.getName().compareToIgnoreCase(smallestValue.getName())<0){
                 smallestValue = comparedMove;
             }
             }catch (Exception e) {
                  System.err.println("comparedMove"+comparedMove.getType()); 
                 System.err.println("smallestValueTYPE:"+smallestValue.getType());
                  
                  System.err.println("smallestValue: "+smallestValue.getName());
                  System.err.println("comparedMoveGETNAME: "+comparedMove.getName());
                  e.printStackTrace();
                  
             }
         }
         return smallestValue;
     }

     
     
     
    
     
     
     
     public synchronized void swapMovesORvsAND__MustBeCalledAfterSwappingPhysicalAndApparentPArtners(){
         //need to delete the moves from the prior successful in Moves
         
         
         enqueuedSubstitutions = new Vector();
         //this.deletePerformedSubstitutionsFromLists();
         performedSubstitutions = new Vector();
         
         Vector allSuccessesFROMPRIOR_ParticipantA = this.mvs.getPriorSuccessfulMovesOfParticipantA();
         Vector allSuccessesFROMPRIOR_ParticipantB = this.mvs.getPriorSuccessfulMovesOfParticipantB();
         
         Vector allSuccessfulONLY_FROMPRIOR = new Vector();
         Vector allSuccessfulXOR_FROMPRIOR = new Vector();
         Vector allSuccessfulAND_FROMPRIOR = new Vector();
         
         for(int i=0;i<allSuccessesFROMPRIOR_ParticipantA.size();i++){
             Move mve = (Move)allSuccessesFROMPRIOR_ParticipantA.elementAt(i);
             for(int j=0;j<allSuccessesFROMPRIOR_ParticipantB.size();j++){
                  Move mve2 = (Move)allSuccessesFROMPRIOR_ParticipantB.elementAt(j);
                  if(mve.getName().equalsIgnoreCase(mve2.getName())){
                        mve.avgNumberOfSuccesses= (int)(mve2.numberOfSuccesses+mve.avgNumberOfSuccesses)/2;
                        if(mve instanceof MoveONLY)allSuccessfulONLY_FROMPRIOR.addElement(mve);
                        if(mve instanceof MoveXOR)allSuccessfulXOR_FROMPRIOR.addElement(mve);
                        if(mve instanceof MoveAND)allSuccessfulAND_FROMPRIOR.addElement(mve);
                  }
             }
 
         }
           Move priorMostSuccessfulMoveONLY = null;    //THESE ARE USED TO KEEP A RECORD OF WHAT TO RESET
         Move priorMostSuccessfulMoveXOR = null;
         Move priorMostSuccessfulMoveAND = null;
          priorMostSuccessfulMoveONLY = null;
          for(int i=0;i<allSuccessfulONLY_FROMPRIOR.size();i++){
                 Move mve = (Move)allSuccessfulONLY_FROMPRIOR.elementAt(i);
                 if(priorMostSuccessfulMoveONLY==null){
                     priorMostSuccessfulMoveONLY=mve;
                 }
                 else if (mve.avgNumberOfSuccesses>priorMostSuccessfulMoveONLY.avgNumberOfSuccesses){
                     priorMostSuccessfulMoveONLY =mve;
                 }  
          } 
          priorMostSuccessfulMoveXOR = null;
          for(int i=0;i<allSuccessfulXOR_FROMPRIOR.size();i++){
                 Move mve = (Move)allSuccessfulXOR_FROMPRIOR.elementAt(i);
                 if(priorMostSuccessfulMoveXOR==null){
                     priorMostSuccessfulMoveXOR=mve;
                 }
                 else if (mve.avgNumberOfSuccesses>priorMostSuccessfulMoveXOR.avgNumberOfSuccesses){
                     priorMostSuccessfulMoveXOR =mve;
                 }  
          } 
                 
          priorMostSuccessfulMoveAND = null;
          for(int i=0;i<allSuccessfulAND_FROMPRIOR.size();i++){
                 Move mve = (Move)allSuccessfulAND_FROMPRIOR.elementAt(i);
                 if(priorMostSuccessfulMoveAND==null){
                     priorMostSuccessfulMoveAND=mve;
                 }
                 else if (mve.avgNumberOfSuccesses>priorMostSuccessfulMoveAND.avgNumberOfSuccesses){
                     priorMostSuccessfulMoveAND =mve;
                 }  
          } 
         
          Move moveOR = null; 
          if(priorMostSuccessfulMoveXOR==null && priorMostSuccessfulMoveONLY!=null){
              moveOR=priorMostSuccessfulMoveONLY;
          }
          else if(priorMostSuccessfulMoveXOR!=null && priorMostSuccessfulMoveONLY==null){
              moveOR=priorMostSuccessfulMoveXOR;
          }
          else if(priorMostSuccessfulMoveXOR==null && priorMostSuccessfulMoveONLY==null){
              //stays null
          }
          else if(priorMostSuccessfulMoveONLY.avgNumberOfSuccesses>priorMostSuccessfulMoveONLY.avgNumberOfSuccesses){
              moveOR=priorMostSuccessfulMoveONLY;
          }
          else {
              moveOR=priorMostSuccessfulMoveXOR;
          }
          
          
          
           Move newMoveOR_AND = null;
           Move newMoveAND_OR = null;
           
          if(moveOR!=null) {
              mvs.removeWordFromParticipantRecordOfPriorSuccess(this.mvs.at.getParticipantA(),((Move)moveOR).getName());
              mvs.removeWordFromParticipantRecordOfPriorSuccess(this.mvs.at.getParticipantB(),((Move)moveOR).getName());
              System.err.println("IT SHOULD HAVE REMOVED(1) "+ ((Move)moveOR).getName()); 
              newMoveOR_AND = new MoveAND(mvs.at.getParticipantA(),mvs.at.getParticipantB(),moveOR.getName());
              newMoveOR_AND.setPriorMoveType(moveOR.getType());
          }
          if(priorMostSuccessfulMoveAND!=null) {
              mvs.removeWordFromParticipantRecordOfPriorSuccess(this.mvs.at.getParticipantA(),(priorMostSuccessfulMoveAND).getName());
              mvs.removeWordFromParticipantRecordOfPriorSuccess(this.mvs.at.getParticipantB(),(priorMostSuccessfulMoveAND).getName());
              System.err.println("IT SHOULD HAVE REMOVED priorMostSuccessfulMoveAND "+priorMostSuccessfulMoveAND.getName());
              int createXOR_or_ONLY = r.nextInt(2);
              int participantAorB = r.nextInt(2);
              if     (createXOR_or_ONLY ==0 && participantAorB == 0){ newMoveAND_OR = new MoveONLY(mvs.at.getParticipantA(),priorMostSuccessfulMoveAND.getName()); }
              else if(createXOR_or_ONLY ==1 ){ newMoveAND_OR = new MoveXOR(priorMostSuccessfulMoveAND.getName()); }
              else if(createXOR_or_ONLY ==0 && participantAorB == 1){ newMoveAND_OR = new MoveONLY(mvs.at.getParticipantB(),priorMostSuccessfulMoveAND.getName()); }
              newMoveAND_OR.setPriorMoveType("AND");
              
          }
          int whichorder = r.nextInt(2);
       
          if(newMoveOR_AND==null){
              //System.exit(-5);
          }
          else{
              //newMoveOR_AND.setName("SHOULDBEAND");
              //System.exit(-5);
              //if(newMoveOR_AND!=null)newMoveOR_AND.setName("ORAND"+newMoveOR_AND.getName());
              //if(newMoveAND_OR!=null)newMoveAND_OR.setName("ANDOR"+newMoveAND_OR.getName());
          }
          
          
          if(whichorder==1){
              if(newMoveOR_AND!=null)enqueuedSubstitutions.addElement(newMoveOR_AND);
              if(newMoveAND_OR!=null)enqueuedSubstitutions.addElement(newMoveAND_OR); }
          else{
               if(newMoveAND_OR!=null)enqueuedSubstitutions.addElement(newMoveAND_OR);
               if(newMoveOR_AND!=null)enqueuedSubstitutions.addElement(newMoveOR_AND); 
          }
          
          
          
         
          
        }
         
         
         Vector enqueuedSubstitutions = new Vector(); ///THESE ARE USED TO GENERATE NEW ONES
         Vector performedSubstitutions = new Vector();
         
         
         
         public void deletePerformedSubstitutionsFromLists(){
             for(int i=0;i<performedSubstitutions.size();i++){
                 Move mve = (Move)performedSubstitutions.elementAt(i);
                 this.deleteWordFromLists(mve.getName());
             }
         }
         
         
         public void deleteWordFromLists(String s){
             for(int i=0;i<this.wordsONLY.size();i++){
                 String s2 = (String)wordsONLY.elementAt(i);
                 if(s2.equals(s)){
                     wordsONLY.remove(s2);
                     break;
                 }
             }
             for(int i=0;i<this.wordsAND.size();i++){
                 String s2 = (String)wordsAND.elementAt(i);
                 if(s2.equals(s)){
                     wordsAND.remove(s2);
                     break;
                 }
             }
             for(int i=0;i<this.wordsXOR.size();i++){
                 String s2 = (String)wordsXOR.elementAt(i);
                 if(s2.equals(s)){
                     wordsXOR.remove(s2);
                     break;
                 }
             }
         }
        
         
         
         
         
         
         
         //get all the successfully coordinated ONLY
          //get all the successfully coordinated AND
          
          //AS DEBUG, SET THEIR TEXT TO SAY WHAT IT WAS
          //INCREMENT THE COUNTER
         
         
         
    
     
     
     


     
     
     
}



/*
 *
 * 8 times ONLY
 * 8 times OR
 * 8 times AND
 *
 *
 * 15 times 2 of each
 * 10 times 3 of each
 *
 *
 *
 */