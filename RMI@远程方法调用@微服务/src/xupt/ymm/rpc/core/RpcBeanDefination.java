package xupt.ymm.rpc.core;

import java.lang.reflect.Method;

public class RpcBeanDefination {

	private Class<?> klass;
	private Method method;
	private Object object;
	
	RpcBeanDefination() {
	}

	Class<?> getKlass() {
		return klass;
	}

	void setKlass(Class<?> klass) {
		this.klass = klass;
	}

	Method getMethod() {
		return method;
	}

	void setMethod(Method method) {
		this.method = method;
	}

	Object getObject() {
		return object;
	}

	void setObject(Object object) {
		this.object = object;
	}
	
	
	
}
