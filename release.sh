#!/usr/bin/env bash
# Factor 5 https://12factor.net/build-release-run
echo "./mvnw clean package -B"\
" one"\
" two" > output.txt