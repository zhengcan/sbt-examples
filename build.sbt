lazy val multiProjects = ProjectRef(file("multi-projects"), "multi-projects")

lazy val resolverForSnapshot = ProjectRef(file("resolver-for-snapshot"), "resolver-for-snapshot")

lazy val root = (project in file("."))
  .dependsOn(multiProjects, resolverForSnapshot)

