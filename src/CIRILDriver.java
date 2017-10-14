import java.util.Scanner;
import com.google.gson.Gson;

public class CIRILDriver
{

	public static void main(String[] args)
	{
		Scanner s = new Scanner(System.in);
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		String originalWord = gson.fromJson(s.nextLine(), String.class);
		// insert function call from other class to create word output
		System.out.print(gson.toJson(originalWord));
	}

}
