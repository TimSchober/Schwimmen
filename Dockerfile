FROM hseeberger/scala-sbt:17.0.2_1.6.2_3.1.1
# Curl and Nano
RUN apt-get update && apt-get install -y curl nano
# App
WORKDIR /Schwimmen
ADD ./cards /Schwimmen/cards
ADD ./player /Schwimmen/player
ADD ./src /Schwimmen/src
ADD ./build.sbt /Schwimmen/build.sbt
ADD ./project/plugins.sbt /Schwimmen/project/plugins.sbt
# Start command
# CMD sbt compile && tail -f /dev/null
CMD sbt run