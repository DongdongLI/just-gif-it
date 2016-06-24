package don.io.services;

import java.awt.image.BufferedImage;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.stereotype.Service;

import com.madgag.gif.fmsware.AnimatedGifEncoder;

@Service
public class ConverterService {

	public void toGif(final FFmpegFrameGrabber frameGrabber, final AnimatedGifEncoder gifEncoder
			, final int start, final int end, final int speed) throws Exception{

		final long startFrame = Math.round(start * frameGrabber.getFrameRate());
		final long endFrame = Math.round(end * frameGrabber.getFrameRate());

		final Java2DFrameConverter frameConverter = new Java2DFrameConverter();

		for(long i=startFrame;i<endFrame;i++){
			if(i % speed >0)
				frameGrabber.setFrameNumber((int)i);

			final BufferedImage bufferedImage = frameConverter
					.convert(frameGrabber.grabImage());
			gifEncoder.addFrame(bufferedImage);
		}

		frameGrabber.stop();
		gifEncoder.finish();
	}

}
