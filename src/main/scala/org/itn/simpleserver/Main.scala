package org.itn.simpleserver

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    SimpleserverServer.stream[IO].compile.drain.as(ExitCode.Success)
}
