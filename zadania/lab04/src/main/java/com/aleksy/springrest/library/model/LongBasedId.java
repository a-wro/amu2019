package com.aleksy.springrest.library.model;

public class LongBasedId {
    private Long id;

    public Long getId() {
        return id;
    }

    public LongBasedId() {
    }

    public LongBasedId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object other) {

        if (other == null) {
            return false;
        }

        if (other == this) {
            return true;
        }

        if (other instanceof LongBasedId) {
            LongBasedId otherId = (LongBasedId) other;

            return otherId.getId().equals(id);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
