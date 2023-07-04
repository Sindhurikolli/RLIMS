package com.pss.lims.sm.InternalForwardFlow.MultiTest;

import org.testng.annotations.Test;

import com.pss.lims.login.SMLoginDetails;
import com.pss.lims.sm.InternalForwardFlow.MultiTestSelectionInSampleRegistration;
import com.pss.lims.sm.InternalForwardFlow.SampleRegistrationWithMultiProducts;

public class LoadSampleReg extends SMLoginDetails{
	@Test
	
    public void methodToCreateMultiSamplReg( ) throws Throwable {
        Thread.sleep(1000);

        String NoOfRuns = properties.getProperty("NoOfRuns");
        int rows = Integer.parseInt(NoOfRuns.trim());
        System.out.println(rows);
        for(int i=1; i<=rows; i++ ) {
//        MultiTestSelectionInSampleRegistration sampleReg = new MultiTestSelectionInSampleRegistration();
//        sampleReg.createSampleRegistration(); 
        SampleRegistrationWithMultiProducts multiSampleReg = new SampleRegistrationWithMultiProducts();
        multiSampleReg.toMultiSampleRegistration(properties.getProperty("Product_Code"), properties.getProperty("Specification_Name"));
        
        }
   }

}
