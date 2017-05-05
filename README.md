# Scala Project Template #

[![Build Status](https://travis-ci.org/mslinn/sbtTemplate.svg?branch=master)](https://travis-ci.org/mslinn/sbtTemplate)
[![GitHub version](https://badge.fury.io/gh/mslinn%2FsbtTemplate.svg)](https://badge.fury.io/gh/mslinn%2FsbtTemplate)

This is a handy starting point for Scala/Java console apps built with SBT.
Projects are built with Scala 2.12, which requires Java 8+.
The `scala_2_11` branch builds projects for Scala 2.11.

For more information about this project, and SBT, see the [SBT Project Setup lecture](https://scalacourses.com/student/showLecture/135) on [ScalaCourses.com](https://www.ScalaCourses.com).

## sbtTemplate Bash command

Copy this to a directory on the path (like `/usr/local/bin/`), and call it `sbtTemplate`:

```
#!/bin/bash

# Clones sbtTemplate and starts a new SBT project
# Optional argument specifies name of directory to place the new project into

DIR=sbtTemplate
if [ "$1" ]; then DIR="$1"; fi
git clone https://github.com/mslinn/sbtTemplate.git "$DIR"
cd "$DIR"
rm -rf .git
git init
echo "Remember to edit README.md and build.sbt ASAP"
```

Make the bash script executable:

    $ chmod a+x /usr/local/bin/sbtTemplate

## Using GitHub? Try Hub!
With `hub` and `sbtTemplate` you can create a new SBT project and a matching GitHub project with only two commands. The setup documented below will supply your GitHub username and password, and will only prompt your for your 2-factor-authentication (2FA) token each time you run it if you set up 2FA.

### Install Hub
Install Hub on Mac OS:

    $ brew install hub

Install Hub on Linux:

    $ sudo -H pip install hub

Put your GitHub login credentials in `~/.bash_profile` or `~/.profile`.
Also alias `hub` as `git` (`hub` also executes `git` commands):

    export GITHUB_USER=yourGithubUserName
    export GITHUB_PASSWORD=yourPassword
    alias git=hub

Reload `~/.bash_profile`

    $ source `~/.bash_profile`

... or reload `~/.profile`

    $ source `~/.profile`

### Using sbtTemplate with Hub
Create a new SBT project and create a new GitHub project, which `hub` automatically adds as a `git` `remote`:

    $ sbtTemplate my-new-project
    $ git create -d "Project description"
    two-factor authentication code: 881078
    Updating origin
    created repository: mslinn/html-form-scala

Now check in the new project:

    $ git add -A && git commit -m "Initial checkin" && git push -u origin master
