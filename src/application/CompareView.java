package application;

import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.concurrent.Worker.State;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import netscape.javascript.JSObject;

@SuppressWarnings("restriction")
public class CompareView extends Pane {
    JSCall jcall;
    
    public WebView webView(String firstTree, String secondTree) {
    	WebView browser = new WebView();
    	final WebEngine webEngine = browser.getEngine();
        
    	jcall = new JSCall();
        
    	final JSObject windowObject = (JSObject) webEngine.executeScript("window");
        windowObject.setMember("app", jcall);
        
        webEngine.getLoadWorker().stateProperty().addListener(
	            new ChangeListener<State>() {
	                public void changed(ObservableValue ov, State oldState, State newState) {
	                    if (newState == State.SUCCEEDED) {
	                    	windowObject.call("compare", firstTree, secondTree);
	                    }
	                }
	            });

        final java.net.URL url = this.getClass().getResource("/resources/compareview.html");
        webEngine.load(url.toExternalForm());
        
        return browser;
    }
}