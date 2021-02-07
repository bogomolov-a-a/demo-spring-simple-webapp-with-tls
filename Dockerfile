# Run use jre 11
FROM openjdk:11.0.9.1-jre

# default port
ENV PORT=8443

WORKDIR /opt/app/

COPY ./app/build/libs/*.jar ./
# for certificate
VOLUME ./tls
# for logs
VOLUME ./logs
# open port
EXPOSE $PORT
# execute app as bootable jar
CMD ["sh","-c","find -type f -name '*.jar' | xargs java -jar "]