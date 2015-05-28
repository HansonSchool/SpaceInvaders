import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;


public class ClipPlayer {
	Map<String, Clip>clipMap = new HashMap<String, Clip>();
	//ArrayList<Clip> clipList;// this is the Clip that will be played
	//ArrayList<String> fileList;
	public ClipPlayer(){
		
	}
	public void mapFile(String command, String path){
		// add a soundclip to the map.  This will map the command 
		// to the clip that is opened at the indicated path
		// do this early in the loadup of the application

		Clip clip= null;
		URL url = getClass().getResource("res/sounds/"+path);
		System.out.println(url);
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(url);
			AudioFormat format = stream.getFormat();
			if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
				format = new AudioFormat(
						AudioFormat.Encoding.PCM_SIGNED,
						format.getSampleRate(),
						format.getSampleSizeInBits()*2,
						format.getChannels(),
						format.getFrameSize()*2,
						format.getFrameRate(),
						true);        
				stream = AudioSystem.getAudioInputStream(format, stream);
			}

			// Create the clip
			DataLine.Info info = new DataLine.Info(
					Clip.class, stream.getFormat(), ((int)stream.getFrameLength()*format.getFrameSize()));
			clip = (Clip) AudioSystem.getLine(info);

			// This method does not return until the audio file is completely loaded
			clip.open(stream);

		}catch (Exception e) {
			System.out.println("Problem with sound loading \n"+ e);

		}
		clipMap.put(command,clip);
	}
	
	public void play(String command) {
		Clip c = clipMap.get(command);
		//System.out.println("Trying to play "+c);
		
//		if(c.isRunning()) {
//			c.stop();
//			c.flush();
//			
//		}
		c.setFramePosition(0);
		c.start();
	}
}

