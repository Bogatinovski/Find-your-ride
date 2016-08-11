<?php
	require_once "../../includes/initialize.php";
	
	$sql = "SELECT t.`trip_id`, t.`user_id`, u.`first_name`, u.`last_name`, t.`sits`, t.`cena`, t.`telefon`, t.`datetime`, ";
	$sql .= "t.`opis`, d.`destination`, d2.`destination` ";
	$sql .= "FROM `trip` t JOIN `users` u ON t.`user_id` = u.`user_id` JOIN `destinations` d ON d.`destination_id`=t.`start_destination` ";
	$sql .= "JOIN `destinations` d2 ON t.`end_destination` = d2.`destination_id` ";
	$sql .= "WHERE t.`user_id` = ?";

	$stmt = $db->prepare($sql);
	$stmt->bind_param('i', $session->user_id);
	$stmt->execute();
	$stmt->bind_result($trip_id, $user_id, $first_name, $last_name, $sits, $cena, $telefon, $datetime, $opis, $od, $do);

	$return = array();

	while($stmt->fetch())
	{
		$date = new DateTime($datetime);
		$date = $date->format("d.m.Y H:i:s");
		$trip = array("trip_id"=>$trip_id, "user_id"=>$user_id, "first"=>$first_name, "last"=>$last_name, "sits"=>$sits,
			"cena"=>$cena, "telefon"=>$telefon, "datetime"=>$date, "opis"=>$opis, "od"=>$od, "do"=>$do);
		
		array_push($return, $trip);
	}

	$stmt->close();

	exitWithStatusCode("HTTP/1.1 200 OK", json_encode($return));
?>