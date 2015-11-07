<?php
require_once ('init.php');

$query = $db->prepare ('INSERT INTO `table` (name, syncId) VALUES (:name, :id);');
$query->bindValue (':id', $_POST['id']);
$query->bindValue (':name', $_POST['name']);
$query->execute ();

