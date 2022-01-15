package com.lglf77.flipviewpagerdracoyalantis.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Friend {
    private final int avatar;
    private final String nickname;
    private final int background;
    private final List<String> interests = new ArrayList<>();

    public Friend(int avatar, String nickname, int background, String... interest) {
        this.avatar = avatar;
        this.nickname = nickname;
        this.background = background;
        interests.addAll(Arrays.asList(interest));
    }

    public int getAvatar() {
        return avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public int getBackground() {
        return background;
    }

    public List<String> getInterests() {
        return interests;
    }

}
