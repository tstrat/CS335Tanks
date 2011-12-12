package gameModel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;

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
	 * The MP3 file handle.
	 */
	
	private File file;
	
	/**
	 * Loads an MP3 file into memory, preparing to play it.
	 * 
	 * @param mp3 The name of an MP3 file.
	 */
	
	public SoundPlayer(String mp3) {
		file = new File(mp3);
	}
	
	/**
	 * Loads an MP3 file into memory, preparing to play it.
	 * 
	 * @param uri The URI representing the file path.
	 */
	
	public SoundPlayer(URI uri) {
		file = new File(uri.getPath());
	}
	
	/**
	 * A convenience method to get a SoundPlayer for a given resource filename.
	 * 
	 * @param filename The name of the resource file.
	 * @return A SoundPlayer object for the resource, or null if one couldn't be created.
	 */
	
	public static SoundPlayer playerFromResource(String filename) {
		try {
			return new SoundPlayer(SoundPlayer.class.getResource(filename).toURI());
		} catch (URISyntaxException e) {
			return null;
		}
	}
	
	/**
	 * Plays the sound in a separate thread.
	 */
	
	public void play() {
		new Thread() {
			@Override
			public void run() {
				try {
					player = new AdvancedPlayer(new BufferedInputStream(new FileInputStream(file)));
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
	 * Plays an MP3 file.
	 * 
	 * @param filename The name of the file to play.
	 */
	public static void play(String filename) {
		SoundPlayer player = playerFromResource(filename);
		if (player != null)
			player.play();
	}
	
	/**
	 * Loops the sound after it is done playing.
	 */
	
	public void loop() {
		new Thread() {
			@Override
			public void run() {
				try {
					player = new AdvancedPlayer(new BufferedInputStream(new FileInputStream(file)));
					player.setPlayBackListener(new TanksPlaybackListener());
					player.play();
				} catch (JavaLayerException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
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
		player = null;
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
