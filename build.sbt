name := "Schwimmen"

version := "0.1"

scalaVersion := "3.0.0"
crossScalaVersions ++= Seq("2.13.5", "3.0.0")

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.15"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test"

libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0"

libraryDependencies += "com.google.inject" % "guice" % "5.1.0"
libraryDependencies += "net.codingwell" %% "scala-guice" % "5.1.1"

libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.1.0"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.0-RC7"

coverageExcludedPackages := "de.htwg.se.schwimmen.aUI.GUI;" +
  "de.htwg.se.schwimmen.model.fileIOComponent.*;"
