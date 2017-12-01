/*package com.simplesys.common
package concurrent

import scala.concurrent._
import scala.concurrent.duration.Duration
import scala.util.Try

class FutureEx[+T](val proxy: Future[T], val interruptMethod: () => Boolean, val cancelMethod: () => Boolean) extends Future[T] {

  override def transform[S](f: (Try[T]) ⇒ Try[S])(implicit executor: ExecutionContext): Future[S] = proxy.transform(f)(executor)
  override def transformWith[S](f: (Try[T]) ⇒ Future[S])(implicit executor: ExecutionContext): Future[S] = proxy.transformWith(f)(executor)

  override def onComplete[U](func: (Try[T]) => U)(implicit executor: ExecutionContext): Unit = proxy.onComplete(func)(executor)

  override def isCompleted: Boolean = proxy.isCompleted

  override def value: Option[Try[T]] = proxy.value

  @throws(classOf[Exception])
  override def result(atMost: Duration)(implicit permit: CanAwait): T = proxy.result(atMost)(permit)

  @throws(classOf[TimeoutException])
  @throws(classOf[InterruptedException])
  override def ready(atMost: Duration)(implicit permit: CanAwait): this.type = {
    proxy.ready(atMost)(permit)
    this
  }

  def interrupt: Boolean = interruptMethod()
  def cancel: Boolean = cancelMethod()

}

object FutureEx {
  def interruptableFuture[T](fun: Future[T] => T)(implicit ex: ExecutionContext): FutureEx[T] = {
    val p = Promise[T]()
    val f = p.future
    val lock = new Object
    var currentThread: Thread = null
    def updateCurrentThread(newThread: Thread): Thread = {
      val old = currentThread
      currentThread = newThread
      old
    }
    p tryCompleteWith Future {
      val thread = Thread.currentThread
      lock.synchronized { updateCurrentThread(thread) }
      try fun(f) finally {
        val wasInterrupted = lock.synchronized { updateCurrentThread(null) } ne thread
        //Deal with interrupted flag of this thread in desired
      }
    }

    new FutureEx(f, () => lock.synchronized {
      Option(updateCurrentThread(null)) exists {
        t =>
          t.interrupt()
          p.tryFailure(new CancellationException)
      }
    },
    () => p.tryFailure(new CancellationException))
  }

  def interruptable[T](body: =>T)(implicit execCtx: ExecutionContext): FutureEx[T] = interruptableFuture {
    future: Future[T] => body
  }

  def cancellable[T](body: Future[T] => T)(implicit execCtx: ExecutionContext): FutureEx[T] = interruptableFuture(body)
}*/
