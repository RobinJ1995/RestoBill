<?php
require_once ('init.php');

$query = $db->prepare ('INSERT INTO `table` (id, name) VALUES (:id, :name);');
$query->bindValue (':id', $_POST['id']);
$query->bindValue (':name', $_POST['name']);
$query->execute ();

