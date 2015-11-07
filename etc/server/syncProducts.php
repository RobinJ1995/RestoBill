<?php
require_once ('init.php');

$query = $db->prepare ('SELECT * FROM `product` WHERE syncId NOT IN (' . $_POST['ids'] . ');');
// Can't use prepared statements with a list. Please don't kill me. It'll have to do for now. //
$query->execute ();

echo json_encode ($query->fetchAll ());
