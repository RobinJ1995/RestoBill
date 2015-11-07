<?php
require_once ('config.php');

$db = new PDO ($config['db']['dsn']);
$db->setAttribute (PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

