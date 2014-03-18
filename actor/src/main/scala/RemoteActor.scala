package remote

import akka.actor._
import akka.event.Logging
import akka.routing.RoundRobinRouter

import com.typesafe.config.ConfigFactory

class RemoteActor extends Actor {

    val log = Logging(context.system, this)

    def receive = {
        case (x: Int, y: Int, z: Int) =>
            Thread.sleep(1000)
            sender ! (x + y + z)
    }

}

object RemoteApp extends App {

    val system = ActorSystem("RemoteApp", ConfigFactory.load.getConfig("remoteapp"))
    val actor = system.actorOf(Props[RemoteActor], "remoteApp")

}
