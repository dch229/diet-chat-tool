/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.message;

import java.awt.Color;
import java.util.Vector;

import diet.parameters.ExperimentSettings;
import diet.parameters.StringParameterFixed;
import diet.server.Conversation;
import diet.server.ExperimentManager;
import diet.client.StyledDocumentStyleSettings;

/**
 *
 * @author Greg
 */
public class MessageClientInterfaceSetupParameterFactory {
    /**
     * Returns a Message of type MessageClientSetupParameters which is constructed from the 
     * {@link diet.parameters.ExperimentSettings} supplied from {@link ExperimentManager} in
     * the constructor. 
     * 
     * @param ownWindowNumber The window number in which participant sees their own text. This is retrieved from {@link PermissionsManager}
     * @return Message to be sent to the clients containing the chat tool window settings
     */
    static public MessageClientSetupParameters generateClientChatToolSetupMessage(int ownWindowNumber, ExperimentSettings expSettings){
             String chatwindowtype = (String)expSettings.getV("Chat tool interface");
             int chatWindowNumberOfRows = (Integer)expSettings.getValueOtherwiseReturnDefault("Number of rows in chat window",10);
                                                   
             int chatWindowNumberOfColumns = (Integer)expSettings.getValueOtherwiseReturnDefault("Number of columns in chat window", 80);
             int numberOfWindows = (Integer) expSettings.getValueOtherwiseReturnDefault("Number of windows per chat tool",1);
             int textEntryAreaNumberOfRows = (Integer)expSettings.getValueOtherwiseReturnDefault("Number of rows in chat text entry area",4);
             String chatToolAlignment= (String)expSettings.getValueOtherwiseReturnDefault("Horizontal or vertical alignment of multiple windows","VERTICAL");
             boolean alignmentIsVertical = true;
             if(chatToolAlignment.equalsIgnoreCase("HORIZONTAL"))alignmentIsVertical=false;
             
             //DEPRECATED DEPRECATED DEPRECATED DEPRECATED DEPRECATED
             if(chatwindowtype.equalsIgnoreCase("-Formulate revise then send. Single or multiple windows")){
                 return new MessageClientSetupParametersWithSendButtonAndTextEntry("server","servername2",
                                                chatWindowNumberOfRows,chatWindowNumberOfColumns,
                                                alignmentIsVertical,
                                                numberOfWindows,
                                                ownWindowNumber,    //This needs to be loaded from the Permissions File //Get x,y
                                                false,
                                                true,
                                                "Setting up",
                                                true,
                                                textEntryAreaNumberOfRows);
             }

              else if (chatwindowtype.equalsIgnoreCase("CBYC")){
               boolean deletesPermitted =true;
               Color background = Color.white;
               Vector othersColors = new Vector();
               Color selfColor = Color.black;
               StyledDocumentStyleSettings cBYCdocsett;
               try{ 
                   StringParameterFixed sf = (StringParameterFixed)expSettings.getParameter("Text colour self");
                   StringParameterFixed o1 = (StringParameterFixed)expSettings.getParameter("Text colour other 1");
                   StringParameterFixed o2 = (StringParameterFixed)expSettings.getParameter("Text colour other 2");
                   StringParameterFixed o3 = (StringParameterFixed)expSettings.getParameter("Text colour other 3");
                   StringParameterFixed o4 = (StringParameterFixed)expSettings.getParameter("Text colour other 4");
                   StringParameterFixed o5 = (StringParameterFixed)expSettings.getParameter("Text colour other 5");
                 
                   selfColor = StyledDocumentStyleSettings.getColor(sf.getValue());
                   Color oth1 = StyledDocumentStyleSettings.getColor(o1.getValue());
                   Color oth2 = StyledDocumentStyleSettings.getColor(o2.getValue());
                   Color oth3 = StyledDocumentStyleSettings.getColor(o3.getValue());
                   Color oth4 = StyledDocumentStyleSettings.getColor(o4.getValue());
                   Color oth5 = StyledDocumentStyleSettings.getColor(o5.getValue());
                 
                   othersColors.addElement(oth1);
                   othersColors.addElement(oth2);
                   othersColors.addElement(oth3); 
                   othersColors.addElement(oth4);
                   othersColors.addElement(oth5);
                   
                   StringParameterFixed bckg = (StringParameterFixed)expSettings.getParameter("Background colour");
                   background = StyledDocumentStyleSettings.getColor(bckg.getValue());
                   StringParameterFixed delsPermitted = (StringParameterFixed)expSettings.getParameter("Deletes permitted");
                   String dloerm = delsPermitted.getValue();
                   if(dloerm.equalsIgnoreCase("permitted")){
                       deletesPermitted = true;
                   }
                   else{
                       System.out.println("DELETES NOT PERMITTED");
                       deletesPermitted = false;    
                   }
                   StringParameterFixed beepOnClash=(StringParameterFixed)expSettings.getParameter("Beep on floor clash");
                   if (beepOnClash!=null)
                   {
                	   String beepOFC=beepOnClash.getValue();
                	   boolean beep;
                	   if(beepOFC.equalsIgnoreCase("yes"))
                	   {
                		   beep=true;
                	   } else beep=false;
                   
                	   cBYCdocsett = new StyledDocumentStyleSettings(background, selfColor,othersColors,deletesPermitted,14, beep);
                   }
                   else cBYCdocsett = new StyledDocumentStyleSettings(background, selfColor,othersColors,deletesPermitted,14);
            
                }catch (Exception e){
                    Conversation.printWSln("Main", "Could not find parameters for CBYC interface...attempting to use defaults" );
                    cBYCdocsett = new StyledDocumentStyleSettings();
                    //e.printStackTrace();
         
                } 
                
                
                 return new MessageClientSetupParametersCBYCOneWindowKeypressIntercepted("server","servername2",
                                                chatWindowNumberOfRows,chatWindowNumberOfColumns,
                                                true,false,true,"Setting up",true,
                                                cBYCdocsett);
             }
             else if(chatwindowtype.equalsIgnoreCase("TurnAtATime")){
                 //System.exit(-25);
                 return new MessageClientSetupParametersWithSendButtonAndTextEntryOneTurnAtATime("server","servername2",
                                                chatWindowNumberOfRows,chatWindowNumberOfColumns,
                                                alignmentIsVertical,
                                                numberOfWindows,
                                                ownWindowNumber,    //This needs to be loaded from the Permissions File //Get x,y
                                                false,
                                                true,
                                                "Setting up",
                                                true,
                                                textEntryAreaNumberOfRows,
                                                2,//NumberOfTurnsDisplayed
                                                true);//Can it be scrolled back?;
              }
             
              //DEPRECATEDDEPRECATEDDEPRECATEDDEPRECATED
              else if (chatwindowtype.equalsIgnoreCase("WithinTurn")){
                  return new MessageClientSetupParametersWithinTurn("server","servername2",
                                                chatWindowNumberOfRows,chatWindowNumberOfColumns,
                                                true,false,true,"Setting up",true);
              }
              
              
              else if (chatwindowtype.equalsIgnoreCase("Formulate revise then send. Single or multiple windows")){
              
                  
               boolean deletesPermitted =true;
               Color background = Color.black;
               Vector othersColors = new Vector();
               Color selfColor = Color.white;
               StyledDocumentStyleSettings cBYCdocsett;
               
               //int heightmainWindow = 300;
               //int widthMainWindow = 400;
               int widthMainWindow = (Integer)expSettings.getValueOtherwiseReturnDefault("Width of main window",(Integer)450);
               int heightmainWindow = (Integer)expSettings.getValueOtherwiseReturnDefault("Height of main window",(Integer)350);
               int widthtextentry =  (Integer)expSettings.getValueOtherwiseReturnDefault("Width of text entry window",(Integer)300);
               int heighttextentry =  (Integer)expSettings.getValueOtherwiseReturnDefault("Height of text entry window",(Integer)150);
               int maxLength = (Integer)expSettings.getValueOtherwiseReturnDefault("Maximum length of textentry",(Integer)1000);
               
               //int widthtextentry = 220;
               //int heighttextentry = 150;
               //int maxLength = 1000;

               try{
                   StringParameterFixed sf = (StringParameterFixed)expSettings.getParameter("Text colour self");
                   StringParameterFixed o1 = (StringParameterFixed)expSettings.getParameter("Text colour other 1");
                   StringParameterFixed o2 = (StringParameterFixed)expSettings.getParameter("Text colour other 2");
                   StringParameterFixed o3 = (StringParameterFixed)expSettings.getParameter("Text colour other 3");
                   StringParameterFixed o4 = (StringParameterFixed)expSettings.getParameter("Text colour other 4");
                   StringParameterFixed o5 = (StringParameterFixed)expSettings.getParameter("Text colour other 5");
                   StringParameterFixed o6 = (StringParameterFixed)expSettings.getParameter("Text colour other 6");
                   StringParameterFixed o7 = (StringParameterFixed)expSettings.getParameter("Text colour other 7");
                   StringParameterFixed o8 = (StringParameterFixed)expSettings.getParameter("Text colour other 8");

                  
                   selfColor = StyledDocumentStyleSettings.getColor(sf.getValue());
                  
                   Color oth1 = StyledDocumentStyleSettings.getColor(o1.getValue());
                   
                   Color oth2 = StyledDocumentStyleSettings.getColor(o2.getValue());
                   
                   Color oth3 = StyledDocumentStyleSettings.getColor(o3.getValue());
                   
                   Color oth4 = StyledDocumentStyleSettings.getColor(o4.getValue());
                   Color oth5 = StyledDocumentStyleSettings.getColor(o5.getValue());
                   Color oth6 = StyledDocumentStyleSettings.getColor(o6.getValue());
                   Color oth7 = StyledDocumentStyleSettings.getColor(o7.getValue());
                   Color oth8 = StyledDocumentStyleSettings.getColor(o8.getValue());
  
                   
                   othersColors.addElement(oth1);
                   othersColors.addElement(oth2);
                   othersColors.addElement(oth3);
                   othersColors.addElement(oth4);
                   othersColors.addElement(oth5);
                   othersColors.addElement(oth6);
                   othersColors.addElement(oth7);
                   othersColors.addElement(oth8);

                   StringParameterFixed bckg = (StringParameterFixed)expSettings.getParameter("Background colour");
                   
                   background = StyledDocumentStyleSettings.getColor(bckg.getValue());
                   
                   deletesPermitted = false;
                   cBYCdocsett = new StyledDocumentStyleSettings(background, selfColor,othersColors,deletesPermitted,14);
 
                }catch (Exception e){
                   
                    //System.exit(-5);
                    Conversation.printWSln("Main", "Could not find parameters for chat tool client interface...attempting to use defaults" );
                    cBYCdocsett = new StyledDocumentStyleSettings();
                    
                   // e.printStackTrace();

                }
               
                return new MessageClientSetupParametersWithSendButtonAndTextEntryWidthByHeight("server","servername2",
                                                widthMainWindow, heightmainWindow,
                                                alignmentIsVertical,
                                                numberOfWindows,
                                                ownWindowNumber,    //This needs to be loaded from the Permissions File //Get x,y
                                                false,
                                                true,
                                                "Setting up",
                                                true,
                                                widthtextentry, heighttextentry,
                                                maxLength,
                                                cBYCdocsett);

              }
              else {
                 return null;
              }
        
                  //(String servername, String servername2,int windowType, boolean alignmentIsVertical,int numberOfWindows, int windowOfOwnText,boolean windowIsBlocked, boolean participantHasStatusWindow, String statusDisplay, boolean statusIsErrorMessage){
              }

}
