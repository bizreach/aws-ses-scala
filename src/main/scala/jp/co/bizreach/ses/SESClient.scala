package jp.co.bizreach.ses

import java.util.concurrent.ExecutorService

import scala.concurrent.Future

import com.amazonaws._
import auth._
import handlers.AsyncHandler
import regions._
import services.simpleemail._
import model._


trait SES { self: SESClient =>
  import client._

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

  def apply(accessKeyId: String, secretKeyId: String)(implicit region: Region): SESClient = {
    apply(new BasicAWSCredentials(accessKeyId, secretKeyId))
  }


  def apply(awsCredentials: AWSCredentials = new AnonymousAWSCredentials)(implicit region: Region): SESClient = {
    val client = new AmazonSimpleEmailServiceAsyncClient(awsCredentials)
    client.setRegion(region)
    new SESClient(client)
  }


  def apply(awsCredentials: AWSCredentials, executorService: ExecutorService)(implicit region: Region): SESClient = {
    val client = new AmazonSimpleEmailServiceAsyncClient(awsCredentials, executorService)
    client.setRegion(region)
    new SESClient(client)
  }


  def apply(awsCredentialsProvider: AWSCredentialsProvider)(implicit region: Region): SESClient = {
    val client = new AmazonSimpleEmailServiceAsyncClient(awsCredentialsProvider)
    client.setRegion(region)
    new SESClient(client)
  }


  def apply(awsCredentialsProvider: AWSCredentialsProvider, executorService: ExecutorService)
           (implicit region: Region): SESClient = {
    val client = new AmazonSimpleEmailServiceAsyncClient(awsCredentialsProvider, executorService)
    client.setRegion(region)
    new SESClient(client)
  }

}



class SESClient private (val client: AmazonSimpleEmailServiceAsync) extends SES

