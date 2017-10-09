package com.example.joseph.weekend2app;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class PDFActivity extends AppCompatActivity {

    public static final String FILENAME = "sample.pdf";
    private static final String TAG = "PDFActivityTag";
    private ParcelFileDescriptor mFileDescriptor;
    private PdfRenderer mPdfRenderer;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        iv = (ImageView) findViewById(R.id.ivFile);
        setTitle(FILENAME);

        try{
            // In this sample, we read a PDF from the assets directory.
            File file = new File(getCacheDir(), FILENAME);
//            if (!file.exists()) {
//                // Since PdfRenderer cannot handle the compressed asset file directly, we copy it into
//                // the cache directory.
//                InputStream asset = getAssets().open(FILENAME);
//                FileOutputStream output = new FileOutputStream(file);
//                final byte[] buffer = new byte[1024];
//                int size;
//                while ((size = asset.read(buffer)) != -1) {
//                    output.write(buffer, 0, size);
//                }
//                asset.close();
//                output.close();
//            }
            mFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
            // This is the PdfRenderer we use to render the PDF.
            if (mFileDescriptor != null) {
                mPdfRenderer = new PdfRenderer(mFileDescriptor);
            }
        }catch (FileNotFoundException e){
            Log.d(TAG, "file not found exception");
        } catch (Exception e){
            Log.d(TAG, e.getMessage());
        }




//        // create a new renderer
//        PdfRenderer renderer = new PdfRenderer(new ParcelFileDescriptor());

        // let us just render all pages
        final int pageCount = mPdfRenderer.getPageCount();
//        for (int i = 0; i < pageCount; i++) {
//            PdfRenderer.Page page = mPdfRenderer.openPage(i);
            PdfRenderer.Page page = mPdfRenderer.openPage(0);

            Bitmap bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(),
                    Bitmap.Config.ARGB_8888);

            // say we render for showing on the screen
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

            // do stuff with the bitmap
            iv.setImageBitmap(bitmap);

            // close the page
//            page.close();
//        }

        // close the renderer
//        mPdfRenderer.close();

    }
}
