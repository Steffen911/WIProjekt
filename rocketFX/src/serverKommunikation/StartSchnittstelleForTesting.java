package serverKommunikation;

public class StartSchnittstelleForTesting {

	public static void main(String[] args) {
		FileSchnittstelle sst = new FileSchnittstelle("x", "//Users//Steffen//Dropbox//");
/*		
		try {
			sst.fileWriter(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
		
		sst.fileReader();
	}

}
