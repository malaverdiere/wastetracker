#!/bin/bash

cd ../resteasy
mvn -o clean install
if [ $? = 1 ]; then
        exit 1
fi

cd ../WasteTracker-Server
mvn -o clean install -P deploy
if [ $? = 1 ]; then
        exit 1
fi

cd ../WasteTracker-ear
mvn -o clean package

cp target/WasteTracker.ear /home/fidox/Projects/WasteTracker/deploy
