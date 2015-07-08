package u.can.i.up.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import u.can.i.up.ui.R;
import u.can.i.up.ui.activities.CutoutActivity;
import u.can.i.up.ui.activities.ImageSetActivity;
import u.can.i.up.ui.activities.LibiraryActivity;


/**
 * @author dongfeng
 * @data 2015.06.13
 * @sumary 素材制作界面：用户选择素材，进入抠图界面
 */
public class CutoutFragment extends Fragment {
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    ImageView ivImage;

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
                startActivity(new Intent(view.getContext(), CutoutActivity.class));
            }
        });



//        Button collocation_start = (Button)view.findViewById(R.id.collocation_start);
//        Button libirary = (Button)view.findViewById(R.id.libirary);
//        libirary.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(view.getContext(), LibiraryActivity.class));
//            }
//        });
//        collocation_start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Dialog dialog = new Dialog(getActivity(), R.style.Theme_CustomDialog);
////                //final Dialog dialog = new Dialog(this, R.style.Theme_CustomDialog);
////                //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////                dialog.setContentView(R.layout.dialog_choose_picture);
////
////                dialog.show();
//                selectImage();
//            }
//        });
        return view;
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
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
}
