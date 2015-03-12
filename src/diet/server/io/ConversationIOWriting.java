/*
 * ConversationIOWriting.java
 *
 * Created on 17 November 2007, 17:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package diet.server.io;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Vector;

import diet.message.Message;
import diet.message.MessageChatTextFromClient;
import diet.server.Conversation;
import diet.server.Participant;
import diet.server.CbyC.DocChange;
import diet.server.conversationhistory.Conversant;
import diet.server.conversationhistory.LexiconEntry;
import diet.server.conversationhistory.turn.DataToBeSaved;
import diet.server.conversationhistory.turn.Turn;
import diet.server.conversationhistory.turn.WindowLogMessage;
import diet.utils.FilenameToolkit;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author user
 */
public class ConversationIOWriting {
    
    int crashIndex =0;
    
    File conversationDirectory;
    File msgsSerialized;
    File msgsAsText;
    File turnsSerialized;
    File turnsAsText;        
    File docEditsSerialized;
    File docEditsAsText;   
    HashMap<String, PrintWriter> windowLogFiles;
    
    ObjectOutputStream msgsSerializedOut;
    FileWriter msgsAsTextOut;
    ObjectOutputStream turnsSerializedOut;
    FileWriter turnsAsTextOut;
    //ObjectOutputStream docEditsSerializedOut;
    //FileWriter docEditsAsTextOut;
    
    int turnsWritingResetThreshold =30;
    int turnsWritesSinceLastReset =0;
    int msgsWritingResetThreshold =30;
    int msgsWritesSinceLastReset =0; 
    
    //int docEditsWritingResetThreshold =30;
    //int docEditsWritesSinceLastReset =0;  
    
    File errorLog;
    FileOutputStream fos ;
    PrintWriter pwerrorfos;
    Conversation c;
    
    
    File  turnsAsTextENCODED;
    FileOutputStream enc_turns_FOS;
    OutputStreamWriter enc_turns_OSW;
    BufferedWriter enc_turns_BWOut;
    
    
    
   
    
    public ConversationIOWriting(Conversation c,String parentDir,String suffix,Turn t) {
        this.c=c;
        this.windowLogFiles=new HashMap<String, PrintWriter>();
        System.err.println("PARENTDIR "+parentDir+" : SUFFIX : "+suffix);
        File pDirF = new File(parentDir);
        String[] dirlist = pDirF.list();
        String prefixNumber=FilenameToolkit.getNextPrefixInteger(dirlist);            
        conversationDirectory = new File(parentDir+File.separatorChar+prefixNumber+suffix);
        try{
          conversationDirectory.mkdirs();
          
          msgsSerialized= new File(conversationDirectory.getAbsolutePath()+File.separatorChar+"messages.dat");
          msgsSerialized.createNewFile();          
          FileOutputStream msgsOutput = new FileOutputStream(msgsSerialized);
          msgsSerializedOut =  new ObjectOutputStream(msgsOutput);
          
     
          msgsAsText = new File(conversationDirectory.getAbsolutePath()+File.separatorChar+"messages.txt");
          msgsAsTextOut = new FileWriter(msgsAsText);
          
          
          this.turnsSerialized = new File(conversationDirectory.getAbsolutePath()+File.separatorChar+"turns.dat");
          turnsSerialized.createNewFile();          
          FileOutputStream turnsfout = new FileOutputStream(turnsSerialized);
          turnsSerializedOut =  new ObjectOutputStream(turnsfout);
         
          
          turnsAsText = new File(conversationDirectory.getAbsolutePath()+File.separatorChar+"turns_deprecated.txt");
          turnsAsTextOut = new FileWriter(turnsAsText);
          
          turnsAsTextENCODED = new File(conversationDirectory.getAbsolutePath()+File.separatorChar+"turns.txt");
        
          try{
             CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();
             encoder.onMalformedInput(CodingErrorAction.REPORT);
             encoder.onUnmappableCharacter(CodingErrorAction.REPORT);

             enc_turns_BWOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.turnsAsTextENCODED),encoder));
           
             
          }catch (Exception e){
              e.printStackTrace();
          }
          
          
          
          String turnsHeader = "ParticipantID" + "|" + "Sender" + "|" + "Type" +"|" +  "ClientTime"+ "|"+
                               "Onset" + "|" + "Enter" + "|" +  "Typingti" + "|" +
                               "Speed" + "|" +  "AppOrig." + "|" + "Text" + "|" +  "Recipients" + "|" +
                               "Blocked" + "|" + "KDels" + "|" +  "DDels" + "|" + "DIns" + "|" +
                               "DDels*N" + "|" + "DIns*N" + "|" + "TaggedText" + "|"+
                               "PriorTurnByOther_TimestampOnClientOfReceipt" + "|" + "PriorTurnByOther_ApparentUsername" + "|" + "PriorTurnByOther_Text";
                  
            
         turnsHeader = turnsHeader+t.getIOAdditionalHeaders();
               
          
          turnsAsTextOut.write(turnsHeader+"\n");
          turnsAsTextOut.flush();
          
          enc_turns_BWOut.append(turnsHeader+"\n");
          this.enc_turns_BWOut.flush();
          
          //Not implemented yet
          //this.docEditsSerialized = new File(conversationDirectory.getAbsolutePath()+File.separatorChar+"docedits.dat");
          //docEditsSerialized.createNewFile();          
          //FileOutputStream docEditsfout = new FileOutputStream(docEditsSerialized);
          //docEditsSerializedOut =  new ObjectOutputStream(docEditsfout);
          
          //docEditsAsText = new File(conversationDirectory.getAbsolutePath()+File.separatorChar+"docedits.txt");
          //docEditsAsTextOut = new FileWriter(turnsAsText);
          errorLog = new File(conversationDirectory,"errorlog.txt");
          fos = new FileOutputStream(errorLog,true);
          pwerrorfos = new PrintWriter(fos);
          
        }
        catch (Exception e){
            System.out.println("Error creating file "+e.toString());
            System.exit(-1);        
        }
    }
  
    
     public File getConversationDirectory(){
         return this.conversationDirectory;
     }
    
     public void reestablishFileSystem(){
      try{
        System.err.println("ATTEMPTING TO RECOVER FROM POSSIBLE FILESYSTEM ERROR");
        Conversation.printWSln("Main", "ATTEMPTING TO RECOVER FROM POSSIBLE FILESYSTEM ERROR");
        Thread.sleep(500); this.crashIndex++;
          
        if(!this.msgsAsText.canWrite()){
            msgsAsText = new File(conversationDirectory.getAbsolutePath()+File.separatorChar+"messages"+crashIndex+".txt");
            msgsAsTextOut = new FileWriter(msgsAsText);
            System.err.println("FILESYSTEMRECOVERY1");
            Conversation.printWSln("Main", "FILESYSTEMRECOVERY1");
      
        }
        if(!this.msgsSerialized.canWrite()){
             msgsSerialized= new File(conversationDirectory.getAbsolutePath()+File.separatorChar+"messages"+crashIndex+".dat");
             msgsSerialized.createNewFile();          
             FileOutputStream msgsOutput = new FileOutputStream(msgsSerialized);
             msgsSerializedOut =  new ObjectOutputStream(msgsOutput);
             System.err.println("FILESYSTEMRECOVERY2");
             Conversation.printWSln("Main", "FILESYSTEMRECOVERY2");
        }
        if(!this.turnsAsText.canWrite()){
            turnsAsText = new File(conversationDirectory.getAbsolutePath()+File.separatorChar+"turns"+crashIndex+".txt");
            turnsAsTextOut = new FileWriter(turnsAsText);
             System.err.println("FILESYSTEMRECOVERY3");
             Conversation.printWSln("Main", "FILESYSTEMRECOVERY3");
        }
        if(!this.turnsSerialized.canWrite()){
            this.turnsSerialized = new File(conversationDirectory.getAbsolutePath()+File.separatorChar+"turns"+crashIndex+".dat");
            turnsSerialized.createNewFile();          
            FileOutputStream turnsfout = new FileOutputStream(turnsSerialized);
            turnsSerializedOut =  new ObjectOutputStream(turnsfout);
             System.err.println("FILESYSTEMRECOVERY4");
             Conversation.printWSln("Main", "FILESYSTEMRECOVERY4");
        }
        if(!this.errorLog.canWrite()){
            errorLog = new File(conversationDirectory,"errorlog"+crashIndex+".txt");
            fos = new FileOutputStream(errorLog,true);
            pwerrorfos = new PrintWriter(fos);
        }
        if(!this.turnsAsTextENCODED.canWrite()){
             enc_turns_BWOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.turnsAsTextENCODED),Charset.forName("UTF-8").newEncoder()));
           
        }
        
      }catch(Exception e){
          e.printStackTrace();
          System.err.println("ERROR SAVING...SOME PROBLEM SAVING THE DATA FILES...THERE IS A REALLY REALLY CRITICAL PROBLEM WITH THE FILE SYSTEM");
          Conversation.printWSln("Main", "SOME REALLY REALLY SERIOUS PROBLEM SAVING THE DATA FILES...THERE IS A REALLY REALLY CRITICAL PROBLEM WITH THE FILE SYSTEM");
      }
    }
    
    
   
    public File getFileNameContainingConversationData(){
        return conversationDirectory;
    }
    
    
    public void saveMessage(Message m){        
        try{
            msgsSerializedOut.writeObject(m);
            msgsSerializedOut.flush();
           
            msgsWritesSinceLastReset++;
            
            String line = ""
;           if(m.getEmail()!=null){
                line = line + m.getEmail() + "|"; 
            }
             else{
                line = line + "| |";
            }
            if(m.getUsername()!=null){
                line = line + m.getUsername() + "|";
            }
             else{
                line = line + "| |";
            }
            if(m.getMessageClass()!=null){
                line = line + m.getMessageClass() +"|";
            }
             else{
                line = line + "| |";
            }
            line = line + "S"+m.getTimeOnClientOfCreation()+ "T" +"|";
            
            if(m.getTimeOnServerOfReceipt()!=null){
                line = line + m.getTimeOnServerOfReceipt().getTime()+"|";
            }
             else{
                line = line + "| |";
            }
            if(m instanceof MessageChatTextFromClient){
                MessageChatTextFromClient m2 = (MessageChatTextFromClient)m;
                line = line + m2.getServerTypingOnset() + "|"+ m2.getServerTypingENTER()+ "|";
                line = line+ m2.getText()+"|";
            }
            else{
                line = line + "| |";
            }
            this.msgsAsTextOut.write(line+"\n");
            this.msgsAsTextOut.flush();
            msgsWritesSinceLastReset++;
            if(msgsWritesSinceLastReset>this.msgsWritingResetThreshold){  
                 this.msgsSerializedOut.flush();
                 this.msgsSerializedOut.reset();
                 msgsWritesSinceLastReset =0;
            }         
            
        }catch (Exception e){
            try{
               reestablishFileSystem();
               System.err.println("ERROR SAVING MESSAGE: "+m.getTimeOnServerOfReceipt()+m.getUsername()+m.getMessageClass()+"  ");
               Conversation.printWSln("Main", "error saving the Message..probably a file system error");
               e.printStackTrace();
               
            }catch (Exception e2){
                System.err.println("ERROR SAVING MESSAGE");
                e2.printStackTrace();
            }
            
            
            
        }
    }
    
    
   
    
    
    public void saveDocEdit(DocChange dc){
        
    }
    public void saveWindowTextToLog(WindowLogMessage wlm)
    {
    	try{
    		
    	
    		if (this.windowLogFiles.containsKey(wlm.windowName))
    		{
    			PrintWriter writer=this.windowLogFiles.get(wlm.windowName);
    			writer.println(wlm.message);
    			writer.flush();
    			
    		}
    		else    			
    		{
    			PrintWriter writer=new PrintWriter(new FileOutputStream(new File(this.conversationDirectory,wlm.windowName+".log")));
    			this.windowLogFiles.put(wlm.windowName, writer);
    			writer.println(wlm.message);
    			writer.flush();
    		}
    	}
    	catch(Exception e)
    	{
    		System.out.println("Error saving window log text");
    		System.out.println("for now won't attempt to recover: TODO for later");
    		e.printStackTrace();
    	}
    }
    
//    public void saveTurn(Turn t){
//
//      String sendersUsername = "Uninitialized";
//      String participantID = "Unititialized";
//      String apparentSenderUsername = "Uninitialized";
//
//
//     if(t instanceof DataToBeSaved){
//         DataToBeSaved dtbs = (DataToBeSaved)t;
//         sendersUsername = dtbs.getSenderName();
//         apparentSenderUsername = dtbs.getApparentSenderName();
//         participantID = dtbs.getSenderID();
//     }
//     else{
//        sendersUsername = t.getSender().getUsername();
//        Participant sender = c.getParticipants().findParticipantWithUsername(sendersUsername);
//        participantID = "Could not find "+sendersUsername;
//        apparentSenderUsername = t.getApparentSender().getUsername();
//        if(sendersUsername.equalsIgnoreCase("server")){
//            participantID="server";
//        }
//        if(sender!=null){
//            participantID=sender.getParticipantID();
//        }
//     }
//    
//        String line = "";
//        line =  participantID +
//               "|" + sendersUsername +
//                "|" + t.getType()+
//               "|" + t.getCreationTimeOnClient()+
//               "|" + t.getTypingOnsetNormalized()+
//               "|" + t.getTypingReturnPressedNormalized()+
//               "|" + (t.getTypingReturnPressedNormalized()-t.getTypingOnsetNormalized());       
//
//        long typingtime = t.getTypingReturnPressedNormalized()-t.getTypingOnsetNormalized();
//        if(typingtime<=0){
//            line = line + "|"+0;
//        }
//        else{
//            line = line+ "|"+ (((long)t.getTextString().length())*1000/typingtime);
//        }    
//        line = line+"|" +apparentSenderUsername +
//               "|"+ t.getTextString().replace("\n", "~");
//    
//    
//        Vector v = t.getRecipients();
//        String names ="";
//        for(int i=0;i<v.size();i++){
//            Conversant c = (Conversant)v.elementAt(i);
//            names = names+", "+c.getUsername();
//        }
//        
//        line = line+ "|"+names;
//        
//    
//    if (t.getTypingWasBlockedDuringTyping()){
//        line = line + "|" + "BLOCKED";
//    }    
//    else{
//        line = line + "|"+"OK";
//     }
//    
//    line = line + "|" + t.getKeypressDeletes() +
//           "|" + t.getDocDeletes() +
//           "|" + t.getDocInsertsBeforeTerminal() +
//           "|" + t.getDocDelsScore()+
//           "|" + t.getDocInsScore();
//    String returnText="";
//    Vector v2 = t.getWordsAsLexicalEntries();
//    for(int i=0;i<v2.size();i++){
//            LexiconEntry lxe= (LexiconEntry)v2.elementAt(i);
//            returnText = returnText+lxe.getWord()+" ("+lxe.getPartOfSpeech()+") ";
//    }
//    line = line +"|"+returnText;
//    //----Now got to add the information about the preceding turn
//    String[] priorTurnByOther = t.getPriorTurnByOtherAsString();
//    
//    
//    
//    //priorTurnByOther[0]="(((((((((("+priorTurnByOther[0]+"))))))))))";
//    //priorTurnByOther[1]="(((((((((("+priorTurnByOther[1]+"))))))))))";
//    //priorTurnByOther[2]="(((((((((("+priorTurnByOther[2]+"))))))))))";
//    
//    
//        try{
//            
//           line = line +  "|"+priorTurnByOther[0] + "|"+priorTurnByOther[1] +"|"+priorTurnByOther[2];
//           
//        }catch (Exception e){
//            line = line + "|ERROR" + "|ERROR" +"|ERROR";
//            e.printStackTrace();
//            //System.exit(-4);
//        }
//    
//    
//    //Now got to add the information about the preceding turn.
//    line = line+t.getIOAdditionalValues();
//   
//    //line = "|"+line+t.getIOAdditionalValues()+"TEXT"+"|" +" ";
//    
//    //line = "| "+"| "+"| "+"| "+"| "+"|A "+"|  |";
//        
//       
//    try{
//      this.turnsAsTextOut.write(line+"\n");
//      this.turnsAsTextOut.flush();
//      this.turnsSerializedOut.writeObject(t);
//      this.turnsSerializedOut.flush();
//      
//     
//      this.enc_turns_BWOut.append(line);
//      this.enc_turns_BWOut.newLine();
//      this.enc_turns_BWOut.flush();
//      
//      
//     turnsWritesSinceLastReset++;
//      if(turnsWritesSinceLastReset>this.turnsWritingResetThreshold){  
//         this.turnsSerializedOut.reset();
//         turnsWritesSinceLastReset =0;
//         
//      }
//      
//    }catch(Exception e){
//        reestablishFileSystem();
//        System.err.println("Error saving turn" +e.getMessage());
//    }
//    
//    }
//    
//    
//    public void closeAllFiles(){
//        try{
//          msgsSerializedOut.flush();
//        }catch (Exception e){}
//        try{
//          msgsAsTextOut.flush();
//        }  catch (Exception e){}
//        try{
//          turnsSerializedOut.flush();
//        }catch (Exception e){}
//        try{
//          turnsAsTextOut.flush();
//        }catch (Exception e){}
//        try{ 
//          msgsSerializedOut.close();
//        }  catch (Exception e){}
//        try{
//         msgsAsTextOut.close();
//        } catch (Exception e){}
//        try{ 
//         turnsSerializedOut.close();
//        } catch (Exception e){} 
//        try{ 
//         turnsAsTextOut.close();
//        } catch (Exception e){}
//        try{
//            pwerrorfos.close();
//        } catch (Exception e){}
//        try{
//            this.enc_turns_BWOut.close();
//        }catch (Exception e){
//            
//        }
//        try{
//        	for(PrintWriter writer: windowLogFiles.values())
//        	{
//        		writer.close();
//        	}
//        }catch (Exception e){e.printStackTrace();}
//    }
    
    public void saveTurn(Turn t){

      String sendersUsername = "Uninitialized";
      String participantID = "Unititialized";
      String apparentSenderUsername = "Uninitialized";


     if(t instanceof DataToBeSaved){
         DataToBeSaved dtbs = (DataToBeSaved)t;
         sendersUsername = dtbs.getSenderName();
         apparentSenderUsername = dtbs.getApparentSenderName();
         participantID = dtbs.getSenderID();
     }
     else{
        sendersUsername = t.getSender().getUsername();
        Participant sender = c.getParticipants().findParticipantWithUsername(sendersUsername);
        participantID = "Could not find "+sendersUsername;
        apparentSenderUsername = t.getApparentSender().getUsername();
        if(sendersUsername.equalsIgnoreCase("server")){
            participantID="server";
        }
        if(sender!=null){
            participantID=sender.getParticipantID();
        }
     }
    
        String line = "";
        line =  sendersUsername;
                      
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        
        line = line + "|" + dateFormat.format(date);
        
        long typingtime = t.getTypingReturnPressedNormalized()-t.getTypingOnsetNormalized();
        if(typingtime<=0){
            line = line + "|"+0;
        }
        else{
            line = line+ "|"+ (((long)t.getTextString().length())*1000/typingtime);
        }    
        line = line+"|" +apparentSenderUsername +
               "|"+ t.getTextString().replace("\n", "~");
    
        
    
    if (t.getTypingWasBlockedDuringTyping()){
        line = line + "|" + "BLOCKED";
    }    
    else{
        line = line + "|"+"OK";
     }
    
    
    try{
      this.turnsAsTextOut.write(line+"\n");
      this.turnsAsTextOut.flush();
      this.turnsSerializedOut.writeObject(t);
      this.turnsSerializedOut.flush();
      
     
      this.enc_turns_BWOut.append(line);
      this.enc_turns_BWOut.newLine();
      this.enc_turns_BWOut.flush();
      
      
     turnsWritesSinceLastReset++;
      if(turnsWritesSinceLastReset>this.turnsWritingResetThreshold){  
         this.turnsSerializedOut.reset();
         turnsWritesSinceLastReset =0;
         
      }
      
    }catch(Exception e){
        reestablishFileSystem();
        System.err.println("Error saving turn" +e.getMessage());
    }
    
    }
    
    
    public void closeAllFiles(){
        try{
          msgsSerializedOut.flush();
        }catch (Exception e){}
        try{
          msgsAsTextOut.flush();
        }  catch (Exception e){}
        try{
          turnsSerializedOut.flush();
        }catch (Exception e){}
        try{
          turnsAsTextOut.flush();
        }catch (Exception e){}
        try{ 
          msgsSerializedOut.close();
        }  catch (Exception e){}
        try{
         msgsAsTextOut.close();
        } catch (Exception e){}
        try{ 
         turnsSerializedOut.close();
        } catch (Exception e){} 
        try{ 
         turnsAsTextOut.close();
        } catch (Exception e){}
        try{
            pwerrorfos.close();
        } catch (Exception e){}
        try{
            this.enc_turns_BWOut.close();
        }catch (Exception e){
            
        }
        try{
        	for(PrintWriter writer: windowLogFiles.values())
        	{
        		writer.close();
        	}
        }catch (Exception e){e.printStackTrace();}
    }
        
    public void finalize(){
         closeAllFiles();
         
    }    
    
    public void saveErrorLog(Throwable t){
        try{
           t.printStackTrace(pwerrorfos);
           pwerrorfos.flush();
           
        }catch (Exception e){
            reestablishFileSystem();
            e.printStackTrace();
        }  
        //file f = new File()
    }
    
     public void saveErrorLog(String s){
        try{
           
           pwerrorfos.print(s);
           pwerrorfos.flush();
           
        }catch (Exception e){
            reestablishFileSystem();
            e.printStackTrace();
        }  
        //file f = new File()
    }



     public void saveData(Turn t){

        String sendersUsername = t.getSender().getUsername();

        Participant sender = c.getParticipants().findParticipantWithUsername(sendersUsername);
        String participantID = "Could not find "+sendersUsername;
        if(sendersUsername.equalsIgnoreCase("server")){
            participantID="server";
        }
        if(sender!=null){
            participantID=sender.getParticipantID();
        }


        String line = "";
        line =  participantID +
               "|" + t.getSender().getUsername() +
                "|" + t.getType()+
               "|" + t.getTypingOnsetNormalized()+
               "|" + t.getTypingReturnPressedNormalized()+
               "|" + (t.getTypingReturnPressedNormalized()-t.getTypingOnsetNormalized());

        long typingtime = t.getTypingReturnPressedNormalized()-t.getTypingOnsetNormalized();
        if(typingtime<=0){
            line = line + "|"+0;
        }
        else{
            line = line+ "|"+ (((long)t.getTextString().length())*1000/typingtime);
        }
        line = line+"|" + t.getApparentSender().getUsername() +
               "|"+ t.getTextString();


        Vector v = t.getRecipients();
        String names ="";
        for(int i=0;i<v.size();i++){
            Conversant c = (Conversant)v.elementAt(i);
            names = names+", "+c.getUsername();
        }

        line = line+ "|"+names;


    if (t.getTypingWasBlockedDuringTyping()){
        line = line + "|" + "BLOCKED";
    }
    else{
        line = line + "|"+"OK";
     }

    line = line + "|" + t.getKeypressDeletes() +
           "|" + t.getDocDeletes() +
           "|" + t.getDocInsertsBeforeTerminal() +
           "|" + t.getDocDelsScore()+
           "|" + t.getDocInsScore();
    String returnText="";
    Vector v2 = t.getWordsAsLexicalEntries();
    for(int i=0;i<v2.size();i++){
            LexiconEntry lxe= (LexiconEntry)v2.elementAt(i);
            returnText = returnText+lxe.getWord()+" ("+lxe.getPartOfSpeech()+") ";
    }
    line = line +"|"+returnText;
    //line = line +"|"+"|"+"|";
    
     String[] priorTurnByOther = t.getPriorTurnByOtherAsString();
    
    
    
    //priorTurnByOther[0]="(((((((((("+priorTurnByOther[0]+"))))))))))";
    //priorTurnByOther[1]="(((((((((("+priorTurnByOther[1]+"))))))))))";
    //priorTurnByOther[2]="(((((((((("+priorTurnByOther[2]+"))))))))))";
    
    
        try{
            
           line = line +  "|"+priorTurnByOther[0] + "|"+priorTurnByOther[1] +"|"+priorTurnByOther[2];
           
        }catch (Exception e){
            line = line + "|ERROR" + "|ERROR" +"|ERROR";
            e.printStackTrace();
            //System.exit(-4);
        }
    
    line = line+t.getIOAdditionalValues();

    try{
      this.turnsAsTextOut.write(line+"\n");
      this.turnsAsTextOut.flush();
      this.turnsSerializedOut.writeObject(t);
      this.turnsSerializedOut.flush();

     turnsWritesSinceLastReset++;
      if(turnsWritesSinceLastReset>this.turnsWritingResetThreshold){
         this.turnsSerializedOut.reset();
         turnsWritesSinceLastReset =0;
      }

    }catch(Exception e){
        System.err.println("Error saving turn" +e.getMessage());
        e.printStackTrace();
        reestablishFileSystem();
    }

    }




}
