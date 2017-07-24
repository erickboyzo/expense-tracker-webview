package com.erickboyzo.expensetracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private ProgressDialog progress;
    private String EXPENSE_TRACKER_URL = "https://expense-tracker-e0028.firebaseapp.com";
    private CookieManager cookieManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setProgressDiaglog();


        mWebView = (WebView) findViewById(R.id.activity_main_webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        cookieManager= CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(EXPENSE_TRACKER_URL, "cookieName=cookieValue");

        mWebView.loadUrl(EXPENSE_TRACKER_URL);

        mWebView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                progress.dismiss();
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && url.startsWith(EXPENSE_TRACKER_URL)) {
                    return false;
                } else {
                    view.getContext().startActivity(
                            new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private void setProgressDiaglog() {
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show();
    }

}
