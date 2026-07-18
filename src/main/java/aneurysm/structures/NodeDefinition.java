package aneurysm.structures;

public class NodeDefinition {
    private short sentryStartOrbitSpeed, sentryRefireDelay, sentrySpeedIncrement, sentryMaxSpeed, sentryNodeFarDistance, sentryNodeCloseDistance, sentrySpeed, sentryCount, unknOne, unknTwo, unknThree, unknFour, nodeHealthRefireAdvance;

    public NodeDefinition(short sentryStartOrbitSpeed, short sentryRefireDelay, short sentrySpeedIncrement, short sentryMaxSpeed, short sentryNodeFarDistance, short sentryNodeCloseDistance, short sentrySpeed, short sentryCount, short unknOne, short unknTwo, short unknThree, short unknFour, short nodeHealthRefireAdvance) {
        this.sentryStartOrbitSpeed = sentryStartOrbitSpeed;
        this.sentryRefireDelay = sentryRefireDelay;
        this.sentrySpeedIncrement = sentrySpeedIncrement;
        this.sentryMaxSpeed = sentryMaxSpeed;
        this.sentryNodeFarDistance = sentryNodeFarDistance;
        this.sentryNodeCloseDistance = sentryNodeCloseDistance;
        this.sentrySpeed = sentrySpeed;
        this.sentryCount = sentryCount;
        this.unknOne = unknOne;
        this.unknTwo = unknTwo;
        this.unknThree = unknThree;
        this.unknFour = unknFour;
        this.nodeHealthRefireAdvance = nodeHealthRefireAdvance;
    }

    public short getSentryStartOrbitSpeed() {
        return sentryStartOrbitSpeed;
    }

    public void setSentryStartOrbitSpeed(short sentryStartOrbitSpeed) {
        this.sentryStartOrbitSpeed = sentryStartOrbitSpeed;
    }

    public short getSentryRefireDelay() {
        return sentryRefireDelay;
    }

    public void setSentryRefireDelay(short sentryRefireDelay) {
        this.sentryRefireDelay = sentryRefireDelay;
    }

    public short getSentrySpeedIncrement() {
        return sentrySpeedIncrement;
    }

    public void setSentrySpeedIncrement(short sentrySpeedIncrement) {
        this.sentrySpeedIncrement = sentrySpeedIncrement;
    }

    public short getSentryMaxSpeed() {
        return sentryMaxSpeed;
    }

    public void setSentryMaxSpeed(short sentryMaxSpeed) {
        this.sentryMaxSpeed = sentryMaxSpeed;
    }

    public short getSentryNodeFarDistance() {
        return sentryNodeFarDistance;
    }

    public void setSentryNodeFarDistance(short sentryNodeFarDistance) {
        this.sentryNodeFarDistance = sentryNodeFarDistance;
    }

    public short getSentryNodeCloseDistance() {
        return sentryNodeCloseDistance;
    }

    public void setSentryNodeCloseDistance(short sentryNodeCloseDistance) {
        this.sentryNodeCloseDistance = sentryNodeCloseDistance;
    }

    public short getSentrySpeed() {
        return sentrySpeed;
    }

    public void setSentrySpeed(short sentrySpeed) {
        this.sentrySpeed = sentrySpeed;
    }

    public short getSentryCount() {
        return sentryCount;
    }

    public void setSentryCount(short sentryCount) {
        this.sentryCount = sentryCount;
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

    public short getUnknThree() {
        return unknThree;
    }

    public void setUnknThree(short unknThree) {
        this.unknThree = unknThree;
    }

    public short getUnknFour() {
        return unknFour;
    }

    public void setUnknFour(short unknFour) {
        this.unknFour = unknFour;
    }

    public short getNodeHealthRefireAdvance() {
        return nodeHealthRefireAdvance;
    }

    public void setNodeHealthRefireAdvance(short nodeHealthRefireAdvance) {
        this.nodeHealthRefireAdvance = nodeHealthRefireAdvance;
    }
}
