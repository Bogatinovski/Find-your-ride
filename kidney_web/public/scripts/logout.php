<?php
	require_once "../../includes/initialize.php";

	$session->logout();
    exitWithStatusCode("HTTP/1.1 200 OK", "Loged out");
?>