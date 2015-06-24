import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class RightCorpus{
	
	static String sourceFile = "/home/cyno/corpus/large2.en";
	static String targetFile = "/home/cyno/corpus/large2.bet";
	static String dicFile = "/home/cyno/data/BuildEval/dic.dat";
	
	public static void main(String args[] ){
		
		HashMap<String,String> word2dic = new HashMap<String,String>();
		
		
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile)));
			BufferedReader readerDic = new BufferedReader(new InputStreamReader(new FileInputStream(dicFile)));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile)));
			
			String line = readerDic.readLine();
			while(line != null){
				String words[] = line.split(" ");
				try{
					word2dic.put(words[0], words[1]);
				}catch(Exception e){
					line = readerDic.readLine();
					continue;
				}
				
				line = readerDic.readLine();	
			}
			readerDic.close();
			System.out.println("Build dic successfully!");
			
			line = reader.readLine();
			while(line != null){
				String[] words = line.split(" ");
				for ( int i = 0; i < words.length; i++){
					if(word2dic.containsKey(words[i])){
						writer.write(word2dic.get(words[i]));
					}else{
						writer.write(words[i]);
					}
					
					if (i == words.length -1){
						writer.write("\n");
					}else{
						writer.write(' ');
					}
				}
				line = reader.readLine();
			}
			System.out.println("Transfer successfully!");
			
			reader.close();
			writer.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
}