FROM gradle:jdk21-jammy AS build

ADD app /home/gradle/app
ADD assets /home/gradle/assets
ADD db /home/gradle/db
ADD timetables /home/gradle/timetables

WORKDIR /home/gradle/app

RUN gradle build --no-daemon

RUN tar -xvf build/distributions/app-bundle.tar

FROM eclipse-temurin:21-jdk-jammy

COPY --from=build /home/gradle/app/app-bundle/ ./app-bundle/
COPY --from=build /home/gradle/db ./db
COPY --from=build /home/gradle/assets ./assets
COPY --from=build /home/gradle/timetables ./timetables

CMD ["./app-bundle/bin/app"]