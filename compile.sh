git pull origin master
mvn clean
mvn package
cp web/target/web-1.0.0.war /opt/apache-tomcat-7.0.56/webapps/web.war
rm -rf /opt/apache-tomcat-7.0.56/webapps/web
cd /opt/apache-tomcat-7.0.56/bin
sh shutdown.sh
sh startup.sh
