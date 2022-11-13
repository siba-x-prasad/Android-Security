# ANDROID SECURITY
- https://proandroiddev.com/secure-data-in-android-encryption-in-android-part-1-e5fd150e316f
- https://labs.nettitude.com/tutorials/tls-certificate-pinning-101/

- Its contains all the sample code of new and advance concepts in android

- We will Discuss the below points in this repository
- Cryptography
- Encryption and Decryption
- Hashing
- Symmetric and Asymmetric encryption
- SSL Pinning
- Encrypted Shared Preference
- Encode and decode

## Encoding
- Encoding transforms data into another format using a scheme that is publicly available so that it can easily be reversed.
- It does not require a key as the only thing required to decode it is the algorithm that was used to encode it.
- Examples: ascii, unicode, URL Encoding, base64

## Decoding
- Encoding transforms data into another format using a scheme that is publicly available so that it can easily be reversed.
- It does not require a key as the only thing required to decode it is the algorithm that was used to encode it.
- Examples: ascii, unicode, URL Encoding, base64

## Hashing
- https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
- Hashing serves the purpose of ensuring integrity, i.e. making it so that if something is changed you can know that it’s changed.
- Technically, hashing takes arbitrary input and produce a fixed-length string that has the following attributes:
    - The same input will always produce the same output.
    - Multiple disparate inputs should not produce the same output.
    - It should not be possible to go from the output to the input.
    - Any modification of a given input should result in drastic change to the hash.
- Examples: sha-3, md5 (now obsolete), etc.
## What is hashing in cryptography?
- cryptography uses multiple hash functions to secure data. Some of the most popular cryptographic hashes include the following:

- Secure Hash Algorithm 1 (SHA-1)
- Secure Hash Algorithm 2 (SHA-2)
- Secure Hash Algorithm 3 (SHA-3)
- MD2
- MD4
- MD5

## Salting in Hashing
- Salted password hashing can be used to improve password security by adding additional layers of randomness on top of the hashing process. 
- Salt is a cryptographically secure random string that is added to a password before it’s hashed, 
- and the salt should be stored with the hash, making it difficult for an attacker to know the original plaintext without having access to both sources.

## How hashes are used to protect passwords
- You are probably wondering how the system checks your password if it has been hashed in a way that cannot be unscrambled. Well the key is that hashing the same data always gives the same output. So you proceed as follows:
- Take the hash of the original password and store it.
- When the user re-enters their password take the hash of the new password.
- If the new hash matches the old hash then let them in, otherwise refuse entry.

## Encryption
- It is the process of transforming a readable message into an unreadable one. 
- To do so we use encoding algorithms.
- The purpose of encryption is to transform data in order to keep it secret from others.
- e.g. sending someone a secret letter that only they should be able to read, or securely sending a password over the Internet.
- Rather than focusing on usability, the goal is to ensure the data cannot be consumed by anyone other than the intended recipient(s).
- Examples: aes, blowfish, rsa
## Decryption
- It is the process of transforming data or information from an unreadable to readable form. 
- To do so we use decoding algorithms.
- Decryption is the process of taking encoded or encrypted text or other data and converting it back into text that you or the computer can read and understand.
- This term could be used to describe a method of unencrypted the data manually or unencrypted the data using the proper codes or keys.
- Examples: aes, rsa, blowfish

## Aes - Advanced Encryption Standard
- https://cybernews.com/resources/what-is-aes-encryption/

## rsa - 
- https://www.comparitech.com/blog/information-security/rsa-encryption/

## CRYPTOGRAPHY
- It is a techniques to convert the plain text to CIPHER text (which is random data/ raw data) using a secret key is known as Cryptography.
- There are two type of cryptography
    - Symmetric Cryptography - Only one public key used to encrypt or decrypt the data.
    - Asymmetric Cryptography - Two keys (private and public ) keys are used to encrypt or decrypt the data.

## Symmetric Key Cryptography
- Same Key used to encrypt and decrypt
- Faster compared to public key encryption
- Key needs to be stored securely.
- Secure channel required to transfer the key
- **Disadvantage**
- Any hacker can get the key and decrypt the encrypted data.
- To decrypt the data, the other party require the secret key to decrypt the code, which need to send in the secure channel.

## Asymmetric Key or Public KeyCryptography
- Uses a public key to and private key
- Public key used to encrypt the data
- private key is used to decrypt the data
- Slower compared with Symmetric key
- The Private Key is intended to be private so that only the authenticated recipient can decrypt the message.
- Public key cryptography allows someone to send their public key in an open, insecure channel.
- Having a friend’s public key allows you to encrypt messages to them.
- Your private key is used to decrypt messages encrypted to you.
- Intermediaries—such as the email service providers, Internet service providers, and those on their networks—are able to see metadata this whole time: who is sending what to whom, when, what time it’s received, what the subject line is, that the message is encrypted, and so on.

## Encrypted Shared Preference
https://developer.android.com/reference/androidx/security/crypto/EncryptedSharedPreferences
- Wraps the SharedPreferences class and automatically encrypts keys and values using a two-scheme method:
    - Keys are encrypted using a deterministic encryption algorithm such that the key can be encrypted and properly looked up.
    - Values are encrypted using AES-256 GCM and are non-deterministic.
    
## SQL INJECTION
- https://portswigger.net/web-security/sql-injection

## How to avoid to use your application classes from another
- Enforcing permissions via the AndroidManifest.xml file means different things to each of the application component types.
- This is because of the various inter-process communications ( IPC ) mechanisms that can be used to interact with them.
- For every application component, the android:permission attribute does the following:
    - Activity : Limits the application components which are external to your application that can successfully call startActivity or startActivityForResult to those with the required permission
    - Service : Limits the external application components that can bind (by calling bindService()) or start (by calling startService()) the service to those with the specified permission
    - Receiver : Limits the number of external application components that can send broadcasted intents to the receiver with the specified permission
    - Provider : Limits access to data that is made accessible via the content provider
    
## SSL Pinning
- https://mailapurvpandey.medium.com/ssl-pinning-in-android-90dddfa3e051
- 
- What SSL ?
    - It Stands For Secure Socket Layer.
    - SSL certificate create a foundation of trust by establishing a secure connection.
    - The connection ensures that all data passed between the web server and web browsers remain private and integral.
    - SSL certificate have a key pairs
        - A public key and a private key
    - These keys work together to establish an encrypted connection.
    - The certificate also contains what is called the subject which is the identity of the certificate/website owner.

## How SSL working ?
- Client Machine sends a connection request to server, server listens the request.
- Server gives response including public key and certificate.
- Client checks the certificate and sends a encrypted key/ public key to server.
- Server decrypt the key and sends encrypted data back to the client machine.
- Client receives and decrypt that encrypted data.

## What is Pinning ?
- Pinning is an optional mechanism that can be used to improve the security of a service or site that relies on SSL Certificates.
- Pinning allows you to specify a cryptographic identity that should be accepted by users visiting your site.

## What to pin ?
- Certificate
    - Normally the certificate is easiest to pin.
    - At runtime, you retrieve the website or server’s certificate.
    - You compare the retrieved certificate with the certificate embedded within the application
    - If the site/service rotates its certificate on a regular basis, then your application would need to be updated regularly
    - This is one of the biggest disadvantage of Certificate Pinning. 
    - Whenever there is a change in certificate, then the application need to be update with new embedded certificate.
    - Otherwise need to embed all the certificates which is going to use in future in server side.
- Public Key
    - More flexible
    - A little trickier due to the extra steps necessary to extract the public key from a certificate
    - Its harder to work with keys since you must extract the key from the certificate – can be somewhat of a pain in Cocoa/CocoaTouch and OpenSSL.
    - As with a certificate, the program checks the extracted public key with its embedded copy of the public key
- Hash
    - Allows you to anonymize a certificate or public key
    - this might be important if you application is concerned about leaking information during de-compilation and reverse engineering
    - A digested certificate fingerprint is often available as a native API for many libraries, so its convenient to use
    - An organization might want to supply a reserve identity in case the primary identity is compromised
## Where to pin ?
- The server’s certificate (a.k.a. leaf certificate)
- The Certificate Authority’s certificate (a.k.a. root certificate)
- An intermediate certificate
- The whole certificate chain

## SSL PINNING EXPLAIN
### What is SSL Pinning ?
- Frida SSL Pinning Burp
- Charls for proxy certificate
- truestkit - github dummy ssl pinning example
- There are two major factors in an HTTPS connection
    - A valid certificate that server presents during handshaking.
    - A cipher suite to be used for data encryption during transmission.
- The certificate is the essential component and serves as a proof of identity of the server.
- The client will only trust the server if the server can provide a valid certificate that is signed by one of the trusted Certificate Authorities that come pre-installed in the client.
- Otherwise, the connection will be aborted.

- Every Device have a list of trusted certificate.
- If the man in middle can create clone the certificates available in device which the server can easily trust.
- An attacker/Man In Middle can abuse this mechanism by either install a malicious root CA certificate to user devices so the client will trust all certificates that are signed by the attacker, or even worse, compromised a CA completely.
- Therefore relying on the certificates received from servers alone cannot guarantee the authenticity of the server, and it is vulnerable to a potential man-in-the-middle attack.
- SSL Pinning is a technique that we use in the client side to avoid man-in-the-middle attack by validating the server certificates again even after SSL handshaking.
- The developers embed (or pin) a list of trustful certificates to the client application during development, and use them to compare against the server certificates during runtime.
- If there is a mismatch between the server and the local copy of certificates, the connection will simply be disrupted, and no further user data will be even sent to that server.
- This enforcement ensures that the user devices are communicating only to the dedicated trustful servers.

- There is a disadvantage in SSL certificate pinning, because when the server certificate expires.
- Then server install new SSL certificate. But client is using the same old certificate.
- That time server is not able to trust the old certificate from client.
- We can avoid this by using below
    - Add all the list of certificate in client side which are going to be update in future
    - Or update your client application with the new certificate.
- Pin either the whole certificate or its hashed public key.
- The hashed public key pinning is the preferred approach because the same private key can be used in signing the updated certificate, therefore we can save the trouble of pinning a new hashed public key for a new certificate, and reduce the risk of app ‘bricking’.

## Key concept of SSL pinning
- Java KeyStore
    - The Java KeyStore is a database that can contain keys. A Java KeyStore is represented by the KeyStore (java.security.KeyStore) class.
    - A KeyStore can be written to disk and read again.
    - The KeyStore as a whole can be protected with a password, and each key entry in the KeyStore can be protected with its own password.
    - This makes the KeyStore class a useful mechanism to handle encryption keys securely.
    - A KeyStore can hold the following types of keys:
        - Private keys
        - Public keys + certificates
    - For more details follow - http://tutorials.jenkov.com/java-cryptography/keystore.html
- SecretKey
    - A secret (symmetric) key.
    - The purpose of this interface is to group (and provide type safety for) all secret key interfaces.
- Key
- KeySpec
- KeyPairGenerator
    - The Java KeyPairGenerator class (java.security.KeyPairGenerator) is used to generate asymmetric encryption / decryption key pairs. An asymmetric key pair consists of two keys.
    - Private Key - The private key is used to encrypt data
    - Public key - the public key can be used to decrypt the data again
    - you could also encrypt data using the public key and decrypt it using the private key.
```
    // Creating a KeyPairGenerator Instance
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    // initialization
    keyPairGenerator.initialize(2048);
    // Generating a Key Pair
    KeyPair keyPair = keyPairGenerator.generateKeyPair();
```
- For more details visit - http://tutorials.jenkov.com/java-cryptography/keypairgenerator.html
- KeyPair
    - The Java KeyPair class (java.security.KeyPair represents an asymmetric key pair.
    - In other words, a public key + private key pair.
    - A KeyPair instance is typically used when performing asymmetric cryptography, like encrypting or signing data.
    ```
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
    ```  
- Key Generator
    - The Java KeyGenerator class (javax.crypto.KeyGenerator) is used to generate symmetric encryption keys.
    - http://tutorials.jenkov.com/java-cryptography/keygenerator.html
- KeyFactory
- KeyAgreement
    - This class provides the functionality of a key agreement (or key exchange) protocol.
    - The keys involved in establishing a shared secret are created by one of the key generators (KeyPairGenerator or KeyGenerator), a KeyFactory, or as a result from an intermediate phase of the key agreement protocol.
    - For each of the correspondents in the key exchange, doPhase needs to be called. For example, if this key exchange is with one other party, doPhase needs to be called once, with the lastPhase flag set to true. If this key exchange is with two other parties, doPhase needs to be called twice, the first time setting the lastPhase flag to false, and the second time setting it to true. There may be any number of parties involved in a key exchange.
    -
- Private Key
- Public Key
- CryptoSpec
- Diff Helman Algorithm
- Java Signature
    - The Java Signature class (java.security.Signature) can create a digital signature for binary data.
    - A digital signature is a message digest encrypted with a private key of a private / public key pair.
    - Anyone in possession of the public key can verify the digital signature.
    - For more details follow - http://tutorials.jenkov.com/java-cryptography/signature.html
- MessageDigest
    - The Java MessageDigest class represents a cryptographic hash function which can calculate a message digest from binary data.
    - When you receive some encrypted data you cannot see from the data itself whether it was modified during transportation.
    - A message digest can help alleviate that problem.
    - For more details - http://tutorials.jenkov.com/java-cryptography/messagedigest.html

## Diffie-Hellman Key Exchange
- https://www.youtube.com/watch?v=YEBfamv-_do&t=362s
- https://www.youtube.com/watch?v=cM4mNVUBtHk
- https://www.youtube.com/watch?v=zLKT4-uRGw4





For Secure application's, Its very important to save the sensitive data with high security so that the middle man cant be hack or leak your sensitive data which leads to huge loss.

TO overcome this isues, we need to follow the following steps

1.Never publish your application without applying proguard, Otherwise anybody can get your code by reverse engineering.  
```  
buildTypes {
release {
minifyEnabled true
shrinkResources true
proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
}
}```

2. Each API have some keys or token or sessionId etc, which you need to store safely with encryption.
3. If you are integrating firebase API, you need to follow the firebase guideline rules
4. Restrict the google API, if its public any hacker can use the public key and make lots of request to the API
5. HTTPS for all the apis. USE ***SSL PINNING*** for your network call.
6. Never store private key and password in shared preference. U can use EncryptedShared preference which is released under jet pack.
7. Remove all the log's you printing in console. ENable only for DEBUG mode.You can use 3rd party library like Timber  
   ```  
   class App :Application(){
   override fun onCreate() {
   super.onCreate()
   if(BuildConfig.DEBUG){
   Timber.plant(Timber.DebugTree())
   }
   //Reset of your code
   }
   }```

8. You can use internal storage to store sensitive files/data.
9. Use Sqlcipher instead of normal sqlite database
10. Do not send any sensitive information through broadcast reciever.
11. Keep your dependencies independent.
12. Protect your service and Content provider from other application by using custom permission.
      ```
      <service
    android:name=".MyAwesomeService"
    android:exported="false" />```  
      
13. Encrypt data on External Storage and Check the validity of that data
14. Avoid Asking for Personal Data
15. Don’t process any payments on a rooted device.
16. Use as little permissions as possible
17. You can use biometric authentication for more security based applications.

## SSL PINNING
Important Links  
https://www.netguru.com/codestories/3-ways-how-to-implement-certificate-pinning-on-android  
https://infinum.co/the-capsized-eight/securing-mobile-banking-on-android-with-ssl-certificate-pinning https://www.nowsecure.com/blog/2017/06/15/certificate-pinning-for-android-and-ios-mobile-man-in-the-middle-attack-prevention/

There are two major factors in an HTTPS connection, a ***valid certificate*** that server presents during handshaking, and a ***cipher*** suite to be used for data encryption during transmission. The certificate is the essential component and serves as a proof of identity of the server. The client will only trust the server if the server can provide a valid certificate that is signed by one of the trusted Certificate Authorities that come pre-installed in the client, otherwise, the connection will be aborted.

An attacker can abuse this mechanism by either install a malicious root CA certificate to user devices so the client will trust all certificates that are signed by the attacker, or even worse, compromised a CA completely. Therefore relying on the certificates received from servers alone cannot guarantee the authenticity of the server, and it is vulnerable to a potential man-in-the-middle attack.

By using mobile application, we use ssl to exchange data between server and client. But sometime we share some sensitive data over the API calls.

In many cases, you'll have to send sensitive data between your application and server. Take mobile banking applications for example. The last thing you want is a malicious hacker to steal someone's bank account info – or worse, their money.

Security is crucial for a mobile banking solution, so you'll be using SSL to keep that data safe and secret. But there's a catch.


**Q. What is SSL pinning ?**  
The SSL pinning (or public key, or certificate pinning) is a technique mitigating Man-in-the-middle attacks against the secure HTTPS communication.

Ans : By default, when making an SSL connection, the client checks that the server’s certificate:

has a verifiable chain of trust back to a trusted (root) certificate  
matches the requested hostname  
What it doesn't do is check if the certificate in question is a specific certificate, namely the one you know your server is using.

Relying on matching certificates between the device's trust store and the remote server opens up a security hole. The device’s trust store can easily be compromised - the user can install unsafe certificates, thus allowing potential man-in-the-middle attacks.

Certificate pinning is the solution to this problem. It means hard-coding the certificate known to be used by the server in the mobile application. The app can then ignore the device’s trust store and rely on its own, and allow only SSL connections to hosts signed with certificates stored inside the application.

This also gives a possibility of trusting a host with a self-signed certificate without the need to install additional certificates on the device.

**Benefits**  
Increased security - with pinned SSL certificates, the app is independent of the device’s trust store. Compromising the hard coded trust store in the app is not so easy - the app would need to be decompiled, changed and then recompiled again - and it can’t be signed using the same Android keystore that the original developer of the app used.

Reduced costs - SSL certificate pinning gives you the possibility to use a self-signed certificate that can be trusted. For example, you’re developing an app that uses your own API server. You can reduce the costs by using a self-signed certificate on your server (and pinning that certificate in your app) instead of paying for a certificate. Although a bit convoluted, this way, you've actually improved security and saved yourself some money.

***drawbacks***  
Less flexibility - when you do SSL certificate pinning, changing the SSL certificate is not that easy. For every SSL certificate change, you have to make an update to the app, push it to Google Play and hope the users will install it.

**Q. What will happen if the certificate expires ?**  
https://github.com/wultra/ssl-pinning-android

**Why do we need to implement SSL Pinning ?**

SSL Pinning is an additional security layer to prevent MITM attack( Man in the Middle Attack) or sniffing data. To intercept the request, we mostly use a proxy tool. The proxy tool installs its own certificate on the device and application trust that certificate as a valid certificate and allow proxy tool to intercept application traffic.

**Ways to Implement SSL Pinning :-**

**1.Certificate Pinning**   
**2.Public Key Pinning**

**Certificate Pinning :-** In certificate pinning , the developer hardcodes some bytecode of SSL certificate into application code. When the application communicates with the server, it checks whether the same bytecode is present in a certificate or not. If it is present, the application sends a request to the server. If the bytecode does not match it will throw an SSL certificate error. This technique prevents an attacker to use his/her own self-signed certificate.

**Public Key Pinning :-** In public key pinning when a customer visits a website, the server pins (by way of injecting it) its public key in client (customer’s) browser. When the client revisits the same website, the server identifies its public key to check the integrity of the connection. This technique also prevents an attacker from using his/her self-signed certificate.

## FOR MORE DETAILS ON ANDROID KEYSTORE
https://developer.android.com/training/articles/keystore  
https://blog.bcaster.com/use-android-keystore-for-securely-storing-and-retrieving-the-data/  


