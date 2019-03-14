package xupt.ymm.rpc.core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RpcServerExecutor implements Runnable{
	private Socket socket;
//	private ObjectInputStream ois;
//	private ObjectOutputStream oos;
	private DataInputStream ois;
	private DataOutputStream oos;
	private RpcServer rpcServer;
	private Gson gson = new GsonBuilder().create();
	
	RpcServerExecutor(Socket socket, RpcServer rpcServer,long threadId) throws Exception {
		this.socket = socket;
		this.rpcServer = rpcServer;
//		ois = new ObjectInputStream(this.socket.getInputStream());
//		oos = new ObjectOutputStream(this.socket.getOutputStream());
		ois = new DataInputStream(this.socket.getInputStream());
		oos = new DataOutputStream(this.socket.getOutputStream());
		new Thread(this,"Rpc_Executor" + threadId).start();
	}
	
	void closeSocket() {
		try {
			if (ois != null) {
				ois.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ois = null;
		}
		try {
			if (oos != null) {
				oos.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			oos = null;
		}
		try {
			if (socket != null && !socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			socket = null;
		} 
	}
	
	private void showParameter(Object[] parameters) {
		for(int i = 0;i < parameters.length ; i++) {
			System.out.println(i + ":"+ parameters[i] );
		}
	}

	@Override
	public void run() {
		try {
			//读到由RPC客户端发送的序列号
			//String beanId = gson.fromJson(ois.readUTF(), String.class);			
			String beanId = ois.readUTF();
			System.out.println(beanId + "Sever");
			
			//读到由RPC客户端发送的参数
			Object[] parameters = gson.fromJson(ois.readUTF(),Object[].class);
			System.out.println(parameters[0].getClass().getTypeName());
			//Object[] parameters = (Object[]) ois.readObject();
			showParameter(parameters);
			// 定位相关类、对象和方法；
			RpcBeanDefination defination;
			defination = rpcServer.getRpcBeanFactory().getBean(beanId);
			// 执行RPC客户端要求执行的方法；
			Method method = defination.getMethod();
			Object object = defination.getObject();
			
			//为了防止是类类型，传过来是字符串，但是还需要转化成类类型
			Class<?>[] parameterTypes = method.getParameterTypes();
			for (int i = 0; i < parameters.length; i++) {
				parameters[i] = gson.fromJson(parameters[i] + "", parameterTypes[i]);
			}	
			
			Object result = method.invoke(object, parameters);
			// 向RPC客户端返回执行结果。
			//oos.writeObject(result);
			oos.writeUTF(gson.toJson(result));
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeSocket();
		}
	}
	
	
}
