package aneurysm.structures;

public class RNC2Header {
    private int header;
    private int unpackedlen;
    private int packedlen;
    private short uncompcrc;
    private short compcrc;
    private byte leeway;
    private byte chunks;

    public RNC2Header(int header, int unpackedlen, int packedlen, short uncompcrc, short compcrc, byte leeway, byte chunks) {
        this.header = header;
        this.unpackedlen = unpackedlen;
        this.packedlen = packedlen;
        this.uncompcrc = uncompcrc;
        this.compcrc = compcrc;
        this.leeway = leeway;
        this.chunks = chunks;
    }

    public RNC2Header() {
        this.header = 0;
        this.unpackedlen = 0;
        this.packedlen = 0;
        this.uncompcrc = 0;
        this.compcrc = 0;
        this.leeway = 0;
        this.chunks = 0;
    }

    // Getters
    public int getHeader() { return header; }
    public int getUnpackedlen() { return unpackedlen; }
    public int getPackedlen() { return packedlen; }
    public short getUncompcrc() { return uncompcrc; }
    public short getCompcrc() { return compcrc; }
    public byte getLeeway() { return leeway; }
    public byte getChunks() { return chunks; }

    // Setters (for mutability)
    public void setHeader(int header) { this.header = header; }
    public void setUnpackedlen(int unpackedlen) { this.unpackedlen = unpackedlen; }
    public void setPackedlen(int packedlen) { this.packedlen = packedlen; }
    public void setUncompcrc(short uncompcrc) { this.uncompcrc = uncompcrc; }
    public void setCompcrc(short compcrc) { this.compcrc = compcrc; }
    public void setLeeway(byte leeway) { this.leeway = leeway; }
    public void setChunks(byte chunks) { this.chunks = chunks; }

    @Override
    public String toString() {
        return String.format("RNC2Header{header=0x%08X, unpackedlen=0x%08X, packedlen=0x%08X, uncompcrc=0x%04X, compcrc=0x%04X, leeway=0x%02X, chunks=0x%02X}",
                header, unpackedlen, packedlen, uncompcrc, compcrc, leeway, chunks);
    }
}