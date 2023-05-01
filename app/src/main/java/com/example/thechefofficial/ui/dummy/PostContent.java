package com.example.thechefofficial.ui.dummy;

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
public class PostContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<PostItem> ITEMS = new ArrayList<PostItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, PostItem> ITEM_MAP = new HashMap<String, PostItem>();

    private static final int COUNT = 25;

   /* static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(PostItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static PostItem createDummyItem(int position) {
        return new PostItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }
*/
    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public class PostItem {
        private String recipeName;
        private String recipeDes;
        private String recipImg;
        private String userId;

       /* public postModel() {
        }*/

        public PostItem(String recipeName, String recipeDes, String recipImg, String userId) {
            this.recipeName = recipeName;
            this.recipeDes = recipeDes;
            this.recipImg = recipImg;
            this.userId = userId;
        }

        public void setRecipeName(String recipeName) {
            this.recipeName = recipeName;
        }

        public void setRecipeDes(String recipeDes) {
            this.recipeDes = recipeDes;
        }

        public void setRecipeDis(String recipeDis) {
            this.recipImg = recipeDis;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}