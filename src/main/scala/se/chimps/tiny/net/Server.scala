package se.chimps.tiny.net

import java.net.ServerSocket

import io.reactivex.rxjava3.core.{Observable, ObservableEmitter}

import scala.util.{Failure, Success, Try}

class Server(private val config:Config) {
	private var socket:ServerSocket = _
	private var running = false

	def start():Observable[Connection] = {
		Try(new ServerSocket(config.port, config.backlog)) match {
			case Success(conn) => socket = conn
			case Failure(e) => return Observable.error(e)
		}

		running = true

		Observable.create((emitter:ObservableEmitter[Connection]) => {
			while (running) {
				Try(socket.accept()) match {
					case Success(conn) => emitter.onNext(ConnectionImpl(conn, config.readBuffer))
					case Failure(e) => emitter.onError(e)
				}
			}

			emitter.onComplete()
		})
	}

	def stop():Unit = {
		running = false
	}

	def localPort():Int = socket.getLocalPort
}
