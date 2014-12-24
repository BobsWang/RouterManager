package com.wolf.routermanager.http.inter;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.protocol.HttpContext;

import com.loopj.android.http.AsyncHttpClient;

public class RedirectHttpClient extends AsyncHttpClient {

    @Override
    public void setEnableRedirects(final boolean enableRedirects) {
        ((DefaultHttpClient) getHttpClient())
                .setRedirectHandler(new DefaultRedirectHandler() {
                    @Override
                    public boolean isRedirectRequested(HttpResponse response,
                                                       HttpContext context) {
                        int statusCode = response.getStatusLine()
                                .getStatusCode();
                        if (statusCode == 301 || statusCode == 302) {
                            return enableRedirects;
                        }
                        return false;
                    }
                });
    }
}
