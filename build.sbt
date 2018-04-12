name := "aws-ses-scala"
organization := "jp.co.bizreach"
scalaVersion := "2.12.5"
crossScalaVersions := Seq("2.11.12", scalaVersion.value)

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-java-sdk-ses" % "1.11.311",
  "com.sun.mail"  % "javax.mail"       % "1.5.2",
  "org.scalatest" %% "scalatest"       % "3.0.5" % "test"
)

scalacOptions ++= (
  "-feature" ::
  "-deprecation" ::
  "-unchecked" ::
  "-Xlint" ::
  "-language:existentials" ::
  "-language:higherKinds" ::
  "-language:implicitConversions" ::
  Nil
)

javacOptions in compile ++= Seq(
  "-encoding", "UTF-8",
  "-target", "1.8"
)


//
// For publish artifact
//
pomExtra := (
  <scm>
    <url>https://github.com/bizreach/aws-ses-scala</url>
    <connection>scm:git:https://github.com/bizreach/aws-ses-scala.git</connection>
  </scm>
  <developers>
    <developer>
      <id>tanacasino</id>
      <name>Tomofumi Tanaka</name>
      <email>tomofumi.tanaka_at_bizreach.co.jp</email>
      <timezone>+9</timezone>
    </developer>
  </developers>
)

pomIncludeRepository := { _ => false }
publishMavenStyle := true
publishTo := sonatypePublishTo.value
homepage := Some(url(s"https://github.com/bizreach/aws-ses-scala"))
licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))

sonatypeProfileName := organization.value
releasePublishArtifactsAction := PgpKeys.publishSigned.value
releaseTagName := (version in ThisBuild).value
releaseCrossBuild := true

import ReleaseTransformations._
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  publishArtifacts,
  releaseStepCommand("sonatypeRelease"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)
