del mpmp.jar
copy mpmp\dist\mpmp.jar mpmp.jar
del mpmp-launcher.jar
copy mpmp-launcher\dist\mpmp-launcher.jar mpmp-launcher.jar
cls
start mpmp-launcher.jar
@pause >nul
taskkill -im java.exe -f
taskkill -im javaw.exe -f