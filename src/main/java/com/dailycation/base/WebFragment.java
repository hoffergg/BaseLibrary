package com.dailycation.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebFragment extends Fragment {

    private WebView mWebView;
    private String mUrl;

    public WebFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mWebView = new WebView(getActivity());
        mWebView.getSettings().setJavaScriptEnabled(true);
        return mWebView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(mUrl))
            mWebView.loadUrl(mUrl);
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
