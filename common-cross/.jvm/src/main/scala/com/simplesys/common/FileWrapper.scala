package com.simplesys.common

import java.io.File

class FileWrapper(val file: File) {
  def /(next: String) = new FileWrapper(new File(file, next))
  override def toString = file.getCanonicalPath
}

object FileWrapper {
  implicit def wrap(file: File): FileWrapper = new FileWrapper(file)
  implicit def unwrap(wrapper: FileWrapper): File = wrapper file
}