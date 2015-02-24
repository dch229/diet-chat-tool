/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cornell.mchci;

import diet.client.ConnectionToServer;
import diet.server.Conversation;
import diet.server.Participant;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;
import javax.swing.*;
import java.util.*;


/**
 *
 * @author dhau
 */
public class TheSurveyFrame extends JFrame {
    JLabel[] questions;
    JButton submitButton;
    ArrayList allRadioButtons;
    
    JLabel title = new JLabel(" Lifeboat Task Survey ");
    JLabel eq = new JLabel ("=====================================================");


    JLabel question1 = new JLabel("How effective is your conversation partner?");
    ButtonGroup grp_1 = new ButtonGroup();


    JLabel question2 = new JLabel("How many fingers am I holding up?");
    ButtonGroup grp_2 = new ButtonGroup();
    

    JLabel question3 = new JLabel("How angry are you?");
    ButtonGroup grp_3 = new ButtonGroup();
    JRadioButton q3o1 = new JRadioButton("somewhat");
    JRadioButton q3o2 = new JRadioButton("decently angry");
    JRadioButton q3o3 = new JRadioButton("volcanic");

    JLabel question4 = new JLabel("Write some random words below.");
    JTextArea q4Answer = new JTextArea ("",8,30);
    

    
    private ConnectionToServer cts;
    public TheSurveyFrame(ConnectionToServer cts){
        super("Survey Form");
        
        this.cts = cts;
        
        Container c = getContentPane();
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
        c.setLayout(fl);

        setSize(350,730);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);


        c.add(title);
        c.add(eq);

        c.add(question1);
        boolean defaultSet = false;
        for(int i = 1; i <= 5; i++){
            JRadioButton temp = new JRadioButton();
            temp.setText(Integer.toString(i));
            grp_1.add(temp);
            c.add(temp);
            if(!defaultSet) {temp.setSelected(true); defaultSet = true;}
        }


        c.add(question2);
        defaultSet = false;
        for(int i = 1; i <= 6; i++){
            JRadioButton temp = new JRadioButton();
            temp.setText(Integer.toString(i));
            grp_2.add(temp);
            c.add(temp);
            if(!defaultSet) {temp.setSelected(true); defaultSet = true;}
        }


        c.add(question3);
        grp_3.add(q3o1);
        grp_3.add(q3o2);
        grp_3.add(q3o3);
        q3o1.setSelected(true);
        c.add(q3o1);
        c.add(q3o2);
        c.add(q3o3);

        c.add(question4);
        c.add(q4Answer);
        
        submitButton = new JButton();
        submitButton.setText("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                
                getSubmitString();
                setVisible(false);
            }
        }); 
        c.add(submitButton);
        
        setContentPane(c); 

    }
    
    private void getSubmitString() {                                         
        // TODO add your handling code here:
        String logString = "**SURVEY RESPONSE: "+ cts.getUsername() + "** ";
        
        logString += "Q1: " + extractButtonText(grp_1) + " / ";
        logString += "Q2: " + extractButtonText(grp_2) + " / ";
        logString += "Q3: " + extractButtonText(grp_3) + " / ";
        logString += "Q4: " + q4Answer.getText() + " / ";

        
        cts.sendSurveySubmitted(logString);
        setVisible(false);
        setVisible(false);
    }
    

    String extractButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }

        return "";
    }
}

    

