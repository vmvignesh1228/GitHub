package org.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFEvaluationWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.google.common.io.FileBackedOutputStream;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	
	public static WebDriver driver;



	public static WebDriver browserLaunch(String username) {
		if(username.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			 driver= new ChromeDriver();
		}
		else if(username.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			 driver= new FirefoxDriver();
		}
		else if(username.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			 driver= new EdgeDriver();
		}
		else {
			WebDriverManager.chromedriver().setup();
			 driver= new ChromeDriver();
			
		}
		return driver;
		
	}
	
	public static void urlLaunch(String url) {
		driver.get(url);
		driver.manage().window().maximize();

	}
	
	public static void implicitWait(long sec) {
		driver.manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);

	}
	
	
	public static void sendKeys(WebElement e,String value) {
		e.sendKeys(value);
	}
	
	public static void click(WebElement e) {
		e.click();

	}
	
	public static  String getTitle() {
		String title = driver.getTitle();
		return title;
	}
	public static void quit() {
		driver.quit();
	}
	
	public static void moveToElement(WebElement e) {
		Actions a = new Actions(driver);
		a.moveToElement(e).perform();

	}
	
	
	public static void selectByIndex(WebElement e,int index) {
		Select s =new Select(e); 
		s.selectByIndex(index);
	}
	
	
	public static void screenShot(String filename) throws IOException {
	TakesScreenshot tk =(TakesScreenshot) driver;
	File src = tk.getScreenshotAs(OutputType.FILE);
	File des = new File
(System.getProperty("user.dir")+"\\src\\test\\resources\\Screenshot\\"+filename+"_"+System.currentTimeMillis()+".png");
	FileUtils.copyFile(src, des);

	}
	
	
	public static void jsSendKeys(WebElement e,String value) {
		JavascriptExecutor js= (JavascriptExecutor) driver;
		js.executeScript("argments[0].setAttribute('value','"+value+"')", e);

	}
	
	
	public static void Alertsaccept() {
		Alert alert = driver.switchTo().alert();
		alert.accept();

	}
	public static void Alertsdismiss() {
		Alert alert1 = driver.switchTo().alert();
		 alert1.dismiss();
	}
	
	
	public static void Thread(long secs) throws InterruptedException {
		Thread.sleep(secs);
		
	}
	
	public static void jsScrollUp(WebElement e) {
		JavascriptExecutor js= (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(false)", e);
	}
	
	public static void jsScrollDown(WebElement e) {
		JavascriptExecutor js= (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true)", e);
	}
	
	
	public static String gettext(WebElement e) {
		String text = e.getText();
		return text;

	}
	
	public static void contextClick(WebElement e) {
		Actions a = new Actions(driver);
		a.contextClick(e).perform();
	}
	
	
	public static void doubleclick(WebElement e) {
		Actions a = new Actions(driver);
		a.doubleClick(e).perform();
	}
	
	public static void windowhandling() {
		String window6 = driver.getWindowHandle();
		Set<String> windows6 = driver.getWindowHandles();
		for(String win6:windows6) {
			if(!window6.equals(win6)) {
				driver.switchTo().window(win6);
			}
		}

	}
	
	public static void SelectByValue(WebElement e,String value) {
		Select s = new Select(e);
		s.selectByValue(value);
	}

	public static void selectByVisibleText(WebElement e,String value) {
		Select s = new Select(e);
		s.selectByVisibleText(value);

	}
	
	public static String getAttribute(WebElement e,String attributeName) {
	return	e.getAttribute(attributeName);
		

	}
	
	
	public static String excelRead(String filename,String sheetname,int row,int cell) throws IOException {
		File loc = new File
				(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\"+filename+".xlsx");
		FileInputStream fs = new FileInputStream(loc);
		Workbook w =new XSSFWorkbook(fs);
		Sheet s = w.getSheet(sheetname);
		Row r = s.getRow(row);
		Cell c = r.getCell(cell);
		
		int type = c.getCellType();
		
		//type 1-----string
		//type 0-----number,Date
		String value="";
		if(type==1) {
			value = c.getStringCellValue();
		}
		else {
			if(DateUtil.isCellDateFormatted(c)) {
				Date date = c.getDateCellValue();
				SimpleDateFormat sf = new SimpleDateFormat("dd-mm-yyyy");
				 value = sf.format(date);
			}
			else {
				double db = c.getNumericCellValue();
				long num=(long) db;
				 value = String.valueOf(num);
			}
		}
		return value;
	}
	
	
	public static  void excelUbdate(String filename,String sheetname,int row,int cell,String value) throws IOException {
		File loc = new File
				(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\"+filename+".xlsx");
		FileInputStream fs = new FileInputStream(loc);
		Workbook w =new XSSFWorkbook(fs);
		Sheet s = w.getSheet(sheetname);
		Row r = s.getRow(row);
		 if (r == null) {
		        r = s.createRow(row);
		    }
		Cell c = r.getCell(cell);
		if (c == null) {
	        c = r.createCell(cell);
	    }
		c.setCellValue(value);
		
		
		FileOutputStream ot = new FileOutputStream(loc);
		w.write(ot);
	
	}
	
	 // Get Current URL
    public static String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
