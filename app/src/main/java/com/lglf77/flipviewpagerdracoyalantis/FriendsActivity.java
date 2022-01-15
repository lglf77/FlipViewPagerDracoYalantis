package com.lglf77.flipviewpagerdracoyalantis;

 // https://github.com/Yalantis/FlipViewPager.Draco
 // VIDEO: https://www.youtube.com/watch?v=zNRPjS53m5w
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.lglf77.flipviewpagerdracoyalantis.adapter.BaseFlipAdapter;
import com.lglf77.flipviewpagerdracoyalantis.model.Friend;
import com.yalantis.flipviewpager.utils.FlipSettings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        final ListView friends = (ListView) findViewById(R.id.friends);

        FlipSettings settings = new FlipSettings.Builder().defaultPage(1).build();
        // friends.setAdapter(new FriendsAdapter(this, Utils.friends, settings)); // ERROR

        friends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Friend f = (Friend) friends.getAdapter().getItem(position);
                Toast.makeText(FriendsActivity.this, f.getNickname(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    class FriendsAdapter extends BaseFlipAdapter {

        private final int[] IDS_INTEREST = {
                R.id.interest_1,
                R.id.interest_2,
                R.id.interest_3,
                R.id.interest_4,
                R.id.interest_5
        };

        public FriendsAdapter(Context context, List<Friend> items, FlipSettings settings) {
            super(context, items, settings);
        }

        @Override
        public View getPage(int position, View convertView, ViewGroup parent, Object friend1,
                            Object friend2, BaseFlipAdapter.CloseListener closeListener) {
            final FriendsHolder holder;

            if (convertView == null) {
                holder = new FriendsHolder();
                convertView = getLayoutInflater().inflate(R.layout.friends_merge_page, parent, false);
                holder.leftAvatar = (ImageView) convertView.findViewById(R.id.first);
                holder.rightAvatar = (ImageView) convertView.findViewById(R.id.second);
                holder.infoPage = getLayoutInflater().inflate(R.layout.friends_info, parent, false);
                holder.nickName = (TextView) holder.infoPage.findViewById(R.id.nickname);

                for (int id : IDS_INTEREST)
                    holder.interests.add((TextView) holder.infoPage.findViewById(id));

                convertView.setTag(holder);
            } else {
                holder = (FriendsHolder) convertView.getTag();
            }

            // Página mesclada com 2 amigos
            if (position == 1){
                holder.leftAvatar.setImageResource ( ((Friend) friend1).getAvatar () );
                if (friend2 != null)
                    holder.rightAvatar.setImageResource ( ((Friend) friend2).getAvatar () );
            } else {
                fillHolder ( holder,position == 0 ? (Friend) friend1 : (Friend) friend2 );
                holder.infoPage.setTag ( holder );
                return holder.infoPage;
            }
            return convertView;
        }

        @Override
        public int getPagesCount() {
            return 3;
        }

        private void fillHolder(FriendsHolder holder, Friend friend) {
            if (friend == null)
                return;
            Iterator<TextView> iViews = holder.interests.iterator();
            Iterator<String> iInterests = friend.getInterests().iterator();
            while (iViews.hasNext() && iInterests.hasNext())
                iViews.next().setText(iInterests.next());
            holder.infoPage.setBackgroundColor(getResources().getColor(friend.getBackground()));
            holder.nickName.setText(friend.getNickname());
        }

        class FriendsHolder {
            ImageView leftAvatar;
            ImageView rightAvatar;
            View infoPage;

            List<TextView> interests = new ArrayList<> ();
            TextView nickName;
        }
    }

}
