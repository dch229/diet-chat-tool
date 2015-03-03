/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cornell.mchci;

import diet.client.ConnectionToServer;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/**
 *
 * @author Gabriel
 */
public class SurveyBoxJFrame extends javax.swing.JFrame {

    ButtonGroup[] surveyButtonGroups;
    JRadioButton[] surveyButtons;
    String[] words = new String[]{  "Engaged", "Tense", "Friendly", "Talkative", "Offended",
    "Engaged", "Tense", "Friendly", "Talkative", "Offended"};
    
    Dimension size;
    // TODO: find a better way to do this
    ConnectionToServer cts;
    public void setConnectionToServer(ConnectionToServer cts){
        this.cts = cts;
    }
    
    /**
     * Creates new form SurveyBoxJFrame
     */
    public SurveyBoxJFrame() {
        initComponents();
        
        //TODO: setSurveyContent is used to get the size. May want to fix this.
        setSurveyContent();
        setInstructionContent();
    }
    
    public void openSurvey(){
        setSurveyContent();
    }
    
    void setInstructionContent(){
        getContentPane().removeAll();
        
        // TODO: this is bad. not sure why all font is bold in panel
        UIManager.put("Label.font", new FontUIResource("Dialog", Font.PLAIN, 12));
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        String[] instructionContent = new String[]{
            "Please start from <b>Dr. Wang</b>",
            "and discuss the rank of each person on this list.",
            "",
            "",
            "Dr. Wang",
            "39, PhD in History, College Professor, good health, married with one child, Bobby. Active and hold conservative views.",
            "Bobby Wang",
            "10, Special Ed classes for four years, good health, enjoys his pets.",
            "Mrs. Garcia",
            "33, 9th grade education; cocktail waitress, good health, foster child, went to jail at 16, one child, three weeks old (Jean).",
            "Jean Garcia",
            "Three weeks old, good health, nursing for food.",
            "John Cloud",
            "13, good health, 8th grader, honor student, very active. Father is an environmental activist.",
            "Ms. Newton",
            "25, starting last year of Medical school; suspended, good health, criticized for liberal views.",
            "Rita Moy",
            "19, college freshman, unmarried, 4 months pregnant, good health, enjoys music, has taken several engineering courses, grew up in the inner city.",
            "Mr. Blake",
            "51, licensed mechanic, 'Mr. Fix-It', married, four children (not with him), good health, enjoys outdoors and working in his shop.",
            "Dr. Gonzales",
            "66, medical doctor, general practitioner, has had two heart attacks in past five years but continues to practice medicine. "
        };
        
        for(int i = 0; i < instructionContent.length; i++){
            String s = instructionContent[i];
            if(i % 2 == 0 && i != 0){
                s = "<html><b>" + s + "</b></html>";
            } else {
                s = "<html><wide>" + s + "</wide></html>";
            }
            JLabel label = new JLabel(s);
            
            getContentPane().add(label);
        }
        
        getContentPane().revalidate();
        getContentPane().repaint();
    }
    
    void setSurveyContent(){
        getContentPane().removeAll();
        
        UIManager.put("Label.font", new FontUIResource("Dialog", Font.PLAIN, 12));
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        surveyButtonGroups = new ButtonGroup[10];
        surveyButtons = new JRadioButton[10 * 7];
        for(int i = 0; i < 10; i++){
            JPanel p = new JPanel();
            p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
            JLabel wordLabel = new JLabel(words[i]);
            wordLabel.setPreferredSize(new Dimension(100, 50));
            //wordLabel.setAlignmentX(LEFT_ALIGNMENT);
            p.add(wordLabel);
            p.add(Box.createHorizontalGlue());
//p.add(Box.createHorizontalStrut(24));
            
            p.add(new JLabel("Agree"));
            ButtonGroup bg = new ButtonGroup();
            for(int j = 0; j < 7; j++){
                JRadioButton jrb = new JRadioButton(); 
                p.add(jrb);
                bg.add(jrb);
                if(j == 3){
                    bg.setSelected(jrb.getModel(), true);
                }
                surveyButtons[i * 7 + j] = jrb;
            }
            surveyButtonGroups[i] = bg;
            p.add(new JLabel("Disagree"));
            
            if(i == 0){
                JPanel p2 = new JPanel();
                p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
                p2.add(new JLabel("<html>In the preceding 5 minutes of discussion, <b>I</b> felt...</html>"));
                p2.add(Box.createHorizontalGlue());
                getContentPane().add(p2);
            } else if(i == 5){
                JPanel p2 = new JPanel();
                p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
                p2.add(new JLabel("<html>In the preceding 5 minutes of discussion, I beleive <b>my partner</b> was...</html>"));
                p2.add(Box.createHorizontalGlue());
                getContentPane().add(p2);
            }
            getContentPane().add(p);
        }
        
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });
        
        submitButton.setAlignmentX(LEFT_ALIGNMENT);
        getContentPane().add(submitButton);
        size = getContentPane().getSize();
        getContentPane().setPreferredSize(size);
        this.pack();
        
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String results = "[SURVEYRESULT:" + cts.getUsername() + "]{";
        for(int i = 0; i < 7 * 10; i++){
             if(surveyButtons[i].isSelected()){
                 results += Integer.toString(i % 7 + 1) + ",";
             }
        }
        results = results.substring(0, results.length() - 1);
        results += "}";
        if(cts != null){
            cts.sendSurveySubmitted(results);
        } else{
            System.out.println("No cts: " + results);
        }
        
        setInstructionContent();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SurveyBoxJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SurveyBoxJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SurveyBoxJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SurveyBoxJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SurveyBoxJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
