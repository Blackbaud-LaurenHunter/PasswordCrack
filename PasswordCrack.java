import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class PasswordCrack {

	private static BufferedReader dictonary;
	private static BufferedReader passwords;


	public static void main(String[] args) {
		try{
			dictonary = new BufferedReader(new FileReader(args[0]));
			passwords = new BufferedReader(new FileReader(args[1]));
			dictonary.close();
			passwords.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}