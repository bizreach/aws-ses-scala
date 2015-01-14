package jp.co.bizreach.ses


import java.util.concurrent.ExecutorService

import scala.concurrent.Future
import scala.collection.JavaConverters._

import com.amazonaws.auth._
import com.amazonaws.handlers.AsyncHandler
import com.amazonaws.regions.Region
import com.amazonaws.services.simpleemail._
import com.amazonaws.services.simpleemail.model._


trait SES { self: SESClient =>
  import client._


  def buildRequest(email: models.Email): SendEmailRequest = {
    val subject = new Content(email.subject.data).withCharset(email.subject.charset)

    val body = new Body()
    email.bodyHtml.foreach { bodyHtml =>
      val htmlContent = new Content()
      htmlContent.setCharset(bodyHtml.charset)
      htmlContent.setData(bodyHtml.data)
      body.setHtml(htmlContent)
    }

    email.bodyText.foreach { bodyText =>
      val textContent = new Content()
      textContent.setCharset(bodyText.charset)
      textContent.setData(bodyText.data)
      body.setText(textContent)
    }
    val message = new Message(subject, body)

    val destination = new Destination()
      .withToAddresses(email.to.map(_.encoded).asJavaCollection)
      .withCcAddresses(email.cc.map(_.encoded).asJavaCollection)
      .withBccAddresses(email.bcc.map(_.encoded).asJavaCollection)

    val req = new SendEmailRequest(email.source.encoded, destination, message)
    req.setReplyToAddresses(email.replyTo.map(_.encoded).asJavaCollection)
    email.returnPath.map { returnPath =>
      req.setReturnPath(returnPath)
    }

    req
  }


  def send(email: models.Email): Future[SendEmailResult] = wrapAsyncMethod {
    sendEmailAsync(buildRequest(email), _: AsyncHandler[SendEmailRequest, SendEmailResult])
  }


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

