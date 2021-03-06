<?php
require_once ('init.php');

$query = $db->prepare ('SELECT * FROM `bill` WHERE syncId = :id;');
$query->bindValue (':id', $_POST['id']);
$query->execute ();
$data = $query->fetchAll ()[0];
$id = $data['id'];

$query = $db->prepare ('DELETE FROM `order` WHERE bill_entity = :id2;');
$query->bindValue (':id2', $id);
$query->execute ();

$query = $db->prepare ('DELETE FROM `bill` WHERE syncId = :id;');
$query->bindValue (':id', $_POST['id']);
$query->execute ();

file_put_contents ('removals.log', file_get_contents ('removals.log') . PHP_EOL . 'bill:' . $_POST['id'] . ':' . $data['table_entity']);
