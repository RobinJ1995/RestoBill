<?php
require_once ('init.php');

$query = $db->prepare ('SELECT * FROM `table` WHERE syncId = :id;');
$query->bindValue (':id', $_POST['id']);
$query->execute ();
$data = $query->fetchAll ()[0];

$query = $db->prepare ('DELETE FROM `table` WHERE syncId = :id;');
$query->bindValue (':id', $_POST['id']);
$query->execute ();

file_put_contents ('removals.log', file_get_contents ('removals.log') . PHP_EOL . 'table:' . $_POST['id'] . ':' . $data['name']);
