set CATALINA_HOME=E:\Java\apache-tomcat-7.0.25-windows-x86\apache-tomcat-7.0.25
rem set JPDA_TRANSPORT=dt_socket
rem set JPDA_ADDRESS=8000 
rem set JPDA_SUSPEND=n
set JPDA_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=8011,suspend=n
start %CATALINA_HOME%\bin\catalina.bat jpda run 