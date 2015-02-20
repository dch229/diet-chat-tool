/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diet.client;

import java.awt.Color;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Vector;

import javax.swing.text.AttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import diet.server.Conversation;
import javax.swing.JComponent;
import javax.swing.JTextPane;

/**
 *
 * @author Greg
 */
public class StyledDocumentStyleSettings implements Serializable{
    
    Color colorBackground = Color.BLACK;
    
    Color colorSelf = Color.WHITE;
    
    
    Color colorOther1 = Color.YELLOW;
    Color colorOther2 = Color.RED;
    Color colorOther3 = Color.GREEN;
    Color colorOther4 = Color.cyan;
    Color colorOther5 = Color.BLUE;
    Color colorOther6 = Color.magenta;
    Color colorOther7 = Color.orange;
    Color colorOther8 = Color.RED; 
    
    
   /*  Color colorOther1 = Color.GRAY;
    Color colorOther2 = Color.GRAY;
    Color colorOther3 = Color.GRAY;
    Color colorOther4 = Color.GRAY;
    Color colorOther5 = Color.GRAY;
    Color colorOther6 = Color.GRAY;
    Color colorOther7 = Color.GRAY;
    Color colorOther8 = Color.GRAY; */
    
    
    
    boolean underlineeditedText = true;
    boolean headerIsBold = true;
    boolean deletesPermitted = false;
    int fontSize =20;
    
    boolean systembeeponfloorclash = true;

    public StyledDocumentStyleSettings() {
    }
    
    public StyledDocumentStyleSettings(Color background,Color selfTextColor,Vector otherColors,boolean deletesPermitted,int fontSize){

        this.fontSize=fontSize;
        //this.fontSize =16;
        this.colorBackground=background;
         colorSelf=selfTextColor;
         this.deletesPermitted=deletesPermitted;
         try{
            colorOther1 = (Color)otherColors.elementAt(0);
            colorOther2 = (Color)otherColors.elementAt(1);
            colorOther3 = (Color)otherColors.elementAt(2);
            colorOther4 = (Color)otherColors.elementAt(3);
            colorOther5 = (Color)otherColors.elementAt(4);
            colorOther6 = (Color)otherColors.elementAt(5);
            colorOther7 = (Color)otherColors.elementAt(6);
            colorOther8 = (Color)otherColors.elementAt(7);
         }catch (Exception e){
            e.printStackTrace();
         }
      }
    public StyledDocumentStyleSettings(Color background,Color selfTextColor,Vector otherColors,boolean deletesPermitted,int fontSize, boolean beepOnFloorClash){
        this.fontSize=fontSize; 
        this.fontSize=16;
        this.colorBackground=background;
         colorSelf=selfTextColor;
         this.deletesPermitted=deletesPermitted;
         this.systembeeponfloorclash=beepOnFloorClash;
         try{
            colorOther1 = (Color)otherColors.elementAt(0);
            colorOther2 = (Color)otherColors.elementAt(1);
            colorOther3 = (Color)otherColors.elementAt(2);
            colorOther4 = (Color)otherColors.elementAt(3);
            colorOther5 = (Color)otherColors.elementAt(4);
            colorOther6 = (Color)otherColors.elementAt(5);
            colorOther7 = (Color)otherColors.elementAt(6);
            colorOther8 = (Color)otherColors.elementAt(7);
         }catch (Exception e){
        	System.err.println("If this is an IndexOutOfBoundException, it is due to having fewer than 7 colours in the experiment parameters. It is harmless though.");
        	
            e.printStackTrace();
         }
      }
    public void setDefaultFont(JTextPane jtp){
        Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setFontFamily(def, "SansSerif");  
        StyledDocument  doc = jtp.getStyledDocument();
        Style regular = doc.addStyle("regular", null);
        StyleConstants.setFontFamily(regular, "SansSerif");       
        StyleConstants.setFontSize(regular, fontSize);
        jtp.setFont(doc.getFont(regular));
        jtp.setForeground(colorSelf);
        jtp.setBackground(this.colorBackground);
    }

   
    public void setStyles(StyledDocument doc, JComponent jc){
        try{
            jc.setBackground(colorBackground);
        }catch(Exception e){
            
        }
        
        Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

       
        
        
        StyleConstants.setFontFamily(def, "SansSerif");       
        //Style regular = doc.addStyle("regular", def);
        Style regular = doc.addStyle("regular", null);
        StyleConstants.setFontSize(regular, fontSize);
        Style header = doc.addStyle("header", regular);
        StyleConstants.setBold(header, headerIsBold);
        Style headerself = doc.addStyle("hs", header);
        Style headerself_number = doc.addStyle("h0", header);
        Style headerother1 = doc.addStyle("h1", header);
        Style headerother2 = doc.addStyle("h2", header);
        Style headerother3 = doc.addStyle("h3", header);
        Style headerother4 = doc.addStyle("h4", header);
        Style headerother5 = doc.addStyle("h5", header);
        Style headerother6 = doc.addStyle("h6", header);
        Style headerother7 = doc.addStyle("h7", header);
        Style headerother8 = doc.addStyle("h8", header);
        
        
        StyleConstants.setForeground(headerself, colorSelf);
        StyleConstants.setForeground(headerother1, colorOther1);
        StyleConstants.setForeground(headerother2, colorOther2);
        StyleConstants.setForeground(headerother3, colorOther3);
        StyleConstants.setForeground(headerother4, colorOther4);
        StyleConstants.setForeground(headerother5, colorOther5);
        StyleConstants.setForeground(headerother6, colorOther6);
        StyleConstants.setForeground(headerother7, colorOther7);
        StyleConstants.setForeground(headerother8, colorOther8);
       
        
        
        Style normaltext = doc.addStyle("normaltext", regular);
        StyleConstants.setBold(normaltext, false);
        Style normaltextself = doc.addStyle("ns", normaltext);
        Style normaltextself_number = doc.addStyle("n0", normaltext);
        Style normaltextother1 = doc.addStyle("n1", normaltext);
        Style normaltextother2 = doc.addStyle("n2", normaltext);
        Style normaltextother3 = doc.addStyle("n3", normaltext);
        Style normaltextother4 = doc.addStyle("n4", normaltext);
        Style normaltextother5 = doc.addStyle("n5", normaltext);
        Style normaltextother6 = doc.addStyle("n6", normaltext);
        Style normaltextother7 = doc.addStyle("n7", normaltext);
        Style normaltextother8 = doc.addStyle("n8", normaltext);
        StyleConstants.setForeground(normaltextself, colorSelf);
        StyleConstants.setForeground(normaltextother1, colorOther1);
        StyleConstants.setForeground(normaltextother2, colorOther2);
        StyleConstants.setForeground(normaltextother3, colorOther3);
        StyleConstants.setForeground(normaltextother4, colorOther4);
        StyleConstants.setForeground(normaltextother5, colorOther5);
        StyleConstants.setForeground(normaltextother6, colorOther6);
        StyleConstants.setForeground(normaltextother7, colorOther7);
        StyleConstants.setForeground(normaltextother8, colorOther8);
        
        
        Style editedtext = doc.addStyle("editedtext", regular);
        StyleConstants.setUnderline(editedtext,underlineeditedText );
        Style editedtextself = doc.addStyle("es", regular);
        Style editedtextself_number = doc.addStyle("e0", regular);
        Style editedother1 = doc.addStyle("e1", editedtext);
        Style editedother2 = doc.addStyle("e2", editedtext);
        Style editedother3 = doc.addStyle("e3", editedtext);
        Style editedother4 = doc.addStyle("e4", editedtext);
        Style editedother5 = doc.addStyle("e5", editedtext);
        Style editedother6 = doc.addStyle("e6", editedtext);
        Style editedother7 = doc.addStyle("e7", editedtext);
        Style editedother8 = doc.addStyle("e8", editedtext);
        StyleConstants.setForeground(editedtextself, colorSelf);
        StyleConstants.setForeground(editedother1, colorOther1);
        StyleConstants.setForeground(editedother2, colorOther2);
        StyleConstants.setForeground(editedother3, colorOther3);
        StyleConstants.setForeground(editedother4, colorOther4);
        StyleConstants.setForeground(editedother5, colorOther5);
        StyleConstants.setForeground(editedother6, colorOther6);
        StyleConstants.setForeground(editedother7, colorOther7);
        StyleConstants.setForeground(editedother8, colorOther8);
        
       
    }
            
    static public String getAttributeSetAsString(AttributeSet a){
        Style s = (Style)a;
        String name =s.getName().substring(0, 2);
        return name;
    }
    
    public boolean getDeletesPermitted(){
        
        return this.deletesPermitted;
    }
    
    public Color getBackgroundColor(){
        return this.colorBackground;
    }
    
    public Color getCaretColor(){
        return this.colorSelf;
    }
    
    static public Color getColor(String colorName) {
        colorName = colorName.toUpperCase();
        try {
            // Find the field and value of colorName
            Field field = Class.forName("java.awt.Color").getField(colorName);
            return (Color)field.get(null);
        } catch (Exception e) {
            Conversation.printWSln("Main", "Bad color name: "+colorName);
            e.printStackTrace();
            return null;
        }
    }

    public boolean performBeepOnFloorClash(){
        return this.systembeeponfloorclash;
    }
}
