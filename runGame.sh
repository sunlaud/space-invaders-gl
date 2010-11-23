#!/bin/sh
ARCH=i586
OS=linux
NATIVES=./etc/native/$OS/$ARCH/
JAR_FILE=./space-invaders-gl-game/target/space-invaders-gl-game-1.0-SNAPSHOT.jar

java -Djava.library.path=$NATIVES -jar $JAR_FILE
