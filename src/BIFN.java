/**
 * Created by adaml on 3/21/2017.
 */
public class BIFN extends Node {
    public BIFN(){
        super(Token.BIFN);
        Node temp = CalcLang.getToken();
        if (temp.token != this.token){
            throw new IllegalStateException("Expected a " + this.token + " but encountered: " + temp);
        }
    }
}
