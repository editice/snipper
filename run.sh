git pull origin master
mvn clean package -Dmaven.test.skip=true
cp snipper-web/target/snipper.war /home/admin/apache-tomcat-7.0.65/webapps/snipper.war
rm -rf /home/admin/apache-tomcat-7.0.65/webapps/snipper
cd /home/admin/apache-tomcat-7.0.65/bin
sh shutdown.sh
sh startup.sh
