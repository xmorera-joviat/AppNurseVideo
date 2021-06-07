<!DOCTYPE html>
<html lang="en">
<head>
  <title>Pagina Visualització</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>

<body>
<script src="https://www.gstatic.com/firebasejs/7.15.5/firebase-app.js"></script>
<script src="https://www.gstatic.com/firebasejs/7.15.5/firebase-auth.js"></script>
<script src="https://www.gstatic.com/firebasejs/7.15.5/firebase-database.js"></script> 

<script>
  // Your web app's Firebase configuration
  // For Firebase JS SDK v7.20.0 and later, measurementId is optional
  var firebaseConfig = {
    apiKey: "AIzaSyCe7NPMFUTamWIH9fdk0xjk9pj3KllxkrI",
    authDomain: "nurseapp-b4a04.firebaseapp.com",
    databaseURL: "https://nurseapp-b4a04.firebaseio.com",
    projectId: "nurseapp-b4a04",
    storageBucket: "nurseapp-b4a04.appspot.com",
    messagingSenderId: "243441860725",
    appId: "1:243441860725:web:47745a69e80569c627b83c",
    measurementId: "G-HL8B5LYF0W"
  };
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);
  firebase.analytics();
</script>
<style>
div.centrar {
	color: #FFFFFF;
	text-align: center;
}
</style>
<style>
body {
background-color: #000000;
}
</style>

<div id="txtHint"></div>
<br>

<div class="centrar">
<br>
<br>
<button  class="btn btn-outline-success" onclick="window.location.href = 'PaginaInserir.php'" >Inserir Nou Vídeo</button>
</div>
<?php 
$url = "https://nurseapp-b4a04.firebaseio.com/LlistatVideos.json";

$ch = curl_init();
curl_setopt($ch, CURLOPT_URL,$url);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

$response = curl_exec($ch);
curl_close($ch);

$data = json_decode($response,true);

foreach ($data as $key => $value) {
	if($value != null){
		$id = $data[$key]["numId"];
		echo "\n <iframe width='560' height='315' src='https://www.youtube.com/embed/".$data[$key]["urlVideo"]."'title='YouTube video player' frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture' allowfullscreen></iframe> \n";
		echo "<br>";
		echo "<p><button type='button' class='btn btn-outline-danger' onclick='Esborrar($id)'>Esborrar</button> \n";
		echo "<br>";
		echo "<br>";
	}
}
?>
</div>
<div class="centrar">
<br>
<br>
<button  class="btn btn-primary btn-lg" onclick="SignOut()" >Sortir</button> 
<br>
<br>
</div>
<script>
function Esborrar(id){
	
	firebase.database().ref('LlistatVideos/'+id).remove();
}
</script>
<script>
function SignOut(){
	firebase.auth().signOut();
	window.location.href = "PaginaInici.html";
}
</script>
</body>