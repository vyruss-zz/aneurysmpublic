package aneurysm.render;

import aneurysm.ui.DataLists;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BufferedImageBuilder {

    public static BufferedImage build1BPPImage(byte[] inbuff, int width, int height, Color[] pal, int paloffs) {
        BufferedImage out = new BufferedImage(width, height + 1, BufferedImage.TYPE_INT_RGB);
        int currX = 0, currY = 0;
        byte currByte;
        for (byte b : inbuff) {
            currByte = b;
            for (int currpix = 0; currpix < 8; currpix++) {
                out.setRGB(currX++, currY, pal[((currByte & 0x80) >> 7) + paloffs].getRGB());
                currByte <<= 1;
                if (currX >= width) {
                    currY++;
                    currX = 0;
                }
            }

        }
        return out;
    }

    public static BufferedImage build4BPPTile(byte[] inbuff, int width, int height, Color[] pal, int buffstart, int buffend) {
        BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int currX = 0, currY = 0;
        byte currByte;
        for (int i = buffstart; i < buffend; i++) {
            currByte = inbuff[i];
            out.setRGB(currX, currY, pal[(currByte >> 4) & 0x0f].getRGB());
            out.setRGB(currX + 1, currY, pal[currByte & 0x0f].getRGB());
            currX += 2;
            if (currX >= width) {
                currY++;
                currX = 0;
            }

        }

        return out;
    }

    public static BufferedImage build8BPPImageFromByte2DArray(Byte[][] gfxbuff, int palBase) {
        BufferedImage out = new BufferedImage(gfxbuff.length, gfxbuff[0].length, BufferedImage.TYPE_INT_RGB);
        for(int i = 0; i < gfxbuff.length; i++) {
            for(int j = 0; j < gfxbuff[i].length; j++) {
                out.setRGB(i, j, DataLists.getLevelPal()[gfxbuff[i][j]+palBase].getRGB());
            }
        }
        return out;
    }
}
