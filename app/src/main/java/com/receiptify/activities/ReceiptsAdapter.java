package com.receiptify.activities;

        /*
         * Copyright (C) 2017 Google Inc.
         *
         * Licensed under the Apache License, Version 2.0 (the "License");
         * you may not use this file except in compliance with the License.
         * You may obtain a copy of the License at
         *
         *      http://www.apache.org/licenses/LICENSE-2.0
         *
         * Unless required by applicable law or agreed to in writing, software
         * distributed under the License is distributed on an "AS IS" BASIS,
         * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
         * See the License for the specific language governing permissions and
         * limitations under the License.
         */

        import android.content.Context;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import com.receiptify.R;
        import com.receiptify.data.Entities.Receipts;

        import java.util.List;


public class ReceiptsAdapter extends RecyclerView.Adapter<ReceiptsAdapter.ReceiptsViewHolder> {

    class ReceiptsViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private ReceiptsViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Receipts> mWords; // Cached copy of words

    ReceiptsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ReceiptsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ReceiptsViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ReceiptsViewHolder holder, int position) {
        if (mWords != null) {
            Receipts current = mWords.get(position);
            holder.wordItemView.setText(current.getCompany());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
        }
    }

    void setWords(List<Receipts> words) {
        mWords = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }
}


