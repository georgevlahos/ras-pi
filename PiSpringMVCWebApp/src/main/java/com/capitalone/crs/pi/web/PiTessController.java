package com.capitalone.crs.pi.web;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capitalone.crs.pi.helpers.FilePathHelper;
import com.capitalone.crs.pi.helpers.TesseractHelper;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A Spring MVC @Controller controller  handling requests to translate files to text.
 */
@Controller
public class PiTessController {
	
	private TesseractHelper tessHelper;
	
	@Autowired
	private FilePathHelper filepathHelper;

	@Autowired 
	public PiTessController(TesseractHelper tessHelper) {
		this.tessHelper = tessHelper;
	}
	
	/**
	 * <p>Translate a tif file to a string.</p>
	 * 
	 * @param filePath name of the file requested.${translateResult}
	 * @param model the "implicit" model created by Spring MVC
	 */
	@RequestMapping("/translateTif")
	public String translateTif(@RequestParam(value="filePath", required = false) String filePath, Model model) 
	{
		if(filePath == null){filePath = filepathHelper.getTranslateTifFilePath();}
			
		model.addAttribute("filePath", filePath);
		model.addAttribute("translateResult", tessHelper.translateImageToString(filePath));
		return "resultPage";
	}
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String handleFormUpload(@RequestParam("file") MultipartFile uploadedMultiFile, Model model) throws IOException
	{
		String returnView = "uploadFailedPage";
		
		String fileUploadDir = filepathHelper.getUploadDirectory() + "uploadedFile." 
							   + new SimpleDateFormat("MMMddyyyyHHmm").format(new Date()) + uploadedMultiFile.getOriginalFilename();
		File fileToTranslate = new File(fileUploadDir);
		
		if (!uploadedMultiFile.isEmpty()) 
		{
			uploadedMultiFile.transferTo(fileToTranslate);
		    Tesseract tessInstance = Tesseract.getInstance(); 
		    try 
		    {
		         String translationResult = tessInstance.doOCR(fileToTranslate);
				 model.addAttribute("fileName", uploadedMultiFile.getOriginalFilename());
				 model.addAttribute("translateResult", translationResult);

				 // START - Output the results of the file translation to txt document.
				 String translatedResultsFilePath = "C:/aFileDirectory/rasberryPi/fileUploads/" + uploadedMultiFile.getOriginalFilename() +  ".translatedToString." 
						   + new SimpleDateFormat("MMMdd-yyyy.HHmm").format(new Date()) + ".txt";
				 File translatedResultsFile = new File(translatedResultsFilePath);
				 PrintWriter pw = 	  new PrintWriter(translatedResultsFile);
				 pw.print(translationResult);
				 pw.close();
				 
				 returnView = "uploadSuccessPage";
		    } 
		    catch (TesseractException e) 
		    {
		        System.err.println(e.getMessage());
		    }
		 }
		
		return returnView;
	}
	
}
