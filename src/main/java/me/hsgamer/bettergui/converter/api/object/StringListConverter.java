package me.hsgamer.bettergui.converter.api.object;

import java.util.ArrayList;
import java.util.List;

public class StringListConverter extends CompoundConverter<List<String>, String> {
    @Override
    public List<String> createEmpty() {
        return new ArrayList<>();
    }

    @Override
    public List<String> join(List<String> all, String current) {
        all.add(current);
        return all;
    }
}
