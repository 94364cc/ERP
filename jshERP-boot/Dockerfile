FROM 10.10.102.113:8443/library/eclipse-temurin:11-jdk-arthas
WORKDIR /app
ENV JAR_FILE=/app/app.jar \
    JAR_OPTS="-Dloader.path=."

COPY ./*.jar ./app.jar
COPY start.sh ./start.sh

# start the script to run your application. Take note that there are several other optional environment variables supported
# for details, please see the start.sh.
ENTRYPOINT ["/bin/bash", "start.sh"]
