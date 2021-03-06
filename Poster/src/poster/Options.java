package poster;

import org.kohsuke.args4j.*;  //导入包

public class Options{
	
	
	@Option(name = "-workPath", usage = "Specify path of workspace ")
	public String workPath = "/home/cyno/data/wsitag/";
	
	@Option(name = "-sourceFile", usage = "Specify source corpus ")
	public String sourceFile = "large2.en";
	
	@Option(name = "-dictFile", usage = "Specify file of dictionary")
	public String dictFile = "index.verb";
	
	@Option(name = "-length", usage = "Specify the sum of source lines")
	public Integer length = 10000;
	
	@Option(name = "-filterLine", usage = "Specify the least sum of words tagged each line")
	public Integer filterLine = -1;

	@Option(name = "-filterLemmaCount", usage = "Specify the least sum of leammas each word")
	public Integer filterLemmaCount = 100;

	@Option(name = "-stopsFile", usage = "Specify the file of stop words")
	public String stopsFile = "stop.en";
		
}