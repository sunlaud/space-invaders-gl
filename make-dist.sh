#!/bin/sh
mkdir dist
cd ./space-invaders-gl-game/
mvn assembly:assembly
cp ./target/space-invaders.zip ../dist
