<?php
require_once ('init.php');

$query = $db->prepare ('INSERT INTO TABLE (table_entity, closed, syncId) VALUES (:table_id, :closed, :id);');
$query->bindValue (':id', $_POST['id']);
$query->bindValue (':table_id', $_POST['table_id']);
$query->bindValue (':closed', $_POST['closed']);
$query->execute ();
