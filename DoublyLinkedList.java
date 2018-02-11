import java.math.BigInteger;

/**
 * The class DoublyLinkedList implements a doubly linked list of BigIntegers in Java. The instance variables head and tail are initially null.
 * As elements are added head points to the first element on the list and tail points to the last element.
 * Each node on the list is of type DoubleNode. Each DoubleNode holds a pointer to the previous node and a pointer to the next node in the list.
 */
public class DoublyLinkedList {

	/**
     *Private instance variables to represent the head and tail DoubleNode objects of the linked list.
     */ 
    private DoubleNode head;
    private DoubleNode tail;
    
    /**
     *Constructs a new DoublyLinkedList object with head and tail as null.
     */
    DoublyLinkedList(){
        head = null;
        tail = null;
    }

    /**
     *Add a BigInteger node containing the BigInteger Int1 to the end of the linked list. This routine does not require a search.
     *Pre-Condition: A valid BigInteger element is passed to the method's argument.
     *Post-Condition: The method updates the DoublyLinkedList object to insert a new DoubleNode object as tail.
     *Theta(1), constant time complexity
     */
    public void addIntAtEnd(BigInteger Int1){
        if(this.isEmpty()){
            DoubleNode newEnd = new DoubleNode (null, Int1, null);
            head = newEnd;
            tail = newEnd;
        } else {
            DoubleNode newEnd = new DoubleNode(tail, Int1, null);
            tail.setNext(newEnd);
            tail = newEnd;
        }
    }

    /**
     *Add a BigInteger node containing the BigInteger Int1 to the front of the linked list. No search is required.
     *Pre-Condition: A valid BigInteger element is passed to the method's argument.
     *Post-Condition: The method updates the DoublyLinkedList object to insert a new DoubleNode object as head.
     *Theta(1), constant time complexity
     */
    public void addIntAtFront(BigInteger Int1){
        if(this.isEmpty()){
            DoubleNode newBeg = new DoubleNode (null, Int1, null);
            head = newBeg;
            tail = newBeg;
        } else {
            DoubleNode newBeg = new DoubleNode(null, Int1, head);
            head.setPrev(newBeg);
            head = newBeg;
        }
    }
    
    /**
     *Counts the number of nodes in the list. We are not maintaining a counter so a search is required.
     *Pre-Condition: must be null-terminating DoublyLinkedList object.
     *Post-Condition: returns the number of nodes in the DoublyLinkedList between head and tail inclusive as an integer.
     *Theta(n), linear time complexity.
     */
    public int countNodes(){
        int i = 0;
        DoubleNode cycleNode = new DoubleNode();
        cycleNode = head;
        if(cycleNode == null){
            return i;
        } else {
            do {
                i += 1;
                cycleNode = cycleNode.getNext();
            } while (cycleNode != null);
            return i;
        }
    }

     /**
     *Method to determine if DoublyLinkedList object is empty.
     *Post-Condition: returns true if the list empty false otherwise.
     *Theta(1), constant time complexity.
     */
    public boolean isEmpty(){
        if(head == null && tail == null){
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Method to return a pointer to the DoubleNode object which is the head of the DoublyLinkedList.
     * Post-Condition: returns reference to head DoubleNode.
     * Theta(1), constant time complexity.
     */
    public DoubleNode getHead(){
        return head;
    }
    
    /**
     * Method to return a pointer to the DoubleNode object which is the tail of the DoublyLinkedList.
     * Post-Condition: returns reference to tail DoubleNode.
     * Theta(1), constant time complexity.
     */
    public DoubleNode getTail(){
        return tail;
    }
    
    /**
     *Method to return the BigInteger contents of the DoubleNode objects as a String.
     *Overrides: toString in class java.lang.Object
     *Pre-Condition: null characters will be disregarded.  Must be valid, null-terminating DoublyLinkedList object.
     *Post-Condition: returns a String containing the BigIntegers in the list.
     *Theta(n), linear time complexity.
     */
    public String toString(){
        String listout = "";
        DoubleNode cycleNode = new DoubleNode();
        cycleNode = head;
        while(cycleNode != null){
            listout += cycleNode.toString() + " ";
            cycleNode = cycleNode.getNext();
        };
        return listout;
    }

}
