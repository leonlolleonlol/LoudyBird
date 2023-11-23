package com.example.demo;

import javax.sound.sampled.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SoundProcessor {

    private TargetDataLine targetDataLine;
    private final LoudyBird loudyBird;
    private boolean startAnimation=false;

    public SoundProcessor(LoudyBird loudyBird) {
        this.loudyBird = loudyBird;
    }

    public void startProcessing() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            // Get all available mixers
            Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();

            // Choose a mixer that supports the desired audio format
            Mixer mixer = null;
            for (Mixer.Info info : mixerInfo) {
                mixer = AudioSystem.getMixer(info);
                Line.Info targetDataLineInfo = new Line.Info(TargetDataLine.class);
                if (mixer.isLineSupported(targetDataLineInfo))
                    break;
            }

            if (mixer == null)
                try {
                    throw new LineUnavailableException("No mixer supports the requested audio format");
                } catch (LineUnavailableException e) {
                    throw new RuntimeException(e);
                }


            // Set up the audio format
            AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    44100, 16, 2, 4, 44100, false);

            DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, format);

            // Open the microphone
            try {
                targetDataLine = (TargetDataLine) mixer.getLine(dataLineInfo);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
            try {
                targetDataLine.open(format);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
            targetDataLine.start();

            // Create a buffer to read data from the microphone
            byte[] buffer = new byte[targetDataLine.getBufferSize() / 5];

            while (true) {
                int bytesRead = targetDataLine.read(buffer, 0, buffer.length);
                int soundLevel = calculateSoundLevel(buffer, bytesRead);

                // Update the LoudyBird object with the sound level
                if(soundLevel>15) {
                    startAnimation=true;
                    loudyBird.setVy(-soundLevel);
                }
            }
        });
        executor.shutdown();
    }

    private static int calculateSoundLevel(byte[] buffer, int bytesRead) {
        // You can implement your own logic to calculate the sound level based on the buffer data.
        // This is a simple example, and you may need to adjust it based on your specific requirements.
        int sum = 0;
        for (int i = 0; i < bytesRead; i += 2) {
            short sample = (short) ((buffer[i] & 0xFF) | (buffer[i + 1] << 8));
            sum += Math.abs(sample);
        }
        return sum / (bytesRead / 2);
    }

    public boolean isStartAnimation() {
        return startAnimation;
    }
}

