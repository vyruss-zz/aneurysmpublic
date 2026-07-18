package aneurysm.io;

import aneurysm.structures.LevelHeader;
import aneurysm.structures.Line;
import aneurysm.structures.WallStructure;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class DataExtraction {
    private FileReader reader;
    private String pack, folder;
    private Map<String, Object> resDefs;

    public DataExtraction(FileReader fileReader, String resPack, String targetFolder) {
        reader = fileReader;
        pack = resPack;
        folder = targetFolder;
    }

    public void extract() {
        Map<String, Object> lumps;
        String keydef;

        resDefs = new ObjectMapper().readValue(new File(pack), new TypeReference<Map<String, Object>>() {
        });

        if (!resDefs.containsKey("packResources")) {
            System.out.println("Pack resource not found");
        } else {
            folder = folder.concat("/bsdata");
            lumps = (Map<String, Object>) resDefs.get("packResources");
            try {
                Files.createDirectories(Path.of(folder));
                for (Map.Entry<String, Object> entry : lumps.entrySet()) {
                    keydef = entry.getKey();
                    if (keydef.equals("hud"))
                        buildHUDEntries((Map<?, ?>) lumps.get(keydef));

                    if (keydef.equals("intro"))
                        buildIntroSlide((Map<?, ?>) lumps.get(keydef));

                    if (keydef.equals("sprites"))
                        buildSpriteEntries((Map<?, ?>) lumps.get(keydef));

                    if (keydef.equals("textures"))
                        buildTextures((Map<?, ?>) lumps.get(keydef));

                    if (keydef.equals("maps"))
                        buildLevFiles((Map<?, ?>) lumps.get(keydef));

                    if (keydef.equals("actors"))
                        buildActorDefs((Map<?, ?>) lumps.get(keydef));
//                        System.out.println("dumping actors: keydef: " + keydef);

                    if (keydef.equals("sound"))
                        buildSFXBanks((Map<?, ?>) lumps.get(keydef));
//                        System.out.println("dumping sound: keydef: " + keydef);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void buildSpriteEntries(Map<?, ?> sprites) {
        Map<?, ?> lumps = (Map<?, ?>) sprites.get("gfx/sprites.dat");
        try {
            int offset = Integer.parseInt(lumps.get("offset").toString(), 16);
            int len = (int) lumps.get("length");
            int offst, offso;
            byte[] outbuff = new byte[len];
            ArrayList<String> spriteoffslist = (ArrayList<String>) lumps.get("spriteoffsets");
            ArrayList<String> thingoffslist = (ArrayList<String>) lumps.get("thingoffsets");
            RandomAccessFile rin = new RandomAccessFile(reader.getConfig().getLocation(), "r");
            RandomAccessFile out = new RandomAccessFile(folder + "/gfx/sprites.dat", "rw");

            rin.seek(offset);
            rin.read(outbuff);


            out.write(outbuff);

            for (String sprite : spriteoffslist) {
                offst = Integer.parseInt(sprite, 16) - offset;
                if ((offst & 0x00FFFFFF) > 0) {
                    out.seek((offst & 0x00ffffff) + 8);
                    offso = out.readInt();
//                    System.out.printf("%08x,sprites.dat#%08x", offst + offset, offst);
                    out.seek(offst + 8);
                    out.writeInt(offso);
//                    System.out.println();
                }

            }

            for (String thing : thingoffslist) {
                offst = Integer.parseInt(thing, 16) - offset;
                if ((offst & 0x00FFFFFF) > 0) {
                    out.seek(offst & 0x00ffffff);
//                    System.out.printf("%08x,sprites.dat#%08x", offst + offset, offst);
                    offso = out.readInt();
                    offso -= (offso > 0 ? offset : 0);
                    out.seek(offst);
                    out.writeInt(offso);
                }
//                System.out.println();
            }
            rin.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void buildHUDEntries(Map<?, ?> hud) {
        Map<?, ?> hudentry;
        try {
            Files.createDirectories(Path.of(folder + "/gfx/hud"));
            RandomAccessFile rin = new RandomAccessFile(reader.getConfig().getLocation(), "r");
            RandomAccessFile out;
            byte[] outbuff;
            int offset, len;
            for (Object key : hud.keySet()) {
                hudentry = (Map<?, ?>) hud.get(key);
                out = new RandomAccessFile(folder + "/" + key, "rw");
                offset = Integer.parseInt(hudentry.get("offset").toString(), 16);
                len = (int) hudentry.get("length");

                rin.seek(offset);
                outbuff = new byte[len];
                rin.read(outbuff);

                out.write(outbuff);
                out.close();
            }
            rin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildIntroSlide(Map<?, ?> intros) {
        Map<?, ?> introdefentry;
        try {
            Files.createDirectories(Path.of(folder + "/gfx/intro"));
            RandomAccessFile rin = new RandomAccessFile(reader.getConfig().getLocation(), "r");
            RandomAccessFile out;
            byte[] outbuff;
            byte r, g, b;
            int offset, len;
            for (Object key : intros.keySet()) {
                introdefentry = (Map<?, ?>) intros.get(key);
                out = new RandomAccessFile(folder + "/" + key, "rw");
                offset = Integer.parseInt(introdefentry.get("offset").toString(), 16);
                len = (int) introdefentry.get("length");
                if (introdefentry.containsKey("decompress")) {
                    outbuff = reader.decompressRNCData(offset, reader.getConfig().getLocation());
                } else if (key.toString().toLowerCase().endsWith(".pal")) {
                    rin.seek(offset);
                    outbuff = new byte[(len / 2) * 3];
                    for (int i = 0; i < outbuff.length; i += 3) {
                        b = rin.readByte();
                        g = rin.readByte();
                        r = (byte) ((g & 0x0f) * 16);
                        g = (byte) (g & 0xf0);
                        b = (byte) ((b * 16) & 0xf0);

                        b = (byte) (((b | (b / 16))) / 4);
                        g = (byte) (((g | (g / 16))) / 4);
                        r = (byte) (((r | (r / 16))) / 4);
                        outbuff[i] = r;
                        outbuff[i + 1] = g;
                        outbuff[i + 2] = b;
                    }
                } else {
                    rin.seek(offset);
                    outbuff = new byte[len];
                    rin.read(outbuff);
                }
                out.write(outbuff);
                out.close();
            }
            rin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildTextures(Map<?, ?> textures) {
        try {
            Files.createDirectories(Path.of(folder + "/gfx"));
            int toffs = Integer.parseInt((String) textures.get("offset"), 16);
            int len = (int) textures.get("length");
            byte[] buff = new byte[len];
            RandomAccessFile rin = new RandomAccessFile(reader.getConfig().getLocation(), "r");
            RandomAccessFile out = new RandomAccessFile(folder + "/gfx/textures.dat", "rw");

            rin.seek(toffs);
            rin.read(buff);

            out.write(buff);

            rin.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void buildLevFiles(Map<?, ?> levinfo) {
        try {

            Files.createDirectories(Path.of(folder + "/maps"));
            RandomAccessFile rin = new RandomAccessFile(reader.getConfig().getLocation(), "r");
            RandomAccessFile levout;
            short r, g, b;
            int headoffs = Integer.parseInt((String) levinfo.get("headeroffset"), 16);
            int numlevels = (int) levinfo.get("numlevels");
            int arenastart = (int) levinfo.get("arenastart");
            int texturebaseoffset = Integer.parseInt((String) levinfo.get("texturebaseoffset"), 16);
            int thingbaseoffset = Integer.parseInt((String) levinfo.get("thingbaseoffset"), 16);
            int toffs;
            byte[] minimap, mapgrid;
            LevelHeader[] headers = new LevelHeader[numlevels];
            LevelHeader curr;
            rin.seek(headoffs);
            //cache headers
            for (int i = 0; i < numlevels; i++) {
                headers[i] = new LevelHeader();
                headers[i].setNumLines(rin.readShort());
                headers[i].setLineoffs(rin.readInt());
                headers[i].setNumThings(rin.readShort());
                headers[i].setThingoffs(rin.readInt());
                headers[i].setNumLights(rin.readShort());
                headers[i].setNumDoors(rin.readShort());
                headers[i].setPaloffs(rin.readInt());
                headers[i].setNodeTimer(rin.readShort());
                headers[i].setNodeoffs(rin.readInt());
                headers[i].setMinimapXScale(rin.readShort());
                headers[i].setMinimapYScale(rin.readShort());
                headers[i].setMinimapXOffset(rin.readShort());
                headers[i].setMinimapWidth(rin.readShort());
                headers[i].setMinimapHeight(rin.readShort());
                headers[i].setMinimapoffs(rin.readInt());
                headers[i].setGridoffs(rin.readInt());
            }

            //build .lev files
            for (int i = 0; i < numlevels; i++) {
                levout = new RandomAccessFile(folder + "/maps/" + (i < arenastart ? "level" + (i + 1) : "arena" + (i - arenastart + 1)) + ".lev", "rw");
                curr = headers[i];
                //header
                levout.writeShort(curr.getNumLines());
                levout.writeInt(0x2c);
                levout.writeShort(curr.getNumThings());
                toffs = 0x2c + (headers[i].getNumLines() * 0x14);
                levout.writeInt(toffs);
                toffs += (headers[i].getNumThings() * 0x0a);
                levout.writeShort(curr.getNumLights());
                levout.writeShort(curr.getNumDoors());
                levout.writeInt(toffs);
                toffs += 192;
                levout.writeShort(curr.getNodeTimer());
                levout.writeInt(curr.getNodeoffs() - 0x62fe);
                levout.writeShort(curr.getMinimapXScale());
                levout.writeShort(curr.getMinimapYScale());
                levout.writeShort(curr.getMinimapXOffset());
                levout.writeShort(curr.getMinimapWidth());
                levout.writeShort(curr.getMinimapHeight());
                levout.writeInt(toffs);
                minimap = reader.decompressRNCData(curr.getMinimapoffs(), reader.getConfig().getLocation());
                toffs += minimap.length;
                mapgrid = reader.decompressRNCData(curr.getGridoffs(), reader.getConfig().getLocation());
                levout.writeInt(toffs);
                //lines
                rin.seek(curr.getLineoffs());
                for (int j = 0; j < headers[i].getNumLines(); j++) {
                    levout.writeInt(rin.readInt() - texturebaseoffset);
                    levout.writeShort(rin.readShort());
                    levout.writeShort(rin.readShort());
                    levout.writeShort(rin.readShort());
                    levout.writeShort(rin.readShort());
                    levout.writeShort(rin.readShort());
                    levout.writeShort(rin.readShort());
                    levout.writeShort(rin.readShort());
                    levout.writeShort(rin.readShort());
                }
                //things
                for (int j = 0; j < headers[i].getNumThings(); j++) {
                    levout.writeInt(rin.readInt() - thingbaseoffset);
                    levout.writeShort(rin.readShort());
                    levout.writeShort(rin.readShort());
                    levout.writeShort(rin.readShort());
                }
                //palette
                rin.seek(curr.getPaloffs());
                for (int j = 0; j < 64; j++) {
                    b = rin.readByte();
                    g = rin.readByte();
                    r = (short) ((g & 0x0f) * 16);
                    g = (short) (g & 0xf0);
                    b = (short) ((b * 16) & 0xf0);

                    b = (short) (((b | (b / 16))) / 4);
                    g = (short) (((g | (g / 16))) / 4);
                    r = (short) (((r | (r / 16))) / 4);

                    levout.writeByte(r);
                    levout.writeByte(g);
                    levout.writeByte(b);
                }

                levout.write(minimap);
                levout.write(mapgrid);
                levout.close();
            }


            //
            rin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void buildActorDefs(Map<?, ?> actors) {
        Map<?, ?> actordefs = (Map<?, ?>) actors.get("actordefs");
        Map<?, ?> tables = (Map<?, ?>) actors.get("spritetables");
        byte[] outbuff;
        int actoffs, projoffs, acttableoffs, playertableoffs;
        long toffs;
        int nodebase, actorbase, projbase, acttblbase, pltblbase, spritebase, acttbllen, pltbllen;
        try {
            RandomAccessFile rin = new RandomAccessFile(reader.getConfig().getLocation(), "r");
            RandomAccessFile out = new RandomAccessFile(folder + "/actors.dat", "rw");
            nodebase = Integer.parseInt((String) actordefs.get("nodedefs"), 16);
            actorbase = Integer.parseInt((String) actordefs.get("actordefs"), 16);
            projbase = Integer.parseInt((String) actordefs.get("projectiledefs"), 16);
            spritebase = Integer.parseInt((String) actordefs.get("spritebase"), 16);
            acttblbase = Integer.parseInt((String) tables.get("actorspritetable"), 16);
            pltblbase = Integer.parseInt((String) tables.get("playerspritetable"), 16);
            acttbllen = (int) tables.get("actorspritetablelen");
            pltbllen = (int) tables.get("playerspritetablelen");

            // collect and place raw data into actors.dat
            rin.seek(nodebase);
            outbuff = new byte[(int) actordefs.get("numnodes") * 0x1a];
            rin.read(outbuff);
            out.write(outbuff);

            actoffs = Math.toIntExact(out.getFilePointer());

            rin.seek(actorbase);
            outbuff = new byte[(int) actordefs.get("numactors") * 0x3a];
            rin.read(outbuff);
            out.write(outbuff);

            projoffs = Math.toIntExact(out.getFilePointer());

            rin.seek(projbase);
            outbuff = new byte[(int) actordefs.get("numprojectiles") * 0x14];
            rin.read(outbuff);
            out.write(outbuff);

            acttableoffs = Math.toIntExact(out.getFilePointer());

            rin.seek(acttblbase);
            outbuff = new byte[acttbllen];
            rin.read(outbuff);
            out.write(outbuff);

            playertableoffs = Math.toIntExact(out.getFilePointer());
            rin.seek(pltblbase);
            outbuff = new byte[pltbllen];
            rin.read(outbuff);
            out.write(outbuff);

            rin.close();

            //all done with the raw data, now we must go through the out data and update to file relative offsets
            out.seek(actoffs);
            for (int i = 0; i < (int) actordefs.get("numactors"); i++) {
//                System.out.printf("%08x: ", actorbase + (0x3a * i));
                out.skipBytes(4);
                toffs = out.readInt();
                if (toffs != 0) {
                    out.seek(out.getFilePointer() - 4);
                    toffs = (toffs - projbase) + projoffs;
                    out.writeInt((int) toffs);
//                    System.out.printf("actors.dat#%08x ", toffs);
                }
                out.skipBytes(34);

                //idle
                toffs = out.readInt();
                if (toffs != 0) {
//                    System.out.printf(" idleorg %08x ", toffs);
                    out.seek(out.getFilePointer() - 4);
                    toffs = (toffs - acttblbase) + acttableoffs;
//                    System.out.printf("actors.dat#%08x ", toffs);
                    out.writeInt((int) toffs);
                }

                //walk
                toffs = out.readInt();
                if (toffs != 0) {
//                    System.out.printf(" walkorg %08x ", toffs);
                    out.seek(out.getFilePointer() - 4);
                    toffs = (toffs - acttblbase) + acttableoffs;
//                    System.out.printf("actors.dat#%08x ", toffs);
                    out.writeInt((int) toffs);
                }

                //hit
                toffs = out.readInt();
                if (toffs != 0) {
//                    System.out.printf(" hitorg %08x ", toffs);
                    out.seek(out.getFilePointer() - 4);
                    toffs = (toffs - acttblbase) + acttableoffs;
//                    System.out.printf("actors.dat#%08x ", toffs);
                    out.writeInt((int) toffs);
                }

                //die
                toffs = out.readInt();
                if (toffs != 0) {
//                    System.out.printf(" dieorg %08x ", toffs);
                    out.seek(out.getFilePointer() - 4);
                    toffs = (toffs - acttblbase) + acttableoffs;
//                    System.out.printf("actors.dat#%08x ", toffs);
                    out.writeInt((int) toffs);
                }

//                System.out.println();
            }

            //projectiles
            out.seek(projoffs);
            for (int i = 0; i < (int) actordefs.get("numprojectiles"); i++) {
                out.skipBytes(6);
//                System.out.printf("%08x: ", (0x14 * i) + projbase);
                toffs = out.readInt();
                if (toffs != 0) {
//                    System.out.printf("  sprite org %08x ", toffs);
                    out.seek(out.getFilePointer() - 4);
                    toffs -= spritebase;
//                    System.out.printf("  sprites.dat#%08x ", toffs);
                    out.writeInt((int) toffs);
                }
                out.skipBytes(10);
//                System.out.println();
            }

            //actor frame tables
            out.seek(acttableoffs);
            for (int i = 0; i < acttbllen / 4; i++) {
                toffs = out.readInt();

//                System.out.printf("%08x: org %08x ", acttblbase + (4*i), toffs);

                //if > 0 and > spritetablebase
                if ((toffs != 0xffffffff && toffs != 0xfffffffe) && toffs > 0) {
                    out.seek(out.getFilePointer() - 4);
                    if (toffs > spritebase) {
                        toffs -= spritebase;
//                        System.out.printf(" sprites.dat#%08x", toffs);
                    } else if (toffs >= acttblbase && toffs < spritebase) {
                        toffs = (toffs - acttblbase) + acttableoffs;
//                        System.out.printf(" actors.dat#%08x", toffs);
                    }
                    out.writeInt((int) toffs);
                }
//                System.out.println();
            }

            //player frame tables
            out.seek(playertableoffs);
            for (int i = 0; i < pltbllen / 4; i++) {
                toffs = out.readInt() - spritebase;
                out.seek(out.getFilePointer() - 4);
                out.writeInt((int) toffs);
            }

            out.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void buildSFXBanks(Map<?, ?> sounds) {
        try {
            byte[] outbuff;
            Files.createDirectories(Path.of(folder + "/sfx"));
            RandomAccessFile rin = new RandomAccessFile(reader.getConfig().getLocation(), "r");
            RandomAccessFile out = new RandomAccessFile(folder + "/sfx/dbank.dat", "rw");
            rin.seek(Integer.parseInt((String) sounds.get("dbank"), 16));
            outbuff = new byte[(int) sounds.get("dbanklen")];
            rin.read(outbuff);
            out.write(outbuff);
            out.close();

            out = new RandomAccessFile(folder + "/sfx/pbank.dat", "rw");
            rin.seek(Integer.parseInt((String) sounds.get("pbank"), 16));
            outbuff = new byte[(int) sounds.get("pbanklen")];
            rin.read(outbuff);
            out.write(outbuff);
            out.close();

            out = new RandomAccessFile(folder + "/sfx/sbank.dat", "rw");
            rin.seek(Integer.parseInt((String) sounds.get("sbank"), 16));
            outbuff = new byte[(int) sounds.get("sbanklen")];
            rin.read(outbuff);
            out.write(outbuff);
            out.close();

            out = new RandomAccessFile(folder + "/gems.bin", "rw");
            rin.seek(Integer.parseInt((String) sounds.get("gems"), 16));
            outbuff = new byte[(int) sounds.get("gemslen")];
            rin.read(outbuff);
            out.write(outbuff);
            out.close();


            rin.close();
            out.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
