package c15p6;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * PersonnelTree
 * 
 * A (node in a) tree representing an employee at a company,
 * with his/her conviviality and place in the corporate 
 * hierarchy.
 * 
 * @author Thomas VanDrunen
 * CSCI 445
 * Sept 24, 2018
 */
public class PersonnelTree {

    /**
     * Counter used to generate unique identifiers
     */
    private static int idgen = 0;

    /**
     * A unique identifier for this person
     */
    private int id;

    /**
     * The person's conviviality rating
     */
    private int conviviality;

    /**
     * A link to one of this person's peers
     */
    private PersonnelTree peer;

    /**
     * A link to one of this person's subordinates
     */
    private PersonnelTree subordinate;

    /**
     * A place to store auxiliary information
     */
    public Object aux;

    /**
     * Plain constructor
     * @param conviviality
     */
    private PersonnelTree(int conviviality) {
        id = idgen++;
        this.conviviality = conviviality;
    }

    /**
     * What is this person's conviviality rating?
     * @return This person's conviviality
     */
    public int conviviality() { return conviviality; }

    /**
     * Does this person have any subordinates?
     * @return true if this person has subordinates in the company, false otherwise
     */
    public boolean hasSubordinates() { return subordinate != null; }

    /**
     * Iterate over this person's <i>immediate</i> subordinates
     * @return An iterable to iterate over this person's subordinates
     */
    public Iterable<PersonnelTree> subordinates() {
        return new Iterable<PersonnelTree>() {
            public Iterator<PersonnelTree> iterator() {
                return new Iterator<PersonnelTree>() {
                    PersonnelTree current = subordinate;
                    public boolean hasNext() {
                        return current != null;
                    }
                    public PersonnelTree next() {
                        if (current == null)
                            throw new NoSuchElementException();
                        else {
                            PersonnelTree toReturn = current;
                            current = current.peer;
                            return toReturn;
                        }
                    }
                    
                };
            }
        };
    }
    
    public String toString() {
        String toReturn =  "(" + conviviality;
        for (PersonnelTree sub : subordinates())
            toReturn += sub;
        toReturn += ")";
        return toReturn;
    }

    /**
     * Factory method to make a forest of personnel trees from
     * a string description.
     * @param description A fully-parenthesized breadth-first representaiton
     * of the company, with each person represented as a conviviality rating.
     * @return An array of personnel trees
     */
    // There has to be a better way...
    public static PersonnelTree[] factory(String description) {
        int people = 0;
        for (char c : description.toCharArray())
            if (c == '(') people++;
        PersonnelTree[] allPersonnel = new PersonnelTree[people];
        int i = 0;
            
        StringTokenizer tokey = new StringTokenizer(description, "()", true);
        String currentToken = tokey.nextToken();
        Stack<PersonnelTree> peers = new Stack<PersonnelTree>();
        PersonnelTree superior = null;
        while (currentToken != null) {
            assert currentToken.length() == 1 && currentToken.charAt(0) == '(';
            int conviviality = Integer.parseInt(tokey.nextToken());
            PersonnelTree newGuy = new PersonnelTree(conviviality);
            allPersonnel[i++] = newGuy;
            if (superior != null) 
                superior.subordinate = newGuy;
            else if (! peers.isEmpty()) {
                PersonnelTree peer = peers.pop();
                peer.peer = newGuy;
            }
            superior = newGuy;
            peers.push(newGuy);
            currentToken = tokey.nextToken();
            if (currentToken.charAt(0) == ')') {
                superior = null;
                for (;;) {
                    if (tokey.hasMoreTokens()) {
                        currentToken = tokey.nextToken();
                        if (currentToken.charAt(0) != ')')
                            break;
                        else  
                            peers.pop();
                        
                    }
                    else {
                        currentToken = null;
                        break;
                    }
                }
            }
        }
        
        assert i == allPersonnel.length;
        return allPersonnel;
    }
    
    public static void main(String[] args) {
        PersonnelTree[] all = factory("(18(6(2)(3))(5(8)(1(10)(12))))");
        for (PersonnelTree p : all)
            System.out.println(p);
    }
}
