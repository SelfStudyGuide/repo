set CATALINA_HOME=E:\Java\apache-tomcat-7.0.25-windows-x86\apache-tomcat-7.0.25
del /Q /S %CATALINA_HOME%\webapps\ssg-gwt\*.*
xcopy /E ..\target\ssg-gwt-0.0.1-SNAPSHOT %CATALINA_HOME%\webapps\ssg-gwt\ 