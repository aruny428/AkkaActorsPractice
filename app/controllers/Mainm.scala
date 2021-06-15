package controllers

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


class HelloActor extends Actor {
  def receive = {
    case "hello" => println("hello back at you")
    case  x: Int    => println(s"got  : $x")
    case x => println(s"what is $x ??")

  }
}

object Main extends App {

  val system = ActorSystem("HelloSystem")
  // default Actor constructor
  val helloActor = system.actorOf(Props[HelloActor], name = "helloactor")
  helloActor ! "hello"
  helloActor ! 34
  helloActor ! true




  def blockingFunction(arg: Int): Int = {
    Thread.sleep(10000)
    arg + 42
  }

  println(blockingFunction(3))
  val theMeaningOfLife = 42
  println(theMeaningOfLife)

  def asyncBlockingFunction(arg: Int): Future[Int] = Future {
    Thread.sleep(10000)
    arg + 42
  }

  println(asyncBlockingFunction(3))

  println("after future ")
}
