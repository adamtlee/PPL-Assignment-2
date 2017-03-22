import java.util.ArrayList;
import java.util.Scanner;
import java.util.LinkedList;

/**
 * Created by adaml on 3/21/2017.
 */
public class CalcLang {
    private static Scanner keyboard;
    private static Lexer lex;
    private static ArrayList<String> variables;
    private static ArrayList<Double> variable;
    public static void main(String[] args) {
        System.out.print("Enter file name: ");
        Scanner keys = new Scanner( System.in );
        String name = keys.nextLine();
        try {
            keyboard = new Scanner(System.in);
            variables = new ArrayList<String>();
            variable = new ArrayList<Double>();
            lex = new Lexer(name);
            System.out.println(lex.tokens);
            Node head = new Statements();
            System.out.println(head);
            execute(head);
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
    public static Node getToken() {
        //return lex.getToken();
        Node temp = lex.getToken();
        if (temp != null){
            return temp;
        }
        return new Node(Token.EOF);
    }
    public static Node peekToken() {
        //return lex.peekToken();
        Node temp = lex.peekToken();
        if (temp != null){
            return temp;
        }
        return  new Node(Token.EOF);
    }
    public static void putBackToken(Node token) {
        lex.putBackToken(token);
    }

    public static double execute(Node node) throws Exception {
        System.out.println(node + "; " + variables + ", " + variable);
        switch(node.token){
            case STATEMENTS:
                execute(node.children.get(0));
                if (node.children.size() > 1){
                    execute(node.children.get(1));
                }
                break;
            case STATEMENT:
                switch (node.children.get(0).token){
                    case VARIABLE:
                        Node var = node.children.get(0);
                        if (variables.contains(var.string)){
                            variable.set(variables.indexOf(var.string), new Double(execute(node.children.get(2))));
                        } else {
                            variables.add(var.string);
                            variable.add(execute(node.children.get(2)));
                        }
                        break;
                    case SHOW:
                    case MSG:
                        System.out.print(execute(node.children.get(1)));
                        break;
                    case INPUT:
                        System.out.print(execute(node.children.get(1)));
                        Node var2 = node.children.get(2);
                        if (variables.contains(var2.string)){
                            variable.set(variables.indexOf(var2.string), Double.parseDouble(keyboard.nextLine()));
                        } else {
                            variables.add(var2.string);
                            variable.add(Double.parseDouble(keyboard.nextLine()));
                        }
                        break;
                    case NEWLINE:
                        System.out.println();
                        break;
                    default:
                }
                break;
            case EXPRESSION:
                if(node.children.size() > 1){
                    if(node.children.get(1).token == Token.PLUS){
                        return execute(node.children.get(0)) + execute(node.children.get(2));
                    }
                    return execute(node.children.get(0)) - execute(node.children.get(2));
                }
                return execute(node.children.get(0));
            case TERM:
                if(node.children.size() > 1){
                    if(node.children.get(1).token == Token.MULTIPLY){
                        return execute(node.children.get(0)) * execute(node.children.get(2));
                    }
                    return execute(node.children.get(0)) / execute(node.children.get(2));
                }
                return execute(node.children.get(0));
            case FACTOR:
                Node first = node.children.get(0);
                switch(first.token){
                    case NUMBER:
                        return first.value;
                    case VARIABLE:
                        if(variables.contains(first.string)){
                            return variable.get(variables.indexOf(first.string));
                        }
                        variables.add(first.string);
                        variable.add(0.0);
                        return 0.0;
                    case LPAREN:
                        return execute(node.children.get(1));
                    case MINUS:
                        return -1.0 * execute(node.children.get(1));
                    case BIFN:
                        switch(first.string){
                            case "abs":
                                return Math.abs(execute(node.children.get(2)));
                            case "sqrt":
                                return Math.sqrt(execute(node.children.get(2)));
                            case "sin":
                                return Math.sin(execute(node.children.get(2)));
                            case "cos":
                                return Math.cos(execute(node.children.get(2)));
                            case "radians":
                                return Math.toRadians(execute(node.children.get(2)));
                            case "degrees":
                                return Math.toDegrees(execute(node.children.get(2)));
                            default:
                                return execute(node.children.get(2));
                        }
                }

        }
        return 0.0;
    }
}
