package com.swasi.androidsecurity.storeInKeystore;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.security.auth.x500.X500Principal;

import static java.security.spec.RSAKeyGenParameterSpec.F4;

public class KeyStoreHelper {

    public static final String TAG = "KeyStoreHelper";

    /**
     * Creates a public and private key and stores it using the Android Key
     * Store, so that only this application will be able to access the keys.
     */
    public static void createKeys(Context context, String alias) throws NoSuchProviderException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        if (!isSigningKey(alias)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                createKeysM(alias, false);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    static void createKeysM(String alias, boolean requireAuth) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_RSA, SecurityConstants.KEYSTORE_PROVIDER_ANDROID_KEYSTORE);
            keyPairGenerator.initialize(
                    new KeyGenParameterSpec.Builder(
                            alias,
                            KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                            .setAlgorithmParameterSpec(new RSAKeyGenParameterSpec(1024, F4))
                            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                            .setDigests(KeyProperties.DIGEST_SHA256,
                                    KeyProperties.DIGEST_SHA384,
                                    KeyProperties.DIGEST_SHA512)
                            // Only permit the private key to be used if the user authenticated
                            // within the last five minutes.
                            .setUserAuthenticationRequired(requireAuth)
                            .build());
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            Log.d(TAG, "Public Key is: " + keyPair.getPublic().toString());

        } catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * JBMR2+ If Key with the default alias exists, returns true, else false.
     * on pre-JBMR2 returns true always.
     */
    public static boolean isSigningKey(String alias) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            try {
                KeyStore keyStore = KeyStore.getInstance(SecurityConstants.KEYSTORE_PROVIDER_ANDROID_KEYSTORE);
                keyStore.load(null);
                return keyStore.containsAlias(alias);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Returns the private key signature on JBMR2+ or else null.
     */
    public static String getSigningKey(String alias) {

        String key = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Certificate cert = getPrivateKeyEntry(alias).getCertificate();
            if (cert == null) {
                return null;
            }
            try {
                key = Base64.encodeToString(cert.getEncoded(), Base64.NO_WRAP);
            } catch (CertificateEncodingException e) {
                e.printStackTrace();
            }
        } else {
            return null;
        }
        return key;
    }

    private static KeyStore.PrivateKeyEntry getPrivateKeyEntry(String alias) {
        try {
            KeyStore ks = KeyStore
                    .getInstance(SecurityConstants.KEYSTORE_PROVIDER_ANDROID_KEYSTORE);
            ks.load(null);
            KeyStore.Entry entry = ks.getEntry(alias, null);

            if (entry == null) {
                Log.w(TAG, "No key found under alias: " + alias);
                Log.w(TAG, "Exiting signData()...");
                return null;
            }

            if (!(entry instanceof KeyStore.PrivateKeyEntry)) {
                Log.w(TAG, "Not an instance of a PrivateKeyEntry");
                Log.w(TAG, "Exiting signData()...");
                return null;
            }
            return (KeyStore.PrivateKeyEntry) entry;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    private static final String KEYSTORE = "AndroidKeyStore";

    public static String encrypt(String alias, String plaintext) {
        try {
            PublicKey publicKey = getPrivateKeyEntry(alias).getCertificate().getPublicKey();
            Cipher cipher = getCipher();
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.encodeToString(cipher.doFinal(plaintext.getBytes()), Base64.NO_WRAP);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String alias, String ciphertext) {
        try {
            PrivateKey privateKey = getPrivateKeyEntry(alias).getPrivateKey();
            Cipher cipher = getCipher();
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(Base64.decode(ciphertext, Base64.NO_WRAP)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance(
                String.format("%s/%s/%s",
                        SecurityConstants.TYPE_RSA,
                        SecurityConstants.BLOCKING_MODE,
                        SecurityConstants.PADDING_TYPE));
    }

    private static final String ALIAS = "MY_APP";
    private static final String TYPE_RSA = "RSA";
    private static final String CYPHER = "RSA/ECB/PKCS1Padding";
    private static final String ENCODING = "UTF-8";

    public static byte[] encryptData(String dataToEncrypt, final String ANDROIDKEYSTORE, String alias) {
        try {
            final KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_HMAC_SHA256, ANDROIDKEYSTORE);
            final KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec.Builder(alias,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build();
            keyGenerator.init(keyGenParameterSpec);
            final SecretKey secretKey = keyGenerator.generateKey();
            final Cipher cipher = getCipher();
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(dataToEncrypt.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public static String rsaEncryptKey(String keyAlias, String secret) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, NoSuchProviderException, NoSuchPaddingException, UnrecoverableEntryException, InvalidKeyException {


        if (TextUtils.isEmpty(secret)) {
            return "";
        }

        byte[] secretBytes = secret.getBytes();

        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);


            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(keyAlias, null);
//            Cipher inputCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidOpenSSL");
            Cipher inputCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidKeyStoreBCWorkaround");
            inputCipher.init(Cipher.ENCRYPT_MODE, privateKeyEntry.getCertificate().getPublicKey());

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, inputCipher);
            cipherOutputStream.write(secretBytes);
            cipherOutputStream.close();

            byte[] encryptedKeyAsByteArray = outputStream.toByteArray();
            return new String(encryptedKeyAsByteArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String rsaDecryptKey(String keyAlias, String encrypted) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableEntryException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException {

        if (TextUtils.isEmpty(encrypted)) {
            return "";
        }

        byte[] encryptedBytes = encrypted.getBytes();

        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);

            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(keyAlias, null);
//        Cipher output = Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidOpenSSL");
            Cipher output = Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidKeyStoreBCWorkaround");
            output.init(Cipher.DECRYPT_MODE, privateKeyEntry.getPrivateKey());
            CipherInputStream cipherInputStream = new CipherInputStream(
                    new ByteArrayInputStream(encryptedBytes), output);
            ArrayList<Byte> values = new ArrayList<>();


            byte[] buffer = new byte[4096];
            int len;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = cipherInputStream.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            byte[] cipherByteArray = baos.toByteArray();

       /* int nextByte;
        while ((nextByte = cipherInputStream.read()) != -1) {
            values.add((byte)nextByte);
        }
*/


            byte[] decryptedKeyAsBytes = new byte[cipherByteArray.length];
            for (int i = 0; i < decryptedKeyAsBytes.length; i++) {
                decryptedKeyAsBytes[i] = cipherByteArray[i];
            }
            return new String(decryptedKeyAsBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String rsaEncrypt(final String keyAlias, final String dataToEncrypt) {
        if (TextUtils.isEmpty(dataToEncrypt)) {
            return "";
        }
        byte[] decryptedBytes = dataToEncrypt.getBytes();

        byte[] encryptedBytes = null;
        try {
            final KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            final KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(keyAlias, null);
            final RSAPublicKey publicKey = (RSAPublicKey) privateKeyEntry.getCertificate().getPublicKey();
            // "RSA/ECB/PKCS1Padding"
            final Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidOpenSSL");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
            cipherOutputStream.write(decryptedBytes);
            cipherOutputStream.close();

            encryptedBytes = outputStream.toByteArray();

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return new String(encryptedBytes);
    }

    public static String rsaDecrypt(final String keyAlias, final String encryptedDataToDeprict) {

        if (TextUtils.isEmpty(encryptedDataToDeprict)) {
            return "";
        }

        byte[] encryptedBytes = encryptedDataToDeprict.getBytes();

        byte[] decryptedBytes = null;
        try {
            final KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            final KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(keyAlias, null);
//            final RSAPrivateKey privateKey = (RSAPrivateKey)privateKeyEntry.getPrivateKey();

            final Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidOpenSSL");
            final RSAPublicKey publicKey = (RSAPublicKey) privateKeyEntry.getCertificate().getPublicKey();
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
//            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            final CipherInputStream cipherInputStream = new CipherInputStream(new ByteArrayInputStream(encryptedBytes), cipher);
            final ArrayList<Byte> arrayList = new ArrayList<>();

            int b = 0;
            byte[] d = new byte[8];
            while ((b = cipherInputStream.read(d)) != -1) {
                arrayList.add((byte) b);
            }

           /* int nextByte = 1;
            while ( (nextByte = cipherInputStream.read()) != -1 )
            {
                arrayList.add((byte)nextByte);
            }*/

            decryptedBytes = new byte[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++) {
                decryptedBytes[i] = arrayList.get(i);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return new String(decryptedBytes);
    }

    public static void put(Context ctx, String key, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        if (value == null) {
            prefs.edit().putString(key, null).apply();
        } else {
            try {
                prefs.edit().putString(key, encryptString(ctx, value)).apply();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static String get(Context ctx, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String pref = prefs.getString(key, "");

        if (!TextUtils.isEmpty(pref)) {
            return decryptString(ctx, pref);
        }

        return null;
    }

    private static String encryptString(Context context, String toEncrypt) {
        try {
            final KeyStore.PrivateKeyEntry privateKeyEntry = getPrivateKey(context);
            if (privateKeyEntry != null) {
                final PublicKey publicKey = privateKeyEntry.getCertificate().getPublicKey();

                // Encrypt the text
                Cipher input = Cipher.getInstance(CYPHER);
                input.init(Cipher.ENCRYPT_MODE, publicKey);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                CipherOutputStream cipherOutputStream = new CipherOutputStream(
                        outputStream, input);
                cipherOutputStream.write(toEncrypt.getBytes(ENCODING));
                cipherOutputStream.close();

                byte[] vals = outputStream.toByteArray();
                return Base64.encodeToString(vals, Base64.DEFAULT);
            }
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return null;
        }
        return null;
    }

    private static String decryptString(Context context, String encrypted) {
        try {
            KeyStore.PrivateKeyEntry privateKeyEntry = getPrivateKey(context);
            if (privateKeyEntry != null) {
                final PrivateKey privateKey = privateKeyEntry.getPrivateKey();

                Cipher output = Cipher.getInstance(CYPHER);
                output.init(Cipher.DECRYPT_MODE, privateKey);

                CipherInputStream cipherInputStream = new CipherInputStream(
                        new ByteArrayInputStream(Base64.decode(encrypted, Base64.DEFAULT)), output);
                ArrayList<Byte> values = new ArrayList<>();
                int nextByte;
                while ((nextByte = cipherInputStream.read()) != -1) {
                    values.add((byte) nextByte);
                }

                byte[] bytes = new byte[values.size()];
                for (int i = 0; i < bytes.length; i++) {
                    bytes[i] = values.get(i);
                }

                return new String(bytes, 0, bytes.length, ENCODING);
            }

        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return null;
        }

        return null;
    }

    private static KeyStore.PrivateKeyEntry getPrivateKey(Context context) throws KeyStoreException,
            CertificateException, NoSuchAlgorithmException,
            IOException, UnrecoverableEntryException {

        KeyStore ks = KeyStore.getInstance(KEYSTORE);

        // Weird artifact of Java API.  If you don't have an InputStream to load, you still need
        // to call "load", or it'll crash.
        ks.load(null);

        // Load the key pair from the Android Key Store
        KeyStore.Entry entry = ks.getEntry(ALIAS, null);

        /* If the entry is null, keys were never stored under this alias.
         */
        if (entry == null) {
            Log.w(TAG, "No key found under alias: " + ALIAS);
            Log.w(TAG, "Generating new key...");
            try {
                createKeys(context);

                // reload keystore
                ks = KeyStore.getInstance(KEYSTORE);
                ks.load(null);

                // reload key pair
                entry = ks.getEntry(ALIAS, null);

                if (entry == null) {
                    Log.w(TAG, "Generating new key failed...");
                    return null;
                }
            } catch (NoSuchProviderException e) {
                Log.w(TAG, "Generating new key failed...");
                e.printStackTrace();
                return null;
            } catch (InvalidAlgorithmParameterException e) {
                Log.w(TAG, "Generating new key failed...");
                e.printStackTrace();
                return null;
            }
        }

        /* If entry is not a KeyStore.PrivateKeyEntry, it might have gotten stored in a previous
         * iteration of your application that was using some other mechanism, or been overwritten
         * by something else using the same keystore with the same alias.
         * You can determine the type using entry.getClass() and debug from there.
         */
        if (!(entry instanceof KeyStore.PrivateKeyEntry)) {
            Log.w(TAG, "Not an instance of a PrivateKeyEntry");
            Log.w(TAG, "Exiting signData()...");
            return null;
        }

        return (KeyStore.PrivateKeyEntry) entry;
    }

    /**
     * Creates a public and private key and stores it using the Android Key Store, so that only
     * this application will be able to access the keys.
     */
    private static void createKeys(Context context) throws NoSuchProviderException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        // Create a start and end time, for the validity range of the key pair that's about to be
        // generated.
        Calendar start = new GregorianCalendar();
        Calendar end = new GregorianCalendar();
        end.add(Calendar.YEAR, 25);

        // The KeyPairGeneratorSpec object is how parameters for your key pair are passed
        // to the KeyPairGenerator.  For a fun home game, count how many classes in this sample
        // start with the phrase "KeyPair".
        KeyPairGeneratorSpec spec =
                new KeyPairGeneratorSpec.Builder(context)
                        // You'll use the alias later to retrieve the key.  It's a key for the key!
                        .setAlias(ALIAS)
                        // The subject used for the self-signed certificate of the generated pair
                        .setSubject(new X500Principal("CN=" + ALIAS))
                        // The serial number used for the self-signed certificate of the
                        // generated pair.
                        .setSerialNumber(BigInteger.valueOf(1337))
                        // Date range of validity for the generated pair.
                        .setStartDate(start.getTime())
                        .setEndDate(end.getTime())
                        .build();

        // Initialize a KeyPair generator using the the intended algorithm (in this example, RSA
        // and the KeyStore.  This example uses the AndroidKeyStore.
        final KeyPairGenerator kpGenerator = KeyPairGenerator.getInstance(TYPE_RSA, KEYSTORE);
        kpGenerator.initialize(spec);

        final KeyPair kp = kpGenerator.generateKeyPair();
        Log.d(TAG, "Public Key is: " + kp.getPublic().toString());
    }

    public interface SecurityConstants {
        String KEYSTORE_PROVIDER_ANDROID_KEYSTORE = "AndroidKeyStore";
        String TYPE_RSA = "RSA";
        String PADDING_TYPE = "PKCS1Padding";
        String BLOCKING_MODE = "NONE";

        String SIGNATURE_SHA256withRSA = "SHA256withRSA";
        String SIGNATURE_SHA512withRSA = "SHA512withRSA";
    }

}