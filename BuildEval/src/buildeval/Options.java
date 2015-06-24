package buildeval;

import org.kohsuke.args4j.*;  //导入包

public class Options{
	
	
	@Option(name = "-workPath", usage = "Specify path of workspace ")
	public String workPath = "/home/cyno/data/BuildEval/";
	
	@Option(name = "-sourceFile", usage = "Specify source corpus ")
	public String sourceFile = "plain";
	
	@Option(name = "-indexFile", usage = "Specify file of dictionary")
	public String indexFile = "index.sense";
	
	@Option(name = "-dicFile", usage = "Specify file of dictionary")
	public String dicFile = "dic.dat";
	
			
}