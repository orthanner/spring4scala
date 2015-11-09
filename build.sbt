name := "spring4scala"

val springVersion = "4.2.2.RELEASE"

lazy val commonSettings = Seq(
  version := "1.0",
  scalaVersion := "2.11.7",
  resolvers += "Spring IO" at "http://repo.spring.io/release",
  libraryDependencies ++= Seq(
    "org.scala-lang.modules" %% "scala-async" % "0.9.2",
  	"commons-codec" % "commons-codec" % "1.9",
  	"org.springframework" % "spring-context" % springVersion
  ),
  scalacOptions ++= Seq("-deprecation", "-feature", "-language:implicitConversions")
)


lazy val hello = taskKey[Unit]("An example task")

hello := { println("Hello!") }

lazy val core = (project in file("spring4scala-core")).
  settings(commonSettings: _*)

lazy val jca = (project in file("spring4scala-jca")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies ++= Seq(
      "javax" % "javaee-api" % "7.0",
      "org.springframework" % "spring-tx" % springVersion
    )
  )


lazy val jdbc = (project in file("spring4scala-jdbc")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies += "org.springframework" % "spring-jdbc" % springVersion
  )

lazy val jms = (project in file("spring4scala-jms")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies ++= Seq(
      "javax" % "javaee-api" % "7.0",
      "org.springframework" % "spring-jms" % springVersion
    )
  )

lazy val transaction = (project in file("spring4scala-tx")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies += "org.springframework" % "spring-tx" % springVersion
  )
//packAutoSettings
