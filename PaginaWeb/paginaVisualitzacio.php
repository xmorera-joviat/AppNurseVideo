
<?php 
$url = "https://nurseapp-b4a04.firebaseio.com/LlistatVideos.json";

$ch = curl_init();
curl_setopt($ch, CURLOPT_URL,$url);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

$response = curl_exec($ch);
curl_close($ch);

$data = json_decode($response,true);
foreach ($data as $key => $value) {
	echo "<iframe width='560' height='315' src='https://www.youtube.com/embed/".$data[$key]["urlVideo"]."'title='YouTube video player' frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture' allowfullscreen></iframe> \n";
	echo "<br>";
	echo "<br>";
}


?> 