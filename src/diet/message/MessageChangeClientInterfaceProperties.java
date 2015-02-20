package diet.message;

import java.io.Serializable;


public class MessageChangeClientInterfaceProperties extends Message implements Serializable {

    private int windowNumber;
   
    
    static public int disableRETURNANDSEND_enableTEXTENTRY = 1;
    static public int enableRETURNANDSEND_enableTEXTENTRY =2;
    static public int clearTextEntryField =3;
    static public int clearMainTextWindows =4;
    static public int clearAllWindowsExceptWindow0 = 14;
    
    static public int disableTextEntry = 5;
    static public int enableTextEntry =6;
    static public int disableTextPane =7;
    static public int enableTextPane =8;
    static public int disableScrolling =9;
    static public int enableScrolling =10;
    static public int changeScreenBackgroundColour =11;
    static public int changeTextStyles =12;
    static public int changeMazeWindow =13;
    
    
    
    private int interfaceproperties;
    private Object value = null;
    private Object value2 = null;
    
    public MessageChangeClientInterfaceProperties(int windowNumber, int interfaceproperties) {
        super("server", "server");
        this.interfaceproperties=interfaceproperties;
    }
    public MessageChangeClientInterfaceProperties(int windowNumber, int interfaceproperties, Object value) {
        super("server", "server");
        this.interfaceproperties=interfaceproperties;
        this.value=value;
    }
    public MessageChangeClientInterfaceProperties(int windowNumber, int interfaceproperties, Object value, Object value2) {
        super("server", "server");
        this.interfaceproperties=interfaceproperties;
        this.value=value;
        this.value2=value2;
    }


    
    public int getWindowNumber(){
        return windowNumber;
    }

    public int getInterfaceproperties() {
        return interfaceproperties;
    }

    //public Color getNewBackgroundColour()

    public Object getValue(){
        return value;
    }
    public Object getValue2(){
        return value2;
    }
}
