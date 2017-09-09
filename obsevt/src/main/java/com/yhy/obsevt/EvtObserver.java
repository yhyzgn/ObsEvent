package com.yhy.obsevt;

import android.os.Bundle;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2017-09-09 9:55
 * version: 1.0.0
 * desc   : 事件观察者
 */
public interface EvtObserver {

    /**
     * 通知事件
     *
     * @param evtCode 事件code
     * @param data    事件携带的数据
     */
    void notifyEvt(int evtCode, Bundle data);

    /**
     * 获取当前事件的code数组
     *
     * @return 事件code数组
     */
    int[] getEvtCode();
}
