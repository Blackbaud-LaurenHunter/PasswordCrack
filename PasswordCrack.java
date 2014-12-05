import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Timer;


public class PasswordCrack {

	private static BufferedReader dictonary;
	private static BufferedReader passwords;
    private static ArrayList<String> wordList;
    private static ArrayList<User> userList;
    private static long startTime;
    private static long endTime;

    private static char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private static char[] upcaseAlphabet = {'A', 'B', 'C', 'C', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static char[] numbers = {'0', '1', '2', '3', '4','5', '6', '7', '8', '9'};
    private static char[] specialCharacters = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '+', '=', '~', '`', '{', '}', '[', ']', '|', '?', '>', '<', '.', ',', '/', '\\' };


	public static void main(String[] args) {
		try{
			dictonary = new BufferedReader(new FileReader(args[0]));
			passwords = new BufferedReader(new FileReader(args[1]));
            generateWordList();
            generateUserList();
            crackPasswords();
			dictonary.close();
			passwords.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

    public static void generateUserList() throws IOException{
        userList = new ArrayList<User>();
        String line;
        while((line = passwords.readLine()) != null){
            userList.add(new User(line));
        }
    }

    public static void generateWordList() throws IOException{
        wordList = new ArrayList<String>();
        String word;
        while((word = dictonary.readLine()) != null)
            wordList.add(word);
    }

    public static void crackPasswords() throws IOException{
        System.out.println("Cracking Passwords");
        startTime = System.currentTimeMillis();
        //Try all first and last names
        for(Iterator<User> it = userList.iterator(); it.hasNext(); ){
            User usr = it.next();
            if (crack(usr.firstName) > -1)
                it.remove();

        }

        for(Iterator<User> it = userList.iterator(); it.hasNext(); ){
            User usr = it.next();
            if (crack(usr.lastName) > -1)
                it.remove();
        }

        for(Iterator<User> it = userList.iterator(); it.hasNext(); ){
            User usr = it.next();
            if (crack(usr.firstName, usr.firstName) > -1)
                it.remove();
        }

        for(Iterator<User> it = userList.iterator(); it.hasNext(); ){
            User usr = it.next();
            if (crack(usr.lastName, usr.lastName) > -1)
                it.remove();
        }

        int index;

        //try words from the word list
        for(String word : wordList){
            if ((index = crack(word)) > -1)
                userList.remove(index);
            if ((index = crack(reverseWord(word))) > -1)
                userList.remove(index);

        }

        //try all two combination words from word list
        for(String word1 : wordList){
            for(String word2 : wordList){
                if ((index = crack(word1, word2)) > -1)
                    userList.remove(index);
            }
        }
        int foundPasswords = 20 - userList.size();
        System.out.println("Found " + Integer.toString(foundPasswords) + " out of 20 Passwords");
    }

    /**
     *Steps to crack password
     * Step 1. Try using the first and last name and then use them with words from the word list
     * Step 2. Use words from the wordlist
     * Step 3. Use mangled versions of the words
     * Step 4. Attempt to apply two mangles to each word.
     */

//    public static boolean crackPassword(String encryptedPassword, String firstName, String lastName, String salt){
//
//
//        //Try words from word list
//        for(String word : wordList){
//            if((password = crack(word, salt, encryptedPassword)) != null){
//                System.out.println("Password for " + firstName + " is " + password);
//                return true;
//            }
//            // try crack method on reverse of the word itself
//            if((password = crack(reverseWord(word), salt, encryptedPassword)) != null){
//                System.out.println("Password for " + firstName + " is " + password);
//                return true;
//            }
//        }
//
//        //Try all two combination words from word list
//        for(String word1 : wordList){
//            for(String word2: wordList){
//                if((password = crack(word1, word2, salt, encryptedPassword)) != null){
//                    System.out.println("Password for " + firstName + " is " + password);
//                    return true;
//                }
//            }
//        }
//
//        System.out.println("Could not crack the password for " + firstName);
//        return false;
//    }

    public static int crack(String word1, String word2){
        int index = -1;
        //word1 + word2
        String password = word1 + word2;
        if ((index = tryPassword(password)) > -1)
            return index;

        //word2 + word1
        password = word2 + word1;
        if ((index = tryPassword(password)) > -1)
            return index;

        //word1.reverse + word2
        password = reverseWord(word1) + word2;
        if ((index = tryPassword(password)) > -1)
            return index;

        //word1 + word2.reverse
        password = word1 + reverseWord(word2);
        if ((index = tryPassword(password)) > -1)
            return index;

        //word2.reverse + word1
        password = reverseWord(word2) + word1;
        if ((index = tryPassword(password)) > -1)
            return index;

        //word2 + word1.reverse
        password = word2 + reverseWord(word1);
        if ((index = tryPassword(password)) > -1)
            return index;

        //word1.reverse + word2.reverse
        password = reverseWord(word1) + reverseWord(word2);
        if ((index = tryPassword(password)) > -1)
            return index;

        //word2.reverse + word1.reverse
        password = reverseWord(word2) + reverseWord(word1);
        if ((index = tryPassword(password)) > -1)
            return index;

        //word1.reverse + first character of word2
        password = reverseWord(word1) + word2.substring(0, 1).toLowerCase();
        if ((index = tryPassword(password)) > -1)
            return index;
        password = reverseWord(word1) + word2.substring(0, 1).toUpperCase();
        if ((index = tryPassword(password)) > -1)
            return index;

        //first character of word1 + word2.reverse
        password = word1.substring(0, 1).toLowerCase() + reverseWord(word2);
        if ((index = tryPassword(password)) > -1)
            return index;
        password = word1.substring(0, 1).toUpperCase() + reverseWord(word2);
        if ((index = tryPassword(password)) > -1)
            return index;

        //word2.reverse + first character of word1
        password = reverseWord(word2) + word1.substring(0, 1).toLowerCase();
        if ((index = tryPassword(password)) > -1)
            return index;
        password = reverseWord(word2) + word1.substring(0, 1).toUpperCase();
        if ((index = tryPassword(password)) > -1)
            return index;

        //first character of word2 + word1.reverse
        password = word2.substring(0, 1).toLowerCase() + reverseWord(word1);
        if ((index = tryPassword(password)) > -1)
            return index;
        password = word2.substring(0, 1).toUpperCase() + reverseWord(word1);
        if ((index = tryPassword(password)) > -1)
            return index;


        //word1 + firt character of word 2
        password = word1.toLowerCase() + word2.substring(0, 1).toLowerCase();
        if ((index = tryPassword(password)) > -1)
            return index;
        password = word1.toLowerCase() + word2.substring(0, 1).toUpperCase();
        if ((index = tryPassword(password)) > -1)
            return index;
        password = word1.toUpperCase() + word2.substring(0, 1).toLowerCase();
        if ((index = tryPassword(password)) > -1)
            return index;
        password = word1.toUpperCase() + word2.substring(0, 1).toUpperCase();
        if ((index = tryPassword(password)) > -1)
            return index;

        //first character of word 1 + word2
        password = word1.substring(0, 1).toLowerCase() + word2.toLowerCase();
        if ((index = tryPassword(password)) > -1)
            return index;
        password = word1.substring(0, 1).toUpperCase() + word2.toLowerCase();
        if ((index = tryPassword(password)) > -1)
            return index;
        password = word1.substring(0, 1).toLowerCase() + word2.toUpperCase();
        if ((index = tryPassword(password)) > -1)
            return index;
        password = word1.substring(0, 1).toUpperCase() + word2.toUpperCase();
        if ((index = tryPassword(password)) > -1)
            return index;

        // word1.ncapitalize + word2
        password = nCapitalize(word1) + word2.toLowerCase();
        if ((index = tryPassword(password)) > -1)
            return index;
        password = nCapitalize(word1) + word2.toUpperCase();
        if ((index = tryPassword(password)) > -1)
            return index;

        // word1.ncapitalize + first character of word2
        password = nCapitalize(word1) + word2.substring(0, 1).toLowerCase();
        if ((index = tryPassword(password)) > -1)
            return index;
        password = nCapitalize(word1) + word2.substring(0, 1).toUpperCase();
        if ((index = tryPassword(password)) > -1)
            return index;

        // word1 + word2.ncapitalize
        password = word1.toLowerCase() + nCapitalize(word2);
        if ((index = tryPassword(password)) > -1)
            return index;
        password = word1.toUpperCase() + nCapitalize(word2);
        if ((index = tryPassword(password)) > -1)
            return index;

        // first character of word1 + word2.ncapitalize
        password = word1.substring(0, 1).toLowerCase() + nCapitalize(word2);
        if ((index = tryPassword(password)) > -1)
            return index;
        password = word1.substring(0,1).toUpperCase() + nCapitalize(word2);
        if ((index = tryPassword(password)) > -1)
            return index;

        return index;

    }

    public static int crack(String word){
        int index = -1;
        String password = word;

        //try just the word
        //System.out.println("Trying password: " + password);
        if ((index = tryPassword(password)) > -1)
            return index;
        //remove last character
        if (word.length() > 1){
            password = word.substring(0, word.length() - 1).toLowerCase();
            if ((index = tryPassword(password)) > -1)
                return index;
            password = password.substring(0, 1).toUpperCase() + password.substring(1).toLowerCase();
            if ((index = tryPassword(password)) > -1)
                return index;
            password = password.substring(0, password.length() - 1).toLowerCase() + password.substring(password.length() - 1).toUpperCase();
            if ((index = tryPassword(password)) > -1)
                return index;
            password = word.substring(0, word.length() - 1).toUpperCase();
            if ((index = tryPassword(password)) > -1)
                return index;
            password = password.substring(0, 1).toLowerCase() + password.substring(1).toUpperCase();
            if ((index = tryPassword(password)) > -1)
                return index;
            password = password.substring(0, password.length() - 1).toUpperCase() + password.substring(password.length() - 1).toLowerCase();
            if ((index = tryPassword(password)) > -1)
                return index;

            //remove first character
            password = word.substring(1).toLowerCase();
            if ((index = tryPassword(password)) > -1)
                return index;
            password = password.substring(0, 1).toUpperCase() + password.substring(1).toLowerCase();
            if ((index = tryPassword(password)) > -1)
                return index;
            password = password.substring(0, password.length() - 1).toLowerCase() + password.substring(password.length() - 1).toUpperCase();
            if ((index = tryPassword(password)) > -1)
                return index;
            password = word.substring(1).toUpperCase();
            if ((index = tryPassword(password)) > -1)
                return index;
            password = password.substring(0, 1).toLowerCase() + password.substring(1).toUpperCase();
            if ((index = tryPassword(password)) > -1)
                return index;
            password = password.substring(0, password.length() - 1).toUpperCase() + password.substring(password.length() - 1).toLowerCase();
            if ((index = tryPassword(password)) > -1)
                return index;
        }

        //reverse word
        password = reverseWord(word);
        //System.out.println("Trying password: " + new StringBuilder(word).reverse().toString());
        if ((index = tryPassword(password)) > -1)
            return index;

        //capitalize the word
        password = word.toUpperCase();
        //System.out.println("Trying password: " + password);
        if ((index = tryPassword(password)) > -1)
            return index;

        //lowercase
        password = word.toLowerCase();
        //System.out.println("Trying password: " + password);
        if ((index = tryPassword(password)) > -1)
            return index;

        //capitalize first letter
        password = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
        //System.out.println("Trying password: " + password);
        if ((index = tryPassword(password)) > -1)
            return index;

        //capitalize last letter
        password = word.substring(0, word.length() - 1).toLowerCase() + word.substring(word.length() - 1).toUpperCase();
        //System.out.println("Trying password: " + password);
        if ((index = tryPassword(password)) > -1)
            return index;

        //capitalize each letter
        for(int i = 1; i < word.length() - 1; i++){
            password = word.substring(0, i).toLowerCase() + word.substring(i, i+1).toUpperCase() + word.substring(i+1).toLowerCase();
            //System.out.println("Trying password: " + password);
            if ((index = tryPassword(password)) > -1)
                return index;
        }

        //ncapitalize the word
        password = nCapitalize(word);
        //System.out.println("Trying password: " + password);
        if ((index = tryPassword(password)) > -1)
            return index;

        //toggle case1
        password = toggle1(word);
        //System.out.println("Trying password: " + password);
        if ((index = tryPassword(password)) > -1)
            return index;

        //toggle case 2
        password = toggle2(word);
        //System.out.println("Trying password: " + password);
        if ((index = tryPassword(password)) > -1)
            return index;

        //prepend and append character
        for (int i = 0; i < alphabet.length; i++){
            password = Character.toString(alphabet[i]) + word.toLowerCase();
            if ((index = tryPassword(password)) > -1)
                return index;
            password = word.toLowerCase() + Character.toString(alphabet[i]);
            if ((index = tryPassword(password)) > -1)
                return index;
        }

        //prepend and append uppercase character
        for (int i = 0; i < upcaseAlphabet.length; i++){
            password = Character.toString(upcaseAlphabet[i]) + word.toLowerCase();
            if ((index = tryPassword(password)) > -1)
                return index;
            password = word.toLowerCase() + Character.toString(upcaseAlphabet[i]);
            if ((index = tryPassword(password)) > -1)
                return index;
        }

        //prepend and append numbers
        for (int i = 0; i < numbers.length; i++){
            password = Character.toString(numbers[i]) + word.toLowerCase();
            if ((index = tryPassword(password)) > -1)
                return index;
            password = word.toLowerCase() + Character.toString(numbers[i]);
            if ((index = tryPassword(password)) > -1)
                return index;
        }

        //prepend and append special characters
        for (int i = 0; i < specialCharacters.length; i++){
            password = Character.toString(specialCharacters[i]) + word.toLowerCase();
            if ((index = tryPassword(password)) > -1)
                return index;
            password = word.toLowerCase() + Character.toString(specialCharacters[i]);
            if ((index = tryPassword(password)) > -1)
                return index;
        }

        //replace first character with a special character
        for(int i = 0; i < specialCharacters.length; i++){
            password = Character.toString(specialCharacters[i]) + word.substring(1).toLowerCase();
            if ((index = tryPassword(password)) > -1)
                return index;
        }

        //replace last character with a special character
        for(int i = 0; i < specialCharacters.length; i++){
            password = word.substring(0, word.length() - 1).toLowerCase() + Character.toString(specialCharacters[i]);
            if ((index = tryPassword(password)) > -1)
                return index;
        }

        //replace first character with a number
        for(int i = 0; i < numbers.length; i++){
            password = Character.toString(numbers[i]) + word.substring(1).toLowerCase();
            if ((index = tryPassword(password)) > -1)
                return index;
        }

        //replace last character with a number
        for(int i = 0; i < numbers.length; i++){
            password = word.substring(0, word.length() - 1).toLowerCase() + Character.toString(numbers[i]);
            if ((index = tryPassword(password)) > -1)
                return index;
        }
        return index;
    }

    public static String reverseWord(String word){
        return new StringBuilder(word).reverse().toString().toLowerCase();
    }

    public static String nCapitalize(String word){
        return word.substring(0, 1).toLowerCase() + word.substring(1).toUpperCase();
    }

    public static String toggle1(String word){
        String password = "";
        for(int i = 0; i < word.length(); i++){
            if (i % 2 == 0){
                password += word.substring(i, i+1).toUpperCase();
            }else{
                password += word.substring(i, i+1).toLowerCase();
            }
        }
        return password;
    }

    public static String toggle2(String word){
        String password = "";
        for(int i = 0; i < word.length(); i++){
            if (i % 2 == 0){
                password += word.substring(i, i+1).toLowerCase();
            }else{
                password += word.substring(i, i+1).toUpperCase();
            }
        }
        return password;
    }

    //try password on all users
    //if password is found remove that user from the list of users
    public static int tryPassword(String password){
        for(int i = 0; i < userList.size(); i++){
            if(jcrypt.crypt(userList.get(i).salt, password).equals(userList.get(i).encryptedPassword)){
                endTime = System.currentTimeMillis();
                System.out.println("Password for " + userList.get(i).firstName + " is " + password + " with encryptedPassword " + userList.get(i).encryptedPassword);
                System.out.println("Found in "+ (endTime - startTime) * .001 + " seconds ");
                return i;
            }
        }
        return -1;
    }

    public static class User{
        public String firstName;
        public String lastName;
        public String salt;
        public String encryptedPassword;
        public static String decryptedPassword;

        public User(String line){
            Pattern pattern = Pattern.compile("[a-z]*:(.*):\\d*:\\d*:([a-zA-Z]*)\\s([a-zA-Z]*):.*", Pattern.CASE_INSENSITIVE);
            Matcher matcher;
            matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    encryptedPassword = matcher.group(1);
                    firstName = matcher.group(2);
                    lastName = matcher.group(3);
                    salt = encryptedPassword.substring(0, 2);
                }
        }
    }



}