
package app.shopping.forevermyangle.model.profile;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links implements Parcelable
{

    @SerializedName("self")
    @Expose
    public List<Self> self = null;
    @SerializedName("collection")
    @Expose
    public List<Collection> collection = null;
    public final static Creator<Links> CREATOR = new Creator<Links>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Links createFromParcel(Parcel in) {
            Links instance = new Links();
            in.readList(instance.self, (app.shopping.forevermyangle.model.profile.Self.class.getClassLoader()));
            in.readList(instance.collection, (Collection.class.getClassLoader()));
            return instance;
        }

        public Links[] newArray(int size) {
            return (new Links[size]);
        }

    }
    ;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(self);
        dest.writeList(collection);
    }

    public int describeContents() {
        return  0;
    }

}
