package gameModel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

/**
 * Plays an MP3 file. This class also limits the number of sounds that can
 * play simultaneously. It is assumed that as soon as you obtain a handle to
 * a SoundPlayer, you are going to ask it to play. If you don't do this,
 * that object is still added to the currently playing count, and will
 * count against the number of SoundPlayers you can create later, until that
 * one has played through or been stopped.
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
	 * The PlaybackListener that we can use to make the song loop or stop looping.
	 */
	private TanksPlaybackListener listener;
	
	/**
	 * The maximum number of SoundPlayers that are allowed to be playing at one time.
	 */
	private static final int maxSounds = 7;
	
	/**
	 * The number of SoundPlayers playing right now (must be <= maxSounds).
	 */
	private static int currentlyPlaying = 0;
	
	/**
	 * Loads an MP3 file into memory, preparing to play it.
	 * 
	 * @param mp3 The name of an MP3 file.
	 */
	private SoundPlayer(String mp3) {
		file = new File(mp3);
	}
	
	/**
	 * Loads an MP3 file into memory, preparing to play it.
	 * 
	 * @param uri The URI representing the file path.
	 */
	private SoundPlayer(URI uri) {
		file = new File(uri.getPath());
	}
	
	/**
	 * A convenience method to get a SoundPlayer for a given resource filename. If too many sounds
	 * are playing, it will return null. Otherwise, currentlyPlaying is incremented and
	 * a valid SoundPlayer is returned.
	 * 
	 * @param filename The name of the resource file.
	 * @return A SoundPlayer object for the resource, or null if one couldn't be created.
	 */
	public static SoundPlayer playerFromResource(String filename) {
		if (currentlyPlaying >= maxSounds)
			return null;
		
		try {
			URL resource = SoundPlayer.class.getResource(filename);
			if (resource == null || resource.toURI() == null || resource.toURI().getPath() == null)
				return null;
			
			SoundPlayer player = new SoundPlayer(resource.toURI());
			++currentlyPlaying;
			return player;
		} catch (URISyntaxException e) {
			return null;
		}
	}
	
	/**
	 * Plays the sound in a separate thread, as long as too many other
	 * sounds are not already playing.
	 */
	public void play() {
		new Thread() {
			@Override
			public void run() {
				try {
					player = new AdvancedPlayer(new BufferedInputStream(new FileInputStream(file)));
					player.setPlayBackListener(listener = new TanksPlaybackListener(false));
					player.play();
				} catch (JavaLayerException e) {
					--currentlyPlaying;
				} catch (FileNotFoundException e) {
					--currentlyPlaying;
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
					player.setPlayBackListener(listener = new TanksPlaybackListener(true));
					player.play();
				} catch (JavaLayerException e) {
					--currentlyPlaying;
				} catch (FileNotFoundException e) {
					--currentlyPlaying;
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
		
		if (listener != null)
			listener.setLooping(false);
		
		// This will invoke playbackFinished, which will decrement currentlyPlaying
		// and set player to null.
		player.stop();
	}
	
	/**
	 * Used for looping playback - makes the player start again once it finishes. 
	 */
	private class TanksPlaybackListener extends PlaybackListener {
		
		private boolean looping;
		
		public TanksPlaybackListener(boolean looping) {
			this.looping = looping;
		}
		
		public void setLooping(boolean looping) {
			this.looping = looping;
		}
		
		@Override
		public void playbackFinished(PlaybackEvent evt) {
			if (looping) {
				loop();
			} else {
				player = null;
				--currentlyPlaying;
			}
		}
		
	}
	
}
