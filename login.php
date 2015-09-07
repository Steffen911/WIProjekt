HalloWelt
<?php session_start();
if (!empty($_POST['email']) AND !empty($_POST['pwd']))
{
	
//DB Verbindung
require_once 'settings.php';
	
//Felder übergeben
$Email = $_POST['email'];
$Password = sha1($_POST['pwd']);	

//Abfrage in der DB
$Query = mysql_query("SELECT * FROM User WHERE Email='$Email' AND Passwort='$Password'");
$NumRows = mysql_num_rows($Query);

if($NumRows != 0)
{
	while($Row = mysql_fetch_assoc($Query))
	{
		$Id = $Row['Id'];
		
		if($Row['ReservationCat']!=0){
			$_SESSION['ReservationCat'] = $Row['ReservationCat'];
		}
	}
}
else
{
	die("Email oder Passwort falsch!");
	exit();
}

//Sessiondaten schreiben
$_SESSION['name'] = $Email;
$_SESSION['uId'] = $Id;

//Link Weiterleitung
$cat = $_POST['cat'];
$activemenu = $_POST['activemenu'];

if(isset($_POST['cat']) AND isset($_POST['activemenu'])){	header('Location: index.php?cat='.$cat.'&activemenu='.$activemenu.'');}
else{														header('Location: index.php');}  

exit();
}
else
{
    echo "Login fehlgeschlagen!";
}
?>
