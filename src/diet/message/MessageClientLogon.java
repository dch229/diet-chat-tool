package diet.message;
import diet.serverclientconsistencycheck.ServerClientConsistency;
import java.io.Serializable;

public class MessageClientLogon extends Message implements Serializable {

    String scc;

    public MessageClientLogon(String email, String username,String scc) {
        super(email, username);
        this.scc=scc;

    }

    public String getMessageClass(){
        return "MessageClientLogon";
    }

    public String getServerClientConsistencyID(){
        return scc;
    }
}
