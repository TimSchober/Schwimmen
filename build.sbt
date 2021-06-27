name := "Schwimmen"

version := "0.1"

scalaVersion := "2.13.5"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.7"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.7" % "test"

libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0"

coverageExcludedPackages := "de.htwg.se.schwimmen.aUI.GUI;"
