package diet.server.ConversationController;

import diet.parameters.ExperimentSettings;
import diet.server.Conversation;

public class CCCBYCDefaultLowFloorTimeOut extends CCCBYCDefaultController {
	
    public static boolean showcCONGUI(){
        return false;
    } 
    
    
	public void initialize(Conversation c, ExperimentSettings expS) {
		super.initialize(c, expS);
		super.setIsTypingTimeOut(200);
                super.setProcessLoopSleepTime(80);

	}
	
	
	
}
