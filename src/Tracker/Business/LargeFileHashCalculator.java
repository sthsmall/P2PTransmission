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

    public LargeFileHashCalculator (File file) {
        this.file = file;
        try {
            byte[] hashBytes = calculateFileHash(file, hashAlgorithm);
            hash = bytesToHex(hashBytes);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }



    public String getHash() {
        return hash;
    }

    private static byte[] calculateFileHash(File file, String algorithm)
            throws IOException, NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            messageDigest.update(buffer, 0, bytesRead);
        }
        fileInputStream.close();
        return messageDigest.digest();
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
