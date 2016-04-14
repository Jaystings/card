/*
    ViewFlipper activity.
 */

package com.jaystings.card;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ViewFlipper;
import android.content.Intent;

public class BookActivity extends AppCompatActivity {
    /** A handler object, used for deferring UI operations. */
    private Handler mHandler = new Handler();

    private ViewFlipper viewFlipper;
    private float lastX;

    private ProgressBar pbSpinner;

    // Flips until the secret editing menu is opened.
    private final int DEFAULT_TEM_VAL = 10;
    private int tillEditMenu = DEFAULT_TEM_VAL;

    private final String PREFS_NAME = "cardSettings";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        pbSpinner = (ProgressBar) findViewById(R.id.pbSpinner);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();



    }

    /* Method for turning the pages without showing an animation, using buttons.
     * Pre: page turning button has been pressed
     * Post: page turns according to the indicated button pressed,
     *       unless the beginning / end of the book has been reached
     */
    public void TurnPage(View v){
        // Show no animation for button press.
        viewFlipper.setInAnimation(null);
        viewFlipper.setOutAnimation(null);

        int pgNum = viewFlipper.getDisplayedChild();
        // Identify which button was pressed
        // If left arrow was pressed 10 times on first page, open menu
        if(v.getId() == R.id.btnFlipL){
            if (pgNum == 0) {
                tillEditMenu--;
                if(tillEditMenu == 0){
                    System.out.println("Open Sesame!");
                    // Open Edit Menu Prompt
                    Intent i = new Intent(this, EditPrompt.class);
                    startActivity(i);
                    tillEditMenu = DEFAULT_TEM_VAL;
                }
                return;
            } else {
                tillEditMenu = DEFAULT_TEM_VAL;
                pbSpinner.setVisibility(View.VISIBLE);
                viewFlipper.setDisplayedChild(--pgNum);
                pbSpinner.setVisibility(View.GONE);
            }
        }
        else{
            if (pgNum == 2) return;
            tillEditMenu = DEFAULT_TEM_VAL;
            pbSpinner.setVisibility(View.VISIBLE);
            viewFlipper.setDisplayedChild(++pgNum);
            pbSpinner.setVisibility(View.GONE);
        }
    }

    // Method to handle touch event like left to right swap and right to left swap
    public boolean onTouchEvent(MotionEvent touchevent)
    {
        switch (touchevent.getAction())
        {
            // when user first touches the screen to swap
            case MotionEvent.ACTION_DOWN:
            {
                lastX = touchevent.getX();
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                float currentX = touchevent.getX();

                // if left to right swipe on screen
                if (lastX < currentX)
                {
                    // If no more View/Child to flip
                    if (viewFlipper.getDisplayedChild() == 0)
                        break;

                    // set the required Animation type to ViewFlipper
                    // The Next screen will come in form Left and current Screen will go OUT from Right
                    viewFlipper.setInAnimation(this, R.anim.in_from_left);
                    viewFlipper.setOutAnimation(this, R.anim.out_to_right);
                    // Show the next Screen
                    viewFlipper.showPrevious();
                }

                // if right to left swipe on screen
                if (lastX > currentX)
                {
                    if (viewFlipper.getDisplayedChild() == 2)
                        break;
                    // set the required Animation type to ViewFlipper
                    // The Next screen will come in form Right and current Screen will go OUT from Left
                    viewFlipper.setInAnimation(this, R.anim.in_from_right);
                    viewFlipper.setOutAnimation(this, R.anim.out_to_left);
                    // Show The Previous Screen
                    viewFlipper.showNext();
                }
                break;
            }
        }
        return false;
    }

}
