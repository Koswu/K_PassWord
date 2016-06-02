package com.koswu.kpassword.tool;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import android.util.Base64;

public class PassWord
{
    private static final String UTF_8="UTF-8";
    private static final String HMAC_SHA256="HmacSHA256";
    //private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd','e', 'f' };
    //private static final String PASSWORD_DIC="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_-";
    public static String CreatePassword(String memory_password,String diff_code) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException
    {
        
        String transformd_string= EncodeHmacSHA256(memory_password,diff_code);
        String len16_string;
        int transformd_string_lenth=transformd_string.length();
        char head_char=transformd_string.charAt(0);
        if (head_char=='_'||
        head_char=='-'||
        Character.isDigit(head_char))
        {
            len16_string=transformd_string.substring(transformd_string_lenth-17,transformd_string_lenth-1);
            head_char=len16_string.charAt(0);
            if (head_char=='_'||
            head_char=='-'||
            Character.isDigit(head_char))
            {
                len16_string="K"+len16_string.substring(1,16);
            }
        }
        else
        {
            len16_string=transformd_string.substring(0,16);
        }
        return len16_string;
    }
    private static String EncodeHmacSHA256 (String password_string,String key_string) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException
    {
        byte password_bytes[]=password_string.getBytes(UTF_8);
        byte code_bytes[]=key_string.getBytes(UTF_8);
        SecretKey secret_key=new SecretKeySpec(code_bytes,HMAC_SHA256);
        String result_string;
        Mac mac=Mac.getInstance(secret_key.getAlgorithm());
        mac.init(secret_key);
        byte result_bytes[]=mac.doFinal(password_bytes);
        result_string=ByteToBASE64(result_bytes);
        /*if (result_string.charAt(0)=='-')
        {
            result_string.replace('-','K');
        }
        if (result_string.charAt(0)=='_')
        {
            result_string.replace('_','K');
        }*/
        return result_string;
    }
    private static String ByteToBASE64(byte []datas)
    {
        return Base64.encodeToString(datas,Base64.URL_SAFE|Base64.NO_PADDING|Base64.NO_WRAP);
    }

    private static byte[] pbkdf2(char[] key, int iterations, int bytes)
    throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        byte salt[] = {2,3,3,3,3,3,3,3};
        PBEKeySpec spec = new PBEKeySpec(key, salt, iterations, bytes * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return skf.generateSecret(spec).getEncoded();
    }
}
