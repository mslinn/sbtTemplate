// See http://www.scala-sbt.org/1.0/docs/Howto-Scaladoc.html
autoAPIMappings := true
// TODO replace yourGithubId with your GitHub id
apiURL := Some(url(s"https://yourGithubId.github.io/${ name.value }/latest/api"))

//bintrayOrganization := Some("micronautics")
//bintrayRepository := "scala"
//bintrayPackage := name.value

// sbt-site settings
enablePlugins(SiteScaladocPlugin)
siteSourceDirectory := target.value / "api"
publishSite

// sbt-ghpages settings
enablePlugins(GhpagesPlugin)
// TODO replace yourGithubId with your GitHub id
git.remoteRepo := s"git@github.com:yourGithubId/${ name.value }.git"
