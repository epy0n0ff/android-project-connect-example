import sbt._

object Dependencies {
  val scalaTest = "org.scalatest" % "scalatest_2.11" % "2.2.6" % "test"

  val androidBuilderModel = "com.android.tools.build" % "builder-model" % "2.1.2"
  val gradleTooling = "org.gradle" % "gradle-tooling-api" % "2.14"

  val deps = Seq(scalaTest, androidBuilderModel, gradleTooling)
}