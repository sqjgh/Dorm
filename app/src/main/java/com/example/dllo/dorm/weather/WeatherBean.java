package com.example.dllo.dorm.weather;

import java.util.List;

/**
 * Created by Wanghuan on 16/12/7.
 */

public class WeatherBean {


    /**
     * result : {"realtime":{"wind":{"windspeed":null,"direct":"西北风","power":"1级","offset":null},"time":"15:00:00","weather":{"humidity":"38","img":"1","info":"多云","temperature":"16"},"dataUptime":"1481096225","date":"2016-12-07","city_code":"101200101","city_name":"武汉","week":"3","moon":"十一月初九"},"life":{"date":"2016-12-7","info":{"kongtiao":["开启制暖空调","您将感到有些冷，可以适当开启制暖空调调节室内温度，以免着凉感冒。"],"yundong":["较不宜","天气较好，但考虑天气寒冷，推荐您进行各种室内运动，若在户外运动请注意保暖并做好准备活动。"],"ziwaixian":["弱","紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"],"ganmao":["易发","昼夜温差很大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。"],"xiche":["较适宜","较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"],"wuran":null,"chuanyi":["较冷","建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"]}},"weather":[{"date":"2016-12-07","week":"三","nongli":"十一月初九","info":{"dawn":null,"day":["1","多云","14","","微风","07:07"],"night":["0","晴","0","","微风","17:21"]}},{"date":"2016-12-08","week":"四","nongli":"十一月初十 ","info":{"dawn":["0","晴","0","无持续风向","微风","17:21"],"day":["0","晴","16","","微风","07:07"],"night":["0","晴","5","","微风","17:21"]}},{"date":"2016-12-09","week":"五","nongli":"十一月十一","info":{"dawn":["0","晴","5","无持续风向","微风","17:21"],"day":["0","晴","16","","微风","07:08"],"night":["0","晴","4","","微风","17:22"]}},{"date":"2016-12-10","week":"六","nongli":"十一月十二","info":{"dawn":["0","晴","4","无持续风向","微风","17:22"],"day":["2","阴","12","","微风","07:09"],"night":["1","多云","5","","微风","17:22"]}},{"date":"2016-12-11","week":"日","nongli":"十一月十三","info":{"dawn":["1","多云","5","无持续风向","微风","17:22"],"day":["1","多云","14","","微风","07:10"],"night":["1","多云","4","","微风","17:22"]}}],"pm25":{"key":"Wuhan","show_desc":"0","pm25":{"curPm":"161","pm25":"123","pm10":"169","level":"4","quality":"中度污染","des":"对污染物比较敏感的人群，例如儿童和老年人、呼吸道疾病或心脏病患者，以及喜爱户外活动的人，他们的健康状况会受到影响，但对健康人群基本没有影响。"},"dateTime":"2016年12月07日15时","cityName":"武汉"},"isForeign":0}
     * error_code : 0
     * reason : Succes
     */

    private ResultEntity result;
    private int error_code;
    private String reason;

    public ResultEntity getResult() {
        return result;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
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

    public static class ResultEntity {
        /**
         * realtime : {"wind":{"windspeed":null,"direct":"西北风","power":"1级","offset":null},"time":"15:00:00","weather":{"humidity":"38","img":"1","info":"多云","temperature":"16"},"dataUptime":"1481096225","date":"2016-12-07","city_code":"101200101","city_name":"武汉","week":"3","moon":"十一月初九"}
         * life : {"date":"2016-12-7","info":{"kongtiao":["开启制暖空调","您将感到有些冷，可以适当开启制暖空调调节室内温度，以免着凉感冒。"],"yundong":["较不宜","天气较好，但考虑天气寒冷，推荐您进行各种室内运动，若在户外运动请注意保暖并做好准备活动。"],"ziwaixian":["弱","紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"],"ganmao":["易发","昼夜温差很大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。"],"xiche":["较适宜","较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"],"wuran":null,"chuanyi":["较冷","建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"]}}
         * weather : [{"date":"2016-12-07","week":"三","nongli":"十一月初九","info":{"dawn":null,"day":["1","多云","14","","微风","07:07"],"night":["0","晴","0","","微风","17:21"]}},{"date":"2016-12-08","week":"四","nongli":"十一月初十 ","info":{"dawn":["0","晴","0","无持续风向","微风","17:21"],"day":["0","晴","16","","微风","07:07"],"night":["0","晴","5","","微风","17:21"]}},{"date":"2016-12-09","week":"五","nongli":"十一月十一","info":{"dawn":["0","晴","5","无持续风向","微风","17:21"],"day":["0","晴","16","","微风","07:08"],"night":["0","晴","4","","微风","17:22"]}},{"date":"2016-12-10","week":"六","nongli":"十一月十二","info":{"dawn":["0","晴","4","无持续风向","微风","17:22"],"day":["2","阴","12","","微风","07:09"],"night":["1","多云","5","","微风","17:22"]}},{"date":"2016-12-11","week":"日","nongli":"十一月十三","info":{"dawn":["1","多云","5","无持续风向","微风","17:22"],"day":["1","多云","14","","微风","07:10"],"night":["1","多云","4","","微风","17:22"]}}]
         * pm25 : {"key":"Wuhan","show_desc":"0","pm25":{"curPm":"161","pm25":"123","pm10":"169","level":"4","quality":"中度污染","des":"对污染物比较敏感的人群，例如儿童和老年人、呼吸道疾病或心脏病患者，以及喜爱户外活动的人，他们的健康状况会受到影响，但对健康人群基本没有影响。"},"dateTime":"2016年12月07日15时","cityName":"武汉"}
         * isForeign : 0
         */

        private RealtimeEntity realtime;
        private LifeEntity life;
        private Pm25EntityX pm25;
        private int isForeign;
        private List<WeatherEntityX> weather;

        public RealtimeEntity getRealtime() {
            return realtime;
        }

        public void setRealtime(RealtimeEntity realtime) {
            this.realtime = realtime;
        }

        public LifeEntity getLife() {
            return life;
        }

        public void setLife(LifeEntity life) {
            this.life = life;
        }

        public Pm25EntityX getPm25() {
            return pm25;
        }

        public void setPm25(Pm25EntityX pm25) {
            this.pm25 = pm25;
        }

        public int getIsForeign() {
            return isForeign;
        }

        public void setIsForeign(int isForeign) {
            this.isForeign = isForeign;
        }

        public List<WeatherEntityX> getWeather() {
            return weather;
        }

        public void setWeather(List<WeatherEntityX> weather) {
            this.weather = weather;
        }

        public static class RealtimeEntity {
            /**
             * wind : {"windspeed":null,"direct":"西北风","power":"1级","offset":null}
             * time : 15:00:00
             * weather : {"humidity":"38","img":"1","info":"多云","temperature":"16"}
             * dataUptime : 1481096225
             * date : 2016-12-07
             * city_code : 101200101
             * city_name : 武汉
             * week : 3
             * moon : 十一月初九
             */

            private WindEntity wind;
            private String time;
            private WeatherEntity weather;
            private String dataUptime;
            private String date;
            private String city_code;
            private String city_name;
            private String week;
            private String moon;

            public WindEntity getWind() {
                return wind;
            }

            public void setWind(WindEntity wind) {
                this.wind = wind;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public WeatherEntity getWeather() {
                return weather;
            }

            public void setWeather(WeatherEntity weather) {
                this.weather = weather;
            }

            public String getDataUptime() {
                return dataUptime;
            }

            public void setDataUptime(String dataUptime) {
                this.dataUptime = dataUptime;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getCity_code() {
                return city_code;
            }

            public void setCity_code(String city_code) {
                this.city_code = city_code;
            }

            public String getCity_name() {
                return city_name;
            }

            public void setCity_name(String city_name) {
                this.city_name = city_name;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getMoon() {
                return moon;
            }

            public void setMoon(String moon) {
                this.moon = moon;
            }

            public static class WindEntity {
                /**
                 * windspeed : null
                 * direct : 西北风
                 * power : 1级
                 * offset : null
                 */

                private Object windspeed;
                private String direct;
                private String power;
                private Object offset;

                public Object getWindspeed() {
                    return windspeed;
                }

                public void setWindspeed(Object windspeed) {
                    this.windspeed = windspeed;
                }

                public String getDirect() {
                    return direct;
                }

                public void setDirect(String direct) {
                    this.direct = direct;
                }

                public String getPower() {
                    return power;
                }

                public void setPower(String power) {
                    this.power = power;
                }

                public Object getOffset() {
                    return offset;
                }

                public void setOffset(Object offset) {
                    this.offset = offset;
                }
            }

            public static class WeatherEntity {
                /**
                 * humidity : 38
                 * img : 1
                 * info : 多云
                 * temperature : 16
                 */

                private String humidity;
                private String img;
                private String info;
                private String temperature;

                public String getHumidity() {
                    return humidity;
                }

                public void setHumidity(String humidity) {
                    this.humidity = humidity;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }
            }
        }

        public static class LifeEntity {
            /**
             * date : 2016-12-7
             * info : {"kongtiao":["开启制暖空调","您将感到有些冷，可以适当开启制暖空调调节室内温度，以免着凉感冒。"],"yundong":["较不宜","天气较好，但考虑天气寒冷，推荐您进行各种室内运动，若在户外运动请注意保暖并做好准备活动。"],"ziwaixian":["弱","紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"],"ganmao":["易发","昼夜温差很大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。"],"xiche":["较适宜","较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"],"wuran":null,"chuanyi":["较冷","建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"]}
             */

            private String date;
            private InfoEntity info;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public InfoEntity getInfo() {
                return info;
            }

            public void setInfo(InfoEntity info) {
                this.info = info;
            }

            public static class InfoEntity {
                /**
                 * kongtiao : ["开启制暖空调","您将感到有些冷，可以适当开启制暖空调调节室内温度，以免着凉感冒。"]
                 * yundong : ["较不宜","天气较好，但考虑天气寒冷，推荐您进行各种室内运动，若在户外运动请注意保暖并做好准备活动。"]
                 * ziwaixian : ["弱","紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"]
                 * ganmao : ["易发","昼夜温差很大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。"]
                 * xiche : ["较适宜","较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"]
                 * wuran : null
                 * chuanyi : ["较冷","建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"]
                 */

                private Object wuran;
                private List<String> kongtiao;
                private List<String> yundong;
                private List<String> ziwaixian;
                private List<String> ganmao;
                private List<String> xiche;
                private List<String> chuanyi;

                public Object getWuran() {
                    return wuran;
                }

                public void setWuran(Object wuran) {
                    this.wuran = wuran;
                }

                public List<String> getKongtiao() {
                    return kongtiao;
                }

                public void setKongtiao(List<String> kongtiao) {
                    this.kongtiao = kongtiao;
                }

                public List<String> getYundong() {
                    return yundong;
                }

                public void setYundong(List<String> yundong) {
                    this.yundong = yundong;
                }

                public List<String> getZiwaixian() {
                    return ziwaixian;
                }

                public void setZiwaixian(List<String> ziwaixian) {
                    this.ziwaixian = ziwaixian;
                }

                public List<String> getGanmao() {
                    return ganmao;
                }

                public void setGanmao(List<String> ganmao) {
                    this.ganmao = ganmao;
                }

                public List<String> getXiche() {
                    return xiche;
                }

                public void setXiche(List<String> xiche) {
                    this.xiche = xiche;
                }

                public List<String> getChuanyi() {
                    return chuanyi;
                }

                public void setChuanyi(List<String> chuanyi) {
                    this.chuanyi = chuanyi;
                }
            }
        }

        public static class Pm25EntityX {
            /**
             * key : Wuhan
             * show_desc : 0
             * pm25 : {"curPm":"161","pm25":"123","pm10":"169","level":"4","quality":"中度污染","des":"对污染物比较敏感的人群，例如儿童和老年人、呼吸道疾病或心脏病患者，以及喜爱户外活动的人，他们的健康状况会受到影响，但对健康人群基本没有影响。"}
             * dateTime : 2016年12月07日15时
             * cityName : 武汉
             */

            private String key;
            private String show_desc;
            private Pm25Entity pm25;
            private String dateTime;
            private String cityName;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getShow_desc() {
                return show_desc;
            }

            public void setShow_desc(String show_desc) {
                this.show_desc = show_desc;
            }

            public Pm25Entity getPm25() {
                return pm25;
            }

            public void setPm25(Pm25Entity pm25) {
                this.pm25 = pm25;
            }

            public String getDateTime() {
                return dateTime;
            }

            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
            }

            public String getCityName() {
                return cityName;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName;
            }

            public static class Pm25Entity {
                /**
                 * curPm : 161
                 * pm25 : 123
                 * pm10 : 169
                 * level : 4
                 * quality : 中度污染
                 * des : 对污染物比较敏感的人群，例如儿童和老年人、呼吸道疾病或心脏病患者，以及喜爱户外活动的人，他们的健康状况会受到影响，但对健康人群基本没有影响。
                 */

                private String curPm;
                private String pm25;
                private String pm10;
                private String level;
                private String quality;
                private String des;

                public String getCurPm() {
                    return curPm;
                }

                public void setCurPm(String curPm) {
                    this.curPm = curPm;
                }

                public String getPm25() {
                    return pm25;
                }

                public void setPm25(String pm25) {
                    this.pm25 = pm25;
                }

                public String getPm10() {
                    return pm10;
                }

                public void setPm10(String pm10) {
                    this.pm10 = pm10;
                }

                public String getLevel() {
                    return level;
                }

                public void setLevel(String level) {
                    this.level = level;
                }

                public String getQuality() {
                    return quality;
                }

                public void setQuality(String quality) {
                    this.quality = quality;
                }

                public String getDes() {
                    return des;
                }

                public void setDes(String des) {
                    this.des = des;
                }
            }
        }

        public static class WeatherEntityX {
            /**
             * date : 2016-12-07
             * week : 三
             * nongli : 十一月初九
             * info : {"dawn":null,"day":["1","多云","14","","微风","07:07"],"night":["0","晴","0","","微风","17:21"]}
             */

            private String date;
            private String week;
            private String nongli;
            private InfoEntityX info;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getNongli() {
                return nongli;
            }

            public void setNongli(String nongli) {
                this.nongli = nongli;
            }

            public InfoEntityX getInfo() {
                return info;
            }

            public void setInfo(InfoEntityX info) {
                this.info = info;
            }

            public static class InfoEntityX {
                /**
                 * dawn : null
                 * day : ["1","多云","14","","微风","07:07"]
                 * night : ["0","晴","0","","微风","17:21"]
                 */

                private Object dawn;
                private List<String> day;
                private List<String> night;

                public Object getDawn() {
                    return dawn;
                }

                public void setDawn(Object dawn) {
                    this.dawn = dawn;
                }

                public List<String> getDay() {
                    return day;
                }

                public void setDay(List<String> day) {
                    this.day = day;
                }

                public List<String> getNight() {
                    return night;
                }

                public void setNight(List<String> night) {
                    this.night = night;
                }
            }
        }
    }
}
