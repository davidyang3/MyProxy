
public class Main {
	public static void main(String args[]){
		Test t=(Test)Proxy.getProxyInstance(Test.class,new SayProxy(new SayTest()));
		t.say();
	}
}
