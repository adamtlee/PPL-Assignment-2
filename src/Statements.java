/**
 * Created by adaml on 3/21/2017.
 */
public class Statements extends Node{
    public Statements(){
        super(Token.STATEMENTS);
        this.children.add(new Statement());
        Node peek = CalcLang.peekToken();
        switch (peek.token){
            case VARIABLE:
            case NEWLINE:
            case INPUT:
            case MSG:
            case SHOW:
                this.children.add(new Statements());
                break;
            case EOF:
                break;
            default:
                throw new IllegalStateException("Expected STATEMENTS but encounter " + peek );

        }
    }
}
