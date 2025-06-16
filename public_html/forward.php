<?php
define("ROBOT", "/usr/bin/sudo ". __DIR__ ."/motor_ou2709");
define("ADDRESS", "127.0.0.1");
define("USER", "pi");
define("PASSWORD", "<password>");

/*nSSH2 module processes */
$sconnection = ssh2_connect(ADDRESS, 22);
if (!$sconnection) {
    die("Failed_ssh");
}
//ssh2_auth_password($sconnection, USER, PASSWORD);
if (!ssh2_auth_password($sconnection, USER, PASSWORD)) {
    die("Failed_connect");
}
echo "connect<br>";
/* execution command */
$command = ROBOT." "."Forward";
echo $command. "<br>";
$stdio_stream = ssh2_exec($sconnection, $command);
$stderr_stream = ssh2_fetch_stream($stdio_stream, SSH2_STREAM_STDERR);


//出力の待機
stream_set_blocking($stdio_stream, true);
stream_set_blocking($stderr_stream, true);

//出力結果の読み取り
$output = stream_get_contents($stdio_stream);
$error  = stream_get_contents($stderr_stream);

// 出力をWebに表示
echo "<pre>";
echo $output;
if (!empty($error)) {
    echo $error;
}
echo "</pre>";

?>