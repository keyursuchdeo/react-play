package controllers

import play.api.mvc._
import java.io.FileReader
import utils.JSEngine

object Application extends Controller {

  def index = Action {
    val scriptEngine = JSEngine.init
    if (scriptEngine == null) {
      BadRequest("Nashorn script engine not found. Are you using JDK 8?")
    } else {
      println("Loading js files")
      scriptEngine.eval(new FileReader("target/web/public/main/javascripts/components/Greetings.js"))
      val initVal = Map (("name", "keyur"))
      Ok(views.html.main("React on Play") {
        play.twirl.api.Html(scriptEngine.eval("ReactDOMServer.renderToString(React.createElement(Greetings, {'name': 'keyur'}))").toString);
      })
    }
  }
}
