lazy val baseSettings: Seq[Setting[_]] = Seq(
  scalaVersion       := "2.12.8",
  scalacOptions     ++= Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-language:higherKinds",
    "-language:implicitConversions", "-language:existentials",
    "-unchecked",
    "-Yno-adapted-args",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-Ypartial-unification",
    "-Xfuture"
  ),
  resolvers += Resolver.sonatypeRepo("releases"),
  addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.9")
)

lazy val march2019 = project.in(file("."))
  .settings(moduleName := "march2019")
  .settings(baseSettings: _*)
  .aggregate(core, slides)
  .dependsOn(core, slides)

lazy val core = project
  .settings(moduleName := "march2019-core")
  .settings(libraryDependencies += "org.typelevel" %% "cats-effect" % "1.2.0")
  .settings(baseSettings: _*)


lazy val slides = project
  .settings(moduleName := "march2019-slides")
  .settings(baseSettings: _*)
  .settings(
    tutSourceDirectory := baseDirectory.value / "tut",
    tutTargetDirectory := baseDirectory.value / "../docs",
    watchSources ++= (tutSourceDirectory.value ** "*.html").get
  ).dependsOn(core)
  .enablePlugins(TutPlugin)
