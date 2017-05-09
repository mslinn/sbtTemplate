// sbt-site settings
enablePlugins(SiteScaladocPlugin)
siteSourceDirectory := target.value / "api"
publishSite

// sbt-ghpages settings
enablePlugins(GhpagesPlugin)
// TODO replace yourGithubId with your GitHub id
git.remoteRepo := s"git@github.com:yourGithubId/${ name.value }.git"
