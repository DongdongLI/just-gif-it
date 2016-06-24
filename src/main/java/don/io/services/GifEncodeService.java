package don.io.services;

import java.nio.file.Path;

import org.springframework.stereotype.Service;

import com.madgag.gif.fmsware.AnimatedGifEncoder;

@Service
public class GifEncodeService {

	public AnimatedGifEncoder getGifEncoder(final boolean repeat, final float frameRate, final Path output){
		final AnimatedGifEncoder encoder = new AnimatedGifEncoder();
		if(repeat)
			encoder.setRepeat(0);

		encoder.setFrameRate(frameRate);
		encoder.start(output.toString());

		return encoder;
	}
}
