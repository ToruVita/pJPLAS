package jplas.server;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class Server {
	static public String usage = "USAGE : command path [service_name]";
	Tomcat tom;
	private File workingFolder;
	private File folder4Open;
	private String serviceName = "";
	private int portNo;
	public static void main(String[] args){
		autoStart(args);
	}
	static void autoStart(String[] args){
		Server s = new Server();
		if(args.length>0){
			File file = new File(args[0]);
			if(file.exists()){
				s.setFolder4Open(file);
			}
			if(args.length>1){
				s.setServiceName(args[1]);
			}
		}else{
			
		}
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			s.start();
			Desktop.getDesktop().browse(new URI(s.fakeUrl()));
			while(!br.readLine().equalsIgnoreCase("exit"));
			s.stop();
		} catch (LifecycleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	String fakeUrl(){
		return String.format("http://127.0.0.1:%d%s", portNo, serviceName);
	}
	Server(){
		super();
		try {
			workingFolder = File.createTempFile("jplas", "psvr");
			workingFolder.delete();
			workingFolder.mkdir();
			Desktop.getDesktop().open(workingFolder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		portNo = 8080;
	}
	public File getFolder4Open() {
		return folder4Open;
	}
	public void setFolder4Open(File folder4Open) {
		this.folder4Open = folder4Open;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		String sn = serviceName;
		while(sn.endsWith("/")){
			sn = sn.substring(0, sn.length()-1);
		}
		if(sn.startsWith("/")){
			this.serviceName = sn;
		}else{
			this.serviceName = "/"+sn;
		}
	}
	public int getPortNo() {
		return portNo;
	}
	public void setPortNo(int portNo) {
		this.portNo = portNo;
	}
	public Server(File folder4Open, String serviceName) {
		this();
		this.folder4Open = folder4Open;
		this.serviceName = serviceName;
	}
	public void start() throws LifecycleException{
		tom = new Tomcat();
		tom.setBaseDir(workingFolder.getAbsolutePath()+"/");
		tom.addWebapp(null, getServiceName(), getFolder4Open().getAbsolutePath());
		tom.setPort(portNo);
		tom.start();
	}
	public void stop() throws LifecycleException{
		tom.stop();
		tom.destroy();
	}
}
