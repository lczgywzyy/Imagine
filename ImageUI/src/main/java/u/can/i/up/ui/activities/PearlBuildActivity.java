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
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
public class PearlBuildActivity extends Fragment {


    public static PearlBuildActivity newInstance(Bundle bundle)
    {
        PearlBuildActivity pearlBuildActivity = new PearlBuildActivity();

        if (bundle != null)
        {
            pearlBuildActivity.setArguments(bundle);
        }

        return pearlBuildActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pearlbuild, container, false);

        final EditText ballnum = (EditText) view.findViewById(R.id.ballnumb);
        Button preview = (Button) view.findViewById(R.id.pearlbuildpreview);
        final ImageViewImpl_PearlBuild pearlBuild = (ImageViewImpl_PearlBuild) view.findViewById(R.id.ImageViewImpl_PearlBuild);

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int inputNum = Integer.parseInt(ballnum.getText().toString());
//                String a = ballnum.getText().toString();
//                int inputNum = Integer.parseInt(a);

                ballnum.setText(null);
                if (inputNum >= 10 && inputNum <= 120) {
                    ballnum.setHintTextColor(Color.GRAY);
                    ballnum.setHint("请输入素珠个数(10-120)：");
                    pearlBuild.updateImage(inputNum);
                } else {
                    int withs = ballnum.getWidth();
                    ballnum.setHintTextColor(Color.RED);
                    ballnum.setHint("请输入素珠个数(10-120)：");
                    ballnum.setWidth(withs);
                }
                pearlBuild.setBmpMotion(BitmapFactory.decodeResource(getResources(), R.drawable.emoji_1));
            }
        });



        return view;
    }

}
