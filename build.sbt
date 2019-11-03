name := "tiny"

version := "0.0.1"

scalaVersion := "2.12.6"

organization := "se.chimps.tiny"

libraryDependencies ++= Seq(
	"io.reactivex.rxjava3" % "rxjava" % "3.0.0-RC4",
	"org.scalatest" %% "scalatest" % "3.0.0" % "test"
)