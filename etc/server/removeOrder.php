<?php
require_once ('init.php');

$query = $db->prepare ('SELECT * FROM `order` WHERE syncId = :id;');
$query->bindValue (':id', $_POST['id']);
$query->execute ();
