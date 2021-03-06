package poster;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map.Entry;
import java.util.Vector;

public class Corpus{
	
	Options option;
	Dict dict;
	Integer length;
	Integer filterLine;
	
	// Path of files
	String sourceFile;
	String coreFile;
	String withOrderFile;
	String lemmaPath;
	
	// Dynamic variables
	Vector<Entry<String, Integer> > wordVec;
	String withOrderLine;
	
	/////////////////////////////Methods/////////////////////
	
	// Constructor
	public Corpus(Options option){
		
		if (!option.workPath.endsWith(File.separator)){
			option.workPath += File.separator;
		}
		this.option = option;
		length = option.length;
		filterLine = option.filterLine;
		
		// init some path
		sourceFile = option.workPath + option.sourceFile;
		coreFile = option.workPath + "core.dat";
		withOrderFile = option.workPath + "withOrder.dat";
		lemmaPath = option.workPath + "lemmas/";
		
		dict = new Dict(option);
		wordVec = new Vector<Entry<String, Integer> >();
		
		
	
	}
	
	public boolean init(){
		
		System.out.println("Reading Dict starting ... ");
		dict.readDict();
		
		File lemmas = new File(lemmaPath);
		if (!lemmas.isDirectory()){
			lemmas.mkdirs();
		}
		
		return true;
	}
	
	public boolean transfer(){
		try{
			BufferedReader sourceReader = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile)));
			BufferedWriter coreWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(coreFile)));
			BufferedWriter withOrderWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(withOrderFile)));
			
			int counter = 0;
			String line = sourceReader.readLine();
			while (line != null){
				
				if (judge(line)){
					saveLemmas(wordVec, line);
					coreWriter.write(line);
					withOrderWriter.write(withOrderLine + '\n');
					
					
					if ( ++counter % 100 == 0){
						System.out.print('|');
						if (counter % 10000 == 0)
							System.out.println();
					}
					if ( length != -1 && counter >= length){
						break;
					}
				}				
				
				
				line = sourceReader.readLine();
			}
			sourceReader.close();
			coreWriter.close();
			withOrderWriter.close();
			System.out.println("\nTransfer successfully!");
			
			System.out.println("Saving list starts...");
			dict.saveList();
			return true;			
		}catch(Exception e){
			System.out.println("Error when transfer source !"+e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean judge(String line){
		wordVec.clear();
		withOrderLine = "";
		
		String [] words = line.split(" ");
		for (int i = 0; i < words.length; i ++){
			Entry<String, Integer> temp = dict.contains(words[i]);
			if (temp != null){
				if (!wordVec.contains(temp))
					wordVec.add(temp);
				withOrderLine += words[i] + "|||" + (temp.getValue() + 1);
			}else{
				withOrderLine += words[i];
			}
			
			if (i != words.length - 1){
				withOrderLine += ' ';
			}
		}
		return wordVec.size() >= filterLine;
	}
	
	public boolean saveLemmas(Vector<Entry<String,Integer> > wordVec, String line){
		try{
			for (int i = 0; i < wordVec.size(); i++){
				String word = wordVec.get(i).getKey();
				Integer num = wordVec.get(i).getValue() + 1;
				
				BufferedWriter lemmaWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(lemmaPath + word + ".lemma",true),"UTF-8"));
				lemmaWriter.write(line+'\n');
				lemmaWriter.close();
				dict.update(word, num);
			}
			return true;
		}catch(Exception e){
			System.out.println("Error when save lemmas!" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
}