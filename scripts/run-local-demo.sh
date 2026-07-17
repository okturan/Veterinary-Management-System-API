#!/usr/bin/env sh
set -eu

# Starts a local-only, synthetic demonstration backed by an ephemeral H2 database.
# Nothing is read from or written to the configured PostgreSQL database.
export VET_DB_URL='jdbc:h2:mem:veterinary;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;DATABASE_TO_LOWER=TRUE'
export VET_DB_USER='sa'
export VET_DB_PASSWORD=''
export VET_DB_DRIVER='org.h2.Driver'
export VET_DDL_AUTO='create-drop'
export VET_SEED_DEMO_DATA='true'
export SPRING_H2_CONSOLE_ENABLED='false'
export SPRING_DEVTOOLS_ADD_PROPERTIES='false'

exec ./mvnw --batch-mode --no-transfer-progress \
  -Dspring-boot.run.useTestClasspath=true \
  spring-boot:run
