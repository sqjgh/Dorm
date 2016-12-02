package com.example.dllo.dorm.tools.okhttp;

import java.util.List;

/**
 * Created by Wanghuan on 16/12/1.
 */
public class InfoBean {


    /**
     * result : [{"time":"2016-11-18 19:10:20","action":"武昌南湖李纸分部(15327177571)的胡泽华已收件，扫描员是张佐15327177571"},{"time":"2016-11-18 19:10:21","action":"快件由武昌南湖李纸分部(15327177571)发往武汉(027-84639979)"},{"time":"2016-11-18 21:23:53","action":"快件到达武汉(027-84639979)，上一站是武昌南湖李纸分部(15327177571)，扫描员是乐宏定(武昌分拣中心)"},{"time":"2016-11-19 01:24:38","action":"武汉(027-84639979)已进行装袋扫描"},{"time":"2016-11-19 01:24:39","action":"快件由武汉(027-84639979)发往武汉分拨中心"},{"time":"2016-11-19 02:06:40","action":"快件到达武汉分拨中心，上一站是无，扫描员是代双桥"},{"time":"2016-11-19 02:09:07","action":"快件由武汉分拨中心发往沈阳分拨中心"},{"time":"2016-11-20 13:11:41","action":"快件由沈阳分拨中心发往大连分拨中心"},{"time":"2016-11-21 05:15:01","action":"快件由大连分拨中心发往大连黄泥川分部(13654938109)"},{"time":"2016-11-21 09:02:33","action":"快件到达大连黄泥川分部(13654938109)，上一站是大连分拨中心，扫描员是系统管理员"},{"time":"2016-11-21 09:47:28","action":"大连黄泥川分部(13654938109)的侯师傅13998681470正在派件"},{"time":"2016-11-21 11:27:51","action":"快件已由米行快递服务站菜鸟驿站代收，请及时取件，如有疑问请联系13332266780"},{"time":"2016-11-21 12:22:17","action":"已签收，签收人凭取货码签收。"},{"time":"2016-11-21 19:10:35","action":"快件已签收,签收人是丰巢，签收网点是大连黄泥川分部(13654938109)"}]
     * status : 0
     * error_code : 0
     * reason : Succes
     */

    private int status;
    private int error_code;
    private String reason;
    private List<ResultEntity> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<ResultEntity> getResult() {
        return result;
    }

    public void setResult(List<ResultEntity> result) {
        this.result = result;
    }

    public static class ResultEntity {
        /**
         * time : 2016-11-18 19:10:20
         * action : 武昌南湖李纸分部(15327177571)的胡泽华已收件，扫描员是张佐15327177571
         */

        private String time;
        private String action;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }
}
