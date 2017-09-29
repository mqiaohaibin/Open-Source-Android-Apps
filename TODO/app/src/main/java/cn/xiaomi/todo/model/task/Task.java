package cn.xiaomi.todo.model.task;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

/**
 * Created by qiaohaibin on 21/09/2017.
 */

public class Task implements Parcelable{

    private final String mId;
    private String mTitle;
    private String mDescription;
    private boolean mCompleted;

    public Task(String title, String description, boolean completed) {
        this(UUID.randomUUID().toString(), title, description, completed);
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

    public static final  Parcelable.Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            String id = source.readString();
            String title = source.readString();
            String description = source.readString();
            boolean completed = source.readByte() == 1;
            return new Task(id, title, description, completed);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeByte((byte) (mCompleted ? 1 : 0));
    }
}
