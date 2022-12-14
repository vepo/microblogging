#!/bin/bash
sdk use java 19-open
rm src/main/resources/META-INF/resources/publicKey.pem
cd src/main/webapp/
npm run watch > ../../../webapp-build.log 2> ../../../webapp-build.err.log &
NPM_PID=$!
cd -

until [ -f src/main/resources/META-INF/resources/publicKey.pem ]
do
     sleep 5
done

mvn quarkus:dev
kill -15 $NPM_PID
ps -aux | grep "ng build" | awk '{print $2}' | xargs sudo kill -9