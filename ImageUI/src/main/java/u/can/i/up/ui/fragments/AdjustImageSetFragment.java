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
import android.widget.Toast;

import cropper.CropImageView;
import u.can.i.up.ui.R;
import u.can.i.up.ui.utils.BitmapCache;


public class AdjustImageSetFragment extends Fragment{
	private CropImageView cropImageView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		cropImageView = (CropImageView) getActivity().findViewById(R.id.CropImageView);
		View view = inflater.inflate(R.layout.fragment_imageset_adjust, container, false);
		final SeekBar BrightnessseekBar = (SeekBar) view.findViewById(R.id.seekbar_brightness);
		final SeekBar SaturationseekBar = (SeekBar) view.findViewById(R.id.seekbar_saturation);
		final SeekBar ContrastseekBar = (SeekBar) view.findViewById(R.id.seekbar_contrast);
//		ContrastseekBar.setMax(10);
//		ContrastseekBar.setProgress((5));
//		BrightnessseekBar.setMax(510);
//		BrightnessseekBar.setProgress((255));

		SaturationseekBar
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			//
			public void onProgressChanged(SeekBar arg0, int progress,
										  boolean fromUser) {
				if (cropImageView != null ) {

				}else
					Toast.makeText(getActivity(), "请先选择图片", Toast.LENGTH_SHORT).show();

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
						if (cropImageView != null ) {
							Bitmap new_bm = changeBitmapContrastBrightness(cropImageView.getImage(), (float)(ContrastseekBar.getProgress()-1), (float)(progress-255));
							cropImageView.setImageView(new_bm);
						}else
							Toast.makeText(getActivity(), "请先选择图片", Toast.LENGTH_SHORT).show();


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
						if (cropImageView != null ) {
							if (progress <= 5) {
								progress = 1 - progress / 5;
							} else {
								progress = 1 + (progress / 10) * 9;
							}
							Bitmap new_bm = changeBitmapContrastBrightness(cropImageView.getImage(), (float)(progress-1),(float)BrightnessseekBar.getProgress() - 255);
							cropImageView.setImageView(new_bm);
						}else
							Toast.makeText(getActivity(), "请先选择图片", Toast.LENGTH_SHORT).show();


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

	public static Bitmap changeBitmapContrastBrightness(Bitmap bmp, float contrast, float brightness)
	{
		ColorMatrix cm = new ColorMatrix(new float[]
				{
						contrast, 0, 0, 0, brightness,
						0, contrast, 0, 0, brightness,
						0, 0, contrast, 0, brightness,
						0, 0, 0, 1, 0
				});

		Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

		Canvas canvas = new Canvas(ret);

		Paint paint = new Paint();
		paint.setColorFilter(new ColorMatrixColorFilter(cm));
		canvas.drawBitmap(bmp, 0, 0, paint);

		return ret;
	}


}
