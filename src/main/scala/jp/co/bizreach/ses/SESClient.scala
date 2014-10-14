package jp.co.bizreach.ses

import scala.concurrent.Future

import com.amazonaws._
import auth._
import handlers.AsyncHandler
import regions._
import services.simpleemail._
import model._

trait SES { self :AmazonSimpleEmailServiceAsyncClient =>

  def sendEmail(request: SESRequest): Future[SendEmailResult] = wrapAsyncMethod {
    sendEmailAsync(
      new SendEmailRequest(
        request.from,
        new Destination().withToAddresses(request.to),
        new Message(new Content(request.subject), new Body(new Content(request.body)))
      ), _: AsyncHandler[SendEmailRequest, SendEmailResult]
    )
  }

  // TODO Wonder if this glad?
  def sendRawEmail(request: SESRawRequest): Future[SendRawEmailResult] = wrapAsyncMethod {
    sendRawEmailAsync(
      new SendRawEmailRequest(
        new RawMessage(request.rawMessage)
      ).withSource(request.from)
       .withDestinations(request.to)
      , _: AsyncHandler[SendRawEmailRequest, SendRawEmailResult]
    )
  }

}

object SESClient {
  // TODO default value
  def apply(credentials: AWSCredentials = new AnonymousAWSCredentials)(implicit region: Region): SESClient = {
    val client = new SESClient(credentials)
    client.setRegion(region)
    client
  }

  def apply(accessKeyId: String, secretAccessKey: String)(implicit region: Region): SESClient =
    apply(new BasicAWSCredentials(accessKeyId, secretAccessKey))(region)

  def at(region: Region): SESClient = apply()(region)
}

/**
 * Amazon SES asynchronous client.
 */
class SESClient private (credentials: AWSCredentials)
  extends AmazonSimpleEmailServiceAsyncClient(credentials)
  with SES
