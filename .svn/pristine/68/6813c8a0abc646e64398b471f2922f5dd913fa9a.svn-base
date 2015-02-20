/**
 * 
 */
package diet.server.CbyC.Sequence;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import diet.message.MessageCBYCTypingUnhinderedRequest;
import diet.server.Conversation;
import diet.server.Participant;
import diet.server.CbyC.DocChange;
import diet.server.CbyC.DocInsert;
import diet.server.CbyC.Sequences;
import diet.server.ConversationController.CCCBYCAbstractProbeCR;
import diet.server.ConversationController.CCCBYCDefaultController;
import diet.server.ConversationController.CCCBYCElicitSplitUtt;
import diet.server.ConversationController.CCCBYCElicitSplitUttGivenNew;
import diet.utils.Dictionary;
import diet.utils.POSTagRegex;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * @author Arash
 * 
 * 
 */

public class TriggerAfterDeterminerSequence extends Sequence {

	String turnSoFar;

	Dictionary dict;
	Map<String, String> misspellings;
	final long waitingTimeBeforeInterventionCheckPoint = 2500;

	private String condition="";

	public String getType()
	{
		return "TriggerAfterDeterminerSequence"+ (this.condition.isEmpty()?"":"("+this.condition+")"); 
	}

	public TriggerAfterDeterminerSequence(Sequences sS,
			CCCBYCDefaultController cC, String sender,
			MessageCBYCTypingUnhinderedRequest mCTUR, Dictionary d,
			Map<String, String> misspellings) {
		super(sS, cC, sender, mCTUR);
		this.dict = d;
		this.misspellings = misspellings;

		this.turnSoFar = "";

		this.condition = "";
		this.start();

	}

	public TriggerAfterDeterminerSequence(Sequences sS,
			CCCBYCDefaultController cC, String sender, Date timeStamp,
			String elementString, int elementStart, int elementFinish,
			Dictionary d, Map<String, String> misspellings) {
		super(sS, cC, sender, timeStamp, elementString, elementStart,
				elementFinish);
		this.dict = d;
		this.misspellings = misspellings;

		this.turnSoFar = "";
		this.condition = "";

		this.start();

	}

	public TriggerAfterDeterminerSequence(Sequences sS,
			CCCBYCDefaultController cC, String sender,
			MessageCBYCTypingUnhinderedRequest mCTUR, Dictionary d,
			Map<String, String> misspellings, String condition) {
		super(sS, cC, sender, mCTUR);
		this.dict = d;

		this.turnSoFar = "";

		this.condition = condition;
		this.misspellings = misspellings;
		this.start();

	}

	public TriggerAfterDeterminerSequence(Sequences sS,
			CCCBYCDefaultController cC, String sender, Date timeStamp,
			String elementString, int elementStart, int elementFinish,
			Dictionary dict, Map<String, String> misspellings, String condition) {
		super(sS, cC, sender, timeStamp, elementString, elementStart,
				elementFinish);
		this.dict = dict;
		this.misspellings = misspellings;		
		this.turnSoFar = "";
		this.condition = condition;
		this.start();

	}

	public synchronized void setInputClosedEditOfOwnTurn() {
		this.setInputClosed();
		notify();
	}

	public synchronized void setInputClosedSpeakerChange() {
		this.setInputClosed();
		notify();
	}

	long lastDocChangeTimeStamp = new Date().getTime();

	public synchronized Sequence addDocChange(DocChange dc) {
		lastDocChangeTimeStamp = new Date().getTime();
		if (!dc.sender.equalsIgnoreCase(sender)) {
			if (dc instanceof DocInsert) {
				DocInsert di = (DocInsert) dc;
				Conversation
						.printWSln("ERROR",
								"New DocChange from: " + dc.getSender() + ", "
										+ di.str);
			}
			Conversation.printWSln("ERROR", "Existing Sequence: " + sender
					+ ", " + this.getFinalText());
			Conversation.printWSln("ERROR",
					"______________________________________________________");
		}
		Vector v = new Vector();
		this.docChangesBySender.addElement(dc);

		if (dc instanceof DocInsert) {
			DocInsert di = (DocInsert) dc;
			String insert = di.getStr();
			// if
			// (insert.equals(" ")||insert.equals("!")||insert.equals("?")||insert.e)
			this.turnSoFar += insert;
			notify();
		} else
			Conversation
					.printWSln("ERROR",
							"DocRemove in POSTagFilterSequence. Edit and deletes should be disabled.");

		Participant senderP = cC.getC().getParticipants()
				.findParticipantWithUsername(dc.sender);
		Vector recipients = cC
				.getC()
				.getParticipants()
				.getAllOtherParticipants(
						cC.getC().getParticipants()
								.findParticipantWithUsername(dc.getSender()));
		for (int i = 0; i < recipients.size(); i++) {
			Participant p = (Participant) recipients.elementAt(i);
			DocChange dcCopy = dc.returnCopy();
			dcCopy.recipient = p.getUsername();
			if (dc instanceof DocInsert) {
				DocInsert di = (DocInsert) dcCopy;
				int unique = sS.getStyleManager().getUniqueIntForRecipient(p,
						senderP);
				di.a = "n" + unique;
			}
			v.addElement(dcCopy);
		}
		fb.enqueue(v);

		return null;
	}

	public boolean isInputFinished() {
		if (fb.isInputCompleted())
			return true;
		else
			return false;

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
	protected String fixSpelling() {
		return fixSpelling(this.turnSoFar);
	}

	

	boolean interventionTriggered = false;
	final String[] defDeterminers = { "the", "that", "this", "your", "my",
			"her", "his", "their", "our" };

	List<String> defDet = Arrays.asList(defDeterminers);
	public void run() {
		while (!this.isInputFinished()) {

			long beforeWait = new Date().getTime();
			synchronized (this) {
				try {
					wait(this.waitingTimeBeforeInterventionCheckPoint);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (this.turnSoFar == null || this.turnSoFar.equals("")) {
				continue;
			}
			if (this.isInputFinished())
				break;

			long afterWait = new Date().getTime();
			if (afterWait - beforeWait >= this.waitingTimeBeforeInterventionCheckPoint
					|| turnSoFar.endsWith(" ")) {
				String turnCorrected = this.fixSpelling();
				String[] words=turnCorrected.split("\\s+");
				String lastWord=words[words.length-1];
				if (defDet.contains(lastWord)) {

					if (this.cC instanceof CCCBYCElicitSplitUttGivenNew) {
						CCCBYCElicitSplitUttGivenNew cont = (CCCBYCElicitSplitUttGivenNew) this.cC;
						int turnLengthSoFar = getFinalText().split("\\s+").length;
						if (turnLengthSoFar < cont.minimumTurnLengthBeforeIntervention
								.getValue()) {
							Conversation.printWSln("POSTagFilterSequence",
									"Turn too short");
							continue;
						}
						if (cont.attemptIntervention(this.sender, lastWord)) {
							
							this.setInputClosed();
							break;
						} else {

						}

					} else {
						Conversation
								.printWSln("ERROR",
										"Wrong conversation controller class. Should be CCCBYCElicitSplitUtt");
					}
				}

			}

		}
	}

	protected static String findFrag(String group3, String group1,
			List<TaggedWord> tagged) {
		if (group3 == null || group3.equals(""))
			return null;

		String[] split3 = group3.trim().split(" ");
		int group1Length;
		if (group1 == null || group1.equals(""))
			group1Length = 0;
		else
			group1Length = group1.trim().split(" ").length;

		int group3Length = split3.length;
		List<TaggedWord> fragSubList = tagged.subList(group1Length,
				group1Length + group3Length);
		String result = "";
		for (TaggedWord w : fragSubList)
			result += (w.word() + " ");
		return result.trim();

	}

	


	protected String getTagSequence(ArrayList<TaggedWord> lastSent) {
		String result = "";
		for (TaggedWord word : lastSent) {
			result += word.tag() + " ";
		}
		return result.trim();
	}

	public synchronized String getTurnSofar() {
		return this.turnSoFar;
	}

	public void setCondition(String string) {
		this.condition = string;

	}

}
