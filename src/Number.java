/**
 * Created by adaml on 3/21/2017.
 */
public class Number extends Node{
    public Number(){
        super(Token.NUMBER);
        Node temp = CalcLang.getToken();
        if (temp.token != Token.NUMBER) throw new IllegalStateException("Expected NUMBER, encountered: " + temp);
        this.value = temp.value;
    }
}
