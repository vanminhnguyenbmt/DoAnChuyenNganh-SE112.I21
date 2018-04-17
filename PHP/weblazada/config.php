<?php
    define('DBSERVER', 'localhost');
    define('DBUSERNAME', 'root');
    define('DBPASSWORD', '');
    define('DBNAME', 'lazada');

    $conn = mysqli_connect(DBSERVER, DBUSERNAME, DBPASSWORD, DBNAME);
    $conn->set_charset("utf8");
    if(!$conn)
    {
        die('Connect error: ' .mysqli_connect_errno());
    };
 ?>
