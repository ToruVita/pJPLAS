package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class Frame extends JFrame implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equalsIgnoreCase("exit")){
			ending();
		}
	}
	/**
	 * File Chooser for loading/saving 
	 */
	public JFileChooser fileChooser = new JFileChooser();
	protected JTextField urlField;
	public JToolBar toolBar = new JToolBar();
	
	public String getUrlField() {
		return urlField.getText();
	}

	/**
	 * 終了処理
	 */
	public void ending() {
		System.exit(1);
	}
	public Frame(){
		setSize(800, 600);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				ending();
			}
		});
		setJMenuBar(new JMenuBar());
		this.setContentPane(mainPanel=new MainPanel());
		setToolBar(toolBar);
		this.setIconImage(createIcon(""));
		setVisible(true);
	}

	public void setDefaultMenu(){
		addMenu("File","Load,Save,-,Start,-,eXit".split(","));
//		addMenu("File","Load,Save,reMakeTitle,-,Start,-,eXit".split(","));
//		addMenu("Edit","Paste,pRepare,-,cLear,-,eXpand".split(","));
//		getJMenuBar().add(urlField = new JTextField());
//		addMenu("Downs","Start,Pause,sTop".split(","));
	}
	public void setCenterComponent(Component c){
		mainPanel.mainpanel.setViewportView(c);
	}
	MainPanel mainPanel;
	JProgressBar pb = new JProgressBar();
	/**
	 * メインコンポーネントの配置を行う
	 * （スクロールバーは既設）．
	 * @param c
	 */
	public void setPlate(Component c){
		mainPanel.mainpanel.setViewportView(c);
	}
	public void setToolBar(JToolBar jToolBar){
		mainPanel.add(jToolBar, BorderLayout.NORTH);
	}
	class MainPanel extends JPanel{
		JPanel statusBar = new JPanel();
		JScrollPane mainpanel = new JScrollPane();
		MainPanel(){
			super();
			setLayout(new BorderLayout());
			add(statusBar, BorderLayout.SOUTH);
			add(mainpanel, BorderLayout.CENTER);
			statusBar.add(pb);
			pb.setValue(50);
		}
		void addWest(Component c){
			add(c, BorderLayout.WEST);
		}
	}
	public void addMenu(String menuTitle, String[] itemTitles) {
		for(String s: itemTitles){
			addMenu(menuTitle, s);
		}
	}
	public void addMenu(String menuTitle, String itemTitle) {
		addMenu(menuTitle, itemTitle, null, -1, -1);
	}
	public void addMenu(String menuTitle, String itemTitle, int indexOfMenu, int indexOfMenuItem) {
		addMenu(menuTitle, itemTitle, null, indexOfMenu, indexOfMenuItem);
	}
	public void addMenu(String menuTitle, String itemTitle, ActionListener a, int indexOfMenu, int indexOfMenuItem) {
		addMenu(menuTitle, itemTitle, a, null, indexOfMenu, indexOfMenuItem);
	}
	
	void addMenu(String menuTitle, String itemTitle, ActionListener a, KeyStroke ks, int indexOfMenu, int indexOfMenuItem) {
		JMenu menu = null;
		for (int i = 0; i < getJMenuBar().getComponentCount(); i++) {
			JMenu wk = getJMenuBar().getMenu(i);
			if(wk!=null){
				if (wk.getText().equalsIgnoreCase(menuTitle)) {
					menu = wk;
				}
			}
		}
		if (menu == null) {
			menu = new JMenu(menuTitle);
			menu.setMnemonic(menuTitle.charAt(0));
			getJMenuBar().add(menu, indexOfMenu);
			toolBar.addSeparator();
		}
		if(itemTitle.equals("-")){
			menu.addSeparator();
		}else{
			Image image = createIcon(itemTitle);
			ImageIcon icon = new ImageIcon(image);
			JMenuItem item = new JMenuItem(itemTitle, icon);
			char c = itemTitle.charAt(0);
			for(int i = 1; i < itemTitle.length(); i++){
				if(Character.isUpperCase(itemTitle.charAt(i))){
					c = itemTitle.charAt(i);
				}
			}
			item.setMnemonic(c);
			if(ks!=null){
				item.setAccelerator(ks);
			}else{
				int ctrl = InputEvent.CTRL_DOWN_MASK;
				item.setAccelerator(KeyStroke.getKeyStroke(c, ctrl));
			}
			if(a==null){
				item.addActionListener(this);
			}else{
				item.addActionListener(a);
			}
			menu.add(item, indexOfMenuItem);
			JButton btn = new JButton("",icon);
			btn.setActionCommand(itemTitle);
			if(a==null){
				btn.addActionListener(this);
			}else{
				btn.addActionListener(a);
			}
			btn.setToolTipText(btn.getActionCommand());
			toolBar.add(btn);
		}
	}
	Image createIcon(String str){
		return IconMaker.createIcon(str);
	}
	public void resetMenu(){
		JMenuBar newMenu = new JMenuBar();
		JToolBar tb = new JToolBar();
		setJMenuBar(newMenu);
		add(tb, BorderLayout.NORTH);
	}
}
