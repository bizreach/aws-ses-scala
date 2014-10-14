package jp.co.bizreach

import scala.concurrent.{Future, Promise}
import java.util.concurrent.{Future => JFuture}

import com.amazonaws.AmazonWebServiceRequest
import com.amazonaws.handlers.AsyncHandler

package object ses {

  private[ses] def wrapAsyncMethod[Request <: AmazonWebServiceRequest, Result](
      execute: AsyncHandler[Request, Result] => JFuture[Result]): Future[Result] = {
    val p = Promise[Result]
    execute {
      new AsyncHandler[Request, Result] {
        def onError(exception: Exception): Unit = p.failure(exception)
        def onSuccess(request: Request, result: Result): Unit = p.success(result)
      }
    }
    p.future
  }

}
