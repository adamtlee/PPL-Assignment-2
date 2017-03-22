/**
 * Created by adaml on 3/21/2017.
 */
public class Variable extends Node {
    public Variable(){
        super(Token.VARIABLE);
        Node temp = CalcLang.getToken();
        if (temp.token != this.token){
            throw new IllegalStateException("Expected a " + this.token + " but encountered: " + temp);
        }
        this.string = temp.string;
    }
}
