<?php
require_once ('init.php');

$query = $db->prepare ('SELECT * FROM `table` WHERE syncId = :id;');
$query->bindValue (':id', $_POST['id']);
$query->execute ();
