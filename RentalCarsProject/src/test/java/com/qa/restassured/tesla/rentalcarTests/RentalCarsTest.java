package com.qa.restassured.tesla.rentalcarTests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.qa.restassured.tesla.stubMapping.StubMappingforRentalcars;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RentalCarsTest extends StubMappingforRentalcars {
	String sHostName ="http://localhost:8088";
	String URI ="/getcars";
	String url =sHostName + URI;
	
	@Test(priority =1)
	public void getRequest_RentalCars() {
		RestAssured.baseURI = url;
		
		Response resp =RestAssured.given().contentType("application/json").get();
		System.out.println("Print response body after converting as string");
		System.out.println(resp.asString());
		System.out.println("printing status code"+resp.statusCode());
//		String responseBody = resp.getBody().asString();
//		System.out.println(responseBody.contains("Tesla");
		System.out.println(resp.jsonPath().getString("Car.make"));
		Assert.assertTrue(resp.jsonPath().getString("Car.make").contains("Tesla"));
		System.out.println("List of tesla cars");
		
	}
	@Test(priority =2)
	public void getRequest_teslaCarsWithBluecolor() {
		RestAssured.baseURI = url;
		String make ="Tesla";
		String color ="Blue";
		Response response =RestAssured.given().contentType("application/json").get();
		List<String> ls = response.jsonPath().getList("Car.make");
		System.out.println(ls);
		int setindex =0;
		for(int i =0; i< ls.size();i++) 
		{
			setindex =i;
			String carColor = response.jsonPath().getString("Car["+setindex +"].metadata.Color");
			if(carColor.equalsIgnoreCase(color)) 
			{
				System.out.println("Printing the car colors that equal to blue  :" +ls.get(i));
				System.out.println("Printing only " +make +"of color "+color);
				Assert.assertEquals(carColor, color);
				String carNotes = response.jsonPath().getString("Car["+setindex+"].metadata.Notes");
				System.out.println("prionting the cars with color = "+ carColor + make +" :" +carNotes);
			}
		}
	}
	
		@Test(priority =3)
		public void getRequest_LowestPricePerDay() 
		{
			RestAssured.baseURI = url;
			Response response =RestAssured.given().contentType("application/json").get();
			List<Float> listOfPerdayRent = response.jsonPath().getList("Car.perdayrent");
			ArrayList<Float> perDayRentPriceList = new ArrayList<>();
			ArrayList<Float> PriceAfterDiscountList = new ArrayList<>();
			int setindex =0;
			for(int i =0; i< listOfPerdayRent.size();i++) 
			{
				Float perdayRentPrice = response.jsonPath().getFloat("Car["+i+"].perdayrent.Price");
				perDayRentPriceList.add(perdayRentPrice);
				//System.out.println("perdayRentPrice :" +perdayRentPrice);
				Float perdayRentDiscountPrice = response.jsonPath().getFloat("Car["+i+"].perdayrent.Discount");
				Float priceAfterDiscount = perdayRentPrice -(perdayRentPrice*perdayRentDiscountPrice/100);
				PriceAfterDiscountList.add(priceAfterDiscount);
			}
			Collections.sort(perDayRentPriceList);
			Collections.sort(PriceAfterDiscountList);
			System.out.println(" printing prices of cars from low to high");
			System.out.println(perDayRentPriceList);
			System.out.println(" printing discounted prices of cars from low to high");
			System.out.println(PriceAfterDiscountList);
			//validating highest discount price
			Assert.assertEquals(PriceAfterDiscountList.get(0).floatValue(),63.0);
		}
		@Test(priority =4)
		public void highestRevenueCarsList() 
		{
			RestAssured.baseURI = url;
			Response response =RestAssured.given().contentType("application/json").get();
			List<Float> ListOfMetrics = response.jsonPath().getList("Car.metrics");
			float highestRevenue = 0 ;
			int setindex =0;
			for(int  i=0; i< ListOfMetrics.size();i++) 
			{
				Float maintananceCost = response.jsonPath().getFloat("Car["+i+"].metrics.yoymaintenancecost");
				Float deprecationCost = response.jsonPath().getFloat("Car["+i+"].metrics.depreciation");
				Float Revenue = maintananceCost+deprecationCost;
				if(Revenue > highestRevenue) {
					highestRevenue = Revenue;
					setindex = i;
				}
			}
			System.out.println("Highest revenue is :" +highestRevenue);
			System.out.println(setindex);
			System.out.println(response.jsonPath().getString("Car["+setindex+"].vin"));
		}
			
	
}
