# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY ./application/build/libs/*.jar /app/

# Make port 8082 available to the world outside this container
EXPOSE 8082

# Define the command to run the application when the container starts
CMD ["java", "-jar", "application-0.0.1-SNAPSHOT.jar"]
