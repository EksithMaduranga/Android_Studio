package com.example.thechefofficial.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 5;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Comment about recipe: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nComment." + i);
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;
        String recipImg,recipeName,recipeDes, userId,userName, userPic;

        public DummyItem(String id, String content, String details) {


            this.id = id;
            this.content = content;
            this.details = details;
        }


        public String getRecipeName() {
            return recipeName;
        }

        public void setRecipeName(String recipeName) {
            this.recipeName = recipeName;
        }

        public String getRecipeDes() {
            return recipeDes;
        }

        public void setRecipeDes(String recipeDes) {
            this.recipeDes = recipeDes;
        }

        public String getRecipImg() {
            return recipImg;
        }

        public void setRecipImg(String recipImg) {
            this.recipImg = recipImg;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserPic() {
            return userPic;
        }

        public void setUserPic(String userPic) {
            this.userPic = userPic;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}