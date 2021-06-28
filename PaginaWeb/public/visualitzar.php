<!DOCTYPE html>
<html lang="en">
  <head>
    <title>Visualitzar Vídeos</title>
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
  		background-color: #4D4D4D;
  		color: #FFFFFF;
  		}
  	</style>
  </head>
  
  <body>
  
  <!-- comentari intern -->	<script>console.log("Prova consola html linia 46");</script>
  
  	<div id="txtHint"></div>
  	<br />
  	<div class="centrar">
  		<br />
  		<br />
  		<button  class="btn btn-outline-success" onclick="window.location.href = 'inserir.php'" >Inserir Nou Vídeo</button>
  	</div>
  		<?php 
  			echo "<div>";
  			//llistat de vídeos a firebase
  			$url = "https://nurseapp-b4a04.firebaseio.com/LlistatVideosCa.json";
  
  
  
  			$ch = curl_init();
  			curl_setopt($ch, CURLOPT_URL,$url);
  			curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
  
  			$response = curl_exec($ch);
  
  			curl_close($ch);
  
  			$data = json_decode($response,true);
  
  			//urls finals
  			$urlFinal = substr($url, strpos($url,"com/")+4, strlen($url));
  
    			echo "<div class='row'>";    
        			echo "<div class='col'>";
            			echo "<b>Llistat de Videos:</b>";
            			echo "<br />";
            
            			foreach ($data as $key => $value) {
            				if($value != null){
            					$id = $data[$key]["numId"];
            					//per a cada element que conte la llista de vídeos obtenim la urlVideo i creem l'iframe corresponent
            					echo "\n <iframe width='560' height='315' src='https://www.youtube.com/embed/".$data[$key]["urlVideo"]."'title='YouTube video player' frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture' allowfullscreen></iframe> \n";
            					
								echo "<br />Títol: ".$data[$key]["titol"];
							  	echo "<br />Descripció: ".$data[$key]["descVideo"];
							  	echo "<br />Categoria: ".$data[$key]["categoria"];
								
								echo "<br /><br />";
            					echo "<button type='button' disabled class='btn btn-outline-warning' onclick='editar($id)'>Editar Dades</button> \n";//botó desabilitat
            					if($data[$key]["mostrar"] == 1){
            						echo "<button type='button' class = 'btn btn-outline-success' onclick='amagar(0,$id)'> Amagar Vídeo</button> \n";
            					}
            					else if($data[$key]["mostrar"] == 0){
            						echo "<button type='button' class = 'btn btn-outline-success' onclick='amagar(1,$id)'> Mostrar Vídeo</button> \n";
            					}
            					echo "<button type='button' class='btn btn-outline-danger' onclick='esborrar($id)'>Esborrar</button> \n";
            					echo "<br />";
            					echo "<br />";
            				}
            			}
        			echo "</div>";
				echo "</div>";    
  		?>
  	</div>
  	<div class="centrar">
  		<br />
  		<br />
  		<button  class="btn btn-primary btn-lg" onclick="signOut()" >Sortir</button> 
  		<br />
  		<br />
  	</div>
  	<script>
  	function esborrar(id){
  		if(confirm('Estas segur que vols esborrar el vídeo?')){
  			firebase.database().ref('LlistatVideosCa/'+id).remove();
  			firebase.database().ref('LlistatVideosEn/'+id).remove();
  			firebase.database().ref('LlistatVideosEs/'+id).remove()
  				.then(result => window.location.reload(result));
  		}
  		
  	}
  	</script>
  
  	<script>
  	function editar(id){
  		window.location.href = "editar.php?id="+id;  	
  	}
  	</script>
  	<script>
  	function amagar(amagar,id){
  		if(amagar == 0){
  			firebase.database().ref('LlistatVideosCa/'+id).update({
  						'mostrar': 0}).then(result => window.location.reload(result));
  		}
  		else if(amagar == 1){
  			firebase.database().ref('LlistatVideosCa/'+id).update({
  						'mostrar': 1}).then(result => window.location.reload(result));
  			}
  		}
  		</script>
  		<script>
  			function signOut(){
  				firebase.auth().signOut();
  				window.location.href = "index.html";
  			}
  	</script>
  </body>
</html>