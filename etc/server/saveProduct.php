<?php
require_once ('init.php');

$query = $db->prepare ('INSERT INTO TABLE (name, price, description, available, syncId) VALUES (:name, :price, :description, :available, :id);');
$query->bindValue (':id', $_POST['id']);
$query->bindValue (':name', $_POST['name']);
$query->bindValue (':price', $_POST['price']);
$query->bindValue (':description', $_POST['description']);
$query->bindValue (':available', $_POST['available']);
$query->execute ();
