package com.simplesys.common

import java.io._
import java.util.{Properties => JProperties}

import com.simplesys.common.Strings._
import com.simplesys.log.Logging

import scala.collection.JavaConverters._
import scala.io.Codec._
import scala.io.Source._

class Properties(protected val proxy: JProperties = new JProperties()) extends Logging {
    def apply(key: String): String = (proxy get key) toString

    def apply(key: String, default: String): String =
        proxy get key match {
            case null =>
                logger trace (s"Property key: '${key}'? default value: '${default}'")
                default
            case value =>
                logger trace (s"Property key: '${key}' value: '${value}'")
                value toString
        }

    def logProperties {
        proxy.asScala foreach (res => logger trace (res._1 + "=" + res._2))
    }

    def getMap: Map[String, String] = proxy.asScala.map {
        case (key, value) => (key -> value)
    }.toMap

    def update(key: String, value: String) = proxy put(key, value)

    def propertyNames = proxy keySet

    private def load(inStream: InputStream) = proxy load inStream

    private def load(reader: Reader) = proxy load reader

    def loadFromURL(url: URLBox): Properties = {
        try {
            url match {
                case URLBox(Some(url), message) =>
                    load(fromURL(url)(UTF8) reader)
                    logger trace (s"loadFromUrl: ${url}")
                    this
                case URLBox(None, message) =>
                    logger error (s"loadFromUrl: ${message}")
                    this
            }
        }
        catch {
            case ex: IOException =>
                logger error (ex)
                this
        }
    }

    def loadFromURI(uri: URIBox): Properties = {
        try {
            uri match {
                case URIBox(Some(uri), message) =>
                    load(fromURL(uri toURL)(UTF8) reader)
                    logger trace (s"loadFromUri: ${uri}")
                    this
                case URIBox(None, message) =>
                    logger error (s"loadFromUri: ${message}")
                    this
            }
        }
        catch {
            case ex: IOException =>
                logger error (ex)
                this
        }
    }

    def loadFromInputStream(is: InputStream): Properties = {
        try {
            load(fromInputStream(is)(UTF8) reader)
            this
        }
        catch {
            case ex: IOException =>
                logger error (ex)
                this
        }
    }

    def loadFromResourse(resourseName: String): Properties = getResourceAsStream(resourseName) match {
        case None => new Properties(new JProperties())
        case Some(stream) => loadFromInputStream(stream)
    }
}

object Properties {
    def loadFromURL(url: URLBox): Properties = new Properties(new JProperties()).loadFromURL(url)

    def loadFromURI(uri: URIBox): Properties = new Properties(new JProperties()).loadFromURI(uri)

    def loadFromInputStream(is: InputStream): Properties = new Properties(new JProperties()).loadFromInputStream(is)

    def loadFromResourse(resourseName: String): Properties = new Properties(new JProperties()).loadFromResourse(resourseName)

    implicit def javaProperties2ssProperties(proxy: JProperties): Properties = {
        proxy match {
            case null => new Properties(new JProperties())
            case properties => new Properties(properties)
        }
    }

    implicit def ssProperties2javaProperties(props: Properties): JProperties = props proxy

    implicit def ssProperties2TupleSeq(props: Properties): Seq[Tuple2[String, String]] = (props.proxy.proxy.asScala map {
        case (key, value) => (key -> value)
    }).toSeq

    def SysProperty(key: String, defaultValue: String) = Option(System.getProperty(key, defaultValue)) getOrElse strEmpty

    def SysProperty(key: String) = Option(System.getProperty(key)) getOrElse strEmpty

    def SysProperty_=(key: String, value: String) = System.setProperty(key, value)

    def getSysProperties: Properties = System.getProperties

    def out = System.out

    def in = System.in

    def err = System.err
}
