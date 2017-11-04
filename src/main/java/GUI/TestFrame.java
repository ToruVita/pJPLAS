package GUI;

import java.util.Date;

public class TestFrame extends Frame {

	public static void main(String[] args) {
		new TestFrame();
	}
	TestFrame(){
		setDefaultMenu();
		ConfPanel cp = new ConfPanel();
		cp.setConf("Oro", new Date());
		setPlate(cp);
	}
}
