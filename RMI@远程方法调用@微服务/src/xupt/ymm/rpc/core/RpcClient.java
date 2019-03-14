package xupt.ymm.rpc.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RpcClient {
	
	private RpcClientExecutor rpcClientExecutor;
	
	public RpcClient(String rpcServerIp,int rpcServerport) {
		this.rpcClientExecutor = new RpcClientExecutor(rpcServerIp, rpcServerport);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getProxy(Class<?> klass) {
		return (T) Proxy.newProxyInstance(
				klass.getClassLoader(),
				new Class[] {klass},
				new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						String BeanId = String.valueOf(method.toString().hashCode());
						//得到方法的返回值类型
						Class<?> returnType = method.getReturnType();
						Object result = rpcClientExecutor.rpcExecutor(BeanId, args, returnType);
						
						return result;
					}
				}
			);
	}
}
