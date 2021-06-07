<!DOCTYPE html>
<html lang="en">
<head>
  <title>Pagina Inserció</title>
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

<br>
<div class="centrar">
<h1>Inserir nou Vídeo</h1>
</div>

<br> 
<br>
<!--formulari-->
<form action="/action_page.php" class="was-validated">
  <div class="form-group">
    <b for="uname" style="color:#FFFFFF;">Categoria:</b>
	<br>
    <input type="text" class="form-control" id="categoria" placeholder="Categoria" name="cat" required>
    <div class="valid-feedback">Vàlid.</div>
    <div class="invalid-feedback">No obligatori.</div>
  </div>
  <div class="form-group">
    <b for="pwd" style="color:#FFFFFF;">descripció:</b>
    <input type="text" class="form-control" id="desc" placeholder="Descripció del Vídeo" name="des" required>
    <div class="valid-feedback">Vàlid.</div>
    <div class="invalid-feedback">Camp obligatori.</div>
  </div>
  <div class="form-group">
    <b for="pwd" style="color:#FFFFFF;">Titol:</b>
    <input type="text" class="form-control" id="titol" placeholder="Titol Vídeo" name="tl" required>
    <div class="valid-feedback">Vàlid.</div>
    <div class="invalid-feedback">Camp obligatori.</div>
  </div>
  <div class="form-group">
    <b for="pwd" style="color:#FFFFFF;">Url:</b>
    <input type="text" class="form-control" id="url" placeholder="URL Vídeo" name="urlvideo" required>
    <div class="valid-feedback">Vàlid.</div>
    <div class="invalid-feedback">Camp obligatori.</div>
  </div>


<div class="centrar">
<!--Php-->
<?php 
$url = "https://nurseapp-b4a04.firebaseio.com/LlistatVideos.json";

$ch = curl_init();
curl_setopt($ch, CURLOPT_URL,$url);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

$response = curl_exec($ch);
curl_close($ch);

$data = json_decode($response,true);
$id = 0;
foreach ($data as $key => $value) {
		$id++;
		
	}
	echo "<button type='button' class='btn btn-primary btn-lg' onclick='Inserir(categoria.value,desc.value,titol.value,url.value,$id)'>Inserir</button>";
?>
		
<br>
<br>

<div id="txtHint"></div>
<br>
<br>
<button type='button' class='btn btn-primary btn-lg' onclick="window.location.href = 'paginaVisualitzacio.php'">Torna a la Visualització</button>
</div>


<!--Funcions-->
  <script>
function Inserir(cat,desc,title,url,id){
		
			document.getElementById("txtHint").innerHTML = id;
		if(desc.length == 0 || title.length == 0 || url.length == 0){
			document.getElementById("txtHint").innerHTML = "Completi la informació obligatòria siusplau.";
		}
		else{
			var urlFinal = url.substring(url.indexOf("watch?v=")+8,url.length);
			if(urlFinal == ""){
				document.getElementById("txtHint").innerHTML = "Posa un enllaç de youtube correcte. Exemple: youtube.com/watch?v=57mfjMVka";
			}
			else{
				firebase.database().ref('LlistatVideos/'+id).set({
					'categoria': cat,
					'descVideo': desc,
					'numId': id,
					'texTitolVideos': title,
					'urlVideo': urlFinal
				});
				
				document.getElementById("txtHint").innerHTML= "Inserit correctament.";
			}
		}
}
</script>
</body>