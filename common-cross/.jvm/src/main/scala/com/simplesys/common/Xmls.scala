package com.simplesys.common

import scala.xml.NodeSeq


object XMLs {
  implicit class NodeSeqWrapper(nodeSeq: NodeSeq) {
    def textOption: Option[String] = {
      val text = nodeSeq.text
      if (text == null || text.isEmpty) None else Some(text)
    }
  }
}


