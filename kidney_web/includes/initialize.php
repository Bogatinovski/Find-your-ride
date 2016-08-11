<?php
// Define the core paths
require_once("core_paths.php");
require_once("config.php");

// load config file first
//require_once(LIB_PATH.DS.'config.php');

// load basic functions next so that everything after can use them
require_once(LIB_PATH.'functions.php');

// load core objects
require_once(LIB_PATH.'session.php');

require_once(LIB_PATH.'database.php');
// load database-related classes

?>