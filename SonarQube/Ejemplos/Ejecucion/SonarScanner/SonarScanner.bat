SET JAVA_HOME=C:\Program Files (x86)\Java\jdk1.8.0_25
SET SONAR_SCANNER_OPTS=-Xmx512m -XX:MaxPermSize=128m
"C:\Program Files (x86)\sonar-scanner\bin\sonar-scanner.bat" -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=admin "-Dsonar.projectBaseDir=C:\PACOLMOS\Clientes\OMC\SVN\OMC\trunk\Q19" -e