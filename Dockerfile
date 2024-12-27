FROM openjdk:21

ADD build/libs/car-listing-producer-1.0.0.jar car-listing-producer.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "car-listing-producer.jar"]