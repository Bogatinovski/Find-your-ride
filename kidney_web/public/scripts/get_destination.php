<?php
	require_once "../../includes/initialize.php";

	$pattern = $_GET['pattern'];

	$sql = "SELECT `destination_id`, `destination` FROM `destinations` WHERE LOWER(`destination`) LIKE '{$pattern}%'";
	$stmt = $db->prepare($sql);
	$stmt->execute();
	$stmt->bind_result($destination_id, $destination);

	$result = array();

	while($stmt->fetch())
		array_push($result, array("destination_id"=>$destination_id, "destination"=>$destination));
	$stmt->close();

	exitWithStatusCode("HTTP/1.1 200 OK", json_encode($result));
?>