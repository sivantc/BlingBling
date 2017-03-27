/*
<?php
  $con = mysqli_connect("	mysql8.000webhost.com", "a1221356_bling", "aA123123", "a1221356_bling");
  $first_name = $_POST["first_name"];
  $last_name = $_POST["last_name"];
  $user_name = $_POST["user_name"];
  $password = $_POST["password"];
  $email = $_POST["email"];
  $radios = $_POST["radios"];

  $statement = mysqli_prepare($con, "INSERT INTO Client (first_name, last_name, user_name, password, email, radios) VALUES (?, ?, ?, ?, ?, ?)");


  function registerUser() {
        global $con, $first_name, $last_name, $user_name, $password, $email, $radios ;
        $statement = mysqli_prepare($connect, "INSERT INTO user (name, age, username, password) VALUES (?, ?, ?, ?)");
        mysqli_stmt_bind_param($statement, "sssssi", $first_name, $last_name, $user_name, $password, $email, $radios);
        mysqli_stmt_execute($statement);
        mysqli_stmt_close($statement);
    }
    function usernameAvailable() {
        global $con, $user_name;
        $statement = mysqli_prepare($connect, "SELECT * FROM user WHERE username = ?");
        mysqli_stmt_bind_param($statement, "s", $username);
        mysqli_stmt_execute($statement);
        mysqli_stmt_store_result($statement);
        $count = mysqli_stmt_num_rows($statement);
        mysqli_stmt_close($statement);
        if ($count < 1){
            return true;
        }else {
            return false;
        }
    }
    $response = array();
    $response["success"] = false;
    if (usernameAvailable()){
        registerUser();
        $response["success"] = true;
    }
  echo json_encode($response);
?>
*/