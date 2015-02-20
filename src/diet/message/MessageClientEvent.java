package diet.message;
import java.io.Serializable;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class MessageClientEvent extends Message implements Serializable{

    String nameOfEvent;
   
    
    public MessageClientEvent(String nameOfEvent, String email, String username) {
        super(email, username);
        this.nameOfEvent=nameOfEvent;
    }

    
    
    public String getMessageClass(){
    return "ClientEvent";
}

   public String getNameOfEvent(){
       return nameOfEvent;
   }
  
   
}
