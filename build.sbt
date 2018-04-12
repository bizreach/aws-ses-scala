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
