@echo off

echo Enter Host Address below (ip address):
echo.
set /p host=

start cmd.exe /k "cd C:\Users\{jar_location_placeholder} & java -jar PortScanner.jar %host%"