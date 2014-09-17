package cn.kongnannan.example;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import cn.kongnannan.circularavatar.JoinBitmaps;
import cn.kongnannan.circularavatar.JoinLayout;

public class BitmapActivity extends Activity {

    ArrayList<Bitmap> mBmps = new ArrayList<Bitmap>();

    ImageView mImageView1;
    ImageView mImageView2;
    ImageView mImageView3;
    ImageView mImageView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);

        mImageView1 = (ImageView) findViewById(R.id.imageView1);
        mImageView2 = (ImageView) findViewById(R.id.imageView2);
        mImageView3 = (ImageView) findViewById(R.id.imageView3);
        mImageView4 = (ImageView) findViewById(R.id.imageView4);

        Bitmap avatar1 = BitmapFactory.decodeResource(getResources(), R.drawable.headshow1);
        Bitmap avatar2 = BitmapFactory.decodeResource(getResources(), R.drawable.headshow2);
        Bitmap avatar3 = BitmapFactory.decodeResource(getResources(), R.drawable.headshow3);

        mBmps.add(avatar1);
        mBmps.add(avatar2);
        mBmps.add(avatar3);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mImageView1.setImageBitmap(JoinBitmaps.createBitmap(mImageView1.getWidth(),
                    mImageView1.getHeight(), mBmps));

            int count = Math.min(mBmps.size(), JoinLayout.max());
            float[] size2 = size2(count);

            mImageView2.setImageBitmap(JoinBitmaps.createBitmap(mImageView2.getWidth(),
                    mImageView2.getHeight(), mBmps, count, size2));

            float[] size3 = size3(count);
            mImageView3.setImageBitmap(JoinBitmaps.createBitmap(mImageView3.getWidth(),
                    mImageView3.getHeight(), mBmps, count, size3));

            mImageView4.setImageBitmap(JoinBitmaps.createBitmap(mImageView4.getWidth(),
                    mImageView4.getHeight(), mBmps, count, size2, 0.3f));
        }
    }

    private static final float[][] sizes3 = { new float[] { 0.9f, 0.9f },
            new float[] { 0.5f, 0.65f }, new float[] { 0.45f, 0.9f },
            new float[] { 0.45f, 0.91f }, new float[] { 0.38f, 0.80f } };

    public static float[] size3(int count) {
        return count > 0 && count <= sizes3.length ? sizes3[count - 1] : null;
    }

    private static final float[][] sizes2 = { new float[] { 0.9f, 0.9f },
            new float[] { 0.5f, 0.65f }, new float[] { 0.35f, 0.8f },
            new float[] { 0.45f, 0.91f }, new float[] { 0.38f, 0.80f } };

    public static float[] size2(int count) {
        return count > 0 && count <= sizes2.length ? sizes2[count - 1] : null;
    }
}
