package diet.message;
import diet.debug.Debug;
import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

import diet.parameters.IntParameter;
import diet.parameters.LongParameter;
import diet.parameters.StringParameter;

public class MessageChatTextFromClient extends Message implements Serializable {

  private Vector keyPresses;
  private boolean processed =false;
  private long startOfTypingCalculatedForServer;
  private long endOfTypingCalculatedForServer;
  private String text;
  private boolean hasBeenBlocked;

  public boolean hasBeenRelayedByServer = false;
  
  public long startOfTypingOnClient;
  public long endOftypingOnClient;
  
  
  //public long priorChatTextByOther_TimeOnClientOfReceipt = 0;
  //public String priorChatTextByOther_Username = "";
  //public String priorChatTextByOther_ChatText = "";
   String[] mostRecentChatTextByPrior ;
  
  
  public MessageChatTextFromClient(String email,String username,String t, long startOfTypingOnClient, boolean currentTurnBeingConstructedHasBeenBlocked, Vector keyPresses, String[] mostRecentPriorChatText) {
    super(email,username);
    setText(t);
    this.setKeyPresses(keyPresses);
    this.startOfTypingOnClient = startOfTypingOnClient;
    endOftypingOnClient = new Date().getTime();
    //setEndOfTyping(new Date().getTime());
    if(startOfTypingOnClient <0) startOfTypingOnClient = endOftypingOnClient;
    //System.out.println("client created message with "+startOfTyping+" "+getEndOfTyping());
    //System.out.println("Text is: "+t);
    //System.out.println("hasbeenblocked:"+currentTurnBeingConstructedHasBeenBlocked);
    this.setHasBeenBlocked(currentTurnBeingConstructedHasBeenBlocked);
    this.mostRecentChatTextByPrior=mostRecentPriorChatText;
    //System.err.println("AT LEAST ON THE CLIENT IT IS "+mostRecentChatTextByPrior[0]);
  }

public void timeStampDEPRECATED(){
  //super.timeStamp();
  //setStartOfTyping(super.getTimeStamp().getTime()  - (getEndOfTyping() - getStartOfTyping()));
  //setEndOfTyping(super.getTimeStamp().getTime());
 
}


    @Override
    public void setTimeOnServerOfReceipt() {
        
        
        
        super.setTimeOnServerOfReceipt();
        this.startOfTypingCalculatedForServer  = super.getTimeOnServerOfReceipt().getTime() - (this.endOftypingOnClient-this.startOfTypingOnClient);
        this.endOfTypingCalculatedForServer = super.getTimeOnServerOfReceipt().getTime();
    }


  public String getText() {
    return text;
  }

  public Vector getKeypresses(){
     return keyPresses;
  }

  public int getNoOfDeletes(){
    //if(2<5)return 5;
    //8 or 127
    int delcount = 0;
    try{
    for (int i =0 ; i < getKeyPresses().size();i++){
       Keypress keyp = (Keypress)getKeyPresses().elementAt(i);
       if(keyp.isDel()){
          delcount = delcount+1;
       }
    }
    }catch(Exception e){}
    return delcount;
  }
  
  public boolean hasBeenBlocked(){
      return this.isHasBeenBlocked();
  }
  public long getServerTypingOnset(){
    return this.startOfTypingCalculatedForServer;
  }
  public long getServerTypingENTER(){
    return this.endOfTypingCalculatedForServer;
  }

   

   
  
  
  public String getMessageClass(){
      return "ChatTextFromClient";
  }

  

  

   

 

    public Vector getKeyPresses() {
        return keyPresses;
    }

    public void setKeyPresses(Vector keyPresses) {
        this.keyPresses = keyPresses;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

   

    public void setText(String text) {
        this.text = text;
    }

    public boolean isHasBeenBlocked() {
        return hasBeenBlocked;
    }

    public void setHasBeenBlocked(boolean hasBeenBlocked) {
        this.hasBeenBlocked = hasBeenBlocked;
    }
  
  
    public void setChatTextHasBeenRelayedByServer(){
        this.hasBeenRelayedByServer=true;
    }
 
    public String[] getMostRecentPriorChatText(){
        //////FOR DEBUGGING: this.mostRecentChatTextByPrior[2]="PRIORTEXT3"+this.mostRecentChatTextByPrior[2];
        return this.mostRecentChatTextByPrior;
    }
    
    
    
    
}
