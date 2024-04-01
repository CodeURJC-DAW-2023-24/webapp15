#!/bin/bash

# Definir variables
DOCKER_USERNAME="jgar2020"
DOCKER_PASSWORD="docker 2024"
IMAGE_NAME="java_app"
VERSION="1.0"

# Iniciar sesión en Docker Hub
echo "Iniciando sesión en Docker Hub..."
echo "$DOCKER_PASSWORD" | docker login --username "$DOCKER_USERNAME" --password-stdin

# Construir y publicar la imagen
echo "Construyendo y publicando la imagen..."
docker build -t $DOCKER_USERNAME/$IMAGE_NAME:$VERSION -f Dockerfile ..
docker push $DOCKER_USERNAME/$IMAGE_NAME:$VERSION

echo "Imagen publicada exitosamente."