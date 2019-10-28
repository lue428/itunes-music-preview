package com.mcs.luel.itunesmusicpriview.pojoClasses;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ResultsPojo implements Parcelable {
    List<TrackPojo> results;

    public ResultsPojo() {
    }

    protected ResultsPojo(Parcel in) {
        results = in.readParcelableList(results, TrackPojo.class.getClassLoader());
    }

    public static final Creator<ResultsPojo> CREATOR = new Creator<ResultsPojo>() {
        @Override
        public ResultsPojo createFromParcel(Parcel in) {
            return new ResultsPojo(in);
        }

        @Override
        public ResultsPojo[] newArray(int size) {
            return new ResultsPojo[size];
        }
    };

    public List<TrackPojo> getResults() {
        return results;
    }

    public void setResults(List<TrackPojo> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(results);
    }
}
