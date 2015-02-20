/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cornell.mchci;

import diet.server.Conversation;
import diet.server.Participant;
import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JRadioButton;
import javax.swing.*;
import java.util.*;


/**
 *
 * @author dhau
 */
public class TheSurveyFrame extends JFrame {
    Conversation chat;
    Participant p;
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

    
    
    public TheSurveyFrame(){
        super("Survey Form");
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
        for(int i = 1; i <= 5; i++){
            JRadioButton temp = new JRadioButton();
            temp.setText(Integer.toString(i));
            grp_1.add(temp);
            c.add(temp);
        }


        c.add(question2);
        for(int i = 1; i <= 6; i++){
            JRadioButton temp = new JRadioButton();
            temp.setText(Integer.toString(i));
            grp_1.add(temp);
            c.add(temp);
        }


        c.add(question3);
        grp_3.add(q3o1);
        grp_3.add(q3o2);
        grp_3.add(q3o3);
        c.add(q3o1);
        c.add(q3o2);
        c.add(q3o3);

        c.add(question4);
        c.add(q4Answer);
        setContentPane(c);

    }
}
