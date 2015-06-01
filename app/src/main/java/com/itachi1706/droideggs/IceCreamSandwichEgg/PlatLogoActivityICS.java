/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.itachi1706.droideggs.IceCreamSandwichEgg;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.Toast;

import com.itachi1706.droideggs.R;

public class PlatLogoActivityICS extends AppCompatActivity {

    Toast mToast;
    ImageView mContent;
    Vibrator mZzz;
    int mCount;
    final Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mToast = Toast.makeText(this, "Android 4.0: Ice Cream Sandwich", Toast.LENGTH_SHORT);

        mContent = new ImageView(this);
        mContent.setImageResource(R.drawable.ics_platlogo);
        mContent.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        mContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    mContent.setPressed(true);
                    mHandler.removeCallbacks(mSuperLongPress);
                    mCount = 0;
                    mHandler.postDelayed(mSuperLongPress, 2* ViewConfiguration.getLongPressTimeout());
                } else if (action == MotionEvent.ACTION_UP) {
                    if (mContent.isPressed()) {
                        mContent.setPressed(false);
                        mHandler.removeCallbacks(mSuperLongPress);
                        mToast.show();
                    }
                }
                return true;
            }
        });

        setContentView(mContent);
    }

    Runnable mSuperLongPress = new Runnable() {
        public void run() {
            mCount++;
            mZzz.vibrate(50 * mCount);
            final float scale = 1f + 0.25f * mCount * mCount;
            mContent.setScaleX(scale);
            mContent.setScaleY(scale);

            if (mCount <= 3) {
                mHandler.postDelayed(mSuperLongPress, ViewConfiguration.getLongPressTimeout());
            } else {
                try {
                    Intent nyan = new Intent(PlatLogoActivityICS.this, Nyandroid.class);
                    nyan.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(nyan);
                } catch (ActivityNotFoundException ex) {
                    android.util.Log.e("PlatLogoActivity", "Couldn't find platlogo screensaver.");
                }
                finish();
            }
        }
    };
}
