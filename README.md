Installation information

- I have used a MySql database. In order to run the application you must update the connection string in the application.properties file to an existing MySql server.  
   Update this configuration: spring.datasource.url=jdbc:mysql://172.25.0.1:3306/devices?createDatabaseIfNotExist=true

Containerization information

- To create the image and run the container run in the root folder this command: docker compose up

Documentation

- You can access the swagger documentation at: http://localhost:8080/swagger-ui/index.html