<?php require('Pusher.php');

$app_id = '142236';
$app_key = 'd7d48c4729507d0b320f';
$app_secret = 'b8130fe6eec5ae953e6a';

$pusher = new Pusher(
  $app_key,
  $app_secret,
  $app_id,
  array('encrypted' => true)
);


if ($_GET['pw'] == "rocket") {
  echo $pusher->socket_auth($_POST['channel_name'], $_POST['socket_id']);
}
else
{
  exit(0);
}
?>