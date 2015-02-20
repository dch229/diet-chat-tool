/**
 * 
 */
package diet.server.ConversationController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.Vector;

import diet.client.WYSIWYGDocumentWithEnforcedTurntaking;
import diet.message.MessageCBYCChangeTurntakingStatusConfirm;
import diet.message.MessageCBYCDocChangeFromClient;
import diet.message.MessageCBYCTypingUnhinderedRequest;
import diet.message.MessageKeypressed;
import diet.parameters.ExperimentSettings;
import diet.parameters.Parameter;
import diet.server.Conversation;
import diet.server.Participant;
import diet.server.CbyC.DocChange;
import diet.server.CbyC.Sequences;
import diet.server.CbyC.Sequence.EditSequence;
import diet.server.CbyC.Sequence.LimitedRecipientsSequence;
import diet.server.CbyC.Sequence.POSTagFilterSequence;
import diet.server.CbyC.Sequence.RecordedSequenceFromApparentOrigin;
import diet.server.CbyC.Sequence.Sequence;
import diet.utils.Dictionary;
import diet.utils.POSTagRegex;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * @author Arash
 * 
 */
public class CCCBYCProbeCRWithinVSBetweenConstituent extends
		CCCBYCAbstractProbeCR {

    public static boolean showcCONGUI(){
        return false;
    } 
    
    
	String[] conditions = { "withinConst", "constBoundary" };
	
	Map<String, Vector<POSTagRegex>> regexes = new HashMap<String, Vector<POSTagRegex>>();
	
	
	
	File withinConstRegexFile = new File(System.getProperty("user.dir")+File.separator+"experimentresources"
			+ File.separator + "fragmentFilters" + File.separator
			+ "withinConstRegexes2.txt");
	File constBoundaryRegexFile = new File(System.getProperty("user.dir")+File.separator+"experimentresources"
			+ File.separator + "fragmentFilters" + File.separator
			+ "constBoundaryRegexes.txt");
	File mentionRegexFile=new File(System.getProperty("user.dir")+File.separator+"experimentresources"
			+ File.separator + "fragmentFilters" + File.separator
			+ "mentionRegexFile.txt");
	
	
	String condition = "constBoundary";
	
	
	

	public void initialize(Conversation c, ExperimentSettings expS) {
		super.initialize(c, expS);
		
		Parameter condition = this.expSettings.getParameter("Condition");
		if (condition != null)
			this.condition = (String) condition.getValue();

		if (this.condition.equalsIgnoreCase("random"))
			this.probeCondition = conditions[r.nextInt(conditions.length)];
		else
			this.probeCondition = this.condition;
		
		
		
		try {

			
			Conversation.printWSln("Main", "Loading Regexes");
			this.loadRegexes();
			Conversation.printWSln("Main", "Loaded Regexes");
			

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	
	private void loadRegexes() throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(
				this.withinConstRegexFile));
		Vector<POSTagRegex> withinConst = new Vector<POSTagRegex>();
		String regex;
		while ((regex = reader.readLine()) != null) {
			if (regex.startsWith("%"))
				continue;
			String[] split = regex.split(" ");
			withinConst.add(new POSTagRegex(split[2], split[0], split[1]));
		}
		reader.close();
		regexes.put("withinConst", withinConst);
		Vector<POSTagRegex> constBoundary = new Vector<POSTagRegex>();
		reader = new BufferedReader(new FileReader(this.constBoundaryRegexFile));
		while ((regex = reader.readLine()) != null) {
			if (regex.startsWith("%"))
				continue;
			String[] split = regex.split(" ");
			constBoundary.add(new POSTagRegex(split[2], split[0], split[1]));
		}
		reader.close();
		regexes.put("constBoundary", constBoundary);

	}

	// ___________________________________________________________	

	private Participant chooseRandomParticipant(Vector participants) {

		Random r = new Random(new Date().getTime());

		int pIndex = r.nextInt(participants.size());
		Participant p = (Participant) participants.elementAt(pIndex);
		return p;
	}

	

	

	
	
	

	public Sequence getNextSequence_Speaker_Change(Sequence prior,
			int sequenceNo, Sequences ss, Participant p,
			MessageCBYCTypingUnhinderedRequest mCTUR) {

		if (this.finishTimeOfFragSending > 0 && fragAppOrigin != null) {
			System.out.println("Returning limited rec sequence");
			Vector v = getC().getParticipants().getAllOtherParticipants(
					fragAppOrigin);
			Vector v2 = (Vector) v.clone();
			v2.remove(p);
			receivingResponse = true;
			String condition=probeCondition+(mentionIntervention?"/"+mentionCondition:"");
			return new LimitedRecipientsSequence(ss, this, p.getUsername(), v2,
					p, condition);
		} else {
			if (this.condition.equalsIgnoreCase("random"))
				this.probeCondition = conditions[r.nextInt(conditions.length)];
			else
				this.probeCondition = condition;

			c.printWlnLog("CCCBYCProbe", "Set condition to:"
					+ this.probeCondition);
			this.speakerChangesSinceLastIntervention++;
			if (prior!=null)
				extractDefiniteFirstMentions(prior.getFinalText(), prior.getSender());
			
			return new POSTagFilterSequence(ss, this, p.getUsername(), mCTUR,
					tagger, this.regexes.get(this.probeCondition), this.dict,
					this.misspellings, this.probeCondition);
		}
	}

	public Sequence getNextSequence_Edit_By_Different_Speaker(Sequence prior,
			int sequenceNo, Sequences ss, Participant p,
			MessageCBYCTypingUnhinderedRequest mCTUR) {
		c
				.printWlnLog(
						"ERROR",
						"getNextSequence_Edit_By_Different_Speaker: This should not happen. Edits should be disabled");
		return null;
		//return new EditSequence(sS, this, p.getUsername(), mCTUR);
	}

	public Sequence getNextSequence_NewLine_By_Same_Speaker(Sequence prior,
			int sequenceNo, Sequences sS, String sender,
			MessageCBYCDocChangeFromClient mCDC) {
		Conversation.printWSlnLog("CCCbyCProbe",
				"getNextSequence_NewLine_By_Same_Speaker");
		Participant p = this.getC().getParticipants()
				.findParticipantWithUsername(sender);
		if (this.finishTimeOfFragSending > 0 && fragAppOrigin != null) {
			System.out.println("Returning limited rec sequence");
			Vector v = getC().getParticipants().getAllOtherParticipants(
					fragAppOrigin);
			Vector v2 = (Vector) v.clone();
			v2.remove(p);
			receivingResponse = true;
			String condition=probeCondition+(mentionIntervention?"/"+mentionCondition:"");
			return new LimitedRecipientsSequence(sS, this, p.getUsername(), v2,
					p, condition);
		} else {

			if (this.condition.equalsIgnoreCase("random"))
				this.probeCondition = conditions[r.nextInt(conditions.length)];
			else
				this.probeCondition = condition;
			c.printWlnLog("CCCBYCProbe", "Set condition to:"
					+ this.probeCondition);
			DocChange dc = mCDC.getDocChange();
			if (prior!=null)
				extractDefiniteFirstMentions(prior.getFinalText(), prior.getSender());
			return new POSTagFilterSequence(sS, this, sender, mCDC
					.getTimeOnServerOfReceipt(), dc.elementString, dc.elementStart,
					dc.elementFinish, this.tagger, this.regexes
							.get(this.probeCondition), this.dict,
					this.misspellings, this.probeCondition);
		}
	}

	public Sequence getNextSequence_Edit_By_Same_Speaker(Sequence prior,
			int sequenceNo, Sequences sS, String sender,
			MessageCBYCDocChangeFromClient mCDC) {
		
		c.printWlnLog(
						"ERROR",
						"getNextSequence_Edit_By_Same_Speaker: This should not happen. Edits should be disabled");
		return null;
//		
//		DocChange dc = mCDC.getDocChange();
//		EditSequence seq2 = new EditSequence(sS, this, sender, mCDC
//				.getTimeOnServerOfReceipt(), dc.elementString, dc.elementStart,
//				dc.elementFinish);
//		return seq2;
	}

	public void processKeyPress(Participant sender, MessageKeypressed mkp) {

	}

	@Override
	protected Participant chooseFragApparentOrigin(String antecedentOwner) {
		Participant antecedentOwnerP = getC().getParticipants()
		.findParticipantWithUsername(antecedentOwner);
		return this.chooseRandomParticipant(getC()
				.getParticipants().getAllOtherParticipants(antecedentOwnerP));
		
	}
	
	
	
	

}
