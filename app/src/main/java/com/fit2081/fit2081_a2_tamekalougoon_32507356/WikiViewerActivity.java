package com.fit2081.fit2081_a2_tamekalougoon_32507356;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WikiViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_web_viewer);
        WebView webView = findViewById(R.id.wikiView);

        //create a client so it does not depend on external client
        webView.setWebViewClient(new WebViewClient());

        // wikipedia url
        String wikiLink = "https://en.wikipedia.org/wiki/";

        // the name of the item the user wrote
        String itemName = getIntent().getStringExtra("itemName");

        // the combined string
        String url = wikiLink + itemName;
        webView.loadUrl(url);

    }

}
