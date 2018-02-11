import java.math.BigInteger;

/**
 * The class DoubleNode holds two pointers and a BigInteger. It is used to represent a single node on a DoublyLinkedList object.
 */
public class DoubleNode {

	/**
     *Private instance variables to represent the references to the previous and next DoubleNodes and the character stored.
     */
    private DoubleNode prevNode;
    private DoubleNode nextNode;
    private BigInteger NodeInt;
    
    /**
     * Constructor with no arguments. Assign null values to previous, next and the null character to c.
     */
    DoubleNode(){
        prevNode = null;
        nextNode = null;
        NodeInt = BigInteger.valueOf(0);
    }

    /**
     * Constructor
     * Parameters:
     * p - is a pointer to a previous node.
     * n - is a pointer to a next node.
     * Int1 - is a BigInteger for this node.
     */
    DoubleNode(DoubleNode p, BigInteger Int1, DoubleNode n){
        prevNode = p;
        nextNode = n;
        NodeInt = Int1;
    }

    /**
     * Method to return a pointer to the previous node or null if none exists.
     * Post-Condition: returns reference to previous DoubleNode.
     * Theta(1), constant time complexity.
     */
    public DoubleNode getPrev(){
        return prevNode;
    }

    /**
     * Method to update the prevNode instance variable for reference to previous Node.
     * Pre-Condition: requires valid reference to DoubleNode as argument.
     * Post-Condition: DoubleNode's prevNode instance variable is updated.
     * Theta(1), constant time complexity.
     */
    public void setPrev(DoubleNode prev){
        prevNode = prev;
    }

    /**
     * Method to return a pointer to the next node or null if none exists.
     * Post-Condition: returns reference to next DoubleNode.
     * Theta(1), constant time complexity.
     */
    public DoubleNode getNext(){
        return nextNode;
    }

    /**
     * Method to update the nextNode instance variable for reference to next Node.
     * Pre-Condition: requires valid reference to DoubleNode as argument.
     * Post-Condition: DoubleNode's nextNode instance variable is updated.
     * Theta(1), constant time complexity.
     */
    public void setNext(DoubleNode next){
        nextNode = next;
    }

    /**
     * Method to return a pointer to the BigInteger object stored in the DoubleNode.
     * Post-Condition: returns reference to NodeInt object.
     * Theta(1), constant time complexity.
     */
    public BigInteger getInt(){
        return NodeInt;
    }

    /**
     * Method to update the NodeInt instance variable for reference to BigInteger stored.
     * Pre-Condition: requires valid reference to BigInteger as argument.
     * Post-Condition: DoubleNode's NodeInt instance variable is updated.
     * Theta(1), constant time complexity.
     */
    public void setInt(BigInteger Int1){
        NodeInt = Int1;
    }

    /**
     *Method to return the BigInteger contents of the DoubleNode object as a String.
     *Overrides: toString in class java.lang.Object
     *Post-Condition: returns a String containing the value of the BigInteger.
     *Theta(1), constant time complexity.
     */
    public String toString(){
       return NodeInt.toString();
        }
}
