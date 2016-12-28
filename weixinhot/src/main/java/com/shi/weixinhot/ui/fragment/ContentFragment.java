package com.shi.weixinhot.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shi.weixinhot.R;

/**
 * Created by shimanqiang on 16/12/28.
 */

public class ContentFragment extends Fragment {
    private WebView mWebView;

    private static final String REQUEST_URL = "url";

    public ContentFragment newInstance(String url) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putSerializable(REQUEST_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_content, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String url = bundle.getString(REQUEST_URL);
            mWebView = (WebView) view.findViewById(R.id.webView);
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

        return view;
    }


}
