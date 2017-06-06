<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Reglas de Seguridad</title>
    </head>
    <body>
        <label>CODIGO: </label>php:S2964<br>
        <label>REGLA: </label>"sleep" should not be called<br>
        <label>SEVERIDAD: </label>Menor<br>
        
        <label>EJEMPLO DE INCUMPLIMIENTO:</label><br>
		<?php
			if (is_bad_ip($requester)) {
				sleep(5);  // Incumple
			}
		?>
            
        <label>RESOLUCIÓN DE INCUMPLIMIENTO:</label><br>
		<?php
			// Eliminar el uso de "sleep"
		?>
 
		
        <hr>
    
        <label>CODIGO: </label>php:S1523<br>
        <label>REGLA: </label>Code should not be dynamically injected and executed<br>
        <label>SEVERIDAD: </label>Crítica<br>
        
        <label>EJEMPLO DE INCUMPLIMIENTO:</label><br>
 		<?php
			eval("$variable=".$valorDeUsuario);
		?>
            
        <label>RESOLUCIÓN DE INCUMPLIMIENTO:</label><br>
		<?php
			$variable=$valorDeUsuario;
		?>
 		
        <hr>

        <label>CODIGO: </label>php:S2068<br>
        <label>REGLA: </label>Credentials should not be hard-coded<br>
        <label>SEVERIDAD: </label>Bloqueante<br>
        
        <label>EJEMPLO DE INCUMPLIMIENTO:</label><br>
 		<?php
			$uname = "steve";
			$password = "blue";
			connect($uname, $password);
		?>
            
        <label>RESOLUCIÓN DE INCUMPLIMIENTO:</label><br>
		<?php
			$uname = getEncryptedUser();
			$password = getEncryptedPass();
			connect($uname, $password);
		?>
 		
        <hr>

        <label>CODIGO: </label>php:S3332<br>
        <label>REGLA: </label>Session-management cookies should not be persistent<br>
        <label>SEVERIDAD: </label>Crítica<br>
        
        <label>EJEMPLO DE INCUMPLIMIENTO:</label><br>
 		<?php
			setcookie("TestCookie", $value, time()+3600);
		?>
            
        <label>RESOLUCIÓN DE INCUMPLIMIENTO:</label><br>
		<?php
			setcookie("TestCookie", $value);
		?>
 		
        <hr>
		
    </body>
</html>