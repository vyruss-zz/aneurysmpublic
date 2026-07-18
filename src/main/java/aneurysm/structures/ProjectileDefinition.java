package aneurysm.structures;

public class ProjectileDefinition {
    private short unknOne, unknTwo, damage, speed, thingId, fireDelay, holdFireDelay;
    private int soundId, spriteOffs;

    public ProjectileDefinition(short unknOne, short unknTwo, short damage, int spriteOffs, int soundId, short speed, short thingId, short fireDelay, short holdFireDelay) {
        this.unknOne = unknOne;
        this.unknTwo = unknTwo;
        this.damage = damage;
        this.soundId = soundId;
        this.speed = speed;
        this.thingId = thingId;
        this.fireDelay = fireDelay;
        this.holdFireDelay = holdFireDelay;
        this.spriteOffs = spriteOffs;
    }

    public ProjectileDefinition() {

    }

    public short getUnknOne() {
        return unknOne;
    }

    public void setUnknOne(short unknOne) {
        this.unknOne = unknOne;
    }

    public short getUnknTwo() {
        return unknTwo;
    }

    public void setUnknTwo(short unknTwo) {
        this.unknTwo = unknTwo;
    }

    public short getDamage() {
        return damage;
    }

    public void setDamage(short damage) {
        this.damage = damage;
    }

    public int getSoundId() {
        return soundId;
    }

    public void setSoundId(int soundId) {
        this.soundId = soundId;
    }

    public short getSpeed() {
        return speed;
    }

    public void setSpeed(short speed) {
        this.speed = speed;
    }

    public short getThingId() {
        return thingId;
    }

    public void setThingId(short thingId) {
        this.thingId = thingId;
    }

    public short getFireDelay() {
        return fireDelay;
    }

    public void setFireDelay(short fireDelay) {
        this.fireDelay = fireDelay;
    }

    public short getHoldFireDelay() {
        return holdFireDelay;
    }

    public void setHoldFireDelay(short holdFireDelay) {
        this.holdFireDelay = holdFireDelay;
    }

    public int getSpriteOffs() {
        return spriteOffs;
    }

    public void setSpriteOffs(int spriteOffs) {
        this.spriteOffs = spriteOffs;
    }

    @Override
    public String toString() {
        return String.format("%s %08x %s  %08x %n", "spriteoffset", spriteOffs, "soundoffset", soundId);
    }
}
