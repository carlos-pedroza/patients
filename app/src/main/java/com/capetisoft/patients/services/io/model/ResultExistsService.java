package com.capetisoft.patients.services.io.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by carlospedroza on 01/12/15.
 */
public class ResultExistsService {

        @SerializedName("total")
        private
        String total;

        public int getTotal() {
            return Integer.valueOf(total);
        }

        public void setTotal(String total) {
            this.total = total;
        }

}
