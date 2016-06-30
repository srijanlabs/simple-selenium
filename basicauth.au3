AutoItSetOption("WinTitleMatchMode","2")  
WinWait("https://oncorps2stg.prod.acquia-sites.com - Google Chrome") 
Sleep(5000)  
$title = WinGetTitle("https://stage.oncorps.org - Google Chrome") ; retrives whole window title   
$UN=WinGetText($title,"User Name:")  
ControlSend($title,"",$UN,"oncorps");Sets Username  
Sleep(2000)
$PWD=WinGetText($title,"Password:") 
Send("{TAB 1}")  
ControlSend($title,"",$PWD,"bigdata");Sets PWD 
Sleep(2000) 
Send("{ENTER}") 