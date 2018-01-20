package com.example.ujjwol.myapplication;

/**
 * Created by ujjwol on 12/30/2017.
 */

public class ClassRecordList {

        private String period;
        private String taught_topic;

        public ClassRecordList(String period, String taught_topic) {
            super();
            this.period = period;
            this.taught_topic= taught_topic;
        }


    public String getPeriod() {
        return period;
    }

    public String getTaught_topic() {
        return taught_topic;
    }


}
