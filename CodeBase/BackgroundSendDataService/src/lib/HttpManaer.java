package com.example.demo.lib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-10-21 下午8:47:03
 * 
 * 描述:一般的网络发送管理(解析数据部分TODO需要手动修改)
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class HttpManaer {
	private final static String ERROR = "error";

	private HttpParams httpParams;
	private HttpClient httpClient;
	private static HttpManaer instance;

	public static HttpManaer getInstance() {
		if (instance == null) {
			synchronized (HttpManaer.class) {
				if (instance == null) {
					instance = new HttpManaer();
				}
			}
		}
		return instance;
	}

	private HttpManaer() {
		// 创建 HttpParams 以用来设置 HTTP 参数（这一部分不是必需的）
		this.httpParams = new BasicHttpParams();
		// 设置连接超时和 Socket 超时，以及 Socket 缓存大小
		HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);
		HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);
		HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
		// 设置重定向，缺省为 true
		HttpClientParams.setRedirecting(httpParams, true);
		// 设置 user agent
		String userAgent = "Chat";
		HttpProtocolParams.setUserAgent(httpParams, userAgent);
		// 创建一个 HttpClient 实例
		httpClient = new DefaultHttpClient(httpParams);
	}

	@SuppressWarnings("unused")
	private String doGet(String url, Map<String, String> headers,
			Map<String, String> params) {
		/* 建立HTTPGet对象 */
		String paramStr = "";
		HttpGet get = new HttpGet(url);
		String result = ERROR;

		if (headers != null) {
			for (Map.Entry<String, String> me : headers.entrySet()) {
				String key = me.getKey();
				String value = me.getValue();

				get.setHeader(key, value);
			}
		}

		if (params != null) {
			for (Map.Entry<String, String> me : params.entrySet()) {
				String key = me.getKey();
				String value = me.getValue();

				paramStr += paramStr = "&" + key + "=" + value;
			}

			if (!paramStr.equals("")) {
				paramStr = paramStr.replaceFirst("&", "?");
				url += paramStr;
			}
		}

		try {
			/* 发送请求并等待响应 */
			HttpResponse response = httpClient.execute(get);
			/* 若状态码为200 ok */
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				/* 读返回数据 */
				result = EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private String doPost(String url, Map<String, String> headers,
			List<NameValuePair> params) {
		/* 建立HTTPPost对象 */
		HttpPost post = new HttpPost(url);
		String result = ERROR;

		if (headers != null) {
			for (Map.Entry<String, String> me : headers.entrySet()) {
				String key = me.getKey();
				String value = me.getValue();

				post.setHeader(key, value);
			}
		}

		try {
			/* 添加请求参数到请求对象 */
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			/* 发送请求并等待响应 */
			HttpResponse response = httpClient.execute(post);
			/* 若状态码为200 ok */
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				/* 读返回数据 */
				result = EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private String doUpload(String url, Map<String, String> headers,
			List<NameValuePair> params, Map<String, File> files) {
		/* 建立HTTPPost对象 */
		HttpPost post = new HttpPost(url);
		String result = ERROR;

		MultipartEntity multipartEntity = new MultipartEntity();

		if (headers != null) {
			for (Map.Entry<String, String> me : headers.entrySet()) {
				String key = me.getKey();
				String value = me.getValue();

				post.setHeader(key, value);
			}
		}
		try {

			if (params != null) {
				for (NameValuePair pair : params) {
					multipartEntity.addPart(
							pair.getName(),
							new StringBody(pair.getValue(), Charset.forName(HTTP.UTF_8)));
				}
			}

			if (files != null) {
				for (Map.Entry<String, File> me : files.entrySet()) {
					String key = me.getKey();
					File file = me.getValue();

					multipartEntity.addPart(key, new FileBody(file, HTTP.UTF_8));
				}
			}

			/* 添加请求参数到请求对象 */
			post.setEntity(multipartEntity);
			/* 发送请求并等待响应 */
			HttpResponse response = httpClient.execute(post);
			/* 若状态码为200 ok */
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				/* 读返回数据 */
				result = EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private String doDownload(String url, Map<String, String> headers,
			List<NameValuePair> params, File localFile) {
		/* 建立HTTPPost对象 */
		HttpPost post = new HttpPost(url);
		String result = ERROR;

		if (headers != null) {
			for (Map.Entry<String, String> me : headers.entrySet()) {
				String key = me.getKey();
				String value = me.getValue();

				post.setHeader(key, value);
			}
		}

		try {
			/* 添加请求参数到请求对象 */
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			/* 发送请求并等待响应 */
			HttpResponse response = httpClient.execute(post);
			/* 若状态码为200 ok */
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {

				File dir = localFile.getParentFile();
				if (!dir.exists()) {
					dir.mkdirs();
				}

				/* 读返回数据 */
				InputStream stream = response.getEntity().getContent();
				FileOutputStream fos = new FileOutputStream(localFile);
				byte[] buffer = new byte[1024];
				int len = -1;

				while ((len = stream.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
					fos.flush();
				}
				stream.close();
				fos.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Post请求
	 * @param url Url
	 * @param headers 头信息
	 * @param paramaters 参数信息
	 * @return true:数据处理成功/false:数据处理失败
	 */
	public boolean post(String url, Map<String, String> headers,
			Map<String, String> paramaters) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		if (paramaters != null) {
			for (Map.Entry<String, String> me : paramaters.entrySet()) {
				params.add(new BasicNameValuePair(me.getKey(), me.getValue()));
			}
		}
		String result = doPost(url, headers, params);

		return parseResult(result);
	}

	private boolean parseResult(String result) {
		System.out.println("result : " + result);

		if (ERROR.equals(result)) {
			return false;
		} else {
			try {
				// TODO 解析数据是否处理成功
				return true; //处理成功返回
			} catch (Exception e) {
				return false;
			}
		}
	}

	/**
	 * 下载
	 * @param url Url
	 * @param headers 头信息
	 * @param paramaters 参数信息
	 * @param localFile 本地下载路径
	 * @return true:数据处理成功/false:数据处理失败
	 */
	public boolean download(String url, Map<String, String> headers,
			Map<String, String> paramaters, File localFile) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		if (paramaters != null) {
			for (Map.Entry<String, String> me : paramaters.entrySet()) {
				params.add(new BasicNameValuePair(me.getKey(), me.getValue()));
			}
		}

		String result = doDownload(url, headers, params, localFile);
		return parseResult(result);
	}

	/**
	 * 上传文件
	 * @param url Url
	 * @param headers 头信息
	 * @param paramaters 参数信息
	 * @param filePaths 文件路径
	 * @return true:数据处理成功/false:数据处理失败
	 */
	public boolean upload(String url, Map<String, String> headers,
			Map<String, String> paramaters, Map<String, String> filePaths) {

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		if (paramaters != null) {
			for (Map.Entry<String, String> me : paramaters.entrySet()) {
				params.add(new BasicNameValuePair(me.getKey(), me.getValue()));
			}
		}

		Map<String, File> files = new HashMap<String, File>();
		if (filePaths != null) {

			for (Map.Entry<String, String> me : filePaths.entrySet()) {
				String key = me.getKey();
				String value = me.getValue();

				files.put(key, new File(value));
			}

		}

		String result = doUpload(url, headers, params, files);
		return parseResult(result);
	}
}
