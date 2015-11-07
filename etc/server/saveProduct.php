<?php
require_once ('init.php');

$query = $db->prepare ('SELECT COUNT(*) FROM `product` WHERE syncId = :id');
$query->bindValue (':id', $_POST['id']);
$query->execute ();

if ($query->fetchAll ()[0][0] > 0)
	$query = $db->prepare ('UPDATE `product` SET name = :name, price = :price, description = :description, available = :available WHERE syncId = :id;');
else
	$query = $db->prepare ('INSERT INTO `product` (name, price, description, available, syncId) VALUES (:name, :price, :description, :available, :id);');
$query->bindValue (':id', $_POST['id']);
$query->bindValue (':name', $_POST['name']);
$query->bindValue (':price', $_POST['price']);
$query->bindValue (':description', $_POST['description']);
$query->bindValue (':available', $_POST['available']);
$query->execute ();
