/**
 * Created by adaml on 3/21/2017.
 */
public class Factor extends Node {
    public Factor(){
        super(Token.FACTOR);
        Node peek = CalcLang.peekToken();
        switch (peek.token){
            case NUMBER:
                this.children.add(new Number());
                break;
            case VARIABLE:
                this.children.add(new Variable());
                break;
            case LPAREN:
                this.children.add(CalcLang.getToken());
                this.children.add(new Expression());
                Node temp = CalcLang.getToken();
                if(temp.token == Token.RPAREN){
                    this.children.add(temp);
                } else {
                    throw new IllegalStateException("expected RPAREN, encountered  " + temp);
                }
                break;
            case MINUS:
                this.children.add(CalcLang.getToken());
                this.children.add(new Factor());
                break;
            case BIFN:
                this.children.add(CalcLang.getToken());
                Node paren = CalcLang.getToken();
                if(paren.token == Token.LPAREN){
                    this.children.add(paren);
                } else {
                    throw new IllegalStateException("expected LPAREN, encountered " + paren);
                }

                this.children.add(new Expression());
                paren = CalcLang.getToken();
                if(paren.token == Token.RPAREN){
                    this.children.add(paren);
                } else {
                    throw new IllegalStateException("expected RPAREN, encountered " + paren);
                }
                break;
            default:
                //throw new IllegalStateException("Expected FACTOR, encountered " + peek);
        }
    }
}
