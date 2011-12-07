package gameModel;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

/**
 * Plays an MP3 file.
 * 
 * @author Parker Snell
 */
public class SoundPlayer {

	/**
	 * The JLayer player which actually plays the MP3.
	 */
	private AdvancedPlayer player;
	
	/**
	 * The file name, so that we can create a new player to loop.
	 */
	private String filename;
	
	/**
	 * Loads an MP3 file into memory, preparing to play it.
	 * 
	 * @param mp3 The name of an MP3 file.
	 */
	public SoundPlayer(String mp3) {
		filename = mp3;
	}
	
	/**
	 * Plays the sound in a separate thread.
	 */
	public void play() {
		new Thread() {
			@Override
			public void run() {
				try {
					player = new AdvancedPlayer(new BufferedInputStream(new FileInputStream(filename)));
					player.play();
				} catch (JavaLayerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	/**
	 * Loops the sound after it is done playing.
	 */
	public void loop() {
		new Thread() {
			@Override
			public void run() {
				try {
					player = new AdvancedPlayer(new BufferedInputStream(new FileInputStream(filename)));
					player.setPlayBackListener(new TanksPlaybackListener());
					player.play();
				} catch (JavaLayerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}
		
	/**
	 * Stops playing the sound.
	 */
	public void stop() {
		if (player == null)
			return;
		
		player.setPlayBackListener(null);
		player.stop();
	}
	
	/**
	 * Used for looping playback - makes the player start again once it finishes. 
	 */
	private class TanksPlaybackListener extends PlaybackListener {
		
		@Override
		public void playbackFinished(PlaybackEvent evt) {
			loop();
		}
		
	}
	
}
