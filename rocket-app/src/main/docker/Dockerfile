FROM java:8
MAINTAINER Ranger wilton.icp@gmail.com
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
  && echo 'Asia/Shanghai' >/etc/timezone \

VOLUME /tmp
EXPOSE 8282

ADD rocket-admin-1.0.0.jar /rocket/rocket-admin-1.0.0.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/rocket/rocket-admin-1.0.0.jar"]