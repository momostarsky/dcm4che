### OpenSSL is an open-source command-line tool that allows users to perform various SSL-related tasks.In this tutorial, we’ll learn how to create a self-signed certificate with OpenSSL.

[Creating a Self-Signed Certificate With OpenSSL](https://www.baeldung.com/openssl-self-signed-cert）
[参考文件](https://qiita.com/tatsunidas/items/58df4bd24197e70b9d5a)
### 第一种做法

```shell
openssl genrsa -aes128 -passout pass:Xp123#cJ  -out tutorial.key 2048
openssl rsa -passin pass:Xp123#cJ   -in tutorial.key -pubout -out tutorial_pub.key  
openssl req -new -passin pass:Xp123#cJ  -key tutorial.key -out tutorial.csr -subj "/C=CN/ST=ZJ/L=Hangzhou/O=Jianpei Ltd/OU=Development/CN=jpstorage"
openssl req -text -in tutorial.csr -noout -verify
openssl x509   -passin pass:Xp123#cJ   -in tutorial.csr -out tutorial.crt -req -signkey tutorial.key -days 36500
openssl x509 -in tutorial.crt -out tutorial.pem -outform PEM
openssl pkcs12 -export -in tutorial.crt  -passin pass:Xp123#cJ   -inkey tutorial.key -passout pass:Xp123#cJ -out mykey.p12
keytool -importcert -storetype PKCS12 -keystore truststore.p12 -storepass Xp123#cJ  -alias ca -file tutorial.crt -noprompt
```
```shell
storescp 
-b  DCMQRSCP@jpstorage:11112
--key-pass Xp123#cJ
--key-store mykey.p12
--key-store-pass  Xp123#cJ
--trust-store truststore.p12
--trust-store-pass  Xp123#cJ
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
Xp123#cJ
--key-store
mykey.p12
--key-store-pass
Xp123#cJ
--trust-store
truststore.p12
--trust-store-pass
Xp123#cJ
sample.dcm
```


########### 第二种做法
```shell
 
openssl pkcs12 -export -in ../../../../dcm4che-assembly/src/etc/certs/cert.pem -inkey ../../../../dcm4che-assembly/src/etc/certs/key.pem -passout pass:Jp123cjx -out jpkey.p12 
storescp  --tls --key-pass Jp123cjx --key-store jpkey.p12 --key-store-pass Jp123cjx
storescu  --tls --key-pass Jp123cjx --key-store jpkey.p12 --key-store-pass Jp123cjx
```