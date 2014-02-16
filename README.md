BDD-trial
=========

This is work in progress, nothing special to see here.

Notes to myself:

Run the top-level pom as: 

mvn clean install

The resulting standalone jar can be run independently as:

export CLASSPATH=JBStoryRunner-1.0-SNAPSHOT-jar-with-dependencies.jar

java org.junit.runner.JUnitCore org.jb.JBStoryRunner


Eclipse setup:
0. create new workspace for eclipse
1. add link between eclipse workspace and local maven repo:
mvn -Declipse.workspace=(path to workspace) eclipse:add-maven-repo
2. copy this project to eclipse workspace
3. run command: mvn eclipse:eclipse

To clean eclipse mvn workspace: mvn eclipse:clean


Remember to have JBehave eclipse plugin installed before importing this project to eclipse

Create maven build configurations for maven in eclipse:
External Tools Configurations --> Location=/user/bin/mvn ; Working Directory=(workspace); Arguments=clean install
