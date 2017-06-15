package com.collection.tools.utils;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by John.Lu on 2017/6/13.
 *
 * 加密数据块
 * key = mdy(token)[8:-8]取出中间16位
 * aes+cbc+base64
 *
 */

public class EncoderHelper {

    private String cipher_mode=null;//此数据加密模式数据必须是16*n，其他方式，可以为PKCS5Padding 数据长度可以为16*（n+m）的形式
    private String key_mode=null;
    private Context mContext ;

    public EncoderHelper(Context context)
    {
        mContext =context;
        cipher_mode = "AES/CBC/PKCS5Padding"; //加密模式采用NoPadding的填充模式，不能加密长度在16*n+m(m<16)的数据，只能加密16*n的数据
        key_mode="AES";
    }

    private void testWriteFile(String data)
    {
        FileHelper fileHelper = new FileHelper(mContext);
        fileHelper.writeFile(data);
    }

    /**
     * 生成key过程 token的中间16位
     * */
    private  String  generatorKey(String token)
    {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(token.getBytes("UTF-8"));
            byte[] bytes = md5.digest();
            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                if (Integer.toHexString(0xff & bytes[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & bytes[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & bytes[i]));
                }
            }
            String tmp =strBuf.toString();
            return  tmp.substring(8,24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[]  getKeyIv(String md5)
    {
        return  md5.getBytes();
    }
    private  String  encodeDataByAesCbc(String key,String plainText)
    {
        try {

            Cipher cipher = Cipher.getInstance(cipher_mode);
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), key_mode);
            if(cipher != null)
            {
                cipher.init(Cipher.ENCRYPT_MODE,skey,new IvParameterSpec(getKeyIv(key),0,16));
                byte[] doFinal = cipher.doFinal(plainText.getBytes(),0,+plainText.getBytes().length);
//                testWriteFile(plainText);
              return  Base64.encodeToString(doFinal,Base64.DEFAULT);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return  null;
    }
    public  String encodeData(String plainText,String transkey)
    {
//            String key =generatorKey(token);
            if(transkey.length() < 16)
            {
                return null;
            }
        return  encodeDataByAesCbc(transkey,plainText);
    }



    public byte[] testDecode(byte[] encryptionText,String key)
    {
//        String key = generatorKey(token);
        if(key.length() < 16)
        {
            return  null;

        }
        return  decode(encryptionText,key);
    }


    private byte[] decode(byte[] encrptionText, String key)
    {
        try {
            Cipher cipher = Cipher.getInstance(cipher_mode);
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), key_mode);
            if(cipher != null)
            {
                cipher.init(Cipher.DECRYPT_MODE,skey,new IvParameterSpec(getKeyIv(key),0,16));
                byte[] doFinal = cipher.doFinal(encrptionText,0,encrptionText.length);
                System.out.print("decode: "+ Arrays.toString(doFinal));
                Log.d("*****","decode: "+Arrays.toString(doFinal));
                return doFinal;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return  null;
    }


}
