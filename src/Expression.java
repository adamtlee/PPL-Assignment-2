/**
 * Created by adaml on 3/21/2017.
 */
public class Expression extends Node {
    public Expression(){
        super(Token.EXPRESSION);
        this.children.add(new Term());
        Node peek = CalcLang.peekToken();
        switch(peek.token){
            case PLUS:
                this.children.add(CalcLang.getToken());
                this.children.add(new Term());
                break;
            case MINUS:
                this.children.add(CalcLang.getToken());
                this.children.add(new Term());
                break;
            default:
                //throw new IllegalStateException("Expected PLUS or MINUS but encountered " + peek);
        }
    }
}
