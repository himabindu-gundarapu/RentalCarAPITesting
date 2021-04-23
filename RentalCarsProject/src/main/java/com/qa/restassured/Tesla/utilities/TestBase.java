package com.qa.restassured.Tesla.utilities;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;


public class TestBase {
	//public static WireMock wireMock1;
public static  int PORT =8088;
public   String HOST ="localhost";
public static WireMockServer wireMockServer;
//public static CommonUtilities commonutil = new CommonUtilities();

@BeforeSuite
public void startServer() {
	System.out.println("startServer");
	wireMockServer = new WireMockServer(PORT);
	//WireMock.configureFor(PORT);
	//wireMock1 = new WireMock("localhost", 9999);
	WireMock.configureFor(HOST, PORT);
	wireMockServer.start();
	}
@AfterSuite
public void teardown() {
	wireMockServer.stop();
	}
}
