#!/bin/bash
if [ ! -d "bin" ]; then
    mkdir bin
fi
javac -d bin src/tester/Tester.java src/tester/Test.java
if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
fi
java -cp bin tester.Tester
