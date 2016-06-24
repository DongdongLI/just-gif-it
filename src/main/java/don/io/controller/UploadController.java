package don.io.controller;


import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class UploadController {

	// logger
	private final static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Value("${multipart.location}")
	private String location;

	@RequestMapping(value = "/upload", method = RequestMethod.POST,
			produces = MediaType.IMAGE_GIF_VALUE)
	public String updload(@RequestParam("file") final MultipartFile file
			, @RequestParam("start") final int start
			, @RequestParam("end") final int end
			, @RequestParam("speed") final int speed
			, @RequestParam("repeat") final boolean repeat) throws IllegalStateException, IOException{

		final File videoFile = new File(location+"/"+System.currentTimeMillis()+".mp4");
		file.transferTo(videoFile);
		log.info("Saved file to {}", videoFile.getAbsolutePath());

		return "";
	}
}
