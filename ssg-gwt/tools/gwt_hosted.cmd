set CATALINA_HOME=E:\Java\apache-tomcat-7.0.25-windows-x86\apache-tomcat-7.0.25
cd ..
rem start mvn war:exploded -DwebappDirectory=%CATALINA_HOME%\webapps\ssg-gwt
start mvn gwt:run -Dgwt.noserver=true -Dgwt.copyWebapp=true -Dgwt.tomcat=%CATALINA_HOME% -Dgwt:runtarget=index.jsp
rem start mvn gwt:run

rem http://127.0.0.1:8080/ssg-gwt/StudentHome.html?gwt.codesvr=127.0.0.1:9997