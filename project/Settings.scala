/* The person who associated a work with this deed has dedicated the work to the public domain by waiving all of his or
 * her rights to the work worldwide under copyright law, including all related and neighboring rights, to the extent allowed by law.
 *
 * You can copy, modify, distribute and perform the work, even for commercial purposes, all without asking permission.
 *
 * In no way are the patent or trademark rights of any person affected by CC0, nor are the rights that other persons may
 * have in the work or in how the work is used, such as publicity or privacy rights.
 * Unless expressly stated otherwise, the person who associated a work with this deed makes no warranties about the work,
 * and disclaims liability for all uses of the work, to the fullest extent permitted by applicable law.
 * When using or citing the work, you should not imply endorsement by the author or the affirmer.
 *
 * The full legal text is here: https://creativecommons.org/publicdomain/zero/1.0/legalcode */

import java.io.{BufferedReader, FileReader}

object Settings {
  import java.io.{BufferedWriter, File, FileWriter}

  def using[A <: AutoCloseable, B](resource: A)
                                  (block: A => B): B = {
    try block(resource) finally resource.close()
  }

  def readLines(file: File): String =
    using(new BufferedReader(new FileReader(file))) { _.readLine() }

  def justForTravis(home: String): Unit = {
    val configDir = new File(s"$home/.config/")
    configDir.mkdir()
    val hubFile = new File(configDir, "hub")
    using(new BufferedWriter(new FileWriter(hubFile))) { bw =>
      bw.write(
        """- user: travis
          |  oauth_token: 12345678901234567890
          |  protocol: https
          |""".stripMargin
      )
    }
    ()
  }

  lazy val gitHubId: String = try {
    val home = System.getProperty("user.home")
    if (home=="/home/travis") justForTravis(home)
    val hubConfigFile = new File(s"$home/.config/hub")
    if (hubConfigFile.exists)
      readLines(hubConfigFile)
        .split("\n")
        .find(_.contains("user:"))
        .map(_.split(" ").slice(2, 3).mkString)
        .mkString
    else
      "noGithubUserFound"
  } catch {
    case ex: java.io.IOException =>
      System.err.println(s"project/Settings.scala error: ${ ex.getMessage }; ${ ex }")
      sys.exit(-1)
  }
}
