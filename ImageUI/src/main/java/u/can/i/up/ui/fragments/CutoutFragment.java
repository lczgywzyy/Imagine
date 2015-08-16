package u.can.i.up.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import u.can.i.up.ui.R;
import u.can.i.up.ui.activities.CutoutActivity;
import u.can.i.up.ui.application.IApplication;
import u.can.i.up.ui.beans.TMaterial;
import u.can.i.up.ui.utils.IBitmapCache;


/**
 * @author dongfeng
 * @data 2015.06.13
 * @sumary 素材制作界面：用户选择素材，进入抠图界面
 */
public class CutoutFragment extends Fragment {
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    ImageView ivImage;

    private GridView gridView;

    private ArrayList<TMaterial> tMaterials;

    public static CutoutFragment newInstance(Bundle bundle)
    {
        CutoutFragment cutoutFragment = new CutoutFragment();

        if (bundle != null)
        {
            cutoutFragment.setArguments(bundle);
        }

        return cutoutFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        // The last two arguments ensure LayoutParams are inflated properly
        View view = inflater.inflate(R.layout.fragmemt_cutout, container, false);
        Button testcutout = (Button)view.findViewById(R.id.testcutout);
        testcutout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(getPickImageChooserIntent(), 200);
//                startActivity(new Intent(view.getContext(), CutoutActivity.class));
            }
        });
        gridView=(GridView)view.findViewById(R.id.gridView);
        setGridView();
        return view;
    }


        @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri imageUri = getPickImageResultUri(data);
            Intent newdata = new Intent(getActivity(), CutoutActivity.class);
            newdata.putExtra("photoUri", imageUri);
            startActivity(newdata);
        }
    }

    private void selectImage() {

//        Dialog dialog = new Dialog(getActivity(), R.style.Theme_CustomDialog);
//        dialog.setContentView(R.layout.dialog_choose_picture);
//        Button camara = (Button)getView().findViewById(R.id.dialog_camera_btn);
//        Button gallery = (Button)getView().findViewById(R.id.dialog_picture_btn);
//        Button cancel = (Button)getView().findViewById(R.id.dialog_cancel_btn);
//
//        camara.setOnClickListener(this);
//        gallery.setOnClickListener(this);
//        cancel.setOnClickListener(this);
//        ivImage = (ImageView)getView().findViewById(R.id.ivImage);


        final CharSequence[] items = { getString(R.string.dialog_choose_camera), getString(R.string.dialog_choose_picture),
                getString(R.string.common_dialog_cancel_btn_text) };

//        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.dialog));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.dialog_setting_head_title));

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.dialog_choose_camera))) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals(getString( R.string.dialog_choose_picture))) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals(getString(R.string.common_dialog_cancel_btn_text))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == SELECT_FILE)
//                onSelectFromGalleryResult(data);
//            else if (requestCode == REQUEST_CAMERA)
//                onCaptureImageResult(data);
//        }
//    }

    private void setGridView(){
        tMaterials= ((IApplication)getActivity().getApplication()).arrayListTMaterial;
        gridView.setNumColumns(5);
        gridView.setBackgroundColor(Color.TRANSPARENT);
        gridView.setHorizontalSpacing(1);
        gridView.setVerticalSpacing(1);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setCacheColorHint(0);
        gridView.setPadding(5, 0, 5, 0);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setGravity(Gravity.CENTER);
        GridViewAdapters gridViewAdapter=new GridViewAdapters(getActivity(),tMaterials);
        gridView.setAdapter(gridViewAdapter);

    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        byte[] bytepicture = bytes.toByteArray();

        Intent newdata = new Intent(getActivity(), CutoutActivity.class);
        newdata.putExtra("picture", bytepicture);
        startActivity(newdata);

//        ivImage.setImageBitmap(thumbnail);

    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getActivity().getContentResolver().query(selectedImageUri, projection, null, null, null);
//                managedQuery(selectedImageUri, projection, null, null,
//                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);
//
//        ivImage.setImageBitmap(bm);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytepicture = baos.toByteArray();

        Intent newdata = new Intent(getActivity(), CutoutActivity.class);
        newdata.putExtra("picture", bytepicture);
        startActivity(newdata);


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
    class GridViewAdapters extends  BaseAdapter{



        private Context context;

        private List<TMaterial> tmaterialArrayList;


        public GridViewAdapters(Context context,List<TMaterial> pearlBeansArrayList){

            this.context=context;
            this.tmaterialArrayList = pearlBeansArrayList;
        }
        public int getCount() {
            return tmaterialArrayList.size();
        }
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_library_type, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.mTextView = (TextView) convertView.findViewById(android.R.id.text1);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String name = tmaterialArrayList.get(position).getTMaterialName();
            viewHolder.mTextView.setText(name);
            IBitmapCache.BitmapAsync bitmapAsync=new IBitmapCache.BitmapAsync(viewHolder.mTextView);

            bitmapAsync.execute(null, tmaterialArrayList.get(position).getTMaterialMd(),"bottom");
            return convertView;
        }
        public TMaterial getItem(int position) {
            return tmaterialArrayList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {
            private TextView mTextView;
        }
    }
}
