import sbt._
import Keys._

object BuildSettings {

	lazy val basicSettings = seq(
		organization := "example",
		name := "test",
		version := "0.0",
		resolvers ++= Dependencies.resolutionRepos,
		scalaVersion := "2.10.3",
		scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")
	)

	lazy val testSettings = seq(
								(testOptions in Test) += Tests.Argument("-oS")
							)

	import scalariform.formatter.preferences._
	import com.typesafe.sbt.SbtScalariform.ScalariformKeys
	def formatPreferences = FormattingPreferences()
		.setPreference(AlignParameters, true)
		.setPreference(AlignSingleLineCaseStatements, true)
		.setPreference(DoubleIndentClassDeclaration, false)
		.setPreference(IndentSpaces, 4)
		.setPreference(IndentWithTabs, false)
		.setPreference(PreserveDanglingCloseParenthesis, true)

	import com.typesafe.sbt.SbtScalariform.scalariformSettings
	lazy val codeFormatSettings = scalariformSettings ++ Seq(
		ScalariformKeys.preferences in Compile := formatPreferences,
		ScalariformKeys.preferences in Test    := formatPreferences
	)

	import spray.revolver.RevolverPlugin._
	lazy val revolverSettings = seq(Revolver.settings: _*)
}
