package u.can.i.up.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import cropper.CropImageView;
import u.can.i.up.ui.R;


public class RotateImageSetFragment extends Fragment implements View.OnClickListener{
	private static final int ROTATE_NINETY_DEGREES = 90;
	private static final int ROTATE_NONINETY_DEGREES = 270;
	private CropImageView cropImageView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_imageset_rotate, container, false );
		cropImageView = (CropImageView) getActivity().findViewById(R.id.CropImageView);
		ImageButton rotate_left = (ImageButton) view.findViewById(R.id.rotate_left);
		ImageButton rotate_right = (ImageButton) view.findViewById(R.id.rotate_right);
		ImageButton flip_horizon = (ImageButton) view.findViewById(R.id.flip_horizon);
		ImageButton flip_vertical = (ImageButton) view.findViewById(R.id.flip_vertical);
		rotate_left.setOnClickListener(this);
		rotate_right.setOnClickListener(this);
		flip_horizon.setOnClickListener(this);
		flip_vertical.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.rotate_left: {
				if (cropImageView != null ) {
					cropImageView.rotateImage(ROTATE_NONINETY_DEGREES);
				}else
					Toast.makeText(getActivity(), "请先选择图片", Toast.LENGTH_SHORT).show();
				break;
			}

			case R.id.rotate_right:
			{
				if (cropImageView != null ) {
					cropImageView.rotateImage(ROTATE_NINETY_DEGREES);
				}else
					Toast.makeText(getActivity(), "请先选择图片", Toast.LENGTH_SHORT).show();

				break;
			}

			case R.id.flip_horizon:
			{
				if (cropImageView != null ) {
					cropImageView.flipImage(true);
				}else
					Toast.makeText(getActivity(), "请先选择图片", Toast.LENGTH_SHORT).show();
				break;
			}

			case R.id.flip_vertical:
			{
				if (cropImageView != null ) {
					cropImageView.flipImage(false);
				}else
					Toast.makeText(getActivity(), "请先选择图片", Toast.LENGTH_SHORT).show();
				break;
			}

			default:break;
		}

	}

}
