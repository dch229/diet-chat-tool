/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.utils.postprocessing.cc2013concepttask;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chris
 */
public class RATallTurns {
    
    public static void inputMessageDat() throws FileNotFoundException, IOException, ClassNotFoundException{
        //File filename = new File("C:/Users/Chris/Desktop/ExperimentalData/Saved experimental data/");
        //String directoryForOutput = "C:/Users/Chris/Desktop/ExperimentalData/Saved experimental data/";
        
        File filename = new File("C:/Users/PPAT/Desktop/Saved experimental data/");
        String directoryForOutput = "C:/Users/PPAT/Desktop/Saved experimental data/";
        BufferedReader RATorder = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(filename+"/RATorder.txt"))));
        String strLine, strLine2, easyHard;
        int solution = 0, notSolution = 0, k = -1, prevThing = -1, hasMatched = 0, prePostMatch = 0;
        String[] RATwords = new String[] {"horse","eight","show","river","fur","print","safety","fish","high","cadet","shock","rain","board","tooth","fight","spoon","note","back"};
        String[][] RATsolutions = new String[][] {{"race","racing"},{"figure"},{"boat"},{"bank"},{"coat"},{"blue"},{"pin"},{"gold"},{"school","court"},{"space"},{"after"},{"acid"},{"switch"},{"sweet"},{"gun"},{"table"},{"key"},{"side","door"}};
        String[][] RATmisses = new String[][] {{"ride","riding","body","cart","plough","meat","queen","force","fox","hunt","gun","show","load","carr","jump","plow","huge","slave","escape","walk","wagon","sea","saddle","dead"},{"roller","trick","ice","cube","ball","board","below","shift","hockey","trick","wooden","after","hard","off","magic"},{"time","theatre","thug","top","death","smelly","talk","case","next","marriage","less","man","color","previous","northern","animal","jacket","puppet","dance","image","music","family"},{"book","balance","bed","flow","current","gun","side","bill","pad","mysterious","interest","house","water","deep","run","closed"},{"whip","long","lamb","animal","half","party","hang","dog","pack","cat","shoe","clothes","industry","test","faux","long","fox"},{"ink","red","black","colour","press","copy","news","pink","seed","small","large","screen","humming","paper","sweet","fly","fine","jam","book","spring","nature","writ","cook","breath","net","cage"},{"net","harness","soft","belt","protect","meeting","strap","car","first","wool","bed","match","sugn","orange","comfort","security","elbow","neck","ground","curtain","cover"},{"hour","check","land","sea","sugar","clown","crowd","gathering","school","catch","craft","cook","quick","fry","finger","sword","forward","head","man","set","california","tail","skin","scales","hook","good","time","high","field","boat","under","easy","pie","head","wife"},{"street","lake","area","legal","line","number","drug","safe","field","way","tall","peak","hope","green","fire","road","avenue","set","mate","rent","party","zone","mountain","guest","ness","private","far","sky","stake","rise","hill","horse","big","plan","fence","business","fine","five","rich"},{"flight","navy","air","mini","sea","waste","chief","captain","navy","army","training","water","uniform","bed","deck","pirate","army","ice","sailor","tiny","wreck","round","cover","hard","work","time","naval","sink","boat","war","ocean","wood","sky","compress","life","field","slave","rocket","battle"},{"wave","test","electric","bud","sensation","sample","sudden","close","instant","alley","smooth","clean","less","foam","attack","absorber","state","sharp","shell","point","beard","stubble","sweet","live","machine","food","post","pre","quick"},{"coat","check","bad","heavy","hard","ache","upset","pain","fall","ring","water","food","bug","forest","shower","storm","puddle","boot","drop","exam","hungry","snap","cloud","run","lining","display","fall","bow","tube","belt","guage","gauge","wet","flu"},{"paper","knife","black","white","edge","skate","push","skin","run","chop","chalk","walk","sharp","iron","cut","roller","bone","hard","razor","game","space","notice","wards","rest","stand","half","bee","fence","swing","chess","stab","hand","track","ache","check","bend","house","stroke","card","rubber","steel"},{"sweet","decay","wisdom","rotten","ache","hard","enlarged","broken","baked","mashed","attack","peeler","brush","valve","tubar","chip","hot","black","cavity","bad","fairy","couch","bag","ship","paste","loose","sour","bitter","break","pump","baby","new","red","hot","fibrous","string","skin","soft","shape"},{"robot","fire","response","fast","panel","system","club","stick","arcade","button","thumb","finger","hand","time","street","self","auto","anger","fist","quality","washing","flight","now","man","war","control","metal","play","remote","sub","struggle","computer","school","freak","black","grey","bruise","guard","ground","power","weight","plane","learning"},{"tea","board","silver","small","gold","holder","white","birthday","hard","big","desert","wooden","metal","plastic","clean","red","yellow","soup","home","itchen","grandma","check","soft","use","give","deck","fabric","wash","full","dish","driving","wire","paper"},{"book","paper","link","bank","grand","head","kinky","lock","mind","board","letter","card","self","mail","link","patch","smoke","pad","train","blue","metal","farewell","card","pass","plan","slave","addict","love","causality","psych","school","shop","grand","wish","bank","karate","cuff","gold","quarter","shock","music"},{"view","play","garden","display","shot","log","step","screen","up","call","save","mash","track","out","blue","green","street","snap","monitor","space","stab","chat","wide","ward","stone","silk","silver","wash","drop","off","on","pack","hunt","move","look","front","big","two","knife","protect","arch","colour","touch","bone","fire","table","in","long","slide","flip"}};
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String myDateString = sdf.format(new Date());
        FileWriter currentFile = new FileWriter(directoryForOutput+File.separator+"RAT"+myDateString+".txt");
        currentFile.write("convo|item|itemType|linenum|ParticipantID|Sender|Type|ClientTime|Onset|Enter|Typingti|Speed|AppOrig.|Text|Recipients|Blocked|KDels|DDels|DIns|DDels*N|DIns*N|TaggedText|correctAnswer|incorrectAnswer|prePostMatch\n");
        
        while ((strLine = RATorder.readLine())!=null){
            String[] lineSplit = strLine.split("[\t]");
            int lineNum = 1;
            try{
                //FileWriter currentFile = new FileWriter(directoryForOutput+File.separator+"RATallturns.txt",true);
                for(int i=2; i<lineSplit.length; i++){
//                    System.out.println(directoryForOutput+File.separator+lineSplit[0]+File.separator+lineSplit[i]+".txt");
                    File turnsFile=new File(directoryForOutput+File.separator+lineSplit[0]+File.separator+lineSplit[i]+".txt");
                    BufferedReader RATfile = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(turnsFile))));
                    while ((strLine2 = RATfile.readLine())!=null){
                        String[] lineSplit2 = strLine2.split("[|]");
                        
                        for (int l=0; l < RATwords.length; l++){
                            if(RATwords[l].contains(lineSplit[i].toLowerCase())){
                                k = l;
                            } 
                        }    
                        for (int j=0; j < RATsolutions[k].length; j++){
                             if(lineSplit2[9].toLowerCase().contains(RATsolutions[k][j])){
                                 //needs to not be case sensitive
                                 solution = 1;
                                 hasMatched++;
                                 //System.out.println(RATwords[k]+"..."+lineSplit2[9]+"...hasMatched = "+hasMatched);
                             }
                        }
                        for (int j=0; j < RATmisses[k].length; j++){
                             if(lineSplit2[9].toLowerCase().contains(RATmisses[k][j])){
                                 //
                                 //needs to not be case sensitive
                                 notSolution = 1;
                             }
                        }
                        if(prevThing != k){
                            prePostMatch = 0;
                        }
                        if(hasMatched == 1){
                            prePostMatch = 1;
                            hasMatched ++;
                        }else if(hasMatched > 1){
                            prePostMatch = 2;
                        }else{
                            prePostMatch = 0;
                        }
                        
                        if(k<=1){
                            easyHard = "prac";
                        }else if (k<=9){
                            easyHard = "easy";
                        }else{
                            easyHard = "hard";
                        }
                        //currentFile.write(lineSplit[i]+"|"+lineSplit2[5]+"|"+lineSplit2[1]+"|"+lineSplit2[9]+"\n");
                        currentFile.write(lineSplit[0]+"|"+lineSplit[i]+"|"+easyHard+"|"+lineNum+"|"+strLine2+"|"+solution+"|"+notSolution+"|"+prePostMatch+"\n");
                        lineNum++;
                        solution=0;
                        notSolution = 0;
                        prevThing = k;
                    }
                    hasMatched = 0;
                }
                currentFile.flush();        
            }catch (Exception exc){
                System.err.println("Error: " + exc.getMessage());
            }
        }
    }

    public static void main(String[] args){
        try {
            inputMessageDat();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RATallTurns.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RATallTurns.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RATallTurns.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
