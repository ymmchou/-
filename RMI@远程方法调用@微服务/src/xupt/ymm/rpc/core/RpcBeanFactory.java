package xupt.ymm.rpc.core;

import java.util.HashMap;
import java.util.Map;

public class RpcBeanFactory {

	private final Map<String, RpcBeanDefination> beanMap;
	
	RpcBeanFactory() {
		beanMap = new HashMap<>();
	}
	
	void rpcBeanRegistry(String beanId,RpcBeanDefination defination) {
		RpcBeanDefination rbd = beanMap.get(beanId);
		if(rbd != null) {
			return;
		}
		beanMap.put(beanId, defination);
	}
	
	RpcBeanDefination getBean(String beanId) {
		return beanMap.get(beanId);
	}
}
