package example

import akka.actor._
import akka.event.Logging
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{ Try, Success, Failure }

import com.typesafe.config.ConfigFactory
import spray.util._

object Application {

    def run(x: Int, y: Int, z: Int): Try[Int] = {

        for {
            answer <- doIt(x, y, z)
        } yield answer

    }

    protected def doIt(x: Int, y: Int, z: Int) = Try[Int] {

        val system = ActorSystem("RemoteApplication", ConfigFactory.load.getConfig("remoteapp"))
        val remotePath = "akka.tcp://RemoteApp@127.0.0.1:47822/user/remoteApp"
        val actor = system.actorOf(Props(classOf[LocalActor], remotePath), "localActor")

        implicit val ec = system.dispatcher
        implicit val timeout = Timeout(2.seconds)

        val message = (x, y, z)

        Thread.sleep(1000)
        val future = ask(actor, message).mapTo[Int]
        future onComplete {
            case Success(res) => println("onSuccess => " + res)
            case Failure(ex)  => throw ex
        }

        val result = Await.result(future, timeout.duration).asInstanceOf[Int]

        result

    }

}

class LocalActor(path: String) extends Actor {

    context.setReceiveTimeout(4.seconds)
    sendIdentifyRequest()

    var origin: ActorRef = _

    val log = Logging(context.system, this)

    def sendIdentifyRequest(): Unit = context.actorSelection(path) ! Identify(path)

    def receive = {
        case ActorIdentity(`path`, Some(actor)) =>
            context.setReceiveTimeout(Duration.Undefined)
            context.become(active(actor))
        case ActorIdentity(`path`, None) => println(s"Remote actor not availible: $path")
        case ReceiveTimeout              => sendIdentifyRequest()
        case _                           => println("Not ready yet")
    }

    def active(actor: ActorRef): Actor.Receive = {
        case req: (x, y, z) =>
            log.info("message handled")
            origin = sender
            log.info(origin.toString)
            actor ! req
            log.info("message sent to " + actor)
        case result: Int =>
            log.info("results received")
            log.info(origin.toString)
            origin ! result
    }

}
