#!/usr/bin/env bash
echo git clone https://github.com/wangrongjun/PersonalWebsite.git
echo git clone https://github.com/wangrongjun/JavaLib.git
echo git clone https://github.com/wangrongjun/WebLib.git

NotePid=$(ps -aux | grep Note.jar | grep -v grep | awk '{print $2}')
if [[ "$NotePid" != "" ]];
    then kill ${NotePid}
fi

cd /wrj/src/PersonalWebsite
git pull
cd Note
mvn clean package spring-boot:repackage -Dmaven.test.skip=true -P prod

sleep 10s

\cp target/Note-1.0-SNAPSHOT.jar /wrj/jar/Note.jar
nohup java -Dserver.port=80 -jar /wrj/jar/Note.jar > /wrj/log/Note.log 2>&1 &
tail -f /wrj/log/Note.log
