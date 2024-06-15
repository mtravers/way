# Heroku deployment
set -e
lein do clean, uberjar
heroku deploy:jar target/way-0.1.1-SNAPSHOT-standalone.jar --jdk=21.0.2
