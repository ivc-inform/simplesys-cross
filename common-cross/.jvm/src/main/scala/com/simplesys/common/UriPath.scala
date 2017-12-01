package com.simplesys.common

object UriPath {
  def apply(parts: String*): String = parts mkString "/"
  def unapplySeq(path: String): Option[Seq[String]] = Some(path split "/")
}