package com.simplesys.common

import java.io.{File, InputStreamReader}
import java.net.{URL ⇒ JURL}

import com.simplesys.common
import com.simplesys.common.Strings._
import com.simplesys.common.exception.ExtThrowable
import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory

case class URLSBox(message: String, urls: JURL*) extends UR{
    private val logger = Logger(LoggerFactory.getLogger(this.getClass))
    override def toString: String = {
        urls foreach {
            url =>
                logger trace (s"======================== URL(${url})===========================")
                logger trace (s"authority: ${url getAuthority}")
                logger trace (s"user-info: ${url getUserInfo}")
                logger trace (s"protocol: ${url getProtocol}")
                logger trace (s"file: ${url getFile}")
                logger trace (s"host: ${Host(Some(url))}")
                logger trace (s"port: ${url getPort}")
                logger trace (s"default port: ${url getDefaultPort}")
                logger trace (s"path: ${url getPath}")
                logger trace (s"query: ${url getQuery}")
                logger trace (s"ref: ${url getRef}")
                logger trace (s"======================== End URL===========================")
        }
        urls.mkString(newLine)
    }

    def Host(url: Option[JURL]): String = url match {
        case Some(url) => url getHost match {
            case "" => "localhost"
            case other => other
        }
        case None => ""
    }
}

case class URLBox(url: Option[JURL], message: String) extends UR {
    private val logger = Logger(LoggerFactory.getLogger(this.getClass))

    override def toString = {
        url match {
            case Some(url) =>
                logger trace (s"======================== URL(${url})===========================")
                logger trace (s"authority: ${url getAuthority}")
                logger trace (s"user-info: ${url getUserInfo}")
                logger trace (s"protocol: ${url getProtocol}")
                logger trace (s"file: ${url getFile}")
                logger trace (s"host: ${Host}")
                logger trace (s"port: ${url getPort}")
                logger trace (s"default port: ${url getDefaultPort}")
                logger trace (s"path: ${url getPath}")
                logger trace (s"query: ${url getQuery}")
                logger trace (s"ref: ${url getRef}")
                logger trace ("======================== End URL===========================")
                url toString

            case None =>
                logger trace ("======================== URL(null)===========================")
                ""
        }
    }

    def Host: String = url match {
        case Some(url) => url getHost match {
            case "" => "localhost"
            case other => other
        }
        case None => ""
    }

    def Path: String = url match {
        case Some(url) => url getPath
        case None => ""
    }

    def PathWithOutHost: String = url match {
        case Some(url) => Path replace("/" + Host, "")
        case None => ""
    }
}

object URLBox {
    implicit def jURL2Url(url: JURL): URLBox = {
        val f = new File(url toURI)
        if (f exists)
            URLBox(Some(url), "")
        else
            URLBox(None, String format(s"URL: '${url}' not found"))
    }

    implicit def Url2JUrl(url: URLBox): JURL = {
        url match {
            case URLBox(Some(url), message) => url
            case URLBox(None, message) => new JURL("")
        }
    }

    def getResource(path: String): URLBox = {
        try {
            val url = getClass.getClassLoader.getResource(path)

            url match {
                case null =>
                    URLBox(None, String format(s"Path: '${path}' not found"))
                case url =>
                    URLBox(Some(url), "")
            }
        }
        catch {
            case ex: Throwable => URLBox(None, s"${new ExtThrowable(ex).getStackTraceString}")
        }
    }

    def getResourceAsStreamReader(path: String): Option[InputStreamReader] = common getResourceAsStreamReader path
}
