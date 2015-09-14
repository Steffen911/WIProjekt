package schnittstellen;

public class main {

	public static void main (String[] args) {
		

		schnittstellen sst = new schnittstellen("x", "//Users//Steffen//Dropbox//WI-Projekt//Server+Technik//");
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