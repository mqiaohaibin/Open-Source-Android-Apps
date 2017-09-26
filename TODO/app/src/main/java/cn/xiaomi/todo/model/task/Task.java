package cn.xiaomi.todo.model.task;

import java.util.UUID;

/**
 * Created by qiaohaibin on 21/09/2017.
 */

public class Task {

    private final String mId;
    private String mTitle;
    private String mDescription;
    private boolean mCompleted;

    public Task(String title, String description) {
        this(UUID.randomUUID().toString(), title, description, false);
    }

    public Task(String id, String title, String description, boolean completed) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mCompleted = completed;
    }

    public String getId() {
        return mId;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public void setCompleted(boolean completed) {
        mCompleted = completed;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }
}
