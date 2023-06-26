package com.pss.lims.sm.InternalRejectionFlow;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.pss.lims.ExtentTestNGPkg.Utility;
import com.pss.lims.login.SMLoginDetails;
import com.pss.lims.util.HeaderFooterPageEvent;
import com.pss.lims.util.Utilities;

public class SampleRegistrationApprove extends SMLoginDetails {

	@Test
	public void approveSampleRegistration() throws Exception {

		document = new Document();
		Font font = new Font(Font.FontFamily.TIMES_ROMAN);
		output = System.getProperty("user.dir") + "\\" + "/TestReport/" + "ApproveSampleRegistration"
				+ (new Random().nextInt()) + ".pdf";
		fos = new FileOutputStream(output);
		writer = PdfWriter.getInstance(document, fos);
		writer.setStrictImageSequence(true);
		writer.open();
		HeaderFooterPageEvent event = new HeaderFooterPageEvent("Approve Sample Registration", "LIMS-SM-092", "Pass");
		writer.setPageEvent(event);
		document.open();
		Thread.sleep(1000);
		driver.findElement(By.name("loginUserName")).sendKeys(properties.getProperty("Approver_Login"));
		Thread.sleep(1000);
		driver.findElement(By.name("loginPassword")).sendKeys(properties.getProperty("Password"));
		Thread.sleep(1000);
		Select module = new Select(driver.findElement(By.id("limsModule")));
		Thread.sleep(1000);
		module.selectByVisibleText(properties.getProperty("Lims_Module_Name1"));
		Thread.sleep(1000);
		input = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		driver.findElement(By.xpath("//*[@id='loginform']/div[7]/input")).click();
		im = Image.getInstance(input);
		im.scaleToFit((PageSize.A4.getWidth() - (PageSize.A4.getWidth() / 8)),
				(PageSize.A4.getHeight() - (PageSize.A4.getHeight() / 8)));
		document.add(new Paragraph(sno + "." + "Enter the username, password, Select Module and click on login button"
				+ Utilities.prepareSSNumber(sno), font));
		document.add(im);
		document.add(new Paragraph("                                     "));
		document.add(new Paragraph("                                     "));
		sno++;
		WebDriverWait wait = new WebDriverWait(driver, 240);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='sampleRegAppPageInSample.do'")));
		driver.findElement(By.cssSelector("a[href='sampleRegAppPageInSample.do'")).click();
		document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Click on Sample Registration Approval", sno,
				false);
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector("#limsSampleRegApprovalJtable > div > div.jtable-busy-message[style='display: none;']")));
		Thread.sleep(4000);
		methodToapproveSampleRegistration();
		document.close();
		writer.close();
		Desktop desktop = Desktop.getDesktop();
		File file = new File(output);
		desktop.open(file);

	}

	private void methodToapproveSampleRegistration() throws Exception {

		sno++;
		int count = 0;
		boolean isRecordSelected = false;
		String arNumber = properties.getProperty("AR_Number");
		isRecordSelected = selectRecordForStorageLocation(count, isRecordSelected, arNumber);
		if (isRecordSelected) {
			document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Select a Record", sno, false);
			Thread.sleep(4000);
			sno++;
			driver.findElement(By.id("commentsInViewSampAppGrid"))
					.sendKeys(properties.getProperty("Approval_Comments"));
			document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Enter Comments", sno, false);
			Thread.sleep(2000);
			sno++;
			driver.findElement(By.id("approveBtnInViewSampAppGrid")).click();
			document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Click on Approve", sno, false);
			Thread.sleep(2000);
			sno++;
			driver.findElement(By.id("eSignPwdInWnd")).sendKeys(properties.getProperty("Esign_Password"));
			document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Enter E-Signature Password", sno,
					false);
			Thread.sleep(3000);
			sno++;
			driver.findElement(By.id("subBtnInValidateESign")).click();
			document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Click on Submit", sno, false);
			WebDriverWait wait = new WebDriverWait(driver, 70);
			wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='modal-window']/div/div/div[3]/a")));
			Thread.sleep(3000);
			if (driver.findElement(By.xpath("//*[@id=\"modal-window\"]/div/div/div[2]/center")).isDisplayed()) {
				sno++;
				document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Click on OK button", sno, false);
				driver.findElement(By.xpath(".//*[@id='modal-window']/div/div/div[3]/a")).click();
			}
			Thread.sleep(3000);
			sno++;
			driver.findElement(By.className("username")).click();
			document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Click on User Name", sno, false);
			Thread.sleep(2000);
			sno++;
			driver.findElement(By.cssSelector("a[href='Logout.do']")).click();
			document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Click on Logout", sno, true);

		} else {
			System.out.println("Record is not Selected");
			Assert.assertTrue(false);
		}
	}

	private boolean selectRecordForStorageLocation(int count, boolean isRecordSelected, String arNumber)
			throws Exception {
		// TODO Auto-generated method stub
		WebElement table = driver.findElement(By.id("limsSampleRegApprovalJtable"));
		WebElement tableBody = table.findElement(By.tagName("tbody"));
		int perPageNoOfRecordsPresent = tableBody.findElements(By.tagName("tr")).size();
		int totalNoOfRecords = 0;
		int noOfRecordsChecked = 0;
		if (perPageNoOfRecordsPresent > 0) {
			String a = driver.findElement(By.xpath("//*[@id=\"limsSampleRegApprovalJtable\"]/div/div[4]/div[2]/span"))
					.getText();// For

//			System.out.println("hi:" + a);
			String[] parts = a.split(" of ");
//			System.out.println("parts:" + parts.toString());
			try {
				totalNoOfRecords = Integer.parseInt(parts[1].trim());
				System.out.println(totalNoOfRecords);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		if (perPageNoOfRecordsPresent > 0 && count == 0) {
			// System.out.println("record is selected or not");
			if ((totalNoOfRecords > 1) && ((arNumber == null) || ("".equalsIgnoreCase(arNumber)))) {
//				System.out.println("hi this is ravi");
				JavascriptExecutor jse0 = (JavascriptExecutor)driver;
				WebElement element = driver.findElement(By.xpath(
						"//*[@id=\"limsSampleRegApprovalJtable\"]/div/table/tbody/tr[1]/td[30]"));
				jse0.executeScript("arguments[0].scrollIntoView()", element); 
				arNumber = driver
						.findElement(By.xpath("//*[@id=\"limsSampleRegApprovalJtable\"]/div/table/tbody/tr[1]/td[30]"))
						.getText();// documentType
//				System.out.println("Storage condition is:" + arNumber);
			} else if ((arNumber == null) || ("".equalsIgnoreCase(arNumber))) {
				JavascriptExecutor jse0 = (JavascriptExecutor)driver;
				WebElement element = driver.findElement(By.xpath(
						"//*[@id=\"limsSampleRegApprovalJtable\"]/div/table/tbody/tr[1]/td[30]"));
				jse0.executeScript("arguments[0].scrollIntoView()", element); 
				arNumber = driver
						.findElement(By.xpath("//*[@id=\"limsSampleRegApprovalJtable\"]/div/table/tbody/tr/td[30]"))
						.getText();// document
//				System.out.println("Storage condition is selected" + arNumber); // type
			}
			++count;
		}
		if (perPageNoOfRecordsPresent > 0) {
			while (noOfRecordsChecked < totalNoOfRecords) {
				if (totalNoOfRecords > 1) {
					for (int i = 1; i <= perPageNoOfRecordsPresent; i++) {
						JavascriptExecutor jse0 = (JavascriptExecutor)driver;
						WebElement element = driver.findElement(By.xpath(
								"//*[@id=\"limsSampleRegApprovalJtable\"]/div/table/tbody/tr[ \" + i + \"]/td[30]"));
						jse0.executeScript("arguments[0].scrollIntoView()", element); 
						String arNumberSequence = driver.findElement(By.xpath(
								"//*[@id=\"limsSampleRegApprovalJtable\"]/div/table/tbody/tr[ " + i + " ]/td[30]"))
								.getText();
						if (arNumber.contains(arNumberSequence)) {
							JavascriptExecutor jse1 = (JavascriptExecutor)driver;
							WebElement element1 =driver.findElement(By.xpath("//*[@id=\"limsSampleRegApprovalJtable\"]/div/table/tbody/tr[ "
									+ i + " ]/td[46]/button"));
							jse1.executeScript("arguments[0].scrollIntoView()", element1); 
							
							driver.findElement(By.xpath("//*[@id=\"limsSampleRegApprovalJtable\"]/div/table/tbody/tr[ "
									+ i + " ]/td[46]/button")).click();
							isRecordSelected = true;
							break;
						}
					}
					if (isRecordSelected) {
						break;
					}
				} else {
					JavascriptExecutor jse0 = (JavascriptExecutor)driver;
					WebElement element = driver.findElement(By.xpath(
							"//*[@id=\"limsSampleRegApprovalJtable\"]/div/table/tbody/tr/td[30]"));
					jse0.executeScript("arguments[0].scrollIntoView()", element); 
					String arNumberSequence = driver
							.findElement(By.xpath("//*[@id=\"limsSampleRegApprovalJtable\"]/div/table/tbody/tr/td[30]"))
							.getText();
					if (arNumber.contains(arNumberSequence)) {
						
						JavascriptExecutor jse1 = (JavascriptExecutor)driver;
						WebElement element1 =driver.findElement(By.xpath("//*[@id=\"limsSampleRegApprovalJtable\"]/div/table/tbody/tr/td[46]/button"));
						jse1.executeScript("arguments[0].scrollIntoView()", element1); 
						
						driver.findElement(By.xpath("//*[@id=\"limsSampleRegApprovalJtable\"]/div/table/tbody/tr/td[46]/button")).click();
						
						
						isRecordSelected = true;
						break;
					}
				}
				noOfRecordsChecked += perPageNoOfRecordsPresent;
				if ((!isRecordSelected) && (noOfRecordsChecked < totalNoOfRecords)) {
					JavascriptExecutor jse0 = (JavascriptExecutor)driver;
					WebElement element = driver.findElement(By.cssSelector(
							"#limsSampleRegApprovalJtable > div > div.jtable-bottom-panel > div.jtable-left-area > span.jtable-page-list > span.jtable-page-number-next"));
					jse0.executeScript("arguments[0].scrollIntoView()", element); 
					driver.findElement(By.cssSelector(
							"#limsSampleRegApprovalJtable > div > div.jtable-bottom-panel > div.jtable-left-area > span.jtable-page-list > span.jtable-page-number-next"))
							.click();// next page in Document approve list
					Thread.sleep(4000);
					WebDriverWait wait = new WebDriverWait(driver, 90);
					wait.until(ExpectedConditions.presenceOfElementLocated(By
				.cssSelector("#limsSampleRegApprovalJtable > div > div.jtable-busy-message[style='display: none;']")));
				Thread.sleep(4000);
					table = driver.findElement(By.id("limsSampleRegApprovalJtable"));// Document Tree approve table
					tableBody = table.findElement(By.tagName("tbody"));
					perPageNoOfRecordsPresent = tableBody.findElements(By.tagName("tr")).size();
				}
			}
		}

		return isRecordSelected;
	}
	@AfterMethod
	public void testIT(ITestResult result)

	{
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility.captureScreenshot(driver, result.getName());
		}

	}
}
