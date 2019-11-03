package se.chimps.tiny.example

import se.chimps.tiny.Tiny
import se.chimps.tiny.net.Connection

import scala.sys.ShutdownHookThread

object ReverseEcho {

	def main(args:Array[String]):Unit = {
		val server = Tiny.builder()
  		.withPort(4000)
  		.build()

		ShutdownHookThread {
			server.stop()
		}

		server.start()
  		.forEach((conn:Connection) => {
			  conn.handle(in => {
				  in.map[String](bs => new String(bs, "utf-8"))
  				  .map[String](str => str.reverse)
  				  .map[Array[Byte]](str => str.getBytes)
			  })
		  })
	}

}
