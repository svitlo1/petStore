package api.steps;

import api.services.StoreService;

public class OrderSteps {
    protected final StoreService storeService;

    public OrderSteps() {
        this.storeService = new StoreService();
    }
}
