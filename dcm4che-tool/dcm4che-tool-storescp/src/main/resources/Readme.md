### 生成TLS证书用于测试
[DICOM testing over TLS](https://www.digihunch.com/2023/02/dicom-testing-with-tls/)
## 第一个命令为 CA 生成私钥和自签名证书。第二个为网站创建私钥和 CSR。第三个使用 CA 的签名私钥从网站对 CSR 进行签名。输出是网站的证书。
```bash
openssl req -x509 -sha256 -newkey rsa:4096 -keyout ca.key -out ca.crt -days 356 -nodes -subj '/CN=Health Certificate Authority'
openssl req -new -newkey rsa:4096 -keyout server.key -out server.csr -nodes -subj '/CN=*.healthviewcn.com'
openssl x509 -req -sha256 -days 365 -in server.csr -CA ca.crt -CAkey ca.key -set_serial 01 -out server.crt
```
https://comp.protocols.dicom.narkive.com/1m0D7j8R/tls-in-dicom
https://qiita.com/tatsunidas/items/58df4bd24197e70b9d5a

```java 
import org.dcm4che3.net.Connection;
import org.dcm4che3.net.Device;
import org.dcm4che3.net.service.StorageService;
import org.dcm4che3.net.service.StorageServiceRegistry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.Security;

public class TLSConfiguredStoreSCP {

    public static void main(String[] args) throws Exception {
        // 加载PEM文件
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        FileInputStream fis = new FileInputStream("/path/to/your/pem/file.pem");
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        keystore.load(fis, "your_passphrase".toCharArray());

        // 创建SSL上下文
        SSLContext sslContext = SSLContext.getInstance("TLS");
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(keystore, "your_passphrase".toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(keystore);

        // 初始化SSL上下文
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        // 创建设备和连接
        Device device = new Device("my-store-scp");
        Connection conn = new Connection();
        conn.setTls(true);
        conn.setTlsNeedClientAuth(true);
        conn.setTlsNeedClientAuth(true);
        conn.setTlsProtocol("TLSv1.2");
        conn.setTlsCipherSuites(new String[]{"TLS_RSA_WITH_AES_128_CBC_SHA"});
        conn.setHostname("0.0.0.0");
        conn.setPort(11112);
        conn.setSocketFactory(sslSocketFactory);

        // 创建Storage Service并添加到设备
        StorageService storageService = new StorageService("*");
        device.addConnection(conn);
        device.addService(storageService);

        // 启动设备
        device.bindConnections();
        device.bindServices();

        // 注册Storage Service
        StorageServiceRegistry.getInstance().register(storageService);

        // 等待存储请求
        System.out.println("Waiting for storage requests...");
        while (true) {
            Thread.sleep(1000);
        }
    }
}
```