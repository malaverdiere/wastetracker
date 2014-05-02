#!/bin/bash

cd ../resteasy
mvn clean install
if [ $? = 1 ]; then
        exit 1
fi

cd ../WasteTracker-Server
mvn clean install -P deploy
if [ $? = 1 ]; then
        exit 1
fi

cd ../WasteTracker-ear
mvn clean package

#cp target/WasteTracker.ear /home/fidox/Projects/WasteTracker/deploy
