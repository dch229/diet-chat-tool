/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.serverclientconsistencycheck;

import diet.server.ConversationController.ui.CustomDialog;

/**
 *
 * @author GM
 */
public class ServerClientConsistency {
    
     ///Please do not change this - this is only ever updated when big changes are made, in particular with the messaging format
     //static String versionID = "002initialtest";
      static String versionID = "002changed format of keypress messages";
     
     public static boolean testForConsistency(String scsString){
         if(scsString.equalsIgnoreCase(versionID)){
             return true;
         }
         
         {
            CustomDialog.showDialog("WARNING!\n The server has detected that the client is NOT using the same version of the software\n"
                    + "This usually isn't a problem for minor updates. But if you are seeing this, this means there has been a large update\n"
                    + "Most probably of the messaging format between client and server! Please update!\n"
                    + "To make sure you don't click and forget...there will be a second error message!!");
         return false;
         
         }
     }
     static public String getVersionID(){
         return versionID;
     }
    
}
