package org.skytruth.common

import com.typesafe.scalalogging.LazyLogging
import com.spotify.scio._
import org.joda.time.{DateTime, DateTimeZone}

import resource._

// TODO(alexwilson): This config is too hard-coded to our current setup. Move
// out to config files for greater flexibility. Note there is an equivalent to
// this in gcp_config.py which should remain in-sync.
object GcpConfig extends LazyLogging {
  import Implicits._

  private def projectId = "world-fishing-827"

  // TODO(alexwilson): No locally-generated date for prod. Needs to be sourced
  // from outside so all prod stages share the same path.
  def makeConfig(environment: String, jobId: String) = {
    val now = new DateTime(DateTimeZone.UTC)
    val rootPath = environment match {
      case "prod" => {
        s"gs://world-fishing-827/data-production/classification/$jobId"
      }
      case "dev" => {
        sys.env.get("USER") match {
          case Some(user) =>
            s"gs://world-fishing-827-dev-ttl30d/data-production/classification/$user/$jobId"
          case _ => logger.fatal("USER environment variable cannot be empty for dev runs.")
        }
      }
      case _ => logger.fatal(s"Invalid environment: $environment.")
    }

    GcpConfig(now, projectId, rootPath)
  }
}

case class GcpConfig(startTime: DateTime, projectId: String, private val rootPath: String) {
  def dataflowStagingPath = s"$rootPath/pipeline/staging"
  def pipelineOutputPath = s"$rootPath/pipeline/output"
}

object ScioContextResource {
  implicit def scioContextResource[A <: ScioContext] = new Resource[A] {
    override def close(r: A) = r.close()
  }
}