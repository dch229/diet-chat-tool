/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.utils.swing;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JDialog;

/**
 *
 * @author sre
 */
public class Display {
 
    
    
    
    public static void displayTextPopup(String s){
       
         JDialog jd = new JDialog();
         jd.setTitle(s);
         jd.setPreferredSize(new Dimension(200,200));
         jd.setMinimumSize(new Dimension(200,200));
         JButton jb = new JButton(s);
         jb.setPreferredSize(new Dimension(200,200));
         jd.add(jb);
         jd.pack();
         jd.validate();
         jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
         jd.setModal(false);
         jd.setVisible(true);
         
    }
    
}
