package io.dexi.service.components;

import io.dexi.service.AppContext;

/**
 * Interface for the "notification" component type
 *
 * @param <T> the activation configuration DTO
 * @param <U> the component configuration DTO
 */
public interface NotificationAppComponent<T, U> extends BaseAppComponent<U> {

    /**
     * Method that is invoked whenever a notification component is asked to notify someone.
     *
     * You read the file content from the request (request.getInputStream()) and also have the content-type
     * available from the headers whenever possible.
     *
     * Will be exposed as POST /dexi/notification/invoke
     *
     * @param ctxt Context information about the current activation and component configuration
     * @param notification the notification information from the dexi system.
     */
    void invoke(AppContext<T, U> ctxt, Notification notification);


    enum NotificationType {
        INFO,
        WARNING,
        ERROR
    }

    class Notification {

        private String accountName;

        private String userFullName;

        private String userEmail;

        private String subject;

        private String message;

        private String url;

        private NotificationType type;

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getUserFullName() {
            return userFullName;
        }

        public void setUserFullName(String userFullName) {
            this.userFullName = userFullName;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public NotificationType getType() {
            return type;
        }

        public void setType(NotificationType type) {
            this.type = type;
        }
    }
}
