import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.truman.CIRIL.WordLogic;

public class CIRILDriver
{

	public static void main(String[] args)
	{
		try {
			System.in.read(new byte[4]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scanner s = new Scanner(System.in);
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		JsonParser jsonParser = new JsonParser();
		JsonObject wordObject = jsonParser.parse(s.nextLine()).getAsJsonObject();
		// insert function call from other class to create word output
		WordLogic wordLogic = new WordLogic(wordObject.get("word").getAsString());
		log(wordObject.get("word").getAsString());
		String jsonMessage = gson.toJson(wordLogic.assembleWordList());
		sendMessage(jsonMessage);
		log(jsonMessage);
		s.close();

	}
	
	public static void sendMessage(String message) {
		try {
			System.out.write(getIntBytes(message.length()));
			System.out.write(message.getBytes("UTF-8"));
			System.out.flush();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	  private static void log(String message) {
		    File file = new File("log.txt");

		    try {
		      if (!file.exists()) {
		        file.createNewFile();
		      }

		      FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
		      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

		      DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		      Date date = new Date();

		      bufferedWriter.write(dateFormat.format(date) + ": " + message + "\r\n");
		      bufferedWriter.close();
		    } catch (Exception e) {
		      log("ERROR ==> Method (log)" + e.getMessage());
		      e.printStackTrace();
		    }
		  }
	
	public static byte[] getIntBytes(int number) {
		byte[] bytes = new byte[4];
		for(int i = 0; i < 4; i++) {
			bytes[i] = (byte) (0xFF & number);
			number >>= 8;
		}
		return bytes;
	}

}
