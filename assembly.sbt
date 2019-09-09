// Settings for sbt assembly

test in assembly := {}

mainClass in assembly := Some("com.micronautics.cad.Cad")

assemblyMergeStrategy in assembly := {
 case PathList("META-INF", xs @ _*) => MergeStrategy.discard
 case x => MergeStrategy.first
}

//fullClasspath in assembly := (fullClasspath in Compile).value

//exportJars := true
