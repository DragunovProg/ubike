package com.alevel.java.ubike.command;

import com.alevel.java.ubike.exceptions.UbikeIngestException;

public interface Command<T> {

    T execute() throws UbikeIngestException;

}
