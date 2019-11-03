package se.chimps.tiny.net

import java.net.Socket

import io.reactivex.rxjava3.core.{Observable, ObservableEmitter}

import scala.util.{Failure, Success, Try}

trait Connection {
	def handle(handler:Observable[Array[Byte]] => Observable[Array[Byte]]):Unit
}

case class ConnectionImpl(socket:Socket, readSize:Int) extends Connection {
	override def handle(handler:Observable[Array[Byte]] => Observable[Array[Byte]]):Unit = {
		val out = socket.getOutputStream
		val in = socket.getInputStream

		val readObservable = Observable.create((emitter:ObservableEmitter[Array[Byte]]) => {
			while (socket.isConnected) {
				if (in.available() > 0) {
					val buffer = Array.ofDim[Byte](readSize)
					Try(in.read(buffer)) match {
						case Success(read) => {
							if (read > 0) {
								emitter.onNext(buffer)
							}
						}
						case Failure(e) => emitter.onError(e)
					}
				}
			}

			emitter.onComplete()
		})

		handler(readObservable).forEach((bs:Array[Byte]) => {
			out.write(bs)
		})
	}
}