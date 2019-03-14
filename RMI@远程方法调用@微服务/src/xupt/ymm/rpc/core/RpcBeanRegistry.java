package xupt.ymm.rpc.core;

import java.lang.reflect.Method;

public class RpcBeanRegistry {
	
	RpcBeanRegistry() {
	}
	
	//给客户端提供
	 static void registryInterface(RpcBeanFactory rpcBeanFactory,Class<?> interfaces) {
		doregistry(rpcBeanFactory,interfaces,null);
	}
	 
	//内部使用，注册
	 private static void doregistry(RpcBeanFactory rpcBeanFactory , Class<?> interfaces ,Object object) {
		  //得到接口中的所有的方法，行程方法的数组
		 Method[] methods = interfaces.getDeclaredMethods();
			for(Method method : methods) {  //遍历这些方法
				String beanId = String.valueOf(method.toString().hashCode());//将方法序列化。
				RpcBeanDefination rpcBeanDefination = new RpcBeanDefination();
				
				//将得到的实现接口的那个类和它的方法以及对象放进RpcBeanDefination()中。
				rpcBeanDefination.setKlass(interfaces);
				rpcBeanDefination.setMethod(method);
				rpcBeanDefination.setObject(object);
				
				rpcBeanFactory.rpcBeanRegistry(beanId, rpcBeanDefination);
			}
	}
	
	 //服务端使用，知道实现类的对象。
	 static void registryInterface(RpcBeanFactory rpcBeanFactory,Class<?> interfaces,Object object) {
		 //判断此类是否实现了这个接口。
		if(!interfaces.isAssignableFrom(object.getClass())){
			return;
		}
		doregistry(rpcBeanFactory,interfaces,object);
	}
	
	 //服务器端使用，知道类，创建一个对象。
	static void registryInterface(RpcBeanFactory rpcBeanFactory,Class<?> interfaces,Class<?> klass) {
		//判断该类是否实现了接口。
		if(!interfaces.isAssignableFrom(klass)){
			return;
		}
		try {
			doregistry(rpcBeanFactory, interfaces, klass.newInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
