/**
 * Name : Hector Herrera
 * PennKey : Hectorh
 * Recitation : 216
 * 
 * Execution: java Caesar
 * 
 * This can encrypt, decrypt, and crack a message 
 */

public class Caesar {
    public static void main(String[] args) {
        //THIS IS ALL TEST CODE
        //stringToSymbolArray("ET TU, BRUTE?");
        //symbolArrayToString(stringToSymbolArray("ET TU, BRUTE?"));
        //shift(-2, 2);
        //unshift(0, 6);
        //encrypt("ET TU, BRUTE?", 6);
        //decrypt("I AM HECTOR", 0);
        //String message = "I AM HECTOR!";
        //String cipher = encrypt(message, 2);
        //String decrypted = decrypt(cipher, 2);
        //System.out.println(decrypted);
        
        String function = args[0];        
        if (function.equals("crack")) {
            String fileName = args[1];
            String englishStr = args[2];
            
            In inStream = new In(fileName);
            String read = inStream.readAll();
            String cracked = crack(read, englishStr);
            System.out.println(cracked);
        }
        if (function.equals("encrypt")) {
            String fileName = args[1];
            In inStream = new In(fileName);
            String read = inStream.readAll();
            
            String key = args[2]; //this is supposed to find the char
            int newKey = (int) key.charAt(0);
            String encrypted = encrypt(read, newKey - 'A');
            System.out.println(encrypted);
        }
        if (function.equals("decrypt")) {
            String fileName = args[1];
            In inStream = new In(fileName);
            String read = inStream.readAll();
           
            String key = args[2];
            int newKey = (int) key.charAt(0);
            String decrypted = decrypt(read, newKey - 'A');
            System.out.println(decrypted);
        }
        
        //THIS IS ALL TEST CODE
        //String a = "AAA B,B C";
        //int [] b = stringToSymbolArray(a);
        //double [] c = findFrequencies(b);
        //for(int i = 0; i < c.length; i++){
        //    System.out.println(c[i]);
        //}
        
        //double [] freqs = {2.5, 2.5, 3, 4};
        //double [] englishFreqs = {2, 1.5, 3, 3};
        //scoreFrequencies(freqs, englishFreqs);
    }
    /*
     * Description: The function will crack an encrypted message and
     *              will do this by comparing the letter frequencies
     *              to produce a string that is in English.
     * Input: two files that will come in as strings
     * Output: a string with the cracked message
     */
    public static String crack(String cipher, String english) {
        String crack = "";
        In inStream = new In(english);
        double smallestFreq = 0;
        int key = 0;
        double minSoFar = Double.POSITIVE_INFINITY;
        double [] englishFreqs = getDictionaryFrequencies(english);
        //for loop to find the key value 
        for (int i = 0; i < 26; i++) {
            String decrypted = decrypt(cipher, i);
            int[] cipherArr = stringToSymbolArray(decrypted);
            double [] freqs = findFrequencies(cipherArr);
            smallestFreq = scoreFrequencies(freqs, englishFreqs);
            if (smallestFreq < minSoFar) {
                minSoFar = smallestFreq;
                key = i;
            }
        }
        crack = decrypt(cipher, key);
        return crack;
    }
    /*
     * Description: This function is supposed to find the letter frequency 
     *              differences using an equation and will add them all up
     *              and will output the sum as a double.
     * Input: it will take in two double arrays 
     * Output: it will output the difference of the arrays it takes in
     *         and will output it as a double.
     */
    public static double scoreFrequencies(double [] freqs, 
                                          double [] englishFreqs) {
        double scoreFrequencies = 0;
        double [] sum = new double[freqs.length];
        if (freqs.length == englishFreqs.length) {
            //nested for loop to go through the double arrays
            for (int i = 0; i < freqs.length; i++) {
                for (int j = 0; j < freqs.length; j++) {
                    if (0 == j) {
                        //will find the difference and add them up
                        sum[i] = Math.abs(freqs[i + j] - englishFreqs[j + i]);
                        scoreFrequencies += sum[i]; 
                    } 
                }
            }
            //finds the max number, which is the final sum of scoreFrequencies
            double maxSoFar = Double.NEGATIVE_INFINITY;
            for (int a = 0; a < freqs.length; a++) {
                if (scoreFrequencies > maxSoFar) {
                    maxSoFar = scoreFrequencies;
                    //System.out.print(maxSoFar);
                }
            }
        }
        return scoreFrequencies;
    }
    /*
     * Description: This function recieves a file that is converted to 
     *              a string and will find the letter frequencies and 
     *              output it as an array of doubles with 26 lines, each 
     *              line representing a letter.
     * Input: a string that was previously a file
     * Output: an array of doubles between 0-1
     */
    public static double [] getDictionaryFrequencies(String english) {
        In inStream = new In(english);
        double[] getDictionaryFrequencies = new double[26]; 
        //for loop that will read the doubles in the string
        for (int i = 0; i < getDictionaryFrequencies.length; i++) {
            getDictionaryFrequencies[i] = inStream.readDouble();
        }
        return getDictionaryFrequencies;    
    }
    /*
     * Description: This function will return a double array where the 
     *              0th element in the array is the frequency of A 
     *              in the ciphertext, and so on.
     * Input: an integer array that was previously a file
     * Output: an array of doubles between 0-1
     */
    public static double [] findFrequencies(int [] symbolArr) {
        double [] findFrequencies = new double [26];
        //for loop that adds a count to findFrequencies
        for (int i = 0; i < symbolArr.length; i++) {
            for (int j = 0; j < 26; j++) {
                if (symbolArr[i] == j) {
                    findFrequencies[j] += 1;
                }
            }
        }
        double count = 0;
        //for loop will count the number of letters
        for (int i = 0; i < symbolArr.length; i++) {
            if (0 <= symbolArr[i] && symbolArr[i] <= 25) {
                count += 1;
            }
        }
        //for loop will give the frequencies of the counted letters
        for (int i = 0; i < findFrequencies.length; i++) {
            findFrequencies[i] = findFrequencies[i] / count;
        }
        return findFrequencies;
    }
    /*
     * Description: This function will encrypt a message by an offset
     *              that is represented by key
     * Input: a file as a string and an offset represented by an int
     * Output: a string of the encrypted message
     */
    public static String encrypt(String message, int key) {
        String encrypt;
        int [] symbol = stringToSymbolArray(message);
        //for loop to shift the symbols in a string by a given key
        for (int i = 0; i < symbol.length; i++) {
            symbol[i] = shift(symbol[i], key);
        }
        //will encrypt the message
        encrypt = symbolArrayToString(symbol);
        return encrypt;
    }
    /*
     * Description: This function will decrypt a message by an offset
     *              that is represented by key
     * Input: a file as a string and an offset represented by an int
     * Output: a string of the decrypted message
     */
    public static String decrypt(String cipher, int key) {
        String decrypt;
        int [] symbol = stringToSymbolArray(cipher);
        //for loop to unshift the symbols in a string by a given key
        for (int i = 0; i < symbol.length; i++) {
            symbol[i] = unshift(symbol[i], key);
        }
        //will decrypt the message
        decrypt = symbolArrayToString(symbol);
        return decrypt;
    }
    /*
     * Description: converts a string to a symbol array,
     *              where each element of the array is an
     *              integer encoding of the corresponding
     *              element of the string.
     * Input:  the message text to be converted
     * Output: integer encoding of the message
     */
    public static int[] stringToSymbolArray(String str) {
        int [] stringToSymbolArray = new int [str.length()];
        str = str.toUpperCase(); //turns everything to an uppercase
        
        //for loop: fill the indices with the corresponding symbol integer value
        for (int i = 0; i < stringToSymbolArray.length; i++) {
            char letter = str.charAt(i);
            
            // cast letter to an integer as encoded by our representation
            int ourSymbolRepresentation = (int) (letter - 'A');
            
            //will prduce an array of integers
            stringToSymbolArray[i] = ourSymbolRepresentation;    
        }
        return stringToSymbolArray;
    }
    /*
     * Description: converts an array of symbols to a string,
     *              where each element of the array is an
     *              integer encoding of the corresponding
     *              element of the string.
     * Input:  integer encoding of the message
     * Output: the message text
     */
    public static String symbolArrayToString(int[] symbols) { 
        String symbolArrayToString = "";
        //for loop to go through every letter and change it to a string
        for (int i = 0; i < symbols.length; i++) {
            //gets the ith number in int [] symbols
            int ourSymbolRepresentation = symbols[i];
            
            //cast the integer to a char
            char letter = (char) (ourSymbolRepresentation + 'A');
            
            //adds letter to symbolArrayToString and places it into itself
            symbolArrayToString = symbolArrayToString + letter;    
        }
        return symbolArrayToString;
    }
    /*
     * Description: If an english letter is between 0 and 25
     *              then it will be shifted by an offset amount
     *              between 0 and 25
     * Input:  integer symbols and integer offsets (between 0 and 25)
     * Output: the shifted integers
     */
    public static int shift(int symbol, int offset) {
        int shift;
        if ((0 <= symbol && symbol <= 25) && (0 <= offset && offset <= 25)) {
            //adds offset to symbol to shift the letters
            shift = symbol + offset;
            //just in case shift is greater than 25
            if (shift > 25) {
                shift = (shift % 25) - 1;  
            }
        } 
        //will take in punctuation and characters that are not letters
        else {
            symbol = (int) symbol;
            shift = symbol;
        }
        return shift;
    }
    /*
     * Description: to undo the shifting of the previous function
     * Input:  integer symbols and integer offsets (between 0 and 25)
     * Output: unshift the integers
     */
    public static int unshift(int symbol, int offset) {
        int unshift;
        if ((0 <= symbol && symbol <= 25) && (0 <= offset && offset <= 25)) {
            //formula to unshift a letter by the offset
            unshift = symbol - offset;
            //in case unshift goes to the negatives
            if (unshift < 0) {
                unshift = 26 + unshift;  
            }
        }
        // will take in a space or a punctuation
        else { 
            symbol = (int) symbol;
            unshift = symbol;
        }
        return unshift;
    } 
}