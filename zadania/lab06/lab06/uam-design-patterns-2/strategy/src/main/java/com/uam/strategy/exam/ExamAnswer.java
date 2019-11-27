package com.uam.strategy.exam;

class ExamAnswer {

    private String value;

    ExamAnswer(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamAnswer that = (ExamAnswer) o;
        return value.equals(that.value);
    }
}
