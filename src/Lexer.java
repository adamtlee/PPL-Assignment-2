/*  an instance of this class provides methods that produce a
    sequence of tokens following some Finite State Automata,
    with capability to put back tokens
*/
import java.util.*;
import java.io.*;

public class Lexer
{
    private Scanner in;
    private String line = null;
    private int lineIndex = 0;
    private State state;
    public LinkedList<Node> tokens;
    private String readChars = "";
    private static ArrayList<String> functions;
    // construct a Lexer ready to produce tokens from a file
    public Lexer( String fileName )
    {
        try{
            functions = new ArrayList<String>();
            for (String function : new String[] {"sin", "cos", "abs", "sqrt", "radians", "degrees"}) {
                functions.add(function);
            }
            in = new Scanner(new File(fileName));
            tokens = new LinkedList<Node>();
            state = State.START;
            readChars = "";
            getTokens();
        }
        catch(Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
        }
    }// constructor

    // produce the next token

    private void getTokens() {
        Node token = new Node(Token.EOF);
        do {
            token = readToken();
            tokens.add(token);
        }while(token.token != Token.EOF);
    }
    public char getSymbol() {
        if (line == null || (lineIndex >= line.length() && in.hasNextLine())) {
            line = in.nextLine() + '\n';
            lineIndex = 0;
        }
        if (line != null && lineIndex <+ line.length()) return line.charAt(lineIndex++);
        return 26;
    }

    private static Type getCategory(char c){
        if (Character.isAlphabetic(c)) return Type.LETTER;
        if (Character.isDigit(c)) return Type.DIGIT;
        if (Character.isWhitespace(c)) return Type.WHITESPACE;
        switch (c) {
            case '+':
            case '-':
            case '/':
            case '*':
            case '"':
            case '(':
            case ')':
            case '=':
                return Type.OPERATOR;
            default:
                return Type.ERROR;
        }
    }
    private Node readToken() throws IllegalStateException {
        while (in.hasNextLine() || lineIndex < line.length()) {
            char symbol = getSymbol();
            //System.out.println("(" + lineIndex + " <= " + line.length() + ") " + state + ": " + symbol);
            switch (state) {
                case START:
                    switch (getCategory(symbol)) {
                        case WHITESPACE:
                            break;
                        case LETTER:
                            state = State.VARIABLE;
                            readChars += symbol;
                            break;
                        case OPERATOR:
                            switch (symbol) {
                                case '+':
                                    readChars = "";
                                    return new Node(Token.PLUS);
                                case '-':
                                    readChars = "";
                                    return new Node(Token.MINUS);
                                case '/':
                                    readChars = "";
                                    return new Node(Token.DIVIDE);
                                case '*':
                                    readChars = "";
                                    return new Node(Token.MULTIPLY);
                                case '(':
                                    readChars = "";
                                    return new Node(Token.LPAREN);
                                case ')':
                                    readChars = "";
                                    return new Node(Token.RPAREN);
                                case '=':
                                    readChars = "";
                                    return new Node(Token.EQUALS);
                                case '"':
                                    readChars = "";
                                    state = state.STRING;
                                    break;
                                default:
                                    throw new IllegalStateException("Character not recognized: " + symbol);
                            }
                            break;
                        case DIGIT:
                            readChars += symbol;
                            state = State.INTEGER;
                            break;
                        default:
                            break;
                    }
                    break;
                case VARIABLE:
                    switch (getCategory(symbol)) {
                        case LETTER:
                        case DIGIT:
                            readChars += symbol;
                            break;
                        default:
                            lineIndex--;
                            state = State.START;
                            Node temp = new Node(Token.VARIABLE);
                            if (functions.contains(readChars.toLowerCase())) {
                                temp = new Node(Token.BIFN);
                            } else {
                                switch (readChars.toLowerCase()) {
                                    case "show":
                                        temp = new Node(Token.SHOW);
                                        break;
                                    case "input":
                                        temp = new Node(Token.INPUT);
                                        break;
                                    case "msg":
                                        temp = new Node(Token.MSG);
                                        break;
                                    case "newline":
                                        temp = new Node(Token.NEWLINE);
                                        break;
                                    default:
                                }
                            }
                            temp.string = readChars;
                            readChars = "";
                            state = State.START;
                            return temp;
                    }
                    break;
                case INTEGER:
                    switch (getCategory(symbol)){
                        case DIGIT:
                            readChars += symbol;
                            break;
                        case OPERATOR:
                            if (symbol == '.'){
                                state = State.DECIMAL;
                                readChars += symbol;
                                break;
                            }
                        default:
                            lineIndex--;
                            Node temp = new Node(Token.NUMBER);
                            temp.value = Integer.parseInt(readChars);
                            readChars = "";
                            state = State.START;
                            return temp;
                    }
                    break;
                case DECIMAL:
                    switch (getCategory(symbol)){
                        case DIGIT:
                            readChars += symbol;
                            break;
                        default:
                            lineIndex--;
                            Node temp = new Node(Token.NUMBER);
                            temp.value = Double.parseDouble(readChars);
                            readChars = "";
                            state = State.START;
                            return temp;
                    }
                    break;
                case STRING:
                    switch (getCategory(symbol)){
                        case OPERATOR:
                            if (symbol == '"'){
                                Node temp = new Node(Token.STRING);
                                temp.string = readChars;
                                readChars = "";
                                state = State.START;
                                return temp;
                            }
                        default:
                            readChars += symbol;
                            break;
                    }
                    break;
                default:
                    throw new IllegalStateException("Encountered Invalid State: " + state);
            }
        }
        return new Node(Token.EOF);
    }
    public Node getToken() {
        return tokens.remove();
    }
    public Node peekToken() {
        return tokens.peek();
    }
    public void putBackToken(Node token) {
        tokens.offerFirst(token);
    }
}