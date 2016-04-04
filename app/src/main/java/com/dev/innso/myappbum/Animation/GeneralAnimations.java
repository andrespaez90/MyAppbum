package com.dev.innso.myappbum.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;

import com.dev.innso.myappbum.animation.interfaces.AnimationListener;

import java.util.ArrayList;


public class GeneralAnimations {

    private static final AccelerateInterpolator sAccelerator = new AccelerateInterpolator();

    private static final DecelerateInterpolator sDecelerator = new DecelerateInterpolator();

    static long SHORT_DURATION = 150;

    static float sDurationScale = 1f;


    public static Animator getAnimationFadeOut(View view, int duration, AnimationListener animationListener) {
        return getAnimationFadeOut(view, duration, 0, sDecelerator, animationListener);
    }

    public static Animator getAnimationFadeOut(View view, int duration, int delay, AnimationListener animationListener) {
        return getAnimationFadeOut(view, duration, delay, sDecelerator, animationListener);
    }

    public static void animateFadeOut(final View view, int duration, final AnimationListener animationListener) {

        ObjectAnimator objectAnimator = (ObjectAnimator) getAnimationFadeOut(view, duration, animationListener);

        objectAnimator.start();
    }

    public static Animator getAnimationFadeOut(final View view, int duration, int delay, TimeInterpolator interpolator, final AnimationListener animationListener) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 0);
        objectAnimator.setDuration(duration);
        objectAnimator.setStartDelay(delay);
        if (animationListener != null)
            objectAnimator.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (animationListener != null)
                        animationListener.onAnimationEnd(null);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }

            });

        objectAnimator.setInterpolator(interpolator);

        return objectAnimator;
    }

    public static void animateFadeOut(final View view, int duration) {

        animateFadeOut(view, duration, new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
            }
        });

    }

    public static void rotate360(final View view, int duration) {
        Animation rotate = new RotateAnimation(0f, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotate.setFillAfter(true);
        rotate.setInterpolator(new DecelerateInterpolator());
        rotate.setDuration(duration);
        view.setAnimation(rotate);
    }

    public static void animateFadeIn(final View view, int duration) {
        animateFadeIn(view, duration, new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
                view.setAlpha(0);
            }
        });
    }

    public static Animator getAnimationFadeIn(final View view, int duration) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 1);
        objectAnimator.setDuration(duration);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });

        objectAnimator.setInterpolator(new DecelerateInterpolator());

        return objectAnimator;
    }

    public static void animateFadeIn(final View view, int duration, final AnimationListener animationListener) {

        ViewCompat.setAlpha(view, 0);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1);
        objectAnimator.setDuration(duration);
        objectAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
                animationListener.onAnimationStart(null);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animationListener.onAnimationEnd(null);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();

    }

    public static void hideDialog(final View view, final AnimationListener animationListener) {


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "scaleY", 1.5f),
                ObjectAnimator.ofFloat(view, "scaleX", 1.5f),
                ObjectAnimator.ofFloat(view, "alpha", 0));
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.setDuration(200);
        animatorSet.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                animationListener.onAnimationStart(null);
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animationListener.onAnimationEnd(null);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });
        animatorSet.start();


    }

    public static void showDialog(final View view) {

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "scaleY", 1.5f, 1),
                ObjectAnimator.ofFloat(view, "scaleX", 1.5f, 1),
                ObjectAnimator.ofFloat(view, "alpha", 1)
        );

        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });
        animatorSet.setDuration(300);
        animatorSet.start();
    }

    public static void zoomIn(final View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "scaleY", 1.8f, 1),
                ObjectAnimator.ofFloat(view, "scaleX", 1.8f, 1),
                ObjectAnimator.ofFloat(view, "alpha", 1)
        );
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
            }
        });

        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.setDuration(200);
        animatorSet.start();
    }

    public static void zoomOut(final View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "scaleY", 1.8f),
                ObjectAnimator.ofFloat(view, "scaleX", 1.8f),
                ObjectAnimator.ofFloat(view, "alpha", 0)
        );
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.setDuration(200);
        animatorSet.start();
    }

    public static ObjectAnimator animateChild(final View view, int delay, int from) {
        ObjectAnimator oa = ObjectAnimator.ofFloat(view, "translationY", from, 0);
        oa.setStartDelay(delay);
        oa.setDuration(1300);
        oa.setInterpolator(new OvershootInterpolator(0.5f));
        oa.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
            }
        });
        return oa;
    }

    public static void rotateInfiniteAnimation(View view, int duration, AnimatorListenerAdapter listner) {
        final ObjectAnimator anim = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        anim.setDuration(duration);
        anim.setInterpolator(new LinearInterpolator());
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                anim.setRepeatMode(ObjectAnimator.INFINITE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();
    }

    public static void appearFromTop(View view, int duration, int delay, AnimatorListenerAdapter animatorListenerAdapter) {
        appearFromTop(view, duration, delay, false, animatorListenerAdapter);
    }

    public static Animator getShakeAnimation(View view, int duration, AnimatorListenerAdapter animatorListenerListener) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, -10f, 10);

        objectAnimator.setDuration(duration);

        objectAnimator.setRepeatMode(ObjectAnimator.REVERSE);

        objectAnimator.setRepeatCount(4);

        if (animatorListenerListener != null)
            objectAnimator.addListener(animatorListenerListener);

        return objectAnimator;
    }

    public static void startAnimation(final View view, final ObjectAnimator objectAnimator, final TimeInterpolator interpolation) {


        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                view.getViewTreeObserver().removeOnPreDrawListener(this);

                objectAnimator.setInterpolator(interpolation);

                objectAnimator.start();

                return false;
            }
        });

    }

    public static Animator getTranslationY(View view, int duration, int delay, float moveY) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0, moveY);

        objectAnimator.setDuration(duration);

        objectAnimator.setStartDelay(delay);

        return objectAnimator;
    }

    public static void appearFromRight(final View view, final int duration, final int delay, final AnimatorListenerAdapter animatorListenerAdapter) {

        ObjectAnimator objectAnimator = (ObjectAnimator) getAppearFromRight(view, duration, delay, animatorListenerAdapter);

        startAnimation(view, objectAnimator, new AccelerateDecelerateInterpolator());

    }

    public static void appearFromLeft(View view, int duration, int delay, AnimatorListenerAdapter animatorListenerAdapter) {

        ObjectAnimator objectAnimator = (ObjectAnimator) getAppearFromLeft(view, duration, delay, animatorListenerAdapter);

        startAnimation(view, objectAnimator, new AccelerateDecelerateInterpolator());

    }

    public static Animator getAppearChildFromRight(ViewGroup view, int duration, int delay, int delayChild, int startChild) {
        return getAppearChildFromRight(view, duration, delay, delayChild, startChild, null);
    }

    public static void appearChildFromRight(ViewGroup view, int duration, int delay, int delayChild, AnimatorListenerAdapter animatorListenerAdapter) {
        getAppearChildFromRight(view, duration, delay, delayChild, 0, animatorListenerAdapter).start();
    }

    public static void appearChildFromLeft(ViewGroup view, int duration, int delay, int delayChild, AnimatorListenerAdapter animatorListenerAdapter) {

        getAppearChildFromLeft(view, duration, delay, delayChild, 0, animatorListenerAdapter).start();
    }

    public static Animator getAppearChildFromRight(ViewGroup view, int duration, int delay, int delayChild, int startChild, AnimatorListenerAdapter animatorListenerAdapter) {
        ArrayList<Animator> childAnimations = new ArrayList<>();

        for (int i = startChild; i < view.getChildCount(); i++) {

            childAnimations.add(getAppearFromRight(view.getChildAt(i), duration, delayChild * i, null));

        }

        return getAnimationChildren(childAnimations, duration, delay, animatorListenerAdapter);
    }


    public static Animator getAppearChildFromLeft(ViewGroup view, int duration, int delay, int delayChild, int startChild) {
        return getAppearChildFromLeft(view, duration, delay, delayChild, startChild, null);
    }


    public static Animator getAppearChildFromLeft(ViewGroup view, int duration, int delay, int delayChild, int startChild, AnimatorListenerAdapter animatorListenerAdapter) {

        ArrayList<Animator> childAnimations = new ArrayList<>();

        for (int i = startChild; i < view.getChildCount(); i++) {

            childAnimations.add(getAppearFromLeft(view.getChildAt(i), duration, delayChild * i, null));

        }

        return getAnimationChildren(childAnimations, duration, delay, animatorListenerAdapter);

    }

    public static Animator getAnimationChildren(ArrayList<Animator> childAnimations, int duration, int delay, AnimatorListenerAdapter animatorListenerAdapter) {

        AnimatorSet animationSet = new AnimatorSet();

        animationSet.playTogether(childAnimations);

        animationSet.setDuration(duration);

        animationSet.setStartDelay(delay);

        if (animatorListenerAdapter != null)
            animationSet.addListener(animatorListenerAdapter);

        return animationSet;
    }


    public static void appearFromBottom(final View view, final int duration, final int delay, final AnimatorListenerAdapter animatorListenerAdapter) {

        ObjectAnimator objectAnimator = (ObjectAnimator) getAppearFromBottom(view, duration, delay, animatorListenerAdapter);

        startAnimation(view, objectAnimator, new AccelerateDecelerateInterpolator());
    }


    public static void disappearToBottom(final View view, final int duration, final int delay, final AnimatorListenerAdapter animatorListenerAdapter) {

        ObjectAnimator objectAnimator = (ObjectAnimator) getDisappearToBottom(view, duration, delay, animatorListenerAdapter);

        startAnimation(view, objectAnimator, new AccelerateDecelerateInterpolator());

    }

    public static void disappearToTop(final View view, final int duration, final int delay, final AnimatorListenerAdapter animatorListenerAdapter) {

        ObjectAnimator objectAnimator = (ObjectAnimator) getDisappearToTop(view, duration, delay, animatorListenerAdapter);

        startAnimation(view, objectAnimator, new AccelerateDecelerateInterpolator());

    }

    public static void disappearToRight(final View view, final int duration, final int delay, final AnimatorListenerAdapter animatorListenerAdapter) {

        ObjectAnimator objectAnimator = (ObjectAnimator) getDisappearToRight(view, duration, delay, animatorListenerAdapter);

        startAnimation(view, objectAnimator, new AccelerateDecelerateInterpolator());

    }

    public static void disappearToLeft(final View view, final int duration, final int delay, final AnimatorListenerAdapter animatorListenerAdapter) {

        ObjectAnimator objectAnimator = (ObjectAnimator) getDisappearToLeft(view, duration, delay, animatorListenerAdapter);

        startAnimation(view, objectAnimator, new AccelerateDecelerateInterpolator());

    }


    public static void appearFromTop(final View view, final int duration, final int delay, final boolean bounce, final AnimatorListenerAdapter animatorListenerAdapter) {

        ObjectAnimator objectAnimator = (ObjectAnimator) getAppearFromTop(view, duration, delay, animatorListenerAdapter);

        if (!bounce)
            startAnimation(view, objectAnimator, new FastOutSlowInInterpolator());
        else
            startAnimation(view, objectAnimator, new FastOutSlowInInterpolator());

    }

    public static Animator getAppearFromTop(View view, int duration, int delay) {
        return getAppearFromTop(view, duration, delay, null);
    }

    public static Animator getAppearFromTop(View view, int duration, int delay, AnimatorListenerAdapter animatorListenerAdapter) {
        return getAppearFrom(view, duration, delay, -(view.getMeasuredHeight() * 4), 0, animatorListenerAdapter);
    }

    public static Animator getAppearFrom(final View view, final int duration, final int delay, int to, int from, final AnimatorListenerAdapter animatorListenerAdapter) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", to, from);

        objectAnimator.setDuration(duration);

        objectAnimator.setStartDelay(delay);

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
                if (animatorListenerAdapter != null) {
                    animatorListenerAdapter.onAnimationStart(animation);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (animatorListenerAdapter != null) {
                    animatorListenerAdapter.onAnimationEnd(animation);
                }
            }
        });

        return objectAnimator;
    }


    public static Animator getAppearFromLeft(final View view, final int duration, final int delay, final AnimatorListenerAdapter animatorListenerAdapter) {
        return getAppearFromLeft(view, duration, delay, new OvershootInterpolator(1f), animatorListenerAdapter);
    }

    public static Animator getAppearFromLeft(final View view, final int duration, final int delay, TimeInterpolator timeInterpolator, final AnimatorListenerAdapter animatorListenerAdapter) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, -(view.getMeasuredWidth() * 2), 0);

        objectAnimator.setDuration(duration);

        objectAnimator.setStartDelay(delay);

        objectAnimator.setInterpolator(timeInterpolator);

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationEnd(animation);
            }
        });

        return objectAnimator;
    }


    public static Animator getAppearFromRight(final View view, final int duration, final int delay, final AnimatorListenerAdapter animatorListenerAdapter) {
        return getAppearFromRight(view, duration, delay, new OvershootInterpolator(1f), animatorListenerAdapter);
    }

    public static Animator getAppearFromRight(final View view, final int duration, final int delay, TimeInterpolator timeInterpolator, final AnimatorListenerAdapter animatorListenerAdapter) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, (view.getMeasuredWidth() * 2), 0);

        objectAnimator.setDuration(duration);

        objectAnimator.setStartDelay(delay);

        objectAnimator.setInterpolator(timeInterpolator);

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationEnd(animation);
            }
        });

        return objectAnimator;

    }

    public static Animator getAppearFromBottom(View view, int duration, int delay) {
        return getAppearFromBottom(view, duration, delay, null);
    }


    public static Animator getAppearFromBottom(final View view, final int duration, final int delay, final AnimatorListenerAdapter animatorListenerAdapter) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", (view.getMeasuredHeight() * 2), 0);

        objectAnimator.setDuration(duration);

        objectAnimator.setStartDelay(delay);

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
                if (animatorListenerAdapter != null) {
                    animatorListenerAdapter.onAnimationStart(animation);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (animatorListenerAdapter != null) {
                    animatorListenerAdapter.onAnimationEnd(animation);
                }
            }
        });

        return objectAnimator;
    }


    public static Animator getDisappearToBottom(final View view, final int duration, final int delay, final AnimatorListenerAdapter animatorListenerAdapter) {

        final float previewY = view.getTranslationY();

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", 0, (view.getMeasuredHeight() * 2));

        objectAnimator.setDuration(duration);

        objectAnimator.setStartDelay(delay);

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setTranslationY(previewY);
                view.setVisibility(View.INVISIBLE);
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationEnd(animation);
            }
        });

        return objectAnimator;
    }

    public static Animator getDisappearToTop(final View view, final int duration, final int delay, final AnimatorListenerAdapter animatorListenerAdapter) {

        final float previewY = view.getTranslationY();

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0, -(view.getMeasuredHeight() * 2));

        objectAnimator.setDuration(duration);

        objectAnimator.setStartDelay(delay);

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
                view.setTranslationY(previewY);
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationEnd(animation);
            }
        });

        return objectAnimator;
    }


    public static Animator getDisappearToRight(final View view, final int duration, final int delay, final AnimatorListenerAdapter animatorListenerAdapter) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationX", 0, (view.getMeasuredWidth() * 2));

        objectAnimator.setDuration(duration);

        objectAnimator.setStartDelay(delay);

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationEnd(animation);
            }
        });

        return objectAnimator;

    }

    public static Animator getDisappearToLeft(final View view, final int duration, final int delay, final AnimatorListenerAdapter animatorListenerAdapter) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationX", 0, -(view.getMeasuredWidth() * 2));

        objectAnimator.setDuration(duration);

        objectAnimator.setStartDelay(delay);

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationEnd(animation);
            }
        });
        return objectAnimator;
    }


    public static void appearFromBottomAndFadeIn(final View view, int duration, final AnimatorListenerAdapter listener) {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, (view.getMeasuredHeight() * 2), 0);
        translationY.setDuration(duration);

        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, View.ALPHA, 0, 1);
        fadeIn.setDuration(duration);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translationY, fadeIn);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (listener != null)
                    listener.onAnimationEnd(animation);
            }
        });

        animatorSet.start();
    }


    public static void scaleY(final View view, int duration, float start, float end, final AnimatorListenerAdapter listener) {
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, start, end);
        scaleY.setDuration(duration);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleY);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (listener != null) {
                    listener.onAnimationEnd(animation);
                }
            }
        });

        animatorSet.start();
    }

    public static void animateAlpha(final View view, int duration, float start, float end, final AnimationListener animationListener) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, start, end);
        objectAnimator.setDuration(duration);
        objectAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (animationListener != null)
                    animationListener.onAnimationEnd(null);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });

        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();


    }

    public static void fadeOutViews(int duration, View... views) {
        for (View view : views) {
            animateFadeOut(view, duration);
        }
    }

    public static void fadeInViews(int duration, View... views) {
        for (View view : views) {
            animateFadeIn(view, duration);
        }
    }

    public static ObjectAnimator scaleY(View view, int duration, float from, float to) {

        ObjectAnimator animScaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, from, to);

        animScaleY.setDuration(duration);

        return animScaleY;
    }

    public static AnimatorSet scaleView(View view, int duration, float from, float to) {
        return scaleView(view, duration, from, to, false);
    }

    public static AnimatorSet scaleView(View view, int duration, float from, float to, boolean repeat) {
        ObjectAnimator anim_scale_X = ObjectAnimator.ofFloat(view, "scaleX", from, to);
        ObjectAnimator anim_scale_Y = ObjectAnimator.ofFloat(view, "scaleY", from, to);

        if (repeat) {
            anim_scale_X.setRepeatCount(ValueAnimator.INFINITE);
            anim_scale_X.setRepeatMode(ValueAnimator.REVERSE);

            anim_scale_Y.setRepeatCount(ValueAnimator.INFINITE);
            anim_scale_Y.setRepeatMode(ValueAnimator.REVERSE);
        }

        anim_scale_X.setDuration(duration);
        anim_scale_Y.setDuration(duration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(anim_scale_X).with(anim_scale_Y);
        animatorSet.setInterpolator(new LinearInterpolator());
        return animatorSet;
    }

    public static void scaleChildrenView(ViewGroup view, int duration, float from, float to) {
        int countChildren = view.getChildCount();
        for (int i = 0; i < countChildren; i++) {
            scaleView(view.getChildAt(i), duration, from, to).start();
        }
    }

    public static void throbInfiniteAnimation(View view, int duration, Animator.AnimatorListener listener) {
        AnimatorSet animatorSet1 = scaleView(view, duration, 1, 1.15f, true);

        if (listener != null)
            animatorSet1.addListener(listener);

        animatorSet1.start();
    }


    public static void beatAnimation(final View view, final long startDelay, int duration) {

        beatAnimation(view, startDelay, duration, new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }
        });
    }

    public static void beatAnimation(final View view, final long startDelay, int duration, Animator.AnimatorListener listener) {

        AnimatorSet animatorSet1 = scaleView(view, duration, 1, 1.3f);
        AnimatorSet animatorSet2 = scaleView(view, (int) (duration / 2.5f), 1.3f, 0.75f);
        AnimatorSet animatorSet3 = scaleView(view, duration / 2, 0.75f, 1.1f);
        AnimatorSet animatorSet4 = scaleView(view, duration / 2, 1.1f, 0.9f);
        AnimatorSet animatorSet5 = scaleView(view, duration / 2, 0.9f, 1);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setStartDelay(startDelay);
        animatorSet.playSequentially(animatorSet1, animatorSet2, animatorSet3, animatorSet4, animatorSet5);
        animatorSet.addListener(listener);

        animatorSet.start();
    }

    public static void beatAnimation(View view, long startDelay) {
        beatAnimation(view, startDelay, 400);
    }

    public static void showViewSlideY(View view, int translate, int duration) {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, translate);
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.setDuration(duration);
        set.play(translationY);
        set.start();
    }
}
