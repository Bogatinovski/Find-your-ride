<?php
	include_once "../../includes/initialize.php";

	$status = $session->is_logged_in();
	if($status)
		exitWithStatusCode("HTTP/1.1 200 OK", "");

	exitWithStatusCode("HTTP/1.1 401 Unauthorized", "Log in");
?>