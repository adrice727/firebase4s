import sbt.Keys._

lazy val paradiseVersion = "2.1.0"
lazy val googleApiVersion = "1.23.0"
lazy val guavaVersion = "20.0"
lazy val firebaseVersion = "5.8.0"

lazy val common = Seq(
  organization := "com.github.firebase4s",
  version := "0.0.1",
  scalaVersion := "2.12.2",
  crossScalaVersions := Seq("2.10.2",
                            "2.10.3",
                            "2.10.4",
                            "2.10.5",
                            "2.10.6",
                            "2.11.0",
                            "2.11.1",
                            "2.11.2",
                            "2.11.3",
                            "2.11.4",
                            "2.11.5",
                            "2.11.6",
                            "2.11.7",
                            "2.11.8"),
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  )
)

lazy val macros = (project in file("macros"))
  .settings(
    common,
    publishArtifact := false,
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      compilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.patch)
    )
  )

lazy val root = (project in file("core"))
  .settings(
    common,
    name := "firebase4s",
    publishTo := Some(
      if (isSnapshot.value) Opts.resolver.sonatypeSnapshots else Opts.resolver.sonatypeStaging
    ),
    homepage := Some(url("https://github.com/firebase4s/firebase4s")),
    scmInfo := Some(
      ScmInfo(url("https://github.com/firebase4s/firebase4s"), "git@github.com:firebase4s/firebase4s.git")
    ),
    developers := List(Developer("username", "Aaron Rice", "adrice727@gmail.com", url("https://github.com/adrice727"))),
    licenses += ("MIT", url("https://opensource.org/licenses/MIT")),
    sonatypeProfileName := "com.github.firebase4s",
    publishMavenStyle := true,
    libraryDependencies ++= Seq(
      "org.slf4j" % "slf4j-api" % "1.7.25",
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "com.google.api-client" % "google-api-client" % googleApiVersion exclude ("com.google.guava", "guava-jdk5"),
      "com.google.guava" % "guava" % guavaVersion,
      "com.google.firebase" % "firebase-admin" % firebaseVersion exclude ("com.google.guava", "guava"),
      compilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.patch)
    )
  )

lazy val test = (project in file("test"))
  .settings(
    common,
    publishArtifact := false,
    libraryDependencies ++= Seq(
      "org.scalactic" %% "scalactic" % "3.0.4",
      "org.scalatest" %% "scalatest" % "3.0.4" % "test"
    ),
    resolvers ++= Seq(
      Resolver.sonatypeRepo("releases"),
      Resolver.sonatypeRepo("snapshots")
    )
  ) dependsOn root

//lazy val root = (project in file("."))
//  .settings(
//    common,
//    publishArtifact := false,
//    run := run in Compile in core
//  ) aggregate(core)
