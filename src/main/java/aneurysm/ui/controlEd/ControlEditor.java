package aneurysm.ui.controlEd;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import aneurysm.ui.Window;
import aneurysm.ui.controlEd.capture.CapturePane;

public class ControlEditor extends JPanel {
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel12;
	private javax.swing.JLabel jLabel13;
	private javax.swing.JLabel jLabel14;
	private javax.swing.JLabel jLabel15;
	private javax.swing.JLabel jLabel16;
	private javax.swing.JLabel jLabel17;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JButton kGridBtn;
	private javax.swing.JButton kHighBtn;
	private javax.swing.JButton kLinesBtn;
	private javax.swing.JButton kPanBtn;
	private javax.swing.JButton kRotBtn;
	private javax.swing.JButton kSaveBtn;
	private javax.swing.JButton kSelectBtn;
	private javax.swing.JButton kSnapBtn;
	private javax.swing.JButton kThingsBtn;
	private javax.swing.JButton kVerticesBtn;
	private javax.swing.JButton kZoomInBtn;
	private javax.swing.JButton kZoomOutBtn;
	private javax.swing.JButton mMoveBtn;
	private javax.swing.JButton mSelectBtn;
	private javax.swing.JButton mPanBtn;
	private static final long serialVersionUID = -8635012649987300945L;

//	private JFrame host;

	public ControlEditor() {

		this.setLayout(new GridLayout(20, 4));
//		this.host = host;
		initComponents();
	}

	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		mPanBtn = new javax.swing.JButton();
		mSelectBtn = new javax.swing.JButton();
		mMoveBtn = new javax.swing.JButton();
		jLabel5 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		kGridBtn = new javax.swing.JButton();
		kZoomInBtn = new javax.swing.JButton();
		kZoomOutBtn = new javax.swing.JButton();
		kLinesBtn = new javax.swing.JButton();
		kThingsBtn = new javax.swing.JButton();
		kVerticesBtn = new javax.swing.JButton();
		kSnapBtn = new javax.swing.JButton();
		kPanBtn = new javax.swing.JButton();
		kSelectBtn = new javax.swing.JButton();
		kHighBtn = new javax.swing.JButton();
		kRotBtn = new javax.swing.JButton();
		kSaveBtn = new javax.swing.JButton();
		jLabel7 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		jLabel9 = new javax.swing.JLabel();
		jLabel10 = new javax.swing.JLabel();
		jLabel11 = new javax.swing.JLabel();
		jLabel12 = new javax.swing.JLabel();
		jLabel13 = new javax.swing.JLabel();
		jLabel14 = new javax.swing.JLabel();
		jLabel15 = new javax.swing.JLabel();
		jLabel16 = new javax.swing.JLabel();
		jLabel17 = new javax.swing.JLabel();

		jLabel1.setText("Mouse Controls");

		jLabel2.setText("Pan");

		jLabel3.setText("Select Item");

		jLabel4.setText("Move Highlighted Item");

		mPanBtn.setText(""+Window.getKeyControls().getMousePan());
		mPanBtn.setActionCommand("mPanBtn");
		mPanBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				awaitMouseInput(evt);
			}
		});

		mSelectBtn.setText(""+Window.getKeyControls().getMouseSelect());
		mSelectBtn.setActionCommand("mSelectBtn");
		mSelectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				awaitMouseInput(evt);
			}
		});

		mMoveBtn.setText(""+Window.getKeyControls().getMouseObjectMove());
		mMoveBtn.setActionCommand("mMovetBtn");
		mMoveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				awaitMouseInput(evt);
			}
		});

		jLabel5.setText("Keyboard Controls");

		jLabel6.setText("Cycle Grid");

		kGridBtn.setText(""+(char)Window.getKeyControls().getGridKey());
		kGridBtn.setActionCommand("kGridBtn");
		kGridBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				awaitKeyboardInput(evt);
			}
		});

		kZoomInBtn.setText(""+(char)Window.getKeyControls().getZoomInKey());
		kZoomInBtn.setActionCommand("kZoomInBtn");
		kZoomInBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				awaitKeyboardInput(evt);
			}
		});

		kZoomOutBtn.setText(""+(char)Window.getKeyControls().getZoomOutKey());
		kZoomOutBtn.setActionCommand("kZoomOutBtn");
		kZoomOutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				awaitKeyboardInput(evt);
			}
		});

		kLinesBtn.setText(""+(char)Window.getKeyControls().getLinesKey());
		kLinesBtn.setActionCommand("kLinesBtn");
		kLinesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				awaitKeyboardInput(evt);
			}
		});

		kThingsBtn.setText(""+(char)Window.getKeyControls().getThingsKey());
		kThingsBtn.setActionCommand("kThingsBtn");
		kThingsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				awaitKeyboardInput(evt);
			}
		});

		kVerticesBtn.setText(""+(char)Window.getKeyControls().getVertsKey());
		kVerticesBtn.setActionCommand("kVerticesBtn");
		kVerticesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				awaitKeyboardInput(evt);
			}
		});

		kSnapBtn.setText(""+(char)Window.getKeyControls().getSnapKey());
		kSnapBtn.setActionCommand("kSnapBtn");
		kSnapBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				awaitKeyboardInput(evt);
			}
		});

		kPanBtn.setText(""+(char)Window.getKeyControls().getPanKey());
		kPanBtn.setActionCommand("kPanBtn");
		kPanBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				awaitKeyboardInput(evt);
			}
		});

		kSelectBtn.setText(""+(char)Window.getKeyControls().getSelectKey());
		kSelectBtn.setActionCommand("kSelectBtn");
		kSelectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				awaitKeyboardInput(evt);
			}
		});

		kHighBtn.setText(""+(char)Window.getKeyControls().getMoveObjectKey());
		kHighBtn.setActionCommand("kHighBtn");
		kHighBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				awaitKeyboardInput(evt);
			}
		});

		kRotBtn.setText(""+(char)Window.getKeyControls().getRotateKey());
		kRotBtn.setActionCommand("kRotBtn");
		kRotBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				awaitKeyboardInput(evt);
			}
		});

		kSaveBtn.setText(""+(char)Window.getKeyControls().getSaveKey());
		kSaveBtn.setActionCommand("kSaveBtn");
		kSaveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				awaitKeyboardInput(evt);
			}
		});

		jLabel7.setText("Zoom In");

		jLabel8.setText("Zoom Out");

		jLabel9.setText("Lines Mode");

		jLabel10.setText("Things Mode");

		jLabel11.setText("Vertices Mode");

		jLabel12.setText("Grid Snap");

		jLabel13.setText("Pan");

		jLabel14.setText("Select Item");

		jLabel15.setText("Move Highlighted Item");

		jLabel16.setText("Rotate");

		jLabel17.setText("Save (While Hold CTRL)");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
						.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addGroup(layout.createSequentialGroup().addGroup(layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												layout.createSequentialGroup().addContainerGap().addComponent(jLabel3))
										.addGroup(
												layout.createSequentialGroup().addContainerGap().addComponent(jLabel2)))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(mSelectBtn).addComponent(mPanBtn)))
								.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel4)
										.addGap(18, 18, 18).addComponent(mMoveBtn)))
						.addGap(55, 55, 55)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jLabel6).addComponent(jLabel7).addComponent(jLabel8).addComponent(jLabel9)
								.addComponent(jLabel10).addComponent(jLabel11).addComponent(jLabel12)
								.addComponent(jLabel13).addComponent(jLabel14).addComponent(jLabel15)
								.addComponent(jLabel16).addComponent(jLabel17))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(kGridBtn).addComponent(kZoomInBtn).addComponent(kZoomOutBtn)
								.addComponent(kLinesBtn).addComponent(kThingsBtn).addComponent(kVerticesBtn)
								.addComponent(kSnapBtn).addComponent(kPanBtn).addComponent(kSelectBtn)
								.addComponent(kHighBtn).addComponent(kRotBtn).addComponent(kSaveBtn)))
						.addGroup(layout.createSequentialGroup().addGap(58, 58, 58).addComponent(jLabel1)
								.addGap(170, 170, 170).addComponent(jLabel5)))
				.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(14, 14, 14)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel1)
						.addComponent(jLabel5))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel2)
						.addComponent(mPanBtn).addComponent(jLabel6).addComponent(kGridBtn))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(mSelectBtn).addComponent(jLabel3).addComponent(kZoomInBtn).addComponent(jLabel7))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(mMoveBtn)
						.addComponent(jLabel4).addComponent(kZoomOutBtn).addComponent(jLabel8))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(kLinesBtn)
						.addComponent(jLabel9))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(kThingsBtn).addComponent(jLabel10))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(kVerticesBtn).addComponent(jLabel11))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(kSnapBtn)
						.addComponent(jLabel12))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(kPanBtn)
						.addComponent(jLabel13))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(kSelectBtn).addComponent(jLabel14))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(kHighBtn)
						.addComponent(jLabel15))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(kRotBtn)
						.addComponent(jLabel16))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(kSaveBtn)
						.addComponent(jLabel17))
				.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

	}

	private void awaitMouseInput(ActionEvent evtA) {
		cap = new CapturePane(this, new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				code = cap.getCode(evt);
				switch(evtA.getActionCommand()) {
				case "mSelectBtn":
					Window.getKeyControls().setMouseSelect(code);
					mSelectBtn.setText(""+code);
					break;
				case "mMoveBtn":
					Window.getKeyControls().setMouseObjectMove(code);
					mSelectBtn.setText(""+code);
					break;
				case "mPanBtn":
					Window.getKeyControls().setMousePan(code);
					mPanBtn.setText(""+code);
					break;

				}
			}
		});

	}
	
	private int code;
	private CapturePane cap=null;
	private void awaitKeyboardInput(ActionEvent evtA) {
		cap = new CapturePane(this, new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				code = cap.getCode(evt);
				switch(evtA.getActionCommand()) {
				case "kGridBtn":
					Window.getKeyControls().setGridKey(code);
					kGridBtn.setText(""+(char)code);
					break;
				case "kHighBtn":
					Window.getKeyControls().setMoveObjectKey(code);
					kHighBtn.setText(""+(char)code);
					break;
				case "kLinesBtn":
					Window.getKeyControls().setLinesKey(code);
					kLinesBtn.setText(""+(char)code);
					break;
				case "kPanBtn":
					Window.getKeyControls().setPanKey(code);
					kPanBtn.setText(""+(char)code);
					break;
				case "kRotBtn":
					Window.getKeyControls().setRotateKey(code);
					kRotBtn.setText(""+(char)code);
					break;
				case "kSaveBtn":
					Window.getKeyControls().setSaveKey(code);
					kSaveBtn.setText(""+(char)code);
					break;
				case "kSelectBtn":
					Window.getKeyControls().setSelectKey(code);
					kSelectBtn.setText(""+(char)code);
					break;
				case "kSnapBtn":
					Window.getKeyControls().setSnapKey(code);
					kSnapBtn.setText(""+(char)code);
					break;
				case "kThingsBtn":
					Window.getKeyControls().setThingsKey(code);
					kThingsBtn.setText(""+(char)code);
					break;
				case "kVerticesBtn":
					Window.getKeyControls().setVertsKey(code);
					kVerticesBtn.setText(""+(char)code);
					break;
				case "kZoomInBtn":
					Window.getKeyControls().setZoomInKey(code);
					kZoomInBtn.setText(""+(char)code);
					break;
				case "kZoomOutBtn":
					Window.getKeyControls().setZoomOutKey(code);
					kZoomOutBtn.setText(""+(char)code);
					break;
				}
			}
		});

	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
