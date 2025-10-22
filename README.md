Installation information

- I have used a MySql database. In order to run the application you must update the connection string in the application.properties file to an existing MySql server.  
   Update this configuration: spring.datasource.url=jdbc:mysql://172.25.0.1:3306/devices?createDatabaseIfNotExist=true

Containerization information

- My home machine is on Windows so I used the local ip for the mysql connection string so that the database is accesible from the container 
- Commands to run in the root folder, after building the project:
  1. docker build -t task-devices .
     (this creates the image named task-devices)
  2. docker run -p 8080:8080 task-devices
     (this starts a container)

    