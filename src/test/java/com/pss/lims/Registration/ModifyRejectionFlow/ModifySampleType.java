package com.pss.lims.Registration.ModifyRejectionFlow;

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
import com.pss.lims.login.RegistrationLoginDetails;
import com.pss.lims.util.HeaderFooterPageEvent;
import com.pss.lims.util.Helper;
import com.pss.lims.util.Utilities;

import org.testng.Assert;

public class ModifySampleType extends RegistrationLoginDetails {
	@Test
	public void modifyExtSampleType() throws Exception {

		document = new Document();
		Font font = new Font(Font.FontFamily.TIMES_ROMAN);
		output = System.getProperty("user.dir") + "\\" + "/TestReport/" + "ModifySampleType" + (new Random().nextInt())
				+ ".pdf";
		fos = new FileOutputStream(output);
		writer = PdfWriter.getInstance(document, fos);
		writer.setStrictImageSequence(true);
		writer.open();
		HeaderFooterPageEvent event = new HeaderFooterPageEvent("Modify Sample Type", "LIMS-SM-074", "Pass");
		writer.setPageEvent(event);
		document.open();
		Thread.sleep(1000);
		driver.findElement(By.name("loginUserName")).sendKeys(properties.getProperty("Initiator_Login"));
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
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='sampleTypeRegnPage.do'")));
		JavascriptExecutor jse1 = (JavascriptExecutor) driver;
		WebElement element1 = driver.findElement(By.cssSelector("a[href='sampleTypeRegnPage.do'"));
		jse1.executeScript("arguments[0].scrollIntoView(true);", element1);
		Thread.sleep(1000);
		jse1.executeScript("arguments[0].click();", element1);
		document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Click on SAMPLE TYPE", sno, false);
		Thread.sleep(4000);
		methodToModifiyExtSampleType();
		document.close();
		writer.close();
		Desktop desktop = Desktop.getDesktop();
		File file = new File(output);
		desktop.open(file);

	}

	private void methodToModifiyExtSampleType() throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, 200);
		sno++;
		driver.findElement(By.id("modifySampleTypeFormAction")).click();
		document = Utilities.getScreenShotAndAddInLogDoc(driver, document,
				"Click on Modification of Existing Sample Types", sno, false);
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.cssSelector("#ExistedSampleTypeTable > div > div.jtable-busy-message[style='display: none;']")));
		Thread.sleep(3000);
		int count1 = 0;
		boolean isRecordSelected1 = false;
		String sampleTypeName = properties.getProperty("SampleType_Name");
		isRecordSelected1 = selectExtSampleTypeRecord(count1, isRecordSelected1, sampleTypeName);
		if (isRecordSelected1) {
			sno++;
			document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Select a Record and View", sno, false);
			Thread.sleep(5000);
			sno++;
			driver.findElement(By.cssSelector(
					"body > div:nth-child(31) > div.ui-dialog-titlebar.ui-widget-header.ui-corner-all.ui-helper-clearfix.ui-draggable-handle > button > span.ui-button-icon-primary.ui-icon.ui-icon-closethick"))
					.click();
			document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Click on Close", sno, false);
			Thread.sleep(3000);
			sno++;
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(By.xpath("//*[@id=\"TotalContent\"]/div[3]/ul/li[2]/a"));
			jse.executeScript("arguments[0].scrollIntoView(true);", element);
			Thread.sleep(1000);
			jse.executeScript("arguments[0].click();", element);
			document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Click on Next", sno, false);
			Thread.sleep(3000);
			sno++;
			driver.findElement(By.id("uniquePrefixNameInLims")).clear();
			document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Clear Prefix", sno, false);
			Thread.sleep(5000);
			sno++;
			driver.findElement(By.id("uniquePrefixNameInLims"))
					.sendKeys(properties.getProperty("Modify_SampleType_Prefix"));
			document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Enter Prefix", sno, false);
			Thread.sleep(3000);
			sno++;
			driver.findElement(By.id("selAppFromUserInSampleTypeReg")).click();
			document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Click on Select", sno, false);
			Thread.sleep(3000);
			sno++;
			driver.findElement(By.id("locTreeInCalPmBdm_2_switch")).click();
			Thread.sleep(3000);
			driver.findElement(By.linkText(properties.getProperty("Location_Name"))).click();
			document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Click on Location", sno, false);
			wait.until(ExpectedConditions.presenceOfElementLocated(
					By.cssSelector("#usersTableContainer > div > div.jtable-busy-message[style='display: none;']")));
			Thread.sleep(3000);
			sno++;
			int count = 0;
			boolean isRecordSelected = false;
			String selectingUserSingleApproval = properties.getProperty("LastName");
			isRecordSelected = Helper.selectingSingleApprovalRecord(driver, selectingUserSingleApproval,
					isRecordSelected, count);
			if (isRecordSelected) {
				sno++;
				document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Select a Record", sno, false);
				Thread.sleep(4000);
				JavascriptExecutor jse5 = (JavascriptExecutor) driver;
				WebElement element5 = driver.findElement(By.id("usersSelBtnInLocaBasedUser"));
				jse5.executeScript("arguments[0].click();", element5);
				document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Click on Select", sno, false);
				Thread.sleep(4000);
				JavascriptExecutor js = (JavascriptExecutor) driver;
				WebElement ele = driver.findElement(By.id("modifyCommentsForSampleType"));
				js.executeScript("arguments[0].scrollIntoView(true);", ele);
				sno++;
				driver.findElement(By.id("modifyCommentsForSampleType"))
						.sendKeys(properties.getProperty("Modified_Comments"));
				document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Enter Comments", sno, false);
				Thread.sleep(3000);
				sno++;
				JavascriptExecutor jse1 = (JavascriptExecutor) driver;
				WebElement element1 = driver.findElement(By.xpath("//*[@id=\"TotalContent\"]/div[3]/ul/li[3]/a"));
				jse1.executeScript("arguments[0].scrollIntoView(true);", element1);
				Thread.sleep(1000);
				jse1.executeScript("arguments[0].click();", element1);
				document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Click on Finish", sno, false);
				Thread.sleep(4000);
				sno++;
				driver.findElement(By.id("eSignPwdInWnd")).sendKeys(properties.getProperty("Esign_Password"));
				document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Enter E-Signature Password", sno,
						false);
				Thread.sleep(3000);
				sno++;
				JavascriptExecutor jse7 = (JavascriptExecutor) driver;
				WebElement element7 = driver.findElement(By.id("subBtnInValidateESign"));
				jse7.executeScript("arguments[0].click();", element7);
				document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Click on Submit", sno, false);
				wait.until(ExpectedConditions
						.presenceOfElementLocated(By.xpath(".//*[@id='modal-window']/div/div/div[3]/a")));
				Thread.sleep(3000);
				if (driver.findElement(By.xpath("//*[@id=\"modal-window\"]/div/div/div[2]/center")).isDisplayed()) {
					sno++;
					document = Utilities.getScreenShotAndAddInLogDoc(driver, document, "Click on OK button", sno,
							false);
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
				System.out.println("Approver is not Selected");
				Assert.assertTrue(false);
			}
		} else {
			System.out.println("Sample Type is not Selected");
			Assert.assertTrue(false);

		}
	}

	private boolean selectExtSampleTypeRecord(int count1, boolean isRecordSelected1, String sampleTypeName)
			throws Exception {
		WebElement table = driver.findElement(By.id("ExistedSampleTypeTable"));
		WebElement tableBody = table.findElement(By.tagName("tbody"));
		int perPageNoOfRecordsPresent = tableBody.findElements(By.tagName("tr")).size();
		int totalNoOfRecords = 0;
		int noOfRecordsChecked = 0;
		if (perPageNoOfRecordsPresent > 0) {
			String a = driver.findElement(By.xpath("//*[@id=\"ExistedSampleTypeTable\"]/div/div[4]/div[2]/span"))
					.getText();
			String parts[] = a.split("of");
			try {
				totalNoOfRecords = Integer.parseInt(parts[1].trim());
				System.out.println(totalNoOfRecords);
			} catch (Exception e) {

				System.out.println(e.getMessage());
			}
		}

		if (perPageNoOfRecordsPresent > 0 && (count1 == 0)) {
			if ((totalNoOfRecords > 1) && ((sampleTypeName == null) || ("".equalsIgnoreCase(sampleTypeName)))) {
				sampleTypeName = driver
						.findElement(By.xpath("//*[@id=\"ExistedSampleTypeTable\"]/div/table/tbody/tr/td[3]"))
						.getText();
			} else if ((sampleTypeName == null) || ("".equalsIgnoreCase(sampleTypeName))) {

				sampleTypeName = driver
						.findElement(By.xpath("//*[@id=\"ExistedSampleTypeTable\"]/div/table/tbody/tr/td[3]"))
						.getText();
			}
			++count1;
		}
		if (perPageNoOfRecordsPresent > 0) {
			while (noOfRecordsChecked < totalNoOfRecords) {
				if (totalNoOfRecords > 1) {
					for (int i = 1; i <= perPageNoOfRecordsPresent; i++) {
						String sampleTypeNameSequence = driver
								.findElement(By.xpath(
										"//*[@id=\"ExistedSampleTypeTable\"]/div/table/tbody/tr[" + i + "]/td[3]"))
								.getText();
						if (sampleTypeName.equalsIgnoreCase(sampleTypeNameSequence)) {
							driver.findElement(By.xpath(
									"//*[@id=\"ExistedSampleTypeTable\"]/div/table/tbody/tr[" + i + "]/td[8]/button"))
									.click();
							isRecordSelected1 = true;
							break;
						}
					}
					if (isRecordSelected1) {
						break;
					}
				} else {

					String sampleTypeNameSequence = driver
							.findElement(By.xpath("//*[@id=\"ExistedSampleTypeTable\"]/div/table/tbody/tr/td[3]"))
							.getText();
					if (sampleTypeName.equalsIgnoreCase(sampleTypeNameSequence)) {
						driver.findElement(
								By.xpath("//*[@id=\"ExistedSampleTypeTable\"]/div/table/tbody/tr/td[8]/button"))
								.click();
						isRecordSelected1 = true;
						break;
					}
				}

				noOfRecordsChecked = noOfRecordsChecked + perPageNoOfRecordsPresent;
				if ((!isRecordSelected1) && (noOfRecordsChecked < totalNoOfRecords)) {

					driver.findElement(By.cssSelector(
							"#ExistedSampleTypeTable > div > div.jtable-bottom-panel > div.jtable-left-area > span.jtable-page-list > span.jtable-page-number-next"))
							.click();
					Thread.sleep(4000);
					table = driver.findElement(By.id("ExistedSampleTypeTable"));
					tableBody = table.findElement(By.tagName("tbody"));
					perPageNoOfRecordsPresent = tableBody.findElements(By.tagName("tr")).size();
				}
			}
		}
		return isRecordSelected1;
	}

	@AfterMethod
	public void testIT(ITestResult result)

	{
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility.captureScreenshot(driver, result.getName());
		}

	}
}
