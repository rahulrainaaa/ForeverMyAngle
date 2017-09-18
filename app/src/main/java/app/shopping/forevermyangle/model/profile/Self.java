
package app.shopping.forevermyangle.model.profile;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Self implements Parcelable
{

    @SerializedName("href")
    @Expose
    public String href;
    public final static Creator<Self> CREATOR = new Creator<Self>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Self createFromParcel(Parcel in) {
            Self instance = new Self();
            instance.href = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Self[] newArray(int size) {
            return (new Self[size]);
        }

    }
    ;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(href);
    }

    public int describeContents() {
        return  0;
    }

}
