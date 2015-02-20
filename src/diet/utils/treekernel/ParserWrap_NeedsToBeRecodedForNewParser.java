/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.utils.treekernel;
import java.io.File;
import java.util.LinkedList;

import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.ling.StringLabel;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.LabeledScoredTreeNode;
import edu.stanford.nlp.trees.Tree;

public class ParserWrap_NeedsToBeRecodedForNewParser{
	
    
    
	LexicalizedParser lp;
	
	public ParserWrap_NeedsToBeRecodedForNewParser()
	{
                System.err.println("THIS CLASS IS BUGGY - HAD TO CHANGE IT BECAUSE OF THE NEW PARSER - see the errors with code 23455. SORRY!!");
                System.exit(-23455);//HAD TO CHANGE (BREAK) THIS BECAUSE OF NEW PARSER
		String grammar =  "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
                String[] options = { "-maxLength", "100", "-retainTmpSubcategories" };
                 lp = LexicalizedParser.loadModel(grammar, options);
                
		lp.setOptionFlags(new String[]{"-maxLength", "100", "-retainTmpSubcategories"});
	}
	
	
	
	public Tree parse(String utt)
	{
		String[] sentences=utt.split("[.!?]");
                System.out.println("there are sentences:"+sentences.length);
		//LinkedList<Tree> list=new LinkedList<Tree>();
		Label rootLabel=new StringLabel("ROOT");
		Tree concat=new LabeledScoredTreeNode(rootLabel,new LinkedList<Tree>());
		
		try{
			for(int i=0;i<sentences.length;i++)
			{
                            boolean parsed=false;
                                if (sentences[i].length()>0){
                                    parsed=true;
                                    System.exit(-23455);//HAD TO CHANGE (BREAK) THIS BECAUSE OF NEW PARSER
                                    //parsed=lp.parse(sentences[i]);
                                }    
                                else continue;
				Tree t=null; 
                                System.exit(-23455);//HAD TO CHANGE (BREAK) THIS BECAUSE OF NEW PARSER
                                //lp.getBestParse();
				Tree rootChild;
				if (t.children().length==1) rootChild=t.removeChild(0);
				else rootChild=t;
				concat.addChild(rootChild);
			}
			if (concat.children().length>1) return concat;
			else return concat.removeChild(0);
		}
		catch(Throwable t)
		{
			System.out.println(t.getMessage());
			System.out.println("Reinitializing parser because of trying to parse error "+utt);
			this.lp=null;
			Runtime r=Runtime.getRuntime();
			r.gc();
                        //HAD TO CHANGE (BREAK) THIS BECAUSE OF NEW PARSER
			String grammar =  "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
                        String[] options = { "-maxLength", "100", "-retainTmpSubcategories" };
                        lp = LexicalizedParser.loadModel(grammar, options);
                
		        lp.setOptionFlags(new String[]{"-maxLength", "100", "-retainTmpSubcategories"});
			return null;
		}
		
		
	}
	
	public static void main(String a[])
	{
		
		ParserWrap_NeedsToBeRecodedForNewParser pw=new ParserWrap_NeedsToBeRecodedForNewParser();
		Tree t=pw.parse("this sentence is false. this isn't bad..... I am a good boy. You can't kill my mother. I won't let you. You think this is a game? I think I'll have to kill you first.");
		t.pennPrint();
		
		
		
	}
}
	