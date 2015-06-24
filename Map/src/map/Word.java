package map;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map.Entry;

public class Word{
	
	String word;
	String mapPath;
	String mapFile;
	
	Double[][] matrix;
	int high;
	int width;
	int size;
	
	
	Double[][] wsi;
	Integer[] senseid;
	Double[][] wn;
	
	////////////////////Methods////////////////////
	
	// Constructor
	public Word(String word, Options option){
		this.word = word;
		this.mapPath = option.workPath + option.mapPath;
		this.mapFile = option.workPath + "result/map.dat";
		this.size = option.size;
	}
	
	public void setWsi(Double[][] wsi){
		this.wsi = wsi;
		this.high = wsi.length;
		//this.size = wsi[0].length;
	}
	
	public void setWn(Entry<Integer[],Double[][]> wn){
		this.wn = wn.getValue();
		this.senseid = wn.getKey();
		this.width = senseid.length;
	}
	
	public void comDistri(){
		matrix = new Double[high][width];
		for (int i = 0; i < high; i++)
			for (int j = 0; j < width; j++)
				matrix[i][j] = comDistance(i,j);
		
	}
	
	public Double comDistance(int i, int j){
		Double ans = 0.0;
		for (int r = 0; r < size; r++){
			ans += wsi[i][r] * wn[j][r];
		}
		return ans;
	}
	
	public boolean saveMatrix(){

		try{
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapPath + word + ".map")));
			BufferedWriter writerMap = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapFile,true )));
			writerMap.write("# "+ word + " " + high + " " + width + "\n");
			Double maxx =-999999.0;
			int flag=0;
			for (int i = 0; i < high; i++){
				writer.write("\ttopic#" + (i + 1));
				maxx = -999999.0;
				flag = 0;
				for (int j = 0; j < width; j++){
					if ( matrix[i][j] > maxx ){
						maxx = matrix[i][j];
						flag = senseid[j];
					}
					writer.write(senseid[i] + "|||" + matrix[i][j] + "\t");
				}
				writer.write("\n");
				writerMap.write(" " + ( i + 1) + " " + flag + "\n");
			}
			writer.close();
			writerMap.close();
			System.out.println("Save word : " + word + " successfully !");
			return true;
		}catch(Exception e){
			System.out.println("Error when save matrix! :" + word + "\n" + e.getMessage());
			e.printStackTrace();
			return false;
		}
		
	}
}