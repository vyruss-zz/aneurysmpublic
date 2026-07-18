package aneurysm.io;

import aneurysm.config.ConfigOptions;
import aneurysm.core.AneurysmLauncher;
import aneurysm.editor.EditorControls;
import aneurysm.structures.RNC2Header;
import aneurysm.ui.ComponentLauncher;
import aneurysm.ui.Window;

import java.io.IOException;
import java.io.ObjectInputFilter;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class JaRNC {

    private static boolean log = false;

    private static byte[] output;
    private static byte[] input;
    private static short padding = 0;
    private static int bitbuf;
    private static int posInput, posOutput, posTemp, pos, len;

    private static boolean zFlag = false, cFlag = false, xFlag = false;

    public static byte[] decompress(byte[] in, int outlen) {
        input = in;
        output = new byte[outlen];
        bitbuf = 0;
        posInput = 0;
        posOutput = 0;
        posTemp = 0;
        pos = 0;
        len = 0;

        Arrays.fill(output, (byte) 255);


        unpack();

        return output;
    }

    private static void unpack() {
        if (log)
            System.out.println("unpack");
        bitbuf = -0x80;
//		xFlag=true;
        if (log)
            System.out.println("bitbuf: " + Integer.toHexString(bitbuf) + " b "
                    + String.format("%16s", Integer.toBinaryString((short) bitbuf)));
        bitbuf = addb((short) bitbuf, (short) bitbuf);
        if (log)
            System.out.println("bitbuf: " + Integer.toHexString(bitbuf) + " b "
                    + String.format("%16s", Integer.toBinaryString((short) bitbuf)).replace(" ", "0"));
        reload();
        getbit();
        short next = 0x26;
        while (next != (short) 0xffff) {
            switch (next) {
                case 0x01:
                    next = Another();
                    break;
                case 0x02:
                    next = Back0();
                    break;
                case 0x03:
                    next = Back1();
                    break;
                case 0x04:
                    next = Back10();
                    break;
                case 0x05:
                    next = Back11();
                    break;
                case 0x06:
                    next = Back12();
                    break;
                case 0x07:
                    next = Back2();
                    break;
                case 0x08:
                    next = Back3();
                    break;
                case 0x09:
                    next = Back4();
                    break;
                case 0x0a:
                    next = Back5();
                    break;
                case 0x0b:
                    next = Back6();
                    break;
                case 0x0c:
                    next = Back7();
                    break;
                case 0x0d:
                    next = Back8();
                    break;
                case 0x0e:
                    next = Back9();
                    break;
                case 0x0f:
                    next = BigDisp();
                    break;
                case 0x10:
                    next = ByteDisp();
                    break;
                case 0x11:
                    next = ByteDisp2();
                    break;
                case 0x12:
                    next = ByteDisp3();
                    break;
                case 0x13:
                    next = ByteDisp4();
                    break;
                case 0x14:
                    next = ByteDisp5();
                    break;
                case 0x15:
                    next = Check4end();
                    break;
                case 0x16:
                    next = Chkz();
                    break;
                case 0x17:
                    next = Copy();
                    break;
                case 0x18:
                    next = Fetch0();
                    break;
                case 0x19:
                    next = Fetch1();
                    break;
                case 0x1a:
                    next = Fetch10();
                    break;
                case 0x1b:
                    next = Fetch11();
                    break;
                case 0x1c:
                    next = Fetch12();
                    break;
                case 0x1d:
                    next = Fetch2();
                    break;
                case 0x1e:
                    next = Fetch3();
                    break;
                case 0x1f:
                    next = Fetch4();
                    break;
                case 0x20:
                    next = Fetch5();
                    break;
                case 0x21:
                    next = Fetch6();
                    break;
                case 0x22:
                    next = Fetch7();
                    break;
                case 0x23:
                    next = Fetch8();
                    break;
                case 0x24:
                    next = Fetch9();
                    break;
                case 0x25:
                    next = GetBits();
                    break;
                case 0x26:
                    next = GetBits2();
                    break;
                case 0x27:
                    next = GetLen();
                    break;
                case 0x28:
                    next = OverNout();
                    break;
                case 0x29:
                    next = Raw();
                    break;
                case 0x2a:
                    next = Smalls();
                    break;
                case 0x2b:
                    next = String();
                    break;
                case 0x2c:
                    next = x4Bits();
                    break;
                case 0x2d:
                    next = xByte();
                    break;
            }
        }
    }

    private static byte addxb(byte s, byte d) {
        if (log)
            System.out.println("addxb " + Integer.toHexString(s) + ", " + Integer.toHexString(d));
        byte result = 0;
        if (xFlag)
            result = 1;
        result += addb(s, d);
//			if(xFlag) {
//				result+=1;
//				xFlag=false;
//			}
        return result;
    }

    private static short addxw(short s, short d) {
        short result = 0;
        if (xFlag)
            result = 1;
        result += addw(s, d);
        return result;
    }

    private static void reload() {
        if (log)
            System.out.println("reload");
        // if(log)System.out.println(String.format("input at current Index %02x",
        // input[posInput]));
//			bitbuf = (bitbuf&0xffffff00) + (input[posInput++]&0x000000ff);
        bitbuf = input[posInput++];
        if (log)
            System.out.println(String.format("posInput(a0) %04x posOutput(a1) %04x", posInput, posOutput));
        if (log)
            System.out.println(String.format("bitbuf after reload step 1: %08x b ", bitbuf)
                    + String.format("%16s", Integer.toBinaryString((short) bitbuf)).replace(" ", "0"));
        bitbuf = addxb((byte) bitbuf, (byte) bitbuf);
        if (log)
            System.out.println("bitbuf after reload step 2: " + Integer.toHexString(bitbuf) + " b "
                    + String.format("%16s", Integer.toBinaryString((short) bitbuf)).replace(" ", "0"));
        // d3=0;
    }

    private static short Back0() {
        if (log)
            System.out.println("Back0");
        if (log)
            System.out.println(String.format("pos at start of Back0 %08x", pos));
        len &= 0x0000ffff;
        while ((short) len > -1) {
            if (log)
                System.out.println(String.format("Back0 len %08x", len));
            pos = addxw((short) pos, (short) pos);
            if (log)
                System.out.println(String.format("pos after addxw %08x", pos));
//				return 0x2c;
            len--;
            if ((short) len > -1)
                bitbuf = addb((byte) bitbuf, (byte) bitbuf);
            if (zFlag) {
                if (log)
                    System.out.println("broke in Back0");
                return 0x18;
            }
            if (log)
                System.out.println(String.format("Back0 len %08x after exec, restarting", len));
        }
        pos += 2;
        getrawREP();
        return 0x26;
    }

    private static short GetLen() {
        if (log)
            System.out.println("GetLen");
        getbit();
        if (zFlag)
            return 0x19;
        else
            return 0x03;
    }

    private static short Back1() {
        if (log)
            System.out.println("Back1");
        len = addxw((short) len, (short) len);
        if (log)
            System.out.println("len after addxw " + String.format(" %08x", len));
        getbit();
        if (zFlag)
            return 0x1d;
        else
            return 0x07;
    }

    private static short Another() {
        getbit();
        if (zFlag)
            return 0x22;
        else
            return 0x0c;
    }

    private static short Copy() {
        if (log)
            System.out.println("Copy");
        getbit();
        if (zFlag) {
            return 0x1f;
        } else {
            return 0x09;
        }
    }

    private static short Back2() {
        if (log)
            System.out.println("Back2");
        if (!cFlag) {
            return 0x17;
        } else {
            len -= 1;
            getbit();
            if (zFlag) {
                return 0x1e;
            } else {
                return 0x08;
            }
        }
    }

    private static short Back3() {
        if (log)
            System.out.println("Back3");
        len = addxw((short) len, (short) len);
        if (log)
            System.out.println(String.format("Back 3 - len after addxw %08x", len));
        if ((byte) len - 9 == 0) {
            return 0x29;
        } else {
            return 0x17;
        }
    }

    private static short Back4() {
        if (log)
            System.out.println("Back4");
        if (!cFlag) {
            return 0x11;
        } else {
            getbit();
            if (zFlag) {
                return 0x20;
            } else {
                return 0x0a;
            }
        }
    }

    private static short Back5() {
        if (log)
            System.out.println("Back5");
        pos = addxw((short) pos, (short) pos);
        if (log)
            System.out.println(String.format("pos after addxw in back5 %04x", pos));
        getbit();
        if (zFlag) {
            return 0x21;
        } else
            return 0x0b;
    }

    private static short Back6() {
        if (log)
            System.out.println("Back6");
        if (cFlag)
            return 0x0f;
        else {
            if ((pos & 0x000000ff) != 0) {
                return 0x10;
            } else {
                if ((pos & 0x000000ff) == 0xff) {
                    xFlag = true;
                    zFlag = true;
                    cFlag = true;
                } else {
                    xFlag = false;
                    zFlag = false;
                    cFlag = false;
                }
                pos++;
                return 0x01;
            }
        }
    }

    private static short BigDisp() {
        if (log)
            System.out.println("BigDisp");
        getbit();
        if (zFlag) {
            return 0x1b;
        } else {
            return 0x05;
        }
    }

    private static short rotateLeft(short n, short d) {
        if (log)
            System.out.println("rol " + String.format(" n %04x d %04x result %04x", n, d, Integer.rotateLeft(n, d)));
        if ((n & 8000) != 0) {
            xFlag = true;
            cFlag = true;
        }
        return (short) ((n << d) | (n >> (8 - d)));
    }

    private static short Back7() {
        pos = addxw((short) pos, (short) pos);
        return 0x10;
    }

    private static short ByteDisp() {
        pos = rotateLeft((short) pos, (short) 8);
        return 0x11;
    }

    private static int lsr(short s, short l) {
        if ((s & 0b00000001) == 1) {
            cFlag = true;
            xFlag = true;
        } else {
            cFlag = false;
        }
        if (log)
            System.out.println("s " + Integer.toBinaryString(s) + " post " + Integer.toBinaryString(s >> 1) + " anded "
                    + (s & 0b00000001));
        if (log)
            System.out.println(
                    "cFlag " + cFlag + String.format(" result %04x s %08x l %08x", ((byte) s >> (byte) l), s, l));
        return (s >>= l);
    }

    private static short ByteDisp2() {
        if (log)
            System.out.println("ByteDisp2");
        pos = (pos & 0xFFFFFF00) + (input[posInput++] & 0x000000ff);
        if ((pos & 0xffffff00) == 0xffffff00)
            pos &= 0x000000ff;
        if (log)
            System.out.println(String.format("pos %08x posoutput %08x postemp %08x", pos, posOutput, posTemp));
        posTemp = posOutput;
        if (log)
            System.out.println(String.format("postemp as posoutput %08x", posTemp));
        if ((short) ((short) posTemp - (short) pos) < 0) {
            if (log)
                System.out.println("less than zero after adjustment");
            cFlag = true;
            xFlag = true;
        } else if ((short) ((short) posTemp - (short) pos) == 0) {
            cFlag = false;
            xFlag = false;
            zFlag = true;
        }
        posTemp = posTemp - (pos);
        if (log)
            System.out.println(String.format("postemp - pos %08x", posTemp));
        if ((short) ((short) posTemp - 1) < 0) {
            if (log)
                System.out.println("less than zero after -1 ");
            cFlag = true;
            xFlag = true;
        } else if ((short) ((short) posTemp - 1) == 0) {
            cFlag = false;
            xFlag = false;
            zFlag = true;
        }
        posTemp -= 1;
        if (log)
            System.out.println(String.format("postemp -1 %08x", posTemp));
//			len >>= 1;
        len = lsr((short) len, (short) 1);
        if ((short) len == 0)
            zFlag = true;
        if (log)
            System.out.println(String.format("len after lsr %08x zFlag ", len) + zFlag + " cFlag " + cFlag);
        if (cFlag) {
            output[posOutput++] = (byte) output[posTemp++];
            if (log)
                System.out.println(String.format("posInput(a0) %04x posOutput(a1) %04x", posInput, posOutput));
            zFlag = (output[posOutput - 1] == 0);
            if (log)
                System.out.println("cFlag end of ByteDisp2 is false - zFlag " + zFlag
                        + String.format(" output was set to %08x", output[posOutput - 1]));
        }
        return 0x12;
    }

    private static short ByteDisp3() {
        if (log)
            System.out.println("ByteDisp3");
        len -= 1;
        if (pos != 0) {
            return 0x14;
        } else {
            pos = (pos & 0xFFFFFF00) + (output[posTemp] & 0x000000ff);
            if ((pos & 0xFFFFFF00) == 0xFFFFFF00)
                pos &= 0x000000ff;
            return 0x13;
        }
    }

    private static short ByteDisp4() {
        if (log)
            System.out.println("ByteDisp4");
        if (log)
            System.out.println(String.format("len in Bytedisp4 %08x", len));
        len &= 0x0000ffff;
        while ((short) len > -1) {
            output[posOutput++] = (byte) pos;
            output[posOutput++] = (byte) pos;
            len--;
        }
        if(log)
            System.out.println(String.format("posouput after bytedisp4 %08x posinput %08x postemp %08x", posOutput,
                posInput, posTemp));
        return 0x26;
    }

    private static short ByteDisp5() {
        if (log) {
            System.out.println("ByteDisp5");
            System.out.println(String.format("len in Bytedisp5 %08x", len));
            System.out.println(String.format(
                    "posouput start of bytedisp5 %08x posinput %08x postemp %08x len %08x pos %08x bitbuf %08x",
                    posOutput, posInput, posTemp, len, pos, bitbuf));
        }
        len &= 0x0000ffff;
        while ((short) len > -1) {
            output[posOutput++] = output[posTemp++];
            output[posOutput++] = output[posTemp++];
            len--;
        }
        if (log)
            System.out.println(String.format("posouput after bytedisp5 %08x posinput %08x postemp %08x", posOutput,
                    posInput, posTemp));
        return 0x26;
    }

    private static short Smalls() {
        if (log)
            System.out.println("Smalls");
        getbit();
        if (zFlag) {
            return 0x24;
        } else {
            return 0x0e;
        }
    }

    private static short Back8() {
        if (log)
            System.out.println("Back8");
        if (!cFlag) {
            return 0x27;
        } else {
            return 0x2a;
        }
    }

    private static short Back9() {
        if (log)
            System.out.println("Back9");
        if (!cFlag) {
            return 0x11;
        } else {
            if ((len & 0x000000FF) < 0xff) {
                cFlag = false;
                zFlag = false;
                xFlag = false;
            } else {
                cFlag = true;
                zFlag = true;
                xFlag = true;
            }
            len = addw((short) len, (short) 1);

            if (log)
                System.out.println(String.format("Back9 len %08x", len));
            getbit();
            if (zFlag) {
                return 0x1a;
            } else {
                return 0x04;
            }
        }
    }

    private static short Back10() {
        if (log)
            System.out.println("Back10 - cFlag " + cFlag + " - zFlag " + zFlag);
        if (!cFlag) {
            return 0x17;
        } else {
            len = (len & 0xffffff00) + (input[posInput++] & 0x000000ff);
            if ((len & 0xffffff00) == 0xffffff00)
                len &= 0x000000ff;
            if (log)
                System.out.println(String.format("posInput(a0) %04x posOutput(a1) %04x", posInput, posOutput));
            if (len == 0)
                zFlag = true;
            else
                zFlag = false;
            if (log)
                System.out.println("len after move " + String.format("%08x", len));
            if (zFlag || (len & 0x000000FF) == 0) {
                return 0x28;
            } else {
                if ((len & 0x000000FF) >= 0xf8) {
                    zFlag = true;
                    cFlag = true;
                    xFlag = true;
                } else {
                    zFlag = false;
                    cFlag = false;
                    xFlag = false;
                }
                len += 8;
                return 0x17;
            }
        }
    }

    private static short Back11() {
        if (log)
            System.out.println("Back11");
        pos = addxw((short) pos, (short) pos);
        pos = (pos & 0xFFFF0000) + ((short) pos | 4);
        if (log)
            System.out.println("pos after or 4" + String.format(" %08x", pos));
        getbit();
        if (zFlag) {
            return 0x1c;
        } else {
            return 0x06;
        }
    }

    private static short Back12() {
        if (log)
            System.out.println("Back12");
        if (log)
            System.out.println("Back12 - cFlag " + cFlag);
        if (cFlag) {
            return 0x10;
        } else {
            return 0x01;
        }
    }

    private static short OverNout() {
        if (log)
            System.out.println("OverNout");
        getbit();
        if (!zFlag || (byte) bitbuf != 0) {
            return 0x15;
        } else {
            reload();
            return 0x15;
        }
    }

    private static short Check4end() {
        if (log)
            System.out.println("Check4end");
//			return;
        if (cFlag) {
            return 0x26;
        } else
            return (short) 0xffff;
    }

    private static short Fetch0() {
        if (log)
            System.out.println("Fetch0");
        reload();
        return 0x02;
    }

    private static short Fetch1() {
        if (log)
            System.out.println("Fetch1");
        if (log)
            System.out.println(String.format("pos in Fetch1 %08x - len %08x - bitbuf %08x", pos, len, bitbuf));
        reload();
        return 0x03;
    }

    private static short Fetch2() {
        if (log)
            System.out.println("Fetch2");
        if (log)
            System.out.println(String.format("pos in Fetch2 %08x - len %08x - bitbuf %08x", pos, len, bitbuf));
        reload();
        return 0x07;
    }

    private static short Fetch3() {
        reload();
        return 0x08;
    }

    private static short Fetch4() {
        if (log)
            System.out.println("Fetch4");
        reload();
        return 0x09;
    }

    private static short Fetch5() {
        if (log)
            System.out.println("Fetch5");
        reload();
        return 0x0a;
    }

    private static short Fetch6() {
        if (log)
            System.out.println("Fetch6");
        reload();
        return 0x0b;
    }

    private static short Fetch7() {
        if (log)
            System.out.println("Fetch7");
        reload();
        return 0x0c;
    }

    private static short Fetch8() {
        if (log)
            System.out.println("Fetch8");
        reload();
        return 0x0d;
    }

    private static short Fetch9() {
        if (log)
            System.out.println("Fetch9");
        reload();
        return 0x0e;
    }

    private static short Fetch10() {
        if (log)
            System.out.println("Fetch10");
        reload();
        return 0x04;
    }

    private static short Fetch11() {
        if (log)
            System.out.println("Fetch11");
        reload();
        return 0x05;
    }

    private static short Fetch12() {
        if (log)
            System.out.println("Fetch12");
        reload();
        return 0x06;
    }

    private static short addw(short b1, short b2) {
        b1 &= 0x0000ffff;
        b2 &= 0x0000ffff;
        int result = (int) (b1 + b2);
        cFlag = ((result & 0xFFFF0000) != 0);
        if ((result & 0xFFFF0000) == 0)
            xFlag = false;
        else
            xFlag = true;
//			xFlag = ((result & 0xFFFF0000) != 0);
        zFlag = ((result & 0x0000FFFF) == 0 && (b1 != 0 && b2 != 0));
//			if(cFlag) if(log)System.out.println("C Set");
//			if(zFlag) if(log)System.out.println("Z Set");
        return (short) (result);
    }

    private static byte addb(short b1, short b2) {
        b1 &= 0x00ff;
        b2 &= 0x00ff;
        int result = ((short) b1 + (short) b2);
        if (log)
            System.out.print(String.format("b1 %02x b2 %02x - result in addb %04x   -   ", b1, b2, b1 + b2));
        cFlag = ((result & 0xFF00) != 0);
        if ((result & 0xFF00) == 0)
            xFlag = false;
        else
            xFlag = true;
        zFlag = ((result & 0x00FF) == 0 && ((byte) b1 != 0 && (byte) b2 != 0));
        if (cFlag)
            if (log)
                System.out.print(" -   C Set   - ");
            else if (log)
                System.out.print(" -   C Clear   - ");
        if (zFlag)
            if (log)
                System.out.print(" -    Z Set  - ");
            else if (log)
                System.out.print(" -    Z Clear  - ");
        if (xFlag)
            if (log)
                System.out.print(" -    X Set  - ");
            else if (log)
                System.out.print(" -    X Clear  - ");
        return (byte) (result);
    }

    private static void getbit() {
        if (log)
            System.out.println("getbit");
        bitbuf = addb((byte) bitbuf, (byte) bitbuf);
        if (log)
            System.out.println(String.format("bitbuf in getbit %08x b ", bitbuf)
                    + String.format("%16s", Integer.toBinaryString((short) bitbuf)).replace(" ", "0"));
    }

    private static short GetBits2() {
        if (log)
            System.out.println("GetBits2");
        getbit();
        if (cFlag) {
            return 0x16;
        } else {
            getraw();
            getbit();
            if (!cFlag) {
                return 0x2d;
            } else {
                return 0x16;
            }
        }
    }

    private static void getrawREP() {
        if (log)
            System.out.println("getrawREP");
        if (log)
            System.out.println(String.format("posInput(a0) %04x posOutput(a1) %04x", posInput, posOutput));
        if (log)
            System.out.println(String.format("pos at start of getrawREP %08x", pos));
        pos &= 0x0000ffff;
        while ((short) pos > -1) {
            output[posOutput++] = input[posInput++];
            if (log)
                System.out.println(String.format("posInput(a0) %04x posOutput(a1) %04x", posInput, posOutput));
            output[posOutput++] = input[posInput++];
            if (log)
                System.out.println(String.format("posInput(a0) %04x posOutput(a1) %04x", posInput, posOutput));
            output[posOutput++] = input[posInput++];
            if (log)
                System.out.println(String.format("posInput(a0) %04x posOutput(a1) %04x", posInput, posOutput));
            output[posOutput++] = input[posInput++];
            if (log)
                System.out.println(String.format("posInput(a0) %04x posOutput(a1) %04x", posInput, posOutput));
            pos--;
        }
    }

    private static short Raw() {
        if (log)
            System.out.println("Raw");
        len = 3;
        if (log)
            System.out.println(String.format("raw len %08x", len));
        return 0x2c;
    }

    private static short x4Bits() {
        if (log)
            System.out.println("x4Bits");
        bitbuf = addb((byte) bitbuf, (byte) bitbuf);
        if (log)
            System.out.println(String.format("bitbuf after addb in x4Bits %08x", bitbuf));
//			len--;
        if ((bitbuf & 0x000000ff) == 0)
            return 0x18;
        else
            return 0x02;
    }

    private static short String() {
        if (log)
            System.out.println("String");
        len = 2;
        cFlag = false;
        pos = 0;
        zFlag = true;
        getbit();
        if (zFlag) {
            return 0x23;
        } else {
            return 0x0d;
        }
    }

    private static void getraw() {
        if (log)
            System.out.print("getraw");
//			if(log)System.out.print(String.format("posOutput %08x output[posOutput] %08x posInput %08x input[posInput] %08x", posOutput,output[posOutput],posInput,input[posInput]));

        output[posOutput++] = input[posInput++];
        if (log)
            System.out.println(String.format("posInput(a0) %04x posOutput(a1) %04x", posInput, posOutput));

        zFlag = (output[posOutput - 1] == 0);
//			if(log)System.out.println("output current is 0? "+ (output[posOutput-1] == 0));
        if (zFlag)
            if (log)
                System.out.println(" - zFlag set");
            else if (log)
                System.out.println(" - zFlag not set");
    }

    private static short xByte() {
        if (log)
            System.out.println("xByte");
        getraw();
        return 0x26;
    }

    private static short GetBits() {
        if (log)
            System.out.println("GetBits");
        reload();
        if (cFlag) {
            return 0x2b;
        } else
            return 0x2d;
    }

    private static short Chkz() {
        if (log)
            System.out.println("Chkz");
        if (zFlag) {
            return 0x25;
        } else
            return 0x2b;
    }
}
