package com.testing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.testing.common.ActivityUtils;
import com.testing.user.UserFragment;

/**
 * Main activity that contains container for changing fragments.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ActivityUtils.openFragment(this, new UserFragment());
    }
}
