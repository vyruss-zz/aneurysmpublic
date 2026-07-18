package aneurysm.structures;

public class DigitalSound {
    private byte[] soundData;
    private int samplerate;

    public byte[] getSoundData() {
        return soundData;
    }

    public void setSoundData(byte[] soundData) {
        this.soundData = soundData;
    }


    public int getSamplerate() {
        return samplerate;
    }

    public void setSamplerate(int samplerate) {
        this.samplerate = samplerate;
    }


    public DigitalSound(byte[] soundData, int samplerate) {
        this.soundData = soundData;
        this.samplerate = samplerate;
    }
}
