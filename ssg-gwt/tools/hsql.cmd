cd ..
start mvn exec:java -Dexec.mainClass="org.hsqldb.server.Server" -Dexec.args="--database.0 file:../../../database/ssg_db --dbname.0 ssg_db"
rem mvn exec:java -Dexec.mainClass="org.hsqldb.server.Server" -Dexec.args="--database.0 file:target/ssgdb_test --dbname.0 ssg_test"