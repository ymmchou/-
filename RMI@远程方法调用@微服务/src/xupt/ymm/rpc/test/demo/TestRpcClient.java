package xupt.ymm.rpc.test.demo;

import xupt.ymm.rpc.core.RpcClient;
import xupt.ymm.rpc.model.UserModel;
import xupt.ymm.rpc.test.core.ITestForRPC;

public class TestRpcClient {
	
	public static void main(String[] args) {
		RpcClient rpcClient = new RpcClient("localhost", 54199);
		
		try {
			ITestForRPC proxy = rpcClient.getProxy(ITestForRPC.class);
			UserModel user = proxy.getUserById("1232");
			System.out.println(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
