package com.qa.restassured.tesla.stubMapping;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.qa.restassured.Tesla.utilities.TestBase;

public  class  StubMappingforRentalcars extends TestBase {
	@BeforeTest
	public  static void setup() 
	{	
		WireMock.stubFor(get(urlEqualTo("/getcars"))
               //.withHeader("Content-Type", equalTo("application/json;charset=utf-8"))
                .willReturn(
                		aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json;charset=utf-8")
                .withBodyFile("RentalCarSchema.json")));
		System.out.println("File accepted");
	}

}
