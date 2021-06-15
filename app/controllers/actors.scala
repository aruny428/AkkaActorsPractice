package controllers

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import scala.concurrent.duration._


class actors (number : Int)extends Actor {
  var count = 0
  def counter {count = count +1}
    def receive = {
      case x : Int => println(s"got Integer : $x  from customer no. : $number : iteration no. : $count")
        counter
        if(count>10){
          context.stop(self)
          System.exit(0)
        }
      case y: String => println(s"got string $y from customer no. : $number")
      case _ => println("got nothing meaningful ")
    }
}
object main1 extends App {
  val system = ActorSystem("HelloSystem")
  val helloActor = system.actorOf(Props(new actors(4)), name = "helloactor")

  /*helloActor ! 22
  helloActor ! "dasd"
  helloActor ! true*/

  import system.dispatcher

val schedulerInstance =  system.scheduler.schedule(1.millis,2.seconds,helloActor,6)

  //schedulerInstance.cancel()

  //system.stop(helloActor)


}


