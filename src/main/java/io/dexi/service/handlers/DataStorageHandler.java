package io.dexi.service.handlers;

import io.dexi.service.Rows;

public interface DataStorageHandler<T, U> {
    default Object write(String activationId, T activationConfig, String componentId, U componentConfig, Rows rows) { return false; }

    //Class<U> getDataStoragePayloadClass();

    /*
    abstract class AbstractDataStoragePayload<V> {
        private V config;

        private Rows rows = new Rows();

        public V getConfig() {
            return config;
        }

        public void setConfig(V config) {
            this.config = config;
        }

        public Rows getRows() {
            return rows;
        }

        public void setRows(Rows rows) {
            this.rows = rows;
        }
    }
    */
}
