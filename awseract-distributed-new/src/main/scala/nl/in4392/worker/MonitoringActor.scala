package main.scala.nl.in4392.worker
import com.typesafe.config.ConfigFactory
import akka.actor.{ ActorRef, Props, Actor, ActorSystem }
import akka.actor.ActorLogging
import akka.actor.ActorPath
import nl.in4392.models.DistributedProtocol.WorkerRegister
import main.scala.nl.in4392.models.DistributedProtocol.MonitorRegister
import scala.concurrent.duration._


class MonitorActor(workerId:String,masterPath: ActorPath) extends Actor with ActorLogging {
  import main.scala.nl.in4392.models.DistributedProtocol.{ReportSystemInfo,RequestSystemInfo}
  import nl.tudelft.ec2interface.sysmonitor._
  import context._

  val master = context.actorSelection(masterPath)
  // val workerId = UUID.randomUUID().toString

  override def preStart() = {
    println("Inside preStart")
    master ! MonitorRegister(workerId)

  }

  def receive = {
    case RequestSystemInfo =>
      master ! ReportSystemInfo(workerId, new SystemUsage().ToJson(new SystemUsage().getInfo))
      context.system.scheduler.scheduleOnce(10 seconds, self, RequestSystemInfo)
  }


}


