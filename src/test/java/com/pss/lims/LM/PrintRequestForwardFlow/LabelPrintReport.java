package com.pss.lims.LM.PrintRequestForwardFlow;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

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
import com.pss.lims.LM.LoginDetails.LoginDetails;
import com.pss.lims.util.HeaderFooterPageEvent;
import com.pss.lims.util.Utilities;

public class LabelPrintReport extends LoginDetails {

	@Test
	public void labelPrintReport() throws Exception {

		document = new Document();
		Font font = new Font(Font.FontFamily.TIMES_ROMAN);
		output = System.getProperty("user.dir") + "\\" + "/TestReport/" + "LabelPrintReport" + (new Random().nextInt())
				+ ".pdf";
		fos = new FileOutputStream(output);
		writer = PdfWriter.getInstance(document, fos);
		writer.setStrictImageSequence(true);
		writer.open();
		HeaderFooterPageEvent event = new HeaderFooterPageEvent("Label Print Report", "LIMS-LM-006", "Pass");
		writer.setPageEvent(event);
		document.open();
		Thread.sleep(1000);
		driver.findElement(By.name("loginUserName")).sendKeys(properties.getProperty("Initiator_Login"));
		Thread.sleep(1000);
		driver.findElement(By.name("loginPassword")).sendKeys(properties.getProperty("Password"));
		Thread.sleep(1000);
		Select module = new Select(driver.findElement(By.id("limsModule")));
		Thread.sleep(1000);
		module.selectByVisibleText(properties.getProperty("limsModule"));
		Thread.sleep(1000);
		input = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		driver.findElement(By.xpath("//*[@id=\"loginform\"]/div[7]/input")).click();
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
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='labelPrintReportForm.do'")));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement ele = driver.findElement(By.cssSelector("a[href='labelPrintReportForm.do'"));
		js.executeScript("arguments[0].scrollIntoView(true);", ele);
		Thread.sleep(1000);
		js.executeScript("arguments[0].click();", ele);
		document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Click on Reports", sno, false);
		Thread.sleep(4000);
		methodForLabelPrintReport();
		document.close();
		writer.close();
		Desktop desktop = Desktop.getDesktop();
		File file = new File(output);
		desktop.open(file);

	}

	private void methodForLabelPrintReport() throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, 240);
		sno++;
		Select type = new Select(driver.findElement(By.id("sampTypeInLabelPrint")));
		Thread.sleep(1000);
		type.selectByVisibleText(properties.getProperty("SampleType"));
		document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Select Sample Type", sno, false);
		Thread.sleep(2000);
		sno++;
		Select temp = new Select(driver.findElement(By.id("templateNameInLabelPrintReport")));
		Thread.sleep(1000);
//		temp.selectByVisibleText(properties.getProperty("Template_Name"));
		temp.selectByIndex(1);
		document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Select Label Template", sno, false);
		Thread.sleep(2000);
		sno++;
		driver.findElement(By.id("searchBtnInLabelPrintReportForm")).click();
		document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Click on Search", sno, false);
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.cssSelector("#LabelPrintReportJTable > div > div.jtable-busy-message[style='display: none;']")));
		Thread.sleep(2000);
		int count = 0;
		boolean isRecordSelected = false;
		String name = properties.getProperty("ARNumber");
		isRecordSelected = selectRecord(count, isRecordSelected, name);
		if (isRecordSelected) {
			sno++;
			document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Select a Record", sno, false);
			Thread.sleep(3000);
			sno++;
			driver.findElement(By.id("printReportBtnInLabelPrintReportForm")).click();
			Thread.sleep(15000);
			document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Click on Print Report", sno, false);
			Thread.sleep(4000);
			String mainWindow = driver.getWindowHandle();
			System.out.println("main window is :" + mainWindow);
			Set<String> set = driver.getWindowHandles();
			Iterator<String> itr = set.iterator();
			while (itr.hasNext()) {
				String childWindow = itr.next();
				System.out.println("child window is:" + childWindow);
				if (!mainWindow.equals(childWindow)) {
					driver.switchTo().window(childWindow);
					System.out.println(driver.switchTo().window(childWindow).getTitle());
					Thread.sleep(3000);
					driver.close();
				} else {
					System.out.println("child window is not closed");
				}
			}
			driver.switchTo().window(mainWindow);
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

	private boolean selectRecord(int count, boolean isRecordSelected, String name) throws Exception {
		// TODO Auto-generated method stub
		WebElement table = driver.findElement(By.id("LabelPrintReportJTable"));
		WebElement tableBody = table.findElement(By.tagName("tbody"));
		int perPageNoOfRecordsPresent = tableBody.findElements(By.tagName("tr")).size();
		int totalNoOfRecords = 0;
		int noOfRecordsChecked = 0;
		if (perPageNoOfRecordsPresent > 0) {
			String a = driver.findElement(By.xpath("//*[@id=\"LabelPrintReportJTable\"]/div/div[4]/div[2]/span"))
					.getText();// For
			// Ex:
			// Showing
			// 1-1
			// of
			// 1
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
			if ((totalNoOfRecords > 1) && ((name == null) || ("".equalsIgnoreCase(name)))) {
				name = driver.findElement(By.xpath("//*[@id=\"LabelPrintReportJTable\"]/div/table/tbody/tr[1]/td[5]"))
						.getText();// documentType
			} else if ((name == null) || ("".equalsIgnoreCase(name))) {
				name = driver.findElement(By.xpath("//*[@id=\"LabelPrintReportJTable\"]/div/table/tbody/tr/td[5]"))
						.getText();// document
									// type
			}
			++count;
		}
		if (perPageNoOfRecordsPresent > 0) {
			while (noOfRecordsChecked < totalNoOfRecords) {
				if (totalNoOfRecords > 1) {
					for (int i = 1; i <= perPageNoOfRecordsPresent; i++) {
						String DevNumberSequence = driver
								.findElement(By.xpath(
										"//*[@id=\"LabelPrintReportJTable\"]/div/table/tbody/tr[ " + i + " ]/td[5]"))
								.getText();// documentTypeName
						if (name.equalsIgnoreCase(DevNumberSequence)) {
							driver.findElement(By
									.xpath("//*[@id=\"LabelPrintReportJTable\"]/div/table/tbody/tr[ " + i + " ]/td[5]"))
									.click();
							isRecordSelected = true;
							break;
						}
					}
					if (isRecordSelected) {
						break;
					}
				} else {
					String DevNumberSequence = driver
							.findElement(By.xpath("//*[@id=\"LabelPrintReportJTable\"]/div/table/tbody/tr/td[5]"))
							.getText();
					if (name.equalsIgnoreCase(DevNumberSequence)) {
						driver.findElement(By.xpath("//*[@id=\"LabelPrintReportJTable\"]/div/table/tbody/tr/td[5]"))
								.click();
						isRecordSelected = true;
						break;
					}
				}
				noOfRecordsChecked += perPageNoOfRecordsPresent;
				if ((!isRecordSelected) && (noOfRecordsChecked < totalNoOfRecords)) {
					driver.findElement(By.cssSelector(
							"#LabelPrintReportJTable > div > div.jtable-bottom-panel > div.jtable-left-area > span.jtable-page-list > span.jtable-page-number-next"))
							.click();// next page in Document approve list
					Thread.sleep(4000);
					table = driver.findElement(By.id("LabelPrintReportJTable"));// Document Tree approve
																				// table
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
