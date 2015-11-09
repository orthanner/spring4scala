name := "spring4scala"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "Spring IO" at "http://repo.spring.io/release"

val springVersion = "4.2.2.RELEASE"

lazy val hello = taskKey[Unit]("An example task")

hello := { println("Hello!") }

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-async" % "0.9.2",
	"commons-codec" % "commons-codec" % "1.9",
	"org.springframework" % "spring-context" % springVersion,
  "org.springframework" % "spring-jdbc" % springVersion % "optional",
  "org.springframework" % "spring-jms" % springVersion % "optional",
	"org.springframework" % "spring-tx" % springVersion % "optional",
  "javax" % "javaee-api" % "7.0"
)

//packAutoSettings
scalacOptions ++= Seq("-deprecation", "-feature", "-language:implicitConversions")
