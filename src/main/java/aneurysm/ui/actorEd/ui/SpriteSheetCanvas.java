package aneurysm.ui.actorEd.ui;

import aneurysm.render.BufferedImageBuilder;
import aneurysm.ui.DataLists;

import aneurysm.structures.ActorDefinition;
import aneurysm.ui.actorEd.edit.ActorEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteSheetCanvas extends JPanel {

    private BufferedImage proj;
    private BufferedImage[] idle;
    private BufferedImage[] walk;
    private BufferedImage[] hit;
    private BufferedImage[] die;
    private ActorEditor host;
    private int width = 8, height = 8, idleWidth = 8, idleHeight = 8, walkWidth = 8, walkHeight = 8, hitWidth = 8, hitHeight = 8, dieWidth = 8, dieHeight = 8;

    public SpriteSheetCanvas(ActorEditor host) {
        this.host = host;
        this.setSize(8, 8);
        updateSpritePreviewPanel();
    }

    public void updateSpritePreviewPanel() {
        ActorDefinition currentActorDef = host.getCurrentActorDef();
        idleWidth = 8;
        idleHeight = 8;
        walkWidth = 8;
        walkHeight = 8;
        hitWidth = 8;
        hitHeight = 8;
        dieWidth = 8;
        dieHeight = 8;
        ArrayList<Integer> sprites;
        //idle frame
//        System.out.printf("idle %08x%n", currentActorDef.getIdleSpriteFrameOffs());
        sprites = DataLists.getEditorActorInfo().getActorSpriteDefs().get(currentActorDef.getIdleSpriteFrameOffs());
//        System.out.println(DataLists.getActorSpriteCache().keySet());
        idle = new BufferedImage[sprites.size()];
        for (int i = 0; i < sprites.size(); i++) {
            idleWidth = 16 + DataLists.getActorSpriteCache().get(sprites.get(i)).length;
            if (idleHeight < DataLists.getActorSpriteCache().get(sprites.get(i))[0].length)
                idleHeight = DataLists.getActorSpriteCache().get(sprites.get(i))[0].length;
            idle[i] = BufferedImageBuilder.build8BPPImageFromByte2DArray(DataLists.getActorSpriteCache().get(sprites.get(i)), 32);

        }
        //walk frames
        sprites = DataLists.getEditorActorInfo().getActorSpriteDefs().get(currentActorDef.getWalkAnimOffs());
        walk = new BufferedImage[sprites.size()];
        for (int i = 0; i < sprites.size(); i++) {
            walkWidth = 16 + DataLists.getActorSpriteCache().get(sprites.get(i)).length;
            if (walkHeight < DataLists.getActorSpriteCache().get(sprites.get(i))[0].length)
                walkHeight = DataLists.getActorSpriteCache().get(sprites.get(i))[0].length;
            walk[i] = BufferedImageBuilder.build8BPPImageFromByte2DArray(DataLists.getActorSpriteCache().get(sprites.get(i)), 32);

        }
        if (walkWidth > width)
            width = walkWidth;
        walkHeight += 8;
        height += walkHeight;
        //hit frames
        sprites = DataLists.getEditorActorInfo().getActorSpriteDefs().get(currentActorDef.getHitAnimOffs());
        hit = new BufferedImage[sprites.size()];
        for (int i = 0; i < sprites.size(); i++) {
            hitWidth = 16 + DataLists.getActorSpriteCache().get(sprites.get(i)).length;
            if (hitHeight < DataLists.getActorSpriteCache().get(sprites.get(i))[0].length)
                hitHeight = DataLists.getActorSpriteCache().get(sprites.get(i))[0].length;
            hit[i] = BufferedImageBuilder.build8BPPImageFromByte2DArray(DataLists.getActorSpriteCache().get(sprites.get(i)), 32);
        }
        if (hitWidth > width)
            width = hitWidth;
        hitHeight += 16;
        height += hitHeight;
        //die frame
        sprites = DataLists.getEditorActorInfo().getActorSpriteDefs().get(currentActorDef.getDieAnimOffs());
        die = new BufferedImage[sprites.size()];
        for (int i = 0; i < sprites.size(); i++) {
            dieWidth = 16 + DataLists.getActorSpriteCache().get(sprites.get(i)).length;
            if (dieHeight < DataLists.getActorSpriteCache().get(sprites.get(i))[0].length)
                dieHeight = DataLists.getActorSpriteCache().get(sprites.get(i))[0].length;
            die[i] = BufferedImageBuilder.build8BPPImageFromByte2DArray(DataLists.getActorSpriteCache().get(sprites.get(i)), 32);
        }
        if (dieWidth > width)
            width = dieWidth;
        dieHeight += 8;
        height += dieHeight;
        //projectile
        height += 16;
        proj = BufferedImageBuilder.build8BPPImageFromByte2DArray(DataLists.getProjectileSpriteCache().get(DataLists.getProjectiles().get(currentActorDef.getProjectile()).getSpriteOffs()), 32);

        this.setSize(700, 700);
        host.resizeSpritePreviewWindow(700, 700);
        this.repaint();

    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        int currX = 0, currY = 0;
        for (int i = 0; i < idle.length; i++) {
            g.drawImage(idle[i], 8 + currX, currY, null);
            currX += idle[i].getWidth() + 8;
        }
        currX = 8;
        currY += idleHeight + 8;
        for (int i = 0; i < walk.length; i++) {
            g.drawImage(walk[i], 8 + currX, currY, null);
            currX += walk[i].getWidth() + 8;
        }

        currX = 8;
        currY += walkHeight;
        for (int i = 0; i < hit.length; i++) {
            g.drawImage(hit[i], 8 + currX, currY, null);
            currX += hit[i].getWidth() + 8;
        }
        currX = 8;
        currY += hitHeight + 8;
        for (int i = 0; i < die.length; i++) {
            g.drawImage(die[i], 8 + currX, currY, null);
            currX += die[i].getWidth() + 8;
        }

        currX = 8;
        currY += dieHeight + 8;
        g.drawImage(proj, 8 + currX, currY, null);
    }
}
