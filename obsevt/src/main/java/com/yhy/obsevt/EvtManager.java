package com.yhy.obsevt;

import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-09-09 10:39
 * version: 1.0.0
 * desc   : 事件管理器
 */
public class EvtManager {
    private volatile static EvtManager instance;

    // 将注册进来的事件观察者保存到一个统一的集合中（为了避免OOM，使用弱引用）
    private final List<WeakReference<EvtObserver>> mEvtObsList;

    private EvtManager() {
        if (null != instance) {
            throw new IllegalStateException("Can not instantiate singleton class.");
        }

        mEvtObsList = new ArrayList<>();
    }

    /**
     * 获取事件管理器的单例对象
     *
     * @return 单例对象
     */
    public static EvtManager getInstance() {
        if (null == instance) {
            synchronized (EvtManager.class) {
                if (null == instance) {
                    instance = new EvtManager();
                }
            }
        }
        return instance;
    }

    /**
     * 将事件观察者注册到管理器
     *
     * @param observer 事件观察者对象
     */
    public void regist(EvtObserver observer) {
        if (null != observer && null != mEvtObsList && !contains(observer)) {
            mEvtObsList.add(new WeakReference<>(observer));
        }
    }

    /**
     * 将事件观察者从管理器中注销
     *
     * @param observer 事件观察者对象
     */
    public void unRegist(EvtObserver observer) {
        if (null != observer && null != mEvtObsList && contains(observer)) {
            mEvtObsList.remove(getWeakObs(observer));
        }
    }

    /**
     * 通知事件
     *
     * @param evtCode 事件code
     */
    public void notifyEvt(int evtCode) {
        notifyEvt(evtCode, null);
    }

    /**
     * 通知事件
     *
     * @param evtCode 事件code
     * @param data    事件携带的数据
     */
    public void notifyEvt(int evtCode, Bundle data) {
        // 通过code获取到对应的事件观察者集合（由于同样的事件可能会在多个页面注册，所以是集合）
        List<EvtObserver> obsList = getObsListByCode(evtCode);
        if (null != obsList) {
            // 遍历并通过每个事件观察者触发通知事件
            for (EvtObserver obs : obsList) {
                obs.notifyEvt(evtCode, data);
            }
        }
    }

    /**
     * 判断某个事件观察者是否已经在管理器中注册
     *
     * @param observer 事件观察者
     * @return 是否已经注册
     */
    private boolean contains(EvtObserver observer) {
        return null != getWeakObs(observer);
    }

    /**
     * 获取某个事件观察者对应的所引用对象
     *
     * @param observer 事件观察者
     * @return 事件观察者对应的所引用对象
     */
    private WeakReference<EvtObserver> getWeakObs(EvtObserver observer) {
        if (null != observer && null != mEvtObsList) {
            for (WeakReference<EvtObserver> eo : mEvtObsList) {
                if (eo.get() == observer) {
                    return eo;
                }
            }
        }
        return null;
    }

    /**
     * 通过事件code获取对应的事件观察者集合
     *
     * @param evtCode 事件code
     * @return 对应的事件观察者集合
     */
    private List<EvtObserver> getObsListByCode(int evtCode) {
        if (null != mEvtObsList) {
            // 由于同样的事件可能会在多个页面注册，而事件触发需要同时触发这些页面的事件，所以需要返回一个相同事件code的事件观察者集合
            List<EvtObserver> result = new ArrayList<>();
            // 临时变量
            EvtObserver evtObs;
            // 先遍历所引用集合，获取到每个事件观察者
            for (WeakReference<EvtObserver> eo : mEvtObsList) {
                evtObs = eo.get();
                if (null != evtObs) {
                    // 再逐个遍历事件观察者的事件code，并判断
                    for (int code : evtObs.getEvtCode()) {
                        if (code == evtCode) {
                            // 如果事件code匹配上，就将该事件观察者添加到结果集中
                            result.add(evtObs);
                        }
                    }
                }
            }
            return result;
        }
        return null;
    }
}
