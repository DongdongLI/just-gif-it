package don.io.services;

import java.io.File;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;
import org.springframework.stereotype.Service;

@Service
public class VideoDecoderService {

	public FFmpegFrameGrabber read(final File file) throws FrameGrabber.Exception{
		final FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(file);
		frameGrabber.start();
		return frameGrabber;
	}
}
