package com.capitalone.crs.pi.helpers;

import java.io.File;

import org.springframework.stereotype.Component;

import net.sourceforge.tess4j.*;

@Component
public class TesseractHelper 
{

	public String translateImageToString(String filePath)
	{
		String resultString = "";
		
		File imageFile = new File(filePath);
	    Tesseract instance = Tesseract.getInstance(); 
	
	    try 
	    {
	        resultString = instance.doOCR(imageFile);
	    } 
	    catch (TesseractException e) 
	    {
	        System.err.println(e.getMessage());
	    }
	    
	    return resultString;
	}
}
