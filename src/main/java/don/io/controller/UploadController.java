package don.io.controller;


import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.madgag.gif.fmsware.AnimatedGifEncoder;

import don.io.services.ConverterService;
import don.io.services.GifEncodeService;
import don.io.services.VideoDecoderService;


@RestController
public class UploadController {

	// logger
	private final static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Value("${multipart.location}")
	private String location;

	@Inject
	private ConverterService convertService;

	@Inject
	private GifEncodeService gifEncoderService;

	@Inject
	private VideoDecoderService videoDecoderService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST,
			produces = MediaType.IMAGE_GIF_VALUE)
	public String updload(@RequestParam("file") final MultipartFile file
			, @RequestParam("start") final int start
			, @RequestParam("end") final int end
			, @RequestParam("speed") final int speed
			, @RequestParam("repeat") final boolean repeat) throws IllegalStateException, IOException, Exception{

		final File videoFile = new File(location+"/"+System.currentTimeMillis()+".mp4");
		file.transferTo(videoFile);
		log.info("Saved file to {}", videoFile.getAbsolutePath());

		final Path output = Paths.get(location+"/gif"+System.currentTimeMillis()+".gif");

		final FFmpegFrameGrabber frameGrabber = videoDecoderService.read(videoFile);
		final AnimatedGifEncoder gifEncoder = gifEncoderService.getGifEncoder(repeat,
				(float)frameGrabber.getFrameRate(), output);

		convertService.toGif(frameGrabber, gifEncoder, start, end, speed);

		log.info("Saved generated gif to {}", output.toString());
        return output.getFileName().toString();
	}
}
