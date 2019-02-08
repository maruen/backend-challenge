#!/bin/bash 

SWAGGER_PROJ_DIR=${PWD}
SWAGGER_DIST_DIR=${SWAGGER_PROJ_DIR}/dist
SWAGGER_PLACE_FOR_PLACING_JAVA_FILES_TO_GEN_DOCS=${SWAGGER_DIST_DIR}/src/main/java

######## REMOVING OLD STUFFS ############
rm -rf ${SWAGGER_PLACE_FOR_PLACING_JAVA_FILES_TO_GEN_DOCS}
rm -rf ${SWAGGER_DIST_DIR}/target
rm -rf ${SWAGGER_DIST_DIR}/swagger.json

cd ${SWAGGER_DIST_DIR}/../../../src/main/java  ; find . -name "*.java"  -exec rsync -rR '{}' ${SWAGGER_PLACE_FOR_PLACING_JAVA_FILES_TO_GEN_DOCS} \;
cd ${SWAGGER_DIST_DIR}
mvn clean compile

mv ${SWAGGER_DIST_DIR}/generated/swagger-ui/swagger.json ${SWAGGER_DIST_DIR}


