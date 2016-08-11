<?php
defined('DS') ? null : define('DS', DIRECTORY_SEPARATOR);
defined('SITE_ROOT') ? null : define('SITE_ROOT', 'C:'.DS.'wamp'.DS.'www'.DS.'kidney'.DS);
defined('LIB_PATH') ? null : define('LIB_PATH', SITE_ROOT.'includes' . DS);
defined('AJAX') ? null : define('AJAX', SITE_ROOT.'ajax' . DS);
defined('PUBLIC_DIR') ? null : define('PUBLIC_DIR', SITE_ROOT.'public' . DS);
defined('URL') ? null : define('URL', "http://localhost/kidney/");
defined('DEFAULT_LOGO') ? null : define('DEFAULT_LOGO', URL.'public/images/default1.jpg');
//defined('URL') ? null : define('URL', "http://46.217.162.25/gbc/");
defined('PROFILE_DIR')   ? null : define("PROFILE_DIR", SITE_ROOT.'public'.DS.'profiles'.DS);
defined('PROFILE_URL')   ? null : define("PROFILE_URL", URL.'public/profiles/');
defined('USERS_URL')   ? null : define("USERS_URL", URL . 'public/users/');
defined('USERS_DIR')   ? null : define("USERS_DIR", SITE_ROOT . "public" . DS. "users" . DS);
defined('DEFAULT_IMG_NAME')   ? null : define("DEFAULT_IMG_NAME", 'default.png');
defined('DEFAULT_IMG_PATH')   ? null : define("DEFAULT_IMG_PATH", URL . 'public/images/default.png');
defined('DATE_FORMAT')   ? null : define("DATE_FORMAT", "d.m.Y H:i:s");

?>