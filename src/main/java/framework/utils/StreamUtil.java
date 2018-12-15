package framework.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class StreamUtil {

	public static String getString(InputStream in) {
		StringBuffer stringBuffer = new StringBuffer();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuffer.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return stringBuffer.toString();
	}
	/**
	 * 复制流
	 * @param inputStream
	 * @param outputStream
	 */
	public static void copyStream(InputStream inputStream, OutputStream outputStream) {
		byte[] bytes = new byte[4*1024];
		try {
			int len;
			while((len = inputStream.read(bytes))!= -1) {
				outputStream.write(bytes, 0, len);
			}
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("复制流出错！");
		}finally {
			try {
                inputStream.close();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
	}

}