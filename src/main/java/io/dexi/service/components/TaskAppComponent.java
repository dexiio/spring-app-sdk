package io.dexi.service.components;

import io.dexi.service.AppContext;

import java.util.*;

/**
 * Interface for the "task" component type
 *
 * @param <T> the activation configuration DTO
 * @param <U> the component configuration DTO
 */
public interface TaskAppComponent<T, U> extends BaseAppComponent<U> {

    /**
     * Method that is invoked whenever a task component is asked to send updated task information
     *
     * You read the file content from the request (request.getInputStream()) and also have the content-type
     * available from the headers whenever possible.
     *
     * Will be exposed as POST /dexi/task/invoke
     *
     * @param ctxt Context information about the current activation and component configuration
     * @param task the notification information from the dexi system.
     */
    void invoke(AppContext<T, U> ctxt, Task task);

    enum TaskStatus {
        PENDING,
        DOING,
        DONE
    }

    enum IssueState {
        PENDING,
        RESOLVED,
        IGNORED
    }

    class Task {

        private String accountName;

        private String description;

        private TaskStatus status;

        private List<User> members = new ArrayList<>();

        private List<Issue> issues = new ArrayList<>();

        private List<Comment> comments = new ArrayList<>();

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public List<User> getMembers() {
            return members;
        }

        public void setMembers(List<User> members) {
            this.members = members;
        }

        public List<Issue> getIssues() {
            return issues;
        }

        public void setIssues(List<Issue> issues) {
            this.issues = issues;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public TaskStatus getStatus() {
            return status;
        }

        public void setStatus(TaskStatus status) {
            this.status = status;
        }

        public List<Comment> getComments() {
            return comments;
        }

        public void setComments(List<Comment> comments) {
            this.comments = comments;
        }
    }

    class Comment {

        private Date created;

        private String userFullName;

        private String content;

        public Date getCreated() {
            return created;
        }

        public void setCreated(Date created) {
            this.created = created;
        }

        public String getUserFullName() {
            return userFullName;
        }

        public void setUserFullName(String userFullName) {
            this.userFullName = userFullName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    class Issue {

        private IssueState issueState;

        private String description;

        private Date firstSeen;

        private Date lastSeen;

        private long hits;

        public IssueState getIssueState() {
            return issueState;
        }

        public void setIssueState(IssueState issueState) {
            this.issueState = issueState;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Date getFirstSeen() {
            return firstSeen;
        }

        public void setFirstSeen(Date firstSeen) {
            this.firstSeen = firstSeen;
        }

        public Date getLastSeen() {
            return lastSeen;
        }

        public void setLastSeen(Date lastSeen) {
            this.lastSeen = lastSeen;
        }

        public long getHits() {
            return hits;
        }

        public void setHits(long hits) {
            this.hits = hits;
        }
    }

    class User {

        private String userFullName;

        private String userEmail;

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
    }
}
