/*
 * Copyright (C) 2012 Paul Burke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ipaulpro.afilechooserexample;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.ipaulpro.afilechooser.FileChooserActivity;
import com.ipaulpro.afilechooser.utils.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * @author paulburke (ipaulpro)
 */
@SuppressWarnings ({"CollectionDeclaredAsConcreteClass", "HardcodedFileSeparator"})
@org.androidannotations.annotations.EActivity (R.layout.example)
public class FileChooserExampleActivity extends Activity {
   private static final String TAG = FileChooserExampleActivity.class.getName ();
   /**
    * <p>onActivityResult request code</p>
    */
    private static final int REQUEST_CODE = 6384;
    @NotNull private static final ArrayList<String> PDF_Files;
    private static final String Pdf_Type = "application/pdf";
   /**
    * <p>File types for my calculator app. Replace it with whatever you want to test.</p>
    *
    * author Martin Krischik" <krischik@users.sourceforge.net>
    */
    @NotNull private static final ArrayList<String> Calculator_Files;
   /**
    * <p>Directory why the unit tests of my calculator app stores there files. The
    * real app uses {@link android.content.Context.getExternalFilesDir(java.lang.String)} </p>
    *
    * author Martin Krischik" <krischik@users.sourceforge.net>
    */
    @NotNull  private static final String Calculator_Dir;

    static
    {
        PDF_Files = new java.util.ArrayList<String> ();
        PDF_Files.add (".pdf");
        Calculator_Files = new java.util.ArrayList<String> ();
        Calculator_Files.add (".af");
        Calculator_Files.add (".df");
        Calculator_Files.add (".pf");
        Calculator_Dir = FileChooserActivity.EXTERNAL_BASE_PATH + "/Android/FX-602P";
    }

   @org.androidannotations.annotations.res.StringRes (R.string.chooser_title)
    String chooser_title;

    @org.androidannotations.annotations.Click (R.id.All_Files)
    void allFiles () {
        android.util.Log.d (TAG, "+ All_Files");

        // Use the GET_CONTENT intent from the utility class
        final Intent target = FileUtils.createGetContentIntent();

        // Create the chooser Intent
        final Intent intent = Intent.createChooser(target, chooser_title);
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (@NotNull final ActivityNotFoundException e) {
           // The reason for the existence of aFileChooser
           android.util.Log.e (TAG, "LOG02230:", e);
        }

        android.util.Log.d (TAG, "+ All_Files");
   }
   @org.androidannotations.annotations.Click (R.id.All_PDF_Files)
   void allPdfFiles () {
      android.util.Log.d (TAG, "+ allPdfFiles");

      com.ipaulpro.afilechooser.utils.FileUtils.startChooser (
        /* callingActivity         => */ this,
        /*  title                  => */ chooser_title,
        /* requestCode             => */ REQUEST_CODE,
        /* mimeTye                 => */ Pdf_Type,
        /* filterIncludeExtensions => */ PDF_Files);

      android.util.Log.d (TAG, "+ allPdfFiles");
   } // allPdfFiles
    @org.androidannotations.annotations.Click (R.id.Local_PDF_Files)
    void localPdfFiles () {
       android.util.Log.d (TAG, "+ localPdfFiles");

       FileChooserActivity.startActivity (
        /* callingActivity         => */ this,
        /* requestCode             => */ REQUEST_CODE,
        /* filterIncludeExtensions => */ PDF_Files);

       android.util.Log.d (TAG, "- localPdfFiles");
   } // localPdfFiles

   /**
    * <p>select calculator files. Not so useful for most users — but it shows you how you can
    * select specific files.</p>
    *
    * author Martin Krischik" <krischik@users.sourceforge.net>
    */
   @org.androidannotations.annotations.Click (R.id.All_Calculator_Files)
   void allCalculatorFiles() {
      android.util.Log.d (TAG, "+ allCalculatorFiles");

      com.ipaulpro.afilechooser.utils.FileUtils.startChooser (
        /* callingActivity         => */ this,
        /* title                   => */ chooser_title,
        /* requestCode             => */ REQUEST_CODE,
        /* mimeTye                 => */ FileUtils.MIME_TYPE_APP,
        /* baseDirectory           => */ Calculator_Dir,
        /* filterIncludeExtensions => */ Calculator_Files);

      android.util.Log.d (TAG, "- allCalculatorFiles");
   } // allCalculatorFiles

    @org.androidannotations.annotations.Click (R.id.Local_Calculator_Files)
    void localCalculatorFiles () {
       android.util.Log.d (TAG, "+ localCalculatorFiles");

       FileChooserActivity.startActivity (
        /* callingActivity         => */ this,
        /* requestCode             => */ REQUEST_CODE,
        /* baseDirectory           => */ Calculator_Dir,
        /* filterIncludeExtensions => */ Calculator_Files);

       android.util.Log.d (TAG, "- localCalculatorFiles");
   } // localCalculatorFiles

    @Override
    protected void onActivityResult(
       final int requestCode,
       final int resultCode,
       @Nullable final Intent data) {
       android.util.Log.d (TAG, "+ onActivityResult");
       android.util.Log.v (TAG, "> requestCode  = " + requestCode);
       android.util.Log.v (TAG, "> resultCode   = " + resultCode);
       android.util.Log.v (TAG, "> data         = " + data);

        switch (requestCode) {
            case REQUEST_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the URI of the selected file
                        final Uri uri = data.getData();
                        Log.i(TAG, "Uri = " + uri.toString());
                        try {
                            // Get the file path from the URI
                            final String path = FileUtils.getPath(this, uri);

                           final android.widget.Toast toast;
                           if (path != null) {
                              toast = android.widget.Toast.makeText (
                                 FileChooserExampleActivity.this,
                                 "File Selected: " + path, android.widget.Toast.LENGTH_LONG);
                           }
                           else {
                              toast = android.widget.Toast.makeText (
                                 FileChooserExampleActivity.this,
                                 "Uri Selected: " + uri, android.widget.Toast.LENGTH_LONG);
                           }
                           toast.show ();
                        } catch (@NotNull final Exception e) {
                            Log.e(TAG, "File select error", e);
                        }

                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
       android.util.Log.d (TAG, "- onActivityResult");
    }

   @Override public String toString ()
   {
      return TAG +
         "{super=" +
         super.toString () +
         ", chooser_title=“" +
         chooser_title +
         '”' +
         '}';
   }
}
