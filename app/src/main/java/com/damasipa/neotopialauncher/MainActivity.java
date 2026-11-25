package com.damasipa.neotopialauncher;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.webkit.WebResourceRequest;
import android.net.Uri;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView = new WebView(this);
        setContentView(webView);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view, WebResourceRequest request) {

                String url = request.getUrl().toString();

                if (url.startsWith("intent://")) {
                    Toast.makeText(
                        MainActivity.this,
                        "Pengalihan Neotopia terdeteksi",
                        Toast.LENGTH_SHORT
                    ).show();

                    return true;
                }

                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view, String url) {

                if (url.startsWith("intent://")) {
                    return true;
                }

                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                String menu =
                    "(function() {" +
                    "if(document.getElementById('neo-menu')) return;" +
                    "let btn=document.createElement('button');" +
                    "btn.id='neo-menu';" +
                    "btn.innerText='NEO MENU';" +
                    "btn.style='position:fixed;top:80px;right:10px;z-index:999999;padding:12px;background:#1685ff;color:white;border:0;border-radius:8px';" +
                    "document.body.appendChild(btn);" +

                    "let panel=document.createElement('div');" +
                    "panel.style='display:none;position:fixed;top:125px;right:10px;width:250px;padding:12px;background:#222;color:white;z-index:999999;border-radius:10px';" +
                    "panel.innerHTML=" +
                    "'<b>NEOTOPIA SCRIPT MANAGER</b>' +" +
                    "'<br><input id=\"neo-name\" placeholder=\"Nama script\" style=\"width:95%;margin-top:10px\">' +" +
                    "'<br><textarea id=\"neo-code\" placeholder=\"Tempel script di sini\" style=\"width:95%;height:90px;margin-top:8px\"></textarea>' +" +
                    "'<br><button id=\"neo-save\">Simpan</button>' +" +
                    "'<div id=\"neo-list\"></div>';" +

                    "document.body.appendChild(panel);" +

                    "btn.onclick=function(){" +
                    "panel.style.display=panel.style.display==='none'?'block':'none';" +
                    "showList();" +
                    "};" +

                    "function showList(){" +
                    "let data=JSON.parse(localStorage.getItem('neoScripts')||'[]');" +
                    "let list=document.getElementById('neo-list');" +
                    "list.innerHTML='';" +
                    "data.forEach(function(s,i){" +
                    "let row=document.createElement('div');" +
                    "row.style='margin-top:8px';" +
                    "row.textContent=s.name+' ';" +
                    "let del=document.createElement('button');" +
                    "del.textContent='Hapus';" +
                    "del.onclick=function(){" +
                    "data.splice(i,1);" +
                    "localStorage.setItem('neoScripts',JSON.stringify(data));" +
                    "showList();" +
                    "};" +
                    "row.appendChild(del);" +
                    "list.appendChild(row);" +
                    "});" +
                    "}" +

                    "document.getElementById('neo-save').onclick=function(){" +
                    "let name=document.getElementById('neo-name').value.trim();" +
                    "let code=document.getElementById('neo-code').value;" +
                    "if(!name||!code){alert('Isi nama dan script dulu');return;}" +
                    "let data=JSON.parse(localStorage.getItem('neoScripts')||'[]');" +
                    "data.push({name:name,code:code});" +
                    "localStorage.setItem('neoScripts',JSON.stringify(data));" +
                    "document.getElementById('neo-name').value='';" +
                    "document.getElementById('neo-code').value='';" +
                    "showList();" +
                    "alert('Script tersimpan');" +
                    "};" +
                    "})();";

                view.evaluateJavascript(menu, null);
            }
        });

        webView.loadUrl("https://playneotopia.com/");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
