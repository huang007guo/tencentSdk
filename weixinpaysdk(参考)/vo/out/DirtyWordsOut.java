package com.wjj.application.util.weixinpaysdk.vo.out;

import java.util.List;

/**
 * 查询APP自定义脏字
 * {
     "ActionStatus": "OK",
     "ErrorInfo": "",
     "ErrorCode": 0,
     "DirtyWordsList": [
         "回收金币",
         "代刷人气",
         "抽奖"
     ]
 }
 */
public class DirtyWordsOut extends TencentImOut {
    private List<String> DirtyWordsList;

    public List<String> getDirtyWordsList() {
        return DirtyWordsList;
    }

    public void setDirtyWordsList(List<String> dirtyWordsList) {
        DirtyWordsList = dirtyWordsList;
    }
}
