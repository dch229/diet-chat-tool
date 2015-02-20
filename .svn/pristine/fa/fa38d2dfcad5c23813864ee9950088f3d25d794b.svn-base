package diet.server.ConversationController;

import java.util.Vector;

import diet.client.WYSIWYGDocumentWithEnforcedTurntaking;
import diet.message.MessageCBYCChangeTurntakingStatus;
import diet.message.MessageCBYCChangeTurntakingStatusConfirm;
import diet.message.MessageCBYCDocChangeFromClient;
import diet.message.MessageCBYCTypingUnhinderedRequest;
import diet.message.MessageKeypressed;
import diet.parameters.ExperimentSettings;
import diet.parameters.Parameter;
import diet.parameters.StringParameterFixed;
import diet.server.Conversation;
import diet.server.Participant;
import diet.server.CbyC.DocChange;
import diet.server.CbyC.DocInsert;
import diet.server.CbyC.DocRemove;
import diet.server.CbyC.FloorHolder;
import diet.server.CbyC.FloorHolderAdvanced;
import diet.server.CbyC.Sequences;
import diet.client.StyleManager;
import diet.parameters.DefaultSettingsFactory;
import diet.server.CbyC.Sequence.EditSequence;
import diet.server.CbyC.Sequence.Sequence;
import diet.server.conversationhistory.turn.CBYCTURN;
import diet.server.conversationhistory.turn.Turn;

public class CCCBYCDefaultController extends DefaultConversationController {

    public static boolean showcCONGUI(){
        return true;
    } 
    
    
	public FloorHolder fh;
	public Sequences sS;
	
	public CCCBYCDefaultController() {
	}

	@Override
	public void initialize(Conversation c, ExperimentSettings expSettings) {
		
		super.initialize(c, expSettings);
		sm = new StyleManager(this);
		fh = new FloorHolderAdvanced(this);
		sS = new Sequences(this);
		fh.sS = sS;

	}

	public void processCBYCTypingUnhinderedRequest(Participant sender,MessageCBYCTypingUnhinderedRequest mWTUR) {
		if (c.getParticipants().getAllParticipants().size() < 2) return;
		Conversation.printWSln("FloorHolder", "Received and processing floor request:");
		Conversation.printWSln("FloorHolder", "Element: "+mWTUR.getElementString()+"|ES:"+mWTUR.getElementStart()+"|EF:"+mWTUR.getElementFinish()+"|Text:"+mWTUR.getText()+"|OffsetFrmR:"+mWTUR.getOffsetFrmRight());
		Conversation.printWSln("KeyPress", "FloorRequest");
		fh.processFloorRequest(sender, mWTUR);
	}

	public void processCBYCDocChange(Participant sender,MessageCBYCDocChangeFromClient mCDC) {
		
		sS.addDocChange(mCDC);
		if (mCDC.getDocChange() instanceof DocInsert) {
			DocInsert di = (DocInsert) mCDC.getDocChange();
			getC().printWln("DOCINSERTS",	"From=" + di.getSender() + " | To="+ di.getRecipient()+" | String='" + di.getStr()+ "' | OFFSET=" + di.offs + " | ElementString='" + di.elementString + "' | Start=" + di.elementStart + " | Finish=" + di.elementFinish);
		}
		else if(mCDC.getDocChange() instanceof DocRemove){
			DocRemove dr = (DocRemove) mCDC.getDocChange();
			getC().printWln("DOCINSERTS", "DocRemove at: "+dr.getOffs()+" with length "+dr.getLen());
		}

	}

	public void processCBYCChangeTurnTakingStatusConfirm(Participant sender,MessageCBYCChangeTurntakingStatusConfirm mConf) {
		fh.processStateChangeConfirm(sender, mConf);
	}

	@Override
	public void processLoop() {
		fh.openFloorAfterTimeOut(super.getIsTypingTimeOut());
	}

	public void setStateDEPRECATED(String participantName, int newState) {
		Participant p = getC().getParticipants().findParticipantWithUsername(participantName);
		MessageCBYCChangeTurntakingStatus mcbycOther = new MessageCBYCChangeTurntakingStatus(	"server", "server",WYSIWYGDocumentWithEnforcedTurntaking.nooneelsetyping, null);
		p.sendMessage(mcbycOther);
		System.out.println("STATECHANGED");
	}

	@Override
	public Turn getTurnTypeForIO_DEPRECATED() {
		return new CBYCTURN();
	}

	public Sequence getNextSequence_Speaker_Change(Sequence prior,int sequenceNo, Sequences ss, Participant p, MessageCBYCTypingUnhinderedRequest mCTUR) {
		return new Sequence(sS, this, p.getUsername(), mCTUR);
	}

	public Sequence getNextSequence_Edit_By_Different_Speaker(Sequence prior,int sequenceNo, Sequences ss, Participant p,MessageCBYCTypingUnhinderedRequest mCTUR) {
		return new EditSequence(sS, this, p.getUsername(), mCTUR);
	}

	public Sequence getNextSequence_NewLine_By_Same_Speaker(Sequence prior,	int sequenceNo, Sequences sS, String sender,MessageCBYCDocChangeFromClient mCDC) {
		DocChange dc = mCDC.getDocChange();
		EditSequence seq2 = new EditSequence(sS, this, sender, mCDC.getTimeOnServerOfReceipt(), dc.elementString, dc.elementStart,	dc.elementFinish);
		return seq2;
	}

	public Sequence getNextSequence_Edit_By_Same_Speaker(Sequence prior,int sequenceNo, Sequences sS, String sender, MessageCBYCDocChangeFromClient mCDC) {
		DocChange dc = mCDC.getDocChange();
		EditSequence seq2 = new EditSequence(sS, this, sender, mCDC.getTimeOnServerOfReceipt(), dc.elementString, dc.elementStart,	dc.elementFinish);
		return seq2;
		
	}
	public void processKeyPress(Participant sender, MessageKeypressed mkp)
	{
		//do nothing (for now), otherwise while A has the floor and the other types, A sees
		//that other is typing, instead of the usual "You are typing"
	}
	/**
	 * by default, does nothing. subclasses implementing particular interventions
	 * should override to get the state of the controller back to normal after intervention. 
	 * 
	 */
	public void resetToNormal()
	{
		
	}
	public synchronized void forceOpenFloorForAll() {
		fh.setAutomaticallyAllowOpenFloorAfterIsTypingTimeout(true);
		Vector ps = getC().getParticipants().getAllActiveParticipants();
		for (Object p : ps) {
			Participant par = (Participant) p;
			fh.changeFloorStatusOfParticipantNoPrefix(par,
					WYSIWYGDocumentWithEnforcedTurntaking.nooneelsetyping);
			getC().sendAndEnableLabelDisplayToALLStatusWindowsOfParticipant(
					par, "Please type", false, true);
		}
	}

    
        
     public static ExperimentSettings getDefaultSettings() {
         ExperimentSettings esd = DefaultSettingsFactory.getDefaultExperimentParameters();
         esd.changeParameterValue("Chat tool interface", "CBYC");
         esd.changeParameterValue("Number of rows in chat window", 350);
         esd.changeParameterValue("Number of columns in chat window", 500);
         esd.changeParameterValue("Number of participants per conversation", 3);
         
         return esd;
    }

    @Override
    public boolean requestParticipantJoinConversation(String participantID) {
        //return super.requestParticipantJoinConversation(participantID); //To change body of generated methods, choose Tools | Templates.
        int maxNumber = (Integer)this.expSettings.getParameter("Number of participants per conversation").getValue();
                
        if(this.c.getParticipants().getAllParticipants().size()>=maxNumber)return false;
        return true;
                //esd.changeParameterValue("Number of participants per conversation", 3);
    
    }
	
     

}
