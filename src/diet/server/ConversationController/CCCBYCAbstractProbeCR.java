package diet.server.ConversationController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import diet.client.WYSIWYGDocumentWithEnforcedTurntaking;
import diet.message.MessageCBYCChangeTurntakingStatusConfirm;
import diet.message.MessageCBYCDocChangeFromClient;
import diet.message.MessageCBYCTypingUnhinderedRequest;
import diet.parameters.ExperimentSettings;
import diet.parameters.IntParameter;
import diet.parameters.Parameter;
import diet.server.Conversation;
import diet.server.Participant;
import diet.server.CbyC.FloorHolderCRProcessing;
import diet.server.CbyC.Sequence.POSTagFilterSequence;
import diet.server.CbyC.Sequence.RecordedSequenceFromApparentOrigin;
import diet.utils.Dictionary;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public abstract class CCCBYCAbstractProbeCR extends CCCBYCDefaultController
		implements UseOfPrerecordedFakeTurn {

	public static boolean showcCONGUI() {
		return false;
	}

	int numberOfParticipants = 2;
	long startTimeOfFragSending = -1;
	long finishTimeOfFragSending = -1;
	int speakerChangesSinceLastIntervention = 0;
	String frag = null;
	String probeCondition;

	final String[] acks = { "ok", "okay", "right", "alright", "oh ok", "uhuh",
			"uhu" };
	// final String[] qbotAcks={"thanks!", "ok", "thank you", "okay"};
	// boolean qbot=false;
	File misspellingsFile = new File(System.getProperty("user.dir")+File.separator+"experimentresources"
			+ File.separator + "fragmentFilters" + File.separator
			+ "misspellings.txt");
	File defMentionRegexFile = new File(System.getProperty("user.dir")+File.separator+"experimentresources"
			+ File.separator + "fragmentFilters" + File.separator
			+ "defMentionRegexes.txt");

	String taggerFileName = System.getProperty("user.dir")+File.separator+"experimentresources" + File.separator
			+ "utils" + File.separator + "english-bidirectional-distsim.tagger";

	Map<String, Set<String>> mentions = new HashMap<String, Set<String>>();
	String appOrigCondition = null;
	boolean mentionIntervention = true;
	boolean interventionTriggered = false;
	boolean receivingResponse = false;
	FloorHolderCRProcessing fhc;
	Participant fragAppOrigin = null;
	IntParameter timeToWaitForResponse;
	IntParameter typingTimeOutBeforeSendingAck;
	IntParameter minimumFloorChangesBetweenInterventions;
	long finishTimeOfFakeTurnSending = -1;
	MaxentTagger tagger;
	Dictionary dict;
	TreeMap<String, String> misspellings = new TreeMap<String, String>();

	public void participantJoinedConversation(Participant p) {
		mentions.put(p.getUsername(), new HashSet<String>());
	}

	public synchronized void loadMisspellings() throws IOException {
		misspellings = new TreeMap<String, String>();
		Conversation.printWSln("Main", "loading misspellings");
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

	public void initialize(Conversation c, ExperimentSettings expS) {
		super.initialize(c, expS);
		super.setIsTypingTimeOut(200);
		super.setProcessLoopSleepTime(80);

		IntParameter responseWait = (IntParameter) expSettings
				.getParameter("Frag Response Time Out");
		this.timeToWaitForResponse = responseWait != null ? responseWait
				: new IntParameter("Frag Response Time Out", 7);

		IntParameter typeTimeOutAck = (IntParameter) expSettings
				.getParameter("Typing Time Out Before Ack");
		this.typingTimeOutBeforeSendingAck = typeTimeOutAck != null ? typeTimeOutAck
				: new IntParameter("Typing Time Out Before Ack", 3);

		IntParameter np = (IntParameter) this.expSettings
				.getParameter("Number of participants per conversation");
		IntParameter minfloor = (IntParameter) this.expSettings
				.getParameter("Min floor changes between interventions");

		this.minimumFloorChangesBetweenInterventions = minfloor != null ? minfloor
				: new IntParameter("Min floor changes between interventions", 2);

		Parameter mention = this.expSettings.getParameter("MentionManip");

		this.mentionIntervention = (mention != null)
				&& !mention.getValue().equals("off");

		this.probeCondition = "";
		String appOrig = (String) this.expSettings.getParameter("AppOrig")
				.getValue();
		if (appOrig != null && !appOrig.equalsIgnoreCase("partner"))
			this.appOrigCondition = appOrig;
		this.numberOfParticipants = np.getValue();
		super.fh = null;
		fhc = new FloorHolderCRProcessing(this);
		fhc.sS = this.sS;
		try {
			Conversation.printWSln("Main",
					"Initialising Stanford POS-Tagger . . .");
			this.tagger = new MaxentTagger(this.taggerFileName);
			Conversation.printWSln("Main", "Initialised Tagger.");
			Conversation.printWSln("Main", "Loading Dictionary");
			this.dict = new Dictionary(System.getProperty("user.dir")
					+ File.separator + "experimentresources"+File.separator+"utils" + File.separator
					+ "fulldictionary.txt");
			Conversation.printWSln("Main", "Loaded Dictionary");
			Conversation.printWSln("Main", "Loading Misspellings Map");
			this.loadMisspellings();
			Conversation.printWSln("Main", "Loaded Misspellings Map");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// this should be called by any pre-recorded fake sequence/turn to notify
	// this c controller
	// that the sending off of fake turn was finished, so it can be dealt with
	// according to whatever intervention
	// is being programmed

	public synchronized void finishedSendingFakeTurn(long finishTime) {
		this.finishTimeOfFakeTurnSending = finishTime;

		c.printWlnLog("CCCbyCProbe", "finish time of fake turn is set to:"
				+ finishTime);
		notifyAll();
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

	String mentionCondition = null;
	int numNewMention = 0;
	int numSeenMention = 0;

	public synchronized boolean triggerFragSending(String antecedentOwner,
			String frag) {
		c.printWlnLog("CCCbyCProbe", "Intervention Triggered");
		if (this.speakerChangesSinceLastIntervention < this.minimumFloorChangesBetweenInterventions
				.getValue()) {
			c.printWlnLog("CCCbyCProbe",
					"Intervention aborted: Not enough turns since the previous.");
			return false;
		} else if (mentionIntervention) {
			String mentioner = this.getMentioner(frag);
			if (isDefinite(frag) && this.getMentioner(frag) != null) {
				if (numSeenMention - numNewMention > 2) {

					c.printWlnLog("CCCbyCProbe",
							"Intervention aborted: too many SEEN interventions");
					return false;
				}
				this.mentionCondition = "seen" + "[" + mentioner + "]";
				numSeenMention++;

			} else {
				if (numNewMention - numSeenMention > 2) {
					c.printWlnLog("CCCbyCProbe",
							"Intervention aborted: too many NEW interventions");
					return false;
				}
				numNewMention++;
				this.mentionCondition = "new" + "[" + antecedentOwner + "]";
			}

			c.printWlnLog("CCCbyCProbe", "Intervening ... mentionCondition:"
					+ mentionCondition);
			c.printWlnLog("CCCbyCProbe", "Seen Mention interventions now: "
					+ numSeenMention);
			c.printWlnLog("CCCbyCProbe", "New Mention intervention now: "
					+ numNewMention);

		}
		c.printWlnLog("CCCbyCProbe", "before extraction");

		//

		this.speakerChangesSinceLastIntervention = 0;

		setFragAppOrig(this.chooseFragApparentOrigin(antecedentOwner));
		c.printWlnLog("CCCbyCProbe", "before telling floor holder");
		fhc.sendingFrag(true);
		this.frag = frag;

		c.printWlnLog("CCCbyCProbe", "frag is:" + frag);
		interventionTriggered = true;
		return true;

	}

	protected void setFragAppOrig(Participant appOrig) {
		this.fragAppOrigin = appOrig;
		this.fhc.fragAppOrig = appOrig;
	}

	long networkErrorInterval = 120000;
	long lastNetworkErrorTime = new Date().getTime();
	boolean waitingOnNetworkError = false;
	private boolean captureResponse = true;
	protected boolean sendRandNetError = true;

	protected void sendRandNetworkError() {
		if (this.fragAppOrigin == null) {

			// send error if time has come
			if (new Date().getTime() - lastNetworkErrorTime > networkErrorInterval) {

				Vector participants = c.getParticipants()
						.getAllActiveParticipants();
				for (Object p : participants)
					c.sendAndEnableLabelDisplayToALLStatusWindowsOfParticipant(
							(Participant) p, "Network Error. Please wait..",
							true, false);
				lastNetworkErrorTime = new Date().getTime();
				networkErrorInterval = 240000 + r.nextInt(60000);
				waitingOnNetworkError = true;

				try {
					Thread.sleep(4000 + r.nextInt(2000));
				} catch (Exception e) {
					e.printStackTrace();
				}
				for (Object p : participants) {
					c.sendAndEnableLabelDisplayToALLStatusWindowsOfParticipant(
							(Participant) p, "Status: OK", false, true);

				}
				this.resetToNormal();// fhc.openFloorAfterTimeOut(super.getIsTypingTimeOut());
				waitingOnNetworkError = false;

			} else
				return;
		} else {
			// don't send error. wait until next time(reduced interval).
			lastNetworkErrorTime = new Date().getTime();
			networkErrorInterval = 20000 + r.nextInt(20000);

		}

	}

	public void processCBYCChangeTurnTakingStatusConfirm(Participant sender,
			MessageCBYCChangeTurntakingStatusConfirm mConf) {
		if (getC().getParticipants().getAllParticipants().size() != this.numberOfParticipants)
			return;

		fhc.processStateChangeConfirm(sender, mConf);
	}

	protected abstract Participant chooseFragApparentOrigin(
			String antecedentOwner);

	public void processCBYCTypingUnhinderedRequest(Participant sender,
			MessageCBYCTypingUnhinderedRequest mWTUR) {
		if (getC().getParticipants().getAllParticipants().size() != this.numberOfParticipants)
			return;

		c.printWlnLog("FloorHolder",
				"Processing Floor request from" + sender.getUsername());

		if (this.waitingOnNetworkError)
			return;

		fhc.processFloorRequest(sender, mWTUR);
	}

	public void processCBYCDocChange(Participant sender,
			MessageCBYCDocChangeFromClient mCDC) {
		if (getC().getParticipants().getAllParticipants().size() != 2
				|| this.waitingOnNetworkError)
			return;

		if (!interventionTriggered || !(this.finishTimeOfFragSending < 0)) {
			sS.addDocChange(mCDC);
		}
	}

	public void resetToNormal() {

		fhc.openFloorResetAllToNormal();
		this.receivingResponse = false;
		startTimeOfFragSending = -1;
		finishTimeOfFragSending = -1;
		this.interventionTriggered = false;
		this.setFragAppOrig(null);

	}

	protected void sendFakeAckAndResetToNormalWhenDone() {
		fhc.sendingFrag(true);
		String ack;
		ack = this.acks[r.nextInt(acks.length)];

		fhc.changeFloorStatusOfParticipantsNoPrefix(this.getC()
				.getParticipants().getAllOtherParticipants(fragAppOrigin),
				WYSIWYGDocumentWithEnforcedTurntaking.othertyping);
		long typingSpeed = this
				.getC()
				.getHistory()
				.getAverageTypingSpeedCharsPerSecond(
						fragAppOrigin.getUsername(), 10);
		RecordedSequenceFromApparentOrigin rs;
		String condition = probeCondition
				+ (mentionIntervention ? "/" + mentionCondition : "");
		rs = new RecordedSequenceFromApparentOrigin(sS, this, ack, typingSpeed,
				this.getC().getParticipants()
						.getAllOtherParticipants(fragAppOrigin),
				this.appOrig, condition);

		rs.createHeader();

		sS.addSequence(rs);
		fhc.forceInformOthersOfTyping(fragAppOrigin);

		try {

			synchronized (this) {

				c.printWlnLog("CCCbyCProbe", "starting to wait for ack output");
				wait();

				c.printWlnLog("CCCbyCProbe",
						"Finished waiting for ack to be sent");

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// reset to normal:
		fhc.openFloorResetAllToNormal();
		this.receivingResponse = false;
		startTimeOfFragSending = -1;
		finishTimeOfFragSending = -1;
		this.interventionTriggered = false;
		this.setFragAppOrig(null);

	}

	public void setCaptureResponse(boolean b) {
		this.captureResponse = b;
	}

	public void processLoop() {

		if (getC().getParticipants().getAllParticipants().size() != 2)
			return;
		long now = new Date().getTime();
		if (!this.interventionTriggered) {
			fhc.openFloorAfterTimeOut(super.getIsTypingTimeOut());// normal
			// operation
		} else {
			if (this.startTimeOfFragSending < 0) {
				intervene();
			} else {
				if (this.finishTimeOfFakeTurnSending > 0) {
					if (receivingResponse) {
						if (fhc.nobodyTypingAndFloorIsOpen(this.typingTimeOutBeforeSendingAck
								.getValue() * 1000)) {
							sendFakeAckAndResetToNormalWhenDone();
						} else {
							fhc.openFloorAfterTimeOut(super
									.getIsTypingTimeOut());
						}

					} else if (now - this.finishTimeOfFragSending > 1000 * this.timeToWaitForResponse
							.getValue()
							&& fhc.nobodyTypingAndFloorIsOpen(this.typingTimeOutBeforeSendingAck
									.getValue() * 1000)) {
						// nothing received after frag was sent and the timeout
						// has
						// expired
						fhc.openFloorResetAllToNormal();
						startTimeOfFragSending = -1;
						finishTimeOfFragSending = -1;
						this.receivingResponse = false;
						this.interventionTriggered = false;
						this.setFragAppOrig(null);
						c.printWlnLog("CCCbyCProbe",
								"opening floor . . waited long enough");

					} else {
						fhc.openFloorAfterTimeOut(super.getIsTypingTimeOut());
					}

				}

			}
		}
		if (this.sendRandNetError)
			this.sendRandNetworkError();
	}

	// this is part of the processLoop Thread
	String appOrig;
	public void intervene() {

		fhc.changeFloorStatusOfParticipantsNoPrefix(this.getC()
				.getParticipants().getAllOtherParticipants(fragAppOrigin),
				WYSIWYGDocumentWithEnforcedTurntaking.othertyping);
		this.getC().sendAndEnableLabelDisplayToALLStatusWindowsOfParticipant(
				fragAppOrigin, "Network Error...please wait", true, false);
		long typingSpeed = getC().getHistory()
				.getAverageTypingSpeedCharsPerSecond(
						fragAppOrigin.getUsername(), 5);
		String recording;
		if (this.captureResponse)
			recording = this.frag + "?";
		else
			recording = this.frag;

		RecordedSequenceFromApparentOrigin rs;
		String condition = probeCondition
				+ (mentionIntervention ? "/" + mentionCondition : "");
		
		if (appOrigCondition!=null&&appOrigCondition.equalsIgnoreCase("random"))
		{
			String[] app={"Q-BOT", fragAppOrigin.getUsername()};
			Random r=new Random();
			appOrig=app[r.nextInt(app.length)];
		}
		else if(appOrigCondition!=null)
			appOrig=appOrigCondition;
		else appOrig=fragAppOrigin.getUsername();
		
		rs = new RecordedSequenceFromApparentOrigin(sS, this, recording,
				typingSpeed, this.getC().getParticipants()
						.getAllOtherParticipants(fragAppOrigin),
				appOrig,
				condition);
		rs.createHeader();
		String curTurn = ((POSTagFilterSequence) sS.getCurrentSequence())
				.getTurnSofar();
		//
		extractDefiniteFirstMentions(curTurn, sS.getCurrentSequence()
				.getSender());
		sS.addSequence(rs);

		fhc.forceInformOthersOfTyping(fragAppOrigin);

		c.printWlnLog("CCCByCProbe", "Setting frag app orig to:"
				+ fragAppOrigin.getUsername());

		try {

			synchronized (this) {
				this.startTimeOfFragSending = new Date().getTime();
				c.printWlnLog("CCCByCProbe", "starting to wait for frag output");
				wait();
				this.finishTimeOfFragSending = this.finishTimeOfFakeTurnSending;
				c.printWlnLog("CCCByCProbe",
						"Finished waiting for frag to be sent");

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (this.captureResponse)
			fhc.openFloorAfterFragSending();
		else {
			this.resetToNormal();
		}

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

	public boolean isDefinite(String frag) {
		String[] fragWords = frag.split("\\s");
		if (defDet.contains(fragWords[0]) || fragWords.length == 1)
			return true;

		return false;
	}

	final String[] defDeterminers = { "the", "that", "this", "your", "my",
			"her", "his", "their", "our" };

	List<String> defDet = Arrays.asList(defDeterminers);

	public void extractDefiniteFirstMentions(String turn, String owner) {

		System.out.println("CORRECTING SPELLING");
		String turnSpellingCorrected = this.fixSpelling(turn);
		c.printWlnLog("CCCBYCProbe", "extracting def mentions from: " + turn);
		List<List<HasWord>> sentences = MaxentTagger
				.tokenizeText(new StringReader(turnSpellingCorrected));
		for (List<HasWord> sentence : sentences) {
			List<TaggedWord> tagged = tagger.tagSentence(sentence);
			c.printWlnLog("CCCBYCProbe", "tagged: " + tagged);
			for (int i = 0; i < tagged.size(); i++) {
				TaggedWord w = tagged.get(i);
				String mention = null;
				if (w.tag().equals("NNP")
						|| w.tag().equals("NNS")
						|| (w.word().charAt(0) >= 'A' && w.word().charAt(0) <= 'Z'))
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
		c.printWlnLog("CCCBYCProbe", "Def mentions after extraction:"
				+ this.mentions);

	}

}
