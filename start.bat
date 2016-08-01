@echo off
set java_home=%JAVA_HOME%
set "java_home=C:\Program Files\Java\jdk1.6.0_26"
set "your_dir=D:\workspace\cn.ieclipse.cap"
set "your_jar=HttpMonitor.jar"
set "my_dir=%cd%"
cd "%your_dir%"
echo %cd%
echo %my_dir%\temp\classlist.txt
"%java_home%\bin\java.exe" -verbose:class -jar "%your_jar%" >%my_dir%\temp\classlist.txt
cd %my_dir%
rem cd bin
rem %java_home%\bin\java cn.ieclipse.jrelite.Classify
rem %java_home%\bin\java cn.ieclipse.jrelite.Packager
rem cd %cur_dir%