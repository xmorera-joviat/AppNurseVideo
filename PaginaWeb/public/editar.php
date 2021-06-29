<!DOCTYPE html>
<html lang="en">
  <head>
    <title>Pagina Editar</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    
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
      
      body {
      	 background-color: #4d4d4d;
      	 color: #FFFFFF;
      }
    </style>
  </head>
  
  <body>
  	<br>
  	<div class="centrar">
  		<h1>Editar Vídeo</h1>
	</div>
  
  	<br> 
	<br>
   <!-- mostrar les dades existents-->
  
  	<?php 
		
		$id = $_GET['id'];//id del vídeo a editar
			
		echo "<div>";
  		//llistat de vídeos a firebase
  		$url = "https://nurseapp-b4a04.firebaseio.com/LlistatVideosCa.json";
		$urlEs = "https://nurseapp-b4a04.firebaseio.com/LlistatVideosEs.json";
		$urlEn = "https://nurseapp-b4a04.firebaseio.com/LlistatVideosEn.json";
  
  		$ch = curl_init();
		$chEs = curl_init();
		$chEn = curl_init();			
			
  		curl_setopt($ch, CURLOPT_URL,$url);
		curl_setopt($chEs, CURLOPT_URL,$urlEs);
		curl_setopt($chEn, CURLOPT_URL,$urlEn);
			
  		curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
		curl_setopt($chEs, CURLOPT_RETURNTRANSFER, true);
		curl_setopt($chEn, CURLOPT_RETURNTRANSFER, true);
			
  		$response = curl_exec($ch);
		$responseEs = curl_exec($chEs);
		$responseEn = curl_exec($chEn);
  
  		curl_close($ch);
		curl_close($chEs);
		curl_close($chEn);
  
  		$data = json_decode($response,true);
		$dataEs = json_decode($responseEs,true);
		$dataEn = json_decode($responseEn,true);
  
  		//urls finals
  		$urlFinal = substr($url, strpos($url,"com/")+4, strlen($url));
  		echo "<div class='row'>";    
  			echo "<div class='col'>";      
				foreach ($data as $key => $value) {
        			if($value != null){
						if($data[$key]["numId"]== $id){
    					//per a cada element que conte la llista de vídeos obtenim la urlVideo i creem l'iframe corresponent
           					echo "\n <iframe width='460' height='259' src='https://www.youtube.com/embed/".$data[$key]["urlVideo"].
							"'title='YouTube video player' frameborder='0' allow='accelerometer; autoplay; clipboard-write; 
							encrypted-media; gyroscope; picture-in-picture' allowfullscreen></iframe> \n";
                					
    						echo "<br />Títol:";
							echo "<br /><input id = 'titolCa' type='text' size='60' maxlength='60' value='".$data[$key]['titol']."'>";
    					  	echo "<br />Descripció:";
							echo "<br /><textarea id = 'descripcioCa' rows='4' cols='63'>".$data[$key]['descVideo']."</textarea>";
    					  	echo "<br />Categoria:";
							echo "<br /><input id = 'categoriaCa' type='text' size='60' maxlength='60' value='".$data[$key]['categoria']."'>";
    						echo "<br /><br />";
						}
        			}
        		}
				foreach ($dataEs as $key => $value) {
        			if($value != null){
						if($dataEs[$key]["numId"]== $id){
    						echo "<br />Título:";
							echo "<br /><input id = 'titolEs' type='text' size='60' maxlength='60' value='".$dataEs[$key]['titol']."'>";
    					  	echo "<br />Descripción:";
							echo "<br /><textarea id = 'descripcioEs' rows='4' cols='63'>".$dataEs[$key]['descVideo']."</textarea>";
    					  	echo "<br />Categoría:";
							echo "<br /><input id = 'categoriaEs' type='text' size='60' maxlength='60' value='".$dataEs[$key]['categoria']."'>";
    						echo "<br /><br />";
						}
        			}
        		}
				foreach ($dataEn as $key => $value) {
        			if($value != null){
						if($dataEn[$key]["numId"]== $id){
    						echo "<br />Title:";
							echo "<br /><input id = 'titolEn' type='text' size='60' maxlength='60' value='".$dataEn[$key]['titol']."'>";
    					  	echo "<br />Description:";
							echo "<br /><textarea id = 'descripcioEn' rows='4' cols='63'>".$dataEn[$key]['descVideo']."</textarea>";
    					  	echo "<br />Category:";
							echo "<br /><input id = 'categoriaEn' type='text' size='60' maxlength='60' value='".$dataEn[$key]['categoria']."'>";
    						echo "<br /><br />";
						}
            		}
           		}
						
       		echo "</div>";
		echo "</div>";    
		echo "<div class='centrar'>";
   		  	echo "<br>";
  			echo "<br>";
  			echo "<button type='button' class='btn btn-primary btn-lg' onclick='Inserir(url.value,$id)'>Actualitzar</button>";
		echo "</div>";
	?>
  		
  	<br>
  	<br>
  
  	<div id="txtHint"></div>
  	   <br>
  	   <br>
	<div>   
  	   <button type='button' class='btn btn-primary btn-lg' onclick="window.location.href = 'visualitzar.php'">Tornar a la Visualització</button>
  	</div>
  
  
  
    <script>
  		function Inserir(url,id){
		
		document.getElementById("txtHint").innerHTML="pendent d'actualitzar... en breu s'implementarà.";
	/*	
			document.getElementById("txtHint").innerHTML =url;
  			
  			//variables de text.
  			var titCa, descCa,catCa;
  			var titEs, descEs, catEs;
  			var titEn, descEn, catEn;
  		
  			//variables català
			titCa = document.getElementById("titolCa").value;
  			catCa = document.getElementById('categoriaCa').value;
  			descCa = document.getElementById("descripcioCa").value;
  		
  			//variables castella
			titEs = document.getElementById("titolEs").value;
  			catEs = document.getElementById("categoriaEs").value;
  			descEs = document.getElementById("descripcioEs").value;
  				
  			//variables anglès
  			titEn = document.getElementById("titolEn").value;
  			descEn = document.getElementById("descripcioEn").value;
  			catEn = document.getElementById("categoriaEn").value;
  		
  			
  			if(descCa.length == 0 || titCa.length == 0 || descEs.length == 0 || titEs.length == 0 || descEn == 0 || titEn == 0 || url.length == 0){
  				document.getElementById("txtHint").innerHTML = "ompli els camps si us plau.";
  			}
  			else if(descCa.length == 0 || titCa.length == 0 || descEs.length == 0 || titEs.length == 0 || descEn == 0 || titEn == 0 ||url.length > 0){
  				 document.getElementById("txtHint").innerHTML = "Posa un enllaç de youtube correcte. Exemple: youtube.com/watch?v=57mfjMVka";
  			
  				 var urlFinal1 = url.substring(url.indexOf("watch?v=")+8,url.length);
  				 if(urlFinal1 == ""){
  				 	document.getElementById("txtHint").innerHTML = "Posa un enllaç de youtube correcte. Exemple: youtube.com/watch?v=57mfjMVka";
  				 }
  				 else{
  				 	firebase.database().ref('LlistatVideosCa/'+id).update({
  						'urlVideo': urlFinal1
  					});
  					firebase.database().ref('LlistatVideosEs/'+id).update({				
  						'urlVideo': urlFinal1
  					});
  					firebase.database().ref('LlistatVideosEn/'+id).update({
  						'urlVideo': urlFinal1
  					})
  					.then(result => window.location.reload(result));
  					document.getElementById("txtHint").innerHTML= "Inserit correctament.";
  				}
  			}
  			else{
  				 var urlFinal = url.substring(url.indexOf("watch?v=")+8,url.length);
  				 if(urlFinal == ""){
  				 	document.getElementById("txtHint").innerHTML = "Posa un enllaç de youtube correcte. Exemple: youtube.com/watch?v=57mfjMVka";
  				}
  				else{
  					 firebase.database().ref('LlistatVideosCa/'+id).update({
  					 	'categoria': catCa,
  						'descVideo': descCa,
  						'numId': id,
  						'titol': titCa,
  						'urlVideo': urlFinal
  					});
  					firebase.database().ref('LlistatVideosEs/'+id).update({
  						'categoria': catEs,
  						'descVideo': descEs,
  						'numId': id,
  						'titol': titEs,
  						'urlVideo': urlFinal
  					});
  					firebase.database().ref('LlistatVideosEn/'+id).update({
  						'categoria': catEn,
  						'descVideo': descEn,
  						'numId': id,
  						'titol': titEn,
  						'urlVideo': urlFinal
  					})
  					.then(result => window.location.reload(result));
  					document.getElementById("txtHint").innerHTML= "Inserit correctament.";
  				}
  			}
  		}
	*/
  	</script>
  </body>
</html>