package diet.message;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

import diet.parameters.LongParameter;
import diet.parameters.StringParameter;

public class Message  implements Serializable {

  private Date dateOfCreation = new Date();
  private Date dateOfReceipt;
  private String email;
  private String username;

  
  public Message(String email, String username) {
      this.email=email;
      this.username=username;
  }

  public void setTimeOnServerOfReceipt() {
    dateOfReceipt = new Date();
  }

  public Date getTimeOnServerOfReceipt() {
    return dateOfReceipt;
  }
  public Date getTimeOnClientOfCreation() {
    return dateOfCreation;
  }

  public String getUsername() {
    return username;
  }

  public void overrideSetUsername(String newUserName) {
    this.username=newUserName;
  }

  public String getEmail(){
      return email;
  }
  public String getMessageClass(){
  return "Message";
}

 
  
}
