package se.chimps.tiny

import se.chimps.tiny.net.Builder

object Tiny {
	def builder():Builder = net.Config(0, 25, 1024)
}