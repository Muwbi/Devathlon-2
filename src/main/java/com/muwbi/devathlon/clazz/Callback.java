package com.muwbi.devathlon.clazz;

/**
 * Created by Muwbi
 * <p/>
 * Used to handle future tasks
 */
public interface Callback<T> {

    /**
     * Called when the action should be done
     *
     * @param t
     */
    public void done( T t );

}
