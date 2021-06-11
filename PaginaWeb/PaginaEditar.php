<!DOCTYPE html>
<html lang="en">
<head>
  <title>Pagina Edició</title>
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
body {
background-color: #000000;
color: #FFFFFF;
}
</style>

<style>
div.centrar {
	color: #FFFFFF;
	text-align: center;
}
</style>

<h1 align="center">Editar Vídeo</h1>
<br>
<br>
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
</form>
<div class="centrar">
<?php 

	$id = $_GET['id'];
	$ref = $_GET['reference'];
	$num = 0;
	if($ref == "LlistatVideosCa"){
		$num = 1;
	}
	else if($ref == "LlistatVideosEs"){
		$num = 2;
	}
	else if ($ref == "LlistatVideosEn"){
		$num = 3;
	}
		echo "<button type='button' class='btn btn-primary btn-lg' onclick='Editar(categoria.value,desc.value,titol.value,url.value,$id,$num)'>Realitzar Edició</button>";
?>
</div>
<br>
<div id="txtHint"></div>
<script>
function Editar(cat,desc,tit,url,id,ref){

	if(cat.length == 0 || desc.length == 0 || tit.length == 0 || url.length == 0){
		document.getElementById("txtHint").innerHTML = "Completi la informació obligatòria siusplau.";
	}
	var urlFinal = url.substring(url.indexOf("watch?v=")+8,url.length);
	if(urlFinal == ""){
		document.getElementById("txtHint").innerHTML = "Posa un enllaç de youtube correcte. Exemple: youtube.com/watch?v=57mfjMVka";
	}
	
	if(ref == 1) {
		firebase.database().ref('LlistatVideosCa/'+id).update({
					'categoria': cat,
					'descVideo': desc,
					'mostrar': '1',
					'numId': id,
					'titol': tit,
					'urlVideo': urlFinal
				});
			document.getElementById("txtHint").innerHTML = "edició correcte.";
			window.location.href = "paginaVisualitzacio.php";
	}
	else if($ref == 2) {
		firebase.database().ref('LlistatVideosEs/'+id).update({
					'categoria': cat,
					'descVideo': desc,
					'mostrar': '1',
					'numId': id,
					'titol': tit,
					'urlVideo': urlFinal
				})
			
			document.getElementById("txtHint").innerHTML = "edició correcte.";
			window.location.href = "paginaVisualitzacio.php";
	}
	else if($ref == 3){
		firebase.database().ref('LlistatVideosEn/'+id).update({
					'categoria': cat,
					'descVideo': desc,
					'mostrar': '1',
					'numId': id,
					'titol': tit,
					'urlVideo': urlFinal
				})
			
			document.getElementById("txtHint").innerHTML = "edició correcte.";
			window.location.href = "paginaVisualitzacio.php";
	}
}
</script>

</body>
</html>