package buildeval;

public class BuildEval{
	
	public static void main(String[] args){
		Options option = new Options();
		Plain plain = new Plain(option);
		
		System.out.println("transfer starts...");
		plain.transfer();
		
		System.out.println("Save Dic starts");
		plain.saveDic();
	}
}