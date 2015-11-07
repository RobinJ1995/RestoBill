<?php
require_once ('init.php');

$query = $db->prepare ('INSERT INTO TABLE (id, table_id, closed) VALUES (:id, :table_id, :closed);');
$query->bindValue (':id', $_POST['id']);
$query->bindValue (':table_id', $_POST['table_id']);
$query->bindValue (':closed', $_POST['closed']);
$query->execute ();
