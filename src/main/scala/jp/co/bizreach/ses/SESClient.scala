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
  import aws._


  def buildRequest(email: models.Email): SendEmailRequest = {
    val destination = new Destination()
    if(email.to.nonEmpty) destination.setToAddresses(email.to.map(_.encoded).asJavaCollection)
    if(email.cc.nonEmpty) destination.setCcAddresses(email.cc.map(_.encoded).asJavaCollection)
    if(email.bcc.nonEmpty) destination.setBccAddresses(email.bcc.map(_.encoded).asJavaCollection)

    val subject = new Content(email.subject.data).withCharset(email.subject.charset)

    val body = new Body()
    email.bodyHtml.foreach { bodyHtml =>
      val htmlContent = new Content(bodyHtml.data)
      htmlContent.setCharset(bodyHtml.charset)
      body.setHtml(htmlContent)
    }
    email.bodyText.foreach { bodyText =>
      val textContent = new Content(bodyText.data)
      textContent.setCharset(bodyText.charset)
      body.setText(textContent)
    }

    val message = new Message(subject, body)

    val req = new SendEmailRequest(email.source.encoded, destination, message)
    if(email.replyTo.nonEmpty) req.setReplyToAddresses(email.replyTo.map(_.encoded).asJavaCollection)

    email.returnPath.map { returnPath =>
      req.setReturnPath(returnPath)
    }

    req
  }


  def send(email: models.Email): Future[SendEmailResult] = wrapAsyncMethod {
    sendEmailAsync(buildRequest(email), _: AsyncHandler[SendEmailRequest, SendEmailResult])
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


class SESClient (val aws: AmazonSimpleEmailServiceAsync) extends SES

