/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local_data;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Filip
 */
public class Resources {

    /**
     *
     * @param name of soundfile
     * @return AudioInputStream of selected file
     */
    public static AudioInputStream getAudioStream(String name) {
	InputStream audioSrc = ClassLoader.getSystemResourceAsStream("resources/" + name);
	InputStream bufferedIn = new BufferedInputStream(audioSrc);
	try {
	    AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
	    return audioStream;
	} catch (UnsupportedAudioFileException ex) {
	    ex.printStackTrace();
	} catch (IOException ex) {
	    ex.printStackTrace();
	}
	return null;
    }

    /**
     *
     * @param name of file
     * @return stream of file
     */
    public static InputStream getStream(String name) {
	return ClassLoader.getSystemResourceAsStream("resources/" + name);
    }

    /**
     *
     * @param name of file
     * @return url to file
     */
    public static URL getUrl(String name) {
	return ClassLoader.getSystemResource("resources/" + name);
    }

}
