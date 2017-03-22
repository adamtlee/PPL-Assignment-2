/**
 * Created by adaml on 3/21/2017.
 */
public class Statement extends Node{
    public Statement(){
        super(Token.STATEMENT);
        Node peek = CalcLang.peekToken();
        switch (peek.token){
            case NEWLINE:
                this.children.add(CalcLang.getToken());
                break;
            case INPUT:
                this.children.add(CalcLang.getToken());
                Node temp = CalcLang.getToken();
                if (temp.token == Token.STRING){
                    this.children.add(temp);
                }else {
                    throw new IllegalStateException("Expected STRING but encountered " + temp);
                }
                this.children.add(new Variable());
                break;
            case MSG:
                this.children.add(CalcLang.getToken());
                Node string = CalcLang.getToken();
                if (string.token == Token.STRING){
                    this.children.add(string);
                } else {
                    throw new IllegalStateException(" Expected STRING but encountered " + string);
                }
                break;
            case SHOW:
                this.children.add(CalcLang.getToken());
                this.children.add(new Expression());
                break;
            case VARIABLE:
                this.children.add(CalcLang.getToken());
                Node equals = CalcLang.getToken();
                if (equals.token == Token.EQUALS){
                    this.children.add(equals);
                } else {
                    throw new IllegalStateException("Expected EQUALS but encountered " + equals);
                }
                this.children.add(new Expression());
                break;
            default:
                throw new IllegalStateException("Expected EXPRESSION but encountered " + peek);
        }
    }

}
