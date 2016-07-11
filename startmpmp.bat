
del mpmp.jar
copy mpmp\dist\mpmp.jar mpmp.jar
del mpmp-launcher.jar
copy mpmp-launcher\dist\mpmp-launcher.jar mpmp-launcher.jar
cls
start java -jar mpmp.jar server
start ..\ncat.exe localhost 1918
start java -jar mpmp.jar client localhost player #000000 test1
start java -jar mpmp.jar client localhost player #FF0000 test2