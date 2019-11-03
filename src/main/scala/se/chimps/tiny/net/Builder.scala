package se.chimps.tiny.net

trait Builder {
	def withPort(port:Int):Builder
	def withBacklog(backlog:Int):Builder
	def withReadBuffer(size:Int):Builder
	def build():Server
}

case class Config(port:Int, backlog:Int, readBuffer:Int) extends Builder {
	override def withPort(port:Int):Builder = copy(port = port)

	override def withBacklog(backlog:Int):Builder = copy(backlog = backlog)

	override def withReadBuffer(size:Int):Builder = copy(readBuffer = size)

	override def build():Server = new Server(this)
}