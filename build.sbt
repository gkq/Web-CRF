name := "web-crf"

version := "0.1.0"

scalaVersion := "2.10.4"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.4.0"

libraryDependencies += "org.apache.spark" % "spark-mllib_2.10" % "1.4.0"

libraryDependencies  ++= Seq(
			"org.scalanlp" %% "breeze" % "0.12",
			"org.scalanlp" %% "breeze-natives" % "0.12",
			"org.scalanlp" %% "breeze-viz" % "0.12")

resolvers += Resolver.sonatypeRepo("public")
