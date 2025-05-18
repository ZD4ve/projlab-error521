#!/bin/bash
if [ ! -d "bin" ]; then
    mkdir bin
fi
find . -name "*.java" | grep -v "src/tester" | xargs javac -d bin
cp -r resources bin/
jar cfe Fungorium.jar gui.Main -C bin .
