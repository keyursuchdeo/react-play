package utils

import java.io.FileReader
import javax.script.{ScriptEngine, ScriptEngineManager}

/**
  * Created by Keyur_Suchdeo on 12/28/2016.
  */
object JSEngine {
  def init: ScriptEngine = {
    // Pass 'null' to force the correct class loader. Without passing any param,
    // the "nashorn" JavaScript engine is not found by the `ScriptEngineManager`.
    //
    // See: https://github.com/playframework/playframework/issues/2532
    val scriptingEngine = new ScriptEngineManager(null).getEngineByName("nashorn")

    if (scriptingEngine != null) {
      // React expects `window` or `global` to exist. Create a `global` pointing
      // to Nashorn's context to give React a place to define its global
      // namespace.
      scriptingEngine.eval("var global = this;")

      // Define `console.log`, etc. to send messages to Nashorn's global `print`
      // function so the messages are written to standard out.
      scriptingEngine.eval("var console = {error: print, log: print, warn: print};")

      // Evaluate React and the application code.
      scriptingEngine.eval(new FileReader("target/web/web-modules/main/webjars/lib/react/react-with-addons.js"))
      scriptingEngine.eval(new FileReader("target/web/web-modules/main/webjars/lib/react/react-dom-server.js"))
    }

    scriptingEngine
  }
}
