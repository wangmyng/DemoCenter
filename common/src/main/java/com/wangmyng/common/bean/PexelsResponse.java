package com.wangmyng.common.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PexelsResponse {

    /**
     * page : 1
     * per_page : 15
     * total_results : 236
     * url : https://www.pexels.com/search/example%20query/
     * next_page : https://api.pexels.com/v1/search/?page=2&per_page=15&query=example+query
     * photos : [{"width":1000,"height":1000,"url":"https://www.pexels.com/photo/12345","photographer":"Name","src":{"original":"https://*.jpg","large":"https://*.jpg","large2x":"https://*.jpg","medium":"https://*.jpg","small":"https://*.jpg","portrait":"https://*.jpg","landscape":"https://*.jpg","tiny":"https://*.jpg"}}]
     */

    private int page;
    private int per_page;
    private int total_results;
    private String url;
    private String next_page;
    private List<PhotosBean> photos;

    public int getPage() { return page;}

    public void setPage(int page) { this.page = page;}

    public int getPer_page() { return per_page;}

    public void setPer_page(int per_page) { this.per_page = per_page;}

    public int getTotal_results() { return total_results;}

    public void setTotal_results(int total_results) { this.total_results = total_results;}

    public String getUrl() { return url;}

    public void setUrl(String url) { this.url = url;}

    public String getNext_page() { return next_page;}

    public void setNext_page(String next_page) { this.next_page = next_page;}

    public List<PhotosBean> getPhotos() { return photos;}

    public void setPhotos(List<PhotosBean> photos) { this.photos = photos;}

    public static class PhotosBean implements Parcelable {
        /**
         * width : 1000
         * height : 1000
         * url : https://www.pexels.com/photo/12345
         * photographer : Name
         * src : {"original":"https://*.jpg","large":"https://*.jpg","large2x":"https://*.jpg","medium":"https://*.jpg","small":"https://*.jpg","portrait":"https://*.jpg","landscape":"https://*.jpg","tiny":"https://*.jpg"}
         */

        private int width;
        private int height;
        private String url;
        private String photographer;
        private SrcBean src;

        public int getWidth() { return width;}

        public void setWidth(int width) { this.width = width;}

        public int getHeight() { return height;}

        public void setHeight(int height) { this.height = height;}

        public String getUrl() { return url;}

        public void setUrl(String url) { this.url = url;}

        public String getPhotographer() { return photographer;}

        public void setPhotographer(String photographer) { this.photographer = photographer;}

        public SrcBean getSrc() { return src;}

        public void setSrc(SrcBean src) { this.src = src;}

        public static class SrcBean implements Parcelable {
            /**
             * original : https://*.jpg
             * large : https://*.jpg
             * large2x : https://*.jpg
             * medium : https://*.jpg
             * small : https://*.jpg
             * portrait : https://*.jpg
             * landscape : https://*.jpg
             * tiny : https://*.jpg
             */

            private String original;
            private String large;
            private String large2x;
            private String medium;
            private String small;
            private String portrait;
            private String landscape;
            private String tiny;

            public String getOriginal() { return original;}

            public void setOriginal(String original) { this.original = original;}

            public String getLarge() { return large;}

            public void setLarge(String large) { this.large = large;}

            public String getLarge2x() { return large2x;}

            public void setLarge2x(String large2x) { this.large2x = large2x;}

            public String getMedium() { return medium;}

            public void setMedium(String medium) { this.medium = medium;}

            public String getSmall() { return small;}

            public void setSmall(String small) { this.small = small;}

            public String getPortrait() { return portrait;}

            public void setPortrait(String portrait) { this.portrait = portrait;}

            public String getLandscape() { return landscape;}

            public void setLandscape(String landscape) { this.landscape = landscape;}

            public String getTiny() { return tiny;}

            public void setTiny(String tiny) { this.tiny = tiny;}

            @Override
            public int describeContents() { return 0; }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.original);
                dest.writeString(this.large);
                dest.writeString(this.large2x);
                dest.writeString(this.medium);
                dest.writeString(this.small);
                dest.writeString(this.portrait);
                dest.writeString(this.landscape);
                dest.writeString(this.tiny);
            }

            public SrcBean() {}

            protected SrcBean(Parcel in) {
                this.original = in.readString();
                this.large = in.readString();
                this.large2x = in.readString();
                this.medium = in.readString();
                this.small = in.readString();
                this.portrait = in.readString();
                this.landscape = in.readString();
                this.tiny = in.readString();
            }

            public static final Creator<SrcBean> CREATOR = new Creator<SrcBean>() {
                @Override
                public SrcBean createFromParcel(Parcel source) {return new SrcBean(source);}

                @Override
                public SrcBean[] newArray(int size) {return new SrcBean[size];}
            };
        }

        @Override
        public int describeContents() { return 0; }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.width);
            dest.writeInt(this.height);
            dest.writeString(this.url);
            dest.writeString(this.photographer);
            dest.writeParcelable(this.src, flags);
        }

        public PhotosBean() {}

        protected PhotosBean(Parcel in) {
            this.width = in.readInt();
            this.height = in.readInt();
            this.url = in.readString();
            this.photographer = in.readString();
            this.src = in.readParcelable(SrcBean.class.getClassLoader());
        }

        public static final Parcelable.Creator<PhotosBean> CREATOR = new Parcelable.Creator<PhotosBean>() {
            @Override
            public PhotosBean createFromParcel(Parcel source) {return new PhotosBean(source);}

            @Override
            public PhotosBean[] newArray(int size) {return new PhotosBean[size];}
        };
    }
}
