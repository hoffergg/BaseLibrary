package com.dailycation.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;



/**
 * 用于加载html文件的页面
 */
public class WebActivity extends BaseActivity {
    public final static String EXTRA_TITLE = "extra_title";
    public final static String EXTRA_URL = "extra_url";
    private String mTitle,mUrl;
    private TextView mTitleView;
    private WebView mWebView;
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private LinearLayout webContainer;
    private BaseApplication application;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = BaseApplication.getBaseApplication();
        setContentView(R.layout.activity_web);
        initView();
        parseIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        parseIntent(intent);
    }

    /**
     * 解析传递数据
     * @param intent
     */
    public void parseIntent(Intent intent){
        mTitle = intent.getStringExtra(EXTRA_TITLE);
        mUrl = intent.getStringExtra(EXTRA_URL);
        if(mTitle!=null)
            mTitleView.setText(mTitle);
        if(TextUtil.isUrl(mUrl))
            loadUrl(mUrl);
    }

    public void initView(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitleView = (TextView) findViewById(R.id.title);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //初始化WebView
        webContainer = (LinearLayout) findViewById(R.id.web_container);
        mWebView = new WebView(application);
        mWebView.setVerticalScrollBarEnabled(false);
        webContainer.addView(mWebView);

        //设置支持JavaScript等
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);//是否支持缩放
        settings.setDisplayZoomControls(false); //是否显示缩放工具
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //设置缓存
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mProgressBar.setProgress(newProgress);
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });

    }

    /**
     * 加载网页
     * @param url
     */
    public void loadUrl(String url){
        mProgressBar.setVisibility(View.VISIBLE);
        mWebView.loadUrl(url);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mWebView!=null)
            mWebView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mWebView!=null) {
            mWebView.setWebViewClient(null);
            mWebView.destroy();
        }
        webContainer.removeAllViews();
    }

    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack())
            mWebView.goBack();
        else
            super.onBackPressed();
    }

    public void onShare(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share));
        intent.putExtra(Intent.EXTRA_TEXT, mTitle + mUrl);
        startActivity(Intent.createChooser(intent, getString(R.string.share)));
    }
}
