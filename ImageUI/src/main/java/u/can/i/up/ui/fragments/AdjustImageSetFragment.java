package u.can.i.up.ui.fragments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import cropper.CropImageView;
import u.can.i.up.ui.R;
import u.can.i.up.ui.utils.BitmapCache;


public class AdjustImageSetFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_imageset_adjust, container, false);
		SeekBar BrightnessseekBar = (SeekBar) view.findViewById(R.id.seekbar_brightness);
		SeekBar SaturationseekBar = (SeekBar) view.findViewById(R.id.seekbar_saturation);
		SeekBar ContrastseekBar = (SeekBar) view.findViewById(R.id.seekbar_contrast);
		CropImageView mcropimageview = BitmapCache.getCropImageView();


		SaturationseekBar
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			//
			public void onProgressChanged(SeekBar arg0, int progress,
										  boolean fromUser) {
				// ,
//				Bitmap bmp = Bitmap.createBitmap(imgWidth, imgHeight,
//						Bitmap.Config.ARGB_8888);
//				ColorMatrix cMatrix = new ColorMatrix();
//				//
//				cMatrix.setSaturation((float) (progress / 100.0));
//
//				Paint paint = new Paint();
//				paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
//
//				Canvas canvas = new Canvas(bmp);
//				// CanvasBitmap??dstBitmapsrcBitmap
//				canvas.drawBitmap(srcBitmap, 0, 0, paint);
//
//				dstimage.setImageBitmap(bmp);

			}

			public void onStartTrackingTouch(SeekBar bar) {
			}

			public void onStopTrackingTouch(SeekBar bar) {
			}
		});

		BrightnessseekBar
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					//
					public void onProgressChanged(SeekBar arg0, int progress,
												  boolean fromUser) {
//						Bitmap bmp = Bitmap.createBitmap(imgWidth, imgHeight,
//								Bitmap.Config.ARGB_8888);
//						int brightness = progress - 127;
//						ColorMatrix cMatrix = new ColorMatrix();
//						cMatrix.set(new float[] { 1, 0, 0, 0, brightness, 0, 1,
//								0, 0, brightness,//
//								0, 0, 1, 0, brightness, 0, 0, 0, 1, 0 });
//
//						Paint paint = new Paint();
//						paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
//
//						Canvas canvas = new Canvas(bmp);
//						// CanvasBitmap??dstBitmapsrcBitmap
//						canvas.drawBitmap(srcBitmap, 0, 0, paint);
//						dstimage.setImageBitmap(bmp);

					}

					public void onStartTrackingTouch(SeekBar bar) {
					}

					public void onStopTrackingTouch(SeekBar bar) {
					}
				});

		ContrastseekBar
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					//
					public void onProgressChanged(SeekBar arg0, int progress,
												  boolean fromUser) {
//						Bitmap bmp = Bitmap.createBitmap(imgWidth, imgHeight,
//								Bitmap.Config.ARGB_8888);
//						// int brightness = progress - 127;
//						float contrast = (float) ((progress + 64) / 128.0);
//						ColorMatrix cMatrix = new ColorMatrix();
//						cMatrix.set(new float[] { contrast, 0, 0, 0, 0, 0,
//								contrast, 0, 0, 0,//
//								0, 0, contrast, 0, 0, 0, 0, 0, 1, 0 });
//
//						Paint paint = new Paint();
//						paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
//
//						Canvas canvas = new Canvas(bmp);
//						// CanvasBitmap??dstBitmapsrcBitmap
//						canvas.drawBitmap(srcBitmap, 0, 0, paint);
//
//						dstimage.setImageBitmap(bmp);
					}

					public void onStartTrackingTouch(SeekBar arg0) {
						// TODO Auto-generated method stub

					}

					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}
				});

		return view;
	}	
}
