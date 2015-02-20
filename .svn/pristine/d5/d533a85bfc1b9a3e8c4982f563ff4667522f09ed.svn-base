/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.task.collabMinitaskProceduralComms;

//import java.util.Vector;

import diet.debug.Debug;
import diet.server.ConversationController.CCGROOP3SEQ4;
import diet.server.ConversationController.DefaultConversationController;
import diet.server.Participant;
import diet.utils.StringOperations;
import java.util.Date;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;


/**
 *
 * @author sre
 */
public class AlphabeticalTaskWithInsertedWords {

   int id ; //USED SIMPLY AS AN INDEX WHEN PRINTING TO THE DEBUG WINDOW
   Random r = new Random();
   MovesFactory mf;
   //CCGROOP3SEQ4 cC;
   JSDM_4WAYConversationControllerINTERFACE cC;
   public Participant a;
   public Participant b;
   Moves mves;

   //String currSetRepresentation = "";
   String allFeedback = "";


   String aForegroundColour = "BLACK"; String aBackgroundColour = "WHITE";
   String bForegroundColour = "BLACK";  String bBackgroundColour = "WHITE";

   String aHTML ="";
   String bHTML = "";



   public boolean showNegativeFeedback = false;
   public boolean flashOnSuccess = false;

   
   private boolean onlyALLOW=true;
   private boolean xorALLOW=false;
   private boolean andALLOW=false;
   private int maxSize = 1;
   
   public long startTimeOfCurrentSet = new Date().getTime();
   
   
   public int wordSetSwapNumber= 0; //INITIAL

   public JCRSender2ParticipantsWithInsertedWords jcrs;

   public AlphabeticalTaskWithInsertedWords(int i,JSDM_4WAYConversationControllerINTERFACE cC) {
        this.cC =cC;
        this.id=i;
   }

   public void changeSettings(int mSize, boolean o, boolean x, boolean a){
       this.maxSize=mSize;
       this.onlyALLOW=o;
       this.xorALLOW=x;
       this.andALLOW=a;
   }
   
   
   
   
   
   
   
   public synchronized void rndWRD(){
       
       
       Object[] resultOFRNDADD;
       int iParticipant = r.nextInt(2);
       if(iParticipant==0){
           resultOFRNDADD=mves.addRNDWRD(this.a,onlyALLOW,xorALLOW,andALLOW);
       }
       else{
           resultOFRNDADD=mves.addRNDWRD(this.b,onlyALLOW,xorALLOW,andALLOW);
       }
       Move newMove = (Move)resultOFRNDADD[1];
       cC.getC().saveDataToConversationHistory(this.getDyadDescription(),"WORDADDED " + newMove.getName() + " "+newMove.getPriorMoveType() + " "+newMove.getType() );
       
       String prefix = "NEWWORDADDED";
       
       if(newMove.getWordForParticipantAsHTML(b)!=null){
           if (!newMove.getWordForParticipantAsHTML(b).equalsIgnoreCase("")){
                this.aHTML = mves.getWordsForBToSelectHTML();
                cC.getC().changeWebpageTextAndColour(a, "ID1", "YOUR SCORE IS: "+this.getScore(a)+"<br>"+"YOUR WORDS ARE:<br>"+ "\n"+ aHTML,aForegroundColour, aBackgroundColour);
                String chatTextWindowA = prefix +" Your score is: "+ this.getScore(a);
                cC.getC().sendArtificialTurnToRecipient(a, chatTextWindowA, 0,cC.getDescriptionForP(a));
                String[] abc = {"CONTINUE"};
                cC.getC().showPopupOnClientQueryInfo("",a, "Another word has been added to your list:\n\n"+newMove.getWordForParticipantAsHTML(b)+"\n", abc, "Another word has been added to your list:", 0, cC.getDescriptionForP(a));
                
           }
       }
       if(newMove.getWordForParticipantAsHTML(a)!=null){
           if (!newMove.getWordForParticipantAsHTML(a).equalsIgnoreCase("")){
                this.bHTML = mves.getWordsForAToSelectHTML();
                cC.getC().changeWebpageTextAndColour(b, "ID1", "YOUR SCORE IS: "+this.getScore(b)+"<br>"+"YOUR WORDS ARE:<br>"+"\n"+bHTML, bForegroundColour, bBackgroundColour);
                String chatTextWindowB = prefix+" Your score is: "+ this.getScore(b);     
                cC.getC().sendArtificialTurnToRecipient(b, chatTextWindowB, 0, cC.getDescriptionForP(b));
                String[] abc = {"CONTINUE"};
                cC.getC().showPopupOnClientQueryInfo("",b, "Another word has been added to your list:\n\n"+newMove.getWordForParticipantAsHTML(a)+"\n", abc, "Another word has been added to your list:", 0, cC.getDescriptionForP(a));
           }
       }
       
       
      
       this.saveSetToFile();
       allFeedback="";
       String msg = mves.getAllMovesToString() + "\n"+ mves.getAandBSuccessfulMoves();
       Debug.printDBG_andclear(""+id,msg);              
       //jcrs.printAndClear(msg);
       this.updateUI();
       
        

       
        
       
   }
   
   
   public synchronized void nextState_assignNEWSPEAKERS___SWAPPING_OR_VS_AND_MOVES(int forceSetSize,Participant a,Participant b){
        this.a=a;
        this.b=b;
        
        try{
          mves.mf.swapMovesORvsAND__MustBeCalledAfterSwappingPhysicalAndApparentPArtners();
        }catch (Exception e){e.printStackTrace();}
        
         try{
          this.maxSize=forceSetSize;
          
           mves.createNewMoveSet(forceSetSize, onlyALLOW, xorALLOW, andALLOW);
           this.jcrs.updateMaxSize(4);
        }catch (Exception e){e.printStackTrace();}
        this.saveSetToFile();
        
        this.jcrs.assignNEWSPEAKERS(a,b);
        
        this.aHTML = mves.getWordsForBToSelectHTML();
        this.bHTML = mves.getWordsForAToSelectHTML();
        allFeedback="";
        String msg =mves.getAllMovesToString()+"\n"+ mves.getAandBSuccessfulMoves();
        Debug.printDBG_andclear(""+id,msg);
        //jcrs.printAndClear(msg);
       // if(cC==null)System.exit(-234234);
        this.updateUI();
        
         cC.getC().changeWebpageTextAndColour(a, "ID1", "PLEASE START. YOUR SCORE IS: "+this.getScore(a)+"<br>"+"YOUR WORDS ARE:<br>"+ "\n"+ aHTML,aForegroundColour, aBackgroundColour);
         cC.getC().changeWebpageTextAndColour(b, "ID1", "PLEASE START.  YOUR SCORE IS: "+this.getScore(b)+"<br>"+"YOUR WORDS ARE:<br>"+"\n"+bHTML, bForegroundColour, bBackgroundColour);

        //String chatTextWindowA = "Please start! Your score is: "+ this.getScore(a);
        //String chatTextWindowB = "Please start! Your score is: "+ this.getScore(b);

        //cC.getC().sendArtificialTurnToRecipient(a, chatTextWindowA, 0, this.getDyadDescription());
        //cC.getC().sendArtificialTurnToRecipient(b, chatTextWindowB, 0, this.getDyadDescription());
         
   }
   
   

  public void startTask(Participant a, Participant b){
        
        this.a=a;
        this.b=b;
        startTimeOfCurrentSet = new Date().getTime();
        jcrs= new JCRSender2ParticipantsWithInsertedWords((DefaultConversationController)cC,this,a,b,maxSize,onlyALLOW,xorALLOW,andALLOW);
        jcrs.setVisible(true);
        jcrs.pack();
        jcrs.repaint();


        mves = new Moves(this);
        
        System.err.println("JCRHASSETUP");
        
        
        try{
           mves.createNewMoveSet(maxSize, onlyALLOW, xorALLOW, andALLOW);
        }catch (Exception e){
            System.err.println("ERRORSETTINGUP");
            e.printStackTrace();}
        this.saveSetToFile();
        
        
        
        this.aHTML = mves.getWordsForBToSelectHTML();
        this.bHTML = mves.getWordsForAToSelectHTML();
        allFeedback="";
        String msg = mves.getAllMovesToString()+"\n"+ mves.getAandBSuccessfulMoves();
        Debug.printDBG_andclear(""+id,msg);
        //jcrs.printAndClear(msg);
        this.updateUI();
        
       // if(cC==null)System.exit(-234234);
        
        
         cC.getC().changeWebpageTextAndColour(a, "ID1", "PLEASE START. YOUR SCORE IS: "+this.getScore(a)+"<br>"+"YOUR WORDS ARE:<br>"+ "\n"+ aHTML,aForegroundColour, aBackgroundColour);
         cC.getC().changeWebpageTextAndColour(b, "ID1", "PLEASE START.  YOUR SCORE IS: "+this.getScore(b)+"<br>"+"YOUR WORDS ARE:<br>"+"\n"+bHTML, bForegroundColour, bBackgroundColour);

      
        String chatTextWindowA = "Please start! Your score is: "+ this.getScore(a);
        String chatTextWindowB = "Please start! Your score is: "+ this.getScore(b);

        cC.getC().sendArtificialTurnToRecipient(a, chatTextWindowA, 0, this.getDyadDescription());
        cC.getC().sendArtificialTurnToRecipient(b, chatTextWindowB, 0, this.getDyadDescription());
  }


    public void saveSetToFile(){
        try{      
          Vector vAllMovesForBToSelect = (Vector)mves.getWordsForBToSelect().clone();
          Vector vAllMovesForAToSelect = (Vector)mves.getWordsForAToSelect().clone();
            
          String sAllMovesForBToSelect = Moves.asString(vAllMovesForBToSelect);
          String sAllMovesForAToSelect = Moves.asString(vAllMovesForAToSelect);

         long senddate = new Date().getTime();
         String dyaddescription = cC.getDescriptionForP(a);//IT DOESN'T MATTER WHETHER IT GETS IT FOR A OR B
         

         cC.getC().saveDataToFile(dyaddescription+"SELINFO", "server","server",senddate, senddate,senddate,"A: "+a.getUsername()+"  B: "+b.getUsername(),null);
         cC.getC().saveDataToFile(dyaddescription+"SELINFO", b.getUsername(), a.getUsername(),senddate, senddate, senddate,"FORB_SENTTO_A"+sAllMovesForBToSelect , null);
         
         cC.getC().saveDataToFile(dyaddescription+"SELINFO", a.getUsername(), b.getUsername(), senddate,senddate, senddate,"FORA_SENTTO_B"+sAllMovesForAToSelect , null);
        
       }catch(Exception e){
            e.printStackTrace();
       }
    }
  
  
    
    public String getDyadDescription(){
        try{
           return a.getParticipantID()+"_"+a.getUsername()+"_"+b.getParticipantID()+"_"+b.getUsername()+"_"+"("+getScore(a)+","+getLargestSuccessfulSetSize(a)+")"+"_"+"("+getScore(b)+","+getLargestSuccessfulSetSize(b)+")"+"_";
        }catch (Exception e){
            return "ONEOFTHEVALUESNOTSETYET";
        }
        
    }
    
  
    
    
    public void updateUI(){
         String msg =   
                 mves.getAandBSuccessfulMoves()+allFeedback +"\n"+
                 mves.getAllMovesToString() +"\n"+
                 ""+a.getUsername()+"  Score:"+this.getScore(a) +"  LargestSuccessfulSetSize:"+this.getLargestSuccessfulSetSize(a)+"\n"
                   +b.getUsername()+"  Score:"+this.getScore(b) + "  LargestSuccessfulSetSize:"+this.getLargestSuccessfulSetSize(b)+"\n";
          jcrs.printAndClear(msg);
          
          
          
           
    }
    
    
    

    public synchronized void nextSet(String prefix, String prefixFORSPREADSHEET){
        try{
        startTimeOfCurrentSet = new Date().getTime();
        mves.createNewMoveSet(maxSize,onlyALLOW,xorALLOW,andALLOW);
        
        }catch (Exception e){e.printStackTrace();}
        this.saveSetToFile();
        
        this.aHTML = mves.getWordsForBToSelectHTML();
        this.bHTML = mves.getWordsForAToSelectHTML();

        cC.getC().changeWebpageTextAndColour(a, "ID1", "YOUR SCORE IS: "+this.getScore(a)+"<br>"+"YOUR WORDS ARE:<br>"+ "\n"+ aHTML,aForegroundColour, aBackgroundColour);
        cC.getC().changeWebpageTextAndColour(b, "ID1", "YOUR SCORE IS: "+this.getScore(b)+"<br>"+"YOUR WORDS ARE:<br>"+"\n"+bHTML, bForegroundColour, bBackgroundColour);
        
        allFeedback="";
        //String msg =""+a.getUsername()+"  Score:"+this.getScore(a) +"  LargestSuccessfulSetSize:"+this.getLargestSuccessfulSetSize(a)+"\n"+b.getUsername()+"  Score:"+this.getScore(b) + "  LargestSuccessfulSetSize:"+this.getLargestSuccessfulSetSize(b)+"\n"+mves.getAllMovesToString();
        
        //Debug.printDBG_andclear(""+id,"");
        Debug.printDBG_andclear(""+id,""+a.getUsername()+"  Score:"+this.getScore(a) + "\n"+ mves.getAandBSuccessfulMoves()+"\n    LargestSuccessfulSetSize:"+this.getLargestSuccessfulSetSize(a)+"\n");
        Debug.printDBG(""+id,""+b.getUsername()+"  Score:"+this.getScore(b) + "    LargestSuccessfulSetSize:"+this.getLargestSuccessfulSetSize(b)+"\n");
        Debug.printDBG(""+id,mves.getAllMovesToString());
        this.updateUI();
       

        String chatTextWindowA = prefix +" Your score is: "+ this.getScore(a);
        String chatTextWindowB = prefix+" Your score is: "+ this.getScore(b);

        cC.getC().sendArtificialTurnToRecipient(a, chatTextWindowA, 0, cC.getDescriptionForP(a)+prefixFORSPREADSHEET); 
        cC.getC().sendArtificialTurnToRecipient(b, chatTextWindowB, 0, cC.getDescriptionForP(b)+prefixFORSPREADSHEET);
    }


    public void swapColours(Participant p){
        //System.exit(-4);
        String temp;
        if(p==a){
            temp=this.aBackgroundColour;
            this.aBackgroundColour=this.aForegroundColour;
            this.aForegroundColour=temp;
            cC.getC().changeWebpageTextAndColour(a, "ID1", "CORRECT. YOUR SCORE IS: "+this.getScore(a)+"<br>"+"YOUR WORDS ARE:<br>"+ "\n"+ this.aHTML,aForegroundColour, aBackgroundColour);
        }
        else if(p==b){
            temp=this.bBackgroundColour;
            this.bBackgroundColour=bForegroundColour;
            this.bForegroundColour=temp;
            cC.getC().changeWebpageTextAndColour(b, "ID1", "CORRECT. YOUR SCORE IS: "+this.getScore(b)+"<br>"+"YOUR WORDS ARE:<br>"+"\n"+ this.bHTML, bForegroundColour, bBackgroundColour);
        }
        else{
            //System.exit(-5);
        }
    }


    public void provideFeedback(Participant sender,Participant pRecipient, String message){
       if (flashOnSuccess&&message.equalsIgnoreCase(Moves.correctSET)){
           //cC.getC().sendArtificialTurnToRecipient(pRecipient, "****CORRECT! NEXT SET OF WORDS****", 0);
           this.swapColours(pRecipient);
       }
       else if(flashOnSuccess && message.equalsIgnoreCase(Moves.correctSIMULTANEOUSWORD)){
            this.swapColours(pRecipient);
            this.swapColours(a);
       }
       else if (flashOnSuccess&&message.startsWith("CORRECT")){
            this.swapColours(pRecipient);
       }
       else if (this.showNegativeFeedback){
           cC.getC().sendArtificialTurnToRecipient(pRecipient, message, 0);
       }
    }
    

    public synchronized void processSelection(Participant p,String s){
        allFeedback = allFeedback + "------"+p.getUsername()+"------"+s+"-----";

        s=s.trim();
        if(s.equalsIgnoreCase("///next")){
            nextSet("Timeout...next set","");
        }
        else{
             String name = s.substring(1).trim();
             String message = mves.evaluate(p,name);

             Participant recipientOfMessageToOther  = this.getOther(p);
             //if (message.equalsIgnoreCase(Moves.correctSET)){
             if (message.startsWith(Moves.correctSET)){
                 cC.getC().saveDataToFile(this.getDyadDescription()+"TASK", p.getParticipantID(), p.getUsername(), new Date().getTime(),new Date().getTime(), new Date().getTime(), s+"(SELECTION)"+message, null);
                 int numberOfWords = mves.moves.size();
                 updateScores(p,recipientOfMessageToOther,numberOfWords);
                 
                 
                 //this.addScore(p, numberOfWords);
                 //this.addScore(recipientOfMessageToOther, (long)mves.moves.size());
                 nextSet("CORRECT! Next set of words:","");
                 provideFeedback(p,recipientOfMessageToOther,message);
             }
             else if(message != null & !message.equalsIgnoreCase("")){
                 cC.getC().saveDataToFile(this.getDyadDescription()+"TASK", p.getParticipantID(), p.getUsername(), new Date().getTime(),new Date().getTime(), new Date().getTime(), s+"(SELECTION)"+message, null);
                 allFeedback = allFeedback + recipientOfMessageToOther.getUsername()+":"+message+"\n";
                 provideFeedback(p,recipientOfMessageToOther,message);
             }
             else{
                 cC.getC().saveDataToFile(this.getDyadDescription()+"TASK", p.getParticipantID(), p.getUsername(), new Date().getTime(),new Date().getTime(), new Date().getTime(), s+"(SELECTION)"+message, null);
                 
                 allFeedback = allFeedback+ recipientOfMessageToOther.getUsername()+": NOMESSAGE\n";
             }
             Debug.printDBG_andclear(""+id,mves.getAllMovesToString()+"\n"+allFeedback +"\n"+ mves.getAandBSuccessfulMoves());
             //jcrs.printAndClear(mves.getAllMovesToString()+"\n"+allFeedback +"\n"+ mves.getAandBSuccessfulMoves());
             this.updateUI();
            
             
             //cC.getC().saveDataToConversationHistory(this.getDyadDescription(),"WORDADDED " + newMove.getName() + " "+newMove.getPriorMoveType() + " "+newMove.getType() );
        }

    }

    public Object[] getMostRecentAndAllMovesForString(String turntext){
        String[] turntextsplit = StringOperations.splitOnlyText(turntext);
        //Vector allMovesthatmatch = mves.findMovesForWords(turntextsplit);

        Object[] mostRecent_And_AllMoves = mves.doesTextMentionMostRecent_andFindAllMovesForWords(turntextsplit);
        return mostRecent_And_AllMoves;
    }



    public Participant getOther(Participant p){
        if (p==this.a)return b;
        if(p==this.b)return a;
        return null;
    }

    
    static Hashtable scores = new Hashtable();
    static Hashtable participantsLargestSuccessfulSetSize = new Hashtable();
    
    
    static public void updateScores(Participant a, Participant b, int setsize){
         setLargestSuccessfulSetSizeNEW(a,setsize);
         setLargestSuccessfulSetSizeNEW(b,setsize);
         addScoreNEW(a,setsize);
         addScoreNEW(b,setsize);
    }
    
    
    
    static public int getLargestSuccessfulSetSize(Participant p){
         Object o = participantsLargestSuccessfulSetSize.get(p);
        if(o==null){
            participantsLargestSuccessfulSetSize.put(p, (int)0);
        }
        return (Integer)participantsLargestSuccessfulSetSize.get(p);
    }
    static public void setLargestSuccessfulSetSizeNEW(Participant p,int setSize){
        
        Object o = participantsLargestSuccessfulSetSize.get(p);
        int oi = (Integer)o;        
        if(o==null || setSize>oi){
            participantsLargestSuccessfulSetSize.put(p, (int)setSize);
        }
        else if ( setSize>oi){
            participantsLargestSuccessfulSetSize.put(p, (int)setSize);
        }     
        //return (Long)scores.get(p);
    }
    
    
    static public int getScore(Participant p){
         Object o = scores.get(p);
        if(o==null){
            scores.put(p, (int)0);
        }
        Object score = scores.get(p);
        if(score==null)score =(int)0;
        return (Integer)score;
    }

    static public void addScoreNEW(Participant p, int numberOfCorrect){
        if(scores==null)scores=new Hashtable();
        Object o = scores.get(p);
        if(o==null){
            scores.put(p, (int)0);
        }
        int score = (Integer)scores.get(p);
        score=score+numberOfCorrect;
        scores.put(p, score);
    }

    public synchronized boolean findAndReplaceAndReset(Participant p){
        if(this.a!=null&a.getParticipantID().equalsIgnoreCase(p.getParticipantID())){
            this.a=p;
             cC.getC().sendArtificialTurnToRecipient(a, "You've managed to log back in...starting new set.", 0,cC.getDescriptionForP(a));
             cC.getC().sendArtificialTurnToRecipient(b, "Other participant has logged back in...starting new set", 0,cC.getDescriptionForP(b));
             this.nextSet("Restarted", "RESTARTED BECAUSE OF RELOGIN BY "+p.getParticipantID()+"_"+p.getUsername());
            return true;
        }
        else if(this.b!=null&&b.getParticipantID().equalsIgnoreCase(p.getParticipantID())){
            this.b=p;
            cC.getC().sendArtificialTurnToRecipient(a, "Other participant has logged back in...starting new set", 0,cC.getDescriptionForP(a));
            cC.getC().sendArtificialTurnToRecipient(b, "You've managed to log back in...starting new set.", 0,cC.getDescriptionForP(b));
            this.nextSet("Restarted", "RESTARTED BECAUSE OF RELOGIN BY "+p.getParticipantID()+"_"+p.getUsername());
            return true;
            
        }
        return false;
    }
    
    
    public Participant getParticipantA(){
        return this.a;
    }
    public Participant getParticipantB(){
        return this.b;
    }
    
    public int getWordSetSwapNumber(){
      return this.wordSetSwapNumber;   
    }
     
    public synchronized void swapWordSets(){
        this.wordSetSwapNumber++;
    }
    public Moves getMoves(){
        return this.mves;   
    }
     
}
