 package cn.cnic.bigdatalab.transformer

/**
 * Created by cnic-liliang on 2016/6/3.
 */

import java.util

 import scala.collection.mutable.ArrayBuffer
 import cn.cnic.bigdatalab.utils.FieldTypeUtil

 class CustomizedTransformer(mapping_conf : String) extends TransformerBase{
   println(mapping_conf)

   val jmapping = Tools.jsonfile2JsonMap(mapping_conf)
   val schemaList =Tools.jsonMap2SchemaList(jmapping)

   override def getSchema():ArrayBuffer[String] = {
     var row = ArrayBuffer[String]()
     for(item <- schemaList) {
       val value = item
       row += item
     }
     row
   }

   override def transform(msg:String): ArrayBuffer[Any] = {
     val result = new ArrayBuffer[Any]()
     //extract and assembl
     for(item <- schemaList) {
       //result.add(Map(item -> msg))
       val value = FieldTypeUtil.parseDataType(item.trim.split(":")(1), msg)
       result += value
     }
     result
   }

   override def multiLineTransform(msg: String):ArrayBuffer[ArrayBuffer[Any]] = {
     return new ArrayBuffer[ArrayBuffer[Any]]()
   }
 }

 // example about defining customized_parser
object CustomizedTransformer {

  def main(agrs: Array[String]): Unit = {
    val mapping_conf = "conf\\" + "customMapping.json"
    val msg = "custom-defined-message"
    val customparser =new CustomizedTransformer(mapping_conf)
    println("msg: " + msg)
    println("parse result: : " + customparser.transform((msg)))
  }
}