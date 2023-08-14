package org.souvenirshop.dao;

import org.souvenirshop.util.FileOperation;

import java.util.List;

public abstract class AbstractDao<T> {
    protected final String filename;
    protected List<T> items;
    protected final FileOperation<T> fileOperation;

    public AbstractDao() {
        this.filename = getFileName();
        this.fileOperation = new FileOperation<T>();
        this.items = fileOperation.readFromFile(filename);
    }

    protected abstract String getFileName();

    public void save(T t) {
        List<T> tempList = items;
        tempList.add(t);

        fileOperation.writeIntoFile(tempList, filename);

        items = tempList;
    }

    public abstract void update(T t);

    public void delete(T t) {
        List<T> tempList = items;
        tempList.remove(t);

        fileOperation.writeIntoFile(tempList, filename);
        items = tempList;
    }

    public List<T> getAll() {
        return items;
    }
}