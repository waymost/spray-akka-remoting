package example

import scala.util.{ Try, Success, Failure }

import akka.actor.Actor
import spray.http._
import HttpHeaders._
import StatusCodes._
import spray.httpx.SprayJsonSupport._
import spray.json._
import DefaultJsonProtocol._
import spray.routing._

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class SiteServiceActor extends Actor with SiteService {

    // the HttpService trait defines only one abstract member, which
    // connects the services environment to the enclosing actor or test
    def actorRefFactory = context

    // this actor only runs our route, but you could add
    // other things here, like request stream processing
    // or timeout handling
    def receive = runRoute(myRoute)
}

trait SiteService extends HttpService {

	// format: OFF
	val myRoute = 
		pathEndOrSingleSlash {
			put {
				ctx => Application.run(1,2,3) match {
					case Success(x) => complete(x.toString)
					case Failure(ex) => complete(ex)
				}
			}
		}

}
