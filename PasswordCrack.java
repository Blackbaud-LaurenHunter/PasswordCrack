import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PasswordCrack {

	private static BufferedReader dictonary;
	private static BufferedReader passwords;
    private static ArrayList<String> wordList;

    private static char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private static char[] upcaseAlphabet = {'A', 'B', 'C', 'C', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static char[] numbers = {'0', '1', '2', '3', '4','5', '6', '7', '8', '9'};
    private static char[] specialCharacters = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '+', '=', '~', '`', '{', '}', '[', ']', '|', '?', '>', '<', '.', ',', '/', '\\' };


	public static void main(String[] args) {
		try{
			dictonary = new BufferedReader(new FileReader(args[0]));
			passwords = new BufferedReader(new FileReader(args[1]));
            generateWordList();
            crackPasswords();
			dictonary.close();
			passwords.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

    public static void generateWordList() throws IOException{
        wordList = new ArrayList<String>();
        String word;
        while((word = dictonary.readLine()) != null)
            wordList.add(word);
    }

    public static void crackPasswords() throws IOException{
        Pattern pattern = Pattern.compile("[a-z]*:(.*):\\d*:\\d*:([a-zA-Z]*)\\s([a-zA-Z]*):.*", Pattern.CASE_INSENSITIVE);
        Matcher matcher;
        String line;
        String salt;
        String encryptedPassword;
        String firstName;
        String lastName;
        int passwordsCracked = 0;
        int numPasswords = 0;
        while ((line = passwords.readLine()) != null){
            //System.out.println(line);
            matcher = pattern.matcher(line);
            if (matcher.matches()){
                encryptedPassword = matcher.group(1);
                firstName = matcher.group(2);
                lastName = matcher.group(3);
                salt = encryptedPassword.substring(0, 2);
                System.out.println("#########################################");
                System.out.println("Cracking Password for " + firstName);
                if(crackPassword(encryptedPassword, firstName, lastName, salt))
                    passwordsCracked++;
                numPasswords++;
            }
        }
        System.out.println("Cracked " + Integer.toString(passwordsCracked) + " passwords out of " + numPasswords);

    }

    /**
     *Steps to crack password
     * Step 1. Try using the first and last name and then use them with words from the word list
     * Step 2. Use words from the wordlist
     * Step 3. Use mangled versions of the words
     * Step 4. Attempt to apply two mangles to each word.
     */

    public static boolean crackPassword(String encryptedPassword, String firstName, String lastName, String salt){

        //Try first name
        String password;
        if ((password = crack(firstName, salt, encryptedPassword)) != null){
            System.out.println("Password for " + firstName + " is " + password);
            return true;
        }

        //Try Last name
        if ((password = crack(lastName, salt, encryptedPassword)) != null){
            System.out.println("Password for " + firstName + " is " + password);
            return true;
        }

        //Try words from word list
        for(String word : wordList){
            if((password = crack(word, salt, encryptedPassword)) != null){
                System.out.println("Password for " + firstName + " is " + password);
                return true;
            }
        }

        System.out.println("Could not crack the password for " + firstName);
        return false;
    }

    public static String crack(String word, String salt, String encryptedPassword){
        String password = word;

        //try just the word
        //System.out.println("Trying password: " + password);
        if(jcrypt.crypt(salt, password).equals(encryptedPassword))
            return password;

        //reverse word
        //System.out.println("Trying password: " + new StringBuilder(word).reverse().toString());
        if(jcrypt.crypt(salt, new StringBuilder(word).reverse().toString().toLowerCase()).equals(encryptedPassword))
            return new StringBuilder(word).reverse().toString().toLowerCase();

        //capitalize the word
        password = word.toUpperCase();
        //System.out.println("Trying password: " + password);
        if(jcrypt.crypt(salt, password).equals(encryptedPassword))
            return password;

        //lowercase
        password = word.toLowerCase();
        //System.out.println("Trying password: " + password);
        if(jcrypt.crypt(salt, password).equals(encryptedPassword))
            return password;

        //capitalize first letter
        password = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
        //System.out.println("Trying password: " + password);
        if(jcrypt.crypt(salt, password).equals(encryptedPassword))
            return password;

        //capitalize last letter
        password = word.substring(0, word.length() - 1).toLowerCase() + word.substring(word.length() - 1).toUpperCase();
        //System.out.println("Trying password: " + password);
        if(jcrypt.crypt(salt, password).equals(encryptedPassword))
            return password;

        //capitalize each letter
        for(int i = 1; i < word.length() - 1; i++){
            password = word.substring(0, i).toLowerCase() + word.substring(i, i+1).toUpperCase() + word.substring(i+1).toLowerCase();
            //System.out.println("Trying password: " + password);
            if(jcrypt.crypt(salt, password).equals(encryptedPassword))
                return password;
        }

        //ncapitalize the word
        password = word.substring(0, 1).toLowerCase() + word.substring(1).toUpperCase();
        //System.out.println("Trying password: " + password);
        if(jcrypt.crypt(salt, password).equals(encryptedPassword))
            return password;

        //toggle case1
        password = "";
        for(int i = 0; i < word.length(); i++){
            if (i % 2 == 0){
                password += word.substring(i, i+1).toUpperCase();
            }else{
                password += word.substring(i, i+1).toLowerCase();
            }
        }
        //System.out.println("Trying password: " + password);
        if(jcrypt.crypt(salt, password).equals(encryptedPassword))
            return password;

        //toggle case 2
        password = "";
        for(int i = 0; i < word.length(); i++){
            if (i % 2 == 0){
                password += word.substring(i, i+1).toLowerCase();
            }else{
                password += word.substring(i, i+1).toUpperCase();
            }
        }
        //System.out.println("Trying password: " + password);
        if(jcrypt.crypt(salt, password).equals(encryptedPassword))
            return password;

        //prepend and append character
        for (int i = 0; i < alphabet.length; i++){
            password = Character.toString(alphabet[i]) + word.toLowerCase();
            if(jcrypt.crypt(salt, password).equals(encryptedPassword))
                return password;
            password = word.toLowerCase() + Character.toString(alphabet[i]);
            if(jcrypt.crypt(salt, password).equals(encryptedPassword))
                return password;
        }

        //prepend and append uppercase character
        for (int i = 0; i < upcaseAlphabet.length; i++){
            password = Character.toString(upcaseAlphabet[i]) + word.toLowerCase();
            if(jcrypt.crypt(salt, password).equals(encryptedPassword))
                return password;
            password = word.toLowerCase() + Character.toString(upcaseAlphabet[i]);
            if(jcrypt.crypt(salt, password).equals(encryptedPassword))
                return password;
        }

        //prepend and append numbers
        for (int i = 0; i < numbers.length; i++){
            password = Character.toString(numbers[i]) + word.toLowerCase();
            if(jcrypt.crypt(salt, password).equals(encryptedPassword))
                return password;
            password = word.toLowerCase() + Character.toString(numbers[i]);
            if(jcrypt.crypt(salt, password).equals(encryptedPassword))
                return password;
        }

        //prepend and append special characters
        for (int i = 0; i < specialCharacters.length; i++){
            password = Character.toString(specialCharacters[i]) + word.toLowerCase();
            if(jcrypt.crypt(salt, password).equals(encryptedPassword))
                return password;
            password = word.toLowerCase() + Character.toString(specialCharacters[i]);
            if(jcrypt.crypt(salt, password).equals(encryptedPassword))
                return password;
        }

        return null;
    }

}