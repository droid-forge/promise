/*
 * Copyright 2017, Solutech RMS
 * Licensed under the Apache License, Version 2.0, "Solutech Limited".
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.dev4vin.model;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.RecyclerView;
import me.dev4vin.R;
import me.dev4vin.view.PromiseAdapter;

/**
 * Created on 6/10/18 by yoctopus.
 */
public class Group<K extends Viewable, T extends Viewable> extends Category<K, T> implements Viewable {


    private PromiseAdapter<T> adapter;
    private PromiseAdapter.Listener<T> listener;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.ItemDecoration itemDecoration;

    public Group(K name) {
        super(name);
    }

    public Group<K, T> layoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        return this;
    }

    public Group<K, T> childrenUnderlined(RecyclerView.ItemDecoration childrenUnderlined) {
        this.itemDecoration = childrenUnderlined;
        return this;
    }

    public Group<K, T> listener(PromiseAdapter.Listener<T> listener) {
        this.listener = listener;
        return this;
    }

    public PromiseAdapter<T> getAdapter() {
        if (adapter == null) adapter = new PromiseAdapter<>(listener);
        return adapter;
    }

    private RecyclerView groupItems;

    @Override
    public int layout() {

        return R.layout.group_item_layout;
    }

    @Override
    public void init(View view) {
        FrameLayout headerContent = view.findViewById(R.id.header_content);
        headerContent.removeAllViews();
        LayoutInflater.from(view.getContext()).inflate(name().layout(), headerContent, true);
        name().init(headerContent);
        groupItems = view.findViewById(R.id.group_items);
        groupItems.setLayoutManager(layoutManager);
        if (itemDecoration != null) groupItems.addItemDecoration(itemDecoration);
    }

    @Override
    public void bind(View view) {
        name().bind(view);
        groupItems.setAdapter(getAdapter());
        getAdapter().add(list());
    }

    private int index;

    @Override
    public void index(int index) {
        this.index = index;
    }

    @Override
    public int index() {
        return index;
    }
}
