/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cornell.mchci;

import diet.message.Message;
import java.io.Serializable;

/**
 *
 * @author gabeculbertson
 */
public class MessageDisplaySurvey extends Message implements Serializable {
    
    private String id;
    private String header;
    
    public MessageDisplaySurvey (String id, String header){
        super("server","server");
        this.id=id;
        this.header=header;
    }
    
    public String getHeader() {
        return header;
    }

    public String getId() {
        return id;
    }
    
}
