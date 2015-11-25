package ee.soidutaja.soidutaja;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Virx on 11.10.2015.
 */
public class Drive implements Parcelable {

    private String id;
    private String user;
    private String origin;
    private String destination;
    private String dateTime;
    private String price;
    private String info;
    private int availableSlots;
    private String fId;

    public Drive() {

    }

    protected Drive(Parcel in) {
        user = in.readString();
        origin = in.readString();
        destination = in.readString();
        dateTime = in.readString();
        price = in.readString();
        info = in.readString();
        availableSlots = in.readInt();
        fId = in.readString();
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

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }

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


    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String date) {
        this.dateTime = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        dest.writeString(dateTime);
        dest.writeString(price);
        dest.writeString(info);
        dest.writeInt(availableSlots);
        dest.writeString(fId);
    }

    @Override
    public String toString() {
        return "Drive{" +
                "user='" + user + '\'' +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", price='" + price + '\'' +
                ", info='" + info + '\'' +
                ", availableSlots=" + availableSlots +
                ", fId=" + fId +
                '}';
    }
}
