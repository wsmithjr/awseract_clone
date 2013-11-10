package main.scala.nl.in4392.worker

import com.typesafe.config.ConfigFactory
import akka.kernel.Bootable
import akka.actor.{ ActorRef, Props, Actor, ActorSystem }
import akka.actor.ActorPath
import java.util.UUID
import nl.tudelft.ec2interface.instancemanager._

class WorkerDaemon extends Bootable {



  val workerId = UUID.randomUUID().toString
  val config = ConfigFactory.load().getConfig("workerSys")
  val system = ActorSystem("WorkerNode", config)
  val masterActorPath = new RemoteActorInfo().getInfoFromFile("conf/masterInfo").getActorPath()
  val workerActor = system.actorOf(Props(new WorkerActor(workerId,ActorPath.fromString(masterActorPath))))
  val watchActor = system.actorOf(Props(new MonitorActor(workerId,ActorPath.fromString(masterActorPath))))



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