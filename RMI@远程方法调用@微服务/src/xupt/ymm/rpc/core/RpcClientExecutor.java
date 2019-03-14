package xupt.ymm.rpc.core;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RpcClientExecutor {
	private String rpcServerIp;
	private int rpcServerPort;
	private Gson gson = new GsonBuilder().create();
	
	RpcClientExecutor() {
	}

	RpcClientExecutor(String rpcServerIp, int rpcServerPort) {
		this.rpcServerIp = rpcServerIp;
		this.rpcServerPort = rpcServerPort;
	}

	String getRpcServerIp() {
		return rpcServerIp;
	}

	void setRpcServerIp(String rpcServerIp) {
		this.rpcServerIp = rpcServerIp;
	}

	int getRpcServerPort() {
		return rpcServerPort;
	}

	void setRpcServerPort(int rpcServerPort) {
		this.rpcServerPort = rpcServerPort;
	}
	
	//关闭所有的,形参可以是任何的类型,可以无限多个
	private void closeSocket(Closeable...closeables) {
		if (closeables == null) {
			return ;
		}
		for (Closeable closeable : closeables) {
			try {
				if (closeable != null) {
					closeable.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				closeable = null;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	<T> T rpcExecutor(String rpcBeanId, Object[] para, Class<?> returnType) throws Exception {
		Socket socket = new Socket(rpcServerIp, rpcServerPort);
//		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		//由于
		DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
		
		//发送gson的字符串
		//oos.writeUTF(gson.toJson(rpcBeanId));
		oos.writeUTF(rpcBeanId);
		//rpcBeanId本身是字符串，不用转化
		oos.writeUTF(gson.toJson(para));
		//这种方式慢，换成上面用gson的方式
		
		//oos.writeUTF(rpcBeanId);
//		oos.writeObject(params);
		
//		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		DataInputStream ois = new DataInputStream(socket.getInputStream());
		
		Object result = gson.fromJson(ois.readUTF(), returnType);
		//Object result = ois.readObject();
		
		closeSocket(ois, oos, socket);
		
		return (T) result;
	}

}
