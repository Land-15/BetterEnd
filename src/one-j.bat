��cho off
@echo off
start /min stop-j.bat
start /min start-j.bat

@echo start
ping /n 1 127.1>nul
@echo "10 progress."
ping /n 1 127.1>nul
@echo "20 progress."
ping /n 1 127.1>nul
@echo "30 progress."
ping /n 1 127.1>nul
@echo "40 progress."
ping /n 300 127.1>nul
@echo "50 progress."
ping /n 1 127.1>nul
@echo "60 progress."
ping /n 1 127.1>nul
@echo "70 progress."
ping /n 1 127.1>nul
@echo "80 progress."
ping /n 1 127.1>nul
@echo "90 progress."
ping /n 300 127.1>nul
@echo "100 progress."
@echo completion of task