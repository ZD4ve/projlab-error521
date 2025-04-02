#!/bin/bash
if [ ! -d "bin" ]; then
    mkdir bin
fi
find . -name "*.java" | grep -v "src/tester" | xargs javac -d bin
jar cfe Fungorium.jar proto.Prototype -C bin .
