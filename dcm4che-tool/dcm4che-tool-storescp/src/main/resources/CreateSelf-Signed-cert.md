### OpenSSL is an open-source command-line tool that allows users to perform various SSL-related tasks.In this tutorial, we’ll learn how to create a self-signed certificate with OpenSSL.

[Creating a Self-Signed Certificate With OpenSSL](https://www.baeldung.com/openssl-self-signed-cert）

### Creating a Private Key
First, we’ll create a private key. A private key helps to enable encryption, and is the most important component of our certificate.

Let’s create a password-protected, 2048-bit RSA private key (domain.key) with the openssl command:
```bash
openssl genrsa -des3 -out domain.key 2048

```
### Creating a Certificate Signing Request
``` 
openssl req -key domain.key -new -out domain.csr
```

“A challenge password” and “An optional company name” can be left empty.


```angular2html
openssl genrsa  -out tutorial.key 2048
openssl rsa -in tutorial.key -pubout -out tutorial_pub.key
openssl req -new -key tutorial.key -out tutorial.csr -subj "/C=CN/ST=ZJ/L=Hangzhou/O=Jianpei Ltd/OU=Development/CN=healthview.cn"
openssl req -text -in tutorial.csr -noout -verify
openssl x509 -in tutorial.csr -out tutorial.crt -req -signkey tutorial.key -days 36500
openssl x509 -in tutorial.crt -out tutorial.pem -outform PEM
openssl pkcs12 -export -in tutorial.crt -inkey tutorial.key -out mykey.p12
keytool -importcert -storetype PKCS12 -keystore truststore.p12 -storepass secret -alias ca -file tutorial.crt -noprompt 
```

### https://www.baeldung.com/openssl-self-signed-cert
```shell
openssl genrsa -des3  --passout pass:Hzjp#123  -out domain.key 2048
openssl req -key domain.key -new  -passin  pass:Hzjp#123   -out domain.csr  -subj "/C=CN/ST=ZJ/L=Hangzhou/O=Jianpei Ltd/OU=Development/CN=healthview.cn"
openssl x509 -passin pass:Hzjp#123  -signkey domain.key -in domain.csr -req -days 365 -out domain.crt

 
openssl req -x509 -sha256 -days 1825 -newkey rsa:2048 -keyout rootCA.key  -passout pass:Jp123X@!  -out rootCA.crt   -subj "/C=CN/ST=ZJ/L=Hangzhou/O=Jianpei Ltd/OU=Development/CN=healthview.cn"
openssl x509 -text -noout -in domain.crt
openssl x509 -in domain.crt -outform der -out domain.der
openssl pkcs12 -passin  pass:Hzjp#123   -inkey domain.key -in domain.crt -export -passout pass:Jp1@#2cL   -out domain.pfx
```

```shell
#生成公钥私钥：
openssl genrsa -des3 -passout pass:Hzjp#123  -out private.pem 1024

openssl req -x509 -nodes -days 365 -passin pass:Hzjp#123 -key  private.pem  \
          -out cert.csr -subj "/C=CN/ST=ZJ/L=Hangzhou/O=Jianpei Ltd/OU=Development/CN=192.168.1.92"
          
openssl x509 -req -in cert.csr -out public.crt -outform pem  -passin pass:Hzjp#123  -signkey private.pem -days 3650



openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
          -keyout dcmtk.key  -out dcmtk.crt -subj "/C=CN/ST=ZJ/L=Hangzhou/O=Jianpei Ltd/OU=Development/CN=192.168.1.92"
          
openssl pkcs12 -export -passout pass:JpC#xx13o  -out orthanc.p12 -in orthanc.crt -inkey orthanc.key
openssl pkcs12 -export -passout pass:JpC#xx13o  -out dcm4che.p12 -in dcmtk.crt   -inkey dcmtk.key


```

https://qiita.com/tatsunidas/items/58df4bd24197e70b9d5a

```shell
openssl genrsa -aes128 -passout pass:secret  -out tutorial.key 2048
openssl rsa -passin pass:secret  -in tutorial.key -pubout -out tutorial_pub.key  
openssl req -new -passin pass:secret -key tutorial.key -out tutorial.csr -subj "/C=CN/ST=ZJ/L=Hangzhou/O=Jianpei Ltd/OU=Development/CN=jpstorage"
openssl req -text -in tutorial.csr -noout -verify
openssl x509   -passin pass:secret  -in tutorial.csr -out tutorial.crt -req -signkey tutorial.key -days 36500
openssl x509 -in tutorial.crt -out tutorial.pem -outform PEM
openssl pkcs12 -export -in tutorial.crt  -passin pass:secret  -inkey tutorial.key -passout pass:secret -out mykey.p12
keytool -importcert -storetype PKCS12 -keystore truststore.p12 -storepass secret -alias ca -file tutorial.crt -noprompt
```
```shell
storescp 
-b  DCMQRSCP@jpstorage:11112
--key-pass secret
--key-store mykey.p12
--key-store-pass  secret
--trust-store truststore.p12
--trust-store-pass  secret
--tls 
--tls12
```
```shell
storescu
-c
DCMQRSCP@jpstorage:11112
--tls12
--tls
--key-pass
secret
--key-store
mykey.p12
--key-store-pass
secret
--trust-store
truststore.p12
--trust-store-pass
secret
sample.dcm
```