import java.util.ArrayList;

/**
 * Created by adaml on 3/21/2017.
 */
public class Node {
    public ArrayList<Node> children;
    public Token token;
    public String string;
    public double value;

    public Node(Token token){
        this(token,0.0,"");
    }
    public Node(Token token, String string){
        this(token,0.0,string);
    }
    public Node (Token token, double value){
        this(token,value,"");
    }
    public Node (Token token, double value, String string){
        this.token = token;
        this.value = value;
        this.string = string;
    }
    @Override
    public String toString() {
        return "[" + token + ": " + string + ", " + value + "]";
    }
}
