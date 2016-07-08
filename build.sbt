import Dependencies._

resolvers += Resolver.jcenterRepo

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xlint",
  "-Ywarn-unused",
  "-Ywarn-unused-import"
)

lazy val `android-project-connect-example` = (project in file("."))
  .settings(
    name := "android-project-connect-example",
    version := "0.0.1",
    scalaVersion := "2.11.8",
    libraryDependencies ++= deps
  )
