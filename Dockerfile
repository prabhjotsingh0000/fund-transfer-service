FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory
COPY build/libs/FundTransfer-1.0-SNAPSHOT.jar FundTransfer.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "FundTransfer.jar"]