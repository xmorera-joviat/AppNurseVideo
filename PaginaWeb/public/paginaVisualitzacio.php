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
color: #FFFFFF;
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
echo "<div>";
$urlCa = "https://nurseapp-b4a04.firebaseio.com/LlistatVideosCa.json";
$urlEn = "https://nurseapp-b4a04.firebaseio.com/LlistatVideosEn.json";
$urlEs = "https://nurseapp-b4a04.firebaseio.com/LlistatVideosEs.json";

$ch = curl_init();
curl_setopt($ch, CURLOPT_URL,$urlCa);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

$response = curl_exec($ch);
curl_close($ch);

$data = json_decode($response,true);

//urls finals
$urlCaFinal = substr($urlCa, strpos($urlCa,"com/")+4, strlen($urlCa));
$urlEsFinal = substr($urlEs, strpos($urlEs,"com/")+4, strlen($urlEs));
$urlEnFinal = substr($urlEn, strpos($urlEn,"com/")+4, strlen($urlEn));
echo "<div class='row'>";


echo "<div class='col'>";
echo "<b>Videos en Català:</b>";
echo "<br>";
//Videos Català
foreach ($data as $key => $value) {
	if($value != null){
		$id = $data[$key]["numId"];
		echo "\n <iframe width='560' height='315' src='https://www.youtube.com/embed/".$data[$key]["urlVideo"]."'title='YouTube video player' frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture' allowfullscreen></iframe> \n";
		echo "<br>";
		echo "<button type='button' class='btn btn-outline-warning' onclick='Editar($id)'>Editar Dades</button> \n";
		if($data[$key]["mostrar"] == 1){
			echo "<button type='button' class = 'btn btn-outline-success' onclick='Amagar(0,$id)'> Amagar Vídeo</button> \n";
		}
		else if($data[$key]["mostrar"] == 0){
			echo "<button type='button' class = 'btn btn-outline-success' onclick='Amagar(1,$id)'> Mostrar Vídeo</button> \n";
		}
		echo "<button type='button' class='btn btn-outline-danger' onclick='Esborrar($id)'>Esborrar</button> \n";
		echo "<br>";
		echo "<br>";
	}
}
echo"</div>";
//Videos Castellà
$chEs = curl_init();
curl_setopt($chEs,CURLOPT_URL,$urlEs);
curl_setopt($chEs,CURLOPT_RETURNTRANSFER,true);

$responseEs = curl_exec($chEs);
curl_close($chEs);

$dataEs = json_decode($responseEs,true);
echo "<div class='col'>";
echo "<b>Videos en Castellà:</b>";
echo "<br>";
foreach ($dataEs as $key => $valueEs) {
	if($valueEs != null){
		$id = $dataEs[$key]["numId"];
		echo "\n <iframe width='560' height='315' src='https://www.youtube.com/embed/".$dataEs[$key]["urlVideo"]."'title='YouTube video player' frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture' allowfullscreen></iframe> \n";
		echo "<br>";
		echo "<button type='button' class='btn btn-outline-warning' onclick='Editar($id)'>Editar Dades</button> \n";
		if($dataEs[$key]["mostrar"] == 1){
			echo "<button type='button' class = 'btn btn-outline-success' onclick='Amagar(0,$id)'> Amagar Vídeo</button> \n";
		}
		else if($dataEs[$key]["mostrar"] == 0){
			echo "<button type='button' class = 'btn btn-outline-success' onclick='Amagar(1,$id)'> Mostrar Vídeo</button> \n";
		}
		echo "<button type='button' class='btn btn-outline-danger' onclick='Esborrar($id)'>Esborrar</button> \n";
		echo "<br>";
		echo "<br>";
		}
}
echo "</div>";

//Videos anglès.
$chEn = curl_init();
curl_setopt($chEn,CURLOPT_URL,$urlEn);
curl_setopt($chEn,CURLOPT_RETURNTRANSFER,true);

$responseEn = curl_exec($chEn);
curl_close($chEn);

$dataEn = json_decode($responseEn,true);
echo "<div class='col'>";
echo "<b> Vídeos en Anglès:</b>";
echo "<br>";
foreach ($dataEn as $key => $valueEn) {
	if($valueEn != null) {
		$id = $dataEn[$key]["numId"];
		echo "\n <iframe width='560' height='315' src='https://www.youtube.com/embed/".$dataEn[$key]["urlVideo"]."'title='YouTube video player' frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture' allowfullscreen></iframe> \n";
		echo "<br>";
		echo "<button type='button' class='btn btn-outline-warning' onclick='Editar($id)'>Editar Dades</button> \n";
		if($dataEn[$key]["mostrar"] == 1){
			echo "<button type='button' class = 'btn btn-outline-success' onclick='Amagar(0,$id)'> Amagar Vídeo</button> \n";
		}
		else if($dataEn[$key]["mostrar"] == 0){
			echo "<button type='button' class = 'btn btn-outline-success' onclick='Amagar(1,$id)'> Mostrar Vídeo</button> \n";
		}
		echo "<button type='button' class='btn btn-outline-danger' onclick='Esborrar($id)'>Esborrar</button> \n";
		echo "<br>";
		echo "<br>";
	}
}
echo "</div>";
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
	if(confirm('Estas segur que vols esborrar el vídeo?')){
		firebase.database().ref('LlistatVideosCa/'+id).remove();
		firebase.database().ref('LlistatVideosEn/'+id).remove();
		firebase.database().ref('LlistatVideosEs/'+id).remove()
			.then(result => window.location.reload(result));
	}
	
}
</script>
<script>
function SignOut(){
	firebase.auth().signOut();
	window.location.href = "PaginaInici.html";
}
</script>
<script>
function Editar(id){
	window.location.href = "PaginaEditar.php?id="+id;  	
}
</script>
<script>
function Amagar(amagar,id){
	if(amagar == 0){
	
		firebase.database().ref('LlistatVideosCa/'+id).update({
					'mostrar': 0
				});
		firebase.database().ref('LlistatVideosEs/'+id).update({
					'mostrar': 0
				});
		firebase.database().ref('LlistatVideosEn/'+id).update({
					'mostrar': 0
				}).then(result => window.location.reload(result));
	}
	else if(amagar == 1){
		
	
		firebase.database().ref('LlistatVideosCa/'+id).update({
					'mostrar': 1
				});
		firebase.database().ref('LlistatVideosEs/'+id).update({
					'mostrar': 1
				});
		firebase.database().ref('LlistatVideosEn/'+id).update({
					'mostrar': 1
				}).then(result => window.location.reload(result));
	}
}
</script>
</body>