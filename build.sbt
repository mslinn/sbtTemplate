organization := "com.micronautics"

name := "changeMe"

crossPaths := false

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.10.2"

scalacOptions ++= Seq("-deprecation", "-encoding", "UTF-8", "-feature", "-target:jvm-1.7", "-unchecked",
    "-Ywarn-adapted-args", "-Ywarn-value-discard", "-Xlint")

javacOptions ++= Seq("-Xlint:deprecation", "-Xlint:unchecked", "-source", "1.7", "-target", "1.7", "-g:vars")

scalacOptions in (Compile, doc) <++= baseDirectory.map {
  (bd: File) => Seq[String](
     "-sourcepath", bd.getAbsolutePath,
     "-doc-source-url", "https://github.com/mslinn/changeMe/tree/master€{FILE_PATH}.scala"
  )
}

resolvers ++= Seq(
  "Typesafe Releases"   at "http://repo.typesafe.com/typesafe/releases"
)

libraryDependencies ++= Seq(
  "org.scalatest"           %% "scalatest"           % "2.0.M6-SNAP16" % "test" withSources(),
  "com.github.nscala-time"  %% "nscala-time"         % "0.2.0" withSources()
)

publishTo <<= (version) { version: String =>
   val scalasbt = "https://bitbucket.org/mslinn/maven-repo/raw/master/"
   val (name, url) = if (version.contains("-SNAPSHOT"))
                       ("snapshots", scalasbt+"snapshots")
                     else
                       ("releases", scalasbt+"releases")
   Some(Resolver.url(name, new URL(url)))
}

publishMavenStyle := true

//logLevel := Level.Error

// define the statements initially evaluated when entering 'console', 'console-quick', or 'console-project'
initialCommands := """
  """

// Only show warnings and errors on the screen for compilations.
// This applies to both test:compile and compile and is Info by default
//logLevel in compile := Level.Warn
