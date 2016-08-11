<?php
class Session {
	
	private $logged_in=false;
  public $user_id;
	public $email;
 

	function __construct() {
    session_start();
		$this->check_login();
    //$this->check_profile();
    //$this->check_current_page();
    //$this->check_errors();
    //$this->errors = array();
    //$this->checkDefaultCountry();
	}

    private function check_login() {

      //echo $_SESSION['user_id'] . "AAA";
    if(session_id() && isset($_SESSION['user_id'])) {
      
      $this->email = $_SESSION['email'];
      $this->user_id = $_SESSION['user_id'];
     
      $this->logged_in = true;
    } else {
   
      unset($this->email);
      unset($this->user_id);
      $this->logged_in = false;
    }
  }

  public function login($email) {
    global $db;

    if($email){
      $this->email = $email;
      $_SESSION['email'] = $email;
      $this->logged_in = true;
      
      $sql = "SELECT `user_id`, `first_name`, `last_name` FROM `users` WHERE `email`= ? LIMIT 1";
      $stmt = $db->prepare($sql);
      $stmt->bind_param('s', $email);
      $stmt->execute();
      $stmt->bind_result($id, $first, $last);
      $stmt->fetch();
      $stmt->close();
   
      $_SESSION['user_id'] = $id;
      $this->user_id = $id;
      //$this->setUniqueId($id);
      //$this->setUserInfo($first, $last);
      return true;
    }
    return false;
  }

  private function setUniqueId($id){
      $this->user_id = $id;
      $_SESSION['user_id'] = $id;
  }

  private function setUserInfo($first, $last){
    $this->$first = $first;
    $this->last = $last;
    $_SESSION['first'] = $first;
    $_SESSION['last'] = $last;
  }
  
    public function is_logged_in() {
      return $this->logged_in;
    }


  public function increase_message_counter()
  {   
      $this->message_counter += 1;
      $_SESSION['msg_count'] = $this->message_counter;
  }

  public function reset_message_counter()
  {
      $this->message_counter = 0;
      $_SESSION['msg_count'] = $this->message_counter;
  }

  public function resetImg()
  {
      global $db;

      $sql = "SELECT `image` FROM `account` WHERE `unique_id`= ? LIMIT 1";
      $stmt = $db->prepare($sql);
      $stmt->bind_param('i', $this->unique_id);
      $stmt->execute();
      $stmt->bind_result($image);
      $stmt->execute();
      $stmt->fetch();
      $stmt->close();
      $path = USERS_URL . "{$this->unique_id}/images/profile/thumb_{$image}";
      $this->img = $path;
      $_SESSION['img'] = $path;
  }

  private function checkDefaultCountry()
  {
      if(isset($_SESSION['default_country']))
        $this->$default_country = $_SESSION['default_country'];
      else
        $this->setDefaultCountry();
  }

  private function setDefaultCountry()
  {
      $this->default_country = 1;
      $session['default_country'] = $this->default_country;
  }

  public function clearErrors()
  {
       $this->errors = array();
       $_SESSION['errors']=$this->errors;
  }

  public function getUniqueId()
  {
      return $this->user_id;
  }

  public function addError($error)
  { 
      $this->errors = $_SESSION['errors'];
      array_push($this->errors, $error);
      $_SESSION['errors'] = $this->errors;
  }

  private function check_errors()
  {
      if(isset($_SESSION['errors']))
          $this->errors = $_SESSION['errors'];
      else $this->errors= $_SESSION['errors'] =array();
  }


  public function setCurrentPage($page)
  {
      $this->current_page = $page;
      $_SESSION['current_page'] = $page;
  }

  public function setProfile($country_id, $company_name)
  {
      $this->setCountryId($country_id);
      $this->setCompanyName($company_name);
      $this->setProfileDir();
  }

  private function setProfileDir()
  {
      $this->profile_dir = $this->country_id.'_'.$this->company_name;
      $_SESSION["profile_dir"] = $this->profile_dir;
  }

  public function getProfileDir()
  {
      return $this->profile_dir;
  }

  private function setCountryId($country_id)
  {
      $this->country_id = $country_id;
      $_SESSION['country_id'] = $country_id;
  }

  private function setCompanyName($company_name)
  {
      $this->company_name = $company_name;
      $_SESSION['company_name'] = $company_name;
  }



	

  
  public function logout() {
    $params = session_get_cookie_params();
    setcookie(session_name(), '', time() - 42000,
      $params["path"], $params["domain"],
      $params["secure"], $params["httponly"]
    );
    session_destroy();
    $this->logged_in = false;
  }


  private function check_current_page()
  {
      if(isset($_SESSION['current_page']))
        $this->current_page = $_SESSION['current_page'];
      else
        unset($this->current_page);
  }

  private function check_profile(){
    if(isset($_SESSION['company_name'])) {
      $this->setProfile($_SESSION['country_id'], $_SESSION['company_name']);
    } else {
      unset($this->company_name);
      unset($this->country_id);
      unset($this->profile_dir);
    }
  }

  public function require_login()
  {
   // echo $_SESSION['email'];
      if(!$this->logged_in)
      {
          redirect_to(URL . "public/register/register.php");
      }
  }

  public function getErrors()
  {
    $html = "<ul class='errors'>";
    $this->errors = $_SESSION['errors'];
    foreach($this->errors as $error)
      $html .= "<li>{$error}</li>";
    $html .= "</ul>";
    return $html;
  }
	
}

$session = new Session();
?>