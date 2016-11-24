// Enable protocol buffer builds.
import sbtprotobuf.{ProtobufPlugin => PB}
PB.protobufSettings

// Project definitions for vessel classification pipeline and modelling.
scalafmtConfig in ThisBuild := Some(file(".scalafmt"))

val tfExampleProtoFiles =
  TaskKey[Seq[File]]("tf-example-protos", "Set of protos defining TF example.")

lazy val commonSettings = Seq(
  organization := "org.skytruth",
  version := "0.0.1",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq("-optimize"),
  resolvers ++= Seq(
    "Apache commons" at "https://repository.apache.org/snapshots"
  ),
  // Main project dependencies.
  libraryDependencies ++= Seq(
    "com.opencsv" % "opencsv" % "3.7",
    "com.spotify" % "scio-core_2.11" % "0.2.6",
    "com.jsuereth" %% "scala-arm" % "1.4",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.4.0",
    "io.github.karols" %% "units" % "0.2.1",
    "joda-time" % "joda-time" % "2.9.4",
    "org.apache.commons" % "commons-math3" % "3.4",
    "org.json4s" %% "json4s-native" % "3.3.0",
    "org.jgrapht" % "jgrapht-core" % "1.0.0",
    "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % "2.8.3",
    "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.8.3",
    "com.chuusai" %% "shapeless" % "2.3.2"
  ),
  // Test dependencies.
  libraryDependencies ++= Seq(
    "ch.qos.logback" % "logback-classic" % "1.1.7",
    "com.spotify" % "scio-test_2.11" % "0.2.6" % "test",
    "org.scalactic" %% "scalactic" % "3.0.0" % "test",
    "org.scalatest" %% "scalatest" % "3.0.0" % "test"
  )
)

// TODO(alexwilson): Download these files from github TF repo rather than having our own copy.
lazy val tfExampleProtos = project
  .in(file("tf-example-protos"))
  .settings(commonSettings: _*)
  .settings(PB.protobufSettings: _*)
  .settings(
    Seq(
      includePaths in PB.protobufConfig += (sourceDirectory in PB.protobufConfig).value
    ))

lazy val common = project.in(file("common")).settings(commonSettings: _*)

// Pipeline for annotating AIS messages with other attributes (such as when
// fishing, port visits, transhipments or AIS gaps occur).
lazy val aisAnnotator =
  project.in(file("ais-annotator")).settings(commonSettings: _*).dependsOn(common)

lazy val anchorages =
  project.in(file("anchorages")).settings(commonSettings: _*).dependsOn(common)

// The dataflow feature generation pipeline.
lazy val features =
  project
    .in(file("feature-pipeline"))
    .settings(commonSettings: _*)
    // TODO(alexwilson): *shame*: tfExampleProtos needs to come first as a dependency because
    // there are currently >1 versions of the proto library in the deployment environment. our
    // code needs to resolve the version in tfExampleProtos as otherwise at runtime on cloud
    // dataflow the other versions are missing some required methods.
    .dependsOn(tfExampleProtos, anchorages, common % "compile->compile;test->test")

// An aggregation of all projects.
lazy val root = (project in file(".")).aggregate(common, anchorages, aisAnnotator, features)
