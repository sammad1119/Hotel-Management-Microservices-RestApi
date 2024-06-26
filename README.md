# Microservices

Microservices architecture is an approach to software development where a large application is broken down into smaller, independent services that can be developed, deployed, and scaled individually.
<br>

<b> Key characteristics of microservices include:</b>
1. Decomposition: Breaking down an application into smaller, independently deployable services, each responsible for a specific aspect of the application's functionality.
2. Loose coupling: Services are independent and can be developed, deployed, and scaled independently of each other. This allows for greater agility and flexibility in development and deployment.
3. Independent deployment: Each service can be deployed and updated independently, without impacting other services. This enables faster release cycles and easier maintenance.
4. Resilience: Failure in one service should not bring down the entire system. Microservices are designed to be resilient, with mechanisms such as fault isolation and graceful degradation.
5. Scalability: Services can be scaled independently based on demand, allowing for efficient resource utilization and better performance.
6. Technology diversity: Different services within a microservices architecture can be implemented using different technologies, languages, and frameworks, based on the specific requirements of each service.
7. Autonomy: Development teams can have autonomy over individual services, allowing them to choose the most suitable technology stack, development methodologies, and deployment strategies.
# Rest Api

A RESTful API (Representational State Transfer API) is an architectural style for designing networked applications. It is based on the principles of REST, which was introduced by Roy Fielding in his doctoral dissertation in 2000. RESTful APIs are commonly used in web development to enable communication between different systems or services over the internet.
RESTful APIs are widely used in web development due to their simplicity, scalability, and compatibility with existing web standards. They are commonly used to build web services, mobile backend APIs, and integrations between different systems or services. Additionally, RESTful APIs are often used in conjunction with other technologies such as JSON (JavaScript Object Notation) or XML (eXtensible Markup Language) for representing resource data.
# Docker
Docker is a platform for developing, shipping, and running applications in containers. Containers are lightweight, portable, and self-sufficient units that encapsulate all the necessary dependencies and configurations required to run an application. Docker provides tools and APIs to automate the process of creating, deploying, and managing containers, making it easier to build and deploy applications in a consistent and reproducible manner across different environments.
<b> Key components of Docker include:</b>

1. Docker Engine: The core runtime environment for containers. It runs on the host operating system and manages container lifecycle, including starting, stopping, and deleting containers.
2. Docker Image: A read-only template that contains the application code, runtime environment, libraries, and dependencies required to run an application. Images are used to create containers.
3. Docker Container: An instance of a Docker image that runs as a lightweight, isolated process on the host system. Containers are portable and can run on any system that supports Docker.
4. Dockerfile: A text file that contains instructions for building a Docker image. Dockerfiles specify the base image, dependencies, configuration settings, and commands required to create the image.
5. Docker Registry: A centralized repository for storing and sharing Docker images. Docker Hub is the official public registry maintained by Docker, but organizations can also set up private registries to store proprietary or sensitive images.
6. Docker Compose: A tool for defining and running multi-container Docker applications. Docker Compose uses a YAML file to define the services, networks, and volumes required by an application and simplifies the process of managing complex multi-container deployments.

# Mysql 
MySQL is an open-source relational database management system (RDBMS) that is widely used for building and managing databases in various applications. It is developed, distributed, and supported by Oracle Corporation.

<br>
<b> Key features of MySQL include:</b>

1. Relational Database: MySQL is a relational database, which means it organizes data into tables consisting of rows and columns, with each row uniquely identified by a primary key.
2. SQL Support: MySQL supports SQL (Structured Query Language), which is a standard language for interacting with relational databases. Users can perform operations such as querying data, inserting, updating, and deleting records using SQL commands.
3. Data Integrity: MySQL provides mechanisms to enforce data integrity constraints, such as primary keys, foreign keys, unique constraints, and check constraints, ensuring the accuracy and consistency of data stored in the database.
4. ACID Compliance: MySQL supports ACID (Atomicity, Consistency, Isolation, Durability) properties, which ensure that database transactions are executed reliably and securely, even in the presence of failures.
5. Scalability: MySQL is designed to scale from small, single-server deployments to large, distributed systems. It supports various replication and clustering techniques for high availability and scalability, including master-slave replication, multi-master replication, and sharding.
6. Security: MySQL provides features for securing data and controlling access to the database, including user authentication, access control lists (ACLs), encryption, and auditing.
7. Performance: MySQL is optimized for performance, with features such as indexing, query optimization, caching, and storage engine options (such as InnoDB, MyISAM, and Memory) to improve database performance and efficiency.

# Postman

Postman is an application used for API development and testing. It provides a user-friendly interface for creating, testing, and managing APIs. With Postman, developers can easily send requests to APIs, inspect responses, organize APIs into collections, and collaborate with team members. It supports various HTTP methods, authentication mechanisms, and environments, making it a versatile tool for API development workflows.

# Tools and Technologies 

1. Java,Maven (Spring, Springboot).
2. Docker(File, image, Container, Compose,Docker Daemon , Docker Client, Docker hub, Deployment,Containerization).
3. Rest Api(Crud, Rest template).
4. MYSQL(Workbench,Communication,Connection).
5. postman(Api Testing and Managing).
6. intelij idea.
7. vscode.
8. Docker Desktop.

# Communication of mysql with app Commands

1. step 1: create a docker image of mysql database with password and database name.
docker run -p 3307:3306 --name mysqldb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=test mysql:8.0.13
2. step 2 do spring configuration
-DMYSQL_USER=root -DMYSQL_PASSWORD=root -DMYSQL_PORT=3307
3. step 3 application.properties
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc: mysql://${MYSQL_HOST: localhost}:${MYSQL_PORT: 3306}/test
spring.datasource.username=${MYSQL_USER: root} spring.datasource.password=${MYSQL_PASSWORD: Ravi@1998}
4. step 4-create docker network and connect mysql container with that
docker network create spring-net
docker network connect spring-net mysqldb
5. steps 5-create docker image of app and connect with network, provide all the database details
  docker run -p 9090:8080 --name springboot-mysql-docker --net spring-net-e MYSQL_HOST=mysqldb -e MYSQL_USER=root w-e MYSQL_PASSWORD=root-e MYSQL_PORT=3306  springboot-mysql-docker

# ERD
Erd diagram and workflow of the following Project.

<br>
<img src="Hotel%20Management%20ERD.png">
