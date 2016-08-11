<?php
	require_once "../../includes/initialize.php";
    //echo $session->user_id;
    //echo $session->is_logged_in() ? "TRUE" : "FALSE";
	$email = isset($_POST['username']) ? $_POST['username'] : $_GET['username'];
	$pass = isset($_POST['password']) ? $_POST['password'] : $_GET['password'];

	$sql  = "SELECT `h_password` FROM `users` WHERE `email` = ? LIMIT 1";
    $stmt = $db->prepare($sql);
    $stmt->bind_param('s', $email);
    $stmt->execute();
    $stmt->bind_result($hpass);
    

    $stmt->fetch();
    if(!$hpass)
         exitWithStatusCode("HTTP/1.1 403 Forbidden", "Login failed");
   $stmt->close();

    //if(password_verify($pass, $hpass))
   
    if($pass == $hpass)
    {
    	if($session->login($email))
    	   exitWithStatusCode("HTTP/1.1 200 OK", "Login successful");
        exitWithStatusCode("HTTP/1.1 403 Forbidden", "Login failed");
    }
    else exitWithStatusCode("HTTP/1.1 403 Forbidden", "Login failed");



?>