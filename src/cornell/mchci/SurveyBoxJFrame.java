/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cornell.mchci;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Gabriel
 */
public class SurveyBoxJFrame extends javax.swing.JFrame {

    String[] words = new String[]{  "Engaged", "Tense", "Friendly", "Talkative", "Offended",
    "Engaged", "Tense", "Friendly", "Talkative", "Offended"};
    
    Dimension size;
    
    /**
     * Creates new form SurveyBoxJFrame
     */
    public SurveyBoxJFrame() {
        initComponents();
        
        setSurveyContent();
    }
    
    void setInstructionContent(){
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        for(int i = 0; i < 10; i++){
            getContentPane().add(new JLabel("abc"));
        }
    }
    
    void setSurveyContent(){
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        for(int i = 0; i < 10; i++){
            JPanel p = new JPanel();
            p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
            JLabel wordLabel = new JLabel(words[i]);
            wordLabel.setPreferredSize(new Dimension(100, 50));
            //wordLabel.setAlignmentX(LEFT_ALIGNMENT);
            p.add(wordLabel);
            p.add(Box.createHorizontalStrut(24));
            
            p.add(new JLabel("Agree"));
            ButtonGroup bg = new ButtonGroup();
            for(int j = 0; j < 7; j++){
                JRadioButton jrb = new JRadioButton(); 
                p.add(jrb);
                bg.add(jrb);
            }
            p.add(new JLabel("Disagree"));
         
            if(i == 0){
                getContentPane().add(new JLabel("I felt...."));
            } else if(i == 5){
                getContentPane().add(new JLabel("My partner felt..."));
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
        this.pack();
    }

    void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {                                         
        getContentPane().removeAll();
        
        setInstructionContent();
        
        getContentPane().revalidate();
        getContentPane().repaint();
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