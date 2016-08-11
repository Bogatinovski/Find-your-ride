<?php
	require_once "../../includes/initialize.php";

	$start_destination = $_POST['start'];
	$end_destination = $_POST['end'];
	$sits = $_POST['sits'];
	$cena = $_POST['price'];
	$telefon = $_POST['telefon'];
	$datetime = $_POST['datum'] . " " . $_POST['vreme'];
	$opis = $_POST['opis'];

	$datetime = new DateTime($datetime);
	$datetime = $datetime->format("Y-m-d H:i:s");

	$sql = "INSERT INTO `trip`(`user_id`, `start_destination`, `end_destination`, `sits`, `cena`, `telefon`, `datetime`, `opis`) ";
	$sql .= "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

	$stmt = $db->prepare($sql);
	$stmt->bind_param("iiiiisss",$session->user_id, $start_destination, $end_destination, $sits, $cena, $telefon, $datetime, $opis);
	$stmt->execute();
	echo $stmt->affected_rows;
	echo $stmt->error;
	$stmt->close();

	exitWithStatusCode("HTTP/1.1 200 OK", "");
?>