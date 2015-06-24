package buildeval;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Plain{
	
	Options option;
	Index index;
	
	String sourceFile;
	String targetFile;
	String dicFile;
	TreeMap<String, String> word2dic;
	
	
	public Plain(Options option){
		this.option = option;
		sourceFile = option.workPath + option.sourceFile;
		targetFile = option.workPath + "plain";
		dicFile = option.workPath + option.dicFile;
		
		this.index = new Index(option);
		index.readFile();
		
		word2dic = new TreeMap<String,String>();
		
		//System.out.println(index.getId("be%2:42:03::") );
	}
	
	public boolean transfer(){		
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile)));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile),"ISO-8859-1"));
			String line = reader.readLine();
			String senten = "";
			while( line != null){
				if (line.startsWith("<wf")){
					senten += clean(line) + ' ';
				}
				/*else if(line.startsWith("<punc>")){
					senten += line.toCharArray()[6] + ' ';
				}*/
				else if(line.startsWith("</s>")){
					writer.write(senten + ".\n");
					senten = "";
					line = reader.readLine();
					continue;
				}
				
				
				line = reader.readLine();
			}
			
			reader.close();
			writer.close();
			
			System.out.println("Transfer successfully!");
			return true;
		}catch(Exception e){
			System.out.println("Error when transfer" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public String clean(String line){
		int lemmaFlag = line.indexOf("lemma");
		int suffixFlag = line.indexOf('>');
		String word = line.substring(suffixFlag + 1,line.length() - 5);
		
		
		if (lemmaFlag != -1){
			try{
			String words[] = line.split(" ");
			String lemma = words[3].substring(6);
			String seq = words[5].substring(6,words[5].indexOf('>'));
			String key = index.getId(lemma + '%' + seq);
			System.out.println(lemma + '%' + seq + ' ' + key);
			
			word2dic.put(word, lemma);
			return lemma;// + "|||" + key;
			}catch(Exception e){
				return word;
			}
		}else{
			return word;
		}
	}
	
	public boolean saveDic(){
		try{
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dicFile)));
			Iterator<Entry<String,String> > iter = word2dic.entrySet().iterator();
			while( iter.hasNext()){
				Entry<String,String> entry = iter.next();
				writer.write(entry.getKey() + ' ' + entry.getValue());
				
			}
			writer.close();
			System.out.println("Save dic successfully!");
			return true;
		}catch(Exception e){
			System.out.println("Error when save dic" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}