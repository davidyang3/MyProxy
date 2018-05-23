import java.lang.reflect.Method;

public interface InvocationHandler {
	public void invoke(Object Proxy,Method m,Object [] args) throws Exception;
}
