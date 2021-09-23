val Http4sVersion = "0.23.1"
val CirceVersion = "0.14.1"
val MunitVersion = "0.7.27"
val LogbackVersion = "1.2.5"
val MunitCatsEffectVersion = "1.0.5"

// Alpine base image with JRE 11 is chosen to be more lightweight.
// `AshScriptPlugin` is enabled because Alpine does not have `bash` which would affect
// starting a container as `docker run --rm -p 8080:8080 <image-id>`.
// However, Alpine has `sh`, so it's possible to login into the running container
// as `docker exec -it <container-id> /bin/sh`.

lazy val root = (project in file("."))
  .settings(
    organization := "org.itn",
    name := "simple-server",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.6",
    libraryDependencies ++= Seq(
      "org.http4s"      %% "http4s-ember-server" % Http4sVersion,
      "org.http4s"      %% "http4s-ember-client" % Http4sVersion,
      "org.http4s"      %% "http4s-circe"        % Http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
      "io.circe"        %% "circe-generic"       % CirceVersion,
      "org.scalameta"   %% "munit"               % MunitVersion           % Test,
      "org.typelevel"   %% "munit-cats-effect-3" % MunitCatsEffectVersion % Test,
      "ch.qos.logback"  %  "logback-classic"     % LogbackVersion,
      "org.scalameta"   %% "svm-subs"            % "20.2.0"
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.13.0" cross CrossVersion.full),
    addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1"),
    testFrameworks += new TestFramework("munit.Framework")
  )
  .enablePlugins(JavaAppPackaging, DockerPlugin, AshScriptPlugin)

Compile / mainClass := Some("org.itn.simpleserver.Main")

dockerBaseImage := "adoptopenjdk/openjdk11:jre-11.0.10_9-alpine"
dockerExposedPorts ++= Seq(8080)
