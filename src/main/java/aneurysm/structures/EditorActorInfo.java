package aneurysm.structures;

import java.util.ArrayList;
import java.util.HashMap;

public class EditorActorInfo {
    private HashMap<Integer, ArrayList<Integer>> actorSpriteDefs = new HashMap<>();
    private HashMap<Integer, ArrayList<Integer>> projectileSpriteDefs = new HashMap<>();
    private ArrayList<Integer> soundIds = new ArrayList<>();
    private HashMap<Integer, Integer> projectiles = new HashMap<>();

    public HashMap<Integer, ArrayList<Integer>> getActorSpriteDefs() {
        return actorSpriteDefs;
    }

    public void setActorSpriteDefs(HashMap<Integer, ArrayList<Integer>> spriteDefs) {
        this.actorSpriteDefs = spriteDefs;
    }

    public ArrayList<Integer> getSoundIds() {
        return soundIds;
    }

    public void setSoundIds(ArrayList<Integer> soundIds) {
        this.soundIds = soundIds;
    }

    public HashMap<Integer, Integer> getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(HashMap<Integer, Integer> projectiles) {
        this.projectiles = projectiles;
    }

    public HashMap<Integer, ArrayList<Integer>> getProjectileSpriteDefs() {
        return projectileSpriteDefs;
    }

    public void setProjectileSpriteDefs(HashMap<Integer, ArrayList<Integer>> projectileSpriteDefs) {
        this.projectileSpriteDefs = projectileSpriteDefs;
    }
}
