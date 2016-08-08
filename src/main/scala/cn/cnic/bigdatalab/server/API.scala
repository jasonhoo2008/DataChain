package cn.cnic.bigdatalab.server

import cn.cnic.bigdatalab.datachain.{Chain, CollectionStep, TaskStep}
import cn.cnic.bigdatalab.task.{RealTimeTask, SchedulerFactory}
import cn.cnic.bigdatalab.utils.{FileUtil, PropertyUtil}

/**
  * Created by xjzhu@cnic.cn on 2016/8/1.
  */
object API {

  val json_path = PropertyUtil.getPropertyValue("json_path")

  def runRealTimeTask(agentId:String, taskId: String): Unit ={
    //1.define Collection
//    val agent_json_path = json_path + "/" + agentId
//    val agent = FileUtil.agentReader(agent_json_path)
//    val collectionStep = new CollectionStep().initAgent(agent)

    //2. Define real Task
    val task_json_path = json_path + "/" + "realtime/" + taskId
    val taskBean = FileUtil.taskReader(task_json_path)
    val taskStep = new TaskStep().setRealTimeTask(new RealTimeTask(taskBean)).run

//    val chain = new Chain()
//    chain.addStep(collectionStep).addStep(taskStep).run()
  }

  def deleteTask(name: String): Unit = {
    if(!name.isEmpty && name.contains("_")){
      val taskType = name.split("_")(0)
      val scheduler = SchedulerFactory(taskType)
      scheduler.cancel(name)
    }

  }

}