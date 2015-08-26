package u.can.i.up.ui.viewholders;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.views.list.ViewHolderBase;
import u.can.i.up.ui.R;
import u.can.i.up.ui.data.ImageListItem;
import u.can.i.up.ui.data.ImageSize;

public class ImageListItemMiddleImageViewHolder extends ViewHolderBase<ImageListItem> {

    private CubeImageView mImageView;
    private ImageLoader mImageLoader;

    public ImageListItemMiddleImageViewHolder(ImageLoader imageLoader) {
        mImageLoader = imageLoader;
    }

    @Override
    public View createView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.load_middle_image_list_item, null);
        mImageView = (CubeImageView) view.findViewById(R.id.load_middle_image_list_image_view);

        LinearLayout.LayoutParams lyp = new LinearLayout.LayoutParams(ImageSize.sGirdImageSize, ImageSize.sGirdImageSize);
        mImageView.setLayoutParams(lyp);
        return view;
    }

    @Override
    public void showData(int position, ImageListItem itemData) {
        mImageView.loadImage(mImageLoader, itemData.picUrl, ImageSize.sGridImageReuseInfo);
    }
}