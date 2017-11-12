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
