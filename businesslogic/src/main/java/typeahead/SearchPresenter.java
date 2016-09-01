package typeahead;

import com.android.volley.VolleyError;
import com.webileapps.data.factories.DataLayerFactory;
import com.webileapps.data.framework.volley.WAGsonRequest;
import com.webileapps.data.search.AlbumSearchResponse;
import com.webileapps.data.search.IAlbumSearchService;


public class SearchPresenter implements ISearchPresenter {


    ISearchView lastFmView;
    IAlbumSearchService iLastFmService;

    public SearchPresenter(ISearchView lastFmView) {
        this.lastFmView=lastFmView;
        iLastFmService= DataLayerFactory.getNetworkLayerFactory().getAlbumSearchService();

    }

    @Override
    public void doSearch(String albumName) {

        lastFmView.showProgressBar();

        iLastFmService.setCallBack(new WAGsonRequest.WAVolleyCallBack<AlbumSearchResponse>() {
            @Override
            public void onSuccess(AlbumSearchResponse response) {
                lastFmView.setData(response);
                lastFmView.dismissProgressBar();
            }

            @Override
            public void onError(VolleyError volleyError) {
                lastFmView.dismissProgressBar();
            }
        });
        iLastFmService.setAlbumName(albumName).setParams();
        iLastFmService.sendRequest();
    }
}
