FROM alpine:edge
 # FROM alpine:3.4

# From https://github.com/ewolff/docker-java/blob/master/Dockerfile
ENV JAVA_VERSION=8 \
    JAVA_UPDATE=121 \
    JAVA_BUILD=13 \
    JAVA_HOME=/usr/lib/jvm/default-jvm



    # Maven from official docker https://github.com/carlossg/docker-maven
    RUN apk add --no-cache curl tar bash

    ARG MAVEN_VERSION=3.3.9
    ARG USER_HOME_DIR="/root"

    RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
      && curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz \
        | tar -xzC /usr/share/maven --strip-components=1 \
      && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

    ENV MAVEN_HOME /usr/share/maven
    ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

    COPY mvn-entrypoint.sh /usr/local/bin/mvn-entrypoint.sh
    COPY settings-docker.xml /usr/share/maven/ref/

    VOLUME "$USER_HOME_DIR/.m2"

    ENTRYPOINT ["/usr/local/bin/mvn-entrypoint.sh"]
    CMD ["mvn"]

    # Mongo https://hub.docker.com/r/mvertes/alpine-mongo/~/dockerfile/
    RUN \
    echo http://dl-4.alpinelinux.org/alpine/edge/testing >> /etc/apk/repositories && \
    apk add --no-cache mongodb && \
    rm /usr/bin/mongosniff /usr/bin/mongoperf

    VOLUME /data/db
    EXPOSE 27017 28017

    COPY run.sh /root
    ENTRYPOINT [ "/root/run.sh" ]
    CMD [ "mongod" ]
