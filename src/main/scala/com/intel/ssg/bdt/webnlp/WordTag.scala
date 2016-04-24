package com.intel.ssg.bdt.webnlp

import scala.collection.mutable.ArrayBuffer
import com.intel.ssg.bdt.nlp._

object WordTag {
  def PosTag(dataSrc: Array[String], modelBme: CRFModel, modelPos: CRFModel): Array[String] = {

    val testData = dataSrc.filter(_.nonEmpty).map(sentence => {
      val words = sentence.toArray
      val tokens = ArrayBuffer[Token]()
      for (i <- words.indices) {
        tokens += Token.put(null, Array(words(i).toString))
      }
      Sequence(tokens.toArray)
    })
   val results1 = modelBme.predict(testData)

    val posData = results1.map(sequence => {
      var tagTmp = ArrayBuffer[String]()
      val tokensTmp = ArrayBuffer[Token]()
      val tokens = sequence.toArray
      for (i <- tokens.indices) {
        tagTmp += tokens(i).tags(0)
        if (tokens(i).label == "s" || tokens(i).label == "e") {
          tokensTmp += Token.put(null, tagTmp.toArray)
          tagTmp = ArrayBuffer[String]()
        }
      }
      Sequence(tokensTmp.toArray)
    })
    val result = modelPos.predict(posData).map(Sequence.serializer)
    result
  }
}
