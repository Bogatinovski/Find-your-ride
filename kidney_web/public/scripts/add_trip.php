<?php
	require_once "../../includes/initialize.php";

	$email = $_POST['email'];
	$start_destination = $_POST['od'];
	$end_destination = $_POST['do'];
	$sits = $_POST['mesta'];
	$cena = $_POST['cena'];
	$telefon = $_POST['telefon'];
	$datetime = $_POST['datum'] . " " . $_POST['vreme'];
	$opis = $_POST['opis'];

	$datetime = new DateTime($datetime);
	$datetime = $datetime->format("Y-m-d H:i:s");

	$sql = "INSERT INTO `trip`(`user_id`, `start_destination`, `end_destination`, `sits`, `cena`, `telefon`, `datetime`, `opis`) ";
	$sql .= "VALUES((SELECT `user_id` FROM `users` WHERE `email` = ? LIMIT 1), ?, ?, ?, ?, ?, ?, ?)";

	$stmt = $db->prepare($sql);
	$stmt->bind_param("siiiisss", $email, $start_destination, $end_destination, $sits, $cena, $telefon, $datetime, $opis);
	$stmt->execute();
	echo $stmt->affected_rows;
	echo $stmt->error;
	$stmt->close();

	exitWithStatusCode("HTTP/1.1 200 OK", "");
?>