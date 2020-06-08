package dev.as.m.profilematchinglintasminat;

public class AlertMessage {

    private String title, message, updateQuery, deleteQuery;

    public AlertMessage(String title, String message){
        this.title = title;
        this.message = message;
    }



    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }
    String id;
    public AlertMessage setId(String id){
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }
    String table;

    public String getTable() {
        return table;
    }

    public AlertMessage setTable(String table) {
        this.table = table;
        return this;
    }

    String field;

    public String getField() {
        return field;
    }

    public AlertMessage setField(String field) {
        this.field = field;
        return this;
    }
}
