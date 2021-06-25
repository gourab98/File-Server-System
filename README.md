# CSE324: Lab Project (SWE)
## Group members:

[Gourab Saha](https://github.com/gourab98)  (Reg: 2017831004)

[Mehedi Hasan](https://github.com/Mahdi-Hasan) (Reg: 2017831040)

##  File-Server-System: 

In this Project, we are trying to develop a File  Server System in Java to allow users/clients to download a file from the server or upload a file into the server using sockets.

## Base Features:
* When the server system is loaded, it will initiate the required communication setup so that clients can join in the system.
* When the client system is loaded, an option to provide the IP address/port number of the server.
* A proper GUI with a directory service that can show the available directories and files.
* A proper context-menu (a right-click mouse operation) for uploading a file in the server.
* A proper context-menu (a right-click mouse operation) for downloading a file from the server.

## How to run this Project:
Please, Open this project with your IDE(intellij). After that setup SDK (java version "14.0.1") and  build and run the Server.java main method. Then build and run the Client. java main method. While Client GUI is visible, please enter the correct IP ADD: "localhost" and the Port Number: "1212", then press connect. There is a pop up console to tell you about your server connection status. Then connection is established, file upload, download and delete can be easily done by the users.  

## Project Details: 
This project is built with TCP/IP Socket Programming in Java. It can transfer files among computers which are connected in the same network. It provides user interface created with Java AWT and Java Swing. This java application can transfer .txt, .xlsx, .pdf, image files, .mp4 and other video and audio format files. The application shows the files and directories present in the server working directory and allows the client to select files and download them in the client system. It allows the client to upload files to the server working directory. It also allows the server to delete files from the server working directory.

### Client
***
![alt Client](https://github.com/gourab98/File-Server-System/blob/main/Screenshot/Client.PNG)

### Server
***
![alt Server](https://github.com/gourab98/File-Server-System/blob/main/Screenshot/Server.PNG)

### Client Connected
***
![alt Client Connected](https://github.com/gourab98/File-Server-System/blob/main/Screenshot/ClientConnect.PNG)

### Server Files
***
![alt Server Files](https://github.com/gourab98/File-Server-System/blob/main/Screenshot/ServerFileList.PNG)

### Delete From Server
***
![alt Delete From Server](https://github.com/gourab98/File-Server-System/blob/main/Screenshot/DeleteByServer.PNG)

### Server And Client(Download from Server)
***
![alt Server And Client](https://github.com/gourab98/File-Server-System/blob/main/Screenshot/ClientandServer.PNG)

 


