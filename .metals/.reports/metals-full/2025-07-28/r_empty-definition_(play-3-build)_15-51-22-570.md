error id: file://<WORKSPACE>/build.sbt:
file://<WORKSPACE>/build.sbt
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 318
uri: file://<WORKSPACE>/build.sbt
text:
```scala
name := """play-3"""
organization := "fci"

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.13.16"

libraryDependencies ++= Seq(
  guice,
  "mysql" % "mysql-connector-java" % "8.0.33",
  "io.ebean" %% "ebean-play" % "8.3.4",
  "io.ebean" % "e@@bean" % "13.23.1",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.14.2"
)

dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.14.2"


//lazy val root = (project in file("."))
//  .enablePlugins(PlayJava, PlayEbean)
```


#### Short summary: 

empty definition using pc, found symbol in pc: 