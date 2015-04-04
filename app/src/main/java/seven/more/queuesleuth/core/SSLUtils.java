package seven.more.queuesleuth.core;

import android.content.Context;
import android.content.res.AssetManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class SSLUtils {

    private static final Logger LOG = LoggerFactory.getLogger(SSLUtils.class.getSimpleName());

    public static KeyStore getKeyStore(Context context) {
        KeyStore keyStore = null;
        try {
            AssetManager assetManager = context.getAssets();
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = assetManager.open("cert.pem");
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                LOG.debug("ca={}", ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }

            String keyStoreType = KeyStore.getDefaultType();
            keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);
        } catch (Exception e) {
            LOG.error("Error during getting keystore", e);
        }
        return keyStore;
    }
}
