package com.example.uas202102266;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CuacaListModel {
    @SerializedName("main")
    private CuacaMainModel mainModel;
    @SerializedName("weather")
    private List<CuacaWeatherModel> weatherModelList;
    private String dt_txt;

    public CuacaListModel() {

    }

    public CuacaMainModel getMainModel() {
        return mainModel;
    }

    public void setMainModel(CuacaMainModel mainModel) {
        this.mainModel = mainModel;
    }

    public List<CuacaWeatherModel> getWeatherModelList() {
        return weatherModelList;
    }

    public void setWeatherModelList(List<CuacaWeatherModel> weatherModelList) {
        this.weatherModelList = weatherModelList;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }
}
