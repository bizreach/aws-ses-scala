package jp.co.bizreach.ses

import com.amazonaws._
import auth._
import regions._
import services.simpleemail._

trait SES { self :AmazonSimpleEmailServiceAsyncClient =>


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
