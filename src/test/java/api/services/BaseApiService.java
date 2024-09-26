package api.services;

import api.BaseApi;

public class BaseApiService {
    protected BaseApi api;

    public BaseApiService() {
        this.api = new BaseApi();
    }

    public BaseApi getBaseApi() {
        return this.api;
    }
}
