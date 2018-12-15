#!/usr/bin/env bash
echo git clone https://github.com/wangrongjun/PersonalWebsite.git

YunPanPid=$(ps -aux | grep YunPan.jar | grep -v grep | awk '{print $2}')
if [[ "$YunPanPid" != "" ]];
    then kill ${YunPanPid}
fi

cd /wrj/src/PersonalWebsite
git pull
cd YunPan
mvn clean package spring-boot:repackage -Dmaven.test.skip=true

sleep 10s

\cp target/YunPan-1.0-SNAPSHOT.jar /wrj/jar/YunPan.jar
cd /wrj
nohup java -Dserver.port=81 -jar /wrj/jar/YunPan.jar > /wrj/log/YunPan.log 2>&1 &
tail -f /wrj/log/YunPan.log
