#!/bin/bash

CURRENT_DIR=${PWD}

#sudo docker run -p 80:8080 swagger-ui
docker run -p 80:8080 -e API_URL=http://192.168.99.100/swagger.json  -e SWAGGER_JSON=/dist/swagger.json -v ${CURRENT_DIR}/dist:/dist swaggerapi/swagger-ui 
#docker run -p 80:8080 -e SWAGGER_JSON=/dist/swagger.json -v ${CURRENT_DIR}/dist:/dist swaggerapi/swagger-ui 
