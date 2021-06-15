package controllers

  import akka.actor._
import scala.concurrent.duration._

case object PingMessage
case object PongMessage
case object StartMessage
case object StopMessage

class Ping(pong: ActorRef) extends Actor {
  var count = 0
  def incrementAndPrint { count += 1; println(s"ping $count") }
  def receive = {
    case StartMessage =>
      incrementAndPrint
      pong ! PingMessage
    case PongMessage =>
      incrementAndPrint
      if (count > 10) {
        sender ! StopMessage
        println("ping stopped")
        context.stop(self)
        //System.exit(0)
      } else {
        sender ! PingMessage
      }
    case _ => println("Ping got something unexpected.")
  }
}

class Pong extends Actor {
  def receive = {
    case PingMessage =>
      println(" pong")
      sender ! PongMessage
    case StopMessage =>
      println("pong stopped")
      //context.stop(self)
      System.exit(0)
    case _ => println("Pong got something unexpected.")
  }
}

object PingPongTest extends App {

  val system = ActorSystem("PingPongSystem")
  val pong = system.actorOf(Props[Pong], name = "pong")
  val ping = system.actorOf(Props(new Ping(pong)), name = "ping")

  ping ! StartMessage
  /*import system.dispatcher
  system.scheduler.scheduleOnce(5.seconds,ping,StartMessage)*/

 // System.exit(0)

}
