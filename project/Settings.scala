object Settings {
  lazy val gitHubId: String = try {
        val hubConfigFilename = System.getProperty("user.home") + "/.config/hub"
        scala.io.Source.fromFile(new java.io.File(hubConfigFilename))
          .getLines
          .filter(_.contains("user:"))
          .toList
          .headOption
          .map(_.split(" ").slice(2, 3).mkString)
          .mkString
      } catch {
        case ex: Exception =>
          System.err.println(ex.getMessage)
          sys.exit(-1)
      }
}
