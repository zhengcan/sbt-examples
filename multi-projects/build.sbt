
lazy val myTask = taskKey[Unit]("Task defined in build.sbt")

val commonSettings = Seq(
  myTask := {
    println("myTask for " + project.id + " - ")
  },
  compile in Compile := ((compile in Compile) dependsOn myTask).value
)

inThisBuild(List(
  organization := "org.example",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.12.2",
  crossPaths := false,
  autoScalaLibrary := false
))

lazy val subProject1 = Project("sub-project-1", file("sub-project-1"))
  .settings(
    commonSettings: _*
  )

lazy val subProject2 = Project("sub-project-2", file("sub-project-2"))
  .settings(
    commonSettings: _*
  )
  .dependsOn(subProject1)
  .aggregate(subProject1)

lazy val parent = Project("multi-projects", file("."))
  .settings(
    packageBin := { file("") },
    packageDoc := { file("") },
    packageSrc := { file("") },
    sbt.Keys.`package` := { file("") }
  )
  .dependsOn(subProject1, subProject2)
  .aggregate(subProject1, subProject2)

