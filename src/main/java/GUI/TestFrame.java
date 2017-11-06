package GUI;

import java.io.File;
import java.util.Date;

public class TestFrame extends Frame {

	public static void main(String[] args) {
		new TestFrame();
	}
	TestFrame(){
		setDefaultMenu();
		ConfPanel cp = new ConfPanel();
		cp.setConf("Oro", new Date());
		cp.setConf("Oro2", "H265");
		cp.setConf("OroF", new File("/"));
		cp.setConf("Oro3", new Integer(23));
		setPlate(cp);
		Object o = new File("c:\\");
		System.out.println(o.toString());
		System.out.println(o.getClass());
	}
}
