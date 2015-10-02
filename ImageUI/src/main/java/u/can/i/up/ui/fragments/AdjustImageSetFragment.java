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


public class AdjustImageSetFragment extends Fragment implements SeekBar.OnSeekBarChangeListener{
	private CropImageView cropImageView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		cropImageView = (CropImageView) getActivity().findViewById(R.id.CropImageView);
		View view = inflater.inflate(R.layout.fragment_imageset_adjust, container, false);
		SeekBar BrightnessseekBar = (SeekBar) view.findViewById(R.id.seekbar_brightness);
		SeekBar SaturationseekBar = (SeekBar) view.findViewById(R.id.seekbar_saturation);
		SeekBar ContrastseekBar = (SeekBar) view.findViewById(R.id.seekbar_contrast);

		SaturationseekBar
				.setOnSeekBarChangeListener(this);
					//);

		BrightnessseekBar
				.setOnSeekBarChangeListener(this);

		ContrastseekBar
				.setOnSeekBarChangeListener(this);

		return view;
	}




	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		switch(seekBar.getId()){
			case R.id.seekbar_saturation:
				cropImageView.saturationChange(progress);
				break;
			case R.id.seekbar_brightness:
				cropImageView.brightnessChange(progress);
				break;
			case R.id.seekbar_contrast:
				cropImageView.contrastChange(progress);
				break;
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}
}