package java0.nio01;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author rongsimin
 * @date 2020/10/26 23:21
 */
public class MyClassLoader extends ClassLoader {

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] bytes = readData(name);
		return defineClass(null, bytes, 0, bytes.length);
	}

	private byte[] readData(String name) {
		try (InputStream inputStream = new FileInputStream(name)) {
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			int i;
			while ((i = inputStream.read()) != -1) {
				byteArray.write(255 - i);
			}
			return byteArray.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		String classPath = "C:\\Users\\rongsimin\\Desktop\\java训练营\\2020_10_25\\讲师秦金卫-资料分享\\Hello.xlass";
		Class<?> clazz = new MyClassLoader().findClass(classPath);
		Method method = clazz.getDeclaredMethod("hello");
		method.setAccessible(true);
		method.invoke(clazz.newInstance());
	}


}
