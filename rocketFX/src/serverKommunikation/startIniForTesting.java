package serverKommunikation;

public class startIniForTesting {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerGuiKontakt server = new ServerGuiKontakt("o", "//Users//Steffen//Dropbox//Test//", 500);

		System.out.println(server.getApiSecret());
	}

}
