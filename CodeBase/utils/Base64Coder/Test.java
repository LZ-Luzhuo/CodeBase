package text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import utils.Base64Coder;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-4 下午11:21:13
 * 
 * 描述:Base64Code工具的案例代码
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class Test {

	public static void main(String[] args) {
		String str = "Hello Wrold, 我来自中国。";
		
		//对字符串进行编码
		String encode = new String(Base64Coder.encode(str.getBytes()));
		System.out.println("字符串编码:"+encode);
		
		//对字符串进行解码
		String destr = Base64Coder.decodeString(encode);
		System.out.println("字符串解码:"+destr);
		
		try{
			//对图片等文件进行编码
			FileInputStream fis = new FileInputStream(new File("a.jpg"));
			
			ByteArrayOutputStream arrayos = new ByteArrayOutputStream();
			
			int len = 0;
			byte[] bys = new byte[1024];
			while((len = fis.read(bys)) != -1){
				arrayos.write(bys, 0, len);
			}
			fis.close();
			
			byte[] array = arrayos.toByteArray();
			String filestr = new String(Base64Coder.encode(array));
			System.out.println("文件编码:"+filestr);
			
			
			//对图片等文件进行解码
			byte[] filedecode = Base64Coder.decode(filestr);
			
			FileOutputStream fos = new FileOutputStream(new File("b.jpg"));
			fos.write(filedecode);
			fos.close();
			System.out.println("文件解码完成.");
		
		}catch(Exception e){
		}
	}
}
