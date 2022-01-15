package com.lglf77.flipviewpagerdracoyalantis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yalantis.flipviewpager.R;
import com.yalantis.flipviewpager.utils.FlipSettings;
import com.yalantis.flipviewpager.view.FlipViewPager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class BaseFlipAdapter extends RecyclerView.Adapter<BaseFlipAdapter.BaseViewHolder> {

    /** Estamos salvando qual FlipViewPager está em qual posição para que possamos chamar
     *  flipToPage() em um clique próximo.*/
    protected Map<Integer, FlipViewPager> flipViewPagerMap = new HashMap<>();
    private final List items;
    private final FlipSettings settings;
    private final LayoutInflater inflater;

    public BaseFlipAdapter(Context context, List items, FlipSettings settings) {
        this.items = items;
        this.settings = settings;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i) {
        View v = inflater
                .inflate(R.layout.flipper, viewGroup, false);
        BaseViewHolder baseViewHolder = new BaseViewHolder ( v );
        baseViewHolder.mFlipViewPager = (FlipViewPager) v.findViewById(R.id.flip_view);
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, final int position) {
        // Truque para dividir a lista em 2 partes
        Object item1 = items.get(position * 2);
        // Usado para casos em que não temos um tamanho uniforme na lista de entrada
        Object item2 = items.size() > (position * 2 + 1) ? items.get(position * 2 + 1) : null;

        // Ouvinte para armazenar a página invertida
        baseViewHolder.mFlipViewPager.setOnChangePageListener(new FlipViewPager.OnChangePageListener() {
            @Override
            public void onFlipped(int page) {
                settings.savePageState(position, page);
            }
        });

        flipViewPagerMap.put(position, baseViewHolder.mFlipViewPager);
        baseViewHolder.mFlipViewPager.setAdapter(new MergeAdapter(item1, item2, position), settings.getDefaultPage(), position, items.size());
    }

    @Override
    public int getItemCount() {
        // Verificando se precisamos de uma linha adicional para um único item
        return items.size() % 2 != 0 ? ((items.size() / 2) + 1) : (items.size() / 2);
    }

    static class BaseViewHolder extends RecyclerView.ViewHolder {

        FlipViewPager mFlipViewPager;

        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    public abstract View getPage(
            int position, View convertView, ViewGroup parent, Object item1, Object item2, CloseListener closeListener);

    public abstract int getPagesCount();

    // Adaptador mescla 2 itens juntos
    private class MergeAdapter extends BaseAdapter implements CloseListener {
        private final Object item1;
        private final Object item2;
        private final int position;

        public MergeAdapter(Object item1, Object item2, int position) {
            this.item1 = item1;
            this.item2 = item2;
            this.position = position;
        }

        @Override
        public int getCount() {
            return item2 == null ? getPagesCount() - 1 : getPagesCount();
        }

        @Override
        public Object getItem(int position) {
            return position; // Stub
        }

        @Override
        public long getItemId(int position) {
            return position; // Stub
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getPage(position, convertView, parent, item1, item2, this);
        }

        @Override
        public void onClickClose() {
            Objects.requireNonNull (flipViewPagerMap.get (position)).flipToPage(1);
        }
    }

    public interface CloseListener {
        void onClickClose();
    }

}