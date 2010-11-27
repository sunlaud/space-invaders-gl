IF NOT [%ARCH%]==[] goto run_game
SET ARCH=i586
SET OS=windows

:run_game
SET NATIVES=etc\native\%OS%\%ARCH%\
SET JAR_FILE=space-invaders-gl-game\target\space-invaders-gl-game-1.0-SNAPSHOT.jar

java -Djava.library.path=%NATIVES% -jar %JAR_FILE%
