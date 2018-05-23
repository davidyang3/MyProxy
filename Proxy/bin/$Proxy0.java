import java.lang.reflect.Method;
public class $Proxy0 implements Test{
	InvocationHandler h;
	public $Proxy0(InvocationHandler i){
		h=i;
	}
@Override
public void say(){
	try{
		h.invoke(this,Test.class.getMethod("say"),null);
	}catch(Exception e){
	}
}
}