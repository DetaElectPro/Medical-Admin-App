package com.hostpital.hospitaladmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;


import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.hostpital.hospitaladmin.R;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;

public class ShowPDFActivity extends AppCompatActivity {
    String stringCV;
    WebView webView;
    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pdf);

        pdfView = findViewById(R.id.pdfView);

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            stringCV = (mBundle.getString("show_cv"));
            Log.w("CV Url", stringCV);
        }

//        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(stringCV));
//        startActivity(browserIntent);
//        webView = (WebView) findViewById(R.id.webView);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl(stringCV);

        FileLoader.with(this)
                .load(stringCV, false) //2nd parameter is optioal, pass true to force load from network
                .fromDirectory("test4", FileLoader.DIR_INTERNAL)
                .asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        File loadedFile = response.getBody();
                        // do something with the file
                        pdfView.fromFile(loadedFile)
                                .enableSwipe(true) // allows to block changing pages using swipe
                                .swipeHorizontal(true)
                                .enableDoubletap(true)
                                .defaultPage(0)
                                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                                .password(null)
                                .scrollHandle(null)
                                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                                // spacing between pages in dp. To define spacing color, set view background
                                .spacing(0)
                                .pageFitPolicy(FitPolicy.WIDTH)
                                .load();
                    }

                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {
                    }
                });

    }
}
