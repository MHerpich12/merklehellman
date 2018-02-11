import java.util.Scanner;
import java.math.BigInteger;

/**
 * Main class to initialize a new MerkleHellman object and encrypt and decrypt user-inputted strings using
 * the Merkle-Hellman encryption protocol.  This method utilizes Doubly-Linked Lists and thus requires access
 * to DoublyLinkedList and DoubleNode Java classes.  The encryption key is generated one time, and the user
 * is allowed to input multiple strings for encryption.  Will only encrypt for input strings up to 80 characters long.
 */
public class Main {

	/**
	 * Main method of program to initilize new Merkle-Hellman object and receive strings from user.
	 * Prompts user for Y/N response to continue inputting strings or to terminate program.
	 */
    public static void main(String[] args) {
    	Scanner scanner = new Scanner(System.in);
    	boolean playagain = true;

    	MerkleHellman newMK = new MerkleHellman();
    	newMK.createw();
    	newMK.createb();
    	
        while(playagain == true){
        	System.out.print("Enter a string to encrypt (1-80 characters): ");
        	String string1 = scanner.nextLine();
        
        	while(string1.length() > 80 || string1.length() <1){
        		System.out.print("Invalid input.  Please try again." + "\n");
        		System.out.print("Enter a string to encrypt (1-80 characters): ");
        		string1 = scanner.nextLine();
        	}
        
        	System.out.println("Clear text entered: " + string1);
        	System.out.println("Number of bytes in input string: " + string1.length());
          
        	newMK.encryptString(string1);
        
        	System.out.println(string1 + " is encrypted as this single large integer: ");
        	System.out.println(newMK.returnEncSum());
        
        	newMK.decryptString();
        
        	System.out.println("Result of decryption: " + newMK.returnDecString());
        	System.out.println("Do you want to encrypt another string? (Y/N) ");
        	String string2 = scanner.nextLine();
        	
        	while(!string2.equals("Y") && !string2.equals("N")){
        		System.out.println("Invalid input.  Please answer Y or N. ");
        		System.out.println("Do you want to encrypt another string? (Y/N) ");
        		string2 = scanner.nextLine();
        	}
        		
        	if (string2.equals("N")){
        		playagain = false;
        	}
        };
    }
	
}
