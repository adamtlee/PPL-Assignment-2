import java.util.Scanner;
import java.util.LinkedList;

/**
 * Created by adaml on 3/21/2017.
 */
public class CalcLang {
    private static Lexer lex;
    public static void main(String[] args) {
        System.out.print("Enter file name: ");
        Scanner keys = new Scanner( System.in );
        String name = keys.nextLine();
        try {
            lex = new Lexer(name);
            System.out.println(lex.tokens);
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
    public static Node getToken() {
        return lex.getToken();
    }
    public static Node peekToken() {
        return lex.peekToken();
    }
    public static void putBackToken(Node token) {
        lex.putBackToken(token);
    }
}
