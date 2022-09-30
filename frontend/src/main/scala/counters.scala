package example

import com.raquo.laminar.api.L._
import org.scalajs.dom
import zio.*

import be.doeraene.webcomponents.ui5.*
import be.doeraene.webcomponents.ui5.configkeys.*

////////////////////////////////////////////// A ControlledWebComponent example

def myCounter: ControlledWebComponent[Div,Int] = new ControlledWebComponent[Div,Int] {
  val period = 100.millisecond
  val timeout = 30.second
  val content = Var("not started yet")
  val elem:Div = div(
    Button(
      "start",
      onClick --> doStart
    ),
    Button(
      "stop",
      onClick --> doStop
    ),
    Button(
      "pause",
      onClick --> doPause
    ),
    Button(
      "resume",
      onClick --> doResume
    ),
    TextArea(
      _.value <-- content.signal
    )
  )
  def getControlledZio(n:Int): ControlledZio = new ControlledZio {
    val uio =
      def loop(n:Int):ZIO[Any,Nothing,Unit] =
        for
          _ <- ZIO.succeed(content.set(n.toString())) *> ZIO.sleep(period)
          _ <- waitIfLocked()  // used to pause/resume
          _ <- loop(n+1)
        yield ()
      val zio1 = loop(n)
      zio1.timeout(timeout).as(())
  }
  def doStart(ev:dom.MouseEvent): Unit =
    val n = ev.clientX.toInt
    start(n*100)
  def doStop(ev:dom.MouseEvent) = stop()
  def doPause(ev:dom.MouseEvent) = pause()
  def doResume(ev:dom.MouseEvent) = resume()
}

