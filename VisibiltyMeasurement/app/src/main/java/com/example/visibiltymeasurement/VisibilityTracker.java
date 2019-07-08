package com.example.visibiltymeasurement;

import android.graphics.Rect;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;
import java.util.WeakHashMap;

public class VisibilityTracker {
    private static final long VISIBILITY_CHECK_DELAY_MILLIS = 100;
    private WeakHashMap<View, TrackingInfo> mTrackedViews = new WeakHashMap<>();
    private ViewTreeObserver.OnPreDrawListener mOnPreDrawListener;
    private VisibilityTrackerListener mVisibilityTrackerListener;
    private boolean mIsVisibilityCheckScheduled;
    private VisibilityChecker mVisibilityChecker;
    private Handler visibilityHandler;
    private Runnable visibilityRunnable;

    static class TrackingInfo {
        int visibilityTime;
        View view;
        int position;
    }

    static class VisibilityChecker {
        private final Rect mClipRect = new Rect();

        boolean isVisible(@Nullable final View view) {
            if (view == null || view.getVisibility() != View.VISIBLE || view.getParent() == null) {
                return false;
            }

            if (!view.getGlobalVisibleRect(mClipRect)) {
                return false;
            }

            double visibleArea = (long) mClipRect.height() * mClipRect.width();
            double totalViewArea = (long) view.getHeight() * view.getWidth();

            double percentage = 0;
            if (totalViewArea > 0) {
                percentage = (visibleArea * 100) / totalViewArea;
            }

            return percentage >= 50;
        }
    }

    public void addView(@NonNull RecyclerView.ViewHolder viewHolder)
    {
       /* if (timerMap != null && !timerMap.containsKey(viewHolder.getAdapterPosition())) {
            timerMap.put(viewHolder.getAdapterPosition(), false);
        }
        TrackingInfo trackingInfo = trackedViewObject.get(viewHolder.itemView);
        if (trackingInfo == null) {
            // view is not yet being tracked
            trackingInfo = new TrackingInfo();
            trackedViewObject.put(viewHolder.itemView, trackingInfo);
            scheduleVisibilityCheck(trackingInfo);
        }

        trackingInfo.view = viewHolder.itemView;

        trackingInfo.sectionInfo = getSectionInfo(saavnModuleObject);
        trackingInfo.position = viewHolder.getAdapterPosition();
        trackingInfo.sectionInfo.getEntityInfoList().add(entityInfo);
        trackingInfo.entityInfo = entityInfo;*/
    }

    public void setVisibilityTrackerListener(VisibilityTrackerListener listener) {
        mVisibilityTrackerListener = listener;
    }

    public void removeVisibilityTrackerListener() {
        mVisibilityTrackerListener = null;
    }

    private void scheduleVisibilityCheck() {
        if (mIsVisibilityCheckScheduled) {
            return;
        }
        mIsVisibilityCheckScheduled = true;
        visibilityHandler.postDelayed(visibilityRunnable, VISIBILITY_CHECK_DELAY_MILLIS);
    }

    class VisibilityRunnable implements Runnable {
        private TrackingInfo trackingInfo;

        VisibilityRunnable() {

        }

        @Override
        public void run() {
            mIsVisibilityCheckScheduled = false;
           /* for (final Map.Entry<View, TrackingInfo> entry : trackedViewObject.entrySet()) {
                final View view = entry.getKey();
                this.trackingInfo = trackedViewObject.get(view);

                if (mVisibilityChecker.isVisible(view)) {
                    startTimer(trackingInfo);
                } else {
                    timerMap.put(trackingInfo.position, false);
                }
            }*/

            visibilityHandler.postDelayed(this, VISIBILITY_CHECK_DELAY_MILLIS);
        }
    }

    private class VisibilityTrackerListener {
    }
}
