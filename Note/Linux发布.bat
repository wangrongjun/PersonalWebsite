rem git clone https://github.com/wangrongjun/PersonalWebsite.git
cd /wrj/src/PersonalWebsite
git pull
cd Note
mvn clean package spring-boot:repackage -Dmaven.test.skip=true -P prod

ps -aux | grep Note.jar | grep -v grep | awk '{print $2}' | xargs kill
\cp target/Note-1.0-SNAPSHOT.jar /wrj/jar/Note.jar
nohup java -jar /wrj/jar/Note.jar > /wrj/log/Note.log 2>&1 &
tail -f /wrj/log/Note.log
