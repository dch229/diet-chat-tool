package diet.server.ConversationController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import diet.client.WYSIWYGDocumentWithEnforcedTurntaking;
import diet.message.MessageCBYCChangeTurntakingStatusConfirm;
import diet.message.MessageCBYCDocChangeFromClient;
import diet.message.MessageCBYCTypingUnhinderedRequest;
import diet.message.MessageKeypressed;
import diet.parameters.ExperimentSettings;
import diet.parameters.IntParameter;
import diet.parameters.Parameter;
import diet.parameters.StringParameter;
import diet.server.Conversation;
import diet.server.Participant;
import diet.server.CbyC.DocChange;
import diet.server.CbyC.DocInsert;
import diet.server.CbyC.Sequences;
import diet.server.CbyC.Sequence.BufferedSequenceWithSpoofTypingResponsivity;
import diet.server.CbyC.Sequence.BufferredPOSTagFilterTriggeringSpoofTypingRes;
import diet.server.CbyC.Sequence.EditSequence;
import diet.server.CbyC.Sequence.LimitedRecipientsSequenceWithSpoofTypingResponsivity;
import diet.server.CbyC.Sequence.POSTagFilterSequence;
import diet.server.CbyC.Sequence.POSTagFilterSequenceBufferedAfterMatch;
import diet.server.CbyC.Sequence.RecordedSequenceFromApparentOrigin;
import diet.server.CbyC.Sequence.Sequence;
import diet.server.CbyC.Sequence.TriggerAfterDeterminerSequence;
import diet.utils.Dictionary;
import diet.utils.POSTagRegex;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class CCCBYCElicitSplitUttGivenNew extends CCCBYCDefaultController
		implements UseOfPrerecordedFakeTurn {

	public static boolean showcCONGUI() {
		return false;
	}

	private boolean interventionTriggered = false;
	Map<String, Set<String>> mentions = new HashMap<String, Set<String>>();
	IntParameter minimumSpeakerChangesBetweenInterventions;
	public IntParameter minimumTurnLengthBeforeIntervention;
	IntParameter timeToWaitForResponse = new IntParameter("Response Time Out",
			12);
	IntParameter typingTimeOutDeterminesEndOfTurn = new IntParameter(
			"Typing time out: Determines end of turn during intervention", 3);
	IntParameter maxDiffBetMentionInterventions = new IntParameter(
			"Maximum Min Max Difference (given/new)", 2);
	private int speakerChangesSinceLastIntervention;
	String[] fillerConditions = { "filler", "nonFiller" };
	String[] fillers;
	// { "err ", "errrm ", "errr ", "errm ", "er ",
	// "erm ", ". . . ", "uhh ", "uuh ", "umm " };

	File misspellingsFile = new File(System.getProperty("user.dir")+File.separator+"experimentresources"
			+ File.separator + "fragmentFilters" + File.separator
			+ "misspellings.txt");
	String taggerFileName = System.getProperty("user.dir") + File.separator
			+ "utils" + File.separator + "english-left3words-distsim.tagger";

	Parameter fillerCondition;
	MaxentTagger tagger;
	Dictionary dict;
	String antecedentOwnerUserName = null;
	Participant antecedentOwner = null;
	String curFillerCondition;

	// this will contain the number of interventions in each tag group
	// however it will not contain any numbers for the groups which
	// are always good (low frequency ones)

	public void initialize(Conversation c, ExperimentSettings expS) {
		super.initialize(c, expS);
		super.setIsTypingTimeOut(200);
		super.setProcessLoopSleepTime(80);
		this.fillerCondition = this.expSettings
				.getParameter("Filler Condition");
		this.minimumSpeakerChangesBetweenInterventions = (IntParameter) this.expSettings
				.getParameter("Speaker Changes Between Interventions");
		this.expSettings.addParameter(this.timeToWaitForResponse);
		this.expSettings.addParameter(this.typingTimeOutDeterminesEndOfTurn);
		this.minimumTurnLengthBeforeIntervention = (IntParameter) this.expSettings
				.getParameter("Minimum Words Before Intervention");
		maxDiffBetMentionInterventions = (IntParameter) this.expSettings
				.getParameter("Maximum Min Max Difference (given/new)");
		this.expSettings.addParameter(this.sendingNetErrors);

		this.minMaxUserDiff = (IntParameter) this.expSettings
				.getParameter("Maximum Min Max Difference (users)");

		this.speakerChangesSinceLastIntervention = 0;
		StringParameter fillerString = (StringParameter) this.expSettings
				.getParameter("Fillers");

		String[] fill = fillerString.getValue().split(",");
		fillers = new String[fill.length];
		Conversation.printWSln("Main", "The fillers are:");
		for (int i = 0; i < fill.length; i++) {
			fillers[i] = fill[i].trim();
			fillers[i] += " ";
			Conversation.printWSln("Main", fillers[i]);
		}

		if (((String) this.fillerCondition.getValue())
				.equalsIgnoreCase("random"))
			this.curFillerCondition = this.fillerConditions[r
					.nextInt(this.fillerConditions.length)];
		else
			this.curFillerCondition = (String) this.fillerCondition.getValue();

		Conversation
				.printWSln("Main", "Initialising Stanford POS-Tagger . . .");
		try {

			this.tagger = new MaxentTagger(this.taggerFileName);
			Conversation
					.printWSln("Main", "Initialisation ended successfully.");

			Conversation.printWSln("Main", "Loading Dictionary");
			this.dict = new Dictionary(System.getProperty("user.dir")
					+ File.separator + "utils" + File.separator
					+ "fulldictionary.txt");
			Conversation.printWSln("Main", "Loaded Dictionary");
			Conversation.printWSln("Main", "Loading Misspellings Map");
			this.loadMisspellings();
			Conversation.printWSln("Main", "Loaded Misspellings Map");

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	TreeMap<String, String> misspellings = new TreeMap<String, String>();

	private synchronized void loadMisspellings() throws IOException {
		misspellings = new TreeMap<String, String>();

		BufferedReader in = new BufferedReader(new FileReader(
				this.misspellingsFile));
		String line;
		while ((line = in.readLine()) != null) {
			String[] halves = getHalves(line);
			misspellings.put(halves[0], halves[1]);
		}

	}

	private String[] getHalves(String whole) {
		String[] result = new String[2];
		for (int i = 0; i < whole.length(); i++) {
			if (whole.substring(i, i + 1).matches("\\s+")) {
				result[0] = whole.substring(0, i);
				result[1] = whole.substring(i + 1, whole.length());
				return result;
			}
		}
		return null;
	}

	public void participantJoinedConversation(Participant p) {
		this.interPerUser.put(p.getUsername(), 0);
		mentions.put(p.getUsername(), new HashSet<String>());
	}

	// _______________________________________________________________

	long interventionTime = -1;
	BufferredPOSTagFilterTriggeringSpoofTypingRes secondHalfSeq;
	boolean waitingForProcessLoopToFinish = false;

	HashMap<String, Integer> interPerUser = new HashMap<String, Integer>();
	boolean waitingForNoun = false;

	public boolean attemptIntervention(String turnOwner, String det) {
		c.printWlnLog("CCCbyCElicit", "Intervention Triggered.");
		int turnLengthSoFar = sS.getCurrentSequence().getFinalText()
				.split("\\s+").length;
		c.printWlnLog("CCCbyCElicit", "Turn Length So Far:" + turnLengthSoFar);
		c.printWlnLog("CCCbyCElicit", "The Turn:"
				+ sS.getCurrentSequence().getFinalText());

		if (this.speakerChangesSinceLastIntervention < this.minimumSpeakerChangesBetweenInterventions
				.getValue()) {
			Conversation
					.printWSln("CCCbyCElicit",
							"Intervention aborted: Not enough turns since the previous.");
			return false;
		} else if (this.waitingOnNetworkError) {
			c.printWlnLog("CCCbyCElicit",
					"Intervention aborted: Net Error in progress");
			return false;
		} else if (turnLengthSoFar < this.minimumTurnLengthBeforeIntervention
				.getValue()) {
			c.printWlnLog("CCCbyCElicit",
					"Intervention aborted: Turn too short");
			return false;

		} else if (this.disturbsTurnOwnerBalance(turnOwner)) {

			c.printWlnLog("CCCbyCElicit",
					"Intervention aborted: would unbalance participant numbers. Participant was "
							+ turnOwner);
			return false;

		}

		interPerUser.put(turnOwner, interPerUser.get(turnOwner) + 1);

		Sequence lastSeq = sS.getCurrentSequence();

		this.secondHalfSeq = new BufferredPOSTagFilterTriggeringSpoofTypingRes(
				sS, this, turnOwner, det, this.misspellings, this.tagger);
		waitingForNoun = true;

		this.antecedentOwnerUserName = turnOwner;
		this.antecedentOwner = getC().getParticipants()
				.findParticipantWithUsername(turnOwner);
		this.responder = (Participant) getC().getParticipants()
				.getAllOtherParticipants(antecedentOwner).elementAt(0);
		if (this.processLoopRunning) {
			waitingForProcessLoopToFinish = true;
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			waitingForProcessLoopToFinish = false;
		}
		fh.setAutomaticallyAllowOpenFloorAfterIsTypingTimeout(false);
		fh.changeFloorStatusOfParticipantNoPrefix(responder,
				WYSIWYGDocumentWithEnforcedTurntaking.othertyping);
		getC().sendAndEnableLabelDisplayToALLStatusWindowsOfParticipant(
				responder, antecedentOwnerUserName + "...is typing", true, true);
		c.printWlnLog("CCCbyCElicit", "Waiting for noun");
		return true;
	}

	public boolean intervene(String defNP) {
		c.printWlnLog("CCCbyCElicit", "Found noun, checking balance");
		String mentioner = this.getMentioner(defNP);
		if (mentioner != null) {
			if (numSeenMention - numNewMention > this.maxDiffBetMentionInterventions
					.getValue()) {

				c.printWlnLog("CCCbyCElicit",
						"Intervention aborted: too many SEEN interventions");

				// abort();
				return false;
			}
			this.mentionCondition = "seen" + "[" + mentioner + "]";
			numSeenMention++;

		} else {
			if (numNewMention - numSeenMention > this.maxDiffBetMentionInterventions
					.getValue()) {
				c.printWlnLog("CCCbyCElicit",
						"Intervention aborted: too many NEW interventions");

				// abort();
				return false;
			}
			numNewMention++;
			this.mentionCondition = "new" + "[" + antecedentOwnerUserName + "]";
		}
		// this.intervene();

		if (this.processLoopRunning) {
			waitingForProcessLoopToFinish = true;
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			waitingForProcessLoopToFinish = false;
		}
		c.printWlnLog("CCCbyCElicit", "Now intervening");
		waitingForNoun = false;
		interventionTriggered = true;
		secondHalfSeq.setCondition(mentionCondition);
		// will try to handle concurrent modification and inconsistent states
		// manualy.
		// letting java do it leads to thread deadlock

		this.speakerChangesSinceLastIntervention = 0;
		fh.setAutomaticallyAllowOpenFloorAfterIsTypingTimeout(false);

		if (((String) this.fillerCondition.getValue())
				.equalsIgnoreCase("random"))
			this.curFillerCondition = this.fillerConditions[r
					.nextInt(this.fillerConditions.length)];
		else
			this.curFillerCondition = (String) this.fillerCondition.getValue();

		c.printWlnLog("CCCbyCElicit", "Filler condition is: "
				+ this.curFillerCondition);
		if (this.curFillerCondition.equalsIgnoreCase("filler")) {

			Vector recip = new Vector();
			recip.add(responder);
			String filler;

			filler = " " + this.fillers[r.nextInt(this.fillers.length)];
			RecordedSequenceFromApparentOrigin rs = new RecordedSequenceFromApparentOrigin(
					sS, this, filler, 8, recip, antecedentOwner,
					mentionCondition);
			sS.addSequence(rs);

		}

		return true;
	}

	public void abortIntervention() {
		c.printWlnLog("CCCbyCElicit", "Aborting intervention");
		if (this.processLoopRunning) {
			waitingForProcessLoopToFinish = true;
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			waitingForProcessLoopToFinish = false;
		}
		waitingForNoun = false;
		abortingIntervention = true;

	}

	boolean abortingIntervention = false;
	boolean antecedentOwnerFinishedTyping = false;
	private Participant responder;
	private boolean responseGiven = false;
	private boolean sendingRest = false;

	private IntParameter sendingNetErrors = new IntParameter(
			"Send Rand Net Error", 1);

	boolean processLoopRunning = false;

	public void processLoop() {
		processLoopRunning = true;
		if (sendingNetErrors.getValue() == 1)
			this.sendRandNetworkError();
		if (!interventionTriggered && !waitingForNoun && !abortingIntervention) {

			// Conversation.printWSln("ProcessLoop",
			// "ProcessLoop: Intervention Not Triggered." + i);
			// i++;
			// if (i == 10)
			// i = 0;

			fh.openFloorAfterTimeOut(super.getIsTypingTimeOut());
			// Conversation.printWSln("ProcessLoop", "Open Floor returned");
		} else if (waitingForNoun) {
			if (waitingForProcessLoopToFinish) {
				processLoopRunning = false;
				synchronized (this) {
					notify();
				}
				return;
			}
			if (!antecedentOwnerFinishedTyping
					&& !this.antecedentOwner
							.isTyping(1000 * this.typingTimeOutDeterminesEndOfTurn
									.getValue())) {

				antecedentOwnerFinishedTyping = true;
				Conversation
						.printWSln("CCCbyCElicit",
								"Antecendent owner finished typing while waiting for noun. Aborting");

				this.secondHalfSeq.stopRecordingDelaysAndSpoofResponsivity();
				// this.secondHalfSeq.setInputClosed();
				this.secondHalfSeq.abort();
				getC().sendAndEnableLabelDisplayToALLStatusWindowsOfParticipant(
						antecedentOwner, "Network Error. Please wait.", true,
						false);

				// we need to abort. waiting no longer since antecedent
				// owner finished typing
				fh.changeFloorStatusOfParticipantNoPrefix(responder,
						WYSIWYGDocumentWithEnforcedTurntaking.othertyping);
				fh.forceInformOthersOfTyping(antecedentOwner);
				// this.secondHalfSeq.createHeader();
				Conversation
						.printWSln("CCCbyCElicit",
								"Antecedent owner finished typing.. no suitable noun. Aborting.");
				c.printWlnLog("CCCbyCElicit",
						"Sending rest of turn. No intervention.	");
				sendingRest = true;
				sS.addSequence(this.secondHalfSeq);

			}

		} else if (abortingIntervention) {
			if (!sendingRest) {
				fh.changeFloorStatusOfParticipantNoPrefix(responder,
						WYSIWYGDocumentWithEnforcedTurntaking.othertyping);
				fh.forceInformOthersOfTyping(antecedentOwner);
				// this.secondHalfSeq.createHeader();

				c.printWlnLog("CCCbyCElicit", "Sending rest of turn. Aborting");
				sendingRest = true;
				sS.addSequence(this.secondHalfSeq);
			}
			if (!antecedentOwnerFinishedTyping
					&& !this.antecedentOwner
							.isTyping(1000 * this.typingTimeOutDeterminesEndOfTurn
									.getValue())) {

				antecedentOwnerFinishedTyping = true;
				Conversation
						.printWSln("CCCbyCElicit",
								"Antecendent owner finished typing while trying to abort. ");

				this.secondHalfSeq.stopRecordingDelaysAndSpoofResponsivity();
				// this.secondHalfSeq.setInputClosed();
				this.secondHalfSeq.abort();
				getC().sendAndEnableLabelDisplayToALLStatusWindowsOfParticipant(
						antecedentOwner, "Network Error. Please wait.", true,
						false);
			}
		} else {

			if (waitingForProcessLoopToFinish) {
				processLoopRunning = false;
				synchronized (this) {
					notify();
				}
				return;
			}
			if (!antecedentOwnerFinishedTyping
					&& !this.antecedentOwner
							.isTyping(1000 * this.typingTimeOutDeterminesEndOfTurn
									.getValue())) {

				antecedentOwnerFinishedTyping = true;
				Conversation
						.printWSln("CCCbyCElicit",
								"Antecendent owner finished typing. Now blocking him with network error");

				this.secondHalfSeq.stopRecordingDelaysAndSpoofResponsivity();
				getC().sendAndEnableLabelDisplayToALLStatusWindowsOfParticipant(
						antecedentOwner, "Network Error. Please wait.", true,
						false);

				// this.secondHalfSeq.setInputClosed();

			}
			if (this.receivingResponse) {
				if (!sendingRest
						&& !responder.isTyping(typingTimeOutDeterminesEndOfTurn
								.getValue() * 1000)) {
					fh.changeFloorStatusOfParticipantNoPrefix(responder,
							WYSIWYGDocumentWithEnforcedTurntaking.othertyping);
					// fh.informOthersOfTyping(antecedentOwner);
					getC().sendAndEnableLabelDisplayToALLStatusWindowsOfParticipant(
							responder,
							antecedentOwnerUserName + "...is typing", true,
							true);
					c.printWlnLog("CCCbyCElicit", "Responder finished response");
					c.printWlnLog("CCCbyCElicit", "Sending rest of turn.");
					this.secondHalfSeq.createHeader();
					sS.addSequence(this.secondHalfSeq);
					this.receivingResponse = false;
					responseGiven = true;
					sendingRest = true;

				}

			} else if (this.interventionTime > 0
					&& !responseGiven
					&& !sendingRest
					&& new Date().getTime() - this.interventionTime > this.timeToWaitForResponse
							.getValue() * 1000) {
				fh.changeFloorStatusOfParticipantNoPrefix(responder,
						WYSIWYGDocumentWithEnforcedTurntaking.othertyping);
				fh.forceInformOthersOfTyping(antecedentOwner);
				// this.secondHalfSeq.createHeader();
				c.printWlnLog("CCCbyCElicit",
						"waited long enough. No response given.");
				c.printWlnLog("CCCbyCElicit", "Sending rest of turn.");
				sendingRest = true;
				sS.addSequence(this.secondHalfSeq);

			}
		}

		processLoopRunning = false;

		if (waitingForProcessLoopToFinish)
			synchronized (this) {
				notify();
			}

	}

	public boolean isProcessLoopRunning() {
		return this.processLoopRunning;
	}

	public void processCBYCTypingUnhinderedRequest(Participant sender,
			MessageCBYCTypingUnhinderedRequest mWTUR) {
		if (c.getParticipants().getAllParticipants().size() != 2
				|| this.waitingOnNetworkError)
			return;
		Conversation.printWSln(
				"FloorHolder",
				"Received and processing floor request from: "
						+ sender.getUsername());
		// Conversation.printWSln("FloorHolder",
		// "Element: "+mWTUR.getElementString()+"|ES:"+mWTUR.getElementStart()+"|EF:"+mWTUR.getElementFinish()+"|Text:"+mWTUR.getText()+"|OffsetFrmR:"+mWTUR.getOffsetFrmRight());
		Conversation.printWSln("KeyPress", "FloorRequest");
		if (!interventionTriggered && !waitingForNoun && !abortingIntervention) {
			Conversation.printWSln("FloorHolder",
					"Passing request to floorholder " + sender.getUsername());

			fh.processFloorRequest(sender, mWTUR);
		} else {
			if (sender.equals(antecedentOwner)
					&& !antecedentOwnerFinishedTyping) {
				fh.changeFloorStatusOfParticipantNoPrefix(sender,
						WYSIWYGDocumentWithEnforcedTurntaking.typingunhindered);
				Conversation
						.printWSln(
								"FloorHolder",
								"sender is antecedentOwner. Not passing to floor holder. Just giving him typing unhindered "
										+ sender.getUsername());
			} else if (interventionTriggered) {
				Conversation
						.printWSln(
								"FloorHolder",
								"sender is responder. Not passing to floor holder. Just giving him typing unhindered "
										+ sender.getUsername());
				receivingResponse = true;
				fh.changeFloorStatusOfParticipantPrefix(sender,
						WYSIWYGDocumentWithEnforcedTurntaking.typingunhindered);
				if (!sS.processSpeakerChangeRequest_SetNewSequence(sender,
						mWTUR))
					Conversation
							.printWSln("ERROR",
									"process speaker change returned false. This shouldn't happen");

			} else {
				Conversation
						.printWSln(
								"FloorHolder",
								"sender is responder. But waiting for noun. Not passing to floor holder. won't let him type "
										+ sender.getUsername());
				fh.changeFloorStatusOfParticipantNoPrefix(sender,
						WYSIWYGDocumentWithEnforcedTurntaking.othertyping);

			}

		}
	}

	public void processCBYCChangeTurnTakingStatusConfirm(Participant sender,
			MessageCBYCChangeTurntakingStatusConfirm mConf) {
		fh.processStateChangeConfirm(sender, mConf);
	}

	String mentionCondition = null;
	int numNewMention = 0;
	int numSeenMention = 0;
	long lastDocChangeAfterDet = -1;

	public void processCBYCDocChange(Participant sender,
			MessageCBYCDocChangeFromClient mCDC) {
		if (c.getParticipants().getAllParticipants().size() != 2
				|| this.waitingOnNetworkError)
			return;

		if (!interventionTriggered && !waitingForNoun && !abortingIntervention) {
			Conversation.printWSln(
					"DocChange",
					"DocChage received. Handling normally"
							+ sender.getUsername());
			sS.addDocChange(mCDC);

		} else if (waitingForNoun) {
			if (sender.equals(this.antecedentOwner)) {
				this.secondHalfSeq.addDocChange(mCDC.getDocChange());

			} else {
				// received docchange from responder while waiting for noun...
				// but he should have been blocked
				Conversation
						.printWSln(
								"ERROR",
								"received docchange from responder while waiting for noun... responder should be blocked");
			}

		} else {
			if (sender.equals(this.antecedentOwner)) {
				Conversation.printWSln("DocChange",
						"DocChage received from sender. Intervention active. Adding to secondHalfSeq."
								+ sender.getUsername());
				this.secondHalfSeq.addDocChange(mCDC.getDocChange());
			} else if (!abortingIntervention) {
				Conversation
						.printWSln(
								"DocChange",
								"DocChage received from responder. Intervention Active, still passing to sequences for processing."
										+ sender.getUsername());
				sS.addDocChange(mCDC);
			}
		}
	}

	private boolean receivingResponse = false;

	// ________________________GET NEXT SEQUENCE METHODS____________

	public Sequence getNextSequence_Speaker_Change(Sequence prior,
			int sequenceNo, Sequences ss, Participant p,
			MessageCBYCTypingUnhinderedRequest mCTUR) {
		if (!interventionTriggered && !waitingForNoun && !abortingIntervention) {
			Conversation.printWSln("getNextSequence",
					"Speaker Change. Sender is:" + p.getUsername());
			Conversation.printWSln("getNextSequence",
					"Returning postagfilterseq");

			this.speakerChangesSinceLastIntervention++;
			if (prior != null)
				extractDefiniteFirstMentions(prior.getFinalText(),
						prior.getSender());
			return new TriggerAfterDeterminerSequence(ss, this,
					p.getUsername(), mCTUR, this.dict, this.misspellings);
		} else {
			// Should return limited recipient sequence (with dyads there are no
			// recipients of the others' response
			Conversation.printWSln(
					"getNextSequence",
					"Speaker Change. Intervention is active. Sender is:"
							+ p.getUsername());
			Conversation
					.printWSln("getNextSequence", "Returning LimitedRecSeq");
			receivingResponse = true;
			return new LimitedRecipientsSequenceWithSpoofTypingResponsivity(ss,
					this, p.getUsername(), new Vector(), p, mentionCondition);
		}
	}

	public Sequence getNextSequence_Edit_By_Different_Speaker(Sequence prior,
			int sequenceNo, Sequences ss, Participant p,
			MessageCBYCTypingUnhinderedRequest mCTUR) {
		c.printWlnLog("ERROR", "No edits allowed. Something is wrong");
		return new EditSequence(sS, this, p.getUsername(), mCTUR);
	}

	public Sequence getNextSequence_NewLine_By_Same_Speaker(Sequence prior,
			int sequenceNo, Sequences sS, String sender,
			MessageCBYCDocChangeFromClient mCDC) {
		if (!interventionTriggered && !waitingForNoun && !abortingIntervention) {
			Conversation.printWSln("getNextSequence", "New Line. Sender is:"
					+ sender);
			Conversation.printWSln("getNextSequence",
					"Returning postagfilterseq");
			DocChange dc = mCDC.getDocChange();
			if (prior != null)
				extractDefiniteFirstMentions(prior.getFinalText(),
						prior.getSender());
			return new TriggerAfterDeterminerSequence(sS, this, sender,
					mCDC.getTimeOnServerOfReceipt(), dc.elementString,
					dc.elementStart, dc.elementFinish, this.dict,
					this.misspellings);
		} else {
			// Should return limited recipient sequence (with dyads there are no
			// recipients of the others' response
			Conversation.printWSln("getNextSequence",
					"New Line. Intervention is active. Sender is:" + sender);
			Conversation
					.printWSln("getNextSequence", "Returning limitedRecSeq");
			receivingResponse = true;
			return new LimitedRecipientsSequenceWithSpoofTypingResponsivity(sS,
					this, sender, new Vector(), getC().getParticipants()
							.findParticipantWithUsername(sender),
					mentionCondition);
		}
	}

	public Sequence getNextSequence_Edit_By_Same_Speaker(Sequence prior,
			int sequenceNo, Sequences sS, String sender,
			MessageCBYCDocChangeFromClient mCDC) {
		c.printWlnLog("ERROR", "No edits allowed. Something is wrong");
		DocChange dc = mCDC.getDocChange();
		EditSequence seq2 = new EditSequence(sS, this, sender,
				mCDC.getTimeOnServerOfReceipt(), dc.elementString,
				dc.elementStart, dc.elementFinish);
		return seq2;

	}

	public void processKeyPress(Participant sender, MessageKeypressed mkp) {

	}

	public void resetToNormal() {
		c.printWlnLog("CCCbyCElicit", "resetting to normal");
		BufferedSequenceWithSpoofTypingResponsivity last = (BufferedSequenceWithSpoofTypingResponsivity) sS
				.getCurrentSequence();
		
		
		this.interventionTime = -1;
		this.interventionTriggered = false;
		this.antecedentOwner = null;

		this.antecedentOwnerFinishedTyping = false;
		this.responder = null;
		this.receivingResponse = false;
		this.responseGiven = false;
		this.sendingRest = false;
		this.abortingIntervention = false;
		this.waitingForNoun = false;
		this.forceOpenFloorForAll();

		// now need to extract mentions from the antecedent turn, composed of
		// the 2 halves
		// the two haves may have the response sequences (limitedrecip) in
		// between

		
		extractDefiniteFirstMentions(last.getFinalText().trim(),
				this.antecedentOwnerUserName);
		this.antecedentOwnerUserName = null;

	}

	// ___________________________________Dealing with random network errors:

	long networkErrorInterval = 120000;
	long lastNetworkErrorTime = new Date().getTime();
	boolean waitingOnNetworkError = false;

	public void sendRandNetworkError() {

		if (!this.interventionTriggered && !this.waitingForProcessLoopToFinish
				&& !this.waitingForNoun && !this.abortingIntervention) {

			// send error if time has come
			if (new Date().getTime() - lastNetworkErrorTime > networkErrorInterval) {

				Vector participants = c.getParticipants()
						.getAllActiveParticipants();
				for (Object p : participants)
					c.sendAndEnableLabelDisplayToALLStatusWindowsOfParticipant(
							(Participant) p, "Network Error. Please wait..",
							true, false);
				lastNetworkErrorTime = new Date().getTime();
				networkErrorInterval = 540000 + r.nextInt(60000);
				waitingOnNetworkError = true;
				c.printWlnLog("CCCbyCElicit",
						"Blocking participants with network error");
				try {
					Thread.sleep(4000 + r.nextInt(2000));
				} catch (Exception e) {
					e.printStackTrace();
				}
				for (Object p : participants) {
					c.sendAndEnableLabelDisplayToALLStatusWindowsOfParticipant(
							(Participant) p, "Status: OK", false, true);

				}
				fh.openFloorAfterTimeOut(super.getIsTypingTimeOut());
				waitingOnNetworkError = false;
				c.printWlnLog("CCCbyCElicit",
						"Released participants from network error");

			} else
				return;
		} else {
			// don't send error. wait until next time(reduced interval).
			lastNetworkErrorTime = new Date().getTime();
			networkErrorInterval = 20000 + r.nextInt(20000);

		}

	}

	@Override
	public synchronized void finishedSendingFakeTurn(long timeOfLastSend) {
		fh.changeFloorStatusOfParticipantNoPrefix(responder,
				WYSIWYGDocumentWithEnforcedTurntaking.nooneelsetyping);
		getC().sendAndEnableLabelDisplayToALLStatusWindowsOfParticipant(
				responder, "Please type", false, true);
		interventionTime = new Date().getTime();
		notifyAll();
	}

	public boolean waitingOnNetError() {
		System.out.println(this.waitingOnNetworkError);
		return this.waitingOnNetworkError;
	}

	public boolean interventionTriggered() {
		System.out.println(this.interventionTriggered);
		return this.interventionTriggered;
	}

	public Participant getAntecedentOwner() {
		System.out.println(this.antecedentOwner.getUsername());
		return this.antecedentOwner;
	}

	public Participant getResponder() {
		System.out.println(this.responder.getUsername());
		return this.responder;
	}

	public boolean receivingResponse() {
		System.out.println(this.receivingResponse);
		return this.receivingResponse;
	}

	public synchronized void setWaitingOnNetError(boolean a) {
		this.waitingOnNetworkError = a;
	}

	IntParameter minMaxUserDiff;

	public boolean disturbsTurnOwnerBalance(String owner) {
		int[] minMax = findMinMaxInterPerUserAfterIntervention(owner);
		int min = minMax[0];
		int max = minMax[1];
		Conversation.printWSln("InterV Per Part Balance Check", "minMax:" + min
				+ " " + max);
		if (max - min > minMaxUserDiff.getValue()) {
			Conversation.printWSln("InterV Per Part Balance Check",
					"balance disturbed. Turn Owner:" + owner);
			Conversation.printWSln("InterV Per Part Balance Check",
					"Inter Per Part counts were:\n" + interPerUser.toString());
			return true;
		} else {
			Conversation.printWSln("InterV Per Part Balance Check",
					"balance not disturbed. Turn Owner:" + owner);
			Conversation.printWSln("InterV Per Part Balance Check",
					"Inter Per Part counts were:\n" + interPerUser.toString());
			return false;

		}
	}

	private int[] findMinMaxInterPerUserAfterIntervention(String owner) {
		int[] r = new int[2];
		r[0] = 9999;
		r[1] = 0;
		for (String userName : this.interPerUser.keySet()) {
			Integer cur;
			if (userName.equals(owner))
				cur = this.interPerUser.get(userName) + 1;
			else
				cur = this.interPerUser.get(userName);

			if (cur < r[0])
				r[0] = cur;
			if (cur > r[1])
				r[1] = cur;
		}
		return r;

	}

	final String[] defDeterminers = { "the", "that", "this", "your", "my",
			"her", "his", "their", "our" };

	List<String> defDet = Arrays.asList(defDeterminers);

	public void extractDefiniteFirstMentions(String turn, String owner) {

		System.out.println("CORRECTING SPELLING");
		String turnSpellingCorrected = this.fixSpelling(turn);
		c.printWlnLog("DefMentions", "extracting def mentions from: " + turn);
		List<List<HasWord>> sentences = MaxentTagger
				.tokenizeText(new StringReader(turnSpellingCorrected));

		for (List<HasWord> sentence : sentences) {
			List<TaggedWord> tagged = tagger.tagSentence(sentence);
			c.printWlnLog("DefMentions", "tagged: " + tagged);
			for (int i = 0; i < tagged.size(); i++) {
				TaggedWord w = tagged.get(i);
				String mention = null;
				if (w.tag().equals("NNP")
						|| w.tag().equals("NNS")
						|| (w.word().length() > 1 && w.word().charAt(0) >= 'A' && w
								.word().charAt(0) <= 'Z'))
					mention = w.word().toLowerCase();

				else if (defDet.contains(w.word()) && i < tagged.size() - 2
						&& tagged.get(i + 2).tag().startsWith("NN"))
					mention = tagged.get(i + 2).word().toLowerCase();

				else if (defDet.contains(w.word()) && i < tagged.size() - 1
						&& tagged.get(i + 1).tag().startsWith("NN"))
					mention = tagged.get(i + 1).word().toLowerCase();

				if (mention != null && getMentioner(mention) == null)
					this.mentions.get(owner).add(mention);

			}
		}
		c.printWlnLog("DefMentions", "Def mentions after extraction:"
				+ this.mentions);

	}

	/**
	 * 
	 * @param s
	 * @return null if not mentioned, the username of the owner of mention if
	 *         mentioned before
	 */
	public String getMentioner(String s) {
		String[] words = fixSpelling(s.trim()).split("\\s");
		for (String name : mentions.keySet())
			if (mentions.get(name).contains(
					words[words.length - 1].toLowerCase()))
				return name;

		return null;
	}

	public String fixSpelling(String turnSoFar) {

		String split[] = turnSoFar.split("\\s+");
		String result = "";
		for (int i = 0; i < split.length; i++) {

			String w = split[i];

			String lowerCase = w.toLowerCase();
			Pattern p = Pattern
					.compile("([\\\\/\\.\\?!,;\\(\\)]*)([^\\\\/\\.\\?!,;\\(\\)]+)([\\\\/\\.\\?!,;\\(\\)]*)");
			Pattern p1 = Pattern
					.compile("([\\\\/\\.\\?!,;\\(\\)]*)([^\\\\/\\.\\?!,;\\(\\)]+)([\\\\/\\.\\?!,;\\(\\)]+)([^\\\\/\\.\\?!,;\\(\\)]+)([\\\\/\\.\\?!,;\\(\\)]*)");
			Matcher m = p.matcher(lowerCase);
			Matcher m1 = p1.matcher(lowerCase);
			if (m.matches()) {
				if (misspellings.containsKey(m.group(2))) {
					String replacement = misspellings.get(m.group(2));
					result += m.group(1) + replacement + m.group(3) + " ";
				} else {
					result += m.group(1) + m.group(2) + m.group(3) + " ";
				}
			} else if (m1.matches()) {

				result += m1.group(1)
						+ (misspellings.containsKey(m1.group(2)) ? misspellings
								.get(m1.group(2)) : m1.group(2))
						+ m1.group(3)
						+ (misspellings.containsKey(m1.group(4)) ? misspellings
								.get(m1.group(4)) : m1.group(4)) + m1.group(5)
						+ " ";

			} else {
				if (misspellings.containsKey(lowerCase)) {

					String replacement = misspellings.get(lowerCase);

					result += replacement + " ";
				} else {
					if (!w.matches("[A-Z][a-z]*"))
						result += lowerCase + " ";
					else
						result += w + " ";
				}
			}
		}
		if (turnSoFar.endsWith(" "))
			return result;
		else
			return result.trim();
	}

}
