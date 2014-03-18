package example

import scala.concurrent.duration._

import org.scalatest.FreeSpec
import org.scalatest.matchers.ShouldMatchers

import spray.http._
import HttpMethods._
import MediaTypes._
import HttpCharsets._
import StatusCodes._
import HttpHeaders._
import spray.httpx.SprayJsonSupport._
import spray.httpx.marshalling.marshalUnsafe
import spray.json._
import DefaultJsonProtocol._
import spray.routing.{ MethodRejection, RequestContext, Directives }
import spray.testkit.ScalatestRouteTest

class SiteServiceSpec extends FreeSpec with ShouldMatchers with ScalatestRouteTest with SiteService {

    implicit val routeTestTimeout = RouteTestTimeout(10.second)

    def actorRefFactory = system

    "this should work" - {

        "request that spawns remote actor" in {

            Put("/") ~> myRoute ~> check {
                responseAs[String] should equal("6")
            }

        }

    }

}
