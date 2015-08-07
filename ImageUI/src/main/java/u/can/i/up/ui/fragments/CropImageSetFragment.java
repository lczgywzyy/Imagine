package u.can.i.up.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import u.can.i.up.ui.R;


public class CropImageSetFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_imageset_cropimage, container, false);

		RadioButton test = (RadioButton)view.findViewById(R.id.crop_free);
		test.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Toast.makeText(getActivity(), "自由切割", Toast.LENGTH_SHORT).show();
			}
		});

		return view;
	}	
}
