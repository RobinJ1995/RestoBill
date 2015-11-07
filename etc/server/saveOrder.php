<?php
require_once ('init.php');

$query = $db->prepare ('INSERT INTO `order` (id, bill_id, product_id, amount) VALUES (:id, :bill_id, :product_id, :amount);');
$query->bindValue (':id', $_POST['id']);
$query->bindValue (':bill_id', $_POST['bill_id']);
$query->bindValue (':product_id', $_POST['product_id']);
$query->bindValue (':amount', $_POST['amount']);
$query->execute ();
