
import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.lwjgl.openal.AL10;

public class Sound {
	private int soundID;
	
	public Sound(String path) 
	{
		try
		{
			// this is ugly!
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
			AudioFormat aif = ais.getFormat();
			ByteBuffer buf = ByteBuffer.allocateDirect(ais.available());
			buf.order(aif.isBigEndian() ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
			byte[] data  = new byte[ais.available()];
			int read=0, total=0;
			while ((read = ais.read(data, total, data.length-total)) != 1 && total < data.length)
			{
				total += read;
			}
			ByteBuffer src = ByteBuffer.wrap(data);
			while (src.hasRemaining()) {
				buf.put(src.get());
			}
			buf.rewind();
			
			int bufID=AL10.alGenBuffers();
			soundID=AL10.alGenSources();
			
			AL10.alBufferData(bufID,aif.getChannels()==1 ? AL10.AL_FORMAT_MONO16 : AL10.AL_FORMAT_STEREO16, 
					buf, (int)aif.getSampleRate());
			AL10.alSourcei(soundID,  AL10.AL_BUFFER,  bufID);
			
		
		} catch (Exception e)
		{
			throw new RuntimeException("error loading sound file", e);
		}
				
		
	}
	
	public void play()
	{
		AL10.alSourcePlay(soundID);
	}
	
	public void stop()
	{
		AL10.alSourceStop(soundID);
	}
	
	public void setGain(float g)
	{
		AL10.alSourcef(soundID,  AL10.AL_GAIN, g);
	}
		
	public void setLoop(boolean b)
	{
		AL10.alSourcei(soundID, AL10.AL_LOOPING, b?1:0);
	}
	
	
}