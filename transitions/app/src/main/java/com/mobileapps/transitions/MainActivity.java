package com.mobileapps.transitions;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FirstFragment.OnFragmentInteractionListener, SecondFragment.OnFragmentInteractionListener {

    private float x1,x2;
    private FragmentTransaction transaction;
    static final int MIN_DISTANCE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.main_container, new FirstFragment());
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                transaction = getFragmentManager().beginTransaction();
                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    if (deltaX > 0) {
                        /*Intent intent_next = new Intent(MainActivity.this, SecondActivity.class);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        startActivity(intent_next);
                        finish();*/

                        /*transaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                        transaction.replace(R.id.main_container, new SecondFragment());
                        transaction.addToBackStack(null);
                        transaction.commit();*/
                        openFragment(new SecondFragment());
                        Toast.makeText(this, "left2right swipe", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        openFragment(new FirstFragment());
                        Toast.makeText(this, "right2left swipe", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    // consider as something else - a screen tap for example
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    private void openFragment(final Fragment fragment)   {
        //transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
        transaction.replace(R.id.main_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
