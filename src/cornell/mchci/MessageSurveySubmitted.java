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
public class MessageSurveySubmitted  extends Message implements Serializable {
    
    private String id;
    private String header;
    private String surveyResults;
    
    public MessageSurveySubmitted (String id, String header, String surveyResults){
        super("server","server");
        this.id=id;
        this.header=header;
        this.surveyResults = surveyResults;
    }
    
    public String getHeader() {
        return header;
    }

    public String getId() {
        return id;
    }
    
     public String getSurveyResults() {
        return surveyResults;
    }
    
}
