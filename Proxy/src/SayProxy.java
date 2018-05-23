import java.lang.reflect.Method;

public class SayProxy implements InvocationHandler {
	public Object target;
	public SayProxy(Object target){
		this.target=target;
	}
	@Override
	public void invoke(Object Proxy, Method m,Object [] args) throws Exception{
		// TODO Auto-generated method stub
		System.out.println("Before");
		m.invoke(target,args);
		System.out.println("After");
	}

}
