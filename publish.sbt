//
// For publish artifact
//
import ReleaseTransformations._

pomIncludeRepository := { _ => false }
publishMavenStyle := true
publishTo := sonatypePublishTo.value

homepage := Some(url(s"https://github.com/bizreach/aws-ses-scala"))
licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))
scmInfo := Some(
  ScmInfo(url("https://github.com/bizreach/aws-ses-scala"), "scm:git:https://github.com/bizreach/aws-ses-scala.git")
)
developers := List(
  Developer(id="tanacasino", name="Tomofumi Tanaka", email="tomofumi.tanaka_at_bizreach.co.jp", url=url("https://github.com/tanacasino"))
)

releasePublishArtifactsAction := PgpKeys.publishSigned.value
releaseTagName := (version in ThisBuild).value
releaseCrossBuild := true

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
