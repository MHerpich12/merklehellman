import java.math.BigInteger;
import java.util.Random;

/**
 * The class MerkleHellman implements a new instance of a Merkle-Hellman object to be used for Merkle-Hellman encryption.
 * This class relies on DoublyLinkedList objects populated with DoubleNodes of type BigInteger for encryption and decryption keys.
 * The class contains public methods to populate the private key, w, and the public key, b.
 * The class also contains a method to encrypt an input string, encstring, and to decrypt the string.
 * For more information, please refer to https://en.wikipedia.org/wiki/Merkle%E2%80%93Hellman_knapsack_cryptosystem.
 */
public class MerkleHellman {
	
	/**
     *Private instance variables for the Merkle-Hellman class.
	 * encstring - input string to be encrypted.
	 * DoublyLinkedList w - private key.
	 * DoublyLinkedList b - public key.
	 * MaxSeed, MinSeed - integer variables to help populate the superincreasing sequence using random numbers.
	 * BigInteger wSum - sum of w values.
	 * BigInteger q - random number greater than wSum.
	 * BigInteger r - random number coprime to q.  Default value will be q-1, which will always be coprime to q.
	 * BigInteger encryptSum - the BigInteger result of the Merkle-Hellman encryption algorithm.
	 * Int vectorlength - used to control the number of DoubleNode objects needed (default 640).
	 * String decryptString - result of decryption algorithm.
     */
	private String encstring;
	private DoublyLinkedList w;
	private DoublyLinkedList b;
	private int MinSeed;
	private int MaxSeed;
	private BigInteger wSum;
	private BigInteger q;
	private BigInteger r;
	private BigInteger encryptSum;
	private int vectorlength;
	private String decryptString;
	
	/**
    *Default constructor for MerkleHellman object.
	 * encstring and decryptString set to null values.
	 * w and b initilized as new DoublyLinkedList objects.
	 * Default values for MinSeed, MaxSeed, and vectorlength.
    */
   MerkleHellman(){
       encstring = "";
       decryptString = "";
	   w = new DoublyLinkedList();
	   b = new DoublyLinkedList();
	   MinSeed = 1;
       MaxSeed = 5;
       vectorlength = 640;
   }
   
   /**
	*Secondary constructor for MerkleHellman object with int inputs.
	* encstring and decryptString set to null values.
	* w and be initilized as new DoublyLinkedList objects.
	* Default value for vectorlength.  MinSeed and MaxSeed provided in constructor argument.
	*/
   MerkleHellman(int Min, int Max){
	   encstring = "";
	   decryptString = "";
	   w = new DoublyLinkedList();
	   b = new DoublyLinkedList();
	   MinSeed = Min;
       MaxSeed = Max;
       vectorlength = 640;
   }
   
   /**
    *Public method to create the private key, w, for the Merkle-Hellman encryption algorithm.
	* w must consist of superincreasing sequence of BigInteger values.
	* Will also calculate wSum, q, and r values based on private key.
	* Pre-Condition: vectorlength must be initialized to positive integer value.  Default = 640 for 80-character input.
	* MinSeed and MaxSeed must also be initialized to positive integer values.
	* Requires use of genCoprime method, which will only work for q greater than 2.
	* Post-Condition: new w DoublyLinkedList object and wSum, q, and r values initilized for Merkle-Hellman object.
	* Theta(n), linear time complexity.
    */
   public void createw(){
	   int incr = 0;
	   BigInteger sumbef = BigInteger.valueOf(0);
       BigInteger sumaft = BigInteger.valueOf(0);
              
       for(int i = 1; i < (vectorlength + 1); i++){
		   sumbef = sumaft;
    	   incr = returnRandInt(MinSeed, MaxSeed);
		   sumbef = sumbef.add(BigInteger.valueOf(incr));
		   w.addIntAtEnd(sumbef);
		   sumaft = sumaft.add(sumbef);
       }
       
       wSum = sumaft;
       incr = returnRandInt(MinSeed, MaxSeed);
       q = wSum.add(BigInteger.valueOf(incr));
       r = genCoprime(q);
   }
	
   /**
	*Public method to create the public key, b, for the Merkle-Hellman encryption algorithm.
	* Pre-Condition: Presumes a valid, null-terminating w DoublyLinkedList object has been initialized.
	* Post-Condition: Updates b DoublyLinkedList object based on required values for algorithm.
	* Theta(n), linear time complexity.
    */
   public void createb(){
	   DoubleNode pointer = new DoubleNode();
	   pointer = w.getHead();
	   BigInteger bval = BigInteger.valueOf(0);
	   	   
       for(int i = 1; i < (vectorlength + 1); i++){
    	   bval = pointer.getInt();
    	   bval = bval.multiply(r);
    	   bval = bval.mod(q);
		   b.addIntAtEnd(bval);
		   pointer = pointer.getNext();
       }
       
   }
   
   /**
    *Public method to return a random integer value in a specified range.
	* Pre-Condition: Requires valid integer arguments as input.
	* Post-Condition: returns an integer value in the range [Min, Max]
	* Theta(1), constant time complexity.
    */
   public int returnRandInt(int Min, int Max){
       int randInt = Min + (int)(Math.random() * ((Max - Min) + 1));
	   return randInt;
   }
   

   /**
    *Public method to generate a coprime value for a BigInteger argument.
	* An integer is coprime to another integer if and only if their Greatest Common Divisor is 1.
	* The algorithm starts with limit - 1, which should always be coprime to limit for cases where limit greater than 2, and decrements.
	* The algorithm throws an exception if it does not converge, i.e. if it returns 1.
	* Pre-Condition: requires valid BigInteger argument with value greater than 2.
	* Post-Condition: returns BigInteger coprime to argument.
	* Best Case: Theta(1), or constant time complexity.  Should resolve to limit - 1.
	* Worst Case: Theta(N), or linear time complexity.  Should only be worst case for input equal to 3.
    */
   public BigInteger genCoprime(BigInteger limit){
	   BigInteger randInt = limit.subtract(BigInteger.valueOf(1));
	   while(randInt.compareTo(BigInteger.valueOf(1)) == 1){
		   if((limit.gcd(randInt).equals(BigInteger.valueOf(1)))){
			   break;
		   }
		   randInt = randInt.subtract(BigInteger.valueOf(1));
	   }
	   if(randInt.compareTo(BigInteger.valueOf(1)) == 0){
	   		throw new ArithmeticException("Algorithm failed to converge.  Could not find valid coprime.");
	   }
	   return randInt;
   }
   
   /**
    *Public method to encrypt an inputted string using Merkle-Hellman algorithm and public key (b).
	* Converts each character to a binary string object and adds back leading 0 values as appropriate.
	* Then utilizes an increasing BigInteger, sumencryptInt, to track the increasing value of the product of b and the bitwise value.
	* Pre-Condition: requires valid, null-terminating b DoublyLinkedList to be initilized.
	* Post-Condition: updates BigInteger encryptSum value based on algorithm.
	* Theta(N), or linear time complexity.  Requires ~2N calculations to convert characters to binary values and ~8N calculations to
	* sum binary values according to algorithm.
    */
   public void encryptString(String inputStr1){
	   encstring = inputStr1;
	   BigInteger sumencryptInt = BigInteger.valueOf(0);
	   BigInteger productInt = BigInteger.valueOf(0);
	   BigInteger encryptInt = BigInteger.valueOf(0);
	   String binresult = "";
	   String charresult = "";
	   char[] CharArray = encstring.toCharArray();
	   DoubleNode pointer = new DoubleNode();
	   pointer = b.getHead();
	   char bit;
	   
	   for(int j = 0; j < (encstring.length()); j++){
		   charresult = Integer.toBinaryString(CharArray[j]) + "";
	   	   
		   if(charresult.length() == 7) {
			   charresult = "0" + charresult;
		   }
		   if(charresult.length() == 6) {
			   charresult = "00" + charresult;
		   }
		   if(charresult.length() == 5) {
			   charresult = "000" + charresult;
		   }
		   if(charresult.length() == 4) {
			   charresult = "0000" + charresult;
		   }
		   if(charresult.length() == 3) {
			   charresult = "00000" + charresult;
		   }
		   if(charresult.length() == 2) {
			   charresult = "000000" + charresult;
		   }
		   if(charresult.length() == 1) {
			   charresult = "0000000" + charresult;
		   }
		   
		   binresult = binresult + charresult;
	   }
	   
	   for(int k = 0; k < (binresult.length()); k++){
		   bit = binresult.charAt(k);
		   encryptInt = pointer.getInt();
		   
		   if(bit == '0'){
			   productInt = encryptInt.multiply(BigInteger.valueOf(0));
		   } else {
			   productInt = encryptInt.multiply(BigInteger.valueOf(1));
		   }
		   
		   sumencryptInt = sumencryptInt.add(productInt);
		   if(pointer.getNext() != null){
			   pointer = pointer.getNext();
		   }
	   }
	   
	   encryptSum =  sumencryptInt;
   }
   
   /**
	*Public method to decrypt the stored string using Merkle-Hellman algorithm, the encryptSum BigInteger, and the private key, w.
	* Utilizes encryptSum to compare against w values and create binary sequence which is converted into ASCII to form decrypted message.
	* Pre-Condition: requires valid, null-terminating w.
	* Post-Condition: decryptString updated for ASCII character sequence as a result of algorithm.
	* Theta(N), or linear time complexity.  Requires ~2N calculations to generate binary string and ~24N calculations to sum binary
	* values and convert to ASCII.
	*/
   public void decryptString(){
	   BigInteger decval = BigInteger.valueOf(0);
	   BigInteger wval = BigInteger.valueOf(0);
	   String decmess = "";
	   String message = "";
	   DoubleNode pointer = new DoubleNode();
	   pointer = w.getTail();
	   int power2 = 7;
	   int cumsum = 0;
	   char c;
	   
	   BigInteger rinv = r.modInverse(q);
	   decval = rinv.multiply(encryptSum);
	   decval = decval.mod(q);
	   
	   while(pointer != null){
		   wval = pointer.getInt();
		   
		   if(wval.compareTo(decval) < 1){
			   decval = decval.subtract(wval);
			   decmess = "1" + decmess;
		   } else {
			   decmess = "0" + decmess;
		   }
		   
		   pointer = pointer.getPrev();
	   }
	   
	   for(int l = 0; l < decmess.length(); l++){
		   
		   if(decmess.charAt(l) == '1'){
			   cumsum += Math.pow(2, power2);
			   power2 -= 1;
		   } else {
			   power2 -= 1;
		   }

		   if(((l+1) % 8 == 0) && (l > 0)){
			   power2 = 7;
			   c = (char) cumsum;
			   message = message + c;
			   cumsum = 0;
		   }
	   }   
  
	   decryptString = message;
   }
   
   /**
    *Public method to update the encstring instance variable with an input string.
	* Post-Condition: encstring is set to input string value.
	* Theta(1), constant time complexity.
    */
   public void setString(String newinString){
       encstring = newinString;
   }
   
   /**
    *Public method to return a pointer to the w DoublyLinkedList object.
	* Post-Condition: returns pointer to w instance variable.
	* Theta(1), constant time complexity.
    */
   public DoublyLinkedList returnw(){
       return w;
   }
   
   /**
    *Public method to return a pointer to the b DoublyLinkedList object.
	* Post-Condition: returns pointer to b instance variable.
	* Theta(1), constant time complexity.
    */
   public DoublyLinkedList returnb(){
       return b;
   }
   
   /**
    *Public method to return a pointer to the BigInteger object encryptSum, the result of the Merkle-Hellman encryption algorithm.
	* Post-Condition: returns pointer to encryptSum instance variable.
	* Theta(1), constant time complexity.
    */
   public BigInteger returnEncSum(){
       return encryptSum;
   }
   
   /**
    *Public method to return a pointer to the decryptString instance variable, the result of the Merkle-Hellman decryption process.
	* Post-Condition: returns pointer to decryptString instance variable.
	* Theta(1), constant time complexity.
    */
   public String returnDecString(){
       return decryptString;
   }
}
