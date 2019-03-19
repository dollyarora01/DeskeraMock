package com.deskera.mock.views.activities;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deskera.mock.DeskeraMockApplication;
import com.deskera.mock.R;
import com.deskera.mock.utils.ImagePicker;
import com.deskera.mock.utils.UtilsHelper;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {

    //region Variables
    private TextView tvTitle;
    private static final String TEMP_IMAGE_NAME = "tempImage";
    public static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 23;
    private static final int CAPTURE_IMAGE_ID = 100;
    Bitmap profileBitmap;
    Uri imageUri;
    CollapsingToolbarLayout ctlProfile;
    ProfileToolbarState profileToolbarState;

    enum ProfileToolbarState {
        Collapsed,
        Expanded;
    }

    //endregion

    //region Events
    @Override
    public int getContentViewId() {
        return R.layout.activity_profile;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_profile;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        if (ctlProfile == null)
            ctlProfile = appBarLayout.findViewById(R.id.ctlProfile);

        if (tvTitle == null) {
            tvTitle = ctlProfile.findViewById(R.id.tvTitle);
            tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPickImage(v);
                }
            });
        }
        if (Math.abs(offset) == appBarLayout.getTotalScrollRange()) {
            profileToolbarState = ProfileToolbarState.Collapsed;
            // Collapsed
            if (profileBitmap == null) {
                tvTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_account_circle_outline_white_24dp, 0);
            } else {
                setImage();
            }
        } else if (offset == 0) {
            profileToolbarState = ProfileToolbarState.Expanded;
            if (profileBitmap == null) {
                tvTitle.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_account_circle_outline_white_48dp, 0, 0);
            } else {
                setImage();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        imageUri = getPickImageResultUri(data);
        switch (requestCode) {
            case CAPTURE_IMAGE_ID:
                break;
            case 200:
                if (resultCode == (RESULT_CANCELED))
                    break;
                if (imageUri != null) {
                    if (data != null) {
                        //This is for Gallery images
                        if (UtilsHelper.getPath(DeskeraMockApplication.getContext(), imageUri) != null) {
                            profileBitmap = ImagePicker.getImageFromResult(DeskeraMockApplication.getContext(), resultCode, data);
                        }
                    } else {
                        //This is for camera images
                        profileBitmap = ImagePicker.getImageFromResult(DeskeraMockApplication.getContext(), resultCode, data);
                    }
                    if (profileBitmap != null)
                        setImage();

                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    //endregion

    //region Functions
    private void setImage() {
        RoundedBitmapDrawable expandedDrawable = RoundedBitmapDrawableFactory.create(getResources(), profileBitmap);
        expandedDrawable.setCircular(true);
        ImageView imageView = new ImageView(DeskeraMockApplication.getContext());
        switch (profileToolbarState) {
            case Collapsed: {
                Picasso.with(DeskeraMockApplication.getContext())
                        .load(imageUri).resize(200, 200)
                        .transform(new CropCircleTransformation()).into(imageView);
                tvTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, imageView.getDrawable(), null);
            }
            break;
            case Expanded:
                Picasso.with(DeskeraMockApplication.getContext())
                        .load(imageUri).resize(400, 400)
                        .transform(new CropCircleTransformation()).into(imageView);

                tvTitle.setCompoundDrawablesWithIntrinsicBounds(null, imageView.getDrawable(), null, null);
                break;
        }
    }

    public void onPickImage(View view) {
        List<String> listPermissionsNeeded = UtilsHelper.checkAndRequestCameraStoragePermissions();
        if (listPermissionsNeeded.size() > 0)
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        else
            startActivityForResult(getPickImageChooserIntent(), 200);
    }

    public Intent getPickImageChooserIntent() {

        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

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

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals
                    ("com.deskera.mock.views.activities.ProfileActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = DeskeraMockApplication.getContext().getExternalCacheDir();
        if (getImage != null) {

            outputFileUri = Uri.fromFile(getTempFile(DeskeraMockApplication.getContext()));
        }
        return outputFileUri;
    }

    private static File getTempFile(Context context) {
        File imageFile = new File(context.getExternalCacheDir(), TEMP_IMAGE_NAME);
        try {
            imageFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null && data.getData() != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }
//endregion

}
