package diet.client;

import cornell.mchci.SurveyBoxJFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import javax.swing.text.AttributeSet;

/**
 * Chat window interface with separate text entry area. Text is only sent when
 * participant presses "ENTER"/"RETURN"/"SEND". Can be configured with single
 * window (similar to existing commercial chat tools) or with multiple windows.
 *
 * @author user
 */
public class JChatFrameMultipleWindowsWithSendButtonWidthByHeight extends JChatFrame {

    boolean displaySENDButton = false;

    JPanel cards = new JPanel(new CardLayout());
    //JPanel card2 = new JPanel();

    JProgressBar jp;
    JCollectionOfChatWindows jccw;
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    BorderLayout borderLayout3 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JButton jSENDButton = new JButton();
    JPanel jPanel2 = new JPanel();
    JScrollPane jTextEntryScrollPane = new JScrollPane();
    JTextPane jTextEntryPane = new JTextPane();

    JLabel jLabeldisplay = new JLabel("Normal operation");
    int participantsOwnWindowForTextEntry;
    InputDocumentListener jtpDocumentListener;
    
    //TODO: find a better place for this
    SurveyBoxJFrame surveyBox;
    public void setConnectionToServer(ConnectionToServer cts){
        surveyBox.setConnectionToServer(cts);
    }
    
    public SurveyBoxJFrame getSurveyBox(){
        return surveyBox;
    }

    //JButton jb = new JButton();
    @Override
    public Container getContentPane() {
        return super.getContentPane();
    }
    
    public int maxcharlength = 90;
    JChatFrameMultipleWindowsWithSendButtonWidthByHeightDocument jcfd = new JChatFrameMultipleWindowsWithSendButtonWidthByHeightDocument(this);
     //StyledDocumentStyleSettings wstyles;

    public JChatFrameMultipleWindowsWithSendButtonWidthByHeight(ClientEventHandler clevh, int numberOfWindows, int mainWindowWidth, int mainWindowHeight, boolean isVertical, boolean hasStatusWindow, int windowOfOwnText, int textEntryWidth, int textEntryHeight, int maxCharlength, StyledDocumentStyleSettings styles) {
        super(clevh);

        //setUndecorated(true);
        try {
            JPanel leftJPanel = new JPanel();
            leftJPanel.setLayout(borderLayout1);
            
            //this.getContentPane().setLayout(borderLayout1);
            this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
            
            cards = new JPanel(new CardLayout());
            jccw = new JCollectionOfChatWindows(numberOfWindows, mainWindowWidth, mainWindowHeight, isVertical, hasStatusWindow, windowOfOwnText, styles);
            cards.add(jccw, "jccw");

            JPanel jp = new JPanel();
            cards.add(jp, "grey");

            //this.getContentPane().add(cards, BorderLayout.CENTER);
            leftJPanel.add(cards, BorderLayout.CENTER);
            jSENDButton.setHorizontalTextPosition(SwingConstants.CENTER);
            jPanel1.setLayout(borderLayout2);

            //this.getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);
            leftJPanel.add(jPanel1, java.awt.BorderLayout.SOUTH);
            if (this.displaySENDButton) {
                jPanel1.add(jPanel2, BorderLayout.EAST);
            }

            this.maxcharlength = maxCharlength;
            this.participantsOwnWindowForTextEntry = windowOfOwnText;

            if (this.displaySENDButton) {
                jPanel2.add(jSENDButton, java.awt.BorderLayout.EAST);
            }
            jSENDButton.setPreferredSize(new Dimension(73, 64));
            jSENDButton.setText("SEND");
            jSENDButton.addActionListener(new JChatFrameSENDButtonActionListener());
            jSENDButton.setMargin(new Insets(0, 0, 0, 0));

            styles.setDefaultFont(jTextEntryPane);

            NavigationFilter jtpnf = new NavigationFilter();
            jTextEntryPane.setNavigationFilter(jtpnf);

            jTextEntryPane.setFocusable(true);
            jTextEntryPane.setEditable(false);
            jTextEntryPane.setFocusable(false);
            //COLORS HAVE BEEN FIXED
            jTextEntryPane.setBackground(Color.WHITE);//styles.getBackgroundColor());
            jTextEntryPane.getCaret().setVisible(true);
            jTextEntryPane.setCaretColor(Color.BLACK);//styles.getCaretColor());
            jTextEntryPane.setForeground(Color.BLACK);

            jTextEntryPane.setEditorKit(new WrapEditorKit());

            jTextEntryScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            jTextEntryScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            jTextEntryScrollPane.getViewport().setPreferredSize(new Dimension(textEntryWidth, textEntryHeight));
            jTextEntryScrollPane.setPreferredSize(new Dimension(textEntryWidth, textEntryHeight));

            jTextEntryPane.setDocument(jcfd);

            jtpDocumentListener = new InputDocumentListener(jTextEntryPane);
            jTextEntryPane.getDocument().addDocumentListener(jtpDocumentListener);

            jTextEntryScrollPane.getViewport().add(jTextEntryPane);
            jTextEntryPane.addKeyListener(new JChatFrameKeyEventListener());

            jPanel1.add(jTextEntryScrollPane, BorderLayout.CENTER);

            this.getContentPane().add(leftJPanel);
            
            // ADD THE SURVEY AND INSTRUCTIONS HERE
            //JPanel rightJPanel = (new SurveyBoxJFrame()).getContentPane();
            //rightJPanel.setPreferredSize(new Dimension(textEntryWidth, textEntryHeight));
            //rightJPanel);
            //TODO: Make SurveyBox a JPanel rather than JFrame
            surveyBox = new SurveyBoxJFrame();
            this.getContentPane().add(surveyBox.getContentPane());
            
            this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            setVisible(true);
            this.setResizable(true);
            try {
                this.validate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //this.revalidate();
            try {
                this.pack();

            } catch (Exception e) {
                e.printStackTrace();
            }

            this.setLocationRelativeTo(null);
            
            //Adjusting formating
            jccw.changeWindowBackgroundColour(Color.WHITE);
            StyledDocumentStyleSettings textStyle = new StyledDocumentStyleSettings();
            textStyle.colorBackground = Color.WHITE;
            textStyle.colorSelf = Color.BLACK;
            textStyle.colorOther1 = Color.BLUE;
            textStyle.colorOther2 = Color.GREEN;
            jccw.changeTextStyles(textStyle);
        } catch (Exception ex) {
            System.err.println("COULD NOT SET UP CHAT INTERFACE");
            ex.printStackTrace();
        }

    }

    public void changeJProgressBar(final String text, final Color colorForeground, int value) {
        final JFrame jf2 = this;

        if (value < 0) {
            value = 0;
        }
        if (value > 100) {
            value = 100;
        }
        final int valCorr = value;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {

                    if (jp == null) {
                        UIManager.put("ProgressBar.selectionForeground", Color.BLACK);
                        UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
                        jp = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
                        jp.setPreferredSize(new Dimension(jPanel2.getWidth(), 25));
                        jp.setIndeterminate(false);

                        jPanel1.add(jp, BorderLayout.SOUTH);
                        jf2.pack();
                    }
                    jp.setForeground(colorForeground);
         //jp.setBackground(Color.BLUE);
                    //jp.setFont(jp.getFont().)

                    jp.setValue(valCorr);
                    jp.setString(text);
                    jp.setStringPainted(true);
                } catch (Exception e) {
                    System.err.println("\n1" + "\n2" + "\n3" + "\n4" + "\n5" + "\n6" + "\n7" + "\n8" + "\n9" + "\n10"
                            + "\n11Error displaying progressBar in CHATFRAME");
                    e.printStackTrace();

                }
            }
        });

    }

    Color oldActiveTextEntryBackground = null;
    Color oldActiveTextEntryForeground = null;

    public void changeInterfaceProperties(final int newInterfaceproperties, final Object value) {
        //System.err.println("ERROR "+newInterfaceproperties +".."+ MessageChangeClientInterfaceProperties.disableRETURNANDSEND_enableTEXTENTRY);
        final JFrame jf = this;

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (newInterfaceproperties == diet.message.MessageChangeClientInterfaceProperties.disableRETURNANDSEND_enableTEXTENTRY) {
                    jccw.setEnablePane(false);

                } else if (newInterfaceproperties == diet.message.MessageChangeClientInterfaceProperties.enableRETURNANDSEND_enableTEXTENTRY) {
                    jccw.setEnablePane(true);

                } else if (newInterfaceproperties == diet.message.MessageChangeClientInterfaceProperties.clearTextEntryField) {
                    jTextEntryPane.setText("");
                    // System.exit(-444);
                } else if (newInterfaceproperties == diet.message.MessageChangeClientInterfaceProperties.clearMainTextWindows) {
                    jccw.clearAllTextWindows();
                } else if (newInterfaceproperties == diet.message.MessageChangeClientInterfaceProperties.clearAllWindowsExceptWindow0) {
                    //jccw.clearAllTextWindows();
                    int numberOfWindows = jccw.getTextPanes().size();
                    for (int i = 1; i < numberOfWindows; i++) {
                        jccw.cleartextWindow(i);
                    }

                } else if (newInterfaceproperties == diet.message.MessageChangeClientInterfaceProperties.disableTextEntry) {
                    if (jTextEntryPane.isEnabled()) {
                        oldActiveTextEntryBackground = jTextEntryPane.getBackground();
                        oldActiveTextEntryForeground = jTextEntryPane.getForeground();
                    }

                    jTextEntryPane.setEnabled(false);
                    jSENDButton.setEnabled(false);
                    jTextEntryPane.setBackground(Color.GRAY);
                } else if (newInterfaceproperties == diet.message.MessageChangeClientInterfaceProperties.enableTextEntry) {

                    if (oldActiveTextEntryBackground != null) {
                        jTextEntryPane.setBackground(oldActiveTextEntryBackground);
                    }
                    if (oldActiveTextEntryForeground != null) {
                        jTextEntryPane.setForeground(oldActiveTextEntryForeground);
                    }

                    jTextEntryPane.setEnabled(true);
                    jSENDButton.setEnabled(true);
                    //jTextEntryPane.setBackground(Color.red);
                    jTextEntryPane.setFocusable(true);
                    jTextEntryPane.requestFocusInWindow();
                } else if (newInterfaceproperties == diet.message.MessageChangeClientInterfaceProperties.enableTextPane) {
                    CardLayout cl = (CardLayout) (cards.getLayout());
                    cl.show(cards, "jccw");
                    //System.exit(-5);
                } else if (newInterfaceproperties == diet.message.MessageChangeClientInterfaceProperties.disableTextPane) {
                    CardLayout cl = (CardLayout) (cards.getLayout());
                    cl.show(cards, "grey");
                     //jccw.setEnablePane(false);
                    //jccw.setEnabled(false);
                    //System.exit(-4);
                } else if (newInterfaceproperties == diet.message.MessageChangeClientInterfaceProperties.enableScrolling) {
                    jccw.setEnableScrollBars(true, jf);
                } else if (newInterfaceproperties == diet.message.MessageChangeClientInterfaceProperties.disableScrolling) {
                    jccw.setEnableScrollBars(false, jf);
                } else if (newInterfaceproperties == diet.message.MessageChangeClientInterfaceProperties.changeScreenBackgroundColour) {
                    jccw.changeWindowBackgroundColour(value);

                } else if (newInterfaceproperties == diet.message.MessageChangeClientInterfaceProperties.changeTextStyles) {
                    jccw.changeTextStyles(value);
                    StyledDocumentStyleSettings sdst = (StyledDocumentStyleSettings) value;
                    sdst.setDefaultFont(jTextEntryPane);
                }
                jf.pack();

            }
        });

    }

    @Override
    public void setTextEntrytext(final String s) {

        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        jTextEntryPane.setText(s);
                    }
                });

    }

    @Override
    public void setLabelAndTextEntryEnabled(int windowOfOwnText, String label, boolean textIsInRed, boolean setWindowEnabled) {
        Vector jTextPanes = jccw.getTextPanes();
        Vector scrollPanes = jccw.getScrollPanes();
        Vector jLabels = jccw.getJLabels();
        if (windowOfOwnText >= jLabels.size()) {
            return;
        }
        JLabel jls = (JLabel) jLabels.elementAt(windowOfOwnText);
        if (windowOfOwnText >= scrollPanes.size()) {
            return;
        }
        JTextPane jtp = jTextEntryPane;
        SwingUtilities.invokeLater(new DoSetLabelAndTextEntryAndSendButtonEnabled(jtp, jSENDButton, jls, label, textIsInRed, setWindowEnabled));
    }

    @Override
    public void setLabel(int windowNumber, String label, boolean textIsInRed) {
        Vector jTextPanes = jccw.getTextPanes();
        Vector scrollPanes = jccw.getScrollPanes();
        Vector jLabels = jccw.getJLabels();
        if (windowNumber >= jLabels.size()) {
            return;
        }
        final JLabel jls = (JLabel) jLabels.elementAt(windowNumber);
        final String labl = label;
        final boolean textIsRed = textIsInRed;
        if (windowNumber >= scrollPanes.size()) {
            return;
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                jls.setText(labl);
                if (textIsRed) {
                    jls.setForeground(Color.RED);
                } else {
                    jls.setForeground(Color.BLACK);
                }

            }
        });
    }

    @Override
    public void appendWithCaretCheck(String text, int windowNumber) {
        Vector jTextPanes = jccw.getTextPanes();
        Vector scrollPanes = jccw.getScrollPanes();
        if (windowNumber >= scrollPanes.size()) {
            return;
        }
        JTextPane jtp = (JTextPane) jTextPanes.elementAt(windowNumber);
        JScrollPane jsp = (JScrollPane) scrollPanes.elementAt(windowNumber);
        SwingUtilities.invokeLater(new DoAppendTextWithCaretCheck(jtp, jsp, text));
    }

    public void appendWithCaretCheck(String text, int windowNumber, Object attributeset) {
        Vector jTextPanes = jccw.getTextPanes();
        Vector scrollPanes = jccw.getScrollPanes();
        if (windowNumber >= scrollPanes.size()) {
            return;
        }
        JTextPane jtp = (JTextPane) jTextPanes.elementAt(windowNumber);
        JScrollPane jsp = (JScrollPane) scrollPanes.elementAt(windowNumber);
        AttributeSet attr = null;

        if (attributeset instanceof String) {
            attr = jtp.getStyle((String) attributeset);
        } else if (attributeset instanceof AttributeSet) {
            attr = (AttributeSet) attributeset;
        }

        SwingUtilities.invokeLater(new DoAppendTextWithCaretCheck(jtp, jsp, text, attr));
    }

    @Override
    public void setTextEntryField(String s) {
        SwingUtilities.invokeLater(new DoSetTextEntryField(jTextEntryPane, s));
    }

    @Override
    public int getParticipantsOwnWindow() {
        return this.participantsOwnWindowForTextEntry;
    }

    @Override
    public String getTextEnteredInField() {
        return jTextEntryPane.getText();
    }

    public void sendButtonPressed(ActionEvent e) {
        getClientEventHandler().sendButtonPressed();
    }

    void keyPressed(KeyEvent e) {
        getClientEventHandler().keyPressFilter(e);
    }

    void keyReleased(KeyEvent e) {
        this.getClientEventHandler().keyReleaseFilter(e);
    }

    class JCollectionOfChatWindows extends JPanel {

        private Vector scrollPanes = new Vector();
        private Vector jLabels = new Vector();
        private Vector jTextPanes = new Vector();
        BoxLayout blyout;
        String labelSpacePadding = "                                         ";
        boolean hasStatusWindow;
        int windowOfParticipant = 0;
        Color enabledColor;

        public JCollectionOfChatWindows(int numberOfWindows, int mainWindowWidth, int mainWindowHeight, boolean isVertical, boolean hasStatusWindow, int windowOfParticipant, StyledDocumentStyleSettings styles) {
            super();
            if (isVertical) {
                blyout = new BoxLayout(this, BoxLayout.Y_AXIS);
            } else {
                blyout = new BoxLayout(this, BoxLayout.X_AXIS);
                labelSpacePadding = "";
            }
            this.hasStatusWindow = hasStatusWindow;
            this.setLayout(blyout);
            this.windowOfParticipant = windowOfParticipant;

            for (int i = 0; i < numberOfWindows; i++) {
                JTextPane jTextPane = new JTextPane();
                jTextPane.setEditable(false);
                jTextPane.setFocusable(false);
                jTextPane.setBackground(styles.getBackgroundColor());
                jTextPane.getCaret().setVisible(false);
                jTextPane.setCaretColor(styles.getCaretColor());
                jTextPane.setEditorKit(new WrapEditorKit());
                styles.setStyles(jTextPane.getStyledDocument(), jTextPane);
                JScrollPane scrollPane = new JScrollPane();
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.getViewport().add(jTextPane);
                //scrollPane.getViewport().setPreferredSize(new Dimension(mainWindowWidth,mainWindowHeight));
                scrollPane.setPreferredSize(new Dimension(mainWindowWidth, mainWindowHeight));
                //scrollPane.setMaximumSize(new Dimension(mainWindowWidth,mainWindowHeight));
                //scrollPane.getViewport().setMaximumSize(new Dimension(mainWindowWidth,mainWindowHeight));

                JLabel jlt = new JLabel("" + labelSpacePadding);
                jlt.setFont(new java.awt.Font("Dialog", 0, 12));

                jTextPanes.addElement(jTextPane);
                scrollPanes.addElement(scrollPane);
                this.add(scrollPane);
                if (hasStatusWindow) {
                    jLabels.addElement(jlt);
                    this.add(jlt);
                }

            }

        }

        public void setEnableScrollBars(final boolean enable, final JFrame jf) {

            final JCollectionOfChatWindows jccw = this;

            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    for (int i = 0; i < scrollPanes.size(); i++) {
                        JScrollPane scrollPane = (JScrollPane) scrollPanes.elementAt(i);
                        scrollPane.getVerticalScrollBar().setEnabled(enable);
                        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                        scrollPane.repaint();
                        try {
                            scrollPane.validate();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    jccw.repaint();

                    try {
                        jf.validate();
                        jf.pack();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            //jf.revalidate();
                            try {
                                jf.validate();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            jf.pack();
                            //jf.revalidate();
                        }
                    });

                }
            }
            );

        }

        public void changeWindowBackgroundColour(Object value) {
            try {
                for (int i = 0; i < this.jTextPanes.size(); i++) {
                    JTextPane jtp = (JTextPane) jTextPanes.elementAt(i);
                    jtp.setBackground((Color) value);
                    //jtp.setBackground(Color.RED);
                    jtp.repaint();
                    //System.exit(-2345);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            // System.exit(-444440);
        }

        public void changeTextStyles(final Object value) {
            try {
                for (int i = 0; i < this.jTextPanes.size(); i++) {
                    JTextPane jtp = (JTextPane) jTextPanes.elementAt(i);
                    // jtp.setBackground((Color)value);
                    StyledDocumentStyleSettings styles = (StyledDocumentStyleSettings) value;
                    styles.setStyles(jtp.getStyledDocument(), jtp);
                    jtp.repaint();
                    // System.exit(-2344456);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            // System.exit(-444440);
        }

        public Vector getTextPanes() {
            return jTextPanes;
        }

        public Vector getScrollPanes() {
            return scrollPanes;
        }

        public Vector getJLabels() {
            return jLabels;
        }

        public void setEnablePane(boolean enable) {

            JTextPane jtp = (JTextPane) jTextPanes.elementAt(windowOfParticipant);
            if (!enable & jtp.isEnabled()) {
                this.enabledColor = jtp.getBackground();
                jtp.setBackground(Color.DARK_GRAY);
                jtp.setEnabled(false);

            }
            if (enable & !jtp.isEnabled()) {
                jtp.setBackground(enabledColor);
                jtp.setEnabled(true);

            }

        }

        public void cleartextWindow(int windowno) {
            JTextPane jtp = (JTextPane) jTextPanes.elementAt(windowno);
            jtp.setText("");
        }

        public void clearAllTextWindows() {
            for (int i = 0; i < jTextPanes.size(); i++) {
                JTextPane jtp = (JTextPane) jTextPanes.elementAt(i);
                jtp.setText("");
            }
        }

    }

    class JChatFrameKeyEventListener extends java.awt.event.KeyAdapter {

        JChatFrameKeyEventListener() {
        }

        public void keyPressed(KeyEvent e) {
            getClientEventHandler().keyPressFilter(e);
            System.out.println("EVENTLISTENER DETERMINES KEYPRESSED");
        }

        public void keyReleased(KeyEvent e) {
            getClientEventHandler().keyReleaseFilter(e);
            System.out.println("EVENTLISTENER DETERMINES KEYRELEASED");
        }
    }

    class JChatFrameSENDButtonActionListener implements java.awt.event.ActionListener {

        JChatFrameSENDButtonActionListener() {
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("EVENTLISTENER DETERMINES SEND BUTTON PRESSED");
            getClientEventHandler().sendButtonPressed();
        }
    }

    public class DoScrolling implements Runnable {

        JScrollPane jsp;

        public DoScrolling(JScrollPane jsp) {
            this.jsp = jsp;
        }

        public void run() {
            jsp.validate();
            jsp.repaint();
            jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
            //jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMinimum());
            jsp.validate();
            jsp.repaint();
        }
    }

    public class DoSetTextEntryField implements Runnable {

        JTextPane jtp;
        String text;

        public DoSetTextEntryField(JTextPane jtp, String text) {
            this.jtp = jtp;
            this.text = text;
        }

        public void run() {
            jtp.getDocument().removeDocumentListener(jtpDocumentListener);
            jtp.setText(text);

            jTextEntryPane.getDocument().addDocumentListener(jtpDocumentListener);
        }

    }

    public class DoAppendTextWithCaretCheck implements Runnable {

        JTextPane jtp;
        String text;
        JScrollPane jsp;
        Object attrset;

        public DoAppendTextWithCaretCheck(JTextPane jtp, JScrollPane jsp, String text) {
            this.jtp = jtp;
            this.jsp = jsp;
            this.text = text;

        }

        public DoAppendTextWithCaretCheck(JTextPane jtp, JScrollPane jsp, String text, Object attributeset) {
            this.jtp = jtp;
            this.jsp = jsp;
            this.text = text;
            this.attrset = attributeset;
        }

        public void run() {
            //jtp.append(text);
            try {
                if (attrset instanceof String) {
                    jtp.getStyledDocument().insertString(jtp.getDocument().getLength(), text, jtp.getStyledDocument().getStyle((String) attrset));
                } else if (attrset instanceof AttributeSet) {
                    jtp.getStyledDocument().insertString(jtp.getDocument().getLength(), text, (AttributeSet) attrset);
                } else {
                    jtp.getStyledDocument().insertString(jtp.getDocument().getLength(), text, null);
                }

                 //System.exit(-999);
            } catch (Exception e) {
                System.err.println("Error inserting string to interface");
                e.printStackTrace();

            }
            SwingUtilities.invokeLater(new DoScrolling(jsp));
        }
    }

    public class DoSetLabelAndTextEntryAndSendButtonEnabled implements Runnable {

        JTextPane jtp = new JTextPane();
        JScrollPane jsp = new JScrollPane();
        boolean setEnabled;
        JLabel label;
        String text;
        boolean textIsInRed;
        JButton jSENDB;

        public DoSetLabelAndTextEntryAndSendButtonEnabled(JTextPane jtp, JButton jSENDB, JLabel label, String text, boolean textIsInRed, boolean setEnabled) {
            this.jtp = jtp;
            this.jsp = jsp;
            this.setEnabled = setEnabled;
            this.label = label;
            this.text = text;
            this.textIsInRed = textIsInRed;
            this.jSENDB = jSENDB;
        }

        public void run() {
            try {

                jtp.setEnabled(setEnabled);
                jtp.setEditable(setEnabled);
                jtp.setFocusable(setEnabled);
                jSENDB.setEnabled(setEnabled);

                if (textIsInRed) {
                    label.setForeground(Color.RED);
                } else {
                    label.setForeground(Color.BLACK);
                }
                label.setText(text);
                if (setEnabled) {
                    jtp.requestFocus();
                }

            } catch (Exception e) {
                System.err.println("Error changing blocked status of window");
            }
        }
    }

    class InputDocumentListener implements DocumentListener {

        JTextPane jTextPaneSource;

        public InputDocumentListener(JTextPane jTextPaneSource) {
            this.jTextPaneSource = jTextPaneSource;
        }

        public void insertUpdate(DocumentEvent e) {

            updateInsert(e);
        }

        public void removeUpdate(DocumentEvent e) {
            updateRemove(e);
        }

        public void changedUpdate(DocumentEvent e) {
        }

        public void updateInsert(DocumentEvent e) {
            int offset = e.getOffset();
            int length = e.getLength();
            try {
                int documentlength = jTextEntryPane.getDocument().getLength();
                int insrtIndex = (documentlength - length) - offset;  //The length of document is increased by length on insertion   
                //System.err.println("DocLength: "+documentlength+" "+". Offset: "+offset+". Length: "+length+". Text: "+jTextAreaSource.getText().substring(offset,offset+length)+". InsertIndex: "+insrtIndex);

                try {
                    getClientEventHandler().textEntryDocumentHasChangedInsert(jTextPaneSource.getText().substring(offset, offset + length), insrtIndex, length, jTextPaneSource.getText());
                } catch (Exception eee) {
                    getClientEventHandler().textEntryDocumentHasChangedInsert("", insrtIndex, length, jTextPaneSource.getText());
                    getClientEventHandler().getCts().sendErrorMessage("ERROR INSIDE CHAT FRAME WITH INDICES");
                }
            } catch (Error e2) {
                System.err.println("OFFSET ERROR " + offset + " , " + jTextPaneSource.getText().length() + " " + jTextPaneSource.getText() + " " + e2.getStackTrace());
                getClientEventHandler().getCts().sendErrorMessage(e2.getMessage() + " ERROR INSIDE THE JCHATFRAME");
            }
        }

        public void updateRemove(DocumentEvent e) {
            int offset = e.getOffset();
            int length = e.getLength();

            int documentlength = jTextEntryPane.getDocument().getLength();
            int insrtIndex = (documentlength + length) - offset; // The length of document is decreased by length on deletion
            //System.err.println("REMOVE: DocLength: "+documentlength+" "+". Offset: "+offset+". Length: "+length+". InsertIndex: "+insrtIndex);

            String textToAdd = "";
            try {
                textToAdd = jTextPaneSource.getText();
            } catch (Exception ee) {
                getClientEventHandler().getCts().sendErrorMessage(ee.getMessage() + " ERROR INSIDE THE JCHATFRAME (REMOVE1)");
            }

            try {
                getClientEventHandler().wYSIWYGDocumentHasChangedRemove(insrtIndex, length, textToAdd);
            } catch (Exception e2) {
                System.err.println("ERROR ATTEMPTING TO CAPTURE A DELETE");
                getClientEventHandler().getCts().sendErrorMessage(e2.getMessage() + " ERROR INSIDE THE JCHATFRAME (REMOVE1)");
            }

        }

    }

    public void closeDown() {

        this.jTextEntryPane.setEnabled(false);
        this.jLabeldisplay.setEnabled(false);
        this.jTextEntryScrollPane.setEnabled(false);
        this.setVisible(false);
        super.dispose();
    }

    class WrapEditorKit extends StyledEditorKit {

        ViewFactory defaultFactory = new WrapColumnFactory();

        public ViewFactory getViewFactory() {
            return defaultFactory;
        }

    }

    class WrapColumnFactory implements ViewFactory {

        public View create(javax.swing.text.Element elem) {
            String kind = elem.getName();
            if (kind != null) {
                if (kind.equals(AbstractDocument.ContentElementName)) {
                    return new WrapLabelView(elem);
                } else if (kind.equals(AbstractDocument.ParagraphElementName)) {
                    return new ParagraphView(elem);
                } else if (kind.equals(AbstractDocument.SectionElementName)) {
                    return new BoxView(elem, View.Y_AXIS);
                } else if (kind.equals(StyleConstants.ComponentElementName)) {
                    return new ComponentView(elem);
                } else if (kind.equals(StyleConstants.IconElementName)) {
                    return new IconView(elem);
                }
            }

            // default to text display
            return new LabelView(elem);
        }
    }

    class WrapLabelView extends LabelView {

        public WrapLabelView(javax.swing.text.Element elem) {
            super(elem);
        }

        public float getMinimumSpan(int axis) {
            switch (axis) {
                case View.X_AXIS:
                    return 0;
                case View.Y_AXIS:
                    return super.getMinimumSpan(axis);
                default:
                    throw new IllegalArgumentException("Invalid axis: " + axis);
            }
        }

    }

}
