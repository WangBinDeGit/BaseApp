package com.wangbin.mydome.tools

import com.wangbin.mydome.bean.LetterIndexesModel
import java.util.*

/**
 * Created by WangBin on 2018/4/27.
 * PinyinComparator
 */
class PinyinComparator<T: LetterIndexesModel> : Comparator<T> {

    override fun compare(o1: T, o2: T): Int {
        return if (o1.sortLetters.equals("@") || o2.sortLetters.equals("#")) {
            -1
        } else if (o1.sortLetters.equals("#") || o2.sortLetters.equals("@")) {
            1
        } else {
            o1.sortLetters!!.compareTo(o2.sortLetters!!)
        }
    }

}
