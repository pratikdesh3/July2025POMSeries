package com.qa.opencart.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {

	private WebDriver driver;
	private JavascriptExecutor js;

	public JavaScriptUtil(WebDriver driver) {
		this.driver = driver;
		js = (JavascriptExecutor) driver;
	}

	public String getTitleByJS() {
		return js.executeScript("return document.title").toString();
	}

	public String getURLByJS() {
		return js.executeScript("return document.URL").toString();
	}

	public void generateJSAlert(String msg) {
		js.executeScript("alert('" + msg + "')");
	}

	public String getPageFullText() {
		return js.executeScript("return document.documentElement.innerText").toString();
	}

	public void goBackWithJS() {
		js.executeScript("history.go(-1)");
	}

	public void goForwardWithJS() {
		js.executeScript("history.go(1)");
	}

	public void refreshThePageWithJS() {
		js.executeScript("history.go(0)");
	}

	public void zoomChromeEdgeSafariFirefox(String zoomPercentage) {
		String zoom = "document.body.style.zoom = '" + zoomPercentage + "%'";
		js.executeScript(zoom);
	}

	public void scrollPageDownByJS() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public void scrollPageUpByJS() {
		js.executeScript("window.scrollTo(document.body.scrollHeight,0)");
	}

	public void scrollMiddlePageDownByJS() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight/2)");
	}

	public void scrollOneFourthPageDownByJS() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight/4)");
	}

	public void scrollPageUpToPixelsByJS(String height) {
		js.executeScript("window.scrollTo(0, '" + height + "')");
	}

	public void scrollIntoView(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void drawBorder(WebElement element) {
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}

	public void flash(WebElement element) {
		String bgcolor = element.getCssValue("backgroundColor");// white
		for (int i = 0; i < 10; i++) {
			changeColor("rgb(0,200,0)", element);// Green
			changeColor(bgcolor, element);// While
		}
	}

	private void changeColor(String color, WebElement element) {
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);// G-->W--G--W---G--W
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
		}
	}

}
