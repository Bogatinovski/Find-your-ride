<?php

function has_error($status)
{
    return $status == -1 || $status == NULL;
}
function getClassForAuctionStatus($status)
{
  if($status==0)
    $class="label label-danger";
  else if($status==1)
    $class="label label-success";
  else if($status==2)
    $class="label label-warning";
  return $class;
}

function generateFileUrlForCompany($file_name, $company_id, $folder)
{
    return PROFILE_URL . "{$company_id}/files/{$folder}/{$file_name}";
}

function generateFileDIRForCompany($file_name, $company_id, $folder)
{
    return PROFILE_DIR . "{$company_id}/files/{$folder}/{$file_name}";
}

function getStatusTextForAuctionStatus($status)
{
  if($status==0)
    $statusTxt = "Not started";
  else if($status==1)
    $statusTxt = "Started";
  else if($status==2)
    $statusTxt = "Waiting payment";
  return $statusTxt;
}
  function println($data)
  {
    echo $data . "<br>";
  }

  function get_performance($from, $time, $mem)
  {
    $memory = (memory_get_usage() - $mem) / (1024 * 1024);
    $seconds = microtime(TRUE) - $time;
    println("{$from} stats: Memory used: " . $memory . ". Seconds: " . $seconds);
    
  }

function isAllowedAccessUserForCompany($company_id)
{
    global $db;
    global $session;

    $sql = "SELECT `nation_id` FROM `companies` WHERE `company_id` = ? LIMIT 1";
    $stmt = $db->prepare($sql);
    $stmt->bind_param('i', $company_id);
    $stmt->execute();
    $stmt->bind_result($nation_id);
    $stmt->fetch();
    $stmt->close();

    return $session->is_logged_in() && $nation_id == $session->nation_id ? true : false;

}

function convertDateToUTCForCompany($datetime, $timezone)
{
    $timezone = new DateTimeZone($timezone);
    $datetime = new DateTime($datetime, $timezone);
    $datetime->setTimeZone(new DateTimeZone(date_default_timezone_get()));
    return $datetime;
}       

function convertUTCDateToCompanyTimezone($datetime, $timezone)
{
    $utc_timezone = new DateTimeZone(date_default_timezone_get());
    $datetime = new DateTime($datetime, $utc_timezone);
    $timezone = new DateTimeZone($timezone);
    $datetime->setTimeZone($timezone);
    return $datetime;
}


function getCompanyTimezone($id)
{
    global $db;
    $sql = "SELECT tz.`city` FROM `time_zones` tz, `companies` c WHERE tz.`time_zone_id` = c.`time_zone_id` AND ";
    $sql .= " `company_id` = ? LIMIT 1";
    
    $stmt = $db->prepare($sql);
    $stmt->bind_param('i', $id);
    $stmt->execute();
    $stmt->bind_result($city);
    $stmt->fetch();
    $stmt->close();

    return $city;
}

function generateFilenameForCompany($file_name, $company_id, $folder)
{
    return PROFILE_URL . "{$company_id}/files/{$folder}/{$file_name}";
}

function generateImgUrlFromName($img, $company_id, $folder){
   $path = "";
    if($img==DEFAULT_IMG_NAME)
        $path = DEFAULT_IMG_PATH;
    else $path = PROFILE_URL."{$company_id}/images/{$folder}/{$img}";
    return $path;
}

function generateImgPathFromName($img, $company_id, $folder)
{
    $path = PROFILE_DIR . getProfileDir($company_id).DS.'images'.DS.$folder.DS.$img;
    return $path; 
}
function generateUserImgUrlFromName($img, $unique_id, $folder){
   $path = "";
     if($img==DEFAULT_IMG_NAME)
        $path = DEFAULT_IMG_PATH;
    else{
         $path = USERS_URL."{$unique_id}/images/{$folder}/{$img}"; 
    } 
    return $path;
}
function extractImgNameFromUrl($url){
    $array = explode("/", $url);
    $size = count($array);
    $last_part = $array[$size-1];
    return $last_part;
}

function strip_zeros_from_date( $marked_string="" ) {
  // first remove the marked zeros
  $no_zeros = str_replace('*0', '', $marked_string);
  // then remove any remaining marks
  $cleaned_string = str_replace('*', '', $no_zeros);
  return $cleaned_string;
}

function redirect_to( $location = NULL) {
  if ($location != NULL) {
    header("Location: {$location}");
    exit;
  }
}

function log_action($action, $message="") {
	$logfile = SITE_ROOT.DS.'logs'.DS.'log.txt';
	$new = file_exists($logfile) ? false : true;
  if($handle = fopen($logfile, 'a')) { // append
    $timestamp = strftime("%Y-%m-%d %H:%M:%S", time());
		$content = "{$timestamp} | {$action}: {$message}\n";
    fwrite($handle, $content);
    fclose($handle);
    if($new) { chmod($logfile, 0755); }
  } else {
    echo "Could not open log file for writing.";
  }
}

function datetime_to_text($datetime="") {
  $unixdatetime = strtotime($datetime);
  return strftime("%B %d, %Y at %I:%M %p", $unixdatetime);
}

function array_push_assoc($array, $key, $value){
     $array[$key] = $value;
     return $array;
    }

function exitWithError($header, $exitMessage)
{
  header($header);
  exit($exitMessage);
}

function exitWithStatusCode($header, $exitMessage)
{
  header($header);
  exit($exitMessage);
}

function addVisit($company_id)
{
    global $db;
    $sql = "UPDATE `statistics` SET `total_visits` = `total_visits`+1 WHERE `company_id`=?";
      
    $stmt = $db->prepare($sql);
    $stmt->bind_param('i', $company_id);
    $stmt->execute();
}

function getProfileDir($company_id)
{
    return $company_id;
}

function checkProfileExistance($company_id){
  global $db;
  $sql = "SELECT `company_id` FROM `companies` WHERE `company_id`= ? LIMIT 1";
  
    $stmt = $db->prepare($sql);
    $stmt->bind_param('i', $company_id);
    $stmt->execute();
    $stmt->bind_result($c);

   if(!$stmt->fetch())
   {
      $stmt->close();
      return false;
   }
        
  
  if(!$stmt->affected_rows)
    return false;
  return true;
}

function checkUserExistance($user){
     global $db;
     $sql = "SELECT `unique_id` FROM `accounts` WHERE `unique_id`= ? LIMIT 1";
  
    $stmt = $db->prepare($sql);
    $stmt->bind_param('i', $user);
    $stmt->execute();
    $stmt->bind_result($c);

   if(!$stmt->fetch())
   {
      $stmt->close();
      return false;
   }
  
  if(!$c)
    return false;
  return true;
}

  function require_admin_login($company_id)
  {
      global $db;
      global $session;
    
      if(!$session->is_logged_in())
      {
          //redirect_to(URL . "public/register/register.php");
          return false;
      }

     $sql = "SELECT `unique_id` FROM `manage` WHERE `company_id`=? AND `unique_id`=? LIMIT 1";
      
      $stmt = $db->prepare($sql);
      $stmt->bind_param('ii', $company_id, $session->unique_id);
      $stmt->execute();
      $stmt->bind_result($id);
      $stmt->store_result();
      $stmt->fetch();

      if($stmt->num_rows != 1 || $id != $session->unique_id)
      {
          $stmt->close();
          return false;
      }

      $stmt->close();
      return true;
  }
?>