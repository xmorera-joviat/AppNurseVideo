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
  
    <br />
    <div class="centrar">
    	   <h1>Inserir nou Vídeo</h1>
    </div>
  
  	<br /> 
    <br />
    <!--formulari-->
    <div class='row'> 
      <div class='col'>
        <b>Català:</b>
        <br />
		<b> Títol: </b>
        <br />
        <input id = "titolCa" type="text" size="60" maxlength="60" placeholder="títol">
        <br /><br />
        <b>Descripció: </b>
        <br />
		<textarea id = "descripcioCa" rows="4" cols="63"></textarea> 
        <br /><br />
        <b>Categoria:</b> 
        <br />  
        <input id = "categoryCa" type="text" size="60" maxlength="60" placeholder="categoria"> 
        <br /><br /> 
      </div>
    
      <div class='col'>
        <b> Español:</b>
        <br />
  	  	<b>Título:</b>
        <br />
        <input id = "titolEs" type="text" size="60" maxlength="60" placeholder="título">   
        <br />
        <br />
        <b>Descripción:</b>
        <br />
		<textarea id = "descripcioEs" rows="4" cols="63"></textarea>
        <br />
        <br />
        <b> Categoría:</b> 
        <br />
        <input id = "categoryEs" type="text" size="60" maxlength="60" placeholder="categoría">
        <br />
        <br />
      </div>
      
      <div class='col'>
        <b> English: </b>	
        <br />
  	  	<b> Title: </b>
        <br />
 		<input id = "titolEn" type="text" size="60" maxlength="60" placeholder="title"> 
        <br />
        <br />
        <b> Description: </b>
        <br />
		<textarea id = "descripcioEn" rows="4" cols="63"></textarea>
<!--        <input id = "descripcioEn" type="text" size="60" maxlength="58" placeholder="description"> -->
        <br />
        <br />
        <b> Category: </b>
        <br />
        <input id = "categoryEn" type="text" size="60" maxlength="60" placeholder="category">
        <br />
        <br />
      </div>
    </div>
	
    <div class ="centrar">
      <div class="form-group">
        <b for="pwd" style="color:#FFFFFF;">Url:</b>
    	<br />
        <input id = "url" type="text" size="100" placeholder="Url">
      </div>
    </div>
	
    <div class="centrar">
      <?php
	  
	  //fem un recorregut de la base de dades per obtenir l'index (revisar aquest mètode i optimitzar)
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
        echo "<br />";
        echo "<br />";
        			
        echo "<button type='button' class='btn btn-primary btn-lg' onclick='Inserir(url.value,$id)'>Inserir</button>";
      ?>
      		
      <br />
      <br />
      
      <div id="txtHint"></div>
      <br />
      <br />
	  
      <button type='button' class='btn btn-primary btn-lg' onclick="window.location.href = 'visualitzar.php'">Tornar a la Visualització</button>
    </div>
    
    
    <!--Funcions-->
	<script>
        function Inserir(url,id){
        			
        	var x = document.getElementById("amagat");
        	var mostrar = 1;
			var urlFinal="";
        	if(x.checked == true){
        		mostrar = 0;
        	}
        	//variables de text.
        	var catCa, descCa,titCa;
        	var catEs, descEs, titEs;
        	var catEn, descEn, titEn;
        	
        	//variables català
        	titCa = document.getElementById("titolCa").value;
    		descCa = document.getElementById("descripcioCa").value;
    		catCa = document.getElementById('categoryCa').value;
        		
        	//variables castella
        	titEs = document.getElementById("titolEs").value;
        	descEs = document.getElementById("descripcioEs").value;
        	catEs = document.getElementById("categoryEs").value;
        	
        	//variables anglès
        	titEn = document.getElementById("titolEn").value;
        	descEn = document.getElementById("descripcioEn").value;
        	catEn = document.getElementById("categoryEn").value;
        		
        	if(descCa.length == 0 || titCa.length == 0 || descEs.length == 0 || titEs.length == 0 || descEn == 0 || titEn == 0 ||url.length == 0){
        		document.getElementById("txtHint").innerHTML = "Completi tots els camps si us plau.";
        	}
        	else{
				
				if (url.indexOf("youtu.be/")!== -1){
        		   urlFinal = url.substring(url.indexOf("youtu.be/")+9,url.length);
				}
				if (url.indexOf("youtube.com/")!==-1){
					 urlFinal = url.substring(url.indexOf("watch?v=")+8,url.length);
				}
				//document.getElementById("txtHint").innerHTML = urlFinal;		 
    		
    			
    		
      		if(urlFinal == ""){
        			document.getElementById("txtHint").innerHTML = "Introduir un enllaç de youtube correcte. Exemple: https://youtu.be/vX3krP6JmOY o https://youtube.com/watch?v=vX3krP6JmOY";
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
 </html>