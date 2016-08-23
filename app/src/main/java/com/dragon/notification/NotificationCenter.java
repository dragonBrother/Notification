package com.dragon.notification;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Handler;
import android.os.Looper;

/**
 * author: zhang.longping
 * date: 16/8/23
 * time: 下午3:10
 */
public class NotificationCenter {
    public static class Notification {
        private final Object source;
        private final Object[] args;

        public Notification(Object source, Object... args) {
            this.source = source;
            this.args = args;
        }

        public Object getSource() {
            return source;
        }

        public Object[] getArgs() {
            return args;
        }
    }

    public static interface NotificationObserver {
        void notify(String name, Notification notification);
    }

    private static NotificationCenter defaultCenter = new NotificationCenter();

    public static NotificationCenter getDefaultCenter() {
        return defaultCenter;
    }

    private Map<String, List<NotificationObserver>> mapping = new HashMap<String, List<NotificationObserver>>();
    private Handler handler;

    public synchronized void on(String name, NotificationObserver observer) {
        List<NotificationObserver> observers = mapping.get(name);
        if (observers == null) {
            observers = new ArrayList<NotificationObserver>();
            mapping.put(name, observers);
        }

        observers.add(observer);
    }

    public synchronized void off(String name, NotificationObserver observer) {
        List<NotificationObserver> observers = mapping.get(name);
        if (observers == null) {
            return;
        }

        observers.remove(observer);
    }

    public synchronized void off(NotificationObserver observer) {
        for (Map.Entry<String, List<NotificationObserver>> entry : mapping
                .entrySet()) {
            List<NotificationObserver> value = entry.getValue();
            value.remove(observer);
        }
    }

    public synchronized void send(final String name,
                                  final Notification notification) {
        List<NotificationObserver> observers = mapping.get(name);
        if (observers == null || observers.isEmpty()) {
            return;
        }

        if (Looper.myLooper() == Looper.getMainLooper()) {
            // in main thread
            fire(observers, name, notification);
        } else {
            final List<NotificationObserver> clonedObservers = new ArrayList<NotificationObserver>(
                    observers);
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    fire(clonedObservers, name, notification);
                }
            });
        }
    }

    private Handler getHandler() {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }

        return handler;
    }

    private void fire(List<NotificationObserver> observers, String name,
                      Notification notification) {
        for (NotificationObserver observer : observers) {
            observer.notify(name, notification);
        }
    }

}
