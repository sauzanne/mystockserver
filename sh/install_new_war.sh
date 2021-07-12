#!/bin/bash

webapps=/opt/tomcat/webapps
source=/home/auzanne/Documents/



#suppprime l'ancienne archive


a=`find /home/auzanne/Documents/ -name "*.war" -mmin -30 | wc -l`


echo $a

if [ $a -gt 0 ];
then
	echo "suppression de l'ancien war"
	rm -Rf $webapps/mystockserver
	rm $webapps/mystockserver.war
	echo "copie de l'archive"
	find $source -name "*.war" -mmin -15 -exec chown tomcat {} $webapps/mystockserver.war \;
	find $source -name "*.war" -mmin -15 -exec chmod +x {} $webapps/mystockserver.war \;
	find $source -name "*.war" -mmin -15 -exec cp -a {} $webapps/mystockserver.war \;
 	exit
fi


