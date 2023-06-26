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
import com.pss.lims.util.Helper;
import com.pss.lims.util.Utilities;

public class COAApprove extends SMLoginDetails {

	@Test
	public void approveCOA() throws Exception {

		document = new Document();
		Font font = new Font(Font.FontFamily.TIMES_ROMAN);
		output = System.getProperty("user.dir") + "\\" + "/TestReport/" + "ApproveCOA" + (new Random().nextInt())
				+ ".pdf";
		fos = new FileOutputStream(output);
		writer = PdfWriter.getInstance(document, fos);
		writer.setStrictImageSequence(true);
		writer.open();
		HeaderFooterPageEvent event = new HeaderFooterPageEvent("Approve COA", "LIMS-SM-100", "Pass");
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
		Thread.sleep(7000);
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
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='coaAppPageInSample.do']")));
		JavascriptExecutor jse1 = (JavascriptExecutor) driver;
		WebElement element1 = driver.findElement(By.cssSelector("a[href='coaAppPageInSample.do']"));
		jse1.executeScript("arguments[0].scrollIntoView(true);", element1);
		Thread.sleep(2000);
		jse1.executeScript("arguments[0].click();", element1);
		document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Click on COA Approval ", sno, false);
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.cssSelector("#sampleResultApprovalJtable > div > div.jtable-busy-message[style='display: none;']")));
		Thread.sleep(10000);
		methodToapproveCOA();
		document.close();
		writer.close();
		Desktop desktop = Desktop.getDesktop();
		File file = new File(output);
		desktop.open(file);

	}

	private void methodToapproveCOA() throws Exception {

		int count = 0;
		boolean isRecordSelected = false;
		String aRNumber = properties.getProperty("AR_Number");
		isRecordSelected = selectRecordForCOAApprove(count, isRecordSelected, aRNumber);
		if (isRecordSelected) {
			sno++;
			document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Select a Record", sno, false);
			Thread.sleep(10000);
			sno++;
			driver.findElement(By.id("viewResultBtnInSamplResApprv")).click();
			document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Click on View Result", sno, false);
			Thread.sleep(3000);
			sno++;
			driver.findElement(By.xpath("//*[starts-with(@id, \"forward\")]")).click();
			document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Click on Forward", sno, false);
			Thread.sleep(3000);
			sno++;
			driver.findElement(By.id("commemtsInSampleResultForApproval"))
					.sendKeys(properties.getProperty("Approval_Comments"));
			document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Enter Comments", sno, false);
			Thread.sleep(2000);
			sno++;
			driver.findElement(By.id("submitBtnInCoaApproval")).click();
			document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Click on Submit", sno, false);
			Thread.sleep(4000);
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

	private boolean selectRecordForCOAApprove(int count, boolean isRecordSelected, String aRNumber)
			throws Exception {
		// TODO Auto-generated method stub
		WebElement table = driver.findElement(By.id("sampleResultApprovalJtable"));
		WebElement tableBody = table.findElement(By.tagName("tbody"));
		int perPageNoOfRecordsPresent = tableBody.findElements(By.tagName("tr")).size();
		int totalNoOfRecords = 0;
		int noOfRecordsChecked = 0;
		if (perPageNoOfRecordsPresent > 0) {
			String a = driver.findElement(By.xpath("//*[@id=\"sampleResultApprovalJtable\"]/div/div[4]/div[2]/span"))
					.getText();// For
			
			String[] parts = a.split(" of ");
			try {
				totalNoOfRecords = Integer.parseInt(parts[1].trim());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		if (perPageNoOfRecordsPresent > 0 && count == 0) {
			if ((totalNoOfRecords > 1) && ((aRNumber == null) || ("".equalsIgnoreCase(aRNumber)))) {
				Helper.scrollElement(driver, By.xpath("//*[@id=\"sampleResultApprovalJtable\"]/div/table/tbody/tr[1]/td[4]"));
				aRNumber = driver.findElement(By.xpath("//*[@id=\"sampleResultApprovalJtable\"]/div/table/tbody/tr[1]/td[4]")).getText();
			} else if ((aRNumber == null) || ("".equalsIgnoreCase(aRNumber))) {
				Helper.scrollElement(driver, By.xpath("//*[@id=\"sampleResultApprovalJtable\"]/div/table/tbody/tr/td[4]"));
				aRNumber = driver.findElement(By.xpath("//*[@id=\"sampleResultApprovalJtable\"]/div/table/tbody/tr/td[4]")).getText();// document
				
			}
			++count;
		}
		if (perPageNoOfRecordsPresent > 0) {
			while (noOfRecordsChecked < totalNoOfRecords) {
				if (totalNoOfRecords > 1) {
					for (int i = 1; i <= perPageNoOfRecordsPresent; i++) {
						JavascriptExecutor js0 = (JavascriptExecutor)driver;
						WebElement element20 = driver.findElement(By.xpath("//*[@id=\"sampleResultApprovalJtable\"]/div/table/tbody/tr[ " + i + " ]/td[4]"));
						js0.executeScript("arguments[0].scrollIntoView();", element20);
						Thread.sleep(2000);
						String aRNumberSequence = driver.findElement(By.xpath("//*[@id=\"sampleResultApprovalJtable\"]/div/table/tbody/tr[ " + i + " ]/td[4]"))
								.getText();
						if (aRNumber.equalsIgnoreCase(aRNumberSequence)) {
						 
							driver.findElement(By.xpath(
									"//*[@id=\"sampleResultApprovalJtable\"]/div/table/tbody/tr[ " + i + " ]/td[4]"))
									.click();
							isRecordSelected = true;
							break;
						}
					}
					if (isRecordSelected) {
						break;
					}
				} else {
					JavascriptExecutor js0 = (JavascriptExecutor)driver;
					WebElement element20 = driver.findElement(By.xpath("//*[@id=\"sampleResultApprovalJtable\"]/div/table/tbody/tr/td[4]"));
					js0.executeScript("arguments[0].scrollIntoView();", element20);
					Thread.sleep(2000);
					String aRNumberSequence = driver
							.findElement(By.xpath("//*[@id=\"sampleResultApprovalJtable\"]/div/table/tbody/tr/td[4]"))
							.getText();
					if (aRNumber.equalsIgnoreCase(aRNumberSequence)) {
						driver.findElement(By.xpath("//*[@id=\"sampleResultApprovalJtable\"]/div/table/tbody/tr/td[4]"))
								.click();
						isRecordSelected = true;
						break;
					}
				}
				noOfRecordsChecked += perPageNoOfRecordsPresent;
				if ((!isRecordSelected) && (noOfRecordsChecked < totalNoOfRecords)) {
					JavascriptExecutor js0 = (JavascriptExecutor)driver;
					WebElement element20 = driver.findElement(By.cssSelector("#sampleResultApprovalJtable > div > div.jtable-bottom-panel > div.jtable-left-area > span.jtable-page-list > span.jtable-page-number-next"));
					js0.executeScript("arguments[0].scrollIntoView();", element20);
					Thread.sleep(3000);
					driver.findElement(By.cssSelector(
							"#sampleResultApprovalJtable > div > div.jtable-bottom-panel > div.jtable-left-area > span.jtable-page-list > span.jtable-page-number-next"))
							.click();// next page in Document approve list
					Thread.sleep(5000);
					WebDriverWait wait = new WebDriverWait(driver, 240);
					wait.until(ExpectedConditions.presenceOfElementLocated(
							By.cssSelector("#sampleResultApprovalJtable > div > div.jtable-busy-message[style='display: none;']")));
					Thread.sleep(5000);
					table = driver.findElement(By.id("sampleResultApprovalJtable"));// Document Tree approve table
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
