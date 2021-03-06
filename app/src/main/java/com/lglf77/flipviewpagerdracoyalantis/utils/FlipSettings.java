package com.lglf77.flipviewpagerdracoyalantis.utils;

import java.util.HashMap;
import java.util.Map;

public class FlipSettings {

    private final int defaultPage;

    private final Map<Integer, Integer> pages = new HashMap<>();

    private FlipSettings(int defaultPage) {
        this.defaultPage = defaultPage;
    }

    public void savePageState(int position, int page) {
        pages.put(position, page);
    }

    public Integer getPageForPosition(int position) {
        return pages.containsKey(position) ? pages.get(position) : defaultPage;
    }

    public int getDefaultPage() {
        return defaultPage;
    }

    public static class Builder {

        private int defaultPage = 1;

        public Builder defaultPage(int page) {
            this.defaultPage = page;
            return this;
        }

        public FlipSettings build() {
            return new FlipSettings(defaultPage);
        }
    }

}
