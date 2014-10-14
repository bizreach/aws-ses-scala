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
