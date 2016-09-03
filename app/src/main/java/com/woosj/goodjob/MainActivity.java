package com.woosj.goodjob;

import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;




public class MainActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//

    /**
     * 로그를 위한 태그
     */
    private static final String TAG = "MainActivity";
    /**
     * 웹뷰 객체
     */
    private WebView webview;
    /**
     * 웹사이트 로딩을 위한 버튼
     */
    private Button loadButton;

    private Handler mHandler = new Handler();

    private ProgressBar mProgressBar;

    private ValueCallback<Uri> filePathCallbackNormal;
    private ValueCallback<Uri[]> filePathCallbackLollipop;
    private final static int FILECHOOSER_NORMAL_REQ_CODE = 1;
    private final static int FILECHOOSER_LOLLIPOP_REQ_CODE = 2;


    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);

        setContentView(R.layout.activity_main);


        // 웹뷰 객체 참조
        webview = (WebView) findViewById(R.id.webview);


        WebSettings webSettings = webview.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);



//        // 웹뷰 설정 정보
//        WebSettings webSettings = webview.getSettings();
//        webSettings.setSavePassword(false);
//        webSettings.setSaveFormData(false);
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setSupportZoom(false);
//        webSettings.setAppCacheEnabled(true);
//
//        webSettings.setBuiltInZoomControls(true); // 안드로이드에서 제공하는 줌 아이콘을 사용할 수 있도록 설정
//        webSettings.setPluginState(WebSettings.PluginState.ON_DEMAND); // 플러그인을 사용할 수 있도록 설정
//        webSettings.setSupportMultipleWindows(true); // 여러개의 윈도우를 사용할 수 있도록 설정
//        webSettings.setSupportZoom(true); // 확대,축소 기능을 사용할 수 있도록 설정
//        webSettings.setBlockNetworkImage(false); // 네트워크의 이미지의 리소스를 로드하지않음
//        webSettings.setLoadsImagesAutomatically(true); // 웹뷰가 앱에 등록되어 있는 이미지 리소스를 자동으로 로드하도록 설정
//        webSettings.setUseWideViewPort(true); // wide viewport를 사용하도록 설정
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 웹뷰가 캐시를 사용하지 않도록 설정
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        //webview.setWebViewClient(new myWebClient());

        //webview.setWebViewClient( new WebViewClientClass() );
        //webview.setWebViewClient(new webViewClient());
        //webview.setWebChromeClient(new WebBrowserClient());

        //webview.addJavascriptInterface(new AndroidBridge(), "sample");

        webview.setWebChromeClient(new myWebChromeClient());
        webview.addJavascriptInterface(new JavaScriptMethods(), "sample");

        // assets 폴더에 있는 메인 페이지 로딩
        webview.loadUrl("http://ec2-52-78-121-110.ap-northeast-2.compute.amazonaws.com:8080/first/goodjob/login/loginView.do");

        //webview.loadUrl("http://219.254.86.194/first/goodjob/login/loginView.do");
        //GCMIntentService  gcm = new GCMIntentService();

//        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
//        Notification.Builder builder = new Notification.Builder(this.getApplicationContext())
//                .setContentIntent(pendingIntent)
//                .setSmallIcon(R.drawable.ic_launcher)
//                .setContentTitle("DSM 알리미")
//                .setContentText("test입니다")
//                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
//                .setTicker("DSM 알리미 - 소식 왔어요!")
//                .setAutoCancel(true);
//        Notification notification = builder.build();
//        nm.notify(Calendar.getInstance().get(Calendar.MILLISECOND), notification);

        //gcm.onMessage(getApplicationContext(), new Intent(this, MainActivity.class) );

//        final EditText urlInput = (EditText) findViewById(R.id.urlInput);
//
//        // 버튼 이벤트 처리
//        loadButton = (Button) findViewById(R.id.loadBtn);
//        loadButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // 입력한 URL의 페이지 로딩
//                webview.loadUrl(urlInput.getText().toString());
//            }
//        });

    }

    /**
     * 자바스크립트 함수를 호출하기 위한 클래스 정의
     */
    public class AndroidBridge  {

        AndroidBridge() {

        }

        @JavascriptInterface
        public void clickOnFace() {
            mHandler.post(new Runnable() {
                public void run() {
                    // 버튼의 텍스트 변경
                    loadButton.setText("클릭후열기");
                    // 자바스크립트 함수 호출
                    webview.loadUrl("javascript:changeFace()");
                }
            });

        }
    }

    final class WebBrowserClient extends WebChromeClient {
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.d(TAG, message);
            result.confirm();

            return true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;

        }
    }

    public class myWebChromeClient extends WebChromeClient
    {
        public void openFileChooser( ValueCallback<Uri> uploadMsg) {
            Log.d("MainActivity", "3.0 <");
            openFileChooser(uploadMsg, "");
        }
        // For Android 3.0+
        public void openFileChooser( ValueCallback<Uri> uploadMsg, String acceptType) {
            Log.d("MainActivity", "3.0+");
            filePathCallbackNormal = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_NORMAL_REQ_CODE);
        }
        // For Android 4.1+
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            Log.d("MainActivity", "4.1+");
            openFileChooser(uploadMsg, acceptType);
        }

        // For Android 5.0+
        public boolean onShowFileChooser(
                WebView webView, ValueCallback<Uri[]> filePathCallback,
                WebChromeClient.FileChooserParams fileChooserParams) {
            Log.d("MainActivity", "5.0+");
            if (filePathCallbackLollipop != null) {
                filePathCallbackLollipop.onReceiveValue(null);
                filePathCallbackLollipop = null;
            }
            filePathCallbackLollipop = filePathCallback;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_LOLLIPOP_REQ_CODE);

            return true;
        }

    }


    final class JavaScriptMethods {

        JavaScriptMethods() {
        }

        public void clickOnFace() {
            mHandler.post(new Runnable() {
                public void run() {
                    //loadBtn.setText("클릭후열기");
                    //webView.loadUrl("javascript:changeFace()");
                }
            });

        }
    }


    /**
     * More info this method can be found at
     * http://developer.android.com/training/camera/photobasics.html
     *
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }


}
