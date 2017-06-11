
version := "1.0-SNAPSHOT"

resolvers := {
  if (isSnapshot.value) {
    resolvers.value :+ ("Akka Snapshot Repo" at "http://repo.akka.io/snapshots/")
  } else {
    resolvers.value
  }
}

