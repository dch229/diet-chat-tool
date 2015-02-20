package diet.server.CbyC.Sequence;

import java.io.StringReader;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import diet.server.Conversation;
import diet.server.Participant;
import diet.server.CbyC.DocChange;
import diet.server.CbyC.DocInsert;
import diet.server.CbyC.Sequences;
import diet.server.CbyC.Sequence.FlowControl.FlowControlDelay;
import diet.server.CbyC.Sequence.FlowControl.FlowControlDoSpoofResponsivityToTypingActivityOfParticipant;
import diet.server.ConversationController.CCCBYCAbstractProbeCR;
import diet.server.ConversationController.CCCBYCDefaultController;
import diet.server.ConversationController.CCCBYCElicitSplitUttGivenNew;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class BufferredPOSTagFilterTriggeringSpoofTypingRes extends
		BufferedSequenceWithSpoofTypingResponsivity {

	String soFar = "";
	Map<String, String> misspellings;
	MaxentTagger tagger;
	String condition="";

	public BufferredPOSTagFilterTriggeringSpoofTypingRes(Sequences sS,
			CCCBYCDefaultController cC, String sender, String initialDet,
			Map<String, String> misspell, MaxentTagger tagger) {
		super(sS, cC, sender);
		this.tagger = tagger;
		this.misspellings = misspell;
		this.soFar = initialDet+" ";

		this.start();
	}

	public void setCondition(String condition)
	{
		this.condition=condition;
	}
	
	public String getType()
	{
		return "Bufferred-SecondHalf"+ (this.condition.isEmpty()?"(aborted)":"("+this.condition+")"); 
	}
	public synchronized Sequence addDocChange(DocChange dc) {
		if (buffering) {
			if (this.spoofTyping == null)
				this.spoofTyping = new FlowControlDoSpoofResponsivityToTypingActivityOfParticipant(
						this, this.sender, this.getSS().getcC()
								.getIsTypingTimeOut());

			this.spoofTyping.sendFakeIsTypingMessage();

			if (this.timeOfLastDocChange > 0) {
				fb.enqueue(new FlowControlDelay(this, new Date().getTime()
						- this.timeOfLastDocChange));
				timeOfLastDocChange = new Date().getTime();
			} else
				timeOfLastDocChange = new Date().getTime();
		}
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
			this.soFar += insert;
			notify();
		} else
			Conversation
					.printWSln(
							"ERROR",
							"DocRemove in BufferedPOSTagFilterTriggering. Edit and deletes should be disabled.");

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

	final long waitingTimeBeforeInterventionCheckPoint = 2500;
	boolean aborted = false;
	private String initialDeterminer;

	public void run() {
		while (!this.isInputFinished()) {
			synchronized (this) {
				try {
					
					
					long beforeWait = new Date().getTime();
					wait(this.waitingTimeBeforeInterventionCheckPoint);
					
					if (this.isInputFinished()||aborted)
						break;
					
					long afterWait = new Date().getTime();

					if (afterWait - beforeWait >= this.waitingTimeBeforeInterventionCheckPoint
							|| soFar.endsWith(" ")) {

						CCCBYCElicitSplitUttGivenNew cc = (CCCBYCElicitSplitUttGivenNew) cC;
						String turnSpellingCorrected = this.fixSpelling(soFar);
						String[] words = turnSpellingCorrected.split("\\s+");
						int length = words.length;
						List<List<HasWord>> sentences = MaxentTagger
								.tokenizeText(new StringReader(
										turnSpellingCorrected));
						List<TaggedWord> tagged = tagger.tagSentence(sentences
								.get(sentences.size() - 1));
						Conversation.printWSln("BufferedSecondHalf", "Tagged:"+tagged);
						TaggedWord last = tagged.get(tagged.size() - 1);
						if (last.tag().equals("NN") || last.tag().equals("NNS")) {

							if (!cc.intervene(soFar))
							{								
								cc.abortIntervention();
							}
							break;
							
						} else if (length > 2) {
							//waiting for noun only for the length of two words.
							
							cc.abortIntervention();
							break;
						}
						
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
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

	public synchronized void abort() {
		aborted=true;
		
	}

}
