import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class withUnderLine{
	static String originFile = "/home/cyno/data/BuildEval/origin.dat";
	static String withoutUnderLineFile = "/home/cyno/data/BuildEval/plain.dat";
	
	public static void main(String args[]){
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(originFile)));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(withoutUnderLineFile)));
			
			String line = reader.readLine();
			String sent = "";
			while( line != null){
				String words[] = line.split(" ");
				for (int i = 0; i < words.length; i ++){
					String terms[] = words[i].split("_");
					for (int j = 0; j < terms.length; j++){
						if(terms[j].length() <= 2)
							continue;
						sent += terms[j] + " ";
					}
				}
				sent = sent.trim();
				writer.write(sent + "\n");
				sent = "";				
				line = reader.readLine();
			}
			reader.close();
			writer.close();
			
			System.out.println("plain successfully");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}