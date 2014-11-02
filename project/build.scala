import sbt._
import Keys._
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import com.mojolly.scalate.ScalatePlugin._
import ScalateKeys._

object TwitterGlobeBuild extends Build {
  val Organization = "org.ledyba"
  val Name = "Twitter Globe"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.11.1"
  val ScalatraVersion = "2.3.1-SNAPSHOT"

  lazy val project = Project (
    "twitter-globe",
    file("."),
    settings = Defaults.defaultSettings ++ ScalatraPlugin.scalatraWithJRebel ++ scalateSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      resolvers += ("LocalRepo" at file(Path.userHome.absolutePath + "/.ivy2/local").getAbsolutePath),
      libraryDependencies ++= Seq(
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
        "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
        "ch.qos.logback"              %  "logback-classic"     % "1.1.1"          % "runtime",
        "org.eclipse.jetty"           %  "jetty-webapp"        % "9.1.5.v20140505"     % "container",
        "org.eclipse.jetty"           %  "jetty-plus"          % "9.1.5.v20140505"     % "container;provided",
        "org.eclipse.jetty.websocket" %  "websocket-server"    % "9.1.5.v20140505"     % "container;provided",
        "org.twitter4j" % "twitter4j-async" % "4.0.2",
        "org.twitter4j" % "twitter4j-stream" % "4.0.2",
        "org.twitter4j" % "twitter4j-core" % "4.0.2",
        "org.scalatra" %% "scalatra-atmosphere" % ScalatraVersion,
        "org.scalatra" %% "scalatra-json" % ScalatraVersion,
        "org.json4s"   %% "json4s-jackson" % "3.2.10"
      ),
      scalateTemplateConfig in Compile <<= (sourceDirectory in Compile){ base =>
        Seq(
          TemplateConfig(
            base / "webapp" / "WEB-INF" / "templates",
            Seq.empty,  /* default imports should be added here */
            Seq(
              Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
            ),  /* add extra bindings here */
            Some("templates")
          )
        )
      }
    )
  )
}
