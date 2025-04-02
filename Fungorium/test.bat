@echo off
if not exist bin mkdir bin
javac -d bin "src\tester\Tester.java" "src\tester\Test.java"
if errorlevel 1 (
    echo Compilation failed.
    exit /b 1
)
java -cp bin tester.Tester