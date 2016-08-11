<?php
	require_once "../../includes/initialize.php";

	$trip_id = isset($_POST['trip_id']) ? $_POST['trip_id'] : $_GET['trip_id'];

	$sql = "DELETE FROM `trip` WHERE `trip_id` = ?";
	$stmt = $db->prepare($sql);
	$stmt->bind_param('i', $trip_id);
	$stmt->execute();
	$stmt->close();

	exitWithStatusCode("HTTP/1.1 200 OK", "");
?>