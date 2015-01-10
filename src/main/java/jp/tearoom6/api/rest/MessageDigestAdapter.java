package jp.tearoom6.api.rest;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MessageDigest取得用クラス
 * http://himtodo.fc2web.com/java/md5.html
 */
public class MessageDigestAdapter {
    private MessageDigest digest_;

    public MessageDigestAdapter(String algorithm)
            throws NoSuchAlgorithmException {
        digest_ = MessageDigest.getInstance(algorithm);
    }

    public synchronized String digest(InputStream in) throws IOException {
        return toHexString(digestArray(in));
    }

    public synchronized String digest(String str) {
        return toHexString(digestArray(str));
    }

    public synchronized byte[] digestArray(InputStream in) throws IOException {
        try {
            byte[] buff = new byte[4096];
            int len = 0;
            while ((len = in.read(buff, 0, buff.length)) >= 0) {
                digest_.update(buff, 0, len);
            }
        } catch (IOException e) {
            throw e;
        }
        byte[] hash = digest_.digest();
        digest_.reset();
        return hash;
    }

    public synchronized byte[] digestArray(String str) {
        byte[] hash = digest_.digest(str.getBytes());
        digest_.reset();
        return hash;
    }

    private String toHexString(byte[] arr) {
        StringBuffer buff = new StringBuffer(arr.length * 2);
        for (int i = 0; i < arr.length; i++) {
            String b = Integer.toHexString(arr[i] & 0xff);
            if (b.length() == 1) {
                buff.append("0");
            }
            buff.append(b);
        }
        return buff.toString();
    }
}
