package me.hsgamer.bettergui.converter.api.object;

import java.util.Comparator;
import java.util.PriorityQueue;

public abstract class CompoundConverter<F, T> {
    private final PriorityQueue<ConvertObject<T>> convertObjectSet = new PriorityQueue<>(Comparator.comparingInt(o -> o.getUnit().getPriority()));

    public void add(ConvertObject<T> convertObject) {
        convertObjectSet.add(convertObject);
    }

    public abstract F createEmpty();

    public abstract F join(F all, T current);

    public F convert() {
        F all = createEmpty();
        for (ConvertObject<T> convertObject : convertObjectSet) {
            all = join(all, convertObject.convert());
        }
        return all;
    }
}
