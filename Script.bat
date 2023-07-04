cd "C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps"
rmdir BuildMineframe
rm BuildMineframe.war
cd "C:\Users\Dimby\Documents\GitHub\Framework\TestFramework"
jar cvf "BuildMineframe.war" *
copy BuildMineFrame.war "C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps"