// See https://github.com/mpeltonen/sbt-idea
addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")

// See https://github.com/typesafehub/sbteclipse
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "5.1.0")

// See https://github.com/orrsella/sbt-sublime
addSbtPlugin("com.orrsella" % "sbt-sublime" % "1.1.1")

// See https://github.com/jrudolph/sbt-dependency-graph
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")

// See https://github.com/rtimush/sbt-updates
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.1")

// See http://www.scalatest.org/install; does not work with Scala 2.12
//resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"
//addSbtPlugin("com.artima.supersafe" % "sbtplugin" % "1.1.2")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.5")