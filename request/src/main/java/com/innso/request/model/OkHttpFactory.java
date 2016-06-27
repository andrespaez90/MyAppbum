package com.innso.request.model;


import android.util.Log;

import com.squareup.okhttp.OkHttpClient;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class OkHttpFactory {

    private static final String sTag = OkHttpFactory.class.getName();

    public static OkHttpClient getHttpclient() {
        return new OkHttpClient();
    }

    public static OkHttpClient getUnsafeOkHttpClient() {
        OkHttpClient okHttpClient = null;
        TrustManager[] trustManager = getUnsafeCertificateValidator();
        okHttpClient = addCertificateValidator(okHttpClient, trustManager);
        return okHttpClient;
    }

    public static OkHttpClient getSafeOkHttpClient(TrustManager[] trustManager) {
        OkHttpClient okHttpClient = null;
        okHttpClient = addCertificateValidator(okHttpClient, trustManager);
        return okHttpClient;
    }

    private static OkHttpClient addCertificateValidator(OkHttpClient okHttpClient, TrustManager[] trustManager) {
        SSLSocketFactory sslSocket = getSSLWithTrustManager(trustManager);
        if (sslSocket != null) {
            okHttpClient = getHttpclient();
            okHttpClient.setSslSocketFactory(getSSLWithTrustManager(trustManager));
        }
        return okHttpClient;
    }

    private static SSLSocketFactory getSSLWithTrustManager(TrustManager[] trustManager) {
        final SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustManager, new java.security.SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            Log.e(sTag, e.getMessage());
        }
        return null;
    }

    private static TrustManager[] getUnsafeCertificateValidator() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }
        };
    }
}