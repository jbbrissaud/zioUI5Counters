package example

import zhttp.http._
import zhttp.service.Server
import zio._
import zio.json._

import java.io.File

object WebServer extends ZIOAppDefault:

  def getFile(filename:String) =
    println(s"filename=$filename")
    val file = new File(filename)
    Http.fromFile(file)
  
  private val appBase = 
    Http.collectHttp[Request] { req =>
      val filename = req match
        case Method.GET -> !! =>
          "frontend/static/index.html"
        case Method.GET -> !! / "favicon.ico"=>
          "frontend/static/plante.ico"
        case Method.GET -> !! / "static" / "main.js" =>
          // "frontend/target/scala-3.2.0/frontend-fastopt/main.js"
          "frontend/target/esbuild/bundle.js"
        case Method.GET -> !! / "static" / "bundle.js.map" =>
          "frontend/target/esbuild/bundle.js.map"
        case Method.GET -> !! / "static" / name =>
          s"frontend/static/$name"
      getFile(filename)
    }

  private val appHello: Http[Any, Nothing, Request, Response] =
    Http.collectZIO[Request] {
      case Method.GET -> !! / "hello" / name  => 
        ZIO.succeed(Response.text(s"Bonjour ${name}!"))
    }
    /*Http.collect[Request] {
      case Method.GET -> !! / "hello" / name  => 
        Response.text(s"Bonjour ${name}!")
    }*/

  private val app = appBase ++ appHello
  def run = Server.start(8090, app)

end WebServer
