echo git clone https://github.com/wangrongjun/PersonalWebsite.git

ps -aux | grep Note.jar | grep -v grep | awk '{print $2}' | xargs kill

cd /wrj/src/PersonalWebsite
git pull
cd Note
mvn clean package spring-boot:repackage -Dmaven.test.skip=true -P prod

sleep 10s

\cp target/Note-1.0-SNAPSHOT.jar /wrj/jar/Note.jar
nohup java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar /wrj/jar/Note.jar > /wrj/log/Note.log 2>&1 &
tail -f /wrj/log/Note.log
