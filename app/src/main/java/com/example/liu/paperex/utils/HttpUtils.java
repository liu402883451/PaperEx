package com.example.liu.paperex.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * 操作网络的工具类
 * 
 * @author liu
 *
 */
public class HttpUtils {

	public static int TIMEOUT = 50000000;

	/**
	 * 链接网络获取json字符串
	 * 
	 * @param path
	 *            当前访问的网络地址
	 * @return 得到json字符串
	 */
	public static String getHttpResult(String path) {
		String jsonString = "";
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(path);
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				jsonString = EntityUtils.toString(httpResponse.getEntity());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

	/**
	 * 下载更新的回调接口
	 */
	public interface CallBack {
		/**
		 * 当下载进度更新时调用
		 * 
		 * @param curSize
		 *            当前已下载的大小
		 * @param totalSize
		 *            文件的总大小
		 */
		public void progessUpdate(int curSize, int totalSize);
	}

	public static byte[] doGet(String urlStr, CallBack callBack) {
		HttpURLConnection conn = null;
		try {
			URL mUrl = new URL(urlStr);
			conn = (HttpURLConnection) mUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(TIMEOUT);
			conn.setConnectTimeout(TIMEOUT);
			conn.connect();
			int code = conn.getResponseCode();
			if (code == HttpURLConnection.HTTP_OK) {
				// 获取文件的总大小
				int totalSize = conn.getContentLength();
				// 记录已经下载的文件的大小
				int curSize = 0;
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				InputStream is = conn.getInputStream();
				byte[] buf = new byte[1024];
				int len = -1;
				while ((len = is.read(buf)) != -1) {
					out.write(buf, 0, len);
					curSize += len;
					callBack.progessUpdate(curSize, totalSize);
				}
				return out.toByteArray();
			} else {
				throw new RuntimeException("网络访问失败：" + code);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("mtag", "下载失败");
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return null;
	}

	public static byte[] doGet(String urlStr) {
		HttpURLConnection conn = null;
		try {
			URL mUrl = new URL(urlStr);
			conn = (HttpURLConnection) mUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(TIMEOUT);
			conn.setConnectTimeout(TIMEOUT);
			conn.connect();
			int code = conn.getResponseCode();
			if (code == HttpURLConnection.HTTP_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				InputStream is = conn.getInputStream();
				byte[] buf = new byte[1024];
				int len = -1;
				while ((len = is.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
				return out.toByteArray();
			} else {
				throw new RuntimeException("网络访问失败：" + code);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("mtag", "下载失败");
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return null;
	}

	/**
	 * 发起GET请求，请求字符串数据（使用默认编码）
	 * 
	 * @param url
	 * @return
	 */
	public static String doGetForString(String url) {
		Log.i("mtag", "url = " + url);
		return new String(doGet(url));
	}

	public static Bitmap doGetForBitmap(String iconUrl) {
		byte[] data = doGet(iconUrl);
		return BitmapFactory.decodeByteArray(data, 0, data.length);
	}
}