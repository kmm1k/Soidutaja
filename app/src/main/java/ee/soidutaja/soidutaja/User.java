package ee.soidutaja.soidutaja;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Virx on 18.10.2015.
 */
public class User implements Parcelable{

    public String name;
    public String email;
    public String facebookID;
    public String gender;

    public User() {

    }

    protected User(Parcel in) {
        name = in.readString();
        email = in.readString();
        facebookID = in.readString();
        gender = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(facebookID);
        dest.writeString(gender);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebookID() {
        return facebookID;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
