package main.scala.nl.in4392.worker

import com.typesafe.config.ConfigFactory
import akka.kernel.Bootable
import akka.actor.{ ActorRef, Props, Actor, ActorSystem }
import akka.actor.ActorPath
import java.util.UUID

class WorkerDaemon extends Bootable {



  val workerId = UUID.randomUUID().toString
  val config = ConfigFactory.load().getConfig("workerSys")
  val system = ActorSystem("WorkerNode", config)
  val workerActor = system.actorOf(Props(new WorkerActor(workerId,ActorPath.fromString("akka.tcp://MasterNode@127.0.0.1:2552/user/masterActor"))))
  val watchActor = system.actorOf(Props(new MonitorActor(workerId,ActorPath.fromString("akka.tcp://MasterNode@127.0.0.1:2552/user/masterActor"))))



  def startup() {
  }


  def shutdown() {
    system.shutdown()
  }
}

object WorkerApp {
  def main(args: Array[String]) {
    val app = new WorkerDaemon
    println("Started Worker Node")
  }
}