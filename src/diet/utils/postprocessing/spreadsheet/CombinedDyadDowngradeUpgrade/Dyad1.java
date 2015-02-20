/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 * 

 * modify the analysis so that it excludes the EDIT files and doesn't add them.
 * add a column for the text of the CR - is it repeated in the response.
 * add a column - does it contain a question mark?
 * 
 * Exclude "DUMMY" turns from the analysis
 * 
 */
package diet.utils.postprocessing.spreadsheet.CombinedDyadDowngradeUpgrade;


import diet.utils.stringsimilarity.StringSimilarityMeasure;
import diet.utils.stringsimilarity.SyntacticSimilarityMeasure;
import java.util.Vector;

/**
 *
 * @author sre
 */
public class Dyad1 {

    Vector contiguouscontributions = new Vector();
    public  String dyadN;
    public String dyadNoCorrected;
    String p1UniqID="";
    String p2UniqID="";
    
    public Dyad1(String dyadNo,String dyadNoCorrected) {
       
        this.dyadN = dyadNo;
        this.dyadNoCorrected=dyadNoCorrected;
    }
    
   
    
    public void addRowFromSpreadsheet(String uniqid,String participantID, String sender , String dyadno, String dyadnocorrected,int dels, int edits, String text, String descType, long sanity, String turntype, String recipient, String cbyctype){
        //System.out.println("INDYAD");
        Spreadsheet1.jo.appendText("1"+uniqid+"\n");
        if(!p1UniqID.equalsIgnoreCase("") & !sender.equalsIgnoreCase("server")) {
             p1UniqID = uniqid;
        }        
        else if(!p2UniqID.equalsIgnoreCase("") & !sender.equalsIgnoreCase("server")){
            if(!uniqid.equals(p1UniqID)){
                p2UniqID = uniqid;
            }
        }  
        else if( ! (p1UniqID.equals(uniqid)|p2UniqID.equals(uniqid))){
           if(!p1UniqID.equalsIgnoreCase("") &!p2UniqID.equalsIgnoreCase("")  &   !sender.equalsIgnoreCase("server")) {
               System.out.println("ERROR...."+dyadN+"...has too many participants: "+uniqid+ "   "+dyadno+"...at sanity: "+sanity);
               System.out.println("ERROR:"+p1UniqID);
               System.out.println("ERROR:"+p2UniqID);
               System.exit(-524);
           }
           // System.err.println("ERROR...."+dyadN+"...has too many participants: "+uniqid);
           // System.err.println(p1UniqID);
           // System.err.println(p2UniqID);
            
           // System.exit(-5);
        }
        if(contiguouscontributions.size()==0||sender.equalsIgnoreCase("server")){
            ContiguousContribution1 cc = new ContiguousContribution1(uniqid,participantID,sender);
            //System.out.println("INDYAD1:" +text);
            cc.addcontribution(participantID,sender,dels, edits,  text, descType,  sanity,turntype, recipient);
            contiguouscontributions.addElement(cc);
        }
        else{
            ContiguousContribution1 lastContiguousContribution = (ContiguousContribution1)contiguouscontributions.lastElement();
            if (lastContiguousContribution.uniqid.equals(uniqid)){
                //System.out.println("INDYAD2:" +text);
               lastContiguousContribution.addcontribution(participantID,sender,dels, edits,  text, descType,  sanity, turntype, recipient);
            }
            else{
              ContiguousContribution1 cc = new ContiguousContribution1(uniqid,participantID,sender);
              // System.out.println("INDYAD3:" +text);
               cc.addcontribution(participantID,sender,dels, edits,  text, descType,  sanity, turntype,recipient);
               contiguouscontributions.insertElementAt(cc, contiguouscontributions.size());
               Spreadsheet1.jo.appendText("2"+"\n");
               lastContiguousContribution=cc;
            }
            if(lastContiguousContribution.type.equalsIgnoreCase("RESPONSE")&&lastContiguousContribution.ccCR==null){
                 this.findTSTandCR(lastContiguousContribution);
            }
            if(cbyctype.startsWith("CR: ")){
                String cr = cbyctype.replace("CR: ", "");
                lastContiguousContribution.crTEXT=cr;
            }
        }
        
        
        
    }
    
    
    
       public void findTSTandCR(ContiguousContribution1 response){
            boolean foundCR = true;
            boolean foundTST = false;
            int index = this.contiguouscontributions.indexOf(response);
            for(int i =index-1;i>=0;i--){
                ContiguousContribution1 cCont = (ContiguousContribution1)contiguouscontributions.elementAt(i);
                if(!foundCR&&cCont.type.equalsIgnoreCase("CRWHY")){
                    //need to store the recipient
                    response.ccCR = cCont;
                    foundCR = true;      
                    response.crTEXT = cCont.crTEXT;
                    //System.exit(-5);
                }          
                else if(!foundTST && cCont.sender.equalsIgnoreCase(response.sender)){
                    response.ccTST=cCont;
                    cCont.ccRESPONSE=response;
                    cCont.type="TST";
                    foundTST=true;
                    
                }
                if(foundTST&&foundCR)return;    
                
                
                if(cCont.type.equalsIgnoreCase("RESPONSE")){
                    System.err.println("WENT BACK TOO FAR -- FOUND ERROR -- ");System.exit(-23432469);
                }
                
            }
            System.out.println("PID:"+response.participantID);
            System.out.println("sender:"+response.sender);
            System.out.println("TEXT:"+response.getFullText());
            System.exit(-23423455);
            
       }
    
    
      public void calculateDistancesFromTSTAndFromRESPONSE(){
           int distanceFROMCRWHY=1;
           int distanceFROMRESPONSE=1;
           int distanceFROMDUMMY=1;
           for(int i=0;i<contiguouscontributions.size()-1;i++){
               ContiguousContribution1 contigc1 = ( ContiguousContribution1)contiguouscontributions.elementAt(i);
               if(contigc1.type.equalsIgnoreCase("CRWHY"))distanceFROMCRWHY = 0;
               if(contigc1.type.equalsIgnoreCase("RESPONSE"))distanceFROMRESPONSE = 0;
               if(contigc1.type.equalsIgnoreCase("DUMMY"))distanceFROMDUMMY = 0;
               contigc1.distanceFROM_RESP = distanceFROMRESPONSE;
               contigc1.distanceFROM_CRWHY=distanceFROMCRWHY;
               contigc1.distanceFROM_DUMMY = distanceFROMDUMMY;
               distanceFROMCRWHY++;
               distanceFROMRESPONSE++;
               distanceFROMDUMMY++;
           }
      }
       
       
       
      public void calculateAlignmentScores(){
          
          
          
      
          for(int i=0;i<contiguouscontributions.size()-1;i++){
              ContiguousContribution1 contigc1 = ( ContiguousContribution1)contiguouscontributions.elementAt(i);
              ContiguousContribution1 contigc2 = ( ContiguousContribution1)contiguouscontributions.elementAt(i+1);
              
              if(contigc1.type.equalsIgnoreCase("CRWHY")){
                  continue; 
              }
              if(contigc1.type.equalsIgnoreCase("TST")){
                  contigc2=contigc1.ccRESPONSE;
              }
              else if(contigc2.type.equalsIgnoreCase("response")){
                  continue;
              }
              
              
              //Lexical Alignment Scores
              int[] unqwdAB = StringSimilarityMeasure.getNoOfUniqueWordsInA_NoOfUniqueWordsInB_NoOfMatchingWords(contigc1.getFullText(), contigc2.getFullText());
              contigc1.ppUniqueWordCount=unqwdAB[0];
              contigc2.ppUniqueWordCount=unqwdAB[1];
              contigc1.ppNoOfUniqueWordsthatAreInThisContiguousContributionAndAlsoInNextContiguousContribution=unqwdAB[2];
              contigc2.ppNoOfUniqueWordsthatAreInThisContiguousContributionAndAlsoInPreviousContiguousContribution=unqwdAB[2];
              
              
              //DescTypeAlignment - Highest And Lowest Scores
              String[] contigc1DescTypeHighLow = contigc1.getHighestAndLowestDesctype();
              String[] contigc2DescTypeHighLow = contigc2.getHighestAndLowestDesctype();
              
              String contigc1Highest =contigc1DescTypeHighLow[0];
              String contigc2Highest =contigc2DescTypeHighLow[0];
              
              String contigc1Lowest =contigc1DescTypeHighLow[1];
              String contigc2Lowest =contigc2DescTypeHighLow[1];
              
              
              
              int diffInHighest = StringSimilarityMeasure.howMuchMoreAbstractIsAThanB(contigc1Highest, contigc2Highest);
              int diffInLowest = StringSimilarityMeasure.howMuchMoreAbstractIsAThanB(contigc1Lowest, contigc2Lowest);
              
              contigc1.ppHowMuchmoreAbstractIsThisContiguousTurnsMOSTAbstractDescriptionComparedWithTheMOSTAbstractOfNextContiguousTurn=diffInHighest;
              contigc1.ppHowMuchmoreAbstractIsThisContiguousTurnsLEASTtAbstractDescriptionComparedWithTheLEASTAbstractOfNextContiguousTurn=diffInLowest;
              
              contigc2.ppHowMuchmoreAbstractIsThisContiguousTurnsMOSTAbstractDescriptionComparedWithTheMOSTAbstractOfPriorContiguousTurn=-diffInHighest;
              contigc2.ppHowMuchmoreAbstractIsThisContiguousTurnsLEASTtAbstractDescriptionComparedWithTheLEASTAbstractOfPriorContiguousTurn=-diffInLowest;
              
              
              
              //DescTypeAlignment - Total number Of Unique Overlaps
              int totaloverlap=0;
              if(contigc1.containsFig&contigc2.containsFig)totaloverlap++;
              if(contigc1.containsPath&contigc2.containsPath)totaloverlap++;
              if(contigc1.containsLine&contigc2.containsLine)totaloverlap++;
              if(contigc1.containsMatrix&contigc2.containsMatrix)totaloverlap++;
              
              contigc1.ppNoOfDescTypesthatAreInNextContiguouscontribution=totaloverlap;
              contigc2.ppNoOfDesctypesThatAreInPreviousContiguouscontribution=totaloverlap;
              
              
              //Syntactic alignment
              int[] syntacticalignmentscores = SyntacticSimilarityMeasure.getRulesInA_getRulesInB_getCommonRules(contigc1.getFullText(), contigc2.getFullText());
              contigc1.ppUniqueNumberofSyntacticRules=syntacticalignmentscores[0];
              contigc2.ppUniqueNumberofSyntacticRules=syntacticalignmentscores[1];
              contigc1.ppUniqueNumberofSyntacticRulesSharedWithNext=syntacticalignmentscores[2];
              contigc2.ppUniqueNumberofSyntacticRulesSharedWithPrior=syntacticalignmentscores[2];
              
              
          }
    
      }
    
    
    
    
    
      public Vector getRowsForSpreadsheet(){
          
          Vector rowsForSpreadsheet = new Vector();
          for(int i=0;i<contiguouscontributions.size();i++){
              ContiguousContribution1 contigc = ( ContiguousContribution1)contiguouscontributions.elementAt(i);
              Vector contributions = contigc.contributions;
              
              for(int j=0;j<contributions.size();j++){
                  Contribution1 contri = (Contribution1)contributions.elementAt(j);
                  String contigType = "MIDDLE";
                  if(contributions.size()==1) {
                      contigType = "ONLY";
                  }
                  else if(contributions.indexOf(contri)==0){
                      contigType = "FIRST";
                  }
                  else if(contributions.indexOf(contri)==contributions.size()-1) {
                      contigType = "LAST";
                  }               
                               
                  
                  String row = 
                          contigc.uniqid+"|"+ 
                          this.dyadN+"|"+
                          this.dyadNoCorrected+"|"+
                          contigType+"|"+
                          contri.sanity+"|"+
                          contri.text+"|"+ 
                          contri.descType +"|"+ 
                          contigc.type +"|"+
                          contigc.distanceFROM_CRWHY +"|"+
                          contigc.distanceFROM_RESP +"|"+
                          contigc.distanceFROM_DUMMY;
                          
                  
                          
                  
                  
                  
                  if(contigType.equalsIgnoreCase("FIRST")|contigType.equalsIgnoreCase("ONLY")){
                          String[] contigc1DescTypeHighLow = contigc.getHighestAndLowestDesctype();
                      
                          row = row + "|"+
                          
                                  
                          
                          contigc.ppUniqueWordCount+ "|"+
                          //UniqueWords        
                                  
              
                           contigc.ppNoOfUniqueWordsthatAreInThisContiguousContributionAndAlsoInPreviousContiguousContribution   + "|"+            
                            //UniqwrdsInThisAndPrior  
                                  
                                  
                          contigc.ppNoOfUniqueWordsthatAreInThisContiguousContributionAndAlsoInNextContiguousContribution+ "|"+
                          //UniqwordsInThisAndNext
                                  
                                                  
                                  
                                  
                          "XXXXXXXX"+ "|"+
                                
                                  
                                  
                          contigc1DescTypeHighLow[0]+ "|"+
                          //DescTMostAbs
                  
                          contigc.ppHowMuchmoreAbstractIsThisContiguousTurnsMOSTAbstractDescriptionComparedWithTheMOSTAbstractOfPriorContiguousTurn+ "|"+
                          //DescTypeHowMuchMoreAbsOfHighestThanPrior
                                  
                                  
                          contigc.ppHowMuchmoreAbstractIsThisContiguousTurnsMOSTAbstractDescriptionComparedWithTheMOSTAbstractOfNextContiguousTurn+ "|"+
                          //how much higher is this highest in this than the highest in the next
                          //DescTypeHowMuchMoreAbsOfHighestThanNext
                                  
                          contigc1DescTypeHighLow[1]+ "|"+
                          //lowest desc type
                           //DescTLeastAbs
                                  
                                  
                          contigc.ppHowMuchmoreAbstractIsThisContiguousTurnsLEASTtAbstractDescriptionComparedWithTheLEASTAbstractOfPriorContiguousTurn+ "|"+
                          //how much higher is the lowest in this than the lowest in the previous
                           //DescTypeHowMuchMoreAbsOfLowestThanPrior
                            
                          contigc.ppHowMuchmoreAbstractIsThisContiguousTurnsLEASTtAbstractDescriptionComparedWithTheLEASTAbstractOfNextContiguousTurn+ "|"+
                          //how much higher is the lowest in this than the lowest in the next
                           //DescTypeHowMuchMoreAbsOfLowestThanNext
                           
                          contigc.uniqueDesctypeCount+ "|"+
                          //total number of unique DescTypes
                          //DescTypeNoOfUnique   
                                  
                          contigc.ppNoOfDesctypesThatAreInPreviousContiguouscontribution+"|"+
                          //total unique shared with prior
                          //DescTypeUniqueSharedInPrior
                                  
                          
                          contigc.ppNoOfDesctypesThatAreInPreviousContiguouscontribution+ "|"+
                          //total unique shared with next
                          //DescTypeUniqueSharedWithNext
                          
                          
                           "XXXXXXXX"+ "|"+
                                  
                                  
                          contigc.ppUniqueNumberofSyntacticRules + "|"+
                                  
                          contigc.ppUniqueNumberofSyntacticRulesSharedWithPrior + "|"+
                                  
                          contigc.ppUniqueNumberofSyntacticRulesSharedWithNext;
                          
                          
                          
                  
                  }
                  rowsForSpreadsheet.addElement(row);
                  
              }
              
              
          }
          return rowsForSpreadsheet;
      }
    
    
    
}
