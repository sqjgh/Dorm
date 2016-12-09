package com.example.dllo.dorm.right.news;

import java.util.List;

/**
 * Created by Wanghuan on 16/12/6.
 */

public class HistoryBean {


    /**
     * result : [{"title":"深刻领会习近平生态文明战略思想","content":"近日,<em>习近平<\/em>总书记再次对生态文明建设作出重要指示强调,生态文明建设是\"五位一体\"总体布局和\"四个全面\"战略布局的重要内容,各地区各部门要切实贯彻新发展理念,树立\"绿水青山就是金山银山\"的强烈意识,努力走向社会主义生态文明新时代。  可以看到,<em>习近平<\/em>总书记的这个指示,把生...","img_width":"","full_title":"深刻领会习近平生态文明战略思想","pdate":"54分钟前","src":"光明网","img_length":"","img":"","url":"http://theory.gmw.cn/2016-12/06/content_23194105.htm","pdate_src":"2016-12-06 09:17:00"},{"title":"习近平:总结经验完善思路突出重点提高改革整体效能扩大改革受益面","content":"新华社北京12月5日电中共中央总书记、国家主席、中央军委主席、中央全面深化改革领导小组组长<em>习近平<\/em>12月5日下午主持召开中央全面深化改革领导小组第三十次会议并发表重要讲话。他强调,总结谋划好改革工作,对做好明年和今后改革工作具有重要意义,要总结经验、完善思路、...","img_width":"","full_title":"习近平:总结经验完善思路突出重点提高改革整体效能扩大改革受益面","pdate":"1小时前","src":"新华网","img_length":"","img":"","url":"http://news.xinhuanet.com/mrdx/2016-12/06/c_135883492.htm","pdate_src":"2016-12-06 08:54:47"},{"title":"宪法日,学习习近平指示中的法治精神","content":"从而减少错杀。英宗采纳了他的建言,天顺三年就下诏曰:每岁霜降后,\"三法司\"会同廷臣审录重囚,谓之朝审,遂为永制。上述虽为死刑之故事,但足可见法律是治国之重器!今天,理解<em>习近平<\/em>主席在宪法日的新指示,对如何做好依法治国,维护社会公平正义,十分重要! 依宪执政是基础。<em>习近平<\/em>强...","img_width":"","full_title":"宪法日,学习习近平指示中的法治精神","pdate":"2小时前","src":"中国青年网","img_length":"","img":"","url":"http://pinglun.youth.cn/ll/201612/t20161206_8918341.htm","pdate_src":"2016-12-06 07:24:00"},{"title":"习近平谈依宪治国依宪执政","content":"--2015年2月2日,<em>习近平<\/em>在省部级主要领导干部学习贯彻十八届四中全会精神全面推进依法治国专题研讨班开班式上的讲话 ★要在全社会牢固树立宪法法律权威,弘扬宪法精神,任何组织和个人都必须在宪法法律范围内活动,都不得有超越宪法法律的特权。 --2016年7月1日,<em>习近平<\/em>在庆祝中...","img_width":"331","full_title":"习近平谈依宪治国依宪执政","pdate":"17小时前","src":"中国网","img_length":"516","img":"http://p1.qhimg.com/t01445cf2d0282fafd9.jpg","url":"http://www.china.com.cn/news/2016-12/05/content_39851885.htm","pdate_src":"2016-12-05 16:11:51"}]
     * error_code : 0
     * reason : Succes
     */

    private int error_code;
    private String reason;
    private List<ResultEntity> result;

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
         * title : 深刻领会习近平生态文明战略思想
         * content : 近日,<em>习近平</em>总书记再次对生态文明建设作出重要指示强调,生态文明建设是"五位一体"总体布局和"四个全面"战略布局的重要内容,各地区各部门要切实贯彻新发展理念,树立"绿水青山就是金山银山"的强烈意识,努力走向社会主义生态文明新时代。  可以看到,<em>习近平</em>总书记的这个指示,把生...
         * img_width :
         * full_title : 深刻领会习近平生态文明战略思想
         * pdate : 54分钟前
         * src : 光明网
         * img_length :
         * img :
         * url : http://theory.gmw.cn/2016-12/06/content_23194105.htm
         * pdate_src : 2016-12-06 09:17:00
         */

        private String title;
        private String content;
        private String img_width;
        private String full_title;
        private String pdate;
        private String src;
        private String img_length;
        private String img;
        private String url;
        private String pdate_src;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImg_width() {
            return img_width;
        }

        public void setImg_width(String img_width) {
            this.img_width = img_width;
        }

        public String getFull_title() {
            return full_title;
        }

        public void setFull_title(String full_title) {
            this.full_title = full_title;
        }

        public String getPdate() {
            return pdate;
        }

        public void setPdate(String pdate) {
            this.pdate = pdate;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getImg_length() {
            return img_length;
        }

        public void setImg_length(String img_length) {
            this.img_length = img_length;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPdate_src() {
            return pdate_src;
        }

        public void setPdate_src(String pdate_src) {
            this.pdate_src = pdate_src;
        }
    }
}
