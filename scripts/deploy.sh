#!/bin/bash
BUILD_JAR=$(ls /home/ubuntu/Tuti-Server/build/libs/*.jar)
JAR_NAME=$(basename $BUILD_JAR)
echo "> build 파일명: $JAR_NAME" >> /home/ubuntu/Tuti-Server/deploy.log

echo "> build 파일 복사" >> /home/ubuntu/Tuti-Server/deploy.log
DEPLOY_PATH=/home/ubuntu/Tuti-Server/
cp $BUILD_JAR $DEPLOY_PATH

echo "> 현재 실행중인 애플리케이션 pid 확인" >> /home/ubuntu/Tuti-Server/deploy.log
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ubuntu/Tuti-Server/deploy.log
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo "> DEPLOY_JAR 배포"    >> /home/ubuntu/Tuti-Server/deploy.log
nohup java -jar $DEPLOY_JAR --spring.profiles.active=dev > /dev/null 2> /dev/null < /dev/null &

nohup java -jar \
    -Dspring.config.location=classpath:/application.yml,classpath:/application-dev.yml \
    -Dspring.profiles.active=dev \
    $DEPLOY_JAR > $DEPLOY_PATH/nohup.out 2>&1 &
