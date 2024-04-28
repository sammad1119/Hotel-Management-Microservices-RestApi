# Microservices

Microservices architecture is an approach to software development where a large application is broken down into smaller, independent services that can be developed, deployed, and scaled individually.
<br>

<b> Key characteristics of microservices include:</b>
<br>

1. Decomposition: Breaking down an application into smaller, independently deployable services, each responsible for a specific aspect of the application's functionality.
<br>

2. Loose coupling: Services are independent and can be developed, deployed, and scaled independently of each other. This allows for greater agility and flexibility in development and deployment.
<br>

3. Independent deployment: Each service can be deployed and updated independently, without impacting other services. This enables faster release cycles and easier maintenance.
<br>

4. Resilience: Failure in one service should not bring down the entire system. Microservices are designed to be resilient, with mechanisms such as fault isolation and graceful degradation.
<br>

5. Scalability: Services can be scaled independently based on demand, allowing for efficient resource utilization and better performance.
<br>

6. Technology diversity: Different services within a microservices architecture can be implemented using different technologies, languages, and frameworks, based on the specific requirements of each service.
<br>

7. Autonomy: Development teams can have autonomy over individual services, allowing them to choose the most suitable technology stack, development methodologies, and deployment strategies.
<br>


# Rest Api

A RESTful API (Representational State Transfer API) is an architectural style for designing networked applications. It is based on the principles of REST, which was introduced by Roy Fielding in his doctoral dissertation in 2000. RESTful APIs are commonly used in web development to enable communication between different systems or services over the internet.
<br>

RESTful APIs are widely used in web development due to their simplicity, scalability, and compatibility with existing web standards. They are commonly used to build web services, mobile backend APIs, and integrations between different systems or services. Additionally, RESTful APIs are often used in conjunction with other technologies such as JSON (JavaScript Object Notation) or XML (eXtensible Markup Language) for representing resource data.
# Docker
Docker is a platform for developing, shipping, and running applications in containers. Containers are lightweight, portable, and self-sufficient units that encapsulate all the necessary dependencies and configurations required to run an application. Docker provides tools and APIs to automate the process of creating, deploying, and managing containers, making it easier to build and deploy applications in a consistent and reproducible manner across different environments.
<br>

<b> Key components of Docker include:</b>

1. Docker Engine: The core runtime environment for containers. It runs on the host operating system and manages container lifecycle, including starting, stopping, and deleting containers.
<br>

2. Docker Image: A read-only template that contains the application code, runtime environment, libraries, and dependencies required to run an application. Images are used to create containers.
<br>

3. Docker Container: An instance of a Docker image that runs as a lightweight, isolated process on the host system. Containers are portable and can run on any system that supports Docker.
<br>

4. Dockerfile: A text file that contains instructions for building a Docker image. Dockerfiles specify the base image, dependencies, configuration settings, and commands required to create the image.
<br>

5. Docker Registry: A centralized repository for storing and sharing Docker images. Docker Hub is the official public registry maintained by Docker, but organizations can also set up private registries to store proprietary or sensitive images.
<br>

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
# Tools and Technologies 

1. Java,Maven (Spring, Springboot)
2. Docker(File, image, Container, Compose,Docker Daemon , Docker Client, Docker hub)
3. Rest Api(Crud, Rest template)
4. MYSQL(Workbench,Communication,Connection)

# ERD

<img src="Hotel%20Management%20ERD.png">
