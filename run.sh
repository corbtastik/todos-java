#!/usr/bin/env bash

java \
-Dspring.cloud.config.username=user \
-Dspring.cloud.config.password=howdy \
-jar target/todos-java-0.0.1-RELEASE.jar \
--spring.profiles.active=dev


