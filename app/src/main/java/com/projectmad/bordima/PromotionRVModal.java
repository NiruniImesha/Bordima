package com.projectmad.bordima;

import android.os.Parcel;
import android.os.Parcelable;

public class PromotionRVModal implements Parcelable {
    private String promotionId;
    private String promotionName;
    private String promotionImgLink;
    private String promotionLink;

    public PromotionRVModal(){

    }

    public PromotionRVModal(String promotionId, String promotionName, String promotionImgLink, String promotionLink) {
        this.promotionId = promotionId;
        this.promotionName = promotionName;
        this.promotionImgLink = promotionImgLink;
        this.promotionLink = promotionLink;
    }

    protected PromotionRVModal(Parcel in) {
        promotionId = in.readString();
        promotionName = in.readString();
        promotionImgLink = in.readString();
        promotionLink = in.readString();
    }

    public static final Creator<PromotionRVModal> CREATOR = new Creator<PromotionRVModal>() {
        @Override
        public PromotionRVModal createFromParcel(Parcel in) {
            return new PromotionRVModal(in);
        }

        @Override
        public PromotionRVModal[] newArray(int size) {
            return new PromotionRVModal[size];
        }
    };

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getPromotionImgLink() {
        return promotionImgLink;
    }

    public void setPromotionImgLink(String promotionImgLink) {
        this.promotionImgLink = promotionImgLink;
    }

    public String getPromotionLink() {
        return promotionLink;
    }

    public void setPromotionLink(String promotionLink) {
        this.promotionLink = promotionLink;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(promotionId);
        parcel.writeString(promotionName);
        parcel.writeString(promotionImgLink);
        parcel.writeString(promotionLink);
    }
}
