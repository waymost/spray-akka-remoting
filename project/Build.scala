import sbt._
import Keys._

object Build extends Build {

	import BuildSettings._
	import Dependencies._

	override lazy val settings = super.settings :+ {
		shellPrompt := { s => Project.extract(s).currentProject.id match {
			case "root" => "> "
			case _ => Project.extract(s).currentProject.id + " > "
		}}
	}

	lazy val root = Project("root",file("."))
		.aggregate(actors,web)
		.settings(basicSettings: _*)

	lazy val web = Project("web",file("web"))
		.settings(basicSettings: _*)
		.settings(testSettings: _*)
		.settings(codeFormatSettings: _*)
		.settings(revolverSettings: _*)
		.settings(libraryDependencies ++= 
			compile(sprayCan, sprayRouting, sprayJson, actor, remote) ++ 
			runtime(akkaSlf4j, logback) ++ 
			test(sprayTest, scalatest)
		)

	lazy val actors = Project("actor",file("actor"))
		.settings(basicSettings: _*)
		.settings(codeFormatSettings: _*)
		.settings(revolverSettings: _*)
		.settings(libraryDependencies ++=
			compile(actor, remote) ++ 
			runtime(akkaSlf4j, logback) ++ 
			test(akkaTest, scalatest)
		)

}
