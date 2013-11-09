package main.scala.nl.in4392.worker
import com.typesafe.config.ConfigFactory
import akka.actor.{ ActorRef, Props, Actor, ActorSystem }
import akka.actor.ActorLogging
import akka.actor.ActorPath
import nl.in4392.models.DistributedProtocol.WorkerRegister
import main.scala.nl.in4392.models.DistributedProtocol.MonitorRegister


class MonitorActor(workerId:String,masterPath: ActorPath) extends Actor with ActorLogging {
  import main.scala.nl.in4392.models.DistributedProtocol.{ReportSystemInfo,RequestSystemInfo}
  import nl.tudelft.ec2interface.sysmonitor._


  val master = context.actorSelection(masterPath)
  // val workerId = UUID.randomUUID().toString

  override def preStart() = {
    println("Inside preStart")
    master ! MonitorRegister(workerId)

  }



  def receive = {
    case RequestSystemInfo => sender ! ReportSystemInfo(workerId, new SystemUsage().ToJson(new SystemUsage().getInfo))
  }



  def getSystemInformation(): String = ""

}
