import sbt._

object Dependencies {

	// additional repos for dependency resolution
	val resolutionRepos = Seq(
		Resolver.sonatypeRepo("releases"),
		"Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
		"spray repo" at "http://repo.spray.io/"
	)

	// For adding a dependency to the respective configuration
	def compile(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "compile")
	def runtime(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "runtime")
	def test(deps: ModuleID*): Seq[ModuleID] =    deps map (_ % "test")

	val sprayVersion = "1.2.0"
	val akkaVersion = "2.2.4"

	val sprayCan       = "io.spray"                %  "spray-can"       % sprayVersion
    val sprayRouting   = "io.spray"                %  "spray-routing"   % sprayVersion
    val sprayTest      = "io.spray"                %  "spray-testkit"   % sprayVersion
    val sprayJson      = "io.spray"                %% "spray-json"      % "1.2.5"

	val scalatest      = "org.scalatest"           %% "scalatest"       % "1.9.1"

	val actor          = "com.typesafe.akka"       %% "akka-actor"      % akkaVersion
	val remote         = "com.typesafe.akka"       %% "akka-remote"     % akkaVersion
	val akkaSlf4j      = "com.typesafe.akka"       %% "akka-slf4j"      % akkaVersion
	val akkaTest       = "com.typesafe.akka"       %% "akka-testkit"    % akkaVersion
	val logback        = "ch.qos.logback"          %  "logback-classic" % "1.0.13"

}
