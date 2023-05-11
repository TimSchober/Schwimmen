FROM hseeberger/scala-sbt:17.0.2_1.6.2_3.1.1
# Curl and Nano
RUN apt-get update && apt-get install -y curl nano
# App
WORKDIR /cards
ADD ./ /cards
# ADD . ../build.sbt
ADD ./build.sbt ./build.sbt
ADD ./project/plugins.sbt ./project/plugins.sbt
# Start command
# CMD sbt compile && tail -f /dev/null
EXPOSE 8080
CMD sbt run