package com.simplesys

import java.util.UUID

import collection.generic.CanBuildFrom
import java.io.{InputStreamReader, InputStream, File}
import javax.xml.transform.stream.StreamSource
import java.net.{URI, URL}
import com.simplesys.common.exception.ExtThrowable
import com.simplesys.common.JVM.Strings._

package object common {
    def getGUID = UUID.randomUUID().toString.toUpperCase

    class TraversableW[A](t: Traversable[A]) {
        def As[CC[X] <: Traversable[X]](implicit cbf: CanBuildFrom[Nothing, A, CC[A]]): CC[A] = t.map(identity)(collection.breakOut)
        def To[Result](implicit cbf: CanBuildFrom[Nothing, A, Result]): Result = t.map(identity)(collection.breakOut)
    }

    implicit def ToTraverseableW[A](t: Traversable[A]): TraversableW[A] = new TraversableW[A](t)

    implicit class ShortClassName(string: String) {
        def shortClassName: String = string.substring(string.lastIndexOf(".") + 1)
        def shortClassName1: String = string.substring(string.lastIndexOf(File.separator) + 1)
        def normalizedSpaces = string.trim.replaceAll( """\s\s*""", " ")
        def unEscape = string.
          replace("&quot;", "\"").
          replace("&amp;", "&").
          replace("&lt;", "<").
          replace("&gt;", ">").
          replace("&apos;", "'")
    }

    implicit class DoubleOpts(value: Double) {
        def toBigDecimal: BigDecimal = BigDecimal(value)
    }

    implicit class IntOpts(value: Int) {
        def toBigDecimal: BigDecimal = BigDecimal(value)
    }

    implicit def throw2Throw(t: Throwable): ExtThrowable = new ExtThrowable(t)
    //implicit def saxThrow2Throw(t: SAXException): ExtThrowable = t.asInstanceOf[ExtThrowable]

    abstract class UR
    case object URNull extends UR

    implicit class URIOpts(uri: URI) {
        def toStreamSource: StreamSource = new StreamSource(new File(uri))
    }

    implicit class URLOpts(url: URL) {
        def toStreamSource: StreamSource = new StreamSource(new File(url.toURI))
    }

    final class NotPerformedError(msg: String) extends Error(msg) {
        def this() = this("should not be performed")
    }

    def ???? : Nothing = throw new NotPerformedError

    case class ResInputStream(inputStream: Option[InputStream], errorMessage: String)

    //def getResourceAsStream(path: String): Option[InputStream] =Option(Thread.currentThread().getContextClassLoader().getResourceAsStream(path))
    def getResourceAsStream(path: String): Option[InputStream] = Option(getClass.getClassLoader.getResourceAsStream(path))

    //def getResourceAsStreamReader(path: String): Option[InputStreamReader] = Thread.currentThread().getContextClassLoader().getResourceAsStream(path) match {
    def getResourceAsStreamReader(path: String): Option[InputStreamReader] = getClass.getClassLoader.getResourceAsStream(path) match {
        case null => None
        case stream => Some(new InputStreamReader(stream, "UTF-8"))
    }


    def getResourceAsStream1(path: String): ResInputStream = {
        if (path.isEmpty)
            ResInputStream(None, strEmpty)
        else
            getResourceAsStream(path) match {
                case Some(stream) => ResInputStream(Some(stream), strEmpty)
                case None => ResInputStream(None, s"Path: ${path} was nor streamed.")
            }
    }
}
