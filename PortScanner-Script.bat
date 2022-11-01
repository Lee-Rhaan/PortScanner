@echo off

echo Enter Host Address below (ip address):
echo.
set /p host=

start cmd.exe /k "cd C:\Users\rhaan\Documents\My Projects\PortScanner\target & java -jar PortScanner.jar %host%"