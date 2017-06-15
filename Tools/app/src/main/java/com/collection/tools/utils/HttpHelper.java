package com.collection.tools.utils;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by John.Lu on 2017/6/12.
 * 网络传输
 *
 */

public class HttpHelper {

    private String urlPath ="";//
    private HttpURLConnection httpURLConnection;
    private InputStream inputStream ;
    private OutputStream outputStream;
    //token值不能是同一个，否则访问会出现404现象
    private  String token = "afiBzrJbKDRYT6bUzaiLzg9QGpJSk28a";//afiBzrJbKDRYT6bUzaiLzg9QGpJSk28i
    private String host ="appserver";
    private String method = null;
    private HttpsURLConnection httpsURLConnection;
    private  int mResponesCode =-1;
    public  HttpHelper(String paramToken)
    {
        Log.e("***********","token: "+paramToken);
         method = "POST";
        token = paramToken;
//        initHttp();
        initHttpsConnection();//
    }

/**
 * https初始化
* */
  private void initHttpsConnection()
  {
      //设置信任过程，全部设置为信任
          TrustManager trustManager[] = new TrustManager[]{
           new X509TrustManager() {
               @Override
               public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

               }

               @Override
               public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

               }

               @Override
               public X509Certificate[] getAcceptedIssuers() {
                   return new X509Certificate[0];
               }
           }

          };
      try {
          SSLContext sslContext = SSLContext.getInstance("SSL");
          sslContext.init(null,trustManager,null);
          URL url = new URL(urlPath);
          httpsURLConnection = (HttpsURLConnection) url.openConnection();
          httpsURLConnection.setSSLSocketFactory(sslContext.getSocketFactory());
          //设置全部都信任
          httpsURLConnection.setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
          //设置校验服务端证书域名是否相等
          final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
              @Override
              public boolean verify(String hostname, SSLSession session) {
                  return true;//默认是不相等，这里设置为相等，不需要校验过程
              }
          };
          httpsURLConnection.setHostnameVerifier(hostnameVerifier);
          httpsURLConnection.setDoInput(true);
          httpsURLConnection.setDoOutput(true);
         // Log.e("====","trans token "+token);
          httpsURLConnection.setRequestProperty("Content-Type","text/plain");
          httpsURLConnection.setRequestProperty("Token",token);
        //  httpsURLConnection.setRequestProperty("Host",host);
          httpsURLConnection.setRequestMethod(method);
          httpsURLConnection.setConnectTimeout(3000);
          httpsURLConnection.setDefaultUseCaches(false);
          httpsURLConnection.connect();//没有
      } catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
      } catch (KeyManagementException e) {
          e.printStackTrace();
      } catch (MalformedURLException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
/**
 * https
 * */
  public  void httpsRequest(byte data[])
  {
          try {
              if(httpsURLConnection != null) {
                  outputStream = httpsURLConnection.getOutputStream();
                  outputStream.write(data);
                  outputStream.flush();
                  outputStream.close();
                  mResponesCode =httpsURLConnection.getResponseCode();
                  if(mResponesCode == HttpsURLConnection.HTTP_OK)
                  {
                      inputStream = httpsURLConnection.getInputStream();
                      Log.d("*****","https ok ----------------->");
                  }
              }
          } catch (IOException e) {
              e.printStackTrace();
          }
  }

/**
 * 采用http
 * */
    private void initHttp()
    {
        try {
            URL url = new URL(urlPath);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDefaultUseCaches(false);
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setRequestProperty("Content-Type","text/plain");
            httpURLConnection.setRequestProperty("Token",token);
            httpURLConnection.connect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/**
 * 传输数据
 * 采用http
 *
 * */
  public void httpRequest(byte[] data){
                try {
                    if(httpURLConnection != null) {
                        outputStream = httpURLConnection.getOutputStream();
                        outputStream.write(data);
                        outputStream.flush();
                        outputStream.close();
                        mResponesCode = httpURLConnection.getResponseCode();
                        if( mResponesCode== HttpURLConnection.HTTP_OK)
                        {
                            //读取返回相应的体，判断是否需要二次传输
                            Log.d("*****","http ok ----------------->");
                            inputStream =httpURLConnection.getInputStream();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
}

/**
 * 读取相应码回啦
 * */
public int getmResponesCode()
{
    return  mResponesCode;
}


public InputStream getInputStream()
{
    if(inputStream != null)
    {
        return inputStream;
    }
    return null;
}

public void closeConnect()
{
    if(httpsURLConnection != null)
    {
        httpsURLConnection.disconnect();
        httpsURLConnection = null;
    }
    if(httpURLConnection != null)
    {
        httpURLConnection.disconnect();
        httpURLConnection=null;
    }
    if(inputStream != null)
    {
        try {
            inputStream.close();
            inputStream = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



}
