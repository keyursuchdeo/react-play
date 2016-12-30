package controllers

import play.api.mvc._
import java.io.FileReader
import javax.script.{ScriptContext, SimpleBindings}

import play.api.libs.json.Json
import utils.JSEngine

object Application extends Controller {

  def index(name: String) = Action {
    val scriptEngine = JSEngine.init
    if (scriptEngine == null) {
      BadRequest("Nashorn script engine not found. Are you using JDK 8?")
    } else {
      scriptEngine.eval(new FileReader("target/web/public/main/javascripts/components/Greetings.js"))
      val prop =  "{'name':'" + name.toString +"'}";
      Ok(views.html.main("React on Play") {
        play.twirl.api.Html(
          scriptEngine.eval("ReactDOMServer.renderToString(React.createElement(Greetings, {'name': 'keyur'}))").toString);
      } (prop))
    }
  }
}
