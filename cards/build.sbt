lazy val commonSettings = Seq(
  fork := true,
  scalaVersion := "3.2.2",
  resolvers += "Slick Repo" at "https://repo.typesafe.com/typesafe/maven-releases/",
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
  libraryDependencies += ("com.typesafe.slick" %% "slick" % "3.3.3").cross(CrossVersion.for3Use2_13),
  libraryDependencies += "org.postgresql" % "postgresql" % "42.3.4",
  libraryDependencies += ("com.typesafe.slick" %% "slick-hikaricp" % "3.3.3").cross(CrossVersion.for3Use2_13),
  libraryDependencies += ("org.mongodb.scala" %% "mongo-scala-driver" % "4.3.0").cross(CrossVersion.for3Use2_13),
  libraryDependencies += ("com.github.tminglei" %% "slick-pg" % "0.20.3").cross(CrossVersion.for3Use2_13),
  coverageExcludedPackages := "de.htwg.se.schwimmen.aUI.GUI;" +
    "de.htwg.se.schwimmen.model.fileIOComponent.*;"
)

lazy val cards = (project in file("."))
  .settings(
    name := "cards",
    version := "0.1",
    commonSettings
  )
