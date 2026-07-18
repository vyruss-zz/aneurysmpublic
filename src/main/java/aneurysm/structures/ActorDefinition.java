package aneurysm.structures;

public class ActorDefinition {
    private short shotDelay;
    private short accuracy;
    private short moveSpeed;
    private short moveDelay;
    private short actionDelay;
    private short unknDelay;
    private short projectileYOrg;
    private short hitPoints;
    private short hitStunMoveTime;
    private short hitStunFireDelayTime;
    private short hitDelayTime;
    private int alertSoundId;
    private int hitSoundId;
    private int deathSoundId;
    private short damageHitRadius;
    private short wallRadius;
    private short knockbackAngle;
    private short knockbackDistance;
    private short playerDistanceKept;
    private int projectile, idleSpriteFrameOffs, walkAnimOffs, hitAnimOffs, dieAnimOffs;

    public ActorDefinition(short shotDelay, short accuracy, int projectile, short moveSpeed, short moveDelay, short actionDelay, short unknDelay, short projectileYOrg, short hitPoints, short hitStunMoveTime, short hitStunFireDelayTime, short hitDelayTime, int alertSoundId, int painSoundId, int deathSoundId, short damageHitRadius, short wallRadius, short knockbackAngle, short knockbackDistance, short playerDistanceKept, int idleSpriteFrameOffs, int walkAnimOffs, int hitAnimOffs, int dieAnimOffs) {
        this.shotDelay = shotDelay;
        this.accuracy = accuracy;
        this.moveSpeed = moveSpeed;
        this.moveDelay = moveDelay;
        this.actionDelay = actionDelay;
        this.unknDelay = unknDelay;
        this.projectileYOrg = projectileYOrg;
        this.hitPoints = hitPoints;
        this.hitStunMoveTime = hitStunMoveTime;
        this.hitStunFireDelayTime = hitStunFireDelayTime;
        this.hitDelayTime = hitDelayTime;
        this.alertSoundId = alertSoundId;
        this.hitSoundId = painSoundId;
        this.deathSoundId = deathSoundId;
        this.damageHitRadius = damageHitRadius;
        this.wallRadius = wallRadius;
        this.knockbackAngle = knockbackAngle;
        this.knockbackDistance = knockbackDistance;
        this.playerDistanceKept = playerDistanceKept;
        this.idleSpriteFrameOffs = idleSpriteFrameOffs;
        this.walkAnimOffs = walkAnimOffs;
        this.hitAnimOffs = hitAnimOffs;
        this.projectile = projectile;
        this.dieAnimOffs = dieAnimOffs;
    }

    public short getHitDelayTime() {
        return hitDelayTime;
    }

    public void setHitDelayTime(short hitDelayTime) {
        this.hitDelayTime = hitDelayTime;
    }

    public short getKnockbackDistance() {
        return knockbackDistance;
    }

    public void setKnockbackDistance(short knockbackDistance) {
        this.knockbackDistance = knockbackDistance;
    }

    public short getShotDelay() {
        return shotDelay;
    }

    public void setShotDelay(short shotDelay) {
        this.shotDelay = shotDelay;
    }

    public short getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(short accuracy) {
        this.accuracy = accuracy;
    }

    public short getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(short moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public short getMoveDelay() {
        return moveDelay;
    }

    public void setMoveDelay(short moveDelay) {
        this.moveDelay = moveDelay;
    }

    public short getActionDelay() {
        return actionDelay;
    }

    public void setActionDelay(short actionDelay) {
        this.actionDelay = actionDelay;
    }

    public short getUnknDelay() {
        return unknDelay;
    }

    public void setUnknDelay(short unknDelay) {
        this.unknDelay = unknDelay;
    }

    public short getProjectileYOrg() {
        return projectileYOrg;
    }

    public void setProjectileYOrg(short projectileYOrg) {
        this.projectileYOrg = projectileYOrg;
    }

    public short getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(short hitPoints) {
        this.hitPoints = hitPoints;
    }

    public short getHitStunMoveTime() {
        return hitStunMoveTime;
    }

    public void setHitStunMoveTime(short hitStunMoveTime) {
        this.hitStunMoveTime = hitStunMoveTime;
    }

    public short getHitStunFireDelayTime() {
        return hitStunFireDelayTime;
    }

    public void setHitStunFireDelayTime(short hitStunFireDelayTime) {
        this.hitStunFireDelayTime = hitStunFireDelayTime;
    }

    public int getAlertSoundId() {
        return alertSoundId;
    }

    public void setAlertSoundId(int alertSoundId) {
        this.alertSoundId = alertSoundId;
    }

    public int getHitSoundId() {
        return hitSoundId;
    }

    public void setHitSoundId(int painSoundId) {
        this.hitSoundId = painSoundId;
    }

    public int getDeathSoundId() {
        return deathSoundId;
    }

    public void setDeathSoundId(int deathSoundId) {
        this.deathSoundId = deathSoundId;
    }

    public short getDamageHitRadius() {
        return damageHitRadius;
    }

    public void setDamageHitRadius(short damageHitRadius) {
        this.damageHitRadius = damageHitRadius;
    }

    public short getWallRadius() {
        return wallRadius;
    }

    public void setWallRadius(short wallRadius) {
        this.wallRadius = wallRadius;
    }

    public short getKnockbackAngle() {
        return knockbackAngle;
    }

    public void setKnockbackAngle(short knockbackAngle) {
        this.knockbackAngle = knockbackAngle;
    }

    public short getPlayerDistanceKept() {
        return playerDistanceKept;
    }

    public void setPlayerDistanceKept(short playerDistanceKept) {
        this.playerDistanceKept = playerDistanceKept;
    }

    public int getProjectile() {
        return projectile;
    }

    public void setProjectile(int projectile) {
        this.projectile = projectile;
    }

    public int getIdleSpriteFrameOffs() {
        return idleSpriteFrameOffs;
    }

    public void setIdleSpriteFrameOffs(int idleSpriteFrameOffs) {
        this.idleSpriteFrameOffs = idleSpriteFrameOffs;
    }

    public int getWalkAnimOffs() {
        return walkAnimOffs;
    }

    public void setWalkAnimOffs(int walkAnimOffs) {
        this.walkAnimOffs = walkAnimOffs;
    }

    public int getHitAnimOffs() {
        return hitAnimOffs;
    }

    public void setHitAnimOffs(int hitAnimOffs) {
        this.hitAnimOffs = hitAnimOffs;
    }

    public int getDieAnimOffs() {
        return dieAnimOffs;
    }

    public void setDieAnimOffs(int dieAnimOffs) {
        this.dieAnimOffs = dieAnimOffs;
    }

    @Override
    public String toString() {
        return String.format("%s=%08x %s=%08x %s=%08x %s=%08x %s=%08x %s=%08x %s=%08x %s=%08x%n", "projectile", projectile, "alertSound", alertSoundId, "hitSound", hitSoundId, "dieSound", deathSoundId,
                "idleFrame", idleSpriteFrameOffs, "walkFrames", walkAnimOffs, "hitFrame", hitAnimOffs, "dieFrames", dieAnimOffs);
    }
}
