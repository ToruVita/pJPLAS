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
		cp.setConf("Oro2", "Sample2");
		cp.setConf("OroF", new File("/"));
		for(int i = 0; i < 30; i++){
			cp.setConf("Or"+i, new Integer(i));
		}
		setPlate(cp);
		Object o = new File("c:\\");
		System.out.println(o.toString());
		System.out.println(o.getClass());
	}
}
