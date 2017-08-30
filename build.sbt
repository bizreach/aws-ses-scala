name := "aws-ses-scala"

organization := "jp.co.bizreach"

version := "0.0.2"

scalaVersion := "2.12.3"
crossScalaVersions := Seq("2.11.8", scalaVersion.value)

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-java-sdk-ses" % "1.11.184",
  "com.sun.mail"  % "javax.mail"       % "1.5.2",
  "org.scalatest" %% "scalatest"       % "3.0.4" % "test"
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

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <url>https://github.com/bizreach/aws-ses-scala</url>
    <licenses>
      <license>
        <name>The Apache Software License, Version 2.0</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
      </license>
    </licenses>
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
    </developers>)
