FROM java:8

ENV DB_HOST=${DB_HOST}
ENV DB_PORT=${DB_PORT}
ENV DB_NAME=${DB_NAME}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}
ENV CHANGELOG_FILE_PATH="db/changelog.yml"
ENV LIQUIBASE_VERSION=3.10.0
ENV MYSQL_CONNECTOR_VERSION=8.0.20

# set working directory
WORKDIR /app

RUN wget https://github.com/liquibase/liquibase/releases/download/v${LIQUIBASE_VERSION}/liquibase-${LIQUIBASE_VERSION}.zip
RUN unzip liquibase-${LIQUIBASE_VERSION}.zip -d liquibase
RUN wget https://repo1.maven.org/maven2/mysql/mysql-connector-java/${MYSQL_CONNECTOR_VERSION}/mysql-connector-java-${MYSQL_CONNECTOR_VERSION}.jar

# Copy files
COPY ./db ./db

RUN ls

# Update
CMD ./liquibase/liquibase --classpath=mysql-connector-java-${MYSQL_CONNECTOR_VERSION}.jar --url="jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}" --username=${DB_USERNAME} --password=${DB_PASSWORD} --changeLogFile=${CHANGELOG_FILE_PATH} --logLevel=debug update