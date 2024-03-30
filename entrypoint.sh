#!/bin/bash

# Wait for MySQL
until nc -z -v -w30 app_db 3306
do
  echo 'Waiting for MySQL...'
  sleep 1
done
echo "MySQL is up and running"

exec java -jar java-app.jar
