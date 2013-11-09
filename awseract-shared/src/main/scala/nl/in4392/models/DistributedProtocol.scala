package main.scala.nl.in4392.models

object DistributedProtocol {


  case class WorkerRegister(workerId: String)
  case class WorkerRequestTask(workerId: String)

  case class TaskCompleted(workerId: String, taskId: String, result: Any)
  case class TaskFailed(workerId: String, taskId: String)

  case object TaskAvailable

  //Monitoring
  case object RequestSystemInfo
  case class ReportSystemInfo(workerId:String,jsonString:String)
  case class MonitorRegister(workerId: String)
}
