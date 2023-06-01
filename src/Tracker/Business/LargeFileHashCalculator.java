package Tracker.Business;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LargeFileHashCalculator {

    private File file;
    private String hashAlgorithm = "SHA-256";
    private static final int BUFFER_SIZE = 8192; // 8 KB buffer
    private String hash;

    public LargeFileHashCalculator () {
    }





    public String getHash(File file)
            throws IOException, NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(hashAlgorithm);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            messageDigest.update(buffer, 0, bytesRead);
        }
        fileInputStream.close();
        hash = bytesToHex(messageDigest.digest());
        return hash;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
