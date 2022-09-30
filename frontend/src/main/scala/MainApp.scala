package example

import com.raquo.laminar.api.L._
import org.scalajs.dom
import zio.*


////////////////////////////////////////////// Main

object Counters:
  val nbrComponent = 200
  val myComponents = for i <- 1 to nbrComponent yield myCounter

  def appComponent = 
    val myDiv =
      div()
    for myComponent <- myComponents do
      myDiv.amend(myComponent.elem)
    myDiv
    
object MyAppCounters:
  def main(args: Array[String]): Unit =
    val _ = documentEvents.onDomContentLoaded.foreach { _ =>
      val appContainer = dom.document.querySelector("#app")
      appContainer.innerHTML = ""
      val _            = render(appContainer, Counters.appComponent)
    }(unsafeWindowOwner)
    //test()

////////////////////////////////////////////// Test

  def test() =
    val nbrClick = 400
    val r = scala.util.Random
    for i <- 1 to nbrClick do
      val n = r.nextInt(Counters.nbrComponent)
      val myComponent = Counters.myComponents(n)
      r.nextInt(4) match
        case 0 => myComponent.start(100*i+n*10)
        case 1 => myComponent.stop()
        case 2 => myComponent.pause()
        case 3 => myComponent.resume()

