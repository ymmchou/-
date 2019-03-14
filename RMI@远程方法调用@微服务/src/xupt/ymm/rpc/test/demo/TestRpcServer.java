package xupt.ymm.rpc.test.demo;

import xupt.ymm.rpc.core.RpcServer;
import xupt.ymm.rpc.test.core.ITestForRPC;
import xupt.ymm.rpc.test.core.UserAction;

public class TestRpcServer {

	public static void main(String[] args) {
		RpcServer rpcServer = new RpcServer();
		rpcServer.setPort(54199);
		rpcServer.rpcRegistry(ITestForRPC.class, UserAction.class);
		
		try {
			rpcServer.startRpcServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
