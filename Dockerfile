FROM openjdk:11.0.9.1-jre

ENV PORT=8443
ENV ENVIRONMENT_NAME=develop

WORKDIR /opt/app/
COPY --from build ./target/*.jar ./

CMD ["sh","-c","find -type f -name '*.jar' | env spring_profiles_active=$ENVIRONMENT_NAME xargs java -jar -Dserver.port=${PORT}"]