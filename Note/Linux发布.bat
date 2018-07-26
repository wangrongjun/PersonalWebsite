rem Build
mkdirs /wrj/src
cd /wrj/src
git clone https://github.com/wangrongjun/PersonalWebsite.git
cd PersonalWebsite/Note
mvn clean package spring-boot:repackage -Dmaven.test.skip=true -P prod

rem Deploy
ps -aux | grep Note.jar | grep -v grep | awk '{print $2}' | xargs kill
cp target/Note-1.0-SNAPSHOT.jar /wrj/jar/Note.jar
nohup java -jar /wrj/jar/Note.jar > /wrj/log/Note.log 2>&1 &
tail -f /wrj/log/Note.log
