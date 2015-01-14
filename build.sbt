name := "aws-ses-scala"

organization := "jp.co.bizreach"

version := "0.0.1"

scalaVersion := "2.11.4"

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-java-sdk-ses" % "1.9.14"
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
  "-target", "1.7"
)
