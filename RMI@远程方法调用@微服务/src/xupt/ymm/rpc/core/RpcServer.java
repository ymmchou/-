package xupt.ymm.rpc.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RpcServer implements Runnable {
	
	private ServerSocket server;
	private int port;
	private boolean goon;
	private final RpcBeanFactory rpcBeanFactory;
	private static long executorId;
	
	public RpcServer() {
		rpcBeanFactory = new RpcBeanFactory();
		this.goon = false;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public void startRpcServer() throws Exception {
		if(this.port == 0) {
			return;
		}
		server = new ServerSocket(port);
		this.goon = true;
		new Thread(this,"Rpc_Server").start();//启动线程
	}
	
	public void stopRpcServer() {
		if (this.server != null && !this.server.isClosed()) {
			try {
				this.server.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				this.server = null;
			}
		}
	}
	
	RpcBeanFactory getRpcBeanFactory() {
		return rpcBeanFactory;
	}
	
	//用注册的方法知道类实现的接口，类的对象，通过对象执行类的方法。
	public void rpcRegistry(Class<?> interfaces,Object object) {
		RpcBeanRegistry.registryInterface(rpcBeanFactory, interfaces, object);
	}
	
	public void rpcRegistry(Class<?> interfaces, Class<?> imClass) {
		RpcBeanRegistry.registryInterface(rpcBeanFactory, interfaces,imClass);
	}
	
	@Override
	public void run() {
		while(goon) {
			try {
				Socket socket = server.accept();//不断的侦听
				
				new RpcServerExecutor(socket, this,++executorId);
				
			} catch (Exception e) {
				goon = false;
				e.printStackTrace();
			}
		}
		stopRpcServer();
	}
}
