<?php
require_once ('init.php');

$query = $db->prepare ('SELECT COUNT(*) FROM `bill` WHERE syncId = :id');
$query->bindValue (':id', $_POST['id']);
$query->execute ();

if ($query->fetchAll ()[0][0] > 0)
	$query = $db->prepare ('UPDATE `bill` SET table_entity = :table_id, closed = :closed WHERE syncId = :id;');
else
	$query = $db->prepare ('INSERT INTO `bill` (table_entity, closed, syncId) VALUES (:table_id, :closed, :id);');

$query->bindValue (':id', $_POST['id']);
$query->bindValue (':table_id', $_POST['table_id']);
$query->bindValue (':closed', $_POST['closed']);
$query->execute ();
