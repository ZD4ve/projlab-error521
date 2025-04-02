@echo off
if not exist bin mkdir bin
dir /s /B *.java | findstr /V "src\tester" > sources.txt
for /f "delims=" %%f in (sources.txt) do (
    set "line=%%f"
    setlocal enabledelayedexpansion
    echo "!line:\=\\!" >> quoted_sources.txt
    endlocal
)
del sources.txt
javac -d bin @quoted_sources.txt
del quoted_sources.txt
if errorlevel 1 (
    echo Compilation failed.
    exit /b 1
)
jar cfe Fungorium.jar proto.Prototype -C bin .