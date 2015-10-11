package ee.soidutaja.soidutaja;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Virx on 11.10.2015.
 */
public class Drive implements Parcelable {

    private String user;
    private String origin;
    private String destination;
    private String time;
    private String date;
    private String price;
    private String info;
    private int availableSlots;


    public Drive() {

    }

    protected Drive(Parcel in) {
        user = in.readString();
        origin = in.readString();
        destination = in.readString();
        time = in.readString();
        date = in.readString();
        price = in.readString();
        info = in.readString();
        availableSlots = in.readInt();
    }

    public static final Creator<Drive> CREATOR = new Creator<Drive>() {
        @Override
        public Drive createFromParcel(Parcel in) {
            return new Drive(in);
        }

        @Override
        public Drive[] newArray(int size) {
            return new Drive[size];
        }
    };

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(int availableSlots) {
        this.availableSlots = availableSlots;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user);
        dest.writeString(origin);
        dest.writeString(destination);
        dest.writeString(time);
        dest.writeString(date);
        dest.writeString(price);
        dest.writeString(info);
        dest.writeInt(availableSlots);
    }
}
