package jp.co.bizreach.ses


case class SESRequest(
  from: String,
  to: String,
  subject: String,
  body: String
)


case class SESRawRequest(
  from: String,
  to: String,
  rawMessage: java.nio.ByteBuffer
)



object models {

  case class Address(address: String,
                     personal: Option[String] = None,
                     charset: String = "UTF-8") {

    // TODO(tanacasino): RFC822に従ってエンコードする.
    def encoded = {
      address
    }

  }

  case class Content(data: String,
                     charset: String = "UTF-8")

  case class Email(subject: Content,
                   bodyText: Option[Content],
                   bodyHtml: Option[Content],
                   source: Address,
                   to: Seq[Address],
                   cc: Seq[Address],
                   bcc: Seq[Address],
                   replyTo: Seq[Address],
                   returnPath: Option[String])
}

