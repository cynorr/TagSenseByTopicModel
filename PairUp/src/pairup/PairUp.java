package pairup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.kohsuke.args4j.CmdLineParser;

public class PairUp{
	
	public static void main(String[] args){
		
		Options option = new Options();
		CmdLineParser parser = new CmdLineParser(option);
		
		if (args.length == 0){
			System.out.println("Poster\t[options...]\t[arguments...]");
			parser.printUsage(System.out);
			//return;
		}
		
		Distri distri = new Distri(option);
		
		System.out.println("Reading tm start...");
		distri.readTm();
		
		
		System.out.println("Transfering starts");
		try{
			BufferedReader withOrderReader = new BufferedReader(new InputStreamReader(new FileInputStream(option.workPath + option.withOrderFile)));
			BufferedWriter wsiTagWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(option.workPath + "wsiTag.dat")));
			
			String line = withOrderReader.readLine();
			while (line != null){
				String words[] = line.split(" ");
				for (int i = 0; i < words.length; i++){

					int flag = words[i].indexOf("|||");
					if (flag != -1){
						String word = words[i].substring(0, flag);
						int r = Integer.parseInt(words[i].substring(flag + 3));
						String topic = distri.get(word, r);
						if (topic != null)
							wsiTagWriter.write(word + "|||" + topic );
						else 
							wsiTagWriter.write(word);
					}else{
						wsiTagWriter.write(words[i]);					
					}
					if (i != words.length - 1)
						wsiTagWriter.write(' ');
				}
				wsiTagWriter.write('\n');
				line = withOrderReader.readLine();
			}
			withOrderReader.close();
			wsiTagWriter.close();
			System.out.println("Transfer successfully");
		}catch(Exception e){
			System.out.println("Error when transfer !" + e.getMessage());
			e.printStackTrace();
		}
		
	}
}