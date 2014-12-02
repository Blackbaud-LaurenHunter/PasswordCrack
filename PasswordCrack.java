import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PasswordCrack {

	private static BufferedReader dictonary;
	private static BufferedReader passwords;


	public static void main(String[] args) {
		try{
			dictonary = new BufferedReader(new FileReader(args[0]));
			passwords = new BufferedReader(new FileReader(args[1]));
            crackPasswords();
			dictonary.close();
			passwords.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

    public static void crackPasswords() throws IOException{
        Pattern pattern = Pattern.compile("[a-z]*:(.*):\\d*:\\d*:([a-zA-Z]*)\\s([a-zA-Z]*):.*", Pattern.CASE_INSENSITIVE);
        Matcher matcher;
        String line;
        String salt;
        String encryptedPassword;
        String firstName;
        String lastName;
        while ((line = passwords.readLine()) != null){
            //System.out.println(line);
            matcher = pattern.matcher(line);
            if (matcher.matches()){
                encryptedPassword = matcher.group(1);
                firstName = matcher.group(2);
                lastName = matcher.group(3);
                salt = encryptedPassword.substring(0, 2);
//                System.out.println(firstName);
//                System.out.println(lastName);
//                System.out.println(encryptedPassword);
//                System.out.println(salt);
            }
        }
    }
}