package typeahead;

import com.webileapps.data.search.AlbumSearchResponse;


public interface ISearchView extends IActivityBaseView {
    void setData(AlbumSearchResponse response);
    void showProgressBar();
    void dismissProgressBar();
}
