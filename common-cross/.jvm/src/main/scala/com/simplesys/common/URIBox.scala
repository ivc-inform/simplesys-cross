package com.simplesys.common

import java.io.{File, InputStreamReader}
import java.net.{URI ⇒ JURI}

import com.simplesys.common
import com.simplesys.common.Strings._
import com.simplesys.common.exception.ExtThrowable
import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory

import scala.util.control.Breaks._

case class URISBox(message: String, uris: JURI*) extends UR  {
    private val logger = Logger(LoggerFactory.getLogger(this.getClass))
    override def toString: String = {
        uris foreach {
            uri =>
                logger trace (s"======================== URI(${uri})===========================")
                logger trace (s"scheme: ${uri getScheme}")
                logger trace (s"scheme-specific-part: ${uri getSchemeSpecificPart}")
                logger trace (s"authority: ${uri getAuthority}")
                logger trace (s"user-info: ${uri getUserInfo}")
                logger trace (s"host: ${uri getHost}")
                logger trace (s"port: ${uri getPort}")
                logger trace (s"path: ${uri getPath}")
                logger trace (s"query: ${uri getQuery}")
                logger trace (s"fragment: ${uri getFragment}")
                logger trace ("======================== End URI===========================")
        }
        uris.mkString(newLine)
    }
}

case class URIBox(uri: Option[JURI], message: String) extends UR {
    private val logger = Logger(LoggerFactory.getLogger(this.getClass))
    
    override def toString = {
        uri match {
            case Some(uri) =>
                logger trace (s"======================== URI(${uri})===========================")
                logger trace (s"scheme: ${uri getScheme}")
                logger trace (s"scheme-specific-part: ${uri getSchemeSpecificPart}")
                logger trace (s"authority: ${uri getAuthority}")
                logger trace (s"user-info: ${uri getUserInfo}")
                logger trace (s"host: ${uri getHost}")
                logger trace (s"port: ${uri getPort}")
                logger trace (s"path: ${uri getPath}")
                logger trace (s"query: ${uri getQuery}")
                logger trace (s"fragment: ${uri getFragment}")
                logger trace ("======================== End URI===========================")
                uri toString

            case None =>
                logger trace ("======================== URI(null)===========================")
                strEmpty
        }
    }

    def Path: String = uri match {
        case Some(uri) => uri getPath
        case None => strEmpty
    }

    def SchemeSpecificPart: String = uri match {
        case Some(uri) => uri getSchemeSpecificPart
        case None => strEmpty
    }
}

object URIBox {
    implicit def jURI2Uri(uri: JURI): URIBox = {
        val f = new File(uri)
        if (f exists)
            URIBox(Some(uri), strEmpty)
        else
            URIBox(None, String format (s"URI: '${uri}' not found"))
    }

    implicit def jURIS2Uri(uris: JURI*): URISBox = {
        var res = URISBox(strEmpty, uris: _*)

        breakable {
            uris foreach {
                uri =>
                    val f = new File(uri)
                    if (!f.exists) {
                        res = URISBox(String format (s"URI: '${uri}' not found"))
                        break
                    }
            }
        }
        res
    }

    implicit def Uri2JUri(uri: URIBox): JURI = {
        uri match {
            case URIBox(Some(uri), message) => uri
            case URIBox(None, message) => new JURI(strEmpty)
        }
    }


    //found   : com.simplesys.common.URISBox
    //required: Seq[com.simplesys.common.URI]

    implicit def Uris2JUri(uris: URISBox): Seq[JURI] = {
        uris match {
            case URISBox(_, _uris) => uris.uris
            case _ => Seq.empty[JURI]
        }
    }

    def getResource(path: String): URIBox = {
        try {
            val url = getClass.getClassLoader.getResource(path)

            url match {
                case null =>
                    URIBox(None, String format (s"Path: '${path}' not found"))
                case url =>
                    URIBox(Some(url.toURI), strEmpty)
            }
        }
        catch {
            case ex: Throwable => URIBox(None, s"${new ExtThrowable(ex).getStackTraceString}")
        }
    }

    def getResourceAsStreamReader(path: String): Option[InputStreamReader] = common getResourceAsStreamReader path
}
