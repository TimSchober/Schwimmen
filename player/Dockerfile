FROM hseeberger/scala-sbt:17.0.2_1.6.2_3.1.1
# Curl and Nano
RUN apt-get update && apt-get install -y curl nano
# App
WORKDIR /player
ADD . /player
ADD ./build.sbt ./build.sbt
ADD ./project/plugins.sbt ./project/plugins.sbt
# Start command
# CMD sbt compile && tail -f /dev/null
EXPOSE 8081
CMD sbt run