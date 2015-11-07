<?php
require_once ('init.php');

$query = $db->prepare ('SELECT COUNT(*) FROM `table` WHERE syncId = :id');
$query->bindValue (':id', $_GET['id']);
$query->execute ();

if ($query->fetchAll ()[0][0] > 0)
	$query = $db->prepare ('UPDATE `table` SET name = :name WHERE syncId = :id;');
else
	$query = $db->prepare ('INSERT INTO `table` (name, syncId) VALUES (:name, :id);');
$query->bindValue (':id', $_POST['id']);
$query->bindValue (':name', $_POST['name']);
$query->execute ();

