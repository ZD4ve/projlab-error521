@echo off
if not exist bin mkdir bin
dir /s /B *.java | findstr /V "src\tester" > sources.txt
javac -d bin @sources.txt
if errorlevel 1 (
    echo Compilation failed.
    exit /b 1
)
jar cfe Fungorium.jar proto.Prototype -C bin .
del sources.txt