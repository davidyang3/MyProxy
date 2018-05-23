import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class Proxy {
	public static Object getProxyInstance(Class intf,InvocationHandler h){
		//构建代理类
		Method[] methods=intf.getDeclaredMethods();
		String methodstr="";
		String br="\r\n";
		for(Method m:methods){
			methodstr+="@Override"+br+
						"public void "+m.getName()+"(){"+br+
						"	try{"+br+
						"		h.invoke(this,"+intf.getName()+".class.getMethod(\""
						+m.getName()+"\"),null);"+br+
						"	}catch(Exception e){"+br+
						"	}"+br+
						"}";
						
		}
		String cls= "import java.lang.reflect.Method;"+br+
					"public class $Proxy0 implements "+ intf.getName()+"{"+br+
					"	InvocationHandler h;"+br+
					"	public $Proxy0(InvocationHandler i){"+br+
					"		h=i;"+br+
					"	}"+br+
					methodstr+br+
					"}";
		System.out.println(cls);
		String bin=System.getProperty("user.dir");
		try {
			File f=new File(bin+"/bin/$Proxy0.java");
			FileOutputStream fs=new FileOutputStream(f);
			fs.write(cls.getBytes());
			fs.flush();
			fs.close();
			//编译代理类
			JavaCompiler j=ToolProvider.getSystemJavaCompiler();
			StandardJavaFileManager sm=j.getStandardFileManager(null, null, null);
			Iterable i=sm.getJavaFileObjects(f);
			CompilationTask c=j.getTask(null, sm, null, null, null, i);
			c.call();
			sm.close();
			//加载代理类
			ClassLoader cl=ClassLoader.getSystemClassLoader();
			Class clazz=cl.loadClass("$Proxy0");
			System.out.println(clazz);
			try {
				Constructor cons=clazz.getConstructor(InvocationHandler.class);
				Object o;
				try {
					o = cons.newInstance(h);
					return o;
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//返回代理对象
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
