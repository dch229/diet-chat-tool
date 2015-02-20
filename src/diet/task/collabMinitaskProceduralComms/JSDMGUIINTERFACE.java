/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.task.collabMinitaskProceduralComms;

import java.util.Vector;

/**
 *
 * @author sre
 */
public interface JSDMGUIINTERFACE {
    public void setStage(int stageNo, String text);    
    public void updateJProgressBarPhysical(final String text,final int value);   
    public void updateJProgressBarAppearance(final String text,final int value);
    public void initializeWITHSTATES(Vector v);
    public void setVisible(boolean v);
    //public void initializeWITHSTATES(Vector transitions);
    //initializeGUIWITHSTATES(transitions);
}