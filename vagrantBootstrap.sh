#!/usr/bin/env bash

apt-get update

apt-get remove -y openjdk-6-jre

apt-get install -y openjdk-7-jdk
apt-get install -y openjdk-7-source
apt-get install -y openjdk-7-doc

export JAVA_HOME=/usr/lib/jvm/java-1.7.0-openjdk-i386

apt-get install -y maven
apt-get install -y git
