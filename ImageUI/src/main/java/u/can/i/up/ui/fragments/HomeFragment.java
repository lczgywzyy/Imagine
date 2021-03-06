package u.can.i.up.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import u.can.i.up.ui.R;
import u.can.i.up.ui.activities.CutoutActivity;
import u.can.i.up.ui.activities.CutoutSetActivity;
import u.can.i.up.ui.activities.ImageCollocateActivity;
import u.can.i.up.ui.activities.ImageSetActivity;
import u.can.i.up.ui.activities.LibiraryActivity;
import u.can.i.up.ui.activities.MyAlbumActivity;
import u.can.i.up.ui.activities.MyAlbumPTRActivity;
import u.can.i.up.ui.activities.PearlBuildActivity;


/**
 * @author dongfeng
 * @data 2015.06.13
 * @sumary 主界面：用户进入的第一个界面
 */

public class HomeFragment extends Fragment implements View.OnClickListener{

    private Button fast_start;

    private Button libirary;

    private Button material_build;

    private Button pearl_build;

    private Button myalbum;

    public final static int REQUEST_FAST_CODE = 1;
    public final static int REQUEST_PEARL_CODE = 2;
    public final static int REQUEST_MATERIAL_CODE = 3;
    ArrayList<String> selectedPhotos = new ArrayList<>();

    private final String[] strMaterialActivitySelect={"抠图制作素材","快速搭配素材","自由组合素材"};


    public static HomeFragment newInstance(Bundle bundle)
    {
        HomeFragment homeFragment = new HomeFragment();

        if (bundle != null)
        {
            homeFragment.setArguments(bundle);
        }

        return homeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        // The last two arguments ensure LayoutParams are inflated properly
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        fast_start = (Button)view.findViewById(R.id.fast_start);
        libirary = (Button)view.findViewById(R.id.libirary);
        material_build= (Button)view.findViewById(R.id.material_build);
        pearl_build = (Button)view.findViewById(R.id.pearl_build);
        myalbum = (Button)view.findViewById(R.id.myalbum);

        fast_start.setOnClickListener(this);
        libirary.setOnClickListener(this);
        material_build.setOnClickListener(this);
        pearl_build.setOnClickListener(this);
        myalbum.setOnClickListener(this);
        //initApplicationPrompt(fast_start);
        return view;
    }

//    private void selectImage() {
//
//
////        startActivityForResult(getPickImageChooserIntent(), 200);
////        Dialog dialog = new Dialog(getActivity(), R.style.Theme_CustomDialog);
////        dialog.setContentView(R.layout.dialog_choose_picture);
////        Button camara = (Button)getView().findViewById(R.id.dialog_camera_btn);
////        Button gallery = (Button)getView().findViewById(R.id.dialog_picture_btn);
////        Button cancel = (Button)getView().findViewById(R.id.dialog_cancel_btn);
////
////        camara.setOnClickListener(this);
////        gallery.setOnClickListener(this);
////        cancel.setOnClickListener(this);
////        ivImage = (ImageView)getView().findViewById(R.id.ivImage);
//
//
//        final CharSequence[] items = { getString(R.string.dialog_choose_camera), getString(R.string.dialog_choose_picture),
//                getString(R.string.common_dialog_cancel_btn_text) };
//
////        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.dialog));
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle(getString(R.string.dialog_setting_head_title));
//
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                if (items[item].equals(getString(R.string.dialog_choose_camera))) {
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(intent, REQUEST_CAMERA);
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



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        List<String> photos = null;
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_FAST_CODE) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();
            if (photos != null) {
                selectedPhotos.addAll(photos);
            }
            Uri imageUri = Uri.fromFile(new File(selectedPhotos.get(0)));
//            String path = selectedPhotos.get(0);
            Intent newdata = new Intent(getActivity(), ImageSetActivity.class);
            newdata.putExtra("photoUri", imageUri);
            startActivity(newdata);
        }
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_PEARL_CODE) {
                if (data != null) {
                    photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                }
                selectedPhotos.clear();
                if (photos != null) {
                    selectedPhotos.addAll(photos);
                }

                String path = selectedPhotos.get(0);
                Intent newdata = new Intent(getActivity(), PearlBuildActivity.class);
                newdata.putExtra("photoUri", path);
                startActivity(newdata);
        }
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_MATERIAL_CODE) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();
            if (photos != null) {
                selectedPhotos.addAll(photos);
            }
            String path = selectedPhotos.get(0);
            Intent newdata = new Intent(getActivity(), CutoutActivity.class);
            newdata.putExtra("photo_path", path);
            startActivity(newdata);
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.fast_start:
                PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
                intent.setPhotoCount(1);
                intent.setShowCamera(true);
                intent.putExtra("typeP", PhotoPickerIntent.TYPE_PICKER_ALL);
                startActivityForResult(intent, REQUEST_FAST_CODE);
//                startActivityForResult(getPickImageChooserIntent(), 200);
                break;
            case R.id.libirary:
                startActivity(new Intent(getActivity(), LibiraryActivity.class));
                break;
            case R.id.material_build:
//                startActivity(new Intent(getActivity(), CutoutSetActivity.class));

                selectMaterialType();
               /* PhotoPickerIntent intentc = new PhotoPickerIntent(getActivity());
                intentc.setPhotoCount(1);
                intentc.setShowCamera(true);
                intentc.putExtra("typeP", PhotoPickerIntent.TYPE_PICKER_ALL);
                startActivityForResult(intentc, REQUEST_MATERIAL_CODE);*/
                break;
            case R.id.pearl_build:
                PhotoPickerIntent intentb = new PhotoPickerIntent(getActivity());
                intentb.setPhotoCount(1);
                intentb.setShowCamera(false);
                intentb.putExtra("typeP", PhotoPickerIntent.TYPE_PICKER_BG);
                startActivityForResult(intentb, REQUEST_PEARL_CODE);
               // startActivityForResult(intentsingle, REQUEST_PEARL_CODE);
//                startActivity(new Intent(getActivity(), PearlBuildActivity.class));
                break;
            case R.id.myalbum:
                startActivity(new Intent(getActivity(), MyAlbumActivity.class));
                break;
            default:
                break;
        }
    }

    private void selectMaterialType(){

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        builder.setTitle("选择素材制作方式").setSingleChoiceItems(strMaterialActivitySelect, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        buildMaterialMatting();
                        dialog.dismiss();
                        break;
                    case 1:
                        buildMaterialCollocate();
                        dialog.dismiss();
                        break;
                    case 2:
                        buildMaterialCombination();
                        dialog.dismiss();
                        break;
                    default:
                        dialog.dismiss();
                        break;
                }

            }
        });
        builder.show();

    }

    private void buildMaterialMatting(){
        PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
        intent.setPhotoCount(1);
        intent.setShowCamera(true);
        intent.putExtra("typeP", PhotoPickerIntent.TYPE_PICKER_ALL);
        startActivityForResult(intent, REQUEST_MATERIAL_CODE);
    }

    private void buildMaterialCollocate(){
        Intent intent=new Intent(getActivity(),ImageCollocateActivity.class);

        intent.setAction("materialBuild");

        startActivity(intent);

    }

    private void buildMaterialCombination(){

        Intent intent=new Intent(getActivity(), PearlBuildActivity.class);

        intent.setAction("materialBuild");

        startActivity(intent);
    }




    private void initApplicationPrompt( final View view){

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub

                int[] location=new int[3];
                view.getLocationOnScreen(location);
                view.getLocationInWindow(location);
                viewFloat(location, view);
            }
        });

    }

    private void viewFloat(int[] location,View view){



        WindowManager mWindowManager = (WindowManager) getActivity()
                .getSystemService(Context.WINDOW_SERVICE);


        Rect frame = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);

        int bitmapHeight=frame.height();
        int bitmapWidth=frame.width();

        int frameHeight=frame.top;


        Bitmap bgBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bgBitmap);

        Paint paint = new Paint();
        paint.setColor(Color.RED);

        paint.setAntiAlias(true);
        canvas.drawBitmap(bgBitmap, new Matrix(), paint);

        Rect rectFull=new Rect(0, 0,bitmapWidth, bitmapHeight);

        canvas.drawRect(rectFull, paint);
        Paint paintT = new Paint();
        paintT.setColor(Color.BLACK);

        paintT.setAntiAlias(true);

        canvas.drawRect(location[0], location[1]-frameHeight, view.getWidth(), view.getHeight(),paintT);



        WindowManager.LayoutParams params = new WindowManager.LayoutParams();


        params.width =bitmapWidth;
        params.height = bitmapHeight;


        params.alpha=0x40;

        params.gravity = Gravity.CENTER;

        ImageView img=new ImageView(getActivity());

        img.setAlpha(0x40);
        img.setImageBitmap(bgBitmap);

        mWindowManager.addView(img, params);

    }




    /**
     * Create a chooser intent to select the source to get image from.<br/>
     * The source can be camera's (ACTION_IMAGE_CAPTURE) or gallery's (ACTION_GET_CONTENT).<br/>
     * All possible sources are added to the intent chooser.
     */
    public Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getActivity().getPackageManager();

        // collect all camera intents
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        // the main intent is the last in the list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    /**
     * Get URI to image received from capture by camera.
     */
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getActivity().getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "pickImageResult.jpeg"));
        }
        return outputFileUri;
    }

    /**
     * Get the URI of the selected image from {@link #getPickImageChooserIntent()}.<br/>
     * Will return the correct URI for camera and gallery image.
     *
     * @param data the returned data of the activity result
     */
    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

}
