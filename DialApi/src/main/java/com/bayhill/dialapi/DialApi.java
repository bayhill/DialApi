package com.bayhill.dialapi;

/**
 * Created by Emil on 2014-05-19.
 */
public class DialApi {
    private final String applicationName;
    private final int searchTimeout;
    private final IDeviceCallback deviceCallback;
    /**
     * DialApi constructor.
     * Note: Use a Builder object to instantiate this class
     * @param applicationName the name of the application to communicate with. Note that it is
     *                        case sensitive, meaning that for instance "YouTube" ain't the same
     *                        as "youtube". This is so because of DIAL protocol specifications.
     * @param searchTimeout the timeout in seconds for searching for devices on the network
     */
    private DialApi(final String applicationName, final int searchTimeout,
                    final IDeviceCallback deviceCallback){
        this.applicationName = applicationName;
        this.searchTimeout = searchTimeout;
        this.deviceCallback = deviceCallback;
    }

    /**
     * Builder object for creating an instance of the DialApi
     */
    public static class Builder{
        private String applicationName = "";
        private IDeviceCallback deviceCallback = null;
        private int searchTimeout = 10;

        public Builder(){}

        /**
         * Sets the application name to use for communication
         * @param applicationName the name of the application
         * @return the builder instance
         */
        public Builder setApplicationName(String applicationName){
            this.applicationName = applicationName;
            return this;
        }

        public Builder setSearchTimeout(int searchTimeout){
            this.searchTimeout = searchTimeout;
            return this;
        }

        public Builder setCallback(IDeviceCallback deviceCallback){
            this.deviceCallback = deviceCallback;
            return this;
        }

        /**
         * Builds an instance of the DialApi class
         * @return an instance of the DialApi class
         * @throws IllegalStateException if no application name has been set prior to calling
         * {@code build()}
         */
        public DialApi build() throws IllegalStateException{
            if ( applicationName.isEmpty() || deviceCallback == null) throw new IllegalStateException();
            return new DialApi(this.applicationName, this.searchTimeout, this.deviceCallback);
        }
    }

    protected int getSearchTimeout(){
        return this.searchTimeout;
    }
    protected IDeviceCallback getDeviceCallback(){
        return this.deviceCallback;
    }

}
