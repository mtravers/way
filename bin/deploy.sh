# Heroku deployment
set -e
lein uberjar
heroku deploy:jar target/way-0.1.0-SNAPSHOT-standalone.jar --jdk=21.0.2
