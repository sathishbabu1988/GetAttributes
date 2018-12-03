package fetchAttributes;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class GetAttributes 
{	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, InterruptedException 
	{
		int repeat;
		ProcessAttributes object = new ProcessAttributes();
		object.browser_initiate();
		do
		{
			object.fetchProcess();
			System.out.println("Do you wish to continue?, Enter 1 if Yes & 2 if No" );
			Scanner option = new Scanner(System.in);
			repeat = option.nextInt();
		}while(repeat == 1);
		System.out.println("Process ENDS....");
		object.driver.quit();
	}
}


class ProcessAttributes
{
	WebDriver driver;
	public void browser_initiate()
	{
		System.setProperty("webdriver.chrome.driver","drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--no-default-browser-check");
		options.addArguments("--ignore-certificate-errors");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		//driver.manage().window().setPosition(new Point(-2000, 0));
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}

	@SuppressWarnings("resource")
	public void fetchProcess() throws IOException, InterruptedException
	{
		int domain,iterate =0;
		WebElement scroll = null;
		System.out.println("Enter 1 for Prod & 2 for Gamma");
		do
		{
			Scanner option = new Scanner(System.in);
			domain = option.nextInt();
			if(domain == 1)
			{
				System.out.println("Please provide PROD Legacy AddressId Here :");
			}
			else if(domain == 2)
			{
				System.out.println("Provide the GAMMA Legacy AddressId Here :");
			}
			else
			{
				System.out.println("Either Provide 1 (or) 2");
			}
		}while(domain>2);
		
		Scanner input = new Scanner(System.in);
		String addressid = input.next();
		int length = addressid.length();
		if (length > 20)
		{
		System.out.println("Entered Address id needs to be Decrypted, So Please wait.....");
		driver.navigate().to("https://aas-bsf-explorer-na-prod-iad.iad.proxy.amazon.com/cgi/bsf.cgi?service=AddressAuthorityService&path=%2Fbsf%2F&host=aas-next-na-gamma-1aa-3df15e0b.us-east-1.amazon.com&port=5001&timeout=10&method=GetDestinationInfo&app=pasture&params=%27*CodigoProtocol*%27%3D%3E%221.1%22%2C%0D%0A%27destinationInput%27%3D%3E{%0D%0A%27*className*%27%3D%3E%27DestinationTypes.DestinationRequest%27%2C+%0D%0A%27*classHierarchy*%27%3D%3E[%27DestinationTypes.DestinationRequest%27%2C+%27java.lang.Object%27]%2C%0D%0A%27marketplaceId%27%3D%3E%221%22%2C%0D%0A%27destinationAddressId%27%3D%3E%27PHE34MNNQVFZDFNAI25EG12LOQD7GLLIBA2WBILLG7DQOLPPXTQ2EIA2OXGT6KB4%27%2C%0D%0A%27destinationType%27%3D%3E%27Home%27%2C%0D%0A%27preferredDeliveryLocationEligibleFlag%27%3D%3E%27true%27%0D%0A}%0D%0A&protocol_version=2");
		String encrytextarea = driver.findElement(By.xpath("//*[@id='params']")).getText();
		driver.findElement(By.xpath("//*[@id='params']")).clear();
		String encryreplace = encrytextarea.replaceFirst("PHE34MNNQVFZDFNAI25EG12LOQD7GLLIBA2WBILLG7DQOLPPXTQ2EIA2OXGT6KB4", addressid);
		driver.findElement(By.xpath("//*[@id='params']")).sendKeys(encryreplace);
		driver.findElement(By.xpath("//*[@id='inputForm']/table/tbody/tr[2]/td[3]/input")).click();
		driver.findElement(By.xpath("//*[@id='data7']/div/b")).click();
		driver.findElement(By.xpath("//*[@id='data8']/div[1]/b")).click();
		driver.findElement(By.xpath("//*[@id='data10']/div[1]/b")).click();		
		driver.findElement(By.xpath("//*[@id='data12']/div[1]/b")).click();
		driver.findElement(By.xpath("/html/body/span[3]/span/span[1]/span[1]/span[1]/div/b")).click();
		driver.findElement(By.xpath("/html/body/span[3]/span/span[1]/span[1]/span[1]/span/div[1]/b")).click();
		driver.findElement(By.xpath("//*[@id='data13']/div[1]/b")).click();
		WebElement encryspan = driver.findElement(By.id("data7"));
		List<WebElement> encrytable = encryspan.findElements(By.tagName("td"));
		int encrysize = encrytable.size();
		for(int e=0;e<encrysize;e++)
			{
				if(encrytable.get(e).getText().contains("LegacyAddressId ="))
				{
					String decryaddre = encrytable.get(e).getText();
					String split = decryaddre.substring(decryaddre.indexOf("=")+1,decryaddre.indexOf("(")).trim();
					addressid = split;
					System.out.println("The Decrypted Address id is :" +addressid);
				}
			}	
		if(domain ==1)
		{
			driver.get("https://aas-bsf-explorer-na-prod-iad.iad.proxy.amazon.com/cgi/bsf.cgi?service=AddressAuthorityService&path=%2Fbsf%2F&host=address-authority-us-prod.amazon.com&port=5001&timeout=100&method=GetAttributes&app=pasture&params=%27*CodigoProtocol*%27%3D%3E%221.1%22%2C%0D%0A%27addressId%27%3D%3E%27160538824203%27%2C%0D%0A%27attributeSource%27%3D%3E%27CUSTOMER%27&protocol_version=2");
		}
		else
		{
			driver.get("https://aas-bsf-explorer-na-prod-iad.iad.proxy.amazon.com/cgi/bsf.cgi?app=pasture&host=aas-next-us-gamma.iad.amazon.com&method=GetAttributes&params=%27*CodigoProtocol*%27%3D%3E%271.1%27%2C%0D%0A%27addressId%27%3D%3E%27160538824203%27&path=%2Fbsf%2F&port=5001&protocol_version=1&requestlogs=on&service=AddressAuthorityService&timeout=100");
		}
			String textarea = driver.findElement(By.xpath("//*[@id='params']")).getText();
			String legacyaddress = textarea.replaceFirst("160538824203", addressid);
			driver.findElement(By.id("params")).clear();
			driver.findElement(By.id("params")).sendKeys(legacyaddress);
		}
		else
		{
			if(domain ==1)
			{
				driver.get("https://aas-bsf-explorer-na-prod-iad.iad.proxy.amazon.com/cgi/bsf.cgi?service=AddressAuthorityService&path=%2Fbsf%2F&host=address-authority-us-prod.amazon.com&port=5001&timeout=100&method=GetAttributes&app=pasture&params=%27*CodigoProtocol*%27%3D%3E%221.1%22%2C%0D%0A%27addressId%27%3D%3E%27160538824203%27%2C%0D%0A%27attributeSource%27%3D%3E%27CUSTOMER%27&protocol_version=2");
			}
			else
			{
				driver.get("https://aas-bsf-explorer-na-prod-iad.iad.proxy.amazon.com/cgi/bsf.cgi?app=pasture&host=aas-next-us-gamma.iad.amazon.com&method=GetAttributes&params=%27*CodigoProtocol*%27%3D%3E%271.1%27%2C%0D%0A%27addressId%27%3D%3E%27160538824203%27&path=%2Fbsf%2F&port=5001&protocol_version=1&requestlogs=on&service=AddressAuthorityService&timeout=100");
			}
		String textarea = driver.findElement(By.xpath("//*[@id='params']")).getText();
		String legacyaddress = textarea.replaceFirst("160538824203", addressid);
		driver.findElement(By.id("params")).clear();
		driver.findElement(By.id("params")).sendKeys(legacyaddress);
		}
		driver.findElement(By.xpath("//*[@id='inputForm']/table/tbody/tr[2]/td[3]/input")).click();
		System.out.println("Fetching the Attributes......");
		System.out.println("---------------------------------------------------------------------------------");
	
		WebElement valuespan = driver.findElement(By.xpath("//*[@id='data4']"));
		List<WebElement> valuespandiv = valuespan.findElements(By.tagName("div"));
		int spansize = valuespandiv.size();
		for(int i=0;i<spansize;i++)
		{
			String Flag = valuespandiv.get(i).getText();
			if(Flag.contains("*value*"))
			{
				valuespandiv.get(i).click();	
			}
		}
		
		WebElement delinfor = driver.findElement(By.xpath("//*[@id='data5']"));
		List<WebElement> delinfolis = delinfor.findElements(By.tagName("div"));
		int spansize1 = delinfolis.size();
		for(int i=0;i<spansize1;i++)
		{
			String Flag = delinfolis.get(i).getText();
			if(Flag.contains("DeliveryInformation"))
			{
				scroll = delinfolis.get(i);
				delinfolis.get(i).click();	
			}
		}
		
		List<WebElement> spanlist = delinfor.findElements(By.tagName("span"));
		int spansize11 = spanlist.size();
		for(int i=0;i<spansize11;i++)
		{
			String Flag = spanlist.get(i).getText();
			if((Flag.contains("+AccessBarrierInformation"))||(Flag.contains("+BusinessHours"))||(Flag.contains("+DefaultPreferredDeliveryLocations")))
			{
				WebElement Accspan = spanlist.get(i);
				List<WebElement> table = Accspan.findElements(By.tagName("td"));
				int tablesize = table.size();
				for (int a=0;a<tablesize;a++)
				{
					String cellname = table.get(a).getText();
					if(cellname.contains("AddressInstructions")||cellname.contains("DeliveryHint")||cellname.contains("GateCode"))
					{
						System.out.println(cellname);
					}
				}
				List<WebElement> clickacc = Accspan.findElements(By.tagName("div"));
				int clickaccsize = clickacc.size();
				for(int j=0;j<clickaccsize;j++)
				{
					String click = clickacc.get(j).getText();
					if(click.contains("AccessBarrierInformation"))
					{
						clickacc.get(j).click();
					}
					if(click.contains("BusinessHours"))
					{
						clickacc.get(j).click();
					}
					if(click.contains("DefaultPreferredDeliveryLocations"))
					{
						clickacc.get(j).click();
					}
				}
				List<WebElement> physic = Accspan.findElements(By.tagName("span"));
				int physicsize = physic.size();
				for(int k=0;k<physicsize;k++)
				{
					String Flag1 = physic.get(k).getText();
					if(Flag1.contains("+PhysicalKeyAccess"))
					{
						WebElement codes = physic.get(k);
						List<WebElement> getcodes = codes.findElements(By.tagName("div"));
						int codesize = getcodes.size();
						for(int l=0;l<codesize;l++)
						{
							String code = getcodes.get(l).getText();
							if(code.contains("PhysicalKeyAccess")||code.contains("AccessCodes")||code.contains("CallBoxNumbers"))
							{
								getcodes.get(l).click();
							}
						}
						
						List<WebElement> truecheck = codes.findElements(By.tagName("span"));
						int truechecksize = truecheck.size();
						for (int e=0;e<truechecksize;e++)
						{
							//System.out.println(allcodes.get(d).getText());
							if(truecheck.get(e).getText().contains("+*classHierarchy*"))
							{
								WebElement trueconf = truecheck.get(e);
								List<WebElement> trueconflist = trueconf.findElements(By.tagName("td"));
								int listsize = trueconflist.size();
								for(int f=0;f<listsize;f++)
								{
									if(trueconflist.get(f).getText().contains("Value ="))
									{
										System.out.println("Physical"+trueconflist.get(f).getText());
									}
								}									
							}								
						}
						
						List<WebElement> allcodes = codes.findElements(By.tagName("span"));
						int allcodesize = allcodes.size();
						for (int d=0;d<allcodesize;d++)
						{
							if(allcodes.get(d).getText().contains("AccessCodes[")||allcodes.get(d).getText().contains("CallBoxNumbers["))
							{
								System.out.println(allcodes.get(d).getText());
							}
								
						}						
					}
				if(Flag1.contains("+businessHoursMap"))
				{
					WebElement busshour1 = physic.get(k);
					List<WebElement> busshour2 = busshour1.findElements(By.tagName("div"));
					for (int kk=0;kk<busshour2.size();kk++)
					{
						if(busshour2.get(kk).getText().contains("+businessHoursMap"))
						{
							busshour2.get(kk).click();
						}

					}
					List<WebElement> busshour3 = busshour1.findElements(By.tagName("span"));
					for(int ll=0;ll<busshour3.size();ll++)
					{
						String busshour4 = busshour3.get(ll).getText();
						if(busshour4.contains("+SAT"))
						{
							WebElement busshour5 = busshour3.get(ll);
							List<WebElement> busshour6 = busshour5.findElements(By.tagName("div"));
							for (int mm=0;mm<busshour6.size();mm++)
							{
								String busshour7 = busshour6.get(mm).getText();
								if((busshour7.contains("+SAT"))||(busshour7.contains("+SUN")))
								{
									busshour6.get(mm).click();
								}
							}
							List<WebElement> busshour8 = busshour5.findElements(By.tagName("span"));
							for (int nn=0;nn<busshour8.size();nn++)
							{
								if(busshour8.get(nn).getText().contains("*classHierarchy* : vector[2]"))
								{
									WebElement busshour9 = busshour8.get(nn);
									List<WebElement> busshour10 = busshour9.findElements(By.tagName("td"));
									for (int oo=0;oo<busshour10.size();oo++)
									{
										if(busshour10.get(oo).getText().contains("isOpen = "))
											{
												if(iterate==0)
												{
													System.out.println("SATURDAY =>" + busshour10.get(oo).getText());
													iterate++;
												}
												else
												{
													System.out.println("SUNDAY =>" +busshour10.get(oo).getText());
												}
											}	
									}
								}
							}
						}
								
					}
					
				}					
				if(Flag1.contains("+Locations : vector[1]"))
					{
						WebElement safeloc1 = physic.get(k);
						List<WebElement> safeloc2 = safeloc1.findElements(By.tagName("div"));
						for (int ff=0;ff<safeloc2.size();ff++)
						{
							String safeloc3 = safeloc2.get(ff).getText();
							if(safeloc3.contains("Locations : vector[1]"))
									{
										safeloc2.get(ff).click();
									}
						}
						List<WebElement> safeloc4 = safeloc1.findElements(By.tagName("span"));
						for (int gg=0;gg<safeloc4.size();gg++)
						{
							String safeloc5 = safeloc4.get(gg).getText();
							if(safeloc5.contains("Locations[0]"))
							{
								WebElement safeloc6 = safeloc4.get(gg);
								List<WebElement> safeloc7 = safeloc6.findElements(By.tagName("div"));
								for (int hh=0;hh<safeloc7.size();hh++)
								{
									String safeloc8 = safeloc7.get(hh).getText();
									if(safeloc8.contains("Locations[0]"))
									{
										safeloc7.get(hh).click();
									}
								}
								List<WebElement> safeloc9 = safeloc6.findElements(By.tagName("span"));
								for (int ii=0;ii<safeloc9.size();ii++)
								{
									String safeloc10 = safeloc9.get(ii).getText();
									if (safeloc10.contains("SafePlaceLocation = "))
									{
										WebElement safeloc11 = safeloc9.get(ii);
										List<WebElement> safeloc12 = safeloc11.findElements(By.tagName("td"));
										for (int jj=0;jj<safeloc12.size();jj++)
										{
											if(safeloc12.get(jj).getText().contains("SafePlaceLocation = "))
											{
												System.out.println(safeloc12.get(jj).getText());
											}
										}
									}
								}
							}
						}
					}
				}
				
			}
		}
		
		System.out.println("---------------------------------------------------------------------------------");
		System.out.println("\n");
		
		//Scrolling through the page until bottom
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();",scroll);
		screenshot();
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		screenshot();	
	}
	
	public void screenshot() throws IOException, InterruptedException
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
	    Date date = new Date();
	    File scr = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    FileUtils.copyFile(scr, new File("screenshots/Screenshot-"+dateFormat.format(date)+".png"));
		Thread.sleep(1000);
	}

}


