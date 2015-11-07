<?php
require_once ('config.php');

header ('Content-type: text/plain');

$db = new PDO ($config['db']['dsn']);
$db->setAttribute (PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

