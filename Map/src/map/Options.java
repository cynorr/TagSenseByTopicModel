package map;

import org.kohsuke.args4j.*;  //导入包

public class Options{
	
	
	@Option(name = "-workPath", usage = "Specify path of workspace ")
	public String workPath = "/home/cyno/data/map/";
	
	@Option(name = "-vecFile", usage = "Specify source corpus ")
	public String vecFile = "vectors.txt";

	@Option(name = "-indexFile", usage = "Specify index file ")
	public String indexFile = "dict/index.verb";

	@Option(name = "-dataFile", usage = "Specify index file ")
	public String dataFile = "dict/data.verb";
	
	@Option(name = "-stopFile", usage = "Specify stop file ")
	public String stopFile = "stop.en";	

	@Option(name = "-wordListFile", usage = "Specify index file ")
	public String wordListFile = "num_test_instances.all.txt";	
	
	@Option(name = "-datPath", usage = "Specify file of dictionary")
	public String datPath = "topic_distri/";
	
	@Option(name = "-size", usage = "Specify the sum of source lines")
	public Integer size = 200;
	
	@Option(name = "-length", usage = "Specify the max sum of words")
	public Integer length = 4000000;
	
	@Option(name = "-mapPath", usage = "Specify the path of map")
	public String mapPath = "mapFile/";
		
}