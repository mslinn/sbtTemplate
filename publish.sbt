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

import _root_.scala.sys.process._
import Settings._

// Uncomment and configure if publishing a library to bintray
//bintrayOrganization := Some("micronautics")
//bintrayRepository := "scala"
//bintrayPackage := name.value

// See http://www.scala-sbt.org/1.0/docs/Howto-Scaladoc.html
autoAPIMappings := true
apiURL := Some(url(s"https://$gitHubId.github.io/${ name.value }/latest/api"))

// sbt-site settings
enablePlugins(SiteScaladocPlugin)
siteSourceDirectory := target.value / "api"
publishSite

// sbt-ghpages settings
enablePlugins(GhpagesPlugin)
git.remoteRepo := s"git@github.com:$gitHubId/${ name.value }.git"


lazy val scaladoc =
  taskKey[Unit]("Rebuilds the Scaladoc and pushes the updated Scaladoc to GitHub pages without committing to the git repository")

scaladoc := {
  println("Creating Scaladoc")
  doc.in(Compile).value

  println("Uploading Scaladoc to GitHub Pages")
  ghpagesPushSite.value
}

/** Best practice is to comment your commits before invoking this task: `git commit -am "Your comment here"`.
  * This task does the following:
  * 1. `git pull` when it starts.
  * 2. Attempts to build Scaladoc, which fails if there is a compile error
  * 3. Ensures any uncommitted changes are committed before publishing, including new files; it provides the comment as "-".
  * 4. Git pushes all files
  * 5. Uploads new Scaladoc
  * 6. Publishes new version to Bintray */
lazy val commitAndDoc =
  taskKey[Unit]("Rebuilds the docs, commits the git repository, and publishes the updated Scaladoc without publishing a new version")

commitAndDoc := {
  try {
    println("Fetching latest updates for this git repo")
    "git pull".!!

    println("Creating Scaladoc")
    doc.in(Compile).value

    val changedFileNames = "git diff --name-only".!!.trim.replace("\n", ", ")
    if (changedFileNames.nonEmpty) {
      println(s"About to commit these changed files: $changedFileNames")
      "git add -A".!!
      "git commit -m -".!!
    }

    println("About to git push to origin")
    "git push origin HEAD".!!  // See https://stackoverflow.com/a/20922141/553865

    println("Uploading Scaladoc to GitHub Pages")
    ghpagesPushSite.value
  } catch {
    case e: Exception => println(e.getMessage)
  }
  ()
}

/** Publish a new version of this library to BinTray.
  * Be sure to update the version string in build.sbt before running this task. */
lazy val publishAndTag =
  taskKey[Unit]("Invokes commitAndPublish, then creates a git tag for the version string defined in build.sbt")

publishAndTag := {
  commitAndDoc.in(Compile).value
  publish.value
  println(s"Creating git tag for v${ version.value }")
  s"""git tag -a ${ version.value } -m ${ version.value }""".!!
  s"""git push origin --tags""".!!
  ()
}