#docker-compose.yml to build reviews and ratings docker image
FROM centos:7.3.1611

#check this

MAINTAINER CommerceOnCloud Team
ENV JAVA_VERSION 8u31
ENV BUILD_VERSION b13

#USER root

ENV     JDK_VERSION="jdk-8u31-linux-x64.rpm" \
        java_version=jdk1.8.0_161 \
        jdkpath=/usr/java \
        JDK_INSTALLER=/tmp/devops/docker-addons/jdk_installer \
        JAVA_OPTS="-server -Xmx4g -Xms4g -XX:MaxPermSize=1024m -XX:+UseG1GC -Dspring.profiles.active=GCP "

RUN     mkdir -p /var/CoC/log/

# Upgrading system
RUN yum -y upgrade && \
    yum -y update && \
    yum -y install wget unzip findutils && \
    wget -c --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u131-b11/d54c1d3a095b4ff2b6607d096fa80163/jdk-8u131-linux-x64.rpm && \
    rpm -Uvh jdk-8u131-linux-x64.rpm && \
    yum -y clean all && \
    rm -f jdk-8u131-linux-x64.rpm MD5SUM8 && \
    echo export JAVA_HOME=$jdkpath/$java_version >> /etc/profile && \
    yum clean all && rm -rf /var/cache/yum/* && rm -rf coc-pricing-service-*.jar

ENV JAVA_HOME /usr/java/latest

COPY target/coc-pricing-service-*.jar /tmp

VOLUME  ["/sys/fs/cgroup"]
VOLUME  ["/usr/java/jdk1.8.0_141"]
EXPOSE 12786
CMD ["/bin/bash"]

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /tmp/coc--pricing-service-*.jar" ]
