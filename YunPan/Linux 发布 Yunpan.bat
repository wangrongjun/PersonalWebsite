echo git clone https://github.com/wangrongjun/PersonalWebsite.git

ps -aux | grep YunPan.jar | grep -v grep | awk '{print $2}' | xargs kill

cd /wrj/src/PersonalWebsite
git pull
cd YunPan
mvn clean package spring-boot:repackage -Dmaven.test.skip=true -P prod

sleep 10s

\cp target/YunPan-1.0-SNAPSHOT.jar /wrj/jar/YunPan.jar
nohup java -jar /wrj/jar/YunPan.jar > /wrj/log/YunPan.log 2>&1 &
tail -f /wrj/log/YunPan.log
