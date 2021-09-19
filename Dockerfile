FROM openjdk:11

ENV PROFILE='local'

# set working directory
WORKDIR /app

# Install app dependencies
COPY . ./
RUN ./gradlew clean

# Build app
RUN ./gradlew build
RUN rm -R src

# Start app
CMD SPRING_PROFILES_ACTIVE=${PROFILE} ./gradlew runApp
