<?php
require_once ('init.php');

$query = $db->prepare ('SELECT * FROM `bill` WHERE syncId = :id;');
$query->bindValue (':id', $_POST['id']);
$query->execute ();
$id = $query->fetchAll ()[0]['id'];

$query = $db->prepare ('DELETE FROM `order` WHERE bill_entity = :id2;');
$query->bindValue (':id2', $id);
$query->execute ();

$query = $db->prepare ('DELETE FROM `bill` WHERE syncId = :id;');
$query->bindValue (':id', $_POST['id']);
$query->execute ();
