package io.dexi.service.handlers;

import io.dexi.service.utils.ResultStream;
import io.dexi.service.utils.RowStream;

/**
 * Implement this handler to handle data-filter components.
 *
 * @param <T> the activation configuration DTO
 * @param <U> the component configuration DTO
 */
public interface DataFilterHandler<T, U> extends BaseComponentHandler<U> {

    /**
     * Method that is invoked for all data-filter components for belonging to this app.
     *
     * Will be exposed as POST /dexi/data/filter/invoke
     *
     * @param ctxt Context information about the current activation and component configuration
     * @param inputs a stream of inputs coming from dexi
     * @param outputs a stream of outputs from your app.
     */
    void filter(AppContext<T,U> ctxt, RowStream inputs, ResultStream outputs);

}
