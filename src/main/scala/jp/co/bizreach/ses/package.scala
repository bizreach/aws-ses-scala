package jp.co.bizreach


import java.util.concurrent.{Future => JFuture}

import scala.concurrent.{Future, Promise}

import com.amazonaws.AmazonWebServiceRequest
import com.amazonaws.handlers.AsyncHandler


package object ses {

  /**
   * Convert result to scala.concurrent.Future from java.util.concurrent.Future.
   */
  private[ses] def wrapAsyncMethod[Request <: AmazonWebServiceRequest, Result](
      execute: AsyncHandler[Request, Result] => JFuture[Result]): Future[Result] = {
    val p = Promise[Result]()
    execute {
      new AsyncHandler[Request, Result] {
        def onError(exception: Exception): Unit = p.failure(exception)
        def onSuccess(request: Request, result: Result): Unit = p.success(result)
      }
    }
    p.future
  }

}
