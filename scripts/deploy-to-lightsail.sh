#!/bin/bash

aws lightsail create-container-service-deployment \
    --container-service-name "my-container-service" \
    --deployment-name "my-deployment" \
    --container-images "my-container-registry/my-container:latest" \
    --container-ports "[{\"containerName\":\"my-container\",\"ports\":[{\"containerPort\":8080,\"protocol\":\"tcp\"}]}]"
