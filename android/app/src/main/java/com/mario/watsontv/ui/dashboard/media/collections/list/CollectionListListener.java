package com.mario.watsontv.ui.dashboard.media.collections.list;

import com.mario.watsontv.responses.CollectionResponse;

public interface CollectionListListener {
    void delete(String collectionId);
    void goToDetails(String collectionId);
}
