lazy val commonSettings = Seq(
  scalaVersion := "3.2.2",
  libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0",
  libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.15",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test",
  libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.1.0",
  libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.0-RC7",
  libraryDependencies += "com.google.inject" % "guice" % "5.1.0",
  libraryDependencies += "net.codingwell" %% "scala-guice" % "5.1.1",
  coverageExcludedPackages := "de.htwg.se.schwimmen.aUI.GUI;" +
    "de.htwg.se.schwimmen.model.fileIOComponent.*;"
)
lazy val root = project
  .in(file("."))
  .dependsOn(model)
  .aggregate(model)
  .settings(
    name := "Schwimmen",
    version := "0.1",
    commonSettings
  )

lazy val model = (project in file("model"))
  .settings(
    name := "model",
    version := "0.1",
    commonSettings
  )
