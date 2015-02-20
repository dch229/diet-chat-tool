


package diet.server;
import java.util.Random;
import java.util.Vector;




/**
 * This class stores in which window participants see their own text and text of each other. It can also be used
 * to stop participants from receiving text from another, and allows configuring of whether the chat tools are initially enabled
 * when starting the experiment. It is recommended, however, that this be done explicitly in
 * the ConversationController. This class also needs to be overhauled. In particular, the definition of windowtype is unclear.
 * Future implementations will remove this class, replacing it with a simpler window manager that is integrated within the
 * Participants class.
 * 
 * @author user
 */
public class DeprecatedWindowController_MustReplace {
    
    public static final int ONEWINDOWENABLED = 0;
    public static final int ONEWINDOWDISABLED =1;
    public static final int EACHOWNWINDOWATTOPENABLED =2;
    public static final int EACHOWNWINDOWATTOPDISABLED=3;
    public static final int EACHOWNWINDOWRANDOMENABLED =4;
    public static final int EACHOWNWINDOWRANDOMDISABLED=5;
    
    private Conversation c;
    private Participants ps;
   
    
    /*
     *
     *                                     participant1 participant2 participant3 participant4 participant5  participants6
     *             participant1SendsTo        0,Bl         1,Bl         2,Bl        3,Bl          4,Bl          (null)
     *             participant2SendsTo        3,Bl         0,Bl         4,Bl        5,Bl          3,Bl          (null)
     *             participant3SendsTo        2,Bl         2,Bl         2,Bl        2,Bl          4,Bl          (null)
     *             participant4SendsTo        5,Bl         6,Bl         7,Bl        8,Bl          4,Bl          (null)
     *             participant5SendsTo        3,Bl         2,Bl         5,Bl        3,Bl          2,Bl          (null)
     *             participant6SendsTo                                                                           0,Bl
     */
    
    
    /**
     * 
     * @param c
     * @param ps
     * @param arraySize Maximum number of participants permitted in experiment (advisable to keep this much higher than number of actual participants)
     * @param arrayType Type of window 
     *        ONEWINDOWENABLED  = a single window. Text entry is enabled (If using WYSIWYG interface make sure it has floor control)
     *        ONEWINDOWDISABLED = a single window. Text entry is disabled (If using WYSIWYG interface make sure it has floor control)
     *        EACHOWNWINDOWATTOPENABLED = multiple windows, with participants own text displayed at top. Text entry is enabled
     *        EACHOWNWINDOWATTOPDISABLED = multiple windows, with participants own text displayed at top. Text entry is disabled
     *        EACHOWNWINDOWRANDOMENABLED = multiple windows, with random assigning of window numbers. Text entry is enabled.
     *        EACHOWNWINDOWRANDOMDISABLED = multiple windows, with random assigning of window numbers. Text entry is disabled.
     * 
     * 
     */
    public DeprecatedWindowController_MustReplace(Conversation c, Participants ps, int arraySize, String arrayType) {
        this.c=c;
        this.ps=ps;
        
    }   
    
    
    
    
     
   
   
    
     
    
    
    /**
     * Returns all the participants that a particular participant is permitted to send chat text to. This duplicates functionality
     * in ConversationCOntroller and will be simplified in next release.
     * @param p
     * @return
     */
    public Vector getOtherParticipantsSettingWindowsTo0(Participant p){
        //Returns Vector with first element vector of Participants, 2nd element is vector of Participants' names'
        //Default seetting is that all other participants are enabled.
        Vector participants = ps.getAllParticipants(); 
        Vector vRecipients = new Vector();
        Vector vRecipientsEmails = new Vector();
        Vector vRecipientsUsernames = new Vector();
        Vector vRecipientsWindowNumbers = new Vector();
        Vector v = new Vector();
        int pIndex = participants.indexOf(p);
        for(int i=0;i<participants.size();i++){
            //System.err.println("Getting permission for "+p.getUsername());
            Participant p2 = (Participant)participants.elementAt(i);
            if(p!=p2){//Doesn't need to be able to send to self'
                
                        vRecipients.addElement(p2);
                        vRecipientsEmails.addElement(p2.getParticipantID());
                        vRecipientsUsernames.addElement(p2.getUsername());
                        vRecipientsWindowNumbers.addElement(1);
    
            }
        }
        v.addElement(vRecipients);
        v.addElement(vRecipientsEmails);
        v.addElement(vRecipientsUsernames);
        v.addElement(vRecipientsWindowNumbers);                
        return v;
     }     
        
    
    
   
    
    /**
     * Returns the window number in which a participant sees text from another
     * @param speaker Participant
     * @param recipient Participant
     * @return window number in which recipient sees text from sender
     */
    public int getWindownumber_DefaultsToZero(Participant speaker, Participant recipient){
       return 0;
        
    } 
    
    
    

}
