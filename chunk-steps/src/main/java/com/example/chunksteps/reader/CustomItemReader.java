package com.example.chunksteps.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.Iterator;
import java.util.List;

/**
 * @author Hjwasim
 */
public class CustomItemReader implements ItemReader<String> {
    Iterator<String> iterator;

    public CustomItemReader(List<String> lists) {
        this.iterator = lists.iterator();
    }

    @Override
    public String read() throws Exception {
        return iterator.hasNext() ? iterator.next() : null;
    }
}
