package emil.markdown

import minitest._
import cats.effect._
import emil._
import emil.builder._

object MarkdownBodyTest extends SimpleTestSuite {

  test("set markdown body") {
    val md = "## Hello!\n\nThis is a *markdown* mail!"
    val mail: Mail[IO] = MailBuilder.build(
      From("me@test.com"),
      To("test@test.com"),
      Subject("Hello!"),
      MarkdownBody(md)
    )

    assertEquals(mail.body.textPart.unsafeRunSync, Some(md))

    val expectedHtml = """<!DOCTYPE html>
        |<html>
        |<head>
        |<meta charset="utf-8"/>
        |<style>
        |body { padding: 2em 5em; }
        |</style>
        |</head>
        |<body>
        |<h2>Hello!</h2>
        |<p>This is a <em>markdown</em> mail!</p>
        |</body>
        |</html>
        |""".stripMargin

    assertEquals(mail.body.htmlPart.unsafeRunSync, Some(expectedHtml))
  }

}
