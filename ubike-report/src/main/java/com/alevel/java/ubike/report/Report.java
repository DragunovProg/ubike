package com.alevel.java.ubike.report;

import com.alevel.java.ubike.exception.UbikeReportException;

public interface Report<T> {

    T load() throws UbikeReportException;

}
