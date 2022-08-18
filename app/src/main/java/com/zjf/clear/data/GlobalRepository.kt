package com.zjf.clear.data

import com.blankj.utilcode.util.TimeUtils

/**
 * create by colin
 * 2022/8/18
 */
object GlobalRepository {

    private var lastInCleanTime = 0L

    /**
     * if in clean page within 10 minute.
     * we get a small value
     */
    fun isRecentInClean(): Boolean {
        val now = TimeUtils.getNowMills()
        val recent = now - lastInCleanTime < 1000 * 60 * 10
        lastInCleanTime = now
        return recent
    }
}