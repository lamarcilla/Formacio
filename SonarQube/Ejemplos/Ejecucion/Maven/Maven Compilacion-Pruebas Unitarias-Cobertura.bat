SET JAVA_HOME=C:\Program Files (x86)\Java\jdk1.8.0_25

"C:\apache-maven-3.0.5\bin\mvn.bat" -Dcobertura.report.format=xml -f "C:\PACOLMOS\Clientes\OMC\SVN\OMC\trunk\Q19\pom.xml" clean cobertura:cobertura