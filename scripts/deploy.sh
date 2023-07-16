#!/usr/bin/env bash

# 프로젝트 관련 설정
PROJECT_ROOT="/home/ubuntu/app"
JAR_FILE="$PROJECT_ROOT/spring-webapp.jar"
APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"

# 배포 로그 파일
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"
TIME_NOW=$(date +%c)

# 실행권한 주기
echo "$TIME_NOW > 실행권한 주기" >> $DEPLOY_LOG
chmod +x app/scripts/deploy.sh

# 빌드 파일 복사
echo "$TIME_NOW > 빌드 파일 복사" >> $DEPLOY_LOG
cp build/libs/*.jar $JAR_FILE

# 실행 중인 프로세스 종료
echo "$TIME_NOW > 실행 중인 프로세스 종료" >> $DEPLOY_LOG
CURRENT_PID=$(pgrep -f $JAR_FILE)
if [ -z "$CURRENT_PID" ]; then
  echo "$TIME_NOW > 현재 실행 중인 애플리케이션이 없습니다" >> $DEPLOY_LOG
else
  echo "$TIME_NOW > 실행 중인 $CURRENT_PID 애플리케이션 종료" >> $DEPLOY_LOG
  kill "$CURRENT_PID"
  sleep 5
fi

# 새로운 프로세스 실행
echo "$TIME_NOW > 새로운 프로세스 실행" >> $DEPLOY_LOG
nohup java -jar "$JAR_FILE" > "$APP_LOG" 2> "$ERROR_LOG" &

# 배포 완료 로그
echo "$TIME_NOW > 애플리케이션 배포 완료" >> $DEPLOY_LOG