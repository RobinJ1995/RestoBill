<?php
require_once ('init.php');

$missing = array ();
foreach (explode (',', $_POST['ids']) as $id)
{
	$query = $db->prepare ('SELECT COUNT(*) FROM `table` WHERE syncId = :id;');
	$query->bindValue (':id', $id);
	$query->execute ();
	
	if ($query->fetchAll ()[0][0] == 0)
		$missing[] = $id;
}

echo implode (',', $missing);
