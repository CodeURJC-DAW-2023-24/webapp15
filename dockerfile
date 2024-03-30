# Use the official OpenJDK image as a parent image
FROM openjdk:23-jdk-slim

# Install netcat-openbsd
RUN apt-get update && \
    apt-get install -y netcat-openbsd && \
    rm -rf /var/lib/apt/lists/*

# Copy the jar file into the container
COPY target/webapp15-0.0.1-SNAPSHOT.jar java-app.jar

# Copy your entrypoint script into the container
COPY entrypoint.sh /usr/local/bin/entrypoint.sh

# Make your entrypoint script executable
RUN chmod +x /usr/local/bin/entrypoint.sh

# Specify the container's entrypoint as entrypoint.sh
ENTRYPOINT ["entrypoint.sh"]
