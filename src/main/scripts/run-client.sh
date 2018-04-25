#!/bin/bash

if [ -z "$1" ]
  then
    echo "Please provide endpoint (i.e. localhost:8080)"
    exit
fi

while true; do
http http://$1/ops/info
echo -e  "\n-------------------"
sleep 3
done