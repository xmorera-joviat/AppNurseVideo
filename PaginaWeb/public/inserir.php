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
background-color: #4d4d4d;
color: #FFFFFF;
}
</style>

<br>
<div class="centrar">
<h1>Inserir nou Vídeo</h1>
</div>

<br> 
<br>
<!--formulari-->
<div class='row'>

<div class='col'>
<b>Videos en Català:</b>
<br>
<b>Categoria:</b> 
  <br>  
<input id = "categoryCa" type="text" size="60" maxlength="40" placeholder="Ingressa una categoria"> 
<br>
<br>	
<b>Descripció: </b>
<br>
<input id = "descripcioCa" type="text" size="60" maxlength="40" placeholder="descripció"> 
<br>
<br>
<b> Títol: </b>
<br>
<input id = "titolCa" type="text" size="60" maxlength="40" placeholder="títol"> 
</div>

<div class='col'>
<b> Vídeo en castellà:</b>
 <br>
<b> Categoría:</b> 
<br>
<input id = "categoryEs" type="text" size="60" maxlength="40" placeholder="Ingresa una categoria">
<br>
<br>
<b>Descripción:</b>
<br>
<input id = "descripcioEs" type="text" size="60" maxlength="40" placeholder="descripción">
<br>
<br>
<b>Título:</b>
<br>
<input id = "titolEs" type="text" size="60" maxlength="40" placeholder="título">
<br>
<br>
</div>

<div class='col'>
<b> Video in english: </b>	
<br>
<b> Category: </b>
<br>
<input id = "categoryEn" type="text" size="60" maxlength="40" placeholder="Ingresa una categoria">
<br>
<br>
<b> Description: </b>
<br>
<input id = "descripcioEn" type="text" size="60" maxlength="40" placeholder="description">
<br>
<br>
<b> Title: </b>
<br>
<input id = "titolEn" type="text" size="60" maxlength="40" placeholder="title">
<br>
<br>
</div>
</div>
<div class ="centrar">
  <div class="form-group">
    <b for="pwd" style="color:#FFFFFF;">Url:</b>
	<br>
    <input id = "url" type="text" size="200" placeholder="Url">
  </div>
</div>
<div class="centrar">
<!--Php-->
<?php 
$url = "https://nurseapp-b4a04.firebaseio.com/LlistatVideosCa.json";

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
	echo "AMAGAR VÍDEO<input type='checkbox' id='amagat'>";
	echo "<br>";
	echo "<br>";
	echo "<button type='button' class='btn btn-primary btn-lg' onclick='Inserir(url.value,$id)'>Inserir</button>";

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
function Inserir(url,id){
			
		var x = document.getElementById("amagat");
		var mostrar = 1;
		if(x.checked == true){
			mostrar = 0;
		}
		//variables de text.
		var catCa, descCa,titCa;
		var catEs, descEs, titEs;
		var catEn, descEn, titEn;
		
		//variables català
		catCa = document.getElementById('categoryCa').value;
		descCa = document.getElementById("descripcioCa").value;
		titCa = document.getElementById("titolCa").value;
		
		//variables castella
		catEs = document.getElementById("categoryEs").value;
		
		descEs = document.getElementById("descripcioEs").value;
		titEs = document.getElementById("titolEs").value;
		
		//variables anglès
		catEn = document.getElementById("categoryEn").value;
		descEn = document.getElementById("descripcioEn").value;
		titEn = document.getElementById("titolEn").value;
		
		
		if(descCa.length == 0 || titCa.length == 0 || descEs.length == 0 || titEs.length == 0 || descEn == 0 || titEn == 0 ||url.length == 0){
			document.getElementById("txtHint").innerHTML = "Completi la informació obligatòria siusplau.";
		}
		else{
			var urlFinal = url.substring(url.indexOf("watch?v=")+8,url.length);
			if(urlFinal == ""){
				document.getElementById("txtHint").innerHTML = "Posa un enllaç de youtube correcte. Exemple: youtube.com/watch?v=57mfjMVka";
			}
			else{
				firebase.database().ref('LlistatVideosCa/'+id).set({
					'categoria': catCa,
					'descVideo': descCa,
					'mostrar': mostrar,
					'numId': id,
					'titol': titCa,
					'urlVideo': urlFinal
				});
				firebase.database().ref('LlistatVideosEs/'+id).set({
					'categoria': catEs,
					'descVideo': descEs,
					'mostrar': mostrar,
					'numId': id,
					'titol': titEs,
					'urlVideo': urlFinal
				});
				firebase.database().ref('LlistatVideosEn/'+id).set({
					'categoria': catEn,
					'descVideo': descEn,
					'mostrar': mostrar,
					'numId': id,
					'titol': titEn,
					'urlVideo': urlFinal
				})
				.then(result => window.location.reload(result));
				document.getElementById("txtHint").innerHTML= "Inserit correctament.";
			}
		}
}
</script>
</body>