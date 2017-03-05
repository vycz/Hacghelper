package moe.ggg.hacghelper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by orange on 2017/2/22.
 */

public class ContentActivity extends AppCompatActivity {
    private Intent intent;
    private WebView webView;
    private String Magnet;
    private ProgressDialog progressDialog;
    private FloatingActionButton floatingActionButton;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private SimpleDraweeView simpleDraweeView;
    private Toolbar toolbar;
    private String url;
    private MyHandler myHandler;
    private MyRunnable myRunnable;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.ffsb);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myRunnable.getLink() != "无法获取磁力链接"){
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(myRunnable.getLink()));
                        i.addCategory("android.intent.category.DEFAULT");
                        startActivity(i);
                }else {
                    Toast.makeText(ContentActivity.this,myRunnable.getLink(),Toast.LENGTH_LONG).show();
                }
            }
        });
        simpleDraweeView = (SimpleDraweeView)findViewById(R.id.coll_dv);
        toolbar = (Toolbar)findViewById(R.id.sroll_toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("正在进行：");
        progressDialog.setMessage("加载中...");
        progressDialog.show();
        webView = (WebView)findViewById(R.id.wv);
        intent = getIntent();
        url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");
        String imgurl = intent.getStringExtra("imgurl");
        if (title != null||title != ""){
            collapsingToolbarLayout.setTitle(title);
        }else         collapsingToolbarLayout.setTitle("助手");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                WebView.HitTestResult hit = view.getHitTestResult();
//                if(hit != null){
//                    int hitType = hit.getType();
//                    if(hitType == WebView.HitTestResult.SRC_ANCHOR_TYPE || hitType == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE){
//                        Intent i = new Intent(Intent.ACTION_VIEW);
//                        i.setData(request.getUrl().parse(url));
//                        startActivity(i);
//
//                    }else{
//                        view.loadUrl(request.getUrl().toString());
//                    }
//                }else{
//                    view.loadUrl(request.getUrl().toString());
//                }
                return false;
            }


        });
        simpleDraweeView.setImageURI(imgurl);
        myHandler = new MyHandler();
        myRunnable = new MyRunnable();
        new Thread(myRunnable).start();
    }
    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            String html = (String)msg.obj;

            webView.loadDataWithBaseURL(null,html,"text/html","utf-8",null);

        }
    }
    class MyRunnable implements Runnable{
        public String getLink(){
            if(MagLink == ""){
                MagLink = "无法获取磁力链接";
            }
            return MagLink;
        }
        private String MagLink = "";
        @Override
        public void run() {
            String html = new GetContent(url).get();
            MagLink = getMagnet(html);
            Message message = new Message();
            message.obj = html;
            myHandler.sendMessage(message);
        }
    }
    class MyWebViewClient extends WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressDialog.dismiss();
        }
    }
    public String getMagnet(String html){
        Pattern p = Pattern.compile("[0-9a-zA-Z]{40}");
        Matcher m = p.matcher(html);
        String magnet = "";
        if(m.find()){
            magnet="magnet:?xt=urn:btih:"+ m.group(m.groupCount());
        }
        return magnet;
    }
}
