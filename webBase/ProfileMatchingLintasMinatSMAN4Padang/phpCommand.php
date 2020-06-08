<?php
$connect_db = mysql_select_db("db_sma4_pm_lm", mysql_connect("localhost", "root", ""));
//$connect_db = mysql_select_db("id9134347_db_sma4_pm_lm", mysql_connect("localhost", "id9134347_root", "aditio"));
$sqlQuery = $_POST["sqlQuery"];
$method = $_POST["method"];
$ERROR_QUERY = "0";
$ERROR_METHOD = "-2";
$NOT_FOUND = "-1";
$SUCCESS = "1";

if($method == "login" || $method == "refresh"){
	$sql = mysql_query($sqlQuery);
	if($sql){
		if(mysql_num_rows($sql)){
			while($r = mysql_fetch_array($sql)){
				$result[] = $r;
			}
			echo json_encode($result);
		}else echo $NOT_FOUND;
	}else echo $ERROR_QUERY;
}else if($method == "insert" || $method == "delete" || $method == "update"){
	$sql = mysql_query($sqlQuery);
	if($sql){
		echo $SUCCESS;
	}else{
		echo $ERROR_QUERY;
	}
}else echo $ERROR_METHOD;
?>

