/*
 * Craps Game Sounds
 */

package view;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * Provides sound effects for the Craps Game application.
 * This utility class generates synthesized audio tones for
 * game events such as dice rolls, wins, and losses.
 *
 * @author Nicholas Humeniuk Sandberg
 * @version November/December 2025
 */
public class CrapsGameSounds {

    /**
     * Plays a tone at the specified frequency and duration.
     *
     * @param theHz the frequency in Hertz
     * @param theMsecs the duration in milliseconds
     */
    private static void playTone(final int theHz, final int theMsecs) {
        try {
            final byte[] buf = new byte[1];
            final AudioFormat af = new AudioFormat(8000f, 8, 1, true, false);
            final SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
            sdl.open(af);
            sdl.start();

            for (int i = 0; i < theMsecs * 8; i++) {
                final double angle = i / (8000f / theHz) * 2.0 * Math.PI;
                buf[0] = (byte) (Math.sin(angle) * 127.0);
                sdl.write(buf, 0, 1);
            }

            sdl.drain();
            sdl.stop();
            sdl.close();

        } catch (final LineUnavailableException e) {
            System.err.println("Audio line is unavailable");
        }
    }

    /**
     * Plays sound effects asynchronously for different game events.
     * Supported sound types: "roll.wav", "win.wav", and "lose.wav".
     *
     * @param theSoundType the type of sound effect to play
     */
    public static void playSoundAsync(final String theSoundType) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if ("roll.wav".equals(theSoundType)) {
                    playTone(400, 50);
                    playTone(500, 50);

                } else if ("win.wav".equals(theSoundType)) {
                    playTone(523, 150);  // C
                    playTone(659, 150);  // E
                    playTone(784, 300);  // G

                } else if ("lose.wav".equals(theSoundType)) {
                    playTone(400, 200);
                    playTone(300, 300);
                }
            }
        }).start();
    }
}
