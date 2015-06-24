package tagnet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class TagNet{
	
	public static String wsiTagFile = "/home/cyno/data/NetTag/wsiTag.dat";
	public static String mapFile = "/home/cyno/data/NetTag/map.dat";
	public static String withTagFile = "/home/cyno/data/NetTag/withTag.dat";
	
	public static void main(String args[]){
		
		HashMap<String,String[]>senseMap = new HashMap<String,String[]>();
		readMap(senseMap);
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(wsiTagFile)));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(withTagFile)));
			String line = reader.readLine();
			while (line != null ){
				String words[] = line.split(" ");
				for (int i = 0; i < words.length; i ++){
					int flag = words[i].indexOf("|||");
					if (flag != -1){
						try{

							String word = words[i].substring(0, flag);
							String[] ids = senseMap.get(word);
							if (ids != null){
								//System.out.println(Integer.parseInt(words[i].substring(flag + 5)));
								writer.write(word + '#' + ids[Integer.parseInt(words[i].substring(flag + 5))] + ' ');
							}else {
								writer.write(word + ' ');
							}
						}catch(Exception e){
							System.out.println("Error when catch : " + words[i] + e.getMessage());
							e.printStackTrace();
							continue;
						}
						
					}else{
						writer.write(words[i] + ' ');
					}
				}
				writer.write('\n');
				line = reader.readLine();
			}
			reader.close();
			writer.close();
			System.out.println("Transfer successfully");
		}catch(Exception e){
			System.out.println("Error when transfer" + e.getMessage());
			e.printStackTrace();
			
		}
		
		
	}
	
	public static boolean readMap(HashMap<String,String[]>senseMap){
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(mapFile)));
			String line = reader.readLine();
			String word = null;
			String[] oneMap = null;
			int size = 0;
			while(line != null){
				String[] words = line.trim().split(" ");		
				if (line.startsWith("#")){
					word = words[1];
					size = Integer.parseInt(words[2]) ;
					oneMap = new String[size + 1];
				}else{
					//System.out.println(words[0]);
					int num = Integer.parseInt(words[0]);
					oneMap[num] = words[1];
					if ( num == size ){
						senseMap.put(word, oneMap);
					}
				}
				line = reader.readLine();
			}
			reader.close();
			System.out.println("Read Map successfully " );
			return true;
		}catch(Exception e){
			System.out.println("Error when readmap" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}