SET JAVA_HOME=C:\Program Files (x86)\Java\jdk1.8.0_25

"C:\apache-maven-3.0.5\bin\mvn.bat" -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=admin -f "C:\PACOLMOS\Clientes\OMC\SVN\OMC\trunk\Q19\pom.xml" sonar:sonar