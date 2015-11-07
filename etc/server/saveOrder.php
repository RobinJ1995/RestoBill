<?php
require_once ('init.php');

$query = $db->prepare ('SELECT COUNT(*) FROM `order` WHERE syncId = :id');
$query->bindValue (':id', $_POST['id']);
$query->execute ();

if ($query->fetchAll ()[0][0] > 0)
	$query = $db->prepare ('UPDATE `order` SET bill_entity = :bill_id, product_entity = :product_id, amount = :amount WHERE syncId = :id;');
else
	$query = $db->prepare ('INSERT INTO `order` (bill_entity, product_entity, amount, syncId) VALUES (:bill_id, :product_id, :amount, :id);');
	
$query->bindValue (':id', $_POST['id']);
$query->bindValue (':bill_id', $_POST['bill_id']);
$query->bindValue (':product_id', $_POST['product_id']);
$query->bindValue (':amount', $_POST['amount']);
$query->execute ();
