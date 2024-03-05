This README provides instructions on how to quickly get booking-system application running inside a Docker container.

# 1. Prerequisites

Before you begin, make sure you have Docker installed on your machine. You can download Docker from https://www.docker.com/get-started.

# 2. Steps to Run

Follow these simple steps to get booking-system application up and running in Docker:

## Build the Docker Image

Navigate to the root directory of the application where the Dockerfile is located and run:

docker build --build-arg JAR_FILE=target/*.jar -t myorg/myapp .

Replace myorg/myapp with the name you wish to give your Docker image.

## Run the Application

After the build completes, start your application by running:

docker compose up

# 3. Accessing REST API Documentation

The application is equipped with Swagger UI, which provides interactive documentation for the REST API. You can access it by navigating to:

http://localhost:8080/swagger-ui/index.html

Here you can view all the REST endpoints, their request/response schemas, and try out the API directly in the browser.

## JavaDoc Documentation

The JavaDoc is available at the following URL when the application is running:

http://localhost:8080/docs/index.html

# 4. Stopping the Application

To stop the Docker container, you'll first need to find the container ID:

docker ps

Then stop the container using:

docker stop <container-id>

Further Help
For more detailed instructions on using Docker, refer to the official Docker documentation at https://docs.docker.com/.