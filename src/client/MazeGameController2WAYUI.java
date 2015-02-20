/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;


import diet.server.ConnectionListener;
import diet.server.Participant;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author user
 */
public class MazeGameController2WAYUI implements ActionListener{
    
    
    
    
    MazeGameController2WAY mgc;
    JTabbedPane jtpDirectorTabbedPane = new JTabbedPane();
    JTabbedPane jtpMatcherTabbedPane = new JTabbedPane();
    //JExperimentInProgressSuperPanel directorMF;// = new JExperimentFrameGameInProgressMaze();
    public JExperimentInProgressSuperPanel directorMF;// matcher1MF = new JMazeFrame();
    public JExperimentInProgressSuperPanel matcherMF; //matcher2MF = new JMazeFrame();
    public JFrame jf;
    JScrollPane js = new JScrollPane();
    JTextPane jtp = new JTextPane();
    JButton jbRESETPOSITION = new JButton("reset position marker to initial position");
    JButton jbUPMAZE = new JButton("  +  ");
    JButton jbDOWNMAZE = new JButton("  -  ");
    
    JProgressBar jp ;
    
    public MazeGameController2WAYUI(MazeGameController2WAY mgc) {
        
        this.mgc=mgc;
        jtpDirectorTabbedPane.setVisible(true);
        String portNumberOfServer = ""+ConnectionListener.staticGetPortNumber();
        jf = new JFrame("Server. Port: "+portNumberOfServer);
         jf.setLayout(new BorderLayout());
        jf.getContentPane().add(jtpDirectorTabbedPane,BorderLayout.WEST);
        jf.getContentPane().add(jtpMatcherTabbedPane,BorderLayout.EAST);
        //jf.getContentPane().add(jb,BorderLayout.SOUTH);
       
        JPanel jp2 = new JPanel();
        jp2.setLayout(new BorderLayout());
        jp2.add(jbRESETPOSITION,BorderLayout.CENTER);
        jp2.add(jbUPMAZE,BorderLayout.EAST);
        jp2.add(jbDOWNMAZE,BorderLayout.WEST);
        
        jf.getContentPane().add(jp2,BorderLayout.SOUTH);
        
        
        
        //jf.get
        
        JPanel jpCentre = new JPanel();
        jpCentre.setPreferredSize(new Dimension(200,200));
        jpCentre.setMinimumSize(new Dimension(200,200));
        
        jtpDirectorTabbedPane.setPreferredSize(new Dimension(200,200));
        jtpMatcherTabbedPane.setPreferredSize(new Dimension(200,200));
        
        //js.add(jtp);
        js.getViewport().setView(jtp);
        js.setPreferredSize(new Dimension(200,200));
        
        jtp.setBackground(Color.BLACK);
        
        
        jf.getContentPane().add(js,BorderLayout.CENTER);
        this.jbDOWNMAZE.addActionListener(this);
        this.jbUPMAZE.addActionListener(this);
        jbRESETPOSITION.addActionListener(this);    
        UIManager.put("ProgressBar.selectionForeground", Color.BLACK);
        UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
          jp = new JProgressBar(JProgressBar.HORIZONTAL,0,100);
            jp.setStringPainted(true);
            jp.setIndeterminate(false);
            
             jf.getContentPane().add(jp,BorderLayout.NORTH);
        
        jf.pack();
        jf.setVisible(true);
        
        
      
        
        
        
        
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(jbRESETPOSITION)){
            mgc.moveToMazeNo(mgc.getMazeNo(), "Restarting maze");
            try{
                mgc.c.saveDataToConversationHistory("ExperimenterControl", "restarted maze number:"+mgc.mazeNumber+" ("+mgc.pDirector.getParticipantID()+", "+mgc.pDirector.getUsername()+")   "
                        + "("+mgc.pMatcher.getParticipantID()+", "+mgc.pMatcher.getUsername()+")");
            }catch(Exception e2){}
        }
        else if(e.getSource().equals(this.jbUPMAZE)){
            mgc.moveToMazeNo(mgc.getMazeNo()+1);
            try{
                mgc.c.saveDataToConversationHistory("ExperimenterControl", "moving up to next maze number:"+mgc.mazeNumber+" ("+mgc.pDirector.getParticipantID()+", "+mgc.pDirector.getUsername()+")   "
                        + "("+mgc.pMatcher.getParticipantID()+", "+mgc.pMatcher.getUsername()+")");
            }catch(Exception e2){}
            
        }
        else if (e.getSource().equals(this.jbDOWNMAZE)){
            mgc.moveToMazeNo(mgc.getMazeNo()-1);
            try{
                mgc.c.saveDataToConversationHistory("ExperimenterControl", "moving down to next maze number:"+mgc.mazeNumber+" ("+mgc.pDirector.getParticipantID()+", "+mgc.pDirector.getUsername()+")   "
                        + "("+mgc.pMatcher.getParticipantID()+", "+mgc.pMatcher.getUsername()+")");
            }catch(Exception e2){}
            
        }
        
    }
    public void updateJProgressBar(final int value, final String text, final Color color){
        SwingUtilities.invokeLater(new Runnable(){
             public void run(){
                  
                  jp.setValue(value);
                  jp.setString(text);
                  jp.setForeground(color);
                  //jProgressBar1.setName(text);       
                  
             }
        });
    }  
    
    
    public void changeTabNamesAndMazes(final String directorTab,final Vector directorMazes, final String matcherTab, final Vector matcherMazes, final int mazeNumber){
        SwingUtilities.invokeLater(new Runnable(){ 
              public void run(){
                   jtpDirectorTabbedPane.setTitleAt(0, directorTab);
                   jtpMatcherTabbedPane.setTitleAt(0, matcherTab);
                   directorMF.setMazes(directorMazes);
                   matcherMF.setMazes(matcherMazes);
                   moveToMazeNo(mazeNumber);
              }
              });
    }
    
    
    public void initializeJTabbedPane(
            final String client1Name,final Vector client1Mazes,
            final String client2Name, final Vector client2Mazes){
        try{
           SwingUtilities.invokeLater(new Runnable(){ 
              public void run(){
                  
                  Vector matcher1MazesClone = mgc.cloneVectorOfMazes(client1Mazes);
                  Vector matcher2MazesClone = mgc.cloneVectorOfMazes(client2Mazes);
                  
                  
                  //directorMF = new JExperimentInProgressSuperPanel(directorName,directorMazesClone);
                  directorMF = new JExperimentInProgressSuperPanel(client1Name,matcher1MazesClone);
                  matcherMF = new JExperimentInProgressSuperPanel(client2Name,matcher2MazesClone);
                  
                 
                  
                  //jtp.insertTab(directorName, null, directorMF, null, 0);
                  jtpDirectorTabbedPane.insertTab(client1Name, null, directorMF, null, 0);
                  jtpMatcherTabbedPane.insertTab(client2Name, null, matcherMF, null, 0);
                  
                  jf.pack();
                  jf.repaint();
              }    
           }); 
        }catch(Exception e){
            System.err.println("COULD NOT INITIALIZE TABBED PANE OF MAZES ON SERVER SIDE");
        }    
    }    
    
    public void moveToMazeNo(final int i){
        try{
            SwingUtilities.invokeLater(new Runnable(){
               public void run(){
                  //directorMF.changeToMazeNo(i);
                  directorMF.changeToMazeNo(i);
                  matcherMF.changeToMazeNo(i);
                  
                  
               } 
            });   
        }catch (Exception e){
            System.err.println("ERROR ON SERVER UPDATING DISPLAY AND MOVING MAZES");
        }
    }   
    
   
    public void movePositionClient(Participant clientName,final Dimension newPos){
        try{
           SwingUtilities.invokeLater(new Runnable(){ 
            public void run(){
                directorMF.updateCursor(newPos);
                jtpDirectorTabbedPane.repaint();
                jtpMatcherTabbedPane.repaint();
             }    
           }); 
        }catch(Exception e){
            System.err.println("ERROR PAINTING MAZES ON SERVER SIDE");
        }    
        
        
    }
    
    
    
    public void movePositionDirector(final Dimension newPos){
        try{
           SwingUtilities.invokeLater(new Runnable(){ 
            public void run(){
                directorMF.updateCursor(newPos);
                jtpDirectorTabbedPane.repaint();
                jtpMatcherTabbedPane.repaint();
             }    
           }); 
        }catch(Exception e){
            System.err.println("ERROR PAINTING MAZES ON SERVER SIDE");
        }    
        
        
    }
    public void movePositionMatcher(final Dimension newPos){
        try{
           SwingUtilities.invokeLater(new Runnable(){ 
            public void run(){
                matcherMF.updateCursor(newPos);
                jtpDirectorTabbedPane.repaint();
                jtpMatcherTabbedPane.repaint();
             }    
           }); 
        }catch(Exception e){
            System.err.println("ERROR PAINTING MAZES ON SERVER SIDE");
        }    
        
    }
    
    public void changeGateStatusDirector(final boolean open){
         try{
           SwingUtilities.invokeLater(new Runnable(){ 
            public void run(){
                directorMF.changeGateStatus(open);
                jtpDirectorTabbedPane.repaint();
                jtpMatcherTabbedPane.repaint();
             }    
           }); 
        }catch(Exception e){
            System.err.println("ERROR PAINTING MAZES ON SERVER SIDE");
        }   
    }
    public void changeGateStatusMatcher(final boolean open){
        try{
           SwingUtilities.invokeLater(new Runnable(){ 
            public void run(){
                matcherMF.changeGateStatus(open);
                jtpDirectorTabbedPane.repaint();
                jtpMatcherTabbedPane.repaint();
             }    
           }); 
        }catch(Exception e){
            System.err.println("ERROR PAINTING MAZES ON SERVER SIDE");
        }   
    }
    
    
 public void append(final String s){
        
     final StyledDocument doc = jtp.getStyledDocument();
     final SimpleAttributeSet keyWord = new SimpleAttributeSet();
     StyleConstants.setForeground(keyWord, Color.WHITE);
     //StyleConstants.setBackground(keyWord, Color.YELLOW);
     //StyleConstants.setBold(keyWord, true);
     try
        {
        
        }
     catch(Exception e) { System.out.println(e); }
     
     try{
           SwingUtilities.invokeLater(new Runnable(){ 
            public void run(){
               try
        { 
                
               doc.insertString(doc.getLength(), s+"\n", keyWord );
             
            }
     catch(Exception e) { System.out.println(e); }
            
            }    
           }); 
        }catch(Exception e){
            System.err.println("ERROR PAINTING MAZES ON SERVER SIDE");
        }   
     
     
     
     
     
     }
    
    
    
    

    
    
    
    
    /**
     * 
     */
    public void closeDown(){
         jtpDirectorTabbedPane.setVisible(false);
         jtpMatcherTabbedPane.setVisible(false);
         jtpDirectorTabbedPane=null;
         jtpMatcherTabbedPane=null;
    }
}
