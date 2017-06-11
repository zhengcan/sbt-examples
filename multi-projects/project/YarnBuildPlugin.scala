package sbt.plugins

import sbt._
import Keys._

object YarnBuildPlugin extends AutoPlugin {
  override def requires = plugins.JvmPlugin

  override def trigger = allRequirements

  lazy val yarnBuild = taskKey[Unit]("Execute yarn build.")

  override lazy val projectSettings = Seq(
    yarnBuild := {
      val packageJson = baseDirectory.value / "package.json"
      packageJson match {
        case _ if !packageJson.exists() => println("No " + packageJson.absolutePath)
        case _ => {
          import sys.process._
          sys.props.getOrElse("os.name", "") match {
            case osName if osName contains "Windows" => {
              Process("cd", baseDirectory.value).!
              Process(Seq("cmd", "/c", "yarn", "run", "build:prod"), baseDirectory.value).!
            }
            case _ => {
              Process("pwd", baseDirectory.value).!
              Process(Seq("yarn", "run", "build:prod"), baseDirectory.value).!
            }
          }
        }
      }
    },

    compile in Compile := ((compile in Compile) dependsOn yarnBuild).value
  )

}