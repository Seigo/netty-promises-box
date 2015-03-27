#!/usr/bin/env bash

apt-get update

apt-get install -y openjdk-7-jdk
apt-get install -y openjdk-7-source
apt-get install -y openjdk-7-doc

update-alternatives --install "/usr/bin/javac" "javac" "/usr/lib/jvm/java-1.7.0-openjdk-i386/bin/javac" 1
update-alternatives --set javac /usr/lib/jvm/java-1.7.0-openjdk-i386/bin/javac
update-alternatives --install "/usr/bin/java" "java" "/usr/lib/jvm/java-1.7.0-openjdk-i386/jre/bin/java" 1
update-alternatives --set java /usr/lib/jvm/java-1.7.0-openjdk-i386/jre/bin/java
export JAVA_HOME=/usr/lib/jvm/java-1.7.0-openjdk-i386/jre/bin

apt-get install -y maven
apt-get install -y git
