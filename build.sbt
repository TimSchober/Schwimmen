lazy val commonSettings = Seq(
  fork := true,
  scalaVersion := "3.2.2",
  libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0",
  libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.15",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test",
  libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.1.0",
  libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.0-RC7",
  libraryDependencies += "com.google.inject" % "guice" % "5.1.0",
  libraryDependencies += "net.codingwell" %% "scala-guice" % "5.1.1",
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-http" % "10.5.0",
    "com.typesafe.akka" %% "akka-actor" % "2.8.0",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.0",
    "com.typesafe.akka" %% "akka-actor-typed" % "2.8.0",
    "com.typesafe.akka" %% "akka-stream-typed" % "2.8.0"
  ),
  coverageExcludedPackages := "de.htwg.se.schwimmen.aUI.GUI;" +
    "de.htwg.se.schwimmen.model.fileIOComponent.*;"
)
lazy val root = (project in file("."))
  .dependsOn(cards, player, fileIO)
  .aggregate(cards, player, fileIO)
  .settings(
    name := "Schwimmen",
    version := "0.1",
    commonSettings
  )

lazy val cards = (project in file("cards"))
  .settings(
    name := "cards",
    version := "0.1",
    commonSettings
  )

lazy val player = (project in file("player"))
  .settings(
    name := "player",
    version := "0.1",
    commonSettings
  )

lazy val fileIO = (project in file("fileIO"))
  .dependsOn(player)
  .aggregate(player)
  .settings(
    name := "fileIO",
    version := "0.1",
    commonSettings
  )
