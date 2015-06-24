package buildeval;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Index{
	
	String indexFile;
	HashMap<String,String> key2id;
	
	public Index(Options option){
		indexFile = option.workPath + option.indexFile;
		key2id = new HashMap<String,String>();
	}
	
	public boolean readFile(){
		
		Pattern pattern = Pattern.compile("[0-9]*");
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(indexFile)));
			String line = reader.readLine();
			while ( line != null){
				String words[] = line.split(" ");
				Matcher matcher = pattern.matcher(words[1]);
				if ( matcher.matches()){
					key2id.put(words[0], words[1]);
				}
				line = reader.readLine();
			}
		reader.close();
		System.out.println("Read Index file successfully!");
		return true;
		}catch(Exception e){
			System.out.println("Error when read Index file" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public String getId(String key){
		return key2id.get(key);
	}
}