@echo off

rem %1
rem %2
rem set "jre=C:\Program Files\Java\jre6"
rem set "jar=%jre%\bin\jar"
rem set "lib=%jre%\lib\jce.jar"
rem set "dir=temp\unziped_lib"
echo %1
echo %2
echo %3
set "jdk=%2"
echo %jdk%
set jar=%jdk%\bin\jar
set "lib=%3"
echo jar=%jar%
call "%2" xf "%lib%"
@pause