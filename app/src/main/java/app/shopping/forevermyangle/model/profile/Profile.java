
package app.shopping.forevermyangle.model.profile;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import app.shopping.forevermyangle.model.base.BaseModel;

public class Profile extends BaseModel implements Parcelable {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("date_created")
    @Expose
    public String dateCreated;
    @SerializedName("date_created_gmt")
    @Expose
    public String dateCreatedGmt;
    @SerializedName("date_modified")
    @Expose
    public String dateModified;
    @SerializedName("date_modified_gmt")
    @Expose
    public String dateModifiedGmt;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("last_name")
    @Expose
    public String lastName;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("billing")
    @Expose
    public Billing billing;
    @SerializedName("shipping")
    @Expose
    public Shipping shipping;
    @SerializedName("is_paying_customer")
    @Expose
    public Boolean isPayingCustomer;
    @SerializedName("orders_count")
    @Expose
    public Integer ordersCount;
    @SerializedName("total_spent")
    @Expose
    public String totalSpent;
    @SerializedName("avatar_url")
    @Expose
    public String avatarUrl;
    @SerializedName("meta_data")
    @Expose
    public List<Object> metaData = null;
    @SerializedName("_links")
    @Expose
    public Links links;
    public final static Creator<Profile> CREATOR = new Creator<Profile>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Profile createFromParcel(Parcel in) {
            Profile instance = new Profile();
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.dateCreated = ((String) in.readValue((String.class.getClassLoader())));
            instance.dateCreatedGmt = ((String) in.readValue((String.class.getClassLoader())));
            instance.dateModified = ((String) in.readValue((String.class.getClassLoader())));
            instance.dateModifiedGmt = ((String) in.readValue((String.class.getClassLoader())));
            instance.email = ((String) in.readValue((String.class.getClassLoader())));
            instance.firstName = ((String) in.readValue((String.class.getClassLoader())));
            instance.lastName = ((String) in.readValue((String.class.getClassLoader())));
            instance.role = ((String) in.readValue((String.class.getClassLoader())));
            instance.username = ((String) in.readValue((String.class.getClassLoader())));
            instance.billing = ((Billing) in.readValue((Billing.class.getClassLoader())));
            instance.shipping = ((Shipping) in.readValue((Shipping.class.getClassLoader())));
            instance.isPayingCustomer = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.ordersCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.totalSpent = ((String) in.readValue((String.class.getClassLoader())));
            instance.avatarUrl = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.metaData, (Object.class.getClassLoader()));
            instance.links = ((Links) in.readValue((Links.class.getClassLoader())));
            return instance;
        }

        public Profile[] newArray(int size) {
            return (new Profile[size]);
        }

    };

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(dateCreated);
        dest.writeValue(dateCreatedGmt);
        dest.writeValue(dateModified);
        dest.writeValue(dateModifiedGmt);
        dest.writeValue(email);
        dest.writeValue(firstName);
        dest.writeValue(lastName);
        dest.writeValue(role);
        dest.writeValue(username);
        dest.writeValue(billing);
        dest.writeValue(shipping);
        dest.writeValue(isPayingCustomer);
        dest.writeValue(ordersCount);
        dest.writeValue(totalSpent);
        dest.writeValue(avatarUrl);
        dest.writeList(metaData);
        dest.writeValue(links);
    }

    public int describeContents() {
        return 0;
    }

}
