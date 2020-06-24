package com.example.newzz;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import static android.view.View.GONE;


public class web extends Fragment {
    private WebView webView;
    private ProgressBar progressBar;
    private String url = "https://openweathermap.org";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_web, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        progressBar = v.findViewById(R.id.pb);
        webView = v.findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.loadUrl("https://openweathermap.org");
        webView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView webView , String url ){
                progressBar.setVisibility(GONE);
            }
        });

        return v;


    }

}
