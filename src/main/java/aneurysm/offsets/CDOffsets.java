package aneurysm.offsets;

import java.util.ArrayList;

import aneurysm.ui.sprEd.offsets.SpriteDimension;

public class CDOffsets {

	private static ArrayList<SpriteDimension> dims = new ArrayList<SpriteDimension>();
	
	static {
		dims.add(new SpriteDimension(0x1e, 0x08)); // light 0c (0)
		dims.add(new SpriteDimension(0x2b, 0x0f));
		dims.add(new SpriteDimension(0x2b, 0x0f));
		dims.add(new SpriteDimension(0x15, 0x06));
		dims.add(new SpriteDimension(0x19, 0x2b)); // o2 tank
		dims.add(new SpriteDimension(0x1c, 0x2c));
		dims.add(new SpriteDimension(0x1c, 0x27));
		dims.add(new SpriteDimension(0x1e, 0x22)); // key

		dims.add(new SpriteDimension(0x1e, 0x22)); // key (8)
		dims.add(new SpriteDimension(0x1e, 0x22)); // white key
		dims.add(new SpriteDimension(0x36, 0x27)); // piercer
		dims.add(new SpriteDimension(0x2f, 0x26)); // lock on
		dims.add(new SpriteDimension(0x3b, 0x1f)); // cannon
		dims.add(new SpriteDimension(0x47, 0x1f)); // rapid
		dims.add(new SpriteDimension(0x38, 0x24)); // tribolt
		dims.add(new SpriteDimension(0x2f, 0x21)); // gun?

		dims.add(new SpriteDimension(0x48, 0x2a)); // piercer? (16)
		dims.add(new SpriteDimension(0x27, 0x25)); // another gunnn
		dims.add(new SpriteDimension(0x5a, 0x3d)); // king hell
		dims.add(new SpriteDimension(0x3a, 0x23)); // spray
		dims.add(new SpriteDimension(0x31, 0x3a)); // short barrel
		dims.add(new SpriteDimension(0x31, 0x46)); // tall barrel 0xd8
		dims.add(new SpriteDimension(0x33, 0x36)); // barrel remains
		dims.add(new SpriteDimension(0x24, 0x58)); // sentry initial frame

		dims.add(new SpriteDimension(0x1c, 0x66)); // sentry walk 1 (24)
		dims.add(new SpriteDimension(0x1c, 0x67)); // sentry walk 2
		dims.add(new SpriteDimension(0x23, 0x62)); // sentry walk 3
		dims.add(new SpriteDimension(0x20, 0x63)); // sentry walk 4
		dims.add(new SpriteDimension(0x1b, 0x66)); // sentry walk 5
		dims.add(new SpriteDimension(0x1b, 0x67)); // sentry walk 6
		dims.add(new SpriteDimension(0x23, 0x62)); // sentry walk 7
		dims.add(new SpriteDimension(0x20, 0x63)); // sentry walk 8

		dims.add(new SpriteDimension(0x28, 0x52)); // sentry damage (32)
		dims.add(new SpriteDimension(0x28, 0x56)); // sentry die 1
		dims.add(new SpriteDimension(0x28, 0x5b)); // die 2
		dims.add(new SpriteDimension(0x2f, 0x52)); // die 3
		dims.add(new SpriteDimension(0x2d, 0x5e)); // die 4
		dims.add(new SpriteDimension(0x2c, 0x5c)); // die 5
		dims.add(new SpriteDimension(0x2a, 0x5c)); // die 6
		dims.add(new SpriteDimension(0x2d, 0x4a)); // die 7

		dims.add(new SpriteDimension(0x2f, 0x21)); // die 8 (40)
		dims.add(new SpriteDimension(0x31, 0x20)); // dead sentry
		dims.add(new SpriteDimension(0x51, 0x80)); // node 0
		dims.add(new SpriteDimension(0x51, 0x80)); // node 1
		dims.add(new SpriteDimension(0x51, 0x80)); // node 2
		dims.add(new SpriteDimension(0x51, 0x80)); // node 3
		dims.add(new SpriteDimension(0x51, 0x80)); // node dmg
		dims.add(new SpriteDimension(0x59, 0x85)); // node chunk

		dims.add(new SpriteDimension(0x22, 0x23)); // node orb(48)
		dims.add(new SpriteDimension(0x22, 0x23)); // node orb 2
		dims.add(new SpriteDimension(0x22, 0x23)); // node orb 3
		dims.add(new SpriteDimension(0x1d, 0x1f)); // orb die 1
		dims.add(new SpriteDimension(0x22, 0x26)); // orb die 2
		dims.add(new SpriteDimension(0x28, 0x2b)); // orb die 3
		dims.add(new SpriteDimension(0x2a, 0x31)); // orb die 4
		dims.add(new SpriteDimension(0x2a, 0x31)); // orb die 5

		dims.add(new SpriteDimension(0x25, 0x64)); // shield guy init (56)
		dims.add(new SpriteDimension(0x26, 0x66)); // walk 1
		dims.add(new SpriteDimension(0x25, 0x64)); // walk 2
		dims.add(new SpriteDimension(0x26, 0x63)); // walk 3
		dims.add(new SpriteDimension(0x25, 0x64)); // walk 4
		dims.add(new SpriteDimension(0x24, 0x66)); // walk 5
		dims.add(new SpriteDimension(0x25, 0x64)); // walk 6
		dims.add(new SpriteDimension(0x24, 0x63)); // walk 7

		dims.add(new SpriteDimension(0x2a, 0x5d)); // pain 1(64)
		dims.add(new SpriteDimension(0x2a, 0x5d)); // pain 2
		dims.add(new SpriteDimension(0x25, 0x5e)); // pain 3
		dims.add(new SpriteDimension(0x30, 0x59)); // shield start
		dims.add(new SpriteDimension(0x30, 0x59)); // shield glow
		dims.add(new SpriteDimension(0x30, 0x59)); // shield up
		dims.add(new SpriteDimension(0x35, 0x68)); // die 1
		dims.add(new SpriteDimension(0x37, 0x5f)); // die 2

		dims.add(new SpriteDimension(0x36, 0x3a)); // die 3 (72)
		dims.add(new SpriteDimension(0x38, 0x2e)); // die 4
		dims.add(new SpriteDimension(0x35, 0x26)); // dead shield guy
		dims.add(new SpriteDimension(0x59, 0x5f)); // brain guy 1
		dims.add(new SpriteDimension(0x59, 0x66)); //
		dims.add(new SpriteDimension(0x57, 0x68));
		dims.add(new SpriteDimension(0x62, 0x67));
		dims.add(new SpriteDimension(0x6a, 0x44));

		dims.add(new SpriteDimension(0x6c, 0x28)); // (80)
		dims.add(new SpriteDimension(0x57, 0x68));
		dims.add(new SpriteDimension(0x59, 0x64)); // walker 1
		dims.add(new SpriteDimension(0x59, 0x73)); // walker pain
		dims.add(new SpriteDimension(0x65, 0x7b));
		dims.add(new SpriteDimension(0x7a, 0x4a));
		dims.add(new SpriteDimension(0x7b, 0x3e));
		dims.add(new SpriteDimension(0x59, 0x6d));

		dims.add(new SpriteDimension(0x59, 0x6a)); // (88)
		dims.add(new SpriteDimension(0x59, 0x6b));
		dims.add(new SpriteDimension(0x59, 0x70));
		dims.add(new SpriteDimension(0x59, 0x6d));
		dims.add(new SpriteDimension(0x59, 0x68));
		dims.add(new SpriteDimension(0x59, 0x6b));
		dims.add(new SpriteDimension(0x59, 0x70));
		dims.add(new SpriteDimension(0x22, 0x5a));

		dims.add(new SpriteDimension(0x24, 0x51)); // (96)
		dims.add(new SpriteDimension(0x24, 0x55));
		dims.add(new SpriteDimension(0x27, 0x5a));
		dims.add(new SpriteDimension(0x29, 0x56));
		dims.add(new SpriteDimension(0x29, 0x4c));
		dims.add(new SpriteDimension(0x2a, 0x3d));
		dims.add(new SpriteDimension(0x2b, 0x2e));
		dims.add(new SpriteDimension(0x2b, 0x2a));

		dims.add(new SpriteDimension(0x2a, 0x22)); // (104)
		dims.add(new SpriteDimension(0x2d, 0x21));
		dims.add(new SpriteDimension(0x22, 0x58));
		dims.add(new SpriteDimension(0x22, 0x59));
		dims.add(new SpriteDimension(0x22, 0x5b));
		dims.add(new SpriteDimension(0x23, 0x59));
		dims.add(new SpriteDimension(0x22, 0x58));
		dims.add(new SpriteDimension(0x22, 0x59));

		dims.add(new SpriteDimension(0x22, 0x5b)); // (112)
		dims.add(new SpriteDimension(0x23, 0x59));
		dims.add(new SpriteDimension(0x70, 0x7c)); // cd enemy init
		dims.add(new SpriteDimension(0x70, 0x79)); // frame 1
		dims.add(new SpriteDimension(0x75, 0x8f)); // pain
		dims.add(new SpriteDimension(0x75, 0x8f)); // pain 2
		dims.add(new SpriteDimension(0x72, 0x85)); // pain 3
		dims.add(new SpriteDimension(0x80, 0x8f)); // die 1

		dims.add(new SpriteDimension(0x86, 0x75)); // die 2(120)
		dims.add(new SpriteDimension(0x8b, 0x4d)); // die 3
		dims.add(new SpriteDimension(0x8d, 0x39)); // die final
		dims.add(new SpriteDimension(0x78, 0x97));
		dims.add(new SpriteDimension(0x78, 0x8a));
		dims.add(new SpriteDimension(0x7a, 0x8f));
		dims.add(new SpriteDimension(0x65, 0x91));
		dims.add(new SpriteDimension(0x65, 0x92));

		dims.add(new SpriteDimension(0x6e, 0x90)); // (128)
		dims.add(new SpriteDimension(0x58, 0x95));
		dims.add(new SpriteDimension(0x5e, 0x8f));
		dims.add(new SpriteDimension(0x73, 0x97));
		dims.add(new SpriteDimension(0x6d, 0x8d));
		dims.add(new SpriteDimension(0x80, 0x6c));
		dims.add(new SpriteDimension(0x8b, 0x43));
		dims.add(new SpriteDimension(0x92, 0x30));

		dims.add(new SpriteDimension(0x3c, 0x6e)); // (136)
		dims.add(new SpriteDimension(0x52, 0x5d));
		dims.add(new SpriteDimension(0x5f, 0x3b));
		dims.add(new SpriteDimension(0x61, 0x2a));
		dims.add(new SpriteDimension(0x34, 0x6b));
		dims.add(new SpriteDimension(0x45, 0x53));
		dims.add(new SpriteDimension(0x4d, 0x34));
		dims.add(new SpriteDimension(0x4d, 0x2d));

		dims.add(new SpriteDimension(0x25, 0x57)); // fire guy dormant (144)
		dims.add(new SpriteDimension(0x21, 0x56)); // fire guy walk 1
		dims.add(new SpriteDimension(0x22, 0x57)); // fire guy walk 2
		dims.add(new SpriteDimension(0x23, 0x5b)); // fire guy walk 3
		dims.add(new SpriteDimension(0x23, 0x58)); // fire guy walk 4
		dims.add(new SpriteDimension(0x22, 0x55)); // fire guy walk 5
		dims.add(new SpriteDimension(0x22, 0x57)); // fire guy walk 6
		dims.add(new SpriteDimension(0x23, 0x5b)); // fire guy walk 7

		dims.add(new SpriteDimension(0x23, 0x58)); // fire guy walk 8 (152)
		dims.add(new SpriteDimension(0x28, 0x4f)); // fire guy hit
		dims.add(new SpriteDimension(0x31, 0x51)); // fire guy die 1
		dims.add(new SpriteDimension(0x28, 0x54)); // die 2
		dims.add(new SpriteDimension(0x2a, 0x56)); // die 3
		dims.add(new SpriteDimension(0x2d, 0x4f)); // die 4
		dims.add(new SpriteDimension(0x2f, 0x3c)); // die 5
		dims.add(new SpriteDimension(0x31, 0x3c)); // die 6

		dims.add(new SpriteDimension(0x17, 0x1b)); // mine 1(160)
		dims.add(new SpriteDimension(0x17, 0x1b)); // mine 2
	}
	
	private static int[] offsets = new int[0x288/4];
	public static int[] getSpriteOffsets() {
		return offsets;
	}
	
	public static ArrayList<SpriteDimension> getSpriteDimensions() {
		return dims;
	}
}
