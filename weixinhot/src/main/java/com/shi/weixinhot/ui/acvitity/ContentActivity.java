package com.shi.weixinhot.ui.acvitity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shi.weixinhot.R;

/**
 * Created by shimanqiang on 16/12/28.
 */

public class ContentActivity extends Activity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        init();
    }

    private void init() {
        String url = getIntent().getStringExtra("url");
        mWebView = (WebView) findViewById(R.id.webView);
        // 开启 localStorage
        mWebView.getSettings().setDomStorageEnabled(true);
        // 设置支持javascript
        mWebView.getSettings().setJavaScriptEnabled(true);
        // 启动缓存
        mWebView.getSettings().setAppCacheEnabled(true);
        // 设置缓存模式
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        //使用自定义的WebViewClient
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
        mWebView.loadUrl(url);
    }
}
