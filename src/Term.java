/**
 * Created by adaml on 3/21/2017.
 */
public class Term extends Node{
    public Term(){
        super(Token.TERM);
        this.children.add(new Factor());
        Node peek = CalcLang.peekToken();
        switch (peek.token){
            case MULTIPLY:
                this.children.add(CalcLang.getToken());
                this.children.add(new Factor());
                break;

            case DIVIDE:
                this.children.add(CalcLang.getToken());
                this.children.add(new Factor());
                break;
            default:
                //throw new IllegalStateException("Expected MULTIPLY or DIVIDE but encountered " + peek);
        }
    }
}
