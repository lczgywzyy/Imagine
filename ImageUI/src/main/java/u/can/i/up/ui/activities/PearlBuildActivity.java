package u.can.i.up.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import u.can.i.up.ui.R;
import u.can.i.up.ui.utils.ImageViewImpl_PearlBuild;

/**
 * @author dongfeng
 * @data 2015.07.15
 * @sumary 串珠组装界面：选择素珠，颗数，形状，搭配出一串珠子
 */
public class PearlBuildActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pearlbuild);
        //init view
        final EditText ballnum = (EditText)findViewById(R.id.ballnumb);
        Button preview = (Button)findViewById(R.id.pearlbuildpreview);
        ImageButton add_pearl = (ImageButton)findViewById(R.id.pearlbuild_add);
        final ImageViewImpl_PearlBuild pearlBuild = (ImageViewImpl_PearlBuild)findViewById(R.id.ImageViewImpl_PearlBuild);

        add_pearl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int inputNum = Integer.parseInt(ballnum.getText().toString());
//                String a = ballnum.getText().toString();
//                int inputNum = Integer.parseInt(a);

                ballnum.setText(null);
                if (inputNum >= 10 && inputNum <= 120) {
                    ballnum.setHintTextColor(Color.BLACK);
                    ballnum.setHint(R.string.suzhu_num_hint);
                    pearlBuild.updateImage(inputNum);
                } else {
                    int withs = ballnum.getWidth();
                    ballnum.setHintTextColor(Color.RED);
                    ballnum.setHint(R.string.suzhu_num_hint);
                    ballnum.setWidth(withs);
                }
                pearlBuild.setBmpMotion(BitmapFactory.decodeResource(getResources(), R.drawable.emoji_1));
            }
        });



    }

////        final View controlsView = findViewById(R.id.fullscreen_content_controls);
////        final View contentView = findViewById(R.id.ivImage);
//        // Initialize components of the app
//        final CropImageView cropImageView = (CropImageView) findViewById(R.id.CropImageView);
//        //Sets the rotate button
//        final ImageButton rotateButton = (ImageButton) findViewById(R.id.Button_rotate);
////        ImageView logoview = (ImageView) findViewById(R.id.ivImage1);
//        ImageButton loadimage = (ImageButton)findViewById(R.id.match_1_close_btn);
//        ImageButton crop = (ImageButton)findViewById(R.id.match_1_continue);
//
//        rotateButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                cropImageView.rotateImage(ROTATE_NINETY_DEGREES);
//            }
//        });
//
//        loadimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivityForResult(getPickImageChooserIntent(), 200);
////                selectImage();
//            }
//        });




//        final byte[] byteArray = getIntent().getExtras().getByteArray("picture");
//        final Bitmap image_bmp = BitmapFactory.decodeByteArray(byteArray, 0,
//                byteArray.length);
//        logoview.setImageBitmap(image_bmp);



//    private void selectImage() {
//
//        final CharSequence[] items = { getString(R.string.dialog_choose_camera), getString(R.string.dialog_choose_picture),
//                getString(R.string.common_dialog_cancel_btn_text) };
//
////        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.dialog));
//        AlertDialog.Builder builder = new AlertDialog.Builder(getApplication());
//        builder.setTitle(getString(R.string.dialog_setting_head_title));
//
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                if (items[item].equals(getString(R.string.dialog_choose_camera))) {
//                    /*
//                    * @data 2015.07.12
//                    * @sumary 修正拍照后图片不清晰情况
//                    * */
////                    File saveDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/DuoBaoChuan/");
////                    saveDir.mkdir();
////                    String format = String.format("%%0%dd", 3);
////                    File saveFile;
////                    do {
////                        int count = getCameraFileCount();
////                        String filename = "DuoBaoChuan_" + String.format(format, count) +"_000.jpeg";
////                        saveFile = new File(saveDir, filename);
////                        incrementCameraFileCount();
////                    } while (saveFile.exists());
////
////                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
////                    mOriginalPhotoPath = saveFile.getAbsolutePath();
////                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(saveFile));
////                    startActivityForResult(intent, REQUEST_CAMERA);
//
//
//
//                } else if (items[item].equals(getString( R.string.dialog_choose_picture))) {
//                    Intent intent = new Intent(
//                            Intent.ACTION_PICK,
//                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    intent.setType("image/*");
//                    startActivityForResult(
//                            Intent.createChooser(intent, "Select File"),
//                            SELECT_FILE);
//                } else if (items[item].equals(getString(R.string.common_dialog_cancel_btn_text))) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == Activity.RESULT_OK) {
//            Uri imageUri = getPickImageResultUri(data);
//            ((CropImageView) findViewById(R.id.CropImageView)).setImageUri(imageUri);
//        }
//    }
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == SELECT_FILE)
//                onSelectFromGalleryResult(data);
//            else if (requestCode == REQUEST_CAMERA)
//                onCaptureImageResult(data);
//        }
//    }

//    /**
//     * Create a chooser intent to select the source to get image from.<br/>
//     * The source can be camera's (ACTION_IMAGE_CAPTURE) or gallery's (ACTION_GET_CONTENT).<br/>
//     * All possible sources are added to the intent chooser.
//     */
//    public Intent getPickImageChooserIntent() {
//
//        // Determine Uri of camera image to save.
//        Uri outputFileUri = getCaptureImageOutputUri();
//
//        List<Intent> allIntents = new ArrayList<>();
//        PackageManager packageManager = getPackageManager();
//
//        // collect all camera intents
//        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
//        for (ResolveInfo res : listCam) {
//            Intent intent = new Intent(captureIntent);
//            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
//            intent.setPackage(res.activityInfo.packageName);
//            if (outputFileUri != null) {
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
//            }
//            allIntents.add(intent);
//        }
//
//        // collect all gallery intents
//        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        galleryIntent.setType("image/*");
//        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
//        for (ResolveInfo res : listGallery) {
//            Intent intent = new Intent(galleryIntent);
//            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
//            intent.setPackage(res.activityInfo.packageName);
//            allIntents.add(intent);
//        }
//
//        // the main intent is the last in the list (fucking android) so pickup the useless one
//        Intent mainIntent = allIntents.get(allIntents.size() - 1);
//        for (Intent intent : allIntents) {
//            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
//                mainIntent = intent;
//                break;
//            }
//        }
//        allIntents.remove(mainIntent);
//
//        // Create a chooser from the main intent
//        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
//
//        // Add all other intents
//        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));
//
//        return chooserIntent;
//    }
//
//    /**
//     * Get URI to image received from capture by camera.
//     */
//    private Uri getCaptureImageOutputUri() {
//        Uri outputFileUri = null;
//        File getImage = getExternalCacheDir();
//        if (getImage != null) {
//            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "pickImageResult.jpeg"));
//        }
//        return outputFileUri;
//    }
//
//    /**
//     * Get the URI of the selected image from {@link #getPickImageChooserIntent()}.<br/>
//     * Will return the correct URI for camera and gallery image.
//     *
//     * @param data the returned data of the activity result
//     */
//    public Uri getPickImageResultUri(Intent data) {
//        boolean isCamera = true;
//        if (data != null) {
//            String action = data.getAction();
//            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
//        }
//        return isCamera ? getCaptureImageOutputUri() : data.getData();
//    }

//    private class Saveimage extends AsyncTask<String, Void, String> {
//        @Override
//        protected void onPreExecute() {
////            super.onPreExecute();
////            pDialog = new ProgressDialog(ImageSetActivity.this);
////            pDialog.setMessage("图片加载中...");
////            pDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String file) {
//            try {
//                    //Write file
//
//                    FileOutputStream stream = getApplication().openFileOutput(file, Context.MODE_PRIVATE);
//                    croppedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                    //Cleanup
//                    stream.close();
//                    croppedImage.recycle();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//
//
//        }
//    }
//    private void onCaptureImageResult(Intent data) {
////        Uri photoUri = data.getData();
//////        mImageView.setImageBitmap(null);
////        mOriginalPhotoPath = MediaUtils.getPath(getActivity(), photoUri);
////        loadPhoto(mOriginalPhotoPath);
//////        mImageView.setImageBitmap(mBitmap);
//
//        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//
//        File destination = new File(Environment.getExternalStorageDirectory(),
//                System.currentTimeMillis() + ".jpg");
//
//        FileOutputStream fo;
//        try {
//            destination.createNewFile();
//            fo = new FileOutputStream(destination);
//            fo.write(bytes.toByteArray());
//            fo.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        byte[] bytepicture = bytes.toByteArray();
//
//        Intent newdata = new Intent(getApplication(), ImageSetActivity.class);
//        newdata.putExtra("picture", bytepicture);
//        startActivity(newdata);
//
////        ivImage.setImageBitmap(thumbnail);
//
//    }
//
//    @SuppressWarnings("deprecation")
//    private void onSelectFromGalleryResult(Intent data) {
//        Uri selectedImageUri = data.getData();
//        String[] projection = { MediaStore.MediaColumns.DATA };
//        Cursor cursor = getApplication().getContentResolver().query(selectedImageUri, projection, null, null, null);
////                managedQuery(selectedImageUri, projection, null, null,
////                null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//        cursor.moveToFirst();
//
//        String selectedImagePath = cursor.getString(column_index);
//
//        Bitmap bm;
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(selectedImagePath, options);
//        final int REQUIRED_SIZE = 200;
//        int scale = 1;
//        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
//                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
//            scale *= 2;
//        options.inSampleSize = scale;
//        options.inJustDecodeBounds = false;
//        bm = BitmapFactory.decodeFile(selectedImagePath, options);
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] bytepicture = baos.toByteArray();
//
//        Intent newdata = new Intent(getApplication(), ImageSetActivity.class);
//        newdata.putExtra("picture", bytepicture);
//        startActivity(newdata);
//    }
}
