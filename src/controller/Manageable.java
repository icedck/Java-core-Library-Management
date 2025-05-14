package controller;

public interface Manageable<T> {
    void add(T item);
    void remove(String id);
    void listAll();
    T findId(String id);
}
