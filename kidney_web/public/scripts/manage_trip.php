<?php
	require_once "../../includes/initialize.php";

	$trip_id = isset($_POST['trip_id']) ? $_POST['trip_id'] : $_GET['trip_id'];
	$action = isset($_POST['action']) ? $_POST['action'] : $_GET['action'];

	switch ($action) {
		case 'remove':
			removePassinger();
			break;
		
		case 'add':
			addPassinger();
			break;

	}

	function removePassinger()
	{	
		global $db;
		global $trip_id;

		$sql = "UPDATE `trip` SET `sits` = `sits`+1 WHERE `trip_id` = ?";
		$stmt = $db->prepare($sql);
		$stmt->bind_param('i', $trip_id);
		$stmt->execute();
		$stmt->close();
		exitWithStatusCode("HTTP/1.1 200 OK", "");
	}

	function addPassinger()
	{	
		global $db;
		global $trip_id;

		$sql = "UPDATE `trip` SET `sits` = `sits`-1 WHERE `trip_id` = ?";
		$stmt = $db->prepare($sql);
		$stmt->bind_param('i', $trip_id);
		$stmt->execute();
		echo $stmt->affected_rows;
		$stmt->close();
		exitWithStatusCode("HTTP/1.1 200 OK", "");
	}

?>