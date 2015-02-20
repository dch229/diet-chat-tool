/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.utils.stringsimilarity;

import diet.utils.postprocessing.spreadsheet.CBYC.Spreadsheet;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.Tree;
import java.util.Vector;

/**
 *
 * @author sre
 */
public class SyntacticSimilarityMeasure {
    
            static LexicalizedParser lp;
            static boolean hasBeenStarted = false;
    
    public SyntacticSimilarityMeasure(){
            String grammar =  "edu/stanford/nlp/models/lexparser/englishPCFG.caseless.ser.gz";
            String[] options = { "-maxLength", "200", "-retainTmpSubcategories" };
            
    }
    
    
    static public void loadParser(){
         String grammar =  "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
            String[] options = { "-maxLength", "200", "-retainTmpSubcategories" };
            lp = LexicalizedParser.loadModel(grammar, options);
           hasBeenStarted = true;
    }
         
         
             
   
    
    
    
    
    static public  void getAllRules(Tree parse, Vector allRules){
        String dash = "IIIIIIII";
        String space ="";
        
        if(parse.isLeaf())return;
        if(parse.isPreTerminal())return;
        if(parse.isPreTerminal())return;
        
        Tree[] children = parse.children();
        String newRule = parse.value()+dash;
        for(int i=0;i<children.length;i++){
            newRule = newRule+ space+children[i].value();
            
        }
        allRules.addElement(newRule);
        
        for(int i=0;i<children.length;i++){
            getAllRules(parse.children()[i],allRules);
        } 
}
    
    
    
    static public String getRulesAsStrings(String s){
        if(!hasBeenStarted)loadParser();
        Tree parse = lp.apply(Sentence.toWordList(s));
        Vector allRules = new Vector();
        getAllRules(parse,allRules);
        String allRulesAsString="";
        for (int i=0;i<allRules.size();i++){
            allRulesAsString = allRulesAsString + "     "+ (String)allRules.elementAt(i);
        }
        //System.out.println(allRulesAsString);
        return allRulesAsString;
    }
    
    
    static public int [] getRulesInA_getRulesInB_getCommonRules(String a, String b){
        String allRulesInA="";
        String allRulesInB="";
        
        try{
          allRulesInA = getRulesAsStrings(a);
          allRulesInB = getRulesAsStrings(b);
        }catch (Exception e){
             Spreadsheet.jo.appendText("CRASH\n");
        }
        Vector aVect = StringSimilarityMeasure.removeDuplicates(StringSimilarityMeasure.splitIntoWords(allRulesInA));
        Vector bVect = StringSimilarityMeasure.removeDuplicates(StringSimilarityMeasure.splitIntoWords(allRulesInB));
        

        int matchingStrings =0;
        for(int i=0;i<aVect.size();i++){
            String asunique = (String)aVect.elementAt(i);
            for(int j=0;j<bVect.size();j++){
                String bsunique = (String)bVect.elementAt(j);
                if(bsunique.equalsIgnoreCase(asunique))matchingStrings++;
            }
        }
        int[] result = new int[3];
        result[0]=aVect.size();
        result[1]=bVect.size();
        result[2]= matchingStrings;
        
        System.out.println("Comparing "+a+"...with...."+b);
        for(int i=0;i<aVect.size();i++){
            String asunique = (String)aVect.elementAt(i);
            System.out.println("1: "+asunique);
        }
        for(int i=0;i<bVect.size();i++){
            String bsunique = (String)bVect.elementAt(i);
            System.out.println("2: "+bsunique);
        }
        System.out.println("1 has "+result[0]+" syntactic rules, and " + "2 has "+result[1]+" ALIGNED ="+result[2]);
        return result;
    }
    
    
    
    public void dotestOLD(){
         Tree parse = lp.apply(Sentence.toWordList("keeping on the edge of the page"));
        parse.pennPrint();
        System.out.println("------------");
        Vector allRules = new Vector();
        getAllRules(parse,allRules);
        for(int i=0;i<allRules.size();i++){
            String s = (String)allRules.elementAt(i);
            System.out.println(s);
        }
    }
    
    
}
