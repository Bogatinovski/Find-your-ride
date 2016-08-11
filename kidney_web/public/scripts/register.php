<?php
	include "../../includes/initialize.php";
	
	$username = $_POST['username'];
	$password = $_POST['password'];
	$first_name = $_POST['first'];
	$last_name = $_POST['last'];

	$sql = "SELECT `email` FROM `users` WHERE `email` = ?";
	$stmt = $db->prepare($sql);
	$stmt->bind_param("s", $username);
	$stmt->execute();
	$stmt->store_result();
	$num_rows = $stmt->num_rows;
	$stmt->close();

	if($num_rows == 1)
		exitWithStatusCode("HTTP/1.1 403 Forbidden", "Register failed");

	//$encripted = password_hash($password, PASSWORD_BCRYPT, ['cost'=>10]);
	$encripted = $password;
	$sql = "INSERT INTO `users`(`email`, `h_password`, `first_name`, `last_name`) VALUES(?, ?, ?, ?)";
	$stmt = $db->prepare($sql);
	$stmt->bind_param("ssss", $username, $encripted, $first_name, $last_name);
	$stmt->execute();
	echo $stmt->error;
	echo $stmt->affected_rows;
	$stmt->close();
	
	exitWithStatusCode("HTTP/1.1 200 OK", "Login successful");
?>