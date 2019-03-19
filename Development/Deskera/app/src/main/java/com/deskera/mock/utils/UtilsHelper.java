package com.deskera.mock.utils;

import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.deskera.mock.DeskeraMockApplication;
import com.deskera.mock.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.core.content.ContextCompat;

public class UtilsHelper {

    private static final int DEFAULT_WIDTH_HEIGHT =
            DeskeraMockApplication.getContext().getResources().getDimensionPixelSize(R.dimen.letter_tile_size);
    private static final String DEFAULT_NAME = "Unknown";
    private static final String TEMP_IMAGE_NAME = "tempImage";
    public static Uri imageUri;
    private static final int DEFAULT_MIN_WIDTH_QUALITY = 400;        // min pixels


    public static int minWidthQuality = DEFAULT_MIN_WIDTH_QUALITY;

    public static Bitmap getTextBitmap(String name, int width, int height) {
        LetterTileProvider tileProvider = new LetterTileProvider(DeskeraMockApplication.getContext());
        Bitmap bitmap = tileProvider.getLetterTile(name, name, width, height);
        return bitmap;
    }

    public static Bitmap getTextBitmap(String name) {
        if (TextUtils.isEmpty(name)) name = DEFAULT_NAME;
        LetterTileProvider tileProvider = new LetterTileProvider(DeskeraMockApplication.getContext());
        Bitmap bitmap = tileProvider.getLetterTile(name, name, DEFAULT_WIDTH_HEIGHT, DEFAULT_WIDTH_HEIGHT);
        return bitmap;
    }

    public static int getRandomNumber() {
        Random r = new Random();
        return r.nextInt(1000 - 100);
    }

    public static List<String> checkAndRequestCameraStoragePermissions() {
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int storagePermission = ContextCompat.checkSelfPermission(DeskeraMockApplication.getContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE);
            int cameraPermission = ContextCompat.checkSelfPermission(DeskeraMockApplication.getContext(),
                    android.Manifest.permission.CAMERA);
            int writeStoragePermission = ContextCompat.checkSelfPermission(DeskeraMockApplication.getContext(),
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
            }
            if (storagePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            if (writeStoragePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        }
        return listPermissionsNeeded;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    public static String getPath(Context context, Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
