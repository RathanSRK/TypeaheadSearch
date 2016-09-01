package com.webileapps.data.factories;


public class DataLayerFactory {

    private static INetworkLayerFactory networkLayerFactory;

    public static void setNetworkLayerFactory(INetworkLayerFactory networkLayerFactory) {
        DataLayerFactory.networkLayerFactory = networkLayerFactory;
    }

    public static INetworkLayerFactory getNetworkLayerFactory() {
        return networkLayerFactory;
    }


}
