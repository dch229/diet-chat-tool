/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.utils.postprocessing.spreadsheet.CBYC;

import diet.utils.stringsimilarity.StringSimilarityMeasure;
import java.util.Vector;

/**
 *
 * @author sre
 */
public class ContiguousContribution {
    
     Vector contributions = new Vector();

     public String uniqid;
     
     
     
     
     public int delcount=0;
     public int editcount=0;
     
     public boolean containsNon=false;
     public boolean containsFig=false;
     public boolean containsPath=false;
     public boolean containsLine=false;
     public boolean containsMatrix=false;
     
     
     public int ppUniqueWordCount;
     public int ppNoOfUniqueWordsthatAreInThisContiguousContributionAndAlsoInPreviousContiguousContribution;
     public int ppNoOfUniqueWordsthatAreInThisContiguousContributionAndAlsoInNextContiguousContribution;
     
    
     
     
     
     
     public int ppHowMuchmoreAbstractIsThisContiguousTurnsMOSTAbstractDescriptionComparedWithTheMOSTAbstractOfPriorContiguousTurn;
     public int ppHowMuchmoreAbstractIsThisContiguousTurnsLEASTtAbstractDescriptionComparedWithTheLEASTAbstractOfPriorContiguousTurn;
             
             
     public int ppHowMuchmoreAbstractIsThisContiguousTurnsMOSTAbstractDescriptionComparedWithTheMOSTAbstractOfNextContiguousTurn;
     public int ppHowMuchmoreAbstractIsThisContiguousTurnsLEASTtAbstractDescriptionComparedWithTheLEASTAbstractOfNextContiguousTurn;
     
     
     
     public int uniqueDesctypeCount;
     public int ppNoOfDesctypesThatAreInPreviousContiguouscontribution;
     public int ppNoOfDescTypesthatAreInNextContiguouscontribution;
     
     
     public int ppUniqueNumberofSyntacticRules;
     public int ppUniqueNumberofSyntacticRulesSharedWithPrior;
     public int ppUniqueNumberofSyntacticRulesSharedWithNext;
     
     
     public int distanceFROM_CRWHY =0;
     public int distanceFROM_RESP=0;
     public int distanceFROM_DUMMY=0;
     
     
     
     public String type =null;  //TST
                              //CR
                              //RESPONSE
                              //ACK
                              //postACK
     
     public String participantID;
     public String sender ="";
     public String recipient = null;
     
     
     public ContiguousContribution ccTST = null;
     public ContiguousContribution ccCR = null;
     public ContiguousContribution ccRESPONSE = null;
     
     public String crTEXT="";
     
    public ContiguousContribution(String uniqid,  String participantID, String sender) {
        this.uniqid=uniqid;
        this.participantID=participantID;
        this.sender=sender;
        
    }
     
     
     public String getFullText(){
         String fullText="";
         for(int i=0;i<contributions.size();i++){
            Contribution conti = (Contribution)contributions.elementAt(i);
            fullText = fullText + " "+conti.text;
        }
         return fullText;
    }
     
     
     
     
     
     
     
     
     public String[] getHighestAndLowestDesctype(){
         String highest = ((Contribution)contributions.elementAt(0)).descType;
         String lowest =((Contribution)contributions.elementAt(0)).descType;
         for(int i=0;i<contributions.size();i++){
            Contribution conti = (Contribution)contributions.elementAt(i);
            
            if(StringSimilarityMeasure.isDescTypeAMoreAbstractThanB(conti.descType, highest)){
                highest=conti.descType;
            }
            if(!conti.descType.equalsIgnoreCase("N") &StringSimilarityMeasure.isDescTypeAMoreAbstractThanB( lowest, conti.descType)){
                lowest=conti.descType;
            }
           
            
            
         }
          return new String []{highest,lowest};
     }
     
     
     
     
     
     public void addcontribution(String participantID, String sender, int dels, int edits, String text, String descType, long sanity, String turntype, String recipient){
         if(!this.participantID.equalsIgnoreCase(participantID)&!this.sender.equalsIgnoreCase(sender)){
             System.out.println("PID:"+this.participantID+"----"+participantID);
             System.out.println("SDENDER:"+this.sender+"----"+sender);
             System.exit(-23421);
         }
         
         Contribution contri = new Contribution(dels, edits, text, descType,sanity, turntype);
         contributions.addElement(contri);
         delcount = delcount+dels;
         edits = this.editcount+edits;
         
         
         
         
         
         if(this.recipient==null){
             this.recipient=recipient;
         }
         else if(!this.recipient.equalsIgnoreCase(recipient)){
             System.exit(-234); //check to make sure all the contributions are consistent
         }
         
         
         if(this.type==null){
             this.type=turntype;
         }
         else if(turntype.equalsIgnoreCase("CRWHY")){
             type="CR";
             if(type!=null){System.err.println("SANITY"+sanity);System.exit(-3);}
         }
         else if(turntype.equalsIgnoreCase("DUMMY")){
             type="DUMMY";
             System.exit(-23234);
         }
         else if(type!=null & turntype.equalsIgnoreCase("ACK")){
             type="ACK";
      
         }
         else if(type!=null & turntype.equalsIgnoreCase("RESPONSE")){
             type="RESPONSE";
             //if(type!=null){System.err.println("SANITY"+sanity);System.exit(-4);}
         }
         
         
         
         
         
         
         
         
         
         
         if(descType.equalsIgnoreCase("N")){
             containsNon=true;
         }
         else  if(!containsFig&descType.equalsIgnoreCase("F")){
             containsFig=true;
             uniqueDesctypeCount++;
             
         }
         else  if(!containsPath&descType.equalsIgnoreCase("P")){
             containsPath=true;
             uniqueDesctypeCount++;
         }
         else  if(!containsLine&descType.equalsIgnoreCase("L")){
             containsLine=true;
             uniqueDesctypeCount++;
         }
         else  if(!containsMatrix&descType.equalsIgnoreCase("M")){
             containsMatrix=true;
             uniqueDesctypeCount++;
         }
         else {
             if(!(descType.equalsIgnoreCase("N")|descType.equalsIgnoreCase("F")|descType.equalsIgnoreCase("P")|descType.equalsIgnoreCase("L")|descType.equalsIgnoreCase("M"))){
             
             System.err.println("ERROR"+sanity+"---"+text+"---"+descType);
             System.err.println("ERROR");
             System.err.println("ERROR");
             System.err.println("ERROR");
             
             System.exit(-542134131);
         }
         }
         
     }
     
     public String getText(){
         String returnVal = "";
         for(int i=0;i<this.contributions.size();i++){
             Contribution cCont = (Contribution)contributions.elementAt(i);
             returnVal = returnVal +"--"+cCont.text;
         }
         return returnVal;
         
     }
     
     
}
